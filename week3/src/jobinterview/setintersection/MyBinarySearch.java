package jobinterview.setintersection;

import static jobinterview.SortUtility.calculateMid;

public final class MyBinarySearch {

    private MyBinarySearch() {}

    public static <T extends Comparable<T>> boolean find(T element, Comparable<T>[] a) {
        return find(element, a, 0, a.length - 1);
    }

    private static <T extends Comparable<T>> boolean find(T element, Comparable<T>[] array, int lo, int hi) {
        if (lo == hi) return array[lo].compareTo(element) == 0;
        int mid = calculateMid(lo, hi);
        Comparable<T> midElement = array[mid];
        if (midElement.compareTo(element) == 0)
            return true;
        if (midElement.compareTo(element) < 0)
            return find(element, array, mid + 1, hi);
        return find(element, array, lo, mid);
    }

}
