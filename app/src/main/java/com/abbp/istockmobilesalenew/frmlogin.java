package com.abbp.istockmobilesalenew;

import android.Manifest;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.abbp.istockmobilesalenew.bluetoothprinter.BaseEnum;
import com.abbp.istockmobilesalenew.bluetoothprinter.BluetoothDeviceChooseDialog;
import com.abbp.istockmobilesalenew.bluetoothprinter.BluetoothPrinter;
import com.abbp.istockmobilesalenew.sunmiprinter.SunmiPrintHelper;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rt.printerlibrary.bean.BluetoothEdrConfigBean;
import com.rt.printerlibrary.connect.PrinterInterface;
import com.rt.printerlibrary.enumerate.CommonEnum;
import com.rt.printerlibrary.enumerate.ConnectStateEnum;
import com.rt.printerlibrary.factory.connect.BluetoothFactory;
import com.rt.printerlibrary.factory.connect.PIFactory;
import com.rt.printerlibrary.factory.printer.PrinterFactory;
import com.rt.printerlibrary.factory.printer.UniversalPrinterFactory;
import com.rt.printerlibrary.observer.PrinterObserver;
import com.rt.printerlibrary.observer.PrinterObserverManager;
import com.rt.printerlibrary.printer.RTPrinter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class frmlogin extends AppCompatActivity implements View.OnClickListener, PrinterObserver {

    private Button btnlogin, btnexit, btnok, btncancel, btnrgok;
    private TextView btnconnect, btnposdown, btnRegister, txtTable, txtProgress, txtSetting;
    private EditText useredit, passedit, edtserver, edtport, edtkey;
    private ToggleButton toggleButton;
    private ListView lv;
    AlertDialog dialog, msg, downloadAlert;
    AlertDialog.Builder builder;
    SharedPreferences sh_ip;
    SharedPreferences sh_port;
    SharedPreferences RegisterID;
    private DatabaseHelper dataBaseHelper;
    private static final String DB_NAME = "iStock.db";
    ArrayList<String> TableName = new ArrayList<>();
    String jsonValue = "";
    String returnJson = "";
    String sqlString = "";
    String url;
    private JsonArrayRequest request;
    private RequestQueue requestQueue;
    ArrayList<posuser> aryUsers = new ArrayList<>();
    public static int LoginUserid = -1;
    public static String username = "";
    public static int det_locationid;
    public static int def_payment;
    public static int Confirm_PrintVou;
    //    public static int allow_priceLevel;
//    public static int select_location;
//    public static int select_customer=1;
//    public static int change_date;F
//    public static int change_price;
//    public static int tax;
//    public static int discount;
//    public static int Allow_Over_Credit_Limit;
    public static int def_cashid;
    public static String Device_Name = "";
    public static String Cashier_Printer;
    public static int Cashier_PrinterType;

    public static int userid;
    public static String name;
    public static int branchid;
    public static String shortdesc;
    public static String password;
    public static int canchangesaleprice;
    public static int canchangepurprice;
    public static int canchangedate;
    public static int defaultlocationid;
    public static int candiscount;
    public static int isusetax;
    public static int ishidepurprice;
    public static int ishidesaleprice;
    public static int ishidepurcostprice;
    public static int isviewallsalepricelevel;
    public static int isinactive;
    public static int defaultbranchid;
    public static int defaultcashid;
    public static int isallowsysdatechange;
    public static int isallowovercreditlimit;
    public static String isknockcode;
    public static int istabletuser, istvuser;
    public static int isallowpricelevel;
    public static int canselectcustomer;
    public static int canselectlocation;

    SharedPreferences sh_printer, sh_ptype;
    public static ArrayList<String> Printers = new ArrayList<>();
    public static ArrayList<Printer_Type> ptype = new ArrayList<>();
    JSONArray jarr;
    ProgressDialog pb;
    String ip;
    String port;
    AlertDialog showmsg;
    Context context;
    ProgressBar pbDownload;
    ArrayList<String> tableNames = new ArrayList<>();
    JSONArray data;
    JSONObject jobj;
    private int printer_type_id = -1;
    private TelephonyManager telephonyManager;
    private int REQUEST_CODE = 101;
    public static SharedPreferences updatetime;
    SharedPreferences.Editor dateEditor;
    TextView btnLocalConnect;

    private final int REQUEST_ENABLE_BT = 101;
    TextView tv_device_selected;
    private Object configObj;
    private int iPrintTimes = 0;
    private RTPrinter rtPrinter = null;
    private PrinterFactory printerFactory;
    private ArrayList<PrinterInterface> printerInterfaceArrayList = new ArrayList<>();
    private PrinterInterface curPrinterInterface = null;
    public static boolean isTVMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.frmlogin);

        globalsetting.username = "ThuraTun";

        GettingIMEINumber.IMEINO = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        dataBaseHelper = DatabaseHelper.getInstance(this, DB_NAME);
        sh_ip = getSharedPreferences("ip", MODE_PRIVATE);
        sh_port = getSharedPreferences("port", MODE_PRIVATE);
        System.out.println(sh_ip + "" + sh_port);
        RegisterID = getSharedPreferences("register", MODE_PRIVATE);
        sh_printer = getSharedPreferences("printer", MODE_PRIVATE);
        sh_ptype = getSharedPreferences("ptype", MODE_PRIVATE);
        updatetime = getSharedPreferences("datetime", MODE_PRIVATE);
        SwitchCompat switchCompat = findViewById(R.id.switchBtn);

        isTVMode = sh_printer.getBoolean("isTVMode", false);
        //added by KLM  for auto Detect if Device is TV or Tablet 25052022
        if (checkIsTelevision() || SunmiPrintHelper.getInstance().checkSunmiPrinter()) {
            switchCompat.setVisibility(View.GONE);
            isTVMode = true;
            SharedPreferences.Editor editor = sh_printer.edit();
            editor.putBoolean("isTVMode", isTVMode);
            editor.apply();
        }

        switchCompat.setChecked(isTVMode);
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isTVMode = isChecked;
                SharedPreferences.Editor editor = sh_printer.edit();
                editor.putBoolean("isTVMode", isTVMode);
                editor.apply();
            }
        });

        setUI();
        CheckConnection();
        context = this;
        isRegister();
        GetDeviceName();
        if (globalsetting.datetime != null && globalsetting.datetime.equals("1990-01-01")) {
            updatetime.getString("datetime", "");
            dateEditor = updatetime.edit();
            dateEditor.remove("datetime");
            dateEditor.commit();

            dateEditor = updatetime.edit();
            dateEditor.putString("datetime", "1990-01-01");
            dateEditor.commit();
        }
        System.out.println(updatetime.getString("datetime", "") + globalsetting.datetime);

        BaseApplication.instance.setCurrentCmdType(BaseEnum.CMD_ESC);
        BaseApplication.instance.setCurrentConnectType(BaseEnum.CON_BLUETOOTH);
        printerFactory = new UniversalPrinterFactory();
        rtPrinter = printerFactory.create();
        PrinterObserverManager.getInstance().add(this);


    }

    private boolean checkIsTelevision() {
        int uiMode = getApplicationContext().getResources().getConfiguration().uiMode;
        System.out.println("tablet device");
        return (uiMode & Configuration.UI_MODE_TYPE_MASK) == Configuration.UI_MODE_TYPE_TELEVISION;

    }

    //set up UI
    private void setUI() {
        btnlogin = findViewById(R.id.login);
        btnexit = findViewById(R.id.exit);
        useredit = findViewById(R.id.useredit);
        passedit = findViewById(R.id.passedit);
        btnconnect = findViewById(R.id.btnconnect);
        btnRegister = findViewById(R.id.btnRegister);
        btnposdown = findViewById(R.id.btnposdown);
        txtSetting = findViewById(R.id.btnSetting);
        toggleButton = findViewById(R.id.toggleButton);
//        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                isTabletMode=toggleButton.isChecked();
//            }
//        });
////        if(!toggleButton.isChecked()){
////            isTabletMode=false;
////        }
//        if(!isTabletMode){
//            toggleButton.setChecked(false);
//        }
        btnlogin.setOnClickListener(this);
        btnexit.setOnClickListener(this);
        useredit.setOnClickListener(this);
        pb = new ProgressDialog(frmlogin.this, R.style.AlertDialogTheme);
        pb.setTitle("Data Downloading");
        pb.setMessage("Please a few minute..");
        pb.setCancelable(false);
        pb.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pb.setIndeterminate(true);
        btnconnect.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        btnposdown.setOnClickListener(this);
        txtSetting.setOnClickListener(this);
    }

    private boolean isRegister() {
        String reg = RegisterID.getString("register", "empty");
        boolean check = reg.equals("empty") ? false : true;
        return check;
    }

    private void GetDeviceName() {

        BluetoothAdapter myDevice = BluetoothAdapter.getDefaultAdapter();
        Device_Name = myDevice.getName();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btnSetting:
                try {
                    String printer_name = sh_printer.getString("printer", "");
                    String localprinter_ip = sh_ip.getString("localprinterip", "");
                    String printer_type = sh_ptype.getString("ptype", "");
                    //printer_type_id = Integer.parseInt(id);
                    AlertDialog.Builder bd = new AlertDialog.Builder(this);
                    View view = getLayoutInflater().inflate(R.layout.devicesetting, null);
                    bd.setView(view);
                    Button btnPrinter = view.findViewById(R.id.btnPrinter);
                    EditText edtPrintIP = view.findViewById(R.id.edtprintserver);

                    CardView bluetoothprintersetting = view.findViewById(R.id.btn_printer_settings);
                    TextView connectedTo = view.findViewById(R.id.tv_printer_info);
                    CardView btnprintertest = view.findViewById(R.id.btn_printer_test);

                    btnLocalConnect = view.findViewById(R.id.btnlocalconnect);

                    if (!printer_name.trim().equals("")) {
                        btnPrinter.setText(printer_name);
                    } else {
                        btnPrinter.setText("Choose");
                    }

                    assert localprinter_ip != null;
                    if (!localprinter_ip.isEmpty()) {
                        edtPrintIP.setText(localprinter_ip);
                    }

                    CheckLocalPrintConnection();
                    btnLocalConnect.setOnClickListener(v1 -> {
                        if (edtPrintIP.getText().toString().trim() != "") {
                            SharedPreferences.Editor editor = sh_ip.edit();
                            editor.remove("localprinterip");
                            editor.putString("localprinterip", edtPrintIP.getText().toString().trim());
                            editor.apply();
                            CheckLocalPrintConnection();
                        } else {
                            Toast.makeText(frmlogin.this, "Please Enter Printer Server IP!", Toast.LENGTH_LONG).show();
                        }
                    });

                    //region SunmiPrinter
                    LinearLayout layoutBTprinter = view.findViewById(R.id.layout_btprinter);
                    LinearLayout layoutSunmiprinter = view.findViewById(R.id.layout_sunmi_printer);
                    LinearLayout layoutLocalprinter = view.findViewById(R.id.layout_localprinter);
                    RelativeLayout layoutPrinter = view.findViewById(R.id.rl_printer);
                    RelativeLayout layoutPrintertype = view.findViewById(R.id.rl_printertype);
                    if (SunmiPrintHelper.getInstance().checkSunmiPrinter()) {
                        layoutSunmiprinter.setVisibility(View.VISIBLE);
                        layoutBTprinter.setVisibility(View.GONE);
                        layoutLocalprinter.setVisibility(View.GONE);
                        layoutPrinter.setVisibility(View.GONE);
                        layoutPrintertype.setVisibility(View.GONE);
                    } else {
                        layoutSunmiprinter.setVisibility(View.GONE);
                        layoutBTprinter.setVisibility(View.VISIBLE);
                    }

                    TextView sunmi_printerstatus = view.findViewById(R.id.txt_sunmi_printerstatus);
                    sunmi_printerstatus.setText(String.format("Sunmi POS Printer (%s)", SunmiPrintHelper.getInstance().showPrinterStatus(frmlogin.this)));

                    CardView btnSunmiPrintStatus = view.findViewById(R.id.btn_sunmi_printerstatus);
                    btnSunmiPrintStatus.setOnClickListener(v1 -> {
                        sunmi_printerstatus.setText(String.format("Sunmi POS Printer (%s)", SunmiPrintHelper.getInstance().showPrinterStatus(frmlogin.this)));
                    });

                    CardView btnSunmiPrintTest = view.findViewById(R.id.btn_sunmi_printertest);
                    btnSunmiPrintTest.setOnClickListener(v1 -> {
//                        SunmiPrintHelper.getInstance().printText("Galaxy Software\niStock Mobile TV Sale\n\n\nSunmi Printer Testing!",
//                                36, false, false, null);
                        SunmiPrintHelper.getInstance().printTest(context);
                    });

                    CardView btnSunmiPrintFeed = view.findViewById(R.id.btn_sunmi_printerfeed);
                    btnSunmiPrintFeed.setOnClickListener(v1 -> {
                        SunmiPrintHelper.getInstance().feedPaper();
                    });

                    CardView btnSunmiPrintCut = view.findViewById(R.id.btn_sunmi_printercut);
                    btnSunmiPrintCut.setOnClickListener(v1 -> {
                        SunmiPrintHelper.getInstance().cutpaper();
                    });

                    //endregion

                    CardView btPrinterSetting = view.findViewById(R.id.btn_btprinter_settings);
                    CardView btnBTprintertest = view.findViewById(R.id.btn_btprinter_test);
                    tv_device_selected = view.findViewById(R.id.tv_device_selected);

                    btPrinterSetting.setOnClickListener(v1 -> {
                        showConnectDialog();
                    });

                    btnBTprintertest.setOnClickListener(v1 -> {
                        try {
                            BluetoothPrinter bluetoothPrinter = new BluetoothPrinter(frmlogin.this, rtPrinter);
                            if (isInConnectList(configObj)) {
                                final String testPrintString = "Printer is connected!\n";
                                String callback = bluetoothPrinter.escTextPrint(testPrintString);
                                if (!callback.isEmpty()) {
                                    showToast(callback);
                                }
                            } else {
                                showToast("Please connect to Printer!");
                            }
                        } catch (UnsupportedEncodingException e) {
                            showToast(e.getMessage());
                            e.printStackTrace();
                        }
                    });

                    //added by TZW
                    bluetoothprintersetting.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showPrinterList(connectedTo, btnprintertest);
                        }
                    });
                    getSavedPrinter(connectedTo);

                    btnPrinter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(frmlogin.this);
                            View view = getLayoutInflater().inflate(R.layout.showposuser, null);
                            builder.setView(view);
                            lv = (ListView) view.findViewById(R.id.lsvposuer);
                            Printer_Type_Adpater ad = new Printer_Type_Adpater(Printers, frmlogin.this);
                            lv.setAdapter(ad);
                            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    btnPrinter.setText(Printers.get(position).toString());
                                    if (btnPrinter.getText().toString().trim().equals("")) {
                                        btnPrinter.setText("Choose");

                                    }
                                    msg.dismiss();

                                }
                            });
                            msg = builder.create();
                            msg.show();

                        }
                    });
                    Button btnPrinterType = view.findViewById(R.id.btnType);
                    if (!printer_type.trim().equals("")) {
                        btnPrinterType.setText(printer_type);
                    } else {
                        btnPrinterType.setText("Choose");
                    }
                    btnPrinterType.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(frmlogin.this);
                            View view = getLayoutInflater().inflate(R.layout.showposuser, null);
                            builder.setView(view);
                            lv = (ListView) view.findViewById(R.id.lsvposuer);
                            Printer_Type_Adpater ad = new Printer_Type_Adpater(frmlogin.this, ptype);
                            lv.setAdapter(ad);
                            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                    btnPrinterType.setText(ptype.get(position).getName());
                                    if (btnPrinterType.getText().toString().trim().equals("")) {
                                        btnPrinterType.setText("Choose");
                                    }

                                    printer_type_id = ptype.get(position).getPrinter_type_id();
                                    msg.dismiss();
                                }
                            });
                            msg = builder.create();
                            msg.show();
                        }
                    });
                    ImageButton ImgSave = view.findViewById(R.id.imgSave);
                    ImgSave.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            SharedPreferences.Editor editor = sh_printer.edit();
                            editor.remove("printer");
                            editor.commit();

                            editor = sh_printer.edit();
                            editor.putString("printer", btnPrinter.getText().toString().trim());
                            editor.commit();

                            editor = sh_ip.edit();
                            editor.remove("localprinterip");
                            editor.putString("localprinterip", edtPrintIP.getText().toString().trim());
                            editor.commit();

                            SharedPreferences.Editor editor1 = sh_ptype.edit();
                            editor1.remove("ptype");
                            editor1.commit();

                            editor1 = sh_ptype.edit();
                            editor1.putString("ptype", btnPrinterType.getText().toString().trim());
                            editor1.commit();

                            dialog.dismiss();
                        }
                    });
                    dialog = bd.create();
                    dialog.show();
                } catch (Exception ee) {
                    String s = ee.getMessage();
                    dialog.dismiss();
                }
                break;

            case R.id.btnconnect:
                ip = sh_ip.getString("ip", "empty");
                port = sh_port.getString("port", "empty");
                showIpBox(ip, port);
                break;

            case R.id.btnposdown:
                if (isRegister()) {
                    GetTableNames();
                    GetDownloading();
                } else {

                    new AlertDialog.Builder(this, R.style.AlertDialogTheme)
                            .setTitle("iStock")
                            .setMessage("Please, Register Before Downloading!")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .create().show();

                }
                break;
            case R.id.login:
                SignIn();
                break;
            case R.id.exit:
                finish();
                break;
            case R.id.useredit:
                showPosuser();
                break;
            case R.id.btnRegister:
                final String id = RegisterID.getString("register", "0");
                if (isRegister()) {


                    new AlertDialog.Builder(this, R.style.AlertDialogTheme)
                            .setTitle("iStock")
                            .setMessage("Already register!Do you want to register again?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    setRegister(id);
                                    dialog.dismiss();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .create().show();

                } else {

                    setRegister(id);
                }
                break;
        }


    }

    private void showConnectDialog() {
        if (!BluetoothAdapter.getDefaultAdapter().isEnabled()) { //蓝牙未开启，则开启蓝牙
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        } else {
            showBluetoothDeviceChooseDialog();
        }
    }


    //added by TZW
    private void getSavedPrinter(TextView connectedTo) {
        BluetoothDevice connectedPrinter = Printama.with(this).getConnectedPrinter();
        if (connectedPrinter != null) {
            //TextView connectedTo = findViewById(R.id.tv_printer_info);
            String text = "Connected to : " + connectedPrinter.getName();
            connectedTo.setText(text);
        }
    }

    private void showPrinterList(TextView connectedTo, CardView printertest) {
        Printama.showPrinterList(this, R.color.colorBlue, printerName -> {
            //Toast.makeText(this, printerName, Toast.LENGTH_SHORT).show();
            //TextView connectedTo = findViewById(R.id.tv_printer_info);
            String text = "Connected to : " + printerName;
            connectedTo.setText(text);
            if (!printerName.contains("failed")) {
                printertest.setVisibility(View.VISIBLE);
                printertest.setOnClickListener(v -> testPrinter());
            }
        });
    }

    private void testPrinter() {
        String s = "Your Printer is Connected.";
        SpannableString ss1 = new SpannableString(s);
        ss1.setSpan(new RelativeSizeSpan(2f), 0, 5, 0); // set size
        ss1.setSpan(new ForegroundColorSpan(Color.RED), 0, 5, 0);// set color
        Printama.with(this).printTest(ss1.toString());
    }

    private void setRegister(final String id) {
        AlertDialog.Builder pass = new AlertDialog.Builder(frmlogin.this, R.style.AlertDialogTheme);
        View password = getLayoutInflater().inflate(R.layout.registerbox, null);
        pass.setView(password);
        final EditText etdpass = password.findViewById(R.id.edtkey);
        Button btn = password.findViewById(R.id.btnrgok);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DATE);
                int month = calendar.get(Calendar.MONTH) + 1;
                int year = calendar.get(Calendar.YEAR);
                int PlusResult = day + month + year;

                String divResult = String.valueOf(day) + String.valueOf(month) + String.valueOf(year);
                String Result = String.valueOf(Long.parseLong(divResult) / PlusResult);
                if (etdpass.getText().toString().contentEquals(Result)) {
                    String Device_ID = Build.ID;
                    String Build_Number = Build.DISPLAY;
                    String sqlstring = "RegisterID=" + id + "&ID=" + Device_ID + "&Build=" + Build_Number + "&Name=" + Device_Name +
                            "";
                    //Device_Name;
                    Register(sqlstring);
                    showmsg.dismiss();
                }
            }
        });
        showmsg = pass.create();
        showmsg.show();
    }

    private void Register(String sqlString) {
        ip = sh_ip.getString("ip", "empty");
        port = sh_port.getString("port", "empty");
        try {
            sqlString = URLEncoder.encode(sqlString, "UTF-8").replace("+", "%20")
                    .replace("%26", "&").replace("%3D", "=")
                    .replace("%2C", ",")
                    .replace("%27", "''")
            ;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = "http://" + ip + ":" + port + "/api/mobile/Register?" + sqlString + "&register=true";
        Log.i("frmlogin", url);
        RequestQueue request = Volley.newRequestQueue(context);
        Response.Listener listener = new Response.Listener() {
            @Override
            public void onResponse(Object response) {

                final String[] result = response.toString().split("/");
                AlertDialog.Builder bd = new AlertDialog.Builder(frmlogin.this, R.style.AlertDialogTheme);
                bd.setMessage(result[0]);
                bd.setTitle("iStock");
                bd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

//                        Modified by ZYP [19-04-2022]
//                        if (!result[1].isEmpty()) {
                        if (result.length > 1) {
                            SharedPreferences.Editor editor = RegisterID.edit();
                            editor.remove("register");
                            editor.commit();

                            editor = RegisterID.edit();
                            editor.putString("register", result[1]);
                            editor.commit();
                            isRegister();

                            //Update Downloaded DateTime to AccessedUser
                            String url = "http://" + ip + ":" + port + "/api/mobile/RegisterUsingIMEI?imei=" + GettingIMEINumber.IMEINO + "&lastupdatedatetime=" + "1990-01-01" + "&lastaccesseduserid=" + LoginUserid + "&clientname=" + Device_Name;
                            Log.i("frmlogin", url);
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
                            StringRequest reqq = new StringRequest(Request.Method.GET, url, listenerr, errorr);
                            requestt.add(reqq);
                            showmsg.dismiss();
                        }
                        dialog.dismiss();
                        showmsg.dismiss();

                    }
                });
                dialog = bd.create();
                dialog.show();
            }
        };

        final Response.ErrorListener error = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder bd = new AlertDialog.Builder(frmlogin.this, R.style.AlertDialogTheme);
                bd.setMessage("Process is Fail!Check your Network Connection");
                bd.setTitle("iStock");

                bd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        showmsg.dismiss();
                    }
                });
                dialog = bd.create();
                dialog.show();
            }
        };
        StringRequest req = new StringRequest(Request.Method.GET, url, listener, error);
        request.add(req);
    }

    private void GetTableNames() {
        if (tableNames.size() > 0) tableNames.clear();
        tableNames.add("Posuser");
        tableNames.add("Class");
        tableNames.add("Category");
        //tableNames.add("category");
        tableNames.add("Customer");
        tableNames.add("Location");
        tableNames.add("Usr_Code");
        tableNames.add("Payment_Type");
        tableNames.add("Dis_Type");
        tableNames.add("SystemSetting");
        tableNames.add("Salesmen");
        tableNames.add("Branch");
        tableNames.add("usr_code_img"); //added by EKK on 28-10-2020
        tableNames.add("s_saleprice"); //added by EKK on 10-11-2020
        tableNames.add("Alias_Code"); //added by EKK on 16-11-2020
        tableNames.add("cashbook_user"); //added by EKK on 16-11-2020
        tableNames.add("userroles");
        tableNames.add("discount_code");
        //tableNames.add("menu_user");
    }

    private void GetDownloading() {
        GetData();
    }

    private void ResetData() {
//        sqlString = "delete from Customer";
//        DatabaseHelper.execute(sqlString);
//        sqlString = "delete from usr_code";
//        DatabaseHelper.execute(sqlString);
//
//        sqlString = "delete from Posuser";
//        DatabaseHelper.execute(sqlString);
//
//        sqlString = "delete from Location";
//        DatabaseHelper.execute(sqlString);
//
//
//        sqlString = "delete from SystemSetting";
//        DatabaseHelper.execute(sqlString);
//
//        sqlString = "delete from Payment_Type";
//        DatabaseHelper.execute(sqlString);
//
//        sqlString = "delete from Dis_Type";
//        DatabaseHelper.execute(sqlString);
//
//        sqlString = "delete from AppSetting";
//        DatabaseHelper.execute(sqlString);
//
//        sqlString = "delete from Salesmen";
//        DatabaseHelper.execute(sqlString);
//
//        sqlString = "delete from Category";
//        DatabaseHelper.execute(sqlString);
//
//        sqlString = "delete from Class";
//        DatabaseHelper.execute(sqlString);
//
//        sqlString = "delete from Branch";
//        DatabaseHelper.execute(sqlString);
//
//        sqlString ="delete from Alias_Code";
//        DatabaseHelper.execute(sqlString);
//
//        sqlString="delete from usr_code_img";
//        DatabaseHelper.execute(sqlString);
//
//        sqlString="delete from menu_user";
//        DatabaseHelper.execute(sqlString);
    }

    private void GetData() {
        try {

//            ResetData();
            AlertDialog.Builder bdProgress = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
            View view = getLayoutInflater().inflate(R.layout.downloadprocess, null);
            pbDownload = view.findViewById(R.id.progressDownload);
            txtProgress = view.findViewById(R.id.txtProgress);
            txtTable = view.findViewById(R.id.txtTable);


            txtProgress.setText("0/" + tableNames.size());
            //  txtProgress.setText("0/0");
            pbDownload.setMax(tableNames.size());
            bdProgress.setCancelable(false);
            bdProgress.setView(view);
            downloadAlert = bdProgress.create();
            context = this;
            downloadAlert.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(final DialogInterface dialog) {
                    ResetData();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String currentDateTime = sdf.format(new Date());

//                    System.out.println(updatetime.getString("datetime", ""));
//                    if (updatetime.getString("datetime", "").equals("1990-01-01")) {
//                        currentDateTime = updatetime.getString("datetime", "");
//                        dateEditor = updatetime.edit();
//                        dateEditor.remove("datetime");
//                        dateEditor.apply();
//
//                        dateEditor = updatetime.edit();
//                        dateEditor.putString("datetime", "");
//                        dateEditor.commit();
//                    }
                    ip = sh_ip.getString("ip", "empty");
                    port = sh_port.getString("port", "empty");
                    String urlString = "http://" + ip + ":" + port + "/api/mobile/GetData?download=true&_macaddress=" + GettingIMEINumber.IMEINO;
                    Log.i("frmlogin", urlString);
                    RequestQueue request = Volley.newRequestQueue(context);
                    final Response.Listener<String> listener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {

                                        JSONArray jsonArray = new JSONArray(response);
                                        jobj = jsonArray.getJSONObject(0);

                                        for (int progress = 0; progress < tableNames.size(); progress++) {

                                            SelectInsertLibrary selectInsertLibrary = new SelectInsertLibrary();
                                            selectInsertLibrary.UpSertingData(tableNames.get(progress), jobj);
                                            int finalProgress = progress;
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    txtProgress.setText((finalProgress + 1) + "/" + tableNames.size());
                                                    txtTable.setText(tableNames.get(finalProgress));
                                                }
                                            });

                                            pbDownload.setProgress(progress + 1);
                                        }

                                        //Update Downloaded DateTime to AccessedUser
                                        String url = "http://" + ip + ":" + port + "/api/mobile/RegisterUsingIMEI?imei=" + GettingIMEINumber.IMEINO + "&lastupdatedatetime=" + currentDateTime + "&lastaccesseduserid=" + LoginUserid + "&clientname=" + Device_Name;
                                        Log.i("frmlogin", url);
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
                                        StringRequest reqq = new StringRequest(Request.Method.GET, url, listenerr, errorr);
                                        requestt.add(reqq);

                                        dialog.dismiss();

                                    } catch (Exception ee) {
                                        Toast.makeText(context, "You are in Offline. Please check your connection!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).start();


                        }
                    };

                    final Response.ErrorListener error = new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(frmlogin.this, "You are in Offline. Please check your connection!", Toast.LENGTH_SHORT).show();
                        }
                    };
                    StringRequest req = new StringRequest(Request.Method.GET, urlString, listener, error);
                    req.setRetryPolicy(new DefaultRetryPolicy(
                            60000,
                            1,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    request.add(req);


                }

            });
            downloadAlert.show();
        } catch (Exception ee) {
            Toast.makeText(this, "You are in Offline. Please check your connection!", Toast.LENGTH_SHORT).show();
        }

    }


    private void SignIn() {
        String username = useredit.getText().toString();
        String password = passedit.getText().toString();
        if (password.isEmpty()) {
            password = "null";
        }
        int userid = 0;
        String name = "";
        String pass = "";
        sqlString = "select userid,name,isknockcode from posuser where userid=" + frmlogin.LoginUserid;
        Cursor cursor = DatabaseHelper.rawQuery(sqlString);
        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    userid = cursor.getInt(cursor.getColumnIndex("userid"));
                    name = cursor.getString(cursor.getColumnIndex("name"));
                    pass = cursor.getString(cursor.getColumnIndex("isknockcode"));

                } while (cursor.moveToNext());

            }

        }
        cursor.close();
       /* String sqls="select * from class";
        Cursor cursor1=DatabaseHelper.rawQuery(sqls);
        Toast.makeText(frmlogin.this,cursor1.getCount()+"no of record in class",Toast.LENGTH_LONG).show();
        while (cursor1.getCount()>0){
            if(cursor1.moveToFirst()){
                Toast.makeText(frmlogin.this,cu)
            }
        }
        cursor1.close();*/
        if (username.equals(name) && password.equals(pass)) {
            try {

                LockUser(frmlogin.LoginUserid, true);

            } catch (Exception e) {

            }

        } else {
            Toast.makeText(this, "Username and Password does not match", Toast.LENGTH_LONG).show();
        }
    }

    private void LockUser(int userid, Boolean locked) {
        String ip = sh_ip.getString("ip", "Localhost");
        String port = sh_port.getString("port", "80");
        String Device = frmlogin.Device_Name.replace(" ", "%20");
        String Url = "http://" + ip + ":" + port + "/api/mobile/LockUser?userid=" + frmlogin.LoginUserid + "&hostname=" + Device + "&locked=" + locked;
        Log.i("frmlogin", Url);
        requestQueue = Volley.newRequestQueue(this);

        final Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("True")) {
                    DatabaseHelper.execute("delete from Login_User where userid=" + frmlogin.LoginUserid);
                    DatabaseHelper.execute("insert into Login_User(userid,hostname)values(" + frmlogin.LoginUserid + ",'" + frmlogin.Device_Name + "')");
                    Intent intent = new Intent(frmlogin.this, frmmain.class);
                    startActivity(intent);
                    finish();
                } else {
                    String hostname = "empty";
                    Cursor cursor = DatabaseHelper.rawQuery("select hostname from Login_user where userid=" + frmlogin.LoginUserid);
                    if (cursor != null && cursor.getCount() != 0) {
                        if (cursor.moveToFirst()) {
                            do {
                                hostname = cursor.getString(cursor.getColumnIndex("hostname"));

                            } while (cursor.moveToNext());
                        }
                    }
                    cursor.close();

                    if (!hostname.equals(frmlogin.Device_Name)) {
                        AlertDialog.Builder bd = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
                        bd.setTitle("iStock");
                        bd.setMessage("The User is already Login");
                        bd.setCancelable(false);
                        bd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                msg.dismiss();
                            }
                        });
                        msg = bd.create();
                        msg.show();
                    } else {
                        Intent intent = new Intent(frmlogin.this, frmmain.class);
                        startActivity(intent);
                        finish();
                    }

                }
            }


        };

        final Response.ErrorListener error = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //added offline
                DatabaseHelper.execute("delete from Login_User where userid=" + frmlogin.LoginUserid);
                DatabaseHelper.execute("insert into Login_User(userid,hostname)values(" + frmlogin.LoginUserid + ",'" + frmlogin.Device_Name + "')");
                Intent intent = new Intent(frmlogin.this, frmmain.class);
                startActivity(intent);
                finish();
                //added offline
