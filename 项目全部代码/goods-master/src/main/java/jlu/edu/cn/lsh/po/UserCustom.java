package jlu.edu.cn.lsh.po;

public class UserCustom extends User {
    private String reloginpass;
    private String verifyCode;
    private String newloginpass;

    @Override
    public String toString() {
        return "UserCustom{" +
                "reloginpass='" + reloginpass + '\'' +
                ", verifyCode='" + verifyCode + '\'' +
                ", newloginpass='" + newloginpass + '\'' +
                '}';
    }

    public String getReloginpass() {
        return reloginpass;
    }

    public void setReloginpass(String reloginpass) {
        this.reloginpass = reloginpass;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getNewloginpass() {
        return newloginpass;
    }

    public void setNewloginpass(String newloginpass) {
        this.newloginpass = newloginpass;
    }
}
