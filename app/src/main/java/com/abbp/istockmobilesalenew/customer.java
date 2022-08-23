package com.abbp.istockmobilesalenew;

public class customer {
    /*CREATE TABLE `Customer` (
	`customerid`	INTEGER NOT NULL,
	`shortdesc`	TEXT,
	`name`	TEXT,
	`Townshipid`	INTEGER,
	`pricelevelid`	INTEGER,
	`iscredit`	INTEGER,
	`balance`	INTEGER,
	`creditlimit`	INTEGER,
	`dueindays`	INTEGER,
	`discountpercent`	INTEGER,
	`isinactive`	INTEGER,
	`discountamount`	INTEGER,
	`custgroupid`	INTEGER,
	`nationalcardid`	INTEGER,
	`isdeleted`	INTEGER,
	PRIMARY KEY(`customerid`)
);*/
    /*`custgroupname`	TEXT,
	`custgroupcode`	TEXT,
	`townshipname`	TEXT,
	`townshipcode`	TEXT,*/
    int customerid;
    String shortdesc;
    String name;
    int Townshipid;
    int pricelevelid;
    boolean iscredit;
    int balance;
    int creditlimit;
    int dueindays;
    double discountpercent;
    boolean isinactive;
    double discountamount;
    int custgroupid;
    int nationalcardid;
    boolean isdeleted;
    String custgroupname;
    String custgroupcode;
    String townshipname;
    String townshipcode;

    public customer() {
    }

    public customer(int customerid, String name, String townshipname) {
        this.customerid=customerid;
        this.name = name;
        this.townshipname = townshipname;
    }

    public customer(int customerid, String shortdesc, String name, int townshipid, int pricelevelid, boolean iscredit, int balance, int creditlimit, int dueindays, double discountpercent, boolean isinactive, int discountamount, int custgroupid, int nationalcardid, boolean isdeleted, String custgroupname, String custgroupcode, String townshipname, String townshipcode) {
        this.customerid = customerid;
        this.shortdesc = shortdesc;
        this.name = name;
        Townshipid = townshipid;
        this.pricelevelid = pricelevelid;
        this.iscredit = iscredit;
        this.balance = balance;
        this.creditlimit = creditlimit;
        this.dueindays = dueindays;
        this.discountpercent = discountpercent;
        this.isinactive = isinactive;
        this.discountamount = discountamount;
        this.custgroupid = custgroupid;
        this.nationalcardid = nationalcardid;
        this.isdeleted = isdeleted;
        this.custgroupname = custgroupname;
        this.custgroupcode = custgroupcode;
        this.townshipname = townshipname;
        this.townshipcode = townshipcode;
    }

    public String getCustgroupname() {
        return custgroupname;
    }

    public void setCustgroupname(String custgroupname) {
        this.custgroupname = custgroupname;
    }

    public String getCustgroupcode() {
        return custgroupcode;
    }

    public void setCustgroupcode(String custgroupcode) {
        this.custgroupcode = custgroupcode;
    }

    public String getTownshipname() {
        return townshipname;
    }

    public void setTownshipname(String townshipname) {
        this.townshipname = townshipname;
    }

    public String getTownshipcode() {
        return townshipcode;
    }

    public void setTownshipcode(String townshipcode) {
        this.townshipcode = townshipcode;
    }

    public customer(int customerid, String shortdesc, String name, int townshipid, int pricelevelid, boolean iscredit, int balance, int creditlimit, int dueindays, double discountpercent, boolean isinactive, double discountamount, int custgroupid, int nationalcardid, boolean isdeleted) {
        this.customerid = customerid;
        this.shortdesc = shortdesc;
        this.name = name;
        Townshipid = townshipid;
        this.pricelevelid = pricelevelid;
        this.iscredit = iscredit;
        this.balance = balance;
        this.creditlimit = creditlimit;
        this.dueindays = dueindays;
        this.discountpercent = discountpercent;
        this.isinactive = isinactive;
        this.discountamount = discountamount;
        this.custgroupid = custgroupid;
        this.nationalcardid = nationalcardid;
        this.isdeleted = isdeleted;
    }

    public int getCustomerid() {
        return customerid;
    }

    public void setCustomerid(int customerid) {
        this.customerid = customerid;
    }

    public String getShortdesc() {
        return shortdesc;
    }

    public void setShortdesc(String shortdesc) {
        this.shortdesc = shortdesc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTownshipid() {
        return Townshipid;
    }

    public void setTownshipid(int townshipid) {
        Townshipid = townshipid;
    }

    public int getPricelevelid() {
        return pricelevelid;
    }

    public void setPricelevelid(int pricelevelid) {
        this.pricelevelid = pricelevelid;
    }

    public boolean isIscredit() {
        return iscredit;
    }

    public void setIscredit(boolean iscredit) {
        this.iscredit = iscredit;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getCreditlimit() {
        return creditlimit;
    }

    public void setCreditlimit(int creditlimit) {
        this.creditlimit = creditlimit;
    }

    public int getDueindays() {
        return dueindays;
    }

    public void setDueindays(int dueindays) {
        this.dueindays = dueindays;
    }

    public double getDiscountpercent() {
        return discountpercent;
    }

    public void setDiscountpercent(double discountpercent) {
        this.discountpercent = discountpercent;
    }

    public boolean isIsinactive() {
        return isinactive;
    }

    public void setIsinactive(boolean isinactive) {
        this.isinactive = isinactive;
    }

    public double getDiscountamount() {
        return discountamount;
    }

    public void setDiscountamount(double discountamount) {
        this.discountamount = discountamount;
    }

    public int getCustgroupid() {
        return custgroupid;
    }

    public void setCustgroupid(int custgroupid) {
        this.custgroupid = custgroupid;
    }

    public int getNationalcardid() {
        return nationalcardid;
    }

    public void setNationalcardid(int nationalcardid) {
        this.nationalcardid = nationalcardid;
    }

    public boolean isIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(boolean isdeleted) {
        this.isdeleted = isdeleted;
    }
    /*  long customerid;
    String shortdesc;
    String name;
    String customercode;
    boolean credit;
    double custDis;
    int due_in_days;
    double credit_limit;

    public long getCustomerid() {
        return customerid;
    }

    public String getName() {
        return name;
    }

    public String getCustomercode() {
        return customercode;
    }

    public boolean isCredit() {
        return credit;
    }

    public void setCustomerid(long customerid) {
        this.customerid = customerid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCustDis() {
        return custDis;
    }

    public void setCustDis(double custDis) {
        this.custDis = custDis;
    }

    public void setCustomercode(String customercode) {
        this.customercode = customercode;
    }

    public void setCredit(boolean credit) {
        this.credit = credit;
    }

    public int getDue_in_days() {
        return due_in_days;
    }

    public void setDue_in_days(int due_in_days) {
        this.due_in_days = due_in_days;
    }

    public double getCredit_limit() {
        return credit_limit;
    }

    public void setCredit_limit(double credit_limit) {
        this.credit_limit = credit_limit;
    }

    public customer(long customerid, String name, String customercode, boolean credit,double custDis,int due_in_days,double credit_limit) {
        this.customerid = customerid;
        this.name = name;
        this.customercode = customercode;
        this.credit = credit;
        this.custDis=custDis;
        this.due_in_days=due_in_days;
        this.credit_limit=credit_limit;
    }*/
}
