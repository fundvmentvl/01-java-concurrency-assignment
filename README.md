# Concurrent and Asynchronous Programming
## Overview
This project demonstrates fundamental concepts of concurrent and asynchronous programming in Java. The application uses a shared bank account to demonstrate concurrent access, a race condition, synchronization for safe access to a shared resource, and asynchronous execution using `CompletableFuture`.
The project begins with an unsafe concurrent implementation, applies synchronization to correct the shared-resource problem, and then adds asynchronous processing.
## Project Structure
```text
src/
└── main/
   └── java/
       └── concurrency/
           ├── Main.java
           ├── BankAccount.java
           └── AsyncService.java
pom.xml
.gitignore
README.md
```
## Main.java
`Main.java` is the entry point for the application.
The program creates a shared `BankAccount` with a starting balance of $100. Two customer threads each attempt to withdraw $80 from the same account.
The class is also used to demonstrate asynchronous execution by starting a transaction-summary operation while the main thread continues performing other work.
## BankAccount.java
`BankAccount.java` represents the shared resource accessed by multiple threads.
The initial implementation used an unsynchronized `withdraw()` method. Because multiple threads could access and modify the balance at the same time, the application could experience a race condition.
The corrected implementation synchronizes the withdrawal method:
```java
public synchronized void withdraw(int amount)
```
The `synchronized` keyword ensures that only one thread can execute the withdrawal operation on the shared `BankAccount` object at a time.
## Race Condition Demonstration
In the initial unsafe implementation, two customer threads can access the same account balance concurrently.
The account begins with a balance of $100, and both customers attempt to withdraw $80.
Because the balance check and withdrawal operation are not protected as one atomic operation, both threads may read the $100 balance before either thread updates it.
An example of the unsafe result is:
```text
UNSAFE VERSION - RACE CONDITION
Customer-1 is attempting to withdraw $80. Current balance: $100
Customer-2 is attempting to withdraw $80. Current balance: $100
Customer-1 completed withdrawal. New balance: $20
Customer-2 completed withdrawal. New balance: $-60
Final balance: $-60
```
The negative balance demonstrates the shared-resource problem caused by the race condition.
## Synchronized Solution
The race condition is corrected by synchronizing the `withdraw()` method.
Synchronization allows one customer thread to complete the balance check and withdrawal before another customer thread can enter the same method.
An example of the corrected execution is:
```text
Customer-1 is attempting to withdraw $80. Current balance: $100
Customer-1 completed withdrawal. New balance: $20
Customer-2 is attempting to withdraw $80. Current balance: $20
Customer-2 could not withdraw $80. Insufficient funds.
Final balance: $20
```
The second customer now sees the updated $20 balance and is prevented from withdrawing another $80.
This demonstrates controlled access to the shared resource and prevents the invalid negative balance produced by the unsafe implementation.
## Asynchronous Execution
The project also demonstrates asynchronous programming using Java's `CompletableFuture`.
`AsyncService.java` performs a transaction-summary operation asynchronously. While the asynchronous operation is running, the main thread continues performing other tasks.
The asynchronous operation is started using `CompletableFuture.supplyAsync()`.
Example behavior:
```text
ASYNCHRONOUS EXECUTION
Main thread continues working while summary runs.
Main thread task 1
Main thread task 2
Main thread task 3
[ASYNC] Transaction summary started on thread: ForkJoinPool.commonPool-worker-1
[ASYNC] Transaction summary finished.
[ASYNC RESULT] Transaction processing complete. Final account balance: $20
```
This demonstrates that the main application does not have to stop executing while the transaction summary is being processed.
## Concurrency vs. Asynchronous Programming
The project demonstrates two related concepts:
**Concurrency** is demonstrated by the two customer threads attempting to access the same `BankAccount`.
**Synchronization** protects the shared account so that only one thread can perform the critical withdrawal operation at a time.
**Asynchronous execution** is demonstrated using `CompletableFuture`, allowing the transaction summary to execute independently while the main thread continues working.
## Technologies Used
- Java
- Maven
- IntelliJ IDEA
- Java Threads
- `synchronized`
- `CompletableFuture`
- Git
- GitHub
## Running the Application
1. Clone or download the repository.
2. Open the project in IntelliJ IDEA.
3. Allow Maven to load the project.
4. Navigate to:
```text
src/main/java/concurrency/Main.java
```
5. Run the `Main` class.
6. Review the console output to observe the concurrent and asynchronous behavior.
## Version Control and Code Comparison
Git and GitHub were used to document the incremental development of the application.
The project history includes separate changes for:
1. The initial unsafe concurrent implementation.
2. Synchronization of the withdrawal operation.
3. Addition of asynchronous transaction-summary processing.
   The Git history and code diff provide evidence of the changes made between the unsafe and synchronized implementations.
   The key synchronization change is:
```diff
- public void withdraw(int amount)
+ public synchronized void withdraw(int amount)
```
This change protects the critical section that checks and modifies the shared account balance.
## Conclusion
This project demonstrates the problems that can occur when multiple threads access shared mutable data without proper synchronization.
The initial implementation demonstrates a race condition that can result in an invalid account balance. Synchronizing the withdrawal operation prevents multiple threads from modifying the shared resource simultaneously and produces the correct account balance.
The project also demonstrates asynchronous execution with `CompletableFuture`, allowing background work to execute while the application's main thread continues processing other tasks.