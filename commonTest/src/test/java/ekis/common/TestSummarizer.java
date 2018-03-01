package ekis.common;

import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class TestSummarizer {
    private static final String EMPTY = "";
    private static final String PREFIX = "\"";
    private static final String SUFFIX = "\", \n";
    private static final String NEWLINE_PATTERN = "\r?\n";

    private List<String> expected = new ArrayList<>();
    private List<String> actual = new ArrayList<>();

    public TestSummarizer expected(String... lines) {
        expected = concat(expected, Stream.of(lines));
        return this;
    }

    public TestSummarizer actual(String args) {
        actual = concat(actual, Pattern.compile(NEWLINE_PATTERN).splitAsStream(args));
        return this;
    }

    public void validate() {
        Assert.assertEquals(coalesce(expected), coalesce(actual));
    }

    private static <T> List<T> concat(List<T> list, Stream<T> tailElements) {
        return Stream.concat(list.stream(), tailElements)
                .collect(Collectors.toList());
    }

    private static String coalesce(List<String> list) {
        return list.stream().collect(Collectors.joining(EMPTY, PREFIX, SUFFIX));
    }
}