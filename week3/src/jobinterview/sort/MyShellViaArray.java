package jobinterview.sort;

import java.util.Comparator;
import java.util.function.BiPredicate;

import static jobinterview.SortUtility.*;

/**
 * Shell sort implementation that calculates h-distance series first and stores the elements in an array,
 * and then walking down the array one element at a pass, as we complete the sort with h = 1.
 */
public final class MyShellViaArray {

    private MyShellViaArray() {}

    public static <T extends Comparable<? super T>> void sort(T[] a) {
        sort(a, lessWithComparable());
    }

    public static <T> void sort(T[] a, Comparator<? super T> c) {
        sort(a, lessWithComparator(c));
    }

    private static <T> void sort(T[] a, BiPredicate<T, T> lessF) {
        int N = a.length;
        int strides[] = new int[computeStridesLengthFor(N)];
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
                for (int k = j; k >= h && lessF.test(a[k], a[k - h]); k -= h)
                    exch(a, k, k - h);
            }
        }
    }

    private static int computeStridesLengthFor(int N) {
        int h = 1;
        int result = 1;
        while (h < N / 3) {
            h = 3 * h + 1;
            result++;
        }
        return result;
    }
}