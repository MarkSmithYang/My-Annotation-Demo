package com.yb.annotation;

import java.util.Optional;

/**
 * @author yangbiao
 * @Description:
 * @date 2018/10/17
 */
@FunctionalInterface
public interface LambdaTest<T> {
    Optional<Integer> get(T object);
}
