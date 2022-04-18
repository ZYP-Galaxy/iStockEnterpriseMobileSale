package com.abbp.istockmobilesalenew;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustOutstandAdapter extends RecyclerView.Adapter<CustOutstandAdapter.MyViewHolder> {
    Context context;
    ArrayList<CustOutstand> custOutstandArrayList;
    public CustOutstandAdapter(Context c,ArrayList<CustOutstand> arrayList) {
        this.context=c;
        this.custOutstandArrayList=arrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater=LayoutInflater.from(viewGroup.getContext());
        View view=layoutInflater.inflate(R.layout.custoutstanditem,viewGroup,false);


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
myViewHolder.merchantform.setText(custOutstandArrayList.get(i).getMerchantname());
myViewHolder.currency.setText(custOutstandArrayList.get(i).getCurrencyshort());
myViewHolder.opening.setText(custOutstandArrayList.get(i).getOpenamount());
//if(custOutstandArrayList.get(i).getSaleamount().equals(null) ){
//   myViewHolder.saleamount.setText("");
//}else if(custOutstandArrayList.get(i).getSaleamount().equals("") ) {
//
//}
//        if(null!=null){
//            System.out.println(custOutstandArrayList.get(i).getSaleamount());
//        }
//        String nornot=custOutstandArrayList.get(i).getSaleamount();
//        if(nornot=="null"){
//            System.out.println(custOutstandArrayList.get(i).getSaleamount());
//        }
       // if(custOutstandArrayList.get(i).getSaleamount()!="null")
        String saleamt=custOutstandArrayList.get(i).getSaleamount();
        String retunrnamt=custOutstandArrayList.get(i).getReturnamount();
        String discountamt=custOutstandArrayList.get(i).getDiscountamount();
        String paidamt=custOutstandArrayList.get(i).getPaidamount();
        String closingamt=custOutstandArrayList.get(i).getClosingbalance();
        if(saleamt!="null"){
            myViewHolder.saleamount.setText(saleamt);
        }else {
            myViewHolder.saleamount.setText("");
        }
if(retunrnamt!="null"){
    myViewHolder.returnin.setText(retunrnamt);
}else {
    myViewHolder.returnin.setText("");
}

if(discountamt!="null"){
    myViewHolder.discountamount.setText(discountamt);
}else {
    myViewHolder.discountamount.setText("");
}


if(paidamt!="null"){
    myViewHolder.paidamount.setText(paidamt);
}
else {
    myViewHolder.paidamount.setText("");
}
if(closingamt!="null"){
    myViewHolder.closing.setText(closingamt);
}
else {
    myViewHolder.closing.setText("");
}
myViewHolder.imgpaid.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
//        System.out.println("this is paid");
        Toast.makeText(context,"this is paid",Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(context,CreditPaidForOutstand.class);
        context.startActivity(intent);
//        LayoutInflater layoutInflater=
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
////        View layout = inflater.inflate(R.layout.activity_credit_paid_for_outstand,null);
    }
});

    }

    @Override
    public int getItemCount() {
        return custOutstandArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView merchantform,currency,opening,saleamount,discountamount,returnin,paidamount,closing;
        ImageView imgpaid;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            merchantform=itemView.findViewById(R.id.merchantinform);
            currency=itemView.findViewById(R.id.currency);
            opening=itemView.findViewById(R.id.opening);
            saleamount=itemView.findViewById(R.id.sale);
            discountamount=itemView.findViewById(R.id.discount);
            returnin=itemView.findViewById(R.id.returnin);
            paidamount=itemView.findViewById(R.id.paid);
            closing=itemView.findViewById(R.id.closing);
            imgpaid=itemView.findViewById(R.id.imgpaid);
        }
    }
}
