package jobinterview;

import java.util.Comparator;
import java.util.function.BiPredicate;

public final class SortUtility {

    public static <T extends Comparable<? super T>> BiPredicate<T, T> lessF() {
        return (v, w) -> v.compareTo(w) < 0;
    }

    public static <T extends Comparable<? super T>> BiPredicate<T, T> eqF() {
        return (v, w) -> v.compareTo(w) == 0;
    }

    public static <T> BiPredicate<T, T> lessF(Comparator<? super T> c) {
        return (t1, t2) -> c.compare(t1, t2) < 0;
    }

    public static <T> BiPredicate<T, T> eqF(Comparator<? super T> c) {
        return (t1, t2) -> c.compare(t1, t2) == 0;
    }

    public static <T> void exch(T[] a, int i, int j) {
        if (i == j) return;
        T temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static <T extends Comparable<? super T>> boolean isSorted(T[] a) {
        BiPredicate<T, T> f = lessF(); // needed because of type erasure; the compiler can't verify the T in this method is the same as the T in the function declaration
        for (int i = 1; i < a.length; i++)
            if (f.test(a[i], a[i - 1])) return false;
        return true;
    }

    public static int calculateMid(int lo, int hi) {
        return ((hi - lo) /2) + lo;
    }
}
