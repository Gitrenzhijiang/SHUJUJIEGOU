package tree;

import java.util.LinkedList;

import util.Sort;
import util.SortHelper;
import util.TimeUtils;


/**
 * 功能齐全的二叉查找树
 * @author renzhijiang
 * [1]支持重复元素[key-value]
 * [2]查询:search(key)  contain(key) 
 * [3]顺序相关函数: min() max() 
 * successor(key):比key大的第一个结点 [后继结点]
 * predecessor(key):比key小的最后一个结点[前驱结点]
 * floor(key):最接近key的最小结点
 * ceil(key):最接近key的最大结点
 * rank(key):key结点 所在排序数,即排第几
 * select(i):查找第i个元素
 */
public class SuperBSearchTree <K extends Comparable<K>, V>{
	
	public static void main(String[] args) {
		SuperBSearchTree<Integer, String> bstree = new SuperBSearchTree<>();
		bstree.insert(22, "22");
		bstree.insert(6, "6");
		bstree.insert(23, "23");
		bstree.insert(4, "4");
		bstree.insert(18, "18");
		bstree.insert(8, "8");
		bstree.insert(20, "20");
		bstree.insert(19, "19");
		bstree.insert(18, "18"); //支持插入相同的键值对
		bstree.levelOrder();
		
		System.out.println();
		System.out.println("search(6):"+bstree.search(6));
		System.out.println("successor(20):" + bstree.successor(20));
		System.out.println("predecessor(21):" + bstree.predecessor(21));
		System.out.println("floor(20):" + bstree.floor(20));
		System.out.println("ceil(21):" + bstree.ceil(21));
		System.out.println("rank(8):" + bstree.rank(8));
		System.out.println("select(2):" + bstree.selectKey(5));//第5大的结点的key
		
		System.out.println(bstree.delete(18));//删除的结点有两个孩子
		System.out.println(bstree.delete(18));//删除的结点有两个孩子
		System.out.println(bstree.delete(20));//删除的结点有1个孩子
		System.out.println(bstree.delete(8));//删除的结点有0个孩子
		bstree.levelOrder();
		
		System.out.println("size():" + bstree.size());
		
		//数字 词频  测试
		SuperBSearchTree<Integer, Integer> sbs2 = new SuperBSearchTree<>();
		//60000个数字
		int []arr =  SortHelper.simpleRandomArray(60000, 600);
		
		TimeUtils.start();
		for(int i = 0;i < arr.length;i++) {
			Integer count = sbs2.search(arr[i]);
			if(count == null)
				sbs2.insert(arr[i], 1);
			else
				sbs2.insert(arr[i], count+1);
		}
		int f = 555;
		System.out.println(f + "的频率:" + sbs2.search(f));
		TimeUtils.over(true);
	}
	
	//根结点
	private Node<K,V> root;
	
	//插入一个[key-value]到二叉搜索树, 支持重复插入相同key, value以最后一次插入时指定的为准
	public void insert(K key, V value) {
		
		Node<K, V> willI = new Node<>(key, value);
		//当前树为空树,直接插入
		if(root == null)
			root = willI;
		else
			insert0(root, willI);
	}
	
	//以key 查询该结点所对应的value, 不存在则返回null
	public V search(K key) {
		if(key == null)
			throw new IllegalArgumentException("key 不能为Null");
		Node <K, V> target = new Node<>(key, null);
		Node<K, V> ret = search0(root, target);
		
		//如果查询结果为空,返回空
		if(ret == null)
			return null;
		else
			//查询成功, 返回key对应的value值
			return ret.value;
	}
	
	// 删除key所对应的结点, 如果该key有多个重复存在,删除一个
	public V delete(K key) {
		if( key == null )
			throw new IllegalArgumentException("key 不能为Null");
		return delete0(root, new Node<>(key, null));
	}
	
	//二叉树的大小
	public int size() {
		if(root == null)
			return 0;
		else
			return root.size;
	}
	
