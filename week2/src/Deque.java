import java.util.Iterator;
import java.util.NoSuchElementException;

// Throw a java.lang.NullPointerException if the client attempts to add a null item;
// throw a java.util.NoSuchElementException if the client attempts to remove an item from an empty deque;
// throw a java.lang.UnsupportedOperationException if the client calls the remove() method in the iterator;
// throw a java.util.NoSuchElementException if the client calls the next() method in the iterator and there are no more items to return.
// backed by doubly-linked list
public class Deque<T> implements Iterable<T> {

    private int size = 0;

    private Node first;
    private Node last;

    // construct an empty deque
    public Deque() {}

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(T item) {
        if (item == null) throw new NullPointerException();

        if (isEmpty()) {
            first = new Node(item);
            last = first;
        } else {
            Node oldFirst = first;
            first = new Node(item);
            first.next = oldFirst;
            oldFirst.prev = first;
        }
        size++;
    }

    // add the item to the end
    public void addLast(T item) {
        if (item == null) throw new NullPointerException();

        if (isEmpty()) {
            last = new Node(item);
            first = last;
        } else {
            Node oldLast = last;
            last = new Node(item);
            last.prev = oldLast;
            oldLast.next = last;
        }
        size++;
    }

    // remove and return the item from the front
    public T removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();

        T result = first.item;
        first = first.next;

        if (size > 1)
            first.prev = null;
        else
            last = null;

        size--;

        return result;
    }

    // remove and return the item from the end
    public T removeLast() {
        if (isEmpty()) throw new NoSuchElementException();

        T result = last.item;
        last = last.prev;

        if (size > 1)
            last.next = null;
        else
            first = null;

        size--;

        return result;
    }

    // return an iterator over items in order from front to end
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                return size != 0;
            }

            @Override
            public T next() {
                return removeFirst();
            }
        };
    }

    private class Node {
        private final T item;
        private Node prev;
        private Node next;

        private Node(T t) {
            item = t;
        }
    }
}