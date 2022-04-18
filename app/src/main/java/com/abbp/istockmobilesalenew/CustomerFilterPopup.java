package com.abbp.istockmobilesalenew;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class CustomerFilterPopup extends AppCompatActivity {
    private AlphabetListAdapter adapter = new AlphabetListAdapter();
    private GestureDetector mGestureDetector;
    private List<Object[]> alphabet = new ArrayList<Object[]>();
    private HashMap<String, Integer> sections = new HashMap<String, Integer>();
    private int sideIndexHeight;
    private static float sideIndexX;
    private static float sideIndexY;
    private int indexListSize;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_filter_popup);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("Customer Info");
        listView=findViewById(R.id.list);
        ImageButton imgSearch=findViewById(R.id.searchcustomer);
        EditText customersearch=findViewById(R.id.customersearch);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        mGestureDetector = new GestureDetector(this, new SideIndexGestureListener());

        final ArrayList<customer> countries = populateCountries();  //to add my collection list
        Collections.sort(countries, new Comparator<customer>() {
            @Override
            public int compare(customer customer, customer t1) {
                return customer.getName().compareTo(t1.getName());
            }
        });

//        String sss="";
//        for(int i=0;i<countries.size();i++){
//            sss+=countries.get(i).getName()+","+countries.get(i).getCustomerid()+","+countries.get(i).getTownshipname()+"\n";
//        }
//        Toast.makeText(getApplicationContext(),sss,Toast.LENGTH_SHORT).show();

//        System.out.println(sss);
        // List sortedcountry=new ArrayList();
        //sortedcountry=Collections.sort(countries);

        List rows = new ArrayList();
        int start = 0;
        int end = 0;
        String previousLetter = null;
        Object[] tmpIndexItem = null;
        Pattern numberPattern = Pattern.compile("[0-9]");

        for (customer country : countries) {
            String firstLetter = country.getName().toString().substring(0, 1);

            // Group numbers together in the scroller
            if (numberPattern.matcher(firstLetter).matches()) {
                firstLetter = "#";
            }

            // If we've changed to a new letter, add the previous letter to the alphabet scroller
            if (previousLetter != null && !firstLetter.equals(previousLetter)) {
                end = rows.size() - 1;
                tmpIndexItem = new Object[3];
                tmpIndexItem[0] = previousLetter/*.toUpperCase(Locale.UK)*/;
                tmpIndexItem[1] = start;
                tmpIndexItem[2] = end;
                alphabet.add(tmpIndexItem);

                start = end + 1;
            }

            // Check if we need to add a header row
            if (!firstLetter.equals(previousLetter)) {
                rows.add(new Section(firstLetter));
                sections.put(firstLetter/*.toUpperCase()*/, start);
            }

            // Add the country to the list
            rows.add(new Item(country.getName()+","+country.getTownshipname()+","+country.getCustomerid()));
            previousLetter = firstLetter;
        }

        if (previousLetter != null) {
            // Save the last letter
            tmpIndexItem = new Object[3];
            tmpIndexItem[0] = previousLetter/*.toUpperCase(Locale.UK)*/;
            tmpIndexItem[1] = start;
            tmpIndexItem[2] = rows.size() - 1;
            alphabet.add(tmpIndexItem);
        }

        adapter.setRows(rows);
        ListView list=findViewById(R.id.list);
        list.setAdapter(adapter);
//        setListAdapter(adapter);

        updateList();


//        EditText edPwd = (EditText)findViewById(R.id.password);
        customersearch.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                customersearch.setInputType(InputType.TYPE_CLASS_TEXT);
                customersearch.requestFocus();

                v.setFocusable(true);
                v.setFocusableInTouchMode(true);

                return false;
            }
        });
        customersearch.setInputType(InputType.TYPE_NULL);
//        customersearch.setFocusable(false);
//        customersearch.setFocusableInTouchMode(false);
        ArrayList<customer> customerArrayList=new ArrayList<>();
        customersearch.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
