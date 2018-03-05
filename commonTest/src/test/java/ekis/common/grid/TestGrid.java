package ekis.common.grid;

import ekis.common.Pair;

import java.util.Comparator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class TestGrid {
    private static final String EMPTY = "";
    private static final String CRLF = "\n";

    private final Matrix matrix;
    private final Map<Integer, ColumnAligner> columnMeta;
    private final String separator;

    private final Function<Object, String> converter;

    private TestGrid(String sep, Function<Object, String> converterF, BiFunction<Integer, Integer, String> emptyCellF, Alignment... alignments) {
        matrix = new Matrix(emptyCellF);
        separator = sep;
        converter = converterF;
        columnMeta = Stream.iterate(0, x -> x + 1)
                .limit(alignments.length)
                .map(col -> Pair.of(col, new ColumnAligner(alignments[col])))
                .collect(Collectors.toMap(Pair::x, Pair::y));
    }

    public static TestGrid defaultStringGrid() {
        return builder().build();
    }

    public static TestGrid.Builder builder() {
        return new Builder();
    }

    public TestGrid column(Object... values) {
        Stream.iterate(0, x -> x + 1)
                .limit(values.length)
                .forEachOrdered(idx -> {
                    Integer maxRow = maxRow();
                    if (maxRow == -1) {
                        insertColumnValueInEmptyMatrix(values[idx]);
                    } else if (maxRow == 0 || lastCellCoordinates().y() < maxCol()) {
                        insertColumnValueToCurrentRow(values[idx]);
                    } else {
                        insertColumnValueToNextRow(maxRow + 1, values[idx]);
                    }
                });
        return this;
    }

    public TestGrid row(Object... values) {
        Integer rowLength = values.length;
        Integer endIndex = Math.max(rowLength, maxUsedCol());
        Integer newRow = maxRow() + 1;
        Stream.iterate(0, x -> x + 1)
                .limit(endIndex)
                .forEachOrdered(index -> {
                    insert(newRow, index, index < rowLength ? values[index] : null);
                });
        return this;
    }

    public String show() {
        StringBuilder sb = new StringBuilder();
        int maxRow = maxRow();
        int maxCol = maxCol();
        for (int row = 0; row <= maxRow; row++) {
            for (int col = 0; col <= maxCol; col++) {
                ColumnAligner columnAligner = getAligner(col);
                String value = matrix.get(Pair.of(row, col));
                updateColumnWidth(col, value.length()); // we need to update the width here because of the default cell values
                sb.append(columnAligner.align(value));
                if (col != maxCol) {
                    sb.append(separator);
                }
            }
            sb.append(CRLF);
        }
        return sb.toString();
    }

    private void insertColumnValueInEmptyMatrix(Object value) {
        insert(0, 0, value);
    }

    private void insertColumnValueToCurrentRow(Object value) {
        Pair<Integer, Integer> lastCell = lastCellCoordinates();
        insert(lastCell.x(), lastCell.y() + 1, value);
    }

    private void insertColumnValueToNextRow(Integer nextRow, Object value) {
        insert(nextRow, 0, value);
    }

    private void insert(Integer row, Integer col, Object value) {
        int stringLength = put(row, col, value);
        updateColumnWidth(col, stringLength);
    }

    private void updateColumnWidth(Integer currentCol, int strLen) {
        ColumnAligner columnAligner = getAligner(currentCol);
        columnAligner.updateWidth(strLen);
        columnMeta.put(currentCol, columnAligner);
    }

    private ColumnAligner getAligner(int column) {
        return columnMeta.getOrDefault(column, new ColumnAligner(Alignment.LEFT));
    }

    private int put(Integer row, Integer column, Object value) {
        String convertedValue = value == null ? null : converter.apply(value);
        matrix.put(Pair.of(row, column), convertedValue);
        return value == null ? 0 : convertedValue.length();
    }

    private Integer maxRow() {
        return lastCellCoordinates().x();
    }

    private Integer maxCol() {
        return matrix.maxCol();
    }

    private Integer maxUsedCol() {
        // this handles a corner case where client erroneously specifies more alignment cols than are actually used
        // in rare circumstances (row() followed immediately by column()) the inserted column value would end up one cell behind
        // so we have to push the value one column forward
        return Math.min(maxCol() + 1, columnMeta.size());
    }

    private Pair<Integer, Integer> lastCellCoordinates() {
        Map.Entry<Pair<Integer, Integer>, String> lastCell = matrix.lastEntry();
        return lastCell == null ? nullMatrixCell() : lastCell.getKey();
    }

    private static Pair<Integer, Integer> nullMatrixCell() {
        return Pair.of(-1, -1);
    }

    private static class Matrix {
        private final NavigableMap<Pair<Integer, Integer>, String> map = new TreeMap<>(pairComparator());
        private final BiFunction<Integer, Integer, String> emptyCell;
        private int maxCol = -1;

        private Matrix(BiFunction<Integer, Integer, String> emptyCellF) {
            emptyCell = emptyCellF;
        }

        String get(Pair<Integer, Integer> cellCoordinates) {
            String value = map.get(cellCoordinates);
            return value == null ? emptyCell.apply(cellCoordinates.x(), cellCoordinates.y()) : value;
        }

        void put(Pair<Integer, Integer> cellCoordinate, String value) {
            map.put(cellCoordinate, value);
            maxCol = Math.max(cellCoordinate.y(), maxCol);
        }

        Map.Entry<Pair<Integer, Integer>, String> lastEntry() {
            return map.lastEntry();
        }

        Integer maxCol() {
            return maxCol;
        }

        private static Comparator<Pair<Integer, Integer>> pairComparator() {
            return (left, right) -> left.x().compareTo(right.x()) == 0
                    ? left.y().compareTo(right.y())
                    : left.x().compareTo(right.x());
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

        private Function<Object, String> converter = String::valueOf;
        private BiFunction<Integer, Integer, String> emptyCellValue = (row, col) -> EMPTY;

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

        public Builder emptyCell(BiFunction<Integer, Integer, String> emptyCellValueProvider) {
            emptyCellValue = emptyCellValueProvider;
            return this;
        }

        public TestGrid build() {
            return new TestGrid(separator, converter, emptyCellValue, alignmentArray);
        }
    }
}