package jobinterview.sort;

import ekis.common.Pair;
import ekis.common.StringGrid;
import ekis.common.TestSupport;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

import static jobinterview.sort.SortAlgorithm.*;

public final class SortStabilityTest {
    private static final Comparator<Pair<Integer, String>> BY_ID = Comparator.comparingInt(Pair::x);
    private static final Comparator<Pair<Integer, String>> BY_NAME = Comparator.comparing(Pair::y);

    @Test
    public void testSelectionSortStability() {
        String[] expected = new String[] {
                "Unsorted input             | System sort output         | Actual output             ", //
                "                           |                            |                           ", //
                "(ID, Name) -> (3, Kanaga)  | (ID, Name) -> (1, Furia)   | (ID, Name) -> (1, Furia)  ", //
                "(ID, Name) -> (3, Chen)    | (ID, Name) -> (2, Rohde)   | (ID, Name) -> (2, Rohde)  ", //
                "(ID, Name) -> (4, Gazsi)   | (ID, Name) -> (3, Andrews) | (ID, Name) -> (3, Chen)   ", //
                "(ID, Name) -> (3, Fox)     | (ID, Name) -> (3, Chen)    | (ID, Name) -> (3, Fox)    ", //
                "(ID, Name) -> (2, Rohde)   | (ID, Name) -> (3, Fox)     | (ID, Name) -> (3, Andrews)", //
                "(ID, Name) -> (3, Andrews) | (ID, Name) -> (3, Kanaga)  | (ID, Name) -> (3, Kanaga) ", //
                "(ID, Name) -> (4, Battle)  | (ID, Name) -> (4, Battle)  | (ID, Name) -> (4, Gazsi)  ", //
                "(ID, Name) -> (1, Furia)   | (ID, Name) -> (4, Gazsi)   | (ID, Name) -> (4, Battle) " //
        };
        sortAndCheck(SELECTION, expected);
    }

    @Test
    public void testInsertionSortStability() {
        sortAndCheck(INSERTION, stableSortExpectation());
    }

    @Test
    public void testShellSortStability() {
        String[] expected = new String[] {
                "Unsorted input             | System sort output         | Actual output             ", //
                "                           |                            |                           ", //
                "(ID, Name) -> (3, Kanaga)  | (ID, Name) -> (1, Furia)   | (ID, Name) -> (1, Furia)  ", //
                "(ID, Name) -> (3, Chen)    | (ID, Name) -> (2, Rohde)   | (ID, Name) -> (2, Rohde)  ", //
                "(ID, Name) -> (4, Gazsi)   | (ID, Name) -> (3, Andrews) | (ID, Name) -> (3, Chen)   ", //
                "(ID, Name) -> (3, Fox)     | (ID, Name) -> (3, Chen)    | (ID, Name) -> (3, Andrews)", //
                "(ID, Name) -> (2, Rohde)   | (ID, Name) -> (3, Fox)     | (ID, Name) -> (3, Kanaga) ", //
                "(ID, Name) -> (3, Andrews) | (ID, Name) -> (3, Kanaga)  | (ID, Name) -> (3, Fox)    ", //
                "(ID, Name) -> (4, Battle)  | (ID, Name) -> (4, Battle)  | (ID, Name) -> (4, Battle) ", //
                "(ID, Name) -> (1, Furia)   | (ID, Name) -> (4, Gazsi)   | (ID, Name) -> (4, Gazsi)  " //
        };
        sortAndCheck(SHELL, expected);
    }

    @Test
    public void testShellSortViaArrayStability() {
        String[] expected = new String[] {
                "Unsorted input             | System sort output         | Actual output             ", //
                "                           |                            |                           ", //
                "(ID, Name) -> (3, Kanaga)  | (ID, Name) -> (1, Furia)   | (ID, Name) -> (1, Furia)  ", //
                "(ID, Name) -> (3, Chen)    | (ID, Name) -> (2, Rohde)   | (ID, Name) -> (2, Rohde)  ", //
                "(ID, Name) -> (4, Gazsi)   | (ID, Name) -> (3, Andrews) | (ID, Name) -> (3, Chen)   ", //
                "(ID, Name) -> (3, Fox)     | (ID, Name) -> (3, Chen)    | (ID, Name) -> (3, Andrews)", //
                "(ID, Name) -> (2, Rohde)   | (ID, Name) -> (3, Fox)     | (ID, Name) -> (3, Kanaga) ", //
                "(ID, Name) -> (3, Andrews) | (ID, Name) -> (3, Kanaga)  | (ID, Name) -> (3, Fox)    ", //
                "(ID, Name) -> (4, Battle)  | (ID, Name) -> (4, Battle)  | (ID, Name) -> (4, Battle) ", //
                "(ID, Name) -> (1, Furia)   | (ID, Name) -> (4, Gazsi)   | (ID, Name) -> (4, Gazsi)  " //
        };
        sortAndCheck(SHELL_VIA_ARRAY, expected);
    }

