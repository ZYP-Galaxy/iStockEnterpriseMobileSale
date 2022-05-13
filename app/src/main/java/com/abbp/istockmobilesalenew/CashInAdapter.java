package com.abbp.istockmobilesalenew;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.abbp.istockmobilesalenew.tvsale.sale_entry_tv;

import java.util.ArrayList;

public class
CashInAdapter  extends RecyclerView.Adapter<CashInAdapter.MyViewHolder> {

    Context context;
    ArrayList<CashIn> data = new ArrayList<>();
    Button btn;
    AlertDialog da;

    public CashInAdapter(Context context, ArrayList<CashIn> data, Button btn, AlertDialog da) {
        this.data = data;
        this.context = context;
        this.btn = btn;
        this.da = da;
    }

    @Override
    public CashInAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater lf = LayoutInflater.from(parent.getContext());
        View v = lf.inflate(R.layout.headerbinding, parent, false);
        return new CashInAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CashInAdapter.MyViewHolder holder, int position) {
        holder.btn.setText(" " + data.get(position).getName());
        holder.btn.setBackgroundResource(R.drawable.usercodegradiant);
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (CashInAdapter.this.context.toString().contains("saleorder_entry")) {
                    btn.setText(/*data.get(position).getLocationid()+":"+*/data.get(position).getName());
                    saleorder_entry.sh.get(0).setDef_cashid(data.get(position).getAccountid());
                    da.dismiss();

                }
                else if(CashInAdapter.this.context.toString().contains("sale_entry_tv")) {
                    btn.setText(/*data.get(position).getLocationid()+":"+*/data.get(position).getName());
                    sale_entry_tv.sh.get(0).setDef_cashid(data.get(position).getAccountid());
                    da.dismiss();
                }
                else {
                    btn.setText(/*data.get(position).getLocationid()+":"+*/data.get(position).getName());
                    sale_entry.sh.get(0).setDef_cashid(data.get(position).getAccountid());
                    da.dismiss();
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
