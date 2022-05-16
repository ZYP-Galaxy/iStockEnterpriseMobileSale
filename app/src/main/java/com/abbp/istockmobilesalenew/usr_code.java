package com.abbp.istockmobilesalenew;

public class usr_code {
    private int code;
    private String usr_code;
    private String description;
    private String classname;
    private String path;
    private double sale_price;

    public usr_code(String usr_code, String description) {
        this.usr_code = usr_code;
        this.description = description;

    }

    public usr_code(String usr_code, String description, double sale_price) {
        this.usr_code = usr_code;
        this.description = description;
        this.sale_price = sale_price;
    }

    public usr_code(String usr_code, String description, String path) {
        this.usr_code = usr_code;
        this.description = description;
        this.path = path;
    }

    public usr_code(int code, String usr_code, String description, double sale_price) {
        this.code = code;
        this.usr_code = usr_code;
        this.description = description;
        this.sale_price = sale_price;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getUsr_code() {
        return usr_code;
    }

    public String getDescription() {
        return description;
    }

    public String getPath() {
        return path;
    }

    public double getSalePrice() {
        return sale_price;
    }

    public void setSalePrice(double sale_price) {
        this.sale_price = sale_price;
    }
}
