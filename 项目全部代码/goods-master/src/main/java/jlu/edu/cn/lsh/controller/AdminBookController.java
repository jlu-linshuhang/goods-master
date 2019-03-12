package jlu.edu.cn.lsh.controller;


import jlu.edu.cn.lsh.allclass.PageConstants;
import jlu.edu.cn.lsh.po.Book;
import jlu.edu.cn.lsh.po.BookCustom;
import jlu.edu.cn.lsh.po.BookVo;
import jlu.edu.cn.lsh.po.CategoryCustom;
import jlu.edu.cn.lsh.service.BookService;
import jlu.edu.cn.lsh.service.CategoryService;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/adminBook")
public class AdminBookController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BookService bookService;

    //查询所有分类
    @RequestMapping("/findAllCategory")
    public String findAllCategory(HttpServletResponse response, HttpServletRequest request, Model model)throws Exception{
        List<CategoryCustom> categoryCustomList =  categoryService.findAllClass();
        request.setAttribute("parents" ,categoryCustomList);
        return "forward:/adminjsps/admin/book/left.jsp";
    }

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
        return "/adminjsps/admin/book/list.jsp";
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
        return "/adminjsps/admin/book/list.jsp";
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
        return "/adminjsps/admin/book/list.jsp";
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
        return "/adminjsps/admin/book/list.jsp";
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
        return "/adminjsps/admin/book/list.jsp";
    }

    //按bid查询
    @RequestMapping("/load")
    public String load(HttpServletResponse response, HttpServletRequest request, Model model) throws Exception{
        String bid = request.getParameter("bid");
        BookCustom bookCustom = bookService.findBookByBid(bid);
        List<CategoryCustom> parents = categoryService.findFirstClass();

        String cid = bookCustom.getCid();
        CategoryCustom categoryCustom = categoryService.findClassByCid(cid);
        String pid = categoryCustom.getPid();
        List<CategoryCustom> children = categoryService.findSecondClassByPid(pid);

        CategoryCustom categoryCustom1 = categoryService.findClassByCid(pid);
        bookCustom.setCategoryCustom(categoryCustom);
        bookCustom.getCategoryCustom().setParent(categoryCustom1);


        request.setAttribute("parents",parents);
        request.setAttribute("children",children);
        request.setAttribute("book",bookCustom);
        return "forward:/adminjsps/admin/book/desc.jsp";
        //<c:if test="">selected="selected"</c:if>
    }

    //添加图书查找一级分类
    @RequestMapping("/addPre")
    public String addPre(HttpServletResponse response, HttpServletRequest request, Model model) throws Exception{
        List<CategoryCustom> categoryCustomList = categoryService.findFirstClass();
        request.setAttribute("parents",categoryCustomList);
        return "forward:/adminjsps/admin/book/add.jsp";
    }

    //添加图书加载二级分类
    @RequestMapping("/ajaxFindChildren")
    public @ResponseBody List<CategoryCustom> ajaxFindChildren(HttpServletResponse response, HttpServletRequest request, Model model) throws Exception{
        String pid = request.getParameter("pid");
        return categoryService.findSecondClassByPid(pid);
    }

    //添加图书
    @RequestMapping("/insertBook")
    public String insertBook(HttpServletResponse response, HttpServletRequest request, Model model,Book book, @RequestParam(value = "item_imageB", required = false) MultipartFile item_imageB, @RequestParam(value = "item_imageW", required = false) MultipartFile item_imageW) throws Exception{

        String originalImageBName = item_imageB.getOriginalFilename();
        String originalImageWName = item_imageW.getOriginalFilename();
        if (item_imageB!=null&&originalImageBName!=null&&originalImageBName.length()!=0){
            String imageBPath = "F:\\project1222\\goods-master\\src\\main\\webapp\\";
            String newImageBPath = "book_img/"+UUID.randomUUID().toString().replace("-","").substring(0,16)+"_"+originalImageBName;
            File imageBFile = new File(imageBPath+newImageBPath);
            item_imageB.transferTo(imageBFile);
            book.setImageB(newImageBPath);

            String s1 = imageBPath+newImageBPath;
            ImageIcon imageIcon = new ImageIcon(s1);
            Image image = imageIcon.getImage();
            int height = image.getHeight(null);
            int width = image.getWidth(null);
            if (height>200||width>200){
                imageBFile.delete();
                List<CategoryCustom> categoryCustomList = categoryService.findFirstClass();
                request.setAttribute("msg","您上传的小图照片尺寸太大！");
                request.setAttribute("parents",categoryCustomList);
                return "/adminjsps/admin/book/add.jsp";
            }
        }

        if (item_imageW!=null&&originalImageWName!=null&&originalImageWName.length()!=0){
            String imageWPath = "F:\\project1222\\goods-master\\src\\main\\webapp\\";
            String newImageWPath = "book_img/"+UUID.randomUUID().toString().replace("-","").substring(0,16)+"_"+originalImageWName;
            File imageWFile = new File(imageWPath+newImageWPath);
            item_imageW.transferTo(imageWFile);
            book.setImageW(newImageWPath);

            String s2 = imageWPath+newImageWPath;
            ImageIcon imageIcon = new ImageIcon(s2);
            Image image = imageIcon.getImage();
            int height = image.getHeight(null);
            int width = image.getWidth(null);
            if (height>350||width>350){
                imageWFile.delete();
                List<CategoryCustom> categoryCustomList = categoryService.findFirstClass();
                request.setAttribute("msg","您上传的大图照片尺寸太大！");
                request.setAttribute("parents",categoryCustomList);
                return "/adminjsps/admin/book/add.jsp";
            }
        }
        book.setBid(UUID.randomUUID().toString().replace("-","").substring(0,32));
        bookService.insertBook(book);
        request.setAttribute("msg", "恭喜添加图书成功");
        return "/adminjsps/msg.jsp";
    }

    //编辑图书
    @RequestMapping("/editBook")
    public String editBook(HttpServletResponse response,HttpServletRequest request, Model model,Book book)throws Exception{

        String method = request.getParameter("method");
        if (method.equals("edit")){
            String cid = request.getParameter("cid");
            book.setCid(cid);
            book.setImageB(null);
            book.setImageW(null);
            bookService.updateBookByBid(book);
            request.setAttribute("msg","修改图书成功！");
            return "forward:/adminjsps/msg.jsp";
        }
        if (method.equals("delete")){
            String bid = request.getParameter("bid");
            BookCustom bookCustom = bookService.findBookByBid(bid);
            String path = "F:\\project1222\\goods-master\\src\\main\\webapp\\";
            String imageBPath = bookCustom.getImageB();
            String imageWPath = bookCustom.getImageW();
            new File(path+imageBPath).delete();
            new File(path+imageWPath).delete();
            bookService.deleteBookByBid(bid);
            request.setAttribute("msg","删除图书成功！");
            return "forward:/adminjsps/msg.jsp";
        }
        request.setAttribute("msg","错误信息！");
        return "forward:/adminjsps/msg.jsp";
    }

}
