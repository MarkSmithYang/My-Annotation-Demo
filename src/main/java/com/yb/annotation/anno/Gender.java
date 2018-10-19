package com.yb.annotation.anno;

import java.lang.annotation.*;

/**
 * @author yangbiao
 * @Description:自定义注解Gender--指定注解的字段的性别
 * @date 2018/10/10
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Gender {
    //设置注解体(默认性别男)
    //把注解的参数值设定为自己定义的枚举,让使用者选择自己定义的枚举
    GenderType[] value();

    //创建枚举
    public enum GenderType {
        /**
         * 男
         */
        MALE("男"),
        /**
         * 女
         */
        FEMALE("女"),
        /**
         * 人妖
         */
        OTHER("人妖");

        private String genderStr;

        //构造实例化枚举
        private GenderType(String genderStr) {
            this.genderStr = genderStr;
        }

        //这用于获取枚举值
        @Override
        public String toString() {
            return genderStr;
        }
    }
}
