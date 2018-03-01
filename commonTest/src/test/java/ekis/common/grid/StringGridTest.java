package ekis.common.grid;

import ekis.common.Pair;
import ekis.common.TestSupport;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StringGridTest {

    @Test
    public void testEmptyFirstRow() {
        String[] expected = new String[]{
                "               |                |                |               ", //
                "Title Column 1 | Title Column 2 | Title Column 3 | Title Column 4", //
                "               |                |                |               "};
        StringGrid grid = StringGrid.defaultStringGrid();
        grid.row()
            .row("", "", "", "")
            .row("Title Column 1", "Title Column 2", "Title Column 3", "Title Column 4")
            .row();
        TestSupport.check(expected, grid);
    }

    @Test
    public void testColumnFirstRow() {
        String expected =
                "Title Column 1 | Title Column 2 | Title Column 3 | Title Column 4\n" +
                "               |                |                |               \n";
        StringGrid grid = StringGrid.defaultStringGrid();
        grid.column("Title Column 1", "Title Column 2", "Title Column 3", "Title Column 4")
             .row();
        assertEquals(expected, grid.show());
    }

    @Test
    public void testMy() {
        String expected = "               |                |                |                 \n" +
                "Title Column 1 | Title Column 2 | Title Column 3 | Title Column 4  \n" +
                "               |                |                |                 \n" +
                "1              | 2              | 3.0            | (x, y) -> (4, 4)\n" +
                "               |                |                |                 \n" +
                "5              | 6              | 7              | 8               \n" +
                "9              |                |                |                 \n" +
                "               |                |                |                 \n" +
                "50             |                | 51             |                 \n" +
                "7              | 8              | 9              | 4               \n" +
                "10.1           | 20.2233444     | 30.33          | 40              \n";

        StringGrid grid = StringGrid.defaultStringGrid();
        //StringGrid grid = StringGrid.with(Alignment.LEFT, Alignment.CENTRE, Alignment.RIGHT, Alignment.CENTRE);
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
            .column("40")
        ;
        assertEquals(expected, grid.show());
    }
}