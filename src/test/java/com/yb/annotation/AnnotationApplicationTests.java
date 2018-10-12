package com.yb.annotation;

import com.yb.annotation.controller.TeacherController;
import com.yb.annotation.model.Teacher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.annotation.Target;
import java.util.List;

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

}
