package com.yb.annotation.model;

import com.yb.annotation.anno.Gender;
import com.yb.annotation.anno.MyNotNull;
import java.io.Serializable;

/**
 * @author yangbiao
 * @Description:注解测试类
 * @date 2018/10/10
 */
public class Teacher implements Serializable {
    private static final long serialVersionUID = 4233437079573079688L;

    /**
     * id
     */
    private String id;

    /**
     * 姓名
     */
    @MyNotNull
    private String name;

    /**
     * 年龄
     */
    @MyNotNull
    private Integer age;

    /**
     * 性别(班级改用)
     */
    @Gender({Gender.GenderType.FEMALE, Gender.GenderType.MALE})
    private String className;

    public Teacher() {
    }

    public Teacher(Integer age) {
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", className='" + className + '\'' +
                '}';
    }
}
