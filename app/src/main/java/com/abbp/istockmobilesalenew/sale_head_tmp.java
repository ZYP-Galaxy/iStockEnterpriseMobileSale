package com.abbp.istockmobilesalenew;

import androidx.annotation.Nullable;

public class sale_head_tmp {
//    -- Table: public.sale_head_main
//
//-- DROP TABLE public.sale_head_main;
//
//CREATE TABLE public.sale_head_main
//(
//    tranid integer NOT NULL,
//    userid smallint NOT NULL,
//    docid character varying(100) COLLATE pg_catalog."default",
//    date date,
//    fromdate date,
//    todate date,
//    issaleconsign boolean DEFAULT false,
//    invoiceno character varying(100) COLLATE pg_catalog."default",
//    locationid smallint,
//    townshipid smallint,
//    customerid integer,
//    paytypeid smallint,
//    salecurr smallint,
//    discountamount double precision,
//    paidamount double precision,
//    invoiceamount double precision,
//    advpayamount double precision,
//    invoiceqty numeric(19,6),
//    soid integer,
//    refno character varying(100) COLLATE pg_catalog."default",
//    isdeleted boolean,
//    taxpercent numeric(10,4),
//    taxamount double precision,
//    focamount double precision,
//    netamount double precision,
//    cbjntranid integer,
//    cashid smallint,
//    voucherremark character varying(1000) COLLATE pg_catalog."default",
//    exgrate numeric(10,4),
//    delid integer,
//    dueindays smallint,
//    isdeliver boolean,
//    membercardid character varying(200) COLLATE pg_catalog."default",
//    memberdiscount double precision,
//    memberamount double precision,
//    deldocid character varying(100) COLLATE pg_catalog."default",
//    jobid smallint,
//    issplit boolean,
//    enqdescription character varying(500) COLLATE pg_catalog."default",
//    departuredate date,
//    voucherremark1 character varying(1000) COLLATE pg_catalog."default",
//    voucherremark2 character varying(1000) COLLATE pg_catalog."default",
//    voucherremark3 character varying(1000) COLLATE pg_catalog."default",
//    mulitlocbranchid smallint,
//    consignid integer,
//    isprinted boolean,
//    isexported character varying(50) COLLATE pg_catalog."default",
//    toexport boolean,
//    exporteduser smallint,
//    iscashreceive boolean NOT NULL,
//    isconfirmed boolean,
//    giftcardamount double precision,
//    giftcardid character varying(200) COLLATE pg_catalog."default",
//    roomtaxamount double precision,
//    advancedmoney double precision,
//    isreserved boolean,
//    reservedname character varying(50) COLLATE pg_catalog."default",
//    checkintime timestamp without time zone,
//    starttime timestamp without time zone,
//    endtime timestamp without time zone,
//    creditcardid smallint,
//    customercount smallint,
//    shiftid integer,
//    shiftname character varying(25) COLLATE pg_catalog."default",
//    fastfoodtag character varying(50) COLLATE pg_catalog."default",
//    commissionamount double precision,
//    discountpercent double precision,
//    salesmenid integer,
//    tablenameid integer,
//    governmenttaxpercent smallint,
//    govermenttaxamount double precision,
//    servicetaxpercent smallint,
//    servicetaxamount double precision,
//    roomtaxpercent smallint,
//    roomid integer,
//    reftranid integer,
//    isordertoroom smallint,
//    CONSTRAINT pk_sale_head_main PRIMARY KEY (tranid)
//)
//WITH (
//    OIDS = FALSE
//)
//TABLESPACE pg_default;
//
//ALTER TABLE public.sale_head_main
//    OWNER to postgres;
//COMMENT ON TABLE public.sale_head_main
//    IS 'TRIAL';
    private String offlinetranid;

    private int userid;
    private String docid;
    private String date;
    private String invoiceno;
    private int locationid, customerid, cashid, townshipid, paytypeid,  salecurr;
    private int dueindays;
    @Nullable
    private Integer dueinday=null;
    private double discountamount, paidamount, invoiceamount, invoiceqty, focamount, netamount, paidpercent;
    private String voucherremark;
    private double taxamount, taxpercent, discountpercent, exgrate, advpayamount;
    private boolean iscashreceive;
    private int tranid;
    private boolean isdeliver, isuseecommerce;
    private String salesmenids;

