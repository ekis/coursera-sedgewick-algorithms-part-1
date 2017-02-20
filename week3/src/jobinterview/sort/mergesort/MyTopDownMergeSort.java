package jobinterview.sort.mergesort;

import jobinterview.sort.MyInsertion;

import java.util.Comparator;
import java.util.function.BiPredicate;

import static jobinterview.Utility.calculateMid;

/**
 * To sort an array, divide it into two halves, sort the two halves (recursively) and then merge the results.
 *
 * Contains some basic optimisations, such as:
 * - use insertion sort for small arrays
 * - do not merge if last element of left array is larger than first element of right array (the array is then sorted)
 *
 * TODO optimisations:
 * - prevent extraneous copying of elements in merge (we need not make a copy to aux array)
 *
 */
public final class MyTopDownMergeSort extends MyMergeSort {

    public static <T extends Comparable<T>> void sort(T[] a) {
        @SuppressWarnings("unchecked")
        T[] aux = (T[]) new Comparable[a.length]; // auxilliary array for merges

        if (a.length <= CUTOFF) {
            MyInsertion.sort(a);
            return;
        }
        sort(lessWithComparable(), aux, a, 0, a.length - 1);
    }

    public static <T> void sort(T[] a, Comparator<? super T> c) {
        @SuppressWarnings("unchecked")
        T[] aux = (T[]) new Object[a.length]; // auxilliary array for merges

        if (a.length <= CUTOFF) {
            MyInsertion.sort(a, c);
            return;
        }
        sort(lessWithComparator(c), aux, a, 0, a.length - 1);
    }

    private static <T> void sort(BiPredicate<T, T> lessF, T[] aux, T[] a, int lo, int hi) {
        if (lo >= hi) return;
        int mid = calculateMid(lo, hi);

        sort(lessF, aux, a, lo, mid); // sort left
        sort(lessF, aux, a, mid + 1, hi); // sort right

        if (!lessF.test(a[mid + 1], a[mid])) return;
        merge(lessF, aux, a, lo, mid, hi);
    }
}