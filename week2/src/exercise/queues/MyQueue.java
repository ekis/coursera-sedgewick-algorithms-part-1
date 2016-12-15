package exercise.queues;

import exercise.MyCollection;

/**
 * Created by ekis on 14/12/16.
 */
public interface MyQueue<T> extends MyCollection<T> {

    void enqueue(T item);

    T dequeue();

}
