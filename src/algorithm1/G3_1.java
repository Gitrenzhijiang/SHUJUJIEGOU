package algorithm1;

import java.util.ArrayList;
import java.util.List;

/**
 * max g1(x1) + g2(x2) + g3(x3)
 * x1^2 + x2^2 +x3^2 <= 10
 * 
 */
public class G3_1 {
    
    public static void main(String[] args) {
        int gg[][] = { // gg[x][g1]
                {2, 5, 8}, 
                {4, 10, 12},
                {7, 16, 17},
                {11, 20, 22}
        };
        List<Abc> res = new ArrayList<>();
        new G3_1().g123(0, gg, new ArrayList<>(), 0, 0, res);
        for (Abc abc : res) {
            System.out.println(abc);
        }
        System.out.println(Abc.max + ":" + Abc.maxValue); // 22:37
        System.out.println(new G3_1().gg1232(gg, 2, 10, new Integer[gg.length][100]));
    }
    public void g123(int count, int[][]gg, List<Integer> res, int curValue, int curGGvalue, List<Abc> list) {
        if (count == 3) {
            Abc abc = new Abc();
            abc.x1 = res.get(0);
            abc.x2 = res.get(1);
            abc.x3 = res.get(2);
            abc.value = curGGvalue;
            list.add(abc);
            if (Abc.maxValue < curGGvalue) {
                Abc.maxValue = curGGvalue;
                Abc.max = list.size()-1;
            }
            return;
        }
        for (int i = 0;i < 4;i++) {
            if (curValue + i * i > 10) {
                continue;
            }
            res.add(i);
            g123(count + 1, gg, res, curValue + i * i, curGGvalue + gg[i][count], list);
            res.remove(new Integer(i));
        }
    }
    
    
    private static class Abc{
        static int max = 0, maxValue = Integer.MIN_VALUE;
        int x1, x2, x3, value;
        
        @Override
        public String toString() {
            return "Abc [x1=" + x1 + ", x2=" + x2 + ", x3=" + x3 + ", value=" + value + "]";
        }
        
    }
    //  [0..i] 当前剩余max 的 x?^2之和
    public int gg1232(int gg[][], int i, int max, Integer[][]buff) {
        if (i < 0) {
            return 0;
        }else if(buff[i][max] != null) {
            System.out.println("hello");
            return buff[i][max];
        }else {
            int mymax = -1;
            for (int m = 0;m < 4;m++) {
                if (m * m > max) {
                    continue;
                }
                int temp = gg1232(gg, i - 1, max - m * m, buff) + gg[m][i];
                if (temp > mymax) {
                    mymax = temp;
                }
            }
            buff[i][max] = mymax;
            return mymax;
        }
        
    }
}