	//min, 整棵树的最小的值
	public V min() {
		
		//当前树为空,不存在最小
		if(root == null)
			return null;
		return minOfNode(root);
	}
	
	//min, 整棵树的最大的值
	public V max() {
		
		//当前树为空,不存在最小
		if(root == null)
			return null;
		return maxOfNode(root);
	}
	
	//successor(key):比key大的第一个结点 [后继结点]
	//这里 返回的是 V的引用, 如果key代表的结点或者 它的后继结点不存在,均返回空
	public V successor(K key) {
		if(root == null)
			throw new RuntimeException("当前树为空!");
		return successor( root, new Node<>(key, null) );
	}	
	
	//key所在结点的前继结点
	//key所在结点不存在
	public V predecessor( K key ) {
		if(root == null)
			throw new RuntimeException("当前树为空!");
		return predecessor( root, new Node<>(key, null) );
	}
	
	//floor(key) 查询key的"地板"
	//key 所在结点存在时,返回自身的值;  不存在时, 查询出小于key的最大结点的值
	public V floor(K key) {
		if(root == null)
			throw new RuntimeException("当前树为空!");
		return floor( root, new Node<>(key, null));
	}
	
	//ceil(key) 查询key的"天花板"
	public V ceil(K key) {
		if(root == null)
			throw new RuntimeException("当前树为空!");
		return ceil( root, new Node<>(key, null));
	}
	
	//找到第n个结点 的key, 没有返回抛出异常(n 从1 开始到树的所有结点数量)
	public K selectKey(int n) {
		return select(n).key;
	}
	
	//找到第n个结点的value, 没有返回抛出异常(n 从1 开始到树的所有结点数量)
	public V selectValue(int n) {
		return select(n).value;
	}
	
	//找到第n个结点的 Node, 没有返回Null
	private Node<K, V> select(int n){
		if(root == null)
			throw new RuntimeException("当前树为空!");
		if(n <= 0 || n > root.size)
			throw new IllegalArgumentException(n + "为非法参数,当前可以传入:[1-" + root.size+"]");
		
		//当前遍历的结点
		Node<K, V> temp = root;
		
		//当前已经遍历过的结点数目
		int curCount = 0;
		while( true ) {
			
			//左子树的大小
			int leftSize = 0;
			if(temp.left != null)
				leftSize = temp.left.size;
			
			//如果n <= leftSize + curCount, 要找的结点在当前结点的左子树中
			int tempCount = leftSize + curCount;
			if(leftSize > 0 && n <= tempCount) {
				temp = temp.left;
			}else if(n <= tempCount+temp.count) {
				
				//当前结点就是查询结果
				return temp;
			}else {
				
				//要查找的结点在当前结点的右子树中
				//记录一个已经遍历过的数目
				curCount += (leftSize + temp.count);
				temp = temp.right;
			}
		}
	}
	
	//rank(key):key结点 所在排序数,即 小于key的结点数量 
	//key结点不存在返回-1
	public int rank(K key) {
		if(root == null)
			throw new RuntimeException("当前树为空!");
		
		// key 小于 它 的结点数量 = 它的左子树的size + [它父节点的左孩子的size+1]
		Node<K, V> key_node = new Node<>(key, null);
		Node<K, V> temp = root;
		
		//记录排名计数
		int ret = 0;
		while( temp != null ) {
			int j = temp.compareTo(key_node);
			
			//如果当前遍历的结点小于key所对应的结点,
			if(j < 0) {
				
				//如果当前结点有左子树,那么左子树一定也比key小
				if(temp.left != null)
					ret += temp.left.size;
				//再加上当前结点的个数
				ret += temp.count;
				temp = temp.right;
			}else if(j > 0) {
				
				//当前遍历的结点大于key所对应的结点,向左继续搜索
				temp = temp.left;
				
			}else {
				
				//当前遍历的结点正是key对应的结点
				//如果当前结点还有左子树,左子树一定比当前结点小
				if(temp.left != null)
					ret += temp.left.size;
				break;
			}
			
			
		}
		
		//如果temp == null, 说明没有找到key所对应的结点
		if(temp == null)
			return -1;
		else 
			return ret;
	}
	
