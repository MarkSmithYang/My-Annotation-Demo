package com.yb.annotation;

import com.yb.annotation.model.Teacher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AnnotationApplicationTests {

    @Test
    public void contextLoads() {
        Teacher teacher = new Teacher();
        System.err.println(teacher.toString());
    }

}
