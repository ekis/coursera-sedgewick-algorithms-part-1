package exercise.stacks;

import java.util.Comparator;

/**
 * Created by ekis on 14/12/16.
 */
public class Stacks {

    public static <T> MyStack<T> simpleStack() {
        return new SimpleStack<>();
    }

    public static <T> MyStack<T> stackWithMax(Comparator<T> comparator) {
        return new StackWithMaximum<>(comparator);
    }
}