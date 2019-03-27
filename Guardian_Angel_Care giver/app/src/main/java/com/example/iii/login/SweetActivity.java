package com.example.iii.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class SweetActivity extends AppCompatActivity {

    private static final int Menu_Drug= Menu.FIRST, Menu_Inform=Menu.FIRST+1, Menu_Lost=Menu.FIRST+2,Menu_Emergency=Menu.FIRST+3, Menu_Other=Menu.FIRST+4;
    private View.OnClickListener btnSent_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            txtSweet.getText().toString();
            Toast.makeText(SweetActivity.this, "已送出小語", Toast.LENGTH_SHORT).show();
            txtSweet.setText("");
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,Menu_Drug, 0, "用藥提醒");
        menu.add(0,Menu_Inform, 1, "回診通知");
        menu.add(0,Menu_Lost, 2, "迷路走失");
        menu.add(0,Menu_Emergency, 3, "緊急連絡");
        menu.add(0,Menu_Other, 4, "其他功能");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case Menu_Drug:
                Intent intent_Drug=new Intent(SweetActivity.this,DrugActivity.class);
                startActivity (intent_Drug);
                return true;
            case Menu_Inform:
                Intent intent_Inform=new Intent(SweetActivity.this,InformActivity.class);
                startActivity (intent_Inform);
                return true;
            case Menu_Lost:
                Intent intent_Lost=new Intent(SweetActivity.this,Man_Search_Activity.class);
                startActivity (intent_Lost);
                return true;
            case Menu_Emergency:
                Intent intent_Emergency=new Intent(SweetActivity.this,EmergencyActivity.class);
                startActivity (intent_Emergency);
                return true;
            case Menu_Other:
                Intent intent_Other=new Intent(SweetActivity.this,OtherActivity.class);
                startActivity (intent_Other);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sweet_activity);
        this.setTitle("貼心小語");

        initialComponent();


    }

    private void initialComponent() {
        spnPerson=findViewById(R.id.spnPerson);
        btnSent=findViewById(R.id.btnSent);
        btnSent.setOnClickListener(btnSent_click);
        txtSweet=findViewById(R.id.txtSweet);
    }
    Spinner spnPerson;
    EditText txtSweet;
    Button btnSent;
}
