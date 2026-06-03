package com.example.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DemoController {

    private final List<Task> tasks = new ArrayList<>();

    public DemoController() {
        tasks.add(new Task("Learn Spring Boot"));
        tasks.add(new Task("Integrate HTMX"));
        tasks.add(new Task("Explore Java 25 features"));
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("tasks", tasks);
        model.addAttribute("stats", computeStats());
        return "index";
    }

    @GetMapping("/tasks")
    public String taskList(Model model) {
        model.addAttribute("tasks", tasks);
        model.addAttribute("stats", computeStats());
        return "fragments/task-list :: task-list";
    }

    @PostMapping("/tasks")
    public String addTask(@RequestParam String title, Model model) {
        tasks.add(new Task(title));
        model.addAttribute("tasks", tasks);
        model.addAttribute("stats", computeStats());
        return "fragments/task-list :: task-list";
    }

    @DeleteMapping("/tasks/{id}")
    public String deleteTask(@PathVariable String id, Model model) {
        tasks.removeIf(t -> t.id().equals(id));
        model.addAttribute("tasks", tasks);
        model.addAttribute("stats", computeStats());
        return "fragments/task-list :: task-list";
    }

    @PostMapping("/tasks/{id}/toggle")
    public String toggleTask(@PathVariable String id, Model model) {
        tasks.stream()
            .filter(t -> t.id().equals(id))
            .findFirst()
            .ifPresent(t -> tasks.set(tasks.indexOf(t), t.toggle()));
        model.addAttribute("tasks", tasks);
        model.addAttribute("stats", computeStats());
        return "fragments/task-list :: task-list";
    }

    private Stats computeStats() {
        var total = tasks.size();
        var done = (int) tasks.stream().filter(Task::done).count();
        return new Stats(total, done, total - done);
    }

    public record Task(String id, String title, boolean done) {
        public Task(String title) {
            this(UUID.randomUUID().toString(), title, false);
        }
        public Task toggle() {
            return new Task(id, title, !done);
        }
    }

    public record Stats(int total, int done, int pending) {}
}
