package jobinterview.sort;

import jobinterview.MySort;

/**
 * Created by ekis on 05/02/17.
 */
public final class MyInsertion extends MySort {

    private MyInsertion() {}

    public static void sort(Comparable[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            for (int j = i; j > 0 && less(a[j], a[j - 1]); j--)
                exch(a, j, j - 1);
//            for (int j = i; j > 0; j--) {
//                if (less(a[j], a[j - 1]))
//                    exch(a, j, j - 1);
//                else break;
//            }
        }
    }
}