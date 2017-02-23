package ekis.common;

import java.util.Locale;

public final class OldPair<A, B> {

    private final A _a;
    private final B _b;

    private OldPair(A a, B b) {
        _a = a;
        _b = b;
    }

    public static <A, B> OldPair<A, B> of(A a, B b) {
        return new OldPair<>(a, b);
    }

    public A a() {
        return _a;
    }

    public B b() {
        return _b;
    }

    @Override
    public int hashCode() {
        return NullSafe.hashCode(_a) * 31 + NullSafe.hashCode(_b);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final OldPair<?, ?> other = (OldPair<?, ?>) obj;
        return NullSafe.equals(_a, other._a) && NullSafe.equals(_b, other._b);
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "(%s, %s)", _a, _b); //$NON-NLS-1$
    }
}