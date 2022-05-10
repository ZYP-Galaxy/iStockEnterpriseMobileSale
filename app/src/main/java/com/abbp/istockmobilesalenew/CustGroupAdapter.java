package com.abbp.istockmobilesalenew;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.abbp.istockmobilesalenew.tvsale.sale_entry_tv;

import java.util.ArrayList;

public class CustGroupAdapter extends RecyclerView.Adapter<CustGroupAdapter.MyViewHolder> {

    Context context;
    ArrayList<customergroup> data = new ArrayList<>();
    Button btn;
    AlertDialog da;

    public CustGroupAdapter(Context context, ArrayList<customergroup> data, Button btn, AlertDialog da) {
        this.data = data;
        this.context = context;
        this.btn = btn;
        this.da = da;
    }

    @Override
    public CustGroupAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater lf = LayoutInflater.from(parent.getContext());
        View v = lf.inflate(R.layout.headerbinding, parent, false);
        return new CustGroupAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CustGroupAdapter.MyViewHolder holder, int position) {
        holder.btn.setText(" " + data.get(position).getName());
        holder.btn.setBackgroundResource(R.drawable.usercodegradiant);
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CustGroupAdapter.this.context.toString().contains("saleorder_entry")) {
                    btn.setText(/*data.get(position).getCustgroupid()+":"+*/data.get(position).getName());
                    saleorder_entry.selected_custgroupid = data.get(position).getCustgroupid();
                    BindMaxCustTownship(CustGroupAdapter.this.context);
                    da.dismiss();

                }
                else if(CustGroupAdapter.this.context.toString().contains("sale_entry_tv")){
                    btn.setText(/*data.get(position).getCustgroupid()+":"+*/data.get(position).getName());
                    sale_entry_tv.selected_custgroupid = data.get(position).getCustgroupid();
                    BindMaxCustTownship(CustGroupAdapter.this.context);
                    da.dismiss();
                }
                else {
                    btn.setText(/*data.get(position).getCustgroupid()+":"+*/data.get(position).getName());
                    sale_entry.selected_custgroupid = data.get(position).getCustgroupid();
                    BindMaxCustTownship(CustGroupAdapter.this.context);
                    da.dismiss();
                }

            }
        });
    }

    private void BindMaxCustTownship(Context context) {


        if (context.toString().contains("saleorder_entry")) {
            String sqlString = "select customerid,name,townshipid,townshipname,credit from customer where customerid=(select min(customerid)customerid " +
                    " from customer" +
                    " where CustGroupID=" + saleorder_entry.selected_custgroupid +
                    " group by CustGroupID)";
            Cursor cursor = DatabaseHelper.rawQuery(sqlString);
            if (cursor != null && cursor.getCount() != 0) {
                if (cursor.moveToFirst()) {
                    do {
                        long customerid = cursor.getLong(cursor.getColumnIndex("customerid"));
                        String customername = cursor.getString(cursor.getColumnIndex("name"));
                        long townshipid = cursor.getLong(cursor.getColumnIndex("townshipid"));
                        String townshipname = cursor.getString(cursor.getColumnIndex("townshipname"));
                        boolean credit = cursor.getInt(cursor.getColumnIndex("credit")) == 1 ? true : false;
                        saleorder_entry.btntownship.setText(/*townshipid+":"+*/townshipname);
                        saleorder_entry.btncustomer.setText(/*customerid+":"+*/customername);
                        saleorder_entry.selected_townshipid = townshipid;
                        saleorder_entry.creditcustomer = credit;
                        String pay_type = credit == false ? "Cash Down" : "Credit";
                        saleorder_entry.btnpaytype.setText(pay_type);
                        saleorder_entry.sh.get(0).setTownshipid(townshipid);
                        saleorder_entry.sh.get(0).setCustomerid(customerid);
                        saleorder_entry.sh.get(0).setPay_type(credit == false ? 1 : 2);
                    } while (cursor.moveToNext());

                }

            }
            cursor = null;

        } else {
            String sqlString = "select customerid,name,townshipid,townshipname,iscredit from customer where customerid=(select min(customerid)customerid " +
                    " from customer" +
                    " where CustGroupID=" + sale_entry.selected_custgroupid +
                    " group by CustGroupID)";
            Cursor cursor = DatabaseHelper.rawQuery(sqlString);
            if (cursor != null && cursor.getCount() != 0) {
                if (cursor.moveToFirst()) {
                    do {
                        long customerid = cursor.getLong(cursor.getColumnIndex("customerid"));
                        String customername = cursor.getString(cursor.getColumnIndex("name"));
                        int townshipid = cursor.getInt(cursor.getColumnIndex("townshipid"));
                        String townshipname = cursor.getString(cursor.getColumnIndex("townshipname"));
                        boolean credit = cursor.getInt(cursor.getColumnIndex("iscredit")) == 1;
                        sale_entry.btntownship.setText(/*townshipid+":"+*/townshipname);
                        sale_entry.btncustomer.setText(/*customerid+":"+*/customername);
                        sale_entry.selected_townshipid = townshipid;
                        sale_entry.creditcustomer = credit;
                        String pay_type = !credit ? "Cash Down" : "Credit";
                        sale_entry.btnpaytype.setText(pay_type);
                        sale_entry.sh.get(0).setTownshipid(townshipid);
                        sale_entry.sh.get(0).setCustomerid(customerid);
                        sale_entry.sh.get(0).setPay_type(!credit ? 1 : 2);
                    } while (cursor.moveToNext());

                }

            }
            cursor = null;
        }

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
