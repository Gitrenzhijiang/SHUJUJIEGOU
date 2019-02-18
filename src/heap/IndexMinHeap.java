package heap;

import util.SortHelper;

/**
 * 索引最小堆 
 * @author renzhijiang
 * 
 */
public class IndexMinHeap extends MinHeap {
	
	public static void main(String[] args) {
		IndexMinHeap minheap = new IndexMinHeap(12);
		minheap.insert(0, 0);minheap.insert(4, 5);minheap.insert(8, 6);
		minheap.insert(11, 4);
		SortHelper.printArray(minheap.arr);
		SortHelper.printArray(minheap.indexs);
		while(!minheap.isEmpty()) {
			System.out.print(minheap.extraction() + " ");
		}
	}
	public IndexMinHeap(int capacity) {
		super(capacity);
		indexs = new int[capacity];
		reverse = new int[capacity];
	}
	/**
	 * count 位置是插入e , 可能会覆盖之前插入点,造成堆的崩溃
	 */
	@Override
	public void insert(int e) {
		insert(count, e);
	}
	//在 i 位置上插入 e
	public void insert(int i, int e) {
		if(i < 0 || i >= capacity)
			throw new IllegalArgumentException(i+"");
		arr[i] = e;
		indexs[count] = i;
		reverse[i] = count;
		shiftUp(count);
		count++;
	}
	//取出索引最小堆的当前最小元素
	public int extraction() {
		if(count == 0)
			throw new RuntimeException("堆已经空了!");
		int ret = arr[indexs[0]];
		SortHelper.swap(indexs, 0, count-1);
		SortHelper.swap(reverse, count-1, 0);
		count--;
		shiftDown(0);
		return ret;
	}
	//取出最小元素  插入时的索引
	public int extractionIndex() {
		if(count == 0)
			throw new RuntimeException("堆已经空了!");
		int ret = indexs[0];
		SortHelper.swap(indexs, 0, count-1);
		SortHelper.swap(reverse, count-1, 0);
		count--;
		shiftDown(0);
		return ret;
	}
	
	private void shiftDown(int k) {
		int e_index = indexs[k];
		while(2*k+1 < count) {
			int j = 2*k + 1;
			if(j+1 < count && arr[indexs[j+1]] < arr[indexs[j]]) {
				j+=1;
			}
			if(arr[indexs[j]] < arr[e_index]) {
				indexs[k] = indexs[j];
				reverse[indexs[j]] = k;//for reverse
				k = j;
			}else
				break;
		}
		indexs[k] = e_index;
		reverse[e_index] = k;
	}
	
	private void shiftUp(int k) {
		
		int e_index = indexs[k];
		int j = 0;
		while( (j = (k%2==0?k/2-1:k/2)) >= 0 && arr[e_index] < arr[indexs[j]] ){
			indexs[k] = indexs[j];
			reverse[indexs[j]] = k;
			k = j;
		}
		indexs[k] = e_index;
		reverse[e_index] = k;
	}
	//test fun
	public void fortest() {
		SortHelper.printArray(arr);
		SortHelper.printArray(indexs);
	}
	private int[] indexs;
	private int[] reverse;
}
