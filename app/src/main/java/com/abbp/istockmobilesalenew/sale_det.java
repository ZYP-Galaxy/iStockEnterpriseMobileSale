package com.abbp.istockmobilesalenew;

public class sale_det {
    private long tranid;
    private String tranidstr;
    private String date;
    private double unit_qty;
    private int open_price;
    private double qty;
    private double sale_price;
    private double dis_price;
    private double dis_type;
    private double dis_percent;
    private String detremark;
    private int unit_type;
    private long code;
    private int sr;
    private String unit_short;
    private String desc;
    private int CalNoTax;
    private String PriceLevel;
    private short pricelevelid;

    public sale_det(long tranid, int sr, String date, double unit_qty, int open_price, double qty, int unit_type, double sale_price, double dis_price, double dis_type, double discount, String detremark, long code, String unit_short, String desc, int CalNoTax, String priceLevel) {
        this.sr = sr;
        this.tranid = tranid;
        this.date = date;
        this.unit_qty = unit_qty;
        this.open_price = open_price;
        this.qty = qty;
        this.unit_type = unit_type;
        this.sale_price = sale_price;
        this.dis_price = dis_price;
        this.dis_type = dis_type;
        this.dis_percent = discount;
        this.detremark = detremark;
        this.code = code;
        this.unit_short = unit_short;
        this.desc = desc;
        this.CalNoTax = CalNoTax;
        this.PriceLevel = priceLevel;
    }

    public String getPriceLevel() {
        return PriceLevel;
    }

    public void setPriceLevel(String priceLevel) {
        PriceLevel = priceLevel;
    }

    public String getUnit_short() {
        return unit_short;
    }

    public void setUnit_short(String unit_short) {
        this.unit_short = unit_short;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getSr() {
        return sr;
    }

    public void setSr(int sr) {
        this.sr = sr;
    }

    public int getUnit_type() {
        return unit_type;
    }

    public void setUnit_type(int unit_type) {
        this.unit_type = unit_type;
    }

    public long getTranid() {
        return tranid;
    }

    public void setTranid(long tranid) {
        this.tranid = tranid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getUnit_qty() {
        return unit_qty;
    }

    public void setUnit_qty(double unit_qty) {
        this.unit_qty = unit_qty;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public int getOpen_price() {
        return open_price;
    }

    public void setOpen_price(int open_price) {
        this.open_price = open_price;
    }

    public double getSale_price() {
        return sale_price;
    }

    public void setSale_price(double sale_price) {
        this.sale_price = sale_price;
    }

    public double getDis_price() {
        return dis_price;
    }

    public void setDis_price(double dis_price) {
        this.dis_price = dis_price;
    }

    public double getDis_type() {
        return dis_type;
    }

    public void setDis_type(double dis_type) {
        this.dis_type = dis_type;
    }

    public double getDis_percent() {
        return dis_percent;
    }

    public void setDis_percent(double discount) {
        this.dis_percent = discount;
    }

    public String getDetremark() {
        return detremark;
    }

    public void setDetremark(String detremark) {
        this.detremark = detremark;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public int getCalNoTax() {
        return CalNoTax;
    }

    public void setCalNoTax(int calNoTax) {
        CalNoTax = calNoTax;
    }

    public String getTranidstr() {
        return tranidstr;
    }

    public void setTranidstr(String tranidstr) {
        this.tranidstr = tranidstr;
    }

    public short getPricelevelid() {
        return pricelevelid;
    }

    public void setPricelevelid(short pricelevelid) {
        this.pricelevelid = pricelevelid;
    }

}
