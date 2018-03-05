package ekis.common;

import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class TestSummarizer {
    private static final String EMPTY = "\n";
    private static final String PREFIX = "\"";
    private static final String SUFFIX = PREFIX + ",";
    private static final String NEWLINE_PATTERN = "\r?\n";

    private List<String> expected = new ArrayList<>();
    private List<String> actual = new ArrayList<>();

    public TestSummarizer expected(String... lines) {
        expected = Stream.concat(expected.stream(), Stream.of(lines))
                .collect(Collectors.toList());
        return this;
    }

    public TestSummarizer actual(String args) {
        actual = Stream.concat(actual.stream(), Pattern.compile(NEWLINE_PATTERN).splitAsStream(args))
                .collect(Collectors.toList());
        return this;
    }

    public void validate() {
        String expectedString = expected.stream()
                .map(s -> PREFIX + s + SUFFIX)
                .collect(Collectors.joining(EMPTY));

        String actualString = actual.stream()
                .map(s -> PREFIX + s + SUFFIX)
                .collect(Collectors.joining(EMPTY));
        Assert.assertEquals(expectedString, actualString);
    }
}