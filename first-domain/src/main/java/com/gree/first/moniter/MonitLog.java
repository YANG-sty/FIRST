package com.gree.first.moniter;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yangLongFei 2021-02-01-15:53
 */
@Data
public class MonitLog implements Serializable {

    private static final long serialVersionUID = -5810953309841401422L;

    //主键id
    private Long id;

    //操作
    private String action;

    private String creator;

    private Date createTime;

    private String updator;

    private Date updateTime;

    //文档id
    private String fieldId;

    //文档类型
    private String fieldType;

}
