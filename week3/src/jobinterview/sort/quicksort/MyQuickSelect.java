package jobinterview.sort.quicksort;

import edu.princeton.cs.algs4.Knuth;

import java.util.function.BiPredicate;

/**
 * Finds the kth smallest number in a list or array (i.e the order statistic).
 */
public final class MyQuickSelect extends MyQuick {

    public static <T extends Comparable<? super T>> T select(T[] a, int k) {
        if (k > a.length - 1) throw new IllegalStateException("K-th element exceeds array size.");
        Knuth.shuffle(a);
        return find(lessWithComparable(), a, 0, a.length - 1, k);
    }

    private static <T> T find(BiPredicate<T, T> lessF, T[] a, int lo, int hi, int k) {
        int mid = partition(lessF, a, lo, hi);

        if (k == mid) return a[k];
        else if (k < mid)
            return find(lessF, a, lo, mid - 1, k); // search left subarray
        else if (k > mid)
            return find(lessF, a, mid + 1, hi, k); // search right subarray
        else throw new IllegalStateException("Not found");
    }
}