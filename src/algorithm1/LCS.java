package algorithm1;

import util.TimeUtils;

/**
 * 最长公共子序列(longest-common-subsequeuece)
 * @author renzhijiang
 * 例如: X=<A, B, C, B, D, A, B>  Y=<B, D, C, A, B, A>
 * <B, C, B, A> 和 <B, C, A, B> 是X 和 Y 的最长公共子序列
 */
public class LCS {

    public static void main(String[] args) {
        String x = "10010101";
        String y = "010110110";
        
        TimeUtils.start();
        System.out.println(lcs_1(x, y)); // 136ms
        TimeUtils.over(true);
        
    }
    public static String lcs_1(String x, String y) {
        if (x.length() == 0 || y.length() == 0) {
            return "";
        }
        String[][] ss = new String[x.length()][y.length()];
        String ret =  lcs_1_0(x, y, x.length()-1, y.length()-1, ss);
        for (int i = 0;i < ss.length;i++) {
            for (int j = 0;j < ss[i].length;j++) {
                System.out.print("[" + ss[i][j] + "]");
            }
            System.out.println();
        }
        return ret;
    }
    /**
     * 带备忘录的自顶向下的递归算法可以保证只计算要用到的子问题
     * @param x
     * @param y
     * @param cx
     * @param cy
     * @param ss
     * @return
     */
    static String lcs_1_0(String x, String y, int cx, int cy, String[][] ss) {
        if (cx < 0 || cy < 0) {
            return "";
        }
        if (ss[cx][cy] != null)
            return ss[cx][cy];
        if (x.charAt(cx) == y.charAt(cy)) {
            // 接下来的最长公共子序列为
            ss[cx][cy] = lcs_1_0(x, y, cx-1, cy-1, ss) + x.charAt(cx);
            return ss[cx][cy];
        }
        String r1 = lcs_1_0(x, y, cx-1, cy, ss);
        String r2 = lcs_1_0(x, y, cx, cy-1, ss);
        if (r1.length() > r2.length()) {
            ss[cx][cy] = r1;
        }else {
            ss[cx][cy] = r2;
        }
            
        return ss[cx][cy];
    }
    
}
