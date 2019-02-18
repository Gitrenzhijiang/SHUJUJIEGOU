package util;

public class PrintHelper {

	public PrintHelper() {
		super();
	}
	/**
	 * 打印这个整型数组
	 * @param arr
	 */
	public static void printArray(int[] arr) {
		printArray(arr, 0, arr.length-1);
	}
	/**
	 * 打印这个整型数组arr[left,right]
	 * @param arr
	 */
	public static void printArray(int[] arr, int left, int right) {
		for (int i = left; i <= right; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}
}
