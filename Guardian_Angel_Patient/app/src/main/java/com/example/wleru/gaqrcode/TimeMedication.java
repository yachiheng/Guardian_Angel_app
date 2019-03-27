package com.example.wleru.gaqrcode;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class TimeMedication extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        String[] arrayMT=getIntent().getExtras().getStringArray(
                CDictionary.BK_MT_ITEMS_LIST);

        ListAdapter adapter=new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                arrayMT);
        setListAdapter(adapter);
        Toast.makeText(this,"點畫面即回到前一頁",Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Bundle bund  = new Bundle();
        bund.putInt(CDictionary.BK_SELECTED_INDEX,position);
        Intent intent = new Intent();
        intent.putExtras(bund);
        setResult(0,intent);
        finish();
    }

}
