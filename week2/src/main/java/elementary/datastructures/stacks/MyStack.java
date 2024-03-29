package elementary.datastructures.stacks;

import elementary.datastructures.MyCollection;

public interface MyStack<T> extends MyCollection<T> {

    /**
     * Pushes the item to top of stack.
     * Operation is O(1).
     *
     * @param item to be added to stack
     */
    void push(T item);

    /**
     * Retrieves and removes the first item from top of stack.
     * Operation is O(1).
     *
     * @return first item from stack
     */
    T pop();

    /**
     * Retrieves but doesn't remove the first item from top of stack.
     * Operation is O(1).
     *
     * @return first item from stack
     */
    T peek();
}
