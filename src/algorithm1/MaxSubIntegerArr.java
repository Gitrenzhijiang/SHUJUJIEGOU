package algorithm1;

import java.util.Random;

import util.SortHelper;
import util.TimeUtils;

/**
 * 最大子数组问题
 * @author renzhijiang
 */
public class MaxSubIntegerArr {
    
    public static void main(String[] args) {
        int[] testArr = {13, -3, -25, 20, -3, -16, -23, 18, 20, -7, 12, -5, -22, 15, -4, 7};
        testArr = SortHelper.simpleRandomArray(50000, 5000);
        for (int i = 0;i < testArr.length;i++) {
            testArr[i] = testArr[i] - new Random().nextInt(5000);
        }
        
        TimeUtils.start();
        int res[] = new MaxSubIntegerArr().maxSubIntArr0(testArr, 0, testArr.length-1);
        TimeUtils.over(true);
        System.out.println(res[0] + "," + res[1] + ":" + res[2]);
        
        
        TimeUtils.start();
        MaxSubArray msa = new MaxSubIntegerArr().maxSubIntArr1(testArr);
        TimeUtils.over(true);
        System.out.println(msa.left + "," + msa.right + ":" + msa.value);
        
        
        TimeUtils.start();
        MaxSubArray msa2 = new MaxSubIntegerArr().maxSubIntArr2(testArr);
        TimeUtils.over(true);
        System.out.println(msa2.left + "," + msa2.right + ":" + msa2.value);
        
        TimeUtils.start();
        MaxSubArray msa3 = new MaxSubIntegerArr().maxSubIntArr3(testArr);
        TimeUtils.over(true);
        System.out.println(msa3.left + "," + msa3.right + ":" + msa3.value);
        
        new MaxSubIntegerArr().test(testArr);
    }
    /**
     * 使用分治策略解决:
     * arr[left..right] 的最大子数组 可能是 从 
     * arr[left, mid] 或 arr[mid+1, right] 或   包含 arr[mid] 的arr[i, j] i <= mid <= j 
     * @param arr
     */
    public int[] maxSubIntArr0(int[] arr, int left, int right) {
        if (right == left) {
            return new int[] {left, right, arr[left]};
        }
        int mid = left + (right - left) / 2;
        int[] aLeft = maxSubIntArr0(arr, left, mid);
        int[] aRight = maxSubIntArr0(arr, mid + 1, right);
        int[] crossLR = find_max_crossing_subarray(arr, left, mid, right);
        if (aLeft[2] >= aRight[2] && aLeft[2] >= crossLR[2]) {
            return aLeft;
        }else if(aRight[2] >= aLeft[2] && aRight[2] >= crossLR[2]) {
            return aRight;
        }else {
            return crossLR;
        }
    }
    private int[] find_max_crossing_subarray(int[] arr, int low, int mid, int high) {
        int sum = 0;
        int left_max = Integer.MIN_VALUE;// 左边[i, mid]最大
        int ret_left = -1;
        for (int i = mid;i >= low;i--) {
            sum = sum + arr[i];
            if (sum > left_max) {
                left_max = sum;
                ret_left = i;
            }
        }
        sum = 0; 
        int ret_right = mid;
        int right_max = Integer.MIN_VALUE;// 后边[mid, right]最大
        for (int i = mid + 1;i <= high;i++) {
            sum = sum + arr[i];
            if (sum > right_max) {
                right_max = sum;
                ret_right = i;
            }
        }
        return new int[] {ret_left, ret_right, left_max + right_max};
    }
    
    /**
     * 感觉这个算法很暴力, 非递归
     */
    public MaxSubArray maxSubIntArr1(int[] arr) {
        MaxSubArray[] buff = new MaxSubArray[arr.length];
        // buff[i]: arr[0, i]最大子数组的值
        buff[0] = new MaxSubArray(0, 0, arr[0]);
        for (int i = 1;i < arr.length;i++) {
            // arr[0, i] 的最大子数组:1.arr[0, i-1] 的最大子数组; 2.arr[k, i]数组,0 <= k <= i
            int max = Integer.MIN_VALUE;
            int sum = 0;
            int k = i;
            for (int j = i;j >= 0;j--) {
                sum += arr[j];
                if (sum > max) {
                    max = sum;
                    k = j;
                }
            }
            buff[i] = buff[i - 1].value > max ? buff[i - 1]:new MaxSubArray(k, i, max);
        }
        return buff[arr.length-1];
    }
    static class MaxSubArray{
        
        public MaxSubArray(int left, int right, int value) {
            super();
            this.left = left;
            this.right = right;
            this.value = value;
        }
        int left;
        int right;
        int value;
    }
    /**
     * 使用buff[]数组
     * @param arr
     * @return
     */
    public MaxSubArray maxSubIntArr2(int[] arr) {
        // buff[i] : [x, i]最大子数组
        MaxSubArray[] buff = new MaxSubArray[arr.length];
        buff[0] = new MaxSubArray(0, 0, arr[0]);
        int maxIndex = 0;
        int maxValue = arr[0];
        for (int i = 1;i < arr.length;i++) {
            if (buff[i - 1].value > 0) {
                buff[i] = new MaxSubArray(buff[i - 1].left, i, buff[i - 1].value + arr[i]);
            }else {
                buff[i] = new MaxSubArray(i, i, arr[i]);
            }
            
            if (buff[i].value > maxValue) {
                maxValue = buff[i].value;
                maxIndex = i;
            }
        }
        return buff[maxIndex];
    }
    /**
     * 线性时间, 最大子数组
     * @param arr
     * @return
     */
    public MaxSubArray maxSubIntArr3(int[] arr) {
        
        int curSum = 0, max = Integer.MIN_VALUE;
        int index_start = 0, index_end = 0;// 最大子数组的start和end索引
        int last_start = 0;// 当前遍历子数组开始索引
        for (int i = 0;i < arr.length;i++) {
            if (curSum > 0) {
                curSum += arr[i];
            }else {
                curSum = arr[i];
                last_start = i;
            }
            
            if (curSum > max){
                max = curSum;
                index_start = last_start;
                index_end = i;
            }
        }
        return new MaxSubArray(index_start, index_end, max);
    }
    
    public void test(int[] arr) {
        int curSum = 0, maxSum = 0;
        int i = 0;
        for(i=0; i<arr.length; i++){
            curSum += arr[i];     // 累加
     
            if(curSum < 0){         // 当前和小于0，重置为0
                curSum = 0;
            }
     
            if(curSum > maxSum){    // 当前和大于最大和，则重置最大和
                maxSum = curSum; 
            }
        }
        System.out.println("maxSum:" + maxSum);

    }
}
