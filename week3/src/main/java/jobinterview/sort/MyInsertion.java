package jobinterview.sort;

import java.util.Comparator;
import java.util.function.BiPredicate;

import static jobinterview.SortUtility.exch;
import static jobinterview.SortUtility.lessF;
/**
 * Traverse the array, exchanging i-th element with (i - 1)-th until i <= (i - 1).
 */
public final class MyInsertion {

    private MyInsertion() {
    }

    public static <T extends Comparable<? super T>> void sort(T[] a) {
        sort(a, lessF());
    }

    public static <T> void sort(T[] a, Comparator<? super T> c) {
        sort(a, lessF(c));
    }

    private static <T> void sort(T[] a, BiPredicate<T, T> lessF) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            for (int j = i; j > 0 && lessF.test(a[j], a[j - 1]); j--)
                exch(a, j, j - 1);
        }
    }
}