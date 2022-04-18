package com.abbp.istockmobilesalenew;

public class Location {
    private long locationid;
    private String name;
    private String shortdes;
    private long branchid;
    private String parentgroupid;
    private String oldparentgroupid;
    private int sortid;
    private int iscalculate;
    private int isdiffsaleprice;
    private  int customerid;
    private int isdeleted;
    /*CREATE TABLE `Location` (
	`locationid`	INTEGER NOT NULL,
	`Name`	TEXT,
	`shortdesc`	TEXT,
	`branchID`	INTEGER,
	`parentgroupid`	TEXT,
	`oldparentgroupid`	TEXT,
	`sortid`	INTEGER,
	`iscalculate`	INTEGER,
	`isdiffsaleprice`	INTEGER,
	`customerid`	INTEGER,
	`isdeleted`	INTEGER,
	PRIMARY KEY(`locationid`)
);*/



    public String getParentgroupid() {
        return parentgroupid;
    }

    public void setParentgroupid(String parentgroupid) {
        this.parentgroupid = parentgroupid;
    }

    public String getOldparentgroupid() {
        return oldparentgroupid;
    }

    public void setOldparentgroupid(String oldparentgroupid) {
        this.oldparentgroupid = oldparentgroupid;
    }

    public int getSortid() {
        return sortid;
    }

    public void setSortid(int sortid) {
        this.sortid = sortid;
    }

    public int getIscalculate() {
        return iscalculate;
    }

    public void setIscalculate(int iscalculate) {
        this.iscalculate = iscalculate;
    }

    public int getIsdiffsaleprice() {
        return isdiffsaleprice;
    }

    public void setIsdiffsaleprice(int isdiffsaleprice) {
        this.isdiffsaleprice = isdiffsaleprice;
    }

    public int getCustomerid() {
        return customerid;
    }

    public void setCustomerid(int customerid) {
        this.customerid = customerid;
    }

    public int getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(int isdeleted) {
        this.isdeleted = isdeleted;
    }

    public Location(long locationid, String name, String shortdes, long branchid, String parentgroupid, String oldparentgroupid, int sortid, int iscalculate, int isdiffsaleprice, int customerid, int isdeleted) {
        this.locationid = locationid;
        this.name = name;
        this.shortdes = shortdes;
        this.branchid = branchid;
        this.parentgroupid = parentgroupid;
        this.oldparentgroupid = oldparentgroupid;
        this.sortid = sortid;
        this.iscalculate = iscalculate;
        this.isdiffsaleprice = isdiffsaleprice;
        this.customerid = customerid;
        this.isdeleted = isdeleted;
    }

    public Location(long locationid, String name, String shortdes, long branchid) {
        this.locationid = locationid;
        this.name = name;
        this.shortdes = shortdes;
        this.branchid = branchid;
    }

    public long getLocationid() {
        return locationid;
    }

    public void setLocationid(long locationid) {
        this.locationid = locationid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortdes() {
        return shortdes;
    }

    public void setShortdes(String shortdes) {
        this.shortdes = shortdes;
    }

    public long getBranchid() {
        return branchid;
    }

    public void setBranchid(long branchid) {
        this.branchid = branchid;
    }
}
