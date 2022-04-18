package com.abbp.istockmobilesalenew;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StockStatusAdapter extends RecyclerView.Adapter<StockStatusAdapter.MyViewHolder> {
    Context context;
    ArrayList<StockStatus> stockStatuses;

    public StockStatusAdapter(Context context, ArrayList<StockStatus> stockStatuses) {
        this.context = context;
        this.stockStatuses = stockStatuses;
    }

    @Override
    public MyViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        LayoutInflater lf= LayoutInflater.from(viewGroup.getContext());
        View v=lf.inflate(R.layout.stockstatusitems,viewGroup,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder( MyViewHolder myViewHolder, int i) {
        myViewHolder.code.setText(stockStatuses.get(i).getUsrcode());
        myViewHolder.description.setText(stockStatuses.get(i).getDescription());
        myViewHolder.saleAmt.setText(stockStatuses.get(i).getSaleamount()+"");
        myViewHolder.balance.setText(stockStatuses.get(i).getBalanceqty()+"");

    }

    @Override
    public int getItemCount() {
        return stockStatuses.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView code,description,saleAmt,balance;
        public MyViewHolder(View itemView) {
            super(itemView);
            code=itemView.findViewById(R.id.code);
            description=itemView.findViewById(R.id.description);
            saleAmt=itemView.findViewById(R.id.saleamt);
            balance=itemView.findViewById(R.id.balance);
        }
    }
}
