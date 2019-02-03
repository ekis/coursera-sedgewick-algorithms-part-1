package setintersection;

import ekis.common.Pair;
import org.junit.Test;

import java.util.*;
import java.util.stream.IntStream;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
        List<Pair<Integer, Integer>> list = IntStream.range(0, 1000) // generate (x, y) pairs
                .sequential()
                .collect(ArrayList::new,
                        (acc, x) -> IntStream.range(0, 1000)
                                .forEach(y -> acc.add(Pair.of(x, y))),
                        ArrayList::addAll);
        SetArrays s = new SetArrays();

        list.forEach(pair -> s.addToA(pair.x(), pair.y()));
        Collections.shuffle(list);
        list.forEach(pair -> s.addToB(pair.x(), pair.y()));

        assertTrue(s.isAPermutationOfB());
    }
}
