import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

// Throw a java.lang.NullPointerException if the client attempts to add a null item;
// throw a java.util.NoSuchElementException if the client attempts to sample or dequeue an item from an empty randomized queue;
// throw a java.lang.UnsupportedOperationException if the client calls the remove() method in the iterator;
// throw a java.util.NoSuchElementException if the client calls the next() method in the iterator and there are no more items to return.
// backed by resizable array
public class RandomizedQueue<Item> implements Iterable<Item> {

    @SuppressWarnings("unchecked")
    private Item[] array = (Item[]) new Object[1];
    private int N = 0; // number of elements in array

    // construct an empty randomized queue
    public RandomizedQueue() {}

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
            expand();
        array[N++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (N == 0) throw new NoSuchElementException();
        Item item = removeRandomItem();
        if (N > 0 && N == array.length / 4)
            shrink();
        return item;
    }

    // return (but do not remove) a random item
    public Item sample() {
        if (N == 0) throw new NoSuchElementException();
        return sampleRandomItem();
    }

    // return an independent iterator over items in random order
    @Override
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            private Item[] iteratorArray = shuffledArray();
            private int i = N;

            @Override
            public boolean hasNext() {
                return i != 0;
            }

            @Override
            public Item next() {
                if (i == 0) throw new NoSuchElementException();
                Item item = iteratorArray[i];
                i--;
                return item;
            }

            @SuppressWarnings("unchecked")
            private Item[] shuffledArray() {
                int j = 0;
                Item[] result = (Item[]) new Object[N];
                for (int i = 0; i < array.length; i++) {
                    Item candidate = array[i];
                    if (candidate != null)
                        result[j++] = candidate;
                }
                StdRandom.shuffle(result);
                return result;
            }
        };
    }

    @SuppressWarnings("unchecked")
    private void expand() {
        Item[] newArray = (Item[]) new Object[2 * array.length];
        //System.out.println("Expanding array to " + 2 * array.length);
        for (int i = 0; i < N; i++)
            newArray[i] = array[i];
        array = newArray;
    }

    @SuppressWarnings("unchecked")
    private void shrink() {
        int j = array.length / 2;
        Item[] newArray = (Item[]) new Object[j];
        //System.out.println("Shrinking array to " + j);
        for (int i = 0; i < array.length; i++) {
            Item candidate = array[i];
            if (candidate != null)
                newArray[--j] = candidate;
        }
        array = newArray;
    }

    private Item removeRandomItem() {
        return randomItem(true);
    }

    private Item sampleRandomItem() {
        return randomItem(false);
    }

    private Item randomItem(boolean remove) {
        int randomIndex = rndIndex();
        Item candidate = array[randomIndex];
        while (candidate == null) {
            randomIndex = rndIndex();
            candidate = array[randomIndex];
        }
        if (remove) {
            array[randomIndex] = null;
            N--;
        }
        return candidate;
    }

    private int rndIndex() {
        return StdRandom.uniform(array.length);
    }
}