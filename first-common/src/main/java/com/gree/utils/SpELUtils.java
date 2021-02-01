package com.gree.utils;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * 解析 SpELl 表达式
 * @author yangLongFei 2021-02-01-15:37
 */
@Slf4j
public class SpELUtils<T> {

    public static Map<String, String> parseSpEL(Map<String, String> exps, MethodSignature methodSignature, Object[] args) {
        SpelExpressionParser spelExpressionParser = new SpelExpressionParser();
        LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();
        Method method = methodSignature.getMethod();
        String[] parameterNames = discoverer.getParameterNames(method);
        StandardEvaluationContext context = new StandardEvaluationContext();
        Map<String, String> result = Maps.newHashMap();
        for (int i = 0; i < parameterNames.length; i++) {
            context.setVariable(parameterNames[i], args[i]);
        }
        for (String s : exps.keySet()) {
            Expression expression = spelExpressionParser.parseExpression(exps.get(s));
            String value = expression.getValue(context, String.class);
            log.debug("************* SpEL 表达式： key => " + s + "   value => " + value);
            result.put(s, value);
        }
        return result;
    }
}
