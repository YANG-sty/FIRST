package com.gree.first.user.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * Create by yang_zzu on 2020/4/13 on 10:58
 */
@Data
@Setter
@Getter
@ToString
public class Student implements Serializable {

    private static final long serialVersionUID = 4210236855232421095L;

    private String id;

    private String name;

    private String password;

    private Integer age;

    private String idCar;

    private String phone;

    private String address;

    private String email;

    //成绩
    private Subject subject;

    //错误信息
    private String errorMsg;

    /**
     * 是否删除，0: 否， 1：是
     */
    private Integer isDelete;

    private String creator;

    private Date createTime;

    private String updator;

    private Date updateTime;

}
