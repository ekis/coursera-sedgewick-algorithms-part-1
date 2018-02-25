package ekis.common;

import org.junit.Assert;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A test helper class that can be used to build up "summary" strings of complicated data
 * structures in order to facilitate comparing expected and actual values.
 * <p>
 * The summaries are expressed in Java syntax in order to make it easy to copy-and-paste summary
 * strings into Java code.
 */
public final class TestSummarizer {
    private static final String PREFIX = "\"";
    private static final String SUFFIX = "\", //\n";
    private static final String LAST_SUFFIX = "\" //\n";
    private static final String NEWLINE_PATTERN = "\r?\n";
    private final List<String> _expected = new ArrayList<>();
    private final List<String> _actual = new ArrayList<>();

    /**
     * Adds a set of lines to the expected summary.
     *
     * @param lines the lines to add. Line strings must not contain a line break.
     */
    public TestSummarizer expected(String... lines) {
        for (String line : lines)
            addStringSplitIntoLines(line, _expected);
        return this;
    }

    /**
     * Adds a single formatted line to the actual summary. The formatted line string must not
     * contain a newline character.
     *
     * @param format    a control string suitable for {@link String#format(String, Object...)}.
     * @param arguments arguments for the control string
     */
    public TestSummarizer actual(String format, Object... arguments) {
        String formattedLine = String.format(format, arguments);
        addStringSplitIntoLines(formattedLine, _actual);
        return this;
    }

    private static void addStringSplitIntoLines(String formattedLine, List<String> target) {
        for (String l : formattedLine.split(NEWLINE_PATTERN)) {
            target.add(l);
        }
    }

    public void check(String message) {
        check(message, false);
    }

    private void check(String message, boolean sortActual) {
        String expected = createJavaLines(_expected);
        if (sortActual) {
            Collections.sort(_actual);
        }
        String actual = createJavaLines(_actual);
        Assert.assertEquals(message, expected, actual);
    }

    private String createJavaLines(List<String> lines) {
        StringBuilder builder = new StringBuilder();
        for (String line : lines) {
            addJavaLine(line, builder);
        }
        return fixLastSuffix(builder.toString());
    }

    private TestSummarizer addJavaLine(String line, StringBuilder builder) {
        builder.append(PREFIX);
        builder.append(line.replace("\\", "\\\\").replace("\"", "\\\""));
        builder.append(SUFFIX);
        return this;
    }

    private static String fixLastSuffix(String summary) {
        return summary.replaceAll(SUFFIX + "$", LAST_SUFFIX);
    }
}
