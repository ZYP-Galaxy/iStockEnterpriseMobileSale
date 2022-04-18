package com.abbp.istockmobilesalenew;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.widget.CardView;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class categoryAdapter extends RecyclerView.Adapter<categoryAdapter.MyViewHolder> {
    Context context;
    ArrayList<category> data;
    RecyclerView rv;
    Button button;
    AlertDialog da;
    public static String categoryid = null;

    public categoryAdapter(Context context, ArrayList<category> data, RecyclerView rv, Button button, AlertDialog da) {
        this.context = context;
        this.data = data;
        this.rv = rv;
        this.button = button;
        this.da = da;
    }

    public categoryAdapter(Context context, ArrayList<category> data, RecyclerView rv) {
        this.context = context;
        this.data = data;
        this.rv = rv;

    }

    public categoryAdapter(Context context, ArrayList<category> filtercate, Button choosecategory, AlertDialog da) {
        this.context = context;
        this.data = filtercate;
        this.button = choosecategory;
        this.da = da;
    }

    @Override
    public categoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater lf = LayoutInflater.from(parent.getContext());
        View v = lf.inflate(R.layout.itembinding, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(categoryAdapter.MyViewHolder holder, int position) {

        holder.btn.setText(" " + data.get(position).getName());
        Context entryform = categoryAdapter.this.context;
        if (entryform.toString().contains("saleorder_entry")) {
            if (frmmain.withoutclass.equals("false")) {
                if (data.get(position).getName() == "Back" && position == 0) {
                    holder.btn.setBackgroundResource(R.drawable.categorygradiant);
//                        holder.btn.setVisibility(View.GONE);
                } else {
//                        holder.backbtn.setVisibility(View.GONE);
                    holder.btn.setBackgroundResource(R.drawable.btngradient);
                    rv = saleorder_entry.gridcodeview;
                /*if (frmmain.withoutclass.equals("false")){

                }*/
                }
            } else {
//                    holder.backbtn.setVisibility(View.GONE);
                holder.btn.setBackgroundResource(R.drawable.btngradient);
            }

              /*  holder.backbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(data.get(position).getName()=="Back")
                        {
                            if(frmmain.withoutclass.equals("false")){
                                rv=saleorder_entry.gridclassview;
                                sale_entry.usr_codes.clear();
                            }

                            saleorder_entry.imgFilterCode.setVisibility(View.GONE);
                            Cursor cursor = DatabaseHelper.DistinctCategorySelectQuery("Usr_Code",new String[]{"class","classname"},"classname");
                            if(saleorder_entry.class_items.size()>0) saleorder_entry.class_items.clear();
                            if(cursor!=null&&cursor.getCount()!=0)
                            {
                                if(cursor.moveToFirst())
                                {
                                    do {
                                        long classid = cursor.getLong(cursor.getColumnIndex("class"));
                                        String name = cursor.getString(cursor.getColumnIndex("classname"));
                                        saleorder_entry.class_items.add(new class_item(classid,name));
                                    }while (cursor.moveToNext());

                                }

                            }
                            cursor.close();
                            classAdapter ad=new classAdapter(context,saleorder_entry.class_items,rv);
                            rv.setAdapter(ad);
                            LinearLayoutManager classlinear = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
                            rv.setLayoutManager(classlinear);

                        }
                    }
                });*/

            holder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (data.get(position).getName() == "Back") {
                        if (frmmain.withoutclass.equals("false")) {
                            rv = saleorder_entry.gridclassview;
                            saleorder_entry.usr_codes.clear();
                        }

                        saleorder_entry.imgFilterCode.setVisibility(View.GONE);
                        Cursor cursor = DatabaseHelper.DistinctCategorySelectQuery("Usr_Code", new String[]{"class", "classname"}, "classname");
                        if (saleorder_entry.class_items.size() > 0)
                            saleorder_entry.class_items.clear();
                        if (cursor != null && cursor.getCount() != 0) {
                            if (cursor.moveToFirst()) {
                                do {
                                    long classid = cursor.getLong(cursor.getColumnIndex("class"));
                                    String name = cursor.getString(cursor.getColumnIndex("classname"));
                                    saleorder_entry.class_items.add(new class_item(classid, name));
                                } while (cursor.moveToNext());

                            }

                        }
                        cursor.close();
                        classAdapter ad = new classAdapter(context, saleorder_entry.class_items, rv);
                        rv.setAdapter(ad);
                        LinearLayoutManager classlinear = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                        rv.setLayoutManager(classlinear);

                    } else {
//                            Toast.makeText(sale_entry)
//                            holder.btn.setBackgroundResource(R.drawable.categoryclick);

/*
                        if (frmmain.use_pic==1){

                            sale_entry.imgFilterCode.setVisibility(View.GONE);
                            sale_entry.fitercode="Description";
                            Cursor cursor=DatabaseHelper.rawQuery("select uc.usr_code,description,path from Usr_Code uc join usr_code_img uic on uic.usr_code=uc.usr_code where uc.category='"+String.valueOf(data.get(position).getCategory())+"'");
                            if(sale_entry.usr_codes.size()>0) sale_entry.usr_codes.clear();
                            if(cursor!=null&&cursor.getCount()!=0)
                            {
                                if(cursor.moveToFirst())
                                {
                                    do {
                                        String usr_code=cursor.getString(cursor.getColumnIndex("usr_code"));
                                        String description=cursor.getString(cursor.getColumnIndex("description"));
                                        String path=cursor.getString(cursor.getColumnIndex("path"));
                                        sale_entry.usr_codes.add(new usr_code(usr_code,description,path));
                                    }while (cursor.moveToNext());

                                }

                            }
                            cursor.close();
                            usrcodeAdapter ad=new usrcodeAdapter(context,sale_entry.usr_codes,rv,data);
                            rv.setAdapter(ad);
                            GridLayoutManager gridLayoutManager = new GridLayoutManager(context.getApplicationContext(),3);
                            rv.setLayoutManager(gridLayoutManager);


                        }else {*/
                        saleorder_entry.imgFilterCode.setVisibility(View.GONE);
                        saleorder_entry.fitercode = "Description";
                        Cursor cursor = DatabaseHelper.rawQuery("select distinct usr_code,description,isinactive from Usr_Code where isdeleted=0 and category=" + data.get(position).getCategory() + " order by code,usr_code");
                        if (saleorder_entry.usr_codes.size() > 0) saleorder_entry.usr_codes.clear();
                        if (frmmain.withoutclass.equals("true")) {
                            saleorder_entry.usr_codes.add(new usr_code("Back", "Back"));
                        }
                        if (cursor != null && cursor.getCount() != 0) {
                            if (cursor.moveToFirst()) {
                                do {
                                    String usr_code = cursor.getString(cursor.getColumnIndex("usr_code"));
                                    String description = cursor.getString(cursor.getColumnIndex("description"));
                                    int isinactive = cursor.getInt(cursor.getColumnIndex("isinactive"));
                                    if (isinactive == 0) {
                                        saleorder_entry.usr_codes.add(new usr_code(usr_code, description));
                                    }


                                } while (cursor.moveToNext());

                            }

                        }
                        cursor.close();
                        usrcodeAdapter ad = new usrcodeAdapter(context, saleorder_entry.usr_codes, rv, data);
                        rv.setAdapter(ad);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(context.getApplicationContext(), 4);
                        rv.setLayoutManager(gridLayoutManager);
                        // }
                    }


                }
            });

        } //end saleorder
        else if (entryform.toString().contains("frmstockstatus")) {
            holder.btn.setWidth(10);
            holder.btn.setHeight(10);
            holder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    button.setText(/*data.get(position).getCategory()+":"+*/data.get(position).getName());
                    categoryid = String.valueOf(data.get(position).getCategory());
                    classAdapter.classid = null;
                    da.dismiss();
                }
            });

        } else {
            if (frmmain.withoutclass.equals("false")) {
                if (data.get(position).getName() == "Back" && position == 0) {
//                        holder.btn.setVisibility(View.GONE);
                    holder.btn.setBackgroundResource(R.drawable.categorygradiant);
                } else {
//                        holder.backbtn.setVisibility(View.GONE);
                    holder.btn.setBackgroundResource(R.drawable.btngradient);
                    rv = sale_entry.gridcodeview;
                /*if (frmmain.withoutclass.equals("false")){

                }*/
                }
            } else {
//                    holder.backbtn.setVisibility(View.GONE);
                holder.btn.setBackgroundResource(R.drawable.btngradient);
            }

               /* holder.backbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(data.get(position).getName()=="Back")
                        {
                            if(frmmain.withoutclass.equals("false")){
                                rv=sale_entry.gridclassview;
                                sale_entry.usr_codes.clear();
                            }

                            sale_entry.imgFilterCode.setVisibility(View.GONE);
                            Cursor cursor = DatabaseHelper.DistinctCategorySelectQuery("Usr_Code",new String[]{"class","classname"},"classname");
                            if(sale_entry.class_items.size()>0) sale_entry.class_items.clear();
                            if(cursor!=null&&cursor.getCount()!=0)
                            {
                                if(cursor.moveToFirst())
                                {
                                    do {
                                        long classid = cursor.getLong(cursor.getColumnIndex("class"));
                                        String name = cursor.getString(cursor.getColumnIndex("classname"));
                                        sale_entry.class_items.add(new class_item(classid,name));
                                    }while (cursor.moveToNext());

                                }

                            }
                            cursor.close();
                            classAdapter ad=new classAdapter(context,sale_entry.class_items,rv);
                            rv.setAdapter(ad);
                            LinearLayoutManager classlinear = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
                            rv.setLayoutManager(classlinear);

                        }
                    }
                });*/
            holder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (data.get(position).getName() == "Back") {
                        if (frmmain.withoutclass.equals("false")) {
                            rv = sale_entry.gridclassview;
                            sale_entry.usr_codes.clear();
                        }

                        sale_entry.imgFilterCode.setVisibility(View.GONE);
                        Cursor cursor = DatabaseHelper.DistinctCategorySelectQuery("Usr_Code", new String[]{"class", "classname"}, "classname");
                        if (sale_entry.class_items.size() > 0) sale_entry.class_items.clear();
                        if (cursor != null && cursor.getCount() != 0) {
                            if (cursor.moveToFirst()) {
                                do {
                                    long classid = cursor.getLong(cursor.getColumnIndex("class"));
                                    String name = cursor.getString(cursor.getColumnIndex("classname"));
                                    sale_entry.class_items.add(new class_item(classid, name));
                                } while (cursor.moveToNext());

                            }

                        }
                        cursor.close();
                        classAdapter ad = new classAdapter(context, sale_entry.class_items, rv);
                        rv.setAdapter(ad);
                        LinearLayoutManager classlinear = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                        rv.setLayoutManager(classlinear);

                    } else {

/*
                        if (frmmain.use_pic==1){

                            sale_entry.imgFilterCode.setVisibility(View.GONE);
                            sale_entry.fitercode="Description";
                            Cursor cursor=DatabaseHelper.rawQuery("select uc.usr_code,description,path from Usr_Code uc join usr_code_img uic on uic.usr_code=uc.usr_code where uc.category='"+String.valueOf(data.get(position).getCategory())+"'");
                            if(sale_entry.usr_codes.size()>0) sale_entry.usr_codes.clear();
                            if(cursor!=null&&cursor.getCount()!=0)
                            {
                                if(cursor.moveToFirst())
                                {
                                    do {
                                        String usr_code=cursor.getString(cursor.getColumnIndex("usr_code"));
                                        String description=cursor.getString(cursor.getColumnIndex("description"));
                                        String path=cursor.getString(cursor.getColumnIndex("path"));
                                        sale_entry.usr_codes.add(new usr_code(usr_code,description,path));
                                    }while (cursor.moveToNext());

                                }

                            }
                            cursor.close();
                            usrcodeAdapter ad=new usrcodeAdapter(context,sale_entry.usr_codes,rv,data);
                            rv.setAdapter(ad);
                            GridLayoutManager gridLayoutManager = new GridLayoutManager(context.getApplicationContext(),3);
                            rv.setLayoutManager(gridLayoutManager);


                        }else {*/
                        sale_entry.imgFilterCode.setVisibility(View.GONE);
                        sale_entry.fitercode = "Description";
                        Cursor cursor = DatabaseHelper.rawQuery("select distinct usr_code,description,isinactive from Usr_Code where isdeleted=0 and category=" + data.get(position).getCategory() + " order by code,usr_code");
                        if (sale_entry.usr_codes.size() > 0) sale_entry.usr_codes.clear();
                        if (frmmain.withoutclass.equals("true")) {
                            sale_entry.usr_codes.add(new usr_code("Back", "Back"));
                        }
                        if (cursor != null && cursor.getCount() != 0) {
                            if (cursor.moveToFirst()) {
                                do {
                                    String usr_code = cursor.getString(cursor.getColumnIndex("usr_code"));
                                    String description = cursor.getString(cursor.getColumnIndex("description"));
                                    int isinactive = cursor.getInt(cursor.getColumnIndex("isinactive"));
                                    if (isinactive == 0)
                                        sale_entry.usr_codes.add(new usr_code(usr_code, description));
                                } while (cursor.moveToNext());

                            }

                        }
                        cursor.close();
                        usrcodeAdapter ad = new usrcodeAdapter(context, sale_entry.usr_codes, rv, data);
                        rv.setAdapter(ad);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(context.getApplicationContext(), 4);
                        rv.setLayoutManager(gridLayoutManager);
                        // }
                    }


                }
            });
        }


    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        Button btn;

        //       ImageButton backbtn;
        public MyViewHolder(View itemView) {
            super(itemView);
            btn = itemView.findViewById(R.id.info_text);
//                backbtn=itemView.findViewById(R.id.backbtn);
        }
    }
}


