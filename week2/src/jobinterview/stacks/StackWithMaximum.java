package jobinterview.stacks;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A stack implementation which tracks the maximum element currently on stack.
 */
public class StackWithMaximum<T> implements MyStack<T> {
    private final MyStack<T> stack = Stacks.simpleStack();
    private final MyStack<T> maximums = Stacks.simpleStack();
    private final Comparator<T> comparator;

    StackWithMaximum(Comparator<T> c) {
        comparator = c;
    }

    @Override
    public void push(T item) {
        if (!isEmpty()) {
            if (isGreaterThanMax(item, maximums.peek()))
                maximums.push(item);
        } else {
            maximums.push(item);
        }
        stack.push(item);
    }

    @Override
    public T pop() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        if (isEqualToMax(maximums.peek(), stack.peek()))
            maximums.pop();
        return stack.pop();
    }

    @Override
    public T peek() {
        return stack.peek();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public int size() {
        return stack.size();
    }

    @Override
    public Iterator<T> iterator() {
        return stack.iterator();
    }

    public T max() {
        return maximums.peek();
    }

    private boolean isGreaterThanMax(T peekedItem, T item) {
        return comparator.compare(peekedItem, item) == 1;
    }

    private boolean isEqualToMax(T peekedItem, T item) {
        return comparator.compare(peekedItem, item) == 0;
    }
}