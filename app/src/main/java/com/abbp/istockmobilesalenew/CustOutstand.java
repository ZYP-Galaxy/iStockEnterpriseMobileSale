package com.abbp.istockmobilesalenew;

public class CustOutstand {
    private String merchantname,currencyshort;
    private String  openamount,saleamount,returnamount,discountamount,paidamount,closingbalance;

    public CustOutstand(String merchantname, String currencyshort, String openamount, String saleamount, String returnamount, String discountamount, String paidamount, String closingbalance) {
        this.merchantname = merchantname;
        this.currencyshort = currencyshort;
        this.openamount = openamount;
        this.saleamount = saleamount;
        this.returnamount = returnamount;
        this.discountamount = discountamount;
        this.paidamount = paidamount;
        this.closingbalance = closingbalance;
    }

    public String getMerchantname() {
        return merchantname;
    }

    public void setMerchantname(String merchantname) {
        this.merchantname = merchantname;
    }

    public String getCurrencyshort() {
        return currencyshort;
    }

    public void setCurrencyshort(String currencyshort) {
        this.currencyshort = currencyshort;
    }

    public String getOpenamount() {
        return openamount;
    }

    public void setOpenamount(String openamount) {
        this.openamount = openamount;
    }

    public String getSaleamount() {
        return saleamount;
    }

    public void setSaleamount(String saleamount) {
        this.saleamount = saleamount;
    }

    public String getReturnamount() {
        return returnamount;
    }

    public void setReturnamount(String returnamount) {
        this.returnamount = returnamount;
    }

    public String getDiscountamount() {
        return discountamount;
    }

    public void setDiscountamount(String discountamount) {
        this.discountamount = discountamount;
    }

    public String getPaidamount() {
        return paidamount;
    }

    public void setPaidamount(String paidamount) {
        this.paidamount = paidamount;
    }

    public String getClosingbalance() {
        return closingbalance;
    }

    public void setClosingbalance(String closingbalance) {
        this.closingbalance = closingbalance;
    }
}
