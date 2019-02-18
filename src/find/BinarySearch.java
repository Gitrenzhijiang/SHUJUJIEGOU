package find;

import util.Find;
import util.FindHelper;

public class BinarySearch implements Find {
	public static void main(String[] args) {
		
		BinarySearch bs = new BinarySearch();
		System.out.println(FindHelper.testCanFind(new BinarySearch()));
		
		//测试floor
		// 37 的"地板"是 36
		FindHelper.printfindAlgorithm(new BinarySearch(), new int[] {2,3,20,20,20,33,36,38,39}, 37);
		
		//测试ceil
		int[] arr = {2,3,20,20,20,33,36,38,42};  // 37 的"天花板"是 38 
		System.out.println(bs.ceil(arr, arr.length, 37));
	}
	
	public int find_simple(int[] arr, int n, int target) {
		int left = 0, right = n-1;
		while(left <= right) {
			int mid = left + (right-left)/2;
			if(arr[mid] == target) {
				return mid;
			}else if(target < arr[mid]) {
				right = mid-1;
			}else {
				left = mid+1;
			}
		}
		return -1;
	}
	/**
	 * 查找 小于或等于 需要查找的值的最接近的那个元素
	 * 如果没有返回-1
	 * @param arr
	 * @param n
	 * @param target
	 * @return
	 */
	public int floor(int[] arr, int n, int target) {
		
		int left = 0, right = n-1;
		while(left <= right) {
			
			//这里用减法避免可能的数据溢出
			int mid = left + (right-left)/2;
			
			//直接查询到结果
			if(arr[mid] == target) {
				return mid;
			}else if(target < arr[mid]) {
				
				//当前的数大于需要查找的数, 继续查找
				right = mid-1;
			}else {
				
				//当前的数小于需要查找的数,有可能就是最接近的
				//当没有下一个元素 或者 下一个元素大于需要查找的数, 当前就是最接近的最小元素
				if(mid + 1 > right || arr[mid + 1] > target)
					return mid;
				left = mid+1;
			}
		}
		return -1;
	}
	/**
	 * 查找 大于或等于 需要查找的元素的最小值
	 * @param arr
	 * @param n
	 * @param target
	 * @return
	 */
	public int ceil(int[] arr, int n, int target) {
		
		int left = 0, right = n-1;
		while(left <= right) {
			int mid = left + (right-left)/2;
			
			//查询到需要查询的值
			if(arr[mid] == target) {
				return mid;
			}else if(target < arr[mid]) {
				
				// 当前值 大于 需要查找的元素, 可能是最接近的值
				// 没有上一个元素 或者 上一个元素 小于 需要查询的值, 当前就是大于需要查找值的 最小值
				if(mid - 1 < left || arr[mid - 1] < target) 
					return mid;
				right = mid-1;
			}else {
				
				// 当前值 小于 需要查找的元素,继续查找
				left = mid+1;
			}
		}
		return -1;
	}


	@Override
	public int find(int[] arr, int n, int target) {
		
		return floor(arr, n, target);
	}
}
