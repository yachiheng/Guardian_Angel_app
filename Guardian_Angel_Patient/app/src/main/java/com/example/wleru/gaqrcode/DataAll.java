package com.example.wleru.gaqrcode;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import static android.os.StrictMode.setThreadPolicy;

public class DataAll extends AppCompatActivity {

    ListView listView;
    Button btnback;
    String strInfo = "", strId;
    StringBuilder stringBuilder = new StringBuilder();
    private  ArrayList<String> Detail;

    private View.OnClickListener btnback_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(DataAll.this, ActMainMain.class);
            Bundle bundle = new Bundle();
            bundle.putString(CDictionary.BK_OidForIndex, strId);

            intent.putExtras(bundle);
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        InitialComponent();
        Log.i("資訊", "開始");

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        strId = bundle.getString(CDictionary.BK_OidForH);


        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        setThreadPolicy(threadPolicy);

        getData();

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        myAdapter adapter = new myAdapter(Detail,inflater);
        listView.setAdapter(adapter);
        //指定事件 Method


//        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.listitemstyle,Detail);
//
//        listView.setAdapter(adapter);

//        listView.setAdapter(new MyAdapter(this, Detail));





    }


    private void getData() {

        Detail = new ArrayList<String>();

        try {
            URL url = new URL("https://gaapitest.azurewebsites.net/Consulation?searchid=" + strId);
            URLConnection con = url.openConnection();
            con.setConnectTimeout(10000);
            con.setReadTimeout(10000);

            InputStream stream = con.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            String line = "", cdate = "", cname = "", ctime = "", cid = "";

            while ((line = reader.readLine()) != null) {

                stringBuilder.append(line);
                strInfo += (stringBuilder.toString() + "\n");
                Log.i("訊息", strInfo);

                JSONArray jsonArray = new JSONArray(strInfo);

                for (int i = 0; i < jsonArray.length(); i++) {

                    cdate = jsonArray.getJSONObject(i).getString("cdate");
                    cname = jsonArray.getJSONObject(i).getString("cname");
                    ctime = jsonArray.getJSONObject(i).getString("ctime");
                    cid = jsonArray.getJSONObject(i).getString("cid");

                    Log.i("資訊", "加入第" + i + "筆資料");
                    Detail.add("日期 : " + cdate + "\n地點 : " + cname + "\n時間 : " + ctime + "\n診號 : " + cid + "號");
                    Log.i("資訊", Detail.get(i));
                }

                Log.i("資訊", "加入成功");

            }

            reader.close();

        } catch (MalformedURLException | JSONException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    private void InitialComponent() {

        setContentView(R.layout.data_all);
        listView = findViewById(R.id.listItem);
        btnback = findViewById(R.id.btnback);
        btnback.setOnClickListener(btnback_click);
    }

}

