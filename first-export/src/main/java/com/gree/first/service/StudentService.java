package com.gree.first.service;

import com.gree.first.user.daomain.Student;

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
}
