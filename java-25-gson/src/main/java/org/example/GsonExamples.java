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
}

