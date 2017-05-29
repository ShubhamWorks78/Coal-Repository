package com.seals.shubham.coalstorage;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    RadioButton pell,stee,powe;
    Button btn_yes,btn_no,btn_disp;
    RadioGroup rg;
    EditText day,month,year,amt_Coal;
    int radio_btn = -1;

    RequestQueue mQueue;
    private final String url = "https://stooped-modem.000webhostapp.com/Sample/InsertData.php";
    private final String url_fetch = "https://stooped-modem.000webhostapp.com/Sample/fetchData.php";

    RequestQueue fetch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mQueue = Volley.newRequestQueue(MainActivity.this);
        fetch = Volley.newRequestQueue(MainActivity.this);

        btn_yes = (Button)findViewById(R.id.Btn_Yes);
        btn_no = (Button)findViewById(R.id.Btn_No);
        btn_disp = (Button)findViewById(R.id.Btn_Disp);
        rg = (RadioGroup)findViewById(R.id.RadGroup);

        day = (EditText)findViewById(R.id.edit_Day);
        month = (EditText)findViewById(R.id.edit_Month);
        year = (EditText)findViewById(R.id.edit_Year);
        amt_Coal = (EditText)findViewById(R.id.edit_Coal);

        pell = (RadioButton)findViewById(R.id.Rad_btn_Pell);
        stee = (RadioButton)findViewById(R.id.Rad_btn_Steel);
        powe = (RadioButton)findViewById(R.id.Rad_btn_Pow);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radio_btn = checkedId;
            }
        });

        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ab = new AlertDialog.Builder(MainActivity.this);
                ab.setMessage("No Changes Made");
                ab.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        initialise();
                    }
                });
                final AlertDialog dialog = ab.create();
                dialog.show();
                final Button neutral_btn = dialog.getButton(AlertDialog.BUTTON_NEUTRAL);
                LinearLayout.LayoutParams neutralButtonLL = (LinearLayout.LayoutParams)neutral_btn.getLayoutParams();
                neutralButtonLL.gravity = Gravity.CENTER;
                neutral_btn.setLayoutParams(neutralButtonLL);

            }
        });

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String da = day.getText().toString();
                final String mon = month.getText().toString();
                final String yea = year.getText().toString();
                final String amount = amt_Coal.getText().toString();
                boolean ans = true;
                Integer day_a = -1,month_a = -1,year_a = -1;
                if(!da.isEmpty() && !mon.isEmpty() && !yea.isEmpty()){
                    day_a = Integer.parseInt(da);
                    month_a = Integer.parseInt(mon);
                    year_a = Integer.parseInt(yea);
                    if(amount.isEmpty() == true || (pell.isChecked()==false && stee.isChecked()==false && powe.isChecked()==false)){
                        ans = false;
                    }
                }else{
                    ans = false;
                }
                if(ans == false){
                    Toast.makeText(MainActivity.this,"Data Insufficient",Toast.LENGTH_LONG).show();
                }
                else{
                    if(!chck_year(year_a) || !chck_month(month_a) || !chck(day_a,month_a,year_a)){
                        Toast.makeText(MainActivity.this,"Wrong Date Format",Toast.LENGTH_LONG).show();
                    }else{
                        String plnt = "";
                        if(pell.isChecked()){
                            plnt = "Pellet";
                        }
                        else if(powe.isChecked()){
                            plnt = "Power";
                        }
                        else if(stee.isChecked()){
                            plnt = "Steel";
                        }
                        final String value_plant = plnt;
                        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                initialise();
                                Toast.makeText(MainActivity.this,""+response.toString(),Toast.LENGTH_LONG).show();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(MainActivity.this,""+error.toString(),Toast.LENGTH_LONG).show();
                            }
                        }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> map = new HashMap<String, String>();
                                map.put("Day",da);
                                map.put("Month",mon);
                                map.put("Year",yea);
                                map.put("Plant",value_plant);
                                map.put("Amount",amount);
                                return map;
                            }
                        };
                        mQueue.add(request);
                    }
                }

            }
        });

        btn_disp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String da = day.getText().toString();
                final String mon = month.getText().toString();
                final String yea = year.getText().toString();
                boolean ans = true;
                Integer day_a = -1, month_a = -1, year_a = -1;
                if (!da.isEmpty() && !mon.isEmpty() && !yea.isEmpty()) {
                    day_a = Integer.parseInt(da);
                    month_a = Integer.parseInt(mon);
                    year_a = Integer.parseInt(yea);
                    if (pell.isChecked() == false && stee.isChecked() == false && powe.isChecked() == false) {
                        ans = false;
                    }
                } else {
                    ans = false;
                }
                if (ans == false) {
                    Toast.makeText(MainActivity.this, "Data Insufficient", Toast.LENGTH_LONG).show();
                } else {
                    if (!chck_year(year_a) || !chck_month(month_a) || !chck(day_a, month_a, year_a)) {
                        Toast.makeText(MainActivity.this, "Wrong Date Format", Toast.LENGTH_LONG).show();
                    } else {
                        String plnt = "";
                        if (pell.isChecked()) {
                            plnt = "Pellet";
                        } else if (powe.isChecked()) {
                            plnt = "Power";
                        } else if (stee.isChecked()) {
                            plnt = "Steel";
                        }
                        final String value_plant = plnt;
                        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url_fetch, new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                int val = 0;
                                for(int i = 0;i<response.length();i++){
                                    try{
                                        JSONObject object = response.getJSONObject(i);
                                        String day_r = object.getString("Day");
                                        String month_r = object.getString("Month");
                                        String year_r = object.getString("Year");
                                        String plant_r = object.getString("Plant");

                                        if(day_r.equals(da) && month_r.equals(mon) && year_r.equals(yea) && plant_r.equals(value_plant)){
                                            String amount_r = object.getString("Amount");
                                            AlertDialog.Builder ab = new AlertDialog.Builder(MainActivity.this);
                                            String str = "Amount of Coal dumped in " +value_plant+" on "+(da+"/"+mon+"/"+yea)+" was "+amount_r;
                                            ab.setMessage(str);
                                            ab.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                    initialise();
                                                }
                                            });
                                            ab.show();
                                            val = 1;
                                            break;
                                        }
                                    }catch (Exception Ee){
                                        Ee.printStackTrace();
                                    }
                                }
                                if(val == 0){
                                    AlertDialog.Builder ab = new AlertDialog.Builder(MainActivity.this);
                                    ab.setMessage("No data found for given Information");
                                    ab.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            initialise();
                                        }
                                    });
                                    ab.show();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                        fetch.add(jsonArrayRequest);
                    }
                }
            }
        });
    }

    public  void initialise(){
        day.setText(null);
        month.setText(null);
        year.setText(null);
        amt_Coal.setText(null);
        pell.setChecked(false);
        powe.setChecked(false);
        stee.setChecked(false);
    }
    public static boolean chck_year(int year){
        if(year<1900 || year>2017){
            return false;
        }
        return true;
    }

    public static boolean chck_month(int month){
        if(month>=1 && month<=12){
            return true;
        }
        return false;
    }

    public static boolean chck(int day,int month,int year){
        if(month==2){
            boolean leap = chck_leap(year);
            if(leap){
                if(day>29){
                    return false;
                }
                return true;
            }
            else{
                if(day>28){
                    return false;
                }
                return true;
            }
        }
        else{
            if(month==4 || month == 6 || month == 9 || month == 11){
                if(day>30){
                    return false;
                }
                else{
                    return true;
                }
            }
            else{
                if(day>31){
                    return false;
                }
                else{
                    return true;
                }
            }
        }
    }

    public static boolean chck_leap(int year){
        if(year%4==0 && year!= 1900){
            return true;
        }
        return false;
    }
}
