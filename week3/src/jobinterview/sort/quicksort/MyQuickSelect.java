package jobinterview.sort.quicksort;

import edu.princeton.cs.algs4.Knuth;

import java.util.function.BiPredicate;

import static jobinterview.SortUtility.eqF;
import static jobinterview.SortUtility.lessF;
import static jobinterview.sort.quicksort.MyQuick.partition;

/**
 * Finds the kth smallest number in a list or array (i.e the order statistic).
 */
public final class MyQuickSelect {

    public static <T extends Comparable<? super T>> T select(T[] a, int k) {
        if (k < 1) throw new IllegalStateException("Invalid k - must be in [1, inputLength].");
        if (k > a.length) throw new IllegalStateException("K-th element exceeds array length.");
        Knuth.shuffle(a);
        return find(lessF(), eqF(), a, 0, a.length - 1, k - 1);
    }

    private static <T> T find(BiPredicate<T, T> lessF, BiPredicate<T, T> eqF, T[] a, int lo, int hi, int k) {
        int mid = partition(lessF, eqF, a, lo, hi);

        if (k == mid) return a[k];
        else if (k < mid)
            return find(lessF, eqF, a, lo, mid - 1, k); // search left subarray
        else if (k > mid)
            return find(lessF, eqF, a, mid + 1, hi, k); // search right subarray
        else throw new IllegalStateException("Not found");
    }
}