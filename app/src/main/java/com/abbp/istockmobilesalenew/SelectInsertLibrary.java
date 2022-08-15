package com.abbp.istockmobilesalenew;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Base64;
import android.util.Log;

import com.abbp.istockmobilesalenew.tvsale.Posuser;
import com.abbp.istockmobilesalenew.tvsale.sale_entry_tv;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class SelectInsertLibrary {
    String sqlString;


    public static boolean OfflineCheck = false;

    //added by KLM to Select location base on Branch User 12082022
    public static void GetLocationBaseOnBrachUser(ArrayList<Location> locations) {
       String sqlString="select l.* from Location l left join Branch_User bu on bu.branchid=l.branchid where  l.isdeleted=0 and bu.isenabled=1 and bu.userid="+frmlogin.LoginUserid+" order by branchid,locationid";
       Cursor cursor = DatabaseHelper.rawQuery(sqlString);
//        System.out.println(cursor.getCount() + "count!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
//                                int branchid=cursor.getInt(cursor.getColumnIndex("branchid"));

                    long locationid = cursor.getLong(cursor.getColumnIndex("locationid"));
                    String locationname = cursor.getString(cursor.getColumnIndex("Name"));
                    String shortname = cursor.getString(cursor.getColumnIndex("shortdesc"));
                    long branchid = cursor.getLong(cursor.getColumnIndex("branchID"));
                    locations.add(new Location(locationid, locationname, shortname, branchid));
                    Log.i("branchid", branchid + "");
                } while (cursor.moveToNext());

            }
        }
        cursor.close();
    }

    public static void BindHeader(Context context, int newCustomerId) {
        Cursor cursor=DatabaseHelper.rawQuery("select customerid,name,townshipid,townshipname,custgroupid,custgroupname,iscredit from Customer where customerid="+newCustomerId);
        String contextString = context.getClass().toString().split("com.abbp.istockmobilesalenew.")[1];
        switch (contextString){
            case "saleorder_entry":
                saleorder_entry.sh.get(0).setCustomerid(newCustomerId);
                if (cursor != null && cursor.getCount() != 0) {
                    if (cursor.moveToFirst()) {
                        do {

                            saleorder_entry.btncustomer.setText(cursor.getString(cursor.getColumnIndex("name")));
                            int townshipid=cursor.getInt(cursor.getColumnIndex("townshipid"));
                            saleorder_entry.selected_townshipid = townshipid;
                            saleorder_entry.selected_custgroupid=cursor.getInt(cursor.getColumnIndex("custgroupid"));
                            saleorder_entry.btntownship.setText(cursor.getString(cursor.getColumnIndex("townshipname")));
                            saleorder_entry.btncustgroup.setText(cursor.getString(cursor.getColumnIndex("custgroupname")));
                            Boolean iscredit=cursor.getInt(cursor.getColumnIndex("iscredit"))==1?true:false;
                            saleorder_entry.isCreditcustomer=iscredit;
                            if(iscredit){
                                saleorder_entry.sh.get(0).setPay_type(2);
                                saleorder_entry.btnpaytype.setText("Credit");

                            } else {
                                saleorder_entry.sh.get(0).setPay_type(1);
                                saleorder_entry.btnpaytype.setText("Cash Down");
                            }

                        } while (cursor.moveToNext());
                    }
                    cursor.close();
                }
                break;

            case "sale_entry":
                sale_entry.sh.get(0).setCustomerid(newCustomerId);
                if (cursor != null && cursor.getCount() != 0) {
                    if (cursor.moveToFirst()) {
                        do {

                            sale_entry.btncustomer.setText(cursor.getString(cursor.getColumnIndex("name")));
                            int townshipid=cursor.getInt(cursor.getColumnIndex("townshipid"));
                            sale_entry.selected_townshipid = townshipid;
                            sale_entry.selected_custgroupid=cursor.getInt(cursor.getColumnIndex("custgroupid"));
                            sale_entry.btntownship.setText(cursor.getString(cursor.getColumnIndex("townshipname")));
                            sale_entry.btncustgroup.setText(cursor.getString(cursor.getColumnIndex("custgroupname")));
                            Boolean iscredit=cursor.getInt(cursor.getColumnIndex("iscredit"))==1?true:false;
                            sale_entry.isCreditcustomer=iscredit;
                            if(iscredit){
                                sale_entry.sh.get(0).setPay_type(2);
                                sale_entry.btnpaytype.setText("Credit");

                            } else {
                                sale_entry.sh.get(0).setPay_type(1);
                                sale_entry.btnpaytype.setText("Cash Down");
                            }

                        } while (cursor.moveToNext());
                    }
                    cursor.close();
                }
                break;
            default:
                sale_entry_tv.sh.get(0).setCustomerid(newCustomerId);
                if (cursor != null && cursor.getCount() != 0) {
                    if (cursor.moveToFirst()) {
                        do {

                            sale_entry_tv.btncustomer.setText(cursor.getString(cursor.getColumnIndex("name")));
                            int townshipid=cursor.getInt(cursor.getColumnIndex("townshipid"));
                            sale_entry_tv.selected_townshipid = townshipid;
                            sale_entry_tv.selected_custgroupid=cursor.getInt(cursor.getColumnIndex("custgroupid"));
                            sale_entry_tv.btntownship.setText(cursor.getString(cursor.getColumnIndex("townshipname")));
                            sale_entry_tv.btncustgroup.setText(cursor.getString(cursor.getColumnIndex("custgroupname")));
                            Boolean iscredit=cursor.getInt(cursor.getColumnIndex("iscredit"))==1?true:false;
                            sale_entry_tv.isCreditcustomer=iscredit;
                            if(iscredit){
                                sale_entry_tv.sh.get(0).setPay_type(2);
                                sale_entry_tv.btnpaytype.setText("Credit");

                            } else {
                                sale_entry_tv.sh.get(0).setPay_type(1);
                                sale_entry_tv.btnpaytype.setText("Cash Down");
                            }

                        } while (cursor.moveToNext());
                    }
                    cursor.close();
                }
                break;
        }


//        newCustomerName="";
//        newCustomerId=0;
    }

    public void insertingData(String table, JSONObject jobj) {
        try {
            switch (table) {
                case "Posuser":
                    JSONArray user = null;

                    user = jobj.getJSONArray("posuser");
                    for (int usercount = 0; usercount < user.length(); usercount++) {
                        JSONObject postobj = user.getJSONObject(usercount);
                        int userid = postobj.getInt("userid");
                        String name = postobj.optString("name", "null");
                        String shortdes = postobj.optString("shortdesc", "null");
                        int branchid = postobj.optInt("branchid");
                        String password = postobj.optString("password", "null");
                        int canchangesaleprice = postobj.optBoolean("canchangesaleprice", false) == true ? 1 : 0;
                        int canchangepurprice = postobj.optBoolean("canchangepurprice", false) == true ? 1 : 0;
                        int canchangedate = postobj.optBoolean("canchangedate", false) == true ? 1 : 0;
                        int defaultlocationid = postobj.optInt("defaultlocationid", 0); //1
                        int candiscount = postobj.optBoolean("candiscount", false) == true ? 1 : 0;
                        int isusetax = postobj.optBoolean("isusetax", false) == true ? 1 : 0;
                        int ishidepurprice = postobj.optBoolean("ishidepurprice", false) == true ? 1 : 0;
                        int ishidesaleprice = postobj.optBoolean("ishidesaleprice", false) == true ? 1 : 0;
                        int ishidepurcostprice = postobj.optBoolean("ishidepurcostprice", false) == true ? 1 : 0;
                        int isviewallsalepricelevel = postobj.optBoolean("isviewallsalepricelevel", false) == true ? 1 : 0;
                        int isinactive = postobj.optBoolean("isinactive", false) == true ? 1 : 0;

                        int defaultbranchid = postobj.optInt("defaultbranchid", 0);//1
                        int defaultcashid = postobj.optInt("defaultcashid", 1);
                        int isallowsysdatechange = postobj.optBoolean("isallowsysdatechange", false) == true ? 1 : 0;
                        int isallowovercreditlimit = postobj.optBoolean("isallowovercreditlimit", false) == true ? 1 : 0;
                        String isknockcode = postobj.optString("isknockcode", "null");
                        int istabletuser = postobj.optBoolean("istabletuser", false) == true ? 1 : 0;
                        int isallowpricelevel = postobj.optBoolean("isallowpricelevel", false) == true ? 1 : 0;
                        int canselectcustomer = postobj.optBoolean("canselectcustomer", false) == true ? 1 : 0;
                        int canselectlocation = postobj.optBoolean("canselectlocation", false) == true ? 1 : 0;
                        int salepricelevelid = postobj.optInt("salepricelevelid", 0);

                        ContentValues values2 = new ContentValues();
                        values2.put("userid", userid);
                        values2.put("name", name);
                        values2.put("shortdesc", shortdes);
                        values2.put("branchid", branchid);
                        values2.put("password", password);
                        values2.put("canchangesaleprice", canchangesaleprice);


                        values2.put("canchangepurprice", canchangepurprice);
                        values2.put("canchangedate", canchangedate);
                        values2.put("defaultlocationid", defaultlocationid);
                        values2.put("candiscount", candiscount);


                        values2.put("isusetax", isusetax);
                        values2.put("ishidepurprice", ishidepurprice);
                        values2.put("ishidesaleprice", ishidesaleprice);
                        values2.put("ishidepurcostprice", ishidepurcostprice);
                        values2.put("isviewallsalepricelevel", isviewallsalepricelevel);
                        values2.put("isinactive", isinactive);
                        values2.put("defaultbranchid", defaultbranchid);
                        values2.put("defaultcashid", defaultcashid);
                        values2.put("isallowsysdatechange", isallowsysdatechange);
                        values2.put("isallowovercreditlimit", isallowovercreditlimit);
                        values2.put("isknockcode", isknockcode);
                        values2.put("istabletuser", istabletuser);

                        values2.put("isallowpricelevel", isallowpricelevel);
                        values2.put("canselectcustomer", canselectcustomer);
                        values2.put("canselectlocation", canselectlocation);
                        values2.put("salepricelevelid", salepricelevelid);


                        DatabaseHelper.insertWithOnConflict("Posuser", null, values2, SQLiteDatabase.CONFLICT_REPLACE);
                    }
                    break;
                case "Customer":
                    JSONArray cust = jobj.getJSONArray("customer");


                    for (int custcount = 0; custcount < cust.length(); custcount++) {
                        JSONObject custobj = cust.getJSONObject(custcount);
                        long customerid = custobj.getLong("customerid");
                        String shortdesc = custobj.optString("shortdesc", "null");
                        String name = custobj.optString("name", "null");
                        int townshipid = custobj.optInt("townshipid", 1);
                        int pricelevelid = custobj.optInt("pricelevelid", 1);
                        int iscredit = custobj.optBoolean("iscredit", false) == true ? 1 : 0;
                        int balance = custobj.optInt("balance");
                        int creditlimit = custobj.optInt("creditlimit");
                        int dueindays = custobj.optInt("dueindays");
                        int discountpercent = custobj.optInt("discountpercent");
                        int isinactive = custobj.optBoolean("isinactive", false) == true ? 1 : 0;
                        int discountamount = custobj.optInt("discountamount");
                        int custgroupid = custobj.optInt("custgroupid");
                        int nationalcardid = custobj.optInt("nationalcardid");
                        int isdeleted = custobj.optInt("isdeleted");

                        String custgroupname = custobj.optString("custgroupname");
                        String custgroupcode = custobj.optString("custgroupcode");
                        String townshipname = custobj.optString("townshipname");
                        String townshipcode = custobj.optString("townshipcode");
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("customerid", customerid);
                        contentValues.put("name", name);
                        contentValues.put("shortdesc", shortdesc);
                        contentValues.put("townshipid", townshipid);
                        contentValues.put("pricelevelid", pricelevelid);
                        contentValues.put("iscredit", iscredit);
                        contentValues.put("balance", balance);
                        contentValues.put("creditlimit", creditlimit);
                        contentValues.put("dueindays", dueindays);
                        contentValues.put("discountpercent", discountpercent);
                        contentValues.put("isinactive", isinactive);
                        contentValues.put("discountamount", discountamount);
                        contentValues.put("custgroupid", custgroupid);
                        contentValues.put("nationalcardid", nationalcardid);
                        contentValues.put("isdeleted", isdeleted);
                        contentValues.put("custgroupname", custgroupname);

                        contentValues.put("custgroupcode", custgroupcode);
                        contentValues.put("townshipname", townshipname);
                        contentValues.put("townshipcode", townshipcode);
                        DatabaseHelper.insertWithOnConflict("Customer", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                txtProgress.setText((custcount + 1) + "/" + cust.length());
//                                pbDownload.setProgress(custcount + 1);
//                            }
//                        });
//                        Thread.sleep(3);


                    }
                    break;
                case "Location":
                    JSONArray loc = jobj.getJSONArray("location");
                    for (int loccount = 0; loccount < loc.length(); loccount++) {


                        JSONObject locobj = loc.getJSONObject(loccount);
                        long locationid = locobj.getLong("locationid");
                        int branchid = locobj.optInt("branchid", 1);
                        String parentgroupid = locobj.optString("parentgroupid", "null");
                        String oldparentgroupid = locobj.optString("oldparentgroupid");
                        int sortid = locobj.optInt("sortid");
                        String name = locobj.optString("name", "null");
                        String shortdesc = locobj.optString("shortdesc");
                        int iscalculate = locobj.optInt("iscalculate", 0);
                        int isdiffsaleprice = locobj.optInt("isdiffsaleprice");
                        int customerid = locobj.optInt("customerid", 1);
                        int isdeleted = locobj.optInt("isdeleted");
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("locationid", locationid);
                        contentValues.put("branchid", branchid);
                        contentValues.put("parentgroupid", parentgroupid);
                        contentValues.put("oldparentgroupid", oldparentgroupid);
                        contentValues.put("sortid", sortid);
                        contentValues.put("name", name);
                        contentValues.put("shortdesc", shortdesc);
                        contentValues.put("iscalculate", iscalculate);
                        contentValues.put("isdiffsaleprice", isdiffsaleprice);
                        contentValues.put("customerid", customerid);
                        contentValues.put("isdeleted", isdeleted);
                        DatabaseHelper.insertWithOnConflict("Location", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
                        //DatabaseHelper.execute(sqlString);
                    }
                    break;
                case "Usr_Code":
                    ContentValues usrcodevalue = new ContentValues();
                    JSONArray usr_code = jobj.getJSONArray("usr_code");
                    //JSONArray usr_code=data.getJSONObject(0).getJSONArray("usr_code");
                    for (int codecount = 0; codecount < usr_code.length(); codecount++) {

                        JSONObject codeobj = usr_code.getJSONObject(codecount);


                        long code = codeobj.getLong("code");
                        String usrcode = codeobj.getString("usrcode");
                        String description = codeobj.optString("description", usrcode);
                        double saleprice = codeobj.optDouble("saleprice", 0);
                        int salecur = codeobj.optInt("salecur", 1);
                        long classid = codeobj.getLong("class");
                        String classname = codeobj.optString("classname", "null");
                        long categoryid = codeobj.getLong("category");
                        String categoryname = codeobj.optString("categoryname", "null");
                        int unit_type = codeobj.optInt("unittype", 1);
                        String unitname = codeobj.optString("unitname", "null");
                        String unitshort = codeobj.optString("unitshort", "null");

                        usrcodevalue.put("code", code);
                        usrcodevalue.put("usr_code", usrcode);
                        usrcodevalue.put("description", description);
                        usrcodevalue.put("sale_price", saleprice);
                        usrcodevalue.put("sale_curr", salecur);
                        usrcodevalue.put("class", classid);
                        usrcodevalue.put("classname", classname);
                        usrcodevalue.put("categoryname", categoryname);
                        usrcodevalue.put("category", categoryid);
                        usrcodevalue.put("unit_type", unit_type);
                        usrcodevalue.put("unitname", unitname);
                        usrcodevalue.put("unitshort", unitshort);
                        DatabaseHelper.insertWithOnConflict("Usr_Code", null, usrcodevalue, SQLiteDatabase.CONFLICT_IGNORE);

                    }
                    break;
                case "Payment_Type":
                    JSONArray pay = jobj.getJSONArray("paymenttype");

                    for (int paycount = 0; paycount < pay.length(); paycount++) {
                        JSONObject paytobj = pay.getJSONObject(paycount);
                        long pay_type = paytobj.getLong("paytypeid");
                        String name = paytobj.optString("name", "null");
                        String shortdes = paytobj.optString("shortdesc", "null");
                        sqlString = "insert into Payment_Type(paytypeid,name,shortdesc)" +
                                " values(" + pay_type + ",'" + name + "','" + shortdes + "')";

                        DatabaseHelper.execute(sqlString);

                    }
                    break;
                case "Dis_Type":
                    JSONArray dis = jobj.getJSONArray("dis_type");

                    for (int discount = 0; discount < dis.length(); discount++) {

                        JSONObject distobj = dis.getJSONObject(discount);
                        long pay_type = distobj.getLong("itemdiscounttypeid");
                        String name = distobj.optString("name", "null");
                        String shortdes = distobj.optString("shortdesc", "null");
                        int paid = distobj.optBoolean("ispaid", false) == true ? 1 : 0;
                        int disamt = distobj.optInt("discount", 0);
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("itemdiscounttypeid", pay_type);
                        contentValues.put("name", name);
                        contentValues.put("shortdesc", shortdes);
                        contentValues.put("ispaid", paid);
                        contentValues.put("discount", disamt);
                        DatabaseHelper.insertWithOnConflict("Dis_Type", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);

                    }
                    break;
                case "System Setting":
                    JSONArray sys = jobj.getJSONArray("systemsetting");

                    for (int syscount = 0; syscount < sys.length(); syscount++) {



                        JSONObject systobj = sys.getJSONObject(syscount);

                        String title = systobj.getString("title");
                        int isuseunit = systobj.optBoolean("isuseunit", false) == true ? 1 : 0;
                        int isusetownship = systobj.optBoolean("isusetownship", false) == true ? 1 : 0;
                        int isuselocation = systobj.optBoolean("isuselocation", false) == true ? 1 : 0;
                        int isusemultipaid = systobj.optBoolean("isusemultipaid", false) == true ? 1 : 0;
                        int isusemultiinvoice = systobj.optBoolean("isusemultiinvoice", false) == true ? 1 : 0;
                        int isusesalesmen = systobj.optBoolean("isusesalesmen", false) == true ? 1 : 0;
                        int qtydecimalplaces = systobj.optBoolean("qtydecimalplaces", false) == true ? 1 : 0;
                        int pricedecimalplaces = systobj.optBoolean("pricedecimalplaces", false) == true ? 1 : 0;
                        int isusecustpricelevel = systobj.optBoolean("isusecustpricelevel", false) == true ? 1 : 0;
                        int isuseuserpricelevel = systobj.optBoolean("isuseuserpricelevel", false) == true ? 1 : 0;
                        int isusepic = systobj.optBoolean("isusepic", false) == true ? 1 : 0;
                        int isusemultipricelvl = systobj.optBoolean("isusemultipricelvl", false) == true ? 1 : 0;
                        int isusetax = systobj.optBoolean("isusetax", false) == true ? 1 : 0;
                        int isexclusivetax = systobj.optBoolean("isexclusivetax", false) == true ? 1 : 0;
                        double defaulttaxpercent = systobj.optDouble("defaulttaxpercent", 0);
                        int taxCal = systobj.getInt("taxcal");
                        int isusecustomergroup = systobj.optBoolean("isusecustomergroup", false) == true ? 1 : 0;
                        int isusedelivery = systobj.optBoolean("isusedelivery", false) == true ? 1 : 0;
                        int afterdiscount = systobj.optBoolean("afterdiscount", false) == true ? 1 : 0; // added by EKK on 05-11-2020
                        String receiptheaderline1 = systobj.getString("receiptheaderline1");
                        String receiptheaderline2 = systobj.getString("receiptheaderline2");
                        String receiptheaderline3 = systobj.getString("receiptheaderline3");
                        String receiptheaderline4 = systobj.getString("receiptheaderline4");


                        ContentValues contentValues = new ContentValues();
                        contentValues.put("title", title);
                        contentValues.put("isuseunit", isuseunit);
                        contentValues.put("isusetownship", isusetownship);
                        contentValues.put("isuselocation", isuselocation);
                        contentValues.put("isusemultipaid", isusemultipaid);
                        contentValues.put("isusemultiinvoice", isusemultiinvoice);
                        contentValues.put("isusesalesmen", isusesalesmen);
                        contentValues.put("qtydecimalplaces", qtydecimalplaces);
                        contentValues.put("pricedecimalplaces", pricedecimalplaces);
                        contentValues.put("isusecustpricelevel", isusecustpricelevel);
                        contentValues.put("isusetax", isusetax);
                        contentValues.put("isuseuserpricelevel", isuseuserpricelevel);
                        contentValues.put("isusepic", isusepic);
                        contentValues.put("isusemultipricelvl", isusemultipricelvl);
                        contentValues.put("defaulttaxpercent", defaulttaxpercent);
                        contentValues.put("taxCal", taxCal);
                        contentValues.put("isexclusivetax", isexclusivetax);
                        contentValues.put("isusecustomergroup", isusecustomergroup);
                        contentValues.put("isusedelivery", isusedelivery);
                        contentValues.put("afterdiscount", afterdiscount); // added by EKK on 05-11-2020
                        contentValues.put("receiptheaderline1", receiptheaderline1);
                        contentValues.put("receiptheaderline2", receiptheaderline2);
                        contentValues.put("receiptheaderline3", receiptheaderline3);
                        contentValues.put("receiptheaderline4", receiptheaderline4);

                        DatabaseHelper.insertWithOnConflict("SystemSetting", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
                    }
                    break;
                case "Salesmen":
                    JSONArray salesmen = null;

                    salesmen = jobj.getJSONArray("salesmen");
                    for (int salesmencount = 0; salesmencount < salesmen.length(); salesmencount++) {
                        JSONObject salesmenobj = salesmen.getJSONObject(salesmencount);
                        int id = salesmenobj.getInt("salesmenid");

                        String name = salesmenobj.optString("salesmenname", "null");
                        String shortdes = salesmenobj.optString("shortdesc", "null");
                        sqlString = "insert into Salesmen(salesmenid,salesmenname,shortdesc)values(" + id + ",'" + name + "','" + shortdes + "')";
                        DatabaseHelper.execute(sqlString);
                    }
                    break;


                case "class":
                    JSONArray cls = jobj.getJSONArray("class");

                    for (int syscount = 0; syscount < cls.length(); syscount++) {
                        JSONObject clsobj = cls.getJSONObject(syscount);
                        int classid = clsobj.getInt("classid");
                        String name = clsobj.optString("name");
                        //String  updateddatetime=clsobj.optString("updateddatetime");
                        int isdeleted = clsobj.optBoolean("isdeleted", false) == true ? 1 : 0;
                        int sortid = clsobj.optInt("sortid", 1);
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("classid", classid);
                        contentValues.put("name", name);
                        //contentValues.put("updateddatetime",updateddatetime);
                        contentValues.put("isdeleted", isdeleted);
                        contentValues.put("sortid", sortid);
                        //ry{
                        DatabaseHelper.insertWithOnConflict("class", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
                        //}catch (error)
                    }
                    break;
                case "category":
                    JSONArray cate = jobj.getJSONArray("category");

                    for (int syscount = 0; syscount < cate.length(); syscount++) {
                        JSONObject cateobj = cate.getJSONObject(syscount);
                        int categoryid = cateobj.getInt("categoryid");
                        int classid = cateobj.optInt("classid", 0);
                        String name = cateobj.optString("name");
                        String parentgroupid = cateobj.optString("parentgroupid");
                        String generatedid = cateobj.optString("generatedid");
                        String shortdesc = cateobj.optString("shortdesc");
                        int sortid = cateobj.optInt("sortid", 1);
                        int israw = cateobj.optInt("israw");
                        String imagepath = cateobj.optString("imagepath");
                        //byte categoryimage=(byte)cateobj.opt("categoryimage");
                        String colorrgb = cateobj.optString("colorrgb");
                        int ismodifiercategory = cateobj.optInt("ismodifiercategory");
                        int issetmenu = cateobj.optInt("issetmenu");
                        // Date updateddatetime=cateobj.;
                        int isdeleted = cateobj.optInt("isdeleted");

                        ContentValues contentValues = new ContentValues();
                        contentValues.put("categoryid", categoryid);
                        contentValues.put("classid", classid);
                        contentValues.put("name", name);
                        contentValues.put("parentgroupid", parentgroupid);
                        contentValues.put("generatedid", generatedid);
                        contentValues.put("shortdesc", shortdesc);
                        contentValues.put("israw", israw);
                        contentValues.put("imagepath", imagepath);
                        //contentValues.put("categoryimage", categoryimage);
                        contentValues.put("colorrgb", colorrgb);
                        contentValues.put("ismodifiercategory", ismodifiercategory);
                        contentValues.put("issetmenu", issetmenu);
                        // contentValues.put("updateddatetime",updateddatetime);
                        contentValues.put("isdeleted", isdeleted);
                        contentValues.put("sortid", sortid);
                        DatabaseHelper.insertWithOnConflict("category", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
                    }

                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void UpSertingData(String table, JSONObject jobj) {
        try {
            switch (table) {
                case "Posuser":
                    JSONArray user = null;
                    user = jobj.getJSONArray("posuser");
                    for (int usercount = 0; usercount < user.length(); usercount++) {
                        JSONObject postobj = user.getJSONObject(usercount);
                        int userid = postobj.getInt("userid");
                        String name = postobj.optString("name", "null");
                        String shortdes = postobj.optString("shortdesc", "null");
                        int branchid = postobj.optInt("branchid");
                        String passwordFromJobj=postobj.optString("password","");
                        String password = "";
                        if(passwordFromJobj!="null"){
                                byte[] passwordsbytes=passwordFromJobj.getBytes(StandardCharsets.UTF_8);
                                passwordsbytes=Base64.decode(passwordsbytes,Base64.DEFAULT);
                                try {
                                    password = new String(passwordsbytes,"UTF-8");
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                                Log.i("Password",postobj.optString("password")+"    "+password);
                        }
                        int canchangesaleprice = postobj.optBoolean("canchangesaleprice", false) == true ? 1 : 0;
                        int canchangepurprice = postobj.optBoolean("canchangepurprice", false) == true ? 1 : 0;
                        int canchangedate = postobj.optBoolean("canchangedate", false) == true ? 1 : 0;
                        int defaultlocationid = postobj.optInt("defaultlocationid", 0);//1
                        int candiscount = postobj.optBoolean("candiscount", false) == true ? 1 : 0;
                        int isusetax = postobj.optBoolean("isusetax", false) == true ? 1 : 0;
                        int ishidepurprice = postobj.optBoolean("ishidepurprice", false) == true ? 1 : 0;
                        int ishidesaleprice = postobj.optBoolean("ishidesaleprice", false) == true ? 1 : 0;
                        int ishidepurcostprice = postobj.optBoolean("ishidepurcostprice", false) == true ? 1 : 0;
                        int isviewallsalepricelevel = postobj.optBoolean("isviewallsalepricelevel", false) == true ? 1 : 0;
                        int isinactive = postobj.optBoolean("isinactive", false) == true ? 1 : 0;

                        int defaultbranchid = postobj.optInt("defaultbranchid", 0);//1
                        int defaultcashid = postobj.optInt("defaultcashid", 1);
                        int isallowsysdatechange = postobj.optBoolean("isallowsysdatechange", false) == true ? 1 : 0;
                        int isallowovercreditlimit = postobj.optBoolean("isallowovercreditlimit", false) == true ? 1 : 0;
                        String isknockcode = postobj.optString("isknockcode", "null");
                        int istabletuser = postobj.optBoolean("istabletuser", false) == true ? 1 : 0;
                        int istvuser = postobj.optBoolean("istvuser", false) == true ? 1 : 0;
                        int isallowpricelevel = postobj.optBoolean("isallowpricelevel", false) == true ? 1 : 0;
                        int canselectcustomer = postobj.optBoolean("canselectcustomer", false) == true ? 1 : 0;
                        int canselectlocation = postobj.optBoolean("canselectlocation", false) == true ? 1 : 0;
                        int salepricelevelid = postobj.optInt("salepricelevelid");
                        int userroleid = postobj.optInt("userroleid");

                        ContentValues values2 = new ContentValues();
                        values2.put("userid", userid);
                        values2.put("name", name);
                        values2.put("shortdesc", shortdes);
                        values2.put("branchid", branchid);
                        values2.put("password", password);
                        values2.put("canchangesaleprice", canchangesaleprice);


                        values2.put("canchangepurprice", canchangepurprice);
                        values2.put("canchangedate", canchangedate);
                        values2.put("defaultlocationid", defaultlocationid);
                        values2.put("candiscount", candiscount);


                        values2.put("isusetax", isusetax);
                        values2.put("ishidepurprice", ishidepurprice);
                        values2.put("ishidesaleprice", ishidesaleprice);
                        values2.put("ishidepurcostprice", ishidepurcostprice);
                        values2.put("isviewallsalepricelevel", isviewallsalepricelevel);
                        values2.put("isinactive", isinactive);
                        values2.put("defaultbranchid", defaultbranchid);
                        values2.put("defaultcashid", defaultcashid);
                        values2.put("isallowsysdatechange", isallowsysdatechange);
                        values2.put("isallowovercreditlimit", isallowovercreditlimit);
                        values2.put("isknockcode", isknockcode);
                        values2.put("istabletuser", istabletuser);
                        values2.put("istvuser", istvuser);
                        values2.put("isallowpricelevel", isallowpricelevel);
                        values2.put("canselectcustomer", canselectcustomer);
                        values2.put("canselectlocation", canselectlocation);
                        values2.put("salepricelevelid", salepricelevelid);
                        values2.put("userroleid", userroleid);

                        DatabaseHelper.upsertWithOnConflit("Posuser", null, values2, SQLiteDatabase.CONFLICT_REPLACE, "userid=?", new String[]{String.valueOf(userid)});
                    }
                    break;
                case "Customer":
                    JSONArray cust = jobj.getJSONArray("customer");


                    for (int custcount = 0; custcount < cust.length(); custcount++) {
                        JSONObject custobj = cust.getJSONObject(custcount);
                        long customerid = custobj.getLong("customerid");
                        String shortdesc = custobj.optString("shortdesc", "null");
                        String name = custobj.optString("name", "null");
                        int townshipid = custobj.optInt("townshipid", 1);
                        int pricelevelid = custobj.optInt("pricelevelid", 0);
                        int iscredit = custobj.optBoolean("iscredit", false) == true ? 1 : 0;
                        int balance = custobj.optInt("balance");
                        int creditlimit = custobj.optInt("creditlimit");
                        int dueindays = custobj.optInt("dueindays");
                        int discountpercent = custobj.optInt("discountpercent");
                        int isinactive = custobj.optBoolean("isinactive", false) == true ? 1 : 0;
                        int discountamount = custobj.optInt("discountamount");
                        int custgroupid = custobj.optInt("custgroupid");
                        int nationalcardid = custobj.optInt("nationalcardid");
                        int isdeleted = custobj.optBoolean("isdeleted", false) == true ? 1 : 0;

                        String custgroupname = custobj.optString("custgroupname");
                        String custgroupcode = custobj.optString("custgroupcode");
                        String townshipname = custobj.optString("townshipname");
                        String townshipcode = custobj.optString("townshipcode");
//                        int isinactive=custobj.optBoolean("isinactive",false)==true?1:0;
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("customerid", customerid);
                        contentValues.put("name", name);
                        contentValues.put("shortdesc", shortdesc);
                        contentValues.put("townshipid", townshipid);
                        contentValues.put("pricelevelid", pricelevelid);
                        contentValues.put("iscredit", iscredit);
                        contentValues.put("balance", balance);
                        contentValues.put("creditlimit", creditlimit);
                        contentValues.put("dueindays", dueindays);
                        contentValues.put("discountpercent", discountpercent);
                        contentValues.put("isinactive", isinactive);
                        contentValues.put("discountamount", discountamount);
                        contentValues.put("custgroupid", custgroupid);
                        contentValues.put("nationalcardid", nationalcardid);
                        contentValues.put("isdeleted", isdeleted);
                        contentValues.put("custgroupname", custgroupname);

                        contentValues.put("custgroupcode", custgroupcode);
                        contentValues.put("townshipname", townshipname);
                        contentValues.put("townshipcode", townshipcode);
                        DatabaseHelper.upsertWithOnConflit("Customer", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE, "customerid=?", new String[]{String.valueOf(customerid)});


                    }
                    break;
                case "Location":
                    JSONArray loc = jobj.getJSONArray("location");
                    for (int loccount = 0; loccount < loc.length(); loccount++) {

                        JSONObject locobj = loc.getJSONObject(loccount);
                        long locationid = locobj.getLong("locationid");
                        int branchid = locobj.optInt("branchid", 1);
                        String parentgroupid = locobj.optString("parentgroupid", "null");
                        String oldparentgroupid = locobj.optString("oldparentgroupid");
                        int sortid = locobj.optInt("sortid");
                        String name = locobj.optString("name", "null");
                        String shortdesc = locobj.optString("shortdesc");
                        int iscalculate = locobj.optInt("iscalculate", 0);
                        int isdiffsaleprice = locobj.optInt("isdiffsaleprice");
                        int customerid = locobj.optInt("customerid", 1);
                        int isdeleted = locobj.optBoolean("isdeleted", false) == true ? 1 : 0;
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("locationid", locationid);
                        contentValues.put("branchid", branchid);
                        contentValues.put("parentgroupid", parentgroupid);
                        contentValues.put("oldparentgroupid", oldparentgroupid);
                        contentValues.put("sortid", sortid);
                        contentValues.put("name", name);
                        contentValues.put("shortdesc", shortdesc);
                        contentValues.put("iscalculate", iscalculate);
                        contentValues.put("isdiffsaleprice", isdiffsaleprice);
                        contentValues.put("customerid", customerid);
                        contentValues.put("isdeleted", isdeleted);
                        DatabaseHelper.upsertWithOnConflit("Location", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE, "locationid=?", new String[]{String.valueOf(locationid)});
                        //DatabaseHelper.execute(sqlString);
                    }
                    break;
                case "Usr_Code":
                case "Unit":
                    ContentValues usrcodevalue = new ContentValues();
                    JSONArray usr_code = jobj.getJSONArray("usr_code");
                    //JSONArray usr_code=data.getJSONObject(0).getJSONArray("usr_code");
                    for (int codecount = 0; codecount < usr_code.length(); codecount++) {   //added by ZYP for #YGN2-21077
                        JSONObject codeobj = usr_code.getJSONObject(codecount);
                        long code = codeobj.getLong("code");
                        String sql = "DELETE FROM usr_code where code = " + code;
                        DatabaseHelper.execute(sql);
                    }
                    for (int codecount = 0; codecount < usr_code.length(); codecount++) {

                        JSONObject codeobj = usr_code.getJSONObject(codecount);


                        long code = codeobj.getLong("code");
                        String usrcode = codeobj.getString("usrcode");
                        String description = codeobj.optString("description", usrcode);
                        double saleprice = codeobj.optDouble("saleprice", 0);
                        int salecur = codeobj.optInt("salecur", 1);
                        long classid = codeobj.getLong("class");
                        String classname = codeobj.optString("classname", "null");
                        long categoryid = codeobj.getLong("category");
                        String categoryname = codeobj.optString("categoryname", "null");
                        int unit_type = codeobj.optInt("unittype", 1);
                        String unitname = codeobj.optString("unitname", "null");
                        String unitshort = (codeobj.optString("unitshort", "null").equals("null")) ? "" : codeobj.optString("unitshort", "null");
//
                        double saleprice1 = codeobj.optDouble("saleprice1", 0);
                        double saleprice2 = codeobj.optDouble("saleprice2", 0);
                        double saleprice3 = codeobj.optDouble("saleprice3", 0);
                        double saleprice4 = codeobj.optDouble("saleprice4", 0);
                        double saleprice5 = codeobj.optDouble("saleprice5", 0);
                        int isdeleted = codeobj.optBoolean("isdeleted", false) == true ? 1 : 0;
                        int isinactive = codeobj.optBoolean("isinactive", false) == true ? 1 : 0;
                        int openprice = codeobj.optBoolean("isopenprice", false) == true ? 1 : 0; //added by EKK on 05-11-2020
                        int CalNoTax = codeobj.optBoolean("iscalnotax", false) == true ? 1 : 0; //added by EKK on 06-11-2020

                        usrcodevalue.put("code", code);
                        usrcodevalue.put("usr_code", usrcode);
                        usrcodevalue.put("description", description.length() == 0 ? usrcode : description);
                        usrcodevalue.put("sale_price", saleprice);
                        usrcodevalue.put("sale_curr", salecur);
                        usrcodevalue.put("class", classid);
                        usrcodevalue.put("classname", classname);
                        usrcodevalue.put("categoryname", categoryname);
                        usrcodevalue.put("category", categoryid);
                        usrcodevalue.put("unit_type", unit_type);
                        usrcodevalue.put("unitname", unitname);
                        usrcodevalue.put("unitshort", unitshort);
                        usrcodevalue.put("saleprice1", saleprice1);
                        usrcodevalue.put("saleprice2", saleprice2);
                        usrcodevalue.put("saleprice3", saleprice3);
                        usrcodevalue.put("saleprice4", saleprice4);
                        usrcodevalue.put("saleprice5", saleprice5);
                        usrcodevalue.put("isdeleted", isdeleted);
                        usrcodevalue.put("isinactive", isinactive);
                        usrcodevalue.put("open_price", openprice); //added by EKK on 05-11-2020
                        usrcodevalue.put("CalNoTax", CalNoTax); //added by EKK on 06-11-2020


                        DatabaseHelper.upsertWithOnConflit("Usr_Code", null, usrcodevalue, SQLiteDatabase.CONFLICT_IGNORE, "code=? and unit_type=?", new String[]{String.valueOf(code), String.valueOf(unit_type)});

                    }
                    break;
                case "Payment_Type":
                    JSONArray pay = jobj.getJSONArray("paymenttype");

                    for (int paycount = 0; paycount < pay.length(); paycount++) {
                        JSONObject paytobj = pay.getJSONObject(paycount);
                        long pay_type = paytobj.getLong("paytypeid");
                        String name = paytobj.optString("name", "null");
                        String shortdes = paytobj.optString("shortdesc", "null");
                        sqlString = "insert into Payment_Type(paytypeid,name,shortdesc)" +
                                " values(" + pay_type + ",'" + name + "','" + shortdes + "')";

                        DatabaseHelper.execute(sqlString);

                    }
                    break;
                case "Dis_Type":
                    JSONArray dis = jobj.getJSONArray("dis_type");

                    for (int discount = 0; discount < dis.length(); discount++) {
                        /*[{"itemdiscounttypeid": 0,"name": "Normal","shortdesc": null,"ispaid": true,"discount": null,"accountid": null,"puraccountid": null,"updateddatetime": "1990-01-01T00:00:00"},{"itemdiscounttypeid": 1,"name": "Discount 5%","shortdesc": "5%","ispaid": true,"discount": 5,"accountid": null,"puraccountid": 31,"updateddatetime": "1990-01-01T00:00:00"},{"itemdiscounttypeid": 2,"name": "Discount 10%","shortdesc": "10%","ispaid": true,"discount": 10,"accountid": null,"puraccountid": 31,"updateddatetime": "1990-01-01T00:00:00"},{"itemdiscounttypeid": 3,"name": "Free Gift","shortdesc": "foc","ispaid": false,"discount": 0,"accountid": 116,"puraccountid": 92,"updateddatetime": "1990-01-01T00:00:00"},{"itemdiscounttypeid": 5,"name": "Discount","shortdesc": "---","ispaid": true,"discount": 999,"accountid": null,"puraccountid": 31,"updateddatetime": "1990-01-01T00:00:00"},{"itemdiscounttypeid": 6,"name": "Office Use","shortdesc": "off","ispaid": false,"discount": null,"accountid": 86,"puraccountid": null,"updateddatetime": "1990-01-01T00:00:00"},{"itemdiscounttypeid": 7,"name": "Promotion","shortdesc": "Pro","ispaid": false,"discount": 0,"accountid": 112,"puraccountid": null,"updateddatetime": "1990-01-01T00:00:00"},{"itemdiscounttypeid": 4,"name": "Sample","shortdesc": "spl","ispaid": false,"discount": 0,"accountid": 83,"puraccountid": 92,"updateddatetime": "1990-01-01T00:00:00"}]*/
                        JSONObject distobj = dis.getJSONObject(discount);
                        long pay_type = distobj.getLong("itemdiscounttypeid");
                        String name = distobj.optString("name", "null");
                        String shortdes = distobj.optString("shortdesc", "null");
                        int paid = distobj.optBoolean("ispaid", false) == true ? 1 : 0;
                        int disamt = distobj.optInt("discount", 0);
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("itemdiscounttypeid", pay_type);
                        contentValues.put("name", name);
                        contentValues.put("shortdesc", shortdes);
                        contentValues.put("ispaid", paid);
                        contentValues.put("discount", disamt);
                        DatabaseHelper.upsertWithOnConflit("Dis_Type", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE, "itemdiscounttypeid=?", new String[]{String.valueOf(pay_type)});

                    }
                    break;
                case "SystemSetting":
                    JSONArray sys = jobj.getJSONArray("systemsetting");

                    for (int syscount = 0; syscount < sys.length(); syscount++) {
                        JSONObject systobj = sys.getJSONObject(syscount);

                        String title = systobj.getString("title");
                        int isuseunit = systobj.optBoolean("isuseunit", false) == true ? 1 : 0;
                        int isusetownship = systobj.optBoolean("isusetownship", false) == true ? 1 : 0;
                        int isuselocation = systobj.optBoolean("isuselocation", false) == true ? 1 : 0;
                        int isusemultipaid = systobj.optBoolean("isusemultipaid", false) == true ? 1 : 0;
                        int isusemultiinvoice = systobj.optBoolean("isusemultiinvoice", false) == true ? 1 : 0;
                        int isusesalesmen = systobj.optBoolean("isusesalesmen", false) == true ? 1 : 0;
                        int qtydecimalplaces = systobj.optInt("qtydecimalplaces");
                        int pricedecimalplaces = systobj.optInt("pricedecimalplaces");
                        int isusecustpricelevel = systobj.optBoolean("isusecustpricelevel", false) == true ? 1 : 0;
                        int isuseuserpricelevel = systobj.optBoolean("isuseuserpricelevel", false) == true ? 1 : 0;
                        int isusepic = systobj.optBoolean("isusepic", false) == true ? 1 : 0;
                        int isusemultipricelvl = systobj.optBoolean("isusemultipricelvl", false) == true ? 1 : 0;
                        int isusetax = systobj.optBoolean("isusetax", false) == true ? 1 : 0;
                        int isexclusivetax = systobj.optBoolean("isexclusivetax", false) == true ? 1 : 0;
                        double defaulttaxpercent = systobj.optDouble("defaulttaxpercent", 0);
                        int taxCal = systobj.getInt("taxcal");
                        int isusecustomergroup = systobj.optBoolean("isusecustomergroup", false) == true ? 1 : 0;
                        int isusedelivery = systobj.optBoolean("isusedelivery", false) == true ? 1 : 0;
                        int afterdiscount = systobj.optBoolean("afterdiscount", false) == true ? 1 : 0; // added by EKK on 05-11-2020
                        String receiptheaderline1 = systobj.getString("receiptheaderline1");
                        String receiptheaderline2 = systobj.getString("receiptheaderline2");
                        String receiptheaderline3 = systobj.getString("receiptheaderline3");
                        String receiptheaderline4 = systobj.getString("receiptheaderline4");
                        int isusespecialprice = systobj.optBoolean("isusespecialprice", false) == true ? 1 : 0; // added by EKK on 05-11-2020
                        int isusemulticash = systobj.optBoolean("isusemulticash", false) == true ? 1 : 0; // added by EKK on 17-11-2020

                        ContentValues contentValues = new ContentValues();
                        contentValues.put("title", title);
                        contentValues.put("isuseunit", isuseunit);
                        contentValues.put("isusetownship", isusetownship);
                        contentValues.put("isuselocation", isuselocation);
                        contentValues.put("isusemultipaid", isusemultipaid);
                        contentValues.put("isusemultiinvoice", isusemultiinvoice);
                        contentValues.put("isusesalesmen", isusesalesmen);
                        contentValues.put("qtydecimalplaces", qtydecimalplaces);
                        contentValues.put("pricedecimalplaces", pricedecimalplaces);
                        contentValues.put("isusecustpricelevel", isusecustpricelevel);
                        contentValues.put("isusetax", isusetax);
                        contentValues.put("isuseuserpricelevel", isuseuserpricelevel);
                        contentValues.put("isusepic", isusepic);
                        contentValues.put("isusemultipricelvl", isusemultipricelvl);
                        contentValues.put("defaulttaxpercent", defaulttaxpercent);
                        contentValues.put("taxCal", taxCal);
                        contentValues.put("isexclusivetax", isexclusivetax);
                        contentValues.put("isusecustomergroup", isusecustomergroup);
                        contentValues.put("isusedelivery", isusedelivery);
                        contentValues.put("afterdiscount", afterdiscount); // added by EKK on 05-11-2020
                        contentValues.put("receiptheaderline1", receiptheaderline1);
                        contentValues.put("receiptheaderline2", receiptheaderline2);
                        contentValues.put("receiptheaderline3", receiptheaderline3);
                        contentValues.put("receiptheaderline4", receiptheaderline4);
                        contentValues.put("isusespecialprice", isusespecialprice);
                        contentValues.put("isusemulticash", isusemulticash); //added by EKK on 17-11-2020

                        DatabaseHelper.deleteWithOnConflit("SystemSetting", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE, "title=?", title);
                    }
                    break;
                case "Salesmen":
                    JSONArray salesmen = null;
                    salesmen = jobj.getJSONArray("salesmen");
                    for (int salesmencount = 0; salesmencount < salesmen.length(); salesmencount++) {
                        JSONObject salesmenobj = salesmen.getJSONObject(salesmencount);
                        int id = salesmenobj.getInt("salesmenid");
                        if (salesmenobj.getBoolean("isinactive")) {
                            sqlString = "delete from Salesmen where salesmenid=" + id;
                            DatabaseHelper.execute(sqlString);
                            continue;
                        }
                        String name = salesmenobj.optString("salesmenname", "null");
                        String shortdes = salesmenobj.optString("shortdesc", "null");
//
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("salesmenid", id);
                        contentValues.put("salesmenname", name);
                        contentValues.put("shortdesc", shortdes);
                        DatabaseHelper.upsertWithOnConflit("Salesmen", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE, "salesmenid=?", new String[]{String.valueOf(id)});
                    }
                    break;


                case "Class":
                    JSONArray cls = jobj.getJSONArray("class");

                    for (int syscount = 0; syscount < cls.length(); syscount++) {
                        JSONObject clsobj = cls.getJSONObject(syscount);
                        int classid = clsobj.getInt("classid");
                        String name = clsobj.optString("name");
                        //String  updateddatetime=clsobj.optString("updateddatetime");
                        int isdeleted = clsobj.optBoolean("isdeleted", false) == true ? 1 : 0;
                        int sortid = clsobj.optInt("sortid", 1);
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("classid", classid);
                        contentValues.put("name", name);
                        //contentValues.put("updateddatetime",updateddatetime);
                        contentValues.put("isdeleted", isdeleted);
                        contentValues.put("sortid", sortid);
                        //ry{
                        DatabaseHelper.upsertWithOnConflit("class", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE, "classid=?", new String[]{String.valueOf(classid)});
                        //}catch (error)
                    }
                    break;
                case "Category":
                    JSONArray cate = jobj.getJSONArray("category");

                    for (int syscount = 0; syscount < cate.length(); syscount++) {
                        JSONObject cateobj = cate.getJSONObject(syscount);
                        int categoryid = cateobj.getInt("categoryid");
                        int classid = cateobj.optInt("classid", 0);
                        String name = cateobj.optString("name");
                        String parentgroupid = cateobj.optString("parentgroupid");
                        String generatedid = cateobj.optString("generatedid");
                        String shortdesc = cateobj.optString("shortdesc");
                        int sortid = cateobj.optInt("sortid", 1);
                        int israw = cateobj.optInt("israw");
                        String imagepath = cateobj.optString("imagepath");
                        //byte categoryimage=(byte)cateobj.opt("categoryimage");
                        String colorrgb = cateobj.optString("colorrgb");
                        int ismodifiercategory = cateobj.optInt("ismodifiercategory");
                        int issetmenu = cateobj.optInt("issetmenu");
                        // Date updateddatetime=cateobj.;
                        int isdeleted = cateobj.optBoolean("isdeleted", false) == true ? 1 : 0;

                        ContentValues contentValues = new ContentValues();
                        contentValues.put("categoryid", categoryid);
                        contentValues.put("classid", classid);
                        contentValues.put("name", name);
                        contentValues.put("parentgroupid", parentgroupid);
                        contentValues.put("generatedid", generatedid);
                        contentValues.put("shortdesc", shortdesc);
                        contentValues.put("israw", israw);
                        contentValues.put("imagepath", imagepath);
                        //contentValues.put("categoryimage", categoryimage);
                        contentValues.put("colorrgb", colorrgb);
                        contentValues.put("ismodifiercategory", ismodifiercategory);
                        contentValues.put("issetmenu", issetmenu);
                        // contentValues.put("updateddatetime",updateddatetime);
                        contentValues.put("isdeleted", isdeleted);
                        contentValues.put("sortid", sortid);
                        DatabaseHelper.upsertWithOnConflit("category", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE, "categoryid=?", new String[]{String.valueOf(categoryid)});
                    }

                    break;
                case "Branch":
                    JSONArray branch = jobj.getJSONArray("branch");

                    for (int syscount = 0; syscount < branch.length(); syscount++) {
                        JSONObject branchobj = branch.getJSONObject(syscount);
                        int branchid = branchobj.getInt("branchid");
                        String name = branchobj.optString("name");
                        String shortdesc = branchobj.optString("shortdesc");
                        int isdeleted = branchobj.optBoolean("isdeleted", false) == true ? 1 : 0;
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("branchid", branchid);
                        contentValues.put("name", name);
                        contentValues.put("shortdesc", shortdesc);
                        contentValues.put("isdeleted", isdeleted);
                        DatabaseHelper.upsertWithOnConflit("branch", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE, "branchid=?", new String[]{String.valueOf(branchid)});
                    }
                    break;
                case "Branch_User":
                    JSONArray branch_user = jobj.getJSONArray("branch_user");

                    for (int syscount = 0; syscount < branch_user.length(); syscount++) {
                        JSONObject branchobj = branch_user.getJSONObject(syscount);
                        int userid = branchobj.getInt("userid");
                        int branchid = branchobj.getInt("branchid");
                        String name = branchobj.optString("name");
                        int isenabled = branchobj.getBoolean("isenabled")==true?1:0;
                        int isdefaultbranch =  branchobj.getBoolean("isdefaultbranch")==true?1:0;

                        ContentValues contentValues = new ContentValues();
                        contentValues.put("userid", userid);
                        contentValues.put("branchid", branchid);
                        contentValues.put("name", name);
                        contentValues.put("isenabled", isenabled);
                        contentValues.put("isdefaultbranch", isdefaultbranch);
                        DatabaseHelper.upsertWithOnConflit("Branch_User", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE, "branchid=? and userid=?", new String[]{String.valueOf(branchid),String.valueOf(userid)});
                    }
                    break;
                //added by EKK on 28-10-2020
                case "usr_code_img":
                    JSONArray usr_code_img = null;
                    usr_code_img = jobj.getJSONArray("usr_code_img");
                    for (int imgcount = 0; imgcount < usr_code_img.length(); imgcount++) {
                        JSONObject usr_code_imgobj = usr_code_img.getJSONObject(imgcount);
                        String usrcode = usr_code_imgobj.optString("usrcode");
                        String image = usr_code_imgobj.optString("image", "null");
                        String path = usr_code_imgobj.optString("path", "null");

                        ContentValues contentValues = new ContentValues();
                        contentValues.put("usr_code", usrcode);
                        contentValues.put("code_img", image);
                        contentValues.put("path", path);

                        DatabaseHelper.upsertWithOnConflit("usr_code_img", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE, "usr_code=?", new String[]{String.valueOf(usrcode)});
                    }
                    break;

                //added by EKK on 11-11-2020
                case "s_saleprice":
                    JSONArray s_saleprice = null;
                    s_saleprice = jobj.getJSONArray("s_saleprice");
                    for (int sCount = 0; sCount < s_saleprice.length(); sCount++) {
                        JSONObject s_salepriceObj = s_saleprice.getJSONObject(sCount);

                        int id = s_salepriceObj.optInt("id");
                        String usrcode = s_salepriceObj.optString("usrcode");
                        int unittype = s_salepriceObj.optInt("unittype");
                        unittype = (unittype == 0 ? 1 : unittype);
                        double minqty = s_salepriceObj.optDouble("minqty");
                        double maxqty = s_salepriceObj.optDouble("maxqty");
                        int pricelevel = s_salepriceObj.optInt("pricelevel");
                        int inactive = s_salepriceObj.optBoolean("inactive", false) == true ? 1 : 0;
                        int isdeleted = s_salepriceObj.optBoolean("isdeleted", false) == true ? 1 : 0;


                        ContentValues contentValues = new ContentValues();
                        contentValues.put("id", id);
                        contentValues.put("usrcode", usrcode);
                        contentValues.put("unittype", unittype);
                        contentValues.put("minqty", minqty);
                        contentValues.put("maxqty", maxqty);
                        contentValues.put("pricelevel", pricelevel);
                        contentValues.put("inactive", inactive);
                        contentValues.put("isdeleted", isdeleted);

                        DatabaseHelper.upsertWithOnConflit("s_saleprice", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE, "id=?", new String[]{String.valueOf(id)});
                    }
                    break;

                //added by EKK on 16-11-2020
                case "Alias_Code":
                    JSONArray alias_code = null;
                    alias_code = jobj.getJSONArray("alias_code");
                    for (int sCount = 0; sCount < alias_code.length(); sCount++) {
                        JSONObject alias_codeObj = alias_code.getJSONObject(sCount);

                        String al_code = alias_codeObj.optString("aliascode");
                        String usrcode = alias_codeObj.optString("usrcode");
                        int isdeleted = alias_codeObj.optBoolean("isdeleted", false) == true ? 1 : 0;


                        ContentValues contentValues = new ContentValues();

                        contentValues.put("al_code", al_code);
                        contentValues.put("usr_code", usrcode);
                        contentValues.put("isdeleted", isdeleted);

                        DatabaseHelper.upsertWithOnConflit("alias_code", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE, "al_code=?", new String[]{String.valueOf(al_code)});
                    }
                    break;

                //added by EKK on 16-11-2020
                case "cashbook_user":
                    JSONArray cashbook_user = null;
                    cashbook_user = jobj.getJSONArray("cashbook_user");
                    for (int sCount = 0; sCount < cashbook_user.length(); sCount++) {
                        JSONObject cashbook_userObj = cashbook_user.getJSONObject(sCount);

                        int userid = cashbook_userObj.getInt("userid");
                        int accountid = cashbook_userObj.getInt("accountid");
                        String name = cashbook_userObj.optString("name", "null");
                        int isenabled = cashbook_userObj.optBoolean("isenabled", false) == true ? 1 : 0;
                        int acctgroupid = cashbook_userObj.getInt("acctgroupid");
                        int isdefaultcash = cashbook_userObj.optBoolean("isdefaultcash", false) == true ? 1 : 0;


                        ContentValues contentValues = new ContentValues();

                        contentValues.put("userid", userid);
                        contentValues.put("accountid", accountid);
                        contentValues.put("name", name);
                        contentValues.put("isenabled", isenabled);
                        contentValues.put("acctgroupid", acctgroupid);
                        contentValues.put("isdefaultcash", isdefaultcash);

                        DatabaseHelper.upsertWithOnConflit("cashbook_user", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE, "userid = ? and accountid = ?", new String[]{String.valueOf(userid), String.valueOf(accountid)});
                    }
                    break;

                //added by EKK on 16-11-2020
                case "userroles":
                    JSONArray userroles = null;
                    userroles = jobj.getJSONArray("userroles");
                    for (int sCount = 0; sCount < userroles.length(); sCount++) {
                        JSONObject userrolesObj = userroles.getJSONObject(sCount);

                        int userroleid = userrolesObj.getInt("userroleid");
                        String userrolename = userrolesObj.getString("userrolename");
                        String userroledescription = userrolesObj.getString("userroledescription");
                        int isallow = userrolesObj.optBoolean("isallow", false) == true ? 1 : 0;
                        int isallowupdated = userrolesObj.optBoolean("isallowupdated", false) == true ? 1 : 0;
                        int isallowdeleted = userrolesObj.optBoolean("isallowdeleted", false) == true ? 1 : 0;
                        int isallowusersview = userrolesObj.optBoolean("isallowallusersview", false) == true ? 1 : 0;
                        int menusubgroupid = userrolesObj.getInt("menusubgroupid");


                        ContentValues contentValues = new ContentValues();

                        contentValues.put("userroleid", userroleid);
                        contentValues.put("userrolename", userrolename);
                        contentValues.put("userroledescription", userroledescription);
                        contentValues.put("isallow", isallow);
                        contentValues.put("isallowupdated", isallowupdated);
                        contentValues.put("isallowdeleted", isallowdeleted);
                        contentValues.put("isallowallusersview", isallowusersview);
                        contentValues.put("menusubgroupid", menusubgroupid);

                        DatabaseHelper.insertWithOnConflict("userroles", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
                        //DatabaseHelper.upsertWithOnConflit("userroles",null,contentValues,SQLiteDatabase.CONFLICT_REPLACE, "userroleid = ? ",new String[] { String.valueOf(userroleid)} );
                    }
                    break;

                case "discount_code":
                    JSONArray discountcode = null;
                    discountcode = jobj.getJSONArray("discount_code");

                    DatabaseHelper.execute("DELETE FROM discount_code");

                    for (int i = 0; i < discountcode.length(); i++) {
                        JSONObject discount = discountcode.getJSONObject(i);
                        ContentValues cv = new ContentValues();

                        long code = discount.getLong("code");
                        int unit_type = discount.optInt("unittypeid", 1);
                        int locationid = discount.getInt("locationid");

                        cv.put("code", discount.getLong("code"));
                        cv.put("locationid", discount.getInt("locationid"));
                        cv.put("unit_type", discount.optInt("unittypeid", 1));

                        cv.put("disamount", discount.optDouble("discountamount", 0));
                        cv.put("disamount1", discount.optDouble("discount1amount", 0));
                        cv.put("disamount2", discount.optDouble("discount2amount", 0));
                        cv.put("disamount3", discount.optDouble("discount3amount", 0));
                        cv.put("disamount4", discount.optDouble("discount4amount", 0));
                        cv.put("disamount5", discount.optDouble("discount5amount", 0));
                        cv.put("disamount6", discount.optDouble("discount6amount", 0));
                        cv.put("disamount7", discount.optDouble("discount7amount", 0));
                        cv.put("disamount8", discount.optDouble("discount8amount", 0));
                        cv.put("disamount9", discount.optDouble("discount9amount", 0));
                        cv.put("disamount10", discount.optDouble("discount10amount", 0));

                        cv.put("dispercent", discount.optDouble("discountpercent", 0));
                        cv.put("dispercent1", discount.optDouble("discount1percent", 0));
                        cv.put("dispercent2", discount.optDouble("discount2percent", 0));
                        cv.put("dispercent3", discount.optDouble("discount3percent", 0));
                        cv.put("dispercent4", discount.optDouble("discount4percent", 0));
                        cv.put("dispercent5", discount.optDouble("discount5percent", 0));
                        cv.put("dispercent6", discount.optDouble("discount6percent", 0));
                        cv.put("dispercent7", discount.optDouble("discount7percent", 0));
                        cv.put("dispercent8", discount.optDouble("discount8percent", 0));
                        cv.put("dispercent9", discount.optDouble("discount9percent", 0));
                        cv.put("dispercent10", discount.optDouble("discount10percent", 0));

                        DatabaseHelper.upsertWithOnConflit("discount_code", null, cv, SQLiteDatabase.CONFLICT_REPLACE, "code=? and unit_type=? and locationid=?",
                                new String[]{String.valueOf(code), String.valueOf(unit_type), String.valueOf(locationid)});
                    }
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }




//    public void UpSertingData(String table, JSONObject jobj) {
//        try {
//            switch (table) {
//                case "Posuser":
//                    JSONArray user = null;
//
//                    user = jobj.getJSONArray("posuser");
//                    for (int usercount = 0; usercount < user.length(); usercount++) {
//                        JSONObject postobj = user.getJSONObject(usercount);
//                        int userid = postobj.getInt("userid");
//                        String name = postobj.optString("name", "null");
//                        String shortdes = postobj.optString("shortdesc", "null");
//                        int branchid = postobj.optInt("branchid");
//                        String password = postobj.optString("password", "null");
//                        int canchangesaleprice = postobj.optBoolean("canchangesaleprice", false) == true ? 1 : 0;
//                        int canchangepurprice = postobj.optBoolean("canchangepurprice", false) == true ? 1 : 0;
//                        int canchangedate = postobj.optBoolean("canchangedate", false) == true ? 1 : 0;
//                        int defaultlocationid = postobj.optInt("defaultlocationid", 1);
//                        int candiscount = postobj.optBoolean("candiscount", false) == true ? 1 : 0;
//                        int isusetax = postobj.optBoolean("isusetax", false) == true ? 1 : 0;
//                        int ishidepurprice = postobj.optBoolean("ishidepurprice", false) == true ? 1 : 0;
//                        int ishidesaleprice = postobj.optBoolean("ishidesaleprice", false) == true ? 1 : 0;
//                        int ishidepurcostprice = postobj.optBoolean("ishidepurcostprice", false) == true ? 1 : 0;
//                        int isviewallsalepricelevel = postobj.optBoolean("isviewallsalepricelevel", false) == true ? 1 : 0;
//                        int isinactive = postobj.optBoolean("isinactive", false) == true ? 1 : 0;
//
//                        int defaultbranchid = postobj.optInt("defaultbranchid", 1);
//                        int defaultcashid = postobj.optInt("defaultcashid", 1);
//                        int isallowsysdatechange = postobj.optBoolean("isallowsysdatechange", false) == true ? 1 : 0;
//                        int isallowovercreditlimit = postobj.optBoolean("isallowovercreditlimit", false) == true ? 1 : 0;
//                        String isknockcode = postobj.optString("isknockcode", "null");
//                        int istabletuser = postobj.optBoolean("istabletuser", false) == true ? 1 : 0;
//                        int isallowpricelevel = postobj.optBoolean("isallowpricelevel", false) == true ? 1 : 0;
//                        int canselectcustomer = postobj.optBoolean("canselectcustomer", false) == true ? 1 : 0;
//                        int canselectlocation = postobj.optBoolean("canselectlocation", false) == true ? 1 : 0;
//                        int salepricelevelid = postobj.optInt("salepricelevelid");
//
//
//                        ContentValues values2 = new ContentValues();
//                        values2.put("userid", userid);
//                        values2.put("name", name);
//                        values2.put("shortdesc", shortdes);
//                        values2.put("branchid", branchid);
//                        values2.put("password", password);
//                        values2.put("canchangesaleprice", canchangesaleprice);
//
//
//                        values2.put("canchangepurprice", canchangepurprice);
//                        values2.put("canchangedate", canchangedate);
//                        values2.put("defaultlocationid", defaultlocationid);
//                        values2.put("candiscount", candiscount);
//
//
//                        values2.put("isusetax", isusetax);
//                        values2.put("ishidepurprice", ishidepurprice);
//                        values2.put("ishidesaleprice", ishidesaleprice);
//                        values2.put("ishidepurcostprice", ishidepurcostprice);
//                        values2.put("isviewallsalepricelevel", isviewallsalepricelevel);
//                        values2.put("isinactive", isinactive);
//                        values2.put("defaultbranchid", defaultbranchid);
//                        values2.put("defaultcashid", defaultcashid);
//                        values2.put("isallowsysdatechange", isallowsysdatechange);
//                        values2.put("isallowovercreditlimit", isallowovercreditlimit);
//                        values2.put("isknockcode", isknockcode);
//                        values2.put("istabletuser", istabletuser);
//
//                        values2.put("isallowpricelevel", isallowpricelevel);
//                        values2.put("canselectcustomer", canselectcustomer);
//                        values2.put("canselectlocation", canselectlocation);
//                        values2.put("salepricelevelid", salepricelevelid);
//
//
//                        DatabaseHelper.upsertWithOnConflit("Posuser", null, values2, SQLiteDatabase.CONFLICT_REPLACE, "userid=?", String.valueOf(userid));
//                    }
//                    break;
//                case "Customer":
//                    JSONArray cust = jobj.getJSONArray("customer");
//
//
//                    for (int custcount = 0; custcount < cust.length(); custcount++) {
//                        JSONObject custobj = cust.getJSONObject(custcount);
//                        long customerid = custobj.getLong("customerid");
//                        String shortdesc = custobj.optString("shortdesc", "null");
//                        String name = custobj.optString("name", "null");
//                        int townshipid = custobj.optInt("townshipid", 1);
//                        int pricelevelid = custobj.optInt("pricelevelid", 1);
//                        int iscredit = custobj.optBoolean("iscredit", false) == true ? 1 : 0;
//                        int balance = custobj.optInt("balance");
//                        int creditlimit = custobj.optInt("creditlimit");
//                        int dueindays = custobj.optInt("dueindays");
//                        int discountpercent = custobj.optInt("discountpercent");
//                        int isinactive = custobj.optBoolean("isinactive", false) == true ? 1 : 0;
//                        int discountamount = custobj.optInt("discountamount");
//                        int custgroupid = custobj.optInt("custgroupid");
//                        int nationalcardid = custobj.optInt("nationalcardid");
//                        int isdeleted = custobj.optInt("isdeleted");
//
//                        String custgroupname = custobj.optString("custgroupname");
//                        String custgroupcode = custobj.optString("custgroupcode");
//                        String townshipname = custobj.optString("townshipname");
//                        String townshipcode = custobj.optString("townshipcode");
//                        ContentValues contentValues = new ContentValues();
//                        contentValues.put("customerid", customerid);
//                        contentValues.put("name", name);
//                        contentValues.put("shortdesc", shortdesc);
//                        contentValues.put("townshipid", townshipid);
//                        contentValues.put("pricelevelid", pricelevelid);
//                        contentValues.put("iscredit", iscredit);
//                        contentValues.put("balance", balance);
//                        contentValues.put("creditlimit", creditlimit);
//                        contentValues.put("dueindays", dueindays);
//                        contentValues.put("discountpercent", discountpercent);
//                        contentValues.put("isinactive", isinactive);
//                        contentValues.put("discountamount", discountamount);
//                        contentValues.put("custgroupid", custgroupid);
//                        contentValues.put("nationalcardid", nationalcardid);
//                        contentValues.put("isdeleted", isdeleted);
//                        contentValues.put("custgroupname", custgroupname);
//
//                        contentValues.put("custgroupcode", custgroupcode);
//                        contentValues.put("townshipname", townshipname);
//                        contentValues.put("townshipcode", townshipcode);
//                        DatabaseHelper.upsertWithOnConflit("Customer", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE, "customerid=?", String.valueOf(customerid));
//
//
//                    }
//                    break;
//                case "Location":
//                    JSONArray loc = jobj.getJSONArray("location");
//                    for (int loccount = 0; loccount < loc.length(); loccount++) {
//
//
//                        JSONObject locobj = loc.getJSONObject(loccount);
//                        long locationid = locobj.getLong("locationid");
//                        int branchid = locobj.optInt("branchid", 1);
//                        String parentgroupid = locobj.optString("parentgroupid", "null");
//                        String oldparentgroupid = locobj.optString("oldparentgroupid");
//                        int sortid = locobj.optInt("sortid");
//                        String name = locobj.optString("name", "null");
//                        String shortdesc = locobj.optString("shortdesc");
//                        int iscalculate = locobj.optInt("iscalculate", 0);
//                        int isdiffsaleprice = locobj.optInt("isdiffsaleprice");
//                        int customerid = locobj.optInt("customerid", 1);
//                        int isdeleted = locobj.optInt("isdeleted");
//                        ContentValues contentValues = new ContentValues();
//                        contentValues.put("locationid", locationid);
//                        contentValues.put("branchid", branchid);
//                        contentValues.put("parentgroupid", parentgroupid);
//                        contentValues.put("oldparentgroupid", oldparentgroupid);
//                        contentValues.put("sortid", sortid);
//                        contentValues.put("name", name);
//                        contentValues.put("shortdesc", shortdesc);
//                        contentValues.put("iscalculate", iscalculate);
//                        contentValues.put("isdiffsaleprice", isdiffsaleprice);
//                        contentValues.put("customerid", customerid);
//                        contentValues.put("isdeleted", isdeleted);
//                        DatabaseHelper.upsertWithOnConflit("Location", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE, "locationid=?", String.valueOf(locationid));
//                        //DatabaseHelper.execute(sqlString);
//                    }
//                    break;
//                case "Usr_Code":
//                case "Unit":
//                    ContentValues usrcodevalue = new ContentValues();
//                    JSONArray usr_code = jobj.getJSONArray("usr_code");
//                    //JSONArray usr_code=data.getJSONObject(0).getJSONArray("usr_code");
//                    for (int codecount = 0; codecount < usr_code.length(); codecount++) {
//
//                        JSONObject codeobj = usr_code.getJSONObject(codecount);
//
//
//                        long code = codeobj.getLong("code");
//                        String usrcode = codeobj.getString("usrcode");
//                        String description = codeobj.optString("description", "null");
//                        double saleprice = codeobj.optDouble("saleprice", 0);
//                        int salecur = codeobj.optInt("salecur", 1);
//                        long classid = codeobj.getLong("class");
//                        String classname = codeobj.optString("classname", "null");
//                        long categoryid = codeobj.getLong("category");
//                        String categoryname = codeobj.optString("categoryname", "null");
//                        int unit_type = codeobj.optInt("unittype", 1);
//                        String unitname = codeobj.optString("unitname", "null");
//                        String unitshort = codeobj.optString("unitshort", "null");
//
//                        usrcodevalue.put("code", code);
//                        usrcodevalue.put("usr_code", usrcode);
//                        usrcodevalue.put("description", description);
//                        usrcodevalue.put("sale_price", saleprice);
//                        usrcodevalue.put("sale_curr", salecur);
//                        usrcodevalue.put("class", classid);
//                        usrcodevalue.put("classname", classname);
//                        usrcodevalue.put("categoryname", categoryname);
//                        usrcodevalue.put("category", categoryid);
//                        usrcodevalue.put("unit_type", unit_type);
//                        usrcodevalue.put("unitname", unitname);
//                        usrcodevalue.put("unitshort", unitshort);
//
//                        DatabaseHelper.upsertWithOnConflit("Usr_Code", null, usrcodevalue, SQLiteDatabase.CONFLICT_IGNORE, "code=?", String.valueOf(code));
//
//                    }
//                    break;
//                case "Payment_Type":
//                    JSONArray pay = jobj.getJSONArray("paymenttype");
//
//                    for (int paycount = 0; paycount < pay.length(); paycount++) {
//                        JSONObject paytobj = pay.getJSONObject(paycount);
//                        long pay_type = paytobj.getLong("paytypeid");
//                        String name = paytobj.optString("name", "null");
//                        String shortdes = paytobj.optString("shortdesc", "null");
//                        sqlString = "insert into Payment_Type(paytypeid,name,shortdesc)" +
//                                " values(" + pay_type + ",'" + name + "','" + shortdes + "')";
//
//                        DatabaseHelper.execute(sqlString);
//
//                    }
//                    break;
//                case "Dis_Type":
//                    JSONArray dis = jobj.getJSONArray("dis_type");
//
//                    for (int discount = 0; discount < dis.length(); discount++) {
//                        /*[{"itemdiscounttypeid": 0,"name": "Normal","shortdesc": null,"ispaid": true,"discount": null,"accountid": null,"puraccountid": null,"updateddatetime": "1990-01-01T00:00:00"},{"itemdiscounttypeid": 1,"name": "Discount 5%","shortdesc": "5%","ispaid": true,"discount": 5,"accountid": null,"puraccountid": 31,"updateddatetime": "1990-01-01T00:00:00"},{"itemdiscounttypeid": 2,"name": "Discount 10%","shortdesc": "10%","ispaid": true,"discount": 10,"accountid": null,"puraccountid": 31,"updateddatetime": "1990-01-01T00:00:00"},{"itemdiscounttypeid": 3,"name": "Free Gift","shortdesc": "foc","ispaid": false,"discount": 0,"accountid": 116,"puraccountid": 92,"updateddatetime": "1990-01-01T00:00:00"},{"itemdiscounttypeid": 5,"name": "Discount","shortdesc": "---","ispaid": true,"discount": 999,"accountid": null,"puraccountid": 31,"updateddatetime": "1990-01-01T00:00:00"},{"itemdiscounttypeid": 6,"name": "Office Use","shortdesc": "off","ispaid": false,"discount": null,"accountid": 86,"puraccountid": null,"updateddatetime": "1990-01-01T00:00:00"},{"itemdiscounttypeid": 7,"name": "Promotion","shortdesc": "Pro","ispaid": false,"discount": 0,"accountid": 112,"puraccountid": null,"updateddatetime": "1990-01-01T00:00:00"},{"itemdiscounttypeid": 4,"name": "Sample","shortdesc": "spl","ispaid": false,"discount": 0,"accountid": 83,"puraccountid": 92,"updateddatetime": "1990-01-01T00:00:00"}]*/
//                        JSONObject distobj = dis.getJSONObject(discount);
//                        long pay_type = distobj.getLong("itemdiscounttypeid");
//                        String name = distobj.optString("name", "null");
//                        String shortdes = distobj.optString("shortdesc", "null");
//                        int paid = distobj.optBoolean("ispaid", false) == true ? 1 : 0;
//                        int disamt = distobj.optInt("discount", 0);
//                        ContentValues contentValues = new ContentValues();
//                        contentValues.put("itemdiscounttypeid", pay_type);
//                        contentValues.put("name", name);
//                        contentValues.put("shortdesc", shortdes);
//                        contentValues.put("ispaid", paid);
//                        contentValues.put("discount", disamt);
//                        DatabaseHelper.upsertWithOnConflit("Dis_Type", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE, "itemdiscounttypeid=?", String.valueOf(pay_type));
//
//                    }
//                    break;
//                case "SystemSetting":
//                    JSONArray sys = jobj.getJSONArray("systemsetting");
//
//                    for (int syscount = 0; syscount < sys.length(); syscount++) {
//                        JSONObject systobj = sys.getJSONObject(syscount);
//
//                        String title = systobj.getString("title");
//                        int isuseunit = systobj.optBoolean("isuseunit", false) == true ? 1 : 0;
//                        int isusetownship = systobj.optBoolean("isusetownship", false) == true ? 1 : 0;
//                        int isuselocation = systobj.optBoolean("isuselocation", false) == true ? 1 : 0;
//                        int isusemultipaid = systobj.optBoolean("isusemultipaid", false) == true ? 1 : 0;
//                        int isusemultiinvoice = systobj.optBoolean("isusemultiinvoice", false) == true ? 1 : 0;
//                        int isusesalesmen = systobj.optBoolean("isusesalesmen", false) == true ? 1 : 0;
//                        int qtydecimalplaces = systobj.optBoolean("qtydecimalplaces", false) == true ? 1 : 0;
//                        int pricedecimalplaces = systobj.optBoolean("pricedecimalplaces", false) == true ? 1 : 0;
//                        int isusecustpricelevel = systobj.optBoolean("isusecustpricelevel", false) == true ? 1 : 0;
//                        int isuseuserpricelevel = systobj.optBoolean("isuseuserpricelevel", false) == true ? 1 : 0;
//                        int isusepic = systobj.optBoolean("isusepic", false) == true ? 1 : 0;
//                        int isusemultipricelvl = systobj.optBoolean("isusemultipricelvl", false) == true ? 1 : 0;
//                        int isusetax = systobj.optBoolean("isusetax", false) == true ? 1 : 0;
//                        int isexclusivetax = systobj.optBoolean("isexclusivetax", false) == true ? 1 : 0;
//                        double defaulttaxpercent = systobj.optDouble("defaulttaxpercent", 0);
//                        int taxCal = systobj.getInt("taxcal");
//                        int isusecustomergroup = systobj.optBoolean("isusecustomergroup", false) == true ? 1 : 0;
//                        int isusedelivery = systobj.optBoolean("isusedelivery", false) == true ? 1 : 0;
//
//
//                        ContentValues contentValues = new ContentValues();
//                        contentValues.put("title", title);
//                        contentValues.put("isuseunit", isuseunit);
//                        contentValues.put("isusetownship", isusetownship);
//                        contentValues.put("isuselocation", isuselocation);
//                        contentValues.put("isusemultipaid", isusemultipaid);
//                        contentValues.put("isusemultiinvoice", isusemultiinvoice);
//                        contentValues.put("isusesalesmen", isusesalesmen);
//                        contentValues.put("qtydecimalplaces", qtydecimalplaces);
//                        contentValues.put("pricedecimalplaces", pricedecimalplaces);
//                        contentValues.put("isusecustpricelevel", isusecustpricelevel);
//                        contentValues.put("isusetax", isusetax);
//                        contentValues.put("isuseuserpricelevel", isuseuserpricelevel);
//                        contentValues.put("isusepic", isusepic);
//                        contentValues.put("isusemultipricelvl", isusemultipricelvl);
//                        contentValues.put("defaulttaxpercent", defaulttaxpercent);
//                        contentValues.put("taxCal", taxCal);
//                        contentValues.put("isexclusivetax", isexclusivetax);
//                        contentValues.put("isusecustomergroup", isusecustomergroup);
//                        contentValues.put("isusedelivery", isusedelivery);
//                        DatabaseHelper.deleteWithOnConflit("SystemSetting", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE, "title=?", title);
//                    }
//                    break;
//                case "Salesmen":
//                    JSONArray salesmen = null;
//                    salesmen = jobj.getJSONArray("salesmen");
//                    for (int salesmencount = 0; salesmencount < salesmen.length(); salesmencount++) {
//                        JSONObject salesmenobj = salesmen.getJSONObject(salesmencount);
//                        int id = salesmenobj.getInt("salesmenid");
//                        String name = salesmenobj.optString("salesmenname", "null");
//                        String shortdes = salesmenobj.optString("shortdesc", "null");
////                        sqlString ="insert into Salesmen(salesmenid,salesmenname,shortdesc)values("+id+",'"+name+"','"+shortdes+"')";
////                        DatabaseHelper.execute(sqlString);
//                        ContentValues contentValues = new ContentValues();
//                        contentValues.put("salesmenid", id);
//                        contentValues.put("salesmenname", name);
//                        contentValues.put("shortdesc", shortdes);
//                        DatabaseHelper.upsertWithOnConflit("Salesmen", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE, "salesmenid=?", String.valueOf(id));
//                    }
//                    break;
//
//
//                case "Class":
//                    JSONArray cls = jobj.getJSONArray("class");
//
//                    for (int syscount = 0; syscount < cls.length(); syscount++) {
//                        JSONObject clsobj = cls.getJSONObject(syscount);
//                        int classid = clsobj.getInt("classid");
//                        String name = clsobj.optString("name");
//                        //String  updateddatetime=clsobj.optString("updateddatetime");
//                        int isdeleted = clsobj.optBoolean("isdeleted", false) == true ? 1 : 0;
//                        int sortid = clsobj.optInt("sortid", 1);
//                        ContentValues contentValues = new ContentValues();
//                        contentValues.put("classid", classid);
//                        contentValues.put("name", name);
//                        //contentValues.put("updateddatetime",updateddatetime);
//                        contentValues.put("isdeleted", isdeleted);
//                        contentValues.put("sortid", sortid);
//                        //ry{
//                        DatabaseHelper.upsertWithOnConflit("class", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE, "classid=?", String.valueOf(classid));
//                        //}catch (error)
//                    }
//                    break;
//                case "Category":
//                    JSONArray cate = jobj.getJSONArray("category");
//
//                    for (int syscount = 0; syscount < cate.length(); syscount++) {
//                        JSONObject cateobj = cate.getJSONObject(syscount);
//                        int categoryid = cateobj.getInt("categoryid");
//                        int classid = cateobj.optInt("classid", 0);
//                        String name = cateobj.optString("name");
//                        String parentgroupid = cateobj.optString("parentgroupid");
//                        String generatedid = cateobj.optString("generatedid");
//                        String shortdesc = cateobj.optString("shortdesc");
//                        int sortid = cateobj.optInt("sortid", 1);
//                        int israw = cateobj.optInt("israw");
//                        String imagepath = cateobj.optString("imagepath");
//                        //byte categoryimage=(byte)cateobj.opt("categoryimage");
//                        String colorrgb = cateobj.optString("colorrgb");
//                        int ismodifiercategory = cateobj.optInt("ismodifiercategory");
//                        int issetmenu = cateobj.optInt("issetmenu");
//                        // Date updateddatetime=cateobj.;
//                        int isdeleted = cateobj.optInt("isdeleted");
//
//                        ContentValues contentValues = new ContentValues();
//                        contentValues.put("categoryid", categoryid);
//                        contentValues.put("classid", classid);
//                        contentValues.put("name", name);
//                        contentValues.put("parentgroupid", parentgroupid);
//                        contentValues.put("generatedid", generatedid);
//                        contentValues.put("shortdesc", shortdesc);
//                        contentValues.put("israw", israw);
//                        contentValues.put("imagepath", imagepath);
//                        //contentValues.put("categoryimage", categoryimage);
//                        contentValues.put("colorrgb", colorrgb);
//                        contentValues.put("ismodifiercategory", ismodifiercategory);
//                        contentValues.put("issetmenu", issetmenu);
//                        // contentValues.put("updateddatetime",updateddatetime);
//                        contentValues.put("isdeleted", isdeleted);
//                        contentValues.put("sortid", sortid);
//                        DatabaseHelper.upsertWithOnConflit("category", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE, "categoryid=?", String.valueOf(categoryid));
//                    }
//
//                    break;
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//    }


    public StringBuffer PostToApi(String sqlstring, String... strings) {
        HttpURLConnection connection;
        StringBuffer response = new StringBuffer();
        try {
            URL url = new URL(strings[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.addRequestProperty("Content-Type", "text/plain");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            if (sqlstring != null) {
                //connection.setRequestProperty("Content-Length", Integer.toString(sqlstring.length()));
                connection.getOutputStream().write(sqlstring.getBytes("UTF8"));
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                String inputLine;


                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    public StringBuffer GetToApi(String sqlstring, String apiurl) {
        HttpURLConnection connection;
        StringBuffer response = new StringBuffer();
        try {
            URL url = new URL(apiurl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.addRequestProperty("Content-Type", "text/plain");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            if (sqlstring != null) {
                //connection.setRequestProperty("Content-Length", Integer.toString(sqlstring.length()));
                connection.getOutputStream().write(sqlstring.getBytes("UTF8"));
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                String inputLine;


                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    public short GettingPriceLevelId(String plevelname) {
        switch (plevelname) {
//                    case "SP":pricelevelid="0";break;
//                    case "SP1":pricelevelid="1";break;
//                    case "SP2":pricelevelid="2";break;
//                    case "SP3":pricelevelid="3";break;
//                    case "SP4":pricelevelid="4";break;
//                    case "SP5":pricelevelid="5";break;
//            case "SP":System.out.println("SP");return 0;
            case "SP1":
                return 1;
            case "SP2":
                return 2;
            case "SP3":
                return 3;
            case "SP4":
                return 4;
            case "SP5":
                return 5;
        }
        System.out.println("No SP");
        return 0;
    }

    public boolean CheckingCreditCustomer(int custid) {
        String sqlString = "select customerid,name,iscredit from Customer where customerid=" + custid;//,credit,due_in_days,credit_limit
        Cursor cursor = DatabaseHelper.rawQuery(sqlString);
        if (cursor != null && cursor.getCount() != 0) {
            boolean credit = false;
            if (cursor.moveToFirst()) {
                do {
                    long customerid = cursor.getLong(cursor.getColumnIndex("customerid"));
                    String customername = cursor.getString(cursor.getColumnIndex("name"));
//                    String customercode = cursor.getString(cursor.getColumnIndex("customer_code"));
                    credit = cursor.getInt(cursor.getColumnIndex("iscredit")) == 1 ? true : false;
//                    int due_in_days = cursor.getInt(cursor.getColumnIndex("due_in_days"));
//                    double credit_limit=cursor.getDouble(cursor.getColumnIndex("credit_limit"));
//                    btn.get(2).setText(customerid+":"+customername);

//                    sale_entry.isCreditcustomer=credit;
                } while (cursor.moveToNext());

            }
            return credit;
        }
        cursor.close();
        return false;
    }

//    private void ChangeHeader(String name, Button btn, Button btnpay, Context context) {
//
//        try {
//
//            String sqlString;
//            String filter;
//            ArrayList<customergroup> cg = new ArrayList<>();
//            ArrayList<Township>townships = new ArrayList<>();
//            ArrayList<customer>customers = new ArrayList<>();
//            ArrayList<Location> locations = new ArrayList<>();
//            ArrayList<pay_type> pay_types = new ArrayList<>();
//            ArrayList<Salesmen>salesmen=new ArrayList<>();
//            Cursor cursor = null;
//            AlertDialog.Builder bd = new AlertDialog.Builder(context);
//            View view = context.getLayoutInflater().inflate(R.layout.changeheadervalue, null);
//            bd.setCancelable(false);
//            bd.setView(view);
//            RecyclerView rv = view.findViewById(R.id.rcvChange);
//            ImageButton imgClose=view.findViewById(R.id.imgNochange);
//            imgClose.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    btnSalesmen.setText("Choose");
//                    da.dismiss();
//                }
//            });
//            ImageButton imgChangSave=view.findViewById(R.id.imgChangeSave);
//            ImageButton imgClear=view.findViewById(R.id.imgClear);
//            if(name=="Salesmen")
//            {
//                imgChangSave.setVisibility(View.VISIBLE);
//                imgClear.setVisibility(View.VISIBLE);
//            }
//            else
//            {
//                imgChangSave.setVisibility(View.GONE);
//                imgClear.setVisibility(View.GONE);
//            }
//            imgChangSave.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(SaleVouSalesmen.size()>0) {
//                        String salesmen="";
//                        if(SaleVouSalesmen.size()>4){
//
//                            for(int i=0;i<4;i++)
//                            {
//                                if(i!=3) {
//                                    salesmen += SaleVouSalesmen.get(i).getSalesmen_Short()+",";
//                                }
//                                else
//                                {
//                                    salesmen += SaleVouSalesmen.get(i).getSalesmen_Short()+",...";
//                                }
//                            }
//
//                        }else {
//
//                            for(int i=0;i<SaleVouSalesmen.size();i++){
//                                if(i!=SaleVouSalesmen.size()-1){
//                                    salesmen+=SaleVouSalesmen.get(i).getSalesmen_Short()+",";
//                                }else {
//                                    salesmen+=SaleVouSalesmen.get(i).getSalesmen_Short();
//                                }
//                            }
//                        }
//
//                        btnSalesmen.setText(salesmen);
//
//                    }else{
//                        btnSalesmen.setText("Choose");
//                    }
//                    da.dismiss();
//                }
//            });
//
//            imgClear.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    SaleVouSalesmen.clear();
//                    if(salesmen.size()>0)
//                    {
//                        salesmen.clear();
//                    }
//                    Cursor salemenCursor=DatabaseHelper.rawQuery("select salesmenid,salesmenname,shortdesc from Salesmen");
//                    if (salemenCursor != null && salemenCursor.getCount() != 0) {
//                        if (salemenCursor.moveToFirst()) {
//                            do {
//                                long salesmenid = salemenCursor.getLong(salemenCursor.getColumnIndex("salesmenid"));
//                                String salesmenname = salemenCursor.getString(salemenCursor.getColumnIndex("salesmenname"));
//                                String shortname = salemenCursor.getString(salemenCursor.getColumnIndex("shortdesc"));
//                                salesmen.add(new Salesmen(salesmenid,salesmenname,shortname));
//                            } while (salemenCursor.moveToNext());
//
//                        }
//
//                    }
//
//                    salemenCursor.close();
//
//
//                    SalesmenAdpater sad = new SalesmenAdpater(saleorder_entry.this,salesmen);
//                    rv.setAdapter(sad);
//                    LinearLayoutManager sgridLayoutManager = new LinearLayoutManager(saleorder_entry.this,LinearLayoutManager.VERTICAL,false);
//                    rv.setLayoutManager(sgridLayoutManager);
//                    salemenCursor = null;
//                    da.show();
//                }
//            });
//
//            openEditText=false;
//            EditText etdSearch=view.findViewById(R.id.etdSearch);
//            ImageButton imgSearch=view.findViewById(R.id.imgSearch);
//            imgSearch.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    try {
//
//                        AlertDialog.Builder searchBuilder=new AlertDialog.Builder(saleorder_entry.this);
//                        View view=getLayoutInflater().inflate(R.layout.searchbox,null);
//                        searchBuilder.setView(view);
//                        EditText etdSearch=view.findViewById(R.id.etdSearch);
//                        ImageButton btnSearch=view.findViewById(R.id.imgOK);
//                        btnSearch.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                if(!etdSearch.getText().toString().isEmpty())
//                                {
//                                    switch (name)
//                                    {
//                                        case "Customer Group":
//                                            ArrayList<customergroup> filteredList = new ArrayList<>();
//
//                                            for (customergroup item : cg) {
//                                                if (item.getName().toLowerCase().contains(etdSearch.getText().toString().toLowerCase())) {
//                                                    filteredList.add(item);
//                                                }
//                                            }
//                                            CustGroupAdapter ad = new CustGroupAdapter(saleorder_entry.this, filteredList, btn, da);
//                                            rv.setAdapter(ad);
//                                            GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 4);
//                                            rv.setLayoutManager(gridLayoutManager);
//                                            break;
//                                        case "Customer":
//
//                                            ArrayList<customer> filteredcustomer = new ArrayList<>();
//
//                                            for (customer item : customers) {
//                                                if (item.getName().toLowerCase().contains(etdSearch.getText().toString().toLowerCase())) {
//                                                    filteredcustomer.add(item);
//                                                }
//                                            }
//                                            CustomerAdapter ca = new CustomerAdapter(saleorder_entry.this, filteredcustomer, btn,btnpay, da);
//                                            rv.setAdapter(ca);
//                                            GridLayoutManager gridLayoutManagerCust = new GridLayoutManager(getApplicationContext(), 4);
//                                            rv.setLayoutManager(gridLayoutManagerCust);
//                                            break;
//                                        case "Township":
//                                            ArrayList<Township> filteredtownship = new ArrayList<>();
//
//                                            for (Township item : townships) {
//                                                if (item.getName().toLowerCase().contains(etdSearch.getText().toString().toLowerCase())) {
//                                                    filteredtownship.add(item);
//                                                }
//                                            }
//                                            TownshipAdapter ta = new TownshipAdapter(saleorder_entry.this, filteredtownship, btn, da);
//                                            rv.setAdapter(ta);
//                                            GridLayoutManager gridLayoutManagertown = new GridLayoutManager(getApplicationContext(), 4);
//                                            rv.setLayoutManager(gridLayoutManagertown);
//                                            break;
//                                        case "Location":
//                                            ArrayList<Location> filteredlocation = new ArrayList<>();
//
//                                            for (Location item : locations) {
//                                                if (item.getName().toLowerCase().contains(etdSearch.getText().toString().toLowerCase())) {
//                                                    filteredlocation.add(item);
//                                                }
//                                            }
//                                            LocationAdapter la = new LocationAdapter(saleorder_entry.this, filteredlocation, btn, da);
//                                            rv.setAdapter(la);
//                                            GridLayoutManager gridLayoutManagerloc = new GridLayoutManager(getApplicationContext(), 4);
//                                            rv.setLayoutManager(gridLayoutManagerloc);
//                                            break;
//                                        case "Payment Type":
//                                            ArrayList<pay_type> filteredpaytype = new ArrayList<>();
//
//                                            for (pay_type item : pay_types) {
//                                                if (item.getName().toLowerCase().contains(etdSearch.getText().toString().toLowerCase())) {
//                                                    filteredpaytype.add(item);
//                                                }
//                                            }
//                                            PaymentTypeAdapter pad = new PaymentTypeAdapter(saleorder_entry.this, filteredpaytype, btn, da);
//                                            rv.setAdapter(pad);
//                                            GridLayoutManager gridLayoutManagerPaymentType = new GridLayoutManager(getApplicationContext(), 4);
//                                            rv.setLayoutManager(gridLayoutManagerPaymentType);
//                                            da.show();
//                                            break;
//
//                                        case "Salesmen":
//
//                                            ArrayList<Salesmen> filteredsalesmen = new ArrayList<>();
//
//                                            for (Salesmen item : salesmen) {
//                                                if (item.getSalesmen_Name().toLowerCase().contains(etdSearch.getText().toString().toLowerCase())) {
//                                                    filteredsalesmen.add(item);
//                                                }
//                                            }
//                                            sm = new SalesmenAdpater(saleorder_entry.this, filteredsalesmen);
//                                            rv.setAdapter(sm);
//                                            LinearLayoutManager linearLayoutManagerSalesmen = new LinearLayoutManager(saleorder_entry.this,LinearLayoutManager.VERTICAL,false);
//                                            rv.setLayoutManager(linearLayoutManagerSalesmen);
//                                            break;
//                                    }
//                                    msg.dismiss();
//                                }
//                            }
//                        });
//
//                        msg=searchBuilder.create();
//                        msg.show();
//                    }
//                    catch (Exception ee)
//                    {
//
//                    }
//                }
//            });
//
//            da = bd.create();
//            switch (name) {
//
//                case "Salesmen":
//                    if(salesmen.size()>0)
//                    {
//                        salesmen.clear();
//                    }
//                    Cursor salemenCursor=DatabaseHelper.rawQuery("select salesmenid,salesmenname,shortdesc from Salesmen");
//                    if (salemenCursor != null && salemenCursor.getCount() != 0) {
//                        if (salemenCursor.moveToFirst()) {
//                            do {
//                                long salesmenid = salemenCursor.getLong(salemenCursor.getColumnIndex("salesmenid"));
//                                String salesmenname = salemenCursor.getString(salemenCursor.getColumnIndex("salesmenname"));
//                                String shortname = salemenCursor.getString(salemenCursor.getColumnIndex("shortdesc"));
//                                salesmen.add(new Salesmen(salesmenid,salesmenname,shortname));
//                            } while (salemenCursor.moveToNext());
//
//                        }
//
//                    }
//
//                    salemenCursor.close();
//
//
//                    SalesmenAdpater sad = new SalesmenAdpater(saleorder_entry.this,salesmen);
//                    rv.setAdapter(sad);
//                    LinearLayoutManager sgridLayoutManager = new LinearLayoutManager(saleorder_entry.this,LinearLayoutManager.VERTICAL,false);
//                    rv.setLayoutManager(sgridLayoutManager);
//                    salemenCursor = null;
//
//
//                    da.show();
//
//
//
//                    break;
//
//                case "Customer Group":
//                    if (cg.size() > 0) {
//                        cg.clear();
//                    }
//                    cursor = DatabaseHelper.DistinctSelectQuery("Customer",new String[]{"custgroupid","custgroupname","custgroupcode"});
//                    if (cursor != null && cursor.getCount() != 0) {
//                        if (cursor.moveToFirst()) {
//                            do {
//                                long custgroupid = cursor.getLong(cursor.getColumnIndex("custgroupid"));
//                                String custgroupname = cursor.getString(cursor.getColumnIndex("custgroupname"));
//                                String shortname = cursor.getString(cursor.getColumnIndex("custgroupcode"));
//                                cg.add(new customergroup(custgroupid, custgroupname, shortname));
//                            } while (cursor.moveToNext());
//
//                        }
//
//                    }
//
//                    cursor.close();
//
//
//                    CustGroupAdapter ad = new CustGroupAdapter(saleorder_entry.this, cg, btn, da);
//                    rv.setAdapter(ad);
//                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 4);
//                    rv.setLayoutManager(gridLayoutManager);
//                    cursor = null;
//
//
//                    da.show();
//
//                    break;
//                case "Township":
//                    cursor = DatabaseHelper.DistinctSelectQuery("Customer",new String[]{"townshipid","townshipname","townshipcode"});
//                    if (cursor != null && cursor.getCount() != 0) {
//                        if (cursor.moveToFirst()) {
//                            do {
//                                long townshipid = cursor.getLong(cursor.getColumnIndex("townshipid"));
//                                String townshipname = cursor.getString(cursor.getColumnIndex("townshipname"));
//                                String shortname = cursor.getString(cursor.getColumnIndex("townshipcode"));
//
//                                townships.add(new Township(townshipid, townshipname, shortname));
//                            } while (cursor.moveToNext());
//
//                        }
//
//                    }
//                    else
//                    {
//                        da.dismiss();
//                    }
//                    cursor.close();
//
//                    TownshipAdapter tad = new TownshipAdapter(saleorder_entry.this, townships, btn, da);
//                    rv.setAdapter(tad);
//                    GridLayoutManager gridLayoutManagerTownship = new GridLayoutManager(getApplicationContext(), 4);
//                    rv.setLayoutManager(gridLayoutManagerTownship);
//                    da.show();
//
//                    break;
//                case "Customer":
//
//                    if(!use_customergroup)
//                    {
//                        selected_custgroupid=-1;
//
//                    }
//                    if(!use_township)
//                    {
//                        selected_townshipid=-1;
//
//                    }
//                    filter = " where  ((custgroupid<>-1 and custgroupid=" + selected_custgroupid + ") or " + selected_custgroupid + "=-1)" +
//                            " and ((townshipid<>-1 and townshipid=" + selected_townshipid + ") or " + selected_townshipid + "=-1)";
//
//                    sqlString = "select *  from Customer "+filter/* + filter +" order by customerid,name"*//*customerid,shortdesc,name,Townshipid,pricelevelid,iscredit,balance,creditlimit,dueindays,discountpercent,isinactive,discountamount,custgroupid,nationalcardid,isdeleted*/;
//                    cursor = DatabaseHelper.rawQuery(sqlString);
//                    System.out.println(cursor.getCount()+"count!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//                    if (cursor != null && cursor.getCount() != 0) {
//                        if (cursor.moveToFirst()) {
//                            do {
////                                long customerid = cursor.getLong(cursor.getColumnIndex("customerid"));
////                                String customername = cursor.getString(cursor.getColumnIndex("customer_name"));
////                                String customercode = cursor.getString(cursor.getColumnIndex("customer_code"));
////                                boolean credit = cursor.getInt(cursor.getColumnIndex("credit")) == 1 ? true : false;
////                                double custdis=cursor.getDouble(cursor.getColumnIndex("Custdiscount"));
////                                int due_in_days = cursor.getInt(cursor.getColumnIndex("due_in_days"));
////                                double credit_limit =cursor.getDouble(cursor.getColumnIndex("credit_limit"));
////                                customers.add(new customer(customerid, customername, customercode, credit,custdis,due_in_days,credit_limit));
////                                //customers.add(new customer(customerid, customername, customercode, credit,custdis));
//                                int customerid=cursor.getInt(cursor.getColumnIndex("customerid"));
//                                String shortdesc=cursor.getString(cursor.getColumnIndex("shortdesc"));
//                                String custname=cursor.getString(cursor.getColumnIndex("name"));
//                                int townshipid=cursor.getInt(cursor.getColumnIndex("townshipid"));
//                                int pricelevelid=cursor.getInt(cursor.getColumnIndex("pricelevelid"));
//                                boolean iscredit=cursor.getInt(cursor.getColumnIndex("iscredit"))==1?true:false;
//                                int balance=cursor.getInt(cursor.getColumnIndex("balance"));
//                                int creditlimit=cursor.getInt(cursor.getColumnIndex("creditlimit"));
//                                int dueindays=cursor.getInt(cursor.getColumnIndex("dueindays"));
//                                int discountpercent=cursor.getInt(cursor.getColumnIndex("discountpercent"));
//                                boolean isinactive=cursor.getInt(cursor.getColumnIndex("isinactive"))==1?true:false;
//                                int discountamount=cursor.getInt(cursor.getColumnIndex("discountamount"));
//                                int custgroupid=cursor.getInt(cursor.getColumnIndex("custgroupid"));
//                                int nationalcardid=cursor.getInt(cursor.getColumnIndex("nationalcardid"));
//                                boolean isdeleted=cursor.getInt(cursor.getColumnIndex("isdeleted"))==1?true:false;
//                                customers.add(new customer( customerid,  shortdesc,  custname,  townshipid,  pricelevelid,  iscredit,  balance,  creditlimit,  dueindays,  discountpercent,  isinactive,  discountamount,  custgroupid,  nationalcardid,  isdeleted) );
//                            } while (cursor.moveToNext());
//
//                        }
//
//                    }
//                    else
//                    {
//                        da.dismiss();
//                    }
//
//                    cursor.close();
//
//                    CustomerAdapter cad = new CustomerAdapter(saleorder_entry.this, customers, btn,btnpay, da);
//                    rv.setAdapter(cad);
//                    GridLayoutManager gridLayoutManagerCustomer = new GridLayoutManager(getApplicationContext(), 4);
//                    rv.setLayoutManager(gridLayoutManagerCustomer);
//                    da.show();
//
//                    break;
//                case "Location":
//
//                    sqlString = "select locationid,name,shortdesc,branchid from Location ";
//                    cursor = DatabaseHelper.rawQuery(sqlString);
//                    System.out.println(cursor.getCount()+"count!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//                    if (cursor != null && cursor.getCount() != 0) {
//                        if (cursor.moveToFirst()) {
//                            do {
//                                long locationid = cursor.getLong(cursor.getColumnIndex("locationid"));
//                                String locationname = cursor.getString(cursor.getColumnIndex("Name"));
//                                String shortname = cursor.getString(cursor.getColumnIndex("shortdesc"));
//                                long branchid = cursor.getLong(cursor.getColumnIndex("branchID"));
//                                locations.add(new Location(locationid, locationname, shortname, branchid));
//                            } while (cursor.moveToNext());
//
//                        }
//
//                    }
//                    else
//                    {
//                        da.dismiss();
//                    }
//                    cursor.close();
//
//                    LocationAdapter lad = new LocationAdapter(saleorder_entry.this, locations, btn, da);
//                    rv.setAdapter(lad);
//                    GridLayoutManager gridLayoutManagerLocation = new GridLayoutManager(getApplicationContext(), 4);
//                    rv.setLayoutManager(gridLayoutManagerLocation);
//                    da.show();
//
//                    break;
//                case "Payment Type":
//
//                    if (pay_types.size() > 0) {
//                        pay_types.clear();
//                    }
//                    if(isCreditcustomer) {
//                        sqlString = "select * from Payment_Type";
//                    }
//                    else
//                    {
//                        sqlString = "select * from Payment_Type where paytypeid=1";
//                    }
//                    cursor = DatabaseHelper.rawQuery(sqlString);
//                    if (cursor != null && cursor.getCount() != 0) {
//                        if (cursor.moveToFirst()) {
//                            do {
//                                int pay_type = cursor.getInt(cursor.getColumnIndex("paytypeid"));
//                                String pay_typename = cursor.getString(cursor.getColumnIndex("name"));
//                                String shortname = cursor.getString(cursor.getColumnIndex("shortdesc"));
//                                pay_types.add(new pay_type(pay_type, pay_typename, shortname));
//                            } while (cursor.moveToNext());
//
//                        }
//
//                    }
//                    else
//                    {
//                        da.dismiss();
//                    }
//                    cursor.close();
//
//                    PaymentTypeAdapter pad = new PaymentTypeAdapter(saleorder_entry.this, pay_types, btn, da);
//                    rv.setAdapter(pad);
//                    GridLayoutManager gridLayoutManagerPaymentType = new GridLayoutManager(getApplicationContext(), 4);
//                    rv.setLayoutManager(gridLayoutManagerPaymentType);
//                    da.show();
//
//                    break;
//            }
//        }
//        catch (Exception e)
//        {
//            da.dismiss();
//        }
//    }


}