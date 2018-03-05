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
        expected = appendToTail(expected, Stream.of(lines));
        return this;
    }

    public TestSummarizer actual(String args) {
        actual = appendToTail(actual, Pattern.compile(NEWLINE_PATTERN).splitAsStream(args));
        return this;
    }

    public void validate() {
        Assert.assertEquals(wrapLinesAndMerge(expected), wrapLinesAndMerge(actual));
    }

    private static <T> List<T> appendToTail(List<T> list, Stream<T> stream) {
        return Stream.concat(list.stream(), stream).collect(Collectors.toList());
    }

    private static String wrapLinesAndMerge(List<String> list) {
        return list.stream()
                .map(s -> PREFIX + s + SUFFIX)
                .collect(Collectors.joining(EMPTY));
    }
}