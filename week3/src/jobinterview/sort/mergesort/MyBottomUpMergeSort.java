package jobinterview.sort.mergesort;

/**
 * Created by ekis on 13/02/17.
 */
public final class MyBottomUpMergeSort extends MyMergeSort {

    private static Comparable[] aux; // auxilliary array for merges

    public static void sort(Comparable[] a) {
        aux = new Comparable[a.length];
        int N = a.length;

        for (int sz = 1; sz < N; sz = sz + sz) {           // sz -> subarray size (incremented log(N) times)
            for (int lo = 0; lo < N - sz; lo += sz + sz) { // lo -> subarray index
                int mid = lo + sz - 1;
                int hi = Math.min(lo + sz + sz -1, N - 1);
                merge(aux, a, lo, mid, hi);
            }
        }
    }
}
