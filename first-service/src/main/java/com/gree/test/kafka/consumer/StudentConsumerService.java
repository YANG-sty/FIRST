package com.gree.test.kafka.consumer;

import com.alibaba.fastjson.JSON;
import com.gree.first.user.domain.Student;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.util.List;
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
     * todo kafka 消费
     */
//    @KafkaListener(id = "student-Consumer", topics = {"studentTopic"}, groupId = "studentGroup")
    public void studentListen(ConsumerRecord<String, String> record, Acknowledgment acknowledgment) {

        /*
        //运行到这里后暂停60s
        try {
            Thread.sleep(600000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
//        log.info(Thread.currentThread().getName() + " {}", record.value());
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

        //手动提交ack
        acknowledgment.acknowledge();

    }

//    @KafkaListener(id = "student-Consumer-Batch", topics = {"studentTopic"}, groupId = "studentGroupBatch")
    public void studentListen(List<ConsumerRecord<String, String>> recordList, Acknowledgment acknowledgment) {

        recordList.forEach(record -> {
            Optional<Object> value = Optional.ofNullable(record.value());
            if (value.isPresent()) {
                Object object = value.get();
                String s = String.valueOf(object);
                Student student = JSON.parseObject(s, Student.class);
            }
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>"+record.value());
        });
        //手动提交ack
        acknowledgment.acknowledge();

        /*
        //运行到这里后暂停60s
        try {
            Thread.sleep(600000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        /*log.info(Thread.currentThread().getName() + " {}", record.value());
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
        }*/

    }

    /**
     * 对topic 的某一个partition 进行消费，
     * 加快消息消费
     * @param record
     * @param acknowledgment
     */
//    @KafkaListener(id = "student-Consumer-partition", topicPartitions = { @TopicPartition(topic = "studentTopic", partitions = { "0" }) },groupId = "studentPartition0Group")
    public void studentListenPartition0(ConsumerRecord<String, String> record, Acknowledgment acknowledgment) {
        //手动提交ack
        acknowledgment.acknowledge();

        log.info(Thread.currentThread().getName() + " {}", record.value());


    }
    /**
     * 对topic 的某一个partition 进行消费，
     * 加快消息消费
     * @param record
     * @param acknowledgment
     */
//    @KafkaListener(id = "student-Consumer-partition", topicPartitions = { @TopicPartition(topic = "studentTopic", partitions = { "1" }) },groupId = "studentPartition1Group")
    public void studentListenPartition1(ConsumerRecord<String, String> record, Acknowledgment acknowledgment) {
        //手动提交ack
        acknowledgment.acknowledge();

        log.info(Thread.currentThread().getName() + " {}", record.value());

    }

    /**
     * 对 topic 进行消费
     * @param record
     * @param acknowledgment
     * todo kafka 消费
     */
//    @KafkaListener(id = "teacher-Consumer", topics = {"teacherTopic"}, groupId = "teacherGroup")
    public void teacherListen(ConsumerRecord<String, String> record, Acknowledgment acknowledgment) {
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
     * 批量消费 teacherTopic
     * @param recordList
     * @param acknowledgment
     */
//    @KafkaListener(id = "teacher-Consumer-Batch", topics = {"teacher"}, groupId = "teacherGroupBatch")
    public void teacherListen(List<ConsumerRecord<String, String>> recordList, Acknowledgment acknowledgment) {

        recordList.forEach(record -> {
            Optional<Object> value = Optional.ofNullable(record.value());
            if (value.isPresent()) {
                Object object = value.get();
                String s = String.valueOf(object);
                Student student = JSON.parseObject(s, Student.class);
            }
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>"+record.value());
        });
        //手动提交ack
        acknowledgment.acknowledge();

    }


}
