package jobinterview.sort.heapsort;

import java.util.stream.Stream;

/**
 * Implementation of priority queue backed by a binary heap. A binary heap is a collection of keys arranged in a
 * complete heap-ordered binary tree, represented in level order in an array (not using the first entry - to simplify
 * index arithmetics). In a heap, the the parent of the node at position k is in position floor(k/2) and, conversely,
 * the two children of the node at position k are in positions 2k and 2k+1. There are no explicit links between nodes.
 *
 * A binary tree is heap-ordered if the key in each node is <= to the keys in that node's children (if any). The largest
 * key in a heap-ordered binary tree is found at the root.
 *
 * The heap-backed priority queue has insert/remove max key operations of time complexity O(logN).
 */
public final class MyMaxPQ<T extends Comparable<? super T>> extends AbstractPriorityQueue {

    private T[] pq;
    private int N = 1;

    private MyMaxPQ(){
        pq = newArray();
    }

    public static <T extends Comparable<? super T>> MyMaxPQ<T> create() {
        return new MyMaxPQ<>();
    }

    public void insert(T... keys) {
        Stream.of(keys).forEach(this::insert);
    }

    public void insert(T key) {
        if (size() == normalisedLength())
            resize(2 * pq.length);
        pq[N] = key;
        swim(N++);
    }

    public T max() {
        return pq[1];
    }

    public void clear() {
        pq = newArray();
        N = 1;
    }

    public T delMax() {
        T max = max();
        exch(--N, 1); // put the item from the end of the heap at the top
        pq[N] = null; // remove the max item from heap
        sink(1); // sink the item all the way to its proper place in the heap
        if (size() == normalisedLength() / 4)
            resize(pq.length / 2);
        return max;
    }

    public boolean isEmpty() {
        return N == 1;
    }

    @Override
    public int size() {
        return N - 1;
    }

    @Override
    protected boolean less(int i, int j) {
        return pq[i].compareTo(pq[j]) < 0;
    }

    @Override
    protected void exch(int i, int j) {
        if (i == j) return;
        T swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
    }

    private int normalisedLength() {
        return pq.length - 1;
    }

    private void resize(int newSize) {
        @SuppressWarnings("unchecked") // no way to prevent this cast as the arrays are reified
        T[] newPq = (T[]) new Comparable[newSize];
        System.arraycopy(pq, 0, newPq, 0, N);
        pq = newPq;
    }

    @SuppressWarnings("unchecked") // no other natural way to create generic array
    private T[] newArray() {
        return (T[]) new Comparable[2];
    }
}
