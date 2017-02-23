package jobinterview.sort.compare;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;
import jobinterview.sort.SortAlgorithm;

public final class SortCompare {

    private static <T extends Comparable<? super T>> double time(SortAlgorithm algorithm, T[] a) {
        Stopwatch timer = new Stopwatch();
        algorithm.sort(a);
        return timer.elapsedTime();
    }

    public static double timeRandomInput(SortAlgorithm algorithm, int N, int T) {
        // use algorithm to sort T random arrays of length N
        double total = 0.0;
        Double[] a = new Double[N];
        for (int t = 0; t < T; t++) { // perform one experiment (generate and sort an array)
            for (int i = 0; i < N; i++) {
                a[i] = StdRandom.uniform();
            }
            total += time(algorithm, a);
        }
        return total;
    }

}
