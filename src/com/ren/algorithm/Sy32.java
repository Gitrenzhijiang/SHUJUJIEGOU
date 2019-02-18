package com.ren.algorithm;

import java.util.Arrays;

import util.SortHelper;

/**
 * 删数问题:求一个n位正整数删除k(k <= n)位后的最小数
 * @author renzhijiang
 *
 */
public class Sy32 {
    public static void main(String[] args) {
        System.out.println(new Sy32().minNumberAfterDeleteK(320100, 6, 2));
    }
    public int minNumberAfterDeleteK(int number, int n, int k) {
        int buff[] = new int[n];// buff[i]表示第i位数的数值
        int t = number;
        for (int i = n-1;i >= 0;i--) {
            buff[i] = t % 10;
            t /= 10;
        }
        Arrays.sort(buff);
        
        //没有开头的0
        if (buff[0] != 0) {
            return combination(buff, 0, n-k-1);
        }else {
            // 如果n-k-1 != 0, 用最小的替换0
            if (buff[n-k-1] != 0) {
                SortHelper.swap(buff, 0, n-k-1);
                return combination(buff, 0, n-k-1);
            }else {
                return 0;
            }
        }
        
    }
    private int combination(int[]buff, int i, int j) {
        StringBuffer sb = new StringBuffer();
        for (int x = i;x <= j;x++) {
            sb.append(buff[x]);
        }
        return Integer.parseInt(sb.toString());
    }
}
