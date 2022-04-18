package com.abbp.istockmobilesalenew;

public class Township {
    private long Townshipid;
    private String name;
    private String TownshipCode;

    public Township(long townshipid, String name, String townshipCode) {
        Townshipid = townshipid;
        this.name = name;
        TownshipCode = townshipCode;
    }

    public long getTownshipid() {
        return Townshipid;
    }

    public void setTownshipid(long townshipid) {
        Townshipid = townshipid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTownshipCode() {
        return TownshipCode;
    }

    public void setTownshipCode(String townshipCode) {
        TownshipCode = townshipCode;
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