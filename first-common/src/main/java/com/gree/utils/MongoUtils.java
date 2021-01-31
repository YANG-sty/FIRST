package com.gree.utils;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.gree.contants.StatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yangLongFei 2021-01-28-20:12
 */
@Slf4j
public class MongoUtils {


    private static String reg = "\\{|\\}|\\*|\\+";
    private static String _ID = "_id";
    private static String ID = "id";


    /**
     * 将需要更新的字段装换成Update对象
     * @param o 更新的对象
     * @param updateColums 需要更新的对象字段的集合
     * @param notNull 需要更新的集合字段的值是否可为空， true表示不为空
     * @param update new Update()
     */
    public static void beanToUpdate(
            Object o,
            List<String> updateColums,
            boolean notNull,
            Update update
    ) {
        Map<String, Object> propertiesValues = BeanUtil.beanToMap(o);
        for (Map.Entry<String, Object> entry : propertiesValues.entrySet()) {
            String key = entry.getKey();
            // updateColums集合为空， 或者  集合不为空但是集合中不含有对象中的某些字段， 则跳过本次循环
            if (!CollectionUtils.isEmpty(updateColums) && !updateColums.contains(key)) {
                continue;
            }
            Object value = entry.getValue();
            //要更新的字段是否可以为空，为 true ，则跳过本次循环
            if (notNull && value == null) {
                continue;
            }
            update.set(key, value);
        }

    }

    /**
     * 通过id更新数据
     */
    public static void updateColumnById(
            Object o,
            List<String> updateColums, //需要更新的列
            MongoTemplate mongoTemplate,
            boolean notNull, //修改的列是否为空，true 表示不为空
            String collectionName //（文档集合名称）表名称
    ) {
        Object fieldValue = BeanUtil.getFieldValue(o, ID);
        Class<?> aClass = o.getClass();
        if (fieldValue == null) {
            throw new IllegalArgumentException("id不能为空");
        }
        try {
            Query query = new Query(Criteria.where(_ID).is(fieldValue));
            Update update = Update.update(_ID, fieldValue);
            beanToUpdate(o, updateColums, notNull, update);
            mongoTemplate.updateMulti(query, update, aClass, collectionName);
        } catch (Exception e) {
            log.error("通过_id更新数据失败：{}", e.getMessage(), e);
            throw new RuntimeException("通过_id更新数据失败", e);
        }

    }

    /**
     * 通过filenName查询数据，进行数据的更新操作
     */
    public static void updateBySpecialField(
            String fileName, //根据fileName进行查询获取数据
            Object o, //对象
            List<String> updateColums, //需要更新的字段
            MongoTemplate mongoTemplate,
            boolean notNull, //更新的字段是否能够为空, ture 不能为空
            String collectionName //（文档集合名称）表名称
    ) {
        if (StringUtils.isEmpty(fileName)) {
            throw new IllegalArgumentException("fileName字段不能为空");
        }
        Field fieldByName = ReflectUtils.getFieldByName(fileName, o);
        Object value = null;
        try {
            value = ReflectUtils.valueByField(fieldByName, o);
        } catch (Exception e) {
            log.error("fileName字段不存在或值不为空", e);
            throw new RuntimeException("fileName字段不存在或值不为空", e);
        }
        if (value == null) {
            throw new RuntimeException("fileName字段不存在");
        }
        Class<?> aClass = o.getClass();
        Query query = new Query(Criteria.where(fileName).is(value));
        Update update = Update.update(fileName, value);
        beanToUpdate(o, updateColums, notNull, update);

        mongoTemplate.updateMulti(query, update, aClass, collectionName);
    }




    /**
     * 获得分页数据
     */
    public static <T> Page<T> getGeneralListPage(
            Object o,
            List<String> include, //需要查询的属性
            List<String> ids, //id列表，可以为空
            Page<? super T> page, //分页信息
            Class<T> tClass, //具体类型
            MongoTemplate mongoTemplate,
            List<Criteria> extendConditions, //扩展条件，可以为空
            String collectionName //（文档集合名称）表名称
    ) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        //降序，字段集合
        List<String> descs = page.getDescs();
        //升序，字段集合
        List<String> ascs = page.getAscs();

        Sort sort = null;
        if (descs != null) {
            sort = new Sort(Sort.Direction.DESC, descs);
            if (ascs != null) {
                sort.and(new Sort(Sort.Direction.ASC, ascs));
            }
        }else{
            if (ascs != null) {
                sort = new Sort(Sort.Direction.ASC, ascs);
            }
        }
        //当前页
        int current = page.getCurrent();
        //页大小
        int size = page.getSize();
        Criteria includeCriteria = getExtendConditions(o, include, extendConditions);
        PageRequest pageRequest = PageRequest.of(current - 1, size);
        Query queue = queue(includeCriteria, pageRequest, sort);
        // 不 查询状态为 1 的数据
        queue.addCriteria(Criteria.where("isDelete").ne(StatusEnum.INVALID.getCode()));
        queue.addCriteria(Criteria.where("_class").is(tClass.getName()));

        if (!CollectionUtils.isEmpty(ids)) {
            queue.addCriteria(Criteria.where(_ID).in(ids));
        }

