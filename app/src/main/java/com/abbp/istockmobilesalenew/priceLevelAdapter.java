package com.abbp.istockmobilesalenew;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class priceLevelAdapter extends RecyclerView.Adapter<priceLevelAdapter.MyViewHolder> {
    Context context;
    ArrayList<PriceLevel> priceLevels = new ArrayList<>();
    int itemposistion;
    TextView txtprice, txtnet, txtqty;
    Button btnDiscount;
    int row_index = -1;
    public boolean issaleentry = false;

    public priceLevelAdapter(Context context, int itemposistion, TextView txtprice, TextView txtqty, TextView txtnet, int row_index) {
        this.context = context;
        this.itemposistion = itemposistion;
        this.txtprice = txtprice;
        this.txtqty = txtqty;
        this.txtnet = txtnet;
        this.row_index = row_index;
        Binding_PriceLevel();
    }

    public priceLevelAdapter(Context context, int itemposistion, TextView txtprice, TextView txtqty, TextView txtnet, Button btnDiscount, int row_index) {
        this.context = context;
        this.itemposistion = itemposistion;
        this.txtprice = txtprice;
        this.txtqty = txtqty;
        this.txtnet = txtnet;
        this.btnDiscount = btnDiscount;
        this.row_index = row_index;
        Binding_PriceLevel();
    }

    public void Binding_PriceLevel() {
        for (int i = 0; i < 6; i++) {
            String SP = i == 0 ? "SP" : "SP" + i;
            PriceLevel priceLevel = new PriceLevel(i, SP);
            priceLevels.add(priceLevel);
        }
    }

    @Override
    public priceLevelAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater lf = LayoutInflater.from(parent.getContext());
        View v = null;
        v = lf.inflate(R.layout.unitbinding, parent, false);
        return new priceLevelAdapter.MyViewHolder(v);
    }

    //not allow_pricelevel in price lewel Adapter for sale entry Editinfo btn click Level SP modified by ABBP
    @Override
    public void onBindViewHolder(priceLevelAdapter.MyViewHolder holder, int position) {

        holder.btn.setText(" " + priceLevels.get(position).getLevel_name());
        if (frmlogin.isallowpricelevel == 0) {

            holder.btn.setEnabled(false);
        }
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseSP(position);
            }
        });

