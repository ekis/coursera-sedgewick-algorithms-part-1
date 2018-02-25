package ekis.common.grid;

import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public enum Alignment {
    LEFT((string, maxColWidth) ->  string + pad(maxColWidth - string.length())),
    RIGHT((string, maxColWidth) -> pad(maxColWidth - string.length()) + string),
    CENTRE((string, maxColWidth) -> {
        int padding = maxColWidth - string.length();
        int limit = padding / 2;
        return pad(limit) + string + pad(padding - limit);
    });

    private final BiFunction<String, Integer, String> _padding;

    Alignment(BiFunction<String, Integer, String> padding) {
        _padding = padding;
    }

    String align(String val, int maxColWidth) {
        return _padding.apply(val, maxColWidth);
    }

    private static String pad(int limit) {
        return IntStream.range(0, limit)
                .mapToObj(elem -> " ")
                .collect(Collectors.joining());
    }
}
