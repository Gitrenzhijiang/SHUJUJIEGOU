package annotation;

public class Sample2 {
    
    // ArithmeticException: 当出现异常的运算条件时，抛出此异常。
    // 例如，一个整数"除以零"时，抛出此类的一个实例
    @ExceptionTest(ArithmeticException.class)
    public static void m1() {
        int i = 0;
        i = i / i;
    }
    
    @ExceptionTest(ArithmeticException.class)
    public static void m2() {
        int[] a = new int[0];
        int i = a[1];
    }
    
    @ExceptionTest(ArithmeticException.class)
    public static void m3() {
        // should fail (no exception)
    }
}
