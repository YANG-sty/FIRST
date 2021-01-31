package com.gree.contants;

/**
 * 状态枚举
 * @author yangLongFei 2021-01-29-9:56
 */
public enum StatusEnum {
    VALID(0),
    INVALID(1)
    ;

    private Integer code;
    StatusEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

}
