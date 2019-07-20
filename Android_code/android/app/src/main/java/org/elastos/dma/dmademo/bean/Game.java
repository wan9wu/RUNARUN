package org.elastos.dma.dmademo.bean;

public class Game {

    private String id;
    private String name;
    private String production;
    private String condition;
    private String price;
    private String count;
    private String remark;
    private String hashaddress;
    private String trestaddress;
    private String location;
    private String time;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProduction() {
        return production;
    }

    public void setProduction(String production) {
        this.production = production;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getHashaddress() {
        return hashaddress;
    }

    public void setHashaddress(String hashaddress) {
        this.hashaddress = hashaddress;
    }

    public String getTrestaddress() {
        return trestaddress;
    }

    public void setTrestaddress(String trestaddress) {
        this.trestaddress = trestaddress;
    }

    public String getContractaddress() {
        return contractaddress;
    }

    public void setContractaddress(String contractaddress) {
        this.contractaddress = contractaddress;
    }

    private String contractaddress;

}
