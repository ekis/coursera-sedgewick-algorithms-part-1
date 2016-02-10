import org.junit.Assert;
import org.junit.Test;

import java.util.function.BiConsumer;

public class DequeTest {

    @Test
    public void testIsEmpty() {
        Deque<Integer> deque = new Deque<>();
        Assert.assertTrue(deque.isEmpty());
    }

    @Test
    public void testAddRemoveFirst() {
        int size = 4;
        Deque<Integer> d = add(size, Deque::addFirst);
        Assert.assertEquals(d.removeFirst().intValue(), 1);
        Assert.assertEquals(d.size(), 3);
        Assert.assertEquals(d.removeFirst().intValue(), 2);
        Assert.assertEquals(d.size(), 2);
        Assert.assertEquals(d.removeFirst().intValue(), 3);
        Assert.assertEquals(d.size(), 1);
        Assert.assertEquals(d.removeFirst().intValue(), 4);
        Assert.assertTrue(d.isEmpty());
    }

    private static Deque<Integer> add(int size, BiConsumer<Deque, Integer> f) {
        Deque<Integer> deque = new Deque<>();
        for (int i = size; i > 0; i--) {
            f.accept(deque, i);
        }
        Assert.assertEquals(deque.size(), size);
        return deque;
    }

//    private static Deque<Integer> remove(int start, Deque<Integer> deque, Function<Deque, Integer> f) {
//        while (!deque.isEmpty()) {
//            Integer value = f.apply(deque);
//            Assert.assertEquals(value);
//        }
//    }
}
