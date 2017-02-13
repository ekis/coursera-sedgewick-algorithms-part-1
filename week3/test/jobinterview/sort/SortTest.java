package jobinterview.sort;

import jobinterview.sort.mergesort.MyTopDownMergeSort;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertArrayEquals;

/**
 * Created by ekis on 05/02/17.
 */
public class SortTest {

    private static final String[] EXAMPLE = new String[]{"S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};
    private static final String[] EXPECTED = new String[]{"A", "E", "E", "L", "M", "O", "P", "R", "S", "T", "X"};

    @Test
    public void testSelectionSort() {
        String[] actual = copyExample();
        MySelection.sort(actual);
        assertTrue(MySelection.isSorted(actual));
        assertArrayEquals(EXPECTED, actual);
    }

    @Test
    public void testInsertionSort() {
        String[] actual = copyExample();
        MyInsertion.sort(actual);
        assertTrue(MyInsertion.isSorted(actual));
        assertArrayEquals(EXPECTED, actual);
    }

    @Test
    public void testShellSort() {
        String[] actual = copyExample();
        MyShell.sort(actual);
        assertTrue(MyShell.isSorted(actual));
        assertArrayEquals(EXPECTED, actual);
    }

    @Test
    public void testShellViaArraySort() {
        String[] actual = copyExample();
        MyShellViaArray.sort(actual);
        assertTrue(MyShellViaArray.isSorted(actual));
        assertArrayEquals(EXPECTED, actual);
    }

    @Test
    public void testTopDownMergeSort() {
        String[] actual = copyExample();
        MyTopDownMergeSort.sort(actual);
        assertTrue(MyTopDownMergeSort.isSorted(actual));
        assertArrayEquals(EXPECTED, actual);
    }

    private static String[] copyExample() {
        return Arrays.copyOf(EXAMPLE, EXAMPLE.length);
    }
}
