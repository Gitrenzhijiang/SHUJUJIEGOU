package algorithm1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.TimeUtils;

/**
 * aa=ab=bb=b
 * ac=bc=ca=a
 * ba=cb=cc=c
 * @author renzhijiang
 *
 */
public class G3_18 {

    public static void main(String[] args) {
        String str = "aaaaaaaaaaaaaaaccccccccccccbbbbbbbbbbbbbbbbbbbb";
        TimeUtils.start();
        List<String> list = new G3_18().hasa(str, new HashMap<>());
        if (list.contains("a")) {
            System.out.println("1");
        }else {
            System.out.println("0");
        }
        TimeUtils.over(true);
        TimeUtils.start();
        System.out.println("动态规划:" + new G3_18().hasa_dy(str));
        TimeUtils.over(true);
    }
    // bbbba   0 1 2 3 4 回溯法 而后加入dp,该成了自上而下的动态规划
    public List<String> hasa(String str, Map<String, List<String>> dp) {
        List<String> res = new ArrayList<>();
        if (str.length() == 1) {
            res.add(str);
            return res;
        }else if (str.length() == 2) {
            res.add(GZ.get(str));
            return res;
        }
        if (dp.containsKey(str)) {
            return dp.get(str);
        }
        // start <= k < end
        for (int k = 0;k < str.length()-1;k++) {
            List<String> t_l = hasa(str.substring(0, k+1), dp);
            List<String> t_r = hasa(str.substring(k+1), dp);
            for (String l:t_l) {
                for (String r:t_r) {
                    String s = GZ.get(l+r);
                    if (!res.contains(s))
                        res.add(s);
                }
            }
        }
        dp.put(str, res);
        return res;
    }
    // 使用动态规划求解, dp[i][j] 表示i-->j所有可能的结果
    // 0 1 2 3 0-0 0-1
    public int hasa_dy(String str) {
        List<String> dp[][] = new List[str.length()][str.length()];
        //初始化一些单个字符的情况
        for (int i = 0;i < str.length();i++) {
            List<String> tlist = new ArrayList<>();
            tlist.add(str.charAt(i)+"");
            dp[i][i] = tlist;
        }
        for (int len = 2;len <= str.length();len++) {
            for (int i = 0;i <= str.length() - len;i++) {
                int j = i + len - 1;
                List<String> res = new ArrayList<>();
                if (j - i == 1) {
                    res.add(GZ.get(str.substring(i, j+1)));
                    dp[i][j] = res;
                    continue;
                }
                // [i,k] [k+1,j]
                for (int k = i;k < j;k++) {
                    List<String> pre = dp[i][k];
                    List<String> last = dp[k+1][j];
                    for (String p_str:pre) {
                        for (String l_str:last) {
                            if (!res.contains(GZ.get(p_str+l_str)))
                                res.add(GZ.get(p_str+l_str));
                        }
                    }
                }
                dp[i][j] = res;
            }
        }
        if (dp[0][str.length()-1].contains("a")) {
            return 1;
        }else
            return 0;
    }
    
    private static Map<String, String> GZ = new HashMap<>();
    static {
        GZ.put("aa", "b");GZ.put("ab", "b");GZ.put("bb", "b");
        GZ.put("ac", "a");GZ.put("bc", "a");GZ.put("ca", "a");
        GZ.put("ba", "c");GZ.put("cb", "c");GZ.put("cc", "c");
    }
}
