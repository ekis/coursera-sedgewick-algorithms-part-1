import java.util.Iterator;

// Throw a java.lang.NullPointerException if the client attempts to add a null item;
// throw a java.util.NoSuchElementException if the client attempts to sample or dequeue an item from an empty randomized queue;
// throw a java.lang.UnsupportedOperationException if the client calls the remove() method in the iterator;
// throw a java.util.NoSuchElementException if the client calls the next() method in the iterator and there are no more items to return.
// backed by resizable array
public class RandomizedQueue<T> implements Iterable<T> {

    // construct an empty randomized queue
    public RandomizedQueue() {

    }

    // is the queue empty?
    public boolean isEmpty() {
        throw new UnsupportedOperationException();
    }

    // return the number of items on the queue
    public int size() {
        throw new UnsupportedOperationException();
    }

    // add the item
    public void enqueue(T item) {
        throw new UnsupportedOperationException();
    }

    // remove and return a random item
    public T dequeue() {
        throw new UnsupportedOperationException();
    }

    // return (but do not remove) a random item
    public T sample() {
        throw new UnsupportedOperationException();
    }

    // return an independent iterator over items in random order
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException();
    }

    // unit testing
    public static void main(String[] args) {
        throw new UnsupportedOperationException();
    }
}