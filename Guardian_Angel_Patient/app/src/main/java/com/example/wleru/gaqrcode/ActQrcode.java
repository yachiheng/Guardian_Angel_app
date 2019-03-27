package com.example.wleru.gaqrcode;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import android.text.format.DateFormat;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.os.StrictMode.setThreadPolicy;

public class ActQrcode extends Activity {

    String strMessageApi = "";
    StringBuilder stringBuilder = new StringBuilder();
    String[] arrayName, arrayQrid, arrayId;

    String api = "", strMsg = "", strTemp = "", data = "",strId="";
    boolean isOK = false;

    private View.OnClickListener btnEnter_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if(isOK==true) {
                Intent intent = new Intent(ActQrcode.this, ActMainMain.class);
                Bundle bundle = new Bundle();
                bundle.putString(CDictionary.BK_OidForIndex, strId);

                intent.putExtras(bundle);
                startActivity(intent);
 Log.i("訊息","順利跳頁囉");
            }
            else if(isOK==false){
                Intent intent = new Intent(ActQrcode.this, ActMain.class);
                startActivity(intent);

   Log.i("訊息","失敗回去吧");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actqrcode);

        StrictMode.ThreadPolicy l_policy =  new StrictMode.ThreadPolicy.Builder().permitAll().build();
        setThreadPolicy(l_policy);


        btnOld = findViewById(R.id.btnOld);
       // btnOld.setText("符合");
        btnEnter = findViewById(R.id.btnEnter);
        btnEnter.setOnClickListener(btnEnter_click);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        data = bundle.getString(CDictionary.QRID);
        QrIsOk();

        Enter();
    }

    private void Enter() {

        if (isOK == true) {
            Intent intent = new Intent(ActQrcode.this, ActMainMain.class);
            Bundle bundle = new Bundle();
            bundle.putString(CDictionary.BK_OidForIndex, strId);

            intent.putExtras(bundle);
            startActivity(intent);
            Log.i("訊息", "順利跳頁囉");
        } else if (isOK == false) {
            Intent intent = new Intent(ActQrcode.this, ActMain.class);
            startActivity(intent);


        }
    }

    private void QrIsOk() {

        /////////抓資料///////////
        try {
            //待改api
            URL url = new URL("https://gaapitest.azurewebsites.net/Qrcode?searchqrid=" + data);
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
                String[] qrid = new String[intJson];
                String[] name = new String[intJson];

                for (int i = 0; i < intJson; i++) {
                    id[i] = jsonArray.getJSONObject(i).getString("id");
                    qrid[i] = jsonArray.getJSONObject(i).getString("qrid");
                    name[i] = jsonArray.getJSONObject(i).getString("name");

                    arrayName = new String[]{name[i]};
                    arrayQrid = new String[]{qrid[i]};
                    arrayId = new String[]{id[i]};
                    Log.i("訊息-老人id:", jsonArray.getJSONObject(i).getString("id") + "," +
                            "訊息-QRid:" + jsonArray.getJSONObject(i).getString("qrid") + "," +
                            "姓名:" + jsonArray.getJSONObject(i).getString("name"));
                }


                for(int i=0; i<arrayName.length; i++) {

                    if (data.equals(arrayQrid[i])) {
                        strMsg = arrayName[i] + "\n\n歡迎使用";
                        strId = arrayId[i];
                        isOK = true;
                        //strTemp = strMsg;
                        break;
                    }
                }

                if(isOK==true){
                    btnOld.setText(strMsg);
                    btnEnter.setText("前往主頁面");
                    //isOK = false;
                }
                else{
                    btnEnter.setText("返回驗證頁");
                    btnOld.setText("查無資料\n請重新確認");
                    btnOld.setTextColor(0xFF0000);
                     Intent intent2 = new Intent(ActQrcode.this,ActMainMain.class);
                     startActivity(intent2);
                }
Log.i("訊息有沒有抓到啊啊啊~",strId);

            }

            reader.close();

        } catch (MalformedURLException | JSONException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

        }

/*
            for(int i=0; i<arrayName.length; i++) {

                if (data.equals(arrayQrid[i])) {
                    strMsg = arrayName[i] + "\n歡迎使用";
                    strId = arrayId[i];
                    isOK = true;
                    //strTemp = strMsg;
                    break;
                }
            }

            if(isOK==true){
                btnOld.setText(strMsg);
                btnEnter.setText("前往主頁面");
                //isOK = false;
            }
            else{
                btnEnter.setText("返回驗證頁");
                btnOld.setText("查無資料\n請重新確認");
                btnOld.setTextColor(0xFF0000);
                // Intent intent2 = new Intent(ActQrcode.this,ActMain.class);
                // startActivity(intent2);
            }

            */
        /////////////////////////
    }


    Button btnOld,btnEnter;
}