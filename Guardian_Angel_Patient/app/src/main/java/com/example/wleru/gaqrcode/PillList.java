package com.example.wleru.gaqrcode;

import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PillList extends AppCompatActivity {

    StringBuilder html = new StringBuilder();
    String line;
    Map<String, String> map = new HashMap<String, String>();
    private String extra_positionNum;
    private String extra_nidentify;
    private String nid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pill_list);

        //取得前一頁傳過來的Intentt物件
        Intent intent = getIntent();
        nid = intent.getStringExtra("extra_nid");
//        extra_positionNum = intent.getStringExtra("extra_positionNum");
        //extra_nidentify = intent.getStringExtra("extra_nidentify");

        //jsom parsing:圖檔名稱和圖檔實體路徑對照
        map.put("01.jpg", "p01");
        map.put("02.jpg", "p02");
        map.put("03.jpg", "p03");
        map.put("04.jpg", "p04");
        map.put("05.jpg", "p05");
        map.put("06.jpg", "p06");
        map.put("07.jpg", "p07");
        map.put("08.jpg", "p08");
        map.put("09.jpg", "p09");
        map.put("10.jpg", "p10");
        map.put("11.jpg", "p11");
        map.put("12.jpg", "p12");
        map.put("13.jpg", "p13");
        map.put("14.jpg", "p14");
        map.put("15.jpg", "p15");
        map.put("16.jpg", "p16");
        map.put("17.jpg", "p17");
        map.put("18.jpg", "p18");
        map.put("19.jpg", "p19");
        map.put("20.jpg", "p20");
        map.put("21.jpg", "p21");
        map.put("22.jpg", "p22");
        map.put("23.jpg", "p23");
        map.put("24.jpg", "p24");
        map.put("25.jpg", "p25");
        map.put("26.jpg", "p26");
        map.put("27.jpg", "p27");
        map.put("28.jpg", "p28");
        map.put("29.jpg", "p29");
        map.put("30.jpg", "p30");
        map.put("31.jpg", "p31");
        map.put("32.jpg", "p32");
        map.put("33.jpg", "p33");
        map.put("34.jpg", "p34");
        map.put("35.jpg", "p35");
        map.put("36.jpg", "p36");
        map.put("37.jpg", "p37");
        map.put("38.jpg", "p38");
        map.put("39.jpg", "p39");
        map.put("40.jpg", "p40");
        map.put("41.jpg", "p41");
        map.put("42.jpg", "p42");
        map.put("43.jpg", "p43");
        map.put("44.jpg", "p44");
        map.put("45.jpg", "p45");
        map.put("46.jpg", "p46");
        map.put("47.jpg", "p47");
        map.put("48.jpg", "p48");
        map.put("49.jpg", "p49");
        map.put("50.jpg", "p50");

//        TextView tvTitle = (TextView)findViewById(R.id.tvTitle);
        ListView lvPill= (ListView) findViewById(R.id.lvPill);
        StrictMode.ThreadPolicy policy_1 = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy_1);


        //建立Pill物件的List
        final List<Pill> pillList  = new ArrayList<>();
        try {
            URL url = new URL("https://gaapitest.azurewebsites.net/NPGroup?searchnid="+nid);
            URLConnection conn = url.openConnection();
            InputStream streamIn = conn.getInputStream();
            BufferedReader r= new BufferedReader(new InputStreamReader(streamIn));
            while ((line = r.readLine()) != null) {
                html.append(line+"\n");
            }
            JSONArray jsonArray = new JSONArray(html.toString());



//            HashSet hashSet = new HashSet();
//            for(int i=0; i<jsonArray.length() ; i++){
//                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                String tid = jsonObject.getString("tid");
//                hashSet.add(tid);
//            }


            //JSON:抓藥品清單
            for(int i=0; i<jsonArray.length() ; i++){

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String P_photo = jsonObject.getString("P_photo");
                String P_name = jsonObject.getString("P_name");
                String P_number = jsonObject.getString("P_number");
                pillList.add(new Pill (getDrawableId(map.get(P_photo)),P_name,Integer.valueOf(P_number)));

            }

            //JSON:抓用藥時間
//            for(int i=0; i< hashSet.size() ; i++){
//                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                String thour = jsonObject.getString("thour");
//                String tmin = jsonObject.getString("tmin");
//                strMsg +=  thour+":"+tmin+",";
//            }
//            strMsg = strMsg.substring(0,strMsg.length()-1);
//            tvTitle.setText(strMsg);

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }


        lvPill.setAdapter(new PillAdapter(this, pillList));



        lvPill.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Pill pill = (Pill) parent.getItemAtPosition(position);
                ImageView imageView = new ImageView(PillList.this);
                imageView.setImageResource(pill.getPphoto());
                Toast toast = new Toast(PillList.this);
                toast.setView(imageView);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    private class PillAdapter extends BaseAdapter {
        Context context;
        List<Pill> pillList;

        PillAdapter(Context context, List<Pill> pillList) {
            this.context = context;
            this.pillList = pillList;
        }

        @Override
        public int getCount() {
            return pillList.size();
        }

        @Override
        public View getView(int position, View itemView, ViewGroup parent) {
            if (itemView == null) {
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                itemView = layoutInflater.inflate(R.layout.pill_list_item, parent, false);
            }

            Pill pill = pillList.get(position);
            ImageView ivPphoto = (ImageView) itemView
                    .findViewById(R.id.ivPphoto);
            ivPphoto.setImageResource(pill.getPphoto());

            TextView tvName= (TextView) itemView
                    .findViewById(R.id.tvName);
            tvName.setText(String.valueOf(pill.getPname()));

            TextView tvNumber = (TextView) itemView
                    .findViewById(R.id.tvNumber);
            tvNumber.setText(String.valueOf(pill.getPnumber()));
            return itemView;
        }

        @Override
        public Object getItem(int position) {
            return pillList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }
    }

    protected int getDrawableId(String key) {

        try {
            String name = key;
            Field field = R.drawable.class.getField(name);
            return field.getInt(null);
        } catch (SecurityException e) {
        } catch (NoSuchFieldException e) {
        } catch (IllegalAccessException e) {
        }
        return -1;
    }
}
