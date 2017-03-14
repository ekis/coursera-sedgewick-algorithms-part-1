package setintersection;

import ekis.common.TestResourceLoader;
import sort.MyShell;
import sort.heapsort.MyHeapsort;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public final class MyBinarySearchTest {

    @Test
    public void testSearch() {
        Integer[] input = new Integer[]{56, 13, 28, 43, 503, 2, 1, 15, 3, 40, 202};
        MyShell.sort(input);
        assertTrue(MyBinarySearch.find(1, input));
        assertTrue(MyBinarySearch.find(2, input));
        assertTrue(MyBinarySearch.find(202, input));
        assertFalse(MyBinarySearch.find(777, input));
    }

    @Test
    public void testSearchOnLargeDataset() throws IOException, URISyntaxException {
        Integer[] input = TestResourceLoader.loadIntegersFrom("largeW.zip");
        MyHeapsort.sort(input);
        assertTrue(MyBinarySearch.find(519039, input));
        assertTrue(MyBinarySearch.find(935085, input));
        assertTrue(MyBinarySearch.find(20038, input));
        assertFalse(MyBinarySearch.find(1, input));
    }
}
