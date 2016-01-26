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
@SuppressWarnings("nls")
public final class TestSummarizer {
    private static final String PREFIX = "\"";
    private static final String SUFFIX = "\", //\n";
    private static final String LAST_SUFFIX = "\" //\n";
    private static final String NEWLINE_PATTERN = "\r?\n";
    private final List<Exception> _exceptions = new ArrayList<>();
    private final List<String> _expected = new ArrayList<>();
    private final List<String> _actual = new ArrayList<>();
    private PatternMatch[] _expectedMatchers = null;

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
     * Adds a set of pattern matchers that will be applied to the expected summary lines.
     * Typically, each matcher will generate some new actual lines using
     * {@link #actual(String, Object...)}.
     */
    public TestSummarizer expectedMatchers(PatternMatch... expectedMatchers) {
        _expectedMatchers = expectedMatchers;
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

    /**
     * Adds many lines to actual summary. Each line is formatted according to lineFormat string.
     * The formatted line string must not contain a newline character.
     *
     * @param lineFormat a control string suitable for {@link String#format(String, Object...)}.
     * @param lines      the lines to add. Line strings must not contain a line break.
     */
    public TestSummarizer formattedActuals(String lineFormat, Object... lines) {
        for (Object l : lines) {
            _actual.add(String.format(lineFormat, l));
        }
        return this;
    }

    private static void addStringSplitIntoLines(String formattedLine, List<String> target) {
        for (String l : formattedLine.split(NEWLINE_PATTERN)) {
            target.add(l);
        }
    }

    /**
     * Adds the stack trace associated with an exception to the actual output.
     */
    public void exception(Exception exception) {
        _exceptions.add(exception);
        StringWriter writer = new StringWriter();
        exception.printStackTrace(new PrintWriter(writer));
        _actual.add("");
        _actual.add("***EXCEPTION***");
        addStringSplitIntoLines(writer.toString(), _actual);
    }

    public void briefException(Exception exception) {
        _exceptions.add(exception);
        actual("***EXCEPTION: %s", exception.toString().replaceAll("\\r|\\n", ""));
    }

    public boolean hasException(Class<? extends Exception> clazz) {
        for (Exception e : _exceptions) {
            if (clazz.isAssignableFrom(e.getClass())) return true;
        }

        return false;
    }

    public void check(String message) {
        check(message, false);
    }

    public void checkSorted(String message) {
        check(message, true);
    }

    private void check(String message, boolean sortActual) {
        applyExpectedMatchers();
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

    private void applyExpectedMatchers() {
        if (null != _expectedMatchers) {
            scanExpected(_expectedMatchers);
        }
    }

    private void scanExpected(PatternMatch... matchers) {
        for (String line : _expected) {
            PatternMatch.dispatch(line, matchers);
        }
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

    public PatternMatch blank() {
        return new PatternMatch(true, " *") {
            @SuppressWarnings("unused")
            public void match() {
                actual("");
            }
        };
    }

    public PatternMatch comment() {
        return new PatternMatch(true, "(?=--)(.*)") {
            @SuppressWarnings("unused")
            public void match(String comment) {
                actual(comment);
            }
        };
    }
}
