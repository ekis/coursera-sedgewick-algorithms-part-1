package jobinterview;

import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;
import java.util.function.BiPredicate;
import java.util.stream.Stream;

public abstract class MySort {

    // is v < w ?
    protected static <T extends Comparable<? super T>> BiPredicate<T, T> lessWithComparable() {
        return (v, w) -> v.compareTo(w) < 0;
    }

    protected static <T> BiPredicate<T, T> lessWithComparator(Comparator<? super T> c) {
        return (t1, t2) -> c.compare(t1, t2) < 0;
    }

    protected static final <T> void exch(T[] a, int i, int j) {
        T temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    protected static final <T extends Comparable<? super T>> void show(T[] a) {
        Stream.of(a).forEach(x -> StdOut.print(x + " "));
    }

    public static final <T extends Comparable<? super T>> boolean isSorted(T[] a) {
        BiPredicate<T, T> f = lessWithComparable(); // needed because of type erasure; the compiler can't verify the T in this method is the same as the T in the function declaration
        for (int i = 1; i < a.length; i++)
            if (f.test(a[i], a[i - 1])) return false;
        return true;
    }
}
