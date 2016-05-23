import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class RandomizedQueueTest {

    @Test
    public void testTrivial() {
        RandomizedQueue<Integer> q = new RandomizedQueue<>();
        assertEquals(0, q.size());
        assertTrue(q.isEmpty());
    }

    @Test
    public void testSimpleLoadUnload() {
        int lo = 1;
        int hi = 1000;

        Set<Integer> expected = new HashSet<>();
        RandomizedQueue<Integer> q = new RandomizedQueue<>();
        System.out.println("Loading queue...");
        IntStream.rangeClosed(lo, hi).forEach(q::enqueue);

        assertEquals(hi, q.size());
        assertFalse(q.isEmpty());

        System.out.println("Emptying queue...");
        while (!q.isEmpty())
            expected.add(q.dequeue());

        System.out.println("Checking queue...");
        IntStream.rangeClosed(lo, hi).forEach(expected::contains);
        assertEquals(0, q.size());
        assertTrue(q.isEmpty());
    }

    @Test
    public void testFillEmptyFill() {
        int testRuns = 15;
        RandomizedQueue<Integer> q = new RandomizedQueue<>();

        for (int i = 0; i <= testRuns; i++) {
            System.out.println("*** Running test -> " + i);
            int lo = 1;
            int hi = (int) Math.pow(3, i);
            HashSet<Integer> set = IntStream.rangeClosed(lo, hi).map(x -> {
                q.enqueue(x);
                return x;
            }).boxed().collect(Collectors.toCollection(HashSet::new));
            assertTrue(IntStream.rangeClosed(lo, hi).filter(x -> !set.contains(q.dequeue())).count() == 0);
        }
    }
}
