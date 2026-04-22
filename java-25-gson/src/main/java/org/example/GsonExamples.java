package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

    static void advancedExamples() {
        var prettyGson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

        Company company = new Company("TechCorp", List.of(
            new User("Grace", 29, "grace@techcorp.com"),
            new User("Henry", 31, "henry@techcorp.com")
        ));
        
        String prettyJson = prettyGson.toJson(company);
        System.out.println("Pretty printed:\n" + prettyJson);

        Gson gson = new Gson();
        String companyJson = "{\"name\":\"DevInc\",\"employees\":[{\"name\":\"Ivy\",\"age\":27,\"email\":\"ivy@devinc.com\"}]}";
        Company parsed = gson.fromJson(companyJson, Company.class);
        System.out.println("Nested object: " + parsed);
    }
}
