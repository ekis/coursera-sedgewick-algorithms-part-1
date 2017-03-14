package elementary.datastructures.queues;

import elementary.datastructures.stacks.MyStack;
import elementary.datastructures.stacks.Stacks;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

class TwoStackQueue<T> implements MyQueue<T> {
    private MyStack<T> inbox = Stacks.simpleStack();
    private MyStack<T> outbox = Stacks.simpleStack();
    private int count = 0;

    @Override
    public void enqueue(T item) {
        inbox.push(item);
        count++;
    }

    @Override
    public T dequeue() {
        if (isEmpty())
            throw new NoSuchElementException("Queue is empty");
        if (outbox.isEmpty())
            transferInboxToOutbox();
        count--;
        return outbox.pop();
    }

    private void transferInboxToOutbox() {
        while (!inbox.isEmpty())
            outbox.push(inbox.pop());
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
            private LinkedList<T> elements = iteratorElements();

            @Override
            public boolean hasNext() {
                return !elements.isEmpty();
            }

            @Override
            public T next() {
                return elements.removeFirst();
            }

            private LinkedList<T> iteratorElements() {
                LinkedList<T> result = StreamSupport.stream(outbox.spliterator(), false)
                        .collect(Collectors.toCollection(LinkedList::new)); // outbox elements are already in proper order, so just transfer them to result list
                Iterator<T> inboxDescIterator = StreamSupport.stream(inbox.spliterator(), false)
                        .collect(Collectors.toCollection(ArrayDeque::new))
                        .descendingIterator(); // inbox elements need to be reversed but that is not trivially done for general types and requires intermediate storage.
                                                // ArrayDeque just happens to have the descending iterator we want - which is better than Collections.reverse()...
                                                // ...as that entails another pass through the inbox collection just to reverse it; we get that here 'for free'
                                                // There exists a quick and efficient solution for Integer type using IntStream.range(from, to)
                inboxDescIterator.forEachRemaining(result::add);
                return result;
            }
        };
    }
}
