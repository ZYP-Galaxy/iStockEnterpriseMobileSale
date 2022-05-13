package com.abbp.istockmobilesalenew.tvsale;

public class usr_code {
    private String usr_code;
    private String description;
    private String classname;
    private String path;
    private double saleprice;

    public usr_code(String usr_code, String description, double saleprice) {
        this.usr_code = usr_code;
        this.description = description;
        this.saleprice = saleprice;
    }

    public usr_code(String usr_code, String description, String path) {
        this.usr_code = usr_code;
        this.description = description;
        this.path = path;
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

    public double getSaleprice() {
        return saleprice;
    }

    public void setSaleprice(double saleprice) {
        this.saleprice = saleprice;
    }

}
