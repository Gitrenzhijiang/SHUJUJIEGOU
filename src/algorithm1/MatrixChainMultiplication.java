package algorithm1;
/**
 * 矩阵链相乘 
 * 一个p x q矩阵  与一个  q x r矩阵相乘, 需要 p * q * r次乘法
 * 我们假设现在有n个矩阵相乘, 由于矩阵加括号的方式会对矩阵乘法运算的代价产生巨大影响
 * A1, A2, A3, A4, A5...An
 */
public class MatrixChainMultiplication {

    public static void main(String[] args) {
        // 这是一个矩阵链数组
        int n = 6;
        Matrix[] matrixs = new Matrix[n];
        // 测试矩阵链
        Matrix m1 = new Matrix(30, 35);
        Matrix m2 = new Matrix(35, 15);
        Matrix m3 = new Matrix(15, 5);
        Matrix m4 = new Matrix(5, 10);
        Matrix m5 = new Matrix(10, 20);
        Matrix m6 = new Matrix(20, 25);
        matrixs[0] = m1;matrixs[1] = m2;matrixs[2] = m3;
        matrixs[3] = m4;matrixs[4] = m5;matrixs[5] = m6;
        //
        mcm(matrixs);
        System.out.println("=====================");
        mc_order(matrixs);
    }
    static void mcm(Matrix[]ms) {
        final int n = ms.length;
        int[][]m = new int[n][n];
        int[][]s = new int[n][n];
        System.out.println("最小相乘次数:" + mcm0(ms, 0, n-1, m, s));
        print2arr(m);
        print2arr(s);
    }
    /**
     * m[i][j] A[i..j]:所需的最小乘法次数
     * s[i][j] s[i..j]:i, j 的最优分隔点, 它的值在[i, j)
     */
    static int mcm0(Matrix[] ms, int left, int right, int[][] m, int[][]s) {
        // 单个矩阵的运算次数为0
        if (left == right) {
            return 0;
        }
        if (m[left][right] > 0)
            return m[left][right];
        // k 从 [left, right)  
        int min = Integer.MAX_VALUE;
        for (int k = left;k < right;k++) {
            int temp = mcm0(ms, left, k, m, s) 
                    + mcm0(ms, k+1, right, m, s) 
                    + ms[left].rows * ms[k].cols * ms[right].cols;
            if (temp < min) {
                min = temp;
                s[left][right] = k;
            }
        }
        m[left][right] = min;
        return min;
    }
    static void print2arr(int[][]arr) {
        for (int i = 0;i < arr.length;i++) {
            for (int j = 0;j < arr[i].length;j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    /**
     * 自底向上 利用动态规划求解
     * @param ms
     */
    static void mc_order(Matrix[]ms) {
        final int n = ms.length;
        int[][]m = new int[n][n];
        int[][]s = new int[n][n];
        // l的长度的矩阵链的最小乘法次数及加括号方式求解
        for (int l = 2;l <= ms.length;l++) {
            // 起始i
            for (int i = 0;i <= ms.length-l;i++) {
                int j = i+l-1;
                // 从i-->j 计算, [i..k] [k+1,j] ; 即k >= i  k < j
                m[i][j] = Integer.MAX_VALUE;
                for (int k = i;k < j;k++) {
                    int q = m[i][k] + m[k+1][j] + 
                            ms[i].rows * ms[k].cols * ms[j].cols;
                    if (q < m[i][j]) {
                        m[i][j] = q;
                        s[i][j] = k;
                    }
                }
            }
        }
        System.out.println("(自底向上求解)最小相乘次数:" + m[0][n-1]);
        print2arr(m);
        print2arr(s);
        printf2s(s, 0, s.length-1);
    }
    // 打印 矩阵链 最优括号结合   的可视化样式
    static void printf2s(int[][]s, int left, int right) {
        if (left == right) {
            System.out.print("A[" + left + "]");
            return;
        }
        System.out.print("(");
        printf2s(s, left, s[left][right]);
        printf2s(s, s[left][right] + 1, right);
        System.out.print(")");
    }
    /**
     * 矩阵的结构
     * 只包含行数和列数
     */
    private static class Matrix{
        final int rows;
        final int cols;
        Matrix(int rows, int cols) {
            this.rows = rows;
            this.cols = cols;
        }
    }

}
