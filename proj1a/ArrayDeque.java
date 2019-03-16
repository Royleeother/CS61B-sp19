public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    /** Creates an empty list. */
    public ArrayDeque() {
        items = (T[]) new Object[8];
        nextFirst = 4;
        nextLast = 5;
        size = 0;
    }

    public int addjustPos_add(int pos) {

    }

    public int addjustPos_remove(int pos) {

    }

    public void addFirst(T item) {
        items[nextFirst] = item;
        size += 1;
        addjustPos_add(nextFirst);
    }

    public void addLast(T item) {
        items[nextLast] = item;
        size += 1;
        addjustPos_add(nextLast);
    }

    public boolean isEmpty() { return (size == 0); }

    public int size() { return size; }

    public void printDeque() {
        if (isEmpty()) { System.out.print("null"); }
        int P = 0;
        while (items[P+1] != null) { //ÕâÐÐ´æÒÉ
            System.out.print(items[P] + " ");
            P += 1;
        }
        System.out.println(items[P]);
    }

    public T removeFirst() {
        T result = items[nextFirst+1];
        items[nextFirst+1] = null;
        size -= 1;
        addjustPos_remove(nextFirst);
        return result;
    }

    public T removeLast() {
        T result = items[nextLast-1];
        items[nextLast-1] = null;
        size -= 1;
        addjustPos_remove(nextLast);
        return result;
    }

    public T get(int index) {
        if (isEmpty()) {
            return null;
        } else if (index >= size) {
            System.out.println("INDEX OUT OF RANGE");
            return null;
        }
        return items[index];
    }

}
