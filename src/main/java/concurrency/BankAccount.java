package concurrency;
public class BankAccount {
    private int balance;
    public BankAccount(int startingBalance) {
        this.balance = startingBalance;
    }
    public void withdraw(int amount) {
        String threadName = Thread.currentThread().getName();
        System.out.println(
                threadName + " is attempting to withdraw $"
                        + amount + ". Current balance: $" + balance
        );
        if (balance >= amount) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            balance = balance - amount;
            System.out.println(
                    threadName + " completed withdrawal. New balance: $" + balance
            );
        } else {
            System.out.println(
                    threadName + " could not withdraw $"
                            + amount + ". Insufficient funds."
            );
        }
    }
    public int getBalance() {
        return balance;
    }
}