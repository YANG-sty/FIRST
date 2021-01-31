package com.gree.mongodb.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.gree.first.mongodb.DocumentDateService;
import com.gree.first.student.dto.Student;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author yangLongFei 2021-01-29-15:04
 */
@Api(value = "mongodbStudent操作")
@RestController
@RequestMapping("/api/student/mongodb/")
public class StudentMongoDBController {

    @Autowired
    private DocumentDateService documentDateService;


    @ApiOperation("新增")
    @PostMapping("/addStudent")
    public Student addStudent(@Valid @RequestBody Student student) {
        Student save = documentDateService.save(student);
        return save;
    }

    @ApiOperation("查询")
    @GetMapping("/selectStudentPage")
    public Page selectStudent(@RequestBody Student student) {
        Page page = documentDateService.selectList(student);
        return page;
    }

    @ApiOperation("修改")
    @PostMapping("/updateStudent")
    public long updateStudent(@Valid @RequestBody Student student) {
        return documentDateService.updateById(student);
    }


    @ApiOperation("删除")
    @PostMapping("/deleteStudent")
    public Integer deleteStudent(@Valid @RequestBody Student student) {
        return documentDateService.delete(student);
    }

}
