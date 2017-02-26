package jobinterview.sort;

import ekis.common.StringGrid;
import ekis.common.TestSupport;
import jobinterview.sort.quicksort.MyQuickSelect;
import org.junit.Test;

import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import static jobinterview.sort.SortTestSupport.*;

public class ComputeMedianTest {

    @Test
    public void testQuickSelect() {
        String[] expectedGrid = new String[]{
                "                  Input Array                    |                                                           Actual Output [format: (index k -> array element)]                                                          ", //
                "                                                 |                                                                                                                                                                       ", //
                "       [S, O, R, T, E, X, A, M, P, L, E]         |                            [(0 -> A), (1 -> E), (2 -> E), (3 -> L), (4 -> M), (5 -> O), (6 -> P), (7 -> R), (8 -> S), (9 -> T), (10 -> X)]                            ", //
                "[M, E, R, G, E, S, O, R, T, E, X, A, M, P, L, E] | [(0 -> A), (1 -> E), (2 -> E), (3 -> E), (4 -> E), (5 -> G), (6 -> L), (7 -> M), (8 -> M), (9 -> O), (10 -> P), (11 -> R), (12 -> R), (13 -> S), (14 -> T), (15 -> X)]", //
                "[Q, U, I, C, K, S, O, R, T, E, X, A, M, P, L, E] | [(0 -> A), (1 -> C), (2 -> E), (3 -> E), (4 -> I), (5 -> K), (6 -> L), (7 -> M), (8 -> O), (9 -> P), (10 -> Q), (11 -> R), (12 -> S), (13 -> T), (14 -> U), (15 -> X)]", //
                "   [P, A, B, X, W, P, P, V, P, D, P, C, Y, Z]    |            [(0 -> A), (1 -> B), (2 -> C), (3 -> D), (4 -> P), (5 -> P), (6 -> P), (7 -> P), (8 -> P), (9 -> V), (10 -> W), (11 -> X), (12 -> Y), (13 -> Z)]           " //
        };

        StringGrid grid = grid("Input Array", "Actual Output [format: (index k -> array element)]");

        populateGridRow(grid, SortTestSupport::copyExample1, EXAMPLE_1);
        populateGridRow(grid, SortTestSupport::copyExample2, EXAMPLE_2);
        populateGridRow(grid, SortTestSupport::copyExample3, EXAMPLE_3);
        populateGridRow(grid, SortTestSupport::copySedgewickExample, SEDGEWICK_DEMO_EXAMPLE);

        TestSupport.check(grid, expectedGrid);
    }

    private static void populateGridRow(StringGrid grid, Supplier<String[]> exampleSupplier, String[] input) {
        String[] example = exampleSupplier.get();
        String[] actual = IntStream.range(0, example.length).mapToObj(k -> String.format("(%s -> %s)", k, MyQuickSelect.select(example, k))).toArray(String[]::new);
        grid.row(Arrays.toString(input), Arrays.toString(actual));
    }
}
