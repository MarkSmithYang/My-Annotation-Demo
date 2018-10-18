package com.yb.annotation.anno;

import javax.validation.Constraint;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;

/**
 * @author yangbiao
 * @Description:自定义注解--不能为空注解
 * @date 2018/10/18
 */
@Documented
@Constraint(validatedBy = {})//校验
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MyNotNull {

    String msg() default "不能为空";
}
