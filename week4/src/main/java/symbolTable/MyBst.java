package symbolTable;

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

    // my insert w/o back link
    private Node insert(K key, V value, Node node) {
        if (node == null) return new Node(key, value);
        int cmp = key.compareTo(node.key);
        if (cmp < 0) node.left = insert(key, value, node.left);
        else if (cmp > 0) node.right = insert(key, value, node.right);
        else node.value = value;
        node.subtreeCount = 1 + size(node.left) + size(node.right);
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
    @Override
    public void delete(K key) {
    }

    @Override
    public boolean contains(K key) {
        return false;
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

    // case 1 : [k == the key at root] -> floor(key) == key
    // case 2 : [k <= the key at root] -> floor(key) is in the LEFT subtree
    // case 3 : [k >= the key at root] -> floor(key) is in the RIGHT subtree (if there is any key <= k in right subtree; otherwise, the floor is the subtree root)
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

    // case 1 : [k == the key at root] -> ceiling(key) == key
    // case 2 : [k <= the key at root] -> ceiling(key) is in the RIGHT subtree
    // case 3 : [k >= the key at root] -> ceiling(key) is in the LEFT subtree (if there is any key <= k in left subtree; otherwise, the floor is the subtree root)
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
    // case 1 : [k == the key at root] -> rank(key) is the node count in the LEFT subtree
    // case 2 : [k <  the key at root] -> rank(key) is the rank of the key in the LEFT subtree
    // case 3 : [k >  the key at root] -> rank(key) = 1 + node count in the LEFT subtree + rank of the of the key in the RIGHT subtree
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
    // case 1 : [k == left subtree count] -> select(k) returns key at that node
    // case 2 : [k <  left subtree count] -> select(k) return
    // case 3 : [k >  left subtree count] -> select(k) key is in the LEFT subtree (computed recursively via rank())
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
        return 0;
    }

    @Override
    public Iterable<K> keys(K lo, K hi) {
        return null;
    }

    @Override
    public Iterable<K> keys() {
        return null;
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
