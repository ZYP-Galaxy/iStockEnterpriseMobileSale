package com.abbp.istockmobilesalenew;

import java.util.Date;

public class class_item {
    long classid;
    String defaultclass;
    Date updateddatetime;
    int isdeleted;
    int sortid;

    public class_item(long classid, String defaultclass, Date updateddatetime, int isdeleted, int sortid, String name) {
        this.classid = classid;
        this.defaultclass = defaultclass;
        this.updateddatetime = updateddatetime;
        this.isdeleted = isdeleted;
        this.sortid = sortid;
        this.name = name;
    }

    public void setClassid(long classid) {
        this.classid = classid;
    }

    public String getDefaultclass() {
        return defaultclass;
    }

    public void setDefaultclass(String defaultclass) {
        this.defaultclass = defaultclass;
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

    public int getSortid() {
        return sortid;
    }

    public void setSortid(int sortid) {
        this.sortid = sortid;
    }

    public void setName(String name) {
        this.name = name;
    }

//CREATE TABLE `class` (
    //	`classid`	INTEGER NOT NULL,
    //	`defaultclass`	TEXT,
    //	`updateddatetime`	NUMERIC,
    //	`isdeleted`	INTEGER,
    //	`sortid`	INTEGER,
    //	PRIMARY KEY(`classid`)
    //);

    public long getClassid() {
        return classid;
    }

    public String getName() {
        return name;
    }

    String name;
    public class_item(long classid, String name) {
        this.classid = classid;
        this.name = name;
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