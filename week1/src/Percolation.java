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
        uf = initUF();
    }

    // open site (row i, column j) if it is not open already
    public void open(int i, int j) {
        throwIfOutOfBounds(i, j);
        opened[i - 1][j - 1] = true;
        //open(left()).at(i, j);
        //open(right()).at(i, j);
        //open(up()).at(i, j);
        //open(down()).at(i, j);
    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        throwIfOutOfBounds(i, j);
        return opened[i - 1][j - 1];
    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        throwIfOutOfBounds(i, j);
        return false;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.connected(0, endIndex - 1);
    }

    // get 1D index at 2D coordinates
    private int mapTo1D(int i, int j) {
        return (j + i * rank) - rank;
    }

    private void throwIfOutOfBounds(int i, int j) {
        if (isOutOfBounds(i) || isOutOfBounds(j)) throw new IndexOutOfBoundsException();
    }

    private boolean isOutOfBounds(int x) {
        return x < 1 || x > rank;
    }

    private WeightedQuickUnionUF initUF() {
        WeightedQuickUnionUF result = new WeightedQuickUnionUF(endIndex + 1);

        for (int i = 1; i <= rank; i++) {
            result.union(0, i); // open up top row -- connect virtual top element with top row elements
            opened[0][i - 1] = true; // mark top row as opened
        }

        int k = 0;
        for (int i = endIndex - rank; i < endIndex; i++) {
            result.union(endIndex, i); // open up bottom row -- connect bottom row elements with virtual bottom element
            opened[rank - 1][k++] = true; // mark bottom row as opened
        }

        return result;
    }

    private OpenSiteTask open(final AdjacentTask task) {
        return (i, j) -> {
            int adjacent = task.getAdjacentIndex(i, j);
            if (adjacent == -1) return;
            int target = mapTo1D(i, j);
            System.out.println(String.format("Executing union at [A, T]: (%s, %s)", adjacent, target));
            uf.union(adjacent, target);
        };
    }

    private AdjacentTask left() {
        return (i, j) -> {
            int adjacent = mapTo1D(i, j - 1);
            if (hasErrors(i, j, adjacent)) return -1;
            System.out.println(String.format("Left site open at [R, C]:(%s, %s); 1d-index -> %s", i, j, adjacent));
            return adjacent;
        };
    }

    private AdjacentTask right() {
        return (i, j) -> {
            int adjacent = mapTo1D(i, j + 1);
            if (hasErrors(i, j, adjacent)) return -1;
            System.out.println(String.format("Right site open at [R, C]:(%s, %s); 1d-index -> %s", i, j, adjacent));
            return adjacent;
        };
    }

    private AdjacentTask up() {
        return (i, j) -> {
            int adjacent = mapTo1D(i - 1, j);
            if (hasErrors(i, j, adjacent)) return -1;
            System.out.println(String.format("Up site open at [R, C]:(%s, %s); 1d-index -> %s", i, j, adjacent));
            return adjacent;
        };
    }

    private AdjacentTask down() {
        return (i, j) -> {
            int adjacent = mapTo1D(i + 1, j);
            if (hasErrors(i, j, adjacent)) return -1;
            System.out.println(String.format("Down site open at [R, C]:(%s, %s); 1d-index -> %s", i, j, adjacent));
            return mapTo1D(i + 1, j);
        };
    }

    private boolean hasErrors(int i, int j, int adjacent) {
        return isAdjacentOpen(i, j) || isAdjacentOutOfRange(adjacent);
    }

    private boolean isAdjacentOutOfRange(int adjacent) {
        boolean outOfRange = adjacent < 0 || adjacent > endIndex - 1;
        if (outOfRange)
            System.out.println(String.format("Adjacent site is %s, exceeds limits [0, %s].", adjacent, endIndex - 1));
        return outOfRange;
    }

    private boolean isAdjacentOpen(int i, int j) {
        boolean isOpen = isOpen(i, j);
        if (!isOpen) System.out.println(String.format("Adjacent site at [R, C]:(%s, %s) is NOT open."));
        return isOpen;
    }

    private interface AdjacentTask {
        int getAdjacentIndex(int i, int j);
    }

    private interface OpenSiteTask {
        void at(int i, int j);
    }
}