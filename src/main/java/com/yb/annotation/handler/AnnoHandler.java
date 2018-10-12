package com.yb.annotation.handler;

import com.yb.annotation.anno.SetVaule;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.FieldSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * @author yangbiao
 * @Description:注解处理
 * @date 2018/10/10
 */
@Aspect
@Component
public class AnnoHandler {
    public static final Logger log = LoggerFactory.getLogger(AnnoHandler.class);

    // 用@PointCut注解统一声明,然后在其它通知中引用该统一声明即可！
//    @Pointcut("@annotation(com.yb.annotation.anno.SetVaule)")
    @Pointcut(value = "execution(* com.yb.annotation.model..*.*(..)) && @annotation(com.yb.annotation.anno.SetVaule)")
    public void setVaulePointcut() {
    }

    @Around("setVaulePointcut()")
    public Object doSetVaule(ProceedingJoinPoint joinPoint) throws Throwable {
        //signature--->签名
        String className = joinPoint.getTarget().getClass().getName();
        log.info("className::" + className);
        //这个应该是注解到什么上就是什么的名字
        String fieldName = joinPoint.getSignature().getName();
        log.info("fieldName::" + fieldName);
        //
        Signature sig = joinPoint.getSignature();
        if (!(sig instanceof FieldSignature)) {
            throw new NoSuchFieldException("This annotation is only valid on a field");
        }
        //强转(需要转成对应的Signature才能获取到对应的信息(字段,方法等))
        FieldSignature fieldSignature = (FieldSignature) sig;
        Field field = fieldSignature.getField();
        Class fieldType = fieldSignature.getFieldType();
        String name = fieldSignature.getName();
        log.info("--" + field + "--" + fieldType + "--" + name);
        //获取字段
        Field fie = joinPoint.getTarget().getClass().getField(fieldSignature.getName());
        Field[] fields = joinPoint.getTarget().getClass().getFields();
        boolean notEmpty = ArrayUtils.isNotEmpty(fields);
        if (notEmpty) {
            Arrays.asList(fields).forEach(s -> {
                //通过字段获取字段上的对应的注解(可以获取这个字段上的所有注解,也可以获取想要的注解,
                // 只需传入对应的注解类的class(字节码))
                SetVaule setVaule = s.getAnnotation(SetVaule.class);
                //获取注解的参数(值)
                String value = setVaule.value();
                if (StringUtils.isNotBlank(value)) {
                    try {
                        s.set(setVaule, value);
                    } catch (IllegalAccessException e) {
                        log.info("给字段赋值失败");
                        e.printStackTrace();
                    }
                }
            });
        }
        return joinPoint.proceed();
    }

}
