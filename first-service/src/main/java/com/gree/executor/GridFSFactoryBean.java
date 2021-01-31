package com.gree.executor;

import com.mongodb.*;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.gridfs.GridFS;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author yangLongFei 2021-01-29-20:24
 */
@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "spring.data.gridfs")
public class GridFSFactoryBean implements FactoryBean {
    private String host;
    private int port;
    private String dbName;
    private String user;
    private String pwd;

    /**
     */
    @Override
    public Object getObject() throws Exception {
        ServerAddress serverAddress = new ServerAddress(host, port);
        MongoCredential scramSha1Credential = MongoCredential.createScramSha1Credential(user, dbName, pwd.toCharArray());
        MongoClient mongoClient = new MongoClient(
                serverAddress,
                scramSha1Credential,
                MongoClientOptions.builder().maxConnectionIdleTime(100000).build()
        );
//        MongoDatabase database = mongoClient.getDatabase(dbName);
//        GridFSBucket bucket = GridFSBuckets.create(database);
//        return bucket;
        DB db = mongoClient.getDB(dbName);
        return new GridFS(db);

    }

    /**
     */
    @Override
    public Class<?> getObjectType() {
        return GridFS.class;
    }

    /**
     */
    @Override
    public boolean isSingleton() {
        return false;
    }

    /**
     * 客户端超过 6 秒的闲置时间，会自动断开
     * 防止出现 prematurely reached end of stream 问题
     * @return
     */
    @Bean
    public static MongoClientOptions mongoClientOptions() {
        return MongoClientOptions.builder().maxConnectionIdleTime(6000).build();
    }
}
