package com.example.iii.login;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class EmergencyActivity extends AppCompatActivity {
    String [] strSelect = {"張素素","林秀美","王阿香","陳美芳","黃秀秀"};
    private static final int Menu_Drug = Menu.FIRST, Menu_Inform = Menu.FIRST + 1, Menu_Lost = Menu.FIRST + 2, Menu_Sweet = Menu.FIRST + 3, Menu_Other = Menu.FIRST + 4;
    private View.OnClickListener btnaddPerson_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent_addPerson = new Intent(EmergencyActivity.this, addPersonActivity.class);
            startActivity(intent_addPerson);

        }
    };


    private AdapterView.OnItemClickListener onClickListView = new AdapterView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(EmergencyActivity.this,"點選第 "+(position +1) +" 個 \n內容："+ strSelect[position], Toast.LENGTH_SHORT).show();
        }
    };






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,Menu_Drug, 0, "用藥提醒");
        menu.add(0,Menu_Inform, 1, "回診通知");
        menu.add(0,Menu_Lost, 2, "迷路走失");
        menu.add(0,Menu_Sweet, 3, "貼心小語");
        menu.add(0,Menu_Other, 4, "其他功能");
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case Menu_Drug:
                Intent intent_Drug = new Intent(EmergencyActivity.this, DrugActivity.class);
                startActivity(intent_Drug);
                return true;
            case Menu_Inform:
                Intent intent_Inform = new Intent(EmergencyActivity.this, InformActivity.class);
                startActivity(intent_Inform);
                return true;
            case Menu_Lost:
                Intent intent_Lost = new Intent(EmergencyActivity.this, Man_Search_Activity.class);
                startActivity(intent_Lost);
                return true;
            case Menu_Sweet:
                Intent intent_Sweet = new Intent(EmergencyActivity.this, SweetActivity.class);
                startActivity(intent_Sweet);
                return true;
            case Menu_Other:
                Intent intent_Other = new Intent(EmergencyActivity.this, OtherActivity.class);
                startActivity(intent_Other);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emergency_activity);
        this.setTitle("緊急連絡");
        initialComponent();



    }

    private void initialComponent() {
    btnaddPerson=findViewById(R.id.btnaddPerson);
    btnaddPerson.setOnClickListener(btnaddPerson_click);
    listViewPerson = findViewById(R.id.listViewPerson);

    ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,strSelect);
    listViewPerson.setAdapter(adapter);
    listViewPerson.setOnItemClickListener(onClickListView);
    }
    Button btnaddPerson;
    ListView listViewPerson;

}
