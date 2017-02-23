package jobinterview;

import jobinterview.linkedlist.RandomLinkedList;
import org.junit.Test;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.Assert.*;

public class RandomLinkedListTest {

    @Test
    public void test() {
        RandomLinkedList<Integer> linkedList = new RandomLinkedList<>();
        assertTrue(linkedList.isEmpty());

        for (int i = 0; i < 130; i++) linkedList.add(i);

        assertFalse(linkedList.isEmpty());
        assertEquals(130, linkedList.size());

        String original = stringTraceOf(linkedList);

        RandomLinkedList<Integer> clonedList = linkedList.cloneLinkedList();
        String cloned = stringTraceOf(clonedList);
        assertEquals(original, cloned);
    }

    private String stringTraceOf(RandomLinkedList<Integer> list) {
        return StreamSupport.stream(list.spliterator(), false)
                .collect(Collectors.joining("\n"));
    }
}