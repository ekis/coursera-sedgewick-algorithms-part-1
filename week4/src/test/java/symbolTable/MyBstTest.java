package symbolTable;

import org.junit.Test;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public final class MyBstTest {

    @Test
    public void testBst() {
        MySymbolTable<Integer, String> bst = SymbolTableFactory.bst();

        bst.put(10, "A");
        bst.put(8, "B");
        bst.put(9, "C");
        bst.put(11, "D");
        bst.put(15, "F");
        bst.put(4, "G");
        bst.put(2, "H");
        checkGet(bst, 2, "H");
        checkGet(bst, 15, "F");
        checkGet(bst, 4, "G");
        checkGet(bst, 10, "A");
        checkKeyReturningMethods(bst::min, 2);
        checkKeyReturningMethods(bst::max, 15);
        checkKeyReturningMethods(() -> bst.floor(50), 15);
        bst.put(20, "I");
        checkKeyReturningMethods(bst::min, 2); // sanity check
        checkKeyReturningMethods(bst::max, 20);
        bst.put(17, "K");
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
        checkKeyReturningMethods(bst::min, 1);
        checkKeyReturningMethods(() -> bst.floor(1), 1);
        expectFailOnKeyReturningMethods(() -> bst.floor(0));
        expectFailOnKeyReturningMethods(() -> bst.floor(-1));
        checkKeyReturningMethods(() -> bst.ceiling(11), 11);
        checkKeyReturningMethods(() -> bst.ceiling(12), 15);
        checkKeyReturningMethods(() -> bst.ceiling(16), 17);
        checkKeyReturningMethods(() -> bst.ceiling(18), 20);
        checkKeyReturningMethods(() -> bst.ceiling(3), 4);
        checkKeyReturningMethods(() -> bst.ceiling(5), 8);
        checkKeyReturningMethods(() -> bst.ceiling(8), 8);
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
        assertEquals(T, bst.size());
        checkKeyReturningMethods(bst::min, 0);
        checkKeyReturningMethods(bst::max, 499999);

        refMap.forEach((key, value) -> {
            assertEquals(value, bst.get(key).orElseThrow(IllegalStateException::new)); // iterate over all entries in that HashMap, checking for each get from HashMap, each get from BST produces an equal value
            checkKeyReturningMethods(() -> bst.floor(key), key); // ensure floor() of every key exists and it is that same key
            checkKeyReturningMethods(() -> bst.ceiling(key), key); // ensure ceiling() of every key exists and it is that same key
        });

        bst.put(600000, "A");
        bst.put(590000, "A");
        bst.put(610000, "A");
        checkKeyReturningMethods(() -> bst.floor(591000), 590000); // spot check
        checkKeyReturningMethods(() -> bst.floor(601000), 600000); // spot check
        checkKeyReturningMethods(() -> bst.floor(611000), 610000); // spot check
        checkKeyReturningMethods(() -> bst.ceiling(581000), 590000); // spot check
        checkKeyReturningMethods(() -> bst.ceiling(591000), 600000); // spot check
        checkKeyReturningMethods(() -> bst.ceiling(601000), 610000); // spot check

    }

    private static <K extends Comparable<? super K>, V> void checkGet(MySymbolTable<K, V> bst, K key, V expectedValue) {
        assertEquals(expectedValue, bst.get(key).get());
    }

    private static <K extends Comparable<? super K>> void checkKeyReturningMethods(Supplier<Optional<K>> supplier, K expectedKey) {
        assertEquals(expectedKey, supplier.get().get());
    }

    private static <K extends Comparable<? super K>> void expectFailOnKeyReturningMethods(Supplier<Optional<K>> supplier) {
        try {
            supplier.get().get();
        } catch (NoSuchElementException e) {
            // muffle exception, we expect this to fail
        }
    }
}