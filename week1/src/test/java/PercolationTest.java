import ekis.common.grid.TestGrid;
import ekis.common.TestSupport;
import org.junit.Test;

import java.util.function.Predicate;

import static org.junit.Assert.assertTrue;

public class PercolationTest {

    @Test
    public void testInitialState() {
        String[] expected = new String[]{
                "(1, 1) ->  "
        };
        testInitial(1, expected);

        expected = new String[]{
                "(1, 1) ->   | (1, 2) ->  ",
                "(2, 1) ->   | (2, 2) ->  "
        };
        testInitial(2, expected);

        expected = new String[]{
                "(1, 1) ->   | (1, 2) ->   | (1, 3) ->  ",
                "(2, 1) ->   | (2, 2) ->   | (2, 3) ->  ",
                "(3, 1) ->   | (3, 2) ->   | (3, 3) ->  "
        };
        testInitial(3, expected);

        expected = new String[]{
                "(1, 1) ->   | (1, 2) ->   | (1, 3) ->   | (1, 4) ->  ",
                "(2, 1) ->   | (2, 2) ->   | (2, 3) ->   | (2, 4) ->  ",
                "(3, 1) ->   | (3, 2) ->   | (3, 3) ->   | (3, 4) ->  ",
                "(4, 1) ->   | (4, 2) ->   | (4, 3) ->   | (4, 4) ->  "
        };
        testInitial(4, expected);

        expected = new String[]{
                "(1, 1) ->   | (1, 2) ->   | (1, 3) ->   | (1, 4) ->   | (1, 5) ->  ",
                "(2, 1) ->   | (2, 2) ->   | (2, 3) ->   | (2, 4) ->   | (2, 5) ->  ",
                "(3, 1) ->   | (3, 2) ->   | (3, 3) ->   | (3, 4) ->   | (3, 5) ->  ",
                "(4, 1) ->   | (4, 2) ->   | (4, 3) ->   | (4, 4) ->   | (4, 5) ->  ",
                "(5, 1) ->   | (5, 2) ->   | (5, 3) ->   | (5, 4) ->   | (5, 5) ->  "
        };
        testInitial(5, expected);

        expected = new String[]{
                "(1, 1) ->    | (1, 2) ->    | (1, 3) ->    | (1, 4) ->    | (1, 5) ->    | (1, 6) ->    | (1, 7) ->    | (1, 8) ->    | (1, 9) ->    | (1, 10) ->   ",
                "(2, 1) ->    | (2, 2) ->    | (2, 3) ->    | (2, 4) ->    | (2, 5) ->    | (2, 6) ->    | (2, 7) ->    | (2, 8) ->    | (2, 9) ->    | (2, 10) ->   ",
                "(3, 1) ->    | (3, 2) ->    | (3, 3) ->    | (3, 4) ->    | (3, 5) ->    | (3, 6) ->    | (3, 7) ->    | (3, 8) ->    | (3, 9) ->    | (3, 10) ->   ",
                "(4, 1) ->    | (4, 2) ->    | (4, 3) ->    | (4, 4) ->    | (4, 5) ->    | (4, 6) ->    | (4, 7) ->    | (4, 8) ->    | (4, 9) ->    | (4, 10) ->   ",
                "(5, 1) ->    | (5, 2) ->    | (5, 3) ->    | (5, 4) ->    | (5, 5) ->    | (5, 6) ->    | (5, 7) ->    | (5, 8) ->    | (5, 9) ->    | (5, 10) ->   ",
                "(6, 1) ->    | (6, 2) ->    | (6, 3) ->    | (6, 4) ->    | (6, 5) ->    | (6, 6) ->    | (6, 7) ->    | (6, 8) ->    | (6, 9) ->    | (6, 10) ->   ",
                "(7, 1) ->    | (7, 2) ->    | (7, 3) ->    | (7, 4) ->    | (7, 5) ->    | (7, 6) ->    | (7, 7) ->    | (7, 8) ->    | (7, 9) ->    | (7, 10) ->   ",
                "(8, 1) ->    | (8, 2) ->    | (8, 3) ->    | (8, 4) ->    | (8, 5) ->    | (8, 6) ->    | (8, 7) ->    | (8, 8) ->    | (8, 9) ->    | (8, 10) ->   ",
                "(9, 1) ->    | (9, 2) ->    | (9, 3) ->    | (9, 4) ->    | (9, 5) ->    | (9, 6) ->    | (9, 7) ->    | (9, 8) ->    | (9, 9) ->    | (9, 10) ->   ",
                "(10, 1) ->   | (10, 2) ->   | (10, 3) ->   | (10, 4) ->   | (10, 5) ->   | (10, 6) ->   | (10, 7) ->   | (10, 8) ->   | (10, 9) ->   | (10, 10) ->  "
        };
        testInitial(10, expected);

        expected = new String[]{
                "(1, 1) ->    | (1, 2) ->    | (1, 3) ->    | (1, 4) ->    | (1, 5) ->    | (1, 6) ->    | (1, 7) ->    | (1, 8) ->    | (1, 9) ->    | (1, 10) ->    | (1, 11) ->    | (1, 12) ->    | (1, 13) ->    | (1, 14) ->    | (1, 15) ->   ",
                "(2, 1) ->    | (2, 2) ->    | (2, 3) ->    | (2, 4) ->    | (2, 5) ->    | (2, 6) ->    | (2, 7) ->    | (2, 8) ->    | (2, 9) ->    | (2, 10) ->    | (2, 11) ->    | (2, 12) ->    | (2, 13) ->    | (2, 14) ->    | (2, 15) ->   ",
                "(3, 1) ->    | (3, 2) ->    | (3, 3) ->    | (3, 4) ->    | (3, 5) ->    | (3, 6) ->    | (3, 7) ->    | (3, 8) ->    | (3, 9) ->    | (3, 10) ->    | (3, 11) ->    | (3, 12) ->    | (3, 13) ->    | (3, 14) ->    | (3, 15) ->   ",
                "(4, 1) ->    | (4, 2) ->    | (4, 3) ->    | (4, 4) ->    | (4, 5) ->    | (4, 6) ->    | (4, 7) ->    | (4, 8) ->    | (4, 9) ->    | (4, 10) ->    | (4, 11) ->    | (4, 12) ->    | (4, 13) ->    | (4, 14) ->    | (4, 15) ->   ",
                "(5, 1) ->    | (5, 2) ->    | (5, 3) ->    | (5, 4) ->    | (5, 5) ->    | (5, 6) ->    | (5, 7) ->    | (5, 8) ->    | (5, 9) ->    | (5, 10) ->    | (5, 11) ->    | (5, 12) ->    | (5, 13) ->    | (5, 14) ->    | (5, 15) ->   ",
                "(6, 1) ->    | (6, 2) ->    | (6, 3) ->    | (6, 4) ->    | (6, 5) ->    | (6, 6) ->    | (6, 7) ->    | (6, 8) ->    | (6, 9) ->    | (6, 10) ->    | (6, 11) ->    | (6, 12) ->    | (6, 13) ->    | (6, 14) ->    | (6, 15) ->   ",
                "(7, 1) ->    | (7, 2) ->    | (7, 3) ->    | (7, 4) ->    | (7, 5) ->    | (7, 6) ->    | (7, 7) ->    | (7, 8) ->    | (7, 9) ->    | (7, 10) ->    | (7, 11) ->    | (7, 12) ->    | (7, 13) ->    | (7, 14) ->    | (7, 15) ->   ",
                "(8, 1) ->    | (8, 2) ->    | (8, 3) ->    | (8, 4) ->    | (8, 5) ->    | (8, 6) ->    | (8, 7) ->    | (8, 8) ->    | (8, 9) ->    | (8, 10) ->    | (8, 11) ->    | (8, 12) ->    | (8, 13) ->    | (8, 14) ->    | (8, 15) ->   ",
                "(9, 1) ->    | (9, 2) ->    | (9, 3) ->    | (9, 4) ->    | (9, 5) ->    | (9, 6) ->    | (9, 7) ->    | (9, 8) ->    | (9, 9) ->    | (9, 10) ->    | (9, 11) ->    | (9, 12) ->    | (9, 13) ->    | (9, 14) ->    | (9, 15) ->   ",
                "(10, 1) ->   | (10, 2) ->   | (10, 3) ->   | (10, 4) ->   | (10, 5) ->   | (10, 6) ->   | (10, 7) ->   | (10, 8) ->   | (10, 9) ->   | (10, 10) ->   | (10, 11) ->   | (10, 12) ->   | (10, 13) ->   | (10, 14) ->   | (10, 15) ->  ",
                "(11, 1) ->   | (11, 2) ->   | (11, 3) ->   | (11, 4) ->   | (11, 5) ->   | (11, 6) ->   | (11, 7) ->   | (11, 8) ->   | (11, 9) ->   | (11, 10) ->   | (11, 11) ->   | (11, 12) ->   | (11, 13) ->   | (11, 14) ->   | (11, 15) ->  ",
                "(12, 1) ->   | (12, 2) ->   | (12, 3) ->   | (12, 4) ->   | (12, 5) ->   | (12, 6) ->   | (12, 7) ->   | (12, 8) ->   | (12, 9) ->   | (12, 10) ->   | (12, 11) ->   | (12, 12) ->   | (12, 13) ->   | (12, 14) ->   | (12, 15) ->  ",
                "(13, 1) ->   | (13, 2) ->   | (13, 3) ->   | (13, 4) ->   | (13, 5) ->   | (13, 6) ->   | (13, 7) ->   | (13, 8) ->   | (13, 9) ->   | (13, 10) ->   | (13, 11) ->   | (13, 12) ->   | (13, 13) ->   | (13, 14) ->   | (13, 15) ->  ",
                "(14, 1) ->   | (14, 2) ->   | (14, 3) ->   | (14, 4) ->   | (14, 5) ->   | (14, 6) ->   | (14, 7) ->   | (14, 8) ->   | (14, 9) ->   | (14, 10) ->   | (14, 11) ->   | (14, 12) ->   | (14, 13) ->   | (14, 14) ->   | (14, 15) ->  ",
                "(15, 1) ->   | (15, 2) ->   | (15, 3) ->   | (15, 4) ->   | (15, 5) ->   | (15, 6) ->   | (15, 7) ->   | (15, 8) ->   | (15, 9) ->   | (15, 10) ->   | (15, 11) ->   | (15, 12) ->   | (15, 13) ->   | (15, 14) ->   | (15, 15) ->  "
        };
        testInitial(15, expected);
    }