//                Toast.makeText(frmlogin.this, error.getMessage()+" this is error", Toast.LENGTH_LONG).show();
            }
        };


        StringRequest req = new StringRequest(Request.Method.GET, Url, listener, error);
        requestQueue.add(req);
    }

    private void showPosuser() {

        aryUsers.clear();
        builder = new AlertDialog.Builder(frmlogin.this, R.style.AlertDialogTheme);
        View view = getLayoutInflater().inflate(R.layout.showposuser, null);
        builder.setCancelable(false);
        builder.setView(view);
        lv = (ListView) view.findViewById(R.id.lsvposuer);
        sqlString = "select  * from Posuser where isinactive=0 and istabletuser=1";
        Cursor cursor = DatabaseHelper.rawQuery(sqlString);
        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    int userid = cursor.getInt(cursor.getColumnIndex("userid"));
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    int branchid = cursor.getInt(cursor.getColumnIndex("branchid"));
                    String shortdesc = cursor.getString(cursor.getColumnIndex("shortdesc"));
                    int canchangedate = cursor.getInt(cursor.getColumnIndex("canchangedate"));
                    String password = cursor.getString(cursor.getColumnIndex("password"));
                    int canchangesaleprice = cursor.getInt(cursor.getColumnIndex("canchangesaleprice"));
                    int canchangepurprice = cursor.getInt(cursor.getColumnIndex("canchangepurprice"));
                    int defaultlocationid = cursor.getInt(cursor.getColumnIndex("defaultlocationid"));
                    int candiscount = cursor.getInt(cursor.getColumnIndex("candiscount"));
                    int isusetax = cursor.getInt(cursor.getColumnIndex("isusetax"));
                    int ishidepurprice = cursor.getInt(cursor.getColumnIndex("ishidepurprice"));
                    int ishidesaleprice = cursor.getInt(cursor.getColumnIndex("ishidesaleprice"));
                    int ishidepurcostprice = cursor.getInt(cursor.getColumnIndex("ishidepurcostprice"));
                    int isviewallsalepricelevel = cursor.getInt(cursor.getColumnIndex("isviewallsalepricelevel"));
                    int isinactive = cursor.getInt(cursor.getColumnIndex("isinactive"));
                    int defaultbranchid = cursor.getInt(cursor.getColumnIndex("defaultbranchid"));
                    int defaultcashid = cursor.getInt(cursor.getColumnIndex("defaultcashid"));
                    int isallowsysdatechange = cursor.getInt(cursor.getColumnIndex("isallowsysdatechange"));
                    int isallowovercreditlimit = cursor.getInt(cursor.getColumnIndex("isallowovercreditlimit"));
                    String isknockcode = cursor.getString(cursor.getColumnIndex("isknockcode"));
                    int istabletuser = cursor.getInt(cursor.getColumnIndex("istabletuser"));
                    int istvuser = cursor.getInt(cursor.getColumnIndex("istvuser"));

                    int isallowpricelevel = cursor.getInt(cursor.getColumnIndex("isallowpricelevel"));
                    int canselectcustomer = cursor.getInt(cursor.getColumnIndex("canselectcustomer"));
                    int canselectlocation = cursor.getInt(cursor.getColumnIndex("canselectlocation"));






                    /*String pass=cursor.getString(cursor.getColumnIndex("knockcode"));
                    int locid=cursor.getInt(cursor.getColumnIndex("def_locationid"));
                    int Confirm_PrintVou=cursor.getInt(cursor.getColumnIndex("Confirm_PrintVou"));
                    int allow_priceLevel= cursor.getInt(cursor.getColumnIndex("allow_pricelevel"));
                    int select_location= cursor.getInt(cursor.getColumnIndex("select_location"));
                    int select_customer= cursor.getInt(cursor.getColumnIndex("select_customer"));
                    int change_price= cursor.getInt(cursor.getColumnIndex("change_price"));
                    int change_date= cursor.getInt(cursor.getColumnIndex("change_date"));
                    int tax=cursor.getInt(cursor.getColumnIndex("tax"));
                    int discount=cursor.getInt(cursor.getColumnIndex("discount"));
                    int def_payment=cursor.getInt(cursor.getColumnIndex("def_payment"));
                    int Allow_Over_Credit_Limit=cursor.getInt((cursor.getColumnIndex("Allow_Over_Credit_Limit")));
                    int def_cashid=cursor.getInt(cursor.getColumnIndex("def_cashid"));
                    String Cashier_Printer=cursor.getString(cursor.getColumnIndex("Cashier_Printer"));
                    int CAshier_PrinterType=cursor.getInt(cursor.getColumnIndex("Cashier_PrinterType"));
                    */
                    aryUsers.add(new posuser(userid, name, branchid, shortdesc, password, canchangesaleprice, canchangepurprice, canchangedate, defaultlocationid, candiscount, isusetax, ishidepurprice, ishidesaleprice, ishidepurcostprice, isviewallsalepricelevel, isinactive, defaultbranchid, defaultcashid, isallowsysdatechange, isallowovercreditlimit, isknockcode, istabletuser, istvuser, isallowpricelevel, canselectcustomer, canselectlocation));/*posuser(userid,name,branchid,password,canchangesaleprice,canchangepurprice,defaultlocationid,candiscount,isusetax,ishidepurprice,ishidesaleprice,ishidepurcostprice,isviewallsalepricelevel,isinactive,defaultbranchid,defaultcashid,isallowsysdatechange,isallowovercreditlimit,isknockcode,istabletuser,isallowpricelevel));*///,Confirm_PrintVou,allow_priceLevel,select_location,select_customer, change_date,tax,discount,change_price,pass,locid,def_payment,Allow_Over_Credit_Limit,def_cashid,Cashier_Printer,CAshier_PrinterType));


                } while (cursor.moveToNext());

            }

        }
        cursor.close();
        posuserAdapter ad = new posuserAdapter(frmlogin.this, aryUsers);
        lv.setAdapter(ad);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                useredit.setText(aryUsers.get(position).getName());
                frmlogin.username = aryUsers.get(position).getName();

                frmlogin.LoginUserid = aryUsers.get(position).getUserid();
                frmlogin.name = aryUsers.get(position).getName();
                frmlogin.branchid = aryUsers.get(position).getBranchid();
                frmlogin.shortdesc = aryUsers.get(position).getShortdesc();
                frmlogin.password = aryUsers.get(position).getPassword();
                frmlogin.canchangesaleprice = aryUsers.get(position).getCanchangesaleprice();
                frmlogin.canchangepurprice = aryUsers.get(position).getCanchangepurprice();
                frmlogin.canchangedate = aryUsers.get(position).getCanchangedate();
                frmlogin.det_locationid = aryUsers.get(position).getDefaultlocationid();
                frmlogin.candiscount = aryUsers.get(position).getCandiscount();
                frmlogin.isusetax = aryUsers.get(position).getIsusetax();
                frmlogin.ishidepurprice = aryUsers.get(position).getIshidepurprice();
                frmlogin.ishidesaleprice = aryUsers.get(position).getIshidesaleprice();
                frmlogin.ishidepurcostprice = aryUsers.get(position).getIshidepurcostprice();
                frmlogin.isviewallsalepricelevel = aryUsers.get(position).getIsviewallsalepricelevel();
                frmlogin.isinactive = aryUsers.get(position).getIsinactive();
                frmlogin.isallowsysdatechange = aryUsers.get(position).getIsallowsysdatechange();
                frmlogin.isallowovercreditlimit = aryUsers.get(position).getIsallowovercreditlimit();
                frmlogin.isknockcode = aryUsers.get(position).getIsknockcode();
                frmlogin.istabletuser = aryUsers.get(position).getIstabletuser();
