package org.example;

import com.google.gson.Gson;
import java.util.List;

public class GsonExamples {
    static void serializationExamples() {
        Gson gson = new Gson();

        User user = new User("Alice", 30, "alice@example.com");
        String json = gson.toJson(user);
        System.out.println("Single object: " + json);

        List<User> users = List.of(
            new User("Bob", 25, "bob@example.com"),
            new User("Charlie", 35, "charlie@example.com")
        );
        String jsonList = gson.toJson(users);
        System.out.println("List: " + jsonList);
    }

    static void deserializationExamples() {
        Gson gson = new Gson();

        String userJson = "{\"name\":\"Diana\",\"age\":28,\"email\":\"diana@example.com\"}";
        User user = gson.fromJson(userJson, User.class);
        System.out.println("Parsed user: " + user);

        String usersJson = "[{\"name\":\"Eve\",\"age\":26,\"email\":\"eve@example.com\"},{\"name\":\"Frank\",\"age\":32,\"email\":\"frank@example.com\"}]";
        User[] userArray = gson.fromJson(usersJson, User[].class);
        System.out.println("Parsed array: " + List.of(userArray));
    }
}
