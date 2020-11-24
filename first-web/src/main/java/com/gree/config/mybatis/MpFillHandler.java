package com.gree.config.mybatis;

import com.baomidou.mybatisplus.entity.TableFieldInfo;
import com.baomidou.mybatisplus.entity.TableInfo;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.mapper.MetaObjectHandler;
import com.baomidou.mybatisplus.toolkit.TableInfoHelper;
import com.gree.context.UserContextHolder;
import com.gree.first.user.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

/**
 * mybatis-plus 自定义填充策略
 * #自定义填充策略接口实现
 * mybatis-plus.global-config.meta-object-handler=com.gree.config.MpFillHandler
 * Create by yang_zzu on 2020/4/14 on 21:09
 */
@Slf4j
@Component
public class MpFillHandler extends MetaObjectHandler {

    private final static String CREATE = "creator";
    private final static String CREATE_TIME = "createTime";
    private final static String UPDATOR = "updator";
    private final static String UPDATOR_TIME = "updateTime";

    @Override
    public void insertFill(MetaObject metaObject) {
        //当前用户
        User currentUser = UserContextHolder.getCurrentUser();

        String userName = currentUser.getUserName();

        if (ifFill(CREATE,userName,metaObject, FieldFill.INSERT)) {
            setFieldValByName(CREATE, userName, metaObject);
        }
        if (ifFill(CREATE_TIME, new Date(), metaObject, FieldFill.INSERT)) {
            setFieldValByName(CREATE_TIME, new Date(), metaObject);
        }
        if (ifFill(UPDATOR, userName, metaObject, FieldFill.INSERT_UPDATE)) {
            setFieldValByName(UPDATOR, userName, metaObject);
        }
        if (ifFill(UPDATOR_TIME, new Date(), metaObject, FieldFill.INSERT_UPDATE)) {
            setFieldValByName(UPDATOR_TIME, new Date(), metaObject);
        }

    }

    @Override
    public void updateFill(MetaObject metaObject) {

        //当前用户
        User currentUser = UserContextHolder.getCurrentUser();

        String userName = currentUser.getUserName();

        if (ifFill(UPDATOR, userName, metaObject, FieldFill.UPDATE)) {
            setFieldValByName(UPDATOR, userName, metaObject);
        }
        if (ifFill(UPDATOR_TIME, new Date(), metaObject, FieldFill.UPDATE)) {
            setFieldValByName(UPDATOR_TIME, new Date(), metaObject);
        }

    }

    /**
     * 是否对该字段进行填充
     * @param fieldName 字段名称
     * @param fieldVal 填充值
     * @param metaObject 存储的是sql的语句条件
     * @param fieldFill 填充类型
     * @return true OR false
     */
    private boolean ifFill(String fieldName, Object fieldVal, MetaObject metaObject, FieldFill fieldFill) {

        TableInfo tableInfo = metaObject.hasGetter("et") ? TableInfoHelper.getTableInfo(metaObject.getValue("et").getClass())
                : TableInfoHelper.getTableInfo(metaObject.getOriginalObject().getClass());
        if (Objects.nonNull(tableInfo)) {
            Optional<TableFieldInfo> first = tableInfo.getFieldList().stream()
                    .filter(e -> e.getProperty().equals(fieldName) && e.getPropertyType().equals(fieldVal.getClass()))
                    .findFirst();
            if (first.isPresent()) {
                FieldFill field = first.get().getFieldFill();
                /**
                 * 任意一个为真，返回的结果为真
                 * 1. 填充类型是否和传入的填充类型一样
                 * 2. 填充类型是否是 插入and更新
                 */
                return field.equals(fieldFill) || FieldFill.INSERT_UPDATE.equals(field) || FieldFill.INSERT.equals(field) || FieldFill.UPDATE.equals(field);
            }
        }
        return false;
    }
}