        //总记录数
        long count = mongoTemplate.count(queue,tClass, collectionName);
        List<T> ts = mongoTemplate.find(queue,tClass, collectionName);
        Page<T> tPage = new Page<>();
        tPage.setRecords(ts);
        tPage.setSize(ts.size());
        tPage.setTotal(count);
        tPage.setCurrent(current);
        tPage.setSize(size);
        return tPage;
    }


    /**
     * 查询数据，不进行分页查询
     */
    public static <T> List<T> getGeneralList(
            Object o,  //数据存储的对象
            List<String> include,  //需要查询的字段
            Class<T> tClass, //用于区分不同的文档，（即不能的表产生的数据）
            MongoTemplate mongoTemplate,
            String collectionName //（文档集合名称）表名称
    ) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        Criteria includQuery = getIncludQuery(o, include);
        Query query = queue(includQuery, null, null);
        try {
            Field id = o.getClass().getDeclaredField("id");
            Object o1 = ReflectUtils.valueByField(id, o);
            if (o1 instanceof String) {
                query.addCriteria(Criteria.where(_ID).is(o1));
            }
        } catch (NoSuchFieldException e) {
            log.error("获取Object中的id值失败：{}", e.getMessage(), e);
            throw new RuntimeException("获取Object中的id值失败", e);
        }

        //不查询状态为 1 的数据
        query.addCriteria(Criteria.where("status").ne(StatusEnum.INVALID.getCode()));
        query.addCriteria(Criteria.where("_class").is(tClass.getName()));

        return mongoTemplate.find(query, tClass, collectionName);
    }


    public static Query queue(Criteria criteria, Pageable pageable, Sort sort) {
        Query query = new Query(criteria);
        if (pageable != null) {
            query.with(pageable);
        }
        if (sort != null) {
            query.with(sort);
        }
        return query;
    }


    /**
     * 获取全部的非空属性做条件
     */
    public static Criteria getNotNullQuery(Object o) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        return getQueryNotNull(o, null, null, null);
    }

    public static Criteria getExcludeQuery(Object o, List<String> exclude) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        return getQueryNotNull(o, exclude, null, null);
    }

    public static Criteria getIncludQuery(Object o, List<String> include) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        return getQueryNotNull(o, null, include, null);
    }


    public static Criteria getExtendConditions(Object o, List<String> include, List<Criteria> extendConditions) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        return getQueryNotNull(o, null, include, extendConditions);
    }


    /**
     * 通过实体获取非空的属性作为查询条件
     * @param o
     * @param exclude 不需要查询的字段，可以为空
     * @param include 需要查询的字段，可以为空
     * @param extendConditions 额外的条件，可以为空
     * @return
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    private static Criteria getQueryNotNull(Object o, List<String> exclude, List<String> include, List<Criteria> extendConditions) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        //根据实体获取相应的属性和属性值
        Map<String, Object> propertiesValues = ReflectUtils.getPropertiesValues(o);
        Criteria criteria = new Criteria();
        List<Criteria> criteriaList = new ArrayList<>();
        boolean isExclude = exclude != null && exclude.size() != 0;
        boolean isInclude = include != null && include.size() != 0;
        if (isExclude && isInclude) {
            throw new IllegalArgumentException("can not use both include and exclude");
        }
        for (Map.Entry<String, Object> entry : propertiesValues.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (isExclude && exclude.contains(key)) {
                continue;
            }
            if (isInclude && !include.contains(key)) {
                continue;
            }
            if (value == null) {
                continue;
            }

            if (value instanceof String) {
                String st = (String) value;
                if (StringUtils.isEmpty(st)) {
                    continue;
                }
                //查找特殊字符进行转义
                Pattern compile = Pattern.compile(reg);
                Matcher matcher = compile.matcher(st);
                StringBuilder stringBuilder = new StringBuilder(st);
                for (int i = 0; matcher.find(); i++) {
                    stringBuilder.insert(matcher.start() + i, "\\");
                }
                st = stringBuilder.toString();
                //db.inventory.find( { item: { $not: { $regex: "^p.*" } } } )
                criteriaList.add(Criteria.where(key).regex(st));

            } else if (value instanceof String[]) {
//                String[] techPoints = (String[]) value;
                //db.inventory.find( { tags: { $all: [ "appliance", "school", "book" ] } } )
                criteriaList.add(Criteria.where(key).all(value));

            } else if (value instanceof Collection) {
                int size = ((Collection) value).size();
                if (size == 0) {
                    continue;
                }
                //db.inventory.find( { tags: { $all: [ "appliance", "school", "book" ] } } )
                criteriaList.add(Criteria.where(key).all(value));

            } else {
                criteriaList.add(Criteria.where(key).is(value));
            }

        }

        /**
         * 扩展条件查询
         */
        if (extendConditions != null && !extendConditions.isEmpty()) {
            criteriaList.addAll(extendConditions);
        }
        if (criteriaList.size() == 0) {
            return criteria;
        }

        Criteria[] arr = new Criteria[criteriaList.size()];
        int count = 0;
        for (Criteria ignored : criteriaList) {
            arr[count] = criteriaList.get(count);
            count++;
        }

        criteria.andOperator(arr);
        return criteria;
    }


    private static String getIdByObject(Object o) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Field declaredField = o.getClass().getDeclaredField(_ID);
        Object o1 = ReflectUtils.valueByField(declaredField, o);
        if (o1 instanceof String) {
            return (String) o;
        } else {
            return "";
        }
    }



}
