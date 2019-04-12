package bearmaps;

// @source: yngz https://github.com/yngz/cs61b/blob/master/proj2ab/bearmaps/ArrayHeapMinPQ.java

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    private ArrayList<PriorityNode> heap;
    private HashMap map;

    public ArrayHeapMinPQ() {
        heap = new ArrayList();
        map = new HashMap<T, Integer>();
    }

    private class PriorityNode {
        private T item;
        private double priority;

        public PriorityNode(T item, double priority) {
            this.item = item;
            this.priority = priority;
        }
    }

    private static int parent(int i) {
        return (i - 1) / 2;
    }

    private static int leftNode(int i) {
        return 2 * i + 1;
    }

    private static int rightNode(int i) {
        return 2 * i + 2;
    }

    private boolean less(int i, int j) {
        return heap.get(i).priority < heap.get(j).priority;
    }

    private void swim(int i) {
        while (i > 0) {
            int p = parent(i);
            if (!less(i, p)) {
                break;
            }
            swap(i, p);
            i = p;
        }
    }

    private void swap(int i, int j) {
        PriorityNode tempt = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, tempt);
        map.put(heap.get(i).item, i);
        map.put(heap.get(j).item, j);
    }

    private void sink(int i) {
        //    i < size()
        while (leftNode(i) < size()) {
            int left = leftNode(i);
            int right = rightNode(i);
            if (less(left, i)) {
                swap(i, left);
                i = left;
            } else if (less(right, i)) {
                swap(i, right);
                i = right;
            } else {
                break;
            }
        }
    }

    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException();
        }
        heap.add(new PriorityNode(item, priority));
        map.put(item, size() - 1);
        swim(size() - 1);
    }
    @Override
    public boolean contains(T item) {
        return map.containsKey(item);
    }
    @Override
    public T getSmallest() {
        if (heap.isEmpty()) {
            throw new NoSuchElementException();
        }
        return heap.get(0).item;
    }
    @Override
    public T removeSmallest() {
        if (heap.isEmpty()) {
            throw new NoSuchElementException();
        }
        T min = heap.get(0).item;
        swap(0, size() - 1);
        heap.remove(size() - 1);
        sink(0);
        map.remove(min);
        return min;
    }
    @Override
    public int size() {
        return heap.size();
    }
    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException();
        }
        int i = (int)map.get(item);
        double oldPriority = heap.get(i).priority;
        //heap.set(i, new PriorityNode(item, priority));
        heap.get(i).priority = priority;
        if (oldPriority < priority) {
            sink(i);
        } else {
            swim(i);
        }
        /*if (less(i, leftNode(i)) || less(i, rightNode(i))) {
            swim(i);
        } else {
            sink(i);
        }*/
    }

}
