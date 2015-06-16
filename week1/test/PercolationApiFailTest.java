import org.junit.Assert;
import org.junit.Test;

public class PercolationApiFailTest {

    @Test(expected = IndexOutOfBoundsException.class)
    public void testConstructionFail() {
        new Percolation(-1);
    }

    @Test
    public void testOpenFail() {
        runTest(open());
    }

    @Test
    public void testIsOpenFail() {
        runTest(isOpen());
    }

    @Test
    public void testIsFull() {
        runTest(isFull());
    }

    private static void runTest(FailTask task) {
        validate(task, 0, 1); // lower bound violation
        validate(task, 1, 0);
        validate(task, 1, 2);
        validate(task, 0, 1); // upper bound violation
        validate(task, 2, 1);
        validate(task, 2, 2);
    }

    private static void validate(FailTask task, int i, int j) {
        try {
            task.run(i, j);
            Assert.fail();
        } catch (IndexOutOfBoundsException e) {
            // muffle, expected
        }
    }

    private static FailTask open() throws IndexOutOfBoundsException {
        return new FailTask() {
            @Override
            public void run(int i, int j) throws IndexOutOfBoundsException {
                new Percolation(1).open(i, j);
            }
        };
    }

    private static FailTask isOpen() throws IndexOutOfBoundsException {
        return new FailTask() {
            @Override
            public void run(int i, int j) throws IndexOutOfBoundsException {
                new Percolation(1).isOpen(i, j);
            }
        };
    }

    private static FailTask isFull() throws IndexOutOfBoundsException {
        return new FailTask() {
            @Override
            public void run(int i, int j) throws IndexOutOfBoundsException {
                new Percolation(1).isFull(i, j);
            }
        };
    }

    private interface FailTask {
        void run(int i, int j) throws IndexOutOfBoundsException;
    }
}
