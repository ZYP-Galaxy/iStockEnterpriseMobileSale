package com.abbp.istockmobilesalenew;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FilterLocation extends RecyclerView.Adapter<FilterLocation.MyViewHolder> {

    Context context;
    ArrayList<Location> data = new ArrayList<>();
    Button btn;
    AlertDialog da;
    public static int locid = -1;
    public static String shortdesc = "";
    //    public static long shortdesc=0;
    String actionName;

    public FilterLocation(Context context, ArrayList<Location> data, Button btn, AlertDialog da, String actionName) {
        this.data = data;
        this.context = context;
        this.btn = btn;
        this.da = da;
        this.actionName = actionName;
    }

    @Override
    public FilterLocation.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater lf = LayoutInflater.from(parent.getContext());
        View v = lf.inflate(R.layout.headerbinding, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FilterLocation.MyViewHolder holder, final int position) {
        holder.btn.setText(" " + data.get(position).getName());
        holder.btn.setBackgroundResource(R.drawable.usercodegradiant);
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn.setText(/*data.get(position).getLocationid()+":"+*/data.get(position).getName());
                locid = (int) data.get(position).getLocationid();
                shortdesc = data.get(position).getShortdes();

//                if (context.toString().contains("frmsalelist")){
//                    FilterUser.uid=-1;
//                FilterCustomer.ccid=-1;
//                frmsalelist.BindingData();
//                }
//comment by TTA on [10-212-2020]
                if (!actionName.equals("status")) {
                    switch (actionName) {
                        case "Sale History":
                            if (frmmain.isallowallusersviewforSE == 0)
                                FilterUser.uid = frmlogin.LoginUserid;
                            else
                                FilterUser.uid = -1;
                            break;
                        case "Sale Order History":
                            if (frmmain.isallowallusersviewforSO == 0)
                                FilterUser.uid = frmlogin.LoginUserid;
                            else
                                FilterUser.uid = -1;
                            break;
                    }
                    FilterCustomer.ccid = -1;
                    //frmsalelist.BindingData();
                }


//                FilterUser.uid=-1;
//                FilterCustomer.ccid=-1;
//                frmsalelist.BindingData();
                da.dismiss();

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


