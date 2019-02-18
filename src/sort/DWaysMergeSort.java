package sort;

import java.util.Arrays;

import heap.IndexMinHeap;
import heap.MinHeap;
import util.CheckHelper;
import util.Sort;
import util.SortHelper;
/**
 * d  路归并排序
 * 思想可以借鉴
 * 实现方式欠妥
 * @author renzhijiang
 *
 */
public class DWaysMergeSort implements Sort {
	public static void main(String[] args) {
		int len = 100000;
		
		Sort []sorts = 
			{
					new MergeSort(),
					new DWaysMergeSort(5),
					new ThreeWaysQuickSort(), 
					new SimpleHeapSort()
			};
		SortHelper.testSortAlgorithm(sorts, len);
		
	}
	public DWaysMergeSort() {
	}
	public DWaysMergeSort(int d) {
		if(d < 2)
			throw new IllegalArgumentException(""+d);
		this.d = d;
	}
	@Override
	public void sort(int[] arr) {
		if(arr.length > 1 && arr.length < d) {
			throw new RuntimeException("data error");
		}
		//初始化最小堆的容量
		minheap = new IndexMinHeap(arr.length);
		dwaysMergeSort0(arr, 0, arr.length-1);
	}
	// [left, right] 分成 d 个段 , 每个段分别进行归并排序之后,再合并d个段
	private void dwaysMergeSort0(int[]arr, int left, int right) {
		if(left >= right)
			return;
//		if(right - left < d - 1){
//			insertSort.sort(arr, left, right);
//			return;
//		}
		if(right - left < Math.max(d - 1, 15)){ // 插入排序优化
			insertSort.sort(arr, left, right);
			return;
		}
		int div = d;
		//每一段都进行 归并排序
		int a = left;//  [a, b]为每一小段
		int[] aux = new int[d];// 存放每一段第一个元素索引
		for(int i = 0;i < d;i++) {
			aux[i] = a;//为了后面方便
			int b = (right- a + 1)/(div-i);
			b = (right- a + 1)%(div-i) == 0? b+a-1 : b+a;
			dwaysMergeSort0(arr, a, b);
			a = b+1;
		}
		//[left, right] 合并
		//插入各个段的第一个元素
		int copy[] = copyArr(arr, left, right);
		
		for(int i = 0;i < d;i++) {
			minheap.insert(aux[i], copy[aux[i]-left]);//copy有一个left的偏移
		}
		int index = left;
		while(!minheap.isEmpty()) {
			int e_index = minheap.extractionIndex();
			arr[index++] = copy[e_index-left];
			//判断e_index 所属于的分段,并加入该分段(如果还有)的下一个元素
			int nextIndex = getNextIndex(aux, right, e_index);
			if(nextIndex != -1) {
				minheap.insert(nextIndex, copy[nextIndex-left]);
			}
		}
	}
	//复制 arr[left,right] --> copy[0, right-left]
	private int[] copyArr(int[]arr, int left, int right) {
		int[] copy = new int [right-left+1];
		for(int i = left;i <= right;i++) {
			copy[i-left] = arr[i];
		}
		return copy;
	}
	//返回当前分段的下一个元素的索引,如果没有了,返回-1
	private int getNextIndex(int[] aux, int right, int curIndex) {
		for(int i = 0;i < aux.length;i++) {
			if(curIndex >= aux[i]) {
				int b = i < aux.length-1?aux[i+1]-1:right;
				if(curIndex <= b && curIndex+1 <= b) {
					return curIndex+1;
				}
			}
		}
		return -1;
	}
	private int d = 2;//默认d = 2;即普通的归并排序
	
	private IndexMinHeap minheap;
	private InsertSort insertSort = new InsertSort();
}
