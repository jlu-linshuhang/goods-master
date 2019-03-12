package jlu.edu.cn.lsh.serviceimp;


import cn.itcast.mail.Mail;
import cn.itcast.mail.MailUtils;
import jlu.edu.cn.lsh.exception.UserException;
import jlu.edu.cn.lsh.mapper.UserMapper;
import jlu.edu.cn.lsh.po.User;
import jlu.edu.cn.lsh.po.UserCustom;
import jlu.edu.cn.lsh.po.UserExample;
import jlu.edu.cn.lsh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import javax.mail.Session;
import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;


public class UserServiceImp implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean ifqueryCountByLoginname(String loginname) throws Exception {

        UserExample userExample = new UserExample();
        userExample.setDistinct(false); //去除重复,true是选择不重复记录,false反之
        UserExample.Criteria criteria = userExample.createCriteria(); //构造自定义查询条件
        criteria.andLoginnameEqualTo(loginname);

        int count = userMapper.countByExample(userExample);
        System.out.println("用户名相同记录数："+count);
        if (count == 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean ifqueryCountByEmail(String email) throws Exception {

        UserExample userExample = new UserExample();
        userExample.setDistinct(false); //去除重复,true是选择不重复记录,false反之
        UserExample.Criteria criteria = userExample.createCriteria(); //构造自定义查询条件
        criteria.andEmailEqualTo(email);

        int count = userMapper.countByExample(userExample);
        System.out.println("email相同记录数："+count);
        if (count == 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void insertUser(UserCustom userCustom) throws Exception {
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replaceAll("\\-","");
        String uid = uuid.substring(0, 32);
        userCustom.setUid(uid);
        userCustom.setStatus(0);
        String str1 = UUID.randomUUID().toString().substring(0, 32);
        String str2 = UUID.randomUUID().toString().substring(0, 32);
        String activationCode = str1 + str2;
        userCustom.setActivationcode(activationCode);
        userMapper.insert(userCustom);

       /* Properties properties = new Properties();
        properties.load(this.getClass().getResourceAsStream("email_template.properties"));
        String host = properties.getProperty("host");//主机名
        String name = properties.getProperty("username");//登录名
        String pass = properties.getProperty("password");//登录密码

        String from = properties.getProperty("from");
        String to = userCustom.getEmail();
        String subject = properties.getProperty("subject");
        String content = MessageFormat.format(properties.getProperty("content"),userCustom.getActivationcode());

        Session session = MailUtils.createSession(host,name,pass);
        Mail mail = new Mail(from ,to,subject,content);*/
       String host = "smtp.qq.com";
       String username = "1187579343";
       String password = "ytopzvveqymdjdfb";
       String from = "1187579343@qq.com";
       String to = "1187579343@qq.com";
       String subject = "激活邮件一封";
       String content = MessageFormat.format("恭喜你注册成功，请转发<a href=http://localhost:8080/goods/user/activation.action?activationCode={0}>这里</a>完成激活。",userCustom.getActivationcode());
        Session session = MailUtils.createSession(host,username,password);
        Mail mail = new Mail(from,to,subject,content);
        MailUtils.send(session,mail);
    }

    @Override
    public void activation(String activationCode) throws UserException {
        UserExample userExample = new UserExample();
        userExample.setDistinct(false); //去除重复,true是选择不重复记录,false反之
        UserExample.Criteria criteria = userExample.createCriteria(); //构造自定义查询条件
        criteria.andActivationcodeEqualTo(activationCode);

        List<User> user;
        user = userMapper.selectByExample(userExample);

        if (user.size()==0){
            throw new UserException("无效的验证码!");
        }else if (user.size()==1){
            if (user.get(0).getStatus()==1){
                throw new UserException("您已激活成功，请不要二次激活");
            }
            if (user.get(0).getStatus()==0){
                user.get(0).setStatus(1);
                userMapper.updateByPrimaryKey(user.get(0));
            }
        }else {
            throw new UserException("发现多条信息，数据库错误！");
        }
    }

    @Override
    public User login(User user) throws Exception {
        UserExample userExample = new UserExample();
        userExample.setDistinct(false); //去除重复,true是选择不重复记录,false反之
        UserExample.Criteria criteria = userExample.createCriteria(); //构造自定义查询条件
        criteria.andLoginnameEqualTo(user.getLoginname()).andLoginpassEqualTo(user.getLoginpass());

        List<User> user1 = userMapper.selectByExample(userExample);
        if (user1.size()==1){
            return user1.get(0);
        }else {
            return null;
        }
    }

    @Override
    public void updatePassword(String uid, String oldpass, String newpass) throws UserException {
        UserExample userExample = new UserExample();
        userExample.setDistinct(false); //去除重复,true是选择不重复记录,false反之
        UserExample.Criteria criteria = userExample.createCriteria(); //构造自定义查询条件
        criteria.andUidEqualTo(uid).andLoginpassLike(oldpass);
        List<User> user;
        user = userMapper.selectByExample(userExample);
        if (user.size()>0){
          user.get(0).setLoginpass(newpass);
            userMapper.updateByPrimaryKeySelective(user.get(0));
        }else{
            throw new UserException("旧密码错误！");
        }
    }
}
