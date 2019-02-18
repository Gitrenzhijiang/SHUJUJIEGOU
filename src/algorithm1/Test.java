package algorithm1;

import java.util.Arrays;
import java.util.Random;

public class Test extends Super implements A, B{

    public static void main(String[] args) {
       
        Super test = new Test();
        test.ovmethod();
        
        System.out.println(int[].class.getSimpleName());
        int[] digits = { 3, 1, 4, 1, 5, 9, 2, 6, 5, 4 };
        System.out.println(Arrays.asList(digits));
        
        int n = 2 * (Integer.MAX_VALUE / 3);
        int low = 0;
        for (int i = 0; i < 1000000; i++) {
            if (random(n) < n/2) {
                low++;
            }
        }
        System.out.println(low);
    }
    private static final Random rnd = new Random();
    public static int random(int n) {
        return Math.abs(rnd.nextInt()) % n;
    }
    
    
    @Override
    public void method() {
       System.out.println("haha");
        
    }

    @Override
    void ovmethod() {
        System.out.println("..");
    }

}
class Super{
    public Super() {
        System.out.println("super...");
        ovmethod();
        System.out.println("super...");
    }
    void ovmethod() {
        System.out.println("ov in super");
    }
}
interface A {
    void method();
}
interface B{
    void method();
}
