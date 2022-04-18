package com.abbp.istockmobilesalenew;

public class Branch {
    private int branchid;
    private String name,shortdesc;
    private int isdeleted;

    public Branch(int branchid, String name, String shortdesc, int isdeleted) {
        this.branchid = branchid;
        this.name = name;
        this.shortdesc = shortdesc;
        this.isdeleted = isdeleted;
    }

    public int getBranchid() {
        return branchid;
    }

    public void setBranchid(int branchid) {
        this.branchid = branchid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortdesc() {
        return shortdesc;
    }

    public void setShortdesc(String shortdesc) {
        this.shortdesc = shortdesc;
    }

    public int getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(int isdeleted) {
        this.isdeleted = isdeleted;
    }
}
