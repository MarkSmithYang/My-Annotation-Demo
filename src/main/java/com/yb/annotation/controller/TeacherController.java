package com.yb.annotation.controller;

import com.yb.annotation.anno.MinAge;
import com.yb.annotation.anno.MinAges;
import com.yb.annotation.model.Teacher;
import com.yb.annotation.service.TeacherService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * @author yangbiao
 * @Description:自定义注解demo工程的控制层
 * @date 2018/10/12
 */
@RestController
@CrossOrigin
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @PostMapping("aa")
    public String addTeacher(Teacher teacher,int a) {
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
    public Teacher findById(String id, @MinAges({@MinAge(value = 10)}) Integer age) {
        Teacher result = teacherService.findById(id);
        return result;
    }

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
