package elementary.datastructures;

import elementary.datastructures.queues.MyQueue;
import elementary.datastructures.queues.Queues;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class QueueTests {

    @Test
    public void testSimpleQueue() {
        test(Queues.simpleQueue());
    }

    @Test
    public void testTwoStackQueue() {
        test(Queues.twoStackQueue());
    }

    private static void test(MyQueue<Integer> queue) {
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        Week2TestHelper.testCollectionState("1, 2, 3, 4", queue);
        assertEquals(Integer.valueOf(1), queue.dequeue());
        assertEquals(Integer.valueOf(2), queue.dequeue());
        assertEquals(Integer.valueOf(3), queue.dequeue());
        assertEquals(Integer.valueOf(4), queue.dequeue());
        assertEquals(0, queue.size());
        queue.enqueue(1);
        assertEquals(1, queue.size());
        queue.dequeue();
        queue.enqueue(1);
        queue.enqueue(2);
        assertEquals(2, queue.size());
        queue.dequeue();
        queue.dequeue();
        queue.enqueue(10);
        queue.enqueue(20);
        queue.enqueue(30);
        queue.enqueue(40);
        assertEquals(4, queue.size());
        Week2TestHelper.testCollectionState("10, 20, 30, 40", queue);
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
        queue.enqueue(1);
        queue.dequeue();
        queue.enqueue(1);
        queue.dequeue();
        queue.enqueue(1);
        queue.dequeue();
        Week2TestHelper.testCollectionState("", queue);
    }
}
