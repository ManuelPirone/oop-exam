package main.java.component;

/**
 * The Component interface in the Composite Pattern.
 * It provides the common contract for both individual items (Leafs)
 * and containers (Composites).
 */
public interface FinancialComponent {
    void displayDetails();
    double getBalance();
    String toCSV();
}
