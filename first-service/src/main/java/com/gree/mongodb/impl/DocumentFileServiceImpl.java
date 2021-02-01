package com.gree.mongodb.impl;

import com.gree.executor.DocumentUploadExcutor;
import com.gree.first.mongodb.DocumentFileService;
import com.gree.first.student.dto.Student;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * mongodb文件操作
 * @author yangLongFei 2021-01-25-16:04
 */
@Primary
@Service
public class DocumentFileServiceImpl implements DocumentFileService {

    /**
     * com.gree.executor.GridFSFactoryBean 进行实例化
     */
    @Autowired
    private GridFS gridFS;

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private MongoTemplate mongoTemplate;


    /**
     * 文件上传
     *
     * @param inputStream 文件流
     * @param fileName    文件名称
     * @param id          主键id
     * @param clazz       集合类型
     * @return 文件的id
     */
    @Override
    public String uploadFile(InputStream inputStream, String fileName, String id, Class clazz) {
        DocumentUploadExcutor documentUploadExcutor = new DocumentUploadExcutor();
        GridFSInputFile gridFSInputFile = gridFS.createFile(inputStream, fileName);
        //将save操作，通过线程池执行
        documentUploadExcutor.put(gridFSInputFile::save);
        //获得保存到mongodb的文件的id
        Object id1 = gridFSInputFile.getId();

        Student objectToSave = new Student();
        objectToSave.setId(id1.toString());
        //保存文档（数据）信息
        mongoTemplate.save(objectToSave);

        return String.valueOf(id1);
    }

    /**
     * 根据id下载附件
     *
     * @param filedId
     * @return
     */
    @Override
    public GridFSDBFile downloadFiledById(String filedId) {
        GridFSDBFile gridFSDBFile = gridFS.find(new ObjectId(filedId));
        return gridFSDBFile;
    }
}
