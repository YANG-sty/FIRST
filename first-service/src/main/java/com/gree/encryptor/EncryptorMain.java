package main.java.com.gree.encryptor;

import com.alibaba.fastjson.JSONObject;
import com.gree.user.daomain.Student;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.util.text.BasicTextEncryptor;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Create by yang_zzu on 2020/4/15 on 9:29
 */
public class EncryptorMain {

    @Autowired
    StringEncryptor stringEncryptor;

    @Test
    public void encryptor() {
        Consumer consumer = System.out::println;
        consumer.accept("--------加密-------》" + stringEncryptor.encrypt("yang_zzu"));
    }

    /**
     * encryptor
     * 加密是使用测试方法生成加密之后的密码，用户名等数据，
     * 保存好 salt 关键字（加密，解密）都需要用到
     * 将，加密后的数据 使用 ENC(......) 放到配置文件中
     * @param args
     */
    public static void main(String[] args) {

        Map<String, String> map = new HashMap<>();

        Student student = JSONObject.parseObject(JSONObject.toJSONString(map), Student.class);

        Consumer consumer = System.out::println;

        BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
        basicTextEncryptor.setPassword("yang_zzu");

        //对内容进行加密
        String encryptUser = basicTextEncryptor.encrypt("root");
        String encryptPassword = basicTextEncryptor.encrypt("123456");
        //进行解密
        String decryptUser = basicTextEncryptor.decrypt(encryptUser);
        String decryptPasword = basicTextEncryptor.decrypt(encryptPassword);

        consumer.accept("-------encryptUser------------"+encryptUser);
        consumer.accept("------------encryptPassword---------"+encryptPassword);
        consumer.accept("------------decryptUser---------"+decryptUser);
        consumer.accept("------------decryptPasword---------"+decryptPasword);

    }

}
