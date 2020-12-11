package com.gree.first.aop;

import com.gree.first.annotation.RoutingDataSource;
import com.gree.first.utils.DataSourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 动态数据源切面
 * <p>
 * # 是否开启切库，
 * com.gree.muti-datasource-open= true
 * <p>
 * Create by yang_zzu on 2020/5/21 on 21:09
 */
@Aspect
@Component
@ConditionalOnProperty(prefix = "com.gree", name = "muti-datasource-open", havingValue = "true")
@Slf4j
@Order(1)
public class DynamicDataSourceAspect {

    /**
     * 各种方法之间可以使用 &&、 ||、 !、 的方式进行组合：
     *
     * execution：用于匹配方法执行的连接点；
     * within：用于匹配指定类型内的方法执行；
     * this：用于匹配当前AOP代理对象类型的执行方法；注意是AOP代理对象的类型匹配，这样就可能包括引入接口也类型匹配；
     * target：用于匹配当前目标对象类型的执行方法；注意是目标对象的类型匹配，这样就不包括引入接口也类型匹配；
     * args：用于匹配当前执行的方法传入的参数为指定类型的执行方法；
     * @within：用于匹配所以持有指定注解类型内的方法；
     * @target：用于匹配当前目标对象类型的执行方法，其中目标对象持有指定的注解；
     * @args：用于匹配当前执行的方法传入的参数持有指定注解的执行；
     * @annotation：用于匹配当前执行方法持有指定注解的方法；
     */
    @Pointcut("@annotation(com.gree.first.annotation.RoutingDataSource)")
    private void cutPoint() {
    }

    @Around(value = "cutPoint()")
    public Object cutDataSource(ProceedingJoinPoint joinPoint) throws Throwable {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = null;
        if (!(signature instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能使用在方法上面--------》");
        }

        methodSignature = (MethodSignature) signature;

        //获取访问当前的class
        Class<?> aClass = joinPoint.getTarget().getClass();

        //获取访问的方法名
        String methodSignatureName = methodSignature.getName();
        //得到方法的参数类型
        Class[] parameterTypes = methodSignature.getParameterTypes();

        String dataSource = DataSourceContextHolder.DEFAULT_DATASOURCE;

        //获得访问的对象方法
        Method method = aClass.getMethod(methodSignatureName, parameterTypes);

        Object result = null;

        try {
            if (method.isAnnotationPresent(RoutingDataSource.class)) {
                RoutingDataSource annotation = method.getAnnotation(RoutingDataSource.class);
                //获取注解的value
                dataSource = annotation.value();
            }
            //切换数据源
            DataSourceContextHolder.setDB(dataSource);
            //执行方法
            result = joinPoint.proceed();
        } catch (Exception e) {
            log.error("ERROR", "Error found: " + e);
        } finally {
            //移除数据源
            DataSourceContextHolder.clearDB();
        }
        return result;

    }

}
