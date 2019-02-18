package tree;

import java.util.Comparator;
import java.util.LinkedList;

/**
 * 红黑树基本实现
 * @author renzhijiang
 * 已经通过基本 插入 测试
 */
public class RedBlackTree<K, V> {
    public static void main(String[] args) {
        RedBlackTree<Integer, String> tree = new RedBlackTree<>();
        tree.put(11, "11");
        tree.put(2, "2");
        tree.put(14, "14");
        tree.put(1, "1");
        tree.put(7, "7");
        tree.put(15, "15");
        tree.put(5, "5");
        tree.put(8, "8");
        tree.put(4, "4");
        System.out.println("打印树结构:");
        System.out.println(tree);
        System.out.println(tree.remove(1));
        tree.put(1, "1");
        System.out.println(tree);
//        TreeMap<K, V>
        
    }
    /**
     * 红黑树哨兵结点, 代表NULL结点
     */
    private final static Entry NIL = new Entry<>(null, null, null);
    
    /**
     * 红黑树根结点
     */
    private transient Entry<K, V> root = NIL;
    
    /**
     * 红黑树的大小
     */
    private transient int size = 0;
    
    /**
     * 这个comparator用于红黑树中结点比较, 如果为空则使用结点key的自然排序 
     */
    private final Comparator<? super K> comparator;
    
    /**
     * 构造一个新的空的红黑树, 并使用自然排序
     */
    public RedBlackTree(){
        comparator = null;
    }
    /**
     * 构造一个新的空的红黑树, 并使用指定的比较器
     */
    public RedBlackTree(Comparator<? super K> comparator){
        this.comparator = comparator;
    }
    /**
     * 将指定键值对插入红黑树中, 如果树中已经包含这个key, 则原先的值将被替换
     * @param key
     * @param value
     * @return 与key相关联的前一个的值, 如果先前没有相关的key返回null, 否则
     * 返回先前相关联的值
     */
    public V put(K key, V value) {
        Entry<K, V> entry = new Entry<>(key, value, null);
        return insert(entry);
    }
    /**
     * 删除key对应树中的结点, 并返回被删除结点的值;如果不存在该结点返回null
     * @param key
     * @return
     */
    public V remove(K key) {
        Entry<K, V> x = getEntry(key);
        if (x == NIL)
            return null;
        V oldV = x.getValue();
        deleteEntry(x);
        return oldV;
    }
    /**
     * 用于比较两个两个key
     * @param k1
     * @param k2
     * @return
     */
    @SuppressWarnings("unchecked")
    final int compare(Object k1, Object k2) {
        return comparator == null?
                ((Comparable<? super K>)k1).compareTo((K)k2) :
                    comparator.compare((K)k1, (K)k2);
    }
    
    /**
     * 红黑树 颜色布尔标记
     */
    private static final boolean BLACK = true;
    private static final boolean RED = false;
    /**
     * 红黑树内部树的结点结构
     * 
     */
    static final class Entry<K, V> {
        
        K key;
        V value;
        Entry<K,V> left;
        Entry<K,V> right;
        Entry<K,V> parent;
        boolean color = BLACK;
        
