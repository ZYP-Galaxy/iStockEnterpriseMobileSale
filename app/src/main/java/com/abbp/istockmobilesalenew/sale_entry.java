package com.abbp.istockmobilesalenew;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abbp.istockmobilesalenew.bluetoothprinter.BluetoothPrinter;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ClearCacheRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.microsoft.signalr.HubConnection;
import com.rt.printerlibrary.exception.SdkException;
import com.rt.printerlibrary.printer.RTPrinter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;


public class sale_entry extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemLongClickListener {
    public static ArrayList<category> categories = new ArrayList<>();
    public static ArrayList<class_item> class_items = new ArrayList<>();

    ArrayList<sale_head_tmp> sale_head_tmps = new ArrayList<>();
    ArrayList<sale_det_tmp> sale_det_tmps = new ArrayList<>();

    ArrayList<sale_head_tmp> sale_head_tmpsfordirect = new ArrayList<>();
    ArrayList<sale_det_tmp> sale_det_tmpsfordirect = new ArrayList<>();
    ArrayList<salechange> salechanges = new ArrayList<>();

    sale_head_tmp sale_head_tmp;
    Gson gson = new Gson();

    public static RecyclerView gridview, gridclassview, gridcodeview;
    ArrayList<usr_code> filteredCode = new ArrayList<>();
    ArrayList<category> filteredList = new ArrayList<>();
    ArrayList<usr_code> filtereddesc = new ArrayList<>();
    ArrayList<class_item> filteredclass = new ArrayList<>();
    RecyclerView rrvv, rrvvc;
    categoryAdapter ad;
    classAdapter cad;
    int tranidi = 0;

    static double userinputtax = 0.0;
    public static long tranid;

    public static long tranidfromcloud;
    public static String strtranid;
    public static int tranidInt;
    public static itemAdapter itemAdapter;
    public static String fitercode;
    public static SharedPreferences sh_ip;
    public static SharedPreferences sh_port;
    DateFormat dateFormat;
    String url;
    static ListView entrygrid;
    public static boolean openEditText = false;
    public static boolean isCodeFinding = false;
    public static boolean isCategory = false;
    String invoice_no = "";
    RequestQueue requestQueue;
    public static ArrayList<Sale_head_main> sh = new ArrayList<>();
    public static ArrayList<sale_det> sd = new ArrayList<>();
    public static ArrayList<usr_code> usr_codes = new ArrayList<>();
    public static ArrayList<PriceLevel> priceLevels = new ArrayList<>();
    boolean isqty = false;
    public static int itemPosition;
    ImageButton imgDeleteAll, imgDelete, imgSummary, imgEdit, imgConfrim, imgPrint, imgHeader, imgBack, imgLogout, savebtntolite, savetocloud;
    AlertDialog dialog, custdia;
    AlertDialog.Builder builder;
    ArrayList<String> keys = new ArrayList<>();
    String sqlstring = "";
    String keynum = "";
    AlertDialog msg;
    public static priceLevelAdapter pad;
    public static UnitAdapter uad;
    Date voudate;
    AlertDialog disDa = null;
    TextView txtChangeQty;
    TextView txtChangePrice;
    TextView txtamt;
    TextView txtdate;
    TextView tvBillCount;
    EditText txtinvoiceNo;
    public static TextView txtitemDisAmt, txtper, txttotal, txtfoc, txtitemdiscount, txtvouper, txtvoudis, txtpaid, txtpaidamt, txtnet, txttaxamt, txttaxamT, txtshowUnit, txtoutstand, txtShowSP, txtdocid;
    ProgressDialog pb;
    com.applandeo.materialcalendarview.CalendarView calendar;
    public static long selected_custgroupid = -1;
    public static long selected_townshipid = -1;
    public static boolean creditcustomer = false;
    public static double dis_percent;
    public static double disamt;
    public static boolean dis_typepercent = false;
    public static boolean changeheader = false;
    RelativeLayout rlUnit, rlLevel;
    TextView txtEdit, txtDel, txtDelAll, txtComfirm, txtBack;
    public static TextView txttax;
    RelativeLayout viewEdit, viewDel, viewDelAll, viewConfirm, viewBack, viewSave, viewcloud;
    public static int Use_Tax = 0;
    static double taxpercent = 0.0;
    AlertDialog da = null;
    AlertDialog salechange = null;
    Long ConfirmedTranid;
    Boolean voudis = false;
    Boolean paiddis = false;
    boolean startOpen = true;
    TextView tvAmount;
    public static double net_amount = 0.0;
    public static double tax_amount = 0.0;
    public static String voudDiscountString = "";
    public static boolean isCreditcustomer = false;
    public static double totalAmt_tmp = 0.0;
    public static double qty_tmp = 0.0;
    public static double vouDis_tmp = 0.0;
    public static double itemDis_tmp = 0.0;
    public static double foc_tmp = 0.0;
    public static double paidAmt_tmp = 0.0, paidPercent_tmp = 0.0;
    public static double taxper_tmp = .0;
    public static double taxamt_tmp = 0.0;
    public static double calresult_tmp = 0.0;
    public static double netamt_tmp = 0.0;
    public static double ItemdisTax_tmp = 0;
    public static int Tax_Type = 0;
    public static int caltax = 100;
    public static boolean logout = false;
    public static boolean use_location, use_customergroup, use_township, use_salesperson, Use_Delivery, use_multicash;
    public static boolean bill_not_print;
    public static int billprintcount = 1;
    String detRemark = "";
    String headRemark = "";
    // String itemdis="Normal";
    TextView headremark, detremark;
    Intent intent;
    boolean comfirm = false;
    DatePickerDialog pickerDialog;
    public static CustGroupAdapter cgd;
    public static TownshipAdapter td;
    public static CustomerAdapter cd;
    public static PaymentTypeAdapter pd;
    public static LocationAdapter ld;
    public static SalesmenAdpater sm;
    public static Button btnpaytype, btncustgroup, btncustomer, btntownship, btnSalesmen;
    public static Button btnStlocation;
    Button btndiscount, btndetail;
    public static ImageButton imgSearchCode, imgFilterCode, imgFilterClear, imgPrinter;
    ImageButton imgScanner, btncustadd;
    public static Context datacontext;
    EditText etdSearchCode;
    public static double paidamount, changeamount;
    public static boolean fromSaleChange, frombillcount = false;
    public TextView tvChange;
    static Double voudisper = 0.0, paiddispercent = 0.0;
    public static RelativeLayout rlchangePrice;
    public static RelativeLayout taxlo;
    public static ArrayList<Salesmen> SaleVouSalesmen = new ArrayList<>();
    public static double custDis = 0.0;
    public static int due_in_days = 0;
    public static double credit_limit;
    int defloc = 1;
    int defunit = -1;
    int def_cashid;
    CheckBox chkDeliver, chkOffline;
    TextView txtCloud;
    SharedPreferences sh_printer, sh_ptype;
    String ToDeliver = "";

    public HubConnection mHubConnection;

    private JSONObject jobj;
    private String msgString;
    public static boolean taxchange = false;
    SelectInsertLibrary selectInsertLibrary = new SelectInsertLibrary();
    boolean isoffline;

    public static boolean use_bluetooth=true;//Add By TZW for BlueToothPrinter.
    public static boolean use_localprint; //Added by ZYP for localprinter
    AlertDialog addDialog = null;

    double tmpSalePrice, tmpTotalAmt; //added by EKK on 18-11-2020

    RTPrinter btPrinter;

    static long specialPrice = 0;
    String newCustomerName = "";
    int newCustomerId = 0;
    private AlertDialog downloadAlert;
    private ProgressBar pbDownload;
    TextView txtProgress, txtTable;
    private Context context;
    public static boolean isnewCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sale_entry);
//        Toast.makeText(getApplicationContext(),GettingIMEINumber.IMEINO+"this is imeino",Toast.LENGTH_LONG).show();


//        Map<Integer, String> colours = new HashMap<>();
//        colours.put(1, "blue");
//        colours.put(2, "yellow");
//        colours.put(3, "green");
//
//
//        Gson gson = new Gson();
//
//        String output = gson.toJson(colours);
//
//        System.out.println(output);

//        Gson gson=new Gson();
//        Sale_head_main sale_head_main=new Sale_head_main(123,234,456);
//        ArrayList<Sale_head_main> sale_head_mains=new ArrayList<>();
//        sale_head_mains.add(new Sale_head_main(123,234,456));
//        sale_head_mains.add(new Sale_head_main(123,234,456));
//        sale_head_mains.add(new Sale_head_main(123,234,456));
//
//        Toast.makeText(this,gson.toJson(sale_head_mains).toString()+"this is json",Toast.LENGTH_LONG).show();


        defloc = frmlogin.det_locationid;
        defunit = frmmain.defunit;
        def_cashid = frmlogin.def_cashid;
        datacontext = sale_entry.this;
        rlchangePrice = (RelativeLayout) findViewById(R.id.rlchangePrice);
        comfirm = false;
        logout = false;
        sh_printer = getSharedPreferences("printer", MODE_PRIVATE);
        sh_ptype = getSharedPreferences("ptype", MODE_PRIVATE);
        sh_ip = getSharedPreferences("ip", MODE_PRIVATE);
        sh_port = getSharedPreferences("port", MODE_PRIVATE);
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");

//        String str = "1,11,111.23d";
//        try {
//            double l = DecimalFormat.getNumberInstance().parse(str).doubleValue();
//            System.out.println(l);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        FillDataWithSignalr();

        entrygrid = findViewById(R.id.entrygrid);
        pb = new ProgressDialog(sale_entry.this, R.style.AlertDialogTheme);
        pb.setMessage("Please a few seconds");
        pb.setTitle("iStock");
        itemPosition = -1;
        entrygrid.setOnItemLongClickListener(this);
        entrygrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemPosition = position;
            }
        });
        isCodeFinding = false;
        SetUI();
        if (frmmain.withoutclass.equals("true")) {
            gridview.setVisibility(View.VISIBLE);
            BindingCategory();
        } else {
            gridclassview.setVisibility(View.VISIBLE);
            gridcodeview.setVisibility(View.VISIBLE);
            BindingClass();
        }
        if (sd.size() > 0) sd.clear();
        if (sh.size() > 0) sh.clear();
        getHeader();
//        getTranID();
        getHeaderWithUUID();
        getData();
        getSystemSetting();
        if (!Use_Delivery) {
            chkDeliver.setVisibility(View.GONE);
        } else {
            chkDeliver.setVisibility(View.VISIBLE);
        }
        Tax_Type = getTaxType();
        caltax = caltaxsetting();
        fitercode = "Code";
        isCategory = true;
        imgFilterCode.setVisibility(View.GONE);
        // GetBillPrintCount();
        //settingcpyname();
//        if(isCreditcustomer&& sh.get(0).getPay_type()==2) {
//            bindingCreditBalance();
//        }

//Toast.makeText(getApplicationContext(),frmlogin.canselectcustomer,Toast.LENGTH_LONG).show();

        btPrinter = BaseApplication.getInstance().getRtPrinter();

    }

    public void bindingCreditBalance() {
        try {
            txtoutstand.setText("0");
            String ip = sh_ip.getString("ip", "empty");
            String port = sh_port.getString("port", "empty");
            String data = "_userid=" + frmlogin.LoginUserid + "&_filterdate=" + sh.get(0).getDate() + "&_customerid=" + sh.get(0).getCustomerid();
            String url = "http://" + ip + ":" +/*"80/DataSyncAPI"*/port + "/api/mobile/GetCreditBalance?" + data;
            RequestQueue requestQueue1 = Volley.newRequestQueue(this);
            final Response.Listener<String> listener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    if (!response.isEmpty()) {
                        if (Double.parseDouble(response) > 0) {
                            txtoutstand.setText(String.format("%,." + frmmain.price_places + "f", Double.parseDouble(response)));
                        }
                    } else {
                        txtoutstand.setText("0");
                    }

                }

            };

            final Response.ErrorListener error = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(sale_entry.this, "You are in Offline. Please check your connection!", Toast.LENGTH_SHORT).show();

                }
            };
            StringRequest req = new StringRequest(Request.Method.GET, url, listener, error);
            requestQueue1.add(req);


        } catch (Exception ee) {
            Toast.makeText(this, "You are in Offline. Please check your connection!", Toast.LENGTH_LONG).show();


        }

    }

    private void settingcpyname() {
        String cpystr = "";
        Cursor cursor = DatabaseHelper.rawQuery("select  * from usr_code");
        if (cursor != null && cursor.getCount() != 0) {
            // Toast.makeText(getApplicationContext(),cursor.getCount()+" this is count",Toast.LENGTH_LONG).show();
            if (cursor.moveToFirst()) {
                do {
                    cpystr += cursor.getString(cursor.getColumnIndex("class")) + "  " + cursor.getString(cursor.getColumnIndex("category")) + "  " + cursor.getString(cursor.getColumnIndex("usr_code")) + "\n";
                } while (cursor.moveToNext());
            }
////            //test for default uint type
////            //defunit=3;
        }
//        //cpyname.setText(cpystr);
        Toast.makeText(getApplicationContext(), cursor.getCount() + " this is count" + cpystr, Toast.LENGTH_LONG).show();
    }

    private void getHeaderWithUUID() {
//        tranid=UU
//        strtranid=UUID.randomUUID().toString();

        Cursor cursor = DatabaseHelper.rawQuery("select * from sale_head_main");
        tranidInt = cursor.getCount() + 1;
//        Toast.makeText(this,tranidInt+"this is tranid ",Toast.LENGTH_LONG).show();

        sh.add(new Sale_head_main(tranid, frmlogin.LoginUserid, "VOU-1", dateFormat.format(new Date()), "", "", frmlogin.det_locationid, 1, frmlogin.def_cashid, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0));

        try {
            sh.get(0).setTownshipid(1);
            SetDefaultLocation();
            String voudate = new SimpleDateFormat("dd/MM/yyyy").format(dateFormat.parse(sh.get(0).getDate()));
            txtdate.setText(voudate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    private void GetBillPrintCount() {
        GetAppSetting getAppSetting = new GetAppSetting("Bill_Print_Count");
        billprintcount = Integer.parseInt(getAppSetting.getSetting_Value());
    }

    private void getSystemSetting() {
        Cursor cursor = DatabaseHelper.rawQuery("select isuselocation,isusecustomergroup,isusetownship,isusesalesmen,isusedelivery,isusemulticash from SystemSetting");
        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    use_location = cursor.getInt(cursor.getColumnIndex("isuselocation")) == 1 ? true : false;
                    use_customergroup = cursor.getInt(cursor.getColumnIndex("isusecustomergroup")) == 1 ? true : false;
                    use_township = cursor.getInt(cursor.getColumnIndex("isusetownship")) == 1 ? true : false;
                    use_salesperson = cursor.getInt(cursor.getColumnIndex("isusesalesmen")) == 1 ? true : false;
                    Use_Delivery = cursor.getInt(cursor.getColumnIndex("isusedelivery")) == 1 ? true : false;
                    use_multicash = cursor.getInt(cursor.getColumnIndex("isusemulticash")) == 1 ? true : false; //added by EKK on 17-11-2020
                } while (cursor.moveToNext());


            }

        }
        if (Use_Tax == 0) {
            taxlo.setVisibility(View.GONE);
        } else {
            taxlo.setVisibility(View.VISIBLE);
            if (frmlogin.isusetax == 0) {
                txttaxamt.setEnabled(false);
            } else {
                txttaxamt.setEnabled(true);
            }

        }

    }

    private void GetCustomerOutstand(long customerid) {
        try {

            String ip = sh_ip.getString("ip", "empty");
            String port = sh_port.getString("port", "empty");
            String data = "userid=" + frmlogin.LoginUserid + "&tranid=" + sh.get(0).getTranid() + "&customerid=" + customerid + "&date=" + sh.get(0).getDate();
            url = "http://" + ip + ":" + port + "/api/DataSync/GetData?" + data;
            requestQueue = Volley.newRequestQueue(this);
            final Response.Listener<String> listener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    txtoutstand.setText(response);

                }

            };

            final Response.ErrorListener error = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {


                }
            };
            StringRequest req = new StringRequest(Request.Method.GET, url, listener, error);
            requestQueue.add(req);


        } catch (Exception ee) {
            //Toast.makeText(sale_entry.this, ee.getMessage(), Toast.LENGTH_LONG).show();


        }

    }

    private void SetUI() {
        try {

            SaleVouSalesmen.clear();
            TextView tvUnit = findViewById(R.id.unit);
            chkDeliver = findViewById(R.id.chkToDeliver);
            chkOffline = findViewById(R.id.chkOffline);
            chkOffline.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        selectInsertLibrary.OfflineCheck = true;
                    } else {
                        selectInsertLibrary.OfflineCheck = false;
                    }
                }
            });
            boolean b = selectInsertLibrary.OfflineCheck;
            if (selectInsertLibrary.OfflineCheck) {
                chkOffline.setChecked(true);
            }
            txtCloud = findViewById(R.id.txtCloud);
            boolean use_unit = false;
            Cursor cursorplvl = DatabaseHelper.rawQuery("select isuseunit from SystemSetting");
            if (cursorplvl != null && cursorplvl.getCount() != 0) {
                if (cursorplvl.moveToFirst()) {
                    do {
                        use_unit = cursorplvl.getInt(cursorplvl.getColumnIndex("isuseunit")) == 1 ? true : false;
                    } while (cursorplvl.moveToNext());
                }
            }
            if (cursorplvl != null)
                cursorplvl.close();

            Cursor cursorCloud = DatabaseHelper.rawQuery("select * from sale_head_main where uploaded='0'");
            int count = 0;
            if (cursorCloud != null && cursorCloud.getCount() > 0) {
                count = cursorCloud.getCount();
            }
            if (count > 0)
                txtCloud.setText(String.valueOf(cursorCloud.getCount()));
            if (cursorCloud != null)
                cursorCloud.close();

            tvUnit.setVisibility(use_unit == true ? View.VISIBLE : View.GONE);
            imgFilterClear = findViewById(R.id.imgClearFilter);
            imgFilterClear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isCategory = true;
                    switch (imgFilterCode.getVisibility()) {
                        case View.GONE:
                            if (frmmain.withoutclass.equals("true")) {
                                BindingCategory();
                                break;
                            } else if (frmmain.withoutclass.equals("false")) {
                                filteredCode.clear();
                                filtereddesc.clear();
                                filteredList.clear();
                                usr_codes.clear();
                                gridcodeview.clearFocus();
                                BindingClass();
                                break;
                            }

                        case View.VISIBLE:
                            usrcodeAdapter usrcodead = new usrcodeAdapter(sale_entry.this, usr_codes, gridview, categories);
                            gridview.setAdapter(usrcodead);
                            GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getApplicationContext(), 4);
                            gridview.setLayoutManager(gridLayoutManager1);
                            break;
                    }
                }
            });

//        imgSearchCode = findViewById(R.id.imgSearchCode);
//        imgSearchCode.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//
//                    AlertDialog.Builder searchBuilder = new AlertDialog.Builder(sale_entry.this, R.style.AlertDialogTheme);
//                    View view = getLayoutInflater().inflate(R.layout.searchbox, null);
//                    searchBuilder.setView(view);
//                    EditText etdSearch = view.findViewById(R.id.etdSearch);
//                    ImageButton btnSearch = view.findViewById(R.id.imgOK);
//                    ImageButton btnFilterCode = view.findViewById(R.id.imgFilterCode);
//                    etdSearch.setHint("By " + fitercode);
//                    btnFilterCode.setVisibility(View.VISIBLE);
//                    btnFilterCode.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            PopupMenu popup = new PopupMenu(sale_entry.this, btnFilterCode);
//                            //Inflating the Popup using xml file
//                            popup.getMenuInflater().inflate(R.menu.filtermenu, popup.getMenu());
//                            Menu pp = popup.getMenu();
//                            if (frmmain.withoutclass.equals("true")) {
//                                pp.findItem(R.id.cclass).setVisible(false);
//                            } else {
//                                pp.findItem(R.id.cclass).setVisible(true);
//                            }
//                            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                                @Override
//                                public boolean onMenuItemClick(MenuItem item) {
//                                    switch (item.getItemId()) {
//                                        case R.id.code:
//                                            fitercode = "Code";
//
//                                            break;
//                                        case R.id.description:
//                                            fitercode = "Description";
////                                            etdSearch.setHint("By "+filteredCode);
//                                            break;
//                                        case R.id.category:
//                                            fitercode = "Category";
////                                            etdSearch.setHint("By "+filteredCode);
//                                            break;
//                                        case R.id.cclass:
//                                            fitercode = "Class";
////                                            etdSearch.setHint("By "+filteredCode);
//                                            break;
//
//                                    }
//                                    etdSearch.setHint("By " + fitercode);
//                                    return true;
//                                }
//                            });
//                            popup.show();//showing popup menu
//                        }
//                    });
//                    btnSearch.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            if (!etdSearch.getText().toString().isEmpty()) {
//                                SearchItem(etdSearch.getText().toString());
//                                msg.dismiss();
//                            }
//                        }
//                    });
//
//                    msg = searchBuilder.create();
//                    msg.show();
//                } catch (Exception ee) {
//
//                }
//
//            }
//        });
            imgFilterCode = findViewById(R.id.imgFilterCode);
            imgFilterCode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(sale_entry.this, R.style.AlertDialogTheme);
                    View view = getLayoutInflater().inflate(R.layout.showposuser, null);
                    // builder.setCancelable(false);
                    builder.setView(view);
                    ListView lv = (ListView) view.findViewById(R.id.lsvposuer);
                    ArrayList<String> s = new ArrayList<>();
                    s.add("Code");
                    s.add("Description");
                    ArrayAdapter<String> item = new ArrayAdapter<>(sale_entry.this, android.R.layout.simple_list_item_1, s);
                    lv.setAdapter(item);
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            fitercode = s.get(position);
                            dialog.dismiss();
                        }
                    });
                    dialog = builder.create();
                    dialog.show();
                }
            });


            txtDel = findViewById(R.id.txtDelete);
            txtDelAll = findViewById(R.id.txtDelAll);
            txtEdit = findViewById(R.id.txtedit);
            txtComfirm = findViewById(R.id.txtConfirm);
            txtBack = findViewById(R.id.txtBack);
            viewConfirm = findViewById(R.id.viewConfirm);

            //to save in sqlite database
            viewSave = findViewById(R.id.viewSave);
            viewSave.setOnClickListener(this);
            savebtntolite = findViewById(R.id.savebtntolite);
            savebtntolite.setOnClickListener(this);
            viewcloud = findViewById(R.id.viewCloud);
            viewcloud.setOnClickListener(this);
            savetocloud = findViewById(R.id.savetoCloud);
            savetocloud.setOnClickListener(this);
            //to save in sqlite database

            viewEdit = findViewById(R.id.viewEdit);
            viewDel = findViewById(R.id.viewDelete);
            viewDelAll = findViewById(R.id.viewDelAll);
            viewBack = findViewById(R.id.viewBack);
            txtDel.setOnClickListener(this);
            txtDelAll.setOnClickListener(this);
            txtEdit.setOnClickListener(this);
            txtComfirm.setOnClickListener(this);
            txtBack.setOnClickListener(this);
            viewBack.setOnClickListener(this);
            viewDelAll.setOnClickListener(this);
            viewDel.setOnClickListener(this);
            viewEdit.setOnClickListener(this);
            viewConfirm.setOnClickListener(this);
            gridview = findViewById(R.id.gv);
            gridclassview = findViewById(R.id.recycler_category);
            gridcodeview = findViewById(R.id.recycler_code);
            imgDelete = findViewById(R.id.delete);
            imgEdit = findViewById(R.id.edit);
            imgDeleteAll = findViewById(R.id.delall);
            // imgSummary = findViewById(R.id.summary);
            // imgSummary.setOnClickListener(this);
            imgEdit.setOnClickListener(this);
            imgDeleteAll.setOnClickListener(this);
            imgDelete.setOnClickListener(this);
            txttotal = findViewById(R.id.txtTotalAmt);
            imgConfrim = findViewById(R.id.imgconfirm);
            imgConfrim.setOnClickListener(this);
            imgPrint = findViewById(R.id.imgHeader);
            imgPrint.setOnClickListener(this);

            imgPrinter = findViewById(R.id.imgPrintSelection);
            imgPrinter.setOnClickListener(this);

            txtdocid = findViewById(R.id.txtdocid);
            taxlo = findViewById(R.id.taxlo);
            txtdate = findViewById(R.id.txtdate);
            txtdate.setOnClickListener(this);
            imgHeader = findViewById(R.id.imgHeader);
            imgHeader.setOnClickListener(this);
            imgSearchCode = findViewById(R.id.imgSearchCode);
            imgSearchCode.setOnClickListener(this);
            imgLogout = findViewById(R.id.imgLogout);
            imgLogout.setOnClickListener(this);
            txtfoc = findViewById(R.id.txtFocAmt);
            txtitemdiscount = findViewById(R.id.txtitemDisAmt);
            txtvoudis = findViewById(R.id.txtvoudisamt);
            txtpaidamt = findViewById(R.id.txtPaidAmt);
            txtpaid = findViewById(R.id.txtPaid);
            // txtpaid.setText("aid amount is %");
            txtnet = findViewById(R.id.txtNetAmt);
            imgBack = findViewById(R.id.back);
            imgBack.setOnClickListener(this);
            txttaxamt = findViewById(R.id.txttaxamt);
            txttax = findViewById(R.id.txttax);
            btndetail = findViewById(R.id.btndetail);

            String tax = "Tax" + (getTax() > 0 ? "( " + getTax() + "% )" : "");
            txttax.setText(tax);
            txtoutstand = findViewById(R.id.txtPreviousAmt);
            txttaxamt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    keynum = txttaxamt.getText().toString();
                    if (frmlogin.isusetax == 1) {
                        showKeyPad(txttaxamt, txttax);
                    }
                }
            });


            btndetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String taxper;
                    String txttax;
                    if (sd.size() > 0) {
                        taxper = "Tax" + (sh.get(0).getTax_per() > 0 ? "( " + sh.get(0).getTax_per() + "% )" : "");
                        txttax = String.valueOf(sh.get(0).getTax_amount());
                    } else {
                        taxper = "Tax" + (getTax() > 0 ? "( " + getTax() + "% )" : "");
                        txttax = "0";
                    }

                    String vouper = "Vou Discount";
                    if (sh.get(0).getDiscount_per() > 0) {
                        custDis=sh.get(0).getDiscount_per();
                        vouper = "Vou Discount( " + custDis + "% )";
                        sh.get(0).setDiscount_per(custDis);
                        getSummary();
                    } else {
                        vouper = "Vou Discount" + (sh.get(0).getDiscount_per() > 0 ? "( " + sh.get(0).getDiscount_per() + "% )" : "");
                    }
                    String paidPer = "Paid%";
                    if (sh.get(0).getPaidpercent() > 0) {
                        paidPer = "Paid( " + sh.get(0).getPaidpercent() + "% )";
//                    sh.get(0).setDiscount_per(custDis);
                        getSummary();
                    } else {
                        paidPer = "Paid" + (sh.get(0).getPaidpercent() > 0 ? "( " + sh.get(0).getPaidpercent() + "% )" : "");
                    }
                    String total = txttotal.getText().toString();
                    String txtvou = String.valueOf(sh.get(0).getDiscount());
                    String txtpaidamt = String.valueOf(sh.get(0).getPaid_amount());
                    String txtfoc = String.valueOf(sh.get(0).getFoc_amount());
                    String txtitem = String.valueOf(sh.get(0).getIstemdis_amount());
                    detailvou(taxper, total, txtvou, vouper, paidPer, txtpaidamt, txttax, txtfoc, txtitem);
                }
            });


//Added By abbp barcode scanner on 19/6/2019

            imgScanner = findViewById(R.id.imgscanner);
            imgScanner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(sale_entry.this, frmscancode.class);
                    i.putExtra("name","sale");//added by KLM to effect both sale & sale order in qrcode scanner
                    startActivity(i);
                }
            });


//def customer outstand
            //GetCustomerOutstand(1);

//**************************************************************************************************


//removed by abbp for detail btn in sale entry on 18/6/2019
        /*
        txtpaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeheader=false;
                if(isCreditcustomer&& sh.get(0).getPay_type()==2) {
                    keynum = txtpaid.getText().toString();
                    paiddis=true;
                    showKeyPad(txtpaid, txtpaid);
                }
                else
                {

                    AlertDialog.Builder ab=new AlertDialog.Builder(sale_entry.this,R.style.AlertDialogTheme);
                    ab.setTitle("iStock");
                    ab.setMessage("Only Allow in Credit.");

                    ab.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            msg.dismiss();
                        }
                    });
                    msg=ab.create();
                    msg.show();
                }



            }
        });
        txtvoudis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeheader=false;
                voudis=true;
                keynum=txtvoudis.getText().toString();
                showKeyPad(txtpaid,txtvoudis);
            }
        });*/
//***************************************************************************************************
//        if(isCreditcustomer&& sh.get(0).getPay_type()==2) {
//            bindingCreditBalance();
//        }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }


    // Add detail btn in sale entry on 18/6/2019
    private void detailvou(String taxper, String total, String txtvou, String vouper, String paidPer, String txtpaidamt, String txttaxam, String txtfocamt, String txtitem) {
        AlertDialog.Builder bd = new AlertDialog.Builder(sale_entry.this);
        View view = getLayoutInflater().inflate(R.layout.frmdetailconvoucher, null);
        bd.setCancelable(false);
        bd.setView(view);
        TextView txttotal = view.findViewById(R.id.txtTotalAmt);
        txtitemDisAmt = view.findViewById(R.id.txtitemDisAmt);
        txtper = view.findViewById(R.id.txttax);
        txtvouper = view.findViewById(R.id.txtvoudis);
        TextView txtfoc = view.findViewById(R.id.txtFocAmt);
        txtitemdiscount = view.findViewById(R.id.txtitemDisAmt);
        txttaxamT = view.findViewById(R.id.txttaxamt);
        txtvoudis = view.findViewById(R.id.txtvoudisamt);
        sale_entry.txtpaidamt = view.findViewById(R.id.txtPaidAmt);
        sale_entry.txtpaid = view.findViewById(R.id.txtPaidlabel);
        sale_entry.txtpaid.setText(paidPer);
        //txtpaid=view.findViewById(R.id.txtPaid);
        taxlo = view.findViewById(R.id.taxlo);
        txtitemDisAmt.setText(txtitem);
        txtvouper.setText(vouper);
        txtper.setText(taxper);
        txttotal.setText(total);
        txtvoudis.setText(txtvou);
        sale_entry.txtpaidamt.setText(txtpaidamt);
        txttaxamT.setText(txttaxam);
        txtfoc.setText(txtfocamt);

        if (Use_Tax == 0) {
            taxlo.setVisibility(View.GONE);
        } else {
            taxlo.setVisibility(View.VISIBLE);
            if (frmlogin.isusetax == 0) {
                txttaxamT.setEnabled(false);
            } else {
                txttaxamT.setEnabled(true);
            }

        }

        //added by EKK on 26-10-2020
        if (frmlogin.candiscount == 0) {
            txtvoudis.setEnabled(false);
        } else {
            txtvoudis.setEnabled(true);
        }


        txtvoudis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                changeheader = false;
                voudis = true;
                keynum = txtvoudis.getText().toString();
                showKeyPad(sale_entry.txtpaidamt, txtvoudis);


            }
        });
