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

public class TownshipAdapter extends RecyclerView.Adapter<TownshipAdapter.MyViewHolder> {

    Context context;
    ArrayList<Township> data = new ArrayList<>();
    Button btn;
    AlertDialog da;
    SelectInsertLibrary selectInsertLibrary = new SelectInsertLibrary();


    public TownshipAdapter(Context context, ArrayList<Township> data, Button btn, AlertDialog da) {
        this.data = data;
        this.context = context;
        this.btn = btn;
        this.da = da;
    }

    @Override
    public TownshipAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater lf = LayoutInflater.from(parent.getContext());
        View v = lf.inflate(R.layout.headerbinding, parent, false);
        return new TownshipAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TownshipAdapter.MyViewHolder holder, int position) {
        holder.btn.setText(" " + data.get(position).getName());
        holder.btn.setBackgroundResource(R.drawable.usercodegradiant);
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TownshipAdapter.this.context.toString().contains("saleorder_entry")) {
                    btn.setText(/*data.get(position).getTownshipid()+":"+*/data.get(position).getName());
                    saleorder_entry.selected_townshipid = data.get(position).getTownshipid();
                    saleorder_entry.sh.get(0).setTownshipid(data.get(position).getTownshipid());

                    BindMaxCustomer(TownshipAdapter.this.context);
                    da.dismiss();

                }
                else if(TownshipAdapter.this.context.toString().contains("sale_entry_tv")) {
                    btn.setText(/*data.get(position).getTownshipid()+":"+*/data.get(position).getName());
                    sale_entry_tv.selected_townshipid = data.get(position).getTownshipid();
                    sale_entry_tv.sh.get(0).setTownshipid(data.get(position).getTownshipid());
                    BindMaxCustomer(TownshipAdapter.this.context);
                    da.dismiss();
                }
                else {
                    btn.setText(/*data.get(position).getTownshipid()+":"+*/data.get(position).getName());
                    sale_entry.selected_townshipid = data.get(position).getTownshipid();
                    sale_entry.sh.get(0).setTownshipid(data.get(position).getTownshipid());
                    BindMaxCustomer(TownshipAdapter.this.context);
                    da.dismiss();
                }


            }
        });
    }

    private void BindMaxCustomer(Context context) {
        if (context.toString().contains("saleorder_entry")) {
            String sqlString = "select customerid,name,iscredit,CustGroupID,CustGroupname from customer where customerid=(select min(customerid)customerid " +
                    " from customer" +
                    " where Townshipid=" + saleorder_entry.selected_townshipid +
                    " group by Townshipid)";
            Cursor cursor = DatabaseHelper.rawQuery(sqlString);
            if (cursor != null && cursor.getCount() != 0) {
                if (cursor.moveToFirst()) {
                    do {
                        long customerid = cursor.getLong(cursor.getColumnIndex("customerid"));
                        String customername = cursor.getString(cursor.getColumnIndex("name"));
                        long custgroupid = cursor.getLong(cursor.getColumnIndex("custgroupid"));
                        String custgroupname = cursor.getString(cursor.getColumnIndex("custgroupname"));
                        boolean credit = cursor.getInt(cursor.getColumnIndex("iscredit")) == 1 ? true : false;
                        saleorder_entry.btncustomer.setText(/*customerid + ":" +*/ customername);
                        saleorder_entry.btncustgroup.setText(/*custgroupid+":"+*/custgroupname);
//                            String pay_type = credit == false ? "1:Cash Down" : "2:Credit";
//                            saleorder_entry.btnpaytype.setText(pay_type);
                        saleorder_entry.sh.get(0).setCustomerid(customerid);
                        saleorder_entry.selected_custgroupid = custgroupid;
                        saleorder_entry.creditcustomer = credit;
                        saleorder_entry.sh.get(0).setPay_type(credit == false ? 1 : 2);
                        saleorder_entry.isCreditcustomer = selectInsertLibrary.CheckingCreditCustomer((int) customerid);
                    } while (cursor.moveToNext());


                }
            }

        }
        else if(context.toString().contains("sale_entry_tv")) {
            String sqlString = null;

            if (sale_entry_tv.use_customergroup) {
                sqlString = "select customerid,name,iscredit,CustGroupID,CustGroupname from customer where customerid=(select min(customerid)customerid " +
                        " from customer" +
                        " where Townshipid=" + sale_entry_tv.selected_townshipid +
                        " and ((custgroupid<>-1 and custgroupid=" + sale_entry_tv.selected_custgroupid + ") or " + sale_entry_tv.selected_custgroupid + "=-1)"+
                        " group by Townshipid)";
            } else {
                sqlString = "select customerid,name,iscredit,CustGroupID,CustGroupname from customer where customerid=(select min(customerid)customerid " +
                        " from customer" +
                        " where Townshipid=" + sale_entry_tv.selected_townshipid +
                        " group by Townshipid)";
            }

            Cursor cursor = DatabaseHelper.rawQuery(sqlString);
            if (cursor != null && cursor.getCount() != 0) {
                if (cursor.moveToFirst()) {
                    do {
                        long customerid = cursor.getLong(cursor.getColumnIndex("customerid"));
                        String customername = cursor.getString(cursor.getColumnIndex("name"));
                        long custgroupid = cursor.getLong(cursor.getColumnIndex("custgroupid"));
                        String custgroupname = cursor.getString(cursor.getColumnIndex("custgroupname"));
                        boolean credit = cursor.getInt(cursor.getColumnIndex("iscredit")) == 1 ? true : false;
                        sale_entry_tv.btncustomer.setText(/*customerid + ":" +*/ customername);
                        sale_entry_tv.btncustgroup.setText(/*custgroupid+":"+*/custgroupname);
//                            String pay_type = credit == false ? "1:Cash Down" : "2:Credit";
//                            sale_entry.btnpaytype.setText(pay_type);
                        sale_entry_tv.sh.get(0).setCustomerid(customerid);
                        sale_entry_tv.selected_custgroupid = custgroupid;
                        sale_entry_tv.creditcustomer = credit;
                        sale_entry_tv.sh.get(0).setPay_type(credit == false ? 1 : 2);
                        sale_entry_tv.isCreditcustomer = selectInsertLibrary.CheckingCreditCustomer((int) customerid);
                    } while (cursor.moveToNext());


                }
            }
        }
        else {
            String sqlString = null;

            if (sale_entry.use_customergroup) {
                sqlString = "select customerid,name,iscredit,CustGroupID,CustGroupname from customer where customerid=(select min(customerid)customerid " +
                        " from customer" +
                        " where Townshipid=" + sale_entry.selected_townshipid +
                        " and ((custgroupid<>-1 and custgroupid=" + sale_entry.selected_custgroupid + ") or " + sale_entry.selected_custgroupid + "=-1)"+
                        " group by Townshipid)";
            } else {
                sqlString = "select customerid,name,iscredit,CustGroupID,CustGroupname from customer where customerid=(select min(customerid)customerid " +
                        " from customer" +
                        " where Townshipid=" + sale_entry.selected_townshipid +
                        " group by Townshipid)";
            }

            Cursor cursor = DatabaseHelper.rawQuery(sqlString);
            if (cursor != null && cursor.getCount() != 0) {
                if (cursor.moveToFirst()) {
                    do {
                        long customerid = cursor.getLong(cursor.getColumnIndex("customerid"));
                        String customername = cursor.getString(cursor.getColumnIndex("name"));
                        long custgroupid = cursor.getLong(cursor.getColumnIndex("custgroupid"));
                        String custgroupname = cursor.getString(cursor.getColumnIndex("custgroupname"));
                        boolean credit = cursor.getInt(cursor.getColumnIndex("iscredit")) == 1 ? true : false;
                        sale_entry.btncustomer.setText(/*customerid + ":" +*/ customername);
                        sale_entry.btncustgroup.setText(/*custgroupid+":"+*/custgroupname);
//                            String pay_type = credit == false ? "1:Cash Down" : "2:Credit";
//                            sale_entry.btnpaytype.setText(pay_type);
                        sale_entry.sh.get(0).setCustomerid(customerid);
                        sale_entry.selected_custgroupid = custgroupid;
                        sale_entry.creditcustomer = credit;
                        sale_entry.sh.get(0).setPay_type(credit == false ? 1 : 2);
                        sale_entry.isCreditcustomer = selectInsertLibrary.CheckingCreditCustomer((int) customerid);
                    } while (cursor.moveToNext());


                }
            }
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
