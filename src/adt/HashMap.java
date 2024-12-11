package adt;

import java.io.Serializable;

/**
 *
 * @author ZY
 */

public class HashMap<K, V> implements MapInterface<K, V>, Serializable {
    
    private static final int DEFAULT_CAPACITY = 10;
    public Entry<K, V>[] table;
    private int size;
    
    public HashMap(){
        this(DEFAULT_CAPACITY);
    }
    
    public HashMap(int capacity){
        table = new Entry[capacity];
        size = 0;
    }
    
    private static class Entry<K, V>{
        K key;
        V value;
        Entry <K, V> next;
        
        public Entry (K key, V value){
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }
    
    
    private int hash(K key){
        return key.hashCode() % table.length;
    }
    
    @Override
    public V get(K key){
        int index = hash(key);
        Entry<K, V> current = table[index];
        while(current != null){
            if (current.key.equals(key)){
                return current.value;
            }
            current = current.next;
        }
        return null;
    }
    
    @Override
    public void clear(){
        table = new Entry[table.length];
        size = 0;
    }
    
    @Override
    public boolean put (K key, V value){
        int index = hash(key);
        Entry<K, V> newEntry = new Entry<>(key, value);
        if(table[index] == null){
            table[index] = newEntry;
        }
        else{
            Entry<K, V> current = table[index];
            while (current.next != null){
                if(current.key.equals(key)){
                    current.value = value;
                    return true;
                }
                current = current.next;
            }
            if(current.key.equals(key)){
                current.value = value;
            }
            else{
                current.next = newEntry;
            }
        }
        size++;
        return true;
    }
    
    @Override
    public boolean containsKey(K key){
        return get(key) != null;
    }
    
    @Override 
    public boolean remove(K key){
        int index = hash(key);
        Entry<K, V> current = table[index];
        Entry<K, V> previous = null;
        
        while(current != null){
            if(current.key.equals(key)){
                if(previous == null){
                    table[index] = current.next;
                }
                else{
                    previous.next = current.next;
                }
                size --;
                return true;
            }
            previous = current;
            current = current.next;
        }
        return false;
    }
    
    @Override
    public int size(){
        return size;
    }
    
    @Override
    public boolean isEmpty(){
        return size == 0;
    }
    

    
}
