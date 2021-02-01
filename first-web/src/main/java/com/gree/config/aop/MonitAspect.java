package com.gree.config.aop;

import com.google.common.collect.Maps;
import com.gree.first.annotation.Monit;
import com.gree.first.moniter.MonitLog;
import com.gree.mongodb.controller.StudentMongoDBController;
import com.gree.monit.MonitListContent;
import com.gree.utils.SpELUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yangLongFei 2021-02-01-15:28
 */
@Aspect
@Component
@Slf4j
public class MonitAspect {


    @Around(value = "@annotation(monit)")
    public Object monit(ProceedingJoinPoint joinPoint, Monit monit) throws Throwable {
        Object result = joinPoint.proceed();
        Map<String, String> param = Maps.newHashMap();
        param.put("fieldId", monit.fieldId());
        Map<String, String> map = SpELUtils.parseSpEL(param, (MethodSignature)joinPoint.getSignature(), joinPoint.getArgs());
        String controller = joinPoint.getTarget().getClass().getName().replace("Controller", "");
        int sub = controller.lastIndexOf(".");
        //文档类型
        String docTypeParsed = controller.substring(sub + 1);
        //接口操作的名称
        String methodName = joinPoint.getSignature().getName();

        MonitLog monitLog = new MonitLog();
        monitLog.setAction(methodName);
        monitLog.setFieldType(docTypeParsed);
        monitLog.setFieldId(map.get("fieldId"));
        monitLog.setCreator("admin");
        monitLog.setCreateTime(new Date());

        //todo 将数据暂时添加到内存中
        MonitListContent.monitLogList.add(monitLog);

        return result;
    }




}
