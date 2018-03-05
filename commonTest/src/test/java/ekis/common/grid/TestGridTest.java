package ekis.common.grid;

import ekis.common.Pair;
import ekis.common.TestSupport;
import org.junit.Test;

public class TestGridTest {

    @Test
    public void defaultStringGrid_rowsOnly_defaultAlignment_expectValidString() {
        String[] expected = new String[]{
                "Title Column 1 | Title Column 2 | Title Column 3 | Title Column 4",
                "A              | B              | C              | D             ",
                "E              | F              | G              |               ",
                "               |                |                |               ",
        };

        TestGrid grid = TestGrid.defaultStringGrid();
        grid.row("Title Column 1", "Title Column 2", "Title Column 3", "Title Column 4")
            .row("A", "B", "C", "D")
            .row("E", "F", "G")
            .row();
        TestSupport.check(expected, grid);
    }

    @Test
    public void defaultStringGrid_rowsOnly_upperTriangular_defaultAlignment_expectValidString() {
        String[] expected = new String[]{
                "/        | Column 1 | Column 2 | Column 3 | Column 4",
                "-------- | -------- | -------- | -------- | --------",
                "Row 1    | 1        | 2        | 3        | 4       ",
                "Row 2    | 5        | 6        | 7        |         ",
                "Row 3    | 8        | 9        |          |         ",
                "Row 4    | 42       |          |          |         ",
        };

        TestGrid grid = TestGrid.defaultStringGrid();
        grid.row()
                .row("/", "Column 1", "Column 2", "Column 3", "Column 4")
                .row("--------", "--------", "--------", "--------", "--------")
                .row("Row 1", 1, 2, 3, 4)
                .row("Row 2", 5, 6, 7)
                .row("Row 3", 8, 9)
                .row("Row 4", 42);
        TestSupport.check(expected, grid);
    }

    @Test
    public void defaultStringGrid_rowsOnly_defineEmptyFirstRow_defaultAlignment_expectValidString() {
        String[] expected = new String[]{
                "               |                |                |               ",
                "Title Column 1 | Title Column 2 | Title Column 3 | Title Column 4",
        };

        TestGrid grid = TestGrid.defaultStringGrid();
        grid.row() // first empty row is ignored as the code cannot guess how many columns to inject
            .row("", "", "", "") // this is how we would define an empty first row
            .row("Title Column 1", "Title Column 2", "Title Column 3", "Title Column 4");
        TestSupport.check(expected, grid);
    }

    @Test
    public void defaultStringGrid_rowsOnly_oneValuePerRow_fillVertical_defaultAlignment_expectValidString() {
        String[] expected = new String[]{
                "Title Column 1 | Title Column 2 | Title Column 3 | Title Column 4",
                "               |                |                |               ",
                "A              |                |                |               ",
                "B              |                |                |               ",
                "C              |                |                |               ",
                "D              |                |                |               ",
                "E              |                |                |               ",
                "F              |                |                |               ",
                "G              |                |                |               ",
        };
        TestGrid grid = TestGrid.defaultStringGrid();
        grid.row("Title Column 1", "Title Column 2", "Title Column 3", "Title Column 4") // first, establish the header row...
            .row() // ... enter a new row so as to define the width of the matrix....
            .row("A") // ....and then continue to insert cell values
            .row("B")
            .row("C")
            .row("D")
            .row("E")
            .row("F")
            .row("G");
        TestSupport.check(expected, grid);
    }

    @Test
    public void defaultStringGrid_columnsOnly_defaultAlignment_expectValidString() {
        String[] expected = new String[]{
                "Title Column 1 | Title Column 2 | Title Column 3 | Title Column 4",
                "               |                |                |               ",
                "A              | B              | C              | D             ",
                "E              | F              | G              |               ",
        };
        TestGrid grid = TestGrid.defaultStringGrid();
        grid.column("Title Column 1", "Title Column 2", "Title Column 3", "Title Column 4")
                .row()
                .column("A", "B")
                .column("C", "D")
                .column() // empty column directives are ignored
                .column()
                .column("E", "F", "G");
        TestSupport.check(expected, grid);
    }