//Added by abbp else case on 26/6/2019
        txttaxamT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keynum = txttaxamT.getText().toString();
                if (frmlogin.isusetax == 1) {
                    showKeyPad(txttaxamT, txtper);
                }
            }
        });


        sale_entry.txtpaidamt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeheader = false;
                if (isCreditcustomer && sh.get(0).getPay_type() == 2) {
                    keynum = sale_entry.txtpaidamt.getText().toString();
                    paiddis = true;
                    showKeyPad(sale_entry.txtpaidamt, sale_entry.txtpaidamt);
                } else {

                    AlertDialog.Builder ab = new AlertDialog.Builder(sale_entry.this, R.style.AlertDialogTheme);
                    ab.setTitle("iStock");
                    ab.setMessage("Only Allow in Credit.");

                    ab.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            msg.dismiss();
                        }
                    });
                    msg = ab.create();
                    msg.show();
                }


            }
        });

        ImageButton btnsave = view.findViewById(R.id.imgSavedet);
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                custDis = sh.get(0).getDiscount_per();
                sh.get(0).setDiscount_per(custDis);
                getSummary();
                dialog.dismiss();
            }
        });

        dialog = bd.create();
        dialog.show();


    }

    //**************************************************************************************************
    private void SearchItem(String s) {


        if (frmmain.withoutclass.equals("true")) {
            rrvv = gridview;
        } else if (frmmain.withoutclass.equals("false")) {
            rrvvc = gridclassview;
            rrvv = gridcodeview;
        }

        switch (fitercode) {
            case "Category":
                filteredList = new ArrayList<>();
                Cursor cursorcate = DatabaseHelper.DistinctSelectQuerySelection("Usr_Code", new String[]{"category", "categoryname", "class"}, "categoryname LIKE?", new String[]{"%" + s + "%"});
                if (filteredList.size() > 0) filteredList.clear();
                if (frmmain.withoutclass.equals("false")) {
                    filteredCode.clear();
                    filteredList.add(new category("Back"));
                }

                if (cursorcate != null && cursorcate.getCount() != 0) {
                    if (cursorcate.moveToFirst()) {
                        do {
                            long category = cursorcate.getLong(cursorcate.getColumnIndex("category"));
                            String name = cursorcate.getString(cursorcate.getColumnIndex("categoryname"));
                            long classid = cursorcate.getLong(cursorcate.getColumnIndex("class"));
                            filteredList.add(new category(category, classid, name));
                        } while (cursorcate.moveToNext());

                    }

                }
                cursorcate.close();

                categoryAdapter ad = new categoryAdapter(sale_entry.this, filteredList, rrvvc);

                if (frmmain.withoutclass.equals("true")) {
                    rrvv = gridview;
                    rrvv.setAdapter(ad);
                    GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getApplicationContext(), 4);
                    rrvv.setLayoutManager(gridLayoutManager1);
                } else if (frmmain.withoutclass.equals("false")) {
                    rrvvc = gridclassview;
                    rrvv = gridcodeview;
                    rrvvc.setAdapter(ad);
                    LinearLayoutManager lc = new LinearLayoutManager(sale_entry.this, LinearLayoutManager.HORIZONTAL, false);
                    rrvvc.setLayoutManager(lc);
                }

                break;
            case "Code":
                filteredCode = new ArrayList<>();
                if (isCategory) {
                    Cursor cursor = DatabaseHelper.DistinctSelectQuerySelection("Usr_Code", new String[]{"usr_code", "description"}, "usr_code LIKE?", new String[]{s});
                    if (sale_entry.usr_codes.size() > 0)
                        if (frmmain.withoutclass.equals("true")) {
                            filteredCode.add(new usr_code("Back", "Back"));
                        }
                    if (cursor != null && cursor.getCount() != 0) {
                        if (cursor.moveToFirst()) {
                            do {
                                String usr_code = cursor.getString(cursor.getColumnIndex("usr_code"));
                                String description = cursor.getString(cursor.getColumnIndex("description"));
                                filteredCode.add(new usr_code(usr_code, description));
                            } while (cursor.moveToNext());

                        }
                        cursor.close();
                    } else {
                        /*String ss=null;
                        Cursor alcursor = DatabaseHelper.rawQuery("select usr_code from Alias_Code where al_code Like '"+s+"'");
                        if(alcursor!=null&&alcursor.getCount()!=0){
                            if(alcursor.moveToNext()){
                                do{
                                    ss=alcursor.getString(alcursor.getColumnIndex("usr_code"));
                                }while (alcursor.moveToNext());
                            }
                        }
                        alcursor.close();*/
                        Cursor urcursor = DatabaseHelper.rawQuery("select usc.usr_code,usc.description from Alias_Code al join Usr_Code usc on usc.usr_code=al.usr_code where al.al_code LIKE '" + s + "'");
                        if (sale_entry.usr_codes.size() > 0)
                            if (frmmain.withoutclass.equals("true")) {
                                filteredCode.add(new usr_code("Back", "Back"));
                            }
                        if (urcursor != null && urcursor.getCount() != 0) {
                            if (urcursor.moveToNext()) {
                                do {
                                    String usr_code = urcursor.getString(urcursor.getColumnIndex("usr_code"));
                                    String description = urcursor.getString(urcursor.getColumnIndex("description"));
                                    filteredCode.add(new usr_code(usr_code, description));
                                } while (urcursor.moveToNext());
                            }
                        }
                        urcursor.close();
                    }


                } else {

                    filteredCode.add(new usr_code("Back", "Back"));
                    for (usr_code item : usr_codes) {
                        if (item.getUsr_code() != "Back") {
                            if (item.getUsr_code().toLowerCase().contains(s.toString().toLowerCase())) {
                                filteredCode.add(item);
                            }
                        }
                    }
                }
                usrcodeAdapter usrcodead = new usrcodeAdapter(sale_entry.this, filteredCode, rrvv, categories);
                rrvv.setAdapter(usrcodead);
                GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getApplicationContext(), 4);
                rrvv.setLayoutManager(gridLayoutManager1);

                break;
            case "Description":
                filtereddesc = new ArrayList<>();
                if (frmmain.withoutclass.equals("true")) {
                    filtereddesc.add(new usr_code("Back", "Back"));
                }
                if (isCategory) {
                    Cursor cursor = DatabaseHelper.DistinctSelectQuerySelection("Usr_Code", new String[]{"usr_code", "description"}, "description LIKE?", new String[]{"%" + s + "%"});
                    if (sale_entry.usr_codes.size() > 0) sale_entry.usr_codes.clear();
                    if (cursor != null && cursor.getCount() != 0) {
                        if (cursor.moveToFirst()) {
                            do {
                                String usr_code = cursor.getString(cursor.getColumnIndex("usr_code"));
                                String description = cursor.getString(cursor.getColumnIndex("description"));
                                filtereddesc.add(new usr_code(usr_code, description));
                            } while (cursor.moveToNext());

                        }

                    }
                    cursor.close();
                } else {
                    for (usr_code item : usr_codes) {
                        if (item.getUsr_code() != "Back") {
                            if (item.getDescription().toLowerCase().contains(s.toString().toLowerCase())) {
                                filtereddesc.add(item);
                            }
                        }
                    }
                }
                usrcodeAdapter descad = new usrcodeAdapter(sale_entry.this, filtereddesc, rrvv, categories);
                rrvv.setAdapter(descad);
                GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getApplicationContext(), 4);
                rrvv.setLayoutManager(gridLayoutManager2);
                break;

            case "Class":
                filteredclass = new ArrayList<>();
                Cursor cursorclass = DatabaseHelper.DistinctSelectQuerySelection("Usr_Code", new String[]{"class", "classname"}, "classname LIKE?", new String[]{"%" + s + "%"});
                if (filteredclass.size() > 0) {
                    filteredclass.clear();
                }
                if (cursorclass != null && cursorclass.getCount() != 0) {
                    if (cursorclass.moveToFirst()) {
                        do {
                            long classid = cursorclass.getLong(cursorclass.getColumnIndex("class"));
                            String name = cursorclass.getString(cursorclass.getColumnIndex("classname"));
                            filteredclass.add(new class_item(classid, name));
                        } while (cursorclass.moveToNext());

                    }

                }
                cursorclass.close();

                cad = new classAdapter(sale_entry.this, filteredclass, gridclassview);
                gridclassview.setAdapter(cad);
                LinearLayoutManager classlayoutmanger = new LinearLayoutManager(sale_entry.this, LinearLayoutManager.HORIZONTAL, false);
                gridclassview.setLayoutManager(classlayoutmanger);
                break;


        }

    }

    public static double getTax() {

        String sqlString = "select isusetax,defaulttaxpercent from SystemSetting";
        Cursor cursor = DatabaseHelper.rawQuery(sqlString);
        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    Use_Tax = cursor.getInt(cursor.getColumnIndex("isusetax"));
                    taxpercent = cursor.getDouble(cursor.getColumnIndex("defaulttaxpercent"));
                } while (cursor.moveToNext());
            }
        }
        if (cursor != null)
            cursor.close();
        if (Use_Tax == 0) taxpercent = 0;
        return taxpercent;
    }

    private void InsertheadMain() {

        String head = "insert into Sale_Head_Main(tranid,userid,docid,date,invoice_no,locationid,customerid,cash_id,pay_type,due_indays,currency,discount,paid_amount,invoice_amount,invoice_qty,foc_amount,Remark,itemdis_amount)" +
                "values(" +
                sh.get(0).getTranid() + "," +
                sh.get(0).getUserid() + ",'" +
                sh.get(0).getDocid() + "'," +
                "'" + String.format(sh.get(0).getDate(), "yyyy-MM-dd") + "','" +
                sh.get(0).getInvoice_no() + "'," +
                sh.get(0).getLocationid() + "," +
                sh.get(0).getCustomerid() + "," +
                sh.get(0).getDef_cashid() + "," +
                sh.get(0).getPay_type() + "," +
                sh.get(0).getDue_in_days() + "," +
                sh.get(0).getCurrency() + "," +
                sh.get(0).getDiscount() + "," +
                sh.get(0).getPaid_amount() + "," +
                sh.get(0).getInvoice_amount() + "," +
                sh.get(0).getInvoice_qty() + "," +
                sh.get(0).getFoc_amount() + "," +
                sh.get(0).getHeadremark() + "'," +
                sh.get(0).getIstemdis_amount()
                + ")";
        sqlstring = head;
        String sqlUrl = "";
        String ip = sh_ip.getString("ip", "empty");
        String port = sh_port.getString("port", "empty");
        sqlstring = sqlstring + "&check=true";
        try {
            sqlstring = URLEncoder.encode(sqlstring, "UTF-8").replace("+", "%20")
                    .replace("%26", "&").replace("%3D", "=")
                    .replace("%2C", ",")
            ;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        sqlUrl = "http://" + ip + ":" + port + "/api/DataSync/GetData?sqlstring=" + sqlstring;
        requestQueue = Volley.newRequestQueue(this);
        final Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }

        };

        final Response.ErrorListener error = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(sale_entry.this, "You are in Offline. Please check your connection!", Toast.LENGTH_SHORT).show();
                pb.dismiss();
            }
        };
        StringRequest req = new StringRequest(Request.Method.GET, sqlUrl, listener, error);
        requestQueue.add(req);

    }


    public static void getData() {
        itemAdapter = new itemAdapter(datacontext);
        itemAdapter.getItemAdpater(itemAdapter);
        entrygrid.setAdapter(itemAdapter);
    }

    private void getHeader() {
        try {
            String entryformname = "saleentry";
            String ip = sh_ip.getString("ip", "empty");
            String port = sh_port.getString("port", "empty");
            url = "http://" + ip + ":" + port + "/api/mobile/GetVoucher?userid=" + frmlogin.LoginUserid + "&entryformname=" + entryformname;
            requestQueue = Volley.newRequestQueue(this);
            final Response.Listener<String> listener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {

                        JSONArray jarr = new JSONArray(response);
                        if (sh.size() > 0) sh.clear();
                        for (int i = 0; i < jarr.length(); i++) {
                            JSONObject obj = jarr.getJSONObject(i);
                            tranid = obj.getLong("tranid");
                            String docid = obj.getString("docid");
                            String date = obj.getString("date");
                            long locationid = obj.getLong("locationid");
                            long customerid = obj.getLong("customerid");
                            double tax_per = getTax();
                            //sh.add(new Sale_head_main(tranid, frmlogin.LoginUserid,  "VOU-1",  date,  "",  "",  1,  1,   1, 0,  1,  0,  0,  0,  0,  0,  0,  0,  0,0));
                            sh.add(new Sale_head_main(tranid, frmlogin.LoginUserid, "VOU-1", date, "", "", frmlogin.det_locationid, customerid, frmlogin.def_cashid, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0));
//                        sh.add(new Sale_head_main(1));
                            sh.get(0).setTownshipid(1);
//                        Toast.makeText(getApplicationContext(),"this is township "+ sh.get(0).getTownshipid(),Toast.LENGTH_LONG).show();
                        }
                        //InsertheadMain();
                        txtdocid.setText(sh.get(0).getDocid());
                        sh.get(0).setDate(dateFormat.format(new Date()));
                        SetDefaultLocation();
//                        try {
//                            String voudate=new SimpleDateFormat("dd/MM/yyyy").format(dateFormat.parse(sh.get(0).getDate()));
//                            txtdate.setText(voudate);
//                        } catch (ParseException e) {
//                            e.printStackTrace();
//                        }
                    } catch (JSONException e) {

                    }
                }


            };

            final Response.ErrorListener error = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {


                }
            };
            StringRequest req = new StringRequest(Request.Method.GET, url, listener, error);
            requestQueue.add(req);


        } catch (Exception ee) {
            Toast.makeText(sale_entry.this, ee.getMessage(), Toast.LENGTH_LONG).show();


        }
    }

    private void BindingClass() {
        if (class_items.size() > 0) {
            class_items.clear();
        }
        Cursor cursor = DatabaseHelper.DistinctCategorySelectQuery("Usr_Code", new String[]{"class", "classname"}, "classname");
        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    long classid = cursor.getLong(cursor.getColumnIndex("class"));
                    String name = cursor.getString(cursor.getColumnIndex("classname"));
                    class_items.add(new class_item(classid, name));
                } while (cursor.moveToNext());

            }

        }
        cursor.close();

        cad = new classAdapter(sale_entry.this, class_items, gridclassview);
        gridclassview.setAdapter(cad);
        LinearLayoutManager classlayoutmanger = new LinearLayoutManager(sale_entry.this, LinearLayoutManager.HORIZONTAL, false);
        gridclassview.setLayoutManager(classlayoutmanger);

    }

/*
    private void BindingCode(){
        if(usr_codes.size()>0){
            usr_codes.clear();
        }
        sale_entry.imgFilterCode.setVisibility(View.GONE);
        sale_entry.fitercode="Description";
        Cursor cursor = DatabaseHelper.rawQuery("Select usr_code,description from Usr_Code where category=1");
        if(sale_entry.usr_codes.size()>0) sale_entry.usr_codes.clear();
        if(cursor!=null&&cursor.getCount()!=0)
        {
            if(cursor.moveToFirst())
            {
                do {
                    String usr_code=cursor.getString(cursor.getColumnIndex("usr_code"));
                    String description=cursor.getString(cursor.getColumnIndex("description"));
                    sale_entry.usr_codes.add(new usr_code(usr_code,description));
                }while (cursor.moveToNext());

            }

        }
        cursor.close();
        usrcodeAdapter ad=new usrcodeAdapter(sale_entry.this,sale_entry.usr_codes,gridcodeview);
        gridcodeview.setAdapter(ad);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(sale_entry.this,4);
        gridcodeview.setLayoutManager(gridLayoutManager);

    }
*/

    private void BindingCategory() {
        if (categories.size() > 0) {
            categories.clear();
        }
        Cursor cursor = DatabaseHelper.DistinctCategorySelectQuery("Usr_Code", new String[]{"category", "categoryname", "class"}, "categoryname");
        //Cursor cursor = DatabaseHelper.DistinctSelectQuery("Usr_Code",new String[]{"category","categoryname","class"});
        //Cursor cursor=DatabaseHelper.rawQuery("select * from Usr_Code");
        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    long categoryid = cursor.getLong(cursor.getColumnIndex("category"));
                    String name = cursor.getString(cursor.getColumnIndex("categoryname"));
                    long classid = cursor.getLong(cursor.getColumnIndex("class"));
                    categories.add(new category(categoryid, classid, name));
                } while (cursor.moveToNext());

            }

        }
        cursor.close();

        ad = new categoryAdapter(sale_entry.this, categories, gridview);
        gridview.setAdapter(ad);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 4);
        gridview.setLayoutManager(gridLayoutManager);
    }

    @Override
    public void onBackPressed() {
        if (!comfirm && sd.size() > 0) {
            Context context = this;
            AlertDialog.Builder bd = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
            bd.setTitle("iStock");
            bd.setMessage("Do you Confirm Voucher? if you do not comfirm,you lost your data");
            bd.setCancelable(false);
            bd.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    voucherConfirm();
                    msg.dismiss();
                }
            });
            bd.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    intent = new Intent(context, frmmain.class);
                    //intent.putExtra("name","Sale History");
                    startActivity(intent);
                    finish();

                }
            });
            bd.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    msg.dismiss();
                }
            });
            msg = bd.create();
            msg.show();
        } else {
            GoToFrmMain();
        }
    }

    public void GoToFrmMain() {
        intent = new Intent(this, frmmain.class);
        intent.putExtra("name", "Sale History");
        startActivity(intent);
        finish();
    }

    public void GoToSaleList() {
        intent = new Intent(this, frmsalelist.class);
        intent.putExtra("name", "Sale History");
        startActivity(intent);
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit:
            case R.id.txtedit:
            case R.id.viewEdit:
//                Toast.makeText(sale_entry.this,"u click edit",Toast.LENGTH_LONG).show();
                EditInfo();
                break;
            case R.id.delete:
            case R.id.txtDelete:
            case R.id.viewDelete:
                if (itemPosition == -1) return;
                sd.remove(itemPosition);
                for (int i = 0; i < sd.size(); i++) {
                    sd.get(i).setSr(i + 1);
                }
                getData();
                getSummary();
                itemPosition = -1;
                break;
            case R.id.delall:
            case R.id.txtDelAll:
            case R.id.viewDelAll:

                if (sd.size() > 0) {
                    AlertDialog.Builder bd = new AlertDialog.Builder(datacontext, R.style.AlertDialogTheme);
                    bd.setTitle("iStock");
                    bd.setMessage("Are you sure want to delete all stock lists?");
                    bd.setCancelable(false);
                    bd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sd.clear();
                            itemAdapter = new itemAdapter(sale_entry.this);
                            entrygrid.setAdapter(itemAdapter);
                            String tax = "Tax" + (getTax() > 0 ? "( " + getTax() + "% )" : "");
                            txttax.setText(tax);
                            txtfoc.setText("0.00");
                            sh.get(0).setIstemdis_amount(0.0);
                            sh.get(0).setTax_per(getTax());
                            sh.get(0).setTax_amount(0.0);
                            sh.get(0).setPaid_amount(0);
                            sh.get(0).setDiscount_per(0);
                            sh.get(0).setDiscount(0);
                            getSummary();
                            itemPosition = -1;
                            dialog.dismiss();
                        }
                    });

                    bd.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    bd.create().show();
                }
                break;
            case R.id.imgSearchCode:
                CodeFind();
                break;
            case R.id.imgLogout:
                logout = true;
                LogOut();
                break;

            case R.id.savebtntolite:
            case R.id.viewSave:
                if (sale_entry.sd.size() > 0) {
                    comfirm = true;
//                    voucherConfirmtoLite();
                    voucherConfirmtoLiteDB();

                } else {
                    AlertDialog.Builder bd = new AlertDialog.Builder(sale_entry.this, R.style.AlertDialogTheme);
                    bd.setTitle("iStock");
                    bd.setMessage("No data to Confirm!");
                    bd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    bd.create().show();
                }
                break;

            case R.id.savetoCloud:
            case R.id.viewCloud:
                voucherConfirmtoCloud();

//
//                Cursor cursor = DatabaseHelper.DistinctSelectQuery("sale_head_main",new String[]{"tranid"
//                        ,"docid"
//                        ,"date",
//                        "invoiceno"
//                        ,"locationid"
//                        ,"customerid"
//                        ,"cashid"
//                        ,"townshipid"
//                        ,"paytypeid"
//                        ,"dueindays"
//                        ,"salecurr"
//                        ,"discountamount"
//                        ,"paidamount"
//                        ,"invoiceamount"
//                        ,"invoiceqty"
//                        ,"focamount"
//                        ,"netamount"
//                        ,"voucherremark"
//                        ,"taxamount"
//                        ,"taxpercent"
//                        ,"discountpercent"
//                        ,"exgrate"
//                        ,"userid",""});
//                if (cursor != null && cursor.getCount() != 0) {
//                    if (cursor.moveToFirst()) {
//                        do {
////                            cursor.getColumnName();
//                            String stringtranid = cursor.getString(cursor.getColumnIndex("docid"));
//                            String custgroupname = cursor.getString(cursor.getColumnIndex("date"));
//                            String shortname = cursor.getString(cursor.getColumnIndex("invoiceno"));
//                            ContentValues contentValues=new ContentValues();
//                            contentValues.put("tranid",);
//                            contentValues.put("docid",cursor.getString(cursor.getColumnIndex("docid")));
//                            contentValues.put("date",cursor.getString(cursor.getColumnIndex("date")));
//                            contentValues.put("invoiceno",cursor.getString(cursor.getColumnIndex("invoiceno")));
//                            contentValues.put("locationid",cursor.getInt(cursor.getColumnIndex("locationid")));
//                            contentValues.put("customerid",cursor.getInt(cursor.getColumnIndex("customerid")));
//                            contentValues.put("cashid",cursor.getInt(cursor.getColumnIndex("cashid")));
//                            contentValues.put("townshipid",cursor.getInt(cursor.getColumnIndex("townshipid")));
//                            contentValues.put("paytypeid",cursor.getInt(cursor.getColumnIndex("paytypeid")));
//                            contentValues.put("dueindays",cursor.getInt(cursor.getColumnIndex("dueindays")));
//                            contentValues.put("salecurr",cursor.getInt(cursor.getColumnIndex("salecurr")));
//                            contentValues.put("discountamount",cursor.getDouble(cursor.getColumnIndex("discountamount")));
//                            contentValues.put("paidamount",cursor.getDouble(cursor.getColumnIndex("paidamount")));
//                            contentValues.put("invoiceamount",cursor.getDouble(cursor.getColumnIndex("invoiceamount")));
//                            contentValues.put("invoiceqty",cursor.getDouble(cursor.getColumnIndex("invoiceqty")));
//                            contentValues.put("focamount",cursor.getDouble(cursor.getColumnIndex("focamount")));
//                            contentValues.put("netamount",cursor.getDouble(cursor.getColumnIndex("netamount")));
//                            contentValues.put("voucherremark",cursor.getString(cursor.getColumnIndex("voucherremark")));
//                            contentValues.put("taxamount",cursor.getDouble(cursor.getColumnIndex("taxamount")));
//                            contentValues.put("taxpercent",cursor.getDouble(cursor.getColumnIndex("taxpercent")));
//                            contentValues.put("discountpercent",cursor.getDouble(cursor.getColumnIndex("discountpercent")));
//                            contentValues.put("exgrate",cursor.getDouble(cursor.getColumnIndex("exgrate")));
//                            contentValues.put("userid",cursor.getInt(cursor.getColumnIndex("userid")));
//
////                            cg.add(new customergroup(custgroupid, custgroupname, shortname));
//                        } while (cursor.moveToNext());
//
//                    }
//
//                }else{
//                    Toast.makeText(getApplicationContext(),"No data to upload cloud",Toast.LENGTH_LONG).show();
//                }

                break;
            case R.id.imgconfirm:
            case R.id.txtConfirm:
            case R.id.viewConfirm:

                if (sale_entry.sd.size() > 0) {
                    comfirm = true;
//                    voucherConfirmtoLite();
                    SelectInsertLibrary selectInsertLibrary = new SelectInsertLibrary();
//                    if (chkOffline.isChecked()) {
//
//                        selectInsertLibrary.OfflineCheck = true;
////                            voucherConfirmtoLiteDB();
//                        voucherConfirm();
//
//                    } else {
//                        selectInsertLibrary.OfflineCheck = false;
//                        voucherConfirm();
//                    }
                    if(sh.get(0).getPay_type()==2){
                        AlertDialog.Builder bd = new AlertDialog.Builder(sale_entry.this, R.style.AlertDialogTheme);
                        bd.setTitle("iStock");
                        bd.setMessage("Do you want to Print Bill?");
                        bd.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                billprintcount=1;
                                bill_not_print=false;
                                use_bluetooth=true;
                                dialog.dismiss();
                                voucherConfirm();
                            }
                        });
                        bd.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                bill_not_print=true;
                                voucherConfirm();
                            }
                        });
                        bd.create().show();

                    }
                    else{
                        voucherConfirm();
                    }

                } else {
                    AlertDialog.Builder bd = new AlertDialog.Builder(sale_entry.this, R.style.AlertDialogTheme);
                    bd.setTitle("iStock");
                    bd.setMessage("No data to Confirm!");
                    bd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    bd.create().show();

                }

                // }
                /*else{
                    if(sale_entry.sd.size()>0) {
                        comfirm = true;
                        voucherConfirm();
                    }
                    else
                    {
                        AlertDialog.Builder bd= new AlertDialog.Builder(sale_entry.this,R.style.AlertDialogTheme);
                        bd.setTitle("iStock");
                        bd.setMessage("No data to Confirm!");
                        bd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        bd.create().show();

                    }
                }*/


                break;

            case R.id.back:
            case R.id.txtBack:
            case R.id.viewBack:


                if (!comfirm && sd.size() > 0) {
                    Context context = this;
                    AlertDialog.Builder bd = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
                    bd.setTitle("iStock");
                    bd.setMessage("Do you Comfirm Voucher? if you do not comfirm,you lost your data");
                    bd.setCancelable(false);
                    bd.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            voucherConfirm();
                            msg.dismiss();
                        }
                    });
                    bd.setNegativeButton("N0", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            CleanGarbage();
                            intent = new Intent(context, frmsalelist.class);
                            intent.putExtra("name", "Sale History");
                            startActivity(intent);
                            finish();
                        }
                    });
                    bd.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            msg.dismiss();
                        }
                    });
                    msg = bd.create();
                    msg.show();
                } else {
                    CleanGarbage();
                    intent = new Intent(this, frmsalelist.class);
                    intent.putExtra("name", "Sale History");
                    startActivity(intent);
                    finish();
                }
                break;
            case R.id.imgPrintSelection:
                ShowPrintSelection();
                break;
            case R.id.imgHeader:
//                Toast.makeText(sale_entry.this,"this is customer ",Toast.LENGTH_LONG).show();
                selected_townshipid = -1;
                selected_custgroupid = -1;

                AlertDialog.Builder builder = new AlertDialog.Builder(sale_entry.this);
                View view = getLayoutInflater().inflate(R.layout.headerinfo, null);
                //builder.setCancelable(false);
                builder.setView(view);
                RelativeLayout rlCustGroup = view.findViewById(R.id.rlCustGroup);
                RelativeLayout rlTownship = view.findViewById(R.id.rlTownship);
                RelativeLayout rlLocatin = view.findViewById(R.id.rl_printer);
                RelativeLayout rlsalesmen = view.findViewById(R.id.rl_printertype);
                RelativeLayout rlCashIn = view.findViewById(R.id.rlCashIn); //added by EKK on 16-11-2020

                btncustgroup = view.findViewById(R.id.btnCustGroup);
                btntownship = view.findViewById(R.id.btnTownship);
                btncustomer = view.findViewById(R.id.btnCustomer);
                btnStlocation = view.findViewById(R.id.location);
                btnSalesmen = view.findViewById(R.id.salesmen);
                btncustadd = view.findViewById(R.id.custadd);


                if (!use_salesperson) {
                    rlsalesmen.setVisibility(View.GONE);
                } else {
                    rlsalesmen.setVisibility(View.VISIBLE);
                }
                if (!use_customergroup) {
                    rlCustGroup.setVisibility(View.GONE);

                }
                if (!use_township) {
                    rlTownship.setVisibility(View.GONE);

                }

                if (!use_multicash) {
                    rlCashIn.setVisibility(View.GONE);

                }

//not select_location in sale entry modified by ABBP
                if (use_location) {
                    if (frmlogin.canselectlocation == 0) {

                        rlLocatin.setEnabled(false);
                        btnStlocation.setEnabled(false);
                    } else {

                        rlLocatin.setEnabled(true);
                        btnStlocation.setEnabled(true);
                    }
                } else {
                    rlLocatin.setVisibility(View.GONE);
                }
                //not select_customer in sale entry modified by ABBP
                if (frmlogin.canselectcustomer == 0) {
                    rlCustGroup.setVisibility(View.GONE);
                    rlTownship.setVisibility(View.GONE);
                    btncustomer.setEnabled(false);
                    btncustomer.setTextColor(Color.GRAY);
                }
                TextView txtinvoiceNo = view.findViewById(R.id.txtInvoiceNo);
                txtinvoiceNo.setText(sh.get(0).getInvoice_no() == "NULL" ? "" : sh.get(0).getInvoice_no());
//
//
//                //add headremark in header
                headremark = view.findViewById(R.id.txtheadremark);
                headremark.setText(sh.get(0).getHeadremark() == "NULL" ? "" : sh.get(0).getHeadremark());

//salesmen selcet to btn
                if (SaleVouSalesmen.size() > 0) {
                    String salesmen = "";
                    if (SaleVouSalesmen.size() > 4) {

                        for (int i = 0; i < 4; i++) {

                            if (i != 3) {
                                salesmen += SaleVouSalesmen.get(i).getSalesmen_Short() + ",";
                            } else {
                                salesmen += SaleVouSalesmen.get(i).getSalesmen_Short() + ",...";
                            }

                        }

                    } else {
                        for (int i = 0; i < SaleVouSalesmen.size(); i++) {
                            if (i != SaleVouSalesmen.size() - 1) {
                                salesmen += SaleVouSalesmen.get(i).getSalesmen_Short() + ",";
                            } else {
                                salesmen += SaleVouSalesmen.get(i).getSalesmen_Short();
                            }
                        }
                    }

                    btnSalesmen.setText(salesmen);
                }


                ImageButton btnsave = view.findViewById(R.id.imgSave);
///*Added by abbp customer quick set up */

                btncustadd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder custb = new AlertDialog.Builder(view.getContext());
                        View layout = getLayoutInflater().inflate(R.layout.custquickadd, null);
                        custb.setCancelable(true);
                        custb.setView(layout);
                        final EditText name = layout.findViewById(R.id.txtName);
//                        final EditText shortdesc = layout.findViewById(R.id.txtShort);
                        final EditText code = layout.findViewById(R.id.txtCode);
                        final CheckBox chkCredit = layout.findViewById(R.id.chkCredit);
                        Button imgCustomerTownship = layout.findViewById(R.id.btnTownship);
                        Button imgCustomerGroup = layout.findViewById(R.id.btnCustGroup);
                        EditText credit = layout.findViewById(R.id.txtCredit);
                        credit.setEnabled(false);
                        Button btnclose = layout.findViewById(R.id.btnclose);
                        Button btnok = layout.findViewById(R.id.btnok);
                        btnclose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                custdia.dismiss();
                            }
                        });
                        custdia = custb.create();
                        custdia.show();

                        //modified by ZYP 01-09-2021 for customer setup

                        imgCustomerTownship.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ChangeHeader("Township", imgCustomerTownship, imgCustomerTownship);
                            }
                        });
                        imgCustomerGroup.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ChangeHeader("Customer Group", imgCustomerGroup, imgCustomerGroup);
                            }
                        });

                        chkCredit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (isChecked) {
                                    credit.setEnabled(true);
                                } else {
                                    credit.setEnabled(false);
                                }
                            }
                        });

                        btnok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (name.getText().toString().trim().isEmpty()) {
                                    //|| code.getText().toString().trim().isEmpty()
                                    AlertDialog.Builder bd = new AlertDialog.Builder(sale_entry.this, R.style.AlertDialogTheme);
                                    bd.setTitle("iStock");
                                    bd.setMessage("No data to Confirm!");
                                    bd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            bd.create().dismiss();
                                        }
                                    });
                                    bd.create().show();
                                } else {
                                    try {
                                        boolean iscredit = false;
                                        double creditL = 0.0;
                                        String nameSt = name.getText().toString().trim();
                                        String codeSt = code.getText().toString().trim();
                                        String creditLimit = credit.getText().toString().trim();
                                        int custid = getCustomerCount() + 1;
                                        if (chkCredit.isChecked()) {
                                            iscredit = true;
                                            creditL = Double.parseDouble(creditLimit.equals("") ? "0.0" : creditLimit);
                                        }

                                        ArrayList<customer> customers = new ArrayList<>();

                                        customer newcustomer = new customer();
                                        newcustomer.setCustomerid(custid);
                                        newcustomer.setName(nameSt);
                                        newcustomer.setIscredit(iscredit);
                                        newcustomer.setCreditlimit((int) creditL);
                                        newcustomer.setCustomerid(Integer.parseInt(String.valueOf(selected_custgroupid)));
                                        newcustomer.setTownshipid(Integer.parseInt(String.valueOf(selected_townshipid)));
                                        newcustomer.setIsdeleted(false);
                                        newcustomer.setIsinactive(false);

                                        customers.add(newcustomer);

                                        //modified by ZYP 31-08-2021 for customer setup
                                        sqlstring = "insert into customer(customerid, shortdesc, sortid, name, companyname, townshipid, contact," +
                                                "pricelevelid, address, phone, fax, email, iscredit, balance, creditlimit, dueindays, " +
                                                "discountpercent, isinactive, discountamount, custgroupid, lastinvoiceno, branchid, " +
                                                "nationalcardid, birthdate, updateddatetime,isdeleted) " +
                                                "values (" + custid + ",'" + codeSt + "',null,'" + nameSt + "',null," + selected_townshipid + ",null, " +
                                                "null,null,null,null,null," + iscredit + ",null," + creditL + ",null, " +
                                                "null,false,null, " + selected_custgroupid + ",null,null, " +
                                                "null,null,localtimestamp,false)"; //gson.toJson(customers) + "&" + frmlogin.LoginUserid;

                                        InsertCustomer();
                                        name.setText("");
                                        code.setText("");
                                        credit.setText("");
                                        chkCredit.setChecked(false);

                                    } catch (Exception eee) {
                                        Toast.makeText(getBaseContext(), eee.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                            }
                        });
                    }
                });

                btncustgroup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ChangeHeader("Customer Group", btncustgroup, btnpaytype);
                    }
                });
                btnSalesmen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Toast.makeText(sale_entry.this,"u click sm!!!!!!!!!!!!!!!!!!!!!!!",Toast.LENGTH_LONG).show();
                        ChangeHeader("Salesmen", btnSalesmen, btnpaytype);
                    }
                });
                btntownship.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ChangeHeader("Township", btntownship, btnpaytype);
                    }
                });
