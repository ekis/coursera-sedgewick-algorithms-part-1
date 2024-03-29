package symbolTable;

import java.util.Collections;
import java.util.Optional;

public interface MySymbolTable<K extends Comparable<? super K>, V> {

    /**
     * Puts key-value pair into the table. Removes key from table if value is null.
     *
     * @param key key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     */
    void put(K key, V value);

    /**
     * * Retrieves value paired with key.
     *
     * @param key key with which the specified value is to be associated
     * @return value paired with key. Optional.empty() if key is not found.
     */
    Optional<V> get(K key);

    /**
     * Removes key (and its value) from the table.
     *
     * @param key key whose mapping is to be removed from the symbol table
     */
    void delete(K key);

    /**
     * Answers if there is a value paired with key.
     *
     * @param key The key whose presence in this symbol table is to be tested
     * @return <tt>true</tt> if this table contains a mapping for the specified key.
     */
    boolean contains(K key);

    /**
     * Answers whether the table is empty.
     *
     * @return <tt>true</tt> if this table is empty.
     */
    boolean isEmpty();

    /**
     * Returns the number of key-value mappings in this symbol table.
     *
     * @return number of key-value pairs in the symbol table.
     */
    int size();

    /**
     * Returns the smallest key in this symbol table.
     *
     * @return the smallest key in this symbol table. Optional.empty() if symbol table is empty.
     */
    Optional<K> min();

    /**
     * Returns the largest key in this symbol table.
     *
     * @return the largest key in this symbol table. Optional.empty() if symbol table is empty.
     */
    Optional<K> max();

    /**
     * Returns the largest key less than or equal to key.
     *
     * @param key the key
     * @return the largest key less than or equal to key. Optional.empty() if no key found.
     */
    Optional<K> floor(K key);

    /**
     * Returns the smallest key greater than or equal to key.
     *
     * @param key the key
     * @return the smallest key greater than or equal to key. Optional.empty() if no key found.
     */
    Optional<K> ceiling(K key);

    /**
     * Returns number of keys less than key.
     *
     * @param key the key
     * @return number of keys less than key
     */
    int rank(K key);


    /**
     * Return the kth smallest key in the symbol table.
     *
     * @param k the order statistic
     * @return key of rank k. Optional.empty() if no key for given k found.
     */
    Optional<K> select(int k);

    /**
     * Deletes the smallest key in this symbol table.
     */
    default void deleteMin(){
        min().ifPresent(this::delete);
    }

    /**
     * Deletes the largest key in this symbol table.
     */
    default void deleteMax() {
        max().ifPresent(this::delete);
    }

    /**
     * Returns the number of keys between [lo...hi].
     *
     * @param lo lower bound
     * @param hi upper bound
     * @return the number of keys between [lo...hi].
     */
    int size(K lo, K hi);

    /**
     * Returns keys between [lo...hi], in sorted order.
     *
     * @param lo lower bound
     * @param hi upper bound
     * @return keys between [lo...hi], in sorted order
     */
    Iterable<K> keys(K lo, K hi);

    /**
     * Returns all keys in the table, in sorted order.
     *
     * @return all keys in the table, in sorted order.
     */
    default Iterable<K> keys() {
        if (isEmpty()) return Collections.emptyList();
        return keys(min().get(), max().get());
    }
}