    @Test
    public void defaultStringGrid_columnsOnly_oneValuePerColumn_fillHorizontally_defaultAlignment_expectValidString() {
        String[] expected = new String[]{
                "Title Column 1 | Title Column 2 | Title Column 3 | Title Column 4",
                "               |                |                |               ",
                "A              | B              | C              | D             ",
                "E              | F              | G              |               ",
        };
        TestGrid grid = TestGrid.defaultStringGrid();
        grid.column("Title Column 1", "Title Column 2", "Title Column 3", "Title Column 4") // first, establish the header row...
            .row() // ... enter a new row so as to define the width of the matrix....
            .column("A") // ....and then continue to insert cell values
            .column("B")
            .column("C")
            .column("D")
            .column("E")
            .column("F")
            .column("G");
        TestSupport.check(expected, grid);
    }

    @Test
    public void defaultStringGrid_rowColumnsCombined_defaultAlignment_expectValidString() {
        String[] expected = new String[]{
                "               |                |                |                 ",
                "Title Column 1 | Title Column 2 | Title Column 3 | Title Column 4  ",
                "               |                |                |                 ",
                "1              | 2              | 3.0            | (x, y) -> (4, 4)",
                "               |                |                |                 ",
                "5              | 6              | 7              | 8               ",
                "9              |                |                |                 ",
                "               |                |                |                 ",
                "50             |                | 51             |                 ",
                "7              | 8              | 9              | 4               ",
                "10.1           | 20.2233444     | 30.33          |                 ",
                "42             |                |                |                 ",
        };

        TestGrid grid = TestGrid.defaultStringGrid();
        grid.row("", "", "", "")
            .row("Title Column 1", "Title Column 2", "Title Column 3", "Title Column 4")
            .row()
            .row("1", 2, 3D, Pair.of(4, 4))
            .row()
            .column("5", "6")
            .column("7", "8")
            .column("9")
            .row()
            .column("50", "", "51")
            .row("7", "8", "9", 4L)
            .row("10.1", "20.2233444", "30.33")
            .column("42");
        TestSupport.check(expected, grid);
    }

    @Test
    public void defaultStringGrid_rowColumnsCombined_dynamicRowResize_defaultAlignment_expectValidString() {
        String[] expected = new String[]{
                "H123456789          | H123456789          | H123456789          | H123456789          | H123456789         ",
                "                    |                     |                     |                     |                    ",
                "1234                | 1234                | 1234                | 1234                | 1234               ",
                "1234567890123456789 | 1234                | 1234                | 1234                | 1234               ",
                "1234                | 1234567890123456789 | 1234                | 1234                | 1234               ",
                "1234                | 1234                | 1234567890123456789 | 1234                | 1234               ",
                "1234                | 1234                | 1234                | 1234567890123456789 | 1234               ",
                "1234                | 1234                | 1234                | 1234                | 1234567890123456789",
        };
        TestGrid grid = TestGrid.defaultStringGrid();
        grid.column("H123456789", "H123456789", "H123456789", "H123456789", "H123456789")
                .row()
                .row(1234, 1234, 1234, 1234, 1234)
                .row(1234567890123456789L, 1234, 1234, 1234, 1234)
                .row(1234, 1234567890123456789L, 1234, 1234, 1234)
                .row(1234, 1234, 1234567890123456789L, 1234, 1234)
                .row(1234, 1234, 1234, 1234567890123456789L, 1234)
                .row(1234, 1234, 1234, 1234, 1234567890123456789L);
        TestSupport.check(expected, grid);
    }

    @Test
    // def.: rank is assumed to be maximum number of columns of matrix
    public void defaultStringGrid_rowExceedsMatrixRank_dynamicRankExpansion_defaultAlignment_expectValidString() {
        String[] expected = new String[]{
                "Col 1 | Col 2 | Col 3 |   |   |   |   |   |  ",
                "1     | 2     | 3     |   |   |   |   |   |  ",
                "1     | 2     | 3     | 4 |   |   |   |   |  ",
                "1     | 2     | 3     | 4 | 5 |   |   |   |  ",
                "1     | 2     | 3     | 4 | 5 | 6 |   |   |  ",
                "1     | 2     | 3     | 4 | 5 | 6 | 7 |   |  ",
                "1     | 2     | 3     | 4 | 5 | 6 | 7 | 8 |  ",
                "1     | 2     | 3     | 4 | 5 | 6 | 7 | 8 | 9",
        };

        TestGrid grid = TestGrid.defaultStringGrid();
        grid.row("Col 1", "Col 2", "Col 3")
            .row(1, 2, 3)
            .row(1, 2, 3, 4)
            .row(1, 2, 3, 4, 5)
            .row(1, 2, 3, 4, 5, 6)
            .row(1, 2, 3, 4, 5, 6, 7)
            .row(1, 2, 3, 4, 5, 6, 7, 8)
            .row(1, 2, 3, 4, 5, 6, 7, 8, 9);
        TestSupport.check(expected, grid);
    }

