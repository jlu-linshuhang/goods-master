package jlu.edu.cn.lsh.serviceimp;

import jlu.edu.cn.lsh.mapper.AdminMapper;
import jlu.edu.cn.lsh.po.Admin;
import jlu.edu.cn.lsh.po.AdminExample;
import jlu.edu.cn.lsh.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AdminServiceImp implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin login(String adminname, String adminpwd) throws Exception {
        AdminExample adminExample = new AdminExample();
        adminExample.setDistinct(false);
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andAdminnameEqualTo(adminname).andAdminpwdEqualTo(adminpwd);

        List<Admin> admins = adminMapper.selectByExample(adminExample);
        if (admins.size()==1){
            return admins.get(0);
        }else {
            return null;
        }
    }
}