//
                btncustomer.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
//                        Toast.makeText(sale_entry.this,"this is customer ",Toast.LENGTH_LONG).show();
                        ChangeHeader("Customer", btncustomer, btnpaytype);
                    }
                });
                btnpaytype = view.findViewById(R.id.btnpaytype);
                btnpaytype.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ChangeHeader("Payment Type", btnpaytype, btnpaytype);

                    }
                });

                Button btnlocation = view.findViewById(R.id.location);
//
                btnlocation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ChangeHeader("Location", btnlocation, btnpaytype);

                    }
                });
//
                //added by EKK on 16-11-2020
                Button btnCashIn = view.findViewById(R.id.cashin);
                btnCashIn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ChangeHeader("Cash In", btnCashIn, btnpaytype);

                    }
                });

//
                btnsave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

//                        Toast.makeText(sale_entry.this,sh.size()+"u click me!",Toast.LENGTH_LONG).show();
                        invoice_no = txtinvoiceNo.getText().toString();/*.toString().trim().isEmpty()?"NULL":txtinvoiceNo.getText().toString().trim();*/
//                        Toast.makeText(sale_entry.this,sh.size()+"u click me!"+invoice_no,Toast.LENGTH_LONG).show();
                        sh.get(0).setInvoice_no(invoice_no);
                        headRemark = headremark.getText().toString().trim().isEmpty() ? null : headremark.getText().toString().trim();
                        sh.get(0).setHeadremark(headRemark);
                        defloc = 1;
                        //Added for credit limit customer
                        String sqlString = "select customerid,iscredit,creditlimit from Customer where customerid=" + sh.get(0).getCustomerid();//,credit,due_in_days,credit_limit
                        Cursor cursor = DatabaseHelper.rawQuery(sqlString);
                        if (cursor != null && cursor.getCount() != 0) {
                            if (cursor.moveToFirst()) {
                                do {
                                    long customerid = cursor.getLong(cursor.getColumnIndex("customerid"));
//                                String customername = cursor.getString(cursor.getColumnIndex("name"));
//                    String customercode = cursor.getString(cursor.getColumnIndex("customer_code"));
                                    boolean credit = cursor.getInt(cursor.getColumnIndex("iscredit")) == 1 ? true : false;
//                    int due_in_days = cursor.getInt(cursor.getColumnIndex("due_in_days"));
//                    double credit_limit=cursor.getDouble(cursor.getColumnIndex("credit_limit"));
//                    btn.get(2).setText(customerid+":"+customername);
                                    sale_entry.isCreditcustomer = credit;
                                    sale_entry.credit_limit = cursor.getDouble(cursor.getColumnIndex("creditlimit"));
                                } while (cursor.moveToNext());

                            }

                        }
                        cursor.close();
                        //Added for credit limit customer

                        //Added for Outstand
                        if (isCreditcustomer /*&& sh.get(0).getPay_type() == 2*/) {
                            bindingCreditBalance();
//                            GetCustomerOutstand(sh.get(0).getCustomerid());
                        } else {
                            txtoutstand.setText("0");
                        }
                        dialog.dismiss();
                        //Added for Outstand
                    }
                });
                ArrayList<String> id = new ArrayList<>();
                ArrayList<Button> btn = new ArrayList<>();

                btn.add(btncustgroup);
                btn.add(btntownship);
                btn.add(btncustomer);
                btn.add(btnlocation);
                btn.add(btnpaytype);
                btn.add(btnCashIn); //added by EKK on 16-11-2020


                id.add(String.valueOf(sh.get(0).getCustomerid()));
                id.add(String.valueOf(sh.get(0).getLocationid()));
                id.add(String.valueOf(sh.get(0).getPay_type()));
                id.add(String.valueOf(sh.get(0).getDef_cashid())); //added by EKK on 16-11-2020


                InitializeHeader(id, btn);

                dialog = builder.create();
                dialog.show();


                break;
//not change_date in sale entry txtdate modified by ABBP
            case R.id.txtdate:

                if (frmlogin.canchangedate == 0) {
                    Toast.makeText(getApplicationContext(), "You have no permission to change date!", Toast.LENGTH_SHORT).show();
                    txtdate.setEnabled(false);
                } else {
                    ChangeVouDate();
                }
                break;

        }

    }

    private void CodeFind() {
        AlertDialog.Builder searchBuilder = new AlertDialog.Builder(sale_entry.this, R.style.AlertDialogTheme);
        View view = getLayoutInflater().inflate(R.layout.codefind, null);
        searchBuilder.setView(view);
        Dialog dialog = searchBuilder.create();
        dialog.show();
        dialog.getWindow().setGravity(Gravity.RIGHT);
        ListView codefindListView = dialog.findViewById(R.id.codefindlist);
        EditText txtcodeSearch = dialog.findViewById(R.id.txtcodeSearch);
        EditText txtdesSearch = dialog.findViewById(R.id.txtdesSearch);
        final List<usr_code> codes = GetCodes();
        final List<usr_code> tmpCodes = new ArrayList<>();
        tmpCodes.addAll(codes);
//        String formname="sale_entry";
        CodeFindListViewAdapter codeFindListViewAdapter = new CodeFindListViewAdapter(sale_entry.this, R.layout.codefinditemlist, codes/*,"sale_entry"*/);
        codefindListView.setAdapter(codeFindListViewAdapter);

        txtcodeSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                tmpCodes.clear();
                String searchText = charSequence.toString().replaceAll("%", "").toLowerCase().trim();
                int textlength = searchText.length();
                if (charSequence.toString().startsWith("%") && charSequence.toString().endsWith("%")) {

                    for (usr_code item : codes) {
                        if (textlength <= item.getUsr_code().length()) {
                            if (item.getUsr_code().toLowerCase().contains(searchText) &&
                                    !item.getUsr_code().toLowerCase().startsWith(searchText) &&
                                    !item.getUsr_code().toLowerCase().endsWith(searchText)) {
                                tmpCodes.add(item);
                            }
                        }
                    }
                } else if (charSequence.toString().startsWith("%")) {

                    for (usr_code item : codes) {
                        if (textlength <= item.getUsr_code().length()) {
                            if (item.getUsr_code().toLowerCase().endsWith(searchText)) {
                                tmpCodes.add(item);
                            }
                        }
                    }
                } else {

                    for (usr_code item : codes) {
                        if (textlength <= item.getUsr_code().length()) {
                            if (item.getUsr_code().toLowerCase().startsWith(searchText)) {
                                tmpCodes.add(item);
                            }
                        }
                    }

                }
                CodeFindListViewAdapter codeFindListViewAdapter = new CodeFindListViewAdapter(sale_entry.this, R.layout.codefinditemlist, tmpCodes/*,formname*/);
                codefindListView.setAdapter(codeFindListViewAdapter);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txtdesSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                tmpCodes.clear();
                String searchText = charSequence.toString().replaceAll("%", "").toLowerCase().trim();
                int textlength = searchText.length();

                if (!charSequence.toString().contains("%")) {

                    for (usr_code item : codes) {
                        if (textlength <= item.getDescription().length()) { //modified by MPPA [07-05-2021]
                            if (item.getDescription().toLowerCase().contains(searchText)) {
                                tmpCodes.add(item);
                            }
                        }
                    }
                } else if (charSequence.toString().startsWith("%") && charSequence.toString().endsWith("%")) {

                    for (usr_code item : codes) {
                        if (textlength <= item.getDescription().length()) { //modified by MPPA [07-05-2021]
                            if (item.getDescription().toLowerCase().contains(searchText) &&
                                    !item.getDescription().toLowerCase().startsWith(searchText) &&
                                    !item.getDescription().toLowerCase().endsWith(searchText)) {
                                tmpCodes.add(item);
                            }
                        }
                    }
                } else if (charSequence.toString().startsWith("%")) {

                    for (usr_code item : codes) {
                        if (textlength <= item.getDescription().length()) { //modified by MPPA [07-05-2021]
                            if (item.getDescription().toLowerCase().endsWith(searchText)) {
                                tmpCodes.add(item);
                            }
                        }
                    }
                } else if (charSequence.toString().endsWith("%")) {

                    for (usr_code item : codes) {
                        if (textlength <= item.getDescription().length()) { //modified by MPPA [07-05-2021]
                            if (item.getDescription().toLowerCase().startsWith(searchText)) {
                                tmpCodes.add(item);
                            }
                        }
                    }
                }
                CodeFindListViewAdapter codeFindListViewAdapter = new CodeFindListViewAdapter(sale_entry.this, R.layout.codefinditemlist, tmpCodes/*,formname*/);
                codefindListView.setAdapter(codeFindListViewAdapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        codefindListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int defunit = 1;
                defunit = frmmain.defunit;
                int[] ut = new int[3];
                int i = 0;
                Cursor cc = DatabaseHelper.rawQuery("select unit_type from Usr_Code where usr_code='" + tmpCodes.get(position).getUsr_code() + "'");
                if (cc != null && cc.getCount() != 0) {
                    if (cc.moveToFirst()) {
                        do {
                            int utt = cc.getInt(cc.getColumnIndex("unit_type"));
                            ut[i] = utt;
                            i++;
                        } while (cc.moveToNext());
                    }
                }
                if (i > 1) {

                    if (i == 3) {
                        specialPrice = usrcodeAdapter.GetPriceLevel();
                        String sale_price = specialPrice == 0 ? "uc.sale_price" : "uc.sale_price" + specialPrice;
                        String SP = specialPrice == 0 ? "SP" : "SP" + specialPrice;
                        String sqlString = "select uc.unit_type,code,description," + sale_price + ",open_price,smallest_unit_qty,unitname,unitshort,CalNoTax from Usr_Code uc " +
                                " where uc.unit_type=" + defunit + " and uc.usr_code='" + tmpCodes.get(position).getUsr_code() + "'";
                        Cursor cursor = DatabaseHelper.rawQuery(sqlString);
                        if (cursor != null && cursor.getCount() != 0) {
                            if (cursor.moveToFirst()) {
                                do {

                                    long code = cursor.getLong(cursor.getColumnIndex("code"));
                                    double price = cursor.getDouble(cursor.getColumnIndex(sale_price));
                                    int open_price = cursor.getInt(cursor.getColumnIndex("open_price"));
                                    double smallest_unit_qty = cursor.getDouble(cursor.getColumnIndex("smallest_unit_qty"));
                                    int unit_type = cursor.getInt(cursor.getColumnIndex("unit_type"));
                                    String unit_short = cursor.getString(cursor.getColumnIndex("unitshort")).equals("null") ? "" : cursor.getString(cursor.getColumnIndex("unitshort"));
                                    String unitname = cursor.getString(cursor.getColumnIndex("unitname"));
                                    String desc = cursor.getString(cursor.getColumnIndex("description"));
                                    int CalNoTax = cursor.getInt(cursor.getColumnIndex("CalNoTax"));
                                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                                    sd.add(new sale_det(
                                            tranid,
                                            sd.size() + 1,
                                            df.format(new Date()),
                                            1,
                                            open_price,
                                            smallest_unit_qty,
                                            unit_type,
                                            price,
                                            price,
                                            0,
                                            0,
                                            "",
                                            code, unit_short, desc, CalNoTax, SP, smallest_unit_qty));
                                } while (cursor.moveToNext());
                            }

                        }
                        cursor.close();
                        itemAdapter.notifyDataSetChanged();
                        entrygrid.setSelection(sale_entry.sd.size());
                        getSummary();
                    } else {
                        int utt = 2;
                        if (defunit == 2 || defunit == 3) {
                            utt = 2;
                        } else if (defunit == 1) {
                            utt = 1;
                        }
                        specialPrice = usrcodeAdapter.GetPriceLevel();
                        String sale_price = specialPrice == 0 ? "uc.sale_price" : "uc.sale_price" + specialPrice;
                        String SP = specialPrice == 0 ? "SP" : "SP" + specialPrice;
                        String sqlString = "select uc.unit_type,code,description," + sale_price + ",open_price,smallest_unit_qty,unitname,unitshort,CalNoTax from Usr_Code uc " +
                                " where uc.unit_type=" + utt + " and uc.usr_code='" + tmpCodes.get(position).getUsr_code() + "'";
                        Cursor cursor = DatabaseHelper.rawQuery(sqlString);
                        if (cursor != null && cursor.getCount() != 0) {
                            if (cursor.moveToFirst()) {
                                do {

                                    long code = cursor.getLong(cursor.getColumnIndex("code"));
                                    double price = cursor.getDouble(cursor.getColumnIndex(sale_price));
                                    int open_price = cursor.getInt(cursor.getColumnIndex("open_price"));
                                    double smallest_unit_qty = cursor.getDouble(cursor.getColumnIndex("smallest_unit_qty"));
                                    int unit_type = cursor.getInt(cursor.getColumnIndex("unit_type"));
                                    String unit_short = cursor.getString(cursor.getColumnIndex("unitshort")).equals("null") ? "" : cursor.getString(cursor.getColumnIndex("unitshort"));
                                    String unitname = cursor.getString(cursor.getColumnIndex("unitname"));
                                    String desc = cursor.getString(cursor.getColumnIndex("description"));
                                    int CalNoTax = cursor.getInt(cursor.getColumnIndex("CalNoTax"));
                                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                                    sd.add(new sale_det(
                                            tranid,
                                            sd.size() + 1,
                                            df.format(new Date()),
                                            1,
                                            open_price,
                                            smallest_unit_qty,
                                            unit_type,
                                            price,
                                            price,
                                            0,
                                            0,
                                            "",
                                            code, unit_short, desc, CalNoTax, SP, smallest_unit_qty));
                                } while (cursor.moveToNext());
                            }

                        }
                        cursor.close();
                        itemAdapter.notifyDataSetChanged();
                        entrygrid.setSelection(sale_entry.sd.size());
                        getSummary();
                    }

                } else if (i == 1 || defunit == 1) {

                    specialPrice = usrcodeAdapter.GetPriceLevel();
                    String sale_price = specialPrice == 0 ? "uc.sale_price" : "uc.sale_price" + specialPrice;
                    String SP = specialPrice == 0 ? "SP" : "SP" + specialPrice;
                    String sqlString = "select uc.unit_type,code,description," + sale_price + ",open_price,smallest_unit_qty,unitname,unitshort,CalNoTax from Usr_Code uc " +
                            " where uc.unit_type=1 and uc.usr_code='" + tmpCodes.get(position).getUsr_code() + "'";
                    Cursor cursor = DatabaseHelper.rawQuery(sqlString);
                    if (cursor != null && cursor.getCount() != 0) {
                        if (cursor.moveToFirst()) {
                            do {

                                long code = cursor.getLong(cursor.getColumnIndex("code"));
                                double price = cursor.getDouble(cursor.getColumnIndex(sale_price));
                                int open_price = cursor.getInt(cursor.getColumnIndex("open_price"));
                                double smallest_unit_qty = cursor.getDouble(cursor.getColumnIndex("smallest_unit_qty"));
                                int unit_type = cursor.getInt(cursor.getColumnIndex("unit_type"));
                                String unit_short = cursor.getString(cursor.getColumnIndex("unitshort")).equals("null") ? "" : cursor.getString(cursor.getColumnIndex("unitshort"));
                                String unitname = cursor.getString(cursor.getColumnIndex("unitname"));
                                String desc = cursor.getString(cursor.getColumnIndex("description"));
                                int CalNoTax = cursor.getInt(cursor.getColumnIndex("CalNoTax"));
                                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                                sd.add(new sale_det(
                                        tranid,
                                        sd.size() + 1,
                                        df.format(new Date()),
                                        1,
                                        open_price,
                                        smallest_unit_qty,
                                        unit_type,
                                        price,
                                        price,
                                        0,
                                        0,
                                        "",
                                        code, unit_short, desc, CalNoTax, SP, smallest_unit_qty));
                            } while (cursor.moveToNext());
                        }

                    }
                    cursor.close();
                    itemAdapter.notifyDataSetChanged();
                    entrygrid.setSelection(sale_entry.sd.size());
                    getSummary();
                }


            }
        });
        Button btnClose = (Button) dialog.findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private List<usr_code> GetCodes() {
        List<usr_code> codes = new ArrayList<>();
        Cursor cursor = DatabaseHelper.rawQuery("select code,usr_code,description,sale_price from Usr_Code where unit_type=1 order by sortcode,categoryname");
        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    int code = cursor.getInt(cursor.getColumnIndex("code"));
                    String usr_code = cursor.getString(cursor.getColumnIndex("usr_code"));
                    String description = cursor.getString(cursor.getColumnIndex("description"));
                    double sale_price = cursor.getDouble(cursor.getColumnIndex("sale_price"));
                    codes.add(new usr_code(code, usr_code, description, sale_price));

                } while (cursor.moveToNext());

            }

        }
        cursor.close();
        return codes;
    }

    private void voucherConfirmtoCloud() {
        sale_head_tmps.clear();
        sale_det_tmps.clear();
//        getHeader();
//        String head="";

//        Cursor cursor = DatabaseHelper.DistinctSelectQuerySelectionWithCondition("Usr_Code", new String[]{"category", "categoryname", "class"}, "class=?", new String[]{String.valueOf(data.get(position).getClassid())}, "sortcode,categoryname");

        Cursor cursor = DatabaseHelper.DistinctSelectQuerySelectionWithCondition("sale_head_main", new String[]{"tranid", "docid", "date", "invoiceno", "locationid", "customerid", "cashid", "townshipid", "paytypeid", "dueindays", "salecurr", "discountamount", "paidamount", "invoiceamount", "invoiceqty", "focamount", "netamount", "voucherremark", "taxamount", "taxpercent", "discountpercent", "exgrate", "userid", "isdeliver", "salesmenids", "paidpercent"}, "uploaded=?", new String[]{"0"}, "");
        if (cursor != null && cursor.getCount() != 0) {
            System.out.println(cursor.getCount());
            if (cursor.moveToFirst()) {
                do {
                    //getTranID();
//                    int tranidi = 0;
                    boolean isdeliver = false;
                    isdeliver = cursor.getInt(cursor.getColumnIndex("isdeliver")) == 1 ? true : false;
                    String offlineTranid = UUID.randomUUID().toString();
                    sale_head_tmps.add(new sale_head_tmp(offlineTranid, //tranidi = cursor.getInt(cursor.getColumnIndex("tranid")),
                            cursor.getInt(cursor.getColumnIndex("userid"))
                            , cursor.getString(cursor.getColumnIndex("docid"))
                            , cursor.getString(cursor.getColumnIndex("date"))
                            , cursor.getString(cursor.getColumnIndex("invoiceno"))
                            , cursor.getInt(cursor.getColumnIndex("locationid"))
                            , cursor.getInt(cursor.getColumnIndex("customerid"))
                            , cursor.getInt(cursor.getColumnIndex("cashid"))
                            , cursor.getInt(cursor.getColumnIndex("townshipid"))
                            , cursor.getInt(cursor.getColumnIndex("paytypeid"))
                            , cursor.getInt(cursor.getColumnIndex("dueindays"))
                            , cursor.getInt(cursor.getColumnIndex("salecurr"))
                            , cursor.getDouble(cursor.getColumnIndex("discountamount"))
                            , cursor.getDouble(cursor.getColumnIndex("paidamount"))
                            , cursor.getDouble(cursor.getColumnIndex("invoiceamount"))
                            , cursor.getDouble(cursor.getColumnIndex("invoiceqty"))
                            , cursor.getDouble(cursor.getColumnIndex("focamount"))
                            , cursor.getDouble(cursor.getColumnIndex("netamount"))
                            , cursor.getString(cursor.getColumnIndex("voucherremark"))
                            , cursor.getDouble(cursor.getColumnIndex("taxamount"))
                            , cursor.getDouble(cursor.getColumnIndex("taxpercent"))
                            , cursor.getDouble(cursor.getColumnIndex("discountpercent"))
                            , cursor.getDouble(cursor.getColumnIndex("exgrate"))
                            , false, tranidi = cursor.getInt(cursor.getColumnIndex("tranid"))
                            , cursor.getInt(cursor.getColumnIndex("isdeliver")) == 1
                            , cursor.getString(cursor.getColumnIndex("salesmenids"))/*==0?(Integer)null:cursor.getInt(cursor.getColumnIndex("salesmenid"))*/
                            , cursor.getDouble(cursor.getColumnIndex("paidpercent"))
                    ));

//                            String head = "update sale_head_tmp set " +
//                                    "tranid="+tranidfromcloud+"," +
//                                    "docid='"+cursor.getString(cursor.getColumnIndex("docid"))+"'," +
//                                    "date=" +"'" +cursor.getString(cursor.getColumnIndex("date"))+ "'," +
//                                    "invoiceno="+cursor.getString(cursor.getColumnIndex("invoiceno"))+"," +
//                                    "locationid="+cursor.getInt(cursor.getColumnIndex("locationid"))+"," +
//                                    "customerid="+cursor.getInt(cursor.getColumnIndex("customerid"))+"," +
//                                    "cashid="+cursor.getInt(cursor.getColumnIndex("cashid"))+"," +
//                                    "townshipid="+cursor.getInt(cursor.getColumnIndex("townshipid"))+","+
//                                    "paytypeid="+cursor.getInt(cursor.getColumnIndex("paytypeid"))+"," +
//                                    "dueindays="+cursor.getInt(cursor.getColumnIndex("dueindays"))+"," +
//                                    "salecurr="+cursor.getInt(cursor.getColumnIndex("salecurr"))+"," +
//                                    "discountamount="+cursor.getDouble(cursor.getColumnIndex("discountamount"))+"," +
//                                    "paidamount="+cursor.getDouble(cursor.getColumnIndex("paidamount"))+"," +
//                                    "invoiceamount="+cursor.getDouble(cursor.getColumnIndex("invoiceamount"))+"," +
//                                    "invoiceqty="+cursor.getDouble(cursor.getColumnIndex("invoiceqty"))+"," +
//                                    "focamount="+cursor.getDouble(cursor.getColumnIndex("focamount"))+"," +
//                                    //"itemdis_amount="+sh.get(0).getIstemdis_amount()+",\n" +
//                                    "netamount="+cursor.getDouble(cursor.getColumnIndex("netamount"))+"," +
//                                    "voucherremark="+cursor.getString(cursor.getColumnIndex("voucherremark"))+","+
//                                    "taxamount="+cursor.getDouble(cursor.getColumnIndex("taxamount"))+"," +
//                                    "taxpercent="+cursor.getDouble(cursor.getColumnIndex("taxpercent"))+"," +
//                                    "discountpercent="+cursor.getDouble(cursor.getColumnIndex("discountpercent"))+","+
//                                    "exgrate="+cursor.getDouble(cursor.getColumnIndex("exgrate"))+
//                                    " where tranid="+tranidfromcloud;
//                            String stranid=cursor.getString(cursor.getColumnIndex("tranid"));
//                            String det = ";delete from sale_det_tmp where tranid="+tranidfromcloud+
//                                    ";insert into sale_det_tmp(tranid,unitqty,qty,saleprice,discountamount,itemdiscounttypeid,discountpercent,remark,unittypeid,code,sr,srno) values ";

                    Cursor cursor1 = DatabaseHelper.rawQuery("select * from sale_det where tranid=" + tranidi + "");
                    if (cursor1 != null && cursor1.getCount() != 0) {
//                                int t=0;
                        while (cursor1.moveToNext()) {

                            sale_det_tmps.add(new sale_det_tmp(offlineTranid,//cursor1.getInt(cursor1.getColumnIndex("tranid")),
                                    cursor1.getDouble(cursor1.getColumnIndex("unitqty")),
                                    cursor1.getDouble(cursor1.getColumnIndex("qty")),
                                    cursor1.getDouble(cursor1.getColumnIndex("saleprice")),
                                    cursor1.getDouble(cursor1.getColumnIndex("discountamount")),
                                    cursor1.getInt(cursor1.getColumnIndex("itemdiscounttypeid")),
                                    cursor1.getDouble(cursor1.getColumnIndex("discountpercent")),
                                    cursor1.getString(cursor1.getColumnIndex("remark")),
                                    cursor1.getInt(cursor1.getColumnIndex("unittypeid")),
                                    cursor1.getInt(cursor1.getColumnIndex("code")),
                                    cursor1.getInt(cursor1.getColumnIndex("sr")),
                                    +cursor1.getInt(cursor1.getColumnIndex("srno")), tranidi,
                                    cursor1.getShort(cursor1.getColumnIndex("pricelevelid")), false,
                                    sale_head_tmps.get(0).getLocationid()));

//                                    det = det + "(" +
//                                            tranidfromcloud + "," +
//
//                                            cursor1.getDouble(cursor1.getColumnIndex("unitqty") )+ "," +
//                                            cursor1.getDouble(cursor1.getColumnIndex("qty")) + "," +
//                                            cursor1.getDouble(cursor1.getColumnIndex("saleprice")) + "," +
//                                            cursor1.getDouble(cursor1.getColumnIndex("discountamount"))+","+
//                                            cursor1.getInt(cursor1.getColumnIndex("itemdiscounttypeid"))+ "," +
//                                            cursor1.getDouble(cursor1.getColumnIndex("discountpercent"))+","+
//                                            cursor1.getString(cursor1.getColumnIndex("remark"))+ "," +
//                                            cursor1.getInt(cursor1.getColumnIndex("unittypeid")) + "," +
//                                            cursor1.getInt(cursor1.getColumnIndex("code")) + "," +
//                                            cursor1.getInt(cursor1.getColumnIndex("sr"))+","
//                                            + cursor1.getInt(cursor1.getColumnIndex("srno"));
//                                    if (t<cursor1.getCount()-1)
//                                           det=det +" ),";
//                                    else det=det+")";
//                                    t++;
                        }
                    }

//                            System.out.println(gson.toJson(sale_det_tmps));

//                            String salechange=";delete from salechange_history where tranid="+tranidfromcloud+
//                                    "; insert into salechange_history(tranid,currencyid,paidamount,changeamount,exgrate,invoiceamount) values ";
//
////                            salechange=salechange + "(" +sh.get(0).getTranid()+","+1+","+paid+","+changeamount+","+1+","+sh.get(0).getInvoice_amount()+");";
//                            Cursor cursor2=DatabaseHelper.rawQuery("select * from salechange_history where tranid='"+stranid+"'");
//                            if (cursor2 != null && cursor2.getCount() != 0) {
//                                while (cursor2.moveToNext()) {
//                                    salechange=salechange + "("
//                                            +tranidfromcloud
//                                 k           +","+cursor2.getInt(cursor2.getColumnIndex("currencyid"))+","
//                                            +cursor2.getInt(cursor2.getColumnIndex("paidamount")) +","
//                                            +cursor2.getInt(cursor2.getColumnIndex("changeamount"))+","
//                                            +cursor2.getInt(cursor2.getColumnIndex("exgrate"))+","
//                                            +cursor2.getInt(cursor2.getColumnIndex("invoiceamount"))+");";
//                                }
//                            }
//                            sqlstring = head +" "+salechange+" "+det;
//                            Confirm();
//                            Toast.makeText(this,gson.toJson(sale_det_tmps),Toast.LENGTH_LONG).show();


//                            DatabaseHelper.execute("update sale_head_main set uploaded='1' "+"where tranid="+tranidi+"");
//                            System.out.println(cursor3.getCount());


////                            cursor.getColumnName();
//                            String stringtranid = cursor.getString(cursor.getColumnIndex("docid"));
//                            String custgroupname = cursor.getString(cursor.getColumnIndex("date"));
//                            String shortname = cursor.getString(cursor.getColumnIndex("invoiceno"));
//                            ContentValues contentValues=new ContentValues();
////                            contentValues.put("tranid",);
//                            contentValues.put("docid",cursor.getString(cursor.getColumnIndex("docid")));
//                            contentValues.put("date",cursor.getString(cursor.getColumnIndex("date")));
//                            contentValues.put("invoiceno",cursor.getString(cursor.getColumnIndex("invoiceno")));
//                            contentValues.put("locationid",cursor.getInt(cursor.getColumnIndex("locationid")));
//                            contentValues.put("customerid",cursor.getInt(cursor.getColumnIndex("customerid")));
//                            contentValues.put("cashid",cursor.getInt(cursor.getColumnIndex("cashid")));
//                            contentValues.put("townshipid",cursor.getInt(cursor.getColumnIndex("townshipid")));
//                            contentValues.put("paytypeid",cursor.getInt(cursor.getColumnIndex("paytypeid")));
//                            contentValues.put("dueindays",cursor.getInt(cursor.getColumnIndex("dueindays")));
//                            contentValues.put("salecurr",cursor.getInt(cursor.getColumnIndex("salecurr")));
//                            contentValues.put("discountamount",cursor.getDouble(cursor.getColumnIndex("discountamount")));
//                            contentValues.put("paidamount",cursor.getDouble(cursor.getColumnIndex("paidamount")));
//                            contentValues.put("invoiceamount",cursor.getDouble(cursor.getColumnIndex("invoiceamount")));
//                            contentValues.put("invoiceqty",cursor.getDouble(cursor.getColumnIndex("invoiceqty")));
//                            contentValues.put("focamount",cursor.getDouble(cursor.getColumnIndex("focamount")));
//                            contentValues.put("netamount",cursor.getDouble(cursor.getColumnIndex("netamount")));
//                            contentValues.put("voucherremark",cursor.getString(cursor.getColumnIndex("voucherremark")));
//                            contentValues.put("taxamount",cursor.getDouble(cursor.getColumnIndex("taxamount")));
//                            contentValues.put("taxpercent",cursor.getDouble(cursor.getColumnIndex("taxpercent")));
//                            contentValues.put("discountpercent",cursor.getDouble(cursor.getColumnIndex("discountpercent")));
//                            contentValues.put("exgrate",cursor.getDouble(cursor.getColumnIndex("exgrate")));
//                            contentValues.put("userid",cursor.getInt(cursor.getColumnIndex("userid")));
//
////                            cg.add(new customergroup(custgroupid, custgroupname, shortname));
                } while (cursor.moveToNext());

            }
            UploadtoCloud();
        } else {
            Toast.makeText(getApplicationContext(), "No data to upload cloud", Toast.LENGTH_LONG).show();
        }

    }

    private void UploadtoCloud() {

        String formname = "sale_entry";
        String ip = sh_ip.getString("ip", "empty");
        String port = sh_port.getString("port", "empty");
        //String sqlUrl="http://" + ip + ":" + port + "/api/DataSync/SaveData";
        String sqlUrl = "http://" + ip + ":" + port + "/api/mobile/UploadData";
//        System.out.println(sh.get(0).setTranid(););
        sqlstring = "sale_head_tmp" + "&" + gson.toJson(sale_head_tmps).toString()/*sh.get(0).getTranid()*/ + "&" + "sale_det_tmp" + "&" + gson.toJson(sale_det_tmps).toString() + "&" + "&" + gson.toJson(sale_det_tmps) + "&" + "usp_saveofflinevoucher&&" + tranidi;
        Log.i("sale_entry", sqlstring);
        new SaveData().execute(sqlUrl, "cloud");
    }

    private void getTranID() {

        try {
//            int tranidint=0;
            String entryformname = "saleentry";
            String ip = sh_ip.getString("ip", "empty");
            String port = sh_port.getString("port", "empty");
            url = "http://" + ip + ":" + port + "/api/mobile/GetVoucher?userid=" + frmlogin.LoginUserid + "&entryformname=" + entryformname;
            new UploadData().execute(url);
//            requestQueue = Volley.newRequestQueue(this);
//            final Response.Listener<String> listener = new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//
//                    try {
//
//                        JSONArray jarr = new JSONArray(response);
//                        if (sh.size() > 0) sh.clear();
//                        for (int i = 0; i < jarr.length(); i++) {
//                            JSONObject obj = jarr.getJSONObject(i);
//                            tranidfromcloud=obj.getLong("tranid");
//                            String docid=obj.getString("docid");
//                            String date=obj.getString("date");
//                            long locationid=obj.getLong("locationid");
//                            long customerid=obj.getLong("customerid");
//                            double tax_per=getTax();
//                            //sh.add(new Sale_head_main(tranid, frmlogin.LoginUserid,  "VOU-1",  date,  "",  "",  1,  1,   1, 0,  1,  0,  0,  0,  0,  0,  0,  0,  0,0));
////                            sh.add(new Sale_head_main( tranid,  frmlogin.LoginUserid,  "VOU-1",  date,  "",  "",  frmlogin.det_locationid,  1, frmlogin.def_cashid,  1, 0,  1,  0,  0,  0,  0,  0,  0,  0,  0,  0));
//                        }
//                        //InsertheadMain();
//
////                        txtdocid.setText(sh.get(0).getDocid());
////                        try {
////                            String voudate=new SimpleDateFormat("dd/MM/yyyy").format(dateFormat.parse(sh.get(0).getDate()));
////                            txtdate.setText(voudate);
////                        } catch (ParseException e) {
////                            e.printStackTrace();
////                        }
//
//
//                    } catch (JSONException e) {
//
//                    }
//                }
//
//
//            };
//
//            final Response.ErrorListener error = new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//
//                        System.out.println("Error");
//                }
//            };
//            StringRequest req = new StringRequest(Request.Method.GET, url, listener, error);
//            requestQueue.add(req);


        } catch (Exception ee) {
            Toast.makeText(sale_entry.this, ee.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    private void voucherConfirmtoLiteDB() {

        paidamount = 0.0;
        changeamount = 0.0;

        if (sh.get(0).getPay_type() == 1) {
            AlertDialog.Builder change = new AlertDialog.Builder(sale_entry.this);
            change.setCancelable(false);
            View v = getLayoutInflater().inflate(R.layout.savechange, null);
            ImageButton imgSave = v.findViewById(R.id.imgSave);
            ImageButton imgClose = v.findViewById(R.id.imgClose);
            tvAmount = v.findViewById(R.id.txtAmount);
            tvAmount.setText(txtnet.getText().toString());
//            tvAmount.setText(String.format("%,."+txtnet.getText().toString()+"f"));
            tvChange = v.findViewById(R.id.txtChange);
            TextView tvPaid = v.findViewById(R.id.txtpaidAmount);
            CheckBox chkPrint = v.findViewById(R.id.chkPrint);
            chkPrint.setChecked(false);
            bill_not_print = false;
            chkPrint.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    bill_not_print = isChecked;
                }
            });

            tvBillCount = v.findViewById(R.id.billcount);
            tvBillCount.setText(String.valueOf(billprintcount));
            RelativeLayout rlBillCount = v.findViewById(R.id.rlBillCount);
            TextView txtAmounttext = v.findViewById(R.id.txtAmounttext);
            tvBillCount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    frombillcount = true;
                    keynum = tvBillCount.getText().toString();
                    showKeyPad(txtAmounttext, tvBillCount);


                }
            });
            rlBillCount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    frombillcount = true;
                    keynum = tvBillCount.getText().toString();
                    showKeyPad(txtAmounttext, tvBillCount);
                }
            });


            RelativeLayout rlPaid = v.findViewById(R.id.rlPaid);
            rlPaid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    keynum = tvPaid.getText().toString();
                    fromSaleChange = true;
                    showKeyPad(tvAmount, tvPaid);


                }
            });
            tvPaid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    keynum = tvPaid.getText().toString();
                    fromSaleChange = true;
                    showKeyPad(tvAmount, tvPaid);
                }
            });

            imgSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(sale_entry.this,"u click save!",Toast.LENGTH_LONG).show();
                    salechange.dismiss();
                    if (tvPaid.getText().toString().trim().isEmpty()) {
                        paidamount = 0;
                    } else {
                        paidamount = Double.parseDouble(tvPaid.getText().toString().trim());
                    }
                    if (tvChange.getText().toString().trim().isEmpty()) {
                        changeamount = 0;
                    } else {
                        changeamount = Double.parseDouble(tvChange.getText().toString().trim());
                    }
