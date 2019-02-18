package algorithm1;

import java.util.Arrays;

import heap.IndexMaxHeap;
import heap.MaxHeap;
import util.SortHelper;

public class NgetPreM {
	
	public static void main(String[] args) {
		int n = 100;
		int m = 10;
		int[] arr = SortHelper.simpleRandomArray(n, n);
		MaxHeap maxheap = new MaxHeap(m);
		//首先插入m个元素
		int i = 0;
		for(;i < n;i++) {
			if(i < m ) {
				maxheap.insert(arr[i]);
			}else if(maxheap.getTop() >= arr[i]) {
				maxheap.extraction();
				maxheap.insert(arr[i]);
			}
		}
		
		for(;!maxheap.isEmpty();) {
			System.out.print(maxheap.extraction() + " ");
		}
		System.out.println();
		Arrays.sort(arr);
		SortHelper.printArray(arr);
	}
	

}
