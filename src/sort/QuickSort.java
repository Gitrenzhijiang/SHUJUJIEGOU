package sort;

import util.Sort;
import util.SortHelper;

public class QuickSort implements Sort {
//快速排序基本实现1
	@Override
	public void sort(int[] arr) {
		sort0(arr, 0, arr.length-1);
	}
	private void sort0(int[] arr, int left, int right) {
		
		if(left >= right)return;
		
		int p = partition(arr, left, right);
		if(left < p-1)
			sort0(arr, left, p-1);
		if(p+1 < right)
			sort0(arr, p+1, right);
	}
	//将arr[left] = e 为分割, arr[left+1...j] <= e     arr[j+1,i) > e
	private int partition(int[]arr, int left, int right) {
			
		int e = arr[left];
		int j = left;
		int i = left + 1;
		for(;i <= right;i++) {
			if (arr[i] <= e) {
				if (j+1 != i)
					SortHelper.swap(arr, j+1, i);
				j++;
			}
		}
		SortHelper.swap(arr, left, j);
		return j;
	}
}