    @Test
    public void testOpenRankThree() {
        int rank = 3;
        Percolation p = new Percolation(rank);

        String[] expected = new String[]{
                "(1, 1) ->   | (1, 2) ->   | (1, 3) ->  ",
                "(2, 1) ->   | (2, 2) ->   | (2, 3) ->  ",
                "(3, 1) ->   | (3, 2) ->   | (3, 3) ->  "
        };
        assertDoesNotPercolate(rank, p, expected);

        p.open(1, 3);
        expected = new String[]{
                "(1, 1) ->   | (1, 2) ->   | (1, 3) -> Y",
                "(2, 1) ->   | (2, 2) ->   | (2, 3) ->  ",
                "(3, 1) ->   | (3, 2) ->   | (3, 3) ->  "
        };
        assertDoesNotPercolate(rank, p, expected);

        p.open(1, 3);
        expected = new String[]{
                "(1, 1) ->   | (1, 2) ->   | (1, 3) -> Y",
                "(2, 1) ->   | (2, 2) ->   | (2, 3) ->  ",
                "(3, 1) ->   | (3, 2) ->   | (3, 3) ->  "
        };
        assertDoesNotPercolate(rank, p, expected);

        p.open(2, 2);
        expected = new String[]{
                "(1, 1) ->   | (1, 2) ->   | (1, 3) -> Y",
                "(2, 1) ->   | (2, 2) -> Y | (2, 3) ->  ",
                "(3, 1) ->   | (3, 2) ->   | (3, 3) ->  "
        };
        assertDoesNotPercolate(rank, p, expected);

        p.open(3, 1);
        expected = new String[]{
                "(1, 1) ->   | (1, 2) ->   | (1, 3) -> Y",
                "(2, 1) ->   | (2, 2) -> Y | (2, 3) ->  ",
                "(3, 1) -> Y | (3, 2) ->   | (3, 3) ->  "
        };
        assertDoesNotPercolate(rank, p, expected);

        p.open(3, 3);
        expected = new String[]{
                "(1, 1) ->   | (1, 2) ->   | (1, 3) -> Y",
                "(2, 1) ->   | (2, 2) -> Y | (2, 3) ->  ",
                "(3, 1) -> Y | (3, 2) ->   | (3, 3) -> Y"
        };
        assertDoesNotPercolate(rank, p, expected);

        p.open(2, 1);
        expected = new String[]{
                "(1, 1) ->   | (1, 2) ->   | (1, 3) -> Y",
                "(2, 1) -> Y | (2, 2) -> Y | (2, 3) ->  ",
                "(3, 1) -> Y | (3, 2) ->   | (3, 3) -> Y"
        };
        assertDoesNotPercolate(rank, p, expected);

        p.open(1, 1);
        expected = new String[]{
                "(1, 1) -> Y | (1, 2) ->   | (1, 3) -> Y",
                "(2, 1) -> Y | (2, 2) -> Y | (2, 3) ->  ",
                "(3, 1) -> Y | (3, 2) ->   | (3, 3) -> Y"
        };
        assertPercolates(rank, p, expected);
    }

