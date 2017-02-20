package jobinterview.sort;

import ekis.common.StringGrid;
import ekis.common.TestSupport;
import jobinterview.sort.mergesort.MyBottomUpMergeSort;
import jobinterview.sort.mergesort.MyTopDownMergeSort;
import org.junit.Test;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntUnaryOperator;
import java.util.function.Supplier;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertArrayEquals;

public final class SortTest {

    private static final String[] EXAMPLE_1 = new String[]{"S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};
    private static final String[] EXPECTED_1 = new String[]{"A", "E", "E", "L", "M", "O", "P", "R", "S", "T", "X"};

    private static final String[] EXAMPLE_2 = new String[]{"M", "E", "R", "G", "E", "S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};
    private static final String[] EXPECTED_2 = new String[]{"A", "E", "E", "E", "E", "G", "L", "M", "M", "O", "P", "R", "R", "S", "T", "X"};

    private static final String[] EXAMPLE_3 = new String[]{"Q", "U", "I", "C", "K", "S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};
    private static final String[] EXPECTED_3 = new String[]{"A", "C", "E", "E", "I", "K", "L", "M", "O", "P", "Q", "R", "S", "T", "U", "X"};

    @Test
    public void testSelectionSort() {
        testAndSort(MySelection::sort, MySelection::isSorted);
    }

    @Test
    public void testInsertionSort() {
        testAndSort(MyInsertion::sort, MyInsertion::isSorted);
    }

    @Test
    public void testShellSort() {
        testAndSort(MyShell::sort, MyShell::isSorted);
    }

    @Test
    public void testShellViaArraySort() {
        testAndSort(MyShellViaArray::sort, MyShell::isSorted);
    }

    @Test
    public void testTopDownMergeSort() {
        testAndSort(MyTopDownMergeSort::sort, MyTopDownMergeSort::isSorted);
    }

    @Test
    public void testBottomUpMergeSort() {
        testAndSort(MyBottomUpMergeSort::sort, MyBottomUpMergeSort::isSorted);
    }

    private static void testAndSort(Consumer<String[]> sortTask, Function<String[], Boolean> isSortedTask) {
        String[] expectedGrid = new String[]{
                "                     Input                       |                     Expected                     |                      Actual                      | Is sorted?", //
                "                                                 |                                                  |                                                  |           ", //
                "       [S, O, R, T, E, X, A, M, P, L, E]         |        [A, E, E, L, M, O, P, R, S, T, X]         |        [A, E, E, L, M, O, P, R, S, T, X]         |    true   ", //
                "[M, E, R, G, E, S, O, R, T, E, X, A, M, P, L, E] | [A, E, E, E, E, G, L, M, M, O, P, R, R, S, T, X] | [A, E, E, E, E, G, L, M, M, O, P, R, R, S, T, X] |    true   ", //
                "[Q, U, I, C, K, S, O, R, T, E, X, A, M, P, L, E] | [A, C, E, E, I, K, L, M, O, P, Q, R, S, T, U, X] | [A, C, E, E, I, K, L, M, O, P, Q, R, S, T, U, X] |    true   " //
        };

        StringGrid grid = grid();

        String[] actual = copyExample1();
        sortTask.accept(actual);
        populateRow(grid, EXAMPLE_1, EXPECTED_1, actual, isSortedTask.apply(actual));

        actual = copyExample2();
        sortTask.accept(actual);

        populateRow(grid, EXAMPLE_2, EXPECTED_2, actual, isSortedTask.apply(actual));

        actual = copyExample3();
        sortTask.accept(actual);
        populateRow(grid, EXAMPLE_3, EXPECTED_3, actual, isSortedTask.apply(actual));
        TestSupport.check(grid, expectedGrid);
    }

    private static String[] copyExample1() {
        return copyOf(EXAMPLE_1);
    }

    private static String[] copyExample2() {
        return copyOf(EXAMPLE_2);
    }

    private static String[] copyExample3() {
        return copyOf(EXAMPLE_3);
    }

    private static String[] copyOf(String[] array) {
        return Arrays.copyOf(array, array.length);
    }

    private static void populateRow(StringGrid grid, String[] exampleColVal, String[] expectedColVal, String[] actualColVal, boolean isSortedColVal) {
        grid.row(Arrays.toString(exampleColVal), Arrays.toString(expectedColVal), Arrays.toString(actualColVal), isSortedColVal);
    }

    private static StringGrid grid() {
        StringGrid grid = new StringGrid();
        grid.row();
        grid.column("Input", "Expected", "Actual", "Is sorted?");
        grid.alignments(StringGrid.Alignment.CENTER, StringGrid.Alignment.CENTER, StringGrid.Alignment.CENTER, StringGrid.Alignment.CENTER);
        grid.row();
        return grid;
    }
}
