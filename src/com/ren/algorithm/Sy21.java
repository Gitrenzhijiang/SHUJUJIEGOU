package com.ren.algorithm;
/**
  游艇租用问题:长江上设置了N个游艇出租站1, 2, 3, ..., N, 游客在这些站中租用游艇, 并在下游的任何一个游艇出租站归还,
 游艇出租站i到游艇出租站j之间的租金r(i, j), 1<=i<j<=N;试求出从游艇出租站1到游艇出租站N所需要的最少租金以及最少租金及其游艇租用和归还方案
 */
public class Sy21 {
    
    public static void main(String[] args) {
        int n = 4;
        int r[][] = new int[n][n];
        r[0][1] = 1;r[0][2] = 2;r[0][3] = 5;
        r[1][2] = 2;r[1][3] = 3;
        r[2][3] = 2;
        System.out.println(new Sy21().minCost(0, n-1, r));
    }
    // 0 1 2: 0-1 1-2   0-2 2-2 
    // w[i][j] 表示从i到j所需的最少租金
    public int minCost(int i, int j, int r[][]) {
        System.err.println(i + "," + j);
        if (i == j) {
            return 0;
        }else if(i + 1 == j) {
            return r[i][j];
        }
        int min = Integer.MAX_VALUE;
        for (int k = i+1;k <= j;k++) {
            int temp = r[i][k] + minCost(k, j, r);
            if (temp < min) {
                min = temp;
                //记录当前最优选择
            }
        }
        return min;
    }
    
}
