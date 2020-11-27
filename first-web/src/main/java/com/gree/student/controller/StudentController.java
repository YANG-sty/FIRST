package com.gree.student.controller;

import com.alibaba.fastjson.JSON;
import com.gree.test.kafka.producer.UpdateKafkaService;
import com.gree.first.user.domain.Student;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * Create by yang_zzu on 2020/4/20 on 16:19
 */
@Api(value = "学生服务", tags = "和value的作用一样，使用一个就可以")
//@Controller//试图返回一个页面
@RestController//返回json/xml数据
@RequestMapping("/api/StudentKafka")
public class StudentController {

    @Autowired
    UpdateKafkaService updateKafkaService;

    @PostMapping("/updateStudent")
    @ApiOperation(value = "学生信息上传到kafka", httpMethod = "post", response = boolean.class, notes = "接口发布说明")
    @ApiParam(required = true, name = "Student", value = "student对象")
    public boolean updateStudent(@Valid @RequestBody Map map) {

        Consumer consumer = System.out::println;
        String student1 = MapUtils.getString(map, "student");
        consumer.accept(student1);
        String string = JSON.toJSONString(student1);
        consumer.accept(string);
        string = JSON.toJSONString(map.get("student"));
        consumer.accept(string);
        Student student = JSON.parseObject(string, Student.class);

        String topic = "student";
//        String key = "student";
        boolean b = false;
        for (int i = 0; i < 300; i++) {
            student.setId(String.valueOf(i));
            student.setAddress("地球+" + i);
            student.setAge(18);
            student.setPhone("66666666666");
            student.setEmail("diqiu@uu.ccc");
            student.setIdCar(UUID.randomUUID().toString());
            String s = JSON.toJSONString(student);

            b = updateKafkaService.sendMessage(topic, student.getAddress(), s);
        }
        return b;
    }
}
