package sort;

import java.util.Random;

import util.Sort;
import util.SortHelper;

public class QuickSort2 implements Sort {
//快速排序基本实现2
	@Override
	public void sort(int[] arr) {
		sort0(arr, 0, arr.length-1);
	}
	private Random random = new Random();
	private void sort0(int[] arr, int left, int right) {
		
		//优化: 随机选择,避免了快速排序退化成 o(n2)的排序算法
		//对近乎有序的数据排序时,效率大大增加
		SortHelper.swap(arr, left, left + random.nextInt(right-left+1));
		//思路:挖坑填数
		int i = left, j = right;
		int e = arr[left];//e
		while(i < j) {
			//从后面找到一个<e的数
			while(i<j && arr[j] >= e)
				j--;
			if(i < j) {
				arr[i++] = arr[j];
			}
			while(i<j && arr[i] <= e)
				i++;
			if(i<j) {
				arr[j--] = arr[i];
			}
		}
		arr[i] = e;
		if(left < i-1)
			sort0(arr, left, i-1);
		if(i+1 < right)
			sort0(arr, i+1, right);
	}
	public static void main(String[] args) {
		//当使用语句A时报错(java.lang.StackOverflowError),使用语句B时不报错
		//奇怪的是当n减少到一定数量,语句A和语句B都不报错
		//原因在于当数组有序时,迭代的次数将是n2
		int n = 15000;
		int[]arr = new int[n];
		for(int i = 0;i < arr.length;i++) {
			//arr[i] = i+1;   //语句A
			arr[i] = new Random().nextInt();//语句B
		}
		//快速排序
		new QuickSort().sort(arr);
		//SortHelper.printArray(arr);
		//System.out.println(SortHelper.isSorted(arr));
	}
}
