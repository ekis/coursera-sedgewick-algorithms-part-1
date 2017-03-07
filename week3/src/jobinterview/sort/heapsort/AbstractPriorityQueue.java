package jobinterview.sort.heapsort;

abstract class AbstractPriorityQueue {

    /**
     * Restores heap order by swimming up the node to its correct place bottom-up.
     */
    final void swim(int k) {
        int parent = k / 2;
        if (parent >= 1 && less(parent, k)) { // is parent < k
            exch(parent, k);
            swim(parent);
        }
    }

    /**
     * Restores heap order by sinking the node to its correct place top-down.
     */
    final void sink(int k) {
        int left = 2 * k;
        int right = left + 1;
        if (left > size()) {
            if (right > size()) return; // is leaf, nothing to do but return
            if (less(k, right)) { // no left, k < right => sink k
                exch(k, right);
                sink(right);
            }
        } else if (right > size()) {
            if (less(k, left)) { // no right, k < left => sink k
                exch(k, left);
                sink(left);
            }
        } else { // both nodes are here, compare them, take larger and exchange with k-th node if k < larger
            int larger = less(left, right) ? right : left;
            if (less(k, larger)) {
                exch(k, larger);
                sink(larger);
            }
        }
        // stop sinking, array is heap-ordered
    }

    protected abstract int size();

    protected abstract boolean less(int i, int j);

    protected abstract void exch(int i, int j);

}
