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
        String expectedString = expected.stream().collect(Collectors.joining(EMPTY, PREFIX, SUFFIX));
        String actualString = actual.stream().collect(Collectors.joining(EMPTY, PREFIX, SUFFIX));
        Assert.assertEquals(expectedString, actualString);
    }
}