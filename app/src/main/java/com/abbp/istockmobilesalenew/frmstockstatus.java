package com.abbp.istockmobilesalenew;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
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

//import static com.abbp.istockmobilesalenew.frmsalelist.GetData;

public class frmstockstatus extends AppCompatActivity {
    TextView txtdate;
    public static Date fdate, tdate;
    List<Calendar> calendars = new ArrayList<>();
    ImageButton btnmenu;
    Button choosecategory, chooselocation;
    AlertDialog da = null;
    AlertDialog msg;
    public static ArrayList<class_item> class_items = new ArrayList<>();
    classAdapter classadapter;
    public static RecyclerView gridclassview;
    int status = 0;
    public static ArrayList<category> categories = new ArrayList<>();
    categoryAdapter categoryadapter;

    public static SharedPreferences sh_ip, sh_port;
    public static String url;
    private JsonArrayRequest request;
    public static RequestQueue requestQueue;
    public static DateFormat dateFormat, griddateformat;
    ImageButton btnrefresh;
    StockStatusAdapter stockStatusAdapter;
    RecyclerView rcv;
    EditText findcode;
    CheckBox chkrecndel, chksaleord, chkinactive;
    ImageButton imgSearchCode, btnclear;
    String searchcode = "";
    ArrayList<StockStatus> stockStatuses;
    ProgressDialog pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frmstockstatus);
        txtdate = findViewById(R.id.txtdate);
        txtdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDateFilter();

            }
        });
        btnmenu = findViewById(R.id.btnmenu);
        choosecategory = findViewById(R.id.choosecategory);
        chooselocation = findViewById(R.id.chooselocation);
        gridclassview = findViewById(R.id.cvv);
        sh_ip = getSharedPreferences("ip", MODE_PRIVATE);
        sh_port = getSharedPreferences("port", MODE_PRIVATE);
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        griddateformat = new SimpleDateFormat("dd/MM/yyyy");
        btnrefresh = findViewById(R.id.btnrefresh);
//        stockStatuses.clear();
        rcv = findViewById(R.id.rcvsaleList);
        findcode = findViewById(R.id.findcode);
        classAdapter.classid = null;
        categoryAdapter.categoryid = null;
        FilterLocation.locid = -1; //modified by EKk
        chkrecndel = findViewById(R.id.chkrecndel);
        chksaleord = findViewById(R.id.chksaleorder);
        chkinactive = findViewById(R.id.chkinactive);
        imgSearchCode = findViewById(R.id.imgSearchCode);
        btnclear = findViewById(R.id.btnclear);
        fdate = new Date();
        tdate = new Date();
        stockStatuses = new ArrayList<>();
        stockStatusAdapter = new StockStatusAdapter(frmstockstatus.this, stockStatuses);
        SetUI();
//        BindingData();
    }

    private void SetUI() {
//        int status=0;
        btnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(frmstockstatus.this, btnmenu);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.filtermenu, popup.getMenu());

                Menu pp = popup.getMenu();
                pp.findItem(R.id.code).setVisible(false);
                pp.findItem(R.id.description).setVisible(false);
                if (frmmain.withoutclass.equals("true")) {
                    pp.findItem(R.id.cclass).setVisible(false);
                } else {
                    pp.findItem(R.id.cclass).setVisible(true);
                }
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.code:
                                choosecategory.setText("Choose Code");

                                break;
                            case R.id.description:
                                choosecategory.setText("Choose Description");
//                                fitercode="Description";
//                                            etdSearch.setHint("By "+filteredCode);
                                break;
                            case R.id.category:
                                choosecategory.setText("Choose Category");
//                                fitercode="Category";
//                                            etdSearch.setHint("By "+filteredCode);
                                break;
                            case R.id.cclass:
                                choosecategory.setText("Choose Class");
//                                fitercode="Class";
//                                            etdSearch.setHint("By "+filteredCode);
                                break;

                        }
