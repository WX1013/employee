package com.emp.utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.InputStream;
import java.util.Properties;

/**
 * 发邮件工具类
 */
public final class MailUtils {

    public static Properties properties = new Properties();

    static {
        try{
            String path = "properties/mail.properties";
            InputStream is = MailUtils.class.getClassLoader().getResourceAsStream(path);
            properties.load(is);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 发件人称号，同邮箱地址
     */
    private static String USER = (String) properties.get("mail.user");
    /**
     * 如果是qq邮箱可以使户端授权码，或者登录密码
     */
    private static String PASSWORD = (String) properties.get("mail.password");

    private static String HOST = "smtp.qq.com";

    /**
     * @param to 收件人邮箱
     * @param content 邮件正文
     * @param title 标题
     */
    public static boolean sendMail(String to, String content, String title){
        try {
            final Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.host", HOST);
            // 发件人的账号
            props.put("mail.user", USER);
            //发件人的密码
            props.put("mail.password", PASSWORD);
            // 设置成465端口
            props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.setProperty("mail.smtp.socketFactory.fallback", "false");
            props.setProperty("mail.smtp.port", "465");
            props.setProperty("mail.smtp.socketFactory.port", "465");

            // 构建授权信息，用于进行SMTP进行身份验证
            Authenticator authenticator = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    // 用户名、密码
                    String userName = props.getProperty("mail.user");
                    String password = props.getProperty("mail.password");
                    return new PasswordAuthentication(userName, password);
                }
            };
            // 使用环境属性和授权信息，创建邮件会话
            Session mailSession = Session.getInstance(props, authenticator);
            // 创建邮件消息
            MimeMessage message = new MimeMessage(mailSession);
            // 设置发件人
            String username = props.getProperty("mail.user");
            InternetAddress form = new InternetAddress(username);
            message.setFrom(form);
            // 设置收件人
            InternetAddress toAddress = new InternetAddress(to);
            message.setRecipient(Message.RecipientType.TO, toAddress);
            // 设置邮件标题
            message.setSubject(title);
            // 设置邮件的内容体
            message.setContent(content, "text/html;charset=UTF-8");
            // 发送邮件
            Transport.send(message);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) throws Exception { // 做测试用
        MailUtils.sendMail("iamwx@foxmail.com","你好，无需回复，这是一封测试邮件。","测试邮箱");
        System.out.println("发送成功");
    }



}
