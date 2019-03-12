package jlu.edu.cn.lsh.service;

import jlu.edu.cn.lsh.po.Category;
import jlu.edu.cn.lsh.po.CategoryCustom;

import java.util.List;

public interface CategoryService {

    //前台的
    //查找所有分类
    public List<CategoryCustom> findAllClass()throws Exception;


    //后台的
    //添加一级分类
    public void addFirstClass(String cname,String desc)throws Exception;
    //查找一级分类
    public List<CategoryCustom> findFirstClass()throws Exception;
    //添加二级分类
    public void addSecondClass(String pid,String cname,String desc)throws Exception;
    //加载分类信息
    public CategoryCustom findClassByCid(String cid)throws Exception;
    //修改一级分类信息
    public void updateClassByCid(CategoryCustom categoryCustom)throws Exception;
    //修改二级分类信息
    public void updateSecondClassByCid(CategoryCustom categoryCustom)throws Exception;
    //查询父分类下子分类个数
    public int findChildCountByParentId(String cid)throws Exception;
    //删除分类
    public void deleteByCid(String cid)throws Exception;
    //查找二级分类
    public List<CategoryCustom> findSecondClassByPid(String pid)throws Exception;


}
