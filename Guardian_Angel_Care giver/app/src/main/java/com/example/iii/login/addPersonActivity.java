package com.example.iii.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class addPersonActivity extends AppCompatActivity {

    private View.OnClickListener btnPersonExit_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent_exit = new Intent(addPersonActivity.this,EmergencyActivity.class);
            startActivity(intent_exit);
        }
    };
    private View.OnClickListener btnPersonOk_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent_person = new Intent(addPersonActivity.this, CardViewActivity.class);
            startActivity(intent_person);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_person_activity);
        this.setTitle("新增聯絡人");
        initialComponent();



    }

    private void initialComponent() {
        txtName=findViewById(R.id.txtName);
        txtPhone=findViewById(R.id.txtPhone);
        txtTel=findViewById(R.id.txtTel);
        txtMail=findViewById(R.id.txtMail);
        btnPersonExit=findViewById(R.id.btnPersonExit);
        btnPersonExit.setOnClickListener(btnPersonExit_click);
        btnPersonOk=findViewById(R.id.btnPersonOk);
        btnPersonOk.setOnClickListener(btnPersonOk_click);
    }
    EditText txtName,txtPhone,txtTel, txtMail;
    Button btnPersonExit, btnPersonOk;
}
