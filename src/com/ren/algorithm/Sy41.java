package com.ren.algorithm;

import java.util.ArrayList;
import java.util.List;

// N-皇后问题
public class Sy41 {
    
    public static void main(String[] args) {
        int n = 5;
        int[][] help = new int[n][n];
        new Sy41().nKing(n, help, 0, new ArrayList<>());
        System.out.println("======");
        new Sy41().nKing2(help, new ArrayList<>(), 0);
    }
    // 已经剪枝
    public void nKing2(int[][] help, List<Integer> list, int k) {
        int n = help.length;
        if (k == n) {
            String text = "";
            for (Integer t:list) {
                System.out.print(t + " ");
                text = t + " " + text;
            }
            System.out.println();
            if (k%2 == 0 || list.get(0) != k/2) {
                System.out.println(text);
            }
            
        }
        // 当前第k行, [0-n]: 
        // 如果n=4, 0 1 2 3
        int count = k == 0?(n%2==0?n/2:1+n/2):n;
        for (int i = 0;i < count;i++) {
            if (canPut(help, k, i)) {
                help[k][i] = 1;
                list.add(i);
                nKing2(help, list, k+1);
                help[k][i] = 0;
                list.remove(new Integer(i));
            }
        }
    }
    
    // 没有剪枝
    private void nKing(int n, int[][] help, int k, List<Integer> list) {
        if (k == n) {
            for (Integer t:list) {
                System.out.print(t + " ");
            }
            System.out.println();
        }
        // 当前第k行, [0-n]: 
        // 如果n=4, 0 1 2 3
        for (int i = 0;i < n;i++) {
            if (canPut(help, k, i)) {
                help[k][i] = 1;
                list.add(i);
                nKing(n, help, k + 1, list);
                help[k][i] = 0;
                list.remove(new Integer(i));
            }
        }
    }
    // 判断第k行第index个能否放皇后
    private boolean canPut(int[][] help, int k, int index) {
        for (int i = k - 1; i >= 0; i--) {
            if (help[i][index] == 1) {
                return false;
            }
            if (index + k-i < help.length && help[i][index + k - i] == 1) {
                return false;
            }
            if (index - k + i >= 0 && help[i][index - k + i] == 1) {
                return false;
            }
        }
        return true;
    }
}
