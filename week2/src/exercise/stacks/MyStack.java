package exercise.stacks;

import exercise.MyCollection;

/**
 * Created by ekis on 14/12/16.
 */
public interface MyStack<T> extends MyCollection<T> {

    void push(T item);

    T pop();

    T peek();
}
