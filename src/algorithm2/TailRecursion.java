package algorithm2;

import java.math.BigInteger;

import util.TimeUtils;

/**
 * 尾递归实例
 * @author renzhijiang
 *
 */
public class TailRecursion {

    public static void main(String[] args) throws Exception {
        int n = 1230;
        TimeUtils.start();
        jc_tr(n, 1);
        TimeUtils.over(true);
        TimeUtils.start();
        jc(n);
        TimeUtils.over(true);
    }
    public static long jc_tr(int n, long res) {
        if (n == 1) {
            return res;
        }else {
            return jc_tr(n-1, res * n);
        }
    }
    // 普通递归
    public static long jc(int n) {
        if (n == 1) {
            return 1;
        }else {
            return jc(n-1) * n;
        }
    }
}
