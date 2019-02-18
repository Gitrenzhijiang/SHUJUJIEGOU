package sort;

import util.Sort;
import util.SortHelper;

/**
 * D 叉堆排序
 * @author renzhijiang
 * 
 */
public class DWaysHeapSort implements Sort{
	public DWaysHeapSort(int d) {
		this.d = d;
	}
	@Override
	public void sort(int[] arr) {
		
		for(int i = arr.length/d - 1;i >= 0;i--) {
			shiftDown(arr, arr.length, i);
		}
		
		for(int i = arr.length;i > 1;i--) {
			SortHelper.swap(arr, 0, i-1);
			shiftDown(arr, i-1, 0);
		}
	}
	// d 叉   data [0,len) 的 k位置元素进行 shiftDown
	private void shiftDown(int[]data, int len, int k) {
		int e = data[k];
		while(d*k+1 < len) {
			int j = d*k+1;
			int max = j;
			//找出所有存在的子节点的最大元素     曾经这里出现BUG
			for(int i = 1;i < d;i++) {
				if(j+i >= len)
					break;
				if(data[j+i] > data[max]){
					max = j+i;
				}
			}
			j = max;
			if(data[j] > e) {
				data[k] = data[j];
				k = j;
			}else
				break;
		}
		data[k] = e;
	}
	private int d = 2;
}
