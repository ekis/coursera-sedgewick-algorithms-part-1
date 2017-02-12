package jobinterview.setintersection;

import jobinterview.sort.MyShell;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Exercise: Given two arrays a[] and b[], each containing N distinct 2D points in the plane, design a sub-quadratic algorithm
 * to count the number of points that are contained both in array a[] and array b[].
 *
 * Solution: We retrieve the count in guaranteed worst-case sub-quadratic time. We use list and not hash-based data structure to store values.
 * Worst-case complexity is sub-quadratic: O(N^3/2) + O(n*logN).
 * 1) The Shellsort is O(N^3/2) for currently used h-function
 * 2) The time it takes to find an intersection for a given element is O(N*logN)
 * Note that in cases of extreme set asymmetry (e.g. a.size() >> b.size()), running time may approach sub-linear,
 * as binary search cost begins to dominate the count operation.
 *
 * Known issues: To improve test runtimes on large datasets (10^6 elements), we don't perform a check whether a point already exists in the list.
 * Such a check on a list is at best O(n) and it dominates the test runtime. Only a more advanced data structure would support such faster ops.
 *
 * Created by ekis on 08/02/17.
 */
public final class SetIntersection {

    private final List<Point> a = new ArrayList<>();
    private final List<Point> b = new ArrayList<>();

    public void addToA(int x, int y) {
        a.add(new Point(x, y));
    }

    public void addToB(int x, int y) {
        b.add(new Point(x, y));
    }

    public long intersectCount() {
        Point[] as = a.toArray(new Point[a.size()]);
        Point[] bs = b.toArray(new Point[b.size()]);
        if (as.length > bs.length)
            return countIntersections(bs, as);
        return countIntersections(as, bs);
    }

    private static long countIntersections(Point[] smaller, Point[] larger) {
        MyShell.sort(larger);
        return Stream.of(smaller).filter(x -> MyBinarySearch.find(x, larger)).count();
    }

    private static class Point implements Comparable<Point> {
        private final int x;
        private final int y;

        private Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int compareTo(Point that) {
            if (x == that.x) {
                if (y == that.y) return 0;
                if (y < that.y) return -1;
                if (y > that.y) return 1;
            }
            if (x < that.x) return -1;
            return 1;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x &&
                    y == point.y;
        }

        @Override
        public String toString() {
            return String.format("(x = %s, y = %s)", x, y);
        }
    }
}