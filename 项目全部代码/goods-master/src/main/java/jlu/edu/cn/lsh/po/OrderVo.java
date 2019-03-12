package jlu.edu.cn.lsh.po;

public class OrderVo {

    private int pc;//当前页码
    private int tp;//总页数
    private int tr;//总记录数
    private int ps;//每页记录数
    private String url;//第一次查询时的条件，地址

    private int startNumber;
    private String uid;
    private int status;

    @Override
    public String toString() {
        return "OrderVo{" +
                "pc=" + pc +
                ", tp=" + tp +
                ", tr=" + tr +
                ", ps=" + ps +
                ", url='" + url + '\'' +
                ", startNumber=" + startNumber +
                ", uid='" + uid + '\'' +
                ", status=" + status +
                '}';
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

    public int getStartNumber() {
        return startNumber;
    }

    public void setStartNumber(int startNumber) {
        this.startNumber = startNumber;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
