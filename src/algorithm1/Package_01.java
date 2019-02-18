package algorithm1;

import java.util.ArrayList;
import java.util.List;

/**
 * 0-1 背包问题(贪心算法无法解决)
 * 贪心算法只能解决分数背包问题
 * @author renzhijiang
 * 
 */
public class Package_01 {
    /**
     * 动态规划解决方案
     * @param args
     */
    public static void main(String[] args) {
        // 背包可以放的重量
        int W = 50;
        int n = 3; //商品数量
        int v[] = new int[n]; // v[i] 表示第i个商品的价值
        v[0] = 60; v[1] = 100; v[2] = 120;
        int w[] = new int[n]; // w[i] 表示第i个商品的重量
        w[0] = 10; w[1] = 20; w[2] = 30;
        List<Integer> list = new ArrayList<>();
        System.out.println(p_01(v, w, list, 0, 0, W, n-1));
        System.out.println(list);
    }
    
    private static int p_01(int[] v, int[] w, List<Integer> add, int curIndex, int curW, int maxW, int maxN) {
        
        if (curIndex > maxN)
            return 0;
        // 如果当前的容量已经装不下当前需要装的东西, 只有一种子问题:不装入之后, 的最大收益
        if (w[curIndex]+curW > maxW) {
            return p_01(v, w, add, curIndex+1, curW, maxW, maxN);
        }
        // 此时, 对于curIndex 的商品, 有两种选择, 
        int ret1 = p_01(v, w, add, curIndex+1, curW+w[curIndex], maxW, maxN);
        int ret2 = p_01(v, w, add, curIndex+1, curW, maxW, maxN);
        if (ret1 + v[curIndex] > ret2) {
            if(!add.contains(curIndex))
                add.add(curIndex);
            return (ret1 + v[curIndex]);
        }else {
            return ret2;
        }
    }
    
}
