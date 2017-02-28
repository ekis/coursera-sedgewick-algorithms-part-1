package jobinterview.sort;

import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;

public enum SortRandomData {

    QUADRATIC_SORT(SortConst.RANDOM_QUADRATIC_DOUBLES, SortConst.RANDOM_QUADRATIC_INTEGER_DUPLICATES_INTERVALS),
    SUBQUADRATIC_SORT(SortConst.RANDOM_SUBQUADRATIC_DOUBLES, SortConst.RANDOM_SUBQUADRATIC_INTEGER_DUPLICATES_INTERVALS),
    LINEARITHMIC_SORT(SortConst.RANDOM_LINEARITHMIC_DOUBLES, SortConst.RANDOM_LINEARITHMIC_INTEGER_DUPLICATES_INTERVALS);

    private final Integer[] randoms;
    private final Integer[] duplicates;

    SortRandomData(Integer[] randoms, Integer[] duplicates) {
        this.randoms = randoms;
        this.duplicates = duplicates;
    }

    Integer[] randoms() {
        return randoms;
    }

    Integer[] duplicates() {
        return duplicates;
    }

    private static Integer[] randoms(int N) {
        Random rnd = new Random();
        return IntStream.iterate(0, x -> rnd.nextInt()).limit(N).boxed().toArray(Integer[]::new);
    }

    private static Integer[] duplicates(int N) {
        int intervalCount = SortConst.intervalCount(N);
        Integer[] result= IntStream.range(0, intervalCount)
                .mapToObj(x -> IntStream.iterate(x, y -> y).limit(intervalCount).boxed())
                .flatMap(Function.identity()).toArray(Integer[]::new);
        return result;
    }

    private static class SortConst {
        private static final int QUADRATIC_ELEMENT_COUNT = 15000;
        private static final int SUBQUADRATIC_ELEMENT_COUNT = 500000; // 5 * 10^5
        private static final int LINEARITHMIC_ELEMENT_COUNT = 1000000; // 10^6

        private static final Integer[] RANDOM_QUADRATIC_DOUBLES = randoms(QUADRATIC_ELEMENT_COUNT);
        private static final Integer[] RANDOM_QUADRATIC_INTEGER_DUPLICATES_INTERVALS = duplicates(QUADRATIC_ELEMENT_COUNT);
        private static final Integer[] RANDOM_SUBQUADRATIC_DOUBLES = randoms(SUBQUADRATIC_ELEMENT_COUNT);
        private static final Integer[] RANDOM_SUBQUADRATIC_INTEGER_DUPLICATES_INTERVALS = duplicates(SUBQUADRATIC_ELEMENT_COUNT);
        private static final Integer[] RANDOM_LINEARITHMIC_DOUBLES = randoms(LINEARITHMIC_ELEMENT_COUNT);
        private static final Integer[] RANDOM_LINEARITHMIC_INTEGER_DUPLICATES_INTERVALS = duplicates(LINEARITHMIC_ELEMENT_COUNT);

        private static int intervalCount(int N) {
            if (N <= SortConst.QUADRATIC_ELEMENT_COUNT) return N / 120;
            else if (N <= SortConst.SUBQUADRATIC_ELEMENT_COUNT) return N / 500;
            else return N / 500;
        }
    }
}