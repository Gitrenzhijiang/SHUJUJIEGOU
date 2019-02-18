package algorithm1;

import java.util.Random;

import util.TimeUtils;

/**
 * 最长回文子序列
 * @author renzhijiang
 * character   最长回文子序列为: carac
 */
public class Lpalindrome {
    public static void main(String[] args) {
        String text = "";
        // 初始化一个长的字符串
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0;i < 1000;i++) {
            sb.append(((char)('A'+r.nextInt(26))));
        }
        text = "UKINLSKTTK";
        text = sb.toString();
//        System.out.println(text);
        final int n = text.length();
        TimeUtils.start();
        String [][] ss = new String[n][n];
        System.out.println(lpl0(text, 0, n-1, ss));
        TimeUtils.over(true);
        TimeUtils.start();
        String result2 = lol1(text.toCharArray());
        System.out.println(result2);
        TimeUtils.over(true);
    }
    static String lol1(char[] src) {
        String[][] cs = new String[src.length][src.length];
        int n = src.length;
        for (int l = 1;l <= n;l++) {
            for (int i = 0;i < n-l+1;i++) {
                int j = i+l-1;
                // [i,j] 寻找最长的    [i+1, j-1]     [i,j-1]    [i+1,j]
                if (src[i] == src[j]) {
                    if (i == j) {
                        cs[i][j] = String.valueOf(src[i]);
                    }else if(j - i == 1) {
                        cs[i][j] = src[i] + "" + src[i];
                    }else {
                        cs[i][j] = src[i] + cs[i+1][j-1] + src[i];
                    }
                }else {
                    if (cs[i+1][j].length() > cs[i][j-1].length()) {
                        cs[i][j] = cs[i+1][j];
                    }else
                        cs[i][j] = cs[i][j-1];
                }
            }
        }
        return cs[0][n-1];
    }
    
    
    /**
     * 自顶向下带备忘录递归方式
     * @param src
     * @param left
     * @param right
     * @param ss 记录[i][j] 的最长回文子序列
     * @return
     */
    static String lpl0(String src, int left, int right, String[][]ss) {
        if (left > right)
            return "";
        if (left == right)
            return String.valueOf(src.charAt(left));
        if (ss[left][right] != null)
            return ss[left][right];
        
        String ret = "";
        if (src.charAt(left) == src.charAt(right)) {
            ret = src.charAt(left) + lpl0(src, left+1, right-1, ss) + src.charAt(left);
        }else {
            String r1 = lpl0(src, left+1, right, ss);
            String r2 = lpl0(src, left, right-1, ss);
            if (r1.length() > r2.length()) {
                ret = r1;
            }else {
                ret = r2;
            }
        }
        ss[left][right] = ret;
        return ret;
    }
}
