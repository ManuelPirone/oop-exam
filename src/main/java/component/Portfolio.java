package main.java.component;

import java.util.ArrayList;
import java.util.List;

/**
 * The top-level Composite object. It holds multiple Account components.
 */
public class Portfolio implements FinancialComponent {
    private String portfolioName;
    private List<FinancialComponent> accounts;

    public Portfolio(String portfolioName) {
        this.portfolioName = portfolioName;
        this.accounts = new ArrayList<>();
    }

    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    @Override
    public void displayDetails() {
        System.out.println("-- Portfolio: " + portfolioName + " --");
        for (FinancialComponent component : accounts) {
            component.displayDetails();
        }
        System.out.printf("\nGRAND TOTAL PORTFOLIO BALANCE: $%.2f\n", getBalance());
    }

    @Override
    public double getBalance() {
        double total = 0;
        for (FinancialComponent component : accounts) {
            total += component.getBalance();
        }
        return total;
    }

    @Override
    public String toCSV() {
        return "ROOT_PORTFOLIO," + portfolioName;
    }
}