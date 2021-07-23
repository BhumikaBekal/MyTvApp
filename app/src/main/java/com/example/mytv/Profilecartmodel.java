package com.example.mytv;

public class Profilecartmodel {

    String finalcartchannel,finalcartprice;
    Profilecartmodel()
    {

    }

    public String getFinalcartchannel() {
        return finalcartchannel;
    }

    public void setFinalcartchannel(String finalcartchannel) {
        this.finalcartchannel = finalcartchannel;
    }

    public String getFinalcartprice() {
        return finalcartprice;
    }

    public void setFinalcartprice(String finalcartprice) {
        this.finalcartprice = finalcartprice;
    }

    public Profilecartmodel(String finalcartchannel, String finalcartprice) {
        this.finalcartchannel = finalcartchannel;
        this.finalcartprice = finalcartprice;

    }
}
