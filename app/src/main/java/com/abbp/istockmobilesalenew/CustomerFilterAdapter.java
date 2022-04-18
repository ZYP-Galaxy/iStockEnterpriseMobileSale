package com.abbp.istockmobilesalenew;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomerFilterAdapter extends RecyclerView.Adapter<CustomerFilterAdapter.MyViewHolder> {
    Context context;
    ArrayList<customer> customerArrayList;
    Button choosecustomer;
    AlertDialog da;

    public CustomerFilterAdapter(Context context, ArrayList<customer> customerArrayList,Button choosecustomer,AlertDialog da) {
        this.context = context;
        this.customerArrayList = customerArrayList;
        this.choosecustomer=choosecustomer;
        this.da=da;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater=LayoutInflater.from(viewGroup.getContext());
        View view=layoutInflater.inflate(R.layout.customer_filter_item,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.customername.setText(customerArrayList.get(i).getName());
        myViewHolder.townshipname.setText(customerArrayList.get(i).getTownshipname());
        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosecustomer.setText(customerArrayList.get(i).getName());
                frmCustOutstand.customerid=customerArrayList.get(i).getCustomerid()+"";
                da.dismiss();
            }
        });
        //da.dismiss();
    }

    @Override
    public int getItemCount() {
        return customerArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView customername,townshipname;
        CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            customername=itemView.findViewById(R.id.txtcustomername);
            townshipname=itemView.findViewById(R.id.txttownshipname);
            cardView=itemView.findViewById(R.id.card);


        }
    }
}
