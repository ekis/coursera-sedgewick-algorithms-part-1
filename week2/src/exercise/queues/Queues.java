package exercise.queues;

/**
 * Created by ekis on 14/12/16.
 */
public class Queues {

    public static <T> MyQueue<T> simpleQueue() {
        return new SimpleQueue<>();
    }

    public static <T> MyQueue<T> twoStackQueue() {
        return new TwoStackQueue<>();
    }
}
