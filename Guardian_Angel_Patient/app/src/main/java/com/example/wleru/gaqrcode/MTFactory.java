package com.example.wleru.gaqrcode;

import java.util.ArrayList;

public class MTFactory {

    private ArrayList<MT> list=new ArrayList<MT>();
    private int position=0;

    public MTFactory(){
        LoadData();
    }

    private void LoadData(){
        list.add(new MT("001","心臟藥","08","30"));
        list.add(new MT("002","感冒藥","13","00"));
        list.add(new MT("003","血壓藥","17","30"));
    }


    public void MoveToFirst(){
        position=0;
    }

    /*
    public void MoveToPrevious(){
        position--;
        if(position<0)
            position=0;
    }
    public void MoveToNext(){
        position++;
        if(position>=list.size())
            MoveToLast();
    }
    public void MoveToLast(){
        position=list.size()-1;
    }
    */

    public void MoveTo(int index){
        position=index;
    }

    public MT GetCurrent(){
        return list.get(position);
    }


    public MT[] GetAll(){
        return list.toArray(new MT[list.size()]);
    }




}
