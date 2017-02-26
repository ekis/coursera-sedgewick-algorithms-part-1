package jobinterview.sort;

import edu.princeton.cs.algs4.StdRandom;
import ekis.common.StringGrid;
import ekis.common.TestSupport;
import jobinterview.SortUtility;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;

import static jobinterview.sort.SortAlgorithm.*;
import static jobinterview.sort.SortTestSupport.*;


public final class SortAlgorithmTest {

    private static final int QUADRATIC_ELEMENT_COUNT = 15000;
    private static final int SUBQUADRATIC_ELEMENT_COUNT = 500000; // 5 * 10^5
    private static final int LINEARITHMIC_ELEMENT_COUNT = 1000000; // 10^6

    @Test
    public void testSelectionSort() {
        sortAndTest(SELECTION, QUADRATIC_ELEMENT_COUNT);
    }

    @Test
    public void testInsertionSort() {
        sortAndTest(INSERTION, QUADRATIC_ELEMENT_COUNT);
    }

    @Test
    public void testShellSort() {
        sortAndTest(SHELL, SUBQUADRATIC_ELEMENT_COUNT);
    }

    @Test
    public void testShellViaArraySort() {
        sortAndTest(SHELL_VIA_ARRAY, SUBQUADRATIC_ELEMENT_COUNT);
    }

    @Test
    public void testTopDownMergeSort() {
        sortAndTest(MERGE_TOP_DOWN, LINEARITHMIC_ELEMENT_COUNT);
    }

    @Test
    public void testBottomUpMergeSort() {
        sortAndTest(MERGE_BOTTOM_UP, LINEARITHMIC_ELEMENT_COUNT);
    }

    @Test
    public void testQuickSort() {
        sortAndTest(QUICK, LINEARITHMIC_ELEMENT_COUNT);
    }

    @Test
    public void testQuick3WaySort() {
        sortAndTest(QUICK_3_WAY, LINEARITHMIC_ELEMENT_COUNT);
    }

    @Test
    public void testQuickSort3WayEntropyOptimal() {
        sortAndTest(QUICK_3_WAY_ENTROPY_OPTIMAL, LINEARITHMIC_ELEMENT_COUNT);
    }

    private static void sortAndTest(SortAlgorithm algorithm, int randomTestSize) {
        String[] expectedGrid = new String[]{
                "                     Input                       |                     Expected                     |                      Actual                      | Is sorted?", //
                "                                                 |                                                  |                                                  |           ", //
                "       [S, O, R, T, E, X, A, M, P, L, E]         |        [A, E, E, L, M, O, P, R, S, T, X]         |        [A, E, E, L, M, O, P, R, S, T, X]         |    true   ", //
                "[M, E, R, G, E, S, O, R, T, E, X, A, M, P, L, E] | [A, E, E, E, E, G, L, M, M, O, P, R, R, S, T, X] | [A, E, E, E, E, G, L, M, M, O, P, R, R, S, T, X] |    true   ", //
                "[Q, U, I, C, K, S, O, R, T, E, X, A, M, P, L, E] | [A, C, E, E, I, K, L, M, O, P, Q, R, S, T, U, X] | [A, C, E, E, I, K, L, M, O, P, Q, R, S, T, U, X] |    true   ", //
                "   [P, A, B, X, W, P, P, V, P, D, P, C, Y, Z]    |    [A, B, C, D, P, P, P, P, P, V, W, X, Y, Z]    |    [A, B, C, D, P, P, P, P, P, V, W, X, Y, Z]    |    true   " //
        };

        StringGrid grid = grid("Input", "Expected", "Actual", "Is sorted?");

        populateGridRow(grid, EXAMPLE_1, EXPECTED_1, SortTestSupport::copyExample1, algorithm);
        populateGridRow(grid, EXAMPLE_2, EXPECTED_2, SortTestSupport::copyExample2, algorithm);
        populateGridRow(grid, EXAMPLE_3, EXPECTED_3, SortTestSupport::copyExample3, algorithm);
        populateGridRow(grid, SEDGEWICK_DEMO_EXAMPLE, SEDGEWICK_DEMO_EXPECTED, SortTestSupport::copySedgewickExample, algorithm);

        TestSupport.check(grid, expectedGrid);

        testRandomSort(algorithm, randomTestSize);
    }

    private static void testRandomSort(SortAlgorithm algorithm, int N) {
        Double[] doubles = DoubleStream.iterate(0, x -> StdRandom.uniform()).limit(N).boxed().toArray(Double[]::new);
        algorithm.sort(doubles);
        Assert.assertTrue(SortUtility.isSorted(doubles));
    }

    private static void populateGridRow(StringGrid grid, String[] example, String[] expected, Supplier<String[]> actualSupplier, SortAlgorithm algorithm) {
        String[] actual = actualSupplier.get();
        algorithm.sort(actual);
        grid.row(Arrays.toString(example), Arrays.toString(expected), Arrays.toString(actual), SortUtility.isSorted(actual));
    }
}