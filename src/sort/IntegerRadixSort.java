package sort;

import util.PrintHelper;
import util.SortHelper;

/**
 * 支持int的基数排序(包括负数，正数和0)
 * @author REN
 */
public class IntegerRadixSort extends RadixSort{
    
    public static void main(String[] args) {
        int len = 630000;
//        int []arr = {-23, 3, Integer.MIN_VALUE, 32, 22, -99, 232323, Integer.MAX_VALUE, -2,-2, -23};
        int []arr = SortHelper.simpleArrayByBounds(len, Integer.MIN_VALUE/2, Integer.MAX_VALUE/2+33333);
        new IntegerRadixSort().sort(arr);
        
        SortHelper.testSortAlgorithm("基数排序(升级版)", new IntegerRadixSort(), arr);
    }
    /**
     * 基数排序
     */
//    public void radixSort(int[] nums) {
//        int[] ret = new int[nums.length];
//        for (int i = 0;i < 4;i++) {
//            countSort(nums, ret, i);
//        }
//    }
    /** 计数排序
     * @param nums
     * @param base
     */
//    @Override
//    protected void countSort(int[] nums, int ret[], int base) {
//        int c[] = new int[256];  
//        // 此时,我们将c[i] = 定义成i元素出现的次数
//        for (int i = 0;i < nums.length;i++) {
//            c[size(nums[i], base)] = c[size(nums[i], base)] + 1;
//        }
//        // 接下来, 将c[i]累计为i应该在排序好的数组的位置
//        for (int i = 1;i < c.length;i++) {
//            c[i] += c[i-1]; 
//        }
//        for (int i = nums.length-1;i >= 0;i--) {
//            ret[--c[size(nums[i], base)]] = nums[i];
//        }
//        System.arraycopy(ret, 0, nums, 0, ret.length);
//    }
    @Override
    protected int size(int n, int base) { 
        if (base < 3) {
            return (n >>> (8*base)) & 0x000000FF;
        }
        // 最后一轮,base == 3时
        if (n >= 0) {
            return (n >>> 24) ^ 0x00000080;
        }
        return (n << 1) >>>25;
    }
    
}
