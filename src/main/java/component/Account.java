package main.java.component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import main.java.iterator.CustomIterator;
import main.java.iterator.WithdrawalIterator;
import main.java.observer.AccountObserver;

/**
 * A Composite object. It can hold a collection of other FinancialComponents
 * (Transactions).
 */
public class Account implements FinancialComponent {
    private String accountName;
    private List<FinancialComponent> transactions;
    private List<AccountObserver> observers = new ArrayList<>();

    public Account(String accountName) {
        this.accountName = accountName;
        this.transactions = new ArrayList<>();
    }

    public CustomIterator<Transaction> createWithdrawalIterator() {
        return new WithdrawalIterator(this.transactions);
    }

    public void addObserver(AccountObserver observer) {
        this.observers.add(observer);
    }

    private void notifyObservers() {
        double currentBalance = getBalance();
        for (AccountObserver observer : observers) {
            observer.update(accountName, currentBalance);
        }
    }

    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
        notifyObservers();
    }

    @Override
    public void displayDetails() {
        System.out.println("\n-- Account: " + accountName + " --");
        for (FinancialComponent component : transactions) {
            component.displayDetails();
        }
        System.out.printf("  Total Balance: $%.2f", getBalance());
    }

    @Override
    public double getBalance() {
        return transactions.stream()
                .mapToDouble(FinancialComponent::getBalance)
                .sum();
    }

    public List<FinancialComponent> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }

    @Override
    public String toCSV() {
        return "ACCOUNT," + accountName + ",0";
    }
}