//                    updateVoucher();
                    insertdatatoLiteDb();
                    ConfirmedTranid = Long.parseLong("0");
                }
            });
            imgClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    comfirm = false;
                    salechange.dismiss();
                }
            });
            change.setView(v);
            salechange = change.create();
            salechange.show();
        } else {

            //This Customer's Credit Limit is Over.Do you want to continue ???
            double outstandamt = net_amount + Double.parseDouble(ClearFormat(txtoutstand.getText().toString()));
            System.out.println(sale_entry.credit_limit + " this is credit limit" + frmlogin.isallowovercreditlimit);
            if (outstandamt > sale_entry.credit_limit && sale_entry.credit_limit != 0) {
                if (frmlogin.isallowovercreditlimit == 1) {
                    AlertDialog.Builder bd = new AlertDialog.Builder(datacontext, R.style.AlertDialogTheme);
                    bd.setTitle("iStock");
                    bd.setMessage("This Customer's Credit Limit is Over.Do you want to continue ???");
                    bd.setCancelable(false);
                    bd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                            updateVoucher();
                            insertdatatoLiteDb();
                            ConfirmedTranid = Long.parseLong("0");
                            dialog.dismiss();
                        }
                    });

                    bd.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    bd.create().show();
                } else {
                    AlertDialog.Builder bd = new AlertDialog.Builder(datacontext, R.style.AlertDialogTheme);
                    bd.setTitle("iStock");
                    bd.setMessage("This Customer's Credit Limit is Over");
                    bd.setCancelable(false);
                    bd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    bd.create().show();
                }
            } else {
//                updateVoucher();
                insertdatatoLiteDb();
                ConfirmedTranid = Long.parseLong("0");
            }
        }

    }

    private void insertdatatoLiteDb() {
        int isdeliver = 0;
        try {
            if (tranidInt != 0) {

                if (sh.get(0).getInvoice_no().equals("NULL") || sh.get(0).getInvoice_no().equals("")) {
                    invoice_no = null;
                } else {
                    invoice_no = sh.get(0).getInvoice_no();

                }
                if (sh.get(0).getHeadremark() != null) {
                    if (sh.get(0).getHeadremark().equals("NULL") || sh.get(0).getHeadremark().equals("")) {
                        headRemark = "";
                    } else {
                        headRemark = /*"N'" + */sh.get(0).getHeadremark() /*+ "'"*/;

                    }
                }
                if (Use_Delivery) {
                    if (chkDeliver.isChecked()) {
                        ToDeliver = ",isdeliver=true";
                        isdeliver = 1;
                    } else {
                        ToDeliver = "";
                    }
                } else {
                    ToDeliver = "";
                }

//                String head = "update sale_head_tmp set " +
//                        "tranid="+sh.get(0).getTranid()+"," +
//                        "docid='"+sh.get(0).getDocid()+"'," +
//                        "date=" +"'" + String.format(sh.get(0).getDate(), "yyyy-MM-dd") + "'," +
//                        "invoiceno="+invoice_no+"," +
//                        "locationid="+sh.get(0).getLocationid()+"," +
//                        "customerid="+sh.get(0).getCustomerid()+"," +
//                        "cashid="+sh.get(0).getDef_cashid()+"," +
//                        "townshipid="+sh.get(0).getTownshipid()+","+
//                        "paytypeid="+sh.get(0).getPay_type()+"," +
//                        "dueindays="+sh.get(0).getDue_in_days()+"," +
//                        "salecurr=1," +
//                        "discountamount="+sh.get(0).getDiscount()+"," +
//                        "paidamount="+sh.get(0).getPaid_amount()+"," +
//                        "invoiceamount="+sh.get(0).getInvoice_amount()+"," +
//                        "invoiceqty="+sh.get(0).getInvoice_qty()+"," +
//                        "focamount="+sh.get(0).getFoc_amount()+"," +
//                        //"itemdis_amount="+sh.get(0).getIstemdis_amount()+",\n" +
//                        "netamount="+net_amount+"," +
//                        "voucherremark="+headRemark+","+
//                        "taxamount="+sh.get(0).getTax_amount()+"," +
//                        "taxpercent="+sh.get(0).getTax_per()+"," +
//                        "discountpercent="+sh.get(0).getDiscount_per()+","+
//                        "exgrate="+1+""+
//                        ToDeliver+
//                        " where tranid="+sd.get(0).getTranid();
//                String uuidstr= UUID.randomUUID().toString();
//                Toast.makeText(getApplicationContext(),uuidstr+"this is uuid",Toast.LENGTH_LONG).show();
                sh.get(0).setTranid(tranidInt);


                ContentValues contentValues = new ContentValues();
                contentValues.put("tranid", sh.get(0).getTranid());
                contentValues.put("userid", frmlogin.LoginUserid);
                contentValues.put("docid", sh.get(0).getDocid());
                contentValues.put("date", String.format(sh.get(0).getDate(), "yyyy-MM-dd"));
                contentValues.put("invoiceno", invoice_no);
                contentValues.put("locationid", sh.get(0).getLocationid());
                contentValues.put("customerid", sh.get(0).getCustomerid());
                contentValues.put("cashid", sh.get(0).getDef_cashid());
                contentValues.put("townshipid", sh.get(0).getTownshipid());
                contentValues.put("paytypeid", sh.get(0).getPay_type());
                contentValues.put("dueindays", sh.get(0).getDue_in_days());
                contentValues.put("salecurr", 1);
                contentValues.put("discountamount", sh.get(0).getDiscount());
                contentValues.put("paidamount", sh.get(0).getPaid_amount());
                contentValues.put("invoiceamount", sh.get(0).getInvoice_amount());
                contentValues.put("invoiceqty", sh.get(0).getInvoice_qty());
                contentValues.put("focamount", sh.get(0).getFoc_amount());
                contentValues.put("netamount", net_amount);
                contentValues.put("paidpercent", sh.get(0).getPaidpercent());
                if (headRemark != null) {
                    contentValues.put("voucherremark", headRemark + "offline" + String.format(sh.get(0).getDate(), "yyyyMMdd"));
                } else {
                    contentValues.put("voucherremark", "offline" + String.format(sh.get(0).getDate(), "yyyyMMdd"));
                }
                contentValues.put("taxamount", sh.get(0).getTax_amount());
                contentValues.put("taxpercent", sh.get(0).getTax_per());
                contentValues.put("discountpercent", sh.get(0).getDiscount_per());
                contentValues.put("exgrate", 1);
                contentValues.put("userid", frmlogin.LoginUserid);
                contentValues.put("uploaded", 0);
                contentValues.put("isdeliver", isdeliver);

                contentValues.put("salesmenids", GetSalesmenids());


                DatabaseHelper.insertWithOnConflict("sale_head_main", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
//                contentValues.put("",);

//                ContentValues contentValues1=new ContentValues();


                //String det = "delete from sale_det_tmp where tranid="+sh.get(0).getTranid()+
                //   " insert into sale_det_tmp(tranid,date,unit_qty,qty,sale_price,dis_price,dis_type,dis_percent,remark,unit_type,code,sr,srno,PriceLevel,SQTY,SPrice) values ";
                /*String det = ";delete from sale_det_tmp where tranid="+sh.get(0).getTranid()+
                        ";insert into sale_det_tmp(tranid,unitqty,qty,saleprice,discountamount,itemdiscounttypeid,discountpercent,remark,unittypeid,code,sr,srno) values ";

                String salechange=";delete from salechange_history where tranid="+sh.get(0).getTranid()+
                        "; insert into salechange_history(tranid,currencyid,paidamount,changeamount,exgrate,invoiceamount) values ";

                salechange=salechange + "(" +sh.get(0).getTranid()+","+1+","+paid+","+changeamount+","+1+","+sh.get(0).getInvoice_amount()+");";
*/


                ContentValues contentValues2 = new ContentValues();
                contentValues2.put("tranid", sh.get(0).getTranid());
                contentValues2.put("currencyid", 1);
                contentValues2.put("paidamount", paidamount);
                contentValues2.put("changeamount", changeamount);
                contentValues2.put("exgrate", 1);
                contentValues2.put("invoiceamount", sh.get(0).getInvoice_amount());
                DatabaseHelper.insertWithOnConflict("salechange_history", null, contentValues2, SQLiteDatabase.CONFLICT_REPLACE);


                for (int i = 0; i < sd.size(); i++) {
                    sd.get(i).setTranid(tranidInt);

                    if (sd.get(i).getDetremark().equals("NULL") || sd.get(i).getDetremark().equals("")) {
                        detRemark = null;
                    } else {
                        detRemark =/*"N'"+*/sd.get(i).getDetremark()/*+"'"*/;
                    }


                    if (i < (sd.size() - 1)) {
//                        det = det + "(" +
//                                sd.get(i).getTranid() + "," +
//                                sd.get(i).getUnit_qty() + "," +
//                                sd.get(i).getQty() + "," +
//                                sd.get(i).getSale_price() + "," +
//                                sd.get(i).getDis_price() + "," +
//                                sd.get(i).getDis_type()+","+
//                                sd.get(i).getDis_percent()+ "," +
//                                detRemark+","+
//                                sd.get(i).getUnt_type() + "," +
//                                sd.get(i).getCode() + "," +
//                                (i + 1) + "," +
//                                (i + 1) +" ),";
                        ContentValues contentValues1 = new ContentValues();
                        contentValues1.put("tranid", sd.get(i).getTranid());
                        contentValues1.put("unitqty", sd.get(i).getUnit_qty());
                        contentValues1.put("qty", sd.get(i).getQty());
                        contentValues1.put("saleprice", sd.get(i).getSale_price());
                        contentValues1.put("discountamount", sd.get(i).getDis_price());
                        contentValues1.put("itemdiscounttypeid", sd.get(i).getDis_type());
                        contentValues1.put("discountpercent", sd.get(i).getDis_percent());
                        contentValues1.put("remark", detRemark);
                        contentValues1.put("unittypeid", sd.get(i).getUnit_type());
                        contentValues1.put("code", sd.get(i).getCode());
                        contentValues1.put("sr", i + 1);
                        contentValues1.put("srno", i + 1);
                        contentValues1.put("pricelevelid", selectInsertLibrary.GettingPriceLevelId(sd.get(i).getPriceLevel()));
                        DatabaseHelper.insertWithOnConflict("sale_det", null, contentValues1, SQLiteDatabase.CONFLICT_REPLACE);

                    } else {

                        /*
                        det = det + "(" +
                                sd.get(i).getTranid() + "," +
                                "'" + String.format(sd.get(0).getDate(), "yyyy-MM-dd") + "'," +
                                sd.get(i).getUnit_qty() + "," +
                                sd.get(i).getQty() + "," +
                                sd.get(i).getSale_price() + "," +
                                sd.get(i).getDis_price() + "," +
                                sd.get(i).getDis_type()+","+
                                sd.get(i).getDis_percent()+ "," +
                                detRemark+","+
                                sd.get(i).getUnt_type() + "," +
                                sd.get(i).getCode() + "," +
                                (i + 1)+ "," +
                                (i + 1) + ",'"+
                                sd.get(i).getPriceLevel()+"',"+
                                getSmallestQty(sd.get(i).getCode(),sd.get(i).getUnit_qty(),sd.get(i).getUnt_type())+","+
                                getSPrice(sd.get(i).getCode()) +" )";


                        det = det + "(" +
                                sd.get(i).getTranid() + "," +
                                sd.get(i).getUnit_qty() + "," +
                                sd.get(i).getQty() + "," +
                                sd.get(i).getSale_price() + "," +
                                sd.get(i).getDis_price() + "," +
                                sd.get(i).getDis_type()+","+
                                sd.get(i).getDis_percent()+ "," +
                                detRemark+","+
                                sd.get(i).getUnt_type() + "," +
                                sd.get(i).getCode() + "," +
                                (i + 1) + "," +
                                (i + 1) +" )";
*/

                        ContentValues contentValues1 = new ContentValues();
                        contentValues1.put("tranid", sd.get(i).getTranid());
                        contentValues1.put("unitqty", sd.get(i).getUnit_qty());
                        contentValues1.put("qty", sd.get(i).getQty());
                        contentValues1.put("saleprice", sd.get(i).getSale_price());
                        contentValues1.put("discountamount", sd.get(i).getDis_price());
                        contentValues1.put("itemdiscounttypeid", sd.get(i).getDis_type());
                        contentValues1.put("discountpercent", sd.get(i).getDis_percent());
                        contentValues1.put("remark", detRemark);
                        contentValues1.put("unittypeid", sd.get(i).getUnit_type());
                        contentValues1.put("code", sd.get(i).getCode());
                        contentValues1.put("sr", i + 1);
                        contentValues1.put("srno", i + 1);
                        contentValues1.put("pricelevelid", selectInsertLibrary.GettingPriceLevelId(sd.get(i).getPriceLevel()));
                        DatabaseHelper.insertWithOnConflict("sale_det", null, contentValues1, SQLiteDatabase.CONFLICT_REPLACE);

                    }

                }
//                sh.get(0).getDiscount_per();
//                sh.get(0).setDiscount_per(0.0);
//                custDis=sh.get(0).getDiscount_per();
//                sqlstring = head +" "+salechange+" "+det;
//                if(use_salesperson && SaleVouSalesmen.size()>0)
//                {
//                    String salePerson=" delete from SalesVoucher_Salesmen_Tmp where Sales_TranID="+sh.get(0).getTranid()+" and userid="+frmlogin.LoginUserid+
//                            " insert into SalesVoucher_Salesmen_Tmp(Sales_TranID,Salesmen_ID,rmt_copy,userid)"+
//                            "values ";
//                    for(int i=0;i<SaleVouSalesmen.size();i++)
//                    {
//                        salePerson=salePerson+"("+
//                                sh.get(0).getTranid()+","+
//                                SaleVouSalesmen.get(i).getSalesmen_Id()+","+
//                                "1,"+frmlogin.LoginUserid+"),";
//                    }
//                    salePerson=salePerson.substring(0,salePerson.length()-1);
//                    sqlstring=sqlstring+" "+salePerson;
//                }

//                Confirm();
                AlertDialog.Builder bd = new AlertDialog.Builder(datacontext, R.style.WarningDialogTheme);
//                bd.setTitle("iStock");
                TextView title = new TextView(getApplicationContext());
                title.setBackgroundColor(Color.rgb(250, 104, 0));
                title.setPadding(30, 20, 10, 20);
                title.setTextSize(18F);
                title.setText("iStock (Offline Save!)");
                title.setTypeface(Typeface.DEFAULT_BOLD);
                title.setTextColor(Color.WHITE);
                bd.setCustomTitle(title);
                TextView message = new TextView(getApplicationContext());
//                message.setBackgroundColor(Color.rgb(250,104,0));
                message.setPadding(30, 20, 10, 20);
//                message.setTextSize(18F);
                message.setText("Confirm Successful.");
//                message.setTypeface(Typeface.DEFAULT_BOLD);
                message.setTextColor(Color.BLACK);
//                message.setGravity(Gravity.CENTER_VERTICAL);
                bd.setView(message);
                bd.setCustomTitle(title);
//                bd.setMessage("Comfirm Successfully to Local");
                bd.setCancelable(false);

                bd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                            updateVoucher();
//                        insertdatatoLiteDb();
//                        ConfirmedTranid = Long.parseLong("0");
                        dialog.dismiss();
                        SaleVouSalesmen.clear();
                        if (sh.size() > 0) sh.clear();
                        if (sd.size() > 0) sd.clear();
                        intent = new Intent(sale_entry.this, sale_entry.class);
                        startActivity(intent);
                        finish();
                    }
                });
                dialog = bd.create();
                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.rgb(8, 111, 158));
                    }
                });
                dialog.show();


            }
        } catch (Exception ee) {

        }
    }

    private void ShowPrintSelection() {
        PopupMenu popup = new PopupMenu(sale_entry.this, imgPrinter);
        //Inflating the Popup using xml file
        popup.getMenuInflater().inflate(R.menu.printselectionmenu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (sd.size() > 0) {
                    showReport(item.getTitle().toString());
                } else {
                    AlertDialog.Builder bd = new AlertDialog.Builder(sale_entry.this, R.style.AlertDialogTheme);
                    bd.setTitle("iStock");
                    bd.setMessage("No Data To Print");
                    bd.setCancelable(false);
                    bd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    bd.create().show();
                }
                return true;
            }
        });
        popup.show();//showing popup menu
    }

    private void showReport(String title) {
        reportviewer.header.put("date", txtdate.getText().toString());
        reportviewer.header.put("Customer", String.valueOf(sh.get(0).getCustomerid()));
        reportviewer.header.put("Pay_Type", String.valueOf(sh.get(0).getPay_type()));
        reportviewer.header.put("TotalAmt", txttotal.getText().toString());
        reportviewer.header.put("itemDis", String.valueOf(Double.parseDouble(txtfoc.getText().toString()) - sh.get(0).getFoc_amount()));
        reportviewer.header.put("Foc", String.valueOf(sh.get(0).getFoc_amount()));
        reportviewer.header.put("VouDis", txtvoudis.getText().toString());
        reportviewer.header.put("previous", txtoutstand.getText().toString());
        reportviewer.header.put("tax", txttaxamt.getText().toString());
        reportviewer.header.put("paid", txtpaidamt.getText().toString());
        reportviewer.header.put("net", txtnet.getText().toString());
        reportviewer.header.put("docid", sh.get(0).getDocid());
        reportviewer.title = title;
        Intent intent = new Intent(sale_entry.this, reportviewer.class);
        startActivity(intent);
    }

    private void CleanGarbage() {
        String sqlUrl = "";
        String ip = sh_ip.getString("ip", "empty");
        String port = sh_port.getString("port", "empty");
        sqlUrl = "http://" + ip + ":" + port + "/api/DataSync/GetData?tranid=" + tranid + "&clear=true";
        requestQueue = Volley.newRequestQueue(this);
        final Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


            }

        };

        final Response.ErrorListener error = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(sale_entry.this, "You are in Offline. Please check your connection!", Toast.LENGTH_SHORT).show();
                pb.dismiss();
            }
        };
        StringRequest req = new StringRequest(Request.Method.GET, sqlUrl, listener, error);
        requestQueue.add(req);
    }

    private void LogOut() {

        AlertDialog.Builder bd = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
        Context context = this;
        bd.setTitle("iStock");
        bd.setMessage("Are You sure to logout?");
        bd.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (!comfirm && sd.size() > 0) {
                    AlertDialog.Builder conf = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
                    conf.setTitle("iStock");
                    conf.setMessage("Do you Comfirm Voucher? if you do not comfirm,you lost your data");
                    conf.setCancelable(false);
                    conf.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            voucherConfirm();
                            UnLockUser(frmlogin.LoginUserid);
                            dialog.dismiss();
                            msg.dismiss();
                        }
                    });
                    conf.setNegativeButton("N0", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            CleanGarbage();
                            UnLockUser(frmlogin.LoginUserid);
                            dialog.dismiss();
                            msg.dismiss();
                            Intent intent = new Intent(sale_entry.this, frmlogin.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    conf.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            msg.dismiss();
                        }
                    });
                    conf.create().show();

                } else {
                    CleanGarbage();
                    UnLockUser(frmlogin.LoginUserid);
                    dialog.dismiss();
                    msg.dismiss();
                    Intent intent = new Intent(sale_entry.this, frmlogin.class);
                    startActivity(intent);
                    finish();

                }
            }
        });
        bd.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                msg.dismiss();
            }
        });
        msg = bd.create();
        msg.show();

    }

    private void UnLockUser(int Userid) {
        String ip = sh_ip.getString("ip", "Localhost");
        String port = sh_port.getString("port", "80");
        String Device = frmlogin.Device_Name.replace(" ", "%20");
        String Url = "http://" + ip + ":" + port + "/api/mobile/LockUser?userid=" + frmlogin.LoginUserid + "&hostname=" + Device + "&locked=" + false;
//        String Url = "http://" + ip + ":" + port + "/api/DataSync/GetData?userid=" + frmlogin.LoginUserid + "&hostname=" + Device + "&locked=" + false;

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
                Toast.makeText(sale_entry.this, "You are in Offline. Please check your connection!", Toast.LENGTH_SHORT).show();
            }
        };


        StringRequest req = new StringRequest(Request.Method.GET, Url, listener, error);
        requestQueue.add(req);
    }

    private void InitializeHeader(ArrayList<String> id, ArrayList<Button> btn) {
        Cursor cursor = null;
        String sqlString = "";
        //customer group
        cursor = DatabaseHelper.DistinctSelectQuerySelection("Customer", new String[]{"custgroupid", "custgroupname", "custgroupcode"}, "customerid=?", new String[]{id.get(0)});
        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {

                    long custgroupid = cursor.getLong(cursor.getColumnIndex("custgroupid"));
                    String custgroupname = cursor.getString(cursor.getColumnIndex("custgroupname"));
                    String shortname = cursor.getString(cursor.getColumnIndex("custgroupcode"));
                    btn.get(0).setText(/*custgroupid+":"+*/custgroupname);
                    sale_entry.selected_custgroupid = custgroupid;
                } while (cursor.moveToNext());

            }

        }

        cursor.close();

        //Township
        cursor = DatabaseHelper.DistinctSelectQuerySelection("Customer", new String[]{"townshipid", "townshipname", "townshipcode"}, "customerid=?", new String[]{id.get(0)});
        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    /*CREATE TABLE `Customer` (
	`customerid`	INTEGER NOT NULL,
	`shortdesc`	TEXT,
	`name`	TEXT,
	`townshipid`	INTEGER,
	`pricelevelid`	INTEGER,
	`iscredit`	INTEGER,
	`balance`	NUMERIC,
	`creditlimit`	INTEGER,
	`dueindays`	INTEGER,
	`discountpercent`	INTEGER,
	`isinactive`	INTEGER,
	`discountamount`	INTEGER,
	`custgroupid`	INTEGER,
	`nationalcardid`	INTEGER,
	`isdeleted`	INTEGER,
	`custgroupname`	TEXT,
	`custgroupcode`	TEXT,
	`townshipname`	TEXT,
	`townshipcode`	TEXT,
	PRIMARY KEY(`customerid`)
);*/
                    long townshipid = cursor.getLong(cursor.getColumnIndex("townshipid"));
                    String townshipname = cursor.getString(cursor.getColumnIndex("townshipname"));
                    String shortname = cursor.getString(cursor.getColumnIndex("townshipcode"));
                    btn.get(1).setText(/*townshipid+":"+*/townshipname);
                    sh.get(0).setTownshipid(townshipid);
                    sale_entry.selected_townshipid = townshipid;
                } while (cursor.moveToNext());

            }

        }

        cursor.close();


        //customer
        sqlString = "select customerid,name,iscredit from Customer where customerid=" + id.get(0);//,credit,due_in_days,credit_limit
        cursor = DatabaseHelper.rawQuery(sqlString);
        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    long customerid = cursor.getLong(cursor.getColumnIndex("customerid"));
                    String customername = cursor.getString(cursor.getColumnIndex("name"));
//                    String customercode = cursor.getString(cursor.getColumnIndex("customer_code"));
                    boolean credit = cursor.getInt(cursor.getColumnIndex("iscredit")) == 1 ? true : false;
//                    int due_in_days = cursor.getInt(cursor.getColumnIndex("due_in_days"));
//                    double credit_limit=cursor.getDouble(cursor.getColumnIndex("credit_limit"));
                    btn.get(2).setText(/*customerid+":"+*/customername);
                    isCreditcustomer = credit;
                } while (cursor.moveToNext());

            }

        }
        cursor.close();
        //location

        String locname = "";
//        if (defloc != 1) {
//            Cursor deflocur = DatabaseHelper.rawQuery("select locationid,Name,shortdesc,Name from Location where locationid=" + sh.get(0).getLocationid());
//            if (deflocur != null && deflocur.getCount() != 0) {
//                if (deflocur.moveToFirst()) {
//                    do {
//                        locname = deflocur.getString(deflocur.getColumnIndex("Name"));
//                    } while (deflocur.moveToNext());
//                }
//            }
//        }


        // sqlString = "select locationid,Name,shortdesc,branchID from Location where locationid= "+id.get(1);

        //modified by EKK
//        if (sale_entry.sh.get(0).getLocationid() == 1) {
//            if (frmlogin.defaultbranchid == 0) {
//                sqlString = "select locationid,Name,shortdesc,branchID from Location where isdeleted=0 Limit 1";
//            } else {
//                if (frmlogin.det_locationid == 0) {
//                    sqlString = "select locationid,Name,shortdesc,branchID from Location where isdeleted=0 and branchid = " + frmlogin.defaultbranchid + " Limit 1";
//                } else {
//                    sqlString = "select locationid,Name,shortdesc,branchID from Location where isdeleted=0 and branchid = " + frmlogin.defaultbranchid + " and locationid =" + frmlogin.det_locationid;
//                }
//
//            }
//        } else {
//            sqlString = "select locationid,Name,shortdesc,branchID from Location where isdeleted=0 and locationid =" + sale_entry.sh.get(0).getLocationid();
//        }

        sqlString = "select locationid,Name,shortdesc,branchID from Location where isdeleted=0 and locationid =" + sale_entry.sh.get(0).getLocationid();
        cursor = DatabaseHelper.rawQuery(sqlString);
        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    long locationid = cursor.getLong(cursor.getColumnIndex("locationid"));
                    String locationname = cursor.getString(cursor.getColumnIndex("Name"));
                    btn.get(3).setText(/*locationid+":"+*/locationname);
                    btn.get(3).setText(locationname);
                    sh.get(0).setLocationid(locationid);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();


        //payment_type
        sqlString = "select * from Payment_Type where paytypeid=" + id.get(2);
        cursor = DatabaseHelper.rawQuery(sqlString);
        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    int pay_type = cursor.getInt(cursor.getColumnIndex("paytypeid"));
                    String pay_typename = cursor.getString(cursor.getColumnIndex("name"));
                    String shortname = cursor.getString(cursor.getColumnIndex("shortdesc"));
                    btn.get(4).setText(/*pay_type+":"+*/pay_typename);
                } while (cursor.moveToNext());

            }

        }

        //added by EKK on 17-11-2020
        //Cash In
        sqlString = "select * from cashbook_user where accountid=" + id.get(3) + " and userid = " + frmlogin.LoginUserid + " and isenabled =1 order by accountid";
        cursor = DatabaseHelper.rawQuery(sqlString);
        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    String cname = cursor.getString(cursor.getColumnIndex("name"));
                    btn.get(5).setText(/*pay_type+":"+*/cname);
                } while (cursor.moveToNext());
            }
        }


    }

    private void ChangeHeader(String name, Button btn, Button btnpay) {

        try {

            String sqlString;
            String filter;
            ArrayList<customergroup> cg = new ArrayList<>();
            ArrayList<Township> townships = new ArrayList<>();
            ArrayList<customer> customers = new ArrayList<>();
            ArrayList<Location> locations = new ArrayList<>();
            ArrayList<pay_type> pay_types = new ArrayList<>();
            ArrayList<Salesmen> salesmen = new ArrayList<>();
            ArrayList<CashIn> CashIn = new ArrayList<>(); //added by EKK on 16-11-2020

            Cursor cursor = null;
            AlertDialog.Builder bd = new AlertDialog.Builder(sale_entry.this);
            View view = getLayoutInflater().inflate(R.layout.changeheadervalue, null);
            bd.setCancelable(false);
            bd.setView(view);
            RecyclerView rv = view.findViewById(R.id.rcvChange);
            ImageButton imgClose = view.findViewById(R.id.imgNochange);
            imgClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnSalesmen.setText("Choose");
                    isCreditcustomer = selectInsertLibrary.CheckingCreditCustomer((int) sale_entry.sh.get(0).getCustomerid());
                    da.dismiss();
                }
            });
            ImageButton imgChangSave = view.findViewById(R.id.imgChangeSave);
            ImageButton imgClear = view.findViewById(R.id.imgClear);
            ImageButton imgAddCustomer = view.findViewById(R.id.imgAddCustomer);    //added by ZYP for customer setup
            ImageButton imgDownloadCustomer = view.findViewById(R.id.imgDowloadCustomer);
            imgDownloadCustomer.setVisibility(View.GONE);
            imgAddCustomer.setVisibility(View.GONE);

            if (name.equals("Salesmen")) {
                imgChangSave.setVisibility(View.VISIBLE);
                imgClear.setVisibility(View.VISIBLE);
            } else if (name.equals("Customer")) {
                imgAddCustomer.setVisibility(View.VISIBLE);

            } else {
                imgChangSave.setVisibility(View.GONE);
                imgClear.setVisibility(View.GONE);
            }

            imgAddCustomer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    da.dismiss();

                    AlertDialog.Builder custb = new AlertDialog.Builder(view.getContext());
                    View layout = getLayoutInflater().inflate(R.layout.custquickadd, null);
                    custb.setCancelable(true);
                    custb.setView(layout);
                    final EditText name = layout.findViewById(R.id.txtName);
