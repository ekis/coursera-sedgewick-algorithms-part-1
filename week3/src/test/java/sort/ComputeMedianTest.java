package sort;

import ekis.common.TestSupport;
import ekis.common.grid.TestGrid;
import org.junit.Test;
import sort.quicksort.MyQuick;
import sort.quicksort.MyQuickSelect;

import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import static sort.SortTestSupport.*;
import static util.SortUtility.lessF;

public final class ComputeMedianTest {

    @Test
    public void testMedianOf3() {
        String[] expected = new String[] {
                "                  Input Array                    | Median of 3 | Tukey's Ninther",
                "                                                 |             |                ",
                "       [S, O, R, T, E, X, A, M, P, L, E]         |      S      |        R       ",
                "[M, E, R, G, E, S, O, R, T, E, X, A, M, P, L, E] |      M      |        M       ",
                "[Q, U, I, C, K, S, O, R, T, E, X, A, M, P, L, E] |      Q      |        Q       ",
                "   [P, A, B, X, W, P, P, V, P, D, P, C, Y, Z]    |      P      |        A       "
        };

        TestGrid grid = grid("Input Array", "Median of 3", "Tukey's Ninther");
        populateGridRow(grid, EXAMPLE_1);
        populateGridRow(grid, EXAMPLE_2);
        populateGridRow(grid, EXAMPLE_3);
        populateGridRow(grid, SEDGEWICK_DEMO_EXAMPLE);

        TestSupport.check(expected, grid);
    }

    @Test
    public void testQuickSelect() {
        String[] expected = new String[]{
                "                  Input Array                    |                                                           Actual Output [format: (index k -> array element)]                                                           ",
                "                                                 |                                                                                                                                                                        ",
                "       [S, O, R, T, E, X, A, M, P, L, E]         |                            [(1 -> A), (2 -> E), (3 -> E), (4 -> L), (5 -> M), (6 -> O), (7 -> P), (8 -> R), (9 -> S), (10 -> T), (11 -> X)]                            ",
                "[M, E, R, G, E, S, O, R, T, E, X, A, M, P, L, E] | [(1 -> A), (2 -> E), (3 -> E), (4 -> E), (5 -> E), (6 -> G), (7 -> L), (8 -> M), (9 -> M), (10 -> O), (11 -> P), (12 -> R), (13 -> R), (14 -> S), (15 -> T), (16 -> X)]",
                "[Q, U, I, C, K, S, O, R, T, E, X, A, M, P, L, E] | [(1 -> A), (2 -> C), (3 -> E), (4 -> E), (5 -> I), (6 -> K), (7 -> L), (8 -> M), (9 -> O), (10 -> P), (11 -> Q), (12 -> R), (13 -> S), (14 -> T), (15 -> U), (16 -> X)]",
                "   [P, A, B, X, W, P, P, V, P, D, P, C, Y, Z]    |            [(1 -> A), (2 -> B), (3 -> C), (4 -> D), (5 -> P), (6 -> P), (7 -> P), (8 -> P), (9 -> P), (10 -> V), (11 -> W), (12 -> X), (13 -> Y), (14 -> Z)]           "
        };

        TestGrid grid = grid("Input Array", "Actual Output [format: (index k -> array element)]");

        populateGridRow(grid, SortTestSupport::copyExample1, EXAMPLE_1);
        populateGridRow(grid, SortTestSupport::copyExample2, EXAMPLE_2);
        populateGridRow(grid, SortTestSupport::copyExample3, EXAMPLE_3);
        populateGridRow(grid, SortTestSupport::copySedgewickExample, SEDGEWICK_DEMO_EXAMPLE);

        TestSupport.check(expected, grid);
    }

    @Test(expected = IllegalStateException.class)
    public void testQuickSelectFailLowerBound() {
        MyQuickSelect.select(EXAMPLE_1, 0);
    }

    @Test(expected = IllegalStateException.class)
    public void testQuickSelectFailUpperBound() {
        MyQuickSelect.select(EXAMPLE_1, 12);
    }

    private static void populateGridRow(TestGrid grid, Supplier<String[]> exampleSupplier, String[] input) {
        String[] example = exampleSupplier.get();
        String[] actual = IntStream.rangeClosed(1, example.length)
                .mapToObj(k -> String.format("(%s -> %s)", k, MyQuickSelect.select(example, k)))
                .toArray(String[]::new);
        grid.row(Arrays.toString(input), Arrays.toString(actual));
    }

    private static void populateGridRow(TestGrid grid, String[] input) {
        grid.row(Arrays.toString(input), input[MyQuick.medianOf3(input, lessF())], input[MyQuick.ninther(input, lessF())]);
    }
}
