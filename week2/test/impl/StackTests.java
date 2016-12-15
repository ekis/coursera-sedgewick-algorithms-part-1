package impl;

import exercise.stacks.MyStack;
import exercise.stacks.Stacks;
import org.junit.Test;

import static impl.Week2TestHelper.testCollectionState;
import static org.junit.Assert.assertEquals;

/**
 * Created by ekis on 12/12/16.
 */
public class StackTests {

    @Test
    public void testSimpleStack() {
        MyStack<Integer> stack = Stacks.simpleStack();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        assertEquals(4, stack.size());
        testCollectionState("4, 3, 2, 1", stack);
        assertEquals(Integer.valueOf(4), stack.pop());
        assertEquals(Integer.valueOf(3), stack.pop());
        assertEquals(Integer.valueOf(2), stack.pop());
        assertEquals(Integer.valueOf(1), stack.pop());
        testCollectionState("", stack);
        stack.push(1);
        assertEquals(1, stack.size());
        stack.pop();
        stack.push(1);
        stack.push(2);
        assertEquals(2, stack.size());
        stack.pop();
        stack.pop();
        stack.push(10);
        stack.push(20);
        stack.push(30);
        assertEquals(3, stack.size());
        testCollectionState("30, 20, 10", stack);
        stack.pop();
        stack.pop();
        stack.pop();
        stack.push(1);
        stack.pop();
        stack.push(1);
        stack.pop();
        stack.push(1);
        stack.pop();
        testCollectionState("", stack);
    }
}