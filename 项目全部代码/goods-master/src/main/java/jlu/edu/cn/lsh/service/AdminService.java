package jlu.edu.cn.lsh.service;

import jlu.edu.cn.lsh.po.Admin;

public interface AdminService {
    //查询管理员，使用用户名和密码
    public Admin login(String adminname,String adminpwd)throws Exception;
}
