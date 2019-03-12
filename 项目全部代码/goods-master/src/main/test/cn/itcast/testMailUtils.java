package cn.itcast;

import cn.itcast.mail.Mail;
import cn.itcast.mail.MailUtils;
import org.junit.Test;

import javax.mail.MessagingException;
import javax.mail.Session;
import java.io.IOException;

public class testMailUtils {

    @Test
    public void send() throws IOException, MessagingException {
       /* Session session = MailUtils.createSession("smtp.163.com","itcast_cxf","itcastitcast");
        Mail mail = new Mail("itcast_cxf@163.com","itcast_cxf@126.com","测试邮件一封","<a herf = 'http://www.baidu.com'>百度</a>");
        MailUtils.send(session,mail);*/
        Session session = MailUtils.createSession("smtp.qq.com","1187579343","ytopzvveqymdjdfb");
        Mail mail = new Mail("1187579343@qq.com","1187579343@qq.com","测试邮件一封","<a href = 'https://www.baidu.com/'>百度</a>");
        MailUtils.send(session,mail);
    }
}
