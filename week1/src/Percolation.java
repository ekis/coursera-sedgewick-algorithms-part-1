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
        endIndex = (N * N) + (2 * N) + 2;
        uf = initUF();
        opened = new boolean[N + 2][N];
    }

    // open site (row i, column j) if it is not open already
    public void open(int i, int j) {
        throwIfOutOfBounds(i, j);
        opened[i][j] = true;
        // I see two bugs here:
        // 1) opened should either have sentinel column or do index math to correctly track opened sites
        // 2) check the UF state is ok - I am not sure if open(up) on the first row returned false due to bug 1) or due to invalid state
        // 3?) I am not sure indexOf() is returning correct indices - for element (1, 1) it's returning index 1, whereas I would expect 4
        open(left()).at(i, j);
        open(right()).at(i, j);
        open(up()).at(i, j);
        open(down()).at(i, j);
    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        throwIfOutOfBounds(i, j);
        return opened[i][j];
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
    private int indexOf(int i, int j) {
        return i + j * rank;
    }

    private boolean isOutOfBounds(int x) {
        return x < 1 || x > rank;
    }

    private void throwIfOutOfBounds(int i, int j) {
        if (isOutOfBounds(i) || isOutOfBounds(j)) throw new IndexOutOfBoundsException();
    }

    private WeightedQuickUnionUF initUF() {
        int startIndex = 0;
        int zeroBasedEndIndex = endIndex - 1;
        WeightedQuickUnionUF result = new WeightedQuickUnionUF(endIndex);

        for (int i = startIndex; i <= rank; i++) // open up top row
            result.union(i, startIndex);
        for (int i = zeroBasedEndIndex - rank; i <= zeroBasedEndIndex; i++) // open up bottom row
            result.union(i, zeroBasedEndIndex);

        return result;
    }

    private OpenSiteTask open(final AdjacentTask task) {
        return new OpenSiteTask() {
            @Override
            public void at(int i, int j) {
                int adjacent = task.getAdjacentIndex(i, j);
                if (adjacent == -1) return;
                int target = indexOf(i, j);
                System.out.println(String.format("Executing union at [A, T]: (%s, %s)", adjacent, target));
                uf.union(adjacent, target);
            }
        };
    }

    private AdjacentTask left() {
        return new AdjacentTask() {
            @Override
            public int getAdjacentIndex(int i, int j) {
                int adjacent = indexOf(i, j - 1);
                if (hasErrors(i, j, adjacent)) return -1;
                System.out.println(String.format("Left site open at [R, C]:(%s, %s); 1d-index -> %s", i, j, adjacent));
                return adjacent;
            }
        };
    }

    private AdjacentTask right() {
        return new AdjacentTask() {
            @Override
            public int getAdjacentIndex(int i, int j) {
                int adjacent = indexOf(i, j + 1);
                if (hasErrors(i, j, adjacent)) return -1;
                System.out.println(String.format("Right site open at [R, C]:(%s, %s); 1d-index -> %s", i, j, adjacent));
                return adjacent;
            }
        };
    }

    private AdjacentTask up() {
        return new AdjacentTask() {
            @Override
            public int getAdjacentIndex(int i, int j) {
                int adjacent = indexOf(i - 1, j);
                if (hasErrors(i, j, adjacent)) return -1;
                System.out.println(String.format("Up site open at [R, C]:(%s, %s); 1d-index -> %s", i, j, adjacent));
                return adjacent;
            }
        };
    }

    private AdjacentTask down() {
        return new AdjacentTask() {
            @Override
            public int getAdjacentIndex(int i, int j) {
                int adjacent = indexOf(i + 1, j);
                if (hasErrors(i, j, adjacent)) return -1;
                System.out.println(String.format("Down site open at [R, C]:(%s, %s); 1d-index -> %s", i, j, adjacent));
                return indexOf(i + 1, j);
            }
        };
    }

    private boolean hasErrors(int i, int j, int adjacent) {
        return isAdjacentOpen(i, j) || isAdjacentOutOfRange(adjacent);
    }

    private boolean isAdjacentOutOfRange(int adjacent) {
        boolean outOfRange = adjacent < 0 || adjacent > endIndex - 1;
        if (outOfRange) System.out.println(String.format("Adjacent site is %s, exceeds limits [0, %s].", adjacent, endIndex - 1));
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