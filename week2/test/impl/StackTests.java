package impl;

import org.junit.Test;
import exercise.stacks.MyStack;
import exercise.stacks.Stacks;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.Assert.assertEquals;

/**
 * Created by pakna on 12/12/16.
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
        testStackState("4, 3, 2, 1", stack);
        assertEquals(new Integer(4), stack.pop());
        assertEquals(new Integer(3), stack.pop());
        assertEquals(new Integer(2), stack.pop());
        assertEquals(new Integer(1), stack.pop());
        testStackState("", stack);
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
        testStackState("30, 20, 10", stack);
        stack.pop();
        stack.pop();
        stack.pop();
        stack.push(1);
        stack.pop();
        stack.push(1);
        stack.pop();
        stack.push(1);
        stack.pop();
        testStackState("", stack);
    }

    private <T> void testStackState(String expected, MyStack<T> stack) {
        String actual = StreamSupport.stream(stack.spliterator(), false)
                .map(Object::toString)
                .collect(Collectors.joining(", "));
        assertEquals(expected, actual);
    }
}