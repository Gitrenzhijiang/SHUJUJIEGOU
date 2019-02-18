package util;

import java.util.Arrays;


public class CheckHelper {
	
	// 指定测试数组的长度并测试该算法
	public static void testSortPrint(Sort sort, int test_len, boolean canSort) {
		int[]arr = SortHelper.simpleRandomArray(test_len, test_len);
		testSortPrint(sort, arr, canSort);
	}
	// 用给定的测试数组测试排序算法
	public static void testSortPrint(Sort sort, int []arr, boolean canSort) {
		SortHelper.printArray(arr);
		sort.sort(arr);
		SortHelper.printArray(arr);
		if(canSort)
			System.out.println("canSort?"+CheckHelper.canSort(sort));
	}
	/**
	 * 这个排序算法是否能够正常运行并排序
	 * @param sort
	 * @return
	 */
	public static boolean canSort(Sort sort) {
		int n = 1000;//1000 测试数据的范围
		int num = 10;//测试10次
		final int TWO = 2; 
		int[][][] arr = new int[num][TWO][];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = SortHelper.simpleRandomArrays(TWO, n, n-15);
			Arrays.sort(arr[i][0]);
			try {
				sort.sort(arr[i][1]);
			}catch(Exception e) {
				System.out.println("err in canSort():"+ e);
			}
			if(!isEquals(arr[i][0], arr[i][1])) {
				return false;
			}
		}
		return true;
		
	}
	// 比较这两个数组是否内容一致
	private static boolean isEquals(int[]arr1, int []arr2) {
		if(arr1.length != arr2.length)
			return false;
		for(int i = 0;i < arr1.length;i++) {
			if(arr1[i] != arr2[i])
				return false;
		}
		return true;
	}
}
