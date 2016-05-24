import edu.princeton.cs.algs4.In;
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
            System.out.println("*** Running test -> " + i);
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
            System.out.println("Input  -> " + set.toString());
            System.out.println("Output -> " + result.toString());
            set.forEach(x -> q.dequeue());
        }
    }

    @Test
    public void testMultipleIterators() {
        int testRuns = 5;
        RandomizedQueue<Integer> q = new RandomizedQueue<>();

        for (int i = 0; i <= testRuns; i++) {
            System.out.println("*** Running iterator A test -> " + i);
            int lo = 1;
            int hi = (int) Math.pow(3, i);
            HashSet<Integer> set = IntStream.rangeClosed(lo, hi)
                    .map(x -> {
                        q.enqueue(x);
                        return x;
                    }).boxed().collect(Collectors.toCollection(HashSet::new));
            Set<Integer> result = StreamSupport.stream(Spliterators.spliteratorUnknownSize(q.iterator(), Spliterator.DISTINCT), false).collect(Collectors.toCollection(LinkedHashSet::new));
            for (int j = 0; j < i; j++) {
                System.out.println("    ***     Running iterator B test -> " + j);
                Set<Integer> result2 = StreamSupport.stream(Spliterators.spliteratorUnknownSize(q.iterator(), Spliterator.DISTINCT), false).collect(Collectors.toCollection(LinkedHashSet::new));
                assertTrue(result2.size() == set.size());
                assertTrue(set.containsAll(result2));
                System.out.println("    Input  -> " + set.toString());
                System.out.println("    Output -> " + result2.toString());
            }
            assertTrue(result.size() == set.size());
            assertTrue(set.containsAll(result));
            System.out.println("Input  -> " + set.toString());
            System.out.println("Output -> " + result.toString());
            set.forEach(x -> q.dequeue());
        }
    }
}
