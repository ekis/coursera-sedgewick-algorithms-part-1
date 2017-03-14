package sort.compare;

import edu.princeton.cs.algs4.Stopwatch;
import sort.SortAlgorithm;

public final class SortCompare {

    public static double timeRandomInput(SortAlgorithm algorithm, Integer[] array, int T) {
        // use algorithm to sort T random arrays of length N
        double total = 0.0;
        for (int t = 0; t < T; t++) { // perform one experiment (sort an array)
            total += time(algorithm, array);
        }
        return total;
    }

    private static <T extends Comparable<? super T>> double time(SortAlgorithm algorithm, T[] a) {
        Stopwatch timer = new Stopwatch();
        algorithm.sort(a);
        return timer.elapsedTime();
    }
}