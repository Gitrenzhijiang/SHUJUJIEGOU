package sort;

import util.Sort;
import util.SortHelper;

public class SimpleBubbleSort implements Sort {
/**
 * 简单的冒泡排序
 * 通过逐步比较并把最大的数比较出来
 */
	@Override
	public void sort(int[] arr) {
		for(int i = 0;i < arr.length - 1;i++) {
			for(int j = 0;j < arr.length - 1 - i;j++) {
				if(arr[j] > arr[j+1]) {
					SortHelper.swap(arr, j, j+1);
				}
			}
		}
	}

}
