package com.yb.annotation.service;

import com.yb.annotation.model.Teacher;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author yangbiao
 * @Description:
 * @date 2018/10/12
 */
@Service
public class TeacherService {
    public static final Logger log = LoggerFactory.getLogger(TeacherService.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 添加Teacher信息
     *
     * @param teacher
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public String addTeacher(Teacher teacher) {
        jdbcTemplate.update("insert into teacher (id,`name`,class_name,age) values (?,?,?,?)", preparedStatement -> {
            preparedStatement.setObject(1, teacher.getId());
            preparedStatement.setObject(2, teacher.getName());
            preparedStatement.setObject(3, teacher.getClassName());
            preparedStatement.setObject(4, teacher.getAge());
        });
        return "添加成功";
    }

    /**
     * 通过id查询Teacher信息
     *
     * @param id
     * @return
     */
    public Teacher findById(String id) {
        if (StringUtils.isBlank(id)) {
            //这里是随意抛的一个异常,实际情况不能这么做,会影响判断的,最好自己定义一个来使用
            throw new NoSuchFieldError("id不能为空");
        }
        //查询信息
        //通过这个模板,可以通过map封装参数,看到网上使用变量(:xxx)来占位的,所以只要变量和map的key对应即可
        //但是我想用?来占位,这个肯定是要根据顺序来的,所以用LinkedHashMap来保持有序
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(jdbcTemplate);
        //现在用JdbcTemplate测试下in的用法,传入List集合,不管是用集合还是数组传参数,都不行,虽然不报错,但是结果是空
        //下面是通过NamedParameterJdbcTemplate来通过变量占位(?占位不知道该怎么传递参数,对了记住使用变量占位需要使用冒号(:))
        //只需要把自己想要的传递参数通过占位变量做key就行了,如下

        //-------------------------------------------------------------------------------------------------------------
        ArrayList<String> objects = Lists.newArrayList();
        objects.add(id);
        objects.add("2");
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("aa", objects);
//        params.put("na", "jack");

        List<Teacher> list = template.query("select * from teacher where id in (:aa) ", params, (resultSet, i) -> {
            Teacher teacher = new Teacher();
            teacher.setClassName(resultSet.getString("class_name"));
            teacher.setId(resultSet.getString("id"));
            teacher.setName(resultSet.getString("name"));
            teacher.setAge(resultSet.getInt("age"));
            return teacher;
        });
//        List<Teacher> list = template.query("select * from teacher where id in (:aa) and `name`=:na ", params, (resultSet, i) -> {
//            Teacher teacher = new Teacher();
//            teacher.setClassName(resultSet.getString("class_name"));
//            teacher.setId(resultSet.getString("id"));
//            teacher.setName(resultSet.getString("name"));
//            teacher.setAge(resultSet.getInt("age"));
//            return teacher;
//        });
        //因为查询的是一个对象,所以只取一个,这里使用List来封装数据,可以容错
        return CollectionUtils.isNotEmpty(list) ? list.get(0) : null;
        //-------------------------------------------------------------------------------------------------------------
    }

    /**
     * 查询所有Teacher信息
     *
     * @return
     */
    public List<Teacher> findAll() {
        List<Teacher> result = jdbcTemplate.query("select * from teacher", (resultSet, i) -> {
            //(resultSet, i)这个实现的是这个参数是可以返回T的方法,如果是resultSet->那么就是另一方法,它的返回值是void
            //这里的i参数,就是数据的行号,从0开始,在这里好像没什么用
            Teacher teacher = new Teacher();
            teacher.setName(resultSet.getString("name"));
            teacher.setId(resultSet.getString("id"));
            teacher.setClassName(resultSet.getString("class_name"));
            teacher.setAge(resultSet.getInt("age"));
            return teacher;
        });
        return result;
    }

    /**
     * 不建议用
     *
     * @param id
     * @return
     */
    public Map<String, Object> findByIdMap(String id) {
        try {
            //建议不要使用queryForMap这个api,有毒,很容易出错,sql语句查询不到数据它都要报错
            Map<String, Object> map = jdbcTemplate.queryForMap("select * from teacher where id=?", new Object[]{id});
            return map;
        } catch (Exception e) {
            //实测捕捉不了异常信息,可能是因为异常是顶层异常,太大了
            log.info("我的异常是", e.getLocalizedMessage());
            log.info("我的异常是2", e.getMessage());
        }
        return null;
    }

    /**
     * 不建议使用
     *
     * @param id
     * @return
     */
    public Teacher bbObject(String id) {
        Teacher t = jdbcTemplate.queryForObject("select * from teacher where id =?", new Object[]{id}, (resultSet, i) -> {
            //本来想用i==0来只取一个,可是根本就是没有用,因为它只有只查询出一个对象的时候,才不会报错
            //所以不能确定返回的是一个值,就别用这个了吧,动不动就报错,受不了,这或许就是它jdbc用的少的原因吧,很容易出错
            //而且它的参数很容易出很多的问题,和下面的queryForMap一样诟病
            //所以建议都是用query这个返回List<T>这个,自己判断一下,取第一个就行了
            Teacher teacher = new Teacher();
            teacher.setName(resultSet.getString("name"));
            teacher.setId(resultSet.getString("id"));
            teacher.setAge(resultSet.getInt("age"));
            teacher.setClassName(resultSet.getString("class_name"));
            return teacher;
        });
        return t;
    }

    /**
     * 查询Teacher的List信息
     *
     * @return
     */
    public List<Map<String, Object>> queryForList() {
        //本身Json是实现了Map的,是key和value,所以不需要在此json化
        List<Map<String, Object>> result = jdbcTemplate.queryForList("select * from teacher");
        return result;
    }

    /**
     * 根据id删除Teahcer信息
     *
     * @param id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public String deleteById(String id) {
        //删除前需要确定有东西可删,就是先查询
        Teacher byId = findById(id);
        if (byId == null) {
            throw new NoSuchFieldError("id不正确");
        }
        jdbcTemplate.update("delete from teacher where id = ?", preparedStatement -> {
            preparedStatement.setObject(1, id);
        });
        return "删除成功";
    }
}
