package com.example.iii.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.webkit.WebView;

public class OtherActivity extends AppCompatActivity {

    private static final int Menu_Lost= Menu.FIRST, Menu_Logout=Menu.FIRST+1;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,Menu_Lost, 0, "迷路走失");
        menu.add(0,Menu_Logout, 1, "登出");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case Menu_Lost:
                Intent intent_Lost=new Intent(OtherActivity.this,Man_Search_Activity.class);
                startActivity (intent_Lost);
                return true;
            case Menu_Logout:
                Intent intent_Logout=new Intent(OtherActivity.this,LogIn_Activity.class);
                startActivity (intent_Logout);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.other_activity);
        this.setTitle("其他功能");
        WebView webView3 = (WebView)findViewById(R.id.webView3);
        webView3.loadUrl("http://guardianangel.southeastasia.cloudapp.azure.com/Login.aspx");
    }
}
