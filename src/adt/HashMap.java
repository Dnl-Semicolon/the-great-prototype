package adt;

import java.io.Serializable;

/**
 *
 * @author
 */
public class HashMap<K, V> implements MapInterface<K, V>, Serializable {

    private static final int DEFAULT_CAPACITY = 10;
    public Entry<K, V>[] table;
    private int size;

    public HashMap() {
        this(DEFAULT_CAPACITY);
    }

    public HashMap(int capacity) {
        table = new Entry[capacity];
        size = 0;
    }

    public static class Entry<K, V> implements Serializable {

        K key;
        V value;
        Entry<K, V> next;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }
    }

    private int hash(K key) {
        return Math.abs(key.hashCode()) % table.length;
    }

    @Override
    public V get(K key) {
        int index = hash(key);
        Entry<K, V> current = table[index];
        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    @Override
    public void clear() {
        table = new Entry[table.length];
        size = 0;
    }

    @Override
    public void resize() {
        Entry<K, V>[] newTable = new Entry[table.length * 2];
        for (Entry<K, V> entry : table) {
            Entry<K, V> current = entry;
            while (current != null) {
                int newIndex = Math.abs(current.key.hashCode()) % newTable.length;
                Entry<K, V> next = current.next;
                current.next = newTable[newIndex];
                newTable[newIndex] = current;
                current = next;
            }
        }
        table = newTable;
    }

    @Override
    public boolean put(K key, V value) {
        int index = hash(key);
        Entry<K, V> newEntry = new Entry<>(key, value);
        if (table[index] == null) {
            table[index] = newEntry;
        } else {
            Entry<K, V> current = table[index];
            while (current.next != null) {
                if (current.key.equals(key)) {
                    current.value = value;
                    return true;
                }
                current = current.next;
            }
            if (current.key.equals(key)) {
                current.value = value;
            } else {
                current.next = newEntry;
            }
        }
        size++;
        if (size >= table.length * 0.75) {
            resize();
        }
        return true;
    }

    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    @Override
    public boolean remove(K key) {
        int index = hash(key);
        Entry<K, V> current = table[index];
        Entry<K, V> previous = null;

        while (current != null) {
            if (current.key.equals(key)) {
                if (previous == null) {
                    table[index] = current.next;
                } else {
                    previous.next = current.next;
                }
                size--;
                return true;
            }
            previous = current;
            current = current.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public V getOrDefault(K key, V defaultValue) {
        V value = get(key);
        return value != null ? value : defaultValue;
    }

    @Override
    public K getKey(V value) {
        for (Entry<K, V> entry : table) {
            Entry<K, V> current = entry;
            while (current != null) {
                if (current.value.equals(value)) {
                    return current.key;
                }
                current = current.next;
            }
        }
        return null;
    }

    public ListInterface<K> keySet() {
        ListInterface<K> keys = new ArrayList<>();
        for (Entry<K, V> entry : table) {
            Entry<K, V> current = entry;
            while (current != null) {
                keys.add(current.key);
                current = current.next;
            }
        }
        return keys;
    }
    
    
}
