package symbolTable;

import java.util.Optional;

final class MyBst<K extends Comparable<? super K>, V> implements MySymbolTable<K, V> {

    private Node root;
    private int N;

    private MyBst(){}

    static <K extends Comparable<? super K>, V> MyBst<K, V> create() {
        return new MyBst<>();
    }

    // if <, go left; if >, go right; if null, insert; if ==, replace
    @Override
    public void put(K key, V value) {
        if (root == null) root = new Node(key, value);
        else insert(key, value, root);
        //else insert(key, value);
    }

    // my insert w/o back link
    private void insert(K key, V value, Node node) {
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            if (node.left == null) node.left = new Node(key, value);
            else insert(key, value, node.left);
        }
        else if (cmp > 0) {
            if (node.right == null) node.right = new Node(key, value);
            else insert(key, value, node.right);
        }
        else node.value = value ;
    }

    // Sedgewick with back link
    private Node insert(Node node, K key, V value) {
        if (node == null) return new Node(key, value);
        int cmp = key.compareTo(node.key);
        if (cmp < 0) node.left = insert(node, key, value);
        else if (cmp > 0) node.right = insert(node, key, value);
        else node.value = value ;
        return node;
    }

    // iterative insert
    private void insert(K key, V value) {
        Node x = root;

        while (true) {
            int cmp = key.compareTo(x.key);
            if (cmp < 0) {
                if (x.left == null) {
                    x.left = new Node(key, value);
                    break;
                }
                else x = x.left;
            } else if (cmp > 0) {
                if (x.right == null) {
                    x.right = new Node(key, value);
                    break;
                }
                else x = x.right;
            } else {
                x.value = value;
                break;
            }
        }
    }

    // if <, go left; if >, go right; if ==, search hit; if null, search miss
    @Override
    public Optional<V> get(K key) {
        Node result = find(key, root);
        return result == null ? Optional.empty() : Optional.of(result.value);
    }

    // recursive find
    private Node find(K key, Node node) {
        if (node == null) return null;
        int cmp = key.compareTo(node.key);
        if (cmp < 0) return find(key, node.left);
        if (cmp > 0) return find(key, node.right);
        return node;
    }

    // Sedgewick iterative
    private V find(K key) {
        Node x = root;

        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp < 0) x = x.left;
            else if (cmp > 0) x = x.right;
            else return x.value;
        }
        return null;
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
        return false;
    }

    public int size() {
        return N;
    }

    @Override
    public Optional<K> min() {
        K result = min(root);
        return result == null ? Optional.empty() : Optional.of(result);
    }

    private K min(Node node) {
        if (node.left == null) return node.key;
        return min(node.left);
    }

    @Override
    public Optional<K> max() {
        K result = max(root);
        return result == null ? Optional.empty() : Optional.of(result);
    }

    private K max(Node node) {
        if (node.right == null) return node.key;
        return max(node.right);
    }

    // case 1 : [k equals the key at root] -> floor(k) == k
    // case 2 : [k <= the key at root]     -> floor(k) is in the LEFT subtree
    // case 3 : [k >= the key at root]     -> floor(k) is in the RIGHT subtree (if there is any key <= k in right subtree; otherwise, the floor is the subtree root)
    @Override
    public Optional<K> floor(K key) {
        if (key == null) throw new IllegalArgumentException("Key argument may not be null.");
        if (isEmpty()) throw new IllegalStateException("Undefined for empty symbol table.");
        return Optional.ofNullable(floor(key, root));
    }

    private K floor(K key, Node node) {
        if (node == null) return null; // we hit the end of the tree without finding anything <= key
        int cmp = key.compareTo(node.key);

        if (cmp == 0) return node.key;
        if (cmp < 0) return floor(key, node.left);

        K smallerKey = floor(key, node.right);
        return smallerKey == null ? node.key : smallerKey;
    }

    // case 1 : [k equals the key at root] -> ceiling(k) == k
    // case 2 : [k <= the key at root]     -> ceiling(k) is in the RIGHT subtree
    // case 3 : [k >= the key at root]     -> ceiling(k) is in the LEFT subtree (if there is any key <= k in left subtree; otherwise, the floor is the subtree root)
    @Override
    public Optional<K> ceiling(K key) {
        if (key == null) throw new IllegalArgumentException("Key argument may not be null.");
        if (isEmpty()) throw new IllegalStateException("Undefined for empty symbol table.");
        return Optional.ofNullable(ceiling(key, root));
    }

    private K ceiling(K key, Node node) {
        if (node == null) return null; // we hit the end of the tree without finding anything <= key
        int cmp = key.compareTo(node.key);

        if (cmp == 0) return node.key;
        if (cmp > 0) return ceiling(key, node.right);

        K largerKey = ceiling(key, node.left);
        return largerKey == null ? node.key : largerKey;
    }

    @Override
    public int rank(K key) {
        return 0;
    }

    @Override
    public K select(int k) {
        return null;
    }

    @Override
    public void deleteMin() {

    }

    @Override
    public void deleteMax() {

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

    private class Node {
        private K key;
        private V value;
        private Node left, right;

        private Node(K k, V v) {
            key = k;
            value = v;
            N++;
        }
    }
}
