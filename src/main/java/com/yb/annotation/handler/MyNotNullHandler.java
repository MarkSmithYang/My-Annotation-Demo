package com.yb.annotation.handler;

import com.yb.annotation.anno.MyNotNull;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.FieldSignature;
import org.aspectj.lang.reflect.MemberSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.assertj.core.internal.bytebuddy.implementation.bytecode.Throw;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author yangbiao
 * @Description:
 * @date 2018/10/18
 */
@Aspect
@Component
public class MyNotNullHandler {
    public static final Logger log = LoggerFactory.getLogger(MyNotNullHandler.class);

    private static final String[] types = {"java.lang.Integer", "java.lang.Double", "java.lang.Float",
            "java.lang.Long", "java.lang.Short", "java.lang.Byte", "java.lang.Boolean", "java.lang.Character",
            "java.lang.String", "int", "double", "long", "short", "byte", "boolean", "char", "float"};


    //切入点签名
    @Pointcut(value = "execution(* com.yb.annotation.*.*..*(..)) && @annotation(org.springframework.web.bind.annotation.PostMapping)")
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
            for (int i = 0; i < args.length; i++) {
                //获取参数的类型
                String typeName = args[i].getClass().getTypeName();
                for (int j = 0; j < types.length; j++) {
                    if(!args[i].equals(types[j])){
                        Field[] fields = args[i].getClass().getFields();
                    }
                }
            }
            MethodSignature memberSignature = (MethodSignature) sig;
            Method method = memberSignature.getMethod();
            Annotation[][] parameterAnnotations = method.getParameterAnnotations();
            if (ArrayUtils.isNotEmpty(parameterAnnotations)) {
                for (int i = 0; i < parameterAnnotations.length; i++) {
                    Annotation[] annotations = parameterAnnotations[i];
                    if (ArrayUtils.isNotEmpty(annotations)) {
                        for (int j = 0; j < annotations.length; j++) {
                            if (annotations[j] instanceof Valid) {
                                System.err.println("0000000");
                            }
                        }
                    }
                }
            }
            return joinPoint.proceed();
        } else {
            return joinPoint.proceed();
        }
    }

}
