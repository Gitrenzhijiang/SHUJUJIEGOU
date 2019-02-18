package algorithm1;
/*
 * 给定两个序列S1 S2,通过一系列的编辑(插入, 删除, 替换)操作, 将S1转变为S2, 完成这种
 * 操作所需要的最少编辑操作个数称为S1 S2的编辑距离
 * 思路:s1  [0..i]   s2[0..j]
 * c[i,j]表示 s1[0,i]和s2[0,j]的编辑距离
 * s1的最后一个元素被删除,  c[i,j] = c[i-1,j] + 1
 * s1的最后加上s2的最后一个元素, c[i,j] = c[i, j-1] + 1
 * s1的最后一个元素被s2的最后一个元素所替换: c[i,j] = c[i-1, j-1] + (s1[i] == s2[j]?0:1)
 */
public class String2String {
    
    public static void main(String[] args) {
        String s1 = "vintner";
        String s2 = "writers";
        System.out.println(new String2String().minDistance(s1, s2));
    }
    public int minDistance(String s1, String s2) {
        if (s1.length() == 0) {
            return s2.length();
        }else if(s2.length() == 0) {
            return s1.length();
        }
        int[][] c = new int[s1.length()+1][s2.length()+1];
        // 初始化 c
        for (int i = 0;i < s1.length()+1;i++) {
            c[i][0] = i;
        }
        for (int i = 0;i < s2.length()+1;i++) {
            c[0][i] = i;
        }
        for (int i = 0;i < s1.length()+1;i++) {
            for (int j = 0;j < s2.length()+1;j++) {
                //不需要重复赋值计算
                if (i == 0 || j == 0) {
                    continue;
                }
                int min = Integer.MAX_VALUE;
                if (s1.charAt(i-1) == s2.charAt(j-1)) {
                    min = c[i-1][j-1];
                }else {
                    //不等的时候, 在s1最后删除, 插入
                    int tmin = c[i-1][j];
                    if (c[i-1][j] > c[i][j-1]) {
                        tmin = c[i][j-1];
                    }
                    // s1最后一个元素替换成s2最后一个元素
                    if (tmin > c[i-1][j-1]) {
                        tmin = c[i-1][j-1];
                    }
                    min = tmin + 1;
                }
                c[i][j] = min;
            }
        }
        return c[s1.length()][s2.length()];
    }
    
}
