package bearmaps;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {

    private ArrayList<PriorityNode> items;
    //private HashSet sets;
    private HashMap maps;

    public ArrayHeapMinPQ() {
        items = new ArrayList<>(20);
        for (int i = 0; i < 20; i++) {
            items.add(new PriorityNode((T) "INITIALIZE", -99));
        }
        //sets = new HashSet<T>();
        maps = new HashMap<T, Integer>();
    }

    private class PriorityNode {
        private T item;
        private double priority;

        public PriorityNode(T item, double priority) {
            this.item = item;
            this.priority = priority;
        }
    }

    @Override
    public void add(T item, double priority) {
        int pos = (int) priority;
        if (maps.containsKey(item)) {
            throw new IllegalArgumentException();
        } else {
            comparePriority(priority, pos, item);
        }
        //sets.add(item);
    }

    private void comparePriority(double priority, int pos, T item) {
        if (items.get(pos).priority == -99) {
            items.set(pos, new PriorityNode(item, priority));
        }
        else {
            double current_prio = items.get(pos).priority;
            double next_prio = items.get(pos + 1).priority;

            if (current_prio <= priority && next_prio > priority) {
                items.add(pos, new PriorityNode(item, priority));
            } else if (current_prio > priority) {
                comparePriority(priority, pos - 1, item);
            } else if (current_prio <= priority){
                comparePriority(priority, pos + 1, item);
            }
        }
        maps.put(item, pos);

    }
    @Override
    public boolean contains(T item) {
        //return sets.contains(item);
        return maps.containsKey(item);
    }
    @Override
    public T getSmallest() {
        return items.get(0).item;
    }
    @Override
    public T removeSmallest() {
        T re = getSmallest();
        items.remove(0);
        return re;
    }
    @Override
    public int size() {
        return items.size();
    }
    @Override
    public void changePriority(T item, double priority) {
        if (!maps.containsKey(item)) {    //!sets.contains(item)
            throw new NoSuchElementException();
        }
        //items.remove(new PriorityNode((T) "hi", 2.5));  // 这里存疑，明天再搞 连这样都remove不了
        int key = (int) maps.get(item);
        items.remove(key);
        maps.remove(item);
        add(item, priority);
    }

    public static void main(String[] args) {
        ArrayHeapMinPQ<String> a = new ArrayHeapMinPQ<>();
        a.items.size();
        a.add("hi", 2.5);
        a.changePriority("hi", 3);
    }

}