//                if(!customersearch.getText().toString().isEmpty()) {
//                    List searchcustomer = new ArrayList();
//                    customerArrayList.clear();
//                    for (customer customer : countries
//                    ) {
//
//
//                        if (customer.getName().toLowerCase().contains(s.toString().toLowerCase())) {
//                            //do your work here
//                            Toast.makeText(getApplicationContext(), "this is equal", Toast.LENGTH_SHORT).show();
//                            customerArrayList.add(customer);
//                        }
//                    }
//                    Collections.sort(customerArrayList, new Comparator<customer>() {
//                        @Override
//                        public int compare(customer customer, customer t1) {
//                            return customer.getName().compareTo(t1.getName());
//                        }
//                    });
//                    for (customer customer : customerArrayList) {
//                        searchcustomer.add(new Item(customer.getName() + "," + customer.getTownshipname() + "," + customer.getCustomerid()));
//                    }
//                    adapter.setRows(searchcustomer);
//                    list.setAdapter(adapter);
//                }else {
//                    adapter.setRows(rows);
//                    list.setAdapter(adapter);
//                }
                if(customersearch.getText().toString().isEmpty()){
                    adapter.setRows(rows);
                    list.setAdapter(adapter);
                    findViewById(R.id.sideIndex).setVisibility(View.VISIBLE);
                }
            }



            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void afterTextChanged(Editable s) {

            }

        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                String s = (String)adapterView.getItemAtPosition(i).toString();

//                Cursor member=(Cursor)adapterView.getItemAtPosition(i);




//
                try {
                    TextView textView=view.findViewById(R.id.textView1);
                    TextView textView1=view.findViewById(R.id.customerid);
                    frmCustOutstand.customerid=textView1.getText().toString();
                frmCustOutstand.choosecustomer.setText(textView.getText().toString());
//                    Toast.makeText(getApplicationContext(), textView1.getText().toString()+" "+" "+textView.getText().toString()+" this is ", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                finish();
                // If you want to close the adapter
            }
        });
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputMethodManager= (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);

