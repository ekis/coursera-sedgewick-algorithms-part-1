package ekis.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class PatternMatch {

    private final Pattern _pattern;

    public PatternMatch(String simplePattern) {
        this(false, simplePattern);
    }

    public PatternMatch(boolean regex, String pattern) {
        _pattern = Pattern.compile(regex ? pattern : simplePatternToRegex(pattern));
    }

    private static String simplePatternToRegex(String simplePattern) {
        StringBuilder pattern = new StringBuilder();
        boolean inEscape = false;
        for (int i = 0; i < simplePattern.length(); ++i) {
            char ch = simplePattern.charAt(i);
            switch (ch) {
                case '?':
                    inEscape = endEscape(pattern, inEscape);
                    pattern.append("(\\S+)"); //$NON-NLS-1$
                    break;
                case '*':
                    inEscape = endEscape(pattern, inEscape);
                    pattern.append(".*"); //$NON-NLS-1$
                    break;
                case ' ':
                    inEscape = endEscape(pattern, inEscape);
                    pattern.append("\\s*"); //$NON-NLS-1$
                    break;
                default:
                    if (!inEscape) {
                        inEscape = true;
                        pattern.append("\\Q"); //$NON-NLS-1$
                    }
                    pattern.append(ch);
                    break;
            }

        }
        endEscape(pattern, inEscape);
        return pattern.toString();
    }

    private static boolean endEscape(StringBuilder builder, boolean inEscape) {
        if (inEscape) {
            builder.append("\\E"); //$NON-NLS-1$
        }
        return false;
    }

    public static <T> T dispatch(String string, PatternMatch... handlers) {
        for (PatternMatch handler : handlers) {
            Matcher matcher = handler._pattern.matcher(string);
            if (matcher.matches()) return handler.result(matcher);
        }
        throw new IllegalStateException("no match: " + string); //$NON-NLS-1$
    }

    private <T> T result(Matcher matcher) {
        Method method = findMatchMethod();
        Class<?>[] parameterTypes = method.getParameterTypes();
        Object[] parameters = new Object[parameterTypes.length];
        int p = 0;
        for (Class<?> type : parameterTypes) {
            if (String.class == type) {
                parameters[p] = matcher.group(++p);
            } else if (Integer.TYPE == type) {
                parameters[p] = Integer.valueOf(matcher.group(++p));
            } else if (Double.TYPE == type) {
                parameters[p] = Double.valueOf(matcher.group(++p));
            } else {
                String message = String.format("%s: %s", //$NON-NLS-1$
                        String.valueOf(type), matcher.group(++p));
                throw new IllegalArgumentException(message);
            }
        }
        try {
            method.setAccessible(true);
            @SuppressWarnings("unchecked") T result = (T) method.invoke(this, parameters);
            return result;
        } catch (InvocationTargetException ex) {
            throw new RuntimeException(ex.getTargetException());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private Method findMatchMethod() {
        for (Method method : getClass().getMethods()) {
            if ("match".equals(method.getName())) { //$NON-NLS-1$
                return method;
            }
        }
        throw new IllegalStateException("cannot find 'match' method"); //$NON-NLS-1$
    }
}
