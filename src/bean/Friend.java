package bean;

public class Friend {
    private String num;
    private String name;
    private  String remark;

    public Friend() {
    }

    public Friend(String num) {
        this.num = num;
    }

    public Friend(String num, String name, String remark) {
        this.num = num;
        this.name = name;
        this.remark = remark;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
