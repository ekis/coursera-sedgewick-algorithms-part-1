package jobinterview.sort.quicksort;

import java.util.function.BiPredicate;

import static jobinterview.SortUtility.*;

/**
 * Contains helper code for quick sort implementations.
 *
 * Median of 3 method and Tukey's ninther implementations are based on the original 1993 Bentley-McIlroy paper[1]
 * [1] http://cs.fit.edu/~pkc/classes/writing/samples/bentley93engineering.pdf
 */
public final class MyQuick {

    /**
     * Phase 1. Repeat until i and j pointers cross:
     * - scan i from left to right so long as (a[i] < a[lo])
     * - scan j from right to left so long as (a[j] > a[lo])
     * - exchange a[i] with a[j]
     *
     * Phase 2. When j < i:
     * - exchange a[pivot] with a[j]
     */
    static <T> int partition(BiPredicate<T, T> lessF, BiPredicate<T, T> eqF, T[] a, int lo, int hi) {
        T pivot = a[lo];
        int i = lo + 1;
        int j = hi;

        while (true) { // phase 1
            while (i <= hi && (lessF.test(a[i], pivot) || eqF.test(a[i], pivot))) // is a[i] >= pivot?
                i++;

            while (j >= i && !lessF.test(a[j], pivot))  // is a[j] <= pivot?
                j--;

            if (i >= j) break;
            exch(a, i, j);
        }
        exch(a, lo, j); // phase 2
        return j;
    }

    public static <T> int medianOf3(T[] a, BiPredicate<T, T> lessF) {
        return medianOf3(lessF, a, 0, calculateMid(0, a.length - 1), a.length - 1);
    }

    public static <T> int ninther(T[] a, BiPredicate<T, T> lessF) {
        return ninther(lessF, a, 0, calculateMid(0, a.length - 1), a.length - 1);
    }

    private static <T> int ninther(BiPredicate<T, T> lessF, T[] a, int lo, int mid, int hi) {
        // the median size breaks were determined empirically in the original paper
        int delta = hi/8;

        int m1 = medianOf3(lessF, a, lo, lo + delta, lo + delta + delta);
        int m2 = medianOf3(lessF, a, mid - delta, mid, mid + delta);
        int m3 = medianOf3(lessF, a, hi - delta - delta, hi - delta, hi);

        @SuppressWarnings("unchecked") // no other reasonable way to create an array of T[]
        T[] medians = (T[]) new Object[] {a[m1], a[m2], a[m3]};

        return medianOf3(lessF, medians, 0, 1, 2);
    }

    private static <T> int medianOf3(BiPredicate<T, T> lessF, T[] a, int lo, int mid, int hi) {
        T x = a[lo];
        T y = a[mid];
        T z = a[hi];

        return (lessF.test(x, y) ?
                (lessF.test(y, z) ? mid : lessF.test(x, z) ? hi : lo) :
                (lessF.test(z, y) ? hi : lessF.test(z, x) ? hi : lo));
    }

    // unfortunately, we can't use this much more elegant version - it's hacky, uses reflection to create a generic array
    // and, owing to type erasure, it wouldn't work with polymorphic types.as, the QuickSort code relying on it
    // ungracefully fails at runtime - which is unacceptable.
//    public static <T extends Comparable<? super T>> T brokenMedianOf3WithGenericsMess(T[] a) {
//        T[] three = (T[]) Array.newInstance(a[0].getClass(), 3);
//        three[0] = a[0];
//        three[1] = a[calculateMid(0, a.length - 1)];
//        three[2] = a[a.length - 1];
//        return MyQuickSelect.select(three, 2);
//    }
}