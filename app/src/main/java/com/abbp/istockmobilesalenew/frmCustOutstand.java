package com.abbp.istockmobilesalenew;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

public class frmCustOutstand extends AppCompatActivity {
    ImageButton btnrefresh, btnclear;
    TextView txtdate;
    ArrayList<CustOutstand> custOutstandArrayList;
    List<Calendar> calendars = new ArrayList<>();
    CustOutstandAdapter custOutstandAdapter;
    public static SharedPreferences sh_ip, sh_port;
    private Date fdate, tdate;
    private DateFormat dateFormat;
    RecyclerView rcv;
    TextView currency, totalClosingBalance;
    public static Button choosecustomer, choosebranchid;
    AlertDialog da = null;
    ProgressDialog pb;
//    ImageView imgpaid;

    public static String branchid = "", customerid = "";
    ArrayList<Branch> branchArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frmcustoutstand);
        sh_ip = getSharedPreferences("ip", MODE_PRIVATE);
        sh_port = getSharedPreferences("port", MODE_PRIVATE);
        btnrefresh = findViewById(R.id.btnrefresh);
        txtdate = findViewById(R.id.txtdate);
        choosecustomer = findViewById(R.id.choosecustomer);
        choosebranchid = findViewById(R.id.choosebranchid);
//        imgpaid=findViewById(R.id.imgpaid);
        txtdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDateFilter();
            }
        });
        fdate = new Date();
        tdate = new Date();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        rcv = findViewById(R.id.rcvcustoutstand);
        currency = findViewById(R.id.txtcurrencyshort);
        totalClosingBalance = findViewById(R.id.txtTotal);
        btnclear = findViewById(R.id.btnclear);
        custOutstandArrayList = new ArrayList<>();
        custOutstandAdapter = new CustOutstandAdapter(this, custOutstandArrayList);
        branchArrayList = new ArrayList<>();
        setUI();

//        BindingCustomer();


    }

//    private void BindingCustomer() {
//
//        choosecustomer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                try {
//                    ArrayList<Location> locationArrayList=new ArrayList<>();
//                    Toast.makeText(frmCustOutstand.this,"This is customer",Toast.LENGTH_SHORT).show();
//                    AlertDialog.Builder bd = new AlertDialog.Builder(frmCustOutstand.this,R.style.AlertDialogTheme);
//                    View views = getLayoutInflater().inflate(R.layout.customer_filter, null);
//                    bd.setCancelable(false);
//                    bd.setView(views);
//                    RecyclerView rv = views.findViewById(R.id.rcvcustomer);
//                    String sql="select customerid,name,townshipname from Customer where isdeleted=0";
//                    //customerArrayList.clear();
//                    Cursor cursor=DatabaseHelper.rawQuery(sql);
//                    if(cursor!=null && cursor.getCount()!=0){
//                        while (cursor.moveToNext())
//                        customerArrayList.add(new customer(cursor.getInt(cursor.getColumnIndex("customerid")),cursor.getString(cursor.getColumnIndex("name")),cursor.getString(cursor.getColumnIndex("townshipname"))));
//                    }
//                    cursor.close();
//                    da=bd.create();
//                    CustomerFilterAdapter customerFilterAdapter=new CustomerFilterAdapter(frmCustOutstand.this,customerArrayList,choosecustomer,da);
//                    rv.setAdapter(customerFilterAdapter);
//                    //LinearLayoutManager linearLayoutManager=new LinearLayoutManager(frmCustOutstand.this);
//                    //rv.setLayoutManager(linearLayoutManager);
//                    GridLayoutManager gridLayoutManager=new GridLayoutManager(frmCustOutstand.this,4);
//                    rv.setLayoutManager(gridLayoutManager);
//
//
//                    ImageButton imgClose = views.findViewById(R.id.imgNochange);
//                    imgClose.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            da.dismiss();
//                        }
//                    });
//                    EditText etdSearch = views.findViewById(R.id.etdSearch);
//                    ImageButton imgSearch = views.findViewById(R.id.imgSearch);
//                    imgSearch.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            if(!etdSearch.getText().toString().isEmpty()){
//                                ArrayList<customer> filteredcustomer = new ArrayList<>();
//
//                                for (customer item : customerArrayList) {
//                                    if (item.getName().toLowerCase().contains(etdSearch.getText().toString().toLowerCase())) {
//                                        filteredcustomer.add(item);
//                                    }
//                                }
//                                CustomerFilterAdapter ca = new CustomerFilterAdapter(frmCustOutstand.this, filteredcustomer,choosecustomer,da);
//                                rv.setAdapter(ca);
//                                GridLayoutManager gridLayoutManagerCust = new GridLayoutManager(getApplicationContext(), 4);
//                                rv.setLayoutManager(gridLayoutManagerCust);
//                            }
//                        }
//                    });
//
//                    da.show();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    Toast.makeText(frmCustOutstand.this,e.toString()+"This is customer",Toast.LENGTH_SHORT).show();
//                }
//
//
//            }
//        });
//    }

    private void ShowDateFilter() {
        Context context = frmCustOutstand.this;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.daterangefilterpopup, null);
        float density = frmCustOutstand.this.getResources().getDisplayMetrics().density;
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

    private void setUI() {
//        choosebranchid.setText(branchArrayList.get(0).getShortdesc());

        btnrefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pb = new ProgressDialog(frmCustOutstand.this, R.style.AlertDialogTheme);
                pb.setTitle("Downloading");
                pb.setMessage("Please Wait...");
                pb.setCancelable(true);
                pb.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                pb.setIndeterminate(true);
//                pb.show();
                custOutstandArrayList.clear();
                custOutstandAdapter.notifyDataSetChanged();
                BindingData();
            }
        });
        choosecustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(frmCustOutstand.this, CustomerFilterPopup.class);
                startActivity(intent);

