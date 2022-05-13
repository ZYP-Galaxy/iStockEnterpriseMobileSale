package com.abbp.istockmobilesalenew;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static com.abbp.istockmobilesalenew.sale_entry.itemPosition;
import static com.abbp.istockmobilesalenew.sale_entry.pad;

public class itemAdapter extends BaseAdapter {
    Context context;
    boolean isqty = false;
    boolean isSalePrice = false;
    String keynum = "";
    AlertDialog msg, dialog, da;
    ArrayList<unitforcode> uc = new ArrayList<>();
    public static TextView txtamt;
    TextView tv4;
    TextView tv5;
    TextView tv2; // added by EKK 12-11-2020
    boolean startOpen;
    public static itemAdapter itemAd;
    public static double disamt;


    public itemAdapter(Context context) {
        this.context = context;

    }

    @Override
    public int getCount() {
        if (this.context.toString().contains("saleorder_entry")) {
            return saleorder_entry.sd.size();
        } else {
            return sale_entry.sd.size();
        }

    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View conView;
        if (this.context.toString().contains("saleorder_entry")) {
            conView = saleorddetdatabind(position, convertView, parent);

        } else {
            conView = saledetdatabind(position, convertView, parent);

        }
        return conView;
    }

    private View saleorddetdatabind(int position, View convertView, ViewGroup parent) {
        LayoutInflater lf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = lf.inflate(R.layout.dataitem, null, false);
        TextView tv = (TextView) convertView.findViewById(R.id.sr);
        TextView tv1 = (TextView) convertView.findViewById(R.id.desc);
        tv2 = (TextView) convertView.findViewById(R.id.qty);
        TextView tv3 = (TextView) convertView.findViewById(R.id.unit);
        boolean use_unit = false;
        Cursor cursorplvl = DatabaseHelper.rawQuery("select isuseunit from SystemSetting");
        if (cursorplvl != null && cursorplvl.getCount() != 0) {
            if (cursorplvl.moveToFirst()) {
                do {
                    use_unit = cursorplvl.getInt(cursorplvl.getColumnIndex("isuseunit")) == 1 ? true : false;
                } while (cursorplvl.moveToNext());


            }

        }
        cursorplvl.close();
        tv3.setVisibility(use_unit == true ? View.VISIBLE : View.GONE);
        tv4 = (TextView) convertView.findViewById(R.id.amt);
        tv5 = convertView.findViewById(R.id.txtDelete);
        tv.setText(String.valueOf(saleorder_entry.sd.get(position).getSr()));

        tv1.setText(saleorder_entry.sd.get(position).getDesc());
        Double unit_qty = saleorder_entry.sd.get(position).getUnit_qty();
        String qtyAsString = String.format("%." + frmmain.qty_places + "f", unit_qty);
        tv2.setText(qtyAsString);
        tv3.setText(saleorder_entry.sd.get(position).getUnit_short());


        double amt = saleorder_entry.sd.get(position).getSale_price() * saleorder_entry.sd.get(position).getUnit_qty();
        String numberAsString = String.format("%,." + frmmain.price_places + "f", amt);

        //modified by EKK on 27-10-2020
        if (frmlogin.ishidesaleprice != 0) {
            tv4.setText("****");
           /* tv4.setCompoundDrawablesWithIntrinsicBounds(
                    0, //left
                    0, //top
                    R.drawable.eyehide, //right
                    0);*/
            tv4.setEnabled(false);
        } else {
            tv4.setText(String.valueOf(numberAsString));
            tv4.setEnabled(true);
        }

        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("this is qty click");
                isqty = true;
                isSalePrice = false;
                keynum = tv2.getText().toString();
                showKeyPad(tv2, tv2, position);
                itemPosition = -1;
            }
        });
        tv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder bd = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
                bd.setTitle("iStock");
                bd.setMessage("Are you sure want to delete this row?");
                bd.setCancelable(false);
                bd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saleorder_entry.sd.remove(position);
                        itemPosition = -1;
                        for (int i = 0; i < saleorder_entry.sd.size(); i++) {
                            saleorder_entry.sd.get(i).setSr(i + 1);
                        }
                        saleorder_entry.getData();
                        saleorder_entry.getSummary();
                        if (saleorder_entry.sd.size() == 0) {
                            String tax = "Tax" + (saleorder_entry.getTax() > 0 ? "( " + saleorder_entry.getTax() + "% )" : "");
                            saleorder_entry.txttax.setText(tax);
                            saleorder_entry.sh.get(0).setTax_per(saleorder_entry.getTax());
                            saleorder_entry.sh.get(0).setTax_amount(0.0);
                            saleorder_entry.sh.get(0).setDiscount(0.0);
                            saleorder_entry.sh.get(0).setDiscount_per(0);
                            saleorder_entry.sh.get(0).setPaid_amount(0);
                            saleorder_entry.txtvoudis.setText("0");
                            // saleorder_entry.txtpaidAmt.setText("0"); //modified by EKK on 05-11-2020
                            saleorder_entry.getSummary();
                        }
                        dialog.dismiss();
                    }
                });

                bd.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                bd.create().show();


            }
        });
        tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isqty = false;
                isSalePrice = true;
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.changesaleprice, null);
                builder.setView(view);
                TextView txtheader = view.findViewById(R.id.caption);
                String title = saleorder_entry.sd.get(position).getDesc();
                txtheader.setText(title);
                TextView txtChangePrice = view.findViewById(R.id.txtChangePrice);
                txtamt = view.findViewById(R.id.txtChangeAmt);
                ImageButton save = view.findViewById(R.id.imgSave);
                //txtChangePrice.setText(String.valueOf(sale_entry.sd.get(position).getSale_price()));
                txtChangePrice.setText(String.format("%,." + frmmain.price_places + "f", saleorder_entry.sd.get(position).getSale_price()));
                disamt = saleorder_entry.sd.get(position).getSale_price() - saleorder_entry.sd.get(position).getDis_price();
                Double amt = sale_entry.StringTODouble(txtChangePrice.getText().toString()) * saleorder_entry.sd.get(position).getUnit_qty();
                String numberAsString = String.format("%,." + frmmain.price_places + "f", amt);
                txtamt.setText(numberAsString);


