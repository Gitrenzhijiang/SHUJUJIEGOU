package sort;

import util.Sort;
import util.SortHelper;

public class MergeSortBU extends MergeSort implements Sort {
/**
 * 自底向上的归并排序
 * 
 */
	@Override
	public void sort(int[] arr) {
		for(int sz = 1;;sz+=sz)
		{
			for(int i = 0;i + sz< arr.length;i += sz*2) {
				if(arr[i+sz-1] <= arr[i+sz])//已经有序,无需 归并
					continue;
				if(sz > 15)//当数量足够小时,采用插入排序优化
					super.merge0(arr, i, i + sz - 1, Math.min(arr.length-1, i + sz + sz - 1));
				else {
					insertSort.sort(arr, i, Math.min(arr.length-1, i + sz + sz - 1));
				}
			}
			if(2*sz >= arr.length)//优化 归并次数
				break;
		}
	}
	public static void main(String[] args) {
		int arr_len = 7;
		int len = 100000;
		
		int[][] Arrays = SortHelper.simpleRandomArrays(arr_len, len, 1000);
//		int[][] Arrays = SortHelper.generateNearlyOrderedArrays(arr_len, len, 1000);
		
		SortHelper.testSortAlgorithm("merge Sort", new MergeSort(), Arrays[0]);
		//SortHelper.testSortAlgorithm("merge SortBU", new MergeSortBU(), Arrays[1]);
		//SortHelper.testSortAlgorithm("quick Sort", new QuickSort(), Arrays[2]);
		//SortHelper.testSortAlgorithm("quick Sort (2)", new QuickSort2(), Arrays[3]);
		//SortHelper.printArray(Arrays[3]);
		
		//SortHelper.testSortAlgorithm("sys Sort ", new SystemSort(), Arrays[4]);
		SortHelper.testSortAlgorithm("quick Sort (3)", new QuickSort3(), Arrays[5]);
		SortHelper.testSortAlgorithm("3Ways quick Sort", new ThreeWaysQuickSort(), Arrays[6]);
	}
}
