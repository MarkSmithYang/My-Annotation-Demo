package com.yb.annotation;

import com.google.common.base.Splitter;
import com.yb.annotation.controller.TeacherController;
import com.yb.annotation.model.Teacher;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.List;
import java.util.Spliterator;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AnnotationApplicationTests {

    @Autowired
    private TeacherController teacherController;

    @Test
    public void contextLoads2() {
        List<Teacher> list = teacherController.findAll();
        System.err.println(list);
    }

    @Test
    public void contextLoads1() {
        Teacher teacher = teacherController.findById("1");
        System.err.println(teacher==null?null:teacher.toString());
    }

    @Test
    public void contextLoads() {
        Teacher teacher = new Teacher();
        teacher.setAge(19);
        teacher.setClassName("搞笑一班");
        teacher.setId("222");
        teacher.setName("张老师");
        String s = teacherController.addTeacher(teacher);
        System.err.println(s);
    }

    @Test
    public void contextLoadsTest() {
        String str =" 四川省,成都市,马关 路区 ";
        List<String> list = Splitter.on(", ").trimResults().omitEmptyStrings().splitToList(str);
        Iterable<String> split = Splitter.on(",").split(str);
        Iterable<String> split1 = Splitter.on(",").trimResults().split(str);
        System.err.println("0--"+list);
        System.err.println("s0--"+split);
        System.err.println("s1--"+split1);
    }

    @Test
    public void contextLoadsTest1() {
        String str =" 四川省, 成都市,马关 路区 ";
        String[] split = StringUtils.split(str.trim(), ",");
        System.err.println(Arrays.asList(split));
        String join = StringUtils.join(split, "");
        System.err.println(join);
    }

}
