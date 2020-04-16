package com.gree.user.daomain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

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

    private Integer age;

    private String idCar;

    private String phone;

    private String address;

    private String email;

    //成绩
    private main.java.com.gree.user.daomain.Subject subject;

}
