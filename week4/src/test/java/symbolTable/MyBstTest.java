package symbolTable;

import org.junit.Test;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

import static org.junit.Assert.*;

public final class MyBstTest {

    @Test
    public void testBstSimple() {
        MySymbolTable<Integer, String> bst = SymbolTableFactory.bst();

        assertTrue(bst.isEmpty());
        bst.put(10, "A");
        checkKeyReturningMethods(bst::min, 10); // min() returns 10
        checkKeyReturningMethods(bst::max, 10); // max() returns 10
        assertFalse(bst.isEmpty());
        bst.put(8, "B");
        bst.put(11, "D");
        bst.put(9, "C");
        bst.put(15, "F");
        bst.put(4, "G");
        bst.put(2, "H");
        assertTrue(bst.contains(10));
        assertTrue(bst.contains(15));
        assertTrue(bst.contains(2));
        assertFalse(bst.contains(1));
        assertFalse(bst.contains(100));
        assertEquals(7, bst.size());
        bst.put(2, "Z");
        bst.put(2, "H");
        assertEquals(7, bst.size()); // check if size remains the same after updating node value
        checkGet(() -> bst.get(2), "H"); // get() returns "H"
        checkGet(() -> bst.get(15), "F");
        checkGet(() -> bst.get(4), "G");
        checkGet(() -> bst.get(10), "A");
        checkKeyReturningMethods(bst::min, 2); // min() returns 2
        checkKeyReturningMethods(bst::max, 15); // max() returns 15
        checkKeyReturningMethods(() -> bst.floor(50), 15); // floor() returns 15
        bst.put(20, "I");
        assertEquals(8, bst.size());
        checkKeyReturningMethods(bst::min, 2); // sanity check
        checkKeyReturningMethods(bst::max, 20);
        bst.put(17, "K");
        assertEquals(9, bst.size());
        checkKeyReturningMethods(bst::max, 20);
        checkKeyReturningMethods(() -> bst.floor(50), 20);
        checkKeyReturningMethods(() -> bst.floor(19), 17);
        checkKeyReturningMethods(() -> bst.floor(8), 8);
        checkKeyReturningMethods(() -> bst.floor(7), 4);
        checkKeyReturningMethods(() -> bst.floor(5), 4);
        checkKeyReturningMethods(() -> bst.floor(3), 2);
        checkKeyReturningMethods(() -> bst.floor(2), 2);
        checkKeyReturningMethods(bst::min, 2); // sanity check
        expectFailOnKeyReturningMethods(() -> bst.floor(1));
        bst.put(1, "J");
        bst.put(1, "JJ");
        bst.put(1, "J");
        assertEquals(10, bst.size()); // check if size remains the same after updating node value
        checkKeyReturningMethods(bst::min, 1);
        checkKeyReturningMethods(() -> bst.floor(1), 1);
        expectFailOnKeyReturningMethods(() -> bst.floor(0));
        expectFailOnKeyReturningMethods(() -> bst.floor(-1));
        checkKeyReturningMethods(() -> bst.ceiling(11), 11);  // ceiling() returns 11
        checkKeyReturningMethods(() -> bst.ceiling(12), 15);
        checkKeyReturningMethods(() -> bst.ceiling(16), 17);
        checkKeyReturningMethods(() -> bst.ceiling(18), 20);
        checkKeyReturningMethods(() -> bst.ceiling(3), 4);
        checkKeyReturningMethods(() -> bst.ceiling(5), 8);
        checkKeyReturningMethods(() -> bst.ceiling(8), 8);
        checkIntReturningMethods(() -> bst.rank(2), 1); // rank() returns 1
        checkKeyReturningMethods(() -> bst.select(1), 2);
        checkIntReturningMethods(() -> bst.rank(9), 4);
        checkKeyReturningMethods(() -> bst.select(4), 9);
        checkIntReturningMethods(() -> bst.rank(6), 3); // key 6 not in symbol table
        checkIntReturningMethods(() -> bst.rank(8), 3);
        checkKeyReturningMethods(() -> bst.select(3), 8);
        checkIntReturningMethods(() -> bst.rank(11), 6);
        checkKeyReturningMethods(() -> bst.select(6), 11);
        checkIntReturningMethods(() -> bst.rank(12), 7); // key 12 not in symbol table
        checkIntReturningMethods(() -> bst.rank(15), 7);
        checkKeyReturningMethods(() -> bst.select(7), 15);
        checkIntReturningMethods(() -> bst.rank(21), bst.size());
        checkIntReturningMethods(() -> bst.size(1, 20), bst.size());
        checkIntReturningMethods(() -> bst.size(4, 15), 6);
        assertFalse(bst.isEmpty());
        checkKeys(bst::keys, "[1, 2, 4, 8, 9, 10, 11, 15, 17, 20]");
        checkKeys(() -> bst.keys(3, 12), "[4, 8, 9, 10, 11]");
        checkKeys(() -> bst.keys(1, 5), "[1, 2, 4]");
        checkKeys(() -> bst.keys(8, 9), "[8, 9]");
        checkKeys(() -> bst.keys(10, 16), "[10, 11, 15]");
    }