//not change_price in sale entry entrygrid amount click modified by ABBP
                //add open_price modified by ABBP
                txtChangePrice.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //keynum=txtChangePrice.getText().toString();
                        int op = saleorder_entry.sd.get(position).getOpen_price();
                        if (frmlogin.canchangesaleprice == 1 || op == 1) {
                            keynum = String.format("%,." + frmmain.price_places + "f", sale_entry.StringTODouble(txtChangePrice.getText().toString()));
                            showKeyPad(txtChangePrice, txtChangePrice, position);
                        }
                        Double amt = sale_entry.StringTODouble(txtChangePrice.getText().toString()) * saleorder_entry.sd.get(position).getUnit_qty();
                        String numberAsString = String.format("%,." + frmmain.price_places + "f", amt);
                        txtamt.setText(numberAsString);

                    }
                });
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        saleorder_entry.sd.get(position).setSale_price(sale_entry.StringTODouble(txtChangePrice.getText().toString()));
                        saleorder_entry.sd.get(position).setDis_price(sale_entry.StringTODouble(txtChangePrice.getText().toString()));

                        if (saleorder_entry.sd.get(position).getDis_type() == 3
                                || saleorder_entry.sd.get(position).getDis_type() == 4
                                || saleorder_entry.sd.get(position).getDis_type() == 6
                                || saleorder_entry.sd.get(position).getDis_type() == 7) {
                            saleorder_entry.sd.get(position).setDis_percent(0);
                            saleorder_entry.sd.get(position).setDis_price(saleorder_entry.sd.get(position).getSale_price());
                        } else if (saleorder_entry.sd.get(position).getDis_type() == 1
                                || saleorder_entry.sd.get(position).getDis_type() == 2) {
                            double dispercent = saleorder_entry.sd.get(position).getDis_type() == 1 ? 0.05 : 0.1;
                            double discount = saleorder_entry.sd.get(position).getDis_type() == 1 ? 5 : 10;
                            saleorder_entry.sd.get(position).setDis_percent(discount);
                            double dis_price = saleorder_entry.sd.get(position).getSale_price() - (saleorder_entry.sd.get(position).getSale_price() * (dispercent));
                            saleorder_entry.sd.get(position).setDis_price(dis_price);
                        } else if (saleorder_entry.sd.get(position).getDis_type() == 5) {
                            if (saleorder_entry.sd.get(position).getDis_percent() > 0) {
                                double dis_percent = saleorder_entry.sd.get(position).getDis_percent();
                                saleorder_entry.sd.get(position).setDis_percent(dis_percent);
                                double dis_price = saleorder_entry.sd.get(position).getSale_price() - (saleorder_entry.sd.get(position).getSale_price() * (dis_percent / 100));
                                saleorder_entry.sd.get(position).setDis_price(dis_price);


                            } else {
                                double dis_percent = 0;
                                saleorder_entry.sd.get(position).setDis_percent(dis_percent);
                                double dis_price = saleorder_entry.sd.get(position).getSale_price() - disamt;
                                saleorder_entry.sd.get(position).setDis_price(dis_price);
                            }
                        }

                        saleorder_entry.getSummary();
                        saleorder_entry.entrygrid.setAdapter(itemAd);
                        saleorder_entry.entrygrid.setSelection(position);
                        itemPosition = -1;
                        da.dismiss();

                    }
                });
                da = builder.create();
                da.show();

            }

        });
        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    System.out.println("this is unit!!!!!!!!!!!!!!!!!!!");
                    String sqlString;
                    int unitcount = 0;
                    String filter;
                    Cursor cursor = null;
                    if (uc.size() > 0) uc.clear();
                    AlertDialog.Builder bd = new AlertDialog.Builder(context);
                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View view = inflater.inflate(R.layout.changeheadervalue, null);
                    bd.setCancelable(false);
                    bd.setView(view);
                    RecyclerView rv = view.findViewById(R.id.rcvChange);
                    ImageButton imgClose = view.findViewById(R.id.imgNochange);
                    da = bd.create();
                    imgClose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            da.dismiss();
                        }
                    });
                    EditText etdSearch = view.findViewById(R.id.etdSearch);
                    ImageButton imgSearch = view.findViewById(R.id.imgSearch);
                    etdSearch.setVisibility(View.GONE);
                    imgSearch.setVisibility(View.GONE);
                    sqlString = "select * from Usr_Code where unit_type>0 and code=" + saleorder_entry.sd.get(position).getCode() + " order by unit_type";
                    cursor = DatabaseHelper.rawQuery(sqlString);
                    unitcount = cursor.getCount();
                    if (cursor != null && cursor.getCount() != 0) {
                        if (cursor.moveToFirst()) {
                            do {
                                int code = cursor.getInt(cursor.getColumnIndex("code"));
                                String usr_code = cursor.getString(cursor.getColumnIndex("usr_code"));
                                int unit_type = cursor.getShort(cursor.getColumnIndex("unit_type"));
                                int unit = cursor.getInt(cursor.getColumnIndex("unit"));
                                String unitname = cursor.getString(cursor.getColumnIndex("unitname"));
                                String shortdes = cursor.getString(cursor.getColumnIndex("unitshort"));
                                double saleprice = cursor.getDouble(cursor.getColumnIndex("sale_price"));
                                double saleprice1 = cursor.getDouble(cursor.getColumnIndex("saleprice1"));
                                double saleprice2 = cursor.getDouble(cursor.getColumnIndex("saleprice2"));
                                double saleprice3 = cursor.getDouble(cursor.getColumnIndex("saleprice3"));
                                double saleprice4 = cursor.getDouble(cursor.getColumnIndex("saleprice4"));
                                double saleprice5 = cursor.getDouble(cursor.getColumnIndex("saleprice5"));
                                double sqty = cursor.getDouble((cursor.getColumnIndex("smallest_unit_qty")));
                                uc.add(new unitforcode(code, usr_code, unit_type, unit, unitname, shortdes, saleprice, saleprice1, saleprice2, saleprice3, saleprice4, saleprice5, sqty));

                            } while (cursor.moveToNext());

                        }
                    }

                    cursor.close();

                    UnitAdapter ad = new UnitAdapter(context, uc, position, itemAd, da);
                    rv.setAdapter(ad);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 4);
                    rv.setLayoutManager(gridLayoutManager);
                    if (unitcount > 0) {
                        da.show();
                    }
                    cursor = null;
                    itemPosition = -1;


                } catch (Exception e) {
                    da.dismiss();
                }
            }
        });

        return convertView;

    }

    private View saledetdatabind(int position, View convertView, ViewGroup parent) {
        LayoutInflater lf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = lf.inflate(R.layout.dataitem, null, false);
        TextView tv = (TextView) convertView.findViewById(R.id.sr);
        TextView tv1 = (TextView) convertView.findViewById(R.id.desc);
        TextView tv2 = (TextView) convertView.findViewById(R.id.qty);
        TextView tv3 = (TextView) convertView.findViewById(R.id.unit);
        boolean use_unit = false;
        Cursor cursorplvl = DatabaseHelper.rawQuery("select isuseunit from SystemSetting");
        if (cursorplvl != null && cursorplvl.getCount() != 0) {
            if (cursorplvl.moveToFirst()) {
                do {
                    use_unit = cursorplvl.getInt(cursorplvl.getColumnIndex("isuseunit")) == 1;
                } while (cursorplvl.moveToNext());

            }
        }
        if (cursorplvl != null) {
            cursorplvl.close();
        }
        tv3.setVisibility(use_unit ? View.VISIBLE : View.GONE);
        tv4 = (TextView) convertView.findViewById(R.id.amt);
        tv5 = convertView.findViewById(R.id.txtDelete);
        tv.setText(String.valueOf(sale_entry.sd.get(position).getSr()));

        tv1.setText(sale_entry.sd.get(position).getDesc());
        Double unit_qty = sale_entry.sd.get(position).getUnit_qty();
        String qtyAsString = String.format("%." + frmmain.qty_places + "f", unit_qty);
        tv2.setText(qtyAsString);
        tv3.setText(sale_entry.sd.get(position).getUnit_short());


        double amt = sale_entry.sd.get(position).getSale_price() * sale_entry.sd.get(position).getUnit_qty();
        String numberAsString = String.format("%,." + frmmain.price_places + "f", amt);

        //modified by EKK on 27-10-2020
        if (frmlogin.ishidesaleprice != 0) {
            tv4.setText("****");
           /* tv4.setCompoundDrawablesWithIntrinsicBounds(
                    0, //left
                    0, //top
                    R.drawable.eyehide, //right
                    0);*/
            tv4.setEnabled(false);
        } else {
            tv4.setText(String.valueOf(numberAsString));
            tv4.setEnabled(true);
        }

        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isqty = true;
                isSalePrice = false;
                keynum = tv2.getText().toString();
                showKeyPad(tv2, tv2, position);
                itemPosition = -1;
            }
        });
        tv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder bd = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
                bd.setTitle("iStock");
                bd.setMessage("Are you sure want to delete this row?");
                bd.setCancelable(false);
                bd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sale_entry.sd.remove(position);
                        itemPosition = -1;
                        for (int i = 0; i < sale_entry.sd.size(); i++) {
                            sale_entry.sd.get(i).setSr(i + 1);
                        }
                        sale_entry.getData();
                        sale_entry.getSummary();
                        if (sale_entry.sd.size() == 0) {
                            String tax = "Tax" + (sale_entry.getTax() > 0 ? "( " + sale_entry.getTax() + "% )" : "");
                            sale_entry.txttax.setText(tax);
                            sale_entry.sh.get(0).setTax_per(sale_entry.getTax());
                            sale_entry.sh.get(0).setTax_amount(0.0);
                            sale_entry.sh.get(0).setDiscount(0.0);
                            sale_entry.sh.get(0).setDiscount_per(0);
                            sale_entry.sh.get(0).setPaid_amount(0);
                            sale_entry.txtvoudis.setText("0");
                            sale_entry.txtpaidamt.setText("0");
                            sale_entry.getSummary();
                        }
                        dialog.dismiss();
                    }
                });

                bd.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                bd.create().show();


            }
        });
        tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isqty = false;
                isSalePrice = true;
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.changesaleprice, null);
                builder.setView(view);
                TextView txtheader = view.findViewById(R.id.caption);
                String title = sale_entry.sd.get(position).getDesc();
                txtheader.setText(title);
                TextView txtChangePrice = view.findViewById(R.id.txtChangePrice);
                txtamt = view.findViewById(R.id.txtChangeAmt);
                ImageButton save = view.findViewById(R.id.imgSave);
                //txtChangePrice.setText(String.valueOf(sale_entry.sd.get(position).getSale_price()));
                txtChangePrice.setText(String.format("%,." + frmmain.price_places + "f", sale_entry.sd.get(position).getSale_price()));
                disamt = sale_entry.sd.get(position).getSale_price() - sale_entry.sd.get(position).getDis_price();
                Double amt = sale_entry.StringTODouble(txtChangePrice.getText().toString()) * sale_entry.sd.get(position).getUnit_qty();
                String numberAsString = String.format("%,." + frmmain.price_places + "f", amt);
                txtamt.setText(numberAsString);


