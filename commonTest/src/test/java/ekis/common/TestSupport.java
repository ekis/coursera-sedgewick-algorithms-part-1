package ekis.common;

import ekis.common.grid.StringGrid;
import org.junit.Assert;

public final class TestSupport {

    private TestSupport() {
        // utility class
    }

    public static void check(String[] expected, StringGrid grid) {
        compare(expected, grid.show());
    }

    public static void compare(String[] expected, String actual) {
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
