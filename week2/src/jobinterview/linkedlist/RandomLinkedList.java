package jobinterview.linkedlist;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

/**
 * Created by ekis on 20/12/16.
 */
public class RandomLinkedList<T> implements Iterable<String> {
    private static Map<Integer, Node<?>> cache = new HashMap<>();
    private Node<T> first;

    private int count = 0;
    private Random random = new Random();

    public void add(T item) {
        Node<T> oldFirst = first;
        Node<T> node;

        if (isEmpty())
            node = Node.firstNodeOf(item);
        else
            node = Node.nextNodeOf(item, oldFirst, getRandomNode());

        cache.put(node.index, node);
        first = node;
        count++;
    }

    public int size() {
        return count;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public RandomLinkedList<T> cloneLinkedList() {
        Node<T> clone = first.cloneNode();
        RandomLinkedList<T> clonedList = new RandomLinkedList<>();
        clonedList.first = clone;
        clonedList.count = count;
        return clonedList;
    }

    @SuppressWarnings("unchecked")
    private Node<T> getRandomNode() {
        Node<T> randomNode = (Node<T>) cache.get(random.nextInt(count));
        if (randomNode != null) return randomNode;
        throw new IllegalStateException("Cannot find random node.");
    }

    @Override
    // produces test output string
    public Iterator<String> iterator() {
        return new Iterator<String>() {
            private Node<T> current = first;
            private int N = count;

            @Override
            public boolean hasNext() {
                return N > 0;
            }

            @Override
            public String next() {
                if (!hasNext()) throw new IllegalStateException();
                String result = current.toString();
                current = current.next;
                N--;
                return result;
            }
        };
    }

    private static class Node<Item> {
        private int index;
        private Item item;
        private Node<Item> next;
        private Node<Item> random;

        private Node(int idx, Item payload, Node<Item> nextNode, Node<Item> randomNode) {
            index = idx;
            item = payload;
            next = nextNode;
            random = randomNode;
        }

        private static <V> Node<V> firstNodeOf(V item) {
            return new Node<>(0, item, null, null);
        }

        private static <V> Node<V> nextNodeOf(V item, Node<V> nextNode, Node<V> randomNode) {
            return new Node<>(nextNode.index + 1, item, nextNode, randomNode);
        }

        private static <V> Node<V> empty() {
            return new Node<>(-1, null, null, null);
        }

        private Node<Item> cloneNode() {
            if (next == null) return this;
            Node<Item> clone = Node.empty();
            clone.index = index;
            clone.item = item;
            clone.next = next.cloneNode();
            clone.random = random.cloneNode();
            return clone;
        }

        @Override
        public String toString() {
            return String.format("Node (index: %s, item: %s, next item: %s, random item: %s)", index, item, next == null ? "null" : next.item, random == null ? "null" : random.item);
        }
    }
}