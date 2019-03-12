package jlu.edu.cn.lsh.po;

import java.math.BigDecimal;

public class CartitemVo {
    private String bid;

    private String bname;

    private String author;

    private BigDecimal price;

    private BigDecimal currprice;

    private BigDecimal discount;

    private String press;

    private String publishtime;

    private Integer edition;

    private Integer pagenum;

    private Integer wordnum;

    private String printtime;

    private Integer booksize;

    private String paper;

    private String cid;

    private String imageW;

    private String imageB;

    private Integer orderby;


    private Integer quantity;

    private BigDecimal subtotal;

    private String cartitemid;

    @Override
    public String toString() {
        return "CartitemVo{" +
                "bid='" + bid + '\'' +
                ", bname='" + bname + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                ", currprice=" + currprice +
                ", discount=" + discount +
                ", press='" + press + '\'' +
                ", publishtime='" + publishtime + '\'' +
                ", edition=" + edition +
                ", pagenum=" + pagenum +
                ", wordnum=" + wordnum +
                ", printtime='" + printtime + '\'' +
                ", booksize=" + booksize +
                ", paper='" + paper + '\'' +
                ", cid='" + cid + '\'' +
                ", imageW='" + imageW + '\'' +
                ", imageB='" + imageB + '\'' +
                ", orderby=" + orderby +
                ", quantity=" + quantity +
                ", subtotal=" + subtotal +
                ", cartitemid='" + cartitemid + '\'' +
                '}';
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getCurrprice() {
        return currprice;
    }

    public void setCurrprice(BigDecimal currprice) {
        this.currprice = currprice;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public String getPublishtime() {
        return publishtime;
    }

    public void setPublishtime(String publishtime) {
        this.publishtime = publishtime;
    }

    public Integer getEdition() {
        return edition;
    }

    public void setEdition(Integer edition) {
        this.edition = edition;
    }

    public Integer getPagenum() {
        return pagenum;
    }

    public void setPagenum(Integer pagenum) {
        this.pagenum = pagenum;
    }

    public Integer getWordnum() {
        return wordnum;
    }

    public void setWordnum(Integer wordnum) {
        this.wordnum = wordnum;
    }

    public String getPrinttime() {
        return printtime;
    }

    public void setPrinttime(String printtime) {
        this.printtime = printtime;
    }

    public Integer getBooksize() {
        return booksize;
    }

    public void setBooksize(Integer booksize) {
        this.booksize = booksize;
    }

    public String getPaper() {
        return paper;
    }

    public void setPaper(String paper) {
        this.paper = paper;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getImageW() {
        return imageW;
    }

    public void setImageW(String imageW) {
        this.imageW = imageW;
    }

    public String getImageB() {
        return imageB;
    }

    public void setImageB(String imageB) {
        this.imageB = imageB;
    }

    public Integer getOrderby() {
        return orderby;
    }

    public void setOrderby(Integer orderby) {
        this.orderby = orderby;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public String getCartitemid() {
        return cartitemid;
    }

    public void setCartitemid(String cartitemid) {
        this.cartitemid = cartitemid;
    }
}
