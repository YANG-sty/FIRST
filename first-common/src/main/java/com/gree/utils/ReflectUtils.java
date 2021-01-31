package com.gree.utils;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yangLongFei 2021-01-28-11:36
 */
public class ReflectUtils {
    private static final String SET = "set";
    private static final String GET = "get";
    private static final String IS = "is";

    public static final String SERIALVERSIONUID = "serialVersionUID";

    /**
     * 通过dto实体生成原生bean
     */
    public static <T, R> R getOriginBean(T t, R r) {
        try {
            BeanUtils.copyProperties(t, r);
            return r;
        } catch (BeansException e) {
            throw new ReflectExcpetion(e.getMessage(), e);
        }
    }


    /**
     * 通过List<dto>实体生成原生的 bean List
     */
    @SuppressWarnings("unchecked")
    public static <T, R> List<R> getFromToBeanList(List<T> tList, Class<R> r) {
        List<R> rList = new ArrayList<>();
        try {
            for (T t : tList) {
                //通过反射的方式，进行实例化
                R r1 = r.getDeclaredConstructor().newInstance();
                BeanUtils.copyProperties(t, r1);
                rList.add(r1);
            }
        } catch (Exception e) {
            throw new ReflectExcpetion(e.getMessage(), e);
        }
        return rList;
    }


    /**
     * 获得bean对象设置属性的方法
     * @param o 对象
     * @param includeIS 是否包含is开头的方法
     * @return
     */
    public static List<Method> getSETMethods(Object o, boolean includeIS) {
        Method[] methods = o.getClass().getMethods();

        List<Method> collect = Arrays.stream(methods)
                .filter(p -> p.getName().startsWith(SET))
                .collect(Collectors.toList());
        //包括 is 开头的方法
        if (includeIS) {
            List<Method> collectIS = Arrays.stream(methods)
                    .filter(p -> p.getName().startsWith(IS))
                    .collect(Collectors.toList());
            collect.addAll(collectIS);
        }
        return collect;
    }

    /**
     * 获得bean对象设置属性的方法
     * @param o 对象
     * @param includeIS 是否包含is开头的方法
     * @return
     */
    public static List<Method> getGETMethods(Object o, boolean includeIS) {
        Method[] methods = o.getClass().getMethods();

        List<Method> collect = Arrays.stream(methods)
                .filter(p -> p.getName().startsWith(GET))
                .collect(Collectors.toList());
        //包括 is 开头的方法
        if (includeIS) {
            List<Method> collectIS = Arrays.stream(methods)
                    .filter(p -> p.getName().startsWith(IS))
                    .collect(Collectors.toList());
            collect.addAll(collectIS);
        }
        return collect;
    }

    /**
     * 获得set方法
     * @param field
     * @param o
     * @param fildType
     * @return
     * @throws NoSuchMethodException
     */
    public static Method getSETMethod(Field field, Object o, Class<?> fildType) throws NoSuchMethodException {
        String name = field.getName();
        String methodName = SET + StringUtils.capitalize(name);
        return o.getClass().getMethod(methodName, fildType);
    }

    /**
     * 获得get方法
     * @param field
     * @param o
     * @param fildType
     * @return
     * @throws NoSuchMethodException
     */
    public static Method getGETMethod(Field field, Object o, Class<?> fildType) throws NoSuchMethodException {
        String name = field.getName();
        String methodName = GET + StringUtils.capitalize(name);
        return o.getClass().getMethod(methodName, fildType);
    }


    public static Object valueByField(Field field, Object o) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        String name = field.getName();
        String methodName = GET + StringUtils.capitalize(name);
        Method method = o.getClass().getMethod(methodName);
        return method.invoke(o);
    }


    /**
     * 根据实体获取相应的属性和属性值
     * @param o
     * @return
     */
    public static Map<String, Object> getPropertiesValues(Object o) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Map<String, Object> map = new HashMap<>();

        try {
            Class<?> aClass = o.getClass();
            List<Field> fields = new ArrayList<>(32);
            recursiveField(aClass, fields);
            for (Field field : fields) {
                String name = field.getName();
                //去除serialVersionUID的值
                if (SERIALVERSIONUID.equals(name)) {
                    continue;
                }
                String getMethod = GET + StringUtils.capitalize(name);
                Method method = aClass.getMethod(getMethod);
                Object invoke = method.invoke(o);
                map.put(name, invoke);
            }
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new ReflectExcpetion("属性解析失败：" + e.getMessage(), e);
        }
        return map;
    }

    /**
     * 通过属性名称获得Filed(包括：属性名称、属性类型、属性修饰符、属性注解 等等)
     * @param name
     * @param o
     * @return
     */
    public static Field getFieldByName(String name, Object o) {
        if (StringUtils.isEmpty(name)) {
            throw new IllegalArgumentException("属性名称为空");
        }
        List<Field> fields = new ArrayList<>(32);
        recursiveField(o.getClass(), fields);
        List<Field> collect = fields.stream()
                .filter(p -> name.equals(p.getName()))
                .collect(Collectors.toList());
        if (collect.isEmpty()) {
            return null;
        }
        return collect.get(0);

    }


    private static void recursiveField(Class calzz, List<Field> fields) {
        if (calzz == null) {
            return;
        }
        Field[] declaredFields = calzz.getDeclaredFields();
        List<Field> fieldsList = Arrays.asList(declaredFields);
        fields.addAll(fieldsList);
        recursiveField(calzz.getSuperclass(), fields);
    }


    private static class ReflectExcpetion extends RuntimeException implements Serializable {

        private static final long serialVersionUID = 3241824750339909493L;

        private ReflectExcpetion(String message, Exception e) {
            super(message, e);
        }

        private ReflectExcpetion(String message) {
            super(message);
        }
    }

}

