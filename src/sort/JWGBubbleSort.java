package sort;

import util.Sort;
import util.SortHelper;

public class JWGBubbleSort implements Sort {
/**
 * 鸡尾酒排序(冒泡排序改进):
 * 普通冒泡排序一次冒泡的过程只选出一个最大的值,但在数据大多有序时则能确定尾部是有序的
 * 可以确定一个范围是有序的,转而言之,可以确定无序区间的范围,这个范围随着每一次正序冒泡或者反向冒泡减少
 */
	@Override
	public void sort(int[] arr) {
		int left = 0;
		int right = arr.length-1;
		boolean tag = true;//冒泡顺序标记
		int index = 0;
		for(;left < right;) {
			if(tag) {//正向冒泡
				index = left;
				for(int i = left;i < right;i++) {
					if(arr[i] > arr[i+1]) {
						SortHelper.swap(arr, i, i+1);
						index = i+1;
					}
				}
				right = index;
				tag = false;//切换
			}else {
				index = right;
				for(int i = right;i > left;i--) {
					if(arr[i] < arr[i-1]) {
						SortHelper.swap(arr, i, i-1);
						index = i-1;
					}
				}
				left = index;
				tag = true;//切换
			}
		}
	}

}
