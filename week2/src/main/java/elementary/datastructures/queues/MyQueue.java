package elementary.datastructures.queues;

import elementary.datastructures.MyCollection;

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
