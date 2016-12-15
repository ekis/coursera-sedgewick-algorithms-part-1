package exercise.queues;

import java.util.Iterator;

/**
 * Created by ekis on 14/12/16.
 */
class SimpleQueue<T> implements MyQueue<T> {

    private final Node<T> sentinel = Node.sentinel();
    private int count = 0;

    private static class Node<Item> {
        private final Item item;
        private Node<Item> prev;
        private Node<Item> next;

        private Node(Item newItem, Node<Item> prevNode, Node<Item> nextNode) {
            item = newItem;
            prev = prevNode;
            next = nextNode;
        }

        private static <V> Node<V> sentinel() {
            return new Node<>(null, null, null);
        }

        private static <V> Node<V> newNode(V item, Node<V> prev, Node<V> next) {
            return new Node<>(item, prev, next);
        }
    }

    @Override
    public void enqueue(T item) { // enqueue at the end of the queue
        if (count == 0) {
            Node<T> newNode = Node.newNode(item, sentinel, sentinel);
            sentinel.prev = newNode;
            sentinel.next = newNode;
        } else {
            Node<T> oldTailNode = sentinel.prev; // get current tail element in list
            Node<T> newTailNode = Node.newNode(item, oldTailNode, sentinel); // construct new tail element whose previous element is the old tail and next element is sentinel
            oldTailNode.next = newTailNode; // update old tail element's next pointer
            sentinel.prev = newTailNode; // update sentinel's previous element to point to new tail element
        }
        count++;
    }

    @Override
    public T dequeue() { // dequeue from start of the queue
        Node<T> result = sentinel.next; // take head of list
        sentinel.next = result.next; // update sentinel to the element following current head
        count--;
        return result.item;
    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            private Node<T> current = sentinel.next;
            private int N = count;

            @Override
            public boolean hasNext() {
                return N != 0;
            }

            @Override
            public T next() {
                T result = current.item;
                current = current.next;
                N--;
                return result;
            }
        };
    }
}
