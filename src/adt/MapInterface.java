package adt;

/**
 *
 * @author ZY
 */

public interface MapInterface<K, V> {
    
    V get(K key);
    
    void clear();
    
    boolean put(K key, V value);
    
    boolean containsKey(K key);
    
    boolean remove(K key);
    
    int size();
    
    boolean isEmpty();
    
}
