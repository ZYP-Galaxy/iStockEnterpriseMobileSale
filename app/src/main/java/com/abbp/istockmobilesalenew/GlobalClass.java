package com.abbp.istockmobilesalenew;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.net.URL;
import java.net.URLConnection;

public class GlobalClass {
    public static void showToast(Context ctx, String message) {
        Toast.makeText(ctx, message, Toast.LENGTH_LONG).show();
    }

    public static void showAlertDialog(Context ctx, String title, String message) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(ctx, R.style.AlertDialogTheme);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setPositiveButton("OK", (dialog1, which) -> {
        });
        dialog.create().show();
    }

    static ProgressDialog progressDialog;

    public static void showProgressDialog(Context context, String message) {
        progressDialog = new ProgressDialog(context, R.style.AlertDialogTheme);
        progressDialog.setTitle(R.string.app_name);
        progressDialog.setMessage(message);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public static void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public static void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static boolean isConnectedToServer(String url) {
        try {
            URL myUrl = new URL(url);
            URLConnection connection = myUrl.openConnection();
            connection.setConnectTimeout(3000);
            connection.connect();
            return true;
        } catch (Exception e) {
            // Handle your exceptions
            return false;
        }
    }

    public static String GetAppSetting(String SettingName) {
        String SettingValue = "";
        String sqlString = "select Setting_No,Setting_Name,Setting_Value from AppSetting where Setting_Name='" + SettingName + "'";
        Cursor cursor = DatabaseHelper.rawQuery(sqlString);
        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    SettingValue = cursor.getString(cursor.getColumnIndex("Setting_Value"));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return SettingValue;
    }

    public static String GetSystemSetting(String ColumnName) {
        String value = "";
        Cursor cursor = DatabaseHelper.rawQuery("select * from SystemSetting");
        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    if (cursor.getColumnIndex(ColumnName) > -1)
                        value = cursor.getString(cursor.getColumnIndex(ColumnName));

                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return value;
    }

    public static boolean UserRight(int userid, int groupid, int subgroupid) {
        boolean allow = false;
        Cursor cursor = DatabaseHelper.rawQuery("SELECT count(userid) allow FROM menu_user WHERE userid=" + userid + " AND groupid = " + groupid + " AND subgroupid = " + subgroupid);
        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    allow = cursor.getInt(cursor.getColumnIndex("allow")) > 0;

                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return allow;
    }

}
