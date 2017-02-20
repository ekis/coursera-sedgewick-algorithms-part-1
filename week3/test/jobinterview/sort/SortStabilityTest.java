package jobinterview.sort;

import edu.princeton.cs.algs4.Insertion;
import edu.princeton.cs.algs4.Selection;
import ekis.common.StringGrid;
import ekis.common.TestSupport;
import jobinterview.sort.mergesort.MyBottomUpMergeSort;
import jobinterview.sort.mergesort.MyTopDownMergeSort;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.BiConsumer;
import java.util.stream.IntStream;

public final class SortStabilityTest {

    @Test
    public void testSelectionSortStability() {
        String[] expected = new String[] {
                "Unsorted                   | Expected                   | Actual                    ", //
                "                           |                            |                           ", //
                "(ID, Name) -> (3, Kanaga)  | (ID, Name) -> (1, Furia)   | (ID, Name) -> (1, Furia)  ", //
                "(ID, Name) -> (3, Chen)    | (ID, Name) -> (2, Rohde)   | (ID, Name) -> (2, Rohde)  ", //
                "(ID, Name) -> (4, Gazsi)   | (ID, Name) -> (3, Chen)    | (ID, Name) -> (3, Chen)   ", //
                "(ID, Name) -> (3, Fox)     | (ID, Name) -> (3, Fox)     | (ID, Name) -> (3, Fox)    ", //
                "(ID, Name) -> (2, Rohde)   | (ID, Name) -> (3, Andrews) | (ID, Name) -> (3, Andrews)", //
                "(ID, Name) -> (3, Andrews) | (ID, Name) -> (3, Kanaga)  | (ID, Name) -> (3, Kanaga) ", //
                "(ID, Name) -> (4, Battle)  | (ID, Name) -> (4, Gazsi)   | (ID, Name) -> (4, Gazsi)  ", //
                "(ID, Name) -> (1, Furia)   | (ID, Name) -> (4, Battle)  | (ID, Name) -> (4, Battle) " //
        };
        sortAndCheck(Selection::sort, MySelection::sort, expected);
    }

    @Test
    public void testInsertionSortStability() {
        sortAndCheck(Insertion::sort, MyInsertion::sort, stableSortExpectation());
    }

    @Test
    public void testShellSortStability() {
        String[] expected = new String[] {
                "Unsorted                   | Expected                   | Actual                    ", //
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
        sortAndCheck(Arrays::sort, MyShell::sort, expected); // no reference shell sort with comparators provided, using system sort as comparison
    }

    @Test
    public void testTopDownMergeSortStability() {
        sortAndCheck(Arrays::sort, MyTopDownMergeSort::sort, stableSortExpectation());
    }

    @Test
    public void testBottomUpMergeSortStability() {
        sortAndCheck(Arrays::sort, MyBottomUpMergeSort::sort, stableSortExpectation());
    }

    private static void sortAndCheck(BiConsumer<TestPair[], Comparator<TestPair>> referenceSortTask, BiConsumer<TestPair[], Comparator<TestPair>> mySortTask, String[] expectedGrid) {
        StringGrid grid = initialiseGrid();

        TestPair[] unsorted = unsorted();
        TestPair[] expected = unsorted();
        TestPair[] actual = unsorted();

        referenceSortTask.accept(expected, TestPair.BY_NAME);
        referenceSortTask.accept(expected, TestPair.BY_ID);
        mySortTask.accept(actual, TestPair.BY_NAME);
        mySortTask.accept(actual, TestPair.BY_ID);

        IntStream.range(0, 8).forEach(i -> grid.row(unsorted[i], expected[i], actual[i]));
        TestSupport.check(grid, expectedGrid);
    }

    private static String[] stableSortExpectation() {
        return new String[]{
                "Unsorted                   | Expected                   | Actual                    ", //
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
        grid.column("Unsorted", "Expected", "Actual");
        grid.alignments(StringGrid.Alignment.LEFT, StringGrid.Alignment.LEFT, StringGrid.Alignment.LEFT);
        grid.row();
        return grid;
    }

    private static TestPair[] unsorted() {
        TestPair[] testPairs = new TestPair[8];
        testPairs[0] = new TestPair(3, "Kanaga");
        testPairs[1] = new TestPair(3, "Chen");
        testPairs[2] = new TestPair(4, "Gazsi");
        testPairs[3] = new TestPair(3, "Fox");
        testPairs[4] = new TestPair(2, "Rohde");
        testPairs[5] = new TestPair(3, "Andrews");
        testPairs[6] = new TestPair(4, "Battle");
        testPairs[7] = new TestPair(1, "Furia");
        return testPairs;
    }

    private static class TestPair {
        private static final Comparator<TestPair> BY_ID = Comparator.comparingInt(p -> p.id);
        private static final Comparator<TestPair> BY_NAME = Comparator.comparing(p -> p.lastName);

        private final int id;
        private final String lastName;

        private TestPair(int a, String b) {
            id = a;
            lastName = b;
        }

        @Override
        public String toString() {
            return String.format("(ID, Name) -> (%s, %s)", id, lastName);
        }
    }
}