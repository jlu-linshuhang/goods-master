package jlu.edu.cn.lsh.service;

import jlu.edu.cn.lsh.po.Book;
import jlu.edu.cn.lsh.po.BookCustom;

import java.util.List;

public interface BookService {

  /*  private int pc;//当前页码，页面传过来的
    private int tp;//总页数，tr/ps
    private int tr;//总记录数,sql查询
    private int ps;//每页记录数，自定义10，可修改
    private String url;//第一次查询时的条件，地址*/

    //cid
    public List<Book> findByCategory(int pc, String cid)throws Exception;
    public int findCountByCid(String cid)throws Exception;
    //author
    public List<Book> findBookByAuthor(int pc,String author)throws Exception;
    public int findCountByAuthor(String author)throws Exception;
    //press
    public List<Book> findBookByPress(int pc,String press)throws Exception;
    public int findCountByPress(String press)throws Exception;
    //bname
    public List<Book> findBookLikeBname(int pc,String bname)throws Exception;
    public int findCountLikeBname(String bname)throws Exception;
    //multiple
    public List<Book> findBookByMultiple(int pc,String author,String press,String bname)throws Exception;
    public int findCountByMultiple(String author,String press,String bname)throws Exception;
    //bid
    public BookCustom findBookByBid(String bid)throws Exception;


    //后台对图书的添加、修改、删除
    public void insertBook(Book book)throws Exception;
    public void updateBookByBid(Book book)throws Exception;
    public void deleteBookByBid(String bid)throws Exception;
}
