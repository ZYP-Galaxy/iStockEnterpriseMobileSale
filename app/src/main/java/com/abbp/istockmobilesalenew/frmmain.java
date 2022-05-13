package com.abbp.istockmobilesalenew;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.abbp.istockmobilesalenew.tvsale.sale_entry_tv;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class frmmain extends AppCompatActivity implements View.OnClickListener {
    private CardView cardsale, cardsalelist, cardstock, cardlogout, cardsaleorder, cardcustoutstand, cardsaleorderlist;
    private RequestQueue requestQueue;
    private TextView txtUsername, cpyname;
    public static int qty_places = 2;
    public static int price_places = 2;
    public static String withoutclass = "false";
    public static int isexclusivetax = 0;
    public static int use_pic = 0;
    public static int defunit = 1;
    public static int afterdiscount = 0;
    public static int isusespecialprice = 0; //added by EKK on 13-11-2020
    public static int isallowallusersviewforSE = 0; //added by EKK on 23-11-2020
    public static int isallowallusersviewforSO = 0; //added by EKK on 23-11-2020

    SharedPreferences sh_ip;
    SharedPreferences sh_port;
    Intent intent;
    public static int CCount = 0;
    private JSONObject jobj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frmmain);
        // globalsetting.username="TZW";
        System.out.println(globalsetting.username);
        SetUI();
        sh_ip = getSharedPreferences("ip", MODE_PRIVATE);
        sh_port = getSharedPreferences("port", MODE_PRIVATE);
        FillDataWithSignalr();
    }

    private void SetUI() {

        cardsale = (CardView) findViewById(R.id.cardsale);
        cardsalelist = (CardView) findViewById(R.id.cardsalelist);
        cardsaleorderlist = findViewById(R.id.cardsaleorderlist);
        cardstock = (CardView) findViewById(R.id.cardstock);
        cardlogout = (CardView) findViewById(R.id.cardlogout);
        cardsaleorder = (CardView) findViewById(R.id.cardsaleorder);
        cardcustoutstand = findViewById(R.id.cardcustoutstand);
        txtUsername = (TextView) findViewById(R.id.txtUsername);
        txtUsername.setText("   " + frmlogin.username);
        cpyname = findViewById(R.id.cpyname);
        if(!frmlogin.isTabletMode){
            cardsaleorder.setVisibility(View.GONE);
            cardsaleorderlist.setVisibility(View.GONE);;
            cardstock.setVisibility(View.GONE);;
            cardcustoutstand.setVisibility(View.GONE);;
//            cardsaleorder.setVisibility(View.GONE);;
        }
        cardsale.setOnClickListener(this);
        cardsalelist.setOnClickListener(this);
        cardsaleorderlist.setOnClickListener(this);
        cardstock.setOnClickListener(this);
        cardlogout.setOnClickListener(this);
        cardsaleorder.setOnClickListener(this);
        cardcustoutstand.setOnClickListener(this);
        getdecimal();
        getclassview();
        getpic();
        getdefunittype();
        getCustid();
        getexclusivetax();
        settingcpyname();
        getAfterDiscount(); // added by EKK on 05-11-2020
        isUseSpecialPrice(); //added by EKK on 13-11-2020
        isAllowUserAllView();
        // Toast .makeText(frmmain.this,"User View " + isallowallusersviewforSE + "\n Sale Order "+ isallowallusersviewforSO, Toast.LENGTH_LONG).show();
    }

    private void FillDataWithSignalr() {
//        String ippp = sh_ip.getString("ip", "empty");
//        String porttt = sh_port.getString("port", "empty");
//        String server = "http://"+ippp+":"+porttt+"/signalr";//159.138.231.20
//
//        /* Your logic here */h
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String currentDateandTime = sdf.format(new Date());
//        String ipp = sh_ip.getString("ip", "empty");
//        String portt = sh_port.getString("port", "empty");
//        String urll = "http://" + ipp + ":" + portt + "/api/mobile/RegisterUsingIMEI?imei="+GettingIMEINumber.IMEINO+"&lastupdatedatetime="+currentDateandTime+"&lastaccesseduserid="+frmlogin.LoginUserid+"&clientname="+frmlogin.Device_Name;
//        RequestQueue requestt = Volley.newRequestQueue(getApplicationContext());
//        final Response.Listener<String> listenerr=new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                JSONArray jarr = null;
//                try {
//                    jarr = new JSONArray(response);
//                    jobj = jarr.getJSONObject(0);
//                    System.out.println(jobj+"this is json");
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        };
//        final Response.ErrorListener errorr=new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
//
//            }
//        };
//
//        StringRequest reqq = new StringRequest(Request.Method.GET, urll, listenerr, errorr);
//        requestt.add(reqq);

        String ip = sh_ip.getString("ip", "empty");
        String port = sh_port.getString("port", "empty");
        String url = "http://" + ip + ":" + port + "/api/mobile/GetData?download=true&_macaddress=" + GettingIMEINumber.IMEINO;
        Log.i("frmmain", url);
        RequestQueue request = Volley.newRequestQueue(getApplicationContext());
        final Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            jobj = jsonArray.getJSONObject(0);

                            SelectInsertLibrary selectInsertLibrary = new SelectInsertLibrary();
                            //instead of Signalr to check what data are changes
                            String tablename = "";
                            if (jobj.getJSONArray("posuser").length() > 0) {
                                tablename = "Posuser";
                                selectInsertLibrary.UpSertingData(tablename, jobj);
                            }

                            try {
                                if (jobj.getJSONArray("customer").length() > 0) {
                                    tablename = "Customer";
                                    selectInsertLibrary.UpSertingData(tablename, jobj);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            try {
                                if (jobj.getJSONArray("location").length() > 0) {
                                    tablename = "Location";
                                    selectInsertLibrary.UpSertingData(tablename, jobj);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            try {
                                if (jobj.getJSONArray("usr_code").length() > 0 || jobj.getJSONArray("unit").length() > 0) {
                                    tablename = "Usr_Code";
                                    selectInsertLibrary.UpSertingData(tablename, jobj);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            try {
                                if (jobj.getJSONArray("paymenttype").length() > 0) {
                                    tablename = "Payment_Type";
                                    selectInsertLibrary.UpSertingData(tablename, jobj);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            try {
                                if (jobj.getJSONArray("dis_Type").length() > 0) {
                                    tablename = "Dis_Type";
                                    selectInsertLibrary.UpSertingData(tablename, jobj);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            try {
                                if (jobj.getJSONArray("systemSetting").length() > 0) {
                                    tablename = "SystemSetting";
                                    selectInsertLibrary.UpSertingData(tablename, jobj);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            try {
                                if (jobj.getJSONArray("salesmen").length() > 0) {
                                    tablename = "Salesmen";
                                    selectInsertLibrary.UpSertingData(tablename, jobj);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            try {
                                if (jobj.getJSONArray("class").length() > 0) {
                                    tablename = "Class";
                                    selectInsertLibrary.UpSertingData(tablename, jobj);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            try {
                                if (jobj.getJSONArray("category").length() > 0) {
                                    tablename = "Category";
                                    selectInsertLibrary.UpSertingData(tablename, jobj);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String currentDateandTime = sdf.format(new Date());
                            String ipp = sh_ip.getString("ip", "empty");
                            String portt = sh_port.getString("port", "empty");
                            String urll = "http://" + ipp + ":" + portt + "/api/mobile/RegisterUsingIMEI?imei=" + GettingIMEINumber.IMEINO + "&lastupdatedatetime=" + currentDateandTime + "&lastaccesseduserid=" + frmlogin.LoginUserid + "&clientname=" + frmlogin.Device_Name;
                            Log.i("frmmain", urll);
                            RequestQueue requestt = Volley.newRequestQueue(getApplicationContext());
                            final Response.Listener<String> listenerr = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    System.out.println(response);
                                }
                            };
                            final Response.ErrorListener errorr = new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getApplicationContext(), "You are in Offline. Please check your connection!", Toast.LENGTH_SHORT).show();
                                }
                            };

                            StringRequest reqq = new StringRequest(Request.Method.GET, urll, listenerr, errorr);
                            requestt.add(reqq);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        };

        final Response.ErrorListener error = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(frmmain.this, "You are in Offline. Please check your connection!", Toast.LENGTH_SHORT).show();
            }
        };

        StringRequest req = new StringRequest(Request.Method.GET, url, listener, error);
        request.add(req);


    }

    private void settingcpyname() {
        String cpystr = "";
        Cursor cursor = DatabaseHelper.rawQuery("select  title from systemsetting");
        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    cpystr = cursor.getString(cursor.getColumnIndex("title"));
                } while (cursor.moveToNext());
            }
            //test for default uint type
            //defunit=3;
        }
        cpyname.setText(cpystr);
    }

    private void getCustid() {
        Cursor cursor = DatabaseHelper.rawQuery("select Max(customerid)as custc from Customer");
        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    CCount = cursor.getInt(cursor.getColumnIndex("custc"));
                } while (cursor.moveToNext());
            }
        }
    }


    private void getdefunittype() {
        Cursor cursor = DatabaseHelper.rawQuery("select  use_unit_type from menu_user where groupid=1 and subgroupid=1 and userid=" + frmlogin.LoginUserid);
        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    defunit = cursor.getInt(cursor.getColumnIndex("use_unit_type"));
                } while (cursor.moveToNext());
            }
            //test for default uint type
            //defunit=3;
        }
    }


    private void getpic() {

        Cursor cursor = DatabaseHelper.rawQuery("select isusepic from SystemSetting");
        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    use_pic = cursor.getInt(cursor.getColumnIndex("isusepic"));
                } while (cursor.moveToNext());
            }

        }
    }

    //added by EKK on 05-11-2020
    private void getAfterDiscount() {

        Cursor cursor = DatabaseHelper.rawQuery("select afterdiscount from SystemSetting");
        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    afterdiscount = cursor.getInt(cursor.getColumnIndex("afterdiscount"));
                } while (cursor.moveToNext());
            }

        }

    }

    //added by EKK on 05-11-2020
    private void isUseSpecialPrice() {

        Cursor cursor = DatabaseHelper.rawQuery("select isusespecialprice from SystemSetting");
        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    isusespecialprice = cursor.getInt(cursor.getColumnIndex("isusespecialprice"));
                } while (cursor.moveToNext());
            }

        }

    }

    //added by EKK on 05-11-2020
    private void isAllowUserAllView() {

        Cursor cursor = DatabaseHelper.rawQuery("select isallowallusersview from userroles u join posuser p on p.userroleid = u.userroleid where u.menusubgroupid = 1 and p.userid = " + frmlogin.LoginUserid);
        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    isallowallusersviewforSE = cursor.getInt(cursor.getColumnIndex("isallowallusersview"));
                } while (cursor.moveToNext());
            }

        }

        cursor = DatabaseHelper.rawQuery("select isallowallusersview from userroles u join posuser p on p.userroleid = u.userroleid where u.menusubgroupid = 2 and p.userid = " + frmlogin.LoginUserid);
        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    isallowallusersviewforSO = cursor.getInt(cursor.getColumnIndex("isallowallusersview"));
                } while (cursor.moveToNext());
            }

        }
        cursor.close();

    }

    private void getclassview() {
        Cursor cursor = DatabaseHelper.rawQuery("select Setting_Value from AppSetting where Setting_Name='Withoutclass'");
        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    withoutclass = cursor.getString(cursor.getColumnIndex("Setting_Value"));
                } while (cursor.moveToNext());
            }

        }
    }

    private void getexclusivetax() {
        Cursor cursor = DatabaseHelper.rawQuery("select isexclusivetax from systemsetting");
        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    isexclusivetax = cursor.getInt(cursor.getColumnIndex("isexclusivetax"));

                } while (cursor.moveToNext());
            }
        }
    }

    private void getdecimal() {
        Cursor cursor = DatabaseHelper.rawQuery("select qtydecimalplaces,pricedecimalplaces from SystemSetting");
        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    qty_places = cursor.getInt(cursor.getColumnIndex("qtydecimalplaces"));
                    price_places = cursor.getInt(cursor.getColumnIndex("pricedecimalplaces"));
                } while (cursor.moveToNext());
            }

        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.cardsale:
                if(frmlogin.isTabletMode) {
                    intent = new Intent(frmmain.this, sale_entry.class);
                }
                else{
                    intent = new Intent(frmmain.this, sale_entry_tv.class);
                }
                startActivity(intent);
                finish();
                break;
            case R.id.cardsaleorder:
                intent = new Intent(frmmain.this, saleorder_entry.class);
                startActivity(intent);
                finish();
                break;

            case R.id.cardsalelist:
                intent = new Intent(frmmain.this, frmsalelist.class);
                intent.putExtra("name", "Sale History");
                startActivity(intent);
                finish();
                break;
            case R.id.cardsaleorderlist:
                intent = new Intent(frmmain.this, frmsalelist.class);
                intent.putExtra("name", "Sale Order History");
                startActivity(intent);
                finish();
                break;

            case R.id.cardlogout:
                UnLockUser(frmlogin.LoginUserid);
                intent = new Intent(frmmain.this, frmlogin.class);
                startActivity(intent);
                finish();
                break;

            case R.id.cardcustoutstand:
                intent = new Intent(frmmain.this, frmCustOutstand.class);
                startActivity(intent);
                finish();
                break;

            case R.id.cardstock:
