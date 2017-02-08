package jobinterview.sort;

import jobinterview.MySort;

/**
 * Created by ekis on 07/02/17.
 */
public final class MyShellViaArray extends MySort {

    private MyShellViaArray() {}

    public static void sort(Comparable[] a) {
        int N = a.length;
        int strides[] = new int[N / 3 - 1];
        int L = strides.length;

        int idx = 1, h = 1;
        strides[0] = h;
        while (idx < strides.length) { // compute and store h-values in array instead of recomputing them after each sort pass
            h = 3 * h + 1;
            strides[idx++] = h;
        }

        for (int i = L - 1; i >= 0; i--) {
            h = strides[i];
            for (int j = h; j < N; j++) {
                for (int k = j; k >= h && less(a[k], a[k - h]); k -= h)
                    exch(a, k, k - h);
            }
        }
    }
}