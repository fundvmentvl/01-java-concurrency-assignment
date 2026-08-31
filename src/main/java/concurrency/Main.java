package concurrency;

import java.util.concurrent.CompletableFuture;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("UNSAFE VERSION - RACE CONDITION");
        System.out.println();
        BankAccount account = new BankAccount(100);
        Thread customer1 = new Thread(
                () -> account.withdraw(80),
                "Customer-1"
        );
        Thread customer2 = new Thread(
                () -> account.withdraw(80),
                "Customer-2"
        );
        customer1.start();
        customer2.start();
        customer1.join();
        customer2.join();
        System.out.println();
        System.out.println(
                "Final balance: $" + account.getBalance()
        );

        System.out.println();
        System.out.println("ASYNCHRONOUS EXECUTION");
        CompletableFuture<String> summaryFuture =
                AsyncService.generateSummaryAsync(account.getBalance());
        System.out.println(
                "Main thread continues working while summary runs."
        );
        for (int i = 1; i <= 3; i++) {
            System.out.println("Main thread task " + i);
            Thread.sleep(400);
        }
        String summary = summaryFuture.join();
        System.out.println("ASYNC RESULT: " + summary);
    }
}