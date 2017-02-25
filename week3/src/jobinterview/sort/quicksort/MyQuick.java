package jobinterview.sort.quicksort;

import java.util.function.BiPredicate;

import static jobinterview.SortUtility.exch;

final class MyQuick {

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
}
