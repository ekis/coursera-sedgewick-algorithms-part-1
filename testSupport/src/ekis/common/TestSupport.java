package ekis.common;

import org.junit.Assert;

public final class TestSupport {

    private TestSupport() {
        // utility class
    }

    public static void check(StringGrid grid, String... expected) {
        grid.separator(" | ");
        compare(grid.show(), expected);
    }

    public static void compare(String actual, String... expected) {
        TestSummarizer summarizer = new TestSummarizer();
        summarizer.actual("%s", actual); //NON-NLS
        summarizer.expected(expected);
        summarizer.check("result"); //NON-NLS
    }

    public static void failAndCheck(Runnable task, String expectedErrorMessage) {
        try {
            task.run();
        } catch (IllegalStateException e) {
            Assert.assertEquals(expectedErrorMessage, e.getMessage());
        }
    }
}
