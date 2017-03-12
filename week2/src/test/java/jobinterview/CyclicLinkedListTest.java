package jobinterview;

import jobinterview.linkedlist.CyclicLinkedList;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class CyclicLinkedListTest {

    @Test
    public void testListNoCycle() {
        CyclicLinkedList.AddNext<Integer> listBuilder = standardOddListBuilder();
        CyclicLinkedList<Integer> list = listBuilder.defineCycleOrBuild().build();
        assertEquals(Optional.empty(), list.firstNodeInCycle());

        listBuilder = standardEvenListBuilder();
        list = listBuilder.defineCycleOrBuild().build();
        assertEquals(Optional.empty(), list.firstNodeInCycle());
    }

    @Test
    public void testListCycleAtBeginning() {
        CyclicLinkedList.AddNext<Integer> listBuilder = standardOddListBuilder();

        CyclicLinkedList<Integer> list = listBuilder
                .defineCycleOrBuild()
                .connectLastTo(4)
                .build();

        assertEquals(Optional.of(10), list.firstNodeInCycle());

        listBuilder = standardEvenListBuilder();

        list = listBuilder
                .defineCycleOrBuild()
                .connectLastTo(5)
                .build();

        assertEquals(Optional.of(10), list.firstNodeInCycle());
    }

    @Test
    public void testListCycleInMiddle() {
        CyclicLinkedList.AddNext<Integer> listBuilder = standardOddListBuilder();

        CyclicLinkedList<Integer> list = listBuilder
                .defineCycleOrBuild()
                .connectLastTo(2)
                .build();

        assertEquals(Optional.of(30), list.firstNodeInCycle());

        listBuilder = standardEvenListBuilder();

        list = listBuilder
                .defineCycleOrBuild()
                .connectLastTo(2)
                .build();

        assertEquals(Optional.of(40), list.firstNodeInCycle());
    }

    @Test
    public void testListCycleAtEnd() {
        CyclicLinkedList.AddNext<Integer> listBuilder = standardOddListBuilder();

        CyclicLinkedList<Integer> list = listBuilder
                .defineCycleOrBuild()
                .connectLastTo(0)
                .build();

        assertEquals(Optional.of(50), list.firstNodeInCycle());

        listBuilder = standardEvenListBuilder();

        list = listBuilder
                .defineCycleOrBuild()
                .connectLastTo(0)
                .build();

        assertEquals(Optional.of(60), list.firstNodeInCycle());
    }

    private static CyclicLinkedList.AddNext<Integer> standardOddListBuilder() {
        return CyclicLinkedList.<Integer>builder().addNext(50).addNext(40).addNext(30).addNext(20).addNext(10);
    }

    private static CyclicLinkedList.AddNext<Integer> standardEvenListBuilder() {
        return CyclicLinkedList.<Integer>builder().addNext(60).addNext(50).addNext(40).addNext(30).addNext(20).addNext(10);
    }
}
