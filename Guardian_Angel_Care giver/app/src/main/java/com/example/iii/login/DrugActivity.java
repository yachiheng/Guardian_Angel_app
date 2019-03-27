package com.example.iii.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.webkit.WebView;

public class DrugActivity extends AppCompatActivity {

    private static final int Menu_Inform= Menu.FIRST, Menu_Lost=Menu.FIRST+1, Menu_Emergency=Menu.FIRST+2,Menu_Sweet=Menu.FIRST+3, Menu_Other=Menu.FIRST+4;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,Menu_Inform, 0, "回診通知");
        menu.add(0,Menu_Lost, 1, "迷路走失");
        menu.add(0,Menu_Emergency, 2, "緊急聯絡");
        menu.add(0,Menu_Sweet, 3, "貼心小語");
        menu.add(0,Menu_Other, 4, "其他功能");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case Menu_Inform:
                Intent intent_Inform=new Intent(DrugActivity.this,InformActivity.class);
                startActivity (intent_Inform);
                return true;
            case Menu_Lost:
                Intent intent_Lost=new Intent(DrugActivity.this,Man_Search_Activity.class);
                startActivity (intent_Lost);
                return true;
            case Menu_Emergency:
                Intent intent_Emergency=new Intent(DrugActivity.this,EmergencyActivity.class);
                startActivity (intent_Emergency);
                return true;
            case Menu_Sweet:
                Intent intent_Sweet=new Intent(DrugActivity.this,SweetActivity.class);
                startActivity (intent_Sweet);
                return true;
            case Menu_Other:
                Intent intent_Other=new Intent(DrugActivity.this,OtherActivity.class);
                startActivity (intent_Other);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drug_activity);
        this.setTitle("用藥提醒");
        WebView webView1 = (WebView)findViewById(R.id.webView1);
        webView1.loadUrl("https://guardianangel20190211104750.azurewebsites.net/index.aspx");
    }

}
