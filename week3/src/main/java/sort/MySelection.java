package sort;

import java.util.Comparator;
import java.util.function.BiPredicate;

import static util.SortUtility.exch;
import static util.SortUtility.lessF;

/**
 * First, find the smallest item in the array and exchange it with first item.
 * Then, find the next smallest item in the array and exchange it with second item.
 * Continue until array is sorted.
 */
public final class MySelection {

    private MySelection() {}

    public static <T extends Comparable<? super T>> void sort(T[] a) {
        sort(a, lessF());
    }

    public static <T> void sort(T[] a, Comparator<? super T> c) {
        sort(a, lessF(c));
    }

    private static <T> void sort(T[] a, BiPredicate<T, T> lessF) {
        int N = a.length;

        for (int i = 0; i < N; i++) {
            int min = i;
            for (int j = i + 1; j < N; j++) { // find smallest
                if (lessF.test(a[j], a[min]))
                    min = j;
            }
            exch(a, i, min); // exchange smallest with current
        }
    }
}
