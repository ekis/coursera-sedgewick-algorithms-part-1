import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

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
        int hi = 10;

        Set<Integer> expected = new HashSet<>();
        RandomizedQueue<Integer> q = new RandomizedQueue<>();
        IntStream.rangeClosed(lo, hi).forEach(q::enqueue);

        assertEquals(hi, q.size());
        assertFalse(q.isEmpty());

        while (!q.isEmpty())
            expected.add(q.dequeue());

        IntStream.rangeClosed(lo, hi).forEach(expected::contains);
        assertEquals(0, q.size());
        assertTrue(q.isEmpty());
    }

    @Test
    public void testFillEmptyFill() {
        int testRuns = 15;
        RandomizedQueue<Integer> q = new RandomizedQueue<>();

        for (int i = 0; i <= testRuns; i++) {
            int lo = 1;
            int hi = (int) Math.pow(2, i);
            HashSet<Integer> set = IntStream.rangeClosed(lo, hi)
                    .map(x -> {
                        q.enqueue(x);
                        return x;
                    }).boxed().collect(Collectors.toCollection(HashSet::new));
            assertTrue(IntStream.rangeClosed(lo, hi).filter(x -> !set.contains(q.dequeue())).count() == 0);
        }
    }

    @Test
    public void testSingleIterator() {
        int testRuns = 5;
        RandomizedQueue<Integer> q = new RandomizedQueue<>();

        for (int i = 0; i <= testRuns; i++) {
            int lo = 1;
            int hi = (int) Math.pow(3, i);
            HashSet<Integer> set = IntStream.rangeClosed(lo, hi)
                    .map(x -> {
                        q.enqueue(x);
                        return x;
                    }).boxed().collect(Collectors.toCollection(HashSet::new));
            Set<Integer> result = StreamSupport.stream(Spliterators.spliteratorUnknownSize(q.iterator(), Spliterator.DISTINCT), false).collect(Collectors.toCollection(LinkedHashSet::new));
            assertTrue(result.size() == set.size());
            assertTrue(set.containsAll(result));
            set.forEach(x -> q.dequeue());
        }
    }

    @Test
    public void testMultipleIterators() {
        int testRuns = 5;
        RandomizedQueue<Integer> q = new RandomizedQueue<>();

        for (int i = 0; i <= testRuns; i++) {
            int lo = 1;
            int hi = (int) Math.pow(3, i);
            HashSet<Integer> set = IntStream.rangeClosed(lo, hi)
                    .map(x -> {
                        q.enqueue(x);
                        return x;
                    }).boxed().collect(Collectors.toCollection(HashSet::new));
            Set<Integer> result = StreamSupport.stream(Spliterators.spliteratorUnknownSize(q.iterator(), Spliterator.DISTINCT), false).collect(Collectors.toCollection(LinkedHashSet::new));
            for (int j = 0; j < i; j++) {
                Set<Integer> result2 = StreamSupport.stream(Spliterators.spliteratorUnknownSize(q.iterator(), Spliterator.DISTINCT), false).collect(Collectors.toCollection(LinkedHashSet::new));
                assertTrue(result2.size() == set.size());
                assertTrue(set.containsAll(result2));
            }
            assertTrue(result.size() == set.size());
            assertTrue(set.containsAll(result));
            set.forEach(x -> q.dequeue());
        }
    }
}
