package com.gree.utils;/*
package come.sys.gree.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

*
 * Create by yang_zzu on 2020/4/9 on 11:21


public class Email {

    private Logger logger = LoggerFactory.getLogger(Email.class);

    public void sendMail2()throws Exception{
        System.out.println("sendMailServlet-----start2");

        //1.创建邮件对象
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "mail.hand-china.com"); // 指定SMTP服务器
        properties.put("mail.smtp.auth", "true"); // 指定是否需要SMTP验证
        Session session = Session.getInstance(properties);
        MimeMessage message =new MimeMessage(session);

2.设置发件人
         * 其中 InternetAddress 的三个参数分别为: 邮箱, 显示的昵称(只用于显示, 没有特别的要求), 昵称的字符集编码
         *

        message.setFrom(new InternetAddress("xiuhong.chen@hand-china.com","xiuhong","UTF-8"));
3.设置收件人
        To收件人   CC 抄送  BCC密送

        message.setRecipient(MimeMessage.RecipientType.TO,new InternetAddress("178622****@qq.com","xiuhong","UTF-8"));
        message.addRecipient(MimeMessage.RecipientType.TO,new InternetAddress("17862****9@qq.com","xiuhong","UTF-8"));

4.设置标题

        message.setSubject("测试邮件","UTF-8");
        //message.setContent("Test Content:这是一封测试邮件...","text/html;charset=UTF-8");

5.设置邮件正文


        //一个Multipart对象包含一个或多个bodypart对象，组成邮件正文
        MimeMultipart multipart = new MimeMultipart();
        //读取本地图片,将图片数据添加到"节点"
        MimeBodyPart image = new MimeBodyPart();
        DataHandler dataHandler1 = new DataHandler(new FileDataSource("C:\\Users\\Chen Xiuhong\\Pictures\\suo.png"));
        image.setDataHandler(dataHandler1);
        image.setContentID("image_suo");

        //创建文本节点
        MimeBodyPart text = new MimeBodyPart();
        text.setContent("这张图片是��锁<br/><img src='cid:image_suo'/>","text/html;charset=UTF-8");

        //创建附件节点  读取本地文件,并读取附件名称
        MimeBodyPart file1 = new MimeBodyPart();
        DataHandler dataHandler2 = new DataHandler(new FileDataSource("C:\\Users\\Chen Xiuhong\\Pictures\\clothes.png"));
        file1.setDataHandler(dataHandler2);
        file1.setFileName(MimeUtility.encodeText(dataHandler2.getName()));

        MimeBodyPart file2 = new MimeBodyPart();
        DataHandler dataHandler3 = new DataHandler(new FileDataSource("C:\\Users\\Chen Xiuhong\\Downloads\\list.xlsx"));
        file2.setDataHandler(dataHandler3);
        file2.setFileName(MimeUtility.encodeText(dataHandler3.getName()));

        //将文本和图片添加到multipart
        multipart.addBodyPart(text);
        multipart.addBodyPart(image);
        multipart.addBodyPart(file1);
        multipart.addBodyPart(file2);
        multipart.setSubType("mixed");//混合关系

        message.setContent(multipart);

        message.setSentDate(new Date());
        message.saveChanges();
        Transport transport = session.getTransport("smtp");
        transport.connect("mail.hand-china.com","xiuhong.chen@hand-china.com","*******");
        transport.sendMessage(message,message.getAllRecipients());
        transport.close();
        Boolean isFlag = true;
        logger.info(Calendar.getInstance().getTime()+" : # Send mail to "+" success #");

        System.out.println("sendMailServlet-----end2");
    }

    public void test() throws MessagingException {

        MimeBodyPart fileBody = new MimeBodyPart();

        DataSource source = new ByteArrayDataSource(inputStream, "application/pdf");

        fileBody.setDataHandler(new DataHandler(source));

        fileBody.setFileName(MimeUtility.encodeText(fileName));
    }

}
*/
