import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.Iterator;

public class MyHashMap<K,V> implements Map61B<K,V> {
    private double loadFactor = 0.75;
    private int size;
    private ArrayList<K>[]      hashMap;
    //private MyHashMap<K,V>[] hashMap;
    private int initialSize = 16;
    private HashSet<K> keySet;

    /*
    陷入自我循环了，每一个hashmap的格子里面，不能再装hashmap了，要装其他类型
    要不就是死循环了
    我打算搞个ArrayList
     */

    // three constructors
    public MyHashMap() { // default hash table
        hashMap = new MyHashMap(initialSize, loadFactor).hashMap;
        //this(initialSize, loadFactor);
    }
    public MyHashMap(int initialSize) {
        hashMap = new MyHashMap(initialSize, loadFactor).hashMap;
    }
    public MyHashMap(int initialSize, double loadFactor) {
        hashMap = new ArrayList(initialSize);
        //hashMap = (MyHashMap<K,V>[]) new MyHashMap[initialSize];
        this.loadFactor = loadFactor;
        size = 0;
        for (int i = 0; i < hashMap.length; i += 1) {
            hashMap[i] = new ArrayList();
        }
    }
    @Override
    public void clear() {
        //hashMap = new MyHashMap[0];
        for (int i = 0; i < hashMap.length; i += 1) {
            hashMap[i] = new MyHashMap();
        }
        size = 0;
    }
    @Override
    public V get(K key) {
        return (V) hashMap[hash(key)].get(key);
    }
    public int hash(K key) {
        int numBuckets = hashMap.length;
        return Math.floorMod(key.hashCode(), numBuckets);
    }
    @Override
    public boolean containsKey(K key) {
        if (hashMap == null) {
            return false;
        }
        return hashMap[hash(key)].get(key) != null;
    }
    @Override
    public int size() {
        return size;
    }
    @Override
    public Set<K> keySet() {
        return keySet;
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
        return keySet.iterator();
    }
    @Override
    public void put(K key, V value) {
        int h = hash(key);
        hashMap[h].put(key, value);
        size += 1;
        keySet.add(key);
    }
}
