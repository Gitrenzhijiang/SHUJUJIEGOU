package sort;

import util.Sort;
import util.SortHelper;

public class SelectSort implements Sort {
/**
 * 选择排序:
 * 依次选出最小值与前面元素交换
 */
	@Override
	public void sort(int[] arr) {
		for(int i = 0;i < arr.length - 1;i++) {
			int minIndex = i;
			for(int j = i + 1;j < arr.length;j++) {
				if(arr[j] < arr[minIndex])
					minIndex = j;
			}
			if(minIndex != i) {
				SortHelper.swap(arr, i, minIndex);
			}
		}
	}
	
	public static void main(String[] args) {
		int arr_len = 6;
		int len = 10000;//数据量
//		int []arr1 = SortHelper.simpleRandomArray(len, 1000);
//		int []arr2 = SortHelper.simpleRandomArray(len, 1000);
//		int []arr3 = SortHelper.simpleRandomArray(len, 1000);
//		int []arr4 = SortHelper.simpleRandomArray(len, 1000);
//		int[]arr5 = SortHelper.simpleRandomArray(len, 1000);
		
//		int []arr1 = SortHelper.generateNearlyOrderedArray(len, 100);
//		int[]arr2 = SortHelper.generateNearlyOrderedArray(len, 100);
//		int[]arr3 = SortHelper.generateNearlyOrderedArray(len, 100);
//		int[]arr4 = SortHelper.generateNearlyOrderedArray(len, 100);
//		int[]arr5 = SortHelper.generateNearlyOrderedArray(len, 100);
//		int[][] Arrays = SortHelper.simpleRandomArrays(arr_len, len, 1000);
		int[][] Arrays = SortHelper.generateNearlyOrderedArrays(arr_len, len, 1000);
		
		SortHelper.testSortAlgorithm("Select Sort", new SelectSort(), Arrays[0]);
		SortHelper.testSortAlgorithm("insert Sort", new InsertSort(), Arrays[1]);
		SortHelper.testSortAlgorithm("simple bubble Sort", new SimpleBubbleSort(), Arrays[2]);
		SortHelper.testSortAlgorithm("JWG bubble Sort", new JWGBubbleSort(), Arrays[3]);
		SortHelper.testSortAlgorithm("shell Sort", new ShellSort(), Arrays[4]);
		SortHelper.testSortAlgorithm("merge Sort", new MergeSort(), Arrays[5]);
		/*SortHelper.printArray(arr);
		SortHelper.printArray(arr2);*/
	}
	
}
