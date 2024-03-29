package sort.quicksort;

import edu.princeton.cs.algs4.Knuth;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.BiPredicate;

import static util.SortUtility.*;
import static sort.quicksort.MyQuick.medianOf3;
import static sort.quicksort.MyQuick.ninther;

/**
 * Implementation of Bentley-McIlroy's entropy-optimal (i.e. the average number of compares in this 3-way quicksort is
 * within constant factor of the average number of compares used by the best possible, compare-sorting algorithm -
 * for an arbitrary distribution of input key values) 3-way quicksort which dispenses with the cost of additional
 * exchanges introduced by Dijkstra's 3-way quicksort by bundling and keeping the identical keys together and paying for
 * the exchange cost just once (instead of possibly many times over the course of partitioning the array).
 *
 * The algorithm works in two phases:
 * 1) Phase 1 - partition the array so that the following invariants hold (from left to right in array):
 * - [lo, p> interval contains elements E1 which satisfy E1 == pivot
 * - [p, i]  interval contains elements E2 which satisfy E2 < pivot
 * - <i, j>  interval contains unseen elements E3
 * - [j, q]  interval contains elements E4 which satisfy E4 > pivot
 * - <q, hi] interval contains elements E4 which satisfy E5 == pivot
 *
 * 2) Phase 2 - move elements equal to pivot from the array boundaries to the inside of array. These elements will no
 * longer be processed in subsequent recursive calls.
 *
 * Further details may be obtained in Bentley-McIlroy original 1993 paper, available here:
 * http://cs.fit.edu/~pkc/classes/writing/samples/bentley93engineering.pdf
 *
 */
public final class MyQuickSortEntropyOptimal {

    public static <T extends Comparable<? super T>> void sort(T[] a) {
        Knuth.shuffle(a);
        sort(lessF(), eqF(), a, 0, a.length - 1);
    }

    public static <T> void sort(T[] a, Comparator<? super T> c) {
        Knuth.shuffle(a);
        sort(lessF(c), eqF(c), a, 0, a.length - 1);
    }

    private static <T> void sort(BiPredicate<T, T> lessF, BiPredicate<T, T> eqF, T[] a, int lo, int hi) {
        if (lo >= hi) return;
        int p = lo, q = hi + 1;
        int i = lo, j = hi + 1;
        int pivotIdx = choosePivot(lessF, a, lo, hi);
        exch(a, lo, pivotIdx);
        T pivot = a[lo];

        // phase 1
        while(true) {
            while(lessF.test(a[++i], pivot)) if (i == hi) break;
            while(lessF.test(pivot, a[--j])) if (j == lo) break;

            if (i >= j) break;

            exch(a, i, j);
            if (eqF.test(a[i], pivot)) exch(a, i, ++p);
            if (eqF.test(a[j], pivot)) exch(a, j, --q);
        }

        // phase 2
        while(p >= lo) exch(a, j--, p--);
        while(q <= hi) exch(a, i++, q++);

        // sort subarrays
        sort(lessF, eqF, a, lo, j);
        sort(lessF, eqF, a, i, hi);
    }

    private static <T> int choosePivot(BiPredicate<T, T> lessF, T[] a, int lo, int hi) {
        int N = hi - lo;
        if (N > 7) {
            // creating this subarray is not as expensive as it seems. asList() delegates to a new ArrayList whose
            // implementation is internal to Arrays class and doesn't copy the array, just stores the reference.
            // subList() yields a SubList which still references the original list (which references the array).
            // so it just creates a couple of wrapper objects, all backing the original array, and since there
            // is no array modification (just reading), this works
            @SuppressWarnings("unchecked") // no other reasonable way to create a T[] array
            T[] subArray = (T[]) Arrays.asList(a).subList(lo, hi).toArray();
            if (N > 40) return lo + ninther(subArray, lessF); // big arrays, pseudomedian of 9
            return lo + medianOf3(subArray, lessF); // mid-size, median of 3
        }
        return calculateMid(lo, hi); // small arrays, middle element
    }
}