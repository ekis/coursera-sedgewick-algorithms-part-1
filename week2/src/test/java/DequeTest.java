import org.junit.Test;

import java.util.function.BiConsumer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DequeTest {

    @Test
    public void testIsEmpty() {
        Deque<Integer> deque = new Deque<>();
        assertTrue(deque.isEmpty());
    }

    @Test
    public void testAddFirstRemoveFirst() {
        int size = 4;
        Deque<Integer> d = add(size, Deque::addFirst);
        assertEquals(1, d.removeFirst().intValue());
        assertEquals(3, d.size());
        assertEquals(2, d.removeFirst().intValue());
        assertEquals(2, d.size());
        assertEquals(3, d.removeFirst().intValue());
        assertEquals(1, d.size());
        assertEquals(4, d.removeFirst().intValue());
        assertTrue(d.isEmpty());
    }

    @Test
    public void testAddFirstRemoveLast() {
        int size = 4;
        Deque<Integer> d = add(size, Deque::addFirst);
        assertEquals(4, d.removeLast().intValue());
        assertEquals(3, d.size());
        assertEquals(3, d.removeLast().intValue());
        assertEquals(2, d.size());
        assertEquals(2, d.removeLast().intValue());
        assertEquals(1, d.size());
        assertEquals(1, d.removeLast().intValue());
        assertTrue(d.isEmpty());
    }

    @Test
    public void testAddFirstRemoveLastFromChecklist() {
        Deque<Integer> d = new Deque<>();
        d.addFirst(1);
        d.addFirst(2);
        d.addFirst(3);
        d.addFirst(4);
        assertEquals(1, d.removeLast().intValue());
        assertEquals(2, d.removeLast().intValue());
        assertEquals(3, d.removeLast().intValue());
        assertEquals(4, d.removeLast().intValue());
        assertTrue(d.isEmpty());
    }

    @Test
    public void testAddLastRemoveFirst() {
        int size = 4;
        Deque<Integer> d = add(size, Deque::addLast);
        assertEquals(4, d.removeFirst().intValue());
        assertEquals(3, d.size());
        assertEquals(3, d.removeFirst().intValue());
        assertEquals(2, d.size());
        assertEquals(2, d.removeFirst().intValue());
        assertEquals(1, d.size());
        assertEquals(1, d.removeFirst().intValue());
        assertTrue(d.isEmpty());
    }

    @Test
    public void testAddLastRemoveLast() {
        int size = 4;
        Deque<Integer> d  = add(size, Deque::addLast);
        assertEquals(1, d.removeLast().intValue());
        assertEquals(3, d.size());
        assertEquals(2, d.removeLast().intValue());
        assertEquals(2, d.size());
        assertEquals(3, d.removeLast().intValue());
        assertEquals(1, d.size());
        assertEquals(4, d.removeLast().intValue());
        assertTrue(d.isEmpty());
    }

    @Test
    public void testAddRemoveInterleaved() {
        Deque<Integer> d  = add(4, Deque::addLast); // 4 3 2 1
        d.addLast(5); // 4 3 2 1 5
        d.addFirst(6); // 6 4 3 2 1 5
        d.addLast(7); // 6 4 3 2 1 5 7
        d.addLast(0); // 6 4 3 2 1 5 7 0
        assertEquals(6, d.removeFirst().intValue()); // 4 3 2 1 5 7 0
        assertEquals(0, d.removeLast().intValue()); // 4 3 2 1 5 7
        assertEquals(6, d.size());
        assertEquals(7, d.removeLast().intValue()); // 4 3 2 1 5
        assertEquals(4, d.removeFirst().intValue()); // 3 2 1 5
        assertEquals(4, d.size());
        assertEquals(5, d.removeLast().intValue()); // 3 2 1
        assertEquals(1, d.removeLast().intValue()); // 3 2
        assertEquals(3, d.removeFirst().intValue()); // 2
        assertEquals(1, d.size());
        assertEquals(2, d.removeLast().intValue()); // empty
        assertEquals(0, d.size());
    }

    @Test
    public void testIterator() {
        Deque<Integer> d  = add(4, Deque::addLast); // 4 3 2 1
        d.addLast(5); // 4 3 2 1 5
        d.addFirst(6); // 6 4 3 2 1 5
        d.addLast(7); // 6 4 3 2 1 5 7
        d.addLast(0); // 6 4 3 2 1 5 7 0

        StringBuilder sb = new StringBuilder();
        for (Integer x : d)
            sb.append(x).append(" ");

        assertEquals("6 4 3 2 1 5 7 0 ", sb.toString());
    }

    private static Deque<Integer> add(int size, BiConsumer<Deque<Integer>, Integer> f) {
        Deque<Integer> deque = new Deque<>();
        for (int i = size; i > 0; i--) {
            f.accept(deque, i);
        }
        assertEquals(deque.size(), size);
        return deque;
    }
}
