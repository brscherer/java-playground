package org.example;

import java.util.concurrent.*;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) {
        executorServiceExample();

        locksExample();

        atomicVariablesExample();
    }

    private static void executorServiceExample() {
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
                Thread.currentThread().interrupt();
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

    private static void locksExample() {
        BankAccount account = new BankAccount(1000);
        ExecutorService executor = Executors.newFixedThreadPool(3);

        for (int i = 0; i < 5; i++) {
            executor.submit(() -> {
                account.withdraw(100);
                System.out.println(Thread.currentThread().getName() + " withdrew 100");
            });
            executor.submit(() -> {
                account.deposit(50);
                System.out.println(Thread.currentThread().getName() + " deposited 50");
            });
        }

        executor.shutdown();
        try {
            executor.awaitTermination(10, TimeUnit.SECONDS);
            System.out.println("Final balance: $" + account.getBalance());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void atomicVariablesExample() {
        PageViewCounter counter = new PageViewCounter();
        ExecutorService executor = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 10; i++) {
            executor.submit(() -> {
                counter.recordPageView();
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(100));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        executor.shutdown();
        try {
            executor.awaitTermination(10, TimeUnit.SECONDS);
            System.out.println("Total page views: " + counter.getViews());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class DishPreparationTask implements Callable<String> {
    private final String dish;

    public DishPreparationTask(String dish) {
        this.dish = dish;
    }

    @Override
    public String call() {
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

class BankAccount {
    private int balance;
    private final ReentrantLock lock = new ReentrantLock();

    public BankAccount(int initialBalance) {
        this.balance = initialBalance;
    }

    public void deposit(int amount) {
        lock.lock();
        try {
            balance += amount;
        } finally {
            lock.unlock();
        }
    }

    public void withdraw(int amount) {
        lock.lock();
        try {
            if (balance >= amount) {
                balance -= amount;
            }
        } finally {
            lock.unlock();
        }
    }

    public int getBalance() {
        lock.lock();
        try {
            return balance;
        } finally {
            lock.unlock();
        }
    }
}

class PageViewCounter {
    private final AtomicInteger views = new AtomicInteger(0);

    public void recordPageView() {
        views.incrementAndGet();
    }

    public int getViews() {
        return views.get();
    }
}