//not change_price in sale entry entrygrid amount click modified by ABBP
                //add open_price modified by ABBP
                txtChangePrice.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //keynum=txtChangePrice.getText().toString();
                        int op = sale_entry.sd.get(position).getOpen_price();
                        if (frmlogin.canchangesaleprice == 1 || op == 1) {
                            keynum = String.format("%,." + frmmain.price_places + "f", sale_entry.StringTODouble(txtChangePrice.getText().toString()));
                            showKeyPad(txtChangePrice, txtChangePrice, position);
                        }

                        Double amt = sale_entry.StringTODouble(txtChangePrice.getText().toString()) * sale_entry.sd.get(position).getUnit_qty();
                        String numberAsString = String.format("%,." + frmmain.price_places + "f", amt);
                        txtamt.setText(numberAsString);

                    }
                });
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        sale_entry.sd.get(position).setSale_price(sale_entry.StringTODouble(txtChangePrice.getText().toString()));
                        sale_entry.sd.get(position).setDis_price(sale_entry.StringTODouble(txtChangePrice.getText().toString()));

                        if (sale_entry.sd.get(position).getDis_type() == 3
                                || sale_entry.sd.get(position).getDis_type() == 4
                                || sale_entry.sd.get(position).getDis_type() == 6
                                || sale_entry.sd.get(position).getDis_type() == 7) {
                            sale_entry.sd.get(position).setDis_percent(0);
                            sale_entry.sd.get(position).setDis_price(sale_entry.sd.get(position).getSale_price());
                        } else if (sale_entry.sd.get(position).getDis_type() == 1
                                || sale_entry.sd.get(position).getDis_type() == 2) {
                            double dispercent = sale_entry.sd.get(position).getDis_type() == 1 ? 0.05 : 0.1;
                            double discount = sale_entry.sd.get(position).getDis_type() == 1 ? 5 : 10;
                            sale_entry.sd.get(position).setDis_percent(discount);
                            double dis_price = sale_entry.sd.get(position).getSale_price() - (sale_entry.sd.get(position).getSale_price() * (dispercent));
                            sale_entry.sd.get(position).setDis_price(dis_price);
                        } else if (sale_entry.sd.get(position).getDis_type() == 5) {
                            if (sale_entry.sd.get(position).getDis_percent() > 0) {
                                double dis_percent = sale_entry.sd.get(position).getDis_percent();
                                sale_entry.sd.get(position).setDis_percent(dis_percent);
                                double dis_price = sale_entry.sd.get(position).getSale_price() - (sale_entry.sd.get(position).getSale_price() * (dis_percent / 100));
                                sale_entry.sd.get(position).setDis_price(dis_price);


                            } else {
                                double dis_percent = 0;
                                sale_entry.sd.get(position).setDis_percent(dis_percent);
                                double dis_price = sale_entry.sd.get(position).getSale_price() - disamt;
                                sale_entry.sd.get(position).setDis_price(dis_price);
                            }
                        }

                        sale_entry.getSummary();
                        sale_entry.entrygrid.setAdapter(itemAd);
                        sale_entry.entrygrid.setSelection(position);
                        itemPosition = -1;
                        da.dismiss();

                    }
                });
                da = builder.create();
                da.show();

            }

        });
        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    System.out.println("this is unit");
                    String sqlString;
                    int unitcount = 0;
                    String filter;
                    Cursor cursor = null;
                    if (uc.size() > 0) uc.clear();
                    AlertDialog.Builder bd = new AlertDialog.Builder(context);
                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View view = inflater.inflate(R.layout.changeheadervalue, null);
                    bd.setCancelable(false);
                    bd.setView(view);
                    RecyclerView rv = view.findViewById(R.id.rcvChange);
                    ImageButton imgClose = view.findViewById(R.id.imgNochange);
                    da = bd.create();
                    imgClose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            da.dismiss();
                        }
                    });
                    EditText etdSearch = view.findViewById(R.id.etdSearch);
                    ImageButton imgSearch = view.findViewById(R.id.imgSearch);
                    etdSearch.setVisibility(View.GONE);
                    imgSearch.setVisibility(View.GONE);

                    sqlString = "select * from Usr_Code where unit_type>0 and code=" + sale_entry.sd.get(position).getCode() + " order by unit_type";
                    cursor = DatabaseHelper.rawQuery(sqlString);
                    unitcount = cursor.getCount();
                    if (cursor != null && cursor.getCount() != 0) {
                        if (cursor.moveToFirst()) {
                            do {
                                int code = cursor.getInt(cursor.getColumnIndex("code"));
                                String usr_code = cursor.getString(cursor.getColumnIndex("usr_code"));
                                int unit_type = cursor.getShort(cursor.getColumnIndex("unit_type"));
                                int unit = cursor.getInt(cursor.getColumnIndex("unit"));
                                String unitname = cursor.getString(cursor.getColumnIndex("unitname"));
                                String shortdes = cursor.getString(cursor.getColumnIndex("unitshort"));
                                double saleprice = cursor.getDouble(cursor.getColumnIndex("sale_price"));
                                double saleprice1 = cursor.getDouble(cursor.getColumnIndex("saleprice1"));
                                double saleprice2 = cursor.getDouble(cursor.getColumnIndex("saleprice2"));
                                double saleprice3 = cursor.getDouble(cursor.getColumnIndex("saleprice3"));
                                double saleprice4 = cursor.getDouble(cursor.getColumnIndex("saleprice4"));
                                double saleprice5 = cursor.getDouble(cursor.getColumnIndex("saleprice5"));
                                double sqty = cursor.getDouble((cursor.getColumnIndex("smallest_unit_qty")));
                                uc.add(new unitforcode(code, usr_code, unit_type, unit, unitname, shortdes, saleprice, saleprice1, saleprice2, saleprice3, saleprice4, saleprice5, sqty));

                            } while (cursor.moveToNext());

                        }
                    }

                    cursor.close();

                    UnitAdapter ad = new UnitAdapter(context, uc, position, itemAd, da);
                    rv.setAdapter(ad);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 4);
                    rv.setLayoutManager(gridLayoutManager);
                    if (unitcount > 0) {
                        da.show();
                    }
                    cursor = null;
                    itemPosition = -1;


                } catch (Exception e) {
                    da.dismiss();
                }
            }
        });

        return convertView;
    }

    private void showKeyPad(TextView txt, TextView source, int itemposition) {

        startOpen = true;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.keypad, null);
        float density = context.getResources().getDisplayMetrics().density;
        final PopupWindow pw = new PopupWindow(layout, (int) density * 310, (int) density * 500, true);
        pw.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        pw.setOutsideTouchable(false);
        pw.showAsDropDown(txt);
        Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btnc, btndec, btndel, btnenter, btnper;
        btn1 = layout.findViewById(R.id.txt1);
        btn2 = layout.findViewById(R.id.txt2);
        btn3 = layout.findViewById(R.id.txt3);
        btn4 = layout.findViewById(R.id.txt4);
        btn5 = layout.findViewById(R.id.txt5);
        btn6 = layout.findViewById(R.id.txt6);
        btn7 = layout.findViewById(R.id.txt7);
        btn8 = layout.findViewById(R.id.txt8);
        btn9 = layout.findViewById(R.id.txt9);
        btn0 = layout.findViewById(R.id.txt0);
        btnc = layout.findViewById(R.id.txtc);
        btndec = layout.findViewById(R.id.txtdec);
        btnenter = layout.findViewById(R.id.txtenter);
        btndel = layout.findViewById(R.id.txtdel);
        btnper = layout.findViewById(R.id.btnpercent);
        TextView txtNum = layout.findViewById(R.id.txtNum);
        btnper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (startOpen == true) {
                    keynum = "";
                    startOpen = false;
                }
                txtNum.setText(keynum + btnper.getText());
                keynum = txtNum.getText().toString();
            }
        });
        txtNum.setText(String.valueOf(keynum));

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startOpen == true) {
                    keynum = "";
                    startOpen = false;
                }
                if (txtNum.getText().equals("0")) {
                    txtNum.setText(btn1.getText());
                    keynum = txtNum.getText().toString();
                } else {
                    txtNum.setText(keynum + btn1.getText());
                    keynum = txtNum.getText().toString();
                }

            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startOpen == true) {
                    keynum = "";
                    startOpen = false;
                }
                txtNum.setText(keynum + btn2.getText());
                keynum = txtNum.getText().toString();
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startOpen == true) {
                    keynum = "";
                    startOpen = false;
                }
                txtNum.setText(keynum + btn3.getText());
                keynum = txtNum.getText().toString();
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startOpen == true) {
                    keynum = "";
                    startOpen = false;
                }
                txtNum.setText(keynum + btn4.getText());
                keynum = txtNum.getText().toString();
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startOpen == true) {
                    keynum = "";
                    startOpen = false;
                }
                txtNum.setText(keynum + btn5.getText());
                keynum = txtNum.getText().toString();
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startOpen == true) {
                    keynum = "";
                    startOpen = false;
                }
                txtNum.setText(keynum + btn6.getText());
                keynum = txtNum.getText().toString();
            }
        });
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startOpen == true) {
                    keynum = "";
                    startOpen = false;
                }
                txtNum.setText(keynum + btn7.getText());
                keynum = txtNum.getText().toString();
            }
        });
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startOpen == true) {
                    keynum = "";
                    startOpen = false;
                }
                txtNum.setText(keynum + btn8.getText());
                keynum = txtNum.getText().toString();
            }
        });
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startOpen == true) {
                    keynum = "";
                    startOpen = false;
                }
                txtNum.setText(keynum + btn9.getText());
                keynum = txtNum.getText().toString();
            }
        });
        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startOpen == true) {
                    keynum = "";
                    startOpen = false;
                }
                txtNum.setText(keynum + btn0.getText());
                keynum = txtNum.getText().toString();
            }
        });
        btnc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startOpen == true) {
                    keynum = "";
                    startOpen = false;
                }
                txtNum.setText("0");
                keynum = txtNum.getText().toString();
            }
        });
        btndec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startOpen == true) {
                    keynum = "";
                    startOpen = false;
                }
                txtNum.setText(keynum + btndec.getText());
                keynum = txtNum.getText().toString();
            }
        });
        btndel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (keynum.length() != 0) {
                    keynum = keynum.substring(0, keynum.length() - 1);
                    txtNum.setText(keynum);

                }
                if (keynum.length() == 0) {
                    keynum = "0";
                    txtNum.setText(keynum);
                }
                startOpen = false;

            }
        });
        btnenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (itemAdapter.this.context.toString().contains("saleorder_entry")) {
                    try {
                        Double check = Double.parseDouble(keynum);
                        if (isqty) {
                            check = check > 0 ? check : 1;
                            saleorder_entry.sd.get(itemposition).setUnit_qty(check);
                            source.setText(String.valueOf(check));
                            saleorder_entry.entrygrid.setAdapter(itemAd);
                            saleorder_entry.entrygrid.setSelection(itemposition);

                            if (frmmain.isusespecialprice == 1) //added by EKK on 13-11-2020
                                getSpecialPrice(itemposition, false);

                            saleorder_entry.getSummary();

                        } else if (isSalePrice) {
                            check = check > 0 ? check : 0;
                            saleorder_entry.sd.get(itemposition).setSale_price(check);
                            source.setText(String.format("%,." + frmmain.price_places + "f", check));
                            Double amt = Double.parseDouble(source.getText().toString()) * saleorder_entry.sd.get(itemposition).getUnit_qty();
                            String numberAsString = String.format("%,." + frmmain.price_places + "f", amt);
                            txtamt.setText(numberAsString);
                            //sale_entry.getSummary();

                        }

                        isqty = false;
                        startOpen = false;
                        pw.dismiss();

                    } catch (Exception e) {
                        keynum = "0";
                        startOpen = true;
                        if (isqty) {
                            keynum = "1";
                        }
                        txtNum.setText(keynum);
//                        AlertDialog.Builder bd = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
//                        bd.setTitle("iStock");
//                        bd.setMessage("Number Format is incompatiable with data type");
//                        bd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                msg.dismiss();
//                            }
//                        });
//                        msg = bd.create();
//                        msg.show();
                        pw.dismiss();
                        isqty = false;
                    }


                } //end saleoreder
                else {
                    try {
                        Double check = Double.parseDouble(keynum);
                        if (isqty) {
                            check = check > 0 ? check : 1;
                            sale_entry.sd.get(itemposition).setUnit_qty(check);
                            source.setText(String.valueOf(check));
                            sale_entry.entrygrid.setAdapter(itemAd);
                            sale_entry.entrygrid.setSelection(itemposition);

                            if (frmmain.isusespecialprice == 1) //added by EKK on 13-11-2020
                                getSpecialPrice(itemposition, true);

                            sale_entry.getSummary();


                        } else if (isSalePrice) {
                            check = check > 0 ? check : 0;
                            sale_entry.sd.get(itemposition).setSale_price(check);
                            source.setText(String.format("%,." + frmmain.price_places + "f", check));
                            Double amt = Double.parseDouble(source.getText().toString()) * sale_entry.sd.get(itemposition).getUnit_qty();
                            String numberAsString = String.format("%,." + frmmain.price_places + "f", amt);
                            txtamt.setText(numberAsString);
                            //sale_entry.getSummary();

                        }

                        isqty = false;
                        startOpen = false;
                        pw.dismiss();

                    } catch (Exception e) {
                        keynum = "0";
                        startOpen = true;
                        if (isqty) {
                            keynum = "1";
                        }
                        txtNum.setText(keynum);
//                        AlertDialog.Builder bd = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
//                        bd.setTitle("iStock");
//                        bd.setMessage("Number Format is incompatiable with data type");
//                        bd.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                msg.dismiss();
//                            }
//                        });
//                        msg = bd.create();
//                        msg.show();
                        pw.dismiss();
                        isqty = false;
                    }
                }
            }
        });


    }

    //added by EKK on  12-11-2020
    private void getSpecialPrice(int itemposition, boolean isSaleEntry) {

        long code;
        int unit_type;

        if (isSaleEntry) {
            code = sale_entry.sd.get(itemposition).getCode();
            unit_type = sale_entry.sd.get(itemposition).getUnit_type();
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
        String pricelevel = "";
        String tmpPriceLevel = "SP";
        boolean isValidQty = false;
        double sale_price = 0;

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

                    if (Double.parseDouble(keynum) >= minqty && Double.parseDouble(keynum) <= maxqty) {
                        tmpPriceLevel = pricelevel;
                        pad = new priceLevelAdapter(context, itemposition, tv4, tv2, txtamt, Integer.parseInt(pricelevel));
                        pad.issaleentry = isSaleEntry;
                        sale_price = pad.getSalePrice(level);
                        isValidQty = true;
                        if (isSaleEntry)
                            sale_entry.sd.get(itemposition).setPriceLevel(level);
                        else
                            saleorder_entry.sd.get(itemposition).setPriceLevel(level);


                    } else {
                        if(!isValidQty){
                            tmpPriceLevel = "SP";
                            isValidQty=true;
                            pad = new priceLevelAdapter(context, itemposition, tv4, tv2, txtamt, 0);
                            pad.issaleentry = isSaleEntry;
                            sale_price = pad.getSalePrice(tmpPriceLevel);

                            if (isSaleEntry)
                                sale_entry.sd.get(itemposition).setPriceLevel(tmpPriceLevel);
                            else
                                saleorder_entry.sd.get(itemposition).setPriceLevel(tmpPriceLevel);
                        }
                    }

                    // Toast.makeText(context,"Price " + sale_price, Toast.LENGTH_LONG).show();
                        if(isValidQty){
                            if (isSaleEntry) {
                                sale_entry.sd.get(itemposition).setSale_price(sale_price);
                                sale_entry.sd.get(itemposition).setDis_price(sale_price);
                            } else {
                                saleorder_entry.sd.get(itemposition).setSale_price(sale_price);
                                saleorder_entry.sd.get(itemposition).setDis_price(sale_price);
                            }

                            double amt = Double.parseDouble(keynum) * sale_price;
                            tv4.setText(String.format("%,." + frmmain.price_places + "f", amt));
                        }


                } while (cursor.moveToNext());
            }
        }


    }

    public void getItemAdpater(itemAdapter itemAdapter) {
        itemAd = itemAdapter;
    }


}

