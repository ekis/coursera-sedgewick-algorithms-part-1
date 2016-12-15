import bookimpl.MergeSortInPlace;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by pakna on 06/09/16.
 */
public class MergeSortTest {

    //private static String[] INPUT = new String[]{"S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};
    private static String INPUT = "SORTEXAMPLE";
    private static String OUTPUT = "AEELMOPRSTX";

    @Test
    public void testInPlaceSort() {
        String[] input = input();
        MergeSortInPlace.sort(input);
        Assert.assertTrue(Arrays.equals(input, output()));
    }

    private static String[] input() {
        return split(INPUT);
    }

    private static String[] output() {
        return split(OUTPUT);
    }

    private static String[] split(String array) {
        return array.split("");

    }

}
