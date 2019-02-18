package algorithm1;
/**
 * 钢条切割问题:
 * 假定i英寸的钢条的价格如下:
 * i 1 2 3 4  5  6  7  8  9 10
 * p 1 5 8 9 10 17 17 20 24 30
 * 给定一个长度为N的钢条, 求解一种切割方案达到收益最大
 * 
 */
public class Steelstripcutting {
    public static void main(String[] args) {
        int n = 5;
        fun1(n);
        fun2(n);
    }
    // 长度对应收益数组
    static final int p[] = {0, 1, 5, 8, 9, 10, 17, 17, 20, 24, 30};
    /**
     * 自顶向下的调用
     */
    static void fun1(int n) {
        int [] r = new int[300];
        for (int i = 0;i < r.length;i++) {
            r[i] = -1;
        }
        System.out.println(cut_rod_aux(n, r));
        
    }
    //         n(子问题切割)  
    private static int cut_rod_aux(int n, int[] r) {
        if (r[n] >= 0) {
            return r[n];
        }
        int q = 0;
        if (n > 0) {
            for (int i = 1;i <= n && i < p.length;i++) {
                q = Math.max(q, p[i] + cut_rod_aux(n-i, r));
            }
        }
        r[n] = q;
        return r[n];
    }
    /**
     * 自底向下的方式
     */
    static void fun2(int n) {
        int r[] = new int[300];
        r[0] = 0;
        for (int j = 1;j <= n;j++) {
            int max = -1;
            for (int i = 1;i <= j;i++) {
                max = Math.max(max, p[i] + r[j-i]);
            }
            r[j] = max;
        }
        System.out.println("max=" + r[n]);
    }
}
