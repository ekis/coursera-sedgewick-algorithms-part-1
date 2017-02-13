package jobinterview.setintersection;

import org.junit.Test;

import java.util.*;
import java.util.stream.IntStream;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by ekis on 12/02/17.
 */
public final class PermutationTest {

    @Test
    public void testTrivial() {
        SetArrays s = new SetArrays();
        s.addToA(0, 0);
        s.addToB(0, 0);
        assertTrue(s.isAPermutationOfB());

        s.addToA(1, 1);
        s.addToB(1, 1);
        assertTrue(s.isAPermutationOfB());

        s.addToB(2, 1);
        assertFalse(s.isAPermutationOfB());

        s.addToA(2, 1);
        assertTrue(s.isAPermutationOfB());
    }

    @Test
    public void testPermutation() {
        SetArrays s = new SetArrays();
        s.addToA(10, 20);
        s.addToA(30, 40);
        s.addToA(50, 60);
        s.addToA(70, 80);
        s.addToA(90, 100);
        s.addToB(90, 100);
        s.addToB(70, 80);
        s.addToB(30, 40);
        s.addToB(10, 20);
        s.addToB(50, 60);
        assertTrue(s.isAPermutationOfB());

        s.addToA(1, 2);
        s.addToB(3, 4);
        assertFalse(s.isAPermutationOfB());
    }

    @Test
    public void testPermutationRandom() {
        List<Pair> list = IntStream.range(0, 1000) // generate (x, y) pairs
                .collect(ArrayList::new,
                        (s, x) -> IntStream.range(0, 1000)
                                .forEach(y -> s.add(new Pair(x, y))),
                        null);
        SetArrays s = new SetArrays();

        list.forEach(pair -> s.addToA(pair.x, pair.y));
        Collections.shuffle(list);
        list.forEach(pair -> s.addToB(pair.x, pair.y));

        assertTrue(s.isAPermutationOfB());
    }

    private static class Pair {
        private final int x;
        private final int y;

        private Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair pair = (Pair) o;
            return x == pair.x &&
                    y == pair.y;
        }
    }
}
