package sort.heapsort;

import static util.SortUtility.exch;

/**
 * This implementation of heapsort uses a 0-based priority queue, so as to prevent expensive conversions of array into
 * 1-based pre-sorting and back into 0-based post-sorting (essentially, calls to System.arraycopy())
 *
 * The algorithm works in two phases:
 * 1) Heap construction - we reorganise the input array into a heap, starting half-way (skipping one-sized heaps)
 * through array and creating binary heap top-down (time complexity - 0(1/2 NlgN)
 * 2) Sortdown - we exchange the first element in the array (i.e. heap max) and the last unsorted element and then
 * simply sink down the unsorted element back into its place that preserves the binary heap invariant
 * (time-complexity - O(NlgN)).
 * Total time complexity is thus O(3/2 NlgN).
 *
 * It is very compact and performs equally well as the fastest quicksort implementation here - however is not generally
 * used as it suffers from poor cache performance (neighbouring array entries are rarely compared so it suffers more
 * cache misses than QS)
 *
 */
public final class MyHeapsort {

    public static <T extends Comparable<? super T>> void sort(T[] a) {
        int N = a.length;
        int mid = N / 2; // anything to the right of this are just one-node heaps we needn't sort in Phase 1
        for (int i = mid; i >= 0; i--) // phase 1 - construct heap
            sink(a, i, N - 1);
        sort(a, N - 1); // phase 2 - sortdown
    }

    // N is sink limit - the array index of the first element that maintains the sort invariant
    // this value will start at the array end and progressively shrink until the array is fully sorted
    private static <T extends Comparable<? super T>> void sort(T[] xs, int N) {
        while (N != 0) {
            exch(xs, N--, 0);
            sink(xs, 0, N);
        }
    }

    /**
     * Restores heap order by sinking the node to its correct place top-down.
     */
    private static <T extends Comparable<? super T>> void sink(T[] xs, int k, int limit) {
        int left = 2 * k;
        int right = left + 1;
        if (left > limit) {
            if (right > limit) return; // is leaf, nothing to do but return
            if (less(xs, k, right)) { // no left, k < right => sink k
                exch(xs, k, right);
                sink(xs, left, limit);
            }
        } else if (right > limit) {
            if (less(xs, k, left)) { // no right, k < left => sink k
                exch(xs, k, left);
                sink(xs, right, limit);
            }
        } else { // both nodes are here, compare them, take larger and exchange with k-th node if k < larger
            int larger = less(xs, left, right) ? right : left;
            if (less(xs, k, larger)) {
                exch(xs, k, larger);
                sink(xs, larger, limit);
            }
        }
    }

    private static <T extends Comparable<? super T>> boolean less(T[] a, int i, int j) {
        return a[i].compareTo(a[j]) < 0;
    }
}