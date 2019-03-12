package jlu.edu.cn.lsh.serviceimp;

import jlu.edu.cn.lsh.mapper.CategoryMapper;
import jlu.edu.cn.lsh.po.*;
import jlu.edu.cn.lsh.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CategoryServiceImp implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<CategoryCustom> findAllClass() throws Exception {
        List<CategoryCustom> firstClassList = categoryMapper.findFistClass();
        for (CategoryCustom categoryCustom: firstClassList){
            List<CategoryCustom> secondClassList = categoryMapper.findSecondClass(categoryCustom.getCid());
            categoryCustom.setChildren(secondClassList);
        }
        return firstClassList;
    }

    @Override
    public void addFirstClass(String cname, String desc) throws Exception {
        CategoryCustom categoryCustom = new CategoryCustom();
        String cid = UUID.randomUUID().toString().substring(0,32);
        categoryCustom.setCname(cname);
        categoryCustom.setDesc(desc);
        categoryCustom.setCid(cid);
        categoryMapper.addFirstClass(categoryCustom);
    }

    @Override
    public List<CategoryCustom> findFirstClass() throws Exception {

        List<CategoryCustom> categoryCustomList = categoryMapper.findFistClass();
        return categoryCustomList;
    }

    @Override
    public void addSecondClass(String pid, String cname, String desc) throws Exception {
        CategoryCustom categoryCustom = new CategoryCustom();
        String cid = UUID.randomUUID().toString().substring(0,32);
        categoryCustom.setPid(pid);
        categoryCustom.setCname(cname);
        categoryCustom.setDesc(desc);
        categoryCustom.setCid(cid);
        categoryMapper.addSecondClass(categoryCustom);
    }

    @Override
    public CategoryCustom findClassByCid(String cid) throws Exception {
        CategoryCustom categoryCustom = categoryMapper.findClassByCid(cid);
        return categoryCustom;
    }

    @Override
    public void updateClassByCid(CategoryCustom categoryCustom) throws Exception {
        categoryMapper.updateCategoryByCid(categoryCustom);
    }

    @Override
    public void updateSecondClassByCid(CategoryCustom categoryCustom) throws Exception {
        categoryMapper.updateCategorySecondClassByCid(categoryCustom);
    }

    @Override
    public int findChildCountByParentId(String cid) throws Exception {
        Integer a = categoryMapper.findChildCountByParentId(cid);
        if (a==null){
            return 0;
        }else {
            return a.intValue();
        }
    }

    @Override
    public void deleteByCid(String cid) throws Exception {
        categoryMapper.deleteByCid(cid);
    }

    @Override
    public List<CategoryCustom> findSecondClassByPid(String pid) throws Exception {
        return categoryMapper.findSecondClass(pid);

    }
}
