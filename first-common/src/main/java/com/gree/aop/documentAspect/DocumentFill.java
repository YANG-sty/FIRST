package com.gree.aop.documentAspect;

import com.gree.beans.CommonUserDto;
import com.gree.first.utils.ShiroUtils;
import com.gree.utils.ReflectUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * @author yangLongFei 2021-01-28-11:20
 */
@Slf4j
@Aspect
@Component
public class DocumentFill {
    private final static String CREATE = "creator";
    private final static String CREATE_TIME = "createTime";
    private final static String UPDATOR = "updator";
    private final static String UPDATOR_TIME = "updateTime";


    @Before(value = "@annotation(docFill)")
    public void docFill(JoinPoint joinPoint, DocFill docFill) throws IllegalAccessException {
        FillType type = docFill.type();
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) {
            throw new IllegalAccessException("@DocFill method parameter cannot be null");
        }
        CommonUserDto user = ShiroUtils.getUser();
        if (user == null) {
            user = new CommonUserDto();
            user.setUserName("admin");
        }
        Date date = new Date();

        wrap(args, user, date, type);
    }


    public void wrap(Object[] args, CommonUserDto userDto, Date date, FillType fillType) {
        String userName = userDto.getUserName();
        try{
            for (Object arg : args) {
                Field[] declaredFields = arg.getClass().getDeclaredFields();
                for (Field declaredField : declaredFields) {
                    String name = declaredField.getName();
                    Class<?> type = declaredField.getType();
                    if (ReflectUtils.SERIALVERSIONUID.equals(name)) {
                        continue;
                    }
                    Method setMethod = ReflectUtils.getSETMethod(declaredField, arg, type);
                    //填充更新人名称
                    if (UPDATOR.equals(name)) {
                        setMethod.invoke(arg, userName);
                    }
                    //填充更新时间
                    if (UPDATOR_TIME.equals(name)) {
                        setMethod.invoke(arg, date);
                    }
                    //如果是新增数据
                    if (FillType.INSERT.equals(fillType)) {
                        //创建人
                        if (CREATE.equals(name)) {
                            setMethod.invoke(arg, userName);
                        }
                        //创建时间
                        if (CREATE_TIME.equals(name)) {
                            setMethod.invoke(arg, date);
                        }
                    }

                }
            }
        }catch(Exception e){
            log.error("文档属性填充异常，具体信息：{}", e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
