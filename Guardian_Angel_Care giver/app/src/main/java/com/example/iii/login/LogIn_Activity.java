package com.example.iii.login;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.ArrayList;

import static android.os.StrictMode.setThreadPolicy;

public class LogIn_Activity extends AppCompatActivity {

    StringBuilder stringBuilder = new StringBuilder();
    String[] id, name, password,account;
    ArrayList<String> arrayId = new ArrayList<>();
    ArrayList<String> arrayName = new ArrayList<>();
    ArrayList<String> arrayPassword = new ArrayList<>();
    ArrayList<String> arrayAccount = new ArrayList<>();



    String strMsg = "",strId="";
    boolean isOK = false;

    private View.OnClickListener btnLogin_Click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            QrIsOk();

            if(isOK==true) {
                Intent intent = new Intent(LogIn_Activity.this, Man_Search_Activity.class);
                Bundle bundle = new Bundle();
                bundle.putString("PassID", strId);
                Log.i("HAHA","HAHA8:"+strId);

                intent.putExtras(bundle);
                startActivity(intent);
                Log.i("LOG","順利跳頁囉");
            }
            else if(isOK==false){

                Toast.makeText(LogIn_Activity.this, "請輸入正確資訊", Toast.LENGTH_SHORT).show();



                Log.i("LOG","失敗回去吧");
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in_activity);

        initialComponent();




    }

    private void initialComponent() {
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(btnLogin_Click);
    }


    private void QrIsOk() {
        StrictMode.ThreadPolicy l_policy =  new StrictMode.ThreadPolicy.Builder().permitAll().build();
        setThreadPolicy(l_policy);

        Log.i("LOG","111");
        /////////抓資料///////////
        try {
            //待改api
            URL url = new URL("https://gaapitest.azurewebsites.net/USERALL");
            URLConnection conn = url.openConnection();
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            InputStream streamIn = conn.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(streamIn));
            ///
            Log.i("LOG", stringBuilder.toString());
            ///
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                strMsg += (stringBuilder.toString() + "\n");
                Log.i("LOG", "log:"+strMsg);

                JSONArray jsonArray = new JSONArray(strMsg);
                Integer intJson = jsonArray.length();
                String[] id= new String[intJson];
                String[] name = new String[intJson];
                String[] account = new String[intJson];
                String[] password = new String[intJson];



                for (int i = 0; i < jsonArray.length(); i++) {
                    Log.i("LOG",String.valueOf(jsonArray.length()));
                    Log.i("LOG","id:HAHA");
                    id[i] = jsonArray.getJSONObject(i).getString("id");
                    Log.i("LOG","id:HAHA1:"+id[i]);
                    name[i] = jsonArray.getJSONObject(i).getString("fname");
                    Log.i("LOG","id:name:"+name[i]);
                    account[i] =jsonArray.getJSONObject(i).getString("account");
                    Log.i("LOG","id:account:"+account[i]);
                    password[i] = jsonArray.getJSONObject(i).getString("password");
                    Log.i("HAHA","id:password:"+password[i]);


                    arrayId.add(id[i]);
                    arrayName.add(name[i]);
                    arrayAccount.add(account[i]);
                    arrayPassword.add(password[i]);




                    Log.i("HAHA2",arrayId.toString());
                }


                for(int i=0; i<arrayName.size(); i++) {
                    Log.i("HAHA2.2",String.valueOf(arrayName.size()));


                    Log.i("HAHA5",txtEmail.getText().toString());
                    Log.i("HAHA4",txtPassword.getText().toString());
                    if (txtEmail.getText().toString().equals(arrayAccount.get(i))&&txtPassword.getText().toString().equals(arrayPassword.get(i))) {

                        Log.i("HAHA6",arrayAccount.get(i));
                        strMsg = arrayName.get(i) + "\n\n歡迎使用";
                        strId = arrayId.get(i);
                        Log.i("HAHA7",strId);
                        isOK = true;
                        //strTemp = strMsg;
                        break;
                    }

                }

                if(isOK==true){
                    Toast.makeText(LogIn_Activity.this, strMsg+"歡迎光臨", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(LogIn_Activity.this, "請輸入正確資訊", Toast.LENGTH_SHORT).show();
                }



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


    Button btnLogin;
    EditText txtEmail,txtPassword;

}
