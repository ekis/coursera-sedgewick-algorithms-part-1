import org.junit.Assert;
import org.junit.Test;

public class PercolationTest {

    @Test
    public void testPercolation() {
        new Percolation(5);
        System.out.println("");
    }

    @Test
    public void testRowMajorMapping() {
        Percolation p = new Percolation(3);
        testOpen(1, 1, p);
        testOpen(1, 2, p);
//        testOpen(1, 3, p);
//        testOpen(2, 1, p);
//        testOpen(2, 2, p);
//        testOpen(2, 3, p);
//        testOpen(3, 1, p);
//        testOpen(3, 2, p);
//        testOpen(3, 3, p);
    }

    private static void testOpen(int i, int j, Percolation p) {
        p.open(i, j);
        if (!p.isOpen(i, j)) Assert.fail(String.format("Site not open at (%s, %s)", i, j));
    }

}
