package com.gree.first.mongodb;

import com.baomidou.mybatisplus.plugins.Page;
import com.gree.first.student.dto.Student;

/**
 * @author yangLongFei 2021-01-28-9:05
 */
public interface DocumentDateService {

    /**
     * 保存数据
     * @param student
     * @return
     */
    Student save(Student student);

    /**
     * 更新
     * @param student
     * @return
     */
    long updateById(Student student);

    /**
     * 查询
     * @param student
     * @return
     */
    Page selectList(Student student);


    /**
     * 删除
     * @param student
     * @return
     */
    Integer delete(Student student);




}
