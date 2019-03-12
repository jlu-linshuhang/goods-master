package jlu.edu.cn.lsh.service;

import jlu.edu.cn.lsh.exception.UserException;
import jlu.edu.cn.lsh.po.User;
import jlu.edu.cn.lsh.po.UserCustom;;

public interface UserService {
    //查询相同用户名是否重复
    public boolean ifqueryCountByLoginname(String loginname) throws Exception;
    //查询相同email是否重复
    public boolean ifqueryCountByEmail(String email)throws Exception;
    //插入用户的信息
    public void insertUser(UserCustom userCustom)throws Exception;
    //校验激活码
    public void activation(String activationCode)throws UserException;
    //登陆
    public User login(User user)throws Exception;
    //修改密码
    public void updatePassword(String uid,String oldpass,String newpass)throws UserException;

}
