package jobinterview.sort;

import jobinterview.MySort;

/**
 * Created by ekis on 06/02/17.
 */
public final class MyShell extends MySort {

    private MyShell() {
    }

    public static void sort(Comparable[] a) {
        int N = a.length;
        int h = 1;

        while (h < N / 3) // compute sort stride (h) by choosing the greatest number (smaller than N / 3) from the 3x + 1 increment sequence: 1, 4, 13, 40, 121, 364...
            h = 3 * h + 1;

        while (h >= 1) {
            for (int i = h; i < N; i++) {
                for (int j = i; j >= h && less(a[j], a[j - h]); j -= h)
                    exch(a, j, j - h);
            }
            h = h / 3;
        }
    }
}