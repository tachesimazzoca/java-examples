package com.github.tachesimazzoca.java.examples.javase;

public class DeadlockExample {
    public static class Account {
        private int balance;

        public Account(int balance) {
            this.balance = balance;
        }

        public int getBalance() {
            return balance;
        }

        public synchronized void deposit(int amount) {
            balance += amount;
        }

        public synchronized void transfer(Account receiver, int amount) {
            if (balance < amount)
                return;
            balance -= amount;
            receiver.deposit(amount);
            System.out.format("sender:%d, receiver:%d%n", balance, receiver.getBalance());
        }
    }

    public static void main(String args[]) {
        final int MAX = 10000;
        final Account a = new Account(MAX);
        final Account b = new Account(MAX);

        System.out.println("Both threads will block when they attempt to invoke Account#deposit.");
        System.out.println("To analyze the threads, issue the command `jstack -l <PID>`.");

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < MAX; i++) {
                    a.transfer(b, 1);
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < MAX; i++) {
                    b.transfer(a, 1);
                }
            }
        }).start();
    }
}
