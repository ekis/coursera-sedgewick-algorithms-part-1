package elementary.datastructures.linkedlist;


import java.util.Optional;

public class CyclicLinkedList<T> {

    private Builder.Node<T> first;
    private int count = 0;

    private CyclicLinkedList() {}

    public static <T> AddNext<T> builder() {
        return new CyclicLinkedList.Builder<>();
    }

    /**
     * Finds repeated value on the stack.
     * Worst-case complexity to retrieve repeated value is O(2N): O(n) to detect cycle and O(n) to retrieve it.
     * Space complexity is O(1).
     *
     * @return repeated value on the stack. If there is none, returns null.
     */
    public Optional<T> firstNodeInCycle() {
        if (count < 2) return Optional.empty();
        Builder.Node<T> slow = first;
        Builder.Node<T> fast = first;

        do {
            if (fast == null || fast.next == null) return Optional.empty(); // if fast reaches end, no cycle
            fast = fast.next.next; // advance fast two steps and slow one step
            slow = slow.next;
            if (fast == null) return Optional.empty(); // again check if fast reached end - if it does, no cycle
        } while (fast.index != slow.index);

        // there is definitely cycle in the linked list, we have to find its start
        slow = first;
        while (fast.index != slow.index) { // find the starting point of the cycle
            slow = slow.next;
            fast = fast.next;
        }

        return Optional.of(slow.item);
    }

    private boolean isEmpty() {
        return count == 0;
    }

    public interface AddNext<T> {
        AddNext<T> addNext(T item);

        Build<T> defineCycleOrBuild();
    }

    public interface Build<T> {
        Build<T> connectLastTo(int index);

        CyclicLinkedList<T> build();
    }

    private static class Builder<T> implements AddNext<T>, Build<T> {
        private CyclicLinkedList<T> instance = new CyclicLinkedList<>();

        private static class Node<Item> {
            private final int index;
            private final Item item;
            private Node<Item> next;

            private Node(int idx, Item nestedItem, Node<Item> nextNode) {
                index = idx;
                item = nestedItem;
                next = nextNode;
            }

            private static <V> Node<V> firstNodeOf(V item) {
                return new Node<>(0, item, null);
            }

            private static <V> Node<V> nextNodeOf(V item, Node<V> nextNode) {
                return new Node<>(nextNode.index + 1, item, nextNode);
            }
        }

        @Override
        public AddNext<T> addNext(T item) {
            Node<T> oldFirst = instance.first;

            if (instance.isEmpty())
                instance.first = Node.firstNodeOf(item);
            else
                instance.first = Node.nextNodeOf(item, oldFirst);
            instance.count++;

            return this;
        }

        @Override
        public Build<T> defineCycleOrBuild() {
            return this;
        }

        @Override
        public Build<T> connectLastTo(int index) {
            if (instance.isEmpty()) throw new IllegalStateException("Stack underflow");
            Node<T> node = findAt(index);
            Node<T> last = findLast();
            last.next = node;
            return this;
        }

        @Override
        public CyclicLinkedList<T> build() {
            return instance;
        }

        private Node<T> findAt(int index) {
            for (Node<T> x = instance.first; x != null; x = x.next)
                if (x.index == index) return x;
            throw new IllegalStateException(String.format("Cannot find node at index %s", index));
        }

        private Node<T> findLast() {
            for (Node<T> x = instance.first; x != null; x = x.next)
                if (x.next == null) return x;
            throw new IllegalStateException("Cannot find last node - malformed linked list");
        }
    }
}
