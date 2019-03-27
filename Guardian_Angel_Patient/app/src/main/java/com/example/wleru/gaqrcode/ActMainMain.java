package com.example.wleru.gaqrcode;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.os.StrictMode.setThreadPolicy;

public class ActMainMain extends AppCompatActivity {

    String data = "", strMessageApi = "", strMsg = "", strMsgh = "",strMsgm = "",
            strMsgP = "",strId="", strWhatstoday = "", strName;
    StringBuilder stringBuilder = new StringBuilder();
    StringBuilder stringBuilderh = new StringBuilder(); //回診
    StringBuilder stringBuilderm = new StringBuilder(); //用藥
    StringBuilder stringBuilderp = new StringBuilder(); //緊急聯絡人
    MTFactory factory = new MTFactory();
    HTFactory htFactory = new HTFactory();
    ArrayList<String> arrayName = new ArrayList<>(); //老人姓名
    ArrayList<String> arrayMessage = new ArrayList(); //溫馨小語
    ArrayList<String> arrayHDate = new ArrayList<>(); //回診日期
    ArrayList<String> arrayHName = new ArrayList<>(); //回診院所
    ArrayList<String> arrayHId = new ArrayList<>(); //回診診號
    ArrayList<String> arrayMDateS = new ArrayList<>(); //用藥起日
    ArrayList<String> arrayMDateE = new ArrayList<>(); //用藥迄日
    ArrayList<String> arrayMHour = new ArrayList<>(); //用藥時
    ArrayList<String> arrayMMin = new ArrayList<>(); //用藥分
    ArrayList<String> arrayMName = new ArrayList<>(); //用藥包
    ArrayList<String> arrayPId = new ArrayList<>(); //緊急聯絡人id
    ArrayList<String> arrayPname = new ArrayList<>(); //緊急聯絡人名稱
    ArrayList<String> arrayPphone = new ArrayList<>(); //緊急聯絡人電話
    ArrayList<String> arrayWhatstoday = new ArrayList<>(); //星期判斷
    ArrayList<String> arrayWhatstoday2 = new ArrayList<>(); //星期判斷-回
    TextView textView;

    int position = 0;
    Integer[] arrayPosition;
    private boolean isTodayH;
    private View.OnClickListener btnUser_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent=new Intent(ActMainMain.this,Fall_Location_Activity.class);

            Bundle bundle = new Bundle();
            bundle.putString(CDictionary.BK_OidForLocation, strId);
            intent.putExtras(bundle);

