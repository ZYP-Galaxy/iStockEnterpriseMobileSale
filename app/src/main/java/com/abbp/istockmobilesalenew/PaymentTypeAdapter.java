package com.abbp.istockmobilesalenew;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PaymentTypeAdapter extends RecyclerView.Adapter<PaymentTypeAdapter.MyViewHolder> {

    Context context;
    ArrayList<pay_type> data = new ArrayList<>();
    Button btn;
    AlertDialog da;

    public PaymentTypeAdapter(Context context, ArrayList<pay_type> data, Button btn, AlertDialog da) {
        this.data = data;
        this.context = context;
        this.btn = btn;
        this.da = da;
    }

    @Override
    public PaymentTypeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater lf = LayoutInflater.from(parent.getContext());
        View v = lf.inflate(R.layout.headerbinding, parent, false);
        return new PaymentTypeAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PaymentTypeAdapter.MyViewHolder holder, int position) {
        holder.btn.setText(" " + data.get(position).getName());
        holder.btn.setBackgroundResource(R.drawable.usercodegradiant);
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (PaymentTypeAdapter.this.context.toString().contains("saleorder_entry")) {
                    btn.setText(/*data.get(position).getPay_type()+":"+*/data.get(position).getName());
                    saleorder_entry.sh.get(0).setPay_type(data.get(position).getPay_type());
                    if (data.get(position).getPay_type() == 1) {
                        saleorder_entry.txtpaidAmt.setText("0");
                        saleorder_entry.sh.get(0).setPaid_amount(0);
                        saleorder_entry.getSummary();
                    }
                    da.dismiss();
                } else {
                    btn.setText(/*data.get(position).getPay_type()+":"+*/data.get(position).getName());
                    sale_entry.sh.get(0).setPay_type(data.get(position).getPay_type());
                    if (data.get(position).getPay_type() == 1) {
                        sale_entry.txtpaidamt.setText("0");
                        sale_entry.sh.get(0).setPaid_amount(0);
                        sale_entry.getSummary();
                    }
//                 else if(data.get(position).getPay_type()==2){
//                     sale_entry.bindingCreditBalance();
//                 }
                    da.dismiss();
                }

            }
        });
//        if(holder.btn.getText().toString().toLowerCase().contains("credit")){
//            sale_entry.bindingCreditBalance();
//        }
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