    @Test
    public void testOpenRankFour() {
        int rank = 4;
        Percolation p = new Percolation(rank);

        String[] expected = new String[]{
                "(1, 1) ->   | (1, 2) ->   | (1, 3) ->   | (1, 4) ->  ",
                "(2, 1) ->   | (2, 2) ->   | (2, 3) ->   | (2, 4) ->  ",
                "(3, 1) ->   | (3, 2) ->   | (3, 3) ->   | (3, 4) ->  ",
                "(4, 1) ->   | (4, 2) ->   | (4, 3) ->   | (4, 4) ->  "
        };
        assertDoesNotPercolate(rank, p, expected);
        assertTrue(!p.isFull(1, 2));
        assertTrue(!p.isFull(2, 1));
        assertTrue(!p.isFull(2, 2));
        assertTrue(!p.isFull(2, 3));
        assertTrue(!p.isFull(3, 1));
        assertTrue(!p.isFull(3, 4));
        assertTrue(!p.isFull(4, 1));
        assertTrue(!p.isFull(4, 3));

        p.open(3, 4);
        expected = new String[]{
                "(1, 1) ->   | (1, 2) ->   | (1, 3) ->   | (1, 4) ->  ",
                "(2, 1) ->   | (2, 2) ->   | (2, 3) ->   | (2, 4) ->  ",
                "(3, 1) ->   | (3, 2) ->   | (3, 3) ->   | (3, 4) -> Y",
                "(4, 1) ->   | (4, 2) ->   | (4, 3) ->   | (4, 4) ->  "
        };
        assertDoesNotPercolate(rank, p, expected);
        assertTrue(!p.isFull(1, 2));
        assertTrue(!p.isFull(2, 1));
        assertTrue(!p.isFull(2, 2));
        assertTrue(!p.isFull(2, 3));
        assertTrue(!p.isFull(3, 1));
        assertTrue(!p.isFull(3, 4));
        assertTrue(!p.isFull(4, 1));
        assertTrue(!p.isFull(4, 3));

        p.open(2, 1);
        expected = new String[]{
                "(1, 1) ->   | (1, 2) ->   | (1, 3) ->   | (1, 4) ->  ",
                "(2, 1) -> Y | (2, 2) ->   | (2, 3) ->   | (2, 4) ->  ",
                "(3, 1) ->   | (3, 2) ->   | (3, 3) ->   | (3, 4) -> Y",
                "(4, 1) ->   | (4, 2) ->   | (4, 3) ->   | (4, 4) ->  "
        };
        assertDoesNotPercolate(rank, p, expected);
        assertTrue(!p.isFull(1, 2));
        assertTrue(!p.isFull(2, 1));
        assertTrue(!p.isFull(2, 2));
        assertTrue(!p.isFull(2, 3));
        assertTrue(!p.isFull(3, 1));
        assertTrue(!p.isFull(3, 4));
        assertTrue(!p.isFull(4, 1));
        assertTrue(!p.isFull(4, 3));

        p.open(2, 2);
        expected = new String[]{
                "(1, 1) ->   | (1, 2) ->   | (1, 3) ->   | (1, 4) ->  ",
                "(2, 1) -> Y | (2, 2) -> Y | (2, 3) ->   | (2, 4) ->  ",
                "(3, 1) ->   | (3, 2) ->   | (3, 3) ->   | (3, 4) -> Y",
                "(4, 1) ->   | (4, 2) ->   | (4, 3) ->   | (4, 4) ->  "
        };
        assertDoesNotPercolate(rank, p, expected);
        assertTrue(!p.isFull(1, 2));
        assertTrue(!p.isFull(2, 1));
        assertTrue(!p.isFull(2, 2));
        assertTrue(!p.isFull(2, 3));
        assertTrue(!p.isFull(3, 1));
        assertTrue(!p.isFull(3, 4));
        assertTrue(!p.isFull(4, 1));
        assertTrue(!p.isFull(4, 3));

        p.open(1, 1);
        expected = new String[]{
                "(1, 1) -> Y | (1, 2) ->   | (1, 3) ->   | (1, 4) ->  ",
                "(2, 1) -> Y | (2, 2) -> Y | (2, 3) ->   | (2, 4) ->  ",
                "(3, 1) ->   | (3, 2) ->   | (3, 3) ->   | (3, 4) -> Y",
                "(4, 1) ->   | (4, 2) ->   | (4, 3) ->   | (4, 4) ->  "
        };
        assertDoesNotPercolate(rank, p, expected);
        assertTrue(!p.isFull(1, 2));
        assertTrue(p.isFull(2, 1));
        assertTrue(p.isFull(2, 2));
        assertTrue(!p.isFull(2, 3));
        assertTrue(!p.isFull(3, 1));
        assertTrue(!p.isFull(3, 4));
        assertTrue(!p.isFull(4, 1));
        assertTrue(!p.isFull(4, 3));

        p.open(4, 4);
        expected = new String[]{
                "(1, 1) -> Y | (1, 2) ->   | (1, 3) ->   | (1, 4) ->  ",
                "(2, 1) -> Y | (2, 2) -> Y | (2, 3) ->   | (2, 4) ->  ",
                "(3, 1) ->   | (3, 2) ->   | (3, 3) ->   | (3, 4) -> Y",
                "(4, 1) ->   | (4, 2) ->   | (4, 3) ->   | (4, 4) -> Y"
        };
        assertDoesNotPercolate(rank, p, expected);
        assertTrue(!p.isFull(1, 2));
        assertTrue(p.isFull(2, 1));
        assertTrue(p.isFull(2, 2));
        assertTrue(!p.isFull(2, 3));
        assertTrue(!p.isFull(3, 1));
        assertTrue(!p.isFull(3, 4));
        assertTrue(!p.isFull(4, 1));
        assertTrue(!p.isFull(4, 3));
        assertTrue(!p.isFull(4, 4));

        p.open(2, 3);
        expected = new String[]{
                "(1, 1) -> Y | (1, 2) ->   | (1, 3) ->   | (1, 4) ->  ",
                "(2, 1) -> Y | (2, 2) -> Y | (2, 3) -> Y | (2, 4) ->  ",
                "(3, 1) ->   | (3, 2) ->   | (3, 3) ->   | (3, 4) -> Y",
                "(4, 1) ->   | (4, 2) ->   | (4, 3) ->   | (4, 4) -> Y"
        };
        assertDoesNotPercolate(rank, p, expected);
        assertTrue(!p.isFull(1, 2));
        assertTrue(p.isFull(2, 1));
        assertTrue(p.isFull(2, 2));
        assertTrue(p.isFull(2, 3));
        assertTrue(!p.isFull(3, 1));
        assertTrue(!p.isFull(3, 4));
        assertTrue(!p.isFull(4, 1));
        assertTrue(!p.isFull(4, 3));

        p.open(3, 1);
        expected = new String[]{
                "(1, 1) -> Y | (1, 2) ->   | (1, 3) ->   | (1, 4) ->  ",
                "(2, 1) -> Y | (2, 2) -> Y | (2, 3) -> Y | (2, 4) ->  ",
                "(3, 1) -> Y | (3, 2) ->   | (3, 3) ->   | (3, 4) -> Y",
                "(4, 1) ->   | (4, 2) ->   | (4, 3) ->   | (4, 4) -> Y"
        };
        assertDoesNotPercolate(rank, p, expected);
        assertTrue(!p.isFull(1, 2));
        assertTrue(p.isFull(2, 1));
        assertTrue(p.isFull(2, 2));
        assertTrue(p.isFull(2, 3));
        assertTrue(p.isFull(3, 1));
        assertTrue(!p.isFull(3, 4));
        assertTrue(!p.isFull(4, 1));
        assertTrue(!p.isFull(4, 3));

        p.open(4, 1);
        expected = new String[]{
                "(1, 1) -> Y | (1, 2) ->   | (1, 3) ->   | (1, 4) ->  ",
                "(2, 1) -> Y | (2, 2) -> Y | (2, 3) -> Y | (2, 4) ->  ",
                "(3, 1) -> Y | (3, 2) ->   | (3, 3) ->   | (3, 4) -> Y",
                "(4, 1) -> Y | (4, 2) ->   | (4, 3) ->   | (4, 4) -> Y"
        };
        assertPercolates(rank, p, expected);
        assertTrue(!p.isFull(1, 2));
        assertTrue(p.isFull(2, 1));
        assertTrue(p.isFull(2, 2));
        assertTrue(p.isFull(2, 3));
        assertTrue(p.isFull(3, 1));
        assertTrue(!p.isFull(3, 4)); // backwash test
        assertTrue(p.isFull(4, 1));
        assertTrue(!p.isFull(4, 3)); // backwash test
    }

