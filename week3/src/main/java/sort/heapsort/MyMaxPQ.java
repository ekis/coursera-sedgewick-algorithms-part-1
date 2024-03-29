package sort.heapsort;

import java.util.function.BiConsumer;
import java.util.stream.Collector;
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
public final class MyMaxPQ<T extends Comparable<? super T>> {

    private T[] pq;
    private int N = 1;

    private MyMaxPQ(){
        pq = newArray();
    }

    public static <T extends Comparable<? super T>> MyMaxPQ<T> create() {
        return new MyMaxPQ<>();
    }

    @SafeVarargs
    public final void insert(T... keys) {
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

    public int size() {
        return N - 1;
    }

    public static <T extends Comparable<? super T>> Collector<T, MyMaxPQ<T>, MyMaxPQ<T>> collectorOf(BiConsumer<MyMaxPQ<T>, T> accumulator) {
        return Collector.of(MyMaxPQ::create,
                accumulator,
                (left, right) -> {
                    left.insert(right.pq);
                    return left;
                });
    }

    /**
     * Restores heap order by swimming up the node to its correct place bottom-up.
     */
    private void swim(int k) {
        int parent = k / 2;
        if (parent >= 1 && less(parent, k)) { // is parent < k
            exch(parent, k);
            swim(parent);
        }
    }

    /**
     * Restores heap order by sinking the node to its correct place top-down.
     */
    private void sink(int k) {
        int left = 2 * k;
        int right = left + 1;
        if (left > size()) {
            if (right > size()) return; // is leaf, nothing to do but return
            if (less(k, right)) { // no left, k < right => sink k
                exch(k, right);
                sink(right);
            }
        } else if (right > size()) {
            if (less(k, left)) { // no right, k < left => sink k
                exch(k, left);
                sink(left);
            }
        } else { // both nodes are here, compare them, take larger and exchange with k-th node if k < larger
            int larger = less(left, right) ? right : left;
            if (less(k, larger)) {
                exch(k, larger);
                sink(larger);
            }
        }
        // stop sinking, array is heap-ordered
    }

    private boolean less(int i, int j) {
        return pq[i].compareTo(pq[j]) < 0;
    }

    private void exch(int i, int j) {
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
