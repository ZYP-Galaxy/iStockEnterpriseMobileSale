package com.abbp.istockmobilesalenew;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abbp.istockmobilesalenew.tvsale.sale_entry_tv;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class frmsalelist extends AppCompatActivity implements View.OnClickListener {
    public static String url;
    private JsonArrayRequest request;
    public static RequestQueue requestQueue;
    public static ArrayList<salelist> salelists = new ArrayList<>();
    public static SharedPreferences sh_ip, sh_port;
    RecyclerView rcv;
    public static ProgressDialog pb;
    TextView txtdate;
    public static Date fdate, tdate;
    List<Calendar> calendars = new ArrayList<>();
    public static DateFormat dateFormat, griddateformat;
    String filterString = "and date between ";
    ImageButton imgFilterClear, imgAdd, imgEdit, imgDelete, filtermenu;
    public static saleListAdp adp;
    public static TextView txtCount, txtUsername, txtTotal;
    public static double total;
    Button selectfilter;
    AlertDialog da = null;
    AlertDialog msg;
    int filterV = 0;
    public static Context listcontext;
    public static String getActionName = "";
    private TextView title;
    String def_locationName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frmsalelist);
        listcontext = frmsalelist.this;
        sh_ip = getSharedPreferences("ip", MODE_PRIVATE);
        sh_port = getSharedPreferences("port", MODE_PRIVATE);
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        griddateformat = new SimpleDateFormat("dd/MM/yyyy");
        fdate = new Date();
        tdate = new Date();
        txtCount = findViewById(R.id.txtCount);
        txtUsername = findViewById(R.id.txtUsername);
        txtUsername.setText(frmlogin.username);
        title = findViewById(R.id.txtsalehistory);
        txtCount.setText("0");
        txtTotal = (TextView) findViewById(R.id.txtTotal);
        txtTotal.setText("0");
        FilterCustomer.ccid = -1;
        FilterUser.uid = -1;
        FilterLocation.locid = -1;
        setUI();
        if (frmlogin.det_locationid != 0) {
            FilterLocation.locid = frmlogin.det_locationid;
        } else {
            FilterLocation.locid = -1;
        }
        //FilterLocation.locid = frmlogin.det_locationid;
        String sqlString = "select locationid,Name,shortdesc,branchid from Location  where locationid=" + frmlogin.det_locationid;
        Cursor cursor = DatabaseHelper.rawQuery(sqlString);
        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    def_locationName = cursor.getString(cursor.getColumnIndex("Name"));
                    selectfilter.setText(def_locationName);
                    //if(frmlogin.canselectlocation!=0){
                    filterV = 3;
                    //}


                } while (cursor.moveToNext());

            }
        }
        cursor.close();
        getActionName = getIntent().getExtras().getString("name");
        title.setText(getActionName);

        if (getActionName.equals("Sale History")) {
            if (frmmain.isallowallusersviewforSE == 0)
                FilterUser.uid = frmlogin.LoginUserid;
            else
                FilterUser.uid = -1;
        } else if (getActionName.equals("Sale Order History")) {
            if (frmmain.isallowallusersviewforSO == 0)
                FilterUser.uid = frmlogin.LoginUserid;
            else
                FilterUser.uid = -1;
        }

        BindingData();

        //Toast.makeText(getApplicationContext(),getActionName+" this is",Toast.LENGTH_LONG).show();


    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.add) {
            Intent intent;
            if (frmlogin.isTVMode) {
                intent = new Intent(frmsalelist.this, sale_entry_tv.class);
            } else {
                intent = new Intent(frmsalelist.this, sale_entry.class);
            }

            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setUI() {
        rcv = findViewById(R.id.rcvsaleList);
        pb = new ProgressDialog(frmsalelist.this, R.style.AlertDialogTheme);
        pb.setTitle("Downloading");
        pb.setMessage("Please Wait...");
        pb.setCancelable(true);
        pb.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pb.setIndeterminate(true);
        txtdate = findViewById(R.id.txtdate);
        txtdate.setOnClickListener(this);
        imgFilterClear = findViewById(R.id.imgFilterClear);
        filtermenu = (ImageButton) findViewById(R.id.filtermenu);
        selectfilter = (Button) findViewById(R.id.selectfilter);
        if (frmlogin.det_locationid != 0) {
            FilterLocation.locid = frmlogin.det_locationid;
        } else {
            FilterLocation.locid = -1;
        }
        imgFilterClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtdate.setText("Today");
                selectfilter.setText("Choose");
                FilterCustomer.ccid = -1;
                //FilterUser.uid=-1;
                //FilterLocation.locid=-1;
                if (frmlogin.det_locationid != 0) {
                    selectfilter.setText(def_locationName);//added by KLm
                    FilterLocation.locid = frmlogin.det_locationid;
                } else {
                    FilterLocation.locid = -1;
                }
                fdate = new Date();
                tdate = new Date();

                if (getActionName.equals("Sale History")) {
                    if (frmmain.isallowallusersviewforSE == 0)
                        FilterUser.uid = frmlogin.LoginUserid;
                    else
                        FilterUser.uid = -1;
                } else if (getActionName.equals("Sale Order History")) {
                    if (frmmain.isallowallusersviewforSO == 0)
                        FilterUser.uid = frmlogin.LoginUserid;
                    else
                        FilterUser.uid = -1;
                }

//                BindingData();

            }
        });


        findViewById(R.id.imgAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActionName.equals("Sale History")) {
                    Intent intent;
                    if (frmlogin.isTVMode) {
                        intent = new Intent(frmsalelist.this, sale_entry_tv.class);
                    } else {
                        intent = new Intent(frmsalelist.this, sale_entry.class);
                    }
                    startActivity(intent);
                    finish();
                } else if (getActionName.equals("Sale Order History")) {
                    Intent intent = new Intent(frmsalelist.this, saleorder_entry.class);
                    startActivity(intent);
                    finish();
                }

            }
        });
        findViewById(R.id.imgEdit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        findViewById(R.id.imgDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        adp = new saleListAdp(frmsalelist.this, salelists);
        rcv.setAdapter(adp);
        LinearLayoutManager lm = new LinearLayoutManager(frmsalelist.this, LinearLayoutManager.VERTICAL, false);
        rcv.setLayoutManager(lm);

        filtermenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showfiltermenu();
            }
        });
