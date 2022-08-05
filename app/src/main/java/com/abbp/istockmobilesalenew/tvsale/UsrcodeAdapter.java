package com.abbp.istockmobilesalenew.tvsale;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abbp.istockmobilesalenew.DatabaseHelper;
import com.abbp.istockmobilesalenew.R;
import com.abbp.istockmobilesalenew.category;
import com.abbp.istockmobilesalenew.categoryAdapter;
import com.abbp.istockmobilesalenew.frmlogin;
import com.abbp.istockmobilesalenew.frmmain;
import com.abbp.istockmobilesalenew.sale_det;
import com.abbp.istockmobilesalenew.usr_code;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UsrcodeAdapter extends RecyclerView.Adapter<UsrcodeAdapter.MyViewHolder> {

    Context context;
    File directory;
    ArrayList<com.abbp.istockmobilesalenew.usr_code> data = new ArrayList<>();
    ArrayList<category> categories = new ArrayList<>();
    String usr_code;
    RecyclerView rv;
    int Use_Tax = 0;

    static long specialPrice = 0;

    public UsrcodeAdapter(Context context, ArrayList<usr_code> data, RecyclerView rv, ArrayList<category> categories) {
        this.context = context;
        this.data = data;
        this.rv = rv;
        this.categories = categories;
    }


    public UsrcodeAdapter(Context context, ArrayList<usr_code> data, RecyclerView rv) {
        this.context = context;
        this.data = data;
        this.rv = rv;
    }


    @Override
    public UsrcodeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater lf = LayoutInflater.from(parent.getContext());
        View v = null;
        if (frmmain.use_pic == 1) {
            v = lf.inflate(R.layout.itemwithpic, parent, false);
        } else {
            v = lf.inflate(R.layout.item_usrcode, parent, false);
        }
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        //added by EKK on 04-11-2020
        if (frmmain.use_pic == 1) {

            String sqlImg = "select path from usr_code_img where usr_code = '" + data.get(position).getUsr_code() + "'";
            Cursor cursor = DatabaseHelper.rawQuery(sqlImg);
            String url = "";
            if (cursor != null && cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    url = cursor.getString(cursor.getColumnIndex("path"));
                    Picasso.with(context).load(url).into(holder.iv);
                    Picasso.with(context).load(url).placeholder(R.drawable.deficon).into(holder.iv);
                }
            }

            holder.tv.setText(String.format(" %s", data.get(position).getDescription()));

            holder.cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (data.get(position).getUsr_code().equals("Back")) {
                        //sale_entry_tv.imgFilterCode.setVisibility(View.GONE);
                        sale_entry_tv.fitercode = "Category";
                        if (categories.size() > 0) {
                            categories.clear();
                        }
                        if (frmmain.withoutclass.equals("false")) {
                            categories.add(new category("Back"));
                        }
                        Cursor cursor = DatabaseHelper.DistinctCategorySelectQuery("Usr_Code", new String[]{"category", "categoryname", "class"}, "sortcode,categoryname");
                        if (cursor != null && cursor.getCount() != 0) {
                            if (cursor.moveToFirst()) {
                                do {
                                    long categoryid = cursor.getLong(cursor.getColumnIndex("category"));
                                    String name = cursor.getString(cursor.getColumnIndex("categoryname"));
                                    long classid = cursor.getLong(cursor.getColumnIndex("class"));
                                    categories.add(new category(categoryid, classid, name));
                                } while (cursor.moveToNext());

                            }
                            cursor.close();
                        }

                        categoryAdapter ad = new categoryAdapter(context, categories, rv);
                        rv.setAdapter(ad);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(context.getApplicationContext(), 4);
                        rv.setLayoutManager(gridLayoutManager);

                    } else {
                        AddData(data.get(position).getUsr_code());
                        if (context.toString().contains("sale_entry_tv")) {
                            GetDiscountCode();
                        }
                    }


                }
            });

        } //use_pic
        else {

            holder.txtName.setText(String.format(" %s", data.get(position).getDescription()));
            String saleprice = String.format("%,." + frmmain.price_places + "f", data.get(position).getSalePrice());
            holder.txtPrice.setText(saleprice);
            holder.layoutItem.setBackgroundResource(R.drawable.usercodegradiant);
            holder.layoutItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (data.get(position).getUsr_code().equals("Back")) {
                        sale_entry_tv.fitercode = "Category";
                        if (categories.size() > 0) {
                            categories.clear();
                        }
                        if (frmmain.withoutclass.equals("false")) {
                            categories.add(new category("Back"));
                        }
                        Cursor cursor = DatabaseHelper.DistinctCategorySelectQuery("Usr_Code", new String[]{"category", "categoryname", "class"}, "sortcode,categoryname");
                        if (cursor != null && cursor.getCount() != 0) {
                            if (cursor.moveToFirst()) {
                                do {
                                    long categoryid = cursor.getLong(cursor.getColumnIndex("category"));
                                    String name = cursor.getString(cursor.getColumnIndex("categoryname"));
                                    long classid = cursor.getLong(cursor.getColumnIndex("class"));
                                    categories.add(new category(categoryid, classid, name));
                                } while (cursor.moveToNext());

                            }
                            cursor.close();
                        }

                        CategoryAdapter adapter = new CategoryAdapter(context, categories, rv);
                        GridLayoutManager gridLayoutManager = new GridLayoutManager(context.getApplicationContext(), 4);
                        rv.setLayoutManager(gridLayoutManager);
                        rv.setAdapter(adapter);

                    } else {

                        AddData(data.get(position).getUsr_code());

                        if (context.toString().contains("sale_entry_tv")) {
                            GetDiscountCode();
                        }

                        sale_entry_tv.itemAdapter.notifyDataSetChanged();
                        sale_entry_tv.entrygrid.smoothScrollToPosition(sale_entry_tv.sd.size());
                        sale_entry_tv.getSummary();

                    }


                }
            });
        }

    }


    private void GetDiscountCode() {
        if (sale_entry_tv.sd.size() > 0) {
            long code = sale_entry_tv.sd.get((sale_entry_tv.sd.size() - 1)).getCode();
            long locationid = sale_entry_tv.sh.get(0).getLocationid();
            int unit_type = sale_entry_tv.sd.get((sale_entry_tv.sd.size() - 1)).getUnit_type();
            int level = GetPriceLevel();
            double discount = 0;
            double dispercent = 0;
            String s = level == 0 ? "" : String.valueOf(level);

            String sql = "select disamount" + s + ",dispercent" + s + " from discount_code " +
                    " where code=" + code + " and unit_type=" + unit_type +
                    " and locationid=" + locationid;
            Cursor cursor = DatabaseHelper.rawQuery(sql);
            if (cursor != null && cursor.getCount() != 0) {
                if (cursor.moveToFirst()) {
                    do {
                        discount = cursor.getDouble(cursor.getColumnIndex("disamount" + s));
                        dispercent = cursor.getDouble(cursor.getColumnIndex("dispercent" + s));
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }

            int index = sale_entry_tv.sd.size() - 1;

            if (discount > 0) {
                sale_entry_tv.sd.get(index).setDis_type(5);
                sale_entry_tv.sd.get(index).setDis_percent(0);
                double discount_price = sale_entry_tv.sd.get(index).getSale_price() - discount;
                sale_entry_tv.sd.get(index).setDis_price(discount_price);
            } else if (dispercent > 0) {
                sale_entry_tv.sd.get(index).setDis_type(5);
                sale_entry_tv.sd.get(index).setDis_percent(dispercent);
                double discount_price = sale_entry_tv.sd.get(index).getSale_price() - (sale_entry_tv.sd.get(index).getSale_price() * (dispercent / 100));
                sale_entry_tv.sd.get(index).setDis_price(discount_price);
            }
        }
    }


    //Added by abbp barcode scanner on 19/6/2019
    public static void scanner(String usr_code) {
        AddData(usr_code);


    }

    private static void AddData(String usr_code) {

/*        specialPrice = GetPriceLevel();
        String sale_price = specialPrice == 0 ? "uc.sale_price" : "uc.saleprice" + specialPrice;
        String SP = specialPrice == 0 ? "SP" : "SP" + specialPrice;
        String sqlString = "select uc.unit_type,code,description," + sale_price + " as sale_price,open_price,smallest_unit_qty,unitname,unitshort,CalNoTax from Usr_Code uc " +
                " where uc.unit_type=1 and uc.usr_code='" + usr_code + "'";
        Cursor cursor = DatabaseHelper.rawQuery(sqlString);
        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {

                    long code = cursor.getLong(cursor.getColumnIndex("code"));
                    double price = cursor.getDouble(cursor.getColumnIndex("sale_price"));
                    int open_price = cursor.getInt(cursor.getColumnIndex("open_price"));
                    double smallest_unit_qty = cursor.getDouble(cursor.getColumnIndex("smallest_unit_qty"));
                    int unit_type = cursor.getInt(cursor.getColumnIndex("unit_type"));
                    String unit_short = cursor.getString(cursor.getColumnIndex("unitshort"));
                    String unitname = cursor.getString(cursor.getColumnIndex("unitname"));
                    String desc = cursor.getString(cursor.getColumnIndex("description"));
                    int CalNoTax = cursor.getInt(cursor.getColumnIndex("CalNoTax"));
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    sale_entry_tv.sd.add(new sale_det(
                            sale_entry_tv.tranid,
                            sale_entry_tv.sd.size() + 1,
                            df.format(new Date()),
                            1,
                            open_price,
                            smallest_unit_qty,
                            unit_type,
                            price,
                            0,
                            0,
                            0,
                            "",
                            code, unit_short, desc, CalNoTax, SP));
                    //Toast.makeText(context,sale_entry_tv.sd.get(0),Toast.LENGTH_LONG).show();
                } while (cursor.moveToNext());
            }

        }
        cursor.close();
        sale_entry_tv.itemAdapter.notifyDataSetChanged();
        sale_entry_tv.entrygrid.setSelection(sale_entry_tv.sd.size());
        sale_entry_tv.getSummary();*/

        int defunit = frmmain.defunit;
        int[] ut = new int[3];
        int i = 0;
        Cursor cc = DatabaseHelper.rawQuery("select distinct unit_type from Usr_Code where usr_code='" + usr_code + "'");
        if (cc != null && cc.getCount() != 0) {
            if (cc.moveToFirst()) {
                do {
                    int utt = cc.getInt(cc.getColumnIndex("unit_type"));
                    ut[i] = utt;
                    i++;
                } while (cc.moveToNext());
            }
            cc.close();
        }

        if (i > 1) {
            if (i == 3) {
                specialPrice = GetPriceLevel();
                String sale_price = specialPrice == 0 ? "uc.sale_price" : "uc.saleprice" + specialPrice;
                String SP = specialPrice == 0 ? "SP" : "SP" + specialPrice;
                String sqlString = "select uc.unit_type,code,description," + sale_price + " as sale_price,open_price,smallest_unit_qty,unitname,unitshort,CalNoTax from Usr_Code uc " +
                        " where uc.unit_type=" + defunit + " and uc.usr_code='" + usr_code + "'";
                Cursor cursor = DatabaseHelper.rawQuery(sqlString);
                if (cursor != null && cursor.getCount() != 0) {
                    if (cursor.moveToFirst()) {
                        do {

                            long code = cursor.getLong(cursor.getColumnIndex("code"));
                            double price = cursor.getDouble(cursor.getColumnIndex("sale_price"));
                            int open_price = cursor.getInt(cursor.getColumnIndex("open_price"));
                            double smallest_unit_qty = cursor.getDouble(cursor.getColumnIndex("smallest_unit_qty"));
                            int unit_type = cursor.getInt(cursor.getColumnIndex("unit_type"));
                            String unit_short = cursor.getString(cursor.getColumnIndex("unitshort")).equals("null") ? "" : cursor.getString(cursor.getColumnIndex("unitshort"));
                            String unitname = cursor.getString(cursor.getColumnIndex("unitname"));
                            String desc = cursor.getString(cursor.getColumnIndex("description"));
                            int CalNoTax = cursor.getInt(cursor.getColumnIndex("CalNoTax"));
                            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                            sale_entry_tv.sd.add(new sale_det(
                                    sale_entry_tv.tranid,
                                    sale_entry_tv.sd.size() + 1,
                                    df.format(new Date()),
                                    1,
                                    open_price,
                                    smallest_unit_qty,
                                    unit_type,
                                    price,
                                    0,
                                    0,
                                    0,
                                    "",
                                    code, unit_short, desc, CalNoTax, SP));
                        } while (cursor.moveToNext());
                    }
                    cursor.close();
                }
            } else {
                int utt = 2;
                if (defunit == 2 || defunit == 3) {
                    utt = 2;
                } else if (defunit == 1) {
                    utt = 1;
                }
                specialPrice = GetPriceLevel();
                String sale_price = specialPrice == 0 ? "uc.sale_price" : "uc.saleprice" + specialPrice;
                String SP = specialPrice == 0 ? "SP" : "SP" + specialPrice;
                String sqlString = "select uc.unit_type,code,description," + sale_price + " as sale_price,open_price,smallest_unit_qty,unitname,unitshort,CalNoTax from Usr_Code uc " +
                        " where uc.unit_type=" + utt + " and uc.usr_code='" + usr_code + "'";
                Cursor cursor = DatabaseHelper.rawQuery(sqlString);
                if (cursor != null && cursor.getCount() != 0) {
                    if (cursor.moveToFirst()) {
                        do {

                            long code = cursor.getLong(cursor.getColumnIndex("code"));
                            double price = cursor.getDouble(cursor.getColumnIndex("sale_price"));
                            int open_price = cursor.getInt(cursor.getColumnIndex("open_price"));
                            double smallest_unit_qty = cursor.getDouble(cursor.getColumnIndex("smallest_unit_qty"));
                            int unit_type = cursor.getInt(cursor.getColumnIndex("unit_type"));
                            String unit_short = cursor.getString(cursor.getColumnIndex("unitshort")).equals("null") ? "" : cursor.getString(cursor.getColumnIndex("unitshort"));
                            String unitname = cursor.getString(cursor.getColumnIndex("unitname"));
                            String desc = cursor.getString(cursor.getColumnIndex("description"));
                            int CalNoTax = cursor.getInt(cursor.getColumnIndex("CalNoTax"));
                            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                            sale_entry_tv.sd.add(new sale_det(
                                    sale_entry_tv.tranid,
                                    sale_entry_tv.sd.size() + 1,
                                    df.format(new Date()),
                                    1,
                                    open_price,
                                    smallest_unit_qty,
                                    unit_type,
                                    price,
                                    0,
                                    0,
                                    0,
                                    "",
                                    code, unit_short, desc, CalNoTax, SP));
                        } while (cursor.moveToNext());
                    }
                    cursor.close();
                }
            }

        } else if (i == 1 || defunit == 1) {
            specialPrice = GetPriceLevel();
            String sale_price = specialPrice == 0 ? "uc.sale_price" : "uc.saleprice" + specialPrice;
            String SP = specialPrice == 0 ? "SP" : "SP" + specialPrice;
            String sqlString = "select distinct uc.unit_type,code,description," + sale_price + " as sale_price,open_price,smallest_unit_qty,unitname,unitshort,CalNoTax from Usr_Code uc " +
                    " where uc.unit_type=1 and uc.usr_code='" + usr_code + "'";
            Cursor cursor = DatabaseHelper.rawQuery(sqlString);
            if (cursor != null && cursor.getCount() != 0) {
                if (cursor.moveToFirst()) {
                    do {

                        long code = cursor.getLong(cursor.getColumnIndex("code"));
                        double price = cursor.getDouble(cursor.getColumnIndex("sale_price"));
                        int open_price = cursor.getInt(cursor.getColumnIndex("open_price"));
                        double smallest_unit_qty = cursor.getDouble(cursor.getColumnIndex("smallest_unit_qty"));
                        int unit_type = cursor.getInt(cursor.getColumnIndex("unit_type"));
                        String unit_short = cursor.getString(cursor.getColumnIndex("unitshort")).equals("null") ? "" : cursor.getString(cursor.getColumnIndex("unitshort"));
                        String unitname = cursor.getString(cursor.getColumnIndex("unitname"));
                        String desc = cursor.getString(cursor.getColumnIndex("description"));
                        int CalNoTax = cursor.getInt(cursor.getColumnIndex("CalNoTax"));
                        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                        sale_entry_tv.sd.add(new sale_det(
                                sale_entry_tv.tranid,
                                sale_entry_tv.sd.size() + 1,
                                df.format(new Date()),
                                1,
                                open_price,
                                smallest_unit_qty,
                                unit_type,
                                price,
                                0,
                                0,
                                0,
                                "",
                                code, unit_short, desc, CalNoTax, SP));
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }

        }

        sale_entry_tv.itemAdapter.notifyDataSetChanged();
        sale_entry_tv.entrygrid.smoothScrollToPosition(sale_entry_tv.sd.size());
        sale_entry_tv.getSummary();

    }


    private static int GetPriceLevel() {
        int level = 0;
        boolean useUserpricelevel = false;
        boolean useCustpricelevel = false;
        Cursor cursor = DatabaseHelper.rawQuery("select isuseuserpricelevel,isusecustpricelevel from SystemSetting");
        if (cursor != null && cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    useUserpricelevel = cursor.getInt(cursor.getColumnIndex("isuseuserpricelevel")) == 1;
                    useCustpricelevel = cursor.getInt(cursor.getColumnIndex("isusecustpricelevel")) == 1;
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        if (useCustpricelevel) {
            String sSql = "select pricelevelid from customer where customerid =" + sale_entry_tv.sh.get(0).getCustomerid();
            cursor = DatabaseHelper.rawQuery(sSql);
            if (cursor != null && cursor.getCount() != 0) {
                if (cursor.moveToFirst()) {
                    do {
                        level = cursor.getInt(cursor.getColumnIndex("pricelevelid"));
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }

        } else if (useUserpricelevel) {
            String sSql = "select salepricelevelid from posuser where userid=" + frmlogin.LoginUserid;
            cursor = DatabaseHelper.rawQuery(sSql);
            if (cursor != null && cursor.getCount() != 0) {
                if (cursor.moveToFirst()) {
                    do {
                        level = cursor.getInt(cursor.getColumnIndex("salepricelevelid"));
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
        }

        return level;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layoutItem;
        TextView txtName;
        TextView txtPrice;
        CardView cv;
        ImageView iv;
        TextView tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            if (frmmain.use_pic == 1) {
                cv = itemView.findViewById(R.id.cardsale);
                iv = itemView.findViewById(R.id.itempic);
                tv = itemView.findViewById(R.id.info_text);
            } else {
                layoutItem = itemView.findViewById(R.id.layout_item_usrcode);
                txtName = itemView.findViewById(R.id.info_text);
                txtPrice = itemView.findViewById(R.id.txt_price);
            }

        }
    }
}
