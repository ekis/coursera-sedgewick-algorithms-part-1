package jobinterview.sort;

import jobinterview.MySort;

import java.util.Comparator;
import java.util.function.BiPredicate;

/**
 * Traverse the array, exchanging i-th element with (i - 1)-th until i <= (i - 1).
 *
 * Created by ekis on 05/02/17.
 */
public final class MyInsertion extends MySort {

    private MyInsertion() {
    }

    public static <T extends Comparable<? super T>> void sort(T[] a) {
        sort(a, lessWithComparable());
    }

    public static <T> void sort(T[] a, Comparator<? super T> c) {
        sort(a, lessWithComparator(c));
    }

    private static <T> void sort(T[] a, BiPredicate<T, T> lessF) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            for (int j = i; j > 0 && lessF.test(a[j], a[j - 1]); j--)
                exch(a, j, j - 1);
        }
    }
}