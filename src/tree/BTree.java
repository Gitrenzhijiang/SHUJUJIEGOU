package tree;

import java.util.LinkedList;
import java.util.List;
/**
 * 一个B树的简单实现
 * @author renzhijiang
 * 
 * @param <E>
 */
public class BTree<E> {
    
    public static void main(String[] args) {
        BTree<String> btree = new BTree<>(3);
        btree.insert("G");btree.insert("M");btree.insert("P");btree.insert("X");
        btree.insert("A");btree.insert("C");btree.insert("D");btree.insert("E");
        btree.insert("J");btree.insert("K");
        btree.insert("N");btree.insert("O");
        btree.insert("R");btree.insert("S");btree.insert("T");btree.insert("U");btree.insert("V");
        btree.insert("Y");btree.insert("Z");
        btree.insert("W");btree.insert("B");
        btree.printBTree();
    }
    
    public BTree() {}
    public BTree(int t) {
        this.t = t;
    }
    /** 当前B树的t值, 除根节点外内部结点至少有t个孩子, 至多2t个孩子 */
    private int t = 2;
  
    /** 根节点 */
    private Node<E> root;
    
    /**
     * B树内部的数据结构
     * @param <E>
     */
    private static class Node<E>{
        /** 当前结点是否是叶子结点 */
        boolean leaf = true;
        /** keys */
        List<E> keys = new LinkedList<>();
        /** 孩子链表 */
        List<Node<E>> childs = new LinkedList<>();
        Node(){}
        Node(boolean leaf){
            this.leaf = leaf;
        }
        /** 是否是叶子结点 */
        boolean getLeaf(){
            return leaf;
        }
        /** 设置叶子结点 */
        void setLeaf(boolean isleaf){
            leaf = isleaf;
        }
        /** 返回这个B数结点中的键的数量 */
        int getSizeOfKeys() {
            return keys.size();
        }
        /** 返回子结点的数量 */
        int getSizeOfChilds(){
            return childs.size();
        }
        /** 返回第i位置的键 i [0..getSizeOfKeys()-1] */
        E getKey(int i){
            return keys.get(i);
        }
        /** 将该键插入i位置, i [0..getSizeOfKeys()] */
        void setKey(int i, E element){
            keys.add(i, element);
        }
        void setKey(E element){
            keys.add(element);
        }
        /** 用新值替换i位置的旧值, 并返回之 */
        E setNewKey(int i, E newElement) {
            E ret = keys.remove(i);
            keys.add(i, newElement);
            return ret;
        }
        /** 得到i位置的子结点 i [0..getSizeOfChilds()-1]*/
        Node<E> getChildNode(int i){
            return childs.get(i);
        }
        /** 在i位置插入新的子结点, i[0..getSizeOfChilds()] */
        void setChildNode(int i, Node<E> childNode){
            childs.add(i, childNode);
        }
        /** 在子结点列表尾部插入新的子结点 */
        void setChildNode(Node<E> childNode){
            childs.add(childNode);
        }
        /** 移除i位置的孩子结点 */
        Node<E> removeChildNode(int i){
            return childs.remove(i);
        }
        /** 移除i位置的键 */
        E removeKey(int i){
            return keys.remove(i);
        }
    }

