package symbolTable;

import java.util.Optional;

class MyRedBlack<K extends Comparable<? super K>, V> implements MySymbolTable<K, V> {

    private MyRedBlack(){}

    public static <K extends Comparable<? super K>, V> MySymbolTable<K,V> create() {
        return new MyRedBlack<>();
    }

    @Override
    public void put(K key, V value) {

    }

    @Override
    public Optional<V> get(K key) {
        return null;
    }

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

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Optional<K> min() {
        return null;
    }

    @Override
    public Optional<K> max() {
        return null;
    }

    @Override
    public Optional<K> floor(K key) {
        return null;
    }

    @Override
    public Optional<K> ceiling(K key) {
        return null;
    }

    @Override
    public int rank(K key) {
        return 0;
    }

    @Override
    public Optional<K> select(int k) {
        return null;
    }

    @Override
    public int size(K lo, K hi) {
        return 0;
    }

    @Override
    public Iterable<K> keys(K lo, K hi) {
        return null;
    }

    private class Node {
        private K key;            // key
        private V value;          // associated data
        private Node left, right; // subtrees
        private int subtreeCount; // # of nodes in this subtree
        private Colour colour;    // colour of link from parent to this node

        private Node(K k, V v, int N, Colour c) {
            key = k;
            value = v;
            subtreeCount = N;
            colour = c;
        }
    }

    private enum Colour {
        RED,
        BLACK;
    }
}
