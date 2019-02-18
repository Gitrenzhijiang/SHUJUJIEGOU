package algorithm1;
/*
 * 算法书第3章 第83页
 * 3.4
 */
public class G3_4 {

    public static void main(String[] args) {
        int r[] = {1, 5, 2, 10, 3};
        int res[] = new int[r.length];
        System.out.println(new G3_4().g34(r, res, r.length-1)[1]);
    }
    // [0..index] 的最佳安排方案
    public int[] g34(int[] r, int[] res, int index) {
        if (index == 0) {
            return new int[] {r[0], 0, r[0]};
        }
        int t[] = g34(r, res, index - 1);
        if (t[0] > t[1]) {
            t[1] += r[index];
        }else {
            t[0] += r[index];
        }
        t[2] = Math.abs(t[0] - t[1]);
        return t;
    }
}
