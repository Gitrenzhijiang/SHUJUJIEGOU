package sort;

import util.Sort;

public class ShellSort implements Sort {
/**
 	希尔排序:
 	1, 4, 13, 40, 121, 364, 1093...
 	
 	无论在有序性高的数组还是有序性低的数组都有不菲的效率
 */
	@Override
	public void sort(int[] arr) {
		int shell = 1;
		while(shell < arr.length/3) {
			shell = 3*shell + 1;
		}
		for(;shell >= 1;) {
			for(int i = shell;i < arr.length;i+=shell) {
				int temp = arr[i];
				int j = i;
				for(;j-shell >= 0 && arr[j-shell] > temp;j-=shell) {
					arr[j] = arr[j-shell];
				}
				arr[j] = temp;
			}
			shell /= 3;
		}
	}

}
