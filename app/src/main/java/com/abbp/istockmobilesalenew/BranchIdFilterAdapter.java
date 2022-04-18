package com.abbp.istockmobilesalenew;

import android.content.Context;
import android.opengl.Visibility;
//import android.support.annotation.NonNull;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.widget.CardView;
//import android.support.v7.widget.RecyclerView;
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

public class BranchIdFilterAdapter extends RecyclerView.Adapter<BranchIdFilterAdapter.MyViewHolder> {
    Context context;
    ArrayList<Branch> branchArrayList;

    Button choosebranchid;
    AlertDialog da;

    public BranchIdFilterAdapter(Context context, ArrayList<Branch> branchArrayList, Button choosebranchid, AlertDialog da) {
        this.context = context;
        this.branchArrayList = branchArrayList;
        this.choosebranchid = choosebranchid;
        this.da = da;
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
myViewHolder.township.setVisibility(View.GONE);

    myViewHolder.customer.setText(branchArrayList.get(i).getName()+"");

        myViewHolder.cardView.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            choosebranchid.setText(branchArrayList.get(i).getShortdesc()+"");
            frmCustOutstand.branchid=branchArrayList.get(i).getBranchid()+"";

            da.dismiss();
        }
    });
    }

    @Override
    public int getItemCount() {
        return branchArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView customer,township;
        CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            customer=itemView.findViewById(R.id.txtcustomername);
            township=itemView.findViewById(R.id.txttownshipname);
            cardView=itemView.findViewById(R.id.card);
        }
    }
}
