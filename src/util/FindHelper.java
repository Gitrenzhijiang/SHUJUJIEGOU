package util;

import java.util.Arrays;
import java.util.Random;

public class FindHelper {
	//简单测试是否能有效找到
	public static boolean testCanFind(Find find) {
		
		int test_num = 20;
		for(int i = 0;i < test_num;i++) {
			int[] arr = sortArr(SortHelper.simpleRandomArray(20, 20));
			int target = new Random().nextInt(20);
			int trueanswer = Arrays.binarySearch(arr, target);
			int answer = find.find(arr, arr.length, target);
			if(trueanswer >= 0 && arr[trueanswer] != arr[answer])
			{
				System.out.println(find.getClass().getSimpleName()+":not ok");
				return false;
			}
		}
		return true;
	}
	//打印出查询的结果及数组和target
	public static void printfindAlgorithm(Find find, int length) {
		int[]arr = sortArr(SortHelper.simpleRandomArray(length, length));
		int target = new Random().nextInt(length);
		printfindAlgorithm(find, arr, target);
	}
	public static void printfindAlgorithm(Find find,int[]arr, int target) {
		PrintHelper.printArray(arr);
		System.out.println("target:" + target);
		System.out.println("index:"+find.find(arr, arr.length, target));
		
	}
	
	private static int[] sortArr(int[]arr) {
		Arrays.sort(arr);
		return arr;
	}
}
