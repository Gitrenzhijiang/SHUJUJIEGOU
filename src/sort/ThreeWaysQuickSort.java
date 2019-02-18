package sort;

import util.Sort;
import util.SortHelper;

public class ThreeWaysQuickSort extends QuickSort3 implements Sort {
/**
 * 3路快速排序
 * 
 * 对于高度重复的数据具有很好的效率
 */
	@Override
	public void sort(int[] arr) {
		sort0(arr, 0, arr.length-1);
	}
	// [left+1, lt] < e    [gt, right] > e
	private void sort0(int[] arr, int left, int right) {
		//插入排序优化
		if(right - left < 16) {
			insertSort.sort(arr, left, right);
			return;
		}
//		if(left >= right)return;
		SortHelper.swap(arr, left, random.nextInt(right-left+1) + left);
		int lt = left, gt = right+1;
		int i = left + 1, e = arr[left];
		while(i < gt) {
			if(arr[i] < e) {
				SortHelper.swap(arr, ++lt, i++);
			}else if(arr[i] > e) {
				SortHelper.swap(arr, --gt, i);
			}else {
				i++;
			}
		}
		if(lt > left)
			SortHelper.swap(arr, left, lt);
		sort0(arr, left, lt-1);
		sort0(arr, gt, right);
	}
}
