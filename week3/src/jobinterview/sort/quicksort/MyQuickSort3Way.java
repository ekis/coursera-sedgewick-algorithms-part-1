package jobinterview.sort.quicksort;

import edu.princeton.cs.algs4.Knuth;

import java.util.Comparator;
import java.util.function.BiPredicate;

import static jobinterview.SortUtility.exch;
import static jobinterview.SortUtility.lessWithComparable;
import static jobinterview.SortUtility.lessWithComparator;

/**
 * A variant of Quicksort which aims to improve performance in presence of a significant number of duplicate keys
 * in input array. It does so by introducing two additional pointers (LT - less than and GT - greater then) which
 * enforce the following invariants during partitioning:
 * - currently examined element is at index i
 * - anything left of LT is less than pivot element
 * - anything right of RT is greater than pivot element
 * - anything in interval [LT, i] is equal to pivot element
 *
 * In presence of large number of duplicate keys, this algorithm will work in linear time on average but is
 * not entropy-optimal with respect to compare operations, so will work slower than regular Quicksort for input array
 * with (highly) distinct key distributions.
 *
 */
public final class MyQuickSort3Way {

    public static <T extends Comparable<? super T>> void sort(T[] a) {
        Knuth.shuffle(a);
        sort(lessWithComparable(), a, 0, a.length - 1);
    }

    public static <T> void sort(T[] a, Comparator<? super T> c) {
        Knuth.shuffle(a);
        sort(lessWithComparator(c), a, 0, a.length - 1);
    }

    private static <T> void sort(BiPredicate<T, T> lessF, T[] a, int lo, int hi) {
        if (lo >= hi) return;
        int lt = lo;
        int i = lo + 1;
        int gt = hi;
        T pivot = a[lt];

        while (i <= gt) {
            if (lessF.test(a[i], pivot)) exch(a, i++, lt++);
            else if (lessF.test(pivot, a[i])) exch(a, i, gt--);
            else i++;
        }
        sort(lessF, a, lo, lt - 1);
        sort(lessF, a, gt + 1, hi);
    }
}
