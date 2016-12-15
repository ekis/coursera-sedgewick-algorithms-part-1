package exercise;

/**
 * Created by ekis on 14/12/16.
 */
public interface MyCollection<T> extends Iterable<T> {

    boolean isEmpty();

    int size();
}
