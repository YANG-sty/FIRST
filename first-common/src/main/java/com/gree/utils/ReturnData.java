package com.gree.utils;

import lombok.Data;

import java.io.Serializable;

/**
 * Create by yang_zzu on 2020/4/17 on 9:07
 */
@Data
public class ReturnData<T> implements Serializable {

    private static final long serialVersionUID = 2405542383762066533L;

    /**
     * 接口是否成功返回
     */
    private boolean success;
    /**
     * 返回的数据描述
     */
    private String resultMessage;
    /**
     * 错误代码
     */
    private Integer resultCode;
    /**
     * 数据集合
     */
    private T result;
}
