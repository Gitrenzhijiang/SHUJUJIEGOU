package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
// 当前注解在运行时期保留
@Retention(RetentionPolicy.RUNTIME)
// 声明注解的类型, 当前注解只能用在方法上面
@Target(ElementType.METHOD)
public @interface ExceptionTest {
    // 注解括号内的参数传递
    Class<? extends Exception> value();
}
