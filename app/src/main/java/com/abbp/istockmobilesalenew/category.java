package com.abbp.istockmobilesalenew;

import java.util.Date;

public class category {
    long category;
    long classid;
    String name;
//CREATE TABLE `category` (
//	`categoryid`	INTEGER NOT NULL,
//	`classid`	INTEGER,
//	`name`	TEXT,
//	`parentgroupid`	TEXT,
//	`generatedid`	TEXT,
//	`shortdesc`	TEXT,
//	`sortid`	INTEGER,
//	`israw`	INTEGER,
//	`imagepath`	TEXT,
//	`categoryimage`	BLOB,
//	`colorrgb`	TEXT,
//	`ismodifiercategory`	INTEGER,
//	`issetmenu`	INTEGER,
//	`updateddatetime`	NUMERIC,
//	`isdeleted`	INTEGER,
//	PRIMARY KEY(`categoryid`)
//);
    int categoryid;
    String parentgroupid;
    String generatedid;
    String shortdesc;
    int sortid;
    int israw;
    String imagepath;
    byte categoryimage;
    String colorrgb;
    int ismodifiercategory;
    int issetmenu;
    Date updateddatetime;
    int isdeleted;//boolean

    public category(long category, long classid, String name, int categoryid, String parentgroupid, String generatedid, String shortdesc, int sortid, int israw, String imagepath, byte categoryimage, String colorrgb, int ismodifiercategory, int issetmenu, Date updateddatetime, int isdeleted) {
        this.category = category;
        this.classid = classid;
        this.name = name;
        this.categoryid = categoryid;
        this.parentgroupid = parentgroupid;
        this.generatedid = generatedid;
        this.shortdesc = shortdesc;
        this.sortid = sortid;
        this.israw = israw;
        this.imagepath = imagepath;
        this.categoryimage = categoryimage;
        this.colorrgb = colorrgb;
        this.ismodifiercategory = ismodifiercategory;
        this.issetmenu = issetmenu;
        this.updateddatetime = updateddatetime;
        this.isdeleted = isdeleted;
    }

    public void setCategory(long category) {
        this.category = category;
    }

    public void setClassid(long classid) {
        this.classid = classid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(int categoryid) {
        this.categoryid = categoryid;
    }

    public String getParentgroupid() {
        return parentgroupid;
    }

    public void setParentgroupid(String parentgroupid) {
        this.parentgroupid = parentgroupid;
    }

    public String getGeneratedid() {
        return generatedid;
    }

    public void setGeneratedid(String generatedid) {
        this.generatedid = generatedid;
    }

    public String getShortdesc() {
        return shortdesc;
    }

    public void setShortdesc(String shortdesc) {
        this.shortdesc = shortdesc;
    }

    public int getSortid() {
        return sortid;
    }

    public void setSortid(int sortid) {
        this.sortid = sortid;
    }

    public int getIsraw() {
        return israw;
    }

    public void setIsraw(int israw) {
        this.israw = israw;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public byte getCategoryimage() {
        return categoryimage;
    }

    public void setCategoryimage(byte categoryimage) {
        this.categoryimage = categoryimage;
    }

    public String getColorrgb() {
        return colorrgb;
    }

    public void setColorrgb(String colorrgb) {
        this.colorrgb = colorrgb;
    }

    public int getIsmodifiercategory() {
        return ismodifiercategory;
    }

    public void setIsmodifiercategory(int ismodifiercategory) {
        this.ismodifiercategory = ismodifiercategory;
    }

    public int getIssetmenu() {
        return issetmenu;
    }

    public void setIssetmenu(int issetmenu) {
        this.issetmenu = issetmenu;
    }

    public Date getUpdateddatetime() {
        return updateddatetime;
    }

    public void setUpdateddatetime(Date updateddatetime) {
        this.updateddatetime = updateddatetime;
    }

    public int getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(int isdeleted) {
        this.isdeleted = isdeleted;
    }

    public category(long category, long classid, String name) {
        this.category = category;
        this.classid = classid;
        this.name = name;
    }
    public category(String name){
        this.name=name;
    }

    public long getCategory() {
        return category;
    }

    public long getClassid() {
        return classid;
    }

    public String getName() {
        return name;
    }


}


//CREATE TABLE `sale_head_main` (
//	`tranid`	INTEGER NOT NULL,
//	`userid`	integer NOT NULL,
//	`docid`	text,
//	`date`	text,
//	`fromdate`	text,
//	`todate`	text,
//	`issaleconsign`	integer,
//	`invoiceno`	text,
//	`locationid`	integer,
//	`townshipid`	integer,
//	`customerid`	integer,
//	`paytypeid`	integer,
//	`salecurr`	integer,
//	`discountamount`	numeric,
//	`paidamount`	numeric,
//	`invoiceamount`	numeric,
//	`advpayamount`	numeric,
//	`invoiceqty`	numeric,
//	`soid`	integer,
//	`refno`	text,
//	`isdeleted`	integer,
//	`taxpercent`	numeric,
//	`taxamount`	numeric,
//	`focamount`	numeric,
//	`netamount`	numeric,
//	`cbjntranid`	integer,
//	`cashid`	integer,
//	`voucherremark`	text,
//	`exgrate`	numeric,
//	`delid`	integer,
//	`dueindays`	integer,
//	`isdeliver`	integer,
//	`membercardid`	text,
//	`memberdiscount`	numeric,
//	`memberamount`	numeric,
//	`deldocid`	text,
//	`jobid`	integer,
//	`issplit`	integer,
//	`enqdescription`	text,
//	`departuredate`	date,
//	`voucherremark1`	text,
//	`voucherremark2`	text,
//	`voucherremark3`	text,
//	`mulitlocbranchid`	integer,
//	`consignid`	integer,
//	`isprinted`	integer,
//	`isexported`	text,
//	`toexport`	integer,
//	`exporteduser`	integer,
//	`iscashreceive`	integer,
//	`isconfirmed`	integer,
//	`giftcardamount`	numeric,
//	`giftcardid`	text,
//	`roomtaxamount`	numeric,
//	`advancedmoney`	numeric,
//	`isreserved`	integer,
//	`reservedname`	text,
//	`checkintime`	text,
//	`starttime`	text,
//	`endtime`	text,
//	`creditcardid`	integer,
//	`customercount`	integer,
//	`shiftid`	integer,
//	`shiftname`	text,
//	`fastfoodtag`	text,
//	`commissionamount`	numeric,
//	`discountpercent`	numeric,
//	`salesmenid`	integer,
//	`tablenameid`	integer,
//	`governmenttaxpercent`	integer,
//	`govermenttaxamount`	numeric,
//	`servicetaxpercent`	integer,
//	`servicetaxamount`	numeric,
//	`roomtaxpercent`	integer,
//	`roomid`	integer,
//	`reftranid`	integer,
//	`isordertoroom`	integer,
//	`uploaded`	TEXT,
//	PRIMARY KEY(`tranid`)
//);
