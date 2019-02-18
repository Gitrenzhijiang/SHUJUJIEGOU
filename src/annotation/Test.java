package annotation;
import java.lang.annotation.*;

/**
 * 注解方法是一个测试方法
 * 只能使用无参的静态方法
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Test {
    
}
