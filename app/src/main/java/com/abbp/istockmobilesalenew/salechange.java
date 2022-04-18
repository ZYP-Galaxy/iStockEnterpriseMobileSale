package com.abbp.istockmobilesalenew;

public class salechange {

private int tranid,currencyid;
private double paidamount;
private double changeamount,exgrate,invoiceamount;

    public salechange(int tranid, int currencyid, double paidamount, double changeamount, double exgrate, double invoiceamount) {
        this.tranid = tranid;
        this.currencyid = currencyid;
        this.paidamount = paidamount;
        this.changeamount = changeamount;
        this.exgrate = exgrate;
        this.invoiceamount = invoiceamount;
    }
}
