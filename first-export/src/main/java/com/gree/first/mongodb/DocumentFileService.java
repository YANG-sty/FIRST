package com.gree.first.mongodb;

import com.mongodb.gridfs.GridFSDBFile;

import java.io.InputStream;

/**
 * @author yangLongFei 2021-01-25-16:01
 */
public interface DocumentFileService {

    /**
     * 文件上传
     * @param inputStream 文件流
     * @param fileName 文件名称
     * @param id 主键id
     * @param clazz 集合类型
     * @return 文件的id
     */
    String uploadFile(InputStream inputStream, String fileName, String id, Class clazz);

    /**
     * 根据id下载附件
     * @param filedId
     * @return
     */
    GridFSDBFile downloadFiledById(String filedId);




}
