package com.gree.first.utils;

import lombok.Data;

/**
 * @author yangLongFei 2020-11-27-16:30
 */
public enum ResultCodeVo {

    //
    PARAM_ILLEGAL(400, "参数不合法"),
    //
    UNKNOW_EXCEPTION(500, "服务器内部错误"),
    //
    UN_AUTHCATED(403, "没有权限"),
    //
    REQUEST_OK(200, "操作成功");


    private Integer code;
    private String msg;

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    ResultCodeVo(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
