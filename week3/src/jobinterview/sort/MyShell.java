package jobinterview.sort;

import java.util.Comparator;
import java.util.function.BiPredicate;

import static jobinterview.SortUtility.*;

/**
 * Generalised version of Insertion sort - it uses the same principle, except it works in multiple passes, each pass
 * exchanging elements of distance h. Each consecutive pass through the array reduces the h, and partially sorts the array
 * so that the final pass is insertion sort (h = 1). The final pass is quick, as insertion sort works well on partially sorted arrays.
 *
 * Calculating h-distance such that sort complexity is sub-quadratic is an ongoing research problem. The distance in this
 * implementation is calculated with Knuth's h-sequence equation, which yields worst-case shell sort performance of O(N^3/2)
 * and is easy to compute. There are better (but more complex) sequences discovered, however, all are sub-quadratic.
 *
 */
public final class MyShell {

    private MyShell() {
    }

    public static <T extends Comparable<? super T>> void sort(T[] a) {
        sort(a, lessWithComparable());
    }

    public static <T> void sort(T[] a, Comparator<? super T> c) {
        sort(a, lessWithComparator(c));
    }

    private static <T> void sort(T[] a, BiPredicate<T, T> lessF) {
        int N = a.length;
        int h = 1;

        while (h < N / 3) // compute sort stride (h) by choosing the greatest number (smaller than N / 3) from the 3x + 1 increment sequence: 1, 4, 13, 40, 121, 364...
            h = 3 * h + 1;

        while (h >= 1) {
            for (int i = h; i < N; i++) {
                for (int j = i; j >= h && lessF.test(a[j], a[j - h]); j -= h)
                    exch(a, j, j - h);
            }
            h = h / 3;
        }
    }
}