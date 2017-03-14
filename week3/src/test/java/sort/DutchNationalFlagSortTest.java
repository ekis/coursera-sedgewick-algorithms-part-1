package sort;

import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertArrayEquals;

public class DutchNationalFlagSortTest {

    private static final int[] SMALL = new int[]{0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2};

    @Test
    public void test() {
        for (int i = 0; i < 100000; i++) {
            int[] input = shuffledInput(SMALL);
            DutchNationalFlagSort.sortedArray(input);
            assertArrayEquals(SMALL, input);
        }
    }


    private static int[] shuffledInput(int[] input) {
        List<Integer> result = IntStream.of(input).boxed().collect(Collectors.toList());
        Collections.shuffle(result);
        return result.stream().mapToInt(Integer::intValue).toArray();
    }
}