//                intent=new Intent (frmmain.this,stock_balance.class);
                intent = new Intent(frmmain.this, frmstockstatus.class);
                startActivity(intent);
                finish();
                break;
        }

    }

    private void UnLockUser(int Userid) {
        String ip = sh_ip.getString("ip", "Localhost");
        String port = sh_port.getString("port", "80");
        String Device = frmlogin.Device_Name.replace(" ", "%20");
        String Url = "http://" + ip + ":" + port + "/DataSyncAPI/api/mobile/LockUser?userid=" + frmlogin.LoginUserid + "&hostname=" + Device + "&locked=" + false;

        requestQueue = Volley.newRequestQueue(this);

        final Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                DatabaseHelper.execute("delete from Login_User where userid=" + frmlogin.LoginUserid);
            }


        };

        final Response.ErrorListener error = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(frmmain.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        };


        StringRequest req = new StringRequest(Request.Method.GET, Url, listener, error);
        requestQueue.add(req);
    }

    //[{"categoryid": 1,"classid": 1,"name": "defaultcategory","parentgroupid": "0001","generatedid": null,"shortdesc": "dc","sortid": 1,"israw": false,"imagepath": null,"categoryimage": null,"colorrgb": null,"ismodifiercategory": false,"issetmenu": false,"updateddatetime": "2018-08-01T14:54:08.46","isdeleted": false,"sr": "7c2a85a8-1719-11ea-993c-938a04f4f730"16 items},{"categoryid": 2,"classid": 2,"name": "Samsung Cover","parentgroupid": "0002","generatedid": null,"shortdesc": "1","sortid": null,"israw": false,"imagepath": null,"categoryimage": null,"colorrgb": null,"ismodifiercategory": false,"issetmenu": false,"updateddatetime": "2019-12-04T19:34:20.684932","isdeleted": false,"sr": "7c2a993a-1719-11ea-993d-f390348ee078"}2 items]

    @Override
    public void onBackPressed() {
        UnLockUser(frmlogin.LoginUserid);
        intent = new Intent(frmmain.this, frmlogin.class);
        startActivity(intent);
        finish();
    }
}

