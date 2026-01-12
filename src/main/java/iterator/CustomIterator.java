package main.java.iterator;

import main.java.component.FinancialComponent;

/**
 * Interface for the Iterator design pattern.
 * Provides a standard way to traverse a collection of financial components
 * without exposing the underlying data structure (like a List or Set).
 * * @param <T> The type of component to iterate over, restricted to FinancialComponent or its subclasses.
 */
public interface CustomIterator<T extends FinancialComponent> {
    boolean hasNext();
    T next();
}