//                        final EditText shortdesc = layout.findViewById(R.id.txtShort);
                    final EditText code = layout.findViewById(R.id.txtCode);
                    final CheckBox chkCredit = layout.findViewById(R.id.chkCredit);
                    EditText txtdueDay=layout.findViewById(R.id.txtdueday);
                    Button imgCustomerTownship = layout.findViewById(R.id.btnTownship);
                    Button imgCustomerGroup = layout.findViewById(R.id.btnCustGroup);
                    EditText credit = layout.findViewById(R.id.txtCredit);
                    credit.setEnabled(false);
                    Button btnclose = layout.findViewById(R.id.btnclose);
                    Button btnok = layout.findViewById(R.id.btnok);
                    btnclose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            custdia.dismiss();
                        }
                    });
                    custdia = custb.create();
                    custdia.show();

                    //modified by ZYP 01-09-2021 for customer setup

                    imgCustomerTownship.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ChangeHeader("Township", imgCustomerTownship, imgCustomerTownship);
                        }
                    });
                    imgCustomerGroup.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ChangeHeader("Customer Group", imgCustomerGroup, imgCustomerGroup);
                        }
                    });

                    chkCredit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                credit.setEnabled(true);
                                if(frmmain.isuseduedate){
                                    txtdueDay.setVisibility(View.VISIBLE);
                                }

                            } else {
                                credit.setEnabled(false);
                            }
                        }
                    });

                    btnok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (name.getText().toString().trim().isEmpty()) {
                                //|| code.getText().toString().trim().isEmpty()
                                AlertDialog.Builder bd = new AlertDialog.Builder(sale_entry.this, R.style.AlertDialogTheme);
                                bd.setTitle("iStock");
                                bd.setMessage("No data to Confirm!");
                                bd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        bd.create().dismiss();
                                    }
                                });
                                bd.create().show();
                            } else {
                                try {
                                    boolean iscredit = false;
                                    double creditL = 0.0;
                                    String nameSt = name.getText().toString().trim();
                                    String codeSt = code.getText().toString().trim();
                                    String creditLimit = credit.getText().toString();
                                    int dueinday=0;
                                    if(!txtdueDay.getText().toString().isEmpty()){
                                        dueinday=Integer.parseInt(txtdueDay.getText().toString());
                                    }

                                    newCustomerName = nameSt;
                                    int custid = getCustomerCount() + 1;
                                    newCustomerId = custid;
                                    if (chkCredit.isChecked()) {
                                        iscredit = true;
                                        creditL = Double.parseDouble(creditLimit.equals("") ? "0.0" : creditLimit);
                                    }
                                    sqlstring = "insert into customer_tmp (shortdesc,  name,townshipid,iscredit,creditlimit, custgroupid,userid,sr, isinactive,isdeleted,dueindays) values (";
//                                    if (codeSt.length() > 0) {
//                                        sqlstring += "'" + codeSt + "','" + nameSt + "'," + selected_townshipid + "," + iscredit + "," + creditL + "," + selected_custgroupid + "," + frmlogin.LoginUserid + ",uuid_generate_v4(),false,false,"+(dueinday>0?dueinday:null)+")";
//                                    } else {
                                    sqlstring += (codeSt.length() > 0?"'" + codeSt + "'":null) + ",'" + nameSt + "'," + selected_townshipid + "," + iscredit + "," + creditL + "," + selected_custgroupid + "," + frmlogin.LoginUserid + ",uuid_generate_v4(),false,false,"+(dueinday>0?dueinday:null)+")";
//                                    }
                                    sqlstring += "&" + frmlogin.LoginUserid;
                                    String sqlString = "select name from Customer where name='" + nameSt + "'";
                                    Cursor cursor = DatabaseHelper.rawQuery(sqlString);
                                    if (cursor != null && cursor.getCount() > 0) {
                                        AlertDialog.Builder bd = new AlertDialog.Builder(sale_entry.this);
                                        bd.setCancelable(false);
                                        bd.setTitle("iStock");
                                        bd.setMessage("This name alredy exists. Do you want to create?");
                                        bd.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogmsg, int which) {
                                                name.setText("");
                                                code.setText("");
                                                credit.setText("");
                                                chkCredit.setChecked(false);
                                                msg.dismiss();
                                                return;
                                            }
                                        });
                                        bd.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogmsg, int which) {


                                                InsertCustomer();


                                                msg.dismiss();

                                            }
                                        });
                                        msg = bd.create();
                                        msg.setOnShowListener(new DialogInterface.OnShowListener() {
                                            @Override
                                            public void onShow(DialogInterface dialog) {
                                                msg.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
                                                msg.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
                                            }
                                        });
                                        msg.show();


                                    } else {

                                        InsertCustomer();

                                    }


                                } catch (Exception eee) {
                                    Toast.makeText(getBaseContext(), eee.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }

                        }
                    });

                }
            });

            imgChangSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (SaleVouSalesmen.size() > 0) {
                        String salesmen = "";
                        if (SaleVouSalesmen.size() > 4) {

                            for (int i = 0; i < 4; i++) {
                                if (i != 3) {
                                    salesmen += SaleVouSalesmen.get(i).getSalesmen_Short() + ",";
                                } else {
                                    salesmen += SaleVouSalesmen.get(i).getSalesmen_Short() + ",...";
                                }
                            }

                        } else {

                            for (int i = 0; i < SaleVouSalesmen.size(); i++) {
                                if (i != SaleVouSalesmen.size() - 1) {
                                    salesmen += SaleVouSalesmen.get(i).getSalesmen_Short() + ",";
                                } else {
                                    salesmen += SaleVouSalesmen.get(i).getSalesmen_Short();
                                }
                            }
                        }

                        btnSalesmen.setText(salesmen);

                    } else {
                        btnSalesmen.setText("Choose");
                    }
                    da.dismiss();
                }
            });

            imgClear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SaleVouSalesmen.clear();
                    if (salesmen.size() > 0) {
                        salesmen.clear();
                    }
                    Cursor salemenCursor = DatabaseHelper.rawQuery("select salesmenid,salesmenname,shortdesc from Salesmen");
                    if (salemenCursor != null && salemenCursor.getCount() != 0) {
                        if (salemenCursor.moveToFirst()) {
                            do {
                                long salesmenid = salemenCursor.getLong(salemenCursor.getColumnIndex("salesmenid"));
                                String salesmenname = salemenCursor.getString(salemenCursor.getColumnIndex("salesmenname"));
                                String shortname = salemenCursor.getString(salemenCursor.getColumnIndex("shortdesc"));
                                salesmen.add(new Salesmen(salesmenid, salesmenname, shortname));
                            } while (salemenCursor.moveToNext());

                        }

                    }

                    salemenCursor.close();


                    SalesmenAdpater sad = new SalesmenAdpater(sale_entry.this, salesmen);
                    rv.setAdapter(sad);
                    LinearLayoutManager sgridLayoutManager = new LinearLayoutManager(sale_entry.this, LinearLayoutManager.VERTICAL, false);
                    rv.setLayoutManager(sgridLayoutManager);
                    salemenCursor = null;
                    da.show();
                }
            });

            openEditText = false;
            EditText etdSearch = view.findViewById(R.id.etdSearch);
            ImageButton imgSearch = view.findViewById(R.id.imgSearch);
            imgSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {

                        AlertDialog.Builder searchBuilder = new AlertDialog.Builder(sale_entry.this);
                        View view = getLayoutInflater().inflate(R.layout.searchbox, null);
                        searchBuilder.setView(view);
                        EditText etdSearch = view.findViewById(R.id.etdSearch);
                        ImageButton btnSearch = view.findViewById(R.id.imgOK);
                        btnSearch.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!etdSearch.getText().toString().isEmpty()) {
                                    switch (name) {
                                        case "Customer Group":
                                            ArrayList<customergroup> filteredList = new ArrayList<>();

                                            for (customergroup item : cg) {
                                                if (item.getName().toLowerCase().contains(etdSearch.getText().toString().toLowerCase())) {
                                                    filteredList.add(item);
                                                }
                                            }
                                            CustGroupAdapter ad = new CustGroupAdapter(sale_entry.this, filteredList, btn, da);
                                            rv.setAdapter(ad);
                                            GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 4);
                                            rv.setLayoutManager(gridLayoutManager);
                                            break;
                                        case "Customer":

                                            ArrayList<customer> filteredcustomer = new ArrayList<>();

                                            for (customer item : customers) {
                                                if (item.getName().toLowerCase().contains(etdSearch.getText().toString().toLowerCase())) {
                                                    filteredcustomer.add(item);
                                                }
                                            }
                                            CustomerAdapter ca = new CustomerAdapter(sale_entry.this, filteredcustomer, btn, btnpay, da);
                                            rv.setAdapter(ca);
                                            GridLayoutManager gridLayoutManagerCust = new GridLayoutManager(getApplicationContext(), 4);
                                            rv.setLayoutManager(gridLayoutManagerCust);

                                            break;
                                        case "Township":
                                            ArrayList<Township> filteredtownship = new ArrayList<>();

                                            for (Township item : townships) {
                                                if (item.getName().toLowerCase().contains(etdSearch.getText().toString().toLowerCase())) {
                                                    filteredtownship.add(item);
                                                }
                                            }
                                            TownshipAdapter ta = new TownshipAdapter(sale_entry.this, filteredtownship, btn, da);
                                            rv.setAdapter(ta);
                                            GridLayoutManager gridLayoutManagertown = new GridLayoutManager(getApplicationContext(), 4);
                                            rv.setLayoutManager(gridLayoutManagertown);
                                            break;
                                        case "Location":
                                            ArrayList<Location> filteredlocation = new ArrayList<>();

                                            for (Location item : locations) {
                                                if (item.getName().toLowerCase().contains(etdSearch.getText().toString().toLowerCase())) {
                                                    filteredlocation.add(item);
                                                }
                                            }
                                            LocationAdapter la = new LocationAdapter(sale_entry.this, filteredlocation, btn, da);
                                            rv.setAdapter(la);
                                            GridLayoutManager gridLayoutManagerloc = new GridLayoutManager(getApplicationContext(), 4);
                                            rv.setLayoutManager(gridLayoutManagerloc);
                                            break;
                                        case "Payment Type":
                                            ArrayList<pay_type> filteredpaytype = new ArrayList<>();

                                            for (pay_type item : pay_types) {
                                                if (item.getName().toLowerCase().contains(etdSearch.getText().toString().toLowerCase())) {
                                                    filteredpaytype.add(item);
                                                }
                                            }
                                            PaymentTypeAdapter pad = new PaymentTypeAdapter(sale_entry.this, filteredpaytype, btn, da);
                                            rv.setAdapter(pad);
                                            GridLayoutManager gridLayoutManagerPaymentType = new GridLayoutManager(getApplicationContext(), 4);
                                            rv.setLayoutManager(gridLayoutManagerPaymentType);
                                            da.show();
                                            break;

                                        case "Salesmen":

                                            ArrayList<Salesmen> filteredsalesmen = new ArrayList<>();

                                            for (Salesmen item : salesmen) {
                                                if (item.getSalesmen_Name().toLowerCase().contains(etdSearch.getText().toString().toLowerCase())) {
                                                    filteredsalesmen.add(item);
                                                }
                                            }
                                            sm = new SalesmenAdpater(sale_entry.this, filteredsalesmen);
                                            rv.setAdapter(sm);
                                            LinearLayoutManager linearLayoutManagerSalesmen = new LinearLayoutManager(sale_entry.this, LinearLayoutManager.VERTICAL, false);
                                            rv.setLayoutManager(linearLayoutManagerSalesmen);
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

                case "Salesmen":
                    if (salesmen.size() > 0) {
                        salesmen.clear();
                    }
                    Cursor salemenCursor = DatabaseHelper.rawQuery("select salesmenid,salesmenname,shortdesc from Salesmen");
                    if (salemenCursor != null && salemenCursor.getCount() != 0) {
                        if (salemenCursor.moveToFirst()) {
                            do {
                                long salesmenid = salemenCursor.getLong(salemenCursor.getColumnIndex("salesmenid"));
                                String salesmenname = salemenCursor.getString(salemenCursor.getColumnIndex("salesmenname"));
                                String shortname = salemenCursor.getString(salemenCursor.getColumnIndex("shortdesc"));
                                salesmen.add(new Salesmen(salesmenid, salesmenname, shortname));
                            } while (salemenCursor.moveToNext());

                        }

                    }

                    salemenCursor.close();


                    SalesmenAdpater sad = new SalesmenAdpater(sale_entry.this, salesmen);
                    rv.setAdapter(sad);
                    LinearLayoutManager sgridLayoutManager = new LinearLayoutManager(sale_entry.this, LinearLayoutManager.VERTICAL, false);
                    rv.setLayoutManager(sgridLayoutManager);
                    salemenCursor = null;


                    da.show();


                    break;

                case "Customer Group":
                    if (cg.size() > 0) {
                        cg.clear();
                    }
                    cursor = DatabaseHelper.DistinctSelectQuery("Customer", new String[]{"custgroupid", "custgroupname", "custgroupcode"});
                    if (cursor != null && cursor.getCount() != 0) {
                        if (cursor.moveToFirst()) {
                            do {
                                long custgroupid = cursor.getLong(cursor.getColumnIndex("custgroupid"));
                                String custgroupname = cursor.getString(cursor.getColumnIndex("custgroupname"));
                                String shortname = cursor.getString(cursor.getColumnIndex("custgroupcode"));
                                cg.add(new customergroup(custgroupid, custgroupname, shortname));
                            } while (cursor.moveToNext());

                        }

                    }

                    cursor.close();


                    CustGroupAdapter ad = new CustGroupAdapter(sale_entry.this, cg, btn, da);
                    rv.setAdapter(ad);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 4);
                    rv.setLayoutManager(gridLayoutManager);
                    cursor = null;


                    da.show();

                    break;
                case "Township":
                    if (use_customergroup) {
                        sqlString = "select  distinct townshipid,townshipname,townshipcode from Customer " +
                                " where  isdeleted=0 and isinactive=0 and ((custgroupid<>-1 and custgroupid=" + selected_custgroupid + ") or " + selected_custgroupid + "=-1)";

                    } else {
                        sqlString = "select distinct townshipid,townshipname,townshipcode from Customer " +
                                " where  isdeleted=0 and isinactive=0";
                    }
                    cursor = DatabaseHelper.rawQuery(sqlString);

                    // cursor = DatabaseHelper.DistinctSelectQuery("Customer", new String[]{"townshipid", "townshipname", "townshipcode"});
                    if (cursor != null && cursor.getCount() != 0) {
                        if (cursor.moveToFirst()) {
                            do {
                                long townshipid = cursor.getLong(cursor.getColumnIndex("townshipid"));
                                String townshipname = cursor.getString(cursor.getColumnIndex("townshipname"));
                                String shortname = cursor.getString(cursor.getColumnIndex("townshipcode"));

                                townships.add(new Township(townshipid, townshipname, shortname));
                            } while (cursor.moveToNext());

                        }
                        System.out.println(townships.size() + "this is size of township");
                    } else {
                        da.dismiss();
                    }
                    cursor.close();

                    TownshipAdapter tad = new TownshipAdapter(sale_entry.this, townships, btn, da);
                    rv.setAdapter(tad);
                    GridLayoutManager gridLayoutManagerTownship = new GridLayoutManager(getApplicationContext(), 4);
                    rv.setLayoutManager(gridLayoutManagerTownship);
                    da.show();

                    break;
                case "Customer":

                    if (!use_customergroup) {
                        selected_custgroupid = -1;

                    }
                    if (!use_township) {
                        selected_townshipid = -1;

                    }
                    filter = " where  isdeleted=0 and ((custgroupid<>-1 and custgroupid=" + selected_custgroupid + ") or " + selected_custgroupid + "=-1)" +
                            " and ((townshipid<>-1 and townshipid=" + selected_townshipid + ") or " + selected_townshipid + "=-1)";

                    sqlString = "select *  from Customer " + filter/* + filter +" order by customerid,name"*//*customerid,shortdesc,name,Townshipid,pricelevelid,iscredit,balance,creditlimit,dueindays,discountpercent,isinactive,discountamount,custgroupid,nationalcardid,isdeleted*/;
                    cursor = DatabaseHelper.rawQuery(sqlString);
                    System.out.println(cursor.getCount() + "count!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
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
                                isCreditcustomer = iscredit;
                                int balance = cursor.getInt(cursor.getColumnIndex("balance"));
                                int creditlimit = cursor.getInt(cursor.getColumnIndex("creditlimit"));
                                int dueindays = cursor.getInt(cursor.getColumnIndex("dueindays"));
//                                due_in_days=dueindays;
//                                if(iscredit){
                                  //  sh.get(0).setDue_in_days(dueindays);
//                                }

                                double discountpercent = cursor.getDouble(cursor.getColumnIndex("discountpercent"));
                                boolean isinactive = cursor.getInt(cursor.getColumnIndex("isinactive")) == 1 ? true : false;
                                double discountamount = cursor.getDouble(cursor.getColumnIndex("discountamount"));
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

                    CustomerAdapter cad = new CustomerAdapter(sale_entry.this, customers, btn, btnpay, da);
                    rv.setAdapter(cad);
                    GridLayoutManager gridLayoutManagerCustomer = new GridLayoutManager(getApplicationContext(), 4);
                    rv.setLayoutManager(gridLayoutManagerCustomer);
                    da.show();

                    break;
                case "Location":
                    //added by KLM for Location select base on branch user 13082022
                    SelectInsertLibrary.GetLocationBaseOnBrachUser(locations);
//                    sqlString="select * from Branch_User";
//                    sqlString="select l.* from Location l left join Branch_User bu on bu.branchid=l.branchid where  l.isdeleted=0 and bu.isenabled=1 and bu.userid="+frmlogin.LoginUserid;
                    //modified by EKK
//                    if (frmlogin.defaultbranchid == 0) {
//                        sqlString = "select locationid,name,shortdesc,branchid from Location where isdeleted=0";
//                    } else {
//                        sqlString = "select locationid,name,shortdesc,branchid from Location where isdeleted=0 and branchid = " + frmlogin.defaultbranchid;
//                    }

//                    cursor = DatabaseHelper.rawQuery(sqlString);
//                    System.out.println(cursor.getCount() + "count!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//                    if (cursor != null && cursor.getCount() != 0) {
//                        if (cursor.moveToFirst()) {
//                            do {
////                                int branchid=cursor.getInt(cursor.getColumnIndex("branchid"));
//
//                                long locationid = cursor.getLong(cursor.getColumnIndex("locationid"));
//                                String locationname = cursor.getString(cursor.getColumnIndex("Name"));
//                                String shortname = cursor.getString(cursor.getColumnIndex("shortdesc"));
//                                long branchid = cursor.getLong(cursor.getColumnIndex("branchID"));
//                                locations.add(new Location(locationid, locationname, shortname, branchid));
//                                Log.i("branchid",branchid+"");
//                            } while (cursor.moveToNext());
//
//                        }
//
//                    } else {
//                        da.dismiss();
//                    }
//                    cursor.close();

                    LocationAdapter lad = new LocationAdapter(sale_entry.this, locations, btn, da);
                    rv.setAdapter(lad);
                    GridLayoutManager gridLayoutManagerLocation = new GridLayoutManager(getApplicationContext(), 4);
                    rv.setLayoutManager(gridLayoutManagerLocation);
                    da.show();

                    break;
                case "Payment Type":

                    if (pay_types.size() > 0) {
                        pay_types.clear();
                    }

                    System.out.println(isCreditcustomer + " this is credit customer or not");
                    if (isCreditcustomer) {
                        sqlString = "select * from Payment_Type where paytypeid!=3 and paytypeid!=4";
                    } else {
                        sqlString = "select * from Payment_Type where paytypeid=1";
                    }
                    cursor = DatabaseHelper.rawQuery(sqlString);
                    if (cursor != null && cursor.getCount() != 0) {
                        if (cursor.moveToFirst()) {
                            do {
                                int pay_type = cursor.getInt(cursor.getColumnIndex("paytypeid"));
                                String pay_typename = cursor.getString(cursor.getColumnIndex("name"));
                                String shortname = cursor.getString(cursor.getColumnIndex("shortdesc"));
                                pay_types.add(new pay_type(pay_type, pay_typename, shortname));
                            } while (cursor.moveToNext());

                        }

                    } else {
                        da.dismiss();
                    }
                    cursor.close();

                    PaymentTypeAdapter pad = new PaymentTypeAdapter(sale_entry.this, pay_types, btn, da);
                    rv.setAdapter(pad);
                    GridLayoutManager gridLayoutManagerPaymentType = new GridLayoutManager(getApplicationContext(), 4);
                    rv.setLayoutManager(gridLayoutManagerPaymentType);
                    da.show();

                    break;

                case "Cash In":

                    if (pay_types.size() > 0) {
                        pay_types.clear();
                    }


                    sqlString = "select * from cashbook_user where isenabled=1 and userid = " + frmlogin.LoginUserid;

                    cursor = DatabaseHelper.rawQuery(sqlString);
                    if (cursor != null && cursor.getCount() != 0) {
                        if (cursor.moveToFirst()) {
                            do {
                                int accountid = cursor.getInt(cursor.getColumnIndex("accountid"));
                                String Cname = cursor.getString(cursor.getColumnIndex("name"));
                                int acctgroupid = cursor.getInt(cursor.getColumnIndex("acctgroupid"));

                                CashIn.add(new CashIn(accountid, Cname, acctgroupid));
                            } while (cursor.moveToNext());

                        }

                    } else {
                        da.dismiss();
                    }
                    cursor.close();

                    //  PaymentTypeAdapter pad = new PaymentTypeAdapter(sale_entry.this, pay_types, btn, da);
                    CashInAdapter ciad = new CashInAdapter(sale_entry.this, CashIn, btn, da);

                    rv.setAdapter(ciad);
                    GridLayoutManager gridLayoutManagerCashIn = new GridLayoutManager(getApplicationContext(), 4);
                    rv.setLayoutManager(gridLayoutManagerCashIn);
                    da.show();

                    break;
            }
        } catch (Exception e) {
            da.dismiss();
        }
    }

    private void voucherConfirm() {

        paidamount = 0.0;
        changeamount = 0.0;

        if (sh.get(0).getPay_type() == 1) {
            AlertDialog.Builder change = new AlertDialog.Builder(sale_entry.this);
            change.setCancelable(false);
            View v = getLayoutInflater().inflate(R.layout.savechange, null);
            LinearLayout rlBT = v.findViewById(R.id.rlBT); //Add by TZW for BlueToothPrinter
            ImageButton imgSave = v.findViewById(R.id.imgSave);
            ImageButton imgClose = v.findViewById(R.id.imgClose);
            tvAmount = v.findViewById(R.id.txtAmount);
            tvAmount.setText(txtnet.getText().toString());
            tvChange = v.findViewById(R.id.txtChange);
            TextView tvPaid = v.findViewById(R.id.txtpaidAmount);
            tvPaid.setText(txtnet.getText().toString());
            CheckBox chkPrint = v.findViewById(R.id.chkPrint);
            chkPrint.setChecked(false);
            bill_not_print = false;

            rlBT.setVisibility(View.VISIBLE);//Add by TZW for BlueToothPrinter
            RadioButton chkBT = v.findViewById(R.id.chkBT);//Add by TZW for BlueToothPrinter
            chkBT.setChecked(use_bluetooth);//Add by TZW for BlueToothPrinter
//            use_bluetooth = true;//Add by tZW for BlueToothPrinter

            chkBT.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    use_bluetooth = isChecked;
                }
            });

            //Added by ZYP for localprint
            RadioButton chkLocalPrint = v.findViewById(R.id.chkLocalPrint);
            //use_localprint = chkLocalPrint.isChecked();
            chkLocalPrint.setChecked(use_localprint);

            chkLocalPrint.setOnCheckedChangeListener((buttonView, isChecked) -> {
                use_localprint = isChecked;
            });

            chkPrint.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    bill_not_print = isChecked;
                }
            });

            tvBillCount = v.findViewById(R.id.billcount);
            tvBillCount.setText(String.valueOf(billprintcount));
            RelativeLayout rlBillCount = v.findViewById(R.id.rlBillCount);
            TextView txtAmounttext = v.findViewById(R.id.txtAmounttext);
            tvBillCount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    frombillcount = true;
                    keynum = tvBillCount.getText().toString();
                    showKeyPad(txtAmounttext, tvBillCount);

                }
            });


            rlBillCount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    frombillcount = true;
                    keynum = tvBillCount.getText().toString();
                    showKeyPad(txtAmounttext, tvBillCount);

                }
            });

            RelativeLayout rlPaid = v.findViewById(R.id.rlPaid);
            rlPaid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    keynum = tvPaid.getText().toString();
                    fromSaleChange = true;
                    showKeyPad(tvAmount, tvPaid);


                }
            });
            tvPaid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    keynum = tvPaid.getText().toString();
                    fromSaleChange = true;
                    showKeyPad(tvAmount, tvPaid);
                }
            });

            imgSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Double.parseDouble(ClearFormat(tvPaid.getText().toString())) < Double.parseDouble(ClearFormat(txtnet.getText().toString()))) {

                        GlobalClass.showAlertDialog(sale_entry.this, "iStock", "Paid Amount is less than Net Amount!");
                    } else {

                        salechange.dismiss();
                        if (tvPaid.getText().toString().trim().isEmpty()) {
                            paidamount = 0;
                        } else {
                            paidamount = Double.parseDouble(ClearFormat(tvPaid.getText().toString()));
                        }
                        if (ClearFormat(tvChange.getText().toString()).isEmpty()) {
                            changeamount = 0;
                        } else {
                            changeamount = Double.parseDouble(ClearFormat(tvChange.getText().toString()));
                        }
                        if (selectInsertLibrary.OfflineCheck) {
                            insertdatatoLiteDb();
                        } else {
                            updateVoucher();
                        }

                        ConfirmedTranid = Long.parseLong("0");
                    }
                }
            });

            imgClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    comfirm = false;
                    salechange.dismiss();
                }
            });
            change.setView(v);
            salechange = change.create();
            salechange.show();
        } else {

            //This Customer's Credit Limit is Over.Do you want to continue ???
//            use_bluetooth =
            //bill_not_print = false;
            double outstandamt = net_amount + Double.parseDouble(ClearFormat(txtoutstand.getText().toString()));
            if (outstandamt > sale_entry.credit_limit && sale_entry.credit_limit != 0) {
                if (frmlogin.isallowovercreditlimit == 1) {
                    AlertDialog.Builder bd = new AlertDialog.Builder(datacontext, R.style.AlertDialogTheme);
                    bd.setTitle("iStock");
                    bd.setMessage("This Customer's Credit Limit is Over.Do you want to continue ???");
                    bd.setCancelable(false);
                    bd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            updateVoucher();
                            ConfirmedTranid = Long.parseLong("0");
                            dialog.dismiss();
                        }
                    });

                    bd.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    bd.create().show();
                } else {
                    AlertDialog.Builder bd = new AlertDialog.Builder(datacontext, R.style.AlertDialogTheme);
                    bd.setTitle("iStock");
                    bd.setMessage("This Customer's Credit Limit is Over");
                    bd.setCancelable(false);
                    bd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    bd.create().show();
                }
            } else {
                if (selectInsertLibrary.OfflineCheck) {
                    insertdatatoLiteDb();
                } else {
                    updateVoucher();
                }

                ConfirmedTranid = Long.parseLong("0");
            }
        }


    }

    public static void getSummary() {

        if (txttax.getText().toString().contains(getTax() + "")) {
            sh.get(0).setTax_per(getTax());
        }
        totalAmt_tmp = 0.0;
        qty_tmp = 0.0;
        vouDis_tmp = 0.0;
        itemDis_tmp = 0.0;
        paidAmt_tmp = 0.0;
        taxamt_tmp = 0.0;
        calresult_tmp = 0.0;
        netamt_tmp = 0.0;
        ItemdisTax_tmp = 0.0;
        foc_tmp = 0.0;
        if (sd.size() > 0) {
            for (int i = 0; i < sale_entry.sd.size(); i++) {
                totalAmt_tmp += sale_entry.sd.get(i).getUnit_qty() * sale_entry.sd.get(i).getSale_price();
                qty_tmp += sd.get(i).getUnit_qty();
                if (sd.get(i).getDis_type() == 1 || sd.get(i).getDis_type() == 2 || sd.get(i).getDis_type()
                        == 5) {
                    itemDis_tmp += (sd.get(i).getSale_price() - sd.get(i).getDis_price()) * sale_entry.sd.get(i).getUnit_qty();
                    sh.get(0).setIstemdis_amount(itemDis_tmp);
                } else if (sd.get(i).getDis_type() == 3 || sd.get(i).getDis_type() == 4 || sd.get(i).getDis_type()
                        == 6 || sd.get(i).getDis_type() == 7) {
                    foc_tmp += sd.get(i).getSale_price() * sale_entry.sd.get(i).getUnit_qty();
                    sh.get(0).setFoc_amount(foc_tmp);
                } else {
                    //itemDis_tmp=0;
                    sh.get(0).setIstemdis_amount(itemDis_tmp);
                    sh.get(0).setFoc_amount(foc_tmp);
                }

            }
            sh.get(0).setInvoice_qty(qty_tmp);
            sh.get(0).setInvoice_amount(totalAmt_tmp);

            vouDis_tmp = sh.get(0).getDiscount();
            paidAmt_tmp = sh.get(0).getPaid_amount();

            if (sh.get(0).getDiscount_per() > 0) {
                vouDis_tmp = sh.get(0).getDiscount_per();
                if (taxper_tmp > 0) {
//                    if (itemDis_tmp>0)
                    vouDis_tmp = (((totalAmt_tmp) * vouDis_tmp) / 100);
//                    else {
//                        vouDis_tmp = ((itemDis_tmp ) / 100);
//                    }
                } else {
//                    if (itemDis_tmp>0){
                    vouDis_tmp = (((totalAmt_tmp - itemDis_tmp) * vouDis_tmp) / 100);
//                    }
//                    else{
//                        vouDis_tmp = ((itemDis_tmp ) / 100);
//                    }

                }
                sh.get(0).setDiscount(vouDis_tmp);
            }

            taxper_tmp = sh.get(0).getTax_per();
            double calresult = 0.0;
            for (int i = 0; i < sd.size(); i++) {
                if (sd.get(i).getCalNoTax() == 0 && sd.get(i).getDis_type() != 3 && sd.get(i).getDis_type() != 4 && sd.get(i).getDis_type() != 6 && sd.get(i).getDis_type() != 7) {
                    calresult += sd.get(i).getSale_price() * sd.get(i).getUnit_qty();
                }
            }
            taxamt_tmp = calresult;

            if (Use_Tax == 0) {
                taxamt_tmp = 0;
            } else {
                if (Tax_Type == 0) {
                    if (taxper_tmp > 0) {
                        //added by EKK on 06-11-2020
                        if (frmmain.afterdiscount == 0)
                            taxamt_tmp = ((taxamt_tmp) * taxper_tmp) / caltax;
                        else
                            taxamt_tmp = ((taxamt_tmp - vouDis_tmp - itemDis_tmp) * taxper_tmp) / caltax;
                    } else
                        taxamt_tmp = 0;
                } else {
                    if (taxper_tmp > 0)
                        taxamt_tmp = ((taxamt_tmp) * taxper_tmp) / caltax;
                    else
                        taxamt_tmp = 0;
                }

            }


            //GetAppSetting getAppSetting=new GetAppSetting("Tax_Exclusive");
            //if(getAppSetting.getSetting_Value().equals("Y"))
            if (frmmain.isexclusivetax == 1) {
                if (taxamt_tmp != 0)
                    netamt_tmp = ((totalAmt_tmp - vouDis_tmp - itemDis_tmp - foc_tmp/* - paidAmt_tmp*/) + taxamt_tmp);
                else
                    netamt_tmp = (totalAmt_tmp - vouDis_tmp - itemDis_tmp - foc_tmp/* - paidAmt_tmp*/);
            } else {
                netamt_tmp = (totalAmt_tmp - vouDis_tmp - itemDis_tmp - foc_tmp/* - paidAmt_tmp*/);
                // taxamt_tmp=0;
            }

            if (sh.get(0).getPaidpercent() > 0) {
                paidAmt_tmp = netamt_tmp * sh.get(0).getPaidpercent() / 100;

            }
            netamt_tmp = netamt_tmp - paidAmt_tmp;
            sh.get(0).setPaid_amount(paidAmt_tmp);

        }
        tax_amount = taxamt_tmp;
        // txttaxamT.setText(String.valueOf(tax_amount));
        txttaxamt.setText(String.valueOf(tax_amount));
//        txttaxamt.setText(String.format("%,."+String.valueOf(tax_amount)+"f"));
        sh.get(0).setTax_amount(tax_amount);
        net_amount = netamt_tmp;
        if ((net_amount + paidAmt_tmp) < paidAmt_tmp) {
            AlertDialog.Builder bd = new AlertDialog.Builder(datacontext, R.style.AlertDialogTheme);
            bd.setTitle("iStock");
            bd.setMessage("Paid Amount is more than Net Amount");
            bd.setCancelable(false);
            bd.setPositiveButton("OK", (dialog, which) -> {
                paidAmt_tmp = net_amount + paidAmt_tmp;
                sh.get(0).setPaid_amount(paidAmt_tmp);
                getSummary();
                dialog.dismiss();
            });
            bd.create().show();
        } else {
            SummaryFormat(txtnet, netamt_tmp);
            SummaryFormat(txtvoudis, sh.get(0).getDiscount());
            SummaryFormat(txtpaidamt, paidAmt_tmp);
            SummaryFormat(txttotal, totalAmt_tmp);
            SummaryFormat(txtfoc, foc_tmp + itemDis_tmp + sh.get(0).getDiscount());
            sh.get(0).setTax_amount(taxamt_tmp);
            SummaryFormat(txttaxamt, tax_amount);
            SummaryFormat(txttaxamT, tax_amount);
        }
    }

    private static int getTaxType() {
        String sqlString = "select TaxCal from SystemSetting ";
        int TaxCal = 0;
        Cursor cursor = DatabaseHelper.rawQuery(sqlString);
        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    TaxCal = cursor.getInt(cursor.getColumnIndex("taxCal"));

                } while (cursor.moveToNext());

            }

        }
        cursor.close();
        return TaxCal;
    }

    private static int caltaxsetting() {
        String sqlString = "select Setting_Value from AppSetting where Setting_Name='CalTaxSetting'";
        int caltax = 100;
        String tax = "100";
        Cursor cursor = DatabaseHelper.rawQuery(sqlString);
        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    tax = cursor.getString(cursor.getColumnIndex("Setting_Value"));
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        caltax = Integer.parseInt(tax);
        return caltax;
    }

    public static void SummaryFormat(TextView source, double value) {
        String numberAsString = String.format("%,." + frmmain.price_places + "f", value);
        if (source != null) {
            source.setText(numberAsString);
        }
    }

    //key pad size change modified by ABBP
    private void showKeyPad(TextView txt, TextView source) {

        if (sd.size() == 0) {
            return;
        }
        startOpen = true;
        LayoutInflater inflater = (LayoutInflater) sale_entry.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.keypad, null);
        float density = sale_entry.this.getResources().getDisplayMetrics().density;
        final PopupWindow pw = new PopupWindow(layout, (int) density * 310, (int) density * 500, true);
        pw.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pw.setOutsideTouchable(false);
        pw.showAsDropDown(txt);
        Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btnc, btndec, btndel, btnenter, btnper;
        btn1 = layout.findViewById(R.id.txt1);
        btn2 = layout.findViewById(R.id.txt2);
        btn3 = layout.findViewById(R.id.txt3);
        btn4 = layout.findViewById(R.id.txt4);
        btn5 = layout.findViewById(R.id.txt5);
        btn6 = layout.findViewById(R.id.txt6);
        btn7 = layout.findViewById(R.id.txt7);
        btn8 = layout.findViewById(R.id.txt8);
        btn9 = layout.findViewById(R.id.txt9);
        btn0 = layout.findViewById(R.id.txt0);
        btnc = layout.findViewById(R.id.txtc);
        btndec = layout.findViewById(R.id.txtdec);
        btnenter = layout.findViewById(R.id.txtenter);
        btndel = layout.findViewById(R.id.txtdel);
        btndec.setEnabled(!frombillcount);
        btnper = layout.findViewById(R.id.btnpercent);
        TextView txtNum = layout.findViewById(R.id.txtNum);
        if (voudis || paiddis) btnper.setVisibility(View.VISIBLE);
        btnper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (startOpen == true) {
                    keynum = "";
                    startOpen = false;
                }
                txtNum.setText(keynum + btnper.getText());
                keynum = txtNum.getText().toString();
            }
        });
        keynum = ClearFormat(keynum);
        txtNum.setText(String.valueOf(keynum));

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startOpen == true) {
                    keynum = "";
                    startOpen = false;
                }

                txtNum.setText(keynum + btn1.getText());
                keynum = txtNum.getText().toString();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startOpen == true) {
                    keynum = "";
                    startOpen = false;
                }
                txtNum.setText(keynum + btn2.getText());
                keynum = txtNum.getText().toString();
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startOpen == true) {
                    keynum = "";
                    startOpen = false;
                }
                txtNum.setText(keynum + btn3.getText());
                keynum = txtNum.getText().toString();
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startOpen == true) {
                    keynum = "";
                    startOpen = false;
                }
                txtNum.setText(keynum + btn4.getText());
                keynum = txtNum.getText().toString();
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startOpen == true) {
                    keynum = "";
                    startOpen = false;
                }
                txtNum.setText(keynum + btn5.getText());
                keynum = txtNum.getText().toString();
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startOpen == true) {
                    keynum = "";
                    startOpen = false;
                }
                txtNum.setText(keynum + btn6.getText());
                keynum = txtNum.getText().toString();
            }
        });
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startOpen == true) {
                    keynum = "";
                    startOpen = false;
                }
                txtNum.setText(keynum + btn7.getText());
                keynum = txtNum.getText().toString();
            }
        });
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startOpen == true) {
                    keynum = "";
                    startOpen = false;
                }
                txtNum.setText(keynum + btn8.getText());
                keynum = txtNum.getText().toString();
            }
        });
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startOpen == true) {
                    keynum = "";
                    startOpen = false;
                }
                txtNum.setText(keynum + btn9.getText());
                keynum = txtNum.getText().toString();
            }
        });
        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startOpen == true) {
                    keynum = "";
                    startOpen = false;
                }
                txtNum.setText(keynum + btn0.getText());
                keynum = txtNum.getText().toString();
            }
        });
        btnc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startOpen == true) {
                    keynum = "";
                    startOpen = false;
                }
                txtNum.setText("0");
                keynum = txtNum.getText().toString();
            }
        });
        btndec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startOpen == true) {
                    keynum = "";
                    startOpen = false;
                }
                txtNum.setText(keynum + btndec.getText());
                keynum = txtNum.getText().toString();
            }
        });
        btndel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (keynum.length() != 0) {
                    keynum = keynum.substring(0, keynum.length() - 1);
                    txtNum.setText(keynum);


                }
                startOpen = false;

            }
        });
        btnenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    double voudisAmt = 0.0, paidpercentAmt = 0.0;
                    boolean voudispercent = false, paidpercent = false;
                    if (voudis) {
                        sh.get(0).setDiscount_per(0);
                        sh.get(0).setDiscount(0);
                        txtvoudis.setText("0");
                        getSummary();
                    }
                    if (paiddis) {
                        sh.get(0).setPaid_amount(0);
                        txtpaidamt.setText("0");
                        getSummary();
                    }
                    if (keynum.length() > 0) {

                        //added by EKK on 11-11-2020
                        if (source == txtChangeQty) {
                            if (frmmain.isusespecialprice == 1) {
                                View view = getLayoutInflater().inflate(R.layout.editinfo, null);
                                RecyclerView rcvSP = view.findViewById(R.id.rcvSP);
                                txtChangeQty.setText(keynum);
                                chooseSpecialPrice(rcvSP, sale_entry.this, sd.get(itemPosition).getCode(), sd.get(itemPosition).getUnit_type());
                            }

                        }

                        //added by EKK
                        if (source == tvBillCount) {
                            billprintcount = Integer.parseInt(keynum);
                        }

                        if (source == txtvoudis) {
                            if (keynum.contains("%")) {
                                keynum = keynum.substring(0, keynum.length() - 1);
                                voudisAmt = Double.parseDouble(keynum);
                                sh.get(0).setDiscount_per(voudisAmt);
                                voudisper = voudisAmt;
                                txtvouper.setText("Vou Discount" + (voudisper > 0 ? "( " + voudisper + "% )" : ""));
                                voudispercent = true;
                            } else {
                                voudDiscountString = "";
                                txtvouper.setText("Vou Discount");
                                sh.get(0).setDiscount_per(0);
                            }
                        } else if (source == txtpaidamt) {
                            if (keynum.contains("%")) {
                                keynum = keynum.substring(0, keynum.length() - 1);
                                paidpercentAmt = Double.parseDouble(keynum);
                                sh.get(0).setPaidpercent(paidpercentAmt);
                                paiddispercent = paidpercentAmt;
                                sale_entry.txtpaid.setText("Paid " + (paiddispercent > 0 ? "( " + paiddispercent + "% )" : ""));
                                paidpercent = true;
                            } else {
                                // voudDiscountString = "";
                                sale_entry.txtpaid.setText("Paid"

                                );
                                sh.get(0).setPaidpercent(0);
                            }
                        }

                        Double check = Double.parseDouble(keynum);
                        if (isqty) {
                            check = check > 0 ? check : 1.0;
                        }


                        if (paidpercent) {
                            if (paidpercentAmt > 100) {
                                sh.get(0).setPaid_amount(0);
                                sh.get(0).setPaidpercent(0);
                                txtpaidamt.setText("0");
                                AlertDialog.Builder builder = new AlertDialog.Builder(sale_entry.this, R.style.AlertDialogTheme);
                                builder.setTitle("iStock");
                                builder.setMessage(" Paid Percent should be up to 100% ");
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        getSummary();
                                        msg.dismiss();
                                    }
                                });
                                msg = builder.create();
                                msg.show();
                            }

                        }

