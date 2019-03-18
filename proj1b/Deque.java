public interface Deque<T> {

    // Add one item to the front of the Deque
    void addFirst(T i);

    // Add one item to the back of the Deque
    void addLast(T i);

    // Return a boolean to tell if a deque is empty
    boolean isEmpty();

    /** Return the size of a queue. */
    int size();

    /** Print every elements in a queue, separated by a space. */
    void printDeque();

    /** Delete the first item and return it. */
    T removeFirst();

    /** Delete the last item and return it. */
    T removeLast();

    /*
    Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
    If no such item exists, returns null. Must not alter the deque!
     */
    T get(int index);

}
