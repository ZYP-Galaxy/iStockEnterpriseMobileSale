package com.abbp.istockmobilesalenew;

public class posuser {
    private int userid;
    private String name;
    private int branchid;
    private String shortdesc;
    private String password;
    private int canchangesaleprice;
    private int canchangepurprice;
    private int canchangedate;
    private int defaultlocationid;
    private int candiscount;
    private int isusetax;
    private int ishidepurprice;
    private int ishidesaleprice;
    private int ishidepurcostprice;
    private int isviewallsalepricelevel;
    private int isinactive;
    private int defaultbranchid;
    private int defaultcashid;
    private int isallowsysdatechange;
    private int isallowovercreditlimit;
    private String isknockcode;
    private int istabletuser;
    private int isallowpricelevel;
    private int canselectcustomer;
    private int canselectlocation;

    public posuser(int userid, String name, int branchid, String shortdesc, String password, int canchangesaleprice, int canchangepurprice, int canchangedate, int defaultlocationid, int candiscount, int isusetax, int ishidepurprice, int ishidesaleprice, int ishidepurcostprice, int isviewallsalepricelevel, int isinactive, int defaultbranchid, int defaultcashid, int isallowsysdatechange, int isallowovercreditlimit, String isknockcode, int istabletuser, int isallowpricelevel, int canselectcustomer, int canselectlocation) {
        this.userid = userid;
        this.name = name;
        this.branchid = branchid;
        this.shortdesc = shortdesc;
        this.password = password;
        this.canchangesaleprice = canchangesaleprice;
        this.canchangepurprice = canchangepurprice;
        this.canchangedate = canchangedate;
        this.defaultlocationid = defaultlocationid;
        this.candiscount = candiscount;
        this.isusetax = isusetax;
        this.ishidepurprice = ishidepurprice;
        this.ishidesaleprice = ishidesaleprice;
        this.ishidepurcostprice = ishidepurcostprice;
        this.isviewallsalepricelevel = isviewallsalepricelevel;
        this.isinactive = isinactive;
        this.defaultbranchid = defaultbranchid;
        this.defaultcashid = defaultcashid;
        this.isallowsysdatechange = isallowsysdatechange;
        this.isallowovercreditlimit = isallowovercreditlimit;
        this.isknockcode = isknockcode;
        this.istabletuser = istabletuser;
        this.isallowpricelevel = isallowpricelevel;
        this.canselectcustomer = canselectcustomer;
        this.canselectlocation = canselectlocation;
    }

    public int getCanselectcustomer() {
        return canselectcustomer;
    }

    public void setCanselectcustomer(int canselectcustomer) {
        this.canselectcustomer = canselectcustomer;
    }

    public int getCanselectlocation() {
        return canselectlocation;
    }

    public void setCanselectlocation(int canselectlocation) {
        this.canselectlocation = canselectlocation;
    }

    public posuser(int userid, String name, int branchid, String shortdesc, String password,
                   int canchangesaleprice, int canchangepurprice, int canchangedate,
                   int defaultlocationid, int candiscount, int isusetax, int ishidepurprice,
                   int ishidesaleprice, int ishidepurcostprice, int isviewallsalepricelevel,
                   int isinactive, int defaultbranchid, int defaultcashid,
                   int isallowsysdatechange, int isallowovercreditlimit, String isknockcode,
                   int istabletuser, int isallowpricelevel) {
        this.userid = userid;
        this.name = name;
        this.branchid = branchid;
        this.shortdesc = shortdesc;
        this.password = password;
        this.canchangesaleprice = canchangesaleprice;
        this.canchangepurprice = canchangepurprice;
        this.canchangedate = canchangedate;
        this.defaultlocationid = defaultlocationid;
        this.candiscount = candiscount;
        this.isusetax = isusetax;
        this.ishidepurprice = ishidepurprice;
        this.ishidesaleprice = ishidesaleprice;
        this.ishidepurcostprice = ishidepurcostprice;
        this.isviewallsalepricelevel = isviewallsalepricelevel;
        this.isinactive = isinactive;
        this.defaultbranchid = defaultbranchid;
        this.defaultcashid = defaultcashid;
        this.isallowsysdatechange = isallowsysdatechange;
        this.isallowovercreditlimit = isallowovercreditlimit;
        this.isknockcode = isknockcode;
        this.istabletuser = istabletuser;
        this.isallowpricelevel = isallowpricelevel;
    }

