public class LinkedListDeque<T> {
    private class TNode {
        private TNode pre;
        private T item;
        private TNode next;

        TNode(TNode p, T i, TNode n) {
            pre = p;
            item = i;
            next = n;
        }
    }
// TNode constructed succeeded

    private TNode BackSentinel = new TNode(null, null, null);
    private TNode FrontSentinel = new TNode(null, null, null);
    private int size;

    public LinkedListDeque() {  //empty list deque
        FrontSentinel.next = BackSentinel;
        BackSentinel.pre = FrontSentinel;
        size = 0;
    }

    /*public LinkedListDeque(T x){
        IN FACT, WE DON'T NEED THIS ONE
        FrontSentinel.next = BackSentinel.pre;
        BackSentinel.pre = FrontSentinel.next;
        FrontSentinel.next = BackSentinel;
        FrontSentinel.next = new TNode(BackSentinel.pre, x, FrontSentinel.next);
        size = 1;
    }*/

    public void addFirst(T item) {
        FrontSentinel.next = new TNode(FrontSentinel.next.pre, item, FrontSentinel.next);
        FrontSentinel.next.next.pre = FrontSentinel.next;
        size += 1;
    }

    public void addLast(T item) {
        BackSentinel.pre = new TNode(BackSentinel.pre, item, BackSentinel.pre.next);
        BackSentinel.pre.pre.next = BackSentinel.pre;
        size += 1;
    }

    public boolean isEmpty() {
        return (size == 0);
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        TNode P = FrontSentinel.next;
        while (P.next.item != null) {
            System.out.print(P.item + " ");
            P = P.next;
        }
        System.out.println(P.item);
    }

    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        T re = FrontSentinel.next.item;
        FrontSentinel.next.next.pre = FrontSentinel.next.pre;
        FrontSentinel.next = FrontSentinel.next.next;
        size -= 1;
        return re;
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        T re = BackSentinel.pre.item;
        BackSentinel.pre.pre.next = BackSentinel.pre.next;
        BackSentinel.pre = BackSentinel.pre.pre;
        size -= 1;
        return re;
    }

    public T get(int index) {
        if (isEmpty()) {
            return null;
        } else if (index >= size) {
            System.out.println("INDEX OUT OF RANGE");
            return null;
        }
        TNode P = FrontSentinel.next;
        while (index != 0) {
            P = P.next;
            index -= 1;
        }
        return P.item;
    }

    public LinkedListDeque(LinkedListDeque other) {
        //other.FrontSentinel.next.item.getClass();
        LinkedListDeque<T> copy = new LinkedListDeque<>();
        TNode P = other.FrontSentinel.next;
        while (P != null) {
            copy.addLast(P.item);
        }
    }

    private T helper(TNode node, int index) {
        if (index == 0) {
            return node.item;
        } else if (index >= size) {
            System.out.println("INDEX OUT OF RANGE");
            return null;
        }
        return helper(node.next, index - 1);
    }

    public T getRecursive(int index) {
        return helper(FrontSentinel.next, index);
    }

    // main function for test
    public static void main(String[] args) {
        LinkedListDeque<Integer> dq = new LinkedListDeque<>();
        dq.addFirst(666);
        dq.addLast(888);
        dq.addLast(999);
        dq.addFirst(555);
        dq.printDeque();
    }

}
