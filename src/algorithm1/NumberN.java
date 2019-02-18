package algorithm1;

import java.util.Random;

import sort.ThreeWaysQuickSort;
import util.SortHelper;
import util.TimeUtils;

//求一个数组第N大的数
/**
 * 一般方法:o(nlog(n)) 直接排序求出 
 * 利用快速排序的思想,我们可以用o(n)求出
 */
public class NumberN {

	public static void main(String[] args) {
		int[]arr = SortHelper.generateNearlyOrderedArray(100000, 10000);
		int res = 0;
		int n = 5600;
		
		TimeUtils.start();
		res = getNumberN(arr, n, 0, arr.length-1);
		TimeUtils.over(true);
		
		TimeUtils.start();
		res = getNumberN_2(arr, n);
		TimeUtils.over(true);
		
		//虽然是快速排序,但是还是 nlogn 级别的时间复杂度
		ThreeWaysQuickSort sort = new ThreeWaysQuickSort();
		TimeUtils.start();
		sort.sort(arr);
		TimeUtils.over(true);
	}
	public static int getNumberN_2(int[]arr, int n) {
		
		int left = 0, right = arr.length-1;
		while(true) {
			//优化1:对于高度有序数组
			SortHelper.swap(arr, left, left + new Random().nextInt(right-left+1));
			int e = arr[left];
			//partition 的过程 求出一个中间结果   [left+1, lt] < e  [gt,right] > e
			//    [lt+1, i) = e
			int lt = left, gt = right+1, i = left+1;
			while(i < gt) {
				if(arr[i] < e) {
					SortHelper.swap(arr, ++lt, i);
					i++;
				}else if(arr[i] > e) {
					SortHelper.swap(arr, --gt, i);
				}else {
					i++;
				}
			}
			SortHelper.swap(arr, left, lt);// [left,lt) < e; [lt, i)=e; [gt,right]>e
			//根据lt, gt 判断是否得出答案
			if(n-1 < lt) {
				right = lt-1;
			}else if(n-1 >= gt) {
				left = gt;
			}else {//得出答案
				return arr[lt];
			}
		}
	}
	
	
	
	//得到一个数组第n大的数,n{1,2,3,4...arr.length}
	public static int getNumberN(int []arr, int n, int left, int right) {
		
		//优化1:对于高度有序数组
		SortHelper.swap(arr, left, left + new Random().nextInt(right-left+1));
		int e = arr[left];
		//partition 的过程 求出一个中间结果   [left+1, lt] < e  [gt,right] > e
		//    [lt+1, i) = e
		int lt = left, gt = right+1, i = left+1;
		while(i < gt) {
			if(arr[i] < e) {
				SortHelper.swap(arr, ++lt, i);
				i++;
			}else if(arr[i] > e) {
				SortHelper.swap(arr, --gt, i);
			}else {
				i++;
			}
		}
		SortHelper.swap(arr, left, lt);// [left,lt) < e; [lt, i)=e; [gt,right]>e
		//根据lt, gt 判断是否得出答案
		if(n-1 < lt) {
			return getNumberN(arr, n, left, lt-1);
		}else if(n-1 >= gt) {
			return getNumberN(arr, n, gt, right);
		}else {//得出答案
			return arr[lt];
		}
		
	}
	
}