    @Test
    public void testTopDownMergeSortStability() {
        sortAndCheck(MERGE_TOP_DOWN, stableSortExpectation());
    }

    @Test
    public void testBottomUpMergeSortStability() {
        sortAndCheck(MERGE_BOTTOM_UP, stableSortExpectation());
    }

    @Test
    public void testQuickSortStability() {
        //sortAndCheck(Arrays::sort, MyQuickSort::sort, stableSortExpectation());
        // a placeholder for future quick sort stability test, should I think of any
        // this version of quick sort is unstable (as it does an in-place sort) and is very hard to test
        // as it relies on initial random shuffle of the input array to make a probabilistic worst-case complexity guarantee of O(NlogN),
        // thus making expected values' test prediction useless (it is possible - though not probable - that the output
        // array will end up sorted in stable sort order, entirely by accident, which makes conclusions relying on
    }

    private static void sortAndCheck(SortAlgorithm algorithm, String[] expectedGrid) {
        StringGrid grid = initialiseGrid();

        Pair<Integer, String>[] unsorted = unsorted();
        Pair<Integer, String>[] expected = unsorted();
        Pair<Integer, String>[] actual = unsorted();

        Arrays.sort(expected, BY_NAME);
        Arrays.sort(expected, BY_ID);
        algorithm.sort(actual, BY_NAME);
        algorithm.sort(actual, BY_ID);

        IntStream.range(0, 8).forEach(i -> grid.row(format(unsorted[i]), format(expected[i]), format(actual[i])));
        TestSupport.check(grid, expectedGrid);
    }

    private static String format(Pair<Integer, String> pair) {
        return String.format("(ID, Name) -> (%s, %s)", pair.x(), pair.y());
    }

    private static String[] stableSortExpectation() {
        return new String[]{
                "Unsorted input             | System sort output         | Actual output             ", //
                "                           |                            |                           ", //
                "(ID, Name) -> (3, Kanaga)  | (ID, Name) -> (1, Furia)   | (ID, Name) -> (1, Furia)  ", //
                "(ID, Name) -> (3, Chen)    | (ID, Name) -> (2, Rohde)   | (ID, Name) -> (2, Rohde)  ", //
                "(ID, Name) -> (4, Gazsi)   | (ID, Name) -> (3, Andrews) | (ID, Name) -> (3, Andrews)", //
                "(ID, Name) -> (3, Fox)     | (ID, Name) -> (3, Chen)    | (ID, Name) -> (3, Chen)   ", //
                "(ID, Name) -> (2, Rohde)   | (ID, Name) -> (3, Fox)     | (ID, Name) -> (3, Fox)    ", //
                "(ID, Name) -> (3, Andrews) | (ID, Name) -> (3, Kanaga)  | (ID, Name) -> (3, Kanaga) ", //
                "(ID, Name) -> (4, Battle)  | (ID, Name) -> (4, Battle)  | (ID, Name) -> (4, Battle) ", //
                "(ID, Name) -> (1, Furia)   | (ID, Name) -> (4, Gazsi)   | (ID, Name) -> (4, Gazsi)  " //
        };
    }

    private static StringGrid initialiseGrid() {
        StringGrid grid = new StringGrid();
        grid.row();
        grid.column("Unsorted input", "System sort output", "Actual output");
        grid.alignments(StringGrid.Alignment.LEFT, StringGrid.Alignment.LEFT, StringGrid.Alignment.LEFT);
        grid.row();
        return grid;
    }

    @SuppressWarnings("unchecked")
    private static Pair<Integer, String>[] unsorted() {
        List<Pair<Integer, String>> pairs = new ArrayList<>();
        pairs.add(Pair.of(3, "Kanaga"));
        pairs.add(Pair.of(3, "Chen"));
        pairs.add(Pair.of(4, "Gazsi"));
        pairs.add(Pair.of(3, "Fox"));
        pairs.add(Pair.of(2, "Rohde"));
        pairs.add(Pair.of(3, "Andrews"));
        pairs.add(Pair.of(4, "Battle"));
        pairs.add(Pair.of(1, "Furia"));
        return pairs.toArray(new Pair[pairs.size()]);
    }
}