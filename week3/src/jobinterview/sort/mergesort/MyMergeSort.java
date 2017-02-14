package jobinterview.sort.mergesort;

import jobinterview.MySort;

/**
 * Created by ekis on 13/02/17.
 */
abstract class MyMergeSort extends MySort {

    protected static final int CUTOFF = 7;

    static void merge(Comparable[] aux, Comparable[] a, int lo, int mid, int hi) {
        int i = lo;      // current entry in the left sub-array
        int j = mid + 1; // current entry in the right sub-array
        int k;           // current entry in the sorted array

        for (k = lo; k <= hi; k++) // copy a[lo..hi] to aux[lo..hi]
            aux[k] = a[k];

        for (k = lo; k <= hi; k++) { // merge back to a[lo..hi]
            if (i > mid)                   // left half exhausted (take from right)
                a[k] = aux[j++];
            else if (j > hi)               // right half exhausted (take from left)
                a[k] = aux[i++];
            else if (less(aux[i], aux[j])) // current key on left < current key on right (take from left)
                a[k] = aux[i++];
            else                           // current key on left > current key on right (take from right)
                a[k] = aux[j++];
        }
    }

}