    /** 这个结构表示在指定结点i位置的key */
    private static class KeyResult<E>{
        Node<E> node;
        int i;
        KeyResult(Node<E> node, int i){
            this.node = node;
            this.i = i;
        }
    }
    /** 比较两个key之间的大小 */
    @SuppressWarnings("unchecked")
    final static<E> int compare(Object k1, Object k2) {
        Comparable<? super E> com_k1 = (Comparable<? super E>)k1;
        return com_k1.compareTo((E)k2);
    }
    /** 如果未找到, 返回null */
    final KeyResult<E> search(Node<E> x, E key){
        if (x == null){
            return null;
        }else{
            int i = 0;
            while (i < x.getSizeOfKeys() && compare(key, x.getKey(i)) > 0){
                i++;
            }
            if (compare(key, x.getKey(i)) == 0){
                return new KeyResult(x, i);
            }else if(x.getLeaf() == true){
                return null;
            }else {
                return search(x.getChildNode(i), key);
            }

        }
    }
    /**
     * 拆分i位置上的满结点
     * @param x 需要拆分结点的父结点
     * @param i 满子结点的位置
     */
    final void split_child(Node<E> x, int i){
        Node<E> y = x.getChildNode(i);
        // y是满结点, 即y.n == 2t-1, 例如 t=2 时, keys.size = 3
        Node<E> nex = new Node<>();
        nex.setLeaf(y.getLeaf());
        for (int j = 0;j < t-1;j++){
            nex.setKey(y.removeKey(0));
            if(y.getSizeOfChilds() > 0)
                nex.setChildNode(y.removeChildNode(0));
        }
        if(y.getSizeOfChilds() > 0)
            nex.setChildNode(y.removeChildNode(0));
        x.setKey(i, y.removeKey(0));
        x.setChildNode(i, nex);
    }
    /**
     * 插入key
     * @param key key
     */
    final public void insert(E key){
        // 如果根节点已经满了
        Node<E> y = this.root;
        if (y == null) {
            //创建一个根节点
            y = this.root = new Node<>(true);
        }
        if (y.getSizeOfKeys() == 2*t-1){
            Node<E> nex = new Node<>(false);
            this.root = nex;
            nex.setChildNode(y);
            split_child(this.root, 0);
        }
        insert_notFull(this.root, key);
    }
    final private void insert_notFull(Node<E> y, E key){
        int i = 0;
        while (i < y.getSizeOfKeys() && compare(key, y.getKey(i)) > 0){
            i++;
        }
        if (y.getLeaf() == true){
            y.setKey(i, key);
        }else{ // 此时, 我们需要判断y的第i个孩子是否已经满了, 递归插入
            Node<E> iy = y.getChildNode(i);
            if (iy.getSizeOfKeys() == 2*t-1){
                split_child(y, i);
                insert_notFull(y, key);
            }else {
                insert_notFull(iy, key);
            }
        }
    }
    // 无法理解真正含义
    public final E deleteKey(E key) {
        Node<E> x = this.root;
        
        
        int i = 0;
        while (i < x.getSizeOfKeys() && compare(key, x.getKey(i)) > 0) {
            i++;
        }
        if (compare(key, x.getKey(i)) == 0){
            // 要删除的元素在 x 中
            // 1.如果x是叶子结点, 删除之(后面的情况会考虑它的结点树少与t)
//            if (x.getLeaf() == true) {
//                x.removeKey(i);
//            }else if(x.getChildNode(i).getSizeOfKeys() > t-1){// x 不是叶子结点, 如果x中的前i个子结点
//                x.getChildNode(i).removeKey(i)
//            }
            return deleteNode(x, i);
        }
        // 要删除的结点在i位置的子结点为根的树中
        if (((i-1 >= 0 && x.getChildNode(i-1).getSizeOfKeys() == t-1) || (i-1 < 0))
                && ((i+1 < x.getSizeOfChilds() && x.getChildNode(i+1).getSizeOfKeys() == t-1) || (i+1 >= x.getSizeOfChilds()))
                && (x.getChildNode(i).getSizeOfKeys() == t-1)) {
            //合并
            Node<E> child = null;
            int temp_i = 0;
            if (i-1 >= 0) {
                temp_i = i-1;
                child = x.getChildNode(i-1);
            }
            else {
                temp_i = i+1;
                child = x.getChildNode(i+1);
            }
                
            delete_union(x, x.getChildNode(i), child, i, temp_i);
        }else {
            int mub_index = exist(x.getChildNode(i), key);
            if (mub_index != -1) {
                if (x.getChildNode(i).getSizeOfKeys() == t-1 && (i-1 >= 0 && x.getChildNode(i-1).getSizeOfKeys() > t-1)) {
                    
                }else if(x.getChildNode(i).getSizeOfKeys() == t-1 && (i+1 < x.getSizeOfChilds() && x.getChildNode(i+1).getSizeOfKeys() > t-1)) {
                    
                }
            }
            
        }
        return null;
        
        
    }
    private int exist(Node<E> x, E key) {
        for (int i = 0;i < x.getSizeOfKeys();i++) {
            if (compare(key, x.getKey(i)) == 0) {
                return i;
            }
        }
        return -1;
    }
    
    private final void delete_union(Node<E> root, Node<E> c1, Node<E> c2, int index1, int index2) {
        root.removeChildNode(index1);
        root.removeChildNode(index2);
        for (int i = c1.getSizeOfKeys()-1;i >= 0;i--) {
            root.setKey(0, c1.removeKey(i));
            root.setKey(c2.removeKey(0));
        }
        for (int i = c1.getSizeOfChilds()-1;i >= 0;i--) {
            root.setChildNode(0, c1.removeChildNode(i));
        }
        for (int i = 0;i < c2.getSizeOfChilds();i++) {
            root.setChildNode(c2.removeChildNode(0));
        }
    }
    
    /**
     * 删除x结点的第i个key
     * @param x
     * @param i
     * @return
     */
    final E deleteNode(Node<E> x, int i) {
        if (x.getLeaf() == true) {
            return x.removeKey(i);
        }else if(x.getChildNode(i).getSizeOfKeys() > t-1){// x 不是叶子结点, 如果x中的前i个子结点
            E _x = deleteNode(x.getChildNode(i), x.getChildNode(i).getSizeOfKeys()-1);
            return x.setNewKey(i, _x);
        }else if(x.getChildNode(i+1).getSizeOfKeys() > t-1) {
            E _x = deleteNode(x.getChildNode(i+1), 0);
            return x.setNewKey(i, _x);
        }else {
            Node<E> prex = x.getChildNode(i);
            Node<E> lastx = x.getChildNode(i+1);
            prex.childs.addAll(lastx.childs);
            prex.keys.addAll(lastx.keys);
            x.removeChildNode(i+1);
            return x.removeKey(i);
        }
    }
    
    
    
    public void printBTree() {
        if (root == null) {
            System.out.println("NIL BTree!");
            return;
        }
        LinkedList<Node<E>> queue = new LinkedList<>();
        queue.addFirst(root);
        int num = 1;
        int save = 0;
        while (!queue.isEmpty()) {
            Node<E> y = queue.removeLast();
            System.out.print("[");
            for (int i = 0;i < y.getSizeOfKeys();i++) {
                System.out.print(y.getKey(i) + " ");
            }
            System.out.print("]");
            num--;
            
            //加入y的所有子结点
            for (int i = 0;i < y.getSizeOfChilds();i++) {
                queue.addFirst(y.getChildNode(i));
                save++;
            }
            if (num == 0) {
                num = save;
                save = 0;
                System.out.println();
            }
        }
    }

}
