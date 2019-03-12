package jlu.edu.cn.lsh.serviceimp;

import jlu.edu.cn.lsh.allclass.PageConstants;
import jlu.edu.cn.lsh.mapper.BookMapper;
import jlu.edu.cn.lsh.po.*;
import jlu.edu.cn.lsh.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

public class BookServiceImp implements BookService{

    @Autowired
    private BookMapper bookMapper;
    //int ps = PageConstants.Book_Page_Size;//每页记录数
  /*  private int pc;//当前页码，页面传过来的
    private int tp;//总页数，tr/ps
    private int tr;//总记录数,sql查询
    private int ps;//每页记录数，自定义10，可修改
    private String url;//第一次查询时的条件，地址*/
    @Override
    public List<Book> findByCategory(int pc, String cid) throws Exception {

        int ps = PageConstants.Book_Page_Size;//每页记录数
        int startNumber = (pc-1)*ps;
        BookVo bookVo = new BookVo();
        bookVo.setStartNumber(startNumber);
        bookVo.setCid(cid);
        bookVo.setPc(pc);
        bookVo.setPs(ps);
        List<Book> books = bookMapper.findByCategory(bookVo);
        return books;
    }

    @Override
    public int findCountByCid(String cid) throws Exception {
        BookExample bookExample = new BookExample();
        bookExample.setDistinct(false);
        BookExample.Criteria criteria = bookExample.createCriteria();
        criteria.andCidEqualTo(cid);
        int count = bookMapper.countByExample(bookExample);
        return count;
    }

    @Override
    public List<Book> findBookByAuthor(int pc, String author) throws Exception {
        int ps = PageConstants.Book_Page_Size;//每页记录数
        int startNumber = (pc-1)*ps;
        BookVo bookVo = new BookVo();
        bookVo.setStartNumber(startNumber);
        bookVo.setPc(pc);
        bookVo.setPs(ps);
        bookVo.setAuthor(author);
        List<Book> books = bookMapper.findBookByAuthor(bookVo);
        return books;
    }

    @Override
    public int findCountByAuthor(String author) throws Exception {
        BookExample bookExample = new BookExample();
        bookExample.setDistinct(false);
        BookExample.Criteria criteria = bookExample.createCriteria();
        criteria.andAuthorEqualTo(author);
        int count = bookMapper.countByExample(bookExample);
        return count;
    }

    @Override
    public List<Book> findBookByPress(int pc, String press) throws Exception {
        int ps = PageConstants.Book_Page_Size;//每页记录数
        int startNumber = (pc-1)*ps;
        BookVo bookVo = new BookVo();
        bookVo.setStartNumber(startNumber);
        bookVo.setPc(pc);
        bookVo.setPs(ps);
        bookVo.setPress(press);
        List<Book> books = bookMapper.findBookByPress(bookVo);
        return books;
    }

    @Override
    public int findCountByPress(String press) throws Exception {
        BookExample bookExample = new BookExample();
        bookExample.setDistinct(false);
        BookExample.Criteria criteria = bookExample.createCriteria();
        criteria.andPressEqualTo(press);
        int count = bookMapper.countByExample(bookExample);
        return count;
    }

    @Override
    public List<Book> findBookLikeBname(int pc, String bname) throws Exception {
        int ps = PageConstants.Book_Page_Size;//每页记录数
        int startNumber = (pc-1)*ps;
        BookVo bookVo = new BookVo();
        bookVo.setStartNumber(startNumber);
        bookVo.setPc(pc);
        bookVo.setPs(ps);
        bookVo.setBname(bname);
        List<Book> books = bookMapper.findBookLikeBname(bookVo);
        return books;
    }

    @Override
    public int findCountLikeBname(String bname) throws Exception {
        BookExample bookExample = new BookExample();
        bookExample.setDistinct(false);
        BookExample.Criteria criteria = bookExample.createCriteria();
        criteria.andBnameLike("%"+bname+"%");
        int count = bookMapper.countByExample(bookExample);
        return count;
    }

    @Override
    public List<Book> findBookByMultiple(int pc, String author, String press, String bname) throws Exception {
        int ps = PageConstants.Book_Page_Size;//每页记录数
        int startNumber = (pc-1)*ps;
        BookVo bookVo = new BookVo();
        bookVo.setStartNumber(startNumber);
        bookVo.setPc(pc);
        bookVo.setPs(ps);
        bookVo.setAuthor(author);
        bookVo.setPress(press);
        bookVo.setBname(bname);
        List<Book> books = bookMapper.findBookByMultiple(bookVo);
        return books;
    }

    @Override
    public int findCountByMultiple(String author, String press, String bname) throws Exception {
        BookExample bookExample = new BookExample();
        bookExample.setDistinct(false);
        BookExample.Criteria criteria = bookExample.createCriteria();
        criteria.andAuthorLike("%"+author+"%").andPressLike("%"+press+"%").andBnameLike("%"+bname+"%");
        int count = bookMapper.countByExample(bookExample);
        return count;
    }

    @Override
    public BookCustom findBookByBid(String bid) throws Exception {
        BookCustom bookCustom = bookMapper.findBookByBid(bid);
        return bookCustom;
    }

    @Override
    public void insertBook(Book book) throws Exception {
        bookMapper.insertSelective(book);
    }

    @Override
    public void updateBookByBid(Book book) throws Exception {
        bookMapper.updateByPrimaryKeySelective(book);
    }

    @Override
    public void deleteBookByBid(String bid) throws Exception {
        bookMapper.deleteByPrimaryKey(bid);
    }
}
