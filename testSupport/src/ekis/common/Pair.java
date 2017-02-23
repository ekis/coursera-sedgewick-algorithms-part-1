package ekis.common;

import java.util.Objects;

public final class Pair<T, V> {
    private final T x;
    private final V y;

    private Pair(T x, V y) {
        this.x = x;
        this.y = y;
    }

    public static <T, V> Pair of(T x, V y) {
        return new Pair<>(x, y);
    }

    public T x() {
        return x;
    }

    public V y() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(x, pair.x) &&
                Objects.equals(y, pair.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return String.format("(x, y) -> (%s, %s)", x, y);
    }
}