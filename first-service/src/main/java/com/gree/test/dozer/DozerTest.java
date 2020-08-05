package com.gree.test.dozer;

import com.gree.first.user.daomain.Student;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Create by yang_zzu on 2020/7/6 on 17:44
 */
public class DozerTest {


    @Test
    public void StudnetCopyUser() {

        Student student = new Student();
        student.setId("1");
        student.setName("小明");




    }


}
