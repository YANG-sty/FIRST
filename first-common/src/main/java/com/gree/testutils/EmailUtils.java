package com.gree.testutils;/*
package come.sys.gree.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

*
 * Create by yang_zzu on 2020/4/9 on 18:36


public class EmailUtils {
*
     * 线程池，最多同时3个线程在运行，其他的排队等候


    private static ExecutorService executor = Executors.newFixedThreadPool(3);

*
     *  发送邮件（注意发送人的邮箱必须设置开通POP3/SMTP/IMAP，否则无法发送）
     * @param sendUserAccount 发送人email账号（163或者qq邮箱） （必填）
     * @param sendUserPassword 发送人email的授权码（必填）
     * @param sendUserNickName 发送人的昵称
     * @param receiveUsers 接收人 （必填）
     * @param copyUsers  抄送人
     * @param darkUsers  暗送人
     * @param title 标题 （必填）
     * @param text 文本
     * @param bodyImgs 正文图片
     * @param attachDocs 附件
     * @param exceptionHandler 邮件发送异常时，调用处理类处理


    public static void sendMail(String sendUserAccount, String sendUserPassword, String sendUserNickName, String[] receiveUsers, String[] copyUsers, String[] darkUsers,
                                String title, String text, File[] bodyImgs, File[] attachDocs, MailSendExceptionHandler exceptionHandler){

        MailThread mail = new MailThread(sendUserAccount, sendUserPassword, sendUserNickName, receiveUsers, copyUsers, darkUsers, title, text,  bodyImgs, attachDocs,exceptionHandler);
        executor.execute(mail);
        executor.shutdown();
    }

    private static  class MailThread extends Thread{

        private String sendUserAccount;
        private String sendUserPassword;
        private String sendUserNickName;
        private String[] receiveUsers;
        private String[] copyUsers;
        private String[] darkUsers;
        private String title;
        private String text;
        private File[] bodyImgs;
        private File[] attachDocs;
        private MailSendExceptionHandler exceptionHandler;

        private JSONObject params;//参数集合

        public MailThread(String sendUserAccount, String sendUserPassword,
                          String sendUserNickName, String[] receiveUsers,
                          String[] copyUsers, String[] darkUsers, String title,
                          String text, File[] bodyImgs,
                          File[] attachDocs,MailSendExceptionHandler exceptionHandler) {
            super();
            this.sendUserAccount = sendUserAccount;
            this.sendUserPassword = sendUserPassword;
            this.sendUserNickName = sendUserNickName;
            this.receiveUsers = receiveUsers;
            this.copyUsers = copyUsers;
            this.darkUsers = darkUsers;
            this.text = text;
            this.title = title;
            this.bodyImgs = bodyImgs;
            this.attachDocs = attachDocs;
            this.exceptionHandler = exceptionHandler;

            setParams();
        }

        private void setParams(){
            params = new JSONObject();
            params.put("sendUserAccount", sendUserAccount);
            params.put("sendUserPassword", sendUserPassword);
            params.put("sendUserNickName", sendUserNickName);
            params.put("receiveUsers", receiveUsers);
            params.put("copyUsers", copyUsers);
            params.put("darkUsers", darkUsers);
            params.put("title", title);
            params.put("text", text);
            if(bodyImgs != null && bodyImgs.length > 0){
                List<String> list = new ArrayList<String>();
                for(File f : bodyImgs){
                    list.add(f.getName());
                }
                params.put("bodyImgs", list);
            }
            if(attachDocs != null && attachDocs.length > 0){
                List<String> list = new ArrayList<String>();
                for(File f : attachDocs){
                    list.add(f.getName());
                }
                params.put("attachDocs", list);
            }
        }

        public void run(){
            long startTime = System.currentTimeMillis();
            try {
                //获取邮件发送实例
                JavaMailSenderImpl mailSender = getJavaMailSenderImpl(sendUserAccount, sendUserPassword);
                //创建邮件帮助类
                MimeMessage mimeMessage = mailSender.createMimeMessage();
                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,true,"UTF-8");//必须true
                //设置邮件内容
                setMailContent(messageHelper, title, text, bodyImgs, attachDocs);

                //设置发送人
                setSenderUser(messageHelper, sendUserAccount,sendUserNickName);

                //设置接收人
                setReceiveUsers(messageHelper, receiveUsers, copyUsers, darkUsers);
                //发送
                mailSender.send(mimeMessage);



            } catch (Exception e) {
                //邮件发送失败，可以将发送失败日志记录到数据库进行相关处理
                e.printStackTrace();
                if(this.exceptionHandler != null){
                    this.exceptionHandler.doForException(e, params);
                }
            }

            long endTime = System.currentTimeMillis();
            String t = ((endTime-startTime)/(60*1000))+" 分 " + (((endTime -startTime)/1000.0)%60) + "秒";
            System.out.println("邮件发送耗时:" + t);
        }

        private  JavaMailSenderImpl  getJavaMailSenderImpl(String sendUserAccount,String sendUserPassword){

            //连接邮件服务器的参数配置
            Properties props = new Properties();
            //开启tls
            props.setProperty("mail.smtp.auth","true");
            props.setProperty("mail.smtp.ssl.enable", "true");
            //    props.setProperty("smtp.starttls.required", "true");

            JavaMailSenderImpl impl = new JavaMailSenderImpl();

            impl.setHost(sendUserAccount.endsWith("163.com") ? "smtp.163.com":"smtp.qq.com");
            impl.setUsername(sendUserAccount);
            impl.setPassword(sendUserPassword);
            impl.setPort(465);
            impl.setDefaultEncoding("UTF-8");
            impl.setProtocol("smtp");
            impl.setJavaMailProperties(props);

            return impl;
        }


*
         * 设置邮件内容
         * @param help
         * @param title
         * @param plainText
         * @param htmlText
         * @param bodyImgs
         * @param attachDocs
         * @throws MessagingException
         * @throws UnsupportedEncodingException


        private void setMailContent(MimeMessageHelper help,String title,String text,File[] bodyImgs,File[] attachDocs) throws MessagingException, UnsupportedEncodingException {
            //设置标题
            help.setSubject(title);
            //设置文本内容
            StringBuffer s = new StringBuffer("<html><body>");
            s.append(text );
            if(bodyImgs != null){
                for(int i = 0;i<bodyImgs.length;i++){
                    s.append("<img src='cid:pic"+i+"' />");
                }
            }
            s.append("</body></html>");
            help.setText(s.toString(), true);
            //展示在正文的图片
            if(bodyImgs != null && bodyImgs.length > 0){
                for(int i = 0;i<bodyImgs.length;i++){
                    help.addInline("pic"+i, bodyImgs[i]);
                }
            }
            //添加附件
            if(attachDocs != null && attachDocs.length > 0){
                for(File file : attachDocs){
                    //解决附件中文乱码
                    help.addAttachment(MimeUtility.encodeWord(file.getName()), file);
                }
            }
        }
*
         * 设置接收人,抄送人，暗送人
         * @param help
         * @param receiveUsers 接收人
         * @param copyUsers  抄送人
         * @param darkUsers  暗送人
         * @throws MessagingException


        private void setReceiveUsers(MimeMessageHelper help,String[] receiveUsers,String[] copyUsers,String[] darkUsers) throws MessagingException{
            if(receiveUsers != null){
                help.setTo(receiveUsers);
            }
            if(copyUsers != null && copyUsers.length > 0){
                help.setCc(copyUsers);
            }
            if(darkUsers != null && darkUsers.length > 0){
                help.setBcc(darkUsers);
            }
        }
*
         * 设置发送人
         * @param help
         * @param senderAccount 邮箱账号
         * @param userName  昵称
         * @throws MessagingException
         * @throws UnsupportedEncodingException


        private void setSenderUser(MimeMessageHelper help,String senderAccount,String userName) throws MessagingException, UnsupportedEncodingException{
            if(userName != null){
                help.setFrom(senderAccount,userName);
            }else{
                help.setFrom(senderAccount);
            }
        }
    }

*
     * 测试
     * @param args


    public static void main(String[] args) {
//		String sendUserAccount = "XXX@163.com";
//		String sendUserPassword = "XXXX";//授权码
        String sendUserAccount = "XXXX@qq.com";
        String sendUserPassword = "zhnbgndydwuxbjdg";

        String sendUserNickName = "XXXX";
        String[] receiveUsers =new String[]{ "XXXX@163.com"};
        String[] copyUsers = new String[]{"XXXX@qq.com"};
        String[] darkUsers = new String[]{"XXXX@qq.com"};
        String title = "诗酒人生";
        String text = "落魄江南载酒行，楚腰肠断掌中轻。十年一觉扬州梦，赢得青楼薄幸名。";
        File[] bodyImgs = new File[]{new File("e:/1.jpg"),new File("e:/2.jpg")};
//		File[] bodyImgs = new File[]{new File("e:/1.jpg")};
//		File[] bodyImgs = null;
        //文件名不要出现空格
        File[] attachDocs = new File[]{new File("e:/文档2.docx"),new File("e:/文档.docx")};
//		File[] attachDocs = null;
        sendMail(sendUserAccount, sendUserPassword, sendUserNickName, receiveUsers, copyUsers, darkUsers, title, text, bodyImgs, attachDocs,null);

        System.out.println("已异步发送。。。");
    }

}
*/
