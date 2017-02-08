package jobinterview.sort;

import jobinterview.MySort;

/**
 * First, find the smallest item in the array and exchange it with first item.
 * Then, find the next smallest item in the array and exchange it with second item.
 * Continue until array is sorted.
 *
 * Created by ekis on 05/02/17.
 */
public final class MySelection extends MySort {

    private MySelection() {}

    public static void sort(Comparable[] a) {
        int N = a.length;

        for (int i = 0; i < N; i++) {
            int min = i;
            for (int j = i + 1; j < N; j++) { // find smallest and exchange it with current
                if (less(a[j], a[min]))
                    min = j;
                exch(a, i, min);
            }
        }
    }
}
