package com.yb.annotation.controller;

import com.yb.annotation.anno.Age;
import com.yb.annotation.model.Teacher;
import com.yb.annotation.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;
import java.util.Map;

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
    public String addTeacher(@RequestParam Teacher teacher) {
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
    @Age(value = 122,message = "年龄不能小于122")
    public Teacher findById(String id,@Age(value = 22,message = "年龄不能小于22") Integer age) {
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
}
