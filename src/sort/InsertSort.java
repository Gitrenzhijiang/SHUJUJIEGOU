package sort;

import util.Sort;

public class InsertSort implements Sort {
/**
 * 插入排序
 * 初始状态为第一个元素有序
 * 将后面的元素插入到有序区域内
 * 
 * 优化1:将多次swap替换成赋值
 * 
 */
	/*
	@Override
	public void sort(int[] arr) {
		for(int i = 1;i < arr.length;i++) {
			int temp = arr[i];
			boolean isPut = false;
			for(int j = i;j > 0;j--) {
				if(temp < arr[j-1]) {
					arr[j] = arr[j-1];
				}else {
					arr[j] = temp;
					isPut = true;
				}
			}
			if(!isPut) {
				arr[0] = temp;
			}
		}
	}
	*/
	@Override
	public void sort(int[] arr) {
		for(int i = 1;i < arr.length;i++) {
			int temp = arr[i];
			int j = i;
			for(;j > 0 && arr[j-1] > temp;j--) {
				arr[j] = arr[j-1];
			}
			arr[j] = temp;
		}
	}
	
	public void sort(int[] arr, int left, int right) {
		for(int i = left+1;i <= right;i++) {
			int temp = arr[i];
			int j = i;
			for(;j > left && arr[j-1] > temp;j--) {
				arr[j] = arr[j-1];
			}
			arr[j] = temp;
		}
	}
}