    @Test
    public void defaultStringGrid_rowAndColumnExceedMatrixRank_dynamicRankExpansion_defaultAlignment_expectValidString() {
        String[] expected = new String[]{
                "Col 1 | Col 2 | Col 3 | 1 | 2 | 3 | 1 | 2 | 3 | 4 | New Empty Row",
                "      |       |       |   |   |   |   |   |   |   |              ",
                "1     | 2     | 3     | 4 | 5 | 1 | 2 | 3 | 4 | 5 | New Empty Row",
                "      |       |       |   |   |   |   |   |   |   |              ",
                "99    |       |       |   |   |   |   |   |   |   |              ",
                "1     | 2     | 3     | 4 | 5 | 6 | 7 | 1 | 2 | 3 | 4            ",
                "5     | 6     | 7     | 8 | 1 | 2 | 3 | 4 | 5 | 6 | 7            ",
                "8     | 9     |       |   |   |   |   |   |   |   |              ",
        };
        TestGrid grid = TestGrid.defaultStringGrid();
        grid.column("Col 1", "Col 2", "Col 3")
            .column(1, 2, 3)
            .column(1, 2, 3, 4, "New Empty Row")
            .row()
            .column(1, 2, 3, 4, 5)
            .column(1, 2, 3, 4, 5, "New Empty Row")
            .row()
            .row(99)
            .column(1, 2, 3, 4, 5, 6, 7)
            .column(1, 2, 3, 4, 5, 6, 7, 8)
            .column(1, 2, 3, 4, 5, 6, 7, 8, 9);
        TestSupport.check(expected, grid);
    }

    @Test
    public void defaultStringGrid_alignments_allColumnsHaveAlignmentsSpecified_expectedValidString() {
        String[] expected = new String[]{
                "                   |                     |                   |                     |                   ",
                "Column Align Right | Column Align Centre | Column Align Left | Column Align Centre | Column Align Right",
                "                   |                     |                   |                     |                   ",
                "             RIGHT |       CENTRE        | LEFT              |       CENTRE        |              RIGHT",
                "                   |                     |                   |                     |                   ",
                "                 5 |          6          | 7                 |          8          |                  9",
                "                   |                     |                   |                     |                   ",
                "             RIGHT |       CENTRE        | LEFT              |       CENTRE        |              RIGHT",
                "             77777 |        88888        | 999999            |       000000        |              11111",
                "              10.1 |     20.2233444      | 30.33             |                     |                   ",
                "             RIGHT |       CENTRE        |                   |       CENTRE        |              RIGHT",
                "                42 |                     |                   |                     |                   ",
        };

        TestGrid grid = TestGrid.builder()
                // length of alignment list matches matrix rank
                .alignments(Alignment.RIGHT, Alignment.CENTRE, Alignment.LEFT, Alignment.CENTRE, Alignment.RIGHT)
                .build();

        String left = "LEFT";
        String centre = "CENTRE";
        String right = "RIGHT";

        grid.row("", "", "", "")
                .row("Column Align Right", "Column Align Centre", "Column Align Left", "Column Align Centre", "Column Align Right")
                .row()
                .row(right, centre, left, centre, right)
                .row()
                .column(5, 6)
                .column(7, 8)
                .column(9)
                .row()
                .column(right, centre, left, centre, right)
                .row("77777", "88888", "999999", "000000", 11111)
                .row("10.1", "20.2233444", "30.33")
                .column(right, centre, "", centre, right)
                .column("42");
        TestSupport.check(expected, grid);
    }

