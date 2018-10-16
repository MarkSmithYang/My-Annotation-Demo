package com.yb.annotation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Sets;
import com.yb.annotation.controller.TeacherController;
import com.yb.annotation.model.Teacher;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.data.Offset;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import java.time.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AnnotationApplicationTests {
 public static final Logger log = LoggerFactory.getLogger(AnnotationApplicationTests.class);

    @Autowired
    private TeacherController teacherController;

    @Test
    public void contextLoads3() {
        //把数据转换为字符串
        //guava的工具
        List<String> list = Arrays.asList(new String[]{"1", "", "ss", null, "yy"});
        String join = Joiner.on("&").skipNulls().join(list);
        System.err.println(join);//1&&ss&yy---->这里只跳过了null并没有跳过""
        try {
        //不使用跳过空(""/null)含""和null的情况
        String join1 = Joiner.on("&").join(list);//java.lang.NullPointerException
        System.err.println(join1);
        //不使用跳过空(""/null)含""的情况
        List<String> list1 = Arrays.asList(new String[]{"1", "", "ss", "", "yy"});
        String join2 = Joiner.on("&").join(list);//java.lang.NullPointerException
        System.err.println(join2);
        }catch (NullPointerException e){
            log.info("空指针异常");
        }

        //commons-lang3的工具
        String join3 = StringUtils.join(list.toArray(), "&");
        //取出两端空格并和"oo"连接,这个和mysql的那个用法应该是一致的
        String join4 = StringUtils.join(list.toArray(), "&").trim().concat("oo");
        System.err.println(join3);//1&&ss&&yy----->这个也是连接了空,只是没有报错而已

        //显然使用lang3更不容易出异常
    }

    @Test
    public void contextLoads2() {
        List<Teacher> list = teacherController.findAll();
        System.err.println(list);
    }

    @Test
    public void contextLoads1() {
        Teacher teacher = teacherController.findById("1");
        System.err.println(teacher == null ? null : teacher.toString());
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
        String str = " 四川省,成都市,马关 路区 ";
        List<String> list = Splitter.on(", ").trimResults().omitEmptyStrings().splitToList(str);
        Iterable<String> split = Splitter.on(",").split(str);
        Iterable<String> split1 = Splitter.on(",").trimResults().split(str);
        System.err.println("0--" + list);
        System.err.println("s0--" + split);
        System.err.println("s1--" + split1);
    }

    @Test
    public void contextLoadsTest1() {
        String str = " 四川省, 成都市,马关 路区 ";
        String[] split = StringUtils.split(str.trim(), ",");
        System.err.println(Arrays.asList(split));
        String join = StringUtils.join(split, "");
        System.err.println(join);
    }

    @Test
    public void contextLoadsTest2() {
        String str = "{四川省, 成都市,}{马关 路区}";
        //剔除字符串中指定的符号
        String remove = StringUtils.remove(str, "}");
        System.err.println(remove);
        String s = StringUtils.substringBetween(str, " ");
        //此方法需要确定自己要切割的那段字符串里没有自己的用来切割的符号,不然会被提前切断的
        //一般来说切割字符串,做个正则校验是比较的好的
        String s1 = StringUtils.substringBetween(str, "{", "}");
        System.err.println(s);
        System.err.println(s1);
    }

    @Test
    public void contextLoadsTest3() {
        Instant now = Instant.now();
        ZonedDateTime atZone = now.atZone(ZoneOffset.UTC);
        System.err.println(atZone);
        //获取偏移时间
        OffsetDateTime offsetDateTime = atZone.toOffsetDateTime();
        System.err.println(offsetDateTime);
        //获取日期和时间
        LocalDateTime localDateTime = atZone.toLocalDateTime();
        System.err.println(localDateTime);
        //获取日期
        LocalDate localDate = atZone.toLocalDate();
        System.err.println(localDate);
        //判断闰年
        System.err.println(localDate.isLeapYear());
        LocalDate localDate1 = localDate.plusYears(2);
        System.err.println(localDate1);
        System.err.println(localDate1.isLeapYear());
        //调整年份判断自己想要判断的年份是否是闰年
        LocalDate localDate2 = localDate.minusYears(2);
        System.err.println(localDate2);
        System.err.println(localDate2.isLeapYear());
        //根据自己的需要任意调整年月日比Date简单太多了
        System.err.println(localDate2.minusDays(15));
        LocalDate localDate3 = localDate.plusYears(7).minusDays(15);
        System.err.println(localDate3);
    }

    @Test
    public void contextLoadsTest4() {
        Instant now = Instant.now();
        ZonedDateTime atZone = now.atZone(ZoneOffset.UTC);
        System.err.println(atZone);//2018-10-16T08:37:53.980Z
        //下面这种才是对应的上海时间
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = now.atZone(zoneId);
        System.err.println(zonedDateTime);//2018-10-16T16:37:53.980+08:00[Asia/Shanghai]

        //Date和LocalDate/LocalDateTime之间的转换---->Instant/ZonedDateTime是转换的中间量
        //Date<--->Instant<--->ZoneDateTime<--->LocalDate/LocalDateTime

        //----------------------------------Date转LocalDate/LocalDateTime-----------------------------
        //获取Date时间
        Date date = new Date();
        Instant instant = date.toInstant();//获取到Instant就和上面的一样了后面的转换就不用说了
        //----------------------------------LocalDate/LocalDateTime转Date-----------------------------
        //LocalDate转Date
        LocalDate localDate = zonedDateTime.toLocalDate();
        //获取Instant
        ZonedDateTime zonedDate = localDate.atStartOfDay(ZoneId.systemDefault());
        Instant instant1 = zonedDate.toInstant();
        Date from = Date.from(instant1);
        //LocalDateTime转Date
        LocalDateTime localDateTime = zonedDate.toLocalDateTime();
        ZonedDateTime zonedDate1 = localDateTime.atZone(ZoneId.systemDefault());
        Instant instant2 = zonedDate1.toInstant();
        Date from1 = Date.from(instant2);
        //输出转换的日期Date
        System.err.println(from);//Tue Oct 16 00:00:00 CST 2018
        System.err.println(from1);//Tue Oct 16 00:00:00 CST 2018

    }
}
