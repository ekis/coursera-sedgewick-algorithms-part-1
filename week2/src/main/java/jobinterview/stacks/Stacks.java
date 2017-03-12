package jobinterview.stacks;

import java.util.Comparator;

public class Stacks {

    public static <T> MyStack<T> simpleStack() {
        return new SimpleStack<>();
    }

    public static <T> MyStack<T> stackWithMax(Comparator<T> comparator) {
        return new StackWithMaximum<>(comparator);
    }
}