package jlu.edu.cn.lsh.po;

import java.util.List;

public class CategoryCustom extends Category{
    private CategoryCustom parent;
    private List<CategoryCustom> children;

    @Override
    public String toString() {
        return "CategoryCustom{" +
                "parent=" + parent +
                ", children=" + children +
                '}';
    }

    public CategoryCustom getParent() {
        return parent;
    }

    public void setParent(CategoryCustom parent) {
        this.parent = parent;
    }

    public List<CategoryCustom> getChildren() {
        return children;
    }

    public void setChildren(List<CategoryCustom> children) {
        this.children = children;
    }
}
