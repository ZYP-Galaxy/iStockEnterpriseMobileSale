package com.abbp.istockmobilesalenew;

import android.content.Context;
import android.database.Cursor;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class classAdapter extends RecyclerView.Adapter<classAdapter.MyViewHolder> {
    Context context;
    ArrayList<class_item> data;
    RecyclerView rv;
    Button button;
    AlertDialog da;
    public static String classid = null;

    public classAdapter(Context context, ArrayList<class_item> data, RecyclerView rv, Button button, AlertDialog da) {
        this.context = context;
        this.data = data;
        this.rv = rv;
        this.button = button;
        this.da = da;
    }

    public classAdapter(Context context, ArrayList<class_item> data, RecyclerView rv) {
        this.context = context;
        this.data = data;
        this.rv = rv;

    }

    public classAdapter(Context context, ArrayList<class_item> data, Button button, AlertDialog da) {
        this.context = context;
        this.data = data;
        this.button = button;
        this.da = da;
    }

    @Override
    public classAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater lf = LayoutInflater.from(parent.getContext());
        View v = lf.inflate(R.layout.classitem, parent, false);
        return new classAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(classAdapter.MyViewHolder holder, int position) {

        holder.btn.setText(" " + data.get(position).getName());
        if (classAdapter.this.context.toString().contains("frmstockstatus")) {
            holder.btn.setBackgroundResource(R.drawable.btngradient);
            holder.btn.setWidth(10);
            holder.btn.setHeight(10);
            // Toast.makeText(classAdapter.this.context," TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT",Toast.LENGTH_LONG).show();
        }
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context entryform = classAdapter.this.context;
                if (entryform.toString().contains("saleorder_entry")) {
                    saleorder_entry.imgFilterCode.setVisibility(View.GONE);
                    saleorder_entry.fitercode = "Description";
                    Cursor cursor = DatabaseHelper.DistinctSelectQuerySelectionWithCondition("Usr_Code", new String[]{"category", "categoryname", "class"}, "class=?", new String[]{String.valueOf(data.get(position).getClassid())}, "sortcode,categoryname");
                    if (saleorder_entry.categories.size() > 0) saleorder_entry.categories.clear();
                    if (frmmain.withoutclass.equals("false")) {
                        saleorder_entry.categories.add(new category("Back"));
                    }

                    if (cursor != null && cursor.getCount() != 0) {
                        if (cursor.moveToFirst()) {
                            do {
                                long category = cursor.getLong(cursor.getColumnIndex("category"));
                                String name = cursor.getString(cursor.getColumnIndex("categoryname"));
                                long classid = cursor.getLong(cursor.getColumnIndex("class"));
                                saleorder_entry.categories.add(new category(category, classid, name));
                            } while (cursor.moveToNext());

                        }

                    }
                    cursor.close();
                    categoryAdapter ad = new categoryAdapter(context, saleorder_entry.categories, rv);
                    rv.setAdapter(ad);
                    LinearLayoutManager classlinear = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                    rv.setLayoutManager(classlinear);
                } else if (entryform.toString().contains("frmstockstatus")) {
//                    holder.btn.setBackgroundResource(R.drawable.btngradient);
                    button.setText(/*data.get(position).getClassid()+":"+*/data.get(position).getName());
                    classid = String.valueOf(data.get(position).getClassid());
                    categoryAdapter.categoryid = null;
                    da.dismiss();
//                    locid= (int) data.get(position).getClassid();
                } else {
                    sale_entry.imgFilterCode.setVisibility(View.GONE);
                    sale_entry.fitercode = "Description";
                    Cursor cursor = DatabaseHelper.DistinctSelectQuerySelectionWithCondition("Usr_Code", new String[]{"category", "categoryname", "class"}, "class=?", new String[]{String.valueOf(data.get(position).getClassid())}, "sortcode,categoryname");
                    if (sale_entry.categories.size() > 0) sale_entry.categories.clear();
                    if (frmmain.withoutclass.equals("false")) {
                        sale_entry.categories.add(new category("Back"));
                    }

                    if (cursor != null && cursor.getCount() != 0) {
                        if (cursor.moveToFirst()) {
                            do {
                                long category = cursor.getLong(cursor.getColumnIndex("category"));
                                String name = cursor.getString(cursor.getColumnIndex("categoryname"));
                                long classid = cursor.getLong(cursor.getColumnIndex("class"));
                                sale_entry.categories.add(new category(category, classid, name));
                            } while (cursor.moveToNext());

                        }

                    }
                    cursor.close();
                    categoryAdapter ad = new categoryAdapter(context, sale_entry.categories, rv);
                    rv.setAdapter(ad);
                    LinearLayoutManager classlinear = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                    rv.setLayoutManager(classlinear);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        Button btn;

        public MyViewHolder(View itemView) {
            super(itemView);
            btn = itemView.findViewById(R.id.info_text);

        }
    }
}
