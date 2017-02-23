package jobinterview.sort;

import ekis.common.StringGrid;
import ekis.common.TestSupport;
import jobinterview.SortUtility;
import jobinterview.sort.mergesort.MyBottomUpMergeSort;
import jobinterview.sort.mergesort.MyTopDownMergeSort;
import jobinterview.sort.quicksort.MyQuickSelect;
import jobinterview.sort.quicksort.MyQuickSort;
import org.junit.Test;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public final class SortTest {

    private static final String[] EXAMPLE_1 = new String[]{"S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};
    private static final String[] EXPECTED_1 = new String[]{"A", "E", "E", "L", "M", "O", "P", "R", "S", "T", "X"};

    private static final String[] EXAMPLE_2 = new String[]{"M", "E", "R", "G", "E", "S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};
    private static final String[] EXPECTED_2 = new String[]{"A", "E", "E", "E", "E", "G", "L", "M", "M", "O", "P", "R", "R", "S", "T", "X"};

    private static final String[] EXAMPLE_3 = new String[]{"Q", "U", "I", "C", "K", "S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};
    private static final String[] EXPECTED_3 = new String[]{"A", "C", "E", "E", "I", "K", "L", "M", "O", "P", "Q", "R", "S", "T", "U", "X"};

    @Test
    public void testSelectionSort() {
        testAndSort(MySelection::sort);
    }

    @Test
    public void testInsertionSort() {
        testAndSort(MyInsertion::sort);
    }

    @Test
    public void testShellSort() {
        testAndSort(MyShell::sort);
    }

    @Test
    public void testShellViaArraySort() {
        testAndSort(MyShellViaArray::sort);
    }

    @Test
    public void testTopDownMergeSort() {
        testAndSort(MyTopDownMergeSort::sort);
    }

    @Test
    public void testBottomUpMergeSort() {
        testAndSort(MyBottomUpMergeSort::sort);
    }

    @Test
    public void testQuickSort() {
        testAndSort(MyQuickSort::sort);
    }

    @Test
    public void testQuickSelect() {
        String[] expectedGrid = new String[]{
                "                  Input Array                    |                                                           Actual Output [format: (index k -> array element)]                                                          ", //
                "                                                 |                                                                                                                                                                       ", //
                "       [S, O, R, T, E, X, A, M, P, L, E]         |                            [(0 -> A), (1 -> E), (2 -> E), (3 -> L), (4 -> M), (5 -> O), (6 -> P), (7 -> R), (8 -> S), (9 -> T), (10 -> X)]                            ", //
                "[M, E, R, G, E, S, O, R, T, E, X, A, M, P, L, E] | [(0 -> A), (1 -> E), (2 -> E), (3 -> E), (4 -> E), (5 -> G), (6 -> L), (7 -> M), (8 -> M), (9 -> O), (10 -> P), (11 -> R), (12 -> R), (13 -> S), (14 -> T), (15 -> X)]", //
                "[Q, U, I, C, K, S, O, R, T, E, X, A, M, P, L, E] | [(0 -> A), (1 -> C), (2 -> E), (3 -> E), (4 -> I), (5 -> K), (6 -> L), (7 -> M), (8 -> O), (9 -> P), (10 -> Q), (11 -> R), (12 -> S), (13 -> T), (14 -> U), (15 -> X)]" //
        };

        StringGrid grid = grid("Input Array", "Actual Output [format: (index k -> array element)]");

        populateGridRow(grid, SortTest::copyExample1, EXAMPLE_1);
        populateGridRow(grid, SortTest::copyExample2, EXAMPLE_2);
        populateGridRow(grid, SortTest::copyExample3, EXAMPLE_3);

        TestSupport.check(grid, expectedGrid);
    }

    private static void testAndSort(Consumer<String[]> sortTask) {
        String[] expectedGrid = new String[]{
                "                     Input                       |                     Expected                     |                      Actual                      | Is sorted?", //
                "                                                 |                                                  |                                                  |           ", //
                "       [S, O, R, T, E, X, A, M, P, L, E]         |        [A, E, E, L, M, O, P, R, S, T, X]         |        [A, E, E, L, M, O, P, R, S, T, X]         |    true   ", //
                "[M, E, R, G, E, S, O, R, T, E, X, A, M, P, L, E] | [A, E, E, E, E, G, L, M, M, O, P, R, R, S, T, X] | [A, E, E, E, E, G, L, M, M, O, P, R, R, S, T, X] |    true   ", //
                "[Q, U, I, C, K, S, O, R, T, E, X, A, M, P, L, E] | [A, C, E, E, I, K, L, M, O, P, Q, R, S, T, U, X] | [A, C, E, E, I, K, L, M, O, P, Q, R, S, T, U, X] |    true   " //
        };

        StringGrid grid = grid("Input", "Expected", "Actual", "Is sorted?");

        populateGridRow(grid, EXAMPLE_1, EXPECTED_1, SortTest::copyExample1, sortTask);
        populateGridRow(grid, EXAMPLE_2, EXPECTED_2, SortTest::copyExample2, sortTask);
        populateGridRow(grid, EXAMPLE_3, EXPECTED_3, SortTest::copyExample3, sortTask);

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

    private static void populateGridRow(StringGrid grid, String[] example, String[] expected, Supplier<String[]> actualSupplier, Consumer<String[]> sortTask) {
        String[] actual = actualSupplier.get();
        sortTask.accept(actual);
        grid.row(Arrays.toString(example), Arrays.toString(expected), Arrays.toString(actual), SortUtility.isSorted(actual));
    }

    private static void populateGridRow(StringGrid grid, Supplier<String[]> exampleSupplier, String[] input) {
        String[] example = exampleSupplier.get();
        String[] actual = IntStream.range(0, example.length).mapToObj(k -> String.format("(%s -> %s)", k, MyQuickSelect.select(example, k))).toArray(String[]::new);
        grid.row(Arrays.toString(input), Arrays.toString(actual));
    }

    private static StringGrid grid(String... columnNames) {
        StringGrid grid = new StringGrid();
        grid.row();
        grid.column((Object []) columnNames);
        grid.alignments(StringGrid.Alignment.CENTER, StringGrid.Alignment.CENTER, StringGrid.Alignment.CENTER, StringGrid.Alignment.CENTER);
        grid.row();
        return grid;
    }
}
