package adt;

/**
 *
 * @author 
 */
public interface MapInterface<K, V> {

    V get(K key);

    void clear();

    void resize();

    boolean put(K key, V value);

    boolean containsKey(K key);

    boolean remove(K key);

    int size();

    boolean isEmpty();

    V getOrDefault(K key, V value);

    K getKey(V value);
}
