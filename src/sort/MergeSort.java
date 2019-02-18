package sort;

import util.Sort;

public class MergeSort implements Sort {
/**
	归并排序:
	利用分治思想,逐级递减        o(  Nlog(n) )
	
	难度主要在于合并算法的实现
	速度比较快,无论在何种数据下
 *
 */
	protected InsertSort insertSort = new InsertSort();
	private void merge(int[]arr, int left, int right) {
		
//		if(left >= right)return;  //当right-left 小于一定数值时选用插入排序(这个做法几乎在所有的排序中都有效)
		if(right - left <= 15) {
			insertSort.sort(arr, left, right);
			return;
		}
		
		int mid = (left+right)/2;//这里有可能会有溢出问题
		//两边分别用递归归并排序
		merge(arr, left, mid);
		merge(arr, mid+1, right);
		if(arr[mid] > arr[mid+1])
			merge0(arr, left, mid, right);
	}
	//将arr[left...mid]   arr[mid+1...right]归并
	protected void merge0(int []arr, int left, int mid, int right) {
		if(left >= right)
			return;
		int[] iHelps = new int[right-left+1];//辅助数组
		for (int i = 0; i < iHelps.length; i++) {
			iHelps[i] = arr[left+i];
		}
		
		int aIndex = left, bIndex = mid + 1;
		for (int index = left; index <= right; index++) {
			if(aIndex > mid) {
				arr[index] = iHelps[bIndex-left];
				bIndex++;
			}else if(bIndex > right) {
				arr[index] = iHelps[aIndex-left];
				aIndex++;
			}else if(iHelps[aIndex-left] > iHelps[bIndex-left]) {
				arr[index] = iHelps[bIndex-left];
				bIndex++;
			}else {
				arr[index] = iHelps[aIndex-left];
				aIndex++;
			}
		}
	}
	@Override
	public void sort(int[] arr) {
		merge(arr,0, arr.length-1);
	}
}
