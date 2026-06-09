package org.example.controller;

import org.example.model.Post;
import org.example.model.User;
import org.example.repository.FollowRepository;
import org.example.repository.PostRepository;
import org.example.repository.UserRepository;
import org.example.service.PostService;
import org.example.service.TimelineService;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class TimelineController {

    private static final int PAGE_SIZE = 20;

    private final TimelineService timelineService;
    private final PostService postService;
    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final PostRepository postRepository;

    public TimelineController(TimelineService timelineService, PostService postService,
                               UserRepository userRepository, FollowRepository followRepository,
                               PostRepository postRepository) {
        this.timelineService = timelineService;
        this.postService = postService;
        this.userRepository = userRepository;
        this.followRepository = followRepository;
        this.postRepository = postRepository;
    }

    @GetMapping("/")
    public Mono<String> timeline(
            @RequestParam(defaultValue = "1") Long userId,
            Model model) {
        Mono<User> userMono = userRepository.findById(userId);
        Flux<Post> posts = timelineService.getTimeline(userId, null, PAGE_SIZE)
            .switchIfEmpty(Flux.defer(() -> fallbackPosts(userId)));
        Mono<Long> latestTs = timelineService.getLatestTimestamp(userId);
        Mono<Long> followerCount = followRepository
            .findFollowerIdsByFolloweeId(userId).count();

        return Mono.zip(userMono, posts.collectList(), latestTs, followerCount)
            .flatMap(tuple -> {
                model.addAttribute("currentUser", tuple.getT1());
                model.addAttribute("posts", tuple.getT2());
                model.addAttribute("latestTimestamp", tuple.getT3());
                model.addAttribute("followerCount", tuple.getT4());
                model.addAttribute("userId", userId);
                return enrichWithAuthors(tuple.getT2())
                    .doOnNext(authors -> model.addAttribute("authors", authors))
                    .thenReturn("timeline");
            });
    }

    @GetMapping(value = "/timeline/newer", produces = MediaType.TEXT_HTML_VALUE)
    public Mono<String> newerPosts(
            @RequestParam(defaultValue = "1") Long userId,
            @RequestParam(defaultValue = "0") Long after,
            Model model) {
        return timelineService.getNewPosts(userId, after, PAGE_SIZE)
            .collectList()
            .flatMap(posts -> {
                model.addAttribute("posts", posts);
                long maxTs = posts.isEmpty() ? after :
                    posts.stream().mapToLong(p -> p.getCreatedAt().toEpochMilli()).max().orElse(after);
                model.addAttribute("latestTimestamp", maxTs);
                return enrichWithAuthors(posts)
                    .doOnNext(authors -> model.addAttribute("authors", authors))
                    .thenReturn("fragments/post-list :: post-list");
            });
    }

    @GetMapping(value = "/timeline/older", produces = MediaType.TEXT_HTML_VALUE)
    public Mono<String> olderPosts(
            @RequestParam(defaultValue = "1") Long userId,
            @RequestParam Long before,
            Model model) {
        return timelineService.getTimeline(userId, before, PAGE_SIZE)
            .collectList()
            .flatMap(posts -> {
                model.addAttribute("posts", posts);
                return enrichWithAuthors(posts)
                    .doOnNext(authors -> model.addAttribute("authors", authors))
                    .thenReturn("fragments/post-list :: post-list");
            });
    }

    @PostMapping(value = "/posts", produces = MediaType.TEXT_HTML_VALUE)
    public Mono<String> createPost(
            ServerWebExchange exchange,
            Model model) {
        return exchange.getFormData()
            .defaultIfEmpty(exchange.getRequest().getQueryParams())
            .flatMap(params -> {
                Long userId = Long.valueOf(params.getFirst("userId") != null
                    ? params.getFirst("userId") : "1");
                String content = params.getFirst("content");
                if (content == null || content.isBlank()) {
                    return Mono.error(new IllegalArgumentException("content is required"));
                }
                return postService.createPost(userId, content);
            })
            .flatMap(post -> userRepository.findById(post.getUserId())
                .map(user -> {
                    model.addAttribute("post", post);
                    model.addAttribute("author", user);
                    return "fragments/post-list :: post-item";
                }));
    }

    @ResponseBody
    @GetMapping(value = "/timeline/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> streamTimeline(
            @RequestParam(defaultValue = "1") Long userId) {
        return followRepository.findFolloweeIdsByFollowerId(userId)
            .collectList()
            .flatMapMany(followeeIds -> {
                followeeIds.add(userId);
                return timelineService.liveEvents()
                    .filter(event -> followeeIds.contains(event.userId()));
            })
            .flatMap(event -> userRepository.findById(event.userId())
                .flatMapMany(author -> postRepository.findById(event.postId())
                    .map(post -> ServerSentEvent.<String>builder()
                        .id(String.valueOf(post.getId()))
                        .event("new-post")
                        .data(renderPostHtml(post, author))
                        .build())))
            .mergeWith(Flux.interval(Duration.ofSeconds(30))
                .map(i -> ServerSentEvent.<String>builder()
                    .event("__ping")
                    .comment("keepalive")
                    .build()));
    }

    private Mono<Map<Long, User>> enrichWithAuthors(List<Post> posts) {
        if (posts.isEmpty()) return Mono.just(Map.of());
        var userIds = posts.stream().map(Post::getUserId).distinct().collect(Collectors.toList());
        return userRepository.findAllById(userIds)
            .collectMap(User::getId);
    }

    private Flux<Post> fallbackPosts(Long userId) {
        return followRepository.findFolloweeIdsByFollowerId(userId)
            .collectList()
            .flatMapMany(followeeIds -> {
                followeeIds.add(userId);
                return postRepository.findPostsByUserIds(followeeIds, PAGE_SIZE);
            });
    }

    private static String renderPostHtml(Post post, User author) {
        return "<div class=\"post\" id=\"post-" + post.getId()
            + "\" data-timestamp=\"" + post.getCreatedAt().toEpochMilli() + "\">"
            + "<div class=\"author\">" + escapeHtml(author.getDisplayName()) + "</div>"
            + "<div class=\"content\">" + escapeHtml(post.getContent()) + "</div>"
            + "<div class=\"meta\">just now</div>"
            + "</div>";
    }

    private static String escapeHtml(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;")
            .replace("\"", "&quot;");
    }
}
