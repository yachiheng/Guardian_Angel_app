package com.example.iii.login;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

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

public class Man_Search_Activity extends AppCompatActivity{

    StringBuilder stringBuilder = new StringBuilder();
    String strMsg="",strId="";
    GoogleMap mMap;
    ArrayList<String> items;

    private static final int Menu_Other= Menu.FIRST, Menu_Logout=Menu.FIRST+1;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,Menu_Other, 0, "其他功能");
        menu.add(0,Menu_Logout, 1, "登出");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case Menu_Other:
                Intent intent_other=new Intent(Man_Search_Activity.this,OtherActivity.class);
                startActivity (intent_other);
                return true;

            case Menu_Logout:
                Intent intent_Logout=new Intent(Man_Search_Activity.this,LogIn_Activity.class);
                startActivity (intent_Logout);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private AdapterView.OnItemSelectedListener spinnerOnItemSelected = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            String mLocations = spinner.getSelectedItem().toString();
            Log.i("INFO","SPN:"+spinner.getSelectedItem());
            String[] sLocation = mLocations.split(",");
            double dLat = Double.parseDouble(sLocation[1]);
            double dLon = Double.parseDouble(sLocation[2]);
            mMap.addMarker(new MarkerOptions ().position(new LatLng(dLat,dLon)));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(dLat,dLon),16));

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.man_search_activity);
        this.setTitle("迷路尋人");
        Log.i("INFO","迷路尋人");
        initialComponent();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        strId = bundle.getString("PassID");


        ArrayList<String> items = getLocation("https://gaapitest.azurewebsites.net/GPSLOC?searchid="+strId);

        Log.i("HAHA","https://gaapitest.azurewebsites.net/GPSLOC?searchid="+strId);
//        ArrayList<String> items = getLocation("https://gaapitest.azurewebsites.net/GPSLocation");
//        ArrayList<String> items = getLocation("https://www.ktec.gov.tw/site_api.php?type=json");

        Log.i("INFO","111");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        Log.i("INFO","222");
        Log.i("INFO","adapter:"+items.toString());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(spinnerOnItemSelected);
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(onMapReadyCallback);


    }
    OnMapReadyCallback onMapReadyCallback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;
            double Lat = 22.628081;
            double Lng = 120.293136;
            Log.i("INFO","333");

            LatLng latlng = new LatLng(Lat,Lng);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
            mMap.addMarker(new MarkerOptions().position(latlng));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Lat,Lng),15));

        }
    };

    public ArrayList<String> getLocation(String url){
        Log.i("INFO","444-1");
        StrictMode.ThreadPolicy l_policy =  new StrictMode.ThreadPolicy.Builder().permitAll().build();
        setThreadPolicy(l_policy);
        Log.i("INFO","444");

        ArrayList<String>CName = new ArrayList<String>();
        ArrayList<String>CList = new ArrayList<String>();

        try{
            URL url1 = new URL(url);
            Log.i("INFO",url);
            URLConnection conn = url1.openConnection();
            Log.i("INFO","555-2");
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            Log.i("INFO","555-3");
            InputStream streamIn = conn.getInputStream();
            Log.i("INFO","555-4");
            BufferedReader reader = new BufferedReader(new InputStreamReader(streamIn));
            String line;
            Log.i("INFO","555");
            while ((line=reader.readLine()) != null){
                stringBuilder.append(line);
                strMsg += stringBuilder.toString();
                Log.i("info",strMsg);

                JSONArray jsonArray = new JSONArray(strMsg);

                for(int i = 0;i<jsonArray.length();i++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    String name = jsonObject1.getString("oname");
                    String latitude = jsonObject1.getString("xaxis");
                    String longitude = jsonObject1.getString("yaxis");
                    Log.i("INFO","String:"+name+","+latitude+","+longitude);

//                    String[] latlng = {latitude,longitude};
//                    String[][] mLocations={{name},{latitude,longitude}};
//                    Log.i("INFO","mLocation:"+mLocations.toString());
                    CName.add(jsonArray.getJSONObject(i).getString("oname"));
                    CList.add(jsonArray.getJSONObject(i).getString("oname")+","+jsonArray.getJSONObject(i).getString("xaxis")+","+jsonArray.getJSONObject(i).getString("yaxis"));
                }

            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();

        }
        return CList;
    }



    private void initialComponent() {
        spinner = findViewById(R.id.spinner);


    }

    Spinner spinner;


}
