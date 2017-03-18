package symbolTable;

import elementary.datastructures.queues.MyQueue;
import elementary.datastructures.queues.Queues;

import java.util.Collections;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

final class MyBst<K extends Comparable<? super K>, V> implements MySymbolTable<K, V> {

    private Node root;

    private MyBst(){}

    static <K extends Comparable<? super K>, V> MyBst<K, V> create() {
        return new MyBst<>();
    }

    // if <, go left; if >, go right; if null, insert; if ==, replace
    @Override
    public void put(K key, V value) {
        if (key == null || value == null) throw new IllegalArgumentException("Key and/or value argument may not be null.");
        if (root == null) root = new Node(key, value);
        insert(key, value, root);
    }

    // my insert with back link
    private Node insert(K key, V value, Node node) {
        if (node == null) return new Node(key, value);
        int cmp = key.compareTo(node.key);
        if (cmp < 0) node.left = insert(key, value, node.left);
        else if (cmp > 0) node.right = insert(key, value, node.right);
        else node.value = value;
        updateSubtreeCountFor(node);
        return node;
    }

    // if <, go left; if >, go right; if ==, search hit; if null, search miss
    @Override
    public Optional<V> get(K key) {
        return size() == 0 ? Optional.empty() : checkAndFind(key, this::find).map(node -> node.value);
    }

    private Optional<Node> find(K key, Node node) {
        if (node == null) return Optional.empty();
        int cmp = key.compareTo(node.key);
        if (cmp < 0) return find(key, node.left);
        if (cmp > 0) return find(key, node.right);
        return Optional.of(node);
    }

    // if <, go left; if >, go right; if ==, delete; if null, nothing
    // Hibbard deletion: to delete a node with key k: search for node T containing key K
    // case 0 : [0 children] -> delete T by setting parent link to null
    // case 1 : [1 children] -> delete T by replacing parent link (min-case)
    // case 2 : [2 children] -> 1. find successor x of T - (find x such that x has no left child)
    //                          2. delete the minimum in T's subtree (but don't GC x)
    //                          3. put x in T's spot (still a BST)
    @Override
    public void delete(K key) {
        delete(key, root);
    }

    private Node delete(K key, Node node) {
        if (node == null) return null;
        int cmp = key.compareTo(node.key);
        if (cmp < 0)
            node.left = delete(key, node.left);
        else if (cmp > 0)
            node.right = delete(key, node.right);
        else {
            switch (childrenCount(node)) {
                case 0 :
                    return null;
                case 1 :
                    node = node.left == null ? node.right : node.left;
                    break;
                case 2 :
                    Node t = node;
                    node = minimum(t.right); // should point to the BST containing all the keys larger than x.key
                    node.right = deleteMinimum(t.right);
                    node.left = t.left; // all the keys that are < both the deleted key and its successor
                    break;
                default :
                    throw new IllegalStateException();
            }
        }
        updateSubtreeCountFor(node);
        return node;
    }

    private Node minimum(Node node) {
        if (node.left == null) return node;
        return minimum(node.left);
    }

    // 1. go left until finding a node with a null left link
    // 2. replace that node by its right link
    // 3. update subtree counts
    private Node deleteMinimum(Node node) {
        if (node.left == null) return node.right;
        node.left = deleteMinimum(node.left);
        updateSubtreeCountFor(node);
        return node;
    }

    private void updateSubtreeCountFor(Node node) {
        node.subtreeCount = 1 + size(node.left) + size(node.right);
    }

    // this should've been an enum but enums don't work with non-static class (Node class isn't) so that would mean that
    // either we refactor this whole class, introduce a bunch of statics and spam those method signature with exactly
    // the same generic type definitions, or we rely on the typedefs from this class instance and just do this dirtiness
    private int childrenCount(Node node) {
        if (node.left == null) return node.right == null ? 0 : 1;
        return node.right == null ? 1 : 2;
    }

    @Override
    public boolean contains(K key) {
        return get(key).map(k -> true).orElse(false);
    }

    @Override
    public boolean isEmpty() {
        return size(root) == 0;
    }

    public int size() {
        return size(root);
    }

    private int size(Node node) {
        return node == null ? 0 : node.subtreeCount;
    }

    @Override
    public Optional<K> min() {
        return checkAndFindOptional(this::min);
    }

    private K min(Node node) {
        if (node.left == null) return node.key;
        return min(node.left);
    }

    @Override
    public Optional<K> max() {
        return checkAndFindOptional(this::max);
    }

    private K max(Node node) {
        if (node.right == null) return node.key;
        return max(node.right);
    }

    // case 0 : [k == the key at root] -> floor(key) == key
    // case 1 : [k <= the key at root] -> floor(key) is in the LEFT subtree
    // case 2 : [k >= the key at root] -> floor(key) is in the RIGHT subtree (if there is any key <= k in right subtree; otherwise, the floor is the subtree root)
    @Override
    public Optional<K> floor(K key) {
        return checkAndFindOptional(key, this::floor);
    }

