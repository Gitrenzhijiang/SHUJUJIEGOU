package com.ren.algorithm;

public class Sy02 {

    public static void main(String[] args) {
        System.out.println(new Sy02().test(600000));
    }
    
    public int test(int n) {
        int res = 1;
        for (int i = n-1;i > 1;i--) {
            if (n % i == 0)
                res += i;
        }
        return res;
    }
    
}
