package heap;


/**
 * 斐波那契堆
 * @author renzhijiang
 * 定义: [a] 是一系列具有最小堆序的有根树的集合;  每一颗树都具有最小堆的性质
 * [b] 每一个节点有  x.parent指针(下同), x.child, x.left, x.right  
 * 和 x.degree(该结点的度) 和 x.mark (结点x 自从上一次成为另一个结点的孩子后,
 * 它便为true,当它失去孩子时变成false)
 * [c] 每一个结点都有一个最大的度数D(n),  
 * 根结点链表的度都必须个各不相同即 根结点链表最多有D(n)+1 个….
 * 
 * 
 * T: 斐波那契堆存放的 数据类型
 */
public class FibHeap<T extends Comparable<T>>{
    
    public static void main(String[] args) {
        FibHeap<Integer> fibheap = new FibHeap<Integer>();
        
        Item<Integer> a23 = new Item<Integer>(23);
        Item<Integer> a7 = new Item<Integer>(7);
        Item<Integer> a21 = new Item<Integer>(21);
        Item<Integer> a3 = new Item<Integer>(3);a3.degree = 3;
            Item<Integer> a18 = new Item<Integer>(18);a18.degree = 1;
                Item<Integer> a39 = new Item<Integer>(39);a39.parent = a18;a39.left = a39;a39.right = a39;
            Item<Integer> a52 = new Item<Integer>(52);
            Item<Integer> a38 = new Item<Integer>(38);a38.degree = 1;
                Item<Integer> a41 = new Item<Integer>(41);a41.parent = a38;a41.left = a41;a41.right = a41;
        Item<Integer> a17 = new Item<Integer>(17);a17.degree = 1;
            Item<Integer> a30 = new Item<Integer>(30);a30.left = a30;a30.right=a30;a30.parent = a17;a17.child = a30;
        Item<Integer> a24 = new Item<Integer>(24);a24.degree =  2;
            Item<Integer> a26 = new Item<Integer>(26);a26.degree = 1;
                Item<Integer> a35 = new Item<Integer>(35);a35.left = a35;a35.right = a35;a35.parent = a26;a26.child = a35;
            Item<Integer> a46 = new Item<Integer>(46);
        //rootList 
        a23.left = a24;a23.right = a7;
        a7.left = a23;a7.right = a21;
        a21.left = a7;a21.right = a3;
        a3.left = a21;a3.right = a17;
        a17.left = a3;a17.right = a24;
        a24.left = a17;a24.right = a23;
        
        //a3 childs
        a3.child = a18;
        a18.parent = a3;a52.parent = a3;a38.parent = a3;
        a18.left = a38;a18.right = a52;
        a52.left = a18;a52.right = a38;
        a38.left = a52;a38.right = a18;
        //a38 child
        a38.child = a41;
        //a18 child
        a18.child = a39;
        //a24 childs
        a24.child = a26;
        a26.parent = a24;
        a46.parent = a24;
        a26.left = a46;a26.right = a46;
        a46.left = a26;a46.right = a26;
        
        
        fibheap.rootList.size = 6;
        fibheap.n = 15;
        fibheap.min = a3;
        fibheap.printFibHeap();
        
        System.out.println(fibheap.extractMin());
        fibheap.printFibHeap();
        System.out.println(fibheap.extractMin());
        fibheap.printFibHeap();
        
    }
    
    private static final int Dn = 3;
    
    private int n;//堆中结点数量
    private Item<T> min;// 斐波那契堆 的 最小结点 
    private CycleLinkedList<T> rootList;
    public FibHeap() {
        rootList = new CycleLinkedList<>();
    }
    // 根链表 的大小 
    public int rootSize() {return rootList.size;}
    //堆中结点数量
    public int getSize() {return n;}
    /**
     * 
     * 插入一个结点到堆中
     * o(1) 的时间复杂度
     * @param data
     */
    public void insert(T data) {
        
        Item<T> addItem = new Item<T>(data);
        rootList.insert(addItem);
        if(min.compareTo(addItem) > 0) {//min.key > addItem.key
            min = addItem;
        }
        n+=1;
    }
    /**
     * 抽取最小结点
     * @return
     */
    public T extractMin() {
        Item<T> minElement = this.min;
        // 把最小结点的所有孩子插入到rootList中
        Item<T> child = null;
        if(minElement.child != null) {
            child = minElement.child;
            Item<T> over = child.left;
            boolean tag = true;
            while(tag) {
                Item<T> next = child.right;
                if(child == over)
                    tag = false;
                rootList.insert(child);//在minElement 的左边插入 child
                child = next;
            }
        }
        // 从rootlist中移除最小结点
        if(minElement.right != minElement)
            min = minElement.right;
        else
            min = null;//堆已经空了
        rootList.delete(minElement);
        
        
        
        n--;//堆中结点数目减一
        consolidate();//整合堆中数据
        return minElement.data;
    }
    