	//查询以node 为根的树, key_node 的 地板
	private V floor(Node<K, V> node, Node<K, V> key_node) {
		
		if(node == null)
			return null;
		int j = node.compareTo(key_node);
		if(j == 0) {
			
			//当前结点 就是查询结果
			return node.value;
		}else if(j > 0) {
			
			//当前结点大于key , 继续向左递归搜索
			return floor(node.left, key_node);
		}else {
			
			//当前结点小于key, 先继续向右搜索,如果当前结点的右子树没有结果,当前结点即结果
			V ret = floor(node.right, key_node);
			if(ret == null) {
				ret = node.value;
			}
			return ret;
		}
	}
	
	//查询以node 为根的树, key_node 的 天花板
	private V ceil(Node<K, V> node, Node<K, V> key_node) {
		
		if(node == null)
			return null;
		int j = node.compareTo(key_node);
		if(j == 0) {
			
			//当前结点 就是查询结果
			return node.value;
		}else if(j < 0) {
			
			//当前结点小于key , 继续向右递归搜索
			return ceil(node.right, key_node);
		}else {
			
			//当前结点大于key, 先继续向左搜索,如果当前结点的左子树没有结果,当前结点即结果
			V ret = ceil(node.left, key_node);
			if(ret == null) {
				ret = node.value;
			}
			return ret;
		}
	}
	
	//以node为根的树的最小结点的值, 调用前保证了Node不为空
	private V minOfNode(Node<K, V> node) {
		while(node.left != null)
			node = node.left;
		return node.value;
	}
	
	
	
	//以node为根的树的最大结点的值, 调用前保证了Node不为空
	private V maxOfNode(Node<K, V> node) {
		while(node.right != null)
			node = node.right;
		return node.value;
	}
	
	//用于存储predecessor 和 successor 查询中是否查到 key所在结点
	private boolean _pre_succ = false;
	
	//node为根的树的key的前驱结点的值
	private V predecessor(Node<K, V> node, Node<K, V> key_node) {
		
		if(node == null) {
			
			//key所在的结点并不存在
			return null;
		}
		int j = node.compareTo(key_node);
		if(j < 0) {
			
			//当前结点小于需要查找的结点, 继续向右递归搜索,得到向右搜索的结果
			V ret = predecessor(node.right, key_node);
			
			//如果搜索到key 并且 如果右边没有key的前继结点, 当前结点 即为结果
			if(ret == null && _pre_succ == true) {
				ret = node.value;
				
				//重置标记,以便下次使用
				_pre_succ = false;
			}
			return ret;
		} else if(j > 0) {
			
			//当前结点大于需要查找的结点, 继续向左递归搜索
			return predecessor(node.left, key_node);
		} else {
			
			//找到需要搜索的结点, 求它的前驱结点
			//首先标记一下搜索到了
			_pre_succ = true;
			//如果它  有 左子树, 找到左子树的最大值即可;如果没有,暂时返回Null
			if( node.left == null )
				return null;
			return minOfNode( node.left );
		}
	}
	
	//node为根的树的 key的后继结点的值
	private V successor(Node<K, V> node, Node<K, V> key_node) {
		
		if(node == null) {
			
			//key所在的结点并不存在
			return null;
		}
		int j = node.compareTo(key_node);
		if(j > 0) {
			
			//当前结点大于需要查找的结点, 继续向左递归搜索,得到向左搜索的结果
			V ret = successor(node.left, key_node);
			
			//如果找到了key所在结点 && 如果左边没有key的后继结点, 当前结点 即为结果
			if(ret == null && _pre_succ == true) {
				ret = node.value;
				
				//重置标记,以便下次使用
				_pre_succ = false;
			}
			return ret;
		} else if(j < 0) {
			
			//当前结点小于需要查找的结点, 继续向右递归搜索
			return successor(node.right, key_node);
		} else {
			
			//找到需要搜索的结点, 求它的下一个后继结点
			//标记已经找打key所在结点
			_pre_succ = true;
			//如果它  有 右子树, 找到右子树的最小值即可;如果没有,暂时返回Null
			if( node.right == null)
				return null;
			return minOfNode( node.right );
		}
	}
	
