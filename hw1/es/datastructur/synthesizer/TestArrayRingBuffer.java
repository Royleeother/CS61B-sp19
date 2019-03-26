package es.datastructur.synthesizer;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void testIterator() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(10);
        for (int i = 0; i < arb.capacity(); i += 1) {
            arb.enqueue(i * 2);
        }
        Iterator iterator = arb.iterator();
        //Iterator<Integer> in = new Iterator<>();
        for (int x : arb) {
            for (int y : arb) {
                System.out.println("x: " + x + "; y: " + y);
            }
        }
        //Iterator<String> this_iter = new Iterable<>();
        //BoundedQueue<Integer> arb = new
    }
}
