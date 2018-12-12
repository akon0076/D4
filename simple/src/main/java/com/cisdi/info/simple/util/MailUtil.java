package com.cisdi.info.simple.util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Properties;
/**
 * @ClassName MailUtil
 * @Author 龚翔
 * @Date 2018-11-29 10:06
 **/
public class MailUtil {
/**
       * 发送邮件
       * @param to 给谁发
      * @param text 发送内容
       */
     public void send_mail(String from, String to, String text) throws MessagingException {
         //创建连接对象 连接到邮件服务器
         Properties properties = new Properties();
        //设置发送邮件的基本参数
         //发送邮件服务器
         properties.put("mail.smtp.host", "smtp.qq.com");
         //发送端口
         properties.put("mail.smtp.port", "25");
         properties.put("mail.smtp.auth", "true");
         //设置发送邮件的账号和密码
         Session session = Session.getInstance(properties, new Authenticator() {
            @Override
             protected PasswordAuthentication getPasswordAuthentication() {
                //两个参数分别是发送邮件的账户和授权码
                return new PasswordAuthentication(from,"detrjhmhhievcbbd");
            }
         });

         //创建邮件对象
         Message message = new MimeMessage(session);
         //设置发件人
         message.setFrom(new InternetAddress(from));
         //设置收件人
         message.setRecipient(Message.RecipientType.TO,new InternetAddress(to));
         //设置主题
         message.setSubject("验证码");

         //设置邮件正文  第二个参数是邮件发送的类型
         message.setContent(text,"text/html;charset=UTF-8");
        //发送一封邮件
         Transport.send(message);
     }
}
