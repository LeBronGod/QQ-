package cn.lbg.model;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
 
/**
 * �ʼ�������
 */
public class MailUtil {
    /**
     * �����ʼ�
     * @param to ��˭��
     * @param text ��������
     */
    public static void send_mail(String to,String text) throws MessagingException {
        //�������Ӷ��� ���ӵ��ʼ�������
        Properties properties = new Properties();
        //���÷����ʼ��Ļ�������
        //�����ʼ�������
        properties.put("mail.smtp.host", "smtp.qq.com");
        //���Ͷ˿�
        properties.put("mail.smtp.port", "25");
        properties.put("mail.smtp.auth", "true");
        //���÷����ʼ����˺ź�����
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                //���������ֱ��Ƿ����ʼ����˻�������
                return new PasswordAuthentication("3506346737@qq.com","eeycuutlbfjkdacj");
            }
        });
 
        //�����ʼ�����
        Message message = new MimeMessage(session);
        //���÷�����
        message.setFrom(new InternetAddress("3506346737@qq.com"));
        //�����ռ���
        message.setRecipient(Message.RecipientType.TO,new InternetAddress(to));
        //��������
        message.setSubject("Verify:������֤");
        //�����ʼ�����  �ڶ����������ʼ����͵�����
        message.setContent(text,"text/html;charset=UTF-8");
        //����һ���ʼ�
        Transport.send(message);
    }
}
