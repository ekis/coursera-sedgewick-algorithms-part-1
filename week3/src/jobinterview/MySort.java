package jobinterview;

import edu.princeton.cs.algs4.StdOut;

import java.util.stream.Stream;

/**
 * Created by ekis on 05/02/17.
 */
public abstract class MySort {

    // is v < w ?
    protected static final boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    protected static final void exch(Comparable[] a, int i, int j) {
        Comparable temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    protected static final void show(Comparable[] a) {
        Stream.of(a).forEach(x -> StdOut.print(x + " "));
    }

    public static final boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++)
            if (less(a[i], a[i - 1])) return false;
        return true;
    }
}