    public sale_head_tmp(int tranid, int userid, String docid, String date, String invoiceno, int locationid, int customerid, int cashid, int townshipid, int paytypeid, Integer dueinday, int salecurr, double discountamount, double paidamount, double invoiceamount, double invoiceqty, double focamount, double netamount, String voucherremark, double taxamount, double taxpercent, double discountpercent, double exgrate, boolean iscashreceive, boolean isdeliver, String salesmenids, double advpayamount, double paidpercent) {
        this.tranid = tranid;
        this.userid = userid;
        this.docid = docid;
        this.date = date;
        this.invoiceno = invoiceno;
        this.locationid = locationid;
        this.customerid = customerid;
        this.cashid = cashid;
        this.townshipid = townshipid;
        this.paytypeid = paytypeid;
        this.dueinday = dueinday;
        this.salecurr = salecurr;
        this.discountamount = discountamount;
        this.paidamount = paidamount;
        this.invoiceamount = invoiceamount;
        this.invoiceqty = invoiceqty;
        this.focamount = focamount;
        this.netamount = netamount;
        this.voucherremark = voucherremark;
        this.taxamount = taxamount;
        this.taxpercent = taxpercent;
        this.discountpercent = discountpercent;
        this.exgrate = exgrate;
        this.iscashreceive = iscashreceive;
        this.isdeliver = isdeliver;
        this.salesmenids = salesmenids;
        this.paidpercent = paidpercent;

    }


    public boolean isIscashreceive() {
        return iscashreceive;
    }

    public void setIscashreceive(boolean iscashreceive) {
        this.iscashreceive = iscashreceive;
    }

    public sale_head_tmp(String offlinetranid, int userid, String docid, String date, String invoiceno, int locationid, int customerid, int cashid, int townshipid, int paytypeid, int dueindays, int salecurr, double discountamount, double paidamount, double invoiceamount, double invoiceqty, double focamount, double netamount, String voucherremark, double taxamount, double taxpercent, double discountpercent, double exgrate, boolean iscashreceive, int tranid, boolean isdeliver, String salesmenids, double paidpercent) {
        this.offlinetranid = offlinetranid;
        this.tranid = tranid;
        this.userid = userid;
        this.docid = docid;
        this.date = date;
        this.invoiceno = invoiceno;
        this.locationid = locationid;
        this.customerid = customerid;
        this.cashid = cashid;
        this.townshipid = townshipid;
        this.paytypeid = paytypeid;
        this.dueindays = dueindays;
        this.salecurr = salecurr;
        this.discountamount = discountamount;
        this.paidamount = paidamount;
        this.invoiceamount = invoiceamount;
        this.invoiceqty = invoiceqty;
        this.focamount = focamount;
        this.netamount = netamount;
        this.voucherremark = voucherremark;
        this.taxamount = taxamount;
        this.taxpercent = taxpercent;
        this.discountpercent = discountpercent;
        this.exgrate = exgrate;
        this.iscashreceive = iscashreceive;
        this.isdeliver = isdeliver;
        this.salesmenids = salesmenids;
        this.paidpercent = paidpercent;
    }

    public sale_head_tmp(int tranid, int userid, String docid, String date, String invoiceno, int locationid, int customerid, int cashid, int townshipid, int paytypeid, int dueindays, int salecurr, double discountamount, double paidamount, double invoiceamount, double invoiceqty, double focamount, double netamount, String voucherremark, double taxamount, double taxpercent, double discountpercent, double exgrate, boolean iscashreceive, boolean isdeliver, String salesmenids, double advpayamount, double paidpercent) {
        this.tranid = tranid;
        this.userid = userid;
        this.docid = docid;
        this.date = date;
        this.invoiceno = invoiceno;
        this.locationid = locationid;
        this.customerid = customerid;
        this.cashid = cashid;
        this.townshipid = townshipid;
        this.paytypeid = paytypeid;
        this.dueindays = dueindays;
        this.salecurr = salecurr;
        this.discountamount = discountamount;
        this.paidamount = paidamount;
        this.invoiceamount = invoiceamount;
        this.invoiceqty = invoiceqty;
        this.focamount = focamount;
        this.netamount = netamount;
        this.voucherremark = voucherremark;
        this.taxamount = taxamount;
        this.taxpercent = taxpercent;
        this.discountpercent = discountpercent;
        this.exgrate = exgrate;
        this.iscashreceive = iscashreceive;
        this.isdeliver = isdeliver;
        this.salesmenids = salesmenids;
        this.paidpercent = paidpercent;

    }