	//删除root 下面的 willD 结点
	private V delete0(Node<K, V> root, Node<K, V> willD){
		
		//root 为空, 即需要删除的元素不存在
		if(root == null)
			return null;
		
		
		int j = root.compareTo(willD);
		V ret = null;
		
		
		if( j > 0 ) {
			
			//当前结点为根  的树, 结点数量减少
			root.size--;
			//当前结点 大于 需要删除的结点
			ret = delete0(root.left, willD);
		} else if(j < 0) {
			
			//当前结点为根  的树, 结点数量减少
			root.size--;
			//当前结点 小于 需要删除的结点
			ret = delete0(root.right, willD);
		} else {
			
			//当前结点即为需要删除的结点
			ret = root.value;
			if(root.left == null && root.right == null) {
				
				//需要删除的结点左右孩子都没有,直接删除即可
				deleteNoChild(root);
			} else if( root.left != null && root.right == null ) {
				
				deleteHasLeftChild(root);
			} else if( root.left == null && root.right != null ) {
				
				deleteHasRightChild(root);
			} else {
				
				//需要删除的结点有两个孩子
				deleteHasChilds(root);
			}
		}
		
		return ret;
	}
	
	//删除指定的结点,它有两个孩子
	private void deleteHasChilds(Node<K, V>del) {
		del.count--;
		del.size--;
		if(del.count >= 1)
			return;
		// 找到要删除结点 的 右子树最小值
		Node<K, V> th = _findMin(del.right);
		if(th.compareTo(del.right) != 0) {
			th.parent.left = th.right;
			th.right.parent = th.parent;
		}else {
			del.right = th.right;
			if(th.right != null) {
				th.right.parent = del;
			}
		}
			
			
		del.count = th.count;
		del.value = th.value;
		del.key = th.key;
		
	}
	
	//调用时保证node 不为空
	//用node树的最小结点替换掉node的父亲结点,并维护size和count字段
	private Node<K, V> _findMin(Node<K, V> node){
		
		if (node.left != null) {
			
			//此时node并不是需要找到的最小结点,向左递归搜索
			Node<K, V> ret =  _findMin(node.left);
			ret.size -= ret.count;
		}
		return node;
	}
	
	//删除指定的结点, 该结点保证只有右孩子
	private void deleteHasRightChild(Node<K, V>del) {
		
		del.count--;
		del.size--;
		
		//删除的结点不是root
		if(del.parent != null) {
			del.parent.size --;
		}
		if(del.count < 1) {
			if(del.parent == null) {
				
				//需要删除的结点为root
				root = del.right;
				root.parent = null;
			}else if(del.parent.left.compareTo(del) == 0) {
				
				//需要删除的结点是它父亲的左孩子
				//需要删除的孩子只有一个右孩子
				del.right.parent = del.parent;
				del.parent.left = del.right;
			}else if(del.parent.right.compareTo(del) == 0) {
				
				//需要删除的结点是它父亲的右孩子
				//需要删除的孩子只有一个右孩子
				del.right.parent = del.parent;
				del.parent.right = del.right;
			}
		}
	}
	
	//删除指定的结点, 该结点保证只有左孩子
	private void deleteHasLeftChild(Node<K, V>del) {
		
		del.count--;
		del.size--;
		
		//删除的结点不是root
		if(del.parent != null) {
			del.parent.size --;
		}
		if(del.count < 1) {
			if(del.parent == null) {
				
				//需要删除的结点为root
				root = del.left;
				root.parent = null;
			}else if(del.parent.left.compareTo(del) == 0) {
				
				//需要删除的结点是它父亲的左孩子
				//需要删除的孩子只有一个左孩子
				del.left.parent = del.parent;
				del.parent.left = del.left;
			}else if(del.parent.right.compareTo(del) == 0) {
				
				//需要删除的结点是它父亲的右孩子
				//需要删除的孩子只有一个左孩子
				del.left.parent = del.right;
				del.parent.right = del.left;
			}
		}
	}
	