        public Entry(K key, V value, Entry<K, V> parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }
        public void setKey(K key) {
            this.key = key;
        }
        public void setValue(V value) {
            this.value = value;
        }
        public K getKey() {
            return key;
        }
        public V getValue() {
            return value;
        }
        
        
        @Override
        public String toString() {
            return "key=" + key + ", value=" + value + ", color=" + (color==true?"Black":"Red") + ", p=" + parent.key;
        }
        
    }
    /**
     * 返回在数中key对应的结点, 如果没有则返回NIL
     * @param key
     * @return
     */
    final Entry<K, V> getEntry(K key){
        Entry<K, V> x = root;
        while (x != NIL) {
            int j = compare(x.getKey(), key);
            if (j > 0) {
                x = leftOf(x);
            }else if (j < 0) {
                x = rightOf(x);
            }else {
                return x;
            }
        }
        return NIL;
    }
    /**
     * 将z这个新结点插入红黑树, 调用前必须保证不等于Null
     * 如果红黑树中已经存在, 返回其OldValue
     * @param z
     * @return
     */
    V insert(Entry<K, V> z) {
        Entry<K, V> y = NIL;
        Entry<K, V> x = root;
        while (x != NIL) {
            y = x; 
            if (compare(x.key, z.key) > 0) {
                x = x.left;
            }else {
                x = x.right;
            }
        }
        if (y == NIL) {
            root = z;
        }else if (compare(y.key, z.key) > 0) {
            y.left = z;
        }else if (compare(y.key, z.key) < 0){
            y.right = z;
        }else {
            V oldV = y.value;
            y.setValue(z.getValue());
            return oldV;
        }
        z.left = NIL;
        z.right = NIL;
        z.color = RED;
        z.parent = y;
        // 修复被破坏的红黑树的性质
        insert_fixup(z);
        return z.getValue();
    }
    /**
     * 修复由于z结点插入红黑树而导致树的性质改变
     * @param z
     */
    void insert_fixup(Entry<K, V> z){
        Entry<K, V> y = null;
        while (z != NIL && z.parent.color != BLACK) {
            
            if (z.parent.parent.left == z.parent) {
                y = z.parent.parent.right;
                // 情况一:z和z的父节点都是红色且 z的叔叔y是红色
                if (y.color == RED) {
                    y.color = BLACK;
                    z.parent.color = BLACK;
                    z = z.parent.parent;
                    z.color = RED;
                }else {
                    //z和z的父节点都是红色, 但z的叔叔y是黑色;
                    if (z.parent.right == z) {
                        z = z.parent;
                        rotateLeft(z);
                    }
                    z.parent.color = BLACK;
                    z.parent.parent.color = RED;
                    rotateRight(z.parent.parent);
                }
            }else {
                y = z.parent.parent.left;
                //z和z的父节点都是红色, z的叔叔y是红色
                if (y.color == RED) {
                    y.color = BLACK;
                    z.parent.color = BLACK;
                    z = z.parent.parent;
                    z.color = RED;
                }else {
                    //z和z的父节点都是红色, 但z的叔叔y是黑色;
                    if (z.parent.left == z) {
                        z = z.parent;
                        rotateRight(z);
                    }
                    z.parent.color = BLACK;
                    z.parent.parent.color = RED;
                    rotateLeft(z.parent.parent);
                }
            }
        }
        root.color = BLACK;
    }
    /**
     * 右旋
     * @param z 最高的结点
     */
    private void rotateRight(Entry<K, V> z) {
       if (z != NIL) {
           Entry<K, V> l = z.left;
           z.left = l.right;
           if (rightOf(l) != NIL)
               rightOf(l).parent = z;
           l.parent = z.parent;
           if (l.parent == NIL) {
               root = l;
           }else if (l.parent.left == z) {
               l.parent.left = l;
           }else {
               l.parent.right = l;
           }
           z.parent = l;
           l.right = z;
       }
    }
    /**
     * 左旋
     * @param z 最高的结点
     */
    private void rotateLeft(Entry<K, V> z) {
       if (z != NIL) {
           Entry<K, V> r = z.right;
           z.right = r.left;
           if (r.left != NIL) {
               leftOf(r).parent = z;
           }
           r.parent = z.parent;
           if (z.parent == NIL) {
               root = r;
           }else if (z.parent.left == z) {
               r.parent.left = r;
           }else {
               r.parent.right = r;
           }
           r.left = z;
           z.parent = r;
       }
    }
    
    
    
    public void order(Entry<K, V> node) {
        if (node != NIL) {
            order(node.left);
            System.out.println(node);
            order(node.right);
        }
    }
    /**
     * 返回这个结点的后继结点, 如果不存在返回NIL
     * @param entry
     * @return
     */
    static <K,V> RedBlackTree.Entry<K,V> successor(Entry<K,V> t){
        if (t == NIL) {
            return NIL;
        }else if (t.right != NIL) {
            Entry<K, V> y = t.right;
            while (y.left != NIL) {
                y = y.left;
            }
            return y;
        }else {
            Entry<K, V> y = t.parent;
            while (y != NIL && y.right == t) {
                t = y;
                y = y.parent;
            }
            return y;
        }
    }
    static<K, V> Entry<K, V> leftOf(Entry<K, V> e) {
        return e.left;
    }
    static<K, V> Entry<K, V> rightOf(Entry<K, V> e) {
        return e.right;
    }
    static<K, V> Entry<K, V> parentOf(Entry<K, V> e) {
        return e.parent;
    }
    static<K, V> void setColor(Entry<K, V> e, boolean color) {
        e.color = color;
    }
    static<K, V> boolean colorOf(Entry<K, V> e) {
        return e.color;
    }
    /**
     * 从当前红黑树中将u结点替换成v
     * @param u
     * @param v
     */
    void transplant(Entry<K, V> u, Entry<K, V> v) {
        if (u == root) {
            root = v;
        }else if (u.parent.left == u) {
            u.parent.left = v;
        }else {
            u.parent.right = v;
        }
        v.parent = u.parent;
    }
    
