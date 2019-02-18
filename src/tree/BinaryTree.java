package tree;

public class BinaryTree <T>{
	
	protected Node root;//根结点
	protected class Node{
		public Node() {}
		public Node(T data) {this.data = data;}
		T data;
		Node left;
		Node right;
	}
	/**
	 * 将add结点插入到node的左子树;如果node结点存在左孩子,这个孩子结点会变成新增结点的左结点
	 * @param node
	 * @param add
	 */
	public void insertLeftChild(Node node, Node add) {
		if(node == null || add == null)
			throw new IllegalArgumentException();
		Node node_left = node.left;
		node.left = add;
		if(node_left != null)
			add.left = node_left;
	}
	public void insertRightChild(Node node, Node add) {
		if(node == null || add == null)
			throw new IllegalArgumentException();
		Node node_right = node.right;
		node.right = add;
		if(node_right != null)
			add.right = node_right;
	}
	//从二叉树中 删除指定结点及其所有子结点
	public void delete_r(Node node) {
		if(node == null)
			throw new IllegalArgumentException();
		if(root == null)
			throw new RuntimeException("当前二叉树为空树!");
		if(root == node) { // 删除根结点
			root = null;
			return;
		}
		Node parent = searchParent(root, node);
		if(parent != null) {//删除
			if(parent.left == node)
				parent.left = null;
			else if(parent.right == node)
				parent.right = null;
		}
	}
	// 在以node为根结点的二叉树中 找到 指定结点的 父节点;如果要查找的是整棵树的root,返回null
	protected Node searchParent(Node node, Node search) {
		if(search == null)
			throw new IllegalArgumentException();
		if(search == root)
			return null;
		if(node.left == search || node.right == search) {// 找打了
			return node;
		}
		Node ret = null;
		if(node.left != null) {
			ret = searchParent(node.left, search);
			if(ret != null)
				return ret;
			else {
				ret = searchParent(node.right, search);
			}
		}
		return ret;
	}
}
