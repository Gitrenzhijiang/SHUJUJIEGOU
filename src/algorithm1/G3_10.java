package algorithm1;
/**
 * 0-1背包问题推广, 设有N中物品, 第i中物品的价值vi, 重量是wi, 体积是ci, 且装入背包的重量限制是W,
 * 体积限制是V且价值达到最大?设计一种动态规划算法求解这个问题, 说明算法的时间复杂度
 * @author renzhijiang
 *
 */
public class G3_10 {
    
    public static void main(String[] args) {
        G3_10 动态规划扩展 = new G3_10();
        int v[] = {3, 5, 8, 6, 2};//价值数组
        int w[] = {3, 7, 7, 4, 1};//重量数组
        int c[] = {2, 3, 3, 2, 1};//体积数组
        int W = 11;//重量限制
        int V = 4;//体积限制
        动态规划扩展.packageEx(v, w, c, W, V);
    }
    /**
     * 
     * @param v 价值数组
     * @param w 重量数组
     * @param c 体积数组
     * @param W 重量限制
     * @param V 体积限制
     */
    public void packageEx(int v[], int w[], int c[], int W, int V) {
        // dp[i][j][k]  0-i  重量限制j  体积限制k, 最大价值
        int dp[][][] = new int[v.length][W+1][V+1];
        // 首先初始化数组, i=0时情况
        for (int j = 0;j < W+1;j++) {
            for (int k = 0;k < V+1;k++) {
                if (j >= w[0] && k >= c[0]) {
                    dp[0][j][k] = v[0];
                }
            }
        }
        // dp[i][j][k] = 第i件物品可以放下时: dp[i-1][j][k] dp[i-1][j-w[i]][k-c[i]]
        for (int i = 1;i < v.length;i++) {
            for (int j = 0;j < W+1;j++) {
                for (int k = 0;k < V+1;k++) {
                    if (j < w[i] || k < c[i]) {
                        //此时第i件物品无法放入当前背包...
                        dp[i][j][k] = dp[i-1][j][k];
                    }else {
                        int t1 = dp[i-1][j][k];
                        int t2 = dp[i-1][j - w[i]][k - c[i]] + v[i];
                        dp[i][j][k] = t1 > t2?t1:t2;
                    }
                }
            }
        }
        System.out.println("最大装入价值:" + dp[v.length-1][W][V]);
        // 追溯解的过程
        int x = v.length - 1, y = W, z = V;
        if (x == 0 && dp[x][y][z] == 0) {
            System.out.println("the package is empty");
        }else if(x == 0 && dp[x][y][z] != 0) {
            System.out.println("package: 0.");
        }else {
            System.out.print("the package:");
            while (x > 0) {
                if (dp[x-1][y][z] != dp[x][y][z]) {
                    System.out.print(x + " ");
                    y -= w[x];
                    z -= c[x];
                    x = x-1;
                }else {
                    x = x-1;
                }
                if (x == 0 && w[0] <= y && c[0] <= z) {
                    System.out.print("0");
                }
            }
            System.out.println();
        }
    }
    
}
