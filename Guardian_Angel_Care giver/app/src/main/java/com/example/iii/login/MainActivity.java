package com.example.iii.login;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

import static android.os.StrictMode.setThreadPolicy;

public class MainActivity extends AppCompatActivity {

    StringBuilder stringBuilder = new StringBuilder();
    String strMsg="";





    private View.OnClickListener btnLogin_click = new View.OnClickListener (){

        @Override
        public void onClick(View v) {
            if((txtemail.getText().toString ().equals ("apple@gmail.com")) && (txtpassword.getText().toString().equals("password"))){

                Intent intent=new Intent(MainActivity.this,Man_Search_Activity.class);
                Toast.makeText(MainActivity.this, "孟蓉 歡迎光臨", Toast.LENGTH_SHORT).show();
                startActivity (intent);
            }else{
                counter--;
                Toast.makeText(MainActivity.this, "請輸入正確資訊,你(妳)還剩"+String.valueOf(counter)+"次機會", Toast.LENGTH_SHORT).show();
                if(counter==0){
                    btnLogin.setEnabled(false);
                }
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        initialComponent();



    }
    public ArrayList<String> getUser(String url){
        Log.i("Login","");
        StrictMode.ThreadPolicy l_policy =  new StrictMode.ThreadPolicy.Builder().permitAll().build();
        setThreadPolicy(l_policy);
        Log.i("Login","444");

        ArrayList<String>userList = new ArrayList<String>();

        try{
            URL url1 = new URL(url);
            Log.i("Login",url);
            URLConnection conn = url1.openConnection();
            Log.i("Login","555-2");
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            Log.i("Login","555-3");
            InputStream streamIn = conn.getInputStream();
            Log.i("Login","555-4");
            BufferedReader reader = new BufferedReader(new InputStreamReader(streamIn));
            String line;
            Log.i("Login","555");
            while ((line=reader.readLine()) != null){
                stringBuilder.append(line);
                strMsg += stringBuilder.toString();
                Log.i("info",strMsg);

                JSONArray jsonArray = new JSONArray(strMsg);

                for(int i = 0;i<jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String id = jsonObject.getString("id");
                    String fname = jsonObject.getString("fname");
                    String password = jsonObject.getString("password");
                    Log.i("Login","String:"+id+","+fname+","+password);

//                    String[] latlng = {latitude,longitude};
//                    String[][] mLocations={{name},{latitude,longitude}};
//                    Log.i("INFO","mLocation:"+mLocations.toString());
                    userList.add(jsonArray.getJSONObject(i).getString("oname")+","+jsonArray.getJSONObject(i).getString("xaxis")+","+jsonArray.getJSONObject(i).getString("yaxis"));
                }

            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();

        }
        return userList;
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
    //    private void validata(String userName,String userPassword){
//        if((userName.equals("tining68@hotmail.com")) && (userPassword.equals("e028715"))){
//            Intent intent=new Intent(MainActivity.this,Main2Activity.class);
//            startActivity (intent);
//        }else{
//            counter--;
//            Toast.makeText(MainActivity.this, "請輸入正確資訊,你(妳)還剩"+String.valueOf(counter)+"次機會", Toast.LENGTH_SHORT).show();
//            if(counter==0){
//                btnLogin.setEnabled(false);
//            }
//        }
//    }

    private void initialComponent() {
        txtemail=findViewById(R.id.txtEmail);
        txtpassword=findViewById(R.id.txtPassword);
        btnLogin=findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(btnLogin_click);
        txtemail.setText("");
        txtpassword.setText("");

    }
    private int counter=5;
    EditText txtemail,txtpassword;
    Button btnLogin;
}