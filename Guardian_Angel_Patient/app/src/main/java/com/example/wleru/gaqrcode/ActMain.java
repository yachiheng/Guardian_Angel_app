package com.example.wleru.gaqrcode;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ActMain extends AppCompatActivity {

    private View.OnClickListener btnQrcode_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

       //     setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);


     IntentIntegrator integrator = new IntentIntegrator(ActMain.this);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
            integrator.setPrompt("");
            integrator.setCameraId(0);
            integrator.setBeepEnabled(true);
            integrator.setBarcodeImageEnabled(false);
            integrator.initiateScan();
        }
    };
    private View.OnClickListener btnOK_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (!editQrcode.getText().toString().equals("")) {
/*
                if (("254005").equals(editQrcode.getText().toString()))
                    Toast.makeText(ActMain.this, "成功", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(ActMain.this, "其他", Toast.LENGTH_LONG).show();
*/
                //傳到qrcode頁
                Intent intent = new Intent(ActMain.this, ActQrcode.class);
                Bundle bundle = new Bundle();
                bundle.putString(CDictionary.QRID, editQrcode.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);

            }
            else
                Toast.makeText(ActMain.this,"請輸入驗證碼或掃描QRCODE",Toast.LENGTH_LONG).show();

        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
/*
            if (result.getContents() == null) {
                Toast.makeText(this, "You cancelled the scanning", Toast.LENGTH_SHORT).show();
            } else {
                if(result.getContents().equals("254005"))
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this,"其他" + result.getContents(),Toast.LENGTH_LONG).show();
*/
                //傳到qrcode頁
                Intent intent = new Intent(ActMain.this,ActQrcode.class);
                Bundle bundle = new Bundle();
                bundle.putString(CDictionary.QRID,result.getContents());

                intent.putExtras(bundle);
                startActivity(intent);

            }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }






        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actmain);

        //基本設定
        initial();


    }

    private void initial() {
        editQrcode = findViewById(R.id.editQrcode);
        btnOK = findViewById(R.id.btnOK);
        btnOK.setOnClickListener(btnOK_click);
        btnQrcode = findViewById(R.id.btnQrcode);
        btnQrcode.setOnClickListener(btnQrcode_click);

    }

    Button btnQrcode, btnOK;
    EditText editQrcode;
}
