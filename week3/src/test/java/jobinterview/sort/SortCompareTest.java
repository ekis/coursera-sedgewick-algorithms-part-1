package jobinterview.sort;

import edu.princeton.cs.algs4.StdOut;
import org.junit.Test;

import static jobinterview.sort.compare.SortCompare.timeRandomInput;
import static jobinterview.sort.SortAlgorithm.*;

public final class SortCompareTest {

    private static final int T = 10; // number of random arrays
    private static final int N = 10000;  // random array length

    @Test
    public void compareAlgorithms() {
        compare(SHELL, INSERTION);
        compare(MERGE_BOTTOM_UP, INSERTION);
        compare(MERGE_TOP_DOWN, INSERTION);
        compare(QUICK, INSERTION);
        compare(SHELL, SHELL_VIA_ARRAY);
        compare(MERGE_BOTTOM_UP, MERGE_TOP_DOWN);
        compare(MERGE_BOTTOM_UP, QUICK);
        compare(MERGE_TOP_DOWN, QUICK);
        compare(QUICK_3_WAY, QUICK);
        compare(QUICK_3_WAY_ENTROPY_OPTIMAL, QUICK);
        compare(QUICK_3_WAY_ENTROPY_OPTIMAL, QUICK_3_WAY);
    }

    private static void compare(SortAlgorithm alg1, SortAlgorithm alg2) {
        double t1 = timeRandomInput(alg1, N, T);
        double t2 = timeRandomInput(alg2, N, T);

        StdOut.printf("For %d random Doubles\n      %s works at", N, alg1.text());
        StdOut.printf(" %.1f %% of %s\n", t2/t1 * 100, alg2.text());
    }
}