//                BindingCustomerLocation(choosecustomer,"choosecustomer");
            }
        });
        choosebranchid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BindingCustomerLocation(choosebranchid, "choosebranchid");
            }
        });
        btnclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtdate.setText("Today");
                choosecustomer.setText("Choose Customer");
               // choosebranchid.setText("Choose Branch");
                customerid = "";
//                branchid = "";
                BindDefaultBranchID();
                fdate = new Date();
                tdate = new Date();
//                custOutstandArrayList.clear();
//                custOutstandAdapter.notifyDataSetChanged();
            }
        });
//        imgpaid.setOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(),"this is toast message when paid",Toast.LENGTH_SHORT).show();
//            }
//        });
        BindDefaultBranchID();
    }

    private void BindDefaultBranchID() {
        if (frmlogin.defaultbranchid != 0) {
            branchid = String.valueOf(frmlogin.defaultbranchid);
            Cursor cursor = DatabaseHelper.rawQuery("select * from Branch where isdeleted=0 and branchid = " + frmlogin.defaultbranchid);
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        String shortdesc = cursor.getString(cursor.getColumnIndex("shortdesc"));
                        if (shortdesc != null && !shortdesc.equals("")) {
                            choosebranchid.setText(shortdesc);
                        }
                    } while (cursor.moveToNext());
                }
            }

        } else {
            branchid = "";
            choosebranchid.setText("Choose Branch");
        }
    }


    private void BindingCustomerLocation(Button choosecustomer, String btnname) {
        // branchArrayList=new ArrayList<>();
        branchArrayList.clear();
        ArrayList<customer> customerArrayList = new ArrayList<>();
        String sql = "";
        Cursor cursor;
//        Toast.makeText(frmCustOutstand.this,"This is customer",Toast.LENGTH_SHORT).show();
        AlertDialog.Builder bd = new AlertDialog.Builder(frmCustOutstand.this, R.style.AlertDialogTheme);
        View views = getLayoutInflater().inflate(R.layout.customer_filter, null);
        EditText etdSearch = views.findViewById(R.id.etdSearch);
        ImageButton imgSearch = views.findViewById(R.id.imgSearch);
        bd.setCancelable(false);
        bd.setView(views);
        da = bd.create();
        RecyclerView rv = views.findViewById(R.id.rcvcustomer);
        if (btnname.equals("choosebranchid")) {
            // sql="select * from Branch where isdeleted=0";
            //modiifed by EKK 
            if (frmlogin.defaultbranchid == 0) {
                sql = "select * from Branch where isdeleted=0";
            } else {
                sql = "select * from Branch where isdeleted=0 and branchid = " + frmlogin.defaultbranchid;
            }
            cursor = DatabaseHelper.rawQuery(sql);
            if (cursor != null && cursor.getCount() != 0) {
                while (cursor.moveToNext()) {
                    int i = 0;
//                   locationArrayList.set(i,).setBranchid(cursor.getInt(cursor.getColumnIndex("branchID")));
                    branchArrayList.add(new Branch(cursor.getInt(cursor.getColumnIndex("branchid")), cursor.getString(cursor.getColumnIndex("name")), cursor.getString(cursor.getColumnIndex("shortdesc")), 0));
                    i++;
                }
            }
            cursor.close();

            imgSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!etdSearch.getText().toString().isEmpty()) {
                        ArrayList<Branch> filterbranch = new ArrayList<>();

                        for (Branch item : branchArrayList) {
                            if (item.getName().toLowerCase().contains(etdSearch.getText().toString().toLowerCase())) {
                                filterbranch.add(item);
                            }
                        }
//                        if(filterbranch.size()>0) {
                        BranchIdFilterAdapter ca = new BranchIdFilterAdapter(frmCustOutstand.this, filterbranch, choosebranchid, da);
                        rv.setAdapter(ca);
                        GridLayoutManager gridLayoutManagerCust = new GridLayoutManager(getApplicationContext(), 4);
                        rv.setLayoutManager(gridLayoutManagerCust);
//                        }else {
                        if (filterbranch.size() == 0) {
                            //branchArrayList.clear();
                            Toast.makeText(getApplicationContext(), "No matching branch id found!", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            });

            System.out.println(etdSearch.getText().toString() + "This is edtSearch");
            if (etdSearch.getText().toString().isEmpty()) {
                BranchIdFilterAdapter branchIdFilterAdapter = new BranchIdFilterAdapter(frmCustOutstand.this, branchArrayList, choosebranchid, da);
                rv.setAdapter(branchIdFilterAdapter);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 4);
                rv.setLayoutManager(gridLayoutManager);
            }
        } else {
            sql = "select customerid,name,townshipname from Customer where isdeleted=0";
            //customerArrayList.clear();
            cursor = DatabaseHelper.rawQuery(sql);
            if (cursor != null && cursor.getCount() != 0) {
                while (cursor.moveToNext())
                    customerArrayList.add(new customer(cursor.getInt(cursor.getColumnIndex("customerid")), cursor.getString(cursor.getColumnIndex("name")), cursor.getString(cursor.getColumnIndex("townshipname"))));
            }
            cursor.close();

            CustomerFilterAdapter customerFilterAdapter = new CustomerFilterAdapter(frmCustOutstand.this, customerArrayList, choosecustomer, da);
            rv.setAdapter(customerFilterAdapter);
            //LinearLayoutManager linearLayoutManager=new LinearLayoutManager(frmCustOutstand.this);
            //rv.setLayoutManager(linearLayoutManager);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(frmCustOutstand.this, 4);
            rv.setLayoutManager(gridLayoutManager);


            imgSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!etdSearch.getText().toString().isEmpty()) {
                        ArrayList<customer> filteredcustomer = new ArrayList<>();

                        for (customer item : customerArrayList) {
                            if (item.getName().toLowerCase().contains(etdSearch.getText().toString().toLowerCase())) {
                                filteredcustomer.add(item);
                            }
                        }
                        CustomerFilterAdapter ca = new CustomerFilterAdapter(frmCustOutstand.this, filteredcustomer, choosecustomer, da);
                        rv.setAdapter(ca);
                        GridLayoutManager gridLayoutManagerCust = new GridLayoutManager(getApplicationContext(), 4);
                        rv.setLayoutManager(gridLayoutManagerCust);
                    }
                }
            });
        }
        da.show();
        ImageButton imgClose = views.findViewById(R.id.imgNochange);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                da.dismiss();
            }
        });


    }

    private void BindingData() {

//        if(customerid==null){
//            customerid="";
//        }
        pb.show();
        currency.setText("");
        totalClosingBalance.setText("");
        String ip = sh_ip.getString("ip", "empty");
        String port = sh_port.getString("port", "empty");
        String url = "http://" + ip + ":" + port + "/api/mobile/GetCustOutstand?userid=" + frmlogin.LoginUserid
                + "&fdate=" + dateFormat.format(fdate) + "&tdate=" + dateFormat.format(tdate)
                + "&customerid=" + customerid + "&branchid=" + branchid;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        final Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    System.out.println("right");
//                    JSONArray jarr=new JSONArray(response);
                    JSONObject jsonObject = new JSONObject(response);


                    JSONArray stockstatusjarr = jsonObject.getJSONArray("head");
                    JSONObject object = null;
                    for (int i = 0; i < stockstatusjarr.length(); i++) {

                        object = stockstatusjarr.getJSONObject(i);
                        custOutstandArrayList.add(new CustOutstand(object.getString("merchantname")
                                , object.getString("currencyshort"),
//                               String numberAsString = String.format("%,."+frmmain.price_places+"f", value);
                                object.getString("openamount").equals("null") ? object.getString("openamount") : String.format("%,." + frmmain.price_places + "f", sale_entry.StringTODouble(object.getString("openamount")))
                                , object.getString("saleamount").equals("null") ? object.getString("saleamount") : String.format("%,." + frmmain.price_places + "f", sale_entry.StringTODouble(object.getString("saleamount"))),
                                object.getString("returnamount").equals("null") ? object.getString("returnamount") : String.format("%,." + frmmain.price_places + "f", sale_entry.StringTODouble(object.getString("returnamount"))),
                                object.getString("discountamount").equals("null") ? object.getString("discountamount") : String.format("%,." + frmmain.price_places + "f", sale_entry.StringTODouble(object.getString("discountamount"))),
                                object.getString("paidamount").equals("null") ? object.getString("paidamount") : String.format("%,." + frmmain.price_places + "f", sale_entry.StringTODouble(object.getString("paidamount")))
                                , object.getString("closingbalance").equals("null") ? object.getString("closingbalance") : String.format("%,." + frmmain.price_places + "f", sale_entry.StringTODouble(object.getString("closingbalance")))));


                    }

                    stockstatusjarr = jsonObject.getJSONArray("det");
                    for (int i = 0; i < stockstatusjarr.length(); i++) {
                        object = stockstatusjarr.getJSONObject(i);
                        currency.setText(object.getString("currencyshort"));
                        totalClosingBalance.setText(object.getString("closingbalance"));
                    }
//
                    rcv.setAdapter(custOutstandAdapter);
                    LinearLayoutManager lm = new LinearLayoutManager(frmCustOutstand.this, LinearLayoutManager.VERTICAL, false);
                    rcv.setLayoutManager(lm);
                    custOutstandAdapter.notifyDataSetChanged();
                    System.out.println(stockstatusjarr);
//
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
                custOutstandArrayList.clear();

                pb.dismiss();
                Toast.makeText(frmCustOutstand.this, "You are in Offline. Please check your connection!", Toast.LENGTH_LONG).show();
            }
        };
        StringRequest req = new StringRequest(Request.Method.GET, url, listener, error);
        requestQueue.add(req);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, frmmain.class);
        startActivity(intent);
        finish();
    }
}
