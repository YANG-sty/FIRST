package com.gree.token.dto;

import com.sun.tools.internal.ws.processor.modeler.annotation.AnnotationProcessorContext;

import javax.xml.crypto.Data;
import java.io.Serializable;

/**
 * 认证 token 封装
 * notes:
 * 一般只有在对数据和数据库进行交互的时候，才会使用 DTO
 *
 * Create by yang_zzu on 2020/4/17 on 9:15
 */
public class TokenDto implements Serializable {
    private static final long serialVersionUID = 1115782987743707307L;

    /**
     * 平台访问授权
     */
    private String access_token;
    /**
     * 当前时间
     */
    private Data time;
    /**
     * 有效时间
     */
    private String expire_in;
    /**
     * 客户端强求中包含该参数，认证服务器回应也包含该参数
     */
    private String stae;

}
