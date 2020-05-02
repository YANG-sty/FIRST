package com.gree.kafka.consumer;

import com.alibaba.fastjson.JSON;
import com.gree.user.daomain.Student;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * kafka消费者
 * Create by yang_zzu on 2020/4/21 on 8:35
 */
@Service
@Slf4j
public class StudentConsumerService {


    /**
     * 对 topic 进行消费
     * @param record
     * @param acknowledgment
     */
    @KafkaListener(id = "student-Consumer", topics = {"studentTopic"}, groupId = "studentGroup")
    public void studentListen(ConsumerRecord<String, String> record, Acknowledgment acknowledgment) {
        //手动提交ack
        acknowledgment.acknowledge();

        /*
        //运行到这里后暂停60s
        try {
            Thread.sleep(600000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        log.info(Thread.currentThread().getName() + " {}", record.value());
        Consumer consumer = System.out::println;
        Optional<Object> value = Optional.ofNullable(record.value());
        if (value.isPresent()) {
            Object object = value.get();
//            String s = JSON.toJSONString(object);
//            consumer.accept(s);
            String s = String.valueOf(object);
//            consumer.accept(s);
            Student student = JSON.parseObject(s, Student.class);

            consumer.accept("offset; "+record.offset()+"------partion: "+record.partition() +"------>>>>>>>>>>>>>>>>>>>" + student.toString());
        }

    }

    /**
     * 对topic 的某一个partition 进行消费，
     * 加快消息消费
     * @param record
     * @param acknowledgment
     */
    @KafkaListener(id = "student-Consumer-partition", topicPartitions = { @TopicPartition(topic = "studentTopic", partitions = { "0" }) })
    public void studentListenPartition(ConsumerRecord<String, String> record, Acknowledgment acknowledgment) {
        //手动提交ack
        acknowledgment.acknowledge();

        log.info(Thread.currentThread().getName() + " {}", record.value());


    }


}
