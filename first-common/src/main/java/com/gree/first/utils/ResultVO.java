package com.gree.first.utils;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据封装展示类
 * @author yangLongFei 2020-11-27-16:14
 */
public class ResultVO<T> implements Serializable {
    private static final long serialVersionUID = -2946357404652763156L;

    /**
     * 结果码
     */
    @JsonView(ResultVO.ResultMsgVO.class)
    private Integer code;

    /**
     * 提示信息
     */
    @JsonView(ResultVO.ResultMsgVO.class)
    private String msg;

    /**
     * 数据集合
     */
    @JsonView(ResultVO.ResultRowsVO.class)
    private T rows;

    /**
     * 数据总数
     */
    @JsonView(ResultVO.ResultRowsVO.class)
    private long total;

    /**
     * 数据对象
     */
    @JsonView(ResultVO.ResultObjVO.class)
    private T data;


    /**
     * todo ? 空的接口, 区分
     */
    public interface ResultMsgVO{}
    public interface ResultRowsVO extends ResultMsgVO {
    }
    public interface ResultObjVO extends ResultMsgVO {
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("msg", this.msg);
        result.put("code", this.code);
        return result;
    }


    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setRows(T rows) {
        this.rows = rows;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public void setData(T data) {
        this.data = data;
    }
}
