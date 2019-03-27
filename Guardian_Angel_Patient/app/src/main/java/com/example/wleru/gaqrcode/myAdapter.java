package com.example.wleru.gaqrcode;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class myAdapter extends BaseAdapter {

    private ArrayList<String> listData ;   //資料
    private LayoutInflater inflater;    //加載layout

    static class ViewHolder{
        TextView itemData;
    }



    public myAdapter(ArrayList<String> listData, LayoutInflater inflater) {
        this.listData = listData;
        this.inflater = inflater;
    }

    //取得數量
    @Override
    public int getCount() {
        return listData.size();
    }
    //取得Item
    @Override
    public String getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        //當ListView被拖拉時會不斷觸發getView，為了避免重複加載必須加上這個判斷
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.listitemstyle, null);
            holder.itemData = (TextView) convertView.findViewById(R.id.ItemText);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (position % 2 == 0) {

            holder.itemData.setText(listData.get(position));
            holder.itemData.setBackgroundResource(R.drawable.itemstyle);
        }
        else {
            holder.itemData.setText(listData.get(position));
            holder.itemData.setBackgroundResource(R.drawable.itemstyle2);
        }
        return convertView;
    }
}