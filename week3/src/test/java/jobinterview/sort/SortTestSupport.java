package jobinterview.sort;

import ekis.common.StringGrid;

import java.util.Arrays;

final class SortTestSupport {

    static final String[] EXAMPLE_1 = new String[]{"S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};
    static final String[] EXPECTED_1 = new String[]{"A", "E", "E", "L", "M", "O", "P", "R", "S", "T", "X"};

    static final String[] EXAMPLE_2 = new String[]{"M", "E", "R", "G", "E", "S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};
    static final String[] EXPECTED_2 = new String[]{"A", "E", "E", "E", "E", "G", "L", "M", "M", "O", "P", "R", "R", "S", "T", "X"};

    static final String[] EXAMPLE_3 = new String[]{"Q", "U", "I", "C", "K", "S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};
    static final String[] EXPECTED_3 = new String[]{"A", "C", "E", "E", "I", "K", "L", "M", "O", "P", "Q", "R", "S", "T", "U", "X"};

    static final String[] SEDGEWICK_DEMO_EXAMPLE = "PABXWPPVPDPCYZ".split("");
    static final String[] SEDGEWICK_DEMO_EXPECTED = "ABCDPPPPPVWXYZ".split("");

    static StringGrid grid(String... columnNames) {
        StringGrid grid = new StringGrid();
        grid.row();
        grid.column((Object []) columnNames);
        grid.alignments(StringGrid.Alignment.CENTER, StringGrid.Alignment.CENTER, StringGrid.Alignment.CENTER, StringGrid.Alignment.CENTER);
        grid.row();
        return grid;
    }

    static String[] copyExample1() {
        return copyOf(EXAMPLE_1);
    }

    static String[] copyExample2() {
        return copyOf(EXAMPLE_2);
    }

    static String[] copyExample3() {
        return copyOf(EXAMPLE_3);
    }

    static String[] copySedgewickExample() {
        return copyOf(SEDGEWICK_DEMO_EXAMPLE);
    }

    private static String[] copyOf(String[] array) {
        return Arrays.copyOf(array, array.length);
    }
}