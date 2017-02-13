package jobinterview.setintersection;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by ekis on 08/02/17.
 */
public final class IntersectionTest {

    @Test
    public void testSimpleSetIntersection() {
        SetArrays s = new SetArrays();
        s.addToA(0, 0);
        s.addToB(0, 0);
        assertEquals(1, s.intersectCount());
    }

    @Test
    public void testSetIntersectionSymmetric() {
        SetArrays s = new SetArrays();
        s.addToA(0, 0); // this
        s.addToA(1, 2);
        s.addToA(0, 3);
        s.addToA(0, 4); // this
        s.addToA(4, 4); // this
        s.addToA(0, 50); // this
        s.addToA(15,1);
        s.addToA(6, 22);
        s.addToB(50, 2);
        s.addToB(30, 10);
        s.addToB(4, 4); //
        s.addToB(300, 400);
        s.addToB(0, 0); //
        s.addToB(0, 4); //
        s.addToB(0, 50); //
        s.addToB(1, 500);
        assertEquals(4, s.intersectCount());
    }

    @Test
    public void testSetIntersectionLeftAsymmetric() {
        SetArrays s = new SetArrays();
        s.addToA(0, 0); // this
        s.addToA(1, 2);
        s.addToA(0, 4);
        s.addToA(4, 4);
        s.addToA(0, 50); // this
        s.addToA(15,1);
        s.addToA(6, 22);
        s.addToB(300, 400);
        s.addToB(0, 0); //
        s.addToB(0, 50); //
        s.addToB(1, 500);
        assertEquals(2, s.intersectCount());
    }

    @Test
    public void testSetIntersectionRightAsymmetric() {
        SetArrays s = new SetArrays();
        s.addToB(0, 0); // this
        s.addToB(1, 2);
        s.addToB(0, 4);
        s.addToB(4, 4);
        s.addToB(0, 50); // this
        s.addToB(15,1);
        s.addToB(6, 22);
        s.addToA(300, 400);
        s.addToA(0, 0); //
        s.addToA(0, 50); //
        s.addToA(1, 500);
        assertEquals(2, s.intersectCount());
    }

    @Test
    public void testSetIntersectionLargeLeftAsymmetric() {
        SetArrays s = new SetArrays();
        addLarge(SetArrays::addToA, s, 1000); // 10^6 elements
        s.addToA(0, 1);
        s.addToB(0, 0);
        s.addToB(0, 1);
        s.addToB(0, 2);
        s.addToB(1, 2);
        s.addToB(45, 45);
        s.addToB(98, 98);
        assertEquals(4, s.intersectCount());
    }

    @Test
    public void testSetIntersectionLargeRightAsymmetric() {
        SetArrays s = new SetArrays();
        addLarge(SetArrays::addToB, s, 1000); // 10^6 elements
        s.addToB(0, 1);
        s.addToA(0, 0);
        s.addToA(0, 1);
        s.addToA(0, 2);
        s.addToA(1, 2);
        s.addToA(45, 45);
        s.addToA(98, 98);
        assertEquals(4, s.intersectCount());
    }

    @Test
    public void testSetIntersectionLargeLeftAsymmetric2() {
        SetArrays s = new SetArrays();
        addLarge(SetArrays::addToA, s, 1000);  // 10^6 elements
        addLarge(SetArrays::addToB, s, 10);  // 10^2 elements
        s.addToB(600, 600);
        s.addToB(700, 700);
        s.addToB(200, 200);
        s.addToB(800, 800);
        s.addToB(900, 900);
        assertEquals(105, s.intersectCount());
    }


    private static void addLarge(AddPoint f, SetArrays s, int limit) {
        for (int x = 1; x <= limit; x++)
            for (int y = 1; y <= limit; y++)
                f.accept(s, x, y);
    }

    @FunctionalInterface
    private interface AddPoint {
        void accept(SetArrays s, int x, int y);
    }
}