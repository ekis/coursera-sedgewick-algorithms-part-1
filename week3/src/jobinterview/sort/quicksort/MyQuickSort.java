package jobinterview.sort.quicksort;

import edu.princeton.cs.algs4.Knuth;
import jobinterview.MySort;

import java.util.function.BiPredicate;

/**
 * Sorts the array by shuffling it initially, pick a suitable pivot element and then partition the array so that
 * - all elements LEFT (== with smaller index) of pivot are not greater than pivot
 * - all elements RIGHT (== with greater index) of pivot are not lesser than pivot
 * Then sort both partitions recursively.
 */
public final class MyQuickSort extends MySort {

    public static <T extends Comparable<? super T>> void sort(T[] a) {
        Knuth.shuffle(a);
        sort(lessWithComparable(), a, 0, a.length - 1);
    }


    private static <T> void sort(BiPredicate<T, T> lessF, T[] a, int lo, int hi) {
        if (lo >= hi) return;
        int mid = partition(lessF, a, lo, hi);
        sort(lessF, a, lo, mid - 1);
        sort(lessF, a, mid + 1, hi);
    }

    /**
     * Phase 1. Repeat until i and j pointers cross:
     * - scan i from left to right so long as (a[i] < a[lo])
     * - scan j from right to left so long as (a[j] > a[lo])
     * - exchange a[i] with a[j]
     *
     * Phase 2. When j < i:
     * - exchange a[pivot] with a[j]
     */
    private static <T> int partition(BiPredicate<T, T> lessF, T[] a, int lo, int hi) {
        T pivot = a[lo];
        int i = lo + 1;
        int j = hi;

        while (true) { // phase 1
            while (i <= hi && lessF.test(a[i], pivot)) { // is a[i] > pivot?
                i++;
            }
            while (j >= i && !lessF.test(a[j], pivot)) { // is a[j] <= pivot?
                j--;
            }
            if (i >= j) break;
            exch(a, i, j);
        }
        exch(a, lo, j); // phase 2
        return j;
    }
}