import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

// Throw a java.lang.NullPointerException if the client attempts to add a null item;
// throw a java.util.NoSuchElementException if the client attempts to sample or dequeue an item from an empty queue;
// throw a java.lang.UnsupportedOperationException if the client calls the remove() method in the iterator;
// throw a java.util.NoSuchElementException if the client calls the next() method in the iterator and there are no more items to return.
// backed by resizable array
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] array = (Item[]) new Object[1];
    private int N = 0; // number of elements in array

    // construct an empty randomized queue
    public RandomizedQueue() {

    }

    // is the queue empty?
    public boolean isEmpty() {
        return N == 0;
    }

    // return the number of items on the queue
    public int size() {
        return N;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new NullPointerException();
        if (N == array.length)
            resize(2 * array.length);
        array[N++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (N == 0) throw new NoSuchElementException();
        Item item = randomItem();
        if (N > 0 && N == array.length / 4)
            resize(array.length / 2);
        return item;
    }

    // return (but do not remove) a random item
    public Item sample() {
        if (N == 0) throw new NoSuchElementException();
        int randomIndex = randomIndex();
        return array[randomIndex];
    }

    // return an independent iterator over items in random order
    @Override
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            private int itemCount = N;
            private Item[] iteratorArray = shuffledArray();

            @Override
            public boolean hasNext() {
                return itemCount != 0;
            }

            @Override
            public Item next() {
                if (itemCount <= 0) throw new NoSuchElementException();
                Item item = iteratorArray[itemCount - 1];
                itemCount--;
                return item;
            }

            private Item[] shuffledArray() { // see if we can rely on sample() instead of shuffling the array here
                Item[] result = (Item[]) new Object[itemCount];
                for (int i = 0; i < itemCount; i++) {
                    result[i] = array[i];
                }
                StdRandom.shuffle(result);
                return result;
            }
        };
    }

    /**
     * This was the linchpin of the solution - how to fetch a uniformly random values from array in constant-time,
     * but without fragmenting the array with null values.
     *
     * The key insight here is to exploit the allowed randomness in array order and plug the whole with a value from
     * the last element in the array.
     *
     * Beside *knowing* a random fetch from [0, N> won't retrieve a null value, this also cuts the shrink resize times
     * considerably, as we don't have to traverse the whole previous array and check if the value in the array is null
     * but we can simply copy the array values from [0, N> and discard the rest (they certainly contain only null values).
     */
    private Item randomItem() {
        int last = N - 1;
        int randomIndex = randomIndex();
        Item result = array[randomIndex];
        array[randomIndex] = array[last];
        array[last] = null;
        N--;
        return result;
    }

    private int randomIndex() {
        return N != 0 ? StdRandom.uniform(N) : 0;
    }

    private void resize(int newSize) {
        Item[] newArray = (Item[]) new Object[newSize];
        for (int i = 0; i < N; i++)
            newArray[i] = array[i];
        array = newArray;
    }
}