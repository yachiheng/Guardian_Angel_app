package com.example.wleru.gaqrcode;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static android.os.StrictMode.setThreadPolicy;

public class ActIndex extends AppCompatActivity {

    String data = "", strMessageApi = "", strMsg = "";
    StringBuilder stringBuilder = new StringBuilder();
    MTFactory factory = new MTFactory();
    HTFactory htFactory = new HTFactory();
    String[] arrayMessage;

    int position = 0;
    Integer[] arrayPosition;

    private View.OnClickListener btnPhone_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //撥打電話


        }
    };
    private View.OnClickListener btnTime_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String[] arrayHT = new String[htFactory.GetAll().length];
            int count = 0;
            for (HT ht : htFactory.GetAll()) {
                arrayHT[count] = ht.getDate() + "：" + ht.getDepartment() + "　" + ht.getNumber() + "號";
                count++;
            }

            Intent intent = new Intent(ActIndex.this, TimeMedication.class);
            Bundle bundle = new Bundle();
            bundle.putString(CDictionary.BK_OidForM, data);

            intent.putExtras(bundle);
            startActivity(intent);
        }
    };
    private View.OnClickListener btnRemind_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String[] arrayMT = new String[factory.GetAll().length];
            int count = 0;
            for (MT mt : factory.GetAll()) {
                arrayMT[count] = mt.getName() + "/" + mt.getTimeH() + ":" + mt.getTimeM();
                count++;
            }

            Intent intent = new Intent(ActIndex.this, TimeMedication.class);
            Bundle bundle = new Bundle();
            bundle.putString(CDictionary.BK_OidForM, data);

            intent.putExtras(bundle);
            startActivity(intent);
        }
    };
    private View.OnClickListener btnPre_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MoveToPre();
            btnMessage.setText(arrayMessage[position]);
        }
    };
    private View.OnClickListener btnNext_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MoveToNext();
            btnMessage.setText(arrayMessage[position]);
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 1, "資料維護");
        menu.add(0, 1, 2, "登出");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 0) {
            //資料維護
            //Intent intent=new Intent(ActIndex.this,OldJSON.class);
            //startActivity(intent);
        } else if (item.getItemId() == 1) {
            //登出
            Intent intent = new Intent(ActIndex.this, ActMain.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actindex);
        initial();

        StrictMode.ThreadPolicy l_policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        setThreadPolicy(l_policy);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        data = bundle.getString(CDictionary.BK_OidForIndex);

        messageJson(); //小語讀取
        position = 0;
        btnMessage.setText(arrayMessage[position]);
/*
        new Thread() {
            @Override
            public void run() {


                StringBuilder stringBuilder = new StringBuilder();
                try {
                    URL url = new URL("https://apitest20190129014724.azurewebsites.net/Oldman?searchid=1");
                    URLConnection conn = url.openConnection();
                    InputStream streamIn = conn.getInputStream();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(streamIn));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    Log.i("DEMO", stringBuilder.toString());

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                try {
                    JSONObject person = new JSONObject(stringBuilder.toString());
                    JSONArray inArray = person.getJSONArray("inf");
                    for (int i = 0; i < inArray.length(); i++) {
                        JSONObject inf_Array = inArray.getJSONObject(i);
                        stringBuilder.append("O_id:" + inf_Array.getString("O_name"));
//                        arrayAdapter.add(mLocations[i][0]);
//                        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                        spnLocationer.setAdapter(arrayAdapter);
//                        spnLocationer.setOnItemSelectedListener(updateMapLocation());
                        Log.i("DEMO", stringBuilder.toString());
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }.start();
        */
    }




    private void messageJson() {

        /////////抓資料///////////

        try {
            URL url = new URL("https://apitest20190212102438.azurewebsites.net/Message?searchid=" + data);
            URLConnection conn = url.openConnection();
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);

            InputStream streamIn = conn.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(streamIn));
            ///
            Log.i("DEMO", stringBuilder.toString());
            ///
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                strMsg += (stringBuilder.toString() + "\n");
                Log.i("DEMO1", strMsg);

                JSONArray jsonArray = new JSONArray(strMsg);
                Integer intJson = jsonArray.length();

                String[] id = new String[intJson];
                String[] mid = new String[jsonArray.length()];
                String[] mmessage = new String[intJson];
                String[] mdate = new String[intJson];
                String[] photo = new String[intJson];

                for (int i = 0; i < intJson; i++) {
                    id[i] = jsonArray.getJSONObject(i).getString("id");
                    mid[i] = jsonArray.getJSONObject(i).getString("mid");
                    mmessage[i] = jsonArray.getJSONObject(i).getString("mmessage");
                    mdate[i] = jsonArray.getJSONObject(i).getString("mdate");
                    photo[i] = jsonArray.getJSONObject(i).getString("photo");

                    arrayMessage = new String[]{mmessage[i]};
                    arrayPosition = new Integer[]{Integer.valueOf(mid[i])};
                    Log.i("老人id:", jsonArray.getJSONObject(i).getString("id") + "," +
                            "訊息id:" + jsonArray.getJSONObject(i).getString("mid") + "," +
                            "訊息:" + jsonArray.getJSONObject(i).getString("mmessage") + "," +
                            "日期:" + jsonArray.getJSONObject(i).getString("mdate") + "," +
                            "照片:" + jsonArray.getJSONObject(i).getString("photo") + "....."+
                            arrayPosition[i]);
                }
            }

            reader.close();

        } catch (MalformedURLException | JSONException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public void MoveToPre(){
        position--;
        if(position<0) {
            position = 0;
            AlertDialog.Builder builder = new AlertDialog.Builder(ActIndex.this);
            builder.setTitle("已經是第一筆");
            builder.setPositiveButton("OK",null);Dialog message = builder.create();
            message.show();
        }
    }

    public void MoveToNext(){
        position++;
        if(position>arrayPosition.length) {
            position = arrayPosition.length;
            AlertDialog.Builder builder = new AlertDialog.Builder(ActIndex.this);
            builder.setTitle("已經是最後一筆");
            builder.setPositiveButton("OK", null);
            Dialog message = builder.create();
            message.show();
        }
    }



    private void initial() {
        btnMessage = findViewById(R.id.btnMessage);
        btnRemind = findViewById(R.id.btnRemind);
        btnRemind.setOnClickListener(btnRemind_click);
        btnTime = findViewById(R.id.btnTime);
        btnTime.setOnClickListener(btnTime_click);
        btnPhone = findViewById(R.id.btnPhone);
        btnPhone.setOnClickListener(btnPhone_click);
        btnPre = findViewById(R.id.btnPre);
        btnPre.setOnClickListener(btnPre_click);
        btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(btnNext_click);

        factory.MoveToFirst();
        MT data=factory.GetCurrent();
        DisplayCustomerInfo(data);

        htFactory.MoveToFirst();
        HT hData = htFactory.GetCurrent();
        DisplayHTInfo(hData);

    }

    private void DisplayHTInfo(HT p) {
        btnTime.setText(p.getDate() + p.getDepartment() + "回診");
    }

    private void DisplayCustomerInfo(MT p) {
        btnRemind.setText(p.getTimeH() + ":" + p.getTimeM()
                + "吃藥(" + p.getName() + ")");
    }

    /*
    @Override //list選出的資料
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CDictionary.ACTID_ACTLIST)
        {
            if(data==null ||data.getExtras()==null)
                return;
            int index=data.getExtras().getInt(CDictionary.BK_SELECTED_INDEX);
            factory.MoveTo(index);
            DisplayCustomerInfo(factory.GetCurrent());
        }
    }
   */

    Button btnMessage, btnRemind, btnTime, btnPhone, btnPre, btnNext;
}
