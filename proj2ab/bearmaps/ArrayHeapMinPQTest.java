package bearmaps;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ArrayHeapMinPQTest {
    @Test
    public void sanityGenericsTest() {
        try {
            ArrayHeapMinPQ<String> a = new ArrayHeapMinPQ<String>();
            ArrayHeapMinPQ<Integer> b = new ArrayHeapMinPQ<Integer>();
        } catch (Exception e) {
            fail();
        }
    }
    @Test
    public void addTest() {
        ArrayHeapMinPQ<String> a = new ArrayHeapMinPQ<String>();

        a.add("hi", 2.5);
        a.add("hi", 5);

    }
}
