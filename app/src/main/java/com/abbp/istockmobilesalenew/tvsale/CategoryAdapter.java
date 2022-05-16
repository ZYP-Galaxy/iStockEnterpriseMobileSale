package com.abbp.istockmobilesalenew.tvsale;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abbp.istockmobilesalenew.DatabaseHelper;
import com.abbp.istockmobilesalenew.R;
import com.abbp.istockmobilesalenew.category;
import com.abbp.istockmobilesalenew.classAdapter;
import com.abbp.istockmobilesalenew.class_item;
import com.abbp.istockmobilesalenew.frmmain;
import com.abbp.istockmobilesalenew.saleorder_entry;
import com.abbp.istockmobilesalenew.usr_code;
import com.abbp.istockmobilesalenew.usrcodeAdapter;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    Context context;
    ArrayList<category> data;
    RecyclerView rv;
    Button button;
    AlertDialog da;
    public static String categoryid = null;

    public static String itemposition = "1";

    public CategoryAdapter(Context context, ArrayList<category> data, RecyclerView rv, Button button, AlertDialog da) {
        this.context = context;
        this.data = data;
        this.rv = rv;
        this.button = button;
        this.da = da;
    }

    public CategoryAdapter(Context context, ArrayList<category> data, RecyclerView rv) {
        this.context = context;
        this.data = data;
        this.rv = rv;

    }

    public CategoryAdapter(Context context, ArrayList<category> filtercate, Button choosecategory, AlertDialog da) {
        this.context = context;
        this.data = filtercate;
        this.button = choosecategory;
        this.da = da;
    }

    @Override
    public CategoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater lf = LayoutInflater.from(parent.getContext());
        View v = lf.inflate(R.layout.itembinding, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CategoryAdapter.MyViewHolder holder, int position) {

        holder.btn.setText(String.format(" %s", data.get(position).getName()));
        Context entryform = CategoryAdapter.this.context;
        if (entryform.toString().contains("saleorder_entry")) {
            if (frmmain.withoutclass.equals("false")) {
                if (data.get(position).getName() == "Back" && position == 0) {
                    holder.btn.setBackgroundResource(R.drawable.categorygradiant);
                } else {
                    holder.btn.setBackgroundResource(R.drawable.btngradient);
                    rv = saleorder_entry.gridcodeview;
                }
            } else {
                holder.btn.setBackgroundResource(R.drawable.btngradient);
            }

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

                        saleorder_entry.imgFilterCode.setVisibility(View.GONE);
                        saleorder_entry.fitercode = "Description";
                        Cursor cursor = DatabaseHelper.rawQuery("select distinct usr_code,description,isinactive from Usr_Code where unit_type=1 and isdeleted=0  and category=" + data.get(position).getCategory() + " order by code,usr_code");
                        if (saleorder_entry.usr_codes.size() > 0) saleorder_entry.usr_codes.clear();
                        if (frmmain.withoutclass.equals("true")) {
                            //saleorder_entry.usr_codes.add(new usr_code("Back", "Back", saleprice));
                        }
                        if (cursor != null && cursor.getCount() != 0) {
                            if (cursor.moveToFirst()) {
                                do {
                                    String usr_code = cursor.getString(cursor.getColumnIndex("usr_code"));
                                    String description = cursor.getString(cursor.getColumnIndex("description"));
                                    int isinactive = cursor.getInt(cursor.getColumnIndex("isinactive"));
                                    if (isinactive == 0) {
                                        //saleorder_entry.usr_codes.add(new usr_code(usr_code, description, saleprice));
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

        } //end stockstatus
        else {
            if (frmmain.withoutclass.equals("false")) {
                if (data.get(position).getName().equals("Back") && position == 0) {
                    holder.btn.setBackgroundResource(R.drawable.categorygradiant);
                } else {
                    holder.btn.setBackgroundResource(R.drawable.btngradient);
                    rv = sale_entry_tv.gridcodeview;
                }
            } else {
                holder.btn.setBackgroundResource(R.drawable.btngradient);
            }

            holder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (data.get(position).getName().equals("Back")) {
                        if (frmmain.withoutclass.equals("false")) {
                            rv = sale_entry_tv.gridclassview;
                            sale_entry_tv.usr_codes.clear();
                        }

                        //sale_entry_tv.imgFilterCode.setVisibility(View.GONE);
                        Cursor cursor = DatabaseHelper.DistinctCategorySelectQuery("Usr_Code", new String[]{"class", "classname"}, "classname");
                        if (sale_entry_tv.class_items.size() > 0) sale_entry_tv.class_items.clear();
                        if (cursor != null && cursor.getCount() != 0) {
                            if (cursor.moveToFirst()) {
                                do {
                                    long classid = cursor.getLong(cursor.getColumnIndex("class"));
                                    String name = cursor.getString(cursor.getColumnIndex("classname"));
                                    sale_entry_tv.class_items.add(new class_item(classid, name));
                                } while (cursor.moveToNext());

                            }

                            cursor.close();
                        }
                        classAdapter ad = new classAdapter(context, sale_entry_tv.class_items, rv);
                        rv.setAdapter(ad);
                        LinearLayoutManager classlinear = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                        rv.setLayoutManager(classlinear);

                    } //Back
                    else {

                        //sale_entry_tv.fitercode = "Description";
                        Cursor cursor = DatabaseHelper.rawQuery("select distinct usr_code,description,sale_price from Usr_Code where unit_type=1 AND isdeleted=0 and isinactive=0 and category=" + data.get(position).getCategory() + " order by " + sale_entry_tv.sortcode);
                        if (sale_entry_tv.usr_codes.size() > 0) sale_entry_tv.usr_codes.clear();

                        if (cursor != null && cursor.getCount() != 0) {
                            if (cursor.moveToFirst()) {
                                do {
                                    String usr_code = cursor.getString(cursor.getColumnIndex("usr_code"));
                                    String description = cursor.getString(cursor.getColumnIndex("description"));
                                    double saleprice = cursor.getDouble(cursor.getColumnIndex("sale_price"));
                                    sale_entry_tv.usr_codes.add(new usr_code(usr_code, description));
                                } while (cursor.moveToNext());

                            }
                            cursor.close();
                        }

                        UsrcodeAdapter adapter = new UsrcodeAdapter(context, sale_entry_tv.usr_codes, rv, data);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(context.getApplicationContext(), 4);
                        rv.setLayoutManager(gridLayoutManager);
                        rv.setAdapter(adapter);

                        //change category name when click
                        sale_entry_tv.txtItemOf.setText(data.get(position).getName());
                        itemposition = String.valueOf(data.get(position).getCategory());
                    }

                }
            });
        }

        if (position == 0) {
            holder.btn.performClick();
        }


    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        Button btn;

        public MyViewHolder(View itemView) {
            super(itemView);
            btn = itemView.findViewById(R.id.info_text);
        }
    }
}