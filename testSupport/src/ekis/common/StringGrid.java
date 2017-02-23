package ekis.common;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import static ekis.common.NullSafe.coalesce;

public final class StringGrid {

    private final Map<OldPair<Integer, Integer>, String> _grid = new LinkedHashMap<>();
    private final Map<Integer, Alignment> _alignments = new HashMap<>();
    private int _row = -1;
    private int _column = 0;
    private int _columnCount = 0;
    private String _separator = "  ";
    private Alignment _defaultAlignment = Alignment.DECIMAL;

    public StringGrid alignments(Alignment... alignments) {
        _alignments.clear();
        for (int i = 0; i < alignments.length; ++i) {
            _alignments.put(i, alignments[i]);
        }
        return this;
    }

    public StringGrid defaultAlignment(Alignment alignment) {
        _defaultAlignment = alignment;
        return this;
    }

    public StringGrid separator(String separator) {
        _separator = separator;
        return this;
    }

    public StringGrid column(Object... values) {
        if (-1 == _row) {
            row();
        }
        for (Object value : values) {
            _grid.put(OldPair.of(_row, _column), String.valueOf(value));
            ++_column;
            _columnCount = Math.max(_columnCount, _column);
        }
        return this;
    }

    public StringGrid format(String format, Object... values) {
        column(String.format(Locale.US, format, values));
        return this;
    }

    public StringGrid row(Object... values) {
        ++_row;
        _column = 0;
        column(values);
        return this;
    }

    public String show() {
        Map<OldPair<Integer, Integer>, String> grid = new LinkedHashMap<>(_grid);
        int rowCount = _row + 1;
        for (int column = 0; column < _columnCount; ++column) {
            Alignment alignment = coalesce(_alignments.get(column), _defaultAlignment);
            alignment.align(grid, column, rowCount);
        }
        return rowMajorGrid(new StringBuilder(), grid, rowCount);
    }

    public String showTransposed() {
        Map<OldPair<Integer, Integer>, String> grid = new LinkedHashMap<>(_grid);
        int rowCount = _row + 1;
        for (int row = 0; row < rowCount; ++row) {
            Alignment alignment = coalesce(_alignments.get(row), _defaultAlignment);
            alignment.align(grid, row, rowCount);
        }
        return columnMajorGrid(new StringBuilder(), grid, rowCount);
    }

    private String rowMajorGrid(StringBuilder sb, Map<OldPair<Integer, Integer>, String> grid, int rowCount) {
        for (int row = 0; row < rowCount; ++row) {
            for (int column = 0; column < _columnCount; ++column) {
                if (0 < column) {
                    sb.append(_separator);
                }
                sb.append(grid.get(OldPair.of(row, column)));
            }
            sb.append("\n"); //$NON-NLS-1$
        }
        return sb.toString();
    }

    private String columnMajorGrid(StringBuilder sb, Map<OldPair<Integer, Integer>, String> grid, int rowCount) {
        for (int column = 0; column < _columnCount; ++column) {
            for (int row = 0; row < rowCount; ++row) {
                if (0 < row) {
                    sb.append(_separator);
                }
                sb.append(grid.get(OldPair.of(row, column)));
            }
            sb.append("\n"); //$NON-NLS-1$
        }
        return sb.toString();
    }

    public enum Alignment {
        LEFT {
            @Override
            String align(String value, int width, int decimalLeft, int decimalRight) {
                return pad(value, 0, width - value.length());
            }
        },

        CENTER {
            @Override
            String align(String value, int width, int decimalLeft, int decimalRight) {
                int amount = width - value.length();
                return pad(value, amount / 2, amount - amount / 2);
            }
        },

        RIGHT {
            @Override
            String align(String value, int width, int decimalLeft, int decimalRight) {
                return pad(value, width - value.length(), 0);
            }
        },

        DECIMAL {
            @Override
            String align(String value, int width, int decimalLeft, int decimalRight) {
                int decimal = value.indexOf("."); //$NON-NLS-1$
                if (-1 == decimal) {
                    return CENTER.align(value, decimalLeft + decimalRight, 0, 0);
                }
                return pad(value, decimalLeft - decimal, decimalRight - (value.length() - decimal));

            }
        };

        private static String pad(String value, int left, int right) {
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < left; ++i) {
                result.append(" "); //$NON-NLS-1$
            }
            result.append(value);
            for (int i = 0; i < right; ++i) {
                result.append(" "); //$NON-NLS-1$
            }
            return result.toString();
        }

        void align(Map<OldPair<Integer, Integer>, String> grid, int column, int rowCount) {
            int width = 0;
            int decimalLeft = 0;
            int decimalRight = 0;
            for (int row = 0; row < rowCount; ++row) {
                String value = coalesce(grid.get(OldPair.of(row, column)), ""); //$NON-NLS-1$
                width = Math.max(width, value.length());
                int decimal = value.indexOf('.');
                if (-1 != decimal) {
                    decimalLeft = Math.max(decimalLeft, decimal);
                    decimalRight = Math.max(decimalRight, value.length() - decimal);
                }
            }
            int excess = Math.max(0, width - (decimalLeft + decimalRight));
            decimalLeft += excess / 2;
            decimalRight += excess - excess / 2;

            for (int row = 0; row < rowCount; ++row) {
                OldPair<Integer, Integer> rc = OldPair.of(row, column);
                String value = coalesce(grid.get(rc), ""); //$NON-NLS-1$
                String alignedValue = align(value, width, decimalLeft, decimalRight);
                grid.put(rc, alignedValue);
            }
        }

        abstract String align(String value, int width, int decimalLeft, int decimalRight);

    }

}