package com.ren.algorithm;
/**
 * 数字计数问题
 * @author renzhijiang
 * 
 */
public class NumberCount {
    
    public static void main(String[] args) {
        NumberCount nc = new NumberCount();
        int testNumber = 56000010;
        for (int i = 0;i < 10;i++) {
            System.out.println(nc.numberCount(testNumber, i));
        }
    }
    /**
     * 一个用于没有规律时的计算numberCount的方法
     * @param n
     * @param countNum
     * @return
     */
    public int numberCount(int n, int countNum) {
        int width = getWidth(n);
        int count = 0;
        for (int i = width;i > 0;i--) {
            int start = (int) Math.pow(10, i-1);
            int end = (int) (i == width? n : (Math.pow(10, i)-1));
            // 从最高位开始计算, 排列中组合的数量计算
            int temp = 0;
            for (int j = 0;j < i;j++) {
                int start_j = getW(start, j);
                int n_j = getW(end, j);
                if (start_j <= countNum && n_j >= countNum) {
                    if (n_j == countNum) {
                        // 特殊情况
                        count += ((start / (Math.pow(10, j))) * temp);
                        if (j == i-1) {
                            count += 1;
                        }else {
                            int k1 = getOnBefore(start, j+1);
                            int k2 = getOnBefore(end, j+1);
                           
                            count += (k2-k1+1);
                        }
                        
                    }else
                        count += ((start / (Math.pow(10, j))) * (temp+1));
                }else {
                    // temp = 2
                    if (temp > 0)
                        count += ((start / (Math.pow(10, j))) * (temp));
                }
                temp = temp * 10  + (n_j - start_j);
            }
        }
        return count;
    }
    // 返回数的位数
    private int getWidth(int n) {
        int res = 0;
        while (n != 0) {
            n = n / 10;
            res++;
        }
        return res == 0? 1 : res;
    }
    // 返回数的第i位数字形式
    private int getW(int n, int i) {
        String str = String.valueOf(n);
        str = str.substring(i, i+1);
        return Integer.parseInt(str);
    }
    
    private int getOnBefore(int n, int before) {
        String str = String.valueOf(n);
        str = str.substring(before);
        return Integer.parseInt(str); 
    }

}
