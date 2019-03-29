
import java.util.Iterator;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K,V> {
    private Node root;
    public class Node {
        private K key;
        private V value;
        private Node left, right; // links to the subtree
        private int count;  // counts how many subtrees below this node, including itself

        public Node(K key, V value, int count) {
            this.key = key;
            this.value = value;
            this.count = count;
        }
    }

    /** Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
    }
    /* Returns true if this map contains a mapping for the specified key. */
    private boolean containsKey_helper(Node x, K key) {
        if (x == null) {
            return false;
        }
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) {
            return containsKey_helper(x.left, key);
        } else if (cmp > 0) {
            return containsKey_helper(x.right, key);
        } else {
            return true;
        }
    }

    @Override
    public boolean containsKey(K key) {
        return containsKey_helper(root, key);
    }
    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */

    private V get_helper(Node x, K key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) return get_helper(x.left, key);
        else if (cmp > 0) return get_helper(x.right, key);
        else return x.value;
    }

    @Override
    public V get(K key) {
        return get_helper(root, key);
    }
    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size(root);
    }
    private int size(Node x) {
        if (x == null) return 0;
        else return x.count;
    }
    /* Associates the specified value with the specified key in this map. */
    private void put_helper(Node x, K key, V value) {
        if (x == null) new Node(key, value, 0);
        int cmp = key.compareTo(x.key);
        if (cmp < 0) put_helper(x.left, key, value);
        else if (cmp > 0) put_helper(x.right, key, value);
        x.count = size(x.left) + size(x.right) + 1;
    }

    @Override
    public void put(K key, V value) {
        put_helper(root, key, value);
    }
    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }
    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }
    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }
    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

    private void print_helper(Node x) {
        if (x == null) System.out.println(' ');
        else {
            System.out.println(x.value);
            print_helper(x.right);
            print_helper(x.left);
        }
    }
    public void printInOrder() {
        print_helper(root);
    }
}
