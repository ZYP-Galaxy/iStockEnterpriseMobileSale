package com.abbp.istockmobilesalenew.tvsale;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.abbp.istockmobilesalenew.DatabaseHelper;
import com.abbp.istockmobilesalenew.R;
import com.abbp.istockmobilesalenew.frmlogin;
import com.abbp.istockmobilesalenew.frmmain;
import com.abbp.istockmobilesalenew.saleorder_entry;
import com.abbp.istockmobilesalenew.unitforcode;

import java.util.ArrayList;

public class UnitAdapter extends RecyclerView.Adapter<UnitAdapter.MyViewHolder> {

    Context context;
    ArrayList<unitforcode> data = new ArrayList<>();
    TextView txtprice, txtqty, txtunit_qty, txtnet;
    Button btn, btnDiscount;
    int itemposition;
    boolean showqty = false;
    ItemAdapter itemAdapter;
    int row_index = -1;
    AlertDialog da;

    public UnitAdapter(Context context, ArrayList<unitforcode> data, int itemposition, TextView txtprice, TextView txtqty, TextView txtunit_qty, TextView txtnet, Button btnDiscount, int row_index) {
        this.data = data;
        this.context = context;
        this.itemposition = itemposition;
        this.txtnet = txtnet;
        this.txtunit_qty = txtunit_qty;
        this.txtprice = txtprice;
        this.txtqty = txtqty;
        this.btnDiscount = btnDiscount;
        this.row_index = row_index;
        AlertDialog da;
        showqty = true;
    }

    public UnitAdapter(Context context, ArrayList<unitforcode> data, int itemposition, ItemAdapter itemAdapter, AlertDialog da) {
        this.data = data;
        this.context = context;
        this.itemposition = itemposition;
        this.itemAdapter = itemAdapter;
        showqty = false;
        this.da = da;

    }

