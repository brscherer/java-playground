package org.example;

import java.util.concurrent.*;
import java.util.List;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(4);

        List<Future<String>> futures = new ArrayList<>();
        String[] dishes = {"Pizza", "Burger", "Salad", "Pasta"};

        for (String dish : dishes) {
            Callable<String> task = new DishPreparationTask(dish);
            Future<String> future = executor.submit(task);
            futures.add(future);
        }

        for (Future<String> future : futures) {
            try {
                System.out.println(future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
    }
}

class DishPreparationTask implements Callable<String> {
    private final String dish;

    public DishPreparationTask(String dish) {
        this.dish = dish;
    }

    @Override
    public String call() throws Exception {
        try {
            int prepTime = ThreadLocalRandom.current().nextInt(1, 5);
            TimeUnit.SECONDS.sleep(prepTime);
            return dish + " ready in " + prepTime + " seconds!";
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return dish + " preparation interrupted.";
        }
    }
}