package jobinterview.sort;

import jobinterview.MySort;

import static jobinterview.Utility.calculateMid;

/**
 * Created by ekis on 12/02/17.
 */
public final class MyTopDownMergeSort extends MySort {

    private static Comparable[] aux; // auxilliary array for merges

    public static void sort(Comparable[] a) {
        aux = new Comparable[a.length];
        sort(a, 0, a.length -1);
    }

    private static void sort(Comparable[] a, int lo, int hi) {
        if (lo >= hi) return;

        int mid = calculateMid(lo, hi);

        sort(a, lo, mid); // sort left
        sort(a, mid + 1, hi); // sort right

        merge(a, lo, mid, hi);
    }

    private static void merge(Comparable[] a , int lo, int mid, int hi) {
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