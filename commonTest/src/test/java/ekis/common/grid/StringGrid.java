package ekis.common.grid;

import ekis.common.Pair;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class StringGrid {
    private static final String EMPTY = "";
    private static final String CRLF = "\n";

    private final Matrix _matrix = new Matrix();
    private final Map<Integer, ColumnAligner> _columnMeta;
    private final String _separator;

    private final Function<Object, String> _converter;
    private Function<Integer, String> _emptyCellValue;

    private StringGrid(String separator, Function<Object, String> converter, Function<Integer, String> emptyCell, Alignment... alignments) {
        _separator = separator;
        _emptyCellValue = emptyCell;
        _converter = converter;
        _columnMeta = Stream.iterate(0, x -> x + 1)
                .limit(alignments.length)
                .map(col -> Pair.of(col, new ColumnAligner(alignments[col])))
                .collect(Collectors.toMap(Pair::x, Pair::y));
    }

    public static StringGrid.Builder stringGridBuilder() {
        return new Builder()
                .converter(String::valueOf);
    }

    public static StringGrid defaultStringGrid() {
        return stringGridBuilder()
                .build();
    }

    public static StringGrid.Builder builder() {
        return new Builder();
    }

    public StringGrid column(Object... values) {
        Stream.iterate(0, x -> x + 1)
                .limit(values.length)
                .forEachOrdered(idx -> {
                    Pair<Integer, Integer> lastRowCol = maxKey();
                    Integer maxElemRow = lastRowCol.x();
                    Integer maxElemCol = lastRowCol.y();
                    if (maxElemRow == -1) {
                        insert(maxElemRow + 1, maxElemCol + 1, _converter.apply(values[idx]));
                    } else if (maxElemRow == 0 || maxElemCol < maxCol()) {
                        insert(maxElemRow, maxElemCol + 1, _converter.apply(values[idx]));
                    } else {
                        insert(maxElemRow + 1, 0, _converter.apply(values[idx]));
                    }
                });
        return this;
    }

    public StringGrid row(Object... values) {
        Integer endIndex = values.length == 0 ? maxUsedCol() : values.length;
        Integer newRow = maxRow() + 1;
        Function<Integer, String> cellValueProvider = values.length == 0
                ? _emptyCellValue
                : cellValue -> _converter.apply(values[cellValue]);
        Stream.iterate(0, x -> x + 1)
                .limit(endIndex)
                .forEachOrdered(index -> insert(newRow, index, cellValueProvider.apply(index)));
        return this;
    }

    public String show() {
        StringBuilder sb = new StringBuilder();
        int maxCol = maxCol();
        for (int row = 0; row <= maxRow(); row++) {
            for (int col = 0; col <= maxCol; col++) {
                ColumnAligner columnAligner = getAligner(col);
                String value = _matrix.get(Pair.of(row, col));
                sb.append(columnAligner.align(value));
                if (col != maxCol) {
                    sb.append(_separator);
                }
            }
            sb.append(CRLF);
        }
        return sb.toString();
    }

    private void insert(Integer row, Integer col, String value) {
        int stringLength = put(row, col, value);
        updateColumnWidth(col, stringLength);
    }

    private void updateColumnWidth(Integer currentCol, int strLen) {
        ColumnAligner columnAligner = getAligner(currentCol);
        columnAligner.updateWidth(strLen);
        _columnMeta.put(currentCol, columnAligner);
    }

    private ColumnAligner getAligner(int column) {
        return _columnMeta.getOrDefault(column, new ColumnAligner(Alignment.LEFT));
    }

    private int put(Integer row, Integer column, String value) {
        _matrix.put(Pair.of(row, column), value);
        return value.length();
    }

    private Integer maxRow() {
        return maxKey().x();
    }

    private Integer maxCol() {
        return _matrix.maxCol();
    }

    private Integer maxUsedCol() {
        // this handles a corner case where client erroneously specifies more alignment cols than are actually used
        // in rare circumstances (row() followed immediately by column()) the inserted column value would end up one cell behind
        // so we have to push the value one column forward
        return Math.min(maxCol() + 1, _columnMeta.size());
    }

    private Pair<Integer, Integer> maxKey() {
        Map.Entry<Pair<Integer, Integer>, String> lastEntry = _matrix.lastEntry();
        return lastEntry == null ? nullMatrixCell() : lastEntry.getKey();
    }

    private static Pair<Integer, Integer> nullMatrixCell() {
        return Pair.of(-1, -1);
    }

    private static class Matrix {
        private final NavigableMap<Pair<Integer, Integer>, String> map = new TreeMap<>(pairComparator());
        private int maxCol = -1;

        String get(Pair<Integer, Integer> key) {
            return map.getOrDefault(key, EMPTY);
        }

        void put(Pair<Integer, Integer> key, String value) {
            map.put(key, value);
            maxCol = Math.max(key.y(), maxCol);
        }

        Map.Entry<Pair<Integer, Integer>, String> lastEntry() {
            return map.lastEntry();
        }

        Integer maxCol() {
            return maxCol;
        }

        private static Comparator<Pair<Integer, Integer>> pairComparator() {
            return (p1, p2) -> p1.x().compareTo(p2.x()) == 0
                    ? p1.y().compareTo(p2.y())
                    : p1.x().compareTo(p2.x());
        }
    }

    private static class ColumnAligner {
        private final Alignment alignment;
        private int maxColWidth = 0;

        private ColumnAligner(Alignment columnAlignment) {
            alignment = columnAlignment;
        }

        private String align(String value) {
            return alignment.align(value, maxColWidth);
        }

        private void updateWidth(int stringLength) {
            maxColWidth = Math.max(stringLength, maxColWidth);
        }
    }

    public static class Builder {
        private static final String DEFAULT_SEPARATOR = " | ";

        private Alignment[] alignmentArray = new Alignment[0];
        private String separator = DEFAULT_SEPARATOR;

        private Function<Object, String> converter;
        private Function<Integer, String> emptyCellValue = any -> EMPTY;

        public Builder alignments(Alignment... alignments) {
            alignmentArray = alignments;
            return this;
        }

        public Builder separator(String sep) {
            separator = sep;
            return this;
        }

        public Builder converter(Function<Object, String> stringConverter) {
            converter = stringConverter;
            return this;
        }

        public Builder emptyCell(Function<Integer, String> emptyCellValueProvider) {
            emptyCellValue = emptyCellValueProvider;
            return this;
        }

        public StringGrid build() {
            if (converter == null) {
                throw new IllegalStateException("Converter function must be specified");
            }
            return new StringGrid(separator, converter, emptyCellValue, alignmentArray);
        }
    }
}