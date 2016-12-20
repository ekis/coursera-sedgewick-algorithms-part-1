package impl;

import exercise.CyclicLinkedList;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by ekis on 20/12/16.
 */
public class CyclicLinkedListTest {

    @Test
    public void testListNoCycle() {
        CyclicLinkedList.AddNext<Integer> listBuilder = standardListBuilder();
        CyclicLinkedList<Integer> list = listBuilder.defineCycleOrBuild().build();
        assertEquals(null, list.firstNodeInCycle());
    }

    @Test
    public void testListCycleAtBeginning() {
        CyclicLinkedList.AddNext<Integer> listBuilder = standardListBuilder();

        CyclicLinkedList<Integer> list = listBuilder
                .defineCycleOrBuild()
                .connectLastTo(4)
                .build();

        assertEquals(Integer.valueOf(10), list.firstNodeInCycle());
    }

    @Test
    public void testListCycleInMiddle() {
        CyclicLinkedList.AddNext<Integer> listBuilder = standardListBuilder();

        CyclicLinkedList<Integer> list = listBuilder
                .defineCycleOrBuild()
                .connectLastTo(2)
                .build();

        assertEquals(Integer.valueOf(30), list.firstNodeInCycle());
    }

    @Test
    public void testListCycleAtEnd() {
        CyclicLinkedList.AddNext<Integer> listBuilder = standardListBuilder();

        CyclicLinkedList<Integer> list = listBuilder
                .defineCycleOrBuild()
                .connectLastTo(0)
                .build();

        assertEquals(Integer.valueOf(50), list.firstNodeInCycle());
    }

    private static CyclicLinkedList.AddNext<Integer> standardListBuilder() {
        return CyclicLinkedList.<Integer>builder().addNext(50).addNext(40).addNext(30).addNext(20).addNext(10);
    }
}
