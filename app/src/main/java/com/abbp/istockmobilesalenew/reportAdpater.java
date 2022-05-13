package com.abbp.istockmobilesalenew;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class reportAdpater extends RecyclerView.Adapter<reportAdpater.MyViewHolder> {
    Context context;

    public reportAdpater(Context context) {
        this.context = context;
    }

    @Override
    public reportAdpater.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater lf= LayoutInflater.from(parent.getContext());
        View v=null;
        v=lf.inflate(R.layout.voucheritem,parent,false);
        return new reportAdpater.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(reportAdpater.MyViewHolder holder, int position) {
        holder.txtsr.setText(String.valueOf(sale_entry.sd.get(position).getSr()));
        holder.txtDesc.setText(String.valueOf(sale_entry.sd.get(position).getDesc()));
        holder.txtqty.setText(String.valueOf(sale_entry.sd.get(position).getUnit_qty()));
        holder.txtunit.setText(String.valueOf(sale_entry.sd.get(position).getUnit_short()));
        holder.txtprice.setText(String.valueOf(sale_entry.sd.get(position).getSale_price()));
        Double amt= sale_entry.sd.get(position).getSale_price()* sale_entry.sd.get(position).getUnit_qty();
        holder.txtamount.setText(String.valueOf(amt));
        holder.txtamount.setText(String.valueOf(amt));
    }

    @Override
    public int getItemCount() {
        return sale_entry.sd.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtsr,txtDesc,txtqty,txtunit,txtprice,txtamount;
        public MyViewHolder(View itemView) {
            super(itemView);
            txtsr=itemView.findViewById(R.id.sr);
            txtDesc=itemView.findViewById(R.id.desc);
            txtunit=itemView.findViewById(R.id.unit);
            txtqty=itemView.findViewById(R.id.qty);
            txtprice=itemView.findViewById(R.id.price);
            txtamount=itemView.findViewById(R.id.amt);
        }
    }
}
