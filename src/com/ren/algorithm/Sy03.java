package com.ren.algorithm;

import java.util.ArrayList;
import java.util.List;

public class Sy03 {

    public static void main(String[] args) {
        System.out.println(new Sy03().test("abc"));
    }
    public String test (String gather) {
        StringBuffer sb = new StringBuffer("()");
        char[] cs = gather.toCharArray();
        for (int i = 1;i <= gather.length();i++) {
            test0(cs, sb, i, new ArrayList<>(), null);
        }
        return sb.toString();
    }
    public void test0(char[] cs, StringBuffer sb, int n, List<Character> lists, Character last) {
        if (n > 0) {
            boolean tag = true;
            if (last == null)
                tag = false;
            for (int i = 0;i < cs.length;i++) {
                if (tag == false) {
                    lists.add(cs[i]);
                    test0(cs, sb, n-1, lists, cs[i]);
                    lists.remove(new Character(cs[i]));
                }else if(cs[i] == last){
                    tag = false;
                }
            }
        }else {
            String temp = "";
            for (char tempc : lists) {
                temp += tempc;
            }
            sb.append(LEFT + temp + RIGHT);
        }
    }
    
    private static final String LEFT = "(";
    private static final String RIGHT = ")";
}
