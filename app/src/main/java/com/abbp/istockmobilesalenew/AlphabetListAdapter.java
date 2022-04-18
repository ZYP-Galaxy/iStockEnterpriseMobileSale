package com.abbp.istockmobilesalenew;

import android.content.Context;
//import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class AlphabetListAdapter extends BaseAdapter {
    private List rows;
    private Context context;
    private ArrayList<customer> customerArrayList;

    public AlphabetListAdapter() {
    }

    public AlphabetListAdapter(Context context, ArrayList<customer> customerArrayList) {
        this.context = context;
        this.customerArrayList = customerArrayList;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }

    @Override
    public int getCount() {
        return rows.size();
    }

    @Override
    public Object getItem(int position) {
        return rows.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position) instanceof Section) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (getItemViewType(position) == 0) { // Item
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = (LinearLayout) inflater.inflate(R.layout.row_item, parent, false);
            }

            Item item = (Item) getItem(position);
//            customer customer= (com.abbp.istockmobilesalenew.customer) getItem(position);
            TextView textView = (TextView) view.findViewById(R.id.textView1);
            TextView textView2=view.findViewById(R.id.textView2);
            TextView textView3=view.findViewById(R.id.customerid);
//            textView.setText(item.text);
            StringTokenizer stringTokenizer=new StringTokenizer(item.text,",");
//            while (stringTokenizer.hasMoreTokens()){
                textView.setText(stringTokenizer.nextToken());
                textView2.setText(stringTokenizer.nextToken());
                String customerid=stringTokenizer.nextToken();
                textView3.setText(customerid);

//            CardView cardView=view.findViewById(R.id.cardview);
//            cardView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    TextView textView=view.findViewById(R.id.textView1);
//                    TextView textView1=view.findViewById(R.id.customerid);
////                Cursor member=(Cursor)adapterView.getItemAtPosition(i);
//
//
//
//
////                    frmCustOutstand.customerid=textView1.getText().toString();
////                    frmCustOutstand.choosecustomer.setText(textView.getText().toString());
//                    System.out.println( textView1.getText().toString()+" "+" "+textView.getText().toString()+" this is ");
//                }
//            });
           // }
        } else { // Section
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = (LinearLayout) inflater.inflate(R.layout.row_section, parent, false);
            }

            Section section = (Section) getItem(position);
            TextView textView = (TextView) view.findViewById(R.id.textView1);
            textView.setText(section.text);
        }

        return view;
    }
}

