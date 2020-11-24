package com.gree.first.student.service;



import com.gree.first.student.dto.Student;

import java.io.InputStream;
import java.util.List;

/**
 * Create by yang_zzu on 2020/7/8 on 11:00
 */
public interface StudentService {

    /**
     * 导入数据
     */
    List<Student> importAll(InputStream inputStream) throws Exception;

    List<String> getResource(String url);

    Student getpassword(String name);

}
