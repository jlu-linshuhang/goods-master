package jlu.edu.cn.lsh.po;

public class BookCustom extends Book{
    private CategoryCustom categoryCustom;

    public CategoryCustom getCategoryCustom() {
        return categoryCustom;
    }

    public void setCategoryCustom(CategoryCustom categoryCustom) {
        this.categoryCustom = categoryCustom;
    }

    @Override
    public String toString() {
        return "BookCustom{" +
                "categoryCustom=" + categoryCustom +
                '}';
    }
}
