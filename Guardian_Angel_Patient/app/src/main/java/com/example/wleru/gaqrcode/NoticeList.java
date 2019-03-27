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
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NoticeList extends AppCompatActivity {

    String strMsg = "";
    String line,nid,oid;
    TextView tvName;
    TextView tvTime;
    StringBuilder html = new StringBuilder();
    Map<Integer, String> map = new HashMap<Integer, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice_list);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        oid = bundle.getString(CDictionary.BK_OidForM);


        ListView lvNotice= (ListView) findViewById(R.id.lvNotice);

        StrictMode.ThreadPolicy policy_1 = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy_1);

        final List<Notice> noticeList  = new ArrayList<>();

        try {
            URL url = new URL("https://gaapitest.azurewebsites.net/Tablet?searchid="+oid);
//            URL url = new URL("https://gaapitest.azurewebsites.net/Tablet?searchid="+3);
            URLConnection conn = url.openConnection();
            InputStream streamIn = conn.getInputStream();
            BufferedReader r= new BufferedReader(new InputStreamReader(streamIn));
            while ((line = r.readLine()) != null) {
                html.append(line+"\n");
            }
            JSONArray jsonArray = new JSONArray(html.toString());

//            HashSet hashSet = new HashSet();
//            int i;
//            for(i=0; i<jsonArray.length() ; i++){
//                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                String T_id = jsonObject.getString("T_id");
//                map.put(i,T_id);
//            }
//           test0215.setText(map.get(i));


//            for(int i=0; i<hashSet.size() ; i++){
//                map.put(,i);
//            }

            //抓N_id
            for(int i=0; i<jsonArray.length() ; i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String N_id = jsonObject.getString("N_id");
                map.put(i,N_id);
            }



            //抓用藥提醒清單
            for(int k=0; k<jsonArray.length() ; k++){
                JSONObject jsonObject = jsonArray.getJSONObject(k);
                String N_name = jsonObject.getString("N_name");
                String T_hour = jsonObject.getString("T_hour");
                String T_minute = jsonObject.getString("T_minute");
                noticeList.add(new Notice (N_name,T_hour,T_minute));
            }

            lvNotice.setAdapter(new NoticeAdapter(this, noticeList));
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        lvNotice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                nid = map.get(position);
                //test0215.setText(String.valueOf(nid));

//                int extra_nidentify = nid;
                Intent intent = new Intent(NoticeList.this,PillList.class);
                intent.putExtra("extra_nid",nid);
//                intent.putExtra("extra_nidentify",extra_nidentify);
                startActivity(intent);
            }
        });
    }

    private class NoticeAdapter extends BaseAdapter {
        Context context;
        List<Notice> pillList;

        NoticeAdapter(Context context, List<Notice> pillList) {
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
                itemView = layoutInflater.inflate(R.layout.notice_list_item, parent, false);
            }

            Notice notice = pillList.get(position);

            tvName= (TextView) itemView
                    .findViewById(R.id.tvNoticeName);
            tvName.setText(String.valueOf(notice.getNname()));

            tvTime = (TextView) itemView
                    .findViewById(R.id.tvNoticeTime);
            tvTime.setText(String.valueOf(notice.getNthour()+":"+notice.getNtmin()));
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


}
