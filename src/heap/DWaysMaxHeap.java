package heap;
/**
 * D 叉最大堆
 * @author renzhijiang
 * 
 */
public class DWaysMaxHeap {
	
	public DWaysMaxHeap(int d, int capacity) {
		this.d = d;
		this.capacity = capacity;
		data = new int[capacity];
	}
	public DWaysMaxHeap(int capacity) {
		this(2, capacity);
	}
	
	
	
	//k 属于 [0, count)
	private void shiftDown(int k) {
		int e = data[k];
		while(d*k+1 < count) {
			int j = d*k+1;
			//找出所有存在的子节点的最大元素
			for(int i = 1;i < d;i++) {
				if(j+i >= count)
					break;
				else if(data[j+i] > data[j]){
					j = j+i;
				}
			}
			if(data[j] > e) {
				data[k] = data[j];
				k = j;
			}else
				break;
		}
		data[k] = e;
	}
	private void shiftUp(int k) {
		int e = data[k];
		while(k > 0) {
			int j = getParent(k);
			if(e > data[j]) {
				data[k] = data[j];
				k = j;
			}else
				break;
		}
		data[k] = e;
	}
	private int getParent(int k) {
		if(k <= 0)
			throw new IllegalArgumentException();
		return k % d == 0? k/d-1:k/d;
	}
	
	
	//堆的大小
	public int size() {return count;}
	public boolean isEmpty() { return count == 0;}
	private int count = 0;// 堆中数据大小
	private int[] data; // 存放堆中的数据
	private int d = 2; // 这个对的分叉数
	private int capacity; // 容量
}
