package jlu.edu.cn.lsh.controller;

import jlu.edu.cn.lsh.allclass.PageConstants;
import jlu.edu.cn.lsh.po.Book;
import jlu.edu.cn.lsh.po.BookCustom;
import jlu.edu.cn.lsh.po.BookVo;
import jlu.edu.cn.lsh.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    //获取页面上的页码，传入其它出了数字以外的，抛异常，设为默认值1
    private int getPc(HttpServletRequest request){
        int pc = 1;
        String param = request.getParameter("pc");
        if (param != null&&!param.trim().isEmpty()){
            try {
                pc = Integer.parseInt(param);
            }catch (RuntimeException e){ }
        }
        return pc;
    }

    //获取url /goods/book/findBookByCategory.action?cid=xxx&pc=xxx
    private String getUrl(HttpServletRequest request){
        String s1 = request.getRequestURI();//获得？前内容
        String s2 = request.getQueryString();//获得？后内容
        String s3 = s1+"?"+s2;
        String url;
        int index = s3.lastIndexOf("&pc=");
        if (index !=-1){
            url = s3.substring(0,index);
        }else {
            url = s3;
        }
        return url;
    }

    //按分类查询
    @RequestMapping("/findBookByCategory")
    public String findBookByCategory(HttpServletResponse response, HttpServletRequest request, Model model) throws Exception {
    /*  private int pc;//当前页码，页面传过来的
        private int tp;//总页数，tr/ps
        private int tr;//总记录数,sql查询
        private int ps;//每页记录数，自定义10，可修改
        private String url;//第一次查询时的条件，地址*/
        int pc = getPc(request);
        String url = getUrl(request);
        int ps = PageConstants.Book_Page_Size;
        String cid = request.getParameter("cid");
        int tr = bookService.findCountByCid(cid);
        int tp = (tr % ps==0)?(tr/ps):(tr/ps+1);


        List<Book> bookList = bookService.findByCategory(pc,cid);
        BookVo bookVo = new BookVo();
        bookVo.setPs(ps);
        bookVo.setPc(pc);
        bookVo.setTp(tp);
        bookVo.setTr(tr);
        bookVo.setUrl(url);
        bookVo.setCid(cid);

        request.setAttribute("pb",bookVo);
        request.setAttribute("booklist",bookList);
        return "/jsps/book/list.jsp";
    }

    //按作者查询
    @RequestMapping("/findBookByAuthor")
    public String findBookByAuthor(HttpServletResponse response, HttpServletRequest request, Model model) throws Exception {

        int pc = getPc(request);
        String url = getUrl(request);
        int ps = PageConstants.Book_Page_Size;
        String author = request.getParameter("author");
        int tr = bookService.findCountByAuthor(author);
        int tp = (tr % ps==0)?(tr/ps):(tr/ps+1);

        List<Book> bookList = bookService.findBookByAuthor(pc,author);
        BookVo bookVo = new BookVo();
        bookVo.setPs(ps);
        bookVo.setPc(pc);
        bookVo.setTp(tp);
        bookVo.setTr(tr);
        bookVo.setUrl(url);
        bookVo.setAuthor(author);

        request.setAttribute("pb",bookVo);
        request.setAttribute("booklist",bookList);
        return "/jsps/book/list.jsp";
    }

    //按出版社查询
    @RequestMapping("/findBookByPress")
    public String findBookByPress(HttpServletResponse response, HttpServletRequest request, Model model) throws Exception {

        int pc = getPc(request);
        String url = getUrl(request);
        int ps = PageConstants.Book_Page_Size;
        String press = request.getParameter("press");
        int tr = bookService.findCountByPress(press);
        int tp = (tr % ps==0)?(tr/ps):(tr/ps+1);

        List<Book> bookList = bookService.findBookByPress(pc,press);
        BookVo bookVo = new BookVo();
        bookVo.setPs(ps);
        bookVo.setPc(pc);
        bookVo.setTp(tp);
        bookVo.setTr(tr);
        bookVo.setUrl(url);
        bookVo.setPress(press);

        request.setAttribute("pb",bookVo);
        request.setAttribute("booklist",bookList);
        return "/jsps/book/list.jsp";
    }

    //按书名模糊查询
    @RequestMapping("/findBookLikeBname")
    public String findBookLikeBname(HttpServletResponse response, HttpServletRequest request, Model model) throws Exception {

        int pc = getPc(request);
        String url = getUrl(request);
        int ps = PageConstants.Book_Page_Size;
        String bname = request.getParameter("bname");
        int tr = bookService.findCountLikeBname(bname);
        int tp = (tr % ps==0)?(tr/ps):(tr/ps+1);

        List<Book> bookList = bookService.findBookLikeBname(pc,bname);
        BookVo bookVo = new BookVo();
        bookVo.setPs(ps);
        bookVo.setPc(pc);
        bookVo.setTp(tp);
        bookVo.setTr(tr);
        bookVo.setUrl(url);
        bookVo.setBname(bname);

        request.setAttribute("pb",bookVo);
        request.setAttribute("booklist",bookList);
        return "/jsps/book/list.jsp";
    }

    //多条件组合查询
    @RequestMapping("/findBookByMuliple")
    public String findBookByMuliple(HttpServletResponse response, HttpServletRequest request, Model model) throws Exception {

        int pc = getPc(request);
        String url = getUrl(request);
        int ps = PageConstants.Book_Page_Size;
        String author = request.getParameter("author");
        String press = request.getParameter("press");
        String bname = request.getParameter("bname");
        int tr = bookService.findCountByMultiple(author,press,bname);
        int tp = (tr % ps==0)?(tr/ps):(tr/ps+1);

        List<Book> bookList = bookService.findBookByMultiple(pc,author,press,bname);
        BookVo bookVo = new BookVo();
        bookVo.setPs(ps);
        bookVo.setPc(pc);
        bookVo.setTp(tp);
        bookVo.setTr(tr);
        bookVo.setUrl(url);
        bookVo.setAuthor(author);
        bookVo.setPress(press);
        bookVo.setBname(bname);

        request.setAttribute("pb",bookVo);
        request.setAttribute("booklist",bookList);
        return "/jsps/book/list.jsp";
    }

    //按bid查询
    @RequestMapping("/load")
    public String load(HttpServletResponse response, HttpServletRequest request, Model model) throws Exception{
        String bid = request.getParameter("bid");
        BookCustom bookCustom = bookService.findBookByBid(bid);
        request.setAttribute("book",bookCustom);
        return "forward:/jsps/book/desc.jsp";
    }
}
