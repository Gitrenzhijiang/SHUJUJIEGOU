package algorithm1;


/**
 * 及其每天接受大量加工任务, 第i天需要加工的任务数为:xi, 随着机器连续运行时间的增加, 处理能力越来越低,
 * 需要花1天时间对机器进行检修, 以提高处理能力, 检修当天必须停工, 重启后的第i天能够加工的任务数是si, 
 * 且满足s1>s2>s3>...>sn>0
 * 给定x1,x2,x3和s1,s2,s3,...,sn, 如何安排机器检修时间, 以使得在n天所完成的任务最大
 * @author renzhijiang
 *
 */
public class G3_15 {

    public static void main(String[] args) {
        G3_15 g315 = new G3_15();
        int s[] = {-1, 9, 7, 5, 2, 1};
        g315.g1(s);
    }
    /**
     * 
     * @param s 机器重启后第i天加工任务数量
     */
    public void g1(int s[]) {
        //加工的天数
        int n = s.length - 1;
        // dp[i][j] : 第i-n 之内(i>=1), 从s[j]开始最大完成任务数
        int dp[][] = new int [s.length][s.length];
        for (int i = 1;i < s.length;i++) {
            dp[n][i] = s[i];
        }
        // dp[i][j] = 第i天重启:dp[i+1][1] | dp[i+1][j+1] + s[j]
        for (int i = n-1;i >= 1;i--) {
            for (int j = 1;j < n;j++) {
                dp[i][j] = dp[i+1][1] > dp[i+1][j+1] + s[j]?
                        dp[i+1][1]:dp[i+1][j+1] + s[j];
            }
        }
        System.out.println(dp[1][1]);
        //追溯解的过程
        // dp[1][1]  dp[2][1] | dp[2][2]+s[1]
        int x = 1, y = 1;
        while (x < n) {
            if (dp[x][y] == dp[x+1][y+1] + s[y]) {
                // 第x天没有重启
                System.out.print(x + "= 0 ");
                y = y+1;
            }else {
                System.out.print(x + "= 1 ");
                y = 1;
            }
            x++;
            
            if (x == n ) {
                System.out.print(x + "= 0 ");
                break;
            }
        }
        System.out.println();
        for (int i = 0;i < dp.length;i++) {
            for (int j = 0;j < dp.length;j++) {
                if (dp[i][j] < 10) {
                    System.out.print("0" + dp[i][j] + " ");
                }else
                    System.out.print(dp[i][j] + " ");
            }
            System.out.println();
        }
            
    }
    
}
