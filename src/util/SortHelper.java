package util;

import java.util.Random;

public class SortHelper extends PrintHelper {
    /**
     * 返回一个简单的数组
     * @param length 数组长度
     * @return
     */
    public static int[] simpleArray(int length) {
        return new int[length];
    }
    /**
     * 对一个整型数组进行随机赋值
     * [left, right)
     * @param arr
     */
    private static void randomArray(int[] arr, int bound) {
        Random random = new Random();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(bound);
        }
    }
    /**
     * 返回一个随机数分布均匀且在[start,end)范围内的随机数组
     * @param length
     * @param start
     * @param end
     * @return
     */
    public static int[] simpleArrayByBounds(int length, int start, int end) {
        Random random = new Random();
        int b = end-start;
        if (b < 0) {
            b = Integer.MAX_VALUE;
        }
        int[] arr = simpleArray(length);
        for (int i = 0;i < arr.length;i++) {
            arr[i] = random.nextInt(b) + start;
        }
        return arr;
    }
    /**
     * 返回一个指定长度且随机数初始化的整型数组
     * @param length
     * @return
     */
    public static int[] simpleRandomArray(int length, int bound) {
        int[] arr = simpleArray(length);
        randomArray(arr, bound);
        return arr;
    }
    /**
     * 一个近乎有序的数组,只有给定个元素是无序的
     * @param n
     * @param swapTimes
     * @return
     */
    public static int[] generateNearlyOrderedArray(int n, int swapTimes) {
        int[]arr = simpleArray(n);
        for(int i = 0;i < n;i++) {
            arr[i] = i;
        }
        Random random = new Random();
        for(int i = 0;i < swapTimes;i++) {
            swap(arr, random.nextInt(n), random.nextInt(n));
        }
        return arr;
    }
    /**
     * 简单随机二维数组,每一个一维数组都是相同的
     * @param arr_len 二维数组长度
     * @param length  列长度
     * @param bound 随机数在[0-bound)之间
     * @return
     */
    public static int[][] simpleRandomArrays(int arr_len, int length, int bound){
        Random random = new Random();
        int[][] arr = new int[arr_len][length];
        for(int i = 0;i < length;i++) {//列
            int temp = random.nextInt(bound);
            for(int j = 0;j < arr_len;j++) {
                arr[j][i] = temp;
            }
        }
        return arr;
    }
    /**
     * 返回一个基本有序的二维数组
     * @param arr_len 二维数组的行数
     * @param n 列数
     * @param swapTimes 一行中有多少的交换量
     * @return
     */
    public static int[][] generateNearlyOrderedArrays(int arr_len, int n, int swapTimes){
        int[][] arr = new int[arr_len][n];
        for(int i = 0;i < arr_len;i++) {
            for(int j = 0;j < n;j++) {
                arr[i][j] = j;
            }
        }
        Random random = new Random();
        for(int i = 0;i < swapTimes;i++) {
            int r_x = random.nextInt(n);
            int r_y = random.nextInt(n);
            for(int j = 0;j < arr_len;j++)
                swap(arr[j], r_x, r_y);
        }
        return arr;
    }
    
    /**
     * 测试sort排序算法是否有效
     * @param sortName 排序算法名称
     * @param sort 排序算法
     * @param arr 测试数组
     */
    public static void testSortAlgorithm(String sortName, Sort sort, int[]arr) {
        if(!CheckHelper.canSort(sort))
        {
            System.out.println(sortName + " 失败!");
            return;
        }
        long startTime = System.nanoTime();
        sort.sort(arr);
        long time = (System.nanoTime() - startTime)/1000000;
        if(!isSorted(arr)) {
            System.out.println(sortName + " 失败!");
            return;
        }
        System.out.println(sortName + ": " + time + " ms");
    }
    //测试sort之间 在各种数据 的对比,打印结果
    public static void testSortAlgorithm(Sort[] sorts, int n) {
        
        String [] strs = {"一般随机数据", "几乎有序数据", "大量重复数据"};
        int[][][] Arrays = new int[strs.length][sorts.length][];
        Arrays[0] = simpleRandomArrays(sorts.length, n, n-(n/1000));//一般随机数据
        Arrays[1] = generateNearlyOrderedArrays(sorts.length, n, (n/100)+5);//几乎有序数据
        Arrays[2] = simpleRandomArrays(sorts.length, n, n/100+5);//大量重复数据   
                
        for(int j = 0;j < Arrays.length;j++) {
            System.out.println(strs[j] + "(" + n + "):");
            for(int i = 0;i < sorts.length;i++) {
                Sort sort = sorts[i];
                testSortAlgorithm(sort.getClass().getSimpleName(), sort, Arrays[j][i]);
            }
        }
    }
    /**
     * 数组从小到大排序则返回true,否则返回false
     * @param arr
     * @return
     */
    public static boolean isSorted(int[]arr) {
        if(arr == null)return false;
        if(arr.length == 1)return true;
        for (int i = 0; i < arr.length - 1; i++) {
            if(arr[i] > arr[i+1])
                return false;
        }
        return true;
    }
    /**
     * 交换数组元素
     * @param arr
     * @param i
     * @param j
     */
    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    
    
}
