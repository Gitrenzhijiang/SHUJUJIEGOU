package annotation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
public class RunTests {
    public static void main(String[] args) throws Exception {
        //testSample();
        testSample2();
    }
    //测试的方法在抛出 ArithmeticException 及其 子类异常时通过测试
    static void testSample2() throws ClassNotFoundException {
        int tests = 0;
        int passed = 0;
        String className = "annotation.Sample2";
        Class<?> testClass = Class.forName(className);
        for (Method m : testClass.getDeclaredMethods()) {
            // 指定类型的注解是否存在与此元素上面
            if (!m.isAnnotationPresent(ExceptionTest.class)) {
                continue;
            }
            tests++;
            try {
                m.invoke(null);
                System.out.println(m + ", failed : " + "no exception!!!");
            }catch(InvocationTargetException e) {
                Throwable exc = e.getCause();
                // 获取到注解的value方法返回的值
                Class<? extends Exception> excType = 
                        m.getAnnotation(ExceptionTest.class).value();
                if (excType.isInstance(exc)) {
                    passed++;
                }else {
                    System.out.println(m + ", failed:" + e.getMessage());
                }
            }catch(Exception e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("tests:" + tests + ", " + "passed:" + passed);
    }
    // 注解的方法必须是public static 和 无参数的
    static void testSample() throws ClassNotFoundException {
        int tests = 0;
        int passed = 0;
        String className = "annotation.Sample";
        Class<?> testClass = Class.forName(className);
        for (Method m : testClass.getDeclaredMethods()) {
            // 指定类型的注解是否存在与此元素上面
            if (!m.isAnnotationPresent(Test.class)) {
                continue;
            }
            tests++;
            try {
                m.invoke(null);
                // 无参静态方法通过
                passed++;
            }catch(Exception e) {
                System.out.println(m + " failed: " + e.getMessage());
            }
        }
        System.out.println("tests:" + tests + ", " + "passed:" + passed);
    }
}
