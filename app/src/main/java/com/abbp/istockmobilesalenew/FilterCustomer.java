package com.abbp.istockmobilesalenew;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FilterCustomer extends RecyclerView.Adapter<FilterCustomer.MyViewHolder> {

    Context context;
    ArrayList<customer> data = new ArrayList<>();
    Button btn;
    AlertDialog da;
    String actionName;
    public static int ccid = -1;

    public FilterCustomer(Context context, ArrayList<customer> data, Button btn, AlertDialog da, String actionName) {
        this.data = data;
        this.context = context;
        this.btn = btn;
        this.da = da;
        this.actionName = actionName;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater lf = LayoutInflater.from(parent.getContext());
        View v = lf.inflate(R.layout.headerbinding, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.btn.setText(" " + data.get(position).getName());
        holder.btn.setBackgroundResource(R.drawable.usercodegradiant);
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn.setText(/*data.get(position).getCustomerid()+":"+*/data.get(position).getName());
                ccid = (int) data.get(position).getCustomerid();
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
                } else
                    FilterUser.uid = -1;

                FilterLocation.locid = -1;
                // frmsalelist.BindingData();
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

