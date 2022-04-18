package com.abbp.istockmobilesalenew;

public class StockStatus {
    private String usrcode;
    private String description;
    private String saleamount;
    private String balanceqty;

    public StockStatus(String usrcode, String description, String saleamount, String balanceqty) {
        this.usrcode = usrcode;
        this.description = description;
        this.saleamount = saleamount;
        this.balanceqty = balanceqty;
    }

    public String getUsrcode() {
        return usrcode;
    }

    public void setUsrcode(String usrcode) {
        this.usrcode = usrcode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSaleamount() {
        return saleamount;
    }

    public void setSaleamount(String saleamount) {
        this.saleamount = saleamount;
    }

    public String getBalanceqty() {
        return balanceqty;
    }

    public void setBalanceqty(String balanceqty) {
        this.balanceqty = balanceqty;
    }

    //    public StockStatus(String usrcode, String description, double saleamount, double balanceqty) {
//        this.usrcode = usrcode;
//        this.description = description;
//        this.saleamount = saleamount;
//        this.balanceqty = balanceqty;
//    }
//
//    public String getUsrcode() {
//        return usrcode;
//    }
//
//    public void setUsrcode(String usrcode) {
//        this.usrcode = usrcode;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public double getSaleamount() {
//        return saleamount;
//    }
//
//    public void setSaleamount(double saleamount) {
//        this.saleamount = saleamount;
//    }
//
//    public double getBalanceqty() {
//        return balanceqty;
//    }
//
//    public void setBalanceqty(double balanceqty) {
//        this.balanceqty = balanceqty;
//    }
}
