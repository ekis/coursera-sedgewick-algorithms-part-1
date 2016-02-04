import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final int rank;
    private final double[] experimentResults;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) throw new IllegalArgumentException();
        rank = N;
        experimentResults = new double[T];
        performExperiment();
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(experimentResults);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        if (experiments() == 1) return Double.NaN;
        return StdStats.stddev(experimentResults);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - confidenceTerm();
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + confidenceTerm();
    }

    private void randomOpen(Percolation p) {
        int row = uniform();
        int col = uniform();
        while (p.isOpen(row, col)) {
            row = uniform();
            col = uniform();
        }
        p.open(row, col);
    }

    private void performExperiment() {
        for (int i = 0; i < experimentResults.length; i++) {
            Percolation p = new Percolation(rank);
            int openSites = 0;
            for (; ; ) {
                randomOpen(p);
                openSites++;
                if (p.percolates()) break;
            }
            experimentResults[i] = (double) openSites / (rank * rank);
        }
    }

    // test client
    public static void main(String[] args) {
        Integer N = Integer.valueOf(args[0]);
        Integer T = Integer.valueOf(args[1]);

        PercolationStats s = new PercolationStats(N, T);
        StdOut.println(String.format("mean                      -> %s", s.mean()));
        StdOut.println(String.format("stddev                    -> %s", s.stddev()));
        StdOut.println(String.format("95%% confidence interval  -> %s, %s", s.confidenceLo(), s.confidenceHi()));
    }

    private int uniform() {
        return StdRandom.uniform(1, rank + 1);
    }

    private int experiments() {
        return experimentResults.length;
    }

    private double confidenceTerm() {
        return (1.96 * Math.sqrt(stddev())) / Math.sqrt(experimentResults.length);
    }
}
