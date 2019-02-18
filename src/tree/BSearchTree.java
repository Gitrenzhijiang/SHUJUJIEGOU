package tree;

import java.util.LinkedList;

/**
 * 二叉搜索树
 * @author renzhijiang
 * 简单实现版本, 插入数据, 搜索数据, 删除数据
 * 第二版在 SuperBSearchTree
 */
public class BSearchTree <K extends Comparable<K>, V>extends BinaryTree {
	public static void main(String[] args) {
		BSearchTree<Integer, String> bstree = new BSearchTree<>();
		bstree.insert(22, "22");
		bstree.insert(6, "6");
		bstree.insert(23, "23");
		bstree.insert(4, "4");
		bstree.insert(18, "18");
		bstree.insert(8, "8");
		bstree.insert(20, "20");
		bstree.insert(19, "19");
		// ====== 测试树的结构
		/*
		 
		  				22
			6						23
	4			18
			8	    20
				 19
		 
		 */
		
		bstree.levelOrder();
		System.out.println(bstree.search(6));
		System.out.println("delete:");
		System.out.println(bstree.deleteByKey(18));//删除的结点有两个孩子
		System.out.println("delete over");
		System.out.println("delete:");
		System.out.println(bstree.deleteByKey(20));//删除的结点有1个孩子
		System.out.println("delete over");
		System.out.println("delete:");
		System.out.println(bstree.deleteByKey(8));//删除的结点有0个孩子
		System.out.println("delete over");
		bstree.levelOrder();
	}
	/**
	 * 将这个键值对插入二叉搜索树
	 * @param key
	 * @param value
	 */
	public void insert(K key, V value) {
		Item<K,V> addItem = new Item<>(key, value);//
		Node addNode = new Node(addItem);
		if(this.root == null) {// 此时二叉搜索树为空树
			//初始化root结点
			this.root = addNode;
		}else {
			insert0(this.root, addNode);
		}
	}
	//先序遍历打印二叉搜索树的 key-value 键值对
	public void preOrder() {
		preOrder0(root);
		System.out.println();//换行
	}
	private void preOrder0(Node node) {
		if(node == null)
			return;
		Item<K,V> item = (Item<K,V>)node.data;
		System.out.print("["+ item.key + "," + item.value +"] ");
		if(node.left != null)
			preOrder0(node.left);
		if(node.right != null)
			preOrder0(node.right);
	}
	//中序遍历打印二叉搜索树的 key-value 键值对
	public void midOrder() {
		midOrder0(root);
		System.out.println();
	}
	private void midOrder0(Node node) {
		if(node == null)
			return;
		if(node.left != null)
			midOrder0(node.left);
		Item<K,V> item = (Item<K,V>)node.data;
		System.out.print("["+ item.key + "," + item.value +"] ");
		if(node.right != null)
			midOrder0(node.right);
	}
	//后序遍历打印二叉搜索树的 key-value 键值对
	public void postOrder() {
		postOrder0(root);
		System.out.println();//换行
	}
	
	
	
	private void postOrder0(Node node) {
		if(node == null)
			return;
		
		if(node.left != null)
			postOrder0(node.left);
		if(node.right != null)
			postOrder0(node.right);
		Item<K,V> item = (Item<K,V>)node.data;
		System.out.print("["+ item.key + "," + item.value +"] ");
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
				
				
				Item<K,V> item = (Item<K,V>)(node.data);
				System.out.print("["+ item.key + "," + item.value +"] ");
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
	private void insert0(Node node, Node addNode) {
		int c = compareNode(node, addNode);
		if(c > 0) {// node  > addNode
			if(node.left == null) {//找到位置
				node.left = addNode;
			}else {
				insert0(node.left, addNode);
			}
		}else if(c < 0) {
			if(node.right == null) {//找到位置
				node.right = addNode;
			}else {
				insert0(node.right, addNode);
			}
		}
			
	}
	//返回key对于元素的值
	//没有找到返回null
	public V search(K key) {
		return search0(root, new Item<>(key, null));
	}
	private V search0(Node node, Item<K,V> shouldFind) {
		if(node == null)
			return null;
		Item<K,V> node_item = getItemOfData(node);
		int j = node_item.compareTo(shouldFind);
		if(j == 0) {
			return node_item.value;//找到
		}else if(j > 0) {
			return search0(node.left, shouldFind);
		}else {
			return search0(node.right, shouldFind);
		}
	}
	
	//返回删除元素的值
	//没有找到返回null
	// has a bug at when you will delete root Node
	public V deleteByKey(K key) {
		if(root == null) {
			throw new RuntimeException("当前树为空树.");
		}
		
		Item<K,V> shouldFind = new Item<>(key, null);
		//删除根结点另外考虑
		Item<K,V> root_item = (Item<K,V>)root.data;
		if(root_item.compareTo(shouldFind) == 0) {
			this.root = null;
			return root_item.value;
		}
		//要删除的不是根节点
		return delete(root, shouldFind);
	}
	private V delete(Node node, Item<K,V> shouldFind) {
		if(node == null)
			return null;
		V ret = null;
		Item<K,V> node_item = getItemOfData(node);
		int j = node_item.compareTo(shouldFind);
		if(j == 0) {
			ret = node_item.value;//找到
			//删除结点
			Node parent = searchParent(root, node);
			
			if(node.left == null && node.right == null) {//要删除的结点没有孩子
				changeFromParent(parent, node, null);
			}else if(node.left != null && node.right != null) {//有两个孩子
				//这里用右孩子的最小结点替换需要删除的结点
				Node min = node.right;//min : 寻找的最小右孩子
				Node min_p = node;
				while(min.left != null) {
					min_p = min;
					min = min.left;
				}
				changeFromParent(min_p, min, null);
				node.data = min.data;
			}else if(node.left != null){//有左孩子
				changeFromParent(parent, node, node.left);
			}else {//  有右孩子
				changeFromParent(parent, node, node.right);
			}
		}else if(j > 0) {
			return delete(node.left, shouldFind);
		}else {
			return delete(node.right, shouldFind);
		}
		return ret;
	}
	@Override
	protected Node searchParent(Node node, Node search) {
//		return super.searchParent(node, search);
		if(search == null)
			throw new IllegalArgumentException();
		if(search == root)
			return null;
		if(node.left == search || node.right == search) {
			return node;
		}else {
			if(compareNode(search, node) > 0 && node.right != null) {
				return searchParent(node.right, search);
			}else if(node.left != null){
				return searchParent(node.left, search);
			}
		}
		return null;
	}
	//从parent结点下  用nChild替换掉delChild这个孩子结点 
	private void changeFromParent(Node parent, Node delChild, Node nChild) {
		if(parent.left != null && compareNode(parent.left, delChild) == 0) {
			parent.left = nChild;
		}else if(parent.right != null && compareNode(parent.right, delChild) == 0) {
			parent.right = nChild;
		}
	}
	//比较两个node的key的大小
	private int compareNode(Node node1, Node node2) {
		return getItemOfData(node1).compareTo(getItemOfData(node2));
	}
	private Item<K,V> getItemOfData(Node node){
		if(node == null) {
			return null;
		}
		return (Item<K,V>)node.data;
	}
	//二叉树中存储的结点
 	class Item<K,V> implements Comparable<Item<K,V>>{
		 K key;
		 V value;
		 public Item(K k, V v) {
			 key = k;
			 value = v;
		 }
		@Override
		public int compareTo(Item<K, V> o) {
			Comparable<K> this_k =  (Comparable<K>)key;
			return this_k.compareTo(o.key);
		}
	}
}