//Added by abbp sale list filter on 12/7/2019
        selectfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selectfilter.getText().toString().trim().equals("Choose Customer") || filterV == 1) {
                    filterV = 1;
                    selecter("Customer", selectfilter);
                } else if (selectfilter.getText().toString().trim().equals("Choose User") || filterV == 2) {
                    filterV = 2;
                    selecter("User", selectfilter);
                } else if (frmlogin.canselectlocation != 0 && (selectfilter.getText().toString().trim().equals("Choose Location") || filterV == 3)) {
                    filterV = 3;
                    selecter("Location", selectfilter);
                } else if (filterV == 0) {
                    filterV = 0;
                    AlertDialog.Builder bd = new AlertDialog.Builder(frmsalelist.this, R.style.AlertDialogTheme);
                    bd.setTitle("iStock");
                    bd.setMessage("Please select filter type! ");
                    bd.setCancelable(false);
                    bd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    bd.create().show();
                }

//                if (selectfilter.getText().toString().trim().equals("Choose Customer") || (filterV == 1 && !selectfilter.getText().toString().trim().equals("Choose User") && !selectfilter.getText().toString().trim().equals("Choose") && !selectfilter.getText().toString().trim().equals("Choose Location"))) {
//                    filterV = 1;
//                    selecter("Customer", selectfilter);
//                } else if (selectfilter.getText().toString().trim().equals("Choose User") || (filterV == 2 && !selectfilter.getText().toString().trim().equals("Choose Customer") && !selectfilter.getText().toString().trim().equals("Choose") && !selectfilter.getText().toString().trim().equals("Choose Location"))) {
//                    filterV = 2;
//                    selecter("User", selectfilter);
//                } else if (selectfilter.getText().toString().trim().equals("Choose Location") || (filterV == 3 && !selectfilter.getText().toString().trim().equals("Choose User") && !selectfilter.getText().toString().trim().equals("Choose") && !selectfilter.getText().toString().trim().equals("Choose Customer"))) {
//                    filterV = 3;
//                    selecter("Location", selectfilter);
//                } else if (selectfilter.getText().toString().trim().equals("Choose")) {
//                    AlertDialog.Builder bd = new AlertDialog.Builder(frmsalelist.this, R.style.AlertDialogTheme);
//                    bd.setTitle("iStock");
//                    bd.setMessage("Please select filter type! ");
//                    bd.setCancelable(false);
//                    bd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    });
//                    bd.create().show();
//                }
            }
        });
    }

    private void selecter(final String name, final Button btn) {
        try {
            String sqlString = "";
            final ArrayList<customer> customers = new ArrayList<>();
            final ArrayList<user> users = new ArrayList<>();
            final ArrayList<Location> locs = new ArrayList<>();
            Cursor cursor = null;
            AlertDialog.Builder bd = new AlertDialog.Builder(frmsalelist.this, R.style.AlertDialogTheme);
            View view = getLayoutInflater().inflate(R.layout.changeheadervalue, null);
            bd.setCancelable(false);
            bd.setView(view);
            final RecyclerView rv = view.findViewById(R.id.rcvChange);
            ImageButton imgClose = view.findViewById(R.id.imgNochange);
            imgClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    da.dismiss();
                }
            });
            EditText etdSearch = view.findViewById(R.id.etdSearch);
            ImageButton imgSearch = view.findViewById(R.id.imgSearch);
            imgSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {

                        AlertDialog.Builder searchBuilder = new AlertDialog.Builder(frmsalelist.this, R.style.AlertDialogTheme);
                        View view = getLayoutInflater().inflate(R.layout.searchbox, null);
                        searchBuilder.setView(view);
                        final EditText etdSearch = view.findViewById(R.id.etdSearch);
                        ImageButton btnSearch = view.findViewById(R.id.imgOK);
                        btnSearch.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!etdSearch.getText().toString().isEmpty()) {
                                    switch (name) {
                                        case "Customer":

                                            ArrayList<customer> filteredcustomer = new ArrayList<>();

                                            for (customer item : customers) {
                                                if (item.getName().toLowerCase().contains(etdSearch.getText().toString().toLowerCase())) {
                                                    filteredcustomer.add(item);
                                                }
                                            }
                                            FilterCustomer ca = new FilterCustomer(frmsalelist.this, filteredcustomer, btn, da, getActionName);
                                            rv.setAdapter(ca);
                                            GridLayoutManager gridLayoutManagerCust = new GridLayoutManager(getApplicationContext(), 4);
                                            rv.setLayoutManager(gridLayoutManagerCust);
                                            break;
                                        case "User":

                                            ArrayList<user> filtereduser = new ArrayList<>();

                                            for (user item : users) {
                                                if (item.getName().toLowerCase().contains(etdSearch.getText().toString().toLowerCase())) {
                                                    filtereduser.add(item);
                                                }
                                            }
                                            FilterUser us = new FilterUser(frmsalelist.this, filtereduser, btn, da);
                                            rv.setAdapter(us);
                                            GridLayoutManager gridLayoutManagerUser = new GridLayoutManager(getApplicationContext(), 4);
                                            rv.setLayoutManager(gridLayoutManagerUser);
                                            break;
                                        case "Location":
                                            ArrayList<Location> filteredloc = new ArrayList<>();

                                            for (Location item : locs) {
                                                if (item.getName().toLowerCase().contains(etdSearch.getText().toString().toLowerCase())) {
                                                    filteredloc.add(item);
                                                }
                                            }
                                            FilterLocation loc = new FilterLocation(frmsalelist.this, filteredloc, btn, da, getActionName);
                                            rv.setAdapter(loc);
                                            GridLayoutManager gridLayoutManagerLoc = new GridLayoutManager(getApplicationContext(), 4);
                                            rv.setLayoutManager(gridLayoutManagerLoc);
                                            break;


                                    }
                                    msg.dismiss();
                                }
                            }
                        });

                        msg = searchBuilder.create();
                        msg.show();
                    } catch (Exception ee) {

                    }
                }
            });

            da = bd.create();
            switch (name) {
                case "Customer":
                   /* sqlString = "select customerid,customer_code,customer_name,credit,Custdiscount,due_in_days,credit_limit from Customer ";
                    cursor = DatabaseHelper.rawQuery(sqlString);
                    if (cursor != null && cursor.getCount() != 0) {
                        if (cursor.moveToFirst()) {
                            do {
                                long customerid = cursor.getLong(cursor.getColumnIndex("customerid"));
                                String customername = cursor.getString(cursor.getColumnIndex("customer_name"));
                                String customercode = cursor.getString(cursor.getColumnIndex("customer_code"));
                                boolean credit = cursor.getInt(cursor.getColumnIndex("credit")) == 1 ? true : false;
                                double custdis=cursor.getInt(cursor.getColumnIndex("Custdiscount"));
                                int due_in_days=cursor.getInt(cursor.getColumnIndex("due_in_days"));
                                double credit_limit=cursor.getDouble(cursor.getColumnIndex("credit_limit"));
                                customers.add(new customer(customerid, customername, customercode, credit,custdis,due_in_days,credit_limit));
                            } while (cursor.moveToNext());

                        }

                    }*/
                    sqlString = "select customerid,shortdesc,name,townshipid,pricelevelid,iscredit,balance,creditlimit,dueindays,discountpercent,isinactive,discountamount,custgroupid,nationalcardid,isdeleted from Customer where isdeleted=0";
                    cursor = DatabaseHelper.rawQuery(sqlString);
                    if (cursor != null && cursor.getCount() != 0) {
                        if (cursor.moveToFirst()) {
                            do {
//                                long customerid = cursor.getLong(cursor.getColumnIndex("customerid"));
//                                String customername = cursor.getString(cursor.getColumnIndex("customer_name"));
//                                String customercode = cursor.getString(cursor.getColumnIndex("customer_code"));
//                                boolean credit = cursor.getInt(cursor.getColumnIndex("credit")) == 1 ? true : false;
//                                double custdis=cursor.getDouble(cursor.getColumnIndex("Custdiscount"));
//                                int due_in_days = cursor.getInt(cursor.getColumnIndex("due_in_days"));
//                                double credit_limit =cursor.getDouble(cursor.getColumnIndex("credit_limit"));
//                                customers.add(new customer(customerid, customername, customercode, credit,custdis,due_in_days,credit_limit));
//                                //customers.add(new customer(customerid, customername, customercode, credit,custdis));
                                int customerid = cursor.getInt(cursor.getColumnIndex("customerid"));
                                String shortdesc = cursor.getString(cursor.getColumnIndex("shortdesc"));
                                String custname = cursor.getString(cursor.getColumnIndex("name"));
                                int townshipid = cursor.getInt(cursor.getColumnIndex("townshipid"));
                                int pricelevelid = cursor.getInt(cursor.getColumnIndex("pricelevelid"));
                                boolean iscredit = cursor.getInt(cursor.getColumnIndex("iscredit")) == 1 ? true : false;
                                int balance = cursor.getInt(cursor.getColumnIndex("balance"));
                                int creditlimit = cursor.getInt(cursor.getColumnIndex("creditlimit"));
                                int dueindays = cursor.getInt(cursor.getColumnIndex("dueindays"));
                                int discountpercent = cursor.getInt(cursor.getColumnIndex("discountpercent"));
                                boolean isinactive = cursor.getInt(cursor.getColumnIndex("isinactive")) == 1 ? true : false;
                                int discountamount = cursor.getInt(cursor.getColumnIndex("discountamount"));
                                int custgroupid = cursor.getInt(cursor.getColumnIndex("custgroupid"));
                                int nationalcardid = cursor.getInt(cursor.getColumnIndex("nationalcardid"));
                                boolean isdeleted = cursor.getInt(cursor.getColumnIndex("isdeleted")) == 1 ? true : false;
                                if (!isinactive)
                                    customers.add(new customer(customerid, shortdesc, custname, townshipid, pricelevelid, iscredit, balance, creditlimit, dueindays, discountpercent, isinactive, discountamount, custgroupid, nationalcardid, isdeleted));
                            } while (cursor.moveToNext());

                        }

                    } else {
                        da.dismiss();
                    }
                    cursor.close();

                    FilterCustomer cad = new FilterCustomer(frmsalelist.this, customers, btn, da, getActionName);
                    rv.setAdapter(cad);
                    GridLayoutManager gridLayoutManagerCustomer = new GridLayoutManager(getApplicationContext(), 4);
                    rv.setLayoutManager(gridLayoutManagerCustomer);
                    da.show();

                    break;
                case "User":

                    if (getActionName.equals("Sale History")) {
                        if (frmmain.isallowallusersviewforSE == 0)
                            sqlString = "select userid,name from posuser where isinactive=0 and userid = " + frmlogin.LoginUserid;
                        else
                            sqlString = "select userid,name from posuser where isinactive=0";
                    } else if (getActionName.equals("Sale Order History")) {
                        if (frmmain.isallowallusersviewforSO == 0)
                            sqlString = "select userid,name from posuser where isinactive=0 and userid= " + frmlogin.LoginUserid;
                        else
                            sqlString = "select userid,name from posuser where isinactive=0";
                    }

                    cursor = DatabaseHelper.rawQuery(sqlString);
                    if (cursor != null && cursor.getCount() != 0) {
                        if (cursor.moveToFirst()) {
                            do {
                                int userid = cursor.getInt(cursor.getColumnIndex("userid"));
                                String usrname = cursor.getString(cursor.getColumnIndex("name"));
                                users.add(new user(userid, usrname));
                            } while (cursor.moveToNext());

                        }

                    } else {
                        da.dismiss();
                    }
                    cursor.close();
                    FilterUser us = new FilterUser(frmsalelist.this, users, btn, da);
                    rv.setAdapter(us);
                    GridLayoutManager gridLayoutManagerUser = new GridLayoutManager(frmsalelist.this, 4);
                    rv.setLayoutManager(gridLayoutManagerUser);
                    da.show();
                    break;

                case "Location":
                    SelectInsertLibrary.GetLocationBaseOnBrachUser(locs);
                    //sqlString = "select locationid,Name,shortdesc,branchID from Location where isdeleted=0";

                    //modified by EKK
//                    if (frmlogin.defaultbranchid == 0) {
//                        sqlString = "select locationid,name,shortdesc,branchid from Location where isdeleted=0";
//                    } else {
//                        sqlString = "select locationid,name,shortdesc,branchid from Location where isdeleted=0 and branchid = " + frmlogin.defaultbranchid;
//                    }
//
//                    cursor = DatabaseHelper.rawQuery(sqlString);
//                    if (cursor != null && cursor.getCount() != 0) {
//                        if (cursor.moveToFirst()) {
//                            do {
//                                long locationid = cursor.getLong(cursor.getColumnIndex("locationid"));
//                                String locationname = cursor.getString(cursor.getColumnIndex("Name"));
//                                String shortname = cursor.getString(cursor.getColumnIndex("shortdesc"));
//                                long branchid = cursor.getLong(cursor.getColumnIndex("branchID"));
//                                locs.add(new Location(locationid, locationname, shortname, branchid));
//                            } while (cursor.moveToNext());
//
//                        }
//
//                    } else {
//                        da.dismiss();
//                    }
//                    cursor.close();

                    FilterLocation lad = new FilterLocation(frmsalelist.this, locs, btn, da, getActionName);
                    rv.setAdapter(lad);
                    GridLayoutManager gridLayoutManagerLocation = new GridLayoutManager(getApplicationContext(), 4);
                    rv.setLayoutManager(gridLayoutManagerLocation);
                    da.show();
                    break;
            }
        } catch (Exception e) {
            da.dismiss();
        }
    }

    // Added by abbp salelist filter on 12/07/2019
    private void showfiltermenu() {

        PopupMenu popup = new PopupMenu(frmsalelist.this, filtermenu);
        //Inflating the Popup using xml file
        popup.getMenuInflater().inflate(R.menu.filterselectionmenu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.customermenu:
                        selectfilter.setText("Choose Customer");
                        filterV = 1;
                        return true;
                    case R.id.usermenu: {
                        if (getActionName.equals("Sale History")) {
                            if (frmmain.isallowallusersviewforSE == 0) {
                                selectfilter.setText(frmlogin.username);
                            } else
                                selectfilter.setText("Choose User");
                        } else if (getActionName.equals("Sale Order History")) {
                            if (frmmain.isallowallusersviewforSO == 0) {
                                selectfilter.setText(frmlogin.username);

                            } else
                                selectfilter.setText("Choose User");
                        }
                        filterV = 2;
                        return true;
                    }

                    case R.id.locmenu:
                        if (frmlogin.canselectlocation == 0) {
                            selectfilter.setText(def_locationName);
                        } else {
                            selectfilter.setText("Choose Location");
                        }

                        filterV = 3;
                        return true;
                    default:
                        return false;
                }
            }
        });
        popup.show();//showing popup menu
    }

    public static void BindingData() {

        GetData();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(frmsalelist.this, frmmain.class);
        startActivity(intent);
        finish();
    }

    public static void GetData() {
        try {

            pb.show();
            salelists.clear();
            adp.notifyDataSetChanged();
            txtCount.setText(String.valueOf(salelists.size()));
            txtTotal.setText("0.0");
            total = 0.0;
            String ip = sh_ip.getString("ip", "empty");
            String port = sh_port.getString("port", "empty");
            url = "http://" + ip + ":" + port + "/api/mobile/GetEnqData?userid=" + frmlogin.LoginUserid + "&uid=" + FilterUser.uid + "&fdate=" + dateFormat.format(fdate) + "&tdate=" + dateFormat.format(tdate) + "&ccid=" + FilterCustomer.ccid + "&locid=" + FilterLocation.locid + "&branchid=0" /*frmlogin.defaultbranchid*/ + "&name=" + getActionName;
            requestQueue = Volley.newRequestQueue(listcontext);
            final Response.Listener<String> listener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    salelists.clear();
                    adp.notifyDataSetChanged();
                    txtCount.setText(String.valueOf(salelists.size()));
                    txtTotal.setText("0.0");
                    total = 0.0;
                    try {
                        JSONArray jarr = new JSONArray(response);
                        for (int i = 0; i < jarr.length(); i++) {
                            JSONObject obj = jarr.getJSONObject(i);
                            int tranid = obj.getInt("tranid");
                            String docid = obj.getString("docid");
                            String currency = obj.getString("currency");
                            String pay_type = obj.getString("pay_type");
                            String dateStr = obj.getString("date");
                            double net_amount = obj.getDouble("net_amount");
                            total += net_amount;
                            String usershort = obj.getString("usershort");
                            String customer_name = obj.getString("customer_name");
                            salelists.add(new salelist(tranid, dateStr, docid, pay_type, currency, net_amount, usershort, customer_name));


                        }
                        adp.notifyDataSetChanged();
                        txtCount.setText(String.valueOf(salelists.size()));
                        if (frmlogin.ishidesaleprice != 0) {
                            txtTotal.setText("****");
                        } else {
                            txtTotal.setText(String.format("%,." + frmmain.price_places + "f", total));
                        }

                        pb.dismiss();
                    } catch (JSONException e) {

                        pb.dismiss();
                        salelists.clear();
                        adp.notifyDataSetChanged();
                        txtCount.setText(String.valueOf(salelists.size()));
                        txtTotal.setText("0.0");

                    }
                }


            };

            final Response.ErrorListener error = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    pb.dismiss();
                    salelists.clear();
                    adp.notifyDataSetChanged();
                    txtCount.setText(String.valueOf(salelists.size()));
                    txtTotal.setText("0.0");
                    //Toast.makeText(, "You are in Offline. Please check your connection!", Toast.LENGTH_LONG).show();

                }
            };
            StringRequest req = new StringRequest(Request.Method.GET, url, listener, error);
            requestQueue.add(req);


        } catch (Exception ee) {
            Toast.makeText(listcontext, ee.getMessage(), Toast.LENGTH_LONG).show();
            pb.dismiss();

        }
    }

    private static void GetBranchid() {
//        branchid
        Cursor cursor=DatabaseHelper.rawQuery("select branchid from branch where locationid="+FilterLocation.locid);
        if(cursor!=null && cursor.getCount()>0){
            if (cursor.moveToFirst()) {
                do{
                    branchid=cursor.getInt(cursor.getColumnIndex("branchid"));
                }while (cursor.moveToNext());
            }
        }
        cursor.close();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtdate:
                ShowDateFilter();
                break;
        }
    }

    private void ShowDateFilter() {
        Context context = frmsalelist.this;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.daterangefilterpopup, null);
        float density = frmsalelist.this.getResources().getDisplayMetrics().density;
        final PopupWindow pw = new PopupWindow(layout, (int) density * 440, WindowManager.LayoutParams.WRAP_CONTENT, true);
        pw.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        pw.setOutsideTouchable(false);
        pw.showAsDropDown(txtdate);

        ImageButton imgDone = layout.findViewById(R.id.imgDone);
        final com.applandeo.materialcalendarview.CalendarView calendarView = layout.findViewById(R.id.calendarView);
//        calendarView.
        imgDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendars = calendarView.getSelectedDates();
                if (calendars.size() > 0) {
                    fdate = calendars.get(0).getTime();
                    tdate = calendars.get(calendars.size() - 1).getTime();
                }
                pw.dismiss();
                txtdate.setText(new SimpleDateFormat("dd/MM/yyyy").format(fdate) + " - " + new SimpleDateFormat("dd/MM/yyyy").format(tdate));
                BindingData();

            }
        });
    }
}

