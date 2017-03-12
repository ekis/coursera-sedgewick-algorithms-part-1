package jobinterview.queues;

public class Queues {

    public static <T> MyQueue<T> simpleQueue() {
        return new SimpleQueue<>();
    }

    public static <T> MyQueue<T> twoStackQueue() {
        return new TwoStackQueue<>();
    }
}
