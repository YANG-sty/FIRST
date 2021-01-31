package com.gree.beans;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author yangLongFei 2020-11-21-18:56
 */
@Data
public class CommonUserDto implements Serializable {

    private static final long serialVersionUID = 4103093470633642269L;

    private Long userId;

    private String userName;

    private Integer orgId;

    private Integer departmentId;


    private String userPassword;

    /**
     * 角色 集合
     */
    private Set<Long> roles;

    /**
     * 角色名称集合
     */
    private Set<String> rolesName;

    /**
     * 组织id 集合
     */
    private List<Integer> orgIdList;

    /**
     * 部门 id  集合
     */
    private List<Integer> departmentIdList;

    /**
     * 资源
     */
    private String resource;


    private String creator;

    private Date createTime;

    private String updator;

    private Date updateTime;

}
