package com.ren.algorithm;


/**
 * 背包问题:
 * 载重为M 的背包, Wi表示第i件物品的重量, Pi表示第i件物品的收益.
 * 求一种最佳的装载方案
 * @author renzhijiang
 *
 */
public class Sy11 {

    public static void main(String[] args) {
        Sy11 sy = new Sy11();
        int w[] = {10, 20, 30}; 
        int p[] = {60, 100, 120};
        int m = 50;
        System.out.println(sy.package01(w, p, m, 0));
        
        sy.test_2_5();
    }
    /*public List<Integer> test(int w[], int p[], int m) {
        List<Integer> res = new ArrayList<>();
        if (w.length == 0 || p.length == 0)
            return res;
        
        
        return res;
    }*/
    //  arr[i][j][m] 表示可以从 第i个物品到第j件物品 装入容量为 m 的背包, 最大的收益是
    // 递归实现 0-1背包问题
    public int package01(int w[], int p[], int m, int count) {
        if (count == w.length) {
            return 0;
        }
        if (m < w[count]) {
            return package01(w, p, m, count + 1);
        }
        int res1 = package01(w, p, m - w[count], count + 1) + p[count];
        int res2 = package01(w, p, m, count + 1);
        return res1 > res2 ? res1 : res2;
    }
    // 使用 非递归实现
    
    
    
    public void test_2_5() {
        int arr[] = {1, 3, 4, 5, 9, 10};
        int L = 2;
        int R = 6;//   L  < x < R
        int ceilOfL = bSearch_ceil(arr, 0, arr.length-1, L);
        int floorOfR = bSearch_floor(arr, 0, arr.length-1, R);
        System.out.println("ceilOfL:" + ceilOfL + ", floorOfR:" + floorOfR);
    }
    // binary search        
    public int bSearch_ceil(int[] arr, int left, int right, int e) {
        if (left > right)
            return -1;
        int mid = (right - left)/2 + left;
        if (arr[mid] == e) {
            return mid;
        }else if (arr[mid] < e) {
            return bSearch_ceil(arr, mid + 1, right, e);
        }else {
            int temp = bSearch_ceil(arr, left, mid - 1, e);
            if (temp == -1) {
                return mid;
            }else {
                return temp;
            }
        }
    }
    public int bSearch_floor(int[] arr, int left, int right, int e) {
        if (left > right)
            return -1;
        int mid = (right - left + 1)/2 + left;
        if (arr[mid] == e) {
            return mid;
        }else if (arr[mid] > e) {
            return bSearch_floor(arr, left, mid - 1, e);
        }else {
            int temp = bSearch_floor(arr, mid + 1, right, e);
            if (temp == -1) {
                return mid;
            }else {
                return temp;
            }
        }
    }
    
}
