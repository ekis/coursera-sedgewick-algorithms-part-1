package jobinterview.sort.mergesort;

import java.util.Comparator;
import java.util.function.BiPredicate;

import static jobinterview.SortUtility.*;
import static jobinterview.sort.mergesort.MyMergeSort.*;

/**
 * Works by organising the merges so that we do all the merges of tiny subarrays in one pass,
 * then do a second pass to merge those subarrays in pairs and so forth, continuing until we do a merge that
 * encompasses the whole array.
 *
 * Does not use recursion, which may be an advantage in languages that do not do tail-recursive optimisation (Java).
 */
public final class MyBottomUpMergeSort {


    public static <T extends Comparable<T>> void sort(T[] a) {
        @SuppressWarnings("unchecked")
        T[] aux = (T[]) new Comparable[a.length]; // auxilliary array for merges
        sort(aux, a, lessWithComparable());
    }

    public static <T> void sort(T[] a, Comparator<? super T> c) {
        @SuppressWarnings("unchecked")
        T[] aux = (T[]) new Object[a.length];
        sort(aux, a, lessWithComparator(c));
    }

    private static <T> void sort(T[] aux, T[] a, BiPredicate<T, T> lessF) {
        @SuppressWarnings("unchecked")
        int N = a.length;

        for (int sz = 1; sz < N; sz = sz + sz) {           // sz -> subarray size (incremented log(N) times)
            for (int lo = 0; lo < N - sz; lo += sz + sz) { // lo -> subarray index
                int mid = lo + sz - 1;
                int hi = Math.min(lo + sz + sz -1, N - 1);
                merge(lessF, aux, a, lo, mid, hi);
            }
        }
    }
}