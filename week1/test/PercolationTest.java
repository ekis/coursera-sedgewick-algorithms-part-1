import org.junit.Test;

public class PercolationTest {

    @Test
    public void testPercolation() {
        System.out.println("Junit works");
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testPercolationConstructionFail() {
        new Percolation(-1);
    }

}
