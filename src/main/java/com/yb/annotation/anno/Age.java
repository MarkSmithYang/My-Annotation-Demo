package com.yb.annotation.anno;


import java.lang.annotation.*;

/**
 * @author yangbiao
 * @Description:为属性注入值的自定义注解
 * @date 2018/10/10
 */
@Documented
//@Target({ METHOD,PARAMETER})
@Target({ElementType.PARAMETER,ElementType.METHOD})//实测证明,想要获取参数的注解,必须要能注解到方法,不然不会生效
@Retention(RetentionPolicy.RUNTIME)
@Inherited//此注解允许被使用类的子类继承(实现接口和重载方法不遵循此)
public @interface Age {
    int value();
    String message() default "";
}
