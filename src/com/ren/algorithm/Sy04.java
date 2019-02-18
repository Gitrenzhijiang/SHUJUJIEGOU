package com.ren.algorithm;

import java.util.ArrayList;
import java.util.List;

public class Sy04 {

    public static void main(String[] args) {
        int n = 3;
        List<String> res = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        new Sy04().test0(res, list, n, n);
        for (String ps:res) {
            System.out.println(ps);
        }
    }
    public void test0(List<String> res, List<Integer> list, int counter, int number) {
        if (counter == 0) {
            StringBuffer sb = new StringBuffer();
            for (int temp:list) {
                sb.append(temp + " ");
            }
            res.add(sb.toString().trim());
        }else {
            for (int i = number;i > 0;i--) {
                if (list.contains(i))
                    continue;
                list.add(i);
                test0(res, list, counter-1, number);
                list.remove(new Integer(i));
            }
        }
    }

}
