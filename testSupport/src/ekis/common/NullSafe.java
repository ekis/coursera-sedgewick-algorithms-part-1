package ekis.common;

import java.util.Collection;
import java.util.List;

public final class NullSafe {

    private NullSafe() {
        // Do nothing
    }

    public static String toStringOrNull(Object o) {
        return null == o ? null : o.toString();
    }

    public static String toString(Object o) {
        return toString(o, ""); //$NON-NLS-1$
    }

    private static String toString(Object o, String nullValue) {
        if (o == null) return nullValue;

        return o.toString();
    }

    public static String trim(String value) {
        if (value == null) return null;

        return value.trim();
    }

    public static <T> boolean equals(T o1, T o2) {
        if (o1 == o2) return true;

        return o1 != null && o1.equals(o2);

    }

    public static boolean equalsIgnoreCase(String s1, String s2) {
        if (s1 == s2) return true;

        return s1 != null && s1.equalsIgnoreCase(s2);

    }

    public static <T extends Comparable<? super T>> int compare(T o1, T o2) {
        if (null == o1) return null == o2 ? 0 : -1;
        if (null == o2) return 1;
        return o1.compareTo(o2);
    }

    public static int compareIgnoreCase(String o1, String o2) {
        if (null == o1) return null == o2 ? 0 : -1;
        if (null == o2) return 1;
        return o1.compareToIgnoreCase(o2);
    }

    public static int hashCode(Object object) {
        return null == object ? 0 : object.hashCode();
    }

    public static Double add(Double number1, Double number2) {
        if (null == number1) return number2;
        if (null == number2) return number1;
        return number1 + number2;
    }

    public static Double multiply(Double number1, Double number2) {
        if (null == number1) return null;
        if (null == number2) return null;
        return number1 * number2;
    }

    @SafeVarargs
    public static <T> T coalesce(T... items) {
        for (final T item : items) {
            if (item != null) return item;
        }
        return null;
    }

    @SafeVarargs
    public static <T> void assertNotNull(T... args) {
        for (T arg : args)
            if (arg == null) {
                throw new IllegalArgumentException("Argument must not be null."); //$NON-NLS-1$
            }
    }

    public static <T> boolean isCollectionNullOrEmpty(Collection<T> c) {
        return c == null || c.isEmpty();
    }

    public static <T> T firstElementOrDefaultFrom(List<T> list, T defaultElem) {
        T item;
        if (defaultElem == null) throw new IllegalStateException("Default element must be specified."); //$NON-NLS-1$
        if (list == null || list.size() != 1) item = defaultElem;
        else item = list.get(0);

        return item;
    }

    public static String toUpperCase(String value) {
        if (value == null) return null;
        return value.toUpperCase();
    }

    public static void closeUnchecked(AutoCloseable closeable) {
        if (closeable == null) return;

        try {
            closeable.close();
        } catch (final Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static <T> T nullWhen(T value, T toReplaceWithNull) {
        return equals(value, toReplaceWithNull) ? null : value;
    }
}