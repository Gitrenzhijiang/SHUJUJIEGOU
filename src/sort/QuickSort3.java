package sort;

import java.util.Random;

import util.Sort;
import util.SortHelper;

public class QuickSort3 implements Sort {

	@Override
	public void sort(int[] arr) {
		sort0(arr, 0, arr.length-1);
	}
	protected InsertSort insertSort = new InsertSort();
	protected Random random = new Random();
	private void sort0(int[] arr, int left, int right) {
		//插入排序优化
		if(right - left < 16) {
			insertSort.sort(arr, left, right);
			return;
		}
//		if(left >= right)return;
		int p = partition(arr, left, right);
		sort0(arr, left, p-1);
		sort0(arr, p+1, right);
	}
	//尽可能的让p处于left 和 right的中间位置
	private int partition(int[]arr, int left, int right) {
		//优化1:数据有序性高时,避免分界点不平均
		SortHelper.swap(arr, left, random.nextInt(right-left+1) + left);
		//优化2: arr[left+1, i) <= e; arr(j, right] >= e  在数字大量重复时,能尽量使分界点平均
		int i = left+1, j = right;
		int e = arr[left];
		while(true) {
			while(i <= right && arr[i] < e)
				i++;
			while(j >= left + 1 && arr[j] > e)
				j--;
			
			if(i > j)break;
			SortHelper.swap(arr, i, j);
			i++;
			j--;
		}
		SortHelper.swap(arr, left, j);
		return j;
	}
}