//                        etdSearch.setHint("By "+fitercode);
                        return true;
                    }
                });
                popup.show();//showing popup menu
            }
        });
        choosecategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (choosecategory.getText().toString().toLowerCase().equals("choose code") || status == 1) {
//                    selecter("usrcode",chooselocation);
//                    status=1;
                } else if (choosecategory.getText().toString().toLowerCase().equals("choose description") || (status == 2 && !choosecategory.getText().toString().toLowerCase().equals("choose category") && !choosecategory.getText().toString().toLowerCase().equals("choose class"))) {

                } else if (choosecategory.getText().toString().toLowerCase().equals("choose category") || (status == 3 && !choosecategory.getText().toString().toLowerCase().equals("choose class"))) {
                    selecter("Category", choosecategory);
                    status = 3;
                } else if (choosecategory.getText().toString().toLowerCase().equals("choose class") || status == 4) {

                    selecter("Class", choosecategory);
                    status = 4;
                }
            }
        });

        GetDefaultLocation();

        chooselocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (chooselocation.getText().toString().toLowerCase().equals("choose"))
//                Toast.makeText(getApplicationContext(),"this is location ",Toast.LENGTH_LONG).show();
                selecter("Location", chooselocation);

            }
        });
        btnrefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb = new ProgressDialog(frmstockstatus.this, R.style.AlertDialogTheme);
                pb.setTitle("Downloading");
                pb.setMessage("Please Wait...");
                pb.setCancelable(true);
                pb.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                pb.setIndeterminate(true);

                stockStatuses.clear();
//                    stockStatusAdapter = new StockStatusAdapter(frmstockstatus.this, stockStatuses);
                stockStatusAdapter.notifyDataSetChanged();
                BindingData();
//                pb.dismiss();
            }
        });
        imgSearchCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(),"U click me", Toast.LENGTH_LONG).show();
                AlertDialog.Builder searchBuilder = new AlertDialog.Builder(frmstockstatus.this, R.style.AlertDialogTheme);
                View view = getLayoutInflater().inflate(R.layout.searchbox, null);
                searchBuilder.setView(view);
                EditText etdSearch = view.findViewById(R.id.etdSearch);
                ImageButton btnSearch = view.findViewById(R.id.imgOK);
                ImageButton btnFilterCode = view.findViewById(R.id.imgFilterCode);
                etdSearch.setHint("By Code");
                btnFilterCode.setVisibility(View.GONE);
//                etdSearch.setLeft();
                btnSearch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!etdSearch.getText().toString().isEmpty()) {
//                            SearchItem(etdSearch.getText().toString());
                            searchcode = etdSearch.getText().toString().toLowerCase();

                            pb = new ProgressDialog(frmstockstatus.this, R.style.AlertDialogTheme);
                            pb.setTitle("Downloading");
                            pb.setMessage("Please Wait...");
                            pb.setCancelable(true);
                            pb.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                            pb.setIndeterminate(true);

                            BindingData();
                            msg.dismiss();
                        }/*else {
                            etdSearch.setText(searchcode);
                        }*/
                    }
                });

                msg = searchBuilder.create();
                msg.show();
            }
        });
        btnclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtdate.setText("Today");
                choosecategory.setText("Choose");
                classAdapter.classid = null;
                categoryAdapter.categoryid = null;
                FilterLocation.locid = -1; //modified by EKK
                chooselocation.setText("Choose Location");
                fdate = new Date();
                tdate = new Date();
                stockStatuses.clear();
                stockStatusAdapter.notifyDataSetChanged();
