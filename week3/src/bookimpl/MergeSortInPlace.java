package bookimpl;

import static bookimpl.SortHelper.less;

/**
 * Created by pakna on 25/08/16.
 */
public class MergeSortInPlace {

    public static void sort(Comparable[] a) {
        Comparable[] aux = new Comparable[a.length];
        sort(a, aux, 0, a.length - 1);
    }

    private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi) { // this is really divide and merge
        if (hi <= lo) return;
        int mid = lo + (hi - lo) / 2;

        sort(a, aux, 0, mid); // sort left
        sort(a, aux, mid + 1, hi); // sort right
        merge(a, aux, lo, mid, hi);
    }

    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
        for (int k = lo; k <= hi; k++) // copy array
            aux[k] = a[k];

        int i = lo;
        int j = mid + 1;

        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                a[k] = a[j++]; // left-hand array is empty
            }
            else if (j > hi) {
                a[k] = a[i++]; // right-hand array is empty
            }
            else if (less(aux[j], aux[i])) {
                a[k] = a[j++]; // i < j
            }
            else {
                a[k] = a[i++]; // i > j
            }
        }
    }
}