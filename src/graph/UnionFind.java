package graph;

import java.util.Random;

import util.TimeUtils;

/**
 * 并查集
 * @author renzhijiang
 *
 */
public class UnionFind {

	public static void main(String[] args) {
		int n = 60000;
		UnionFind uf= new UnionFind(n);
		Random random = new Random();
		TimeUtils.start();
		for (int i = 0;i < n;i++) {
		    uf.union(random.nextInt(n/2) % n, random.nextInt(n) % n);
		}
		for (int i = 0;i < n;i++) {
            uf.isConnected(random.nextInt(n/2) % n, random.nextInt(n) % n);
        }
		TimeUtils.over(true);
	}
	/**
	 * 
	 * @param capacity 能够存放集合的最大容量
	 */
	public UnionFind(int capacity) {
	    this.capacity = capacity;
	    parent = new int[capacity];
	    rank = new int[capacity];
	    for (int i = 0;i < parent.length;i++) {
	        parent[i] = i;
	    }
    }
	
	public void union(int x, int y) {
	    int x_i = find(x);
	    int y_i = find(y);
	    if (x_i == y_i)
	        return;
	    int c = rank[x_i] - rank[y_i];
	    if (c > 0) {
	        //x的 集合层数更多
	        parent[y_i] = x_i;
	    }else if (c < 0) {
	        parent[x_i] = y_i;
	    }else {
	        parent[y_i] = x_i;
	        rank[x_i]++;
	    }
	}
	/**
	 * x 索引位置元素的根节点的索引
	 * @param x
	 * @return
	 */
	public int find(int x) {
	    if (x < 0 || x >= capacity)
	        throw new IllegalArgumentException(x+"是非法参数!");
	    
//	    int parentIndex = parent[x];
//	    while(parentIndex != parent[parentIndex]) {
//	        parentIndex = parent[parentIndex];
//	    }
//	    return parentIndex;
	    //使用路径压缩
	    while (x != parent[x]) {
	        //当前元素不是根
	        parent[x] = parent[parent[x]];
	        x = parent[x];
	    }
	    return parent[x];
	}
	
	public boolean isConnected(int x, int y) {
	    return find(x) == find(y);
	}
	private int capacity;
	/** parent[i] 表示 i 索引元素的父元素索引, 如果parent[i] = i表示它是集合的根 */
	private int[] parent;
	/** 
	 * rank[i] : 第i个元素的层数
	 */
	private int[] rank;
}