    private static void testInitial(int rank, String[] expected) {
        String actual = show(new Percolation(rank), rank);
        TestSupport.compare(expected, actual);
    }

    private static void assertPercolates(int rank, Percolation p, String[] expected) {
        testPercolates(rank, p, expected, Percolation::percolates);
    }

    private static void assertDoesNotPercolate(int rank, Percolation p, String[] expected) {
        testPercolates(rank, p, expected, x -> !x.percolates());
    }

    private static void testPercolates(int rank, Percolation p, String[] expected, Predicate<Percolation> predicate) {
        String actual = show(p, rank);
        TestSupport.compare(expected, actual);
        assertTrue(predicate.test(p));
    }

    private static String show(Percolation p, int rank) {
        return gridOf(p, rank).show();
    }

    private static TestGrid gridOf(Percolation p, int rank) {
        TestGrid grid = TestGrid.defaultStringGrid();
        for (int i = 1; i <= rank; i++) {
            String[] row = new String[rank];
            for (int j = 1; j <= rank; j++) {
                String s = testOpenAt(i, j, p);
                row[j - 1] = s;
            }
            grid.row((Object []) row);
        }
        return grid;
    }

    private static String testOpenAt(int i, int j, Percolation p) {
        return String.format("(%s, %s) -> %s", i, j, p.isOpen(i, j) ? "Y" : " ");
    }
}