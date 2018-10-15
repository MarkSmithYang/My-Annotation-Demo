package com.yb.annotation.anno;


import java.lang.annotation.*;

/**
 * @author yangbiao
 * @Description:为属性注入值的自定义注解
 * @date 2018/10/10
 */
@Documented
@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited//此注解允许被使用类的子类继承(实现接口和重载方法不遵循此)
public @interface Age {
    int value();
//    String value() default "";
    String message();
}
