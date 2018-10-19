package com.yb.annotation.handler;

import com.yb.annotation.anno.Gender;
import com.yb.annotation.anno.MyNotNull;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.FieldSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * @author yangbiao
 * @Description:@MyNotNull注解的逻辑处理类
 * @date 2018/10/18
 */
@Aspect
@Component
public class MyAnnoHandler {
    public static final Logger log = LoggerFactory.getLogger(MyAnnoHandler.class);

    //切入点签名,切入点表达式,可以指定包范围,可以指定注解,还可以两个一起用
    @Pointcut(value = "execution(* com.yb.annotation.controller.*..*(..))")
    public void myNotNullPointcut() {
    }

    @Around(value = "myNotNullPointcut()")
    public Object myNotNullAround(ProceedingJoinPoint joinPoint) throws Throwable {
        //获取签名
        Signature sig = joinPoint.getSignature();
        //判断签名是否属性指定的签名类型--->这里我只处理方法的签名
        if (sig instanceof MethodSignature) {
            //这里获取的就只有方法的参数了
            Object[] args = joinPoint.getArgs();
            for (Object object : args) {
                //这包括公共的、受保护的、默认的(包)访问和私有字段，但排除继承字段。
                Field[] declaredFields = object.getClass().getDeclaredFields();
                for (Field field : declaredFields) {
                    //获取属性字段上的指注解
                    MyNotNull annotation = field.getAnnotation(MyNotNull.class);
                    //如果属性字段上有指定注解,就获取字段值进行逻辑处理
                    if (annotation != null) {
                        //获取字段值-->开启暴力反射,就是设置访问权限
                        field.setAccessible(true);
                        //获属性字段的值
                        Object obj = field.get(object);
                        //判断属性字段的值是否为空
                        if (obj == null || StringUtils.isBlank(obj.toString())) {
                            //属性字段为空,提示用户-->(常用的是抛出异常)
                            //这个异常是随便抛的,与这里的情况不要符合
                            throw new NoSuchFieldError("属性字段" + field.getName() + annotation.msg());
                        }
                    }
                    //处理第二个指定注解的逻辑
                    Gender gender = field.getAnnotation(Gender.class);
                    if (gender != null) {
                        //开启暴力反射
                        field.setAccessible(true);
                        //获取字段值
                        Object obj = field.get(object);
                        //获取注解参数值
                        Gender.GenderType[] values = gender.value();
                        //因为values的类型不是String类型的,所以需要处理一下,不然下面的contains会有问题
                        List<String> list = Lists.newArrayList();
                        for (Gender.GenderType genderType : values) {
                            list.add(genderType.toString());
                        }
                        //处理相关的逻辑-->values不可能为空
                        if (obj == null || !list.contains(obj.toString())) {
                            throw new NoSuchFieldError("属性字段" + field.getName() + "性别有误,属性字段值和注解指定不匹配");
                        }
                    }
                }
            }
        }
        return joinPoint.proceed();
    }
}
