package exercise.stacks;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by ekis on 12/12/16.
 */
class SimpleStack<T> implements MyStack<T> {

    private Node<T> first;
    private int count = 0;

    private static class Node<Item> {
        private final Item item;
        private final Node<Item> next;

        private Node(Item nestedItem, Node<Item> nextNode) {
            item = nestedItem;
            next = nextNode;
        }

        private static <V> Node<V> firstNodeOf(V item) {
            return new Node<>(item, null);
        }

        private static <V> Node<V> nextNodeOf(V item, Node<V> nextNode) {
            return new Node<>(item, nextNode);
        }
    }

    @Override
    public void push(T item) {
        Node<T> oldFirst = first;

        if (isEmpty())
            first = Node.firstNodeOf(item);
        else
            first = Node.nextNodeOf(item, oldFirst);
        count++;
    }

    @Override
    public T pop() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        T result = first.item;
        first = first.next;
        count--;
        return result;
    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public Iterator<T> iterator() { // LIFO iteration
        return new Iterator<T>() {
            private Node<T> current = first;
            private int N = count;

            @Override
            public boolean hasNext() {
                return N != 0;
            }

            @Override
            public T next() {
                if (!hasNext()) throw new IllegalStateException();
                T result = current.item;
                current = current.next;
                N--;
                return result;
            }
        };
    }
}