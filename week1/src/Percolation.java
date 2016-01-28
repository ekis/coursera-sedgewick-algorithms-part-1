import edu.princeton.cs.algs4.WeightedQuickUnionUF;

// by convention, the row and column indices i and j are integers between 1 and N, where (1, 1) is the upper-left site.
public class Percolation {

    private final int rank;
    private final int endIndex;

    private final boolean[][] opened;
    private final WeightedQuickUnionUF uf;

    // create N-by-N grid, with all sites blocked
    public Percolation(int N) throws IndexOutOfBoundsException {
        if (N <= 0) throw new IndexOutOfBoundsException();
        rank = N;
        endIndex = (N * N) + 1;
        opened = new boolean[N][N];
        uf = new WeightedQuickUnionUF(endIndex + 1);
    }

    // open site (row i, column j) if it is not open already
    // connect it with its adjacent open sites
    public void open(int i, int j) {
        throwIfOutOfBounds(i, j);
        opened[i - 1][j - 1] = true;
        open(left()).at(i, j);
        open(right()).at(i, j);
        open(up()).at(i, j);
        open(down()).at(i, j);
    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        throwIfOutOfBounds(i, j);
        return opened[i - 1][j - 1];
    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        throwIfOutOfBounds(i, j);
        int site = mapTo1D(i, j);
        return isOpen(i, j) && uf.connected(site, 0);
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.connected(0, endIndex);
    }

    private OpenSiteTask open(final AdjacentTask task) {
        return (i, j) -> {
            int adjacent = task.getAdjacentIndex(i, j);
            if (adjacent == -1) return;
            int target = mapTo1D(i, j);
            uf.union(adjacent, target);
        };
    }

    private AdjacentTask left() {
        return (row, col) -> adjacent(row, col - 1);
    }

    private AdjacentTask right() {
        return (row, col) -> adjacent(row, col + 1);
    }

    private AdjacentTask up() {
        return (row, col) -> {
            if (row == 1) return 0;
            return adjacent(row - 1, col);
        };
    }

    private AdjacentTask down() {
        return (row, col) -> {
            if (row == rank) return endIndex;
            return adjacent(row + 1, col);
        };
    }

    private int adjacent(int i, int j) {
        int adjacent = mapTo1D(i, j);
        if (isOutOfRangeOrClosed(i, j)) return -1;
        return adjacent;
    }

    private boolean isOutOfRangeOrClosed(int i, int j) {
        return isOutOfBounds(i, j) || !isOpen(i, j);
    }

    // get 1D index at 2D coordinates
    private int mapTo1D(int i, int j) {
        return (j + i * rank) - rank;
    }

    private void throwIfOutOfBounds(int i, int j) {
        if (isOutOfBounds(i, j)) throw new IndexOutOfBoundsException();
    }

    private boolean isOutOfBounds(int i, int j) {
        return isOutOfBoundsPer(i) || isOutOfBoundsPer(j);
    }

    private boolean isOutOfBoundsPer(int x) {
        return x < 1 || x > rank;
    }

    @FunctionalInterface
    private interface AdjacentTask {
        int getAdjacentIndex(int i, int j);
    }

    @FunctionalInterface
    private interface OpenSiteTask {
        void at(int i, int j);
    }
}