//msg box and condition for voudisAmt modified by ABBP
                        if (voudispercent) {
                            if (voudisAmt > 100) {
                                sh.get(0).setDiscount(0);
                                sh.get(0).setDiscount_per(0);

                                AlertDialog.Builder builder = new AlertDialog.Builder(sale_entry.this, R.style.AlertDialogTheme);
                                builder.setTitle("iStock");
                                builder.setMessage(" Discount Percent should be up to 100% ");
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        getSummary();
                                        msg.dismiss();
                                    }
                                });
                                msg = builder.create();
                                msg.show();
                            }

                        } else {
                            System.out.println(source.getText().toString() + " this is source value");
                            if (source.getText().toString().contains("Tax")) {
                                userinputtax = check;
                                double tmpTax = sh.get(0).getTax_per();
                                if (userinputtax >= 100)
                                    userinputtax = check = tmpTax;
                                sh.get(0).setTax_per(check);
                                String tax = "Tax ( " + check + "% )";
                                if (userinputtax == 0.0) {
                                    userinputtax = -1;
                                }
                                taxchange = true;
                                txttax.setText(tax);
                                source.setText(tax);
                                txt.setText(String.valueOf(sh.get(0).getTax_amount()));

                                getSummary();

                            } else {
                                source.setText(String.valueOf(check));
                            }
                        }

                        if (changeheader) {

                            Double amt = Double.parseDouble(txtChangeQty.getText().toString()) * StringTODouble(txtChangePrice.getText().toString());
                            String numberAsString = String.format("%,." + frmmain.price_places + "f", amt);
                            //EKK
                            if (frmlogin.ishidesaleprice != 0)
                                txtamt.setText("****");
                            else
                                txtamt.setText(numberAsString);

                            sd.get(itemPosition).setUnit_qty(Double.parseDouble(txtChangeQty.getText().toString()));
                            sd.get(itemPosition).setSale_price(StringTODouble(txtChangePrice.getText().toString()));
                            changeheader = false;
                        } else {

                            String paid = ClearFormat(txtpaidamt.getText().toString());
                            String dis = ClearFormat(txtvoudis.getText().toString());
                            String net = ClearFormat(txtnet.getText().toString());
                            sh.get(0).setDiscount(Double.parseDouble(dis));
                            sh.get(0).setPaid_amount(Double.parseDouble(paid));

                        }
                        if (isqty) {
                            String numberAsString = String.format("%." + frmmain.qty_places + "f", Double.parseDouble(ClearFormat(source.getText().toString())));
                            source.setText(numberAsString);
                        } else {
                            if (!source.getText().toString().contains("Tax")) {
                                String numberAsString = String.format("%,." + frmmain.price_places + "f", Double.parseDouble(ClearFormat(source.getText().toString())));
                                source.setText(numberAsString);
                            }
                        }
                        isqty = false;
                        startOpen = false;
                        if (voudis) {
                            if (check > net_amount) {
                                sh.get(0).setDiscount_per(0);
                                sh.get(0).setDiscount(0);
                                txtvoudis.setText("0");
                                new AlertDialog.Builder(sale_entry.this, R.style.AlertDialogTheme)
                                        .setTitle("iStock")
                                        .setMessage("Voucher Discount is more than Net_Amount")
                                        .setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                getSummary();
                                            }
                                        }).create().show();

                            } else {
                                getSummary();
                            }
                        } else {
                            getSummary();
                        }
                        voudis = false;
                        paiddis = false;

                        if (fromSaleChange) {
                            if (Double.parseDouble(keynum) < Double.parseDouble(ClearFormat(txtnet.getText().toString())) && Double.parseDouble(keynum) > 0) {
                                AlertDialog.Builder bd = new AlertDialog.Builder(sale_entry.this, R.style.AlertDialogTheme);
                                bd.setTitle("iStock");
                                bd.setMessage("Paid Amount is less than Net Amount");
                                bd.setCancelable(false);
                                bd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
//                                        changeamount = 0;
//                                        tvChange.setText("0");
//                                        source.setText("0");
                                        changeamount = Double.parseDouble(keynum) - Double.parseDouble(ClearFormat(txtnet.getText().toString()));
                                        SummaryFormat(tvChange, changeamount);
                                        dialog.dismiss();
                                    }
                                });
                                bd.create().show();
                            } else {
                                changeamount = Double.parseDouble(keynum) - Double.parseDouble(ClearFormat(txtnet.getText().toString()));
                                SummaryFormat(tvChange, changeamount);
                            }

                        }
                        fromSaleChange = false;
                        if (frombillcount) {
                            changeamount = Integer.parseInt(keynum);
                            source.setText(String.valueOf(keynum));
                        }
                        frombillcount = false;
                        //keynum = "";
                        pw.dismiss();
                    }
                } catch (Exception e) {
                    keynum = "0";
                    startOpen = true;
                    if (isqty) {
                        keynum = "1";

                    }
                    txtNum.setText(keynum);
//                    AlertDialog.Builder bd=new AlertDialog.Builder(sale_entry.this,R.style.AlertDialogTheme);
//                    bd.setTitle("iStock");
//                    bd.setMessage("Number Format is incompatiable with data type");
//                    bd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            msg.dismiss();
//                        }
//                    });
//                    msg=bd.create();
//                    msg.show();
//                    pw.dismiss();
                    isqty = false;
                }
            }
        });

    }

    private String ClearFormat(String s) {
        return s.replace(",", "");
    }


    private void Confirm() {
        String formname = "sale_entry";
        String ip = sh_ip.getString("ip", "empty");
        String port = sh_port.getString("port", "empty");
        //String sqlUrl="http://" + ip + ":" + port + "/api/DataSync/SaveData";
        String sqlUrl = "http://" + ip + ":" + port + "/api/mobile/UploadData";
//        System.out.println(sh.get(0).setTranid(););
        sqlstring = "sale_head_tmp" + "&" + gson.toJson(sale_head_tmpsfordirect).toString()/*sh.get(0).getTranid()*/ + "&" + "sale_det_tmp" + "&" + gson.toJson(sale_det_tmpsfordirect).toString() + "&" + "salechange_history&" + gson.toJson(salechanges) + "&" + "usp_savevoucher&usp_confirmsaleentry&" + tranid + "&" + frmlogin.LoginUserid;
        Log.i("sale_entry", sqlstring);
        new SaveData().execute(sqlUrl, "record");
    }
    /*Added by abbp customer quick set up*/


    private void InsertCustomer() {
        String ip = sh_ip.getString("ip", "empty");
        String port = sh_port.getString("port", "empty");
        String sqlUrl = "http://" + ip + ":" + port + "/api/mobile/AddCustomer";
        new addCust().execute(sqlUrl);

    }


    private void PrintVoucher(long tranid) {

        //if (frmlogin.Confirm_PrintVou == 1 && bill_not_print == false && billprintcount > 0 && ConfirmedTranid > 0 && use_localprint) {
        if (bill_not_print == false && billprintcount > 0 && tranid > 0 && use_localprint) {
            pb.show();
            String sqlUrl = "";
            String localprinter_ip = sh_ip.getString("localprinterip", "empty");
            String port = sh_port.getString("port", "80");
            String printername = sh_printer.getString("printer", "Choose");
            String printertype = sh_ptype.getString("ptype", "A5");
            printertype = printertype.equals("Slip") ? "88mm" : printertype;

//            if (frmlogin.Cashier_PrinterType != -1 && !frmlogin.Cashier_Printer.equals("null")) {
//                printername = frmlogin.Cashier_Printer;
//                printertypeid = String.valueOf(frmlogin.Cashier_PrinterType);
//            }

            //sqlstring = "userid=" + frmlogin.LoginUserid + "&tranid=" + tranidfromcloud + "&net_amount=" + txtnet.getText().toString() + "&billcount=" + billprintcount + "&printername=" + printername + "&printertypeid=" + printertypeid + "&report=empty";
            Log.i("printertype", printertype);

            sqlstring = "userid=" + frmlogin.LoginUserid + "&tranid=" + tranid + "&papersize=" + printertype + "&printername=" + printername + "&billcount=" + billprintcount
                    + "&qtydecimal=" + frmmain.qty_places + "&pricedecimal=" + frmmain.price_places;

            try {
                sqlstring = URLEncoder.encode(sqlstring, "UTF-8").replace("+", "%20")
                        .replace("%26", "&").replace("%3D", "=")
                        .replace("%2C", ",");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            //sqlUrl = "http://" + ip + ":" + port + "/api/DataSync/GetData?" + sqlstring;
            sqlUrl = "http://" + localprinter_ip + "/api/mobile/PrintReport?" + sqlstring; // ip + ":" + port +
            requestQueue = Volley.newRequestQueue(this);

            final Response.Listener<String> listener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        AlertDialog.Builder b = new AlertDialog.Builder(sale_entry.this);
                        b.setTitle("iStock");
                        b.setMessage(response);
                        b.setCancelable(false);
                        b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                pb.dismiss();
                                dialog.dismiss();
                                if (sh.size() > 0) sh.clear();
                                if (sd.size() > 0) sd.clear();
                                if (comfirm) {
                                    intent = new Intent(sale_entry.this, sale_entry.class);
                                    startActivity(intent);
                                    finish();
                                } else if (logout) {
                                    intent = new Intent(getApplicationContext(), frmlogin.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    intent = new Intent(getApplicationContext(), frmsalelist.class);
                                    intent.putExtra("name", "Sale History");
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
                        dialog = b.create();
                        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                            @Override
                            public void onShow(DialogInterface dialog1) {
                                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
                            }
                        });
                        dialog.show();

                    } catch (Exception e) {
                        pb.dismiss();

                    }
                }

            };

            final Response.ErrorListener error = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    pb.dismiss();
                    AlertDialog.Builder b = new AlertDialog.Builder(sale_entry.this);
                    b.setTitle("iStock");
                    b.setMessage("Printer is not found!");
                    b.setCancelable(false);
                    b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (sh.size() > 0) sh.clear();
                            if (sd.size() > 0) sd.clear();
                            if (comfirm) {
                                intent = new Intent(sale_entry.this, sale_entry.class);
                                startActivity(intent);
                                finish();
                            } else if (logout) {
                                intent = new Intent(getApplicationContext(), frmlogin.class);
                                startActivity(intent);
                                finish();
                            } else {
                                intent = new Intent(getApplicationContext(), frmsalelist.class);
                                intent.putExtra("name", "Sale History");
                                startActivity(intent);
                                finish();
                            }

                            dialog.dismiss();

                        }
                    });
                    dialog = b.create();
                    dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                        @Override
                        public void onShow(DialogInterface dialog1) {
                            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
                        }
                    });
                    dialog.show();

                }
            };

            StringRequest req = new StringRequest(Request.Method.GET, sqlUrl, listener, error);
            DefaultRetryPolicy retryPolicy = new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            req.setRetryPolicy(retryPolicy);
            requestQueue.add(req);

        } else {
            if (sh.size() > 0) sh.clear();
            if (sd.size() > 0) sd.clear();
            if (comfirm) {
                intent = new Intent(sale_entry.this, sale_entry.class);
                startActivity(intent);
                finish();
            } else if (logout) {
                intent = new Intent(getApplicationContext(), frmlogin.class);
                startActivity(intent);
                finish();
            } else {
                intent = new Intent(this, frmsalelist.class);
                intent.putExtra("name", "Sale History");
                startActivity(intent);
                finish();
            }
        }

        billprintcount = 1; //added by EKK
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void ChangeVouDate() {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonty = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        pickerDialog = new DatePickerDialog(this, R.style.DatePickerDialog, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                try {
                    voudate = new SimpleDateFormat("dd/MM/yyyy").parse(selectedDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Date todate = new Date();
                if (voudate.getTime() <= todate.getTime()) {
                    txtdate.setText(new SimpleDateFormat("dd/MM/yyyy").format(voudate));
                    sh.get(0).setDate(dateFormat.format(voudate));
                } else {
                    Toast.makeText(getApplicationContext(), "You can't change greater than Today date", Toast.LENGTH_LONG).show();
                    txtdate.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
                }

            }
        }, mYear, mMonty, mDay);
        pickerDialog.show();

    }

    /*
        private void updateVoucher() {

            try {
                if (tranid < 0) {

                    if(sh.get(0).getInvoice_no().equals("NULL") || sh.get(0).getInvoice_no().equals(""))
                    {
                        invoice_no="NULL";
                    }
                    else
                    {
                        invoice_no="N'"+sh.get(0).getInvoice_no()+"'";

                    }

                    if(sh.get(0).getHeadremark().equals("NULL") || sh.get(0).getHeadremark().equals(""))
                    {
                        headRemark="NULL";
                    }
                    else
                    {
                        headRemark="N'"+sh.get(0).getHeadremark()+"'";

                    }
                    if(Use_Delivery)
                    {
                        if(chkDeliver.isChecked()) {
                            ToDeliver = ",deliver=1";
                        }
                        else
                        {
                            ToDeliver="";
                        }
                    }
                    else
                    {
                        ToDeliver="";
                    }

                    String head = "update Sale_Head_Main set\n" +
                            "tranid="+sh.get(0).getTranid()+",\n" +
                            "docid='"+sh.get(0).getDocid()+"',\n" +
                            "date=" +"'" + String.format(sh.get(0).getDate(), "yyyy-MM-dd") + "'," +
                            "invoice_no="+invoice_no+",\n" +
                            "locationid="+sh.get(0).getLocationid()+",\n" +
                            "customerid="+sh.get(0).getCustomerid()+",\n" +
                            "cash_id="+sh.get(0).getDef_cashid()+",\n" +
                            "townshipid="+sh.get(0).getTownshipid()+",\n"+
                            "pay_type="+sh.get(0).getPay_type()+",\n" +
                            "due_indays="+sh.get(0).getDue_in_days()+",\n" +
                            "currency=1,\n" +
                            "discount="+sh.get(0).getDiscount()+",\n" +
                            "paid_amount="+sh.get(0).getPaid_amount()+",\n" +
                            "invoice_amount="+sh.get(0).getInvoice_amount()+",\n" +
                            "invoice_qty="+sh.get(0).getInvoice_qty()+",\n" +
                            "foc_amount="+sh.get(0).getFoc_amount()+",\n" +
                            "itemdis_amount="+sh.get(0).getIstemdis_amount()+",\n" +
                            "net_amount="+net_amount+",\n" +
                            "Remark="+headRemark+",\n"+
                            "tax_amount="+sh.get(0).getTax_amount()+",\n" +
                            "tax_percent="+sh.get(0).getTax_per()+",\n" +
                            "discount_per="+sh.get(0).getDiscount_per()+",\n"+
                            "exg_rate="+1+"\n"+ToDeliver+
                            " where tranid="+sd.get(0).getTranid();

                    String det = "delete from sale_det where tranid="+sh.get(0).getTranid()+
                                " insert into Sale_Det(tranid,date,unit_qty,qty,sale_price,dis_price,dis_type,dis_percent,remark,unit_type,code,sr,srno,PriceLevel,SQTY,SPrice) values ";


                    for(int i=0;i<sd.size();i++)
                    {


                        if(sd.get(i).getDetremark().equals("NULL") || sd.get(i).getDetremark().equals("")){
                            detRemark="NULL";
                        }else {
                            detRemark="N'"+sd.get(i).getDetremark()+"'";
                        }


                        if (i < (sd.size() - 1))
                        {
                            det = det + "(" +
                                    sd.get(i).getTranid() + "," +
                                    "'" + String.format(sh.get(0).getDate(), "yyyy-MM-dd") + "'," +
                                    sd.get(i).getUnit_qty() + "," +
                                    sd.get(i).getQty() + "," +
                                    sd.get(i).getSale_price() + "," +
                                    sd.get(i).getDis_price() + "," +
                                    sd.get(i).getDis_type()+","+
                                    sd.get(i).getDis_percent()+ "," +
                                    detRemark+","+
                                    sd.get(i).getUnt_type() + "," +
                                    sd.get(i).getCode() + "," +
                                    (i + 1) + "," +
                                    (i + 1) + ",'"+
                                    sd.get(i).getPriceLevel()+"',"+
                                    getSmallestQty(sd.get(i).getCode(),sd.get(i).getUnit_qty(),sd.get(i).getUnt_type())+","+
                                    getSPrice(sd.get(i).getCode()) +" ),";


                        }

                        else {
                            det = det + "(" +
                                    sd.get(i).getTranid() + "," +
                                    "'" + String.format(sd.get(0).getDate(), "yyyy-MM-dd") + "'," +
                                    sd.get(i).getUnit_qty() + "," +
                                    sd.get(i).getQty() + "," +
                                    sd.get(i).getSale_price() + "," +
                                    sd.get(i).getDis_price() + "," +
                                    sd.get(i).getDis_type()+","+
                                    sd.get(i).getDis_percent()+ "," +
                                    detRemark+","+
                                    sd.get(i).getUnt_type() + "," +
                                    sd.get(i).getCode() + "," +
                                    (i + 1)+ "," +
                                    (i + 1) + ",'"+
                                    sd.get(i).getPriceLevel()+"',"+
                                    getSmallestQty(sd.get(i).getCode(),sd.get(i).getUnit_qty(),sd.get(i).getUnt_type())+","+
                                    getSPrice(sd.get(i).getCode()) +" )";
                        }

                    }
                    sh.get(0).getDiscount_per();
                    sh.get(0).setDiscount_per(0.0);
                    custDis=sh.get(0).getDiscount_per();
                    sqlstring = head +" "+det;
                    if(use_salesperson && SaleVouSalesmen.size()>0)
                    {
                        String salePerson=" delete from SalesVoucher_Salesmen_Tmp where Sales_TranID="+sh.get(0).getTranid()+" and userid="+frmlogin.LoginUserid+
                                " insert into SalesVoucher_Salesmen_Tmp(Sales_TranID,Salesmen_ID,rmt_copy,userid)"+
                                "values ";
                        for(int i=0;i<SaleVouSalesmen.size();i++)
                        {
                            salePerson=salePerson+"("+
                                    sh.get(0).getTranid()+","+
                                    SaleVouSalesmen.get(i).getSalesmen_Id()+","+
                                    "1,"+frmlogin.LoginUserid+"),";
                        }
                        salePerson=salePerson.substring(0,salePerson.length()-1);
                        sqlstring=sqlstring+" "+salePerson;
                    }

                    Confirm();
                    SaleVouSalesmen.clear();

                }
            }
            catch (Exception ee)
            {

            }

        }
    */
    private void updateVoucher() {
        sale_det_tmpsfordirect.clear();
        sale_head_tmpsfordirect.clear();
        salechanges.clear();
        boolean isdeliver = false;

        try {
            if (tranid > 0) {

                if (sh.get(0).getInvoice_no().equals("NULL") || sh.get(0).getInvoice_no().equals("")) {
                    invoice_no = null;
                } else {
                    invoice_no =/*"N'"+*/sh.get(0).getInvoice_no()/*+"'"*/;

                }
                System.out.println(sh.get(0).getHeadremark());
                if (sh.get(0).getHeadremark() != null) {
                    if (sh.get(0).getHeadremark().equals("NULL") || sh.get(0).getHeadremark().equals("")) {
                        headRemark = null;
                    } else {
                        headRemark = /*"N'" + */sh.get(0).getHeadremark() /*+ "'"*/;

                    }
                }
                if (Use_Delivery) {
                    if (chkDeliver.isChecked()) {
                        isdeliver = true;
                    } else {
                        ToDeliver = "";
                    }
                } else {
                    ToDeliver = "";
                }
//            Integer a=null;
//            int b=a.intValue();
//            System.out.println(b);
//Toast.makeText(getApplicationContext(),"this is invoice no "+invoice_no+sh.get(0).getTownshipid(),Toast.LENGTH_LONG).show();
                SetDefaultLocation();
                String salesmenids = "";
                if (SaleVouSalesmen.size() > 0) {
                    for (int i = 0; i < SaleVouSalesmen.size() - 1; i++) {
                        salesmenids += SaleVouSalesmen.get(i).getSalesmen_Id() + ",";
                    }
                    salesmenids += SaleVouSalesmen.get(SaleVouSalesmen.size() - 1).getSalesmen_Id() + "";
                }

                sale_head_tmpsfordirect.add(new sale_head_tmp((int) sh.get(0).getTranid(),
                        frmlogin.LoginUserid,
                        sh.get(0).getDocid(),
                        String.format(sh.get(0).getDate(), "yyyy-MM-dd"),
                        invoice_no,
                        (int) sh.get(0).getLocationid(),
                        (int) sh.get(0).getCustomerid(),
                        (int) sh.get(0).getDef_cashid(),
                        (int) sh.get(0).getTownshipid(),
                        (int) sh.get(0).getPay_type(),
                        sh.get(0).getDue_in_days(),
                        1,
                        sh.get(0).getDiscount(),
                        sh.get(0).getPaid_amount(),
                        sh.get(0).getInvoice_amount(),
                        sh.get(0).getInvoice_qty(),
                        sh.get(0).getFoc_amount(),
                        net_amount,
                        headRemark,
                        sh.get(0).getTax_amount(),
                        sh.get(0).getTax_per(),
                        sh.get(0).getDiscount_per(),
                        1,
                        false,
                        isdeliver,
                        salesmenids/*==0?(Integer)null:sh.get(0).getSalesmenid()*/, 0,
                        sh.get(0).getPaidpercent()
                ));

//
//            String head = "update sale_head_tmp set " +
//                    "tranid="+sh.get(0).getTranid()+"," +
//                    "docid='"+sh.get(0).getDocid()+"'," +
//                    "date=" +"'" + String.format(sh.get(0).getDate(), "yyyy-MM-dd") + "'," +
//                    "invoiceno="+invoice_no+"," +
//                    "locationid="+sh.get(0).getLocationid()+"," +
//                    "customerid="+sh.get(0).getCustomerid()+"," +
//                    "cashid="+sh.get(0).getDef_cashid()+"," +
//                    "townshipid="+sh.get(0).getTownshipid()+","+
//                    "paytypeid="+sh.get(0).getPay_type()+"," +
//                    "dueindays="+sh.get(0).getDue_in_days()+"," +
//                    "salecurr=1," +
//                    "discountamount="+sh.get(0).getDiscount()+"," +
//                    "paidamount="+sh.get(0).getPaid_amount()+"," +
//                    "invoiceamount="+sh.get(0).getInvoice_amount()+"," +
//                    "invoiceqty="+sh.get(0).getInvoice_qty()+"," +
//                    "focamount="+sh.get(0).getFoc_amount()+"," +
//                    //"itemdis_amount="+sh.get(0).getIstemdis_amount()+",\n" +
//                    "netamount="+net_amount+"," +
//                    "voucherremark="+headRemark+","+
//                    "taxamount="+sh.get(0).getTax_amount()+"," +
//                    "taxpercent="+sh.get(0).getTax_per()+"," +
//                    "discountpercent="+sh.get(0).getDiscount_per()+","+
//                    "exgrate="+1+""+
//                     ToDeliver+
//                    " where tranid="+sd.get(0).getTranid();

//            ContentValues contentValues=new ContentValues();
//            contentValues.put("trandid",sh.get(0).getTranid());

                //String det = "delete from sale_det_tmp where tranid="+sh.get(0).getTranid()+
                //   " insert into sale_det_tmp(tranid,date,unit_qty,qty,sale_price,dis_price,dis_type,dis_percent,remark,unit_type,code,sr,srno,PriceLevel,SQTY,SPrice) values ";
//            String det = ";delete from sale_det_tmp where tranid="+sh.get(0).getTranid()+
//                    ";insert into sale_det_tmp(tranid,unitqty,qty,saleprice,discountamount,itemdiscounttypeid,discountpercent,remark,unittypeid,code,sr,srno) values ";
//
//            String salechange=";delete from salechange_history where tranid="+sh.get(0).getTranid()+
//                    "; insert into salechange_history(tranid,currencyid,paidamount,changeamount,exgrate,invoiceamount) values ";

//            salechange=salechange + "(" +sh.get(0).getTranid()+","+1+","+paid+","+changeamount+","+1+","+sh.get(0).getInvoice_amount()+");";

                salechanges.add(new salechange((int) sh.get(0).getTranid(),
                        1,
                        paidamount,
                        changeamount,
                        1,
                        sh.get(0).getInvoice_amount()));

                short pricelevelid = 0;

//String pricelevelid=null;

                for (int i = 0; i < sd.size(); i++) {
//                pricelevelid=null;
                    pricelevelid = 0;

                    if (sd.get(i).getDetremark().equals("NULL") || sd.get(i).getDetremark().equals("")) {
                        detRemark = null;
                    } else {
                        detRemark =/*"N'"+*/sd.get(i).getDetremark()/*+"'"*/;
                    }


//                if (i < (sd.size() - 1))
//                {


//                switch (sd.get(i).getPriceLevel()){
////                    case "SP":pricelevelid="0";break;
////                    case "SP1":pricelevelid="1";break;
////                    case "SP2":pricelevelid="2";break;
////                    case "SP3":pricelevelid="3";break;
////                    case "SP4":pricelevelid="4";break;
////                    case "SP5":pricelevelid="5";break;
//                    case "SP":pricelevelid=0;break;
//                    case "SP1":pricelevelid=1;break;
//                    case "SP2":pricelevelid=2;break;
//                    case "SP3":pricelevelid=3;break;
//                    case "SP4":pricelevelid=4;break;
//                    case "SP5":pricelevelid=5;break;
//                }

//                if(selectInsertLibrary.GettingPriceLevelId(sd.get(i).getPriceLevel())==-1){
////                    short plevel;
//                    sale_det_tmpsfordirect.add(new sale_det_tmp((int)sd.get(i).getTranid(),
//                            sd.get(i).getUnit_qty(),
//                            sd.get(i).getQty(),
//                            sd.get(i).getSale_price(),
//                            sd.get(i).getDis_price(),
//                            (int)sd.get(i).getDis_type(),
//                            sd.get(i).getDis_percent(),
//                            detRemark,
//                            sd.get(i).getUnt_type(),
//                            (int)sd.get(i).getCode(),
//                            (i + 1),
//                            (i + 1),));
//
//            }
//                if(selectInsertLibrary.GettingPriceLevelId(sd.get(i).getPriceLevel())!=-1){
//                if(selectInsertLibrary.GettingPriceLevelId(sd.get(i).getPriceLevel())==0){
//                    sale_det_tmpsfordirect.add(new sale_det_tmp((int)sd.get(i).getTranid(),
//                            sd.get(i).getUnit_qty(),
//                            sd.get(i).getQty(),
//                            sd.get(i).getSale_price(),
//                            sd.get(i).getDis_price(),
//                            (int)sd.get(i).getDis_type(),
//                            sd.get(i).getDis_percent(),
//                            detRemark,
//                            sd.get(i).getUnt_type(),
//                            (int)sd.get(i).getCode(),
//                            (i + 1),
//                            (i + 1)));
//                    System.out.println("this is sp");
//                }else{
                    sale_det_tmpsfordirect.add(new sale_det_tmp((int) sd.get(i).getTranid(),
                            sd.get(i).getUnit_qty(),
                            sd.get(i).getQty(),
                            sd.get(i).getSale_price(),
                            sd.get(i).getDis_price(),
                            (int) sd.get(i).getDis_type(),
                            sd.get(i).getDis_percent(),
                            detRemark,
                            sd.get(i).getUnit_type(),
                            (int) sd.get(i).getCode(),
                            (i + 1),
                            (i + 1), selectInsertLibrary.GettingPriceLevelId(sd.get(i).getPriceLevel()),
                            false,
                            (int) sh.get(0).getLocationid()));
//                }

//                    Toast.makeText(getApplicationContext(),"this is price level"+sd.get(i).getPriceLevel(),Toast.LENGTH_LONG).show();
//                }else{
//                    sale_det_tmpsfordirect.add(new sale_det_tmp((int)sd.get(i).getTranid(),
//                            sd.get(i).getUnit_qty(),
//                            sd.get(i).getQty(),
//                            sd.get(i).getSale_price(),
//                            sd.get(i).getDis_price(),
//                            (int)sd.get(i).getDis_type(),
//                            sd.get(i).getDis_percent(),
//                            detRemark,
//                            sd.get(i).getUnt_type(),
//                            (int)sd.get(i).getCode(),
//                            (i + 1),
//                            (i + 1),sd.get(i).getPricelevelid()));
//                }


//                    det = det + "(" +
//                            sd.get(i).getTranid() + "," +
//                            sd.get(i).getUnit_qty() + "," +
//                            sd.get(i).getQty() + "," +
//                            sd.get(i).getSale_price() + "," +
//                            sd.get(i).getDis_price() + "," +
//                            sd.get(i).getDis_type()+","+
//                            sd.get(i).getDis_percent()+ "," +
//                            detRemark+","+
//                            sd.get(i).getUnt_type() + "," +
//                            sd.get(i).getCode() + "," +
//                            (i + 1) + "," +
//                            (i + 1) +" ),";

//                }

//                else {
                        /*
                        det = det + "(" +
                                sd.get(i).getTranid() + "," +
                                "'" + String.format(sd.get(0).getDate(), "yyyy-MM-dd") + "'," +
                                sd.get(i).getUnit_qty() + "," +
                                sd.get(i).getQty() + "," +
                                sd.get(i).getSale_price() + "," +
                                sd.get(i).getDis_price() + "," +
                                sd.get(i).getDis_type()+","+
                                sd.get(i).getDis_percent()+ "," +
                                detRemark+","+
                                sd.get(i).getUnt_type() + "," +
                                sd.get(i).getCode() + "," +
                                (i + 1)+ "," +
                                (i + 1) + ",'"+
                                sd.get(i).getPriceLevel()+"',"+
                                getSmallestQty(sd.get(i).getCode(),sd.get(i).getUnit_qty(),sd.get(i).getUnt_type())+","+
                                getSPrice(sd.get(i).getCode()) +" )";

                         */

//                    det = det + "(" +
//                            sd.get(i).getTranid() + "," +
//                            sd.get(i).getUnit_qty() + "," +
//                            sd.get(i).getQty() + "," +
//                            sd.get(i).getSale_price() + "," +
//                            sd.get(i).getDis_price() + "," +
//                            sd.get(i).getDis_type()+","+
//                            sd.get(i).getDis_percent()+ "," +
//                            detRemark+","+
//                            sd.get(i).getUnt_type() + "," +
//                            sd.get(i).getCode() + "," +
//                            (i + 1) + "," +
//                            (i + 1) +" )";
//                }

                }
//            sh.get(0).getDiscount_per();
//            sh.get(0).setDiscount_per(0.0);
//            custDis=sh.get(0).getDiscount_per();
//            sqlstring = head +" "+salechange+" "+det;
//            if(use_salesperson && SaleVouSalesmen.size()>0)
//            {
//                String salePerson=" delete from SalesVoucher_Salesmen_Tmp where Sales_TranID="+sh.get(0).getTranid()+" and userid="+frmlogin.LoginUserid+
//                        " insert into SalesVoucher_Salesmen_Tmp(Sales_TranID,Salesmen_ID,rmt_copy,userid)"+
//                        "values ";
//                for(int i=0;i<SaleVouSalesmen.size();i++)
//                {
//                    salePerson=salePerson+"("+
//                            sh.get(0).getTranid()+","+
//                            SaleVouSalesmen.get(i).getSalesmen_Id()+","+
//                            "1,"+frmlogin.LoginUserid+"),";
//                }
//                salePerson=salePerson.substring(0,salePerson.length()-1);
//                sqlstring=sqlstring+" "+salePerson;
            }

            Confirm();
            //SaleVouSalesmen.clear();

//        }
        } catch (Exception ee) {
            System.out.println(ee.toString() + "error");
        }

    }


    private double getSmallestQty(long code, double unit_qty, int unt_type) {
        double sqty = 0;
        Cursor cursor = DatabaseHelper.rawQuery("select smallest_unit_qty from usr_code where code=" + code + " and unit_type=" + unt_type);
        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    sqty = cursor.getDouble(cursor.getColumnIndex("smallest_unit_qty"));

                } while (cursor.moveToNext());


            }

        }
        cursor.close();
        return sqty * unit_qty;
    }

    private String GetSalesmenids() {
        String salesmenids = "";
        if (SaleVouSalesmen.size() > 0) {
            for (int i = 0; i < SaleVouSalesmen.size() - 1; i++) {
                salesmenids += SaleVouSalesmen.get(i).getSalesmen_Id() + ",";
            }
            salesmenids += SaleVouSalesmen.get(SaleVouSalesmen.size() - 1).getSalesmen_Id() + "";
        }
        return salesmenids;
    }

    private double getSPrice(long code) {
        double sPrice = 0;
        Cursor cursor = DatabaseHelper.rawQuery("select sale_price from usr_code where code=" + code + " and unit_type=(select max(unit_type) from usr_code where code=" + code + ")");
        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    sPrice = cursor.getDouble(cursor.getColumnIndex("sale_price"));

                } while (cursor.moveToNext());


            }

        }
        cursor.close();
        return sPrice;

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        itemPosition = position;
        EditInfo();
        return false;
    }

    @SuppressLint("WrongViewCast")
    public void EditInfo() {
        Double changeprice = 0.0;

        if (itemPosition > -1 && sd.size() > 0) {
            changeheader = true;
            Cursor cursor = null;
            String sqlString = "";
            AlertDialog.Builder builder = new AlertDialog.Builder(sale_entry.this, R.style.AlertDialogTheme);
            View view = getLayoutInflater().inflate(R.layout.editinfo, null);
            builder.setCancelable(false);
            builder.setView(view);
            rlUnit = view.findViewById(R.id.rlUnit);
            rlLevel = view.findViewById(R.id.rllevel);
            txtShowSP = view.findViewById(R.id.showSP);
            txtShowSP.setText(sd.get(itemPosition).getPriceLevel());
            TextView txtheader = view.findViewById(R.id.caption);
            TextView txtsqty = view.findViewById(R.id.txtChangeSQty);
            txtChangePrice = view.findViewById(R.id.txtChangePrice);
            txtamt = view.findViewById(R.id.txtChangeAmt);
            String title = sd.get(itemPosition).getDesc();
            txtheader.setText(title);
            txtshowUnit = view.findViewById(R.id.showpkg);
            txtshowUnit.setText(sd.get(itemPosition).getUnit_short());
            txtChangeQty = view.findViewById(R.id.txtChangeQty);
            txtChangeQty.setText(String.valueOf(sd.get(itemPosition).getUnit_qty()));
            ImageButton imgaddQty, imgsubQty;
            RecyclerView rcvUnit = view.findViewById(R.id.rcvUnit);
            RecyclerView rcvSP = view.findViewById(R.id.rcvSP);
            btndiscount = view.findViewById(R.id.btndiscountype);
            ArrayList<unitforcode> cg = new ArrayList<>();

//            sd.get(itemPosition).setDis_price(0);
            boolean use_multipricelvl = false;
            boolean use_unit = false;

            Cursor cursorplvl = DatabaseHelper.rawQuery("select isusemultipricelvl,isuseunit from SystemSetting");
            if (cursorplvl != null && cursorplvl.getCount() != 0) {
                if (cursorplvl.moveToFirst()) {
                    do {
                        use_multipricelvl = cursorplvl.getInt(cursorplvl.getColumnIndex("isusemultipricelvl")) == 1 ? true : false;
                        use_unit = cursorplvl.getInt(cursorplvl.getColumnIndex("isuseunit")) == 1 ? true : false;
                    } while (cursorplvl.moveToNext());


                }

            }
            cursorplvl.close();


            if (frmlogin.candiscount == 0) {
                btndiscount.setEnabled(false);
            } else {
                btndiscount.setEnabled(true);
            }

            if (use_multipricelvl) {

                //binding pricelevel
                rlLevel.setVisibility(View.VISIBLE);
                int row_index = -1;
                switch (sd.get(itemPosition).getPriceLevel()) {
                    case "SP":
                        row_index = 0;
                        break;
                    case "SP1":
                        row_index = 1;
                        break;
                    case "SP2":
                        row_index = 2;
                        break;
                    case "SP3":
                        row_index = 3;
                        break;
                    case "SP4":
                        row_index = 4;
                        break;
                    case "SP5":
                        row_index = 5;
                        break;

                }
                pad = new priceLevelAdapter(sale_entry.this, itemPosition, txtChangePrice, txtChangeQty, txtamt, btndiscount, row_index);
                rcvSP.setAdapter(pad);
                GridLayoutManager gridPricelevel = new GridLayoutManager(getApplicationContext(), 4);
                rcvSP.setLayoutManager(gridPricelevel);
            } else {
                rlLevel.setVisibility(View.GONE);
            }

            //binding unit
            if (use_unit) {
                rlUnit.setVisibility(View.VISIBLE);
                if (cg.size() > 0) {
                    cg.clear();
                }
                sqlString = "select * from Usr_Code where unit_type>0 and code=" + sd.get(itemPosition).getCode() + " order by unit_type ";
                cursor = DatabaseHelper.rawQuery(sqlString);
                if (cursor != null && cursor.getCount() != 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            int code = cursor.getInt(cursor.getColumnIndex("code"));
                            String usr_code = cursor.getString(cursor.getColumnIndex("usr_code"));
                            int unit_type = cursor.getShort(cursor.getColumnIndex("unit_type"));
                            int unit = cursor.getInt(cursor.getColumnIndex("unit"));
                            String unitname = cursor.getString(cursor.getColumnIndex("unitname"));
                            String shortdes = cursor.getString(cursor.getColumnIndex("unitshort"));
                            double saleprice = cursor.getDouble(cursor.getColumnIndex("sale_price"));
                            double saleprice1 = cursor.getDouble(cursor.getColumnIndex("saleprice1"));
                            double saleprice2 = cursor.getDouble(cursor.getColumnIndex("saleprice2"));
                            double saleprice3 = cursor.getDouble(cursor.getColumnIndex("saleprice3"));
                            double saleprice4 = cursor.getDouble(cursor.getColumnIndex("saleprice4"));
                            double saleprice5 = cursor.getDouble(cursor.getColumnIndex("saleprice5"));

                            double sqty = cursor.getDouble((cursor.getColumnIndex("smallest_unit_qty")));
                            cg.add(new unitforcode(code, usr_code, unit_type, unit, unitname, shortdes, saleprice, saleprice1, saleprice2, saleprice3, saleprice4, saleprice5, sqty));
// public unitforcode(int code, String usr_code, int unit_type, int unit, String unitname, String shortdes, double sale_price, double smallest_unit_qty, double sale_Price1, double sale_price2, double sale_price3) {
                        } while (cursor.moveToNext());

                    }
                }
                cursor.close();
                int row_index = -1;
//Added by abbp default unit type on 09/7/2019
                if (defunit == 1) {
                    row_index = 0;
                } else if (defunit == 2) {
                    row_index = 1;
                } else if (defunit == 3) {
                    row_index = 2;
                }


                switch (sd.get(itemPosition).getUnit_type()) {
                    case 1:
                        row_index = 0;
                        defunit = -1;
                        break;
                    case 2:
                        row_index = 1;
                        defunit = -1;
                        break;
                    case 3:
                        row_index = 2;
                        defunit = -1;
                        break;
                }


                uad = new UnitAdapter(sale_entry.this, cg, itemPosition, txtChangePrice, txtsqty, txtChangeQty, txtamt, btndiscount, row_index);
                rcvUnit.setAdapter(uad);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 4);
                rcvUnit.setLayoutManager(gridLayoutManager);
                cursor = null;
            } else {
                rlUnit.setVisibility(View.GONE);
            }

            imgaddQty = view.findViewById(R.id.imgAddqty);
            imgsubQty = view.findViewById(R.id.imgSubqty);


            //Initialize Dis_Type
            sqlString = "select * from Dis_Type where itemdiscounttypeid= " + sd.get(itemPosition).getDis_type();
            cursor = DatabaseHelper.rawQuery(sqlString);
            if (cursor != null && cursor.getCount() != 0) {
                if (cursor.moveToFirst()) {
                    do {
                        long dis_type = cursor.getLong(cursor.getColumnIndex("itemdiscounttypeid"));
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String shortname = cursor.getString(cursor.getColumnIndex("shortdesc"));
                        int paid = cursor.getInt(cursor.getColumnIndex("ispaid"));
                        long discount = cursor.getLong(cursor.getColumnIndex("discount"));
                        if (dis_type == 5) {
                            if (sd.get(itemPosition).getDis_percent() > 0) {
                                btndiscount.setText(sd.get(itemPosition).getDis_percent() + "%");

                            } else {
                                double dis = sd.get(itemPosition).getSale_price() - sd.get(itemPosition).getDis_price();

                                btndiscount.setText(String.valueOf(dis));
                            }
                        } else {
                            btndiscount.setText(name);
                        }
//                        itemdis=btndiscount.getText().toString();
                    } while (cursor.moveToNext());

                }

            } else {
                // disDa.dismiss();
            }

            cursor.close();
            btndiscount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {

                        String sqlString;
                        Cursor cursor = null;
                        AlertDialog.Builder bd = new AlertDialog.Builder(sale_entry.this, R.style.AlertDialogTheme);
                        View view = getLayoutInflater().inflate(R.layout.changeheadervalue, null);
                        bd.setView(view);
                        RecyclerView rv = view.findViewById(R.id.rcvChange);
                        ImageButton imgClose = view.findViewById(R.id.imgNochange);
                        EditText etdSearch = view.findViewById(R.id.etdSearch);
                        ImageButton imgSearch = view.findViewById(R.id.imgSearch);
                        etdSearch.setVisibility(View.GONE);
                        imgSearch.setVisibility(View.GONE);
                        disDa = bd.create();
                        imgClose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                disDa.dismiss();
                            }
                        });
                        ArrayList<Dis_Type> cg = new ArrayList<>();
                        if (cg.size() > 0) {
                            cg.clear();
                        }

                        sqlString = "select * from Dis_Type ";
                        cursor = DatabaseHelper.rawQuery(sqlString);
                        if (cursor != null && cursor.getCount() != 0) {
                            if (cursor.moveToFirst()) {
                                do {
                                    long dis_type = cursor.getLong(cursor.getColumnIndex("itemdiscounttypeid"));
                                    String name = cursor.getString(cursor.getColumnIndex("name"));
                                    String shortname = cursor.getString(cursor.getColumnIndex("shortdesc"));
                                    int paid = cursor.getInt(cursor.getColumnIndex("ispaid"));
                                    long discount = cursor.getLong(cursor.getColumnIndex("discount"));
                                    cg.add(new Dis_Type(dis_type, name, shortname, discount));
                                } while (cursor.moveToNext());

                            }

                        } else {
                            disDa.dismiss();
                        }

                        cursor.close();

                        DisTypeAdapter ad = new DisTypeAdapter(sale_entry.this, cg, btndiscount, disDa, itemPosition, txtChangeQty);
                        rv.setAdapter(ad);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 4);
                        rv.setLayoutManager(gridLayoutManager);
                        cursor = null;
                        disDa.show();


                    } catch (Exception e) {
                        //disDa.dismiss();
                    }
                }
            });
