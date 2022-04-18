package com.abbp.istockmobilesalenew;

public class CashIn {
  private  int userid;
  private  int accountid;
  private String name;
  private  int isenabled;
  private int acctgroupid;
  private  int isdafaultcash;

    public CashIn(int accountid, String name,int acctgroupid) {
        this.accountid = accountid;
        this.name = name;
       this.acctgroupid = acctgroupid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getAccountid() {
        return accountid;
    }

    public void setAccountid(int accountid) {
        this.accountid = accountid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIsenabled() {
        return isenabled;
    }

    public void setIsenabled(int isenabled) {
        this.isenabled = isenabled;
    }

    public int getAcctgroupid() {
        return acctgroupid;
    }

    public void setAcctgroupid(int acctgroupid) {
        this.acctgroupid = acctgroupid;
    }

    public int getIsdafaultcash() {
        return isdafaultcash;
    }

    public void setIsdafaultcash(int isdafaultcash) {
        this.isdafaultcash = isdafaultcash;
    }
}