    private long GetPriceLevel() {
        long level = 0;
        boolean useUserpricelevel = false;
        boolean useCustpricelevel = false;
        Cursor cursor = DatabaseHelper.rawQuery("select use_user_pricelevel,use_cust_pricelevel from SystemSetting");
        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    useUserpricelevel = cursor.getInt(cursor.getColumnIndex("use_user_pricelevel")) == 1 ? true : false;
                    useCustpricelevel = cursor.getInt(cursor.getColumnIndex("use_cust_pricelevel")) == 1 ? true : false;
                } while (cursor.moveToNext());


            }

        }
        cursor.close();
        if (useCustpricelevel) {
            String sSql = "select pricelevel from customer where customerid =" + sale_entry_tv.sh.get(0).getCustomerid();
            cursor = DatabaseHelper.rawQuery(sSql);
            if (cursor != null && cursor.getCount() != 0) {
                if (cursor.moveToFirst()) {
                    do {
                        level = cursor.getInt(cursor.getColumnIndex("pricelevel"));
                    } while (cursor.moveToNext());


                }

            }
            cursor.close();

        } else if (useUserpricelevel) {
            String sSql = "select saleprice_level from posuser where userid=" + frmlogin.LoginUserid;
            cursor = DatabaseHelper.rawQuery(sSql);
            if (cursor != null && cursor.getCount() != 0) {
                if (cursor.moveToFirst()) {
                    do {
                        level = cursor.getInt(cursor.getColumnIndex("saleprice_level"));
                    } while (cursor.moveToNext());


                }

            }
            cursor.close();
        } else {
            level = 0;
        }
        return level;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater lf = LayoutInflater.from(parent.getContext());
        View v = null;
        if (showqty) {
            v = lf.inflate(R.layout.unitbinding, parent, false);
        } else {
            v = lf.inflate(R.layout.headerbinding, parent, false);
        }
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (data.get(position).getShortdes().isEmpty()) holder.btn.setVisibility(View.GONE);
        holder.btn.setText(" " + data.get(position).getShortdes());
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*                if (UnitAdapter.this.context.toString().contains("saleorder_entry")) {
                    System.out.println("click me!!!!!!!!!!!!!!");
                    saleorder_entry.sd.get(itemposition).setUnt_type(data.get(position).getUnit_type());
                    saleorder_entry.sd.get(itemposition).setUnit_short(data.get(position).getShortdes());

                    if (frmmain.isusespecialprice == 1)
                        getSpecialPrice(itemposition, false); //added by EKK on 12-11-2020

                    //long specialPrice=GetPriceLevel();
                    //String SP=specialPrice==0?"SP":"SP"+specialPrice;
                    //sale_entry_tv.sd.get(itemposition).setPriceLevel(SP);
                    switch (saleorder_entry.sd.get(itemposition).getPriceLevel()) {
                        case "SP":
                            saleorder_entry.sd.get(itemposition).setSale_price(data.get(position).getSale_price());
                            saleorder_entry.sd.get(itemposition).setDis_price(data.get(position).getSale_price());
                            saleorder_entry.sd.get(itemposition).setDis_type(0);
                            break;
                        case "SP1":
                            saleorder_entry.sd.get(itemposition).setSale_price(data.get(position).getSale_Price1());
                            saleorder_entry.sd.get(itemposition).setDis_price(data.get(position).getSale_Price1());
                            saleorder_entry.sd.get(itemposition).setDis_type(0);
                            break;
                        case "SP2":
                            saleorder_entry.sd.get(itemposition).setSale_price(data.get(position).getSale_price2());
                            saleorder_entry.sd.get(itemposition).setDis_price(data.get(position).getSale_price2());
                            saleorder_entry.sd.get(itemposition).setDis_type(0);
                            break;
                        case "SP3":
                            saleorder_entry.sd.get(itemposition).setSale_price(data.get(position).getSale_price3());
                            saleorder_entry.sd.get(itemposition).setDis_price(data.get(position).getSale_price3());
                            saleorder_entry.sd.get(itemposition).setDis_type(0);
                            break;
                        case "SP4":
                            saleorder_entry.sd.get(itemposition).setSale_price(data.get(position).getSale_price4());
                            saleorder_entry.sd.get(itemposition).setDis_price(data.get(position).getSale_price4());
                            saleorder_entry.sd.get(itemposition).setDis_type(0);
                            break;
                        case "SP5":
                            saleorder_entry.sd.get(itemposition).setSale_price(data.get(position).getSale_price5());
                            saleorder_entry.sd.get(itemposition).setDis_price(data.get(position).getSale_price5());
                            saleorder_entry.sd.get(itemposition).setDis_type(0);
                            break;
                    }
                    saleorder_entry.sd.get(itemposition).setQty(saleorder_entry.sd.get(itemposition).getUnit_qty() * data.get(position).getSmallest_unit_qty());

                    if (showqty) {
                        saleorder_entry.txtshowUnit.setText(data.get(position).getShortdes());
                        txtprice.setText(String.valueOf(saleorder_entry.sd.get(itemposition).getSale_price()));
                        double qty = Double.parseDouble(txtunit_qty.getText().toString()) * data.get(position).getSmallest_unit_qty();
                        txtqty.setText(String.valueOf(qty));
                        double amt = Double.parseDouble(txtunit_qty.getText().toString()) * saleorder_entry.sd.get(itemposition).getSale_price();
                        txtnet.setText(String.valueOf(amt));
                        btnDiscount.setText("Normal");
                        saleorder_entry.getSummary();
                        row_index = position;
                        saleorder_entry.uad.notifyDataSetChanged();

                    } else {
                        saleorder_entry.entrygrid.setAdapter(itemAdapter);
                        saleorder_entry.getSummary();
                        da.dismiss();
                    }


                }//end sale order
                else {*/
                sale_entry_tv.sd.get(itemposition).setUnit_type(data.get(position).getUnit_type());
                sale_entry_tv.sd.get(itemposition).setUnit_short(data.get(position).getShortdes());


                if (frmmain.isusespecialprice == 1)
                    getSpecialPrice(itemposition, true); //added by EKK on 12-11-2020

                switch (sale_entry_tv.sd.get(itemposition).getPriceLevel()) {
                    case "SP":
                        sale_entry_tv.sd.get(itemposition).setSale_price(data.get(position).getSale_price());
                        sale_entry_tv.sd.get(itemposition).setDis_price(data.get(position).getSale_price());
                        sale_entry_tv.sd.get(itemposition).setDis_type(0);
                        break;
                    case "SP1":
                        sale_entry_tv.sd.get(itemposition).setSale_price(data.get(position).getSale_Price1());
                        sale_entry_tv.sd.get(itemposition).setDis_price(data.get(position).getSale_Price1());
                        sale_entry_tv.sd.get(itemposition).setDis_type(0);
                        break;
                    case "SP2":
                        sale_entry_tv.sd.get(itemposition).setSale_price(data.get(position).getSale_price2());
                        sale_entry_tv.sd.get(itemposition).setDis_price(data.get(position).getSale_price2());
                        sale_entry_tv.sd.get(itemposition).setDis_type(0);
                        break;
                    case "SP3":
                        sale_entry_tv.sd.get(itemposition).setSale_price(data.get(position).getSale_price3());
                        sale_entry_tv.sd.get(itemposition).setDis_price(data.get(position).getSale_price3());
                        sale_entry_tv.sd.get(itemposition).setDis_type(0);
                        break;
                    case "SP4":
                        sale_entry_tv.sd.get(itemposition).setSale_price(data.get(position).getSale_price4());
                        sale_entry_tv.sd.get(itemposition).setDis_price(data.get(position).getSale_price4());
                        sale_entry_tv.sd.get(itemposition).setDis_type(0);
                        break;
                    case "SP5":
                        sale_entry_tv.sd.get(itemposition).setSale_price(data.get(position).getSale_price5());
                        sale_entry_tv.sd.get(itemposition).setDis_price(data.get(position).getSale_price5());
                        sale_entry_tv.sd.get(itemposition).setDis_type(0);
                        break;
                }
                sale_entry_tv.sd.get(itemposition).setQty(sale_entry_tv.sd.get(itemposition).getUnit_qty() * data.get(position).getSmallest_unit_qty());

                if (showqty) {
                    sale_entry_tv.txtshowUnit.setText(data.get(position).getShortdes());
                    if (frmlogin.ishidesaleprice != 0) {
                        txtprice.setText("****");
                    } else
                        txtprice.setText(String.valueOf(sale_entry_tv.sd.get(itemposition).getSale_price()));

                    double qty = Double.parseDouble(txtunit_qty.getText().toString()) * data.get(position).getSmallest_unit_qty();
                    txtqty.setText(String.valueOf(qty));
                    double amt = Double.parseDouble(txtunit_qty.getText().toString()) * sale_entry_tv.sd.get(itemposition).getSale_price();
                    if (frmlogin.ishidesaleprice != 0)
                        txtnet.setText("****");
                    else
                        txtnet.setText(String.valueOf(amt));

                    btnDiscount.setText("Normal");
                    sale_entry_tv.getSummary();
                    row_index = position;
                    sale_entry_tv.uad.notifyDataSetChanged();

                } else {
                    sale_entry_tv.entrygrid.setAdapter(itemAdapter);
                    sale_entry_tv.getSummary();
                    da.dismiss();
                }

                GetDiscountCode(itemposition);

            }
