package com.example.iii.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Main2Activity extends AppCompatActivity {

    private View.OnClickListener btnDrug_click=new View.OnClickListener ( ) {
        @Override
        public void onClick(View v) {
            Intent intent_Drug = new Intent(Main2Activity.this,DrugActivity.class);
            Log.i("Demo","drug");
            startActivity(intent_Drug);

        }
    };
    private View.OnClickListener btnInform_click=new View.OnClickListener ( ) {
        @Override
        public void onClick(View v) {
            Intent intent_Inform = new Intent(Main2Activity.this,InformActivity.class);
            Log.i("Demo","inform");
            startActivity(intent_Inform);

        }
    };
    private View.OnClickListener btnEmergency_click=new View.OnClickListener ( ) {
        @Override
        public void onClick(View v) {
            Intent intent_Emergency = new Intent(Main2Activity.this,EmergencyActivity.class);
            Log.i("Demo","Emer");
            startActivity(intent_Emergency);

        }
    };
    private View.OnClickListener btnSweet_click=new View.OnClickListener ( ) {
        @Override
        public void onClick(View v) {
            Intent intent_Sweet = new Intent(Main2Activity.this,SweetActivity.class);
            startActivity(intent_Sweet);
        }
    };
    private View.OnClickListener btnOther_click=new View.OnClickListener ( ) {
        @Override
        public void onClick(View v) {
            Intent intent_Other = new Intent(Main2Activity.this,OtherActivity.class);
            startActivity(intent_Other);
        }
    };
    private View.OnClickListener btnLost_click= new View.OnClickListener ( ) {
        @Override
        public void onClick(View v) {
//            Intent intent_Lost = new Intent (Main2Activity.this,testActivity.class);
            Intent intent_Lost = new Intent (Main2Activity.this,Man_Search_Activity.class);

            startActivity (intent_Lost);
            Log.i("Demo","Lost");
        }
    };
    private View.OnClickListener btnExit_click=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent_Exit = new Intent(Main2Activity.this,MainActivity.class);
            startActivity (intent_Exit);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main2);
        this.setTitle("功能");
        initialComponent ();
    }

    private void initialComponent() {
        btnDrug=findViewById (R.id.btnDrug);
        btnDrug.setOnClickListener (btnDrug_click);
        btnInform=findViewById (R.id.btnInform);
        btnInform.setOnClickListener (btnInform_click);
        btnEmergency=findViewById (R.id.btnEmergency);
        btnEmergency.setOnClickListener (btnEmergency_click);
        btnSweet=findViewById (R.id.btnSweet);
        btnSweet.setOnClickListener (btnSweet_click);
        btnOther=findViewById (R.id.btnOther);
        btnOther.setOnClickListener (btnOther_click);
        btnLost=findViewById (R.id.btnLost);
        btnLost.setOnClickListener (btnLost_click);
        btnExit=findViewById(R.id.btnExit);
        btnExit.setOnClickListener(btnExit_click);

    }

    Button btnDrug,btnInform,btnEmergency,btnSweet,btnOther,btnLost;
    Button btnExit;
}
