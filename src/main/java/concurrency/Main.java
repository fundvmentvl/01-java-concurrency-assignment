package concurrency;

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
    }
}