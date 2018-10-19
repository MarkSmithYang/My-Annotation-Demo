package com.yb.annotation.handler;

import com.yb.annotation.anno.MinAge;
import com.yb.annotation.anno.MinAges;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author yangbiao
 * @Description:注解处理
 * @date 2018/10/10
 */
@Aspect
@Component
public class MinAgeHandler {
    public static final Logger log = LoggerFactory.getLogger(MinAgeHandler.class);

    //切入点签名,切入点表达式,可以指定包范围,可以指定注解,还可以两个一起用
    // 用@PointCut注解统一声明,然后在其它通知中引用该统一声明即可！
    // ---因为没有指定的注解去切,所以就这样的方式扩大范围
    @Pointcut(value = "execution(* com.yb.annotation.*..*.*(..))")
    public void minAgePointcut() {
    }

    @Around("minAgePointcut()")
    public Object dominAgeHandler(ProceedingJoinPoint joinPoint) throws Throwable {
        //强转(需要转成对应的Signature才能获取到对应的信息(字段,方法等)),需要根据Target来转换
        Signature sig = joinPoint.getSignature();
        ///实测证明,想要获取参数的注解,必须要能注解到方法,不然不会生效
        if (sig instanceof MethodSignature) {
            //强转为方法签名
            MethodSignature methodSignature = (MethodSignature) sig;
            //获取方法的参数的注解
            Method method = methodSignature.getMethod();
            //获取注解列表
            Annotation[][] parameterAnnotations = method.getParameterAnnotations();
            //获取方法的参数(值)
            Object[] args = joinPoint.getArgs();
            //判断参数列表是否是空 
            if (ArrayUtils.isNotEmpty(parameterAnnotations)) {
                //判断参数是否有注解
                for (int i = 0; i < parameterAnnotations.length; i++) {
                    Annotation[] annotations = parameterAnnotations[i];
                    for (Annotation annotation : annotations) {
                        //获取参数上的指定注解(需要做如下判断,只获取需要的)---注意需要首先判断有没有重复注解的存在
                        //处理多个注解的部分
                        if (annotation instanceof MinAges) {
                           log.info("处理重复注解的逻辑");
                            //注解要么有默认值,要么必填,所以取出的数组必然不会为空
                            MinAge[] values = ((MinAges) annotation).value();
                            //保险的做法还是判断所属关系,主要是担心@MinAges里面有除了@MinAge的注解,而导致异常错误
                            for (MinAge minAge : values) {
                                annoHandler(minAge, args[i]);
                            }
                        }
                        //处理单个注解的部分
                        if (annotation instanceof MinAge) {
                            log.info("处理单个注解的逻辑");
                            MinAge minAge = (MinAge) annotation;
                            annoHandler(minAge, args[i]);
                        }
                    }
                }
            }
        }
        //如果不执行这个方法返回Object,方法如果有返回值,那么返回只能是null,没返回值没有影响
        return joinPoint.proceed();
    }

    /**
     * 处理逻辑部分的代码抽取
     *
     * @param minAge
     * @param arg
     */
    private void annoHandler(MinAge minAge, Object arg) {
        String message = minAge.message();
        Integer value = minAge.value();
        if (arg instanceof Integer) {
            if ((Integer) arg < value) {
                throw new NoSuchFieldError(message + value);
            }
        }
    }

}
