package sort;

import ekis.SortRandomData;
import ekis.common.grid.TestGrid;
import ekis.common.TestSupport;
import util.SortUtility;
import org.junit.Test;

import java.util.Arrays;
import java.util.function.Supplier;

import static ekis.SortRandomData.*;
import static sort.SortAlgorithm.*;
import static sort.SortTestSupport.*;

public final class SortAlgorithmTest {
    @Test
    public void testSelectionSort() {
        sortAndTest(SELECTION, QUADRATIC_SORT);
    }

    @Test
    public void testInsertionSort() {
        sortAndTest(INSERTION, QUADRATIC_SORT);
    }

    @Test
    public void testShellSort() {
        sortAndTest(SHELL, SUBQUADRATIC_SORT);
    }

    @Test
    public void testShellViaArraySort() {
        sortAndTest(SHELL_VIA_ARRAY, SUBQUADRATIC_SORT);
    }

    @Test
    public void testTopDownMergeSort() {
        sortAndTest(MERGE_TOP_DOWN, LINEARITHMIC_SORT);
    }

    @Test
    public void testBottomUpMergeSort() {
        sortAndTest(MERGE_BOTTOM_UP, LINEARITHMIC_SORT);
    }

    @Test
    public void testQuickSort() {
        sortAndTest(QUICK, LINEARITHMIC_SORT);
    }

    @Test
    public void testQuick3WaySort() {
        sortAndTest(QUICK_3_WAY, LINEARITHMIC_SORT);
    }

    @Test
    public void testQuickSort3WayEntropyOptimal() {
        sortAndTest(QUICK_3_WAY_ENTROPY_OPTIMAL, LINEARITHMIC_SORT);
    }

    @Test
    public void testHeapSort() {
        sortAndTest(HEAP, LINEARITHMIC_SORT);
    }

    private static void sortAndTest(SortAlgorithm algorithm, SortRandomData data) {
        String[] expected = new String[]{
                "                     Input                       |                     Expected                     |                      Actual                      | Is sorted?", //
                "                                                 |                                                  |                                                  |           ", //
                "       [S, O, R, T, E, X, A, M, P, L, E]         |        [A, E, E, L, M, O, P, R, S, T, X]         |        [A, E, E, L, M, O, P, R, S, T, X]         |    true   ", //
                "[M, E, R, G, E, S, O, R, T, E, X, A, M, P, L, E] | [A, E, E, E, E, G, L, M, M, O, P, R, R, S, T, X] | [A, E, E, E, E, G, L, M, M, O, P, R, R, S, T, X] |    true   ", //
                "[Q, U, I, C, K, S, O, R, T, E, X, A, M, P, L, E] | [A, C, E, E, I, K, L, M, O, P, Q, R, S, T, U, X] | [A, C, E, E, I, K, L, M, O, P, Q, R, S, T, U, X] |    true   ", //
                "   [P, A, B, X, W, P, P, V, P, D, P, C, Y, Z]    |    [A, B, C, D, P, P, P, P, P, V, W, X, Y, Z]    |    [A, B, C, D, P, P, P, P, P, V, W, X, Y, Z]    |    true   ", //
                "          Uniformly random data input            |        Sorted uniformly random data input        |        Sorted uniformly random data input        |    true   ", //
                "         Heavily duplicated data input           |                Sorted data input                 |                Sorted data input                 |    true   " //
        };

        TestGrid grid = grid("Input", "Expected", "Actual", "Is sorted?");

        populateGridRow(grid, EXAMPLE_1, EXPECTED_1, SortTestSupport::copyExample1, algorithm);
        populateGridRow(grid, EXAMPLE_2, EXPECTED_2, SortTestSupport::copyExample2, algorithm);
        populateGridRow(grid, EXAMPLE_3, EXPECTED_3, SortTestSupport::copyExample3, algorithm);
        populateGridRow(grid, SEDGEWICK_DEMO_EXAMPLE, SEDGEWICK_DEMO_EXPECTED, SortTestSupport::copySedgewickExample, algorithm);
        grid.row("Uniformly random data input", "Sorted uniformly random data input", "Sorted uniformly random data input", testRandomSort(algorithm, data.randoms()));
        grid.row("Heavily duplicated data input", "Sorted data input", "Sorted data input", testRandomSort(algorithm, data.duplicates()));

        TestSupport.check(expected, grid);
    }

    private static <T extends Comparable<? super T>> boolean testRandomSort(SortAlgorithm algorithm, T[] values) {
        algorithm.sort(values);
        return SortUtility.isSorted(values);
    }

    private static void populateGridRow(TestGrid grid, String[] example, String[] expected, Supplier<String[]> actualSupplier, SortAlgorithm algorithm) {
        String[] actual = actualSupplier.get();
        algorithm.sort(actual);
        grid.row(Arrays.toString(example), Arrays.toString(expected), Arrays.toString(actual), SortUtility.isSorted(actual));
    }
}