	//删除指定的结点, 调用之前保证该结点存在且它没有孩子
	private void deleteNoChild(Node<K, V> del) {
		
		del.count--;
		del.size--;
		
		//删除的结点不是root
		if(del.parent != null) {
			
			//需要删除的结点  的父节点少了一个子结点
			del.parent.size --;
		}
		if( del.count < 1) {
			if(del.parent == null) {
				
				//需要删除的结点为root
				root = null;
			}else if(del.parent.left.compareTo(del) == 0) {
				del.parent.left = null;
			}else {
				del.parent.right = null;
			}
		}
	}
	
	//以node 为根查询 
	private Node<K, V> search0(Node<K, V> node, Node<K, V> target) {
		
		//该结点不存在
		if(node == null)
			return null;
		int j = node.compareTo(target);
		
		if(j == 0) {
			
			//查询成功
			return node;
		}else if(j > 0) {
			
			//当前结点 大于 需要查询的结点, 向左继续递归查询
			return search0(node.left, target);
		}else {
			
			//当前结点小于 需要查询的结点, 继续向右递归查询
			return search0(node.right, target);
		}
	}
	
	
	
	//将 需要插入的结点 插入到 以node为根的树,  调用前保证了node 不为空
	private void insert0(Node<K,V> node, Node<K,V> willI) {
		
		//node 需要添加一个结点
		node.size++;
		
		int j = node.compareTo(willI);
		if(j == 0) {
			
			//如果willI 与 当前元素相同, 当前元素个数计数增加
			node.count++;
			
			//更新结点的值
			node.value = willI.value;
		}else if(j > 0) {
			
			//当前元素 大于 需要插入的元素
			if(node.left == null) {
				node.left = willI;
				willI.parent = node;
			}
			else
				//当前结点 左孩子不为空, 继续递归插入
				insert0(node.left, willI);
				
		}else {
			
			//当前元素 小于 需要插入的结点
			if(node.right == null) {
				node.right = willI;
				willI.parent = node;
			}
			else
				//当前结点的右孩子不为空, 递归插入
				insert0(node.right, willI);
		}
		
	}
	
	//二叉查询数的内部数据结构
	private class Node<k, V> implements Comparable<Node<K,V>>{
		
		public Node(K key, V value) {
			this.key = key;
			this.value = value;
		}
		
		//作为Node 比较的 值,它必须是comparable的子类
		K key; 
		V value;
		Node<K, V> parent;
		Node<K, V> left;
		Node<K, V> right;
		
		//以当前结点为根节点,树的结点个数(包含根结点)
		int size = 1;
		
		//当前结点的个数
		int count = 1;
		
		
		@Override
		public int compareTo(Node<K, V> o) {
			Comparable<K> com_this = (Comparable<K>)key;
			return com_this.compareTo(o.key);
		}

		
	}
	
	//层序遍历打印
	public void levelOrder() {
		LinkedList<Node> queue = new LinkedList<>();
		if(root != null) {
			queue.addLast(root);
			int num = 1;
			int nextNum = 0;
			while(!queue.isEmpty()) {
				Node node = queue.removeFirst();
				
				
				System.out.print("["+ node.key + "," + node.value +"] ");
				num--;
				
				if(node.left != null) {
					queue.addLast(node.left);
					nextNum++;
				}
				if(node.right != null) {
					queue.addLast(node.right);
					nextNum++;
				}
				if(num == 0) {//这一层都遍历完毕
					System.out.println();
					num = nextNum;
					nextNum = 0;
				}
			}
		}
		
	}
}
