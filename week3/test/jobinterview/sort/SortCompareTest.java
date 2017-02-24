package jobinterview.sort;

import edu.princeton.cs.algs4.StdOut;
import org.junit.Test;

import static jobinterview.sort.compare.SortCompare.timeRandomInput;
import static jobinterview.sort.SortAlgorithm.*;

public final class SortCompareTest {

    private static final int T = 100; // number of random arrays
    private static final int N = 100000;  // random array length

    @Test
    public void compareAlgorithms() {
        compare(INSERTION, SHELL);
        compare(INSERTION, MERGE_BOTTOM_UP);
        compare(INSERTION, MERGE_TOP_DOWN);
        compare(MERGE_BOTTOM_UP, MERGE_TOP_DOWN);
        compare(INSERTION, QUICK);
        compare(MERGE_BOTTOM_UP, QUICK);
        compare(MERGE_TOP_DOWN, QUICK);
        compare(QUICK_3_WAY, QUICK);
    }

    private static void compare(SortAlgorithm alg1, SortAlgorithm alg2) {
        double t1 = timeRandomInput(alg1, N, T);
        double t2 = timeRandomInput(alg2, N, T);

        StdOut.printf("For %d random Doubles\n      %s is", N, alg1.text());
        StdOut.printf(" %.1f times faster than %s\n", t2/t1, alg2.text());
    }
}