    @Test
    public void defaultStringGrid_alignments_missingAlignmentSpecification_assumeDefault_expectedValidString() {
        String[] expected = new String[]{
                "                   |                     |                     |                    |                                       ",
                "Column Align Right | Column Align Centre | Column Align Centre | Column Align Right | Column Align Unspecified (assume Left)",
                "                   |                     |                     |                    |                                       ",
                "             RIGHT |       CENTRE        |       CENTRE        |              RIGHT | LEFT                                  ",
                "                   |                     |                     |                    |                                       ",
                "                 5 |          6          |          7          |                  8 | 9                                     ",
                "                   |                     |                     |                    |                                       ",
                "             RIGHT |       CENTRE        |       CENTRE        |              RIGHT | LEFT                                  ",
                "             77777 |        88888        |       999999        |             000000 | 11111                                 ",
                "              10.1 |     20.2233444      |        30.33        |                    |                                       ",
                "             RIGHT |       CENTRE        |                     |             CENTRE | LEFT                                  ",
                "                42 |                     |                     |                    |                                       ",
        };

        TestGrid grid = TestGrid.builder()
                // length of alignment list is less than matrix rank => assume default
                .alignments(Alignment.RIGHT, Alignment.CENTRE, Alignment.CENTRE, Alignment.RIGHT)
                .build();

        String left = "LEFT";
        String centre = "CENTRE";
        String right = "RIGHT";

        grid.row("", "", "", "")
                .row("Column Align Right", "Column Align Centre", "Column Align Centre", "Column Align Right", "Column Align Unspecified (assume Left)")
                .row()
                .row(right, centre, centre, right, left)
                .row()
                .column(5, 6)
                .column(7, 8)
                .column(9)
                .row()
                .column(right, centre, centre, right, left)
                .row("77777", "88888", "999999", "000000", 11111)
                .row("10.1", "20.2233444", "30.33")
                .column(right, centre, "", centre, left)
                .column("42");
        TestSupport.check(expected, grid);
    }

    @Test
    public void defaultStringGrid_alignments_extraAlignmentSpecification_assumeDefault_expectedValidString() {
        String[] expected = new String[]{
                "                   |                     |                   |                     |                    |        |        |       ",
                "Column Align Right | Column Align Centre | Column Align Left | Column Align Centre | Column Align Right |        |        |       ",
                "                   |                     |                   |                     |                    |        |        |       ",
                "             RIGHT |       CENTRE        | LEFT              |       CENTRE        |              RIGHT |        |        |       ",
                "                   |                     |                   |                     |                    |        |        |       ",
                "                 5 |          6          | 7                 |          8          |                  9 |        |        |       ",
                "                   |                     |                   |                     |                    |        |        |       ",
                "Column Align Right | Column Align Centre | Column Align Left | Column Align Centre | Column Align Right | 123456 | 123456 | 123456",
                "Column Align Right | Column Align Centre | Column Align Left | Column Align Centre | Column Align Right |  AAA   |      B | C     ",
                "             RIGHT |       CENTRE        | LEFT              |       CENTRE        |              RIGHT | CENTRE |  RIGHT | LEFT  ",
                "             77777 |        88888        | 999999            |       000000        |              11111 |        |        |       ",
                "              10.1 |     20.2233444      | 30.33             |                     |                    |        |        |       ",
                "             RIGHT |       CENTRE        |                   |       CENTRE        |              RIGHT |   42   |        |       ",
        };

        TestGrid grid = TestGrid.builder()
                // length of alignment list matches matrix rank -> simply ignore the extra definition(s) until the rank is increased with row() containing extra cols
                .alignments(Alignment.RIGHT, Alignment.CENTRE, Alignment.LEFT, Alignment.CENTRE, Alignment.RIGHT, Alignment.CENTRE, Alignment.RIGHT, Alignment.LEFT)
                .build();

        String left = "LEFT";
        String centre = "CENTRE";
        String right = "RIGHT";

        grid.row("", "", "", "")
                .row("Column Align Right", "Column Align Centre", "Column Align Left", "Column Align Centre", "Column Align Right")
                .row()
                .row(right, centre, left, centre, right)
                .row()
                .column(5, 6)
                .column(7, 8)
                .column(9)
                .row()
                .row("Column Align Right", "Column Align Centre", "Column Align Left", "Column Align Centre", "Column Align Right", "123456", "123456", "123456")
                .row("Column Align Right", "Column Align Centre", "Column Align Left", "Column Align Centre", "Column Align Right", "AAA", "B", "C")
                .column(right, centre, left, centre, right, centre, right, left)
                .row("77777", "88888", "999999", "000000", 11111)
                .row("10.1", "20.2233444", "30.33")
                .column(right, centre, "", centre, right)
                .column("42");
        TestSupport.check(expected, grid);
    }