    public sale_head_tmp(int tranid, int userid, String docid, String date, String invoiceno, int locationid, int customerid, int cashid, int townshipid, int paytypeid, int dueindays, int salecurr, double discountamount, double paidamount, double invoiceamount, double invoiceqty, double focamount, double netamount, String voucherremark, double taxamount, double taxpercent, double discountpercent, double exgrate, double paidpercent) {
        this.tranid = tranid;
        this.userid = userid;
        this.docid = docid;
        this.date = date;
        this.invoiceno = invoiceno;
        this.locationid = locationid;
        this.customerid = customerid;
        this.cashid = cashid;
        this.townshipid = townshipid;
        this.paytypeid = paytypeid;
        this.dueindays = dueindays;
        this.salecurr = salecurr;
        this.discountamount = discountamount;
        this.paidamount = paidamount;
        this.invoiceamount = invoiceamount;
        this.invoiceqty = invoiceqty;
        this.focamount = focamount;
        this.netamount = netamount;
        this.voucherremark = voucherremark;
        this.taxamount = taxamount;
        this.taxpercent = taxpercent;
        this.discountpercent = discountpercent;
        this.exgrate = exgrate;
        this.paidpercent = paidpercent;
    }

    public String getOfflinetranid() {
        return offlinetranid;
    }

    public void setOfflinetranid(String offlinetranid) {
        this.offlinetranid = offlinetranid;
    }

    public int getTranid() {
        return tranid;
    }

    public void setTranid(int tranid) {
        this.tranid = tranid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getInvoiceno() {
        return invoiceno;
    }

    public void setInvoiceno(String invoiceno) {
        this.invoiceno = invoiceno;
    }

    public int getLocationid() {
        return locationid;
    }

    public void setLocationid(int locationid) {
        this.locationid = locationid;
    }

    public int getCustomerid() {
        return customerid;
    }

    public void setCustomerid(int customerid) {
        this.customerid = customerid;
    }

    public int getCashid() {
        return cashid;
    }

    public void setCashid(int cashid) {
        this.cashid = cashid;
    }

    public int getTownshipid() {
        return townshipid;
    }

    public void setTownshipid(int townshipid) {
        this.townshipid = townshipid;
    }

    public int getPaytypeid() {
        return paytypeid;
    }

    public void setPaytypeid(int paytypeid) {
        this.paytypeid = paytypeid;
    }

    public int getDueindays() {
        return dueindays;
    }

    public void setDueindays(int dueindays) {
        this.dueindays = dueindays;
    }

    public int getSalecurr() {
        return salecurr;
    }

    public void setSalecurr(int salecurr) {
        this.salecurr = salecurr;
    }

    public double getDiscountamount() {
        return discountamount;
    }

    public void setDiscountamount(double discountamount) {
        this.discountamount = discountamount;
    }

    public double getPaidamount() {
        return paidamount;
    }

    public void setPaidamount(double paidamount) {
        this.paidamount = paidamount;
    }

    public double getInvoiceamount() {
        return invoiceamount;
    }

    public void setInvoiceamount(double invoiceamount) {
        this.invoiceamount = invoiceamount;
    }

    public double getInvoiceqty() {
        return invoiceqty;
    }

    public void setInvoiceqty(double invoiceqty) {
        this.invoiceqty = invoiceqty;
    }

    public double getFocamount() {
        return focamount;
    }

    public void setFocamount(double focamount) {
        this.focamount = focamount;
    }

    public double getNetamount() {
        return netamount;
    }

    public void setNetamount(double netamount) {
        this.netamount = netamount;
    }

    public String getVoucherremark() {
        return voucherremark;
    }

    public void setVoucherremark(String voucherremark) {
        this.voucherremark = voucherremark;
    }

    public double getTaxamount() {
        return taxamount;
    }

    public void setTaxamount(double taxamount) {
        this.taxamount = taxamount;
    }

    public double getTaxpercent() {
        return taxpercent;
    }

    public void setTaxpercent(double taxpercent) {
        this.taxpercent = taxpercent;
    }

    public double getDiscountpercent() {
        return discountpercent;
    }

    public void setDiscountpercent(double discountpercent) {
        this.discountpercent = discountpercent;
    }

    public double getExgrate() {
        return exgrate;
    }

    public void setExgrate(double exgrate) {
        this.exgrate = exgrate;
    }

    public boolean isIsdeliver() {
        return isdeliver;
    }

    public void setIsdeliver(boolean isdeliver) {
        this.isdeliver = isdeliver;
    }

    public double getAdvpayamount() {
        return advpayamount;
    }

    public void setAdvpayamount(double advpayamount) {
        this.advpayamount = advpayamount;
    }

    public double getPaidpercent() {
        return paidpercent;
    }

    public void setPaidpercent(double paidpercent) {
        this.paidpercent = paidpercent;
    }
}