            startActivity(intent);

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 1, "位置");
        menu.add(0, 1, 2, "登出");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 0) {
            //位置
           /*
            Intent intent=new Intent(ActMainMain.this,Fall_Location_Activity.class);

            Bundle bundle = new Bundle();
            bundle.putString(CDictionary.BK_OidForLocation, strId);
            intent.putExtras(bundle);

            startActivity(intent);
            */
        } else if (item.getItemId() == 1) {
            //登出
            Intent intent = new Intent(ActMainMain.this, ActMain.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private View.OnClickListener btnRemind_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ////////用藥提醒////////

            Intent intent = new Intent(ActMainMain.this, NoticeList.class);
            Bundle bundle = new Bundle();
            bundle.putString(CDictionary.BK_OidForM, strId);
//            bundle.putString(CDictionary.BK_OidForH, "用藥提醒測試頁");

            intent.putExtras(bundle);
            startActivity(intent);
        }
    };
    private View.OnClickListener btnTime_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ////////回診通知////////

            Intent intent = new Intent(ActMainMain.this, DataAll.class);
            Bundle bundle = new Bundle();
            bundle.putString(CDictionary.BK_OidForH, strId);
         //   bundle.putString(CDictionary.BK_OidForH, "回診時間測試頁");

            intent.putExtras(bundle);
            startActivity(intent);
        }



    };
    private View.OnClickListener btnPhone_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ////////緊急聯絡人////////

            Intent intent = new Intent(Intent.ACTION_CALL); //撥打管理員
            //Uri data = Uri.parse("tel:" + arrayPphone.get(0));
            Uri data = Uri.parse("tel:" + "0958620158");
            intent.setData(data);
            startActivity(intent);//撥出電話
            sendEvent(strId);//sendEvent

        }

        private void sendEvent(String strId) {

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd   HH:mm:ss");
            Date curDate = new Date(System.currentTimeMillis());
            String time = formatter.format(curDate);
            StrictMode.ThreadPolicy l_policy =  new StrictMode.ThreadPolicy.Builder().permitAll().build();
            setThreadPolicy(l_policy);

            JSONArray ja=new JSONArray();
            try {

                JSONObject ob = new JSONObject();
                try {
                    ob.put("A_style","1");
                    ob.put("A_date",time);
                    ob.put("A_status", "1");
                    ob.put("O_id",strId);
                    ja.put(ob);
                    Log.i("INFO","ja:"+ja.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String api= "https://insertgps.azurewebsites.net/Accident/insert?json=";
                api+=ja.toString();


                URL url=new URL(api);
                URLConnection conn=url.openConnection();
                InputStream streamIn=conn.getInputStream();

                BufferedReader r = new BufferedReader(new InputStreamReader(streamIn));
                StringBuilder html = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null) {
                    html.append(line);
                    Log.i("INFO","Html:"+html.toString());

                }
                Log.i("INFO","json:"+html.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    };
    private View.OnClickListener btnPre_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ////////溫馨小語上一筆////////

            MoveToPre();
            btnMessage.setText(arrayMessage.get(position));
        }
    };

    private void MoveToPre() {

        AlertDialog.Builder builder = new AlertDialog.Builder(ActMainMain.this);

        position--;
        if(position<0) {
            position = 0;
            builder.setTitle("已經是第一筆");
            builder.setPositiveButton("OK",null);Dialog message = builder.create();
            message.show();
        }
    }

    private View.OnClickListener btnNext_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ////////溫馨小語下一筆////////

            MoveToNext();
            btnMessage.setText(arrayMessage.get(position));
        }
    };

    private void MoveToNext() {


        AlertDialog.Builder builder = new AlertDialog.Builder(ActMainMain.this);

        position++;

        if(position >= arrayMessage.size()) {
            position = (arrayMessage.size() - 1);
            builder.setTitle("已經是最後一筆");
            builder.setPositiveButton("OK",null);Dialog message = builder.create();
            message.show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actmainmain);
        initial();

        StrictMode.ThreadPolicy l_policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        setThreadPolicy(l_policy);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        strId = bundle.getString(CDictionary.BK_OidForIndex);

        phoneJson();

        messageJson(); //小語讀取
  Log.i("訊息有多長呀呀呀",String.valueOf(arrayMessage.size()));
        position = 0;
        btnMessage.setText(arrayMessage.get(position));

        hJson(); //回診時間讀取
        //position = 0;

        //Log.i("唉呀0221",arrayWhatstoday2.get(0));

       // if(arrayWhatstoday2.get(0).equals("0"))
        //    btnTime.setText("今天" + arrayHDate.get(0) + "\n\n" + arrayHName.get(0) + "\n\n" + arrayHId.get(0) + "號");
     //   else
      //      //if(arrayWhatstoday2.get(0) == 1)
            btnTime.setText(arrayHDate.get(0) + "\n\n" + arrayHName.get(0) + "\n\n" + arrayHId.get(0) + "號");

        mJson(); //用藥提醒讀取
        btnRemind.setText(arrayWhatstoday.get(0) +
                "\n\n" + arrayMHour.get(0) + "：" + arrayMMin.get(0) + "\n\n" + arrayMName.get(0));

        phoneJson();  //取緊急聯絡人電話
     //   Toast.makeText(this,"會打電話喔" + arrayPphone.get(0),Toast.LENGTH_LONG).show();

        //老人名您好
        btnUser.setText(arrayName.get(0) + " 您好");
    }

    private void timeKuraberu() {
        /////時間比較/////
        Calendar mCal = Calendar.getInstance();
        CharSequence s = DateFormat.format("yyyy/MM/dd", mCal.getTime());    // kk:24小時制, hh:12小時制

        SimpleDateFormat timeKuraberu = new SimpleDateFormat("yyyy/MM/dd");
        Date dToday = null;
        Date dMS = null;
        Date dME = null;

        try {
            dToday = timeKuraberu.parse((String) s);
            dMS = timeKuraberu.parse(arrayMDateS.get(0));
            dME = timeKuraberu.parse(arrayMDateE.get(0));
        } catch (ParseException e) {
            e.printStackTrace();

        }

        if ( (dMS.getTime() <= dToday.getTime()) && (dME.getTime() >= dToday.getTime())) {
            strWhatstoday = "今天";
        }
    }

    private void phoneJson() {

        /////////抓資料-緊急聯絡人電話///////////
        try {

            URL url = new URL("https://gaapitest.azurewebsites.net/emcontact?searchid=" + strId);

            URLConnection conn = url.openConnection();
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            InputStream streamIn = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(streamIn));

            String line;
            int count=0;
            while ((line = reader.readLine()) != null) {

                stringBuilderp.append(line);
                strMsgP += (stringBuilderp.toString() + "\n");

                JSONArray jsonArray = new JSONArray(strMsgP);
                Integer intJson = jsonArray.length();
////////oid改eid,oname改ename,ophone改ephone//////////////
                String[] E_name = new String[intJson];
                String[] E_phone = new String[intJson];
                count++;

                for (int i = 0; i < intJson; i++) {
                    E_name[i] = jsonArray.getJSONObject(i).getString("E_name");
                    E_phone[i] = jsonArray.getJSONObject(i).getString("E_phone");

                    arrayPname.add(E_name[i]);
                    arrayPphone.add(E_phone[i]);
                    Log.i("成功-last",E_name[i]);
                }
            }

            reader.close();

        } catch (MalformedURLException | JSONException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private void mJson() {

        /////////抓資料-用藥提醒///////////
        try {

/////////////比時間///////////////////
            Calendar mCal = Calendar.getInstance();
            CharSequence s = DateFormat.format("yyyy/MM/dd", mCal.getTime());    // kk:24小時制, hh:12小時制

            CharSequence s2 = DateFormat.format("kk", mCal.getTime());    // kk:24小時制, hh:12小時制
            CharSequence s3 = DateFormat.format("mm", mCal.getTime());    // kk:24小時制, hh:12小時制

            SimpleDateFormat timeKuraberu = new SimpleDateFormat("yyyy/MM/dd");
            SimpleDateFormat timeKuraberu2 = new SimpleDateFormat("kk");
            SimpleDateFormat timeKuraberu3 = new SimpleDateFormat("mm");
            Date dToday = null;
            Date tTodayH = null;
            Date tTodayM = null;
            Date dMS = null;
            Date dME = null;
            Date tMH = null; //時間時
            Date tMM = null; //時間分

/////////////比時間///////////////////


            URL url = new URL("https://gaapitest.azurewebsites.net/UseMedicine?searchid=" + strId);

            URLConnection conn = url.openConnection();
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            InputStream streamIn = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(streamIn));

            String line;
            int count=0;
            while ((line = reader.readLine()) != null) {

                stringBuilderm.append(line);
                strMsgm += (stringBuilderm.toString() + "\n");

                JSONArray jsonArray = new JSONArray(strMsgm);
                Integer intJson = jsonArray.length();

                String[] oid = new String[intJson];
                String[] thour = new String[intJson];
                String[] tmin = new String[intJson];
                String[] nname = new String[intJson];
                String[] nstartdate = new String[intJson];
                String[] nenddate = new String[intJson];
                String[] T_hour = new String[intJson];
                String[] T_minute = new String[intJson];

                count++;

                for (int i = 0; i < intJson; i++) {

                    Log.i("訊息第幾筆1",String.valueOf(i));

                    oid[i] = jsonArray.getJSONObject(i).getString("O_id");
                    thour[i] = jsonArray.getJSONObject(i).getString("T_hour");
                    tmin[i] = jsonArray.getJSONObject(i).getString("T_minute");
                    nname[i] = jsonArray.getJSONObject(i).getString("N_name");
                    nstartdate[i] = jsonArray.getJSONObject(i).getString("N_startdate");
                    nenddate[i] = jsonArray.getJSONObject(i).getString("N_enddate");
                    T_hour[i] = jsonArray.getJSONObject(i).getString("T_hour");
                    T_minute[i] = jsonArray.getJSONObject(i).getString("T_minute");



                    try {
                        dToday = timeKuraberu.parse((String) s);
                        tTodayH = timeKuraberu2.parse((String) s2);
                        tTodayM = timeKuraberu3.parse((String) s3);
                        dMS = timeKuraberu.parse(nstartdate[i]);
                        dME = timeKuraberu.parse(nenddate[i]);
                        tMH = timeKuraberu2.parse(T_hour[i]);
                        tMM = timeKuraberu2.parse(T_minute[i]);
                    } catch (ParseException e) {
                        e.printStackTrace();

                    }
                    Log.i("哈囉","有嗎");

                    Log.i("哈囉",String.valueOf(tMH.getTime()));
                    Log.i("哈囉",String.valueOf(tMM.getTime()));
                    Log.i("哈囉",String.valueOf(tTodayH.getTime()));

                    Log.i("哈囉0221",String.valueOf(tMH.getTime()));

                    if ( (dMS.getTime() <= dToday.getTime()) && (dME.getTime() >= dToday.getTime())
                       // &&
                            //(
                         //           (tMH.getTime() >= tTodayH.getTime())
                         //&& (tMM.getTime() >= tTodayM.getTime()) )
                    ) {
                        arrayMHour.add(thour[i]);
                        arrayMMin.add(tmin[i]);
                        arrayMName.add(nname[i]);
                        arrayMDateS.add(nstartdate[i]);
                        arrayMDateE.add(nenddate[i]);

                        Log.i("唉呀-今",arrayMDateS.get(i));

                        arrayWhatstoday.add("今天");
                    } else if (dMS.getTime() > dToday.getTime()){
                            arrayMHour.add(thour[i]);
                            arrayMMin.add(tmin[i]);
                            arrayMName.add(nname[i]);
                            arrayMDateS.add(nstartdate[i]);
                            arrayMDateE.add(nenddate[i]);
                            if(strWhatstoday==null)
                                arrayWhatstoday.add("明天");
                    }
                }
            }

            reader.close();

        } catch (MalformedURLException | JSONException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    private void hJson() {

        /////////抓資料-回診時間///////////
        try {

            /////////////比時間///////////////////
            Calendar mCal = Calendar.getInstance();
            CharSequence s = DateFormat.format("yyyy/MM/dd", mCal.getTime());    // kk:24小時制, hh:12小時制

            SimpleDateFormat timeKuraberu = new SimpleDateFormat("yyyy/MM/dd");

            Date dToday = null;
            Date dHdate = null;

/////////////比時間///////////////////


            URL url = new URL("https://gaapitest.azurewebsites.net/Consulation?searchid=" + strId);

            URLConnection conn = url.openConnection();
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            InputStream streamIn = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(streamIn));

            String line;
            int count=0;
            while ((line = reader.readLine()) != null) {

                stringBuilderh.append(line);
                strMsgh += (stringBuilderh.toString() + "\n");

                JSONArray jsonArray = new JSONArray(strMsgh);
                Integer intJson = jsonArray.length();

                String[] oid = new String[intJson];
                String[] cid = new String[intJson];
                String[] cdate = new String[intJson];
                String[] cname = new String[intJson];

                count++;

                for (int i = 0; i < intJson; i++) {

                    oid[i] = jsonArray.getJSONObject(i).getString("oid");
                    cid[i] = jsonArray.getJSONObject(i).getString("cid");
                    cdate[i] = jsonArray.getJSONObject(i).getString("cdate");
                    cname[i] = jsonArray.getJSONObject(i).getString("cname");


                    try {
                        dToday = timeKuraberu.parse((String) s);
                        dHdate = timeKuraberu.parse(cdate[i]);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Log.i("唉呀today",String.valueOf(dToday));
                    Log.i("唉呀cdate",String.valueOf(dHdate));

                 //   if ( (dHdate.getTime() == dToday.getTime())) {
                        Log.i("唉呀..............",String.valueOf(dHdate));
                        arrayHDate.add(cdate[i]);
                        arrayHName.add(cname[i]);
                        arrayHId.add(cid[i]);

                     //   arrayWhatstoday2.add("0"); //0=今天
                 // }
                    /*
                    else{

                        arrayHDate.add(cdate[i]);
                        arrayHName.add(cname[i]);
                        arrayHId.add(cid[i]);

                        arrayWhatstoday2.add(1);
                    }
                    */
                    if ( (dHdate.getTime() == dToday.getTime())){
                        Log.i("hello","yes!!");
                    }
                }
            }

            reader.close();

        } catch (MalformedURLException | JSONException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    private void messageJson() {
        /////////抓資料///////////

        try {
            URL url = new URL("https://gaapitest.azurewebsites.net/Message?searchid=" + strId);
            URLConnection conn = url.openConnection();
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);

            InputStream streamIn = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(streamIn));

            String line;
            int count=0;
            while ((line = reader.readLine()) != null) {

                stringBuilder.append(line);
                strMsg += (stringBuilder.toString() + "\n");
                Log.i("訊息strMsg-抓到的all", strMsg);

                JSONArray jsonArray = new JSONArray(strMsg);
                Integer intJson = jsonArray.length();

                String[] id = new String[intJson];
                String[] mid = new String[intJson];
                String[] mmessage = new String[intJson];
                String[] mdate = new String[intJson];
                String[] photo = new String[intJson];
                String[] name = new String[intJson];

                count++;

                Log.i("訊息-count第幾次", String.valueOf(count));
                Log.i("訊息-intJson有多長", String.valueOf(intJson));

                for (int i = 0; i < intJson; i++) {

                    Log.i("訊息第幾筆1",String.valueOf(i));

                    id[i] = jsonArray.getJSONObject(i).getString("id");
                    mid[i] = jsonArray.getJSONObject(i).getString("mid");
                    mmessage[i] = jsonArray.getJSONObject(i).getString("mmessage");
                    mdate[i] = jsonArray.getJSONObject(i).getString("mdate");
                    photo[i] = jsonArray.getJSONObject(i).getString("photo");
                    name[i] = jsonArray.getJSONObject(i).getString("name");

                    arrayMessage.add(mmessage[i]);
                    arrayName.add(name[i]);
                }
            }

            reader.close();

        } catch (MalformedURLException | JSONException e) {
           e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

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
        btnUser = findViewById(R.id.btnUser);
        btnUser.setOnClickListener(btnUser_click);
    }


    Button btnMessage, btnRemind, btnTime, btnPhone, btnPre, btnNext, btnUser;
}
