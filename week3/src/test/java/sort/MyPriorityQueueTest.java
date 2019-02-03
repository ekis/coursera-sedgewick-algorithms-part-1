package sort;

import sort.heapsort.MyMaxPQ;
import org.junit.Test;

import java.util.Random;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public final class MyPriorityQueueTest {

    @Test
    public void testMaxPriorityQueue() {
        MyMaxPQ<String> queue = MyMaxPQ.create();
        assertTrue(queue.isEmpty());
        queue.insert("P", "Q", "E");
        assertEquals("Q", queue.delMax());
        queue.insert("X", "A", "M");
        assertEquals("X", queue.delMax());
        queue.insert("P", "L", "E");
        assertEquals("P", queue.delMax());
        queue.clear();
        assertTrue(queue.isEmpty());
        queue.insert("F", "R", "A", "S", "G");
        assertEquals("S", queue.delMax());
        queue.insert("Z", "X", "B", "R", "J");
        assertEquals("Z", queue.delMax());
        assertEquals("X", queue.delMax());
        queue.insert("B", "E", "B", "N", "O");
        assertEquals("R", queue.delMax());
        assertEquals("R", queue.delMax());
        assertEquals("O", queue.delMax());
        assertEquals(9, queue.size());
        assertEquals("N", queue.delMax());
        assertEquals("J", queue.delMax());
        assertEquals("G", queue.delMax());
        assertEquals("F", queue.delMax());
        assertEquals("E", queue.delMax());
        assertEquals("B", queue.delMax());
        assertEquals("B", queue.delMax());
        assertEquals("B", queue.delMax());
        assertEquals("A", queue.delMax());
        assertTrue(queue.isEmpty());
    }

    @Test
    public void testMaxPriorityQueueRandom() {
        Random rnd = new Random();
        String[] alphabet = "ABCDEFGHIJKLMNOPQRSTUV".split(""); // WXYZ missing
        int startSize = 10000000; // 10^7 elements
        // take random string from alphabet and insert it into the queue; there are 22 letters in our alphabet
        MyMaxPQ<String> queue = IntStream.range(0, startSize)
                .mapToObj(x -> randomCharIn(alphabet, rnd))
                .collect(MyMaxPQ.collectorOf(MyMaxPQ::insert));
        queue.insert("W", "Z", "X", "Y");
        queue.insert(IntStream.range(0, startSize)
                .mapToObj(x -> randomCharIn(alphabet, rnd))
                .toArray(String[]::new));
        assertEquals("Z", queue.delMax());
        assertEquals("Y", queue.delMax());
        assertEquals("X", queue.delMax());
        assertEquals("W", queue.delMax());
        assertEquals(2 * startSize, queue.size());
    }

    private static String randomCharIn(String[] alphabet, Random rnd) {
        return alphabet[rnd.nextInt(22)];
    }
}