    void deleteEntry(Entry<K, V> z){
        
//        if (z.left == NIL) {
//            // z 只有右孩子或者没有孩子
//            transplant(z, z.right);
//        }else if (z.right == NIL) {
//            // z 只有左孩子
//            transplant(z, z.left);
//        }else {
//            // z有两个孩子
//            
//        }
        if (z.left != NIL && z.right != NIL) {
            Entry<K, V> y = successor(z);
            z.key = y.key;
            z.value = y.value;
            z = y;
        }
        Entry<K, V> replacement = z.left != NIL ? z.left : z.right;
        if (replacement != NIL) {
            // 要删除的结点不是root
            // 链接replacement 和 z的父亲
            transplant(z, replacement);
            // 删除 z 与其他结点的联系
            z.left = z.right = z.parent = NIL;
            if (z.color == BLACK) {
                delete_fixup(replacement);
            }
        }else if (replacement.parent == NIL) {
            //要删除的结点是root
            root = NIL;
        }else {
            if (colorOf(z) == BLACK) {
                delete_fixup(z);
            }
            // 要删除的孩子没有结点且不是root
            transplant(z, NIL);
        }
    }
    void delete_fixup(Entry<K, V> x) {
        while (x != root && x.color == BLACK) {
            // 要删除的x是左孩子
            if (leftOf(parentOf(x)) == x) {
                Entry<K, V> w = rightOf(parentOf(x));
                // 要删除结点的兄弟是红色的
                if (w.color == RED) {
                    setColor(parentOf(x), RED);
                    setColor(w, BLACK);
                    rotateLeft(parentOf(x));
                    w = rightOf(parentOf(x));
                }
                // 兄弟的孩子结点全是黑色的
                // w 变成红色, B变成黑色
                if (colorOf(leftOf(w)) == BLACK &&
                     colorOf(rightOf(w)) == BLACK) {
                    setColor(w, RED);
                    x = parentOf(w);
                }else {
                    // 兄弟结点的左孩子是红色的
                    if (colorOf(leftOf(w)) == RED) {
                        setColor(w, RED);
                        setColor(leftOf(w), BLACK);
                        rotateRight(w);
                        w = parentOf(w);
                    }
                    // 兄弟结点的右孩子是红色的
                    setColor(w, colorOf(parentOf(x)));
                    setColor(rightOf(w), BLACK);
                    setColor(parentOf(x), BLACK);
                    rotateLeft(parentOf(x));
                    // 结束循环
                    x = root;
                }
                
            }else {
                Entry<K, V> w = leftOf(parentOf(x));
                // 要删除结点的兄弟是红色的
                if (colorOf(w) == RED) {
                    setColor(parentOf(x), RED);
                    setColor(w, BLACK);
                    rotateRight(parentOf(x));
                    w = leftOf(parentOf(x));
                }
                // 兄弟的孩子结点全是黑色的
                // w 变成红色, B变成黑色
                if (colorOf(leftOf(w)) == BLACK &&
                     colorOf(rightOf(w)) == BLACK) {
                    setColor(w, RED);
                    x = parentOf(w);
                }else {
                    // 兄弟结点的右孩子是红色的
                    if (colorOf(rightOf(w)) == RED) {
                        setColor(w, RED);
                        setColor(rightOf(w), BLACK);
                        rotateLeft(w);
                        w = parentOf(w);
                    }
                    // 兄弟结点的左孩子是红色的
                    setColor(w, colorOf(parentOf(x)));
                    setColor(leftOf(w), BLACK);
                    setColor(parentOf(x), BLACK);
                    rotateLeft(parentOf(x));
                    // 结束循环
                    x = root;
                }
            }
        }
        
        setColor(x, BLACK);
    }
    
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        if (root != NIL) {
            LinkedList<Entry<K, V>> list = new LinkedList<>();
            list.addLast(root);
            int count = 1;
            int savecount = 0;
            while (!list.isEmpty()) {
                Entry<K, V> ele = list.removeLast();
                System.out.print("[" + ele + "]");
                count--;
                //加入它的所有孩子
                if (ele.left != NIL) {
                    list.addFirst(ele.left);
                    savecount++;
                }
                if (ele.right != NIL) {
                    list.addFirst(ele.right);
                    savecount++;
                }
                if (count == 0) {
                    System.out.println();
                    count = savecount;
                    savecount = 0;
                }
            }
        }
        return sb.toString();
    }
}