//                frmlogin.istvuser=aryUsers.get(position).getIstvuser();
                frmlogin.isallowpricelevel = aryUsers.get(position).getIsallowpricelevel();
                frmlogin.canselectcustomer = aryUsers.get(position).getCanselectcustomer();
                frmlogin.canselectlocation = aryUsers.get(position).getCanselectlocation();
                frmlogin.defaultbranchid = aryUsers.get(position).getDefaultbranchid();

                //added by EKK on 17-11-2020
                sqlString = "select * from cashbook_user where  isenabled =  1 and  isdefaultcash = 1 and userid = " + frmlogin.LoginUserid;
                Cursor cursor = DatabaseHelper.rawQuery(sqlString);
                if (cursor != null && cursor.getCount() != 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            int accountid = cursor.getInt(cursor.getColumnIndex("accountid"));
                            frmlogin.def_cashid = accountid;
                        } while (cursor.moveToNext());

                    }
                } else {

                    sqlString = "select * from cashbook_user where  isenabled =  1 and userid = " + frmlogin.LoginUserid + " Limit 1";
                    cursor = DatabaseHelper.rawQuery(sqlString);
                    if (cursor != null && cursor.getCount() != 0) {
                        if (cursor.moveToFirst()) {
                            do {
                                int accountid = cursor.getInt(cursor.getColumnIndex("accountid"));
                                frmlogin.def_cashid = accountid;
                            } while (cursor.moveToNext());

                        }
                    } else {
                        frmlogin.def_cashid = aryUsers.get(position).getDefaultcashid();
                    }
                }

                dialog.dismiss();
            }
        });
        dialog = builder.create();
        if (aryUsers.size() > 0) {
            dialog.show();
            dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        } else {
            Toast.makeText(frmlogin.this, "Please Download Posuser!!", Toast.LENGTH_LONG).show();

        }

    }


    private void showIpBox(String ip, String port) {
        builder = new AlertDialog.Builder(frmlogin.this, R.style.AlertDialogTheme);
        View view = getLayoutInflater().inflate(R.layout.ipaddressbox, null);
        builder.setView(view);
        builder.setCancelable(false);
        edtserver = view.findViewById(R.id.edtserver);
        edtport = view.findViewById(R.id.edtport);

        if (!ip.equals("empty")) {
            edtserver.setText(ip);
        }
        if (!port.equals("empty")) {
            edtport.setText(port);
        }
        btnok = (Button) view.findViewById(R.id.btnok);
        btncancel = (Button) view.findViewById(R.id.btncancel);
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
//serveraddr trim() in login form modified by ABBP
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((!edtserver.getText().toString().trim().isEmpty() || edtserver.getText().toString().trim() != "")) {
                    SharedPreferences.Editor editor = sh_ip.edit();
                    editor.remove("ip");
                    editor.commit();

                    editor = sh_ip.edit();
                    editor.putString("ip", edtserver.getText().toString().trim());
                    editor.commit();

                    SharedPreferences.Editor editor_port = sh_port.edit();
                    editor_port.remove("port");
                    editor_port.commit();
                    editor_port = sh_port.edit();
                    if (edtport.getText().toString().trim().isEmpty() || edtport.getText().toString().trim() == "") {
                        edtport.setText("80");
                    }
                    editor_port.putString("port", edtport.getText().toString().trim());
                    editor_port.commit();
                    dialog.dismiss();
                    CheckConnection();
                } else {
                    Toast.makeText(frmlogin.this, "Fill Server Information", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog = builder.create();
        dialog.show();
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);


    }

    private void CheckConnection() {

        if (sh_ip.getString("ip", "empty").equals("empty") || sh_port.getString("port", "empty").equals("empty")) {
            //btnconnect.setImageResource(R.drawable.disconnect);
            btnconnect.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.wifidis, 0, 0);
            btnlogin.setEnabled(false);
            useredit.setEnabled(false);
            Toast.makeText(frmlogin.this, "Please Connect to server", Toast.LENGTH_LONG).show();
            return;
        }

        String ip = sh_ip.getString("ip", "Localhost");
        String port = sh_port.getString("port", "80");
        // String Url="http://"+ip+":"+port+"/api/DataSync/GetData";
        String Url = "http://" + ip + ":" + port + "/api/mobile/CheckConnection";
        requestQueue = Volley.newRequestQueue(this);

        final Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //btnconnect.setImageResource(R.drawable.connect);
                btnconnect.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.wificonnect, 0, 0);
                //btnLogin.setEnabled(true);
                useredit.setEnabled(true);
                btnlogin.setEnabled(true);
                if (ptype.size() > 0) ptype.clear();
                if (Printers.size() > 0) Printers.clear();
                //Binding_PrinterSetting();
                Toast.makeText(frmlogin.this, "Server is Connected", Toast.LENGTH_LONG).show();
            }


        };

        final Response.ErrorListener error = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(frmlogin.this, "You are in Offline. Please check your connection!", Toast.LENGTH_LONG).show();
                btnconnect.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.wifidis, 0, 0);
            }
        };


        StringRequest req = new StringRequest(Request.Method.GET, Url, listener, error);
        requestQueue.add(req);

    }

    //added by ZYP for localprinter
    private void CheckLocalPrintConnection() {

        if (sh_ip.getString("localprinterip", "empty").equals("empty")) {
            //btnconnect.setImageResource(R.drawable.disconnect);
            btnLocalConnect.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.wifidis, 0, 0);
//            btnlogin.setEnabled(false);
//            useredit.setEnabled(false);
            //Toast.makeText(frmlogin.this, "Please Enter Server IP Address!", Toast.LENGTH_LONG).show();
            return;
        }

        String ip = sh_ip.getString("localprinterip", "Localhost");
        //String port = sh_port.getString("port", "80");
        // String Url="http://"+ip+":"+port+"/api/DataSync/GetData";
        //String Url = "http://" + ip + ":" + port + "/api/mobile/CheckConnection";
        String Url = "http://" + ip + "/api/mobile/CheckConnection";

        requestQueue = Volley.newRequestQueue(this);

        final Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //btnconnect.setImageResource(R.drawable.connect);
                btnLocalConnect.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.wificonnect, 0, 0);
                //btnLogin.setEnabled(true);
