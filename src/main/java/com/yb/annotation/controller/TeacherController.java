package com.yb.annotation.controller;

import com.yb.annotation.anno.Age;
import com.yb.annotation.anno.Ages;
import com.yb.annotation.anno.MyNotNull;
import com.yb.annotation.model.Teacher;
import com.yb.annotation.service.TeacherService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.lang.annotation.*;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author yangbiao
 * @Description:
 * @date 2018/10/12
 */
@RestController
@CrossOrigin
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @PostMapping("aa")
    public String addTeacher(@Valid Teacher teacher) {
        System.err.println(teacher);
        //新增
        String result = teacherService.addTeacher(teacher);
        return result;
    }

    @GetMapping("queryForList")
    public List<Teacher> queryForList() {
        List<Teacher> result = teacherService.queryForList();
        return result;
    }

    @GetMapping("bb")
    public Teacher findById(String id, @Age(value = 10, message = "年龄不能小于10岁") Integer age) {
        Teacher result = teacherService.findById(id);
        return result;
    }

//    @GetMapping("bb")
//    @Age(value = 122, message = "年龄不能小于122")
//    @Ages(value = {@Age(value = 10, message = "年龄不能小于10岁"), @Age(value = 30, message = "年龄不能小于30")})
//    public Teacher findById(String id, @Ages(value = {@Age(value = 10, message = "年龄不能小于10岁"),
//            @Age(value = 30, message = "年龄不能小于30")}) @Age(value = 1, message = "年龄不能小于1岁") Integer age) {
//        Teacher result = teacherService.findById(id);
//        return result;
//    }

    @GetMapping("bbObject")
    public Teacher bbObject(String id) {
        Teacher result = teacherService.bbObject(id);
        return result;
    }

    @GetMapping("bbMap")
    public Map<String, Object> findByIdMap(String id) {
        Map<String, Object> result = teacherService.findByIdMap(id);
        return result;
    }

    @GetMapping("cc")
    public List<Teacher> findAll() {
        List<Teacher> result = teacherService.findAll();
        return result;
    }

    @DeleteMapping("dd")
    public String deleteById(String id) {
        if (StringUtils.isBlank(id)) {
            throw new NoSuchFieldError("id不能为空");
        }
        String result = teacherService.deleteById(id);
        return result;
    }
}
