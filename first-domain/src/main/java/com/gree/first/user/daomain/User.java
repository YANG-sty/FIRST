package com.gree.first.user.daomain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Create by yang_zzu on 2020/5/26 on 16:12
 */
@Data
@TableName("first_user")
public class User implements Serializable {
    private static final long serialVersionUID = 2107651223108826390L;

    @TableId(value = "id", type = IdType.UUID)
    private String id;

    private String userAccount;

    private String userPassword;

    private String userName;

    @TableField(value = "creator", fill = FieldFill.INSERT)
    private String creator;

    @TableField(value="create_time", fill= FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "updator", fill = FieldFill.INSERT_UPDATE)
    private String updator;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;


}
