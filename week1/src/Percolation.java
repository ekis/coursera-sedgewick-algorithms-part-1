import java.util.Arrays;

// by convention, the row and column indices i and j are integers between 1 and N, where (1, 1) is the upper-left site.
public class Percolation {

    private final int dimension;
    private final WeightedQuickUnionUF uf;

    // create N-by-N grid, with all sites blocked
    public Percolation(int N) throws IndexOutOfBoundsException {
        if (N <= 0) throw new IndexOutOfBoundsException();
        dimension = N;
        uf = new WeightedQuickUnionUF(N * N);
    }

    // open site (row i, column j) if it is not open already
    public void open(int i, int j) {
        throwIfOutOfRange(i, j);
    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        throwIfOutOfRange(i, j);
        return false;
    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        throwIfOutOfRange(i, j);
        return false;
    }

    // does the system percolate?
    public boolean percolates() {
        return false;
    }

    // get 1D index of 2D coordinates
    private int indexOf(int i, int j) {
        return 0;
    }

    private void throwIfOutOfRange(int i, int j) {
        if (isOutOfRange(i) || isOutOfRange(j)) throw new IndexOutOfBoundsException();
    }

    private boolean isOutOfRange(int x) {
        return x < 1 || x > dimension;
    }

}