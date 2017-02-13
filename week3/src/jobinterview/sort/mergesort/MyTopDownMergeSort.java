package jobinterview.sort.mergesort;

import static jobinterview.Utility.calculateMid;

/**
 * Created by ekis on 12/02/17.
 */
public final class MyTopDownMergeSort extends MyMergeSort {

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

        merge(aux, a, lo, mid, hi);
    }
}