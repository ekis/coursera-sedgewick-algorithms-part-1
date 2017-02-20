package jobinterview.sort.mergesort;

import jobinterview.MySort;

import java.util.function.BiPredicate;

abstract class MyMergeSort extends MySort {

    protected static final int CUTOFF = 7;

    protected static final <T> void merge(BiPredicate<T, T> lessF, T[] aux, T[] a, int lo, int mid, int hi) {
        int i = lo;      // current entry in the left sub-array
        int j = mid + 1; // current entry in the right sub-array
        int k;           // current entry in the sorted array

        for (k = lo; k <= hi; k++) // copy a[lo..hi] to aux[lo..hi]
            aux[k] = a[k];

        for (k = lo; k <= hi; k++) { // merge back to a[lo..hi]
            if (i > mid)     a[k] = aux[j++]; // left half exhausted (take from right)
            else if (j > hi) a[k] = aux[i++]; // right half exhausted (take from left)
            else if (lessF.test(aux[j], aux[i])) a[k] = aux[j++]; // current key on right < current key on left (take from right) -- this is what makes the sort stable!!
            else a[k] = aux[i++]; // current key on right > current key on right (take from left)
        }
    }
}