    public posuser(int userid, String name) {
        this.userid = userid;
        this.name = name;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBranchid() {
        return branchid;
    }

    public void setBranchid(int branchid) {
        this.branchid = branchid;
    }

    public String getShortdesc() {
        return shortdesc;
    }

    public void setShortdesc(String shortdesc) {
        this.shortdesc = shortdesc;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCanchangesaleprice() {
        return canchangesaleprice;
    }

    public void setCanchangesaleprice(int canchangesaleprice) {
        this.canchangesaleprice = canchangesaleprice;
    }

    public int getCanchangepurprice() {
        return canchangepurprice;
    }

    public void setCanchangepurprice(int canchangepurprice) {
        this.canchangepurprice = canchangepurprice;
    }

    public int getCanchangedate() {
        return canchangedate;
    }

    public void setCanchangedate(int canchangedate) {
        this.canchangedate = canchangedate;
    }

    public int getDefaultlocationid() {
        return defaultlocationid;
    }

    public void setDefaultlocationid(int defaultlocationid) {
        this.defaultlocationid = defaultlocationid;
    }

    public int getCandiscount() {
        return candiscount;
    }

    public void setCandiscount(int candiscount) {
        this.candiscount = candiscount;
    }

    public int getIsusetax() {
        return isusetax;
    }

    public void setIsusetax(int isusetax) {
        this.isusetax = isusetax;
    }

    public int getIshidepurprice() {
        return ishidepurprice;
    }

    public void setIshidepurprice(int ishidepurprice) {
        this.ishidepurprice = ishidepurprice;
    }

    public int getIshidesaleprice() {
        return ishidesaleprice;
    }

    public void setIshidesaleprice(int ishidesaleprice) {
        this.ishidesaleprice = ishidesaleprice;
    }

    public int getIshidepurcostprice() {
        return ishidepurcostprice;
    }

    public void setIshidepurcostprice(int ishidepurcostprice) {
        this.ishidepurcostprice = ishidepurcostprice;
    }

    public int getIsviewallsalepricelevel() {
        return isviewallsalepricelevel;
    }

    public void setIsviewallsalepricelevel(int isviewallsalepricelevel) {
        this.isviewallsalepricelevel = isviewallsalepricelevel;
    }

    public int getIsinactive() {
        return isinactive;
    }

    public void setIsinactive(int isinactive) {
        this.isinactive = isinactive;
    }

    public int getDefaultbranchid() {
        return defaultbranchid;
    }

    public void setDefaultbranchid(int defaultbranchid) {
        this.defaultbranchid = defaultbranchid;
    }

    public int getDefaultcashid() {
        return defaultcashid;
    }

    public void setDefaultcashid(int defaultcashid) {
        this.defaultcashid = defaultcashid;
    }

    public int getIsallowsysdatechange() {
        return isallowsysdatechange;
    }

    public void setIsallowsysdatechange(int isallowsysdatechange) {
        this.isallowsysdatechange = isallowsysdatechange;
    }

    public int getIsallowovercreditlimit() {
        return isallowovercreditlimit;
    }

    public void setIsallowovercreditlimit(int isallowovercreditlimit) {
        this.isallowovercreditlimit = isallowovercreditlimit;
    }

    public String getIsknockcode() {
        return isknockcode;
    }

    public void setIsknockcode(String isknockcode) {
        this.isknockcode = isknockcode;
    }

    public int getIstabletuser() {
        return istabletuser;
    }

    public void setIstabletuser(int istabletuser) {
        this.istabletuser = istabletuser;
    }

    public int getIsallowpricelevel() {
        return isallowpricelevel;
    }

    public void setIsallowpricelevel(int isallowpricelevel) {
        this.isallowpricelevel = isallowpricelevel;
    }

    /*[{"title": "Galaxy Software Co.,Ltd","date": "2000-01-01","isposting": false,"isshowerrormsg": false,"isuseunit": true,"isprofitcenter": false,"ispcbycode": false,"backupdir": null,"isuseinvoice": false,"isusedueday": true,"isusealiascode": true,"isusemulticurrency": false,"isuseuserdefinedoc": false,"isusepurorder": true,"isusecategory2": false,"isuseclass": false,"isuse_diffconacct": false,"isusepurprice": false,"isusesaleprice": false,"isusesuppliercode": false,"supcodeglength": 1,"isusecustomercode": false,"custcodeglength": 1,"isuseclasscatecode": false,"programmerpassword": "MDE1NDI3","specialpassword": "S0tL","isusetownship": true,"isusecustomergroup": true,"isuselocation": true,"isusemultipaid": true,"isusesaleorder": true,"isusepconsign": true,"isusemultiinvoice": true,"isusecreditcard": true,"isusebarcode": true,"isusesaleconsign": true,"saleconsigndet": false,"languageid": 1,"isusemultibranch": true,"cashaccountpur": "1","cashacctsale": "1","isusesalesmen": true,"isusebrandinentry": true,"isuseremotecopy": false,"isrmtcopytoall": false,"isdisableapar": false,"isrmtnewcodeonly": false,"isrmtkeepoldprice": false,"isrmtusetrnprice": false,"isallowbypasskey": false,"qtydecimalplaces": 0,"pricedecimalplaces": 2,"issaleaccountbyloc": false,"isuseduedate": false,"lockdate": "1980-01-01","ispdisbycode": true,"isusecustpricelevel": false,"islogtransaction": true,"isdirtyflag": false,"caltime": "2004-12-17T10:33:09.54","stockflag": null,"defaultlockdays": 2,"lastautostockdate": "2000-01-01","isusecostprice": false,"isusetablename": false,"isusetax": false,"isuselocmerge": false,"isuselocgroup": false,"isuselastpurprice": false,"isusedetailcust": false,"usecustfilter": 2,"isuseuserpricelevel": false,"ischeckstockbal": false,"canchangeprepaydate": false,"isuseexpdate": true,"backupfrequencyindays": null,"isusecolorsize": true,"isusesalesremark": false,"istrialversion": false,"trialexpireddate": "2011-12-25","isusepic": false,"isuseautoinvno": false,"usermtsetup": false,"versionno": "7.2.5","isusebackupsvr": false,"isuseoffline": false,"isaccount": false,"isinventory": false,"isuseautoconverion": true,"isusecreatedb": false,"isshowbrandinf1": false,"isusemulticash": true,"salesmencomm": 2,"isuseouttstandstock": false,"isusemultitransfer": true,"pccount": 4,"backuprestorepassword": "MTIzNA==","isuserestaurant": false,"tobar": false,"isuseserial": true,"fontname": "Myanmar3","ischeckrecdeli": true,"isuseimgsave": true,"isuseappautobackup": false,"autobackupinterval": null,"clientname": null,"isuseimport": false,"stockstatuscal": "1","systempassword": null,"path": null,"isusemembercard": true,"isusedelivery": true,"isusereceive": true,"isusejob": true,"isuseaccountstock": false,"isusestandbysvr": false,"isusemultipricelvl": true,"isusecoderecover": true,"kgperton": null,"ctnperton": null,"isusemultilocation": true,"isusemultidel": true,"isusemultirec": true,"isusepricecalculate": true,"isusespecialprice": true,"isusemultisale": true,"isusemultipurchase": false,"isusemultiadjustment": true,"isusepricecalculatebyamt": false,"isusepricechangeonexgrate": true,"isuseunitrelationpricechange": true,"isshowminmaxqty": false,"useunittype": 1,"defaultoilunit": 1,"isusemsg": true,"useserialsaleonly": true,"isusesmallestqtyprice": false,"isuseexcludepurdisonstkamt": false,"isusemultibranchjournal": false,"uniqueid": null,"serialkey": null,"defaulttaxpercent": 10,"emailaddress": null,"emailpassword": null,"isusesortingsr": false,"isusesvouchercustom": false,"isuseyearend": false,"isusesaleexpimp": false,"isusestandlonetablet": false,"isallowusrcode": false,"isallowtownship": false,"isallowmulticonnection": false,"isusegiftcard": false,"taxcal": 0,"logoimg": null,"tabletcount": null,"isusehotel": false,"hotelsvrname": null,"hoteldbname": null,"isuseremotecopytablet": false,"isuseserialpurchaseonly": true,"isusepromotion": true,"updateddatetime": "2019-12-08T23:01:02.315439","myanmarfontid": 1,"receiptheaderline1": null,"receiptheaderline2": null,"receiptheaderline3": null,"receiptheaderline4": null,"receiptheaderline5": null,"receiptfooterline1": null,"receiptfooterline2": null,"receiptfooterline3": null,"receiptfooterline4": null,"receiptfooterline5": null,"defaultpurtaxpercent": 15,"isexclusivetax": true,"afterdiscount": true}]*/

    /*[{"title": "Galaxy Software Co.,Ltd","date": "2000-01-01","isposting": false,"isshowerrormsg": false,"isuseunit": true,"isprofitcenter": false,"ispcbycode": false,"backupdir": null,"isuseinvoice": false,"isusedueday": true,"isusealiascode": true,"isusemulticurrency": false,"isuseuserdefinedoc": false,"isusepurorder": true,"isusecategory2": false,"isuseclass": false,"isuse_diffconacct": false,"isusepurprice": false,"isusesaleprice": false,"isusesuppliercode": false,"supcodeglength": 1,"isusecustomercode": false,"custcodeglength": 1,"isuseclasscatecode": false,"programmerpassword": "MDE1NDI3","specialpassword": "S0tL","isusetownship": true,"isusecustomergroup": true,"isuselocation": true,"isusemultipaid": true,"isusesaleorder": true,"isusepconsign": true,"isusemultiinvoice": true,"isusecreditcard": true,"isusebarcode": true,"isusesaleconsign": true,"saleconsigndet": false,"languageid": 1,"isusemultibranch": true,"cashaccountpur": "1","cashacctsale": "1","isusesalesmen": true,"isusebrandinentry": true,"isuseremotecopy": false,"isrmtcopytoall": false,"isdisableapar": false,"isrmtnewcodeonly": false,"isrmtkeepoldprice": false,"isrmtusetrnprice": false,"isallowbypasskey": false,"qtydecimalplaces": 0,"pricedecimalplaces": 2,"issaleaccountbyloc": false,"isuseduedate": false,"lockdate": "1980-01-01","ispdisbycode": true,"isusecustpricelevel": false,"islogtransaction": true,"isdirtyflag": false,"caltime": "2004-12-17T10:33:09.54","stockflag": null,"defaultlockdays": 2,"lastautostockdate": "2000-01-01","isusecostprice": false,"isusetablename": false,"isusetax": false,"isuselocmerge": false,"isuselocgroup": false,"isuselastpurprice": false,"isusedetailcust": false,"usecustfilter": 2,"isuseuserpricelevel": false,"ischeckstockbal": false,"canchangeprepaydate": false,"isuseexpdate": true,"backupfrequencyindays": null,"isusecolorsize": true,"isusesalesremark": false,"istrialversion": false,"trialexpireddate": "2011-12-25","isusepic": false,"isuseautoinvno": false,"usermtsetup": false,"versionno": "7.2.5","isusebackupsvr": false,"isuseoffline": false,"isaccount": false,"isinventory": false,"isuseautoconverion": true,"isusecreatedb": false,"isshowbrandinf1": false,"isusemulticash": true,"salesmencomm": 2,"isuseouttstandstock": false,"isusemultitransfer": true,"pccount": 4,"backuprestorepassword": "MTIzNA==","isuserestaurant": false,"tobar": false,"isuseserial": true,"fontname": "Myanmar3","ischeckrecdeli": true,"isuseimgsave": true,"isuseappautobackup": false,"autobackupinterval": null,"clientname": null,"isuseimport": false,"stockstatuscal": "1","systempassword": null,"path": null,"isusemembercard": true,"isusedelivery": true,"isusereceive": true,"isusejob": true,"isuseaccountstock": false,"isusestandbysvr": false,"isusemultipricelvl": true,"isusecoderecover": true,"kgperton": null,"ctnperton": null,"isusemultilocation": true,"isusemultidel": true,"isusemultirec": true,"isusepricecalculate": true,"isusespecialprice": true,"isusemultisale": true,"isusemultipurchase": false,"isusemultiadjustment": true,"isusepricecalculatebyamt": false,"isusepricechangeonexgrate": true,"isuseunitrelationpricechange": true,"isshowminmaxqty": false,"useunittype": 1,"defaultoilunit": 1,"isusemsg": true,"useserialsaleonly": true,"isusesmallestqtyprice": false,"isuseexcludepurdisonstkamt": false,"isusemultibranchjournal": false,"uniqueid": null,"serialkey": null,"defaulttaxpercent": 10,"emailaddress": null,"emailpassword": null,"isusesortingsr": false,"isusesvouchercustom": false,"isuseyearend": false,"isusesaleexpimp": false,"isusestandlonetablet": false,"isallowusrcode": false,"isallowtownship": false,"isallowmulticonnection": false,"isusegiftcard": false,"taxcal": 0,"logoimg": null,"tabletcount": null,"isusehotel": false,"hotelsvrname": null,"hoteldbname": null,"isuseremotecopytablet": false,"isuseserialpurchaseonly": true,"isusepromotion": true,"updateddatetime": "2019-12-08T23:01:02.315439","myanmarfontid": 1,"receiptheaderline1": null,"receiptheaderline2": null,"receiptheaderline3": null,"receiptheaderline4": null,"receiptheaderline5": null,"receiptfooterline1": null,"receiptfooterline2": null,"receiptfooterline3": null,"receiptfooterline4": null,"receiptfooterline5": null,"defaultpurtaxpercent": 15,"isexclusivetax": true,"afterdiscount": true}]*/

/*private int Confirm_PrintVou;
    private int allow_priceLevel;
    private int select_location;
    private int select_customer;
    private int change_date;
    private int change_price;
    private String knockcode;
    private  int def_locationid;
    private  int def_payment;
    private int tax;
    private int discount;
    private  int Allow_Over_Credit_Limit;
    private  int def_cashid;
    private  String Cashier_Printer;
    private  int Cashier_PrinterType;



    public posuser(int userid, String name, int confirm_PrintVou, int allow_priceLevel, int select_location, int select_customer, int change_date,int tax,int discount, int change_price, String knockcode, int def_locationid,int def_payment,int Allow_Over_Credit_Limit,int def_cashid,String Cashier_Printer,int Cashier_PrinterType) {
        this.userid = userid;
        this.name = name;
        Confirm_PrintVou = confirm_PrintVou;
        this.allow_priceLevel = allow_priceLevel;
        this.select_location = select_location;
        this.select_customer = select_customer;
        this.change_date = change_date;
        this.tax=tax;
        this.discount=discount;
        this.change_price = change_price;
        this.knockcode = knockcode;
        this.def_locationid = def_locationid;
        this.def_payment=def_payment;
        this.Allow_Over_Credit_Limit=Allow_Over_Credit_Limit;
        this.def_cashid=def_cashid;
        this.Cashier_Printer=Cashier_Printer;
        this.Cashier_PrinterType=Cashier_PrinterType;

    }
    public posuser(int userid, String name){
        this.userid=userid;
        this.name=name;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getConfirm_PrintVou() {
        return Confirm_PrintVou;
    }

    public void setConfirm_PrintVou(int confirm_PrintVou) {
        Confirm_PrintVou = confirm_PrintVou;
    }

    public int getAllow_priceLevel() {
        return allow_priceLevel;
    }

    public void setAllow_priceLevel(int allow_priceLevel) {
        this.allow_priceLevel = allow_priceLevel;
    }

    public int getSelect_location() {
        return select_location;
    }

    public void setSelect_location(int select_location) {
        this.select_location = select_location;
    }

    public int getSelect_customer() {
        return select_customer;
    }

    public void setSelect_customer(int select_customer) {
        this.select_customer = select_customer;
    }

    public int getChange_date() {
        return change_date;
    }

    public void setChange_date(int change_date) {
        this.change_date = change_date;
    }

    public int getTax() {
        return tax;
    }

    public void setTax(int tax) {
        this.tax = tax;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getChange_price() {
        return change_price;
    }

    public void setChange_price(int change_price) {
        this.change_price = change_price;
    }

    public String getKnockcode() {
        return knockcode;
    }

    public void setKnockcode(String knockcode) {
        this.knockcode = knockcode;
    }

    public int getDef_locationid() {
        return def_locationid;
    }

    public void setDef_locationid(int def_locationid) {
        this.def_locationid = def_locationid;
    }


    public int getDef_payment() {
        return def_payment;
    }

    public void setDef_payment(int def_payment) {
        this.def_payment = def_payment;
    }

    public int getAllow_Over_Credit_Limit() {
        return Allow_Over_Credit_Limit;
    }

    public void setAllow_Over_Credit_Limit(int Allow_Over_Credit_Limit) {
        this.Allow_Over_Credit_Limit = Allow_Over_Credit_Limit;
    }

    public int getDef_cashid() {
        return def_cashid;
    }

    public void setDef_cashid(int def_cashid) {
        this.def_cashid = def_cashid;
    }


    public String getCashier_Printer() {
        return Cashier_Printer;
    }

    public void setCashier_Printer(String cashier_Printer) {
        Cashier_Printer = cashier_Printer;
    }

    public int getCashier_PrinterType() {
        return Cashier_PrinterType;
    }

    public void setCashier_PrinterType(int cashier_PrinterType) {
        Cashier_PrinterType = cashier_PrinterType;
    }*/
}
