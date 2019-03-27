package com.example.wleru.gaqrcode;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.os.StrictMode;
import android.os.Vibrator;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static android.os.StrictMode.setThreadPolicy;

public class Fall_Location_Activity extends AppCompatActivity implements SensorEventListener, LocationListener{

    private static final String TAG =ActMain.class.getSimpleName();
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private Vibrator vib ; //手機震動
    private String strId = ""; //老人id

    String lat1,lng1,ati1;

    String strMsgP = "";

    StringBuilder stringBuilderp = new StringBuilder(); //緊急聯絡人
    ArrayList<String> arrayPId = new ArrayList<>(); //緊急聯絡人id
    ArrayList<String> arrayPname = new ArrayList<>(); //緊急聯絡人名稱
    ArrayList<String> arrayPphone = new ArrayList<>(); //緊急聯絡人電話



    String [] phonenumber = new String[] {"0958620158"}; //簡訊發送號碼
    private TextView text_x,text_y,text_z,text_f,textResult;
    AlertDialog.Builder builder;
    private float Gfmin,lastGf;
    private long lasttimestamp = 0;
    Calendar mCalendar;
    Boolean check=false;
    private Intent intent;



    /** Called when the activity is first created. */    /** Called when the activity is first created. */

    private void sendEvent(String strId){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd   HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        String time = formatter.format(curDate);
        StrictMode.ThreadPolicy l_policy =  new StrictMode.ThreadPolicy.Builder().permitAll().build();
        setThreadPolicy(l_policy);

        JSONArray ja=new JSONArray();
        try {

            JSONObject ob = new JSONObject();
            try {
                ob.put("A_style","2");
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fall_location_activity);
        getSupportActionBar().hide();


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        strId = bundle.getString(CDictionary.BK_OidForLocation);

        Log.i("INFO","11111");

        Initialcompoenet();


        if(checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(Manifest.permission.ANSWER_PHONE_CALLS) != PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE,Manifest.permission.ANSWER_PHONE_CALLS,Manifest.permission.SEND_SMS,Manifest.permission.READ_PHONE_STATE},0);
        }
        LocationManager locationmanager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions (new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
            Toast.makeText(Fall_Location_Activity.this, "沒有打開定位功能", Toast.LENGTH_SHORT).show();
        }//顯示GPS位置資訊
        if (locationmanager.isProviderEnabled (LocationManager.GPS_PROVIDER)) {
            Location location = locationmanager.getLastKnownLocation (LocationManager.GPS_PROVIDER);

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd   HH:mm:ss");
            Date curDate = new Date(System.currentTimeMillis());
            String time = formatter.format(curDate);

            Log.i("INFO","有有");

            Log.i("INFO","time");
            double lat = location.getLatitude ( );
            Log.i("INFO", "lat");
            double lng = location.getLongitude ( );
            Log.i("INFO", "lng");
            double ati = location.getAltitude ( );
            Log.i("INFO", "ati");

            lat1 = Double.toString (lat);
//            txtLat.setText (lat1);
            lng1 = Double.toString (lng);
//            txtLong.setText (lng1);
            ati1 = Double.toString (ati);
//            txtAti.setText (ati1);
//            txtTime.setText(time);

            StrictMode.ThreadPolicy l_policy =  new StrictMode.ThreadPolicy.Builder().permitAll().build();
            setThreadPolicy(l_policy);

            JSONArray ja=new JSONArray();
            try {

                JSONObject ob = new JSONObject();
                try {
                    ob.put("x_axis", lat);
                    ob.put("y_axis", lng);
                    ob.put("z_asix", ati);
                    ob.put("G_time",time);
                    ob.put("O_id",strId);
                    ja.put(ob);
                    Log.i("INFO","ja:"+ja.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String api= "https://insertgps.azurewebsites.net/GPS/insert?json=";
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
            locationmanager.requestLocationUpdates (LocationManager.GPS_PROVIDER,3000,1,locationListener);
//s            showMarkerMe(lat, lng);
            mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);// TYPE_GRAVITY
            if (null == mSensorManager) {
                Log.d(TAG, "device not support SensorManager");
            }

            startSeneor();



        } if(locationmanager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            Location location = locationmanager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd   HH:mm:ss");
            Date curDate = new Date(System.currentTimeMillis());
            String time = formatter.format(curDate);

            double lat = location.getLatitude();
            double lng = location.getLongitude();
            double ati = location.getAltitude();

            lat1 = Double.toString(lat);
            Log.i("INFO", lat1);
//            txtLat.setText(lat1);
            lng1 = Double.toString(lng);
            Log.i("INFO", lng1);
//            txtLong.setText(lng1);
            ati1 = Double.toString(ati);
            Log.i("INFO", ati1);
//            txtAti.setText(ati1);
//            txtTime.setText(time);

            StrictMode.ThreadPolicy l_policy =  new StrictMode.ThreadPolicy.Builder().permitAll().build();
            setThreadPolicy(l_policy);

            JSONArray ja=new JSONArray();
            try {
                JSONObject ob = new JSONObject();
                try {
                    ob.put("x_axis", lat);
                    ob.put("y_axis", lng);
                    ob.put("z_asix", ati);
                    ob.put("G_time",time);
                    ob.put("O_id",strId);
                    ja.put(ob);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String api= "https://insertgps.azurewebsites.net/GPS/insert?json=";
                api+=ja.toString();
                Log.i("INFO", api);

                URL url=new URL(api);
                URLConnection conn=url.openConnection();
                InputStream streamIn=conn.getInputStream();

                BufferedReader r = new BufferedReader(new InputStreamReader(streamIn));
                StringBuilder html = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null) {
                    html.append(line);

                }
                Log.i("INFO",html.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            locationmanager.requestLocationUpdates (LocationManager.GPS_PROVIDER,3000,1,locationListener);

            mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);// TYPE_GRAVITY
            if (null == mSensorManager) {
                Log.d(TAG, "device not support SensorManager");
            }

            startSeneor();





        }else
            Toast.makeText(Fall_Location_Activity.this, "接收不到信號", Toast.LENGTH_SHORT).show();
    }



    private void startSeneor() {
        // 参数三，检测的精准度
        mSensorManager.registerListener(this, mSensor,
                SensorManager.SENSOR_DELAY_NORMAL);// SENSOR_DELAY_GAME
    }

    private void Initialcompoenet(){

//        text_x = findViewById(R.id.textsenser1);
//        text_y = findViewById(R.id.textsenser2);
//        text_z = findViewById(R.id.textsenser3);
//        text_f = findViewById(R.id.textsenser4);
        textResult = findViewById(R.id.textResult);
//        txtLong = findViewById(R.id.txtLong);
//        txtLat = findViewById(R.id.txtLat);
//        txtAti = findViewById(R.id.txtAti);
//        txtTime = findViewById(R.id.txtTime);
        imgP = findViewById(R.id.imgP);
        
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // TODO Auto-generated method stub
        if (event.sensor == null) {
            return;
        }

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            mCalendar = Calendar.getInstance();
            long stamp = mCalendar.getTimeInMillis() / 10000;// 1393844912

//            text_x.setText(String.valueOf(x));
//            text_y.setText(String.valueOf(y));
//            text_z.setText(String.valueOf(z));

            int second = mCalendar.get(Calendar.SECOND);// 53

            float Gf = Math.abs(getMaxValue(x, y, z));
//            text_f.setText(String.valueOf(Gf));
            Gfmin = Gf - lastGf;

            Log.d(TAG, " X:" + x + "   Y:" + y + "   Z:" + z + "   G:" + Gf + "  MaxValue:"+Gfmin+"    stamp:"
                    + stamp + "  second:" + second);

            /*if (Gfmin < 27 && Gf >40 && (stamp - lasttimestamp) > 5) {
                lasttimestamp = stamp;
                Log.d(TAG, " sensor isMoveorchanged  Normal....");
                textResult.setText("正常狀態");

            }else*/
            if (Gfmin > 27 && z < 0   && (stamp - lasttimestamp) >8) {
                lasttimestamp = stamp;
                Log.d(TAG, " sensor isMoveorchanged  Accident....");
                textResult.setText("跌倒了");
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("緊急通知")
                        .setMessage("於倒數計時結束前，尚未按下確認鍵，將為您撥打緊急電話")
                        .setNegativeButton(android.R.string.no,  new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss(); //視窗消失
                                vib.cancel(); //震動結束
                                Toast toast = Toast.makeText(Fall_Location_Activity.this,
                                        "緊急電話已取消", Toast.LENGTH_LONG);
                                //顯示Toast
                                toast.show();
                                textResult.setText("正常狀態");
                                return;
                            }
                        })
                        .create();
                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    private static final int AUTO_DISMISS_MILLIS = 10000; //倒數秒數
                    @Override
                    public void onShow(final DialogInterface dialog) {
                        vib = (Vibrator)getSystemService(VIBRATOR_SERVICE); // 啟動震動事件
                        //震動持續1秒{開始,停止,開始,停止...}
                        vib.vibrate(new long[]{0, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000}, -1);
                        final Button defaultButton = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_NEGATIVE); //彈跳視窗按鈕
                        final CharSequence positiveButtonText = defaultButton.getText(); // get視窗按鈕倒數文字
                        new CountDownTimer(AUTO_DISMISS_MILLIS, 1000) { // <- 倒數間隔時間
                            @Override
                            public void onTick(long millisUntilFinished) { //倒數ing
                                defaultButton.setText(String.format( // set彈跳視窗按鈕文字
                                        Locale.getDefault(), "%s (%d)",
                                        positiveButtonText,
                                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) + 1 // 加 1 讓他不顯示為0
                                ));
                            }
                            @Override
                            public void onFinish() { //倒數結束
                                if (((AlertDialog) dialog).isShowing()) {
                                    dialog.dismiss(); //視窗消失
                                    vib.cancel(); //震動取消

                                    intent = new  Intent(Intent.ACTION_CALL); //撥打管理員
                                    Uri data = Uri.parse("tel:" +"0958620158");
                                    intent.setData(data);
                                    startActivity(intent);//撥出電話

                                    // 發送簡訊
                                    PendingIntent intent = PendingIntent.getActivity(
                                            Fall_Location_Activity.this,
                                            0,
                                            new Intent(Fall_Location_Activity.this, Activity.class),
                                            0);
                                    SmsManager smsManager = SmsManager.getDefault();

                                    for(int i =0; i<phonenumber.length ; i++){
                                        SimpleDateFormat formatter = new SimpleDateFormat   ("yyyy年MM月dd日");
                                        Date curDate = new Date(System.currentTimeMillis());
                                        String str = formatter.format(curDate);
                                        Log.i("demo",str);
                                        smsManager.sendTextMessage(
                                                phonenumber[i],
                                                null,
                                                str+"\n"+"http://maps.google.com/?q="+lat1+","+lng1,
                                                intent,
                                                null);
                                    }
//                                    Log.i("Send", "http://maps.google.com/?q="+txtLat.getText().toString()+","+txtLong.getText().toString());
                                    Toast.makeText(Fall_Location_Activity.this,"發出簡訊成功",Toast.LENGTH_SHORT).show();
                                    sendEvent(strId);

                                }
                            }
                        }.start();
                    }
                });
                dialog.show();
            }

            lastGf = Gf; //紀錄前一次G值
        }
    }

    private float getMaxValue ( float x, float y, float z){
        float G;
        G = (float) Math.sqrt((x * x) + (y * y) + (z * z));
        return G;
    }

    @Override
    protected void onPause () {
        // TODO Auto-generated method stub
        /* 取消註冊SensorEventListener */
        mSensorManager.unregisterListener(this);
        /*Toast.makeText(this, "Unregister accelerometerListener", Toast.LENGTH_LONG).show();*/
        super.onPause();
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    LocationListener locationListener = new LocationListener(){

        @Override
        public void onLocationChanged(Location location) {

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

ImageView imgP;
//    TextView txtLong, txtLat, txtAti,txtTime;

}
