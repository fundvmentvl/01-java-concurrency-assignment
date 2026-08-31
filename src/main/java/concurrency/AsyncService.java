package concurrency;

import java.util.concurrent.CompletableFuture;

public class AsyncService {

    public static CompletableFuture<String> generateSummaryAsync(int finalBalance) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println();
            System.out.println("[ASYNC] Transaction summary started on thread: "
                    + Thread.currentThread().getName());

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            String summary = "Transaction processing complete. Final account balance: $"
                    + finalBalance;

            System.out.println("[ASYNC] Transaction summary finished.");
            return summary;
        });
    }
}
