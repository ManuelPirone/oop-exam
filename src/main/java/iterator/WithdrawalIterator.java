package main.java.iterator;

import main.java.component.Transaction;
import main.java.component.FinancialComponent;
import java.util.List;

/**
 * Concrete implementation of CustomIterator that filters transactions 
 * to return only withdrawals.
 */
public class WithdrawalIterator implements CustomIterator<Transaction> {
    
    private final List<FinancialComponent> transactions;
    private int position = 0;

    public WithdrawalIterator(List<FinancialComponent> transactions) {
        this.transactions = transactions;
    }

    @Override
    public boolean hasNext() {
        for (int i = position; i < transactions.size(); i++) {
            FinancialComponent component = transactions.get(i);
            // Check if it's a Transaction and if its amount is negative (a withdrawal)
            if (component instanceof Transaction) {
                Transaction t = (Transaction) component;
                if (t.getAmount() < 0) {
                    return true; // Found a withdrawal
                }
            }
        }
        return false;
    }

    @Override
    public Transaction next() {
        // Advance the position until a withdrawal is found
        while (position < transactions.size()) {
            FinancialComponent component = transactions.get(position++);
            if (component instanceof Transaction) {
                Transaction t = (Transaction) component;
                if (t.getAmount() < 0) {
                    return t;
                }
            }
        }
        return null; 
    }
}