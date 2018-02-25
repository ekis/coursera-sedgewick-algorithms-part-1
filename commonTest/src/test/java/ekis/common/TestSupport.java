package ekis.common;

import ekis.common.grid.StringGrid;
import org.junit.Assert;

public final class TestSupport {

    private TestSupport() {
        // utility class
    }

    public static void check(StringGrid grid, String... expected) {
        compare(grid.show(), expected);
    }

    public static void compare(String actual, String... expected) {
        TestSummarizer summarizer = new TestSummarizer();
        summarizer.actual("%s", actual);
        summarizer.expected(expected);
        summarizer.check("result");
    }

    public static void failAndCheck(Runnable task, String expectedErrorMessage) {
        try {
            task.run();
        } catch (IllegalStateException e) {
            Assert.assertEquals(expectedErrorMessage, e.getMessage());
        }
    }
}
