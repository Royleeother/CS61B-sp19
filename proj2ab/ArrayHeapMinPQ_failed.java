package bearmaps;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {

    private ArrayList<PriorityNode> items;
    //private HashSet sets;
    private HashMap maps;
    private int smallestPos;
    private int Pos_moveFactor;

    public ArrayHeapMinPQ() {
        items = new ArrayList<>(20);
        smallestPos = 0;
        Pos_moveFactor = 0;
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
            maps.put(item, pos);
            setSmallestPos(pos);
        }
        else {
            double current_prio = items.get(pos).priority;
            double next_prio = items.get(pos + 1).priority;

            if (current_prio <= priority && next_prio > priority) {
                items.add(pos + 1, new PriorityNode(item, priority));
                maps.put(item, pos);
                setSmallestPos(pos);
            } else if (current_prio > priority) {
                comparePriority(priority, pos - 1, item);
            } else if (current_prio <= priority){
                comparePriority(priority, pos + 1, item);
            }
            Pos_moveFactor += 1;
        }
        //之所以要重复maps 和 setsmallestPos 是为了避免后续的重复
    }
    private void setSmallestPos(int pos) {
        double smp_prio = items.get(smallestPos + Pos_moveFactor).priority;
        double current_prio = items.get(pos + Pos_moveFactor).priority;
        if (smp_prio == -99 || current_prio < smp_prio) {
            T current_item = items.get(pos + Pos_moveFactor).item;
            smallestPos = (int)maps.get(current_item);
        }
    }

    @Override
    public boolean contains(T item) {
        //return sets.contains(item);
        return maps.containsKey(item);
    }
    @Override
    public T getSmallest() {
        return items.get(smallestPos + Pos_moveFactor).item;
    }
    @Override
    public T removeSmallest() {
        T re = getSmallest();
        items.remove(smallestPos + Pos_moveFactor);
        maps.remove(re);
        Pos_moveFactor -= 1;
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
 /* 现在问题在于，当执行 add，remove方法的时候，hashmap里的pos他妈是不会随着变的。。。
    参考了一下他们用 sink，swim 的思想 上浮，下沉
    想想那个factor要什么时候用，理论上是可行的，technically
    it failed....totally failed

    */
}
