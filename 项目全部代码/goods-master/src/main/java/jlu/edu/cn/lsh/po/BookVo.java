package jlu.edu.cn.lsh.po;

public class BookVo {
    private BookCustom bookCustom;

    private int pc;//当前页码
    private int tp;//总页数
    private int tr;//总记录数
    private int ps;//每页记录数
    private String url;//第一次查询时的条件，地址

    private String author;
    private String bname;
    private String press;
    private int startNumber;
    private String cid;

    @Override
    public String toString() {
        return "BookVo{" +
                "bookCustom=" + bookCustom +
                ", pc=" + pc +
                ", tp=" + tp +
                ", tr=" + tr +
                ", ps=" + ps +
                ", url='" + url + '\'' +
                ", author='" + author + '\'' +
                ", bname='" + bname + '\'' +
                ", press='" + press + '\'' +
                ", startNumber=" + startNumber +
                ", cid='" + cid + '\'' +
                '}';
    }

    public BookCustom getBookCustom() {
        return bookCustom;
    }

    public void setBookCustom(BookCustom bookCustom) {
        this.bookCustom = bookCustom;
    }

    public int getPc() {
        return pc;
    }

    public void setPc(int pc) {
        this.pc = pc;
    }

    public int getTp() {
        return tp;
    }

    public void setTp(int tp) {
        this.tp = tp;
    }

    public int getTr() {
        return tr;
    }

    public void setTr(int tr) {
        this.tr = tr;
    }

    public int getPs() {
        return ps;
    }

    public void setPs(int ps) {
        this.ps = ps;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public int getStartNumber() {
        return startNumber;
    }

    public void setStartNumber(int startNumber) {
        this.startNumber = startNumber;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }
}
