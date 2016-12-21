package jobinterview;

import jobinterview.stacks.MyStack;
import jobinterview.stacks.StackWithMaximum;
import jobinterview.stacks.Stacks;
import org.junit.Test;

import java.util.function.Consumer;

import static jobinterview.Week2TestHelper.testCollectionState;
import static org.junit.Assert.assertEquals;

/**
 * Created by ekis on 12/12/16.
 */
public class StackTests {

    @Test
    public void testSimpleStack() {
        test(Stacks.simpleStack());
    }

    @Test
    public void testStackWithMax() {
        test((StackWithMaximum<Integer>) Stacks.stackWithMax(Integer::compareTo));
    }

    private void test(MyStack<Integer> stack) {
        stage1Push().accept(stack);
        stage2Pop().accept(stack);
        stage3InterleavedPushPop().accept(stack);
        stage4PushPeek().accept(stack);
        stage5Pop().accept(stack);
        stage6InterleavedPushPopWhileEmpty().accept(stack);
    }

    private void test(StackWithMaximum<Integer> stack) {
        stage1Push().accept(stack);
        assertEquals(Integer.valueOf(4), stack.max());
        stage2Pop().accept(stack);
        stage3InterleavedPushPop().accept(stack);
        stage4PushPeek().accept(stack);
        assertEquals(Integer.valueOf(30), stack.max());
        stage5Pop().accept(stack);
        assertEquals(Integer.valueOf(10), stack.max());
        stage6InterleavedPushPopWhileEmpty().accept(stack);
    }

    private Consumer<MyStack<Integer>> stage1Push() {
        return stack -> {
            stack.push(1);
            stack.push(4);
            stack.push(2);
            stack.push(3);
            assertEquals(4, stack.size());
            testCollectionState("3, 2, 4, 1", stack);
        };
    }

    private Consumer<MyStack<Integer>> stage2Pop() {
        return stack -> {
            assertEquals(Integer.valueOf(3), stack.pop());
            assertEquals(Integer.valueOf(2), stack.pop());
            assertEquals(Integer.valueOf(4), stack.pop());
            assertEquals(Integer.valueOf(1), stack.pop());
            testCollectionState("", stack);
        };
    }

    private Consumer<MyStack<Integer>> stage3InterleavedPushPop() {
        return stack -> {
            stack.push(1);
            assertEquals(1, stack.size());
            stack.pop();
            stack.push(1);
            stack.push(2);
            assertEquals(2, stack.size());
            stack.pop();
            stack.pop();
        };
    }

    private Consumer<MyStack<Integer>> stage4PushPeek() {
        return stack -> {
            stack.push(10);
            stack.push(20);
            stack.push(30);
            assertEquals(Integer.valueOf(30), stack.peek());
            assertEquals(Integer.valueOf(30), stack.peek());
            assertEquals(Integer.valueOf(30), stack.peek());
            assertEquals(Integer.valueOf(30), stack.peek());
            assertEquals(3, stack.size());
            testCollectionState("30, 20, 10", stack);
        };
    }

    private Consumer<MyStack<Integer>> stage5Pop() {
        return stack -> {
            stack.pop();
            assertEquals(Integer.valueOf(20), stack.peek());
            stack.pop();
            assertEquals(Integer.valueOf(10), stack.peek());
        };
    }

    private Consumer<MyStack<Integer>> stage6InterleavedPushPopWhileEmpty() {
        return stack -> {
            stack.pop();
            stack.push(1);
            stack.pop();
            stack.push(1);
            stack.pop();
            stack.push(1);
            stack.pop();
            testCollectionState("", stack);
        };
    }
}