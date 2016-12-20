package exercise.queues;

import exercise.MyCollection;

/**
 * Created by ekis on 14/12/16.
 */
public interface MyQueue<T> extends MyCollection<T> {

    /**
     * Adds item to tail of queue.
     *
     * @param item to be added
     */
    void enqueue(T item);

    /**
     * Removes item from head of queue.
     *
     * @return item from head of queue
     */
    T dequeue();

}
