package com.gree.first.user.dto;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Create by yang_zzu on 2020/5/26 on 16:12
 */
@Data
public class UserDto implements Serializable {

    private static final long serialVersionUID = -3203909999146850492L;

    private String id;

    private String userPassword;

    private String userName;

    /**
     * 用户角色集合
     */
    private Set<Long> roles;

    private String creator;

    private Date createTime;

    private String updator;

    private Date updateTime;

    /**
     * 资源
     */
    private String resource;


}
