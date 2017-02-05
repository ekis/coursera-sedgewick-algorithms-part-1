package jobinterview;

import jobinterview.linkedlist.RandomLinkedList;
import org.junit.Test;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.Assert.*;

/**
 * Created by ekis on 31/01/17.
 */
public class RandomLinkedListTest {

    @Test
    public void test() {
        RandomLinkedList<Integer> linkedList = new RandomLinkedList<>();
        assertTrue(linkedList.isEmpty());

        for (int i = 0; i < 130; i++) linkedList.add(i);

        assertFalse(linkedList.isEmpty());
        assertEquals(130, linkedList.size());

        String original = stringTraceOf(linkedList);
        //System.out.println("Original list -> \n" + original);

        RandomLinkedList<Integer> clonedList = linkedList.cloneLinkedList();
        String cloned = stringTraceOf(clonedList);
        //System.out.println("Cloned list -> \n" + cloned);
        assertEquals(original, cloned);
    }

    private String stringTraceOf(RandomLinkedList<Integer> list) {
        return StreamSupport.stream(list.spliterator(), false)
                .collect(Collectors.joining("\n"));
    }
}