    //整合rootList 的函数,保证rootList 中每一个 结点的度各不相同
    private void consolidate() {
        
        if(min == null || rootList.size == 1)// 空堆 或者只有一个结点
            return;
            
        Item<T>[] aux = new Item[Dn+1];// 0  1  2  3 aux[i] = xxx  表示xxx的度为i
        int minIndex = -1; // aux[]中最小结点索引
        //遍历rootList中每一个结点
        Item<T> item = min;//item 当前遍历的结点
        Item<T> over = item.left;//遍历终止结点
        
        boolean isover = false;
        while(!isover) {
            Item<T> x = item;
            item = item.right;//下一个需要遍历的item
            
            //循环在遍历完over后结束
            if(x == over)
                isover = true;
            
            
            int degree = x.degree;//遍历结点的度
            while(aux[degree] != null) {
                //有另外一个结点的度和x相同,取出这个结点y
                Item<T> y = aux[degree];
                //保证x.key <= y.key
                if(x.compareTo(y) > 0) {
                    Item<T> _forChange = x;
                    x = y;
                    y = _forChange;
                }
                //从根链表删除y, 把y 插到 x 的子结点中
                heapLink(x, y);
                //链接之后没有结点的度为degree
                aux[degree] = null;
                if(degree == minIndex)// 优化
                    minIndex = -1;
                //此时x 的度为 degree+1
                degree++;
            }
            x.degree = degree;
            aux[degree] = x;
            //just for find min
            if(minIndex == -1 || aux[minIndex].compareTo(aux[degree]) > 0) {
                minIndex = degree;
            }
        }
        // 寻找最小
        min = aux[minIndex];
    }
    ////从根链表删除y,  把y 插入到 x 的子结点中
    //x.child 总是最后一个插入的x的 孩子 且这个孩子在所有孩子的最左边
    private void heapLink(Item<T> x, Item<T> y) {
        
        rootList.delete(y);
        if(x.child == null) {
            x.child = y;
            y.parent = x;
            y.left = y;
            y.right = y;
        }else {
            Item<T> xChild1 = x.child;
            Item<T> xChild2 = xChild1.left;
            xChild1.left = y; 
            y.right = xChild1;
            
            y.left = xChild2;
            xChild2.right = y;
            
            y.parent = x;
            x.child = y;//最后一个插入的孩子为x.child
        }
    }
    //打印斐波那契堆
    public void printFibHeap() {
        if(rootSize() == 0) {
            System.out.println("this is a null heap");
        }else {
            Item<T> item = min;
            do {
                //打印item
                System.out.print("["+ item.data + ":");
                if(item.child != null) {
                    Item temp = item.child;
                    while(true) {
                        System.out.print(temp.data + " ");
                        temp = temp.right;
                        if(temp == item.child)
                            break;
                    }
                }
                System.out.print("] ");
                
                item = item.right;
            }while(item != min);
            System.out.println();
        }
    }
    
    //结点
    static class Item <T> implements Comparable<Item<T>>{
        //一个数据为t, 左右结点指向自己的结点
        public Item(T t) {
            this.data = t;
            left = this;
            right = this;
        }
        Item<T> parent;
        Item<T> child;
        Item<T> left;//这个结点的左结点
        Item<T> right;//右结点
        int degree;// 结点的度
        boolean mark = false;//默认未被标记
        T data;//存放的数据
        
        //比较方法
        @Override
        public int compareTo(Item<T> o) {
            Comparable<T> com_this = (Comparable<T>)data;
            return com_this.compareTo(o.data);
        }
        
    }
    
    //循环双向链表 rootList
    class CycleLinkedList<T>{
        int size;
        
        //删除结点
        public void delete(Item deleteItem) {
            Item left = deleteItem.left;
            Item right = deleteItem.right;
            left.right = right;
            right.left = left;
            deleteItem.left = null;
            deleteItem.right = null;
            size--;
        }
        //插入元素入双向循环链表
        public void insert(Item addItem) {
            if(min == null) {
                min = addItem;
                size++;
            }else {
                insert0(addItem, min);
            }
        }
        // 在start结点的左边插入一个元素
        private void insert0(Item addItem, Item start) {
            Item temp = start.left;
            temp.right = addItem;
            addItem.left = temp;
            
            addItem.right = start;
            start.left = addItem;
            size++;
        }
    }
}

