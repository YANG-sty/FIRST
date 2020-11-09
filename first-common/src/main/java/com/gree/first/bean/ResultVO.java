package com.gree.first.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * 数据封装类
 * Create by yang_zzu on 2020/10/1 on 16:31
 */
@Data
public class ResultVO<T> implements Serializable {
    private static final long serialVersionUID = -6008667474956425560L;

    /**
     * 结果码
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 数据总数
     */
    private Long count;

    /**
     * 数据对象
     */
    private T data;

}