    @Test
    public void defaultStringGrid_emptyCellWithNotAvailable_lowerTriangular_expectValidString() {
        String[] expected = new String[]{
                "1 | N/A at (0, 1) | N/A at (0, 2) | N/A at (0, 3) | N/A at (0, 4)",
                "1 | 2             | N/A at (1, 2) | N/A at (1, 3) | N/A at (1, 4)",
                "1 | 2             | 3             | N/A at (2, 3) | N/A at (2, 4)",
                "1 | 2             | 3             | 4             | N/A at (3, 4)",
                "1 | 2             | 3             | 4             | 5            ",
        };

        TestGrid grid = TestGrid.builder()
                .emptyCell((row, col) -> String.format("N/A at (%s, %s)", row, col))
                .build();

        grid.row()
            .row(1)
            .row(1, 2)
            .row(1, 2, 3)
            .row(1, 2, 3, 4)
            .row(1, 2, 3, 4, 5);

        TestSupport.check(expected, grid);
    }

    @Test
    public void defaultStringGrid_customSeparator_lowerTriangular_expectValidString() {
        String[] expected = new String[]{
                "1   '       '       '       '    ",
                "1   '   2   '       '       '    ",
                "1   '   2   '   3   '       '    ",
                "1   '   2   '   3   '   4   '    ",
                "1   '   2   '   3   '   4   '   5",
        };

        TestGrid grid = TestGrid.builder()
                .separator("   '   ")
                .build();

        grid.row()
            .row(1)
            .row(1, 2)
            .row(1, 2, 3)
            .row(1, 2, 3, 4)
            .row(1, 2, 3, 4, 5);

        TestSupport.check(expected, grid);
    }

    @Test
    public void defaultStringGrid_customConverter_expectValidString() {
        String[] expected = new String[]{
                "Example row is -> ID: 1000 | Description: Row 1 | Coordinates: (x, y) -> (0, 0)",
                "Example row is -> ID: 2000 | Description: Row 2 | Coordinates: (x, y) -> (1, 0)",
                "Example row is -> ID: 3000 | Description: Row 3 | Coordinates: (x, y) -> (2, 0)",
                "Example row is -> ID: 4000 | Description: Row 4 | Coordinates: (x, y) -> (3, 0)",
                "Example row is -> ID: 5000 | Description: Row 5 | Coordinates: (x, y) -> (4, 0)",
                "Example row is -> ID: 6000 | Description: Row 6 | Coordinates: (x, y) -> (5, 0)",
        };

        TestGrid grid = TestGrid.builder()
                .converter(exampleRow -> String.format("Example row is -> ID: %s | Description: %s | Coordinates: %s", ((TestExampleRow) exampleRow).id, ((TestExampleRow) exampleRow).description, ((TestExampleRow) exampleRow).coordinates.toString()))
                .build();

        grid.row(new TestExampleRow(1000, "Row 1", Pair.of(0, 0)))
            .row(new TestExampleRow(2000, "Row 2", Pair.of(1, 0)))
            .row(new TestExampleRow(3000, "Row 3", Pair.of(2, 0)))
            .row(new TestExampleRow(4000, "Row 4", Pair.of(3, 0)))
            .row(new TestExampleRow(5000, "Row 5", Pair.of(4, 0)))
            .row(new TestExampleRow(6000, "Row 6", Pair.of(5, 0)));

        TestSupport.check(expected, grid);
    }

    private static class TestExampleRow {
        private final int id;
        private final String description;
        private final Pair<Integer, Integer> coordinates;

        private TestExampleRow(int rowId, String desc, Pair<Integer, Integer> coord) {
            id = rowId;
            description = desc;
            coordinates = coord;
        }
    }
}