package com.gree.first.user.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 第二个用户表（在另外一个数据库中）
 *
 * @author yangLongFei 2020-11-27-15:41
 */
@Data
@TableName("second_user")
public class UserSecond implements Serializable {
    private static final long serialVersionUID = -3908616599881678280L;

    @TableId(value = "id", type = IdType.UUID)
    private String id;

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

    /**
     * 资源
     */
    private String resource;
}
