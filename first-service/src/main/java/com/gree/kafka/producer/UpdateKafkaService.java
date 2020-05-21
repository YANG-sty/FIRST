package com.gree.kafka.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;


/**
 * Create by yang_zzu on 2020/4/20 on 15:43
 */
@Service
@Slf4j
public class UpdateKafkaService {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    public boolean sendMessage(String topic, String key, String value) {

        boolean flag = false;

        try {
            //数据上传
            ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, key, value);

            Class<? extends ListenableFuture> aClass = future.getClass();
            future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
                @Override
                public void onFailure(Throwable throwable) {
                    log.info("kafka send message failed !!!!!!");
                }

                @Override
                public void onSuccess(SendResult<String, String> stringStringSendResult) {
                    log.info("kafka send message ok.........");
                }
            });
            flag = true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return flag;

    }
}
