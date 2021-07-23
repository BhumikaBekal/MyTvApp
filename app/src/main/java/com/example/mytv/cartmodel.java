package com.example.mytv;

public class cartmodel {

    String tempcartchannel,tempcartprice;
    cartmodel()
    {

    }
    public cartmodel(String tempcartchannel, String tempcartprice) {
        this.tempcartchannel = tempcartchannel;
        this.tempcartprice = tempcartprice;

    }
    public String getTempcartchannel() {
        return tempcartchannel;
    }

    public void setTempcartchannel(String tempcartchannel) {
        this.tempcartchannel = tempcartchannel;
    }

    public String getTempcartprice() {
        return tempcartprice;
    }

    public void setTempcartprice(String tempcartprice) {
        this.tempcartprice = tempcartprice;
    }


}
