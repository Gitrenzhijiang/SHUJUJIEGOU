package map;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 1.7 版本的HashMap
 * @author REN
 *
 */
public class HashMap7 <K, V>{
    public static void main(String[] args) {
//        for (Integer i = 0;i < 10;i++) {
//            System.out.println(i.hashCode() + ":::" + hash(i));
//        }
        Object obj = "12a";
        System.out.println(obj.hashCode() + "::" + hash(obj));
        System.out.println(48736 ^ (48736 >>> 16));
        // 12a 的index
        for (int i = 0;i < 20;i++) {
            int l = 15 & hash("aabc" + i*i); // 
            int r = 31 & hash("aabc" + i*i);
            if (l != r) {
                System.out.println("===============" + l+":" +r);
            }
        }
        
        
        HashMap<String, String> map;
        
        HashMap7<String, String> hm7 = new HashMap7<>();
        hm7.put("111", "23456");// 1
        hm7.put("0", "0"); // 0
//        hm7.put("12a", "12a");// 0
        
        System.out.println(hm7.get("111"));
        System.out.println(hm7.get("0"));
        System.out.println(hm7.get("12a"));
        
        
    }
    
    public HashMap7() {
        // 初始化table
        table = new Node[DEFAULT_INITIAL_CAPACITY];
        threshold = (int) (DEFAULT_INITIAL_CAPACITY * DEFAULT_LOAD_FACTOR);
    }
    
    /**
     * 默认初始化长度 为16
     */
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; 
    /**
     * 默认装填因子 0.75; 
     */
    static final float DEFAULT_LOAD_FACTOR = 0.75f;
    
    private static class Node<K, V>{
        final int hash;
        final K key;
        V value;
        Node<K,V> next;
        Node(int hash, K key, V value, Node<K,V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }
        
        public final K getKey()        { return key; }
        public final V getValue()      { return value; }
        public final String toString() { return key + "=" + value; }
        public final V setValue(V newValue) {
            V oldValue = value;
            value = newValue;
            return oldValue;
        }
        // ??? 为什么这里要覆盖 hashCode 和 equals方法
        public final int hashCode() {
            return Objects.hashCode(key) ^ Objects.hashCode(value);
        }
        public final boolean equals(Object o) {
            if (o == this)
                return true;
            if (o instanceof Map.Entry) {
                Map.Entry<?,?> e = (Map.Entry<?,?>)o;
                if (Objects.equals(key, e.getKey()) &&
                    Objects.equals(value, e.getValue()))
                    return true;
            }
            return false;
        }
        
    }
    /**
     * =========================
     */
    transient Node<K,V>[] table;
    transient int size;
    /**
     * 扩容的阈值 = DEFAULT_LOAD_FACTOR * capacity 
     */
    transient int threshold;
    /**
     * =======================
     */
    public int size() {
        return size;
    }
    public V get(Object key) {
        int index = indexFor(hash(key), table.length);
        Node<K, V> node = table[index];
        if (node != null) {
            // 必须equals
            while (node != null) {
                if (node.getKey().equals(key)) {
                    return node.getValue();
                }
                node = node.next;
            }
        }
        return null;
    }
    public V put(K key, V value) {
        if (key == null)
            return putForNullKey(value);
        int hash = hash(key);
        int index = indexFor(hash, table.length);
        
        if (table[index] != null) {
            // 遍历一下看是否有重复的key
            Node<K, V> next = table[index];
            while (next != null) {
                if (next.hash == hash) {
                    V oldV = next.getValue();
                    next.setValue(value);
                    return oldV;
                }
                next = next.next;
            }
        }
        // table[index] == null 或者没有重复元素， 直接插入
        return addNodeOfIndex(key, value, hash, index);
    }
    //=====================
    /**
     * 把 null->value 放入table[0]中
     * @param value
     * @return
     */
    private V putForNullKey(V value) {
        
        if (table[0] == null) {
            return addNodeOfIndex(null, value, 0, 0);
        }else {
            V oldV = table[0].getValue();
            table[0].setValue(value);
            return oldV;
        }
    }
    // 插入在index
    private V addNodeOfIndex(K key, V value, int hash, int index) {
        Node node = new Node<K, V>(hash, key, value, null);
        V oldV = table[index] == null?null:table[index].value;
        node.next = table[index];
        table[index] = node;
        size++;
        return oldV;
    }
    /**
     * 当当前元素个数达到threshold时，使容量加倍，并且重新计算 hash后的位置
     * 16->32     [0-15] -> [0-31]     原本处在0位置的元素就非常有可能在1.还是在0位置， 2.可能hash后的位置变成了[16]
     * resize 操作和 put 操作等都有可能在并发时候导致死锁
     */
    private void resize() {
        // 
    }
    
    /**
     * 计算在数组中具体位置
     * @param hash
     * @param length
     * @return
     */
    static int indexFor(int hash, int length) {
        // assert Integer.bitCount(length) == 1 : "length must be a non-zero power of 2";
        /**
         * 0000 1111  1101 0101  1010 0010  1111 [1/0]010 这是hash
         * 0000 0000  0000 0000  0000 0000  0000 0111 这是size=8
         * 0000 0000  0000 0000  0000 0000  0000 1111 size扩容到16时, hash的第4位决定了是否需要更换位置，更换的位置是oldIndex+oldcapacity
         */
        return hash & (length-1);
    }
    /**
     * 计算一个键的hash值, 对于null的键返回0
     * @param key
     * @return
     */
    private static final int hash(Object key) {
        /**
         * 关于这里我的理解: 
         * 如果一个数的hashCode:
         * 0101 0000  1010 0000  0000 0000 1101 0010
         * 0000 0000  0000 0000  0101 0000 1010 0000
         * 高位16位和地16位 ^ 构成 低16位
         * 原来高16位 不变
         */
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }
    
}
