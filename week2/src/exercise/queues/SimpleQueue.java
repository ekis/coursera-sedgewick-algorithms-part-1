package exercise.queues;

import java.util.Iterator;

/**
 * Created by ekis on 14/12/16.
 */
class SimpleQueue<T> implements MyQueue<T> {

    @Override
    public void enqueue(T item) {

    }

    @Override
    public T dequeue() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }
}
