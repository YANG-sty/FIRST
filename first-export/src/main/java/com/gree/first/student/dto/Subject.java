package com.gree.first.student.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Create by yang_zzu on 2020/4/13 on 21:11
 */
@Data
@Setter
@Getter
@ToString
public class Subject implements Serializable {
    private static final long serialVersionUID = 3167816469301840808L;
    private double yuwen;
    private double shuxue;
    private double yingyu;
    private double shengwu;
    private double wuli;
    private double huaxue;
}