//                BindingData();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(frmstockstatus.this, frmmain.class);
        startActivity(intent);
        finish();
    }

    private void ShowDateFilter() {
        Context context = frmstockstatus.this;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.daterangefilterpopup, null);
        float density = frmstockstatus.this.getResources().getDisplayMetrics().density;
        final PopupWindow pw = new PopupWindow(layout, (int) density * 440, WindowManager.LayoutParams.WRAP_CONTENT, true);
        pw.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        pw.setOutsideTouchable(false);
        pw.showAsDropDown(txtdate);
        ImageButton imgDone = layout.findViewById(R.id.imgDone);
        final com.applandeo.materialcalendarview.CalendarView calendarView = layout.findViewById(R.id.calendarView);
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
//                BindingData();

            }
        });
    }

    private void BindingData() {

//        stockStatuses.clear();
        GetData();
    }

    private void GetData() {
        pb.show();
        boolean saleorder = false, recanddel = false, inactive = false;
        if (chksaleord.isChecked()) {
            saleorder = true;
        }
        if (chkrecndel.isChecked()) {
            recanddel = true;
        }
        if (chkinactive.isChecked()) {
            inactive = true;
        }
//        ArrayList<StockStatus> stockStatuses/*=new ArrayList<>()*/;
//        stockStatuses=new ArrayList<>();

        stockStatuses.clear();
        /*stockStatusAdapter=new StockStatusAdapter(frmstockstatus.this,stockStatuses);
        rcv.setAdapter(stockStatusAdapter);
        LinearLayoutManager lm=new LinearLayoutManager(frmstockstatus.this,LinearLayoutManager.VERTICAL,false);
        rcv.setLayoutManager(lm);
        stockStatusAdapter.notifyDataSetChanged();*/
        String ip = sh_ip.getString("ip", "empty");
        String port = sh_port.getString("port", "empty");
        //int loc=FilterLocation.locid;

        String loc = "-1";

        loc = GetFilteredLocationID();

        /*
        if (FilterLocation.locid == -1) {
            loc = "-1";
        } else {
            loc = FilterLocation.locid + "";
        }
      */

        url = "http://" + ip + ":" + port/*"80/DataSyncAPI"*/ + "/api/mobile/GetStockStatus?userid=" + frmlogin.LoginUserid +/*"&uid="+FilterUser.uid+*/"&fdate=" + dateFormat.format(fdate) + "&tdate=" + dateFormat.format(tdate) + "&classid=" + classAdapter.classid + "&category=" + categoryAdapter.categoryid + "&location=" + loc + "&saleorder=" + saleorder + "&recNdel=" + recanddel + "&inactive=" + inactive/*+"&ccid="+FilterCustomer.ccid+"&locid="+FilterLocation.locid*/;
        requestQueue = Volley.newRequestQueue(frmstockstatus.this);
        final Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    System.out.println("right");
//                    JSONArray jarr=new JSONArray(response);
                    JSONObject jsonObject = new JSONObject(response);
                    System.out.println(jsonObject.length());
                    String usrcode = "", description = "", saleamount = "", balanceqty = "";


                    JSONArray stockstatusjarr = jsonObject.getJSONArray("stockstatus");
                    JSONObject object = null;
                    for (int i = 0; i < stockstatusjarr.length(); i++) {

                        object = stockstatusjarr.getJSONObject(i);
                        usrcode = object.getString("usrcode");

                        description = object.optString("description",usrcode);
                        saleamount = object.getString("saleamount");
                        balanceqty = object.getString("totalbalance");
//                        balanceqty = "";
                        System.out.println(usrcode + " " + description + " " + saleamount + " " + balanceqty);
//                        JSONArray stockhistorylist = object.getJSONArray("stockhistorylist");
//                        for (int j = 0; j < stockhistorylist.length(); j++) {
//                            JSONObject stockhistorylistobj = stockhistorylist.getJSONObject(j);
////                            if(stockhistorylistobj.getString("locationname").equals(FilterLocation.shortdesc)){
//                            balanceqty = stockhistorylistobj.getString("balanceqty");
////                            }
//                        }
//                        Toast.makeText(getApplicationContext(),usrcode+" "+description+" "+saleamount+" "+balanceqty, Toast.LENGTH_LONG).show();
//                        if(usrcode.toLowerCase().contains(findcode.getText().toString().toLowerCase())) {
                        String description1 = description.length() == 0 || description.equals("null")? usrcode : description;
                        if (!searchcode.equals("")) {
                            if (usrcode.toLowerCase().contains(searchcode)) {
                                System.out.println(usrcode + " " + description + " " + saleamount + " " + balanceqty + " " + searchcode);

                                stockStatuses.add(new StockStatus(usrcode, description1, saleamount, balanceqty));
                                searchcode = "";
                                if (stockStatuses.size() == 0) {
                                    Toast.makeText(getApplicationContext(), "Anything does not match with " + searchcode, Toast.LENGTH_LONG).show();
                                }
                                break;
                            }
                        } else {
                            stockStatuses.add(new StockStatus(usrcode, description1, saleamount, balanceqty));
                        }
                    }

                    rcv.setAdapter(stockStatusAdapter);
                    LinearLayoutManager lm = new LinearLayoutManager(frmstockstatus.this, LinearLayoutManager.VERTICAL, false);
                    rcv.setLayoutManager(lm);
                    stockStatusAdapter.notifyDataSetChanged();

                    pb.dismiss();
                } catch (JSONException e) {
                    pb.dismiss();
                    System.out.println(e.toString());

                }
            }


        };

        final Response.ErrorListener error = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                System.out.println("this is error" + error.toString());
                stockStatuses.clear();
                pb.dismiss();
                Toast.makeText(frmstockstatus.this, "You are in Offline. Please check your connection!", Toast.LENGTH_LONG).show();


            }
        };
        StringRequest req = new StringRequest(Request.Method.GET, url, listener, error);
        requestQueue.add(req);


    }

    private void GetDefaultLocation() {
        if (frmlogin.det_locationid != 0) {
            FilterLocation.locid = frmlogin.det_locationid;
            String locationname = "Choose Location";

            String sqlString = "select locationid,name,shortdesc,branchid from Location where isdeleted=0 and locationid = " + frmlogin.det_locationid;
            Cursor cursor = DatabaseHelper.rawQuery(sqlString);
            if (cursor != null && cursor.getCount() != 0) {
                if (cursor.moveToFirst()) {
                    do {
                        locationname = cursor.getString(cursor.getColumnIndex("Name"));
                    } while (cursor.moveToNext());
                }
            }
            chooselocation.setText(locationname);
        } else {
            FilterLocation.locid = -1;
            chooselocation.setText("Choose Location");
        }
    }

    private String GetFilteredLocationID() {
        StringBuilder loc = new StringBuilder();
        String sqlString;

        if (FilterLocation.locid == -1) {
            if (frmlogin.defaultbranchid == 0) {
                loc = new StringBuilder("-1");
            } else {
                sqlString = "select locationid  from Location where isdeleted=0 and branchid = " + frmlogin.defaultbranchid;
                int count = 0;
                Cursor cursor = DatabaseHelper.rawQuery(sqlString);
                if (cursor != null && cursor.getCount() != 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            count += 1;
                            String locid = cursor.getString(cursor.getColumnIndex("locationid"));
                            loc.append(locid);
                            if (count < cursor.getCount())
                                loc.append(",");
                        } while (cursor.moveToNext());
                    }
                }
                cursor.close();
            }

        } else {
            loc = new StringBuilder(FilterLocation.locid + "");
        }
        return loc.toString();
    }

    private void selecter(final String name, final Button btn) {
        try {
            String sqlString;
            final ArrayList<customer> customers = new ArrayList<>();
            final ArrayList<user> users = new ArrayList<>();
            final ArrayList<Location> locs = new ArrayList<>();
            Cursor cursor = null;
            AlertDialog.Builder bd = new AlertDialog.Builder(frmstockstatus.this, R.style.AlertDialogTheme);
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

                        AlertDialog.Builder searchBuilder = new AlertDialog.Builder(frmstockstatus.this, R.style.AlertDialogTheme);
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
                                            FilterCustomer ca = new FilterCustomer(frmstockstatus.this, filteredcustomer, btn, da, "status");
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
                                            FilterUser us = new FilterUser(frmstockstatus.this, filtereduser, btn, da);
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
                                            FilterLocation loc = new FilterLocation(frmstockstatus.this, filteredloc, btn, da, "status");
                                            rv.setAdapter(loc);
                                            GridLayoutManager gridLayoutManagerLoc = new GridLayoutManager(getApplicationContext(), 4);
                                            rv.setLayoutManager(gridLayoutManagerLoc);
                                            break;
                                        case "Class":
                                            ArrayList<class_item> Filterclass = new ArrayList<>();
                                            for (class_item citem : class_items) {
                                                if (citem.getName().toLowerCase().contains(etdSearch.getText().toString().toLowerCase())) {
                                                    Filterclass.add(citem);
                                                }
                                            }
                                            classAdapter cAdatper = new classAdapter(frmstockstatus.this, Filterclass, choosecategory, da);
                                            rv.setAdapter(cAdatper);
                                            GridLayoutManager gridLayoutManagerclass = new GridLayoutManager(getApplicationContext(), 4);
                                            rv.setLayoutManager(gridLayoutManagerclass);
                                            break;

                                        case "Category":
                                            ArrayList<category> Filtercate = new ArrayList<>();
                                            for (category catitem : categories) {
                                                if (catitem.getName().toLowerCase().contains(etdSearch.getText().toString().toLowerCase())) {
                                                    Filtercate.add(catitem);
                                                }
                                            }
                                            categoryAdapter cateAdapter = new categoryAdapter(frmstockstatus.this, Filtercate, choosecategory, da);
                                            rv.setAdapter(cateAdapter);
                                            GridLayoutManager gridLayoutManagercate = new GridLayoutManager(getApplicationContext(), 4);
                                            rv.setLayoutManager(gridLayoutManagercate);
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

                    FilterCustomer cad = new FilterCustomer(frmstockstatus.this, customers, btn, da, "status");
                    rv.setAdapter(cad);
                    GridLayoutManager gridLayoutManagerCustomer = new GridLayoutManager(getApplicationContext(), 4);
                    rv.setLayoutManager(gridLayoutManagerCustomer);
                    da.show();

                    break;
                case "User":
                    sqlString = "select userid,name from posuser where isinactive=0";
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
                    FilterUser us = new FilterUser(frmstockstatus.this, users, btn, da);
                    rv.setAdapter(us);
                    GridLayoutManager gridLayoutManagerUser = new GridLayoutManager(frmstockstatus.this, 4);
                    rv.setLayoutManager(gridLayoutManagerUser);
                    da.show();
                    break;

                case "Location":
                    // sqlString = "select locationid,Name,shortdesc,branchID from Location where isdeleted=0";

                    //modified by EKK
                    if (frmlogin.defaultbranchid == 0) {
                        sqlString = "select locationid,name,shortdesc,branchid from Location where isdeleted=0";
                    } else {
                        sqlString = "select locationid,name,shortdesc,branchid from Location where isdeleted=0 and branchid = " + frmlogin.defaultbranchid;
                    }
                    cursor = DatabaseHelper.rawQuery(sqlString);
                    if (cursor != null && cursor.getCount() != 0) {
                        if (cursor.moveToFirst()) {
                            do {
                                long locationid = cursor.getLong(cursor.getColumnIndex("locationid"));
                                String locationname = cursor.getString(cursor.getColumnIndex("Name"));
                                String shortname = cursor.getString(cursor.getColumnIndex("shortdesc"));
                                long branchid = cursor.getLong(cursor.getColumnIndex("branchID"));
                                locs.add(new Location(locationid, locationname, shortname, branchid));
                            } while (cursor.moveToNext());

                        }

                    } else {
                        da.dismiss();
                    }
                    cursor.close();

                    FilterLocation lad = new FilterLocation(frmstockstatus.this, locs, btn, da, "status");
                    rv.setAdapter(lad);
                    GridLayoutManager gridLayoutManagerLocation = new GridLayoutManager(getApplicationContext(), 4);
                    rv.setLayoutManager(gridLayoutManagerLocation);
                    da.show();
                    break;
                case "Class":
                    if (class_items.size() < 1) {
                        System.out.println(class_items.size());
                        cursor = DatabaseHelper.DistinctCategorySelectQuery("Usr_Code", new String[]{"class", "classname"}, "classname");
                        if (cursor != null && cursor.getCount() != 0) {
                            if (cursor.moveToFirst()) {
                                do {
                                    long classid = cursor.getLong(cursor.getColumnIndex("class"));
                                    String classname = cursor.getString(cursor.getColumnIndex("classname"));
                                    class_items.add(new class_item(classid, classname));
                                } while (cursor.moveToNext());

                            }

                        }
                        cursor.close();
                    }

                    classadapter = new classAdapter(frmstockstatus.this, class_items, gridclassview, choosecategory, da);
                    rv.setAdapter(classadapter);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 4);
                    rv.setLayoutManager(gridLayoutManager);
                    da.show();

                    break;
                case "Category":
                    if (categories.size() < 1) {
                        cursor = DatabaseHelper.DistinctCategorySelectQuery("Usr_Code", new String[]{"category", "categoryname", "class"}, "categoryname");
                        //Cursor cursor = DatabaseHelper.DistinctSelectQuery("Usr_Code",new String[]{"category","categoryname","class"});
                        //Cursor cursor=DatabaseHelper.rawQuery("select * from Usr_Code");
                        if (cursor != null && cursor.getCount() != 0) {
                            if (cursor.moveToFirst()) {
                                do {
                                    long categoryid = cursor.getLong(cursor.getColumnIndex("category"));
                                    String categoryname = cursor.getString(cursor.getColumnIndex("categoryname"));
                                    long classid = cursor.getLong(cursor.getColumnIndex("class"));
                                    categories.add(new category(categoryid, classid, categoryname));
                                } while (cursor.moveToNext());

                            }

                        }
                        cursor.close();


                    }
                    categoryadapter = new categoryAdapter(frmstockstatus.this, categories, gridclassview, choosecategory, da);
                    rv.setAdapter(categoryadapter);
                    GridLayoutManager categorygridLayoutManager = new GridLayoutManager(getApplicationContext(), 4);
                    rv.setLayoutManager(categorygridLayoutManager);
                    da.show();
                    break;
            }
        } catch (Exception e) {
            da.dismiss();
        }
    }

//    private void BindingClass(){
//        if(class_items.size()>0){
//            class_items.clear();
//        }
//        Cursor cursor = DatabaseHelper.DistinctCategorySelectQuery("Usr_Code",new String[]{"class","classname"},"classname");
//        if (cursor != null && cursor.getCount() != 0) {
//            if (cursor.moveToFirst()) {
//                do {
//                    long classid = cursor.getLong(cursor.getColumnIndex("class"));
//                    String name = cursor.getString(cursor.getColumnIndex("classname"));
//                    class_items.add(new class_item(classid,name));
//                } while (cursor.moveToNext());
//
//            }
//
//        }
//        cursor.close();
//
//        cad = new classAdapter(frmstockstatus.this, class_items, gridclassview);
//        gridclassview.setAdapter(cad);
//        LinearLayoutManager classlayoutmanger = new LinearLayoutManager(frmstockstatus.this,LinearLayoutManager.HORIZONTAL,false);
//        gridclassview.setLayoutManager(classlayoutmanger);
//
//    }
}
