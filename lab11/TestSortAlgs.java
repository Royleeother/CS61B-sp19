import edu.princeton.cs.algs4.Queue;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestSortAlgs {

    @Test
    public void testQuickSort() {
        Queue<Integer> tas = new Queue<Integer>();
        tas.enqueue(39);
        tas.enqueue(6);
        tas.enqueue(199);
        tas.enqueue(2);
        tas.enqueue(55);

        tas = QuickSort.quickSort(tas);

        assertTrue(isSorted(tas));
    }

    @Test
    public void testMergeSort() {
        Queue<Integer> tas = new Queue<Integer>();
        tas.enqueue(39);
        tas.enqueue(6);
        tas.enqueue(199);
        tas.enqueue(2);
        tas.enqueue(55);

        tas = MergeSort.mergeSort(tas);

        assertTrue(isSorted(tas));
    }

    /**
     * Returns whether a Queue is sorted or not.
     *
     * @param items  A Queue of items
     * @return       true/false - whether "items" is sorted
     */
    private <Item extends Comparable> boolean isSorted(Queue<Item> items) {
        if (items.size() <= 1) {
            return true;
        }
        Item curr = items.dequeue();
        Item prev = curr;
        while (!items.isEmpty()) {
            prev = curr;
            curr = items.dequeue();
            if (curr.compareTo(prev) < 0) {
                return false;
            }
        }
        return true;
    }
}