//                customersearch.setInputType(InputType.TYPE_NULL);
//                customersearch.requestFocus();
//                customersearch.setFocusable(false);
//                customersearch.setFocusableInTouchMode(false);
                if(!customersearch.getText().toString().isEmpty()) {
                    List searchrow = new ArrayList();
                    System.out.println("OKKKKKKKKKKKKKKKKKKKKKK");
                    String customername = customersearch.getText().toString();
//                    Toast.makeText(getApplicationContext(), customername + " this is toast", Toast.LENGTH_SHORT).show();
                    customerArrayList.clear();
                    for (customer customer : countries
                    ) {

                        if (customer.getName().toLowerCase().contains(customername.toLowerCase())) {
                            //do your work here
                            System.out.println("this is equal");
//                            Toast.makeText(getApplicationContext(), "this is equal", Toast.LENGTH_SHORT).show();
                            customerArrayList.add(customer);
                        }
                    }
                    Collections.sort(customerArrayList, new Comparator<customer>() {
                        @Override
                        public int compare(customer customer, customer t1) {
                            return customer.getName().compareTo(t1.getName());
                        }
                    });
                    for (customer customer : customerArrayList) {
                        searchrow.add(new Item(customer.getName() + "," + customer.getTownshipname() + "," + customer.getCustomerid()));
                    }
                    adapter.setRows(searchrow);
                    list.setAdapter(adapter);
                    findViewById(R.id.sideIndex).setVisibility(View.GONE);
                }else {
                    adapter.setRows(rows);
                    list.setAdapter(adapter);
                    findViewById(R.id.sideIndex).setVisibility(View.VISIBLE);
                }
            }
        });
        ImageButton imgClose = findViewById(R.id.imgNochange);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    private ArrayList populateCountries() {
//        List countries = new ArrayList();
        ArrayList<customer> customerArrayList=new ArrayList<>();
        String sql="select customerid,name,townshipname from Customer where isdeleted=0";
        //customerArrayList.clear();
        Cursor cursor=DatabaseHelper.rawQuery(sql);
        if(cursor!=null && cursor.getCount()!=0) {
            while (cursor.moveToNext()) {
                customerArrayList.add(new customer(cursor.getInt(cursor.getColumnIndex("customerid")),cursor.getString(cursor.getColumnIndex("name")),cursor.getString(cursor.getColumnIndex("townshipname"))));

            }
        }
        cursor.close();
//        countries.add("Afghanistan");
//        countries.add("Albania");
//        countries.add("Bahrain");
//        countries.add("Bangladesh");
//        countries.add("Cambodia");
//        countries.add("Cameroon");
//        countries.add("Denmark");
//        countries.add("Djibouti");
//        countries.add("East Timor");
//        countries.add("Ecuador");
//        countries.add("Fiji");
//        countries.add("Finland");
//        countries.add("Gabon");
//        countries.add("Georgia");
//        countries.add("Haiti");
//        countries.add("Holy See");
//        countries.add("Iceland");
//        countries.add("India");
//        countries.add("Jamaica");
//        countries.add("Japan");
//        countries.add("Kazakhstan");
//        countries.add("Kenya");
//        countries.add("Laos");
//        countries.add("Latvia");
//        countries.add("Macau");
//        countries.add("Macedonia");
//        countries.add("Myanmar");
//        countries.add("Namibia");
//        countries.add("Nauru");
//        countries.add("Oman");
//        countries.add("Pakistan");
//        countries.add("Palau");
//        countries.add("Qatar");
//        countries.add("Romania");
//        countries.add("Russia");
//        countries.add("Saint Kitts and Nevis");
//        countries.add("Saint Lucia");
//        countries.add("Taiwan");
//        countries.add("Tajikistan");
//        countries.add("Uganda");
//        countries.add("Ukraine");
//        countries.add("Vanuatu");
//        countries.add("Venezuela");
//        countries.add("Yemen");
//        countries.add("Zambia");
//        countries.add("Zimbabwe");
//        countries.add("0");
//        countries.add("2");
//        countries.add("9");
//        countries.add("မျန်မာ");
//        countries.add("1");
//        countries.add("သန္းတုိး");
        return customerArrayList;
    }

    public void displayListItem() {
        LinearLayout sideIndex = (LinearLayout) findViewById(R.id.sideIndex);
        sideIndexHeight = sideIndex.getHeight();
        // compute number of pixels for every side index item
        double pixelPerIndexItem = (double) sideIndexHeight / indexListSize;

        // compute the item index for given event position belongs to
        int itemPosition = (int) (sideIndexY / pixelPerIndexItem);

        // get the item (we can do it since we know item index)
        if (itemPosition < alphabet.size()) {
            Object[] indexItem = alphabet.get(itemPosition);
            int subitemPosition = sections.get(indexItem[0]);

            //ListView listView = (ListView) findViewById(android.R.id.list);
//            getListView().setSelection(subitemPosition);
            listView.setSelection(subitemPosition);
        }
    }

    @SuppressLint("ResourceAsColor")
    public void updateList() {
        LinearLayout sideIndex = (LinearLayout) findViewById(R.id.sideIndex);
        sideIndex.removeAllViews();
        indexListSize = alphabet.size();
        if (indexListSize < 1) {
            return;
        }

        int indexMaxSize = (int) Math.floor(sideIndex.getHeight() / 20);
        int tmpIndexListSize = indexListSize;
        while (tmpIndexListSize > indexMaxSize) {
            tmpIndexListSize = tmpIndexListSize / 2;
        }
        double delta;
        if (tmpIndexListSize > 0) {
            delta = indexListSize / tmpIndexListSize;
        } else {
            delta = 1;
        }

        TextView tmpTV;
        for (double i = 1; i <= indexListSize; i = i+ delta) {
            Object[] tmpIndexItem = alphabet.get((int) i - 1);
            String tmpLetter = tmpIndexItem[0].toString();

            tmpTV = new TextView(this);
//            if(i%2==0){
//                tmpTV.setText(".");
//            }else {
                tmpTV.setText(tmpLetter);
//            }

            tmpTV.setGravity(Gravity.CENTER);
            tmpTV.setTextColor(R.color.black);
            tmpTV.setTextSize(15);
            tmpTV.setPaddingRelative(0,0,0,15);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
            tmpTV.setLayoutParams(params);
            sideIndex.addView(tmpTV);
        }

        sideIndexHeight = sideIndex.getHeight();

        sideIndex.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // now you know coordinates of touch
                sideIndexX = event.getX();
                sideIndexY = event.getY();

                // and can display a proper item it country list
                displayListItem();

                return false;
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mGestureDetector.onTouchEvent(event)) {
            return true;
        } else {
            return false;
        }
    }

    public class SideIndexGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            // we know already coordinates of first touch
            // we know as well a scroll distance
            sideIndexX = sideIndexX - distanceX;
            sideIndexY = sideIndexY - distanceY;

            // when the user scrolls within our side index
            // we can show for every position in it a proper
            // item in the country list
            if (sideIndexX >= 0 && sideIndexY >= 0) {
                displayListItem();
            }

            return super.onScroll(e1, e2, distanceX, distanceY);
        }
    }
}
