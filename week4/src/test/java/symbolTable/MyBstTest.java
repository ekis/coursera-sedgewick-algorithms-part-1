package symbolTable;

import org.junit.Test;

import java.util.*;
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
        checkEquality(bst, 2, "H");
        checkEquality(bst, 15, "F");
        checkEquality(bst, 4, "G");
        checkEquality(bst, 10, "A");
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

        refMap.forEach((key, value) -> assertEquals(value, bst.get(key)));
    }

    private static <K extends Comparable<? super K>, V> void checkEquality(MySymbolTable<K, V> bst, K key, V expectedValue) {
        assertEquals(bst.get(key), expectedValue);
    }
}