//        System.out.println(row_index+"this is position "+position);
        if (row_index == position) {
            holder.btn.setBackgroundResource(R.drawable.usercodegradiant);
        } else {
            holder.btn.setBackgroundResource(R.drawable.unitgradiant);
        }
    }

    public void chooseSP(int position) {
        System.out.println("u click me!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(itemposistion + " this is item + Position " + position);
        System.out.println(priceLevels.get(position).getLevel_name() + " pricelevels");
//                System.out.println(sale_entry.sd.get(itemposistion).getCode());
        if (context.toString().contains("saleorder_entry")) {
            System.out.println("saleorder");
            saleorder_entry.sd.get(itemposistion).setPriceLevel(priceLevels.get(position).getLevel_name());
            saleorder_entry.txtShowSP.setText(priceLevels.get(position).getLevel_name());

            System.out.println(priceLevels.get(position).getLevel_name() + "this is price level");
            double sale_price = getSalePrice(priceLevels.get(position).getLevel_name());
            saleorder_entry.sd.get(itemposistion).setSale_price(sale_price);
            saleorder_entry.sd.get(itemposistion).setDis_price(sale_price);

            double amt = Double.parseDouble(txtqty.getText().toString()) * sale_price;

            if (frmlogin.ishidesaleprice != 0) {
                txtprice.setText("****");
                txtnet.setText("****");
            } else {
                txtprice.setText(String.format("%,." + frmmain.price_places + "f", sale_price));
                txtnet.setText(String.format("%,." + frmmain.price_places + "f", amt));
            }


            saleorder_entry.getSummary();
            row_index = position;
            saleorder_entry.pad.notifyDataSetChanged();

        } else {
            issaleentry = true;
            System.out.println("sale");
            sale_entry.sd.get(itemposistion).setPriceLevel(priceLevels.get(position).getLevel_name());
            sale_entry.sd.get(itemposistion).setPricelevelid((short) priceLevels.get(position).getLevel());
            sale_entry.txtShowSP.setText(priceLevels.get(position).getLevel_name());

            System.out.println(priceLevels.get(position).getLevel_name() + "this is price level");
            double sale_price = getSalePrice(priceLevels.get(position).getLevel_name());
            sale_entry.sd.get(itemposistion).setSale_price(sale_price);
            sale_entry.sd.get(itemposistion).setDis_price(sale_price);

            double amt = Double.parseDouble(txtqty.getText().toString()) * sale_price;

            if (frmlogin.ishidesaleprice != 0) {
                txtprice.setText("****");
                txtnet.setText("****");
            } else {
                txtprice.setText(String.format("%,." + frmmain.price_places + "f", sale_price));
                txtnet.setText(String.format("%,." + frmmain.price_places + "f", amt));
            }

            GetDiscountCode(itemposistion);

            sale_entry.getSummary();
            row_index = position;
            sale_entry.pad.notifyDataSetChanged();
        }

    }

    private void GetDiscountCode(int position) {
        long code = sale_entry.sd.get(position).getCode();
        long locationid = sale_entry.sh.get(0).getLocationid();
        int unit_type = sale_entry.sd.get(position).getUnit_type();
        long level = sale_entry.sd.get(position).getPricelevelid();
        double discount = 0;
        double dispercent = 0;
        String s = level == 0 ? "" : String.valueOf(level);


        String sql = "select disamount" + s + ",dispercent" + s + " from discount_code where code=" + code + " and unit_type=" + unit_type +
                " and locationid=" + locationid;
        Cursor cursor = DatabaseHelper.rawQuery(sql);
        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    discount = cursor.getDouble(cursor.getColumnIndex("disamount" + s));
                    dispercent = cursor.getDouble(cursor.getColumnIndex("dispercent" + s));
                } while (cursor.moveToNext());
            }
        }
        cursor.close();

        int index = position;

        if (discount > 0) {
            sale_entry.sd.get(index).setDis_type(5);
            sale_entry.sd.get(index).setDis_percent(0);
            double discount_price = sale_entry.sd.get(index).getSale_price() - discount;
            sale_entry.sd.get(index).setDis_price(discount_price);
            sale_entry.itemAdapter.notifyDataSetChanged();
            sale_entry.entrygrid.setSelection(sale_entry.sd.size());
            btnDiscount.setText(String.valueOf(discount));
            sale_entry.getSummary();
        } else if (dispercent > 0) {
            sale_entry.sd.get(index).setDis_type(5);
            sale_entry.sd.get(index).setDis_percent(dispercent);
            sale_entry.dis_percent = dispercent;
            double discount_price = sale_entry.sd.get(index).getSale_price() - (sale_entry.sd.get(index).getSale_price() * (dispercent / 100));
            sale_entry.sd.get(index).setDis_price(discount_price);
            sale_entry.itemAdapter.notifyDataSetChanged();
            sale_entry.entrygrid.setSelection(sale_entry.sd.size());
            btnDiscount.setText(dispercent + "%");
            sale_entry.getSummary();
        } else {
            sale_entry.sd.get(index).setDis_type(0);
            btnDiscount.setText("Normal");
        }

    }

    public double getSalePrice(String pricelevel) {

        double sale_price = 0;
        String level = "";
        switch (pricelevel) {
            case "SP":
                level = "uc.sale_price";
                break;
            case "SP1":
                level = "uc.saleprice1";
                break;
            case "SP2":
                level = "uc.saleprice2";
                break;
            case "SP3":
                level = "uc.saleprice3";
                break;
            case "SP4":
                level = "uc.saleprice4";
                break;
            case "SP5":
                level = "uc.saleprice5";
                break;
        }


        String sqlString = "";
        if (issaleentry) {
            sqlString = "select uc.unit_type,code,description," + level + ",smallest_unit_qty,unitname,unitshort,CalNoTax from Usr_Code uc " +
                    " where code=" + sale_entry.sd.get(itemposistion).getCode() + " and unit_type=" + sale_entry.sd.get(itemposistion).getUnit_type();

        } else {
            sqlString = "select uc.unit_type,code,description," + level + ",smallest_unit_qty,unitname,unitshort,CalNoTax from Usr_Code uc " +
                    " where code=" + sale_entry.sd.get(itemposistion).getCode() + " and unit_type=" + sale_entry.sd.get(itemposistion).getUnit_type();

        }
        Cursor cursor = DatabaseHelper.rawQuery(sqlString);
        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    switch (pricelevel) {
                        case "SP":
                            level = "uc.sale_price";
//                            sale_entry.sd.set()
                            break;
                        case "SP1":
                            level = "uc.saleprice1";
                            break;
                        case "SP2":
                            level = "uc.saleprice2";
                            break;
                        case "SP3":
                            level = "uc.saleprice3";
                            break;
                        case "SP4":
                            level = "uc.saleprice4";
                            break;
                        case "SP5":
                            level = "uc.saleprice5";
                            break;
                    }
                    sale_price = cursor.getDouble(cursor.getColumnIndex(level));

                } while (cursor.moveToNext());


            }

        }
        cursor.close();
        return sale_price;

    }

    @Override
    public int getItemCount() {
        return priceLevels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        Button btn;

        public MyViewHolder(View itemView) {
            super(itemView);
            btn = itemView.findViewById(R.id.btnunit);
        }
    }
}
