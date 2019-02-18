package sort;

import util.Sort;

public class RadixSort implements Sort{
    
    @Override
    public void sort(int[] arr) {
        radixSort(arr);
    }
 /**
  * 基数排序,只适用与非负数的排序
  * @param nums
  */
    public void radixSort(int[] nums) {
        int[] ret = new int[nums.length];
        
        for (int i = 0;i < 4;i++) {
            if (i == 0 || i == 2)
                countSort(nums, ret, i);
            else
                countSort(ret, nums, i);
        }
    }
    /**
     * 计数排序
     * @param nums 未进行排序的
     * @param ret 部分已经排好序的数组
     * @param base 哪一个部分
     */
    protected void countSort(int[] nums, int ret[], int base) {
        int c[] = new int[256];  
        // 此时,我们将c[i] = 定义成i元素出现的次数
        for (int i = 0;i < nums.length;i++) {
            c[size(nums[i], base)] = c[size(nums[i], base)] + 1;
        }
        // 接下来, 将c[i]累计为i应该在排序好的数组的位置
        for (int i = 1;i < c.length;i++) {
            c[i] += c[i-1]; 
        }
        for (int i = nums.length-1;i >= 0;i--) {
            ret[--c[size(nums[i], base)]] = nums[i];
        }
    }
    protected int size(int n, int base) {
        return (n >>> (8*base)) & 0x000000FF;
    }
}