//add detremark in editinfo
            detremark = view.findViewById(R.id.txtDetRemark);
            detremark.setText(sd.get(itemPosition).getDetremark() == "NULL" ? "" : sd.get(itemPosition).getDetremark());


            txtsqty.setText(String.valueOf(sd.get(itemPosition).getQty()));
            imgaddQty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    txtChangeQty.setText(String.valueOf(Double.parseDouble(txtChangeQty.getText().toString()) + 1));
                    sd.get(itemPosition).setUnit_qty(Double.parseDouble(txtChangeQty.getText().toString()));
                    Double amt = Double.parseDouble(txtChangeQty.getText().toString()) * StringTODouble(txtChangePrice.getText().toString());
                    String numberAsString = String.format("%,." + frmmain.price_places + "f", amt);

                    //18-11-2020
                    if (frmlogin.ishidesaleprice != 0)
                        txtamt.setText("****");
                    else
                        txtamt.setText(numberAsString);

                    double sqty = sd.get(itemPosition).getQty() * Double.parseDouble(txtChangeQty.getText().toString());
                    txtsqty.setText(String.valueOf(sqty));

                    if (frmmain.isusespecialprice == 1)
                        chooseSpecialPrice(rcvSP, sale_entry.this, sd.get(itemPosition).getCode(), sd.get(itemPosition).getUnit_type());
                }
            });

            imgsubQty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Double.parseDouble(txtChangeQty.getText().toString()) >= 2) {
                        txtChangeQty.setText(String.valueOf(Double.parseDouble(txtChangeQty.getText().toString()) - 1));
                        sd.get(itemPosition).setUnit_qty(Double.parseDouble(txtChangeQty.getText().toString()));
                        Double amt = Double.parseDouble(txtChangeQty.getText().toString()) * StringTODouble(txtChangePrice.getText().toString());
                        String numberAsString = String.format("%,." + frmmain.price_places + "f", amt);
                        //18-11-2020
                        if (frmlogin.ishidesaleprice != 0)
                            txtamt.setText("****");
                        else
                            txtamt.setText(String.valueOf(numberAsString));

                        double sqty = sd.get(itemPosition).getQty() * Double.parseDouble(txtChangeQty.getText().toString());
                        txtsqty.setText(String.valueOf(sqty));

                        if (frmmain.isusespecialprice == 1)
                            chooseSpecialPrice(rcvSP, sale_entry.this, sd.get(itemPosition).getCode(), sd.get(itemPosition).getUnit_type());
                    }
                }
            });

            txtChangeQty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isqty = true;
                    changeheader = true;
                    keynum = txtChangeQty.getText().toString();
                    showKeyPad(txtChangeQty, txtChangeQty);
                    double sqty = sd.get(itemPosition).getQty() * Double.parseDouble(txtChangeQty.getText().toString());
                    txtsqty.setText(String.valueOf(sqty));

                    if (frmmain.isusespecialprice == 1)
                        chooseSpecialPrice(rcvSP, sale_entry.this, sd.get(itemPosition).getCode(), sd.get(itemPosition).getUnit_type());
                }

            });


//not change_price in sale entry modified by ABBP
            //txtChangePrice.setText(String.valueOf(sd.get(itemPosition).getSale_price()));


            //modified by EKK on 18-11-2020
            tmpSalePrice = sd.get(itemPosition).getSale_price();

            if (frmlogin.ishidesaleprice != 0) {
                txtChangePrice.setText("****");
                txtChangePrice.setEnabled(false);
            } else {
                SummaryFormat(txtChangePrice, sd.get(itemPosition).getSale_price());
                txtChangePrice.setEnabled(true);
            }


            ImageButton imgChangePrice = view.findViewById(R.id.imgChangePrice);
            imgChangePrice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (frmlogin.canchangesaleprice == 1) {
                        imgChangePrice.setEnabled(false);

                        changeheader = true;

                        keynum = String.format("%,." + frmmain.price_places + "f", StringTODouble(txtChangePrice.getText().toString()));
                        showKeyPad(txtChangeQty, txtChangePrice);
                    }
                }
            });
//not change_price in sale entry modified by ABBP
            //open_price add by ABBP
            txtChangePrice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int op = sd.get(itemPosition).getOpen_price();

                    if (frmlogin.canchangesaleprice == 1 || op == 1) {
                        changeheader = true;
                        keynum = String.format("%,." + frmmain.price_places + "f", StringTODouble(txtChangePrice.getText().toString()));
                        showKeyPad(txtChangeQty, txtChangePrice);
                    }

                    Double amt = Double.parseDouble(txtChangeQty.getText().toString()) * StringTODouble(txtChangePrice.getText().toString());
                    String numberAsString = String.format("%,." + frmmain.price_places + "f", amt);
                    txtamt.setText(numberAsString);

                }

            });

            // Double amt=Double.parseDouble(txtChangeQty.getText().toString())* StringTODouble(txtChangePrice.getText().toString());
            Double amt = Double.parseDouble(txtChangeQty.getText().toString()) * tmpSalePrice;
            String numberAsString = String.format("%,." + frmmain.price_places + "f", amt);

            //modified by EKK on 18-11-2020
            if (frmlogin.ishidesaleprice != 0) {
                txtamt.setText("****");
            } else {
                txtamt.setText(numberAsString);
            }

            ImageButton save = view.findViewById(R.id.imgSave);
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sd.get(itemPosition).setUnit_qty(Double.parseDouble(txtChangeQty.getText().toString()));
                    // sd.get(itemPosition).setSale_price(StringTODouble(txtChangePrice.getText().toString()));
//                    sd.get(itemPosition).setSale_price(tmpSalePrice); //18-11-2020
                    sd.get(itemPosition).setDis_price(0/*StringTODouble(txtChangePrice.getText().toString())*/);
                    sd.get(itemPosition).setQty(Double.parseDouble(txtsqty.getText().toString()) * sd.get(itemPosition).getUnit_qty());
                    if (sd.get(itemPosition).getDis_type() == 3
                            || sd.get(itemPosition).getDis_type() == 4
                            || sd.get(itemPosition).getDis_type() == 6
                            || sd.get(itemPosition).getDis_type() == 7) {
                        sd.get(itemPosition).setDis_percent(0);
                        sd.get(itemPosition).setDis_price(sd.get(itemPosition).getSale_price());
                    } else if (sd.get(itemPosition).getDis_type() == 1
                            || sd.get(itemPosition).getDis_type() == 2) {
                        double dispercent = sd.get(itemPosition).getDis_type() == 1 ? 0.05 : 0.1;
                        double discount = sd.get(itemPosition).getDis_type() == 1 ? 5 : 10;
                        sd.get(itemPosition).setDis_percent(discount);
                        double dis_price = sd.get(itemPosition).getSale_price() - (sd.get(itemPosition).getSale_price() * (dispercent));
                        sd.get(itemPosition).setDis_price(dis_price);
                    } else if (sd.get(itemPosition).getDis_type() == 5) {
                        if (dis_typepercent && sd.get(itemPosition).getDis_percent() > 0) {
                            double dis_percent = sale_entry.dis_percent;
                            sd.get(itemPosition).setDis_percent(dis_percent);
                            double dis_price = sd.get(itemPosition).getSale_price() - (sd.get(itemPosition).getSale_price() * (dis_percent / 100));
                            sd.get(itemPosition).setDis_price(dis_price);


                        } else if (!btndiscount.getText().toString().equals("Discount")) {
                            double dis_percent = sale_entry.dis_percent;
                            sd.get(itemPosition).setDis_percent(dis_percent);
                            double dis_price = sd.get(itemPosition).getSale_price() - Double.parseDouble(ClearFormat(btndiscount.getText().toString()));
                            //  double dis_price = sd.get(itemPosition).getSale_price() - Double.parseDouble(btndiscount.getText().toString());
                            sd.get(itemPosition).setDis_price(dis_price);
                        } else {
                            sd.get(itemPosition).setDis_type(0);
                        }
                    }
