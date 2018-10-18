package com.yb.annotation.anno;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;

/**
 * @author yangbiao
 * @Description:允许@Age注解多个的注解
 * @date 2018/10/18
 */
//@Target(ElementType.ANNOTATION_TYPE)
@Documented
@Target({PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Ages {

    Age[] value();
}
