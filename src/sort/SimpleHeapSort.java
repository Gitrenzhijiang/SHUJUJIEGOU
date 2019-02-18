package sort;

import util.CheckHelper;
import util.Sort;
import util.SortHelper;

public class SimpleHeapSort implements Sort {
	public static void main(String[] args) {
		int len = 630000;
		
		Sort []sorts = 
			{		
					new MergeSort(),  
					new ThreeWaysQuickSort(), 
					new SimpleHeapSort(),
					new DWaysHeapSort(3),
					new RadixSort(),
					new IntegerRadixSort(),
					new SystemSort()
					};
		
		SortHelper.testSortAlgorithm(sorts, len);
//		int [] arr = SortHelper.simpleRandomArray(10, 100);
		
//		new SimpleHeapSort().sort(arr);
		
	}
	@Override
	public void sort(int[] arr) {
		createMaxHeap(arr, arr.length);//首先生成一个堆 o(n)
		for(int i = arr.length;i > 1;i--) {
			SortHelper.swap(arr, 0, i-1);//将堆中的最大数与末尾交换,
			shiftDown(arr, 0, i-1);
		}
	}
	//将第k 元素 shiftDown    [0, len)
	private void shiftDown(int arr[], int k, int len) {
		int e = arr[k];
		while(2*k+1 < len) { //k 有左孩子
			int j = 2*k+1;//k 应该和j 交换位置
			if(j+1 < len && arr[j+1] > arr[j]) {
				j += 1;
			}
			if(e < arr[j])
			{
				arr[k] = arr[j];
				k = j;
			}else {
				break;
			}
		}
		arr[k] = e;
	}
	
	//将  [0, len) 内的元素满足最大堆的定义
	// [0 , 10)
	//时间复杂度为 o(n) 
	private void createMaxHeap(int[]arr, int len) {
		/**
		 * 思路:从最后一个非叶子节点开始,使其满足最大堆定义
		 */
		for(int k = len/2-1;k >= 0;k--) {
			shiftDown(arr, k, len);
		}
	}
}