    private K floor(K key, Node node) {
        if (node == null) return null; // we hit the end of the tree without finding anything <= key
        int cmp = key.compareTo(node.key);

        if (cmp == 0) return node.key;
        if (cmp < 0) return floor(key, node.left);

        K smallerKey = floor(key, node.right);
        return smallerKey == null ? node.key : smallerKey;
    }

    // case 0 : [k == the key at root] -> ceiling(key) == key
    // case 1 : [k <= the key at root] -> ceiling(key) is in the RIGHT subtree
    // case 2 : [k >= the key at root] -> ceiling(key) is in the LEFT subtree (if there is any key <= k in left subtree; otherwise, the floor is the subtree root)
    @Override
    public Optional<K> ceiling(K key) {
        return checkAndFindOptional(key, this::ceiling);
    }

    private K ceiling(K key, Node node) {
        if (node == null) return null; // we hit the end of the tree without finding anything <= key
        int cmp = key.compareTo(node.key);

        if (cmp == 0) return node.key;
        if (cmp > 0) return ceiling(key, node.right);

        K largerKey = ceiling(key, node.left);
        return largerKey == null ? node.key : largerKey;
    }

    // how many keys < k?
    // case 0 : [k == the key at root] -> rank(key) is the node count in the LEFT subtree
    // case 1 : [k <  the key at root] -> rank(key) is the rank of the key in the LEFT subtree
    // case 2 : [k >  the key at root] -> rank(key) = 1 + node count in the LEFT subtree + rank of the of the key in the RIGHT subtree
    // inverse of select()
    @Override
    public int rank(K key) {
        return checkAndFind(key, this::rank);
    }

    private int rank(K key, Node node) {
        if (node == null) return 0;
        int cmp = key.compareTo(node.key);

        if (cmp == 0) return size(node.left);
        if (cmp < 0) return rank(key, node.left);

        return 1 + size(node.left) + rank(key, node.right);
    }

    // what is the key of rank k?
    // case 0 : [k == left subtree count] -> select(k) returns key at that node
    // case 1 : [k <  left subtree count] -> select(k) return
    // case 2 : [k >  left subtree count] -> select(k) key is in the LEFT subtree (computed recursively via rank())
    // inverse of rank()
    @Override
    public Optional<K> select(int k) {
        if (k < 0 || k > size()) throw new IllegalArgumentException("Argument k must be 0 < k < size().");
        return Optional.ofNullable(select(k, root));
    }

    private K select(int k, Node node) {
        if (node == null) return null;
        int t = node.left == null ? 0 : node.left.subtreeCount;

        if (k == t) return node.key;
        if (k < t) return select(k, node.left);
        return select(k - t - 1, node.right);
    }

    @Override
    public int size(K lo, K hi) {
        if (lo == null || hi == null) throw new IllegalArgumentException("LO and/or HI argument may not be null.");

        if (lo.compareTo(hi) > 0) return 0;
        if (contains(hi)) return rank(hi) - rank(lo) + 1;
        return rank(hi) - rank(lo);
    }

    @Override
    public Iterable<K> keys(K lo, K hi) {
        if (lo == null || hi == null) throw new IllegalArgumentException("LO and/or HI argument may not be null.");
        if (lo.compareTo(hi) > 0) return Collections.emptyList();
        MyQueue<K> queue = Queues.simpleQueue();
        traverseInorder(lo, hi, root, queue);
        return queue;
    }

    private void traverseInorder(K lo, K hi, Node node, MyQueue<K> queue) {
        if (node == null) return;
        int cmpLo = lo.compareTo(node.key);
        int cmpHi = hi.compareTo(node.key);
        if (cmpLo < 0) traverseInorder(lo, hi, node.left, queue);
        if (cmpLo <= 0 && cmpHi >= 0) queue.enqueue(node.key); // filter out node key which are exceeding [lo...hi] boundaries
        if (cmpHi > 0) traverseInorder(lo, hi, node.right, queue);
    }

    private Optional<K> checkAndFindOptional(Function<Node, K> f) {
        if (isEmpty()) throw new IllegalStateException("Undefined for empty symbol table.");
        return Optional.ofNullable(f.apply(root));
    }

    private Optional<K> checkAndFindOptional(K key, BiFunction<K, Node, K> f) {
        return Optional.ofNullable(checkAndFind(key, f));
    }

    private <R> R checkAndFind(K key, BiFunction<K, Node, R> f) {
        if (key == null) throw new IllegalArgumentException("Key argument may not be null.");
        if (isEmpty()) throw new IllegalStateException("Undefined for empty symbol table.");
        return f.apply(key, root);
    }

    private class Node {
        private K key;
        private V value;
        private Node left, right;
        private int subtreeCount = 1;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }
}