    @Test
    public void testBstRandom() {
        Random rnd = new Random();
        String[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".split("");
        int N = alphabet.length;
        int T = 500000;

        List<Integer> keys = IntStream.range(0, T).boxed().collect(Collectors.toList());

        // load the BST with T uniform, non-repeating keys, taking a uniformly random letter from alphabet
        // also storing the K,V pair into a HashMap for comparison purposes
        Map<Integer, String> refMap = new HashMap<>();
        MySymbolTable<Integer, String> bst =
                IntStream.range(0, T)
                        .collect(SymbolTableFactory::bst, (acc, rangeIndex) -> {
                            Integer key = keys.remove(rnd.nextInt(T - rangeIndex));
                            String val = alphabet[rnd.nextInt(N)];
                            refMap.put(key, val);
                            acc.put(key, val);
                        }, (u, v) -> {});
        assertFalse(bst.isEmpty());
        assertEquals(T, bst.size());
        checkKeyReturningMethods(bst::min, 0);
        checkKeyReturningMethods(bst::max, 499999);

        refMap.forEach((key, value) -> {
            assertEquals(value, bst.get(key).orElseThrow(IllegalStateException::new)); // iterate over all entries in that HashMap, checking for each get from HashMap, each get from BST produces an equal value
            checkKeyReturningMethods(() -> bst.floor(key), key); // ensure floor() of every key exists and it is that same key
            checkKeyReturningMethods(() -> bst.ceiling(key), key);
            checkIntReturningMethods(() -> bst.rank(key), key);
            checkKeyReturningMethods(() -> bst.select(key), key);
            assertTrue(bst.contains(key));
        });

        bst.put(590000, "A");
        bst.put(600000, "A");
        bst.put(610000, "A");
        assertFalse(bst.isEmpty());
        assertEquals(bst.size(), 500003);
        assertTrue(bst.contains(590000));  // spot checks
        assertTrue(bst.contains(600000));
        assertTrue(bst.contains(610000));
        assertFalse(bst.contains(700000));
        checkKeyReturningMethods(() -> bst.floor(591000), 590000);
        checkKeyReturningMethods(() -> bst.floor(601000), 600000);
        checkKeyReturningMethods(() -> bst.floor(611000), 610000);
        checkKeyReturningMethods(() -> bst.ceiling(581000), 590000);
        checkKeyReturningMethods(() -> bst.ceiling(591000), 600000);
        checkKeyReturningMethods(() -> bst.ceiling(601000), 610000);
        checkKeyReturningMethods(() -> bst.select(500000), 590000);
        checkIntReturningMethods(() -> bst.rank(591000), 500001);
        checkKeyReturningMethods(() -> bst.select(500001), 600000);
        checkIntReturningMethods(() -> bst.rank(601000), 500002);
        checkKeyReturningMethods(() -> bst.select(500002), 610000);
        checkIntReturningMethods(() -> bst.rank(610001), 500003);
        checkKeys(() -> bst.keys(499998, 609000), "[499998, 499999, 590000, 600000]");
        assertFalse(bst.isEmpty());
    }

    private static <V> void checkGet(Supplier<Optional<V>> f, V expectedValue) {
        assertEquals(expectedValue, f.get().get());
    }

    private static <K extends Comparable<? super K>> void checkKeyReturningMethods(Supplier<Optional<K>> f, K expectedKey) {
        assertEquals(expectedKey, f.get().get());
    }

    private static <T> void checkIntReturningMethods(Supplier<T> f, T expected) {
        assertEquals(expected, f.get());
    }

    private static <K extends Comparable<? super K>> void checkKeys(Supplier<Iterable<K>> f, String expected) {
        String actual = Arrays.toString(StreamSupport.stream(f.get().spliterator(), false).toArray());
        assertEquals(expected, actual);
    }

    private static <K extends Comparable<? super K>> void expectFailOnKeyReturningMethods(Supplier<Optional<K>> supplier) {
        try {
            K result = supplier.get().get();
            fail("Unexpectedly found key -> " + result);
        } catch (NoSuchElementException e) {
            // muffle exception, we expect this to fail
        }
    }
}