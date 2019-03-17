public class ArrayDeque<T> {

    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;
    private double usage; //add
    private final double usageFactor = 0.25;

    /** Creates an empty list. */
    public ArrayDeque() {
        items = (T[]) new Object[8];
        nextFirst = 4;  //default setting
        nextLast = nextFirst + 1;
        size = 0;
        usage = 0;
    }

    private void adjustPos_addFirst(int pos) {
        pos = (nextFirst - 1) & (items.length - 1);
        nextFirst = pos;
    }

    private void adjustPos_addLast(int pos) {
        pos = (nextLast + 1) & (items.length - 1);
        nextLast = pos;
    }

    private void adjustPos_removeFirst(int pos) {
        pos = (nextFirst + 1) & (items.length - 1);
        nextFirst = pos;
    }

    private void adjustPos_removeLast(int pos) {
        pos = (nextLast - 1) & (items.length - 1);
        nextLast = pos;
    }

    private void checkUsage() {
        int len = items.length;
        if (len >= 16) {
            while (usage < usageFactor) {
                resizeForUsage();
                len = items.length;
                usage = Double.valueOf(size) / Double.valueOf(len);
            }
        }
    }

    private void resizeForUsage() {
        int p = (nextFirst + 1) & (items.length - 1);
        T[] a = (T[]) new Object[size * 2];
        for (int n = 0; n < size; n+=1) {
            System.arraycopy(items, p, a, n + 1, 1);
            p = (p + 1) & (items.length - 1);
        }
        items = a;
        nextFirst = 0;
        nextLast = size + 1;
    }

    private void resize(int capacity) {
        int p = nextFirst;
        int len = items.length;
        int elementsAtRight = len - p;
        T[] a = (T[]) new Object[capacity];
        System.arraycopy(items, p, a, 0, elementsAtRight);
        System.arraycopy(items, 0, a, elementsAtRight, p);
        items = a;
        nextFirst = 0;
        nextLast = len;
    }

    private void regression(){
        int len = items.length;
        if (nextLast == nextFirst) {
            resize(len * 2);
        }
        usage = Double.valueOf(size) / Double.valueOf(len);
        checkUsage();
    }

    public void addFirst(T item) {
        if(item == null) {
            throw new NullPointerException();
        }
        items[nextFirst] = item;
        size += 1;
        adjustPos_addFirst(nextFirst);
        regression();
    }

    public void addLast(T item) {
        if(item == null) {
            throw new NullPointerException();
        }
        items[nextLast] = item;
        size += 1;
        adjustPos_addLast(nextLast);
        regression();
    }

    public boolean isEmpty() { return (size == 0); }

    public int size() { return size; }

    public void printDeque() {
        if (isEmpty()) { System.out.print("null"); }
        int P = 0;
        while (items[P+1] != null) { //problems here
            System.out.print(items[P] + " ");
            P += 1;
        }
        System.out.println(items[P]);
    }

    public T removeFirst() {
        if (size > 0) {
            int pos = (nextFirst + 1) & (items.length - 1);
            T result = items[pos];
            items[pos] = null;
            size -= 1;
            adjustPos_removeFirst(nextFirst);
            regression();
            return result;
        }
        return null;
    }

    public T removeLast() {
        if (size > 0) {
            int pos = (nextLast - 1) & (items.length - 1);
            T result = items[pos];
            items[pos] = null;
            size -= 1;
            adjustPos_removeLast(nextLast);
            regression();
            return result;
        }
        return null;
    }

    private int adjustIndex(int id) {
        int head =  nextFirst + 1;
        head = (head + id) & (items.length - 1);
        return head;
    }

    public T get(int index) {
        if (isEmpty()) {
            return null;
        } else if (index >= size) {
            System.out.println("INDEX OUT OF RANGE");
            return null;
        }
        return items[adjustIndex(index)];
    }

    /*public static void main(String[] args) {
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        ad.addFirst(5);
        ad.addFirst(6);
        ad.addLast(7);
        ad.addFirst(4);
        ad.addLast(8);
        ad.addFirst(3);
        ad.addLast(1);
        ad.addLast(0);
        ad.addLast(9);
        ad.addFirst(2);
        ad.addFirst(1);
        System.out.println(ad.get(0));
        System.out.println(ad.get(7));
        ad.removeLast();
        ad.removeFirst();
        ad.removeFirst();
        ad.removeFirst();
        ad.removeFirst();
        ad.removeFirst();
        ad.removeFirst();
        ad.removeFirst();
        ad.removeFirst();
        ad.removeFirst();
        ad.removeFirst();
        ad.removeFirst();
        ad.removeFirst();
    }*/

}
