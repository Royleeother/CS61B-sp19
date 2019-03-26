package es.datastructur.synthesizer;
import java.util.Iterator;

public class ArrayRingBuffer<T> implements BoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Variable for the fillCount. */
    private int fillCount;
    /* Array for storing the buffer data. */
    private T[] rb;
    // record for capacity
    private int kp;
    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        kp = capacity;
        rb = (T[]) new Object[capacity];
        first = capacity / 2;
        last  = capacity / 2;
        fillCount = 0;
    }

    @Override
    public int capacity() {
        return kp;
    }
    @Override
    public int fillCount() {
        return fillCount;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow").
     */
    @Override
    public void enqueue(T x) {
        if (isFull()) {
            throw new RuntimeException("Ring buffer overflow");
        }
        rb[last] = x;
        last += 1;
        fillCount += 1;
        if (last == capacity()) {
            last = 0;
        }
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        T re =  (T) rb[first];
        rb[first] = null;
        first += 1;
        fillCount -= 1;
        if (first == capacity()) {
            first = 0;
        }
        return re;
    }

    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        return (T) rb[first];
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayRingIterator();
    }
    private class ArrayRingIterator implements Iterator<T> {
        private int wizPos;
        private int count;
        public ArrayRingIterator() {
            //wizPos = 0;
            wizPos = first;
            count = 0;
        }
        public boolean hasNext() {
            return count < fillCount();
        }
        public T next() {
            T re = rb[wizPos];
            wizPos += 1;
            if (wizPos == capacity()) {
                wizPos = 0;
            }
            count += 1;
            return re;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;

        } else if (o == null) {
            return false;

        } else if (this.getClass() != o.getClass()) {
            return false;
        }
        ArrayRingBuffer<T> other = (ArrayRingBuffer<T>) o;
        if (this.fillCount() != other.fillCount()) {
            return false;
        }
        Iterator<T> this_iter = iterator();
        Iterator<T> other_iter = other.iterator();
        while (this_iter.hasNext() && other_iter.hasNext()) {
            if (this_iter.next() != other_iter.next()) {
                return false;
            }
        }
        return true;
    }

}

