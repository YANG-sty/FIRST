package com.gree.first.utils;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * @author yangLongFei 2020-11-27-16:12
 */
public class ResultVOUtils {

    @JsonView(ResultVO.ResultObjVO.class)
    public static ResultVO successData(Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setData(object);
        resultVO.setCode(ResultCodeVo.REQUEST_OK.getCode());
        resultVO.setMsg(ResultCodeVo.REQUEST_OK.getMsg());
        return resultVO;
    }

    //没有返回的数据对象
    public static ResultVO success() {
        return successData(null);
    }

    @JsonView(ResultVO.ResultMsgVO.class)
    public static ResultVO message(Integer code, String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }

    @JsonView(ResultVO.ResultObjVO.class)
    public static ResultVO successRows(Object object, long total) {
        ResultVO resultVO = new ResultVO();
        resultVO.setData(object);
        resultVO.setCode(ResultCodeVo.REQUEST_OK.getCode());
        resultVO.setMsg(ResultCodeVo.REQUEST_OK.getMsg());
        resultVO.setRows(object);
        resultVO.setTotal(total);
        return resultVO;
    }


}