//                    else if(sd.get(itemPosition).getDis_type()==5){
//                        sd.get(itemPosition).setDis_price(0);
//                    }

                    detRemark = detremark.getText().toString().trim().isEmpty() ? "NULL" : detremark.getText().toString().trim();
                    sd.get(itemPosition).setDetremark(detRemark);


                    //itemAdapter.notifyDataSetChanged();
                    entrygrid.setAdapter(itemAdapter);
                    sale_entry.entrygrid.setSelection(itemPosition);
                    getSummary();


                    dialog.dismiss();
                }
            });
            dialog = builder.create();
            dialog.show();

        }
    }

    public void chooseSpecialPrice(@Nullable RecyclerView view, Context c, long code, int unittype) {

        double minqty = 0, maxqty = 0;
        int pricelevel = 0, tmpPriceLevel = 0;
        String usr_code = "";

        Cursor dbCursor = DatabaseHelper.rawQuery("select usr_code from Usr_Code where code=" + code);
        if (dbCursor != null && dbCursor.getCount() > 0) {
            if (dbCursor.moveToFirst()) {
                do {
                    usr_code = dbCursor.getString(dbCursor.getColumnIndex("usr_code"));
                } while (dbCursor.moveToNext());

            }
            dbCursor.close();
        } else {

            return;
        }

        boolean isSpecialPriceExist = false;
        Cursor cursor = DatabaseHelper.rawQuery("select minqty,maxqty,pricelevel from s_saleprice where inactive = 0 and isdeleted=0 and usrcode= '" + usr_code + "' and unittype = " + unittype);
        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    minqty = cursor.getDouble(cursor.getColumnIndex("minqty"));
                    maxqty = cursor.getDouble(cursor.getColumnIndex("maxqty"));
                    pricelevel = cursor.getInt(cursor.getColumnIndex("pricelevel"));

                    if (Double.parseDouble(txtChangeQty.getText().toString()) >= minqty && Double.parseDouble(txtChangeQty.getText().toString()) <= maxqty) {
                        isSpecialPriceExist = true;
                        pad = new priceLevelAdapter(c, itemPosition, txtChangePrice, txtChangeQty, txtamt, btndiscount, pricelevel);
                        pad.chooseSP(pricelevel);
                        tmpPriceLevel = pricelevel;
                    } else {
                        if (!isSpecialPriceExist) {
                            pad = new priceLevelAdapter(c, itemPosition, txtChangePrice, txtChangeQty, txtamt, btndiscount, tmpPriceLevel);
                            pad.chooseSP(tmpPriceLevel);
                            isSpecialPriceExist = true;
                        }
                    }

                    view.setAdapter(pad);
                    GridLayoutManager gridPricelevel = new GridLayoutManager(c, 4);
                    view.setLayoutManager(gridPricelevel);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
    }

    public class addCust extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pb.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            HttpURLConnection connection;
            StringBuffer response = new StringBuffer();
            SelectInsertLibrary selectInsertLibrary = new SelectInsertLibrary();
            response = selectInsertLibrary.PostToApi(sqlstring, strings);

            return response.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pb.dismiss();
            try {
                if (result != null && !result.isEmpty()) {
                    newCustomerId = Integer.parseInt(result);
                }
                AlertDialog.Builder b = new AlertDialog.Builder(sale_entry.this, R.style.AlertDialogTheme);
                b.setTitle("iStock");
                if (result != null && !result.isEmpty()) {
                    b.setMessage("Save Successful.");
                    DownloadingCustomer();
                    custdia.dismiss();
//                    FillDataWithSignalr();

                } else {
                    b.setMessage("Save Fail.");
                }
                b.setCancelable(false);
                b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
//                dialog =
                b.create().show();
//                dialog.show();
            } catch (Exception ee) {
                pb.dismiss();
//                dialog.dismiss();
            }
        }
    }

    private void DownloadingCustomer() {

//        JSONArray jarr = null;
        try {
            AlertDialog.Builder bdProgress = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
            View view = getLayoutInflater().inflate(R.layout.downloadprocess, null);
            pbDownload = view.findViewById(R.id.progressDownload);
            txtProgress = view.findViewById(R.id.txtProgress);
            txtTable = view.findViewById(R.id.txtTable);
            txtTable.setText("Customer");
            txtProgress.setText("0/" + 10);
            pbDownload.setMax(10);
            bdProgress.setView(view);
            bdProgress.setCancelable(false);
            downloadAlert = bdProgress.create();
            context = this;
            downloadAlert.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            DownloadCustomer();
//                            FillDataWithSignalr();
                            downloadAlert.dismiss();
                        }
                    }).start();
                }
            });

            downloadAlert.show();
        } catch (Exception ee) {
            Toast.makeText(sale_entry.this, ee.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void DownloadCustomer() {
        String ip = sh_ip.getString("ip", "empty");
        String port = sh_port.getString("port", "empty");
        String url = "http://" + ip + ":" + port + "/api/mobile/GetData?download=true&_macaddress=" + GettingIMEINumber.IMEINO;
        Log.i("tvsale", url);
        RequestQueue request = Volley.newRequestQueue(getApplicationContext());
        final Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    jobj = jsonArray.getJSONObject(0);
                    SelectInsertLibrary selectInsertLibrary = new SelectInsertLibrary();
                    try {
                        if (jobj.getJSONArray("customer").length() > 0) {

                            selectInsertLibrary.UpSertingData("Customer", jobj);
                        }
                        SelectInsertLibrary.BindHeader(sale_entry.this, newCustomerId);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {

                }
            }
        };
        final Response.ErrorListener error = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "You are in Offline. Please check your connection!", Toast.LENGTH_SHORT).show();

            }
        };

        StringRequest req = new StringRequest(Request.Method.GET, url, listener, error);
        request.add(req);
    }


    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    private void printView() throws SdkException, IOException {
        //View view = findViewById(R.id.root_view);
        FrameLayout frame = findViewById(R.id.frame);
        LinearLayout rootView = findViewById(R.id.root_view);
        frame.setVisibility(View.VISIBLE);
//       LinearLayout listLayout=findViewById(R.id.list_layout);
        //  rootView.setVisibility(View.VISIBLE);
//      listLayout.setVisibility(View.GONE);
/*
       LinearLayout liner=new LinearLayout(this);

        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        AlertDialog.Builder change = new AlertDialog.Builder(sale_entry.this);
        change.setCancelable(false);
        View bluttoothvoucher = getLayoutInflater().inflate(R.layout.bluttoothvoucherprint,null);
        liner.setMinimumWidth(250);
        liner.addView(bluttoothvoucher,params);
        change.setView(liner);
        salechange = change.create();
        salechange.show();

 */
        View voucher = getLayoutInflater().inflate(R.layout.bluetoothvoucherprint, null);

        TextView custname = voucher.findViewById(R.id.txtcustomer);
        TextView salemenName = voucher.findViewById(R.id.txtsalemen);
        TextView tvdate = voucher.findViewById(R.id.txtdate);
        TextView tvinvoice = voucher.findViewById(R.id.txtinvoice);
        TextView tvtotalamount = voucher.findViewById(R.id.txttotalamount);
        TextView tvtotaldiscount = voucher.findViewById(R.id.txttotaldisamount);
        TextView tvtotalfocamount = voucher.findViewById(R.id.txttotalfocamount);
        TextView tvtaxamount = voucher.findViewById(R.id.txt_tax_amount);
        TextView txttaxpercent = voucher.findViewById(R.id.txt_tax_percent);
        TextView tvtotalnetamount = voucher.findViewById(R.id.txtnetamount);
        TextView tvpaidamount = voucher.findViewById(R.id.txtpaidamount);
        TextView tvchangeamount = voucher.findViewById(R.id.txtchangeamount);

        TextView tvcompanyname = voucher.findViewById(R.id.txtcompanyname);
        TextView tvheader1 = voucher.findViewById(R.id.txtheadertitle1);
        TextView tvheader2 = voucher.findViewById(R.id.txtheadertitle2);
        TextView tvheader3 = voucher.findViewById(R.id.txtheadertitle3);
        TextView txtVoucherRemark = voucher.findViewById(R.id.txt_voucher_remark);
        LinearLayout salemenLayout = voucher.findViewById(R.id.salemenlayout);

        LinearLayout tax_layout = voucher.findViewById(R.id.layout_tax);
        if (frmlogin.isusetax == 0) {
            tax_layout.setVisibility(View.GONE);
        }
        if (sh.get(0).getTax_per() > 0) {
            txttaxpercent.setText("Tax(" + CurrencyFormat(sh.get(0).getTax_per()) + "%)");
        }

        //Title
        String companyname = null;
        String headerline1 = null;
        String headerline2 = null;
        String headerline3 = null;
        Cursor cursor = DatabaseHelper.rawQuery("select title,receiptheaderline1,receiptheaderline2,receiptheaderline3,receiptheaderline4 from SystemSetting");
        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    companyname = cursor.getString(cursor.getColumnIndex("title"));
                    headerline1 = cursor.getString(cursor.getColumnIndex("receiptheaderline1"));
                    headerline2 = cursor.getString(cursor.getColumnIndex("receiptheaderline2"));
                    headerline3 = cursor.getString(cursor.getColumnIndex("receiptheaderline3"));
                } while (cursor.moveToNext());
            }
        }
        tvcompanyname.setText(companyname);
        tvheader1.setText(headerline1);
        tvheader2.setText(headerline2);
        tvheader3.setText(headerline3);

        if (tvheader1.getText().toString().equals("null"))
            tvheader1.setVisibility(View.GONE);
        else
            tvheader1.setVisibility(View.VISIBLE);

        if (tvheader2.getText().toString().equals("null"))
            tvheader2.setVisibility(View.GONE);
        else
            tvheader2.setVisibility(View.VISIBLE);

        if (tvheader1.getText().toString().equals("null"))
            tvheader3.setVisibility(View.GONE);
        else
            tvheader3.setVisibility(View.VISIBLE);

        //Header
        String Date = sale_entry.sh.get(0).getDate();
        String invoiceno = (sh.get(0).getInvoice_no().equals("") || sh.get(0).getInvoice_no().toLowerCase().equals("null")) ? sh.get(0).getDocid() : sh.get(0).getInvoice_no();
        String Customername = null;
        cursor = DatabaseHelper.rawQuery("select name from Customer where customerid=" + sh.get(0).getCustomerid());
        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    Customername = cursor.getString(cursor.getColumnIndex("name"));

                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        custname.setText(Customername);
        tvdate.setText(Date);
        tvinvoice.setText(invoiceno);
        txtVoucherRemark.setText(sh.get(0).getHeadremark());

        if (use_salesperson) {
            salemenLayout.setVisibility(View.VISIBLE);
            String salesmenString = "";
            if (SaleVouSalesmen.size() > 0) {
                for (int i = 0; i < SaleVouSalesmen.size() - 1; i++) {
                    salesmenString += SaleVouSalesmen.get(i).getSalesmen_Name() + ", ";
                }

                salesmenString += SaleVouSalesmen.get(SaleVouSalesmen.size() - 1).getSalesmen_Name();
            }
            salemenName.setText(salesmenString);
        }


//Detail

        String text = "";
        String stamount = null;
        String qtyprice = null;
        String item = null;
        double amt = 0.0;
        LinearLayout detailLayout = voucher.findViewById(R.id.detail);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < sale_entry.sd.size(); i++) {
            View voucheritem = getLayoutInflater().inflate(R.layout.layout_voucher_item, null);
            TextView tvdescription = voucheritem.findViewById(R.id.txtdescription);
            TextView tvamount = voucheritem.findViewById(R.id.txtAmount);
            TextView tvqtyamount = voucheritem.findViewById(R.id.txtQtyPrice);

            item = sale_entry.sd.get(i).getDesc();
            amt = sale_entry.sd.get(i).getUnit_qty() * sale_entry.sd.get(i).getSale_price();
//            int len = item.length();
//            if (len > 20) {
//                item = item.substring(0, 20);
//            }
            stamount = CurrencyFormat(amt);
            qtyprice = "(" + String.format("%,." + frmmain.qty_places + "f", sale_entry.sd.get(i).getUnit_qty()) + "  " + sale_entry.sd.get(i).getUnit_short() + "x"
                    + CurrencyFormat(sale_entry.sd.get(i).getSale_price()) + ")";

            tvdescription.setText(item);
            tvamount.setText(stamount);
            tvqtyamount.setText(qtyprice);
            detailLayout.addView(voucheritem);
        }

        //Summary


        String TotalAmount = CurrencyFormat(sh.get(0).getInvoice_amount());
        String DisAmount = CurrencyFormat(sh.get(0).getDiscount() + sh.get(0).getIstemdis_amount());
        String FocAmount = CurrencyFormat(sh.get(0).getFoc_amount());
        String NetAmount = CurrencyFormat(net_amount);
        String PaidAmount = CurrencyFormat(paidamount);
        String ChangeAmount = CurrencyFormat(changeamount);

        tvtotalamount.setText(TotalAmount);
        tvtotaldiscount.setText(DisAmount);
        tvtotalfocamount.setText(FocAmount);
        tvtotalnetamount.setText(NetAmount);
        tvtaxamount.setText(CurrencyFormat(sh.get(0).getTax_amount()));
        tvpaidamount.setText(PaidAmount);
        tvchangeamount.setText(ChangeAmount);

        //LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        tv.setText("Descrption");
//       rootView.addView(tv,params);
        //TextView tv=voucher.findViewById(R.id.txtdisplay);
        // tv.setText("မုန့်စိန်လား ရွှေဘ ငွေဘ အမွေရ");
//       LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        for(int i=0;i<20;i++){
//            TextView txt=new TextView(this);
//            txt.setWidth(200);
//            txt.setTextSize(20);
//            txt.setHeight(80);
//            txt.setText("မုန့်စိန်လား ရွှေဘ ငွေဘ အမွေရ" +i);
//            rootView.addView(txt,params);
//        }TotalAmount

        rootView.addView(voucher);
        //liner.addView(voucher);

        View v = getWindow().getDecorView().getRootView();
//        Printama.with(this).connect(printama -> {
//            printama.printFromView(voucher);
//            // new Handler().postDelayed(printama::close, 2000); // comment by T2A 08-12-2020
//            frame.setVisibility(View.GONE);
//        }, this::showToast);
        for (int i = 0; i < billprintcount; i++) {
            RTPrinter rtPrinter = BaseApplication.getInstance().getRtPrinter();
            BluetoothPrinter bluetoothPrinter = new BluetoothPrinter(sale_entry.this, rtPrinter);
            bluetoothPrinter.printFromView(rootView);
        }
//        File file = bluetoothPrinter.saveBitmap(bmpVoucher);
//        if(file.exists()){
//            Uri uri = Uri.parse(file.getPath());
//            bluetoothPrinter.printImageFromFile(uri, bmpVoucher);
//        }
        //BluetoothPrinter.escImagePrint(btPrinter, bmpVoucher, BluetoothPrinter.PRINT_80MM);
        //BindingCategory();
    }

    public String CurrencyFormat(Double amt) {
        return String.format("%,." + frmmain.price_places + "f", amt);
    }

    public class SaveData extends AsyncTask<String, Void, String> {
        String modify = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pb.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            modify = strings[1];
            StringBuffer response = new StringBuffer();
//            HttpURLConnection connection;
//            StringBuffer response = new StringBuffer();
//            try {
//                URL url=new URL(strings[0]);
//                connection = (HttpURLConnection) url.openConnection();
//                connection.setRequestMethod("POST");
//                connection.addRequestProperty("Content-Type","text/plain");
//                connection.setDoOutput(true);
//                connection.setDoInput(true);
//                if(sqlstring!=null)
//                {
//                    //connection.setRequestProperty("Content-Length", Integer.toString(sqlstring.length()));
//                    connection.getOutputStream().write(sqlstring.getBytes("UTF8"));
//                    BufferedReader in = new BufferedReader(
//                            new InputStreamReader(connection.getInputStream()));
//                    String inputLine;
//
//
//                    while ((inputLine = in.readLine()) != null) {
//                        response.append(inputLine);
//                    }
//                    in.close();
//
//                }
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            SelectInsertLibrary selectInsertLibrary = new SelectInsertLibrary();
            response = selectInsertLibrary.PostToApi(sqlstring, strings);

            return response.toString();
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            pb.dismiss();
            try {
                String[] result = response.split("&");
                sh.get(0).setDocid(result[1]);
                String s = result[0];

                if (s.equals("") || s.isEmpty()) {
                    ConfirmedTranid = Long.parseLong("0");
                } else {
                    ConfirmedTranid = Long.parseLong(s);
                }
                AlertDialog.Builder b = new AlertDialog.Builder(sale_entry.this, R.style.MyDialogTheme);
                TextView title = new TextView(getApplicationContext());
                title.setBackgroundColor(Color.rgb(96, 169, 23));
                title.setPadding(30, 20, 10, 20);
                title.setTextSize(18F);
                title.setText("iStock");
                title.setTextColor(Color.WHITE);
                b.setCustomTitle(title);

                if (ConfirmedTranid > 0) {
                    int offlinetranid = 0;
                    Cursor cursor = DatabaseHelper.DistinctSelectQuerySelectionWithCondition("sale_head_main", new String[]{"tranid", "docid", "date", "invoiceno", "locationid", "customerid", "cashid", "townshipid", "paytypeid", "dueindays", "salecurr", "discountamount", "paidamount", "invoiceamount", "invoiceqty", "focamount", "netamount", "voucherremark", "taxamount", "taxpercent", "discountpercent", "exgrate", "userid"}, "uploaded=?", new String[]{"0"}, "");
                    if (cursor != null && cursor.getCount() != 0) {
                        System.out.println(cursor.getCount());
                        if (cursor.moveToFirst()) {
                            do {

                                offlinetranid = cursor.getInt(cursor.getColumnIndex("tranid"));

                                DatabaseHelper.execute("update sale_head_main set uploaded='1' " + "where tranid=" + offlinetranid + "");

                            } while (cursor.moveToNext());

                        }
                    }
                    b.setMessage("Upload Successful");

//                    ConfirmedTranid = Long.parseLong(s);
                    ConfirmedTranid = Long.valueOf(0);
                } else if (ConfirmedTranid == -1) {
                    b.setMessage("Confirm Successful.");
                    ConfirmedTranid = Long.parseLong(s);
                } else {

//                    AlertDialog.Builder alert = new AlertDialog.Builder(sale_entry.this);
//                    alert.setTitle("iStock");
//                    alert.setMessage("Connection Fail! Do you want to save Locally?");
//                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            insertdatatoLiteDb();
//                            pb.dismiss();
//                            dialog.dismiss();
////                            if(ConfirmedTranid.toString()!="0")
////                            {
////                                PrintVoucher(ConfirmedTranid);
////                            }
//                        }
//                    });
//                    alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    });
////                    alert.show();
//                    dialog=alert.create();
//                    dialog.setOnShowListener(new DialogInterface.OnShowListener() {
//                        @Override
//                        public void onShow(DialogInterface alert) {
//                            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
//                            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLUE);
//
//                        }
//                    });
//                    dialog.show();
                    if (modify.equals("record")) {

                        b = new AlertDialog.Builder(sale_entry.this, R.style.WarningDialogTheme);
//                        b.setTitle("iStock");
//                        TextView title=new TextView(getApplicationContext());
                        title.setBackgroundColor(Color.rgb(250, 104, 0));
                        title.setPadding(30, 20, 10, 20);
                        title.setTextSize(18F);
                        title.setText("iStock (Offline Save)");
                        title.setTextColor(Color.WHITE);
                        b.setCustomTitle(title);
//                        b.setc
                        b.setMessage("Connection Fail! Do you want to save Offline?");
                        selectInsertLibrary.OfflineCheck = true;
                        b.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                    } else {
                        title.setBackgroundColor(Color.rgb(229, 20, 0));
                        b.setMessage("Connection Fail!");
                    }

//                    b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//
//                            pb.dismiss();
//                            dialog.dismiss();
//                            if(ConfirmedTranid.toString()!="0")
//                            {
//                                PrintVoucher(ConfirmedTranid);
//                            }
//                        }
//                    });

//                    b.setCancelable(false);
//                    b.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                                                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    dialog.dismiss();
//                                }
//                            });
//                    insertdatatoLiteDb();

                    ConfirmedTranid = Long.parseLong("0");
                }
//                b.setCancelable(false);
//                if(selectInsertLibrary.OfflineCheck==true && modify.isEmpty()){
//                    b=new AlertDialog.Builder(sale_entry.this,R.style.WarningDialogTheme);
//                    b.setTitle("iStock");
//                }
                b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // chkOffline.setChecked(true);
                        if (selectInsertLibrary.OfflineCheck == true && modify.equals("record")) {
                            insertdatatoLiteDb();
                        }
                        pb.dismiss();
                        dialog.dismiss();

                        if (ConfirmedTranid != 0) {
                            if(sh.get(0).getPay_type()==2){

                            }
                            if (!bill_not_print && billprintcount > 0 && use_bluetooth) {
                                //bluetoothPrinter.Connect_Device();
                                //showPrinterList();
                                //String msg=bluetoothPrinter.Print(getPrintText());//for image.

//                              GetDocIDForVoucher();
                                try {
                                    printView();
                                } catch (Exception e) {
                                    showToast(e.getMessage());
                                    e.printStackTrace();
                                }
//
                                // try {
                                // String msg = bluetoothPrinter.BlueToothPrint(getStringPrint());

                                //} catch (IOException e) {
                                //  e.printStackTrace();
                                // }
                                AlertDialog.Builder bd = new AlertDialog.Builder(sale_entry.this);
                                bd.setTitle("iStock");
                                bd.setMessage("Printing is Successful.");
                                bd.setCancelable(false);
                                bd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        addDialog.dismiss();
                                        intent = new Intent(sale_entry.this, sale_entry.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                                addDialog = bd.create();
                                addDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                                    @Override
                                    public void onShow(DialogInterface dialog) {
                                        addDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
                                    }
                                });
                                addDialog.show();

                            } else {
                                PrintVoucher(tranid);
                            }

                        } else {
                            intent = new Intent(sale_entry.this, sale_entry.class);
                            startActivity(intent);
                            finish();
                        }

                    }
                });
//               b.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    dialog.dismiss();
//                                }
//                            });

                dialog = b.create();
                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface alert) {
                        Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        int height = getResources().getDimensionPixelSize(R.dimen.alertdialog_button_height);
                        int width = getResources().getDimensionPixelSize(R.dimen.alertdialog_button_width);
//                        button.setHeight(height);
//                        button.setWidth(width);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
//                        params.setMargins(20,0,0,0);
//                        button.setLayoutParams(params);
//                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundColor(Color.rgb(8,111,158));
                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setPadding(0, 0, 0, 0);
//                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setWidth(100);
//                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setHeight(0);
//                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(20F);
                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.rgb(8, 111, 158));
                        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.rgb(8, 111, 158));
//                        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setBackgroundColor(Color.rgb(8,111,158));
                        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setPadding(0, 0, 0, 0);
//                        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setWidth(width);
//                        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setHeight(height);
//                        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextSize(20F);
//                        dialog.getListView(AlertDialog)

                    }
                });
                dialog.show();

            } catch (Exception ee) {
//                pb.dismiss();
//                dialog.dismiss();
                Toast.makeText(sale_entry.this, "No internet connection! Please check your internet connection!" /*ee.getMessage()*/, Toast.LENGTH_LONG).show();
            }
        }
    }


    private void FillDataWithSignalr() {
//        String ippp = sh_ip.getString("ip", "empty");
//        String porttt = sh_port.getString("port", "empty");
//        String server = "http://"+ippp+":"+porttt+"/signalr";//159.138.231.20
//
//        /* Your logic here */
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
        RequestQueue request = Volley.newRequestQueue(getApplicationContext());
        final Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
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

                    try {
                        if (jobj.getJSONArray("discount_code").length() > 0) {
                            tablename = "discount_code";
                            selectInsertLibrary.UpSertingData(tablename, jobj);
                        }
                    } catch (JSONException e) {
                        DatabaseHelper.rawQuery("delete from discount_code");
                        e.printStackTrace();
                    }


                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String currentDateandTime = sdf.format(new Date());
                    String ipp = sh_ip.getString("ip", "empty");
                    String portt = sh_port.getString("port", "empty");
                    String urll = "http://" + ipp + ":" + portt + "/api/mobile/RegisterUsingIMEI?imei=" + GettingIMEINumber.IMEINO + "&lastupdatedatetime=" + currentDateandTime + "&lastaccesseduserid=" + frmlogin.LoginUserid + "&clientname=" + frmlogin.Device_Name;
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
                            Toast.makeText(sale_entry.this, "You are in Offline. Please check your connection!", Toast.LENGTH_SHORT).show();

                        }
                    };

                    StringRequest reqq = new StringRequest(Request.Method.GET, urll, listenerr, errorr);
                    requestt.add(reqq);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        final Response.ErrorListener error = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "You are in Offline. Please check your connection!", Toast.LENGTH_SHORT).show();

            }
        };

        StringRequest req = new StringRequest(Request.Method.GET, url, listener, error);
        request.add(req);


    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    private class UploadData extends AsyncTask<String, Void, String> {

        public static final String REQUEST_METHOD = "GET";
        public static final int READ_TIMEOUT = 15000;
        public static final int CONNECTION_TIMEOUT = 15000;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String stringUrl = strings[0];
            String result;
            String inputLine;
            try {
                //Create a URL object holding our url
                URL myUrl = new URL(stringUrl);
                //Create a connection
                HttpURLConnection connection = (HttpURLConnection)
                        myUrl.openConnection();
                //Set methods and timeouts
                connection.setRequestMethod(REQUEST_METHOD);
                connection.setReadTimeout(READ_TIMEOUT);
                connection.setConnectTimeout(CONNECTION_TIMEOUT);

                //Connect to our url
                connection.connect();
                //Create a new InputStreamReader
                InputStreamReader streamReader = new
                        InputStreamReader(connection.getInputStream());
                //Create a new buffered reader and String Builder
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();
                //Check if the line we are reading is not null
                while ((inputLine = reader.readLine()) != null) {
                    stringBuilder.append(inputLine);
                }
                //Close our InputStream and Buffered reader
                reader.close();
                streamReader.close();
                //Set our result equal to our stringBuilder
                result = stringBuilder.toString();
            } catch (IOException e) {
                e.printStackTrace();
                result = null;
            }
            return result;

        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//            Toast.makeText(sale_entry.this, "this is tranid " + s, Toast.LENGTH_LONG).show();

            try {
                JSONArray jsonArray = new JSONArray(s);
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                tranidfromcloud = jsonObject.optLong("tranid", 0);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Cursor cursor = DatabaseHelper.DistinctSelectQuerySelectionWithCondition("sale_head_main", new String[]{"tranid", "docid", "date", "invoiceno", "locationid", "customerid", "cashid", "townshipid", "paytypeid", "dueindays", "salecurr", "discountamount", "paidamount", "invoiceamount", "invoiceqty", "focamount", "netamount", "voucherremark", "taxamount", "taxpercent", "discountpercent", "exgrate", "userid"}, "uploaded=?", new String[]{"0"}, "");
            if (cursor != null && cursor.getCount() != 0) {
                System.out.println(cursor.getCount());
                if (cursor.moveToFirst()) {
                    do {
//                        getTranID();

                        String head = "update sale_head_tmp set " +
                                "tranid=" + tranidfromcloud + "," +
                                "docid='" + cursor.getString(cursor.getColumnIndex("docid")) + "'," +
                                "date=" + "'" + cursor.getString(cursor.getColumnIndex("date")) + "'," +
                                "invoiceno=" + cursor.getString(cursor.getColumnIndex("invoiceno")) + "," +
                                "locationid=" + cursor.getInt(cursor.getColumnIndex("locationid")) + "," +
                                "customerid=" + cursor.getInt(cursor.getColumnIndex("customerid")) + "," +
                                "cashid=" + cursor.getInt(cursor.getColumnIndex("cashid")) + "," +
                                "townshipid=" + cursor.getInt(cursor.getColumnIndex("townshipid")) + "," +
                                "paytypeid=" + cursor.getInt(cursor.getColumnIndex("paytypeid")) + "," +
                                "dueindays=" + cursor.getInt(cursor.getColumnIndex("dueindays")) + "," +
                                "salecurr=" + cursor.getInt(cursor.getColumnIndex("salecurr")) + "," +
                                "discountamount=" + cursor.getDouble(cursor.getColumnIndex("discountamount")) + "," +
                                "paidamount=" + cursor.getDouble(cursor.getColumnIndex("paidamount")) + "," +
                                "invoiceamount=" + cursor.getDouble(cursor.getColumnIndex("invoiceamount")) + "," +
                                "invoiceqty=" + cursor.getDouble(cursor.getColumnIndex("invoiceqty")) + "," +
                                "focamount=" + cursor.getDouble(cursor.getColumnIndex("focamount")) + "," +
                                //"itemdis_amount="+sh.get(0).getIstemdis_amount()+",\n" +
                                "netamount=" + cursor.getDouble(cursor.getColumnIndex("netamount")) + "," +
                                "voucherremark=" + cursor.getString(cursor.getColumnIndex("voucherremark")) + "," +
                                "taxamount=" + cursor.getDouble(cursor.getColumnIndex("taxamount")) + "," +
                                "taxpercent=" + cursor.getDouble(cursor.getColumnIndex("taxpercent")) + "," +
                                "discountpercent=" + cursor.getDouble(cursor.getColumnIndex("discountpercent")) + "," +
                                "exgrate=" + cursor.getDouble(cursor.getColumnIndex("exgrate")) +
                                " where tranid=" + tranidfromcloud;
                        String stranid = cursor.getString(cursor.getColumnIndex("tranid"));
                        String det = ";delete from sale_det_tmp where tranid=" + tranidfromcloud +
                                ";insert into sale_det_tmp(tranid,unitqty,qty,saleprice,discountamount,itemdiscounttypeid,discountpercent,remark,unittypeid,code,sr,srno) values ";

                        Cursor cursor1 = DatabaseHelper.rawQuery("select * from sale_det where tranid='" + stranid + "'");
                        if (cursor1 != null && cursor1.getCount() != 0) {
                            int t = 0;
                            while (cursor1.moveToNext()) {

                                det = det + "(" +
                                        tranidfromcloud + "," +

                                        cursor1.getDouble(cursor1.getColumnIndex("unitqty")) + "," +
                                        cursor1.getDouble(cursor1.getColumnIndex("qty")) + "," +
                                        cursor1.getDouble(cursor1.getColumnIndex("saleprice")) + "," +
                                        cursor1.getDouble(cursor1.getColumnIndex("discountamount")) + "," +
                                        cursor1.getInt(cursor1.getColumnIndex("itemdiscounttypeid")) + "," +
                                        cursor1.getDouble(cursor1.getColumnIndex("discountpercent")) + "," +
                                        cursor1.getString(cursor1.getColumnIndex("remark")) + "," +
                                        cursor1.getInt(cursor1.getColumnIndex("unittypeid")) + "," +
                                        cursor1.getInt(cursor1.getColumnIndex("code")) + "," +
                                        cursor1.getInt(cursor1.getColumnIndex("sr")) + ","
                                        + cursor1.getInt(cursor1.getColumnIndex("srno"));
                                if (t < cursor1.getCount() - 1)
                                    det = det + " ),";
                                else det = det + ")";
                                t++;
                            }
                        }

                        String salechange = ";delete from salechange_history where tranid=" + tranidfromcloud +
                                "; insert into salechange_history(tranid,currencyid,paidamount,changeamount,exgrate,invoiceamount) values ";

//                            salechange=salechange + "(" +sh.get(0).getTranid()+","+1+","+paid+","+changeamount+","+1+","+sh.get(0).getInvoice_amount()+");";
                        Cursor cursor2 = DatabaseHelper.rawQuery("select * from salechange_history where tranid='" + stranid + "'");
                        if (cursor2 != null && cursor2.getCount() != 0) {
                            while (cursor2.moveToNext()) {
                                salechange = salechange + "("
                                        + tranidfromcloud
                                        + "," + cursor2.getInt(cursor2.getColumnIndex("currencyid")) + ","
                                        + cursor2.getInt(cursor2.getColumnIndex("paidamount")) + ","
                                        + cursor2.getInt(cursor2.getColumnIndex("changeamount")) + ","
                                        + cursor2.getInt(cursor2.getColumnIndex("exgrate")) + ","
                                        + cursor2.getInt(cursor2.getColumnIndex("invoiceamount")) + ");";
                            }
                        }
                        sqlstring = head + " " + salechange + " " + det;
                        Confirm();

                        DatabaseHelper.execute("update sale_head_main set uploaded='1' " + "where tranid='" + stranid + "'");
//                            System.out.println(cursor3.getCount());


////                            cursor.getColumnName();
//                            String stringtranid = cursor.getString(cursor.getColumnIndex("docid"));
//                            String custgroupname = cursor.getString(cursor.getColumnIndex("date"));
//                            String shortname = cursor.getString(cursor.getColumnIndex("invoiceno"));
//                            ContentValues contentValues=new ContentValues();
////                            contentValues.put("tranid",);
//                            contentValues.put("docid",cursor.getString(cursor.getColumnIndex("docid")));
//                            contentValues.put("date",cursor.getString(cursor.getColumnIndex("date")));
//                            contentValues.put("invoiceno",cursor.getString(cursor.getColumnIndex("invoiceno")));
//                            contentValues.put("locationid",cursor.getInt(cursor.getColumnIndex("locationid")));
//                            contentValues.put("customerid",cursor.getInt(cursor.getColumnIndex("customerid")));
//                            contentValues.put("cashid",cursor.getInt(cursor.getColumnIndex("cashid")));
//                            contentValues.put("townshipid",cursor.getInt(cursor.getColumnIndex("townshipid")));
//                            contentValues.put("paytypeid",cursor.getInt(cursor.getColumnIndex("paytypeid")));
//                            contentValues.put("dueindays",cursor.getInt(cursor.getColumnIndex("dueindays")));
//                            contentValues.put("salecurr",cursor.getInt(cursor.getColumnIndex("salecurr")));
//                            contentValues.put("discountamount",cursor.getDouble(cursor.getColumnIndex("discountamount")));
//                            contentValues.put("paidamount",cursor.getDouble(cursor.getColumnIndex("paidamount")));
//                            contentValues.put("invoiceamount",cursor.getDouble(cursor.getColumnIndex("invoiceamount")));
//                            contentValues.put("invoiceqty",cursor.getDouble(cursor.getColumnIndex("invoiceqty")));
//                            contentValues.put("focamount",cursor.getDouble(cursor.getColumnIndex("focamount")));
//                            contentValues.put("netamount",cursor.getDouble(cursor.getColumnIndex("netamount")));
//                            contentValues.put("voucherremark",cursor.getString(cursor.getColumnIndex("voucherremark")));
//                            contentValues.put("taxamount",cursor.getDouble(cursor.getColumnIndex("taxamount")));
//                            contentValues.put("taxpercent",cursor.getDouble(cursor.getColumnIndex("taxpercent")));
//                            contentValues.put("discountpercent",cursor.getDouble(cursor.getColumnIndex("discountpercent")));
//                            contentValues.put("exgrate",cursor.getDouble(cursor.getColumnIndex("exgrate")));
//                            contentValues.put("userid",cursor.getInt(cursor.getColumnIndex("userid")));
//
////                            cg.add(new customergroup(custgroupid, custgroupname, shortname));
                    } while (cursor.moveToNext());

                }

            } else {
                Toast.makeText(getApplicationContext(), "No data to upload cloud", Toast.LENGTH_LONG).show();
            }
        }
    }

    public static double StringTODouble(String stringprice) {
        try {
            return DecimalFormat.getNumberInstance().parse(stringprice).doubleValue();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    private void SetDefaultLocation() {
        if (sh.get(0).getLocationid() == 0) {
            if (frmlogin.det_locationid != 0)
                sh.get(0).setLocationid(frmlogin.det_locationid);
            else if (frmlogin.defaultbranchid != 0) {
                Cursor cursor = DatabaseHelper.rawQuery("select locationid,Name,shortdesc,branchID from Location where isdeleted=0 and branchid = " + frmlogin.defaultbranchid + " Limit 1");
                if (cursor != null && cursor.getCount() != 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            long locationid = cursor.getLong(cursor.getColumnIndex("locationid"));
                            sh.get(0).setLocationid(locationid);
                        } while (cursor.moveToNext());

                    }

                }
                cursor.close();
            } else {

                Cursor cursor = DatabaseHelper.rawQuery("select locationid,Name,shortdesc,branchID from Location where isdeleted=0 and locationid=(select min(locationid) from location)");
                if (cursor != null && cursor.getCount() != 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            long locationid = cursor.getLong(cursor.getColumnIndex("locationid"));
                            sh.get(0).setLocationid(locationid);
                        } while (cursor.moveToNext());

                    }

                }
                cursor.close();
//                sh.get(0).setLocationid(1);
            }
        }
    }

    public static int getCustomerCount() {
        int result = 0;
        Cursor cursor = DatabaseHelper.rawQuery("select Max(customerid)as custc from Customer");
        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    result = cursor.getInt(cursor.getColumnIndex("custc"));

                } while (cursor.moveToNext());
            }
        }
        return result;
    }


}
