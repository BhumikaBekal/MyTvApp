package com.example.mytv;

public class RCmodel {
    String logo,price,cname;

    public RCmodel(String logo, String price, String cname) {
        this.logo = logo;
        this.price = price;
        this.cname = cname;
    }

    public RCmodel() {
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }
}
