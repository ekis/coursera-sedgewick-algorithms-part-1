package symbolTable;

import org.junit.Test;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

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
        checkMinMax(bst::min, 2);
        checkMinMax(bst::max, 15);
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
        // then iterate over all entries in that HashMap, checking for each get from HashMap, each get from BST produces an equal value
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
        checkMinMax(bst::min, 0);
        checkMinMax(bst::max, 499999);

        refMap.forEach((key, value) -> assertEquals(value, bst.get(key).orElseThrow(IllegalStateException::new)));
    }

    private static <K extends Comparable<? super K>, V> void checkGet(MySymbolTable<K, V> bst, K key, V expectedValue) {
        assertEquals(expectedValue, bst.get(key).orElseThrow(IllegalStateException::new));
    }

    private static <K extends Comparable<? super K>> void checkMinMax(Supplier<Optional<K>> supplier, K expectedKey) {
        assertEquals(expectedKey, supplier.get().orElseThrow(IllegalStateException::new));
    }
}