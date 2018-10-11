package com.yb.annotation.anno;

import java.lang.annotation.*;

/**
 * @author yangbiao
 * @Description:
 * @date 2018/10/10
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Gender {

    //创建枚举
    public enum GenderType{
        MALE("男"),
        FEMALE("女"),
        OTHER("中性");

        private String genderStr;

        //构造实例化枚举
        private GenderType(String genderStr) {
            this.genderStr=genderStr;
        }

        //这个暂时还不知其作用
        @Override
        public String toString() {
            return genderStr;
        }
    }
    //设置注解体(默认性别男)
    GenderType value() default GenderType.MALE;
}