//        }
        });
        if (row_index == position) {
            holder.btn.setBackgroundResource(R.drawable.usercodegradiant);
        } else {
            holder.btn.setBackgroundResource(R.drawable.unitgradiant);
        }


    }

    private void GetDiscountCode(int position) {
        long code = sale_entry_tv.sd.get(position).getCode();
        long locationid = sale_entry_tv.sh.get(0).getLocationid();
        int unit_type = sale_entry_tv.sd.get(position).getUnit_type();
        long level = sale_entry_tv.sd.get(position).getPricelevelid();
        double discount = 0;
        double dispercent = 0;
        String s = level == 0 ? "" : String.valueOf(level);


        String sql = "select disamount" + s + ",dispercent" + s + " from discount_code where code=" + code + " and unit_type=" + unit_type +
                " and '" + locationid + "' in (locationids)";
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
            sale_entry_tv.sd.get(index).setDis_type(5);
            sale_entry_tv.sd.get(index).setDis_percent(0);
            double discount_price = sale_entry_tv.sd.get(index).getSale_price() - discount;
            sale_entry_tv.sd.get(index).setDis_price(discount_price);
            sale_entry_tv.itemAdapter.notifyDataSetChanged();
            sale_entry_tv.entrygrid.setSelection(sale_entry_tv.sd.size());
            if (btnDiscount != null)
                btnDiscount.setText(String.valueOf(discount));
            sale_entry_tv.dis_percent = discount;
            sale_entry_tv.dis_typepercent = false;
            sale_entry_tv.getSummary();
        } else if (dispercent > 0) {
            sale_entry_tv.sd.get(index).setDis_type(5);
            sale_entry_tv.dis_typepercent = true;
            sale_entry_tv.dis_percent = dispercent;
            sale_entry_tv.sd.get(index).setDis_percent(dispercent);
            double discount_price = sale_entry_tv.sd.get(index).getSale_price() - (sale_entry_tv.sd.get(index).getSale_price() * (dispercent / 100));
            sale_entry_tv.sd.get(index).setDis_price(discount_price);
            sale_entry_tv.itemAdapter.notifyDataSetChanged();
            sale_entry_tv.entrygrid.setSelection(sale_entry_tv.sd.size());
            if (btnDiscount != null)
                btnDiscount.setText(dispercent + "%");
            sale_entry_tv.getSummary();
        } else {
            sale_entry_tv.sd.get(index).setDis_type(0);
            sale_entry_tv.dis_typepercent = false;
            sale_entry_tv.dis_percent = 0;
        }

    }


    //added by EKK on 12-11-2020
    private void getSpecialPrice(int itemposition, boolean isSaleEntry) {
        long code;
        int unit_type;
        if (isSaleEntry == true) {
            code = sale_entry_tv.sd.get(itemposition).getCode();
            unit_type = sale_entry_tv.sd.get(itemposition).getUnit_type();
        } else {
            code = saleorder_entry.sd.get(itemposition).getCode();
            unit_type = saleorder_entry.sd.get(itemposition).getUnit_type();
        }

        String sqlString = "select usr_code,unit_type from Usr_Code where  code=" + code;
        Cursor cursor = DatabaseHelper.rawQuery(sqlString);
        String usr_code = "";

        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {

                    usr_code = cursor.getString(cursor.getColumnIndex("usr_code"));

                } while (cursor.moveToNext());

            }
        }
        cursor.close();

        double minqty = 0, maxqty = 0;
        String pricelevel = "0";
        String tmpLevel = "SP";

        cursor = DatabaseHelper.rawQuery("select minqty,maxqty,pricelevel from s_saleprice where inactive = 0 and isdeleted=0 and usrcode= '" + usr_code + "' and unittype = " + unit_type);
        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    minqty = cursor.getDouble(cursor.getColumnIndex("minqty"));
                    maxqty = cursor.getDouble(cursor.getColumnIndex("maxqty"));
                    pricelevel = cursor.getString(cursor.getColumnIndex("pricelevel"));

                    String level = "";
                    switch (pricelevel) {
                        case "0":
                            level = "SP";
                            break;
                        case "1":
                            level = "SP1";
                            break;
                        case "2":
                            level = "SP2";
                            break;
                        case "3":
                            level = "SP3";
                            break;
                        case "4":
                            level = "SP4";
                            break;
                        case "5":
                            level = "SP5";
                            break;
                    }

                    double qty = 0;


                    if (isSaleEntry == true)
                        qty = sale_entry_tv.sd.get(itemposition).getUnit_qty();
                    else
                        qty = saleorder_entry.sd.get(itemposition).getUnit_qty();


                    if (minqty != 0 && maxqty != 0 && qty >= minqty && qty <= maxqty) {
                        tmpLevel = level;
                        if (isSaleEntry == true)
                            sale_entry_tv.sd.get(itemposition).setPriceLevel(level);
                        else
                            saleorder_entry.sd.get(itemposition).setPriceLevel(level);
                    } else {
                        if (isSaleEntry == true) {
                            // level = sale_entry_tv.sd.get(itemposition).getPriceLevel();
                            sale_entry_tv.sd.get(itemposition).setPriceLevel(tmpLevel);
                        } else {
                            //level = saleorder_entry.sd.get(itemposition).getPriceLevel();
                            saleorder_entry.sd.get(itemposition).setPriceLevel(tmpLevel);
                        }
                    }

                } while (cursor.moveToNext());
            }
        }


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        Button btn;

        public MyViewHolder(View itemView) {
            super(itemView);
            if (showqty) {
                btn = itemView.findViewById(R.id.btnunit);
            } else {
                btn = itemView.findViewById(R.id.info_text);
                btn.setBackgroundResource(R.drawable.usercodegradiant);

            }

        }
    }
}
