package jobinterview.sort.heapsort;

import java.util.Arrays;
import java.util.function.BiConsumer;

import static jobinterview.SortUtility.*;

/**
 * Implementation of heapsort works in two phases:
 * 1) Heap construction - we reorganise the input array into a heap (NlgN)
 * 2) Sortdown - we pull the items off of the heap in decreasing order
 */
public final class MyHeapsort {

    public static <T extends Comparable<? super T>> void sort(T[] a) {
        //T[] copy = copyArray(a, (input, sorted) -> System.arraycopy(input, 0, sorted, 1, input.length));
        T[] copy = copyArray(a);
        int M = copy.length;
        int mid = M / 2; // anything to the right of this are just one-node heaps we needn't sort in Phase 1
        for (int i = mid; i > 0; i--) { // phase 1 - construct heap
            sink(copy, i, M - 1);
        }
        sort(copy, M - 1);
        System.arraycopy(copy, 1, a, 0, a.length);
    }

    private static <T extends Comparable<? super T>> T[] copyArray(T[] a) {
        int N = a.length;
        @SuppressWarnings("unchecked") // no better way to create a generic array
        T[] sorted = (T[]) new Comparable[N + 1]; // 1-based array
        System.arraycopy(a, 0, sorted, 1, a.length);
        return sorted;
    }

    /**
     * Restores heap order by swimming up the node to its correct place bottom-up.
     */
    private static <T extends Comparable<? super T>> void swim(T[] xs, int k) {
        int parent = k / 2;
        if (parent >= 1 && less(xs, parent, k)) { // is parent < k
            exch(xs, parent, k);
            swim(xs, parent);
        }
    }

    /**
     * Restores heap order by sinking the node to its correct place top-down.
     */
    private static <T extends Comparable<? super T>> void sink(T[] xs, int k, int size) {
        int left = 2 * k;
        int right = left + 1;
        if (left > size) {
            if (right > size) return; // is leaf, nothing to do but return
            if (less(xs, k, right)) { // no left, k < right => sink k
                exch(xs, k, right);
                sink(xs, right, size);
            }
        } else if (right > size) {
            if (less(xs, k, left)) { // no right, k < left => sink k
                exch(xs, k, left);
                sink(xs, left, size);
            }
        } else { // both nodes are here, compare them, take larger and exchange with k-th node if k < larger
            int larger = less(xs, left, right) ? right : left;
            if (less(xs, k, larger)) {
                exch(xs, k, larger);
                sink(xs, larger, size);
            }
        }
        // stop sinking, array is heap-ordered
    }

    // N represents sink limit - the array index of the first element that maintains the sort invariant
    // this value will start at the array end and progressively shrink until the array is fully sorted
    private static <T extends Comparable<? super T>> void sort(T[] xs, int N) {
        if (N == 1) return;
        exch(xs, N--, 1);
        sink(xs, 1, N);
        //System.out.println("Sort stage -> " + Arrays.toString(xs));
        sort(xs, N);
    }

    private static <T extends Comparable<? super T>> boolean less(T[] a, int i, int j) {
        return a[i].compareTo(a[j]) < 0;
    }
}