//<?xml version="1.0" encoding="utf-8"?>
//<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
//    xmlns:app="http://schemas.android.com/apk/res-auto"
//    xmlns:tools="http://schemas.android.com/tools"
//    android:layout_width="match_parent"
//    android:layout_height="match_parent"
//    android:background="@drawable/bgcolor"
//    tools:context=".frmstockstatus">
//
//    <RelativeLayout
//        android:layout_width="match_parent"
//        android:layout_height="match_parent"
//        >
//
//        <android.support.v7.widget.CardView
//            android:layout_width="wrap_content"
//            android:layout_height="30dp"
//            app:cardBackgroundColor="@color/barcolor"
//            android:elevation="20dp"
//            android:padding="10dp"
//            android:id="@+id/head"
//            >
//
//            <RelativeLayout
//                android:layout_width="match_parent"
//                android:layout_height="50dp"
//                android:layout_gravity="center_vertical"
//                >
//                <TextView
//                    android:layout_width="match_parent"
//                    android:layout_height="wrap_content"
//                    android:text="Stock Status"
//                    android:id="@+id/txtStockStatus"
//                    android:layout_centerVertical="true"
//                    android:layout_marginLeft="20dp"
//                    android:textStyle="bold"
//                    android:textColor="@color/white"
//                    android:textSize="15dp"/>
//
//            </RelativeLayout>
//        </android.support.v7.widget.CardView>
//
//        <RelativeLayout
//            android:layout_width="match_parent"
//            android:id="@+id/header"
//            android:layout_marginTop="1dp"
//            android:layout_marginLeft="10dp"
//            android:layout_below="@+id/head"
//            android:layout_height="40dp">
//            <Button
//                android:layout_width="200dp"
//                android:text="Today"
//                android:layout_centerVertical="true"
//                android:textStyle="bold"
//                android:id="@+id/txtdate"
//                android:textColor="@color/btntxtcolor"
//                android:textAllCaps="false"
//                android:background="@drawable/btnround3"
//                android:layout_height="30dp" />
//
//            <Button
//                android:layout_marginLeft="20dp"
//                android:layout_width="160dp"
//                android:layout_height="30dp"
//                android:text="Choose Location"
//                android:layout_toRightOf="@+id/txtdate"
//                android:background="@drawable/btnround3"
//                android:textColor="@color/btntxtcolor"
//                android:textStyle="bold"
//                android:layout_centerVertical="true"
//                android:layout_gravity="center_vertical"
//                android:id="@+id/chooselocation"
//                android:textAllCaps="false"/>
//
//            <Button
//                android:layout_marginLeft="20dp"
//                android:layout_width="160dp"
//                android:layout_height="30dp"
//                android:text="Choose Category"
//                android:layout_toRightOf="@+id/chooselocation"
//                android:background="@drawable/btnround3"
//                android:textColor="@color/btntxtcolor"
//                android:textStyle="bold"
//                android:layout_centerVertical="true"
//                android:layout_gravity="center_vertical"
//                android:id="@+id/choosecategory"
//                android:textAllCaps="false"/>
//
//            <EditText
//                android:layout_width="60dp"
//                android:layout_height="40dp"
//                android:hint="Find code"
//                android:layout_toRightOf="@+id/choosecategory"
//                android:textSize="12dp"
//                />
//
//            <ImageButton
//                android:layout_width="40dp"
//                android:layout_height="40dp"
//                android:id="@+id/btnrefresh"
//                android:layout_centerVertical="true"
//                android:layout_toRightOf="@+id/choosecategory"
//                android:src="@drawable/refresh"
//                android:background="@drawable/cyclebutton2"
//                android:layout_marginLeft="70dp"
//                />
//            <ImageButton
//                android:layout_width="40dp"
//                android:layout_height="40dp"
//                android:id="@+id/btnclear"
//                android:layout_centerVertical="true"
//                android:layout_toRightOf="@+id/btnrefresh"
//                android:src="@drawable/clearsalelist"
//                android:background="@drawable/cyclebutton2"
//                android:layout_marginLeft="10dp"
//                />
//
//            <ImageButton
//                android:layout_width="40dp"
//                android:layout_height="40dp"
//                android:id="@+id/btnmenu"
//                android:layout_centerVertical="true"
//                android:layout_alignParentRight="true"
//                android:src="@drawable/stockmenu"
//                android:background="@drawable/cyclebutton2"
//                android:layout_marginLeft="20dp"
//                android:visibility="gone"
//                />
//
//
//            <ImageButton
//                android:layout_width="40dp"
//                android:layout_height="40dp"
//                android:id="@+id/filtermenu"
//                android:layout_centerVertical="true"
//                android:layout_toRightOf="@+id/selectfilter"
//                android:layout_gravity="center_vertical"
//                android:src="@drawable/filtericon"
//                android:background="@drawable/cyclebutton2"
//                android:layout_marginLeft="20dp"
//                android:visibility="gone"
//                />
//            <ImageButton
//                android:layout_width="40dp"
//                android:layout_height="40dp"
//                android:layout_marginLeft="20dp"
//                android:id="@+id/imgFilterClear"
//                android:layout_centerVertical="true"
//                android:layout_toRightOf="@+id/filtermenu"
//                android:layout_gravity="center_vertical"
//                android:src="@drawable/cuticon"
//                android:background="@drawable/cyclebutton2"
//                android:visibility="gone"
//                />
//            <ImageButton
//                android:id="@+id/imgAdd"
//                android:layout_width="40dp"
//                android:layout_height="40dp"
//                android:layout_marginRight="20dp"
//                android:layout_centerVertical="true"
//                android:layout_alignParentRight="true"
//                android:background="@drawable/cyclebutton2"
//                android:src="@drawable/add"
//                android:visibility="gone"/>
//
//        </RelativeLayout>
//
//        <android.support.v7.widget.CardView
//            android:layout_width="match_parent"
//            android:layout_above="@+id/footer"
//            android:layout_below="@+id/header"
//            android:layout_marginLeft="0dp"
//            android:layout_marginRight="0dp"
//            android:id="@+id/middle"
//            android:elevation="10dp"
//            android:layout_marginTop="0dp"
//            android:layout_height="wrap_content">
//
//            <RelativeLayout
//                android:layout_width="match_parent"
//                android:layout_height="wrap_content">
//
//                <LinearLayout
//                    android:id="@+id/column"
//                    android:layout_width="match_parent"
//                    android:layout_height="40dp"
//                    android:background="@drawable/columnheader">
//
//                    <TextView
//                        android:layout_width="0dp"
//                        android:layout_height="match_parent"
//                        android:layout_weight="1.5"
//                        android:gravity="center_vertical"
//                        android:text="Code"
//                        android:textColor="@color/white"
//                        android:textSize="15dp"
//                        android:textStyle="bold"
//                        android:layout_marginLeft="10dp"/>
//
//                    <TextView
//                        android:layout_width="0dp"
//                        android:layout_height="match_parent"
//                        android:layout_weight="1.5"
//                        android:gravity="center_vertical"
//                        android:text="Description"
//                        android:textColor="@color/white"
//                        android:textSize="15dp"
//                        android:textStyle="bold"
//                        android:layout_marginLeft="10dp"/>
//                    <TextView
//                        android:layout_width="0dp"
//                        android:layout_height="match_parent"
//                        android:layout_weight="1.5"
//                        android:gravity="center_vertical"
//                        android:text="Sale Amount"
//                        android:textColor="@color/white"
//                        android:textSize="15dp"
//                        android:textStyle="bold" />
//
//
//                    <TextView
//                        android:layout_width="0dp"
//                        android:layout_height="match_parent"
//                        android:layout_weight="1.5"
//                        android:gravity="center_vertical"
//                        android:text="Balance"
//                        android:textColor="@color/white"
//                        android:textSize="15dp"
//                        android:textStyle="bold" />
//
//
//                </LinearLayout>
//
//                <android.support.v7.widget.RecyclerView
//                    android:id="@+id/rcvsaleList"
//                    android:layout_width="match_parent"
//                    android:layout_height="match_parent"
//                    android:layout_below="@+id/column"
//                    android:background="@drawable/rcvback">
//
//                </android.support.v7.widget.RecyclerView>
//
//            </RelativeLayout>
//        </android.support.v7.widget.CardView>
//
//
//
//    </RelativeLayout>
//
//</android.support.constraint.ConstraintLayout>