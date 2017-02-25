package jobinterview.sort.quicksort;

import edu.princeton.cs.algs4.Knuth;

import java.util.Comparator;
import java.util.function.BiPredicate;

import static jobinterview.SortUtility.eqF;
import static jobinterview.SortUtility.lessF;
import static jobinterview.sort.quicksort.MyQuick.partition;

/**
 * Sorts the array by shuffling it initially, pick a suitable pivot element and then partition the array so that
 * - all elements LEFT (== with smaller index) of pivot are not greater than pivot
 * - all elements RIGHT (== with greater index) of pivot are not lesser than pivot
 * Then sort both partitions recursively.
 */
public final class MyQuickSort {

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
        int mid = partition(lessF, eqF, a, lo, hi);
        sort(lessF, eqF, a, lo, mid - 1);
        sort(lessF, eqF, a, mid + 1, hi);
    }
}