package impl;

import exercise.MyCollection;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.Assert.assertEquals;

/**
 * Created by ekis on 14/12/16.
 */
class Week2TestHelper {

    static <T> void testCollectionState(String expected, MyCollection<T> collection) {
        String actual = StreamSupport.stream(collection.spliterator(), false)
                .map(Object::toString)
                .collect(Collectors.joining(", "));
        assertEquals(expected, actual);
    }
}