//                useredit.setEnabled(true);
//                btnlogin.setEnabled(true);
                if (ptype.size() > 0) ptype.clear();
                if (Printers.size() > 0) Printers.clear();
                Binding_PrinterSetting();
                Toast.makeText(frmlogin.this, "Printer Server is Connected", Toast.LENGTH_LONG).show();
            }


        };

        final Response.ErrorListener error = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(frmlogin.this, "Please check your connection!", Toast.LENGTH_LONG).show();
                btnLocalConnect.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.wifidis, 0, 0);
            }
        };


        StringRequest req = new StringRequest(Request.Method.GET, Url, listener, error);
        requestQueue.add(req);

    }

    private void Binding_PrinterSetting() {
        if (Printers.size() > 0) Printers.clear();
        if (ptype.size() > 0) ptype.clear();
        ip = sh_ip.getString("localprinterip", "empty");
        //port = sh_port.getString("port", "80");
        //String url = "http://" + ip + ":" + port + "/api/mobile/GetPrinter?&printer=true";
        String url = "http://" + ip + "/api/mobile/GetPrinter?&printer=true";
        Log.i("frmlogin", url);
        RequestQueue request = Volley.newRequestQueue(context);
        Response.Listener listener = new Response.Listener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(Object response) {
                try {//Added by KLM for YGN1-220410 21042022
                    String[] str = response.toString().split("&&");
                    String[] prt = str[0].split(",");
                    String[] pty = str[1].split(",");
                    for (int i = 0; i < prt.length; i++) {
                        Printers.add(prt[i]);
                    }

                    ptype.add(new Printer_Type(0, "A5"));
                    ptype.add(new Printer_Type(1, "A4"));
                    ptype.add(new Printer_Type(2, "Slip"));

//                for (int i = 0; i < pty.length; i++) {
//                    if (i == 0) {
//                        ptype.add(new Printer_Type((-1), pty[i]));
//                    } else {
//                        ptype.add(new Printer_Type((i), pty[i]));
//                    }
//                }
                } catch (Exception ex) {
                    Toast.makeText(frmlogin.this, "Something went wong! Please check Local Printer Link!", Toast.LENGTH_SHORT).show();
                }
            }
        };
        final Response.ErrorListener error = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder bd = new AlertDialog.Builder(frmlogin.this);
                bd.setMessage("Check your Network Connection.");
                bd.setTitle("iStock");

                bd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        showmsg.dismiss();
                    }
                });
                dialog = bd.create();
                dialog.show();
            }
        };

        StringRequest req = new StringRequest(Request.Method.GET, url, listener, error);
        request.add(req);
    }

    private void SettingIMEI() {
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        //        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider calling
//                //    Activity#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for Activity#requestPermissions for more details.
//                return;
//            }
//        }
        int permission = ContextCompat.checkSelfPermission(frmlogin.this, Manifest.permission.READ_PHONE_STATE);
        System.out.println(permission);
        if (permission == PackageManager.PERMISSION_GRANTED) {
            System.out.println(telephonyManager.getDeviceId());
        } else {
            ActivityCompat.requestPermissions(frmlogin.this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_CODE);
            System.out.println(telephonyManager.getDeviceId());
            return;
        }

        GettingIMEINumber.IMEINO = telephonyManager.getDeviceId();
        //Toast.makeText(getApplicationContext(),GettingIMEINumber.IMEINO+" this is imei", Toast.LENGTH_LONG).show();
    }


    private void showBluetoothDeviceChooseDialog() {
        BluetoothDeviceChooseDialog bluetoothDeviceChooseDialog = new BluetoothDeviceChooseDialog();
        bluetoothDeviceChooseDialog.setOnDeviceItemClickListener(new BluetoothDeviceChooseDialog.onDeviceItemClickListener() {
            @Override
            public void onDeviceItemClick(BluetoothDevice device) {
                if (TextUtils.isEmpty(device.getName())) {
                    tv_device_selected.setText(device.getAddress());
                } else {
                    tv_device_selected.setText(device.getName());// + " [" + device.getAddress() + "]");
                }
                configObj = new BluetoothEdrConfigBean(device);
                tv_device_selected.setTag(BaseEnum.HAS_DEVICE);
//                isConfigPrintEnable(configObj);
                doConnect();
            }
        });
        bluetoothDeviceChooseDialog.show(frmlogin.this.getFragmentManager(), null);
    }

    private void doConnect() {
//        pb_connect.setVisibility(View.VISIBLE);
        BluetoothEdrConfigBean bluetoothEdrConfigBean = (BluetoothEdrConfigBean) configObj;
        iPrintTimes = 0;
        connectBluetooth(bluetoothEdrConfigBean);
//        connectBluetoothByMac();
//        pb_connect.setVisibility(View.GONE);

    }

    private void connectBluetooth(BluetoothEdrConfigBean bluetoothEdrConfigBean) {
        PIFactory piFactory = new BluetoothFactory();
        PrinterInterface printerInterface = piFactory.create();
        printerInterface.setConfigObject(bluetoothEdrConfigBean);
        rtPrinter.setPrinterInterface(printerInterface);

        BaseApplication.getInstance().setRtPrinter(rtPrinter);
        try {
            rtPrinter.connect(bluetoothEdrConfigBean);
        } catch (Exception e) {
            e.printStackTrace();
            // Log.e("mao",e.getMessage());
        } finally {

        }
    }

    private boolean isInConnectList(Object configObj) {
        if (configObj == null) return false;
        boolean isInList = false;
        for (int i = 0; i < printerInterfaceArrayList.size(); i++) {
            PrinterInterface printerInterface = printerInterfaceArrayList.get(i);
            if (configObj.toString().equals(printerInterface.getConfigObject().toString())) {
                if (printerInterface.getConnectState() == ConnectStateEnum.Connected) {
                    isInList = true;
                    break;
                }
            }
        }

        return isInList;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK) {
                showBluetoothDeviceChooseDialog();
            }
        }
    }

    @Override
    public void printerObserverCallback(PrinterInterface printerInterface, int state) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //pb_connect.setVisibility(View.GONE);
                try {
                    switch (state) {
                        case CommonEnum.CONNECT_STATE_SUCCESS:
//                        TimeRecordUtils.record("RT连接end：", System.currentTimeMillis());
                            showToast(printerInterface.getConfigObject().toString() + " - connect success");
                            tv_device_selected.setText(printerInterface.getConfigObject().toString());
                            tv_device_selected.setTag(BaseEnum.HAS_DEVICE);
                            curPrinterInterface = printerInterface;//设置为当前连接， set current Printer Interface
                            printerInterfaceArrayList.add(printerInterface);//多连接-添加到已连接列表
                            rtPrinter.setPrinterInterface(printerInterface);
//                          BaseApplication.getInstance().setRtPrinter(rtPrinter);
//                        setPrintEnable(true);
                            break;
                        case CommonEnum.CONNECT_STATE_INTERRUPTED:
                            if (printerInterface != null && printerInterface.getConfigObject() != null) {
                                showToast(printerInterface.getConfigObject().toString() + " disconnected");
                            } else {
                                showToast("disconnected");
                            }
//                        TimeRecordUtils.record("RT连接断开：", System.currentTimeMillis());
//                        tv_device_selected.setText(R.string.please_connect);
                            tv_device_selected.setTag(BaseEnum.NO_DEVICE);
                            curPrinterInterface = null;
                            printerInterfaceArrayList.remove(printerInterface);//多连接-从已连接列表中移除
                            //  BaseApplication.getInstance().setRtPrinter(null);
//                        setPrintEnable(false);

                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void printerReadMsgCallback(PrinterInterface printerInterface, byte[] bytes) {

    }

    private void showToast(String message) {
        Toast.makeText(frmlogin.this, message, Toast.LENGTH_LONG).show();
    }

}