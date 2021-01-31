package com.gree.mongodb.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.gree.aop.documentAspect.DocFill;
import com.gree.aop.documentAspect.FillType;
import com.gree.contants.StatusEnum;
import com.gree.first.mongodb.DocumentDateService;
import com.gree.first.student.dto.Student;
import com.gree.utils.MongoUtils;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.lang.annotation.Documented;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * mongodb数据操作
 * @author yangLongFei 2021-01-28-9:08
 */
@Component
@Primary
public class DocumentDateServiceImpl implements DocumentDateService {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 保存数据
     *
     * @param student
     * @return
     */
    @Override
    @DocFill(type = FillType.INSERT)
    public Student save(Student student) {
        student.setIsDelete(StatusEnum.VALID.getCode());
        /**
         * student 保存的数据对象
         * Student.class.getName() 表明（使用对象的全路径作为表名）
         */
        mongoTemplate.save(student, Student.class.getName());
        return student;
    }

    /**
     * 更新
     *
     * @param student
     * @return
     */
    @Override
    @DocFill(type = FillType.UPDATE)
    public long updateById(Student student) {

        /*Query query = new Query();
        //主键id一定要使用 _id
        query.addCriteria(Criteria.where("_id").is(student.getId()));
        Update update = new Update();

        MongoUtils.beanToUpdate(student, sutdentKeyList(), true, update);
        UpdateResult updateResult = mongoTemplate.updateMulti(query, update, Student.class.getName());
        //获取查询匹配的文档数
        long matchedCount = updateResult.getMatchedCount();
        //获取修改的文档数
        long modifiedCount = updateResult.getModifiedCount();
        // 修改的计数是否可用，2.6版本的 修改的计数才能正常使用
        boolean modifiedCountAvailable = updateResult.isModifiedCountAvailable();
        return modifiedCount;*/

        /**
         * 由于主键 id 在 Mongodb 中的形式为 _id， 所以通过主键查询数据进行更新，和其他字段进行更新要分开
         * 主键进行修改
         * com.gree.utils.MongoUtils#updateColumnById(java.lang.Object, java.util.List, org.springframework.data.mongodb.core.MongoTemplate, boolean)
         *
         * 其他字段进行修改
         * com.gree.utils.MongoUtils#updateBySpecialField(java.lang.String, java.lang.Object, java.util.List, org.springframework.data.mongodb.core.MongoTemplate, boolean)
         */
        MongoUtils.updateColumnById(student, sutdentKeyList(), mongoTemplate, true, Student.class.getName());

        return 1;
    }

    /**
     * 查询
     *
     * @param student
     * @return
     */
    @Override
    public Page selectList(Student student) {

        Page page = new Page();
        page.setSize(10);
        page.setCurrent(1);
        List<String> arrayList = new ArrayList();
        arrayList.add("createTime");
        page.setAscs(arrayList);
        try {
            Page generalListPage = MongoUtils.getGeneralListPage(student, sutdentKeyList(), null, page, Student.class, mongoTemplate, null, Student.class.getName());
            return generalListPage;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return page;
    }

    /**
     * 删除
     *
     * @param student
     * @return
     */
    @Override
    @DocFill(type = FillType.UPDATE)
    public Integer delete(Student student) {

        List<String> list = new ArrayList<>();
        student.setIsDelete(StatusEnum.INVALID.getCode());
        list.add("isDelete");
        MongoUtils.updateColumnById(student, sutdentKeyList(), mongoTemplate, true, Student.class.getName());

        return 1;
    }


    /**
     * 获得Student对象的每个属性名称
     */
    public List<String> sutdentKeyList() {
        Set<String> stringSet = BeanUtil.beanToMap(new Student()).keySet();
        ArrayList strings = new ArrayList(stringSet);
        return strings;
    }
}
