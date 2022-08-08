package cn.itcast.product.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cacheable1 {
    String value() default "";
    String key() default "";

    // 泛型的Class类型
    Class<?> type() default Exception.class;
}
