package sort;

import edu.princeton.cs.algs4.StdOut;
import ekis.SortRandomData;
import org.junit.Ignore;
import org.junit.Test;

import static sort.compare.SortCompare.timeRandomInput;
import static sort.SortAlgorithm.*;

@Ignore // the timings here are wild and unexpected, I need to take a closer look at this
public final class SortCompareTest {

    private static final int T = 10; // number of test runs

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
        compare(HEAP, INSERTION);
        compare(HEAP, SHELL);
        compare(HEAP, MERGE_TOP_DOWN);
        compare(HEAP, QUICK);
        compare(HEAP, QUICK_3_WAY_ENTROPY_OPTIMAL);
    }

    private static void compare(SortAlgorithm alg1, SortAlgorithm alg2) {
        Integer[] data = SortRandomData.SUBQUADRATIC_SORT.randoms();
        double t1 = timeRandomInput(alg1, data, T);
        double t2 = timeRandomInput(alg2, data, T);

        StdOut.printf("For %d random Doubles\n      %s works at", data.length, alg1.text());
        StdOut.printf(" %.1f %% of %s\n", t2/t1 * 100, alg2.text());
    }
}