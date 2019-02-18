package algorithm1;

public class G3_9 {

    public static void main(String[] args) {
        G3_9 g39 = new G3_9();
        int t[] = {2, 3, 4, 7};// 0 2 3 4
        int v[] = {1, 3, 5, 9};
        int D = 10;
        System.out.println(g39.works(t, v, D, 0));
        System.out.println(g39.works2(t, v, D));
        System.out.println(g39.works3(t, v, D));
//        for (int i = 0;i < help.length;i++) {
//            for (int j = 0;j < help[i].length;j++) {
//                if (help[i][j] != null)
//                    System.out.print(help[i][j] + "\t");
//                else
//                    System.out.print('N' + "\t");
//            }
//            System.out.println();
//        }
//        System.out.println();
    }
    // 第一个任务做还是不做.
    public int works(int t[], int v[], int time, int index) {
        if (index == t.length) {
            return 0;
        }
        int res = -1;
        if (t[index] > time) {
            //只能不做
            res = works(t, v, time, index + 1);
        }else {
            int t1 = works(t, v, time, index + 1);
            int t2 = works(t, v, time - t[index], index + 1) + v[index];
            
            res = t2 > t1 ? t2:t1;
        }
        return res;
    }
    // has a problem: 如何保存第i个物品是否放入背包?
    public int works2(int t[], int v[], int d) {
        // 0 1 2  - (n-1)
        int[][] dp = new int[t.length][d+1];
        for (int j = 0;j < d + 1;j++) {
            if (t[t.length - 1] <= j) {
                dp[t.length - 1][j] = v[t.length - 1];
            }
        }
        for (int i = t.length - 2;i >= 0;i--) {
            for (int j = 0;j < d+1;j++) {
                if (j < t[i]) {
                    dp[i][j] = dp[i+1][j];
                }else {
                    int t1 = dp[i+1][j];
                    int t2 = dp[i+1][j-t[i]] + v[i];
                    dp[i][j] = t1 > t2?t1:t2;
                }
            }
        }
        return dp[0][d];
    }
    /**
     * 带标记函数的0-1背包, 利用dp数组, 回溯出计算过程...
     * @param t
     * @param v
     * @param d
     * @return
     */
    public int works3(int t[], int v[], int d) {
        int[][] dp = new int[t.length][d+1];
        // dp[i][j]: 0-i 装入 j容量的背包的最大价值
        for (int j = 0;j < d + 1;j++) {
            if (t[0] <= j) {
                dp[0][j] = v[0];
            }
        }
        for (int i = 1;i < t.length;i++) {
            for (int j = 0;j < d+1;j++) {
                if (j < t[i]) {
                    dp[i][j] = dp[i-1][j];
                }else {
                    int t1 = dp[i-1][j];
                    int t2 = dp[i-1][j-t[i]] + v[i];
                    dp[i][j] = t1 > t2?t1:t2;
                }
            }
        }
        int x = t.length - 1;
        int y = d;
        if (x == 0 && dp[x][y] == 0) {
            System.out.println("empty package...");
        }else if (x == 0 && dp[x][y] == v[0]){// only one thing
            System.out.println("the package:" + x);
        }else {
            System.out.print("the package:");
            while (x > 0) {
                if (dp[x-1][y] != dp[x][y]) {
                    System.out.print(x + " ");
                    y = y - t[x]; // 至关重要的顺序
                    x = x-1;
                }else {
                    x = x-1;
                }
                // out ,  x 和 y都是x=0时状态
                // 如果最后剩余容量可以装下第0个, 确认装入
                if (x == 0 && y >= t[0]) {
                    System.out.print(0 + " ");
                }
            }
            System.out.println();
        }
//        for (int i = 0;i < dp.length;i++) {
//            for (int j = 0;j < d+1;j++) {
//                System.out.print(dp[i][j] + " ");
//            }
//            System.out.println();
//        }
        return dp[t.length - 1][d];
    }
}
