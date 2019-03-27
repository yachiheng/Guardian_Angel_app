package com.example.iii.login;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Old_Man_JSONparse extends AsyncTask<Void,Void,Boolean> {

    Context c;
    String jsonData;
    Spinner spnLocation;
    Dialog pd;


    ArrayList<String> oldMen = new ArrayList<>();

    public Old_Man_JSONparse(Context c, String jsonData, Spinner spnLocation){
        this.c = c;
        this.jsonData = jsonData;
        this.spnLocation = spnLocation;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//        pd = new ProcessDialog(C);
//        pd = setTitle("ParseJSON");
//        pd.setMessage("Parsing...Please wait");
//        pd.show();

    }
    @Override
    protected Boolean doInBackground(Void... voids) {
        return this.parse();
    }

    @Override
    protected void onPostExecute(Boolean isParsed) {
        super.onPostExecute(isParsed);
        pd.dismiss();
        if(isParsed){
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(c,android.R.layout.simple_list_item_1,oldMen);
            spnLocation.setAdapter(adapter);
            spnLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long id) {
                    Toast.makeText(c, oldMen.get(i), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }else{
                Toast.makeText(c, "Unable to Parse, check your output Log", Toast.LENGTH_SHORT).show();
        }

    }
    private Boolean parse() {
        try {
            JSONArray ja = new JSONArray(jsonData);
            JSONObject jo;

            oldMen.clear();
            for (int i = 0; i < ja.length(); i++) {
                jo = ja.getJSONObject(i);
                String oname = jo.getString("oname");
                oldMen.add(oname);
            }
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }

    }
}
