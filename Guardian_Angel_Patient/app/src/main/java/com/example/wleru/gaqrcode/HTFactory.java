package com.example.wleru.gaqrcode;

import java.util.ArrayList;

public class HTFactory {

    private ArrayList<HT> list=new ArrayList<HT>();
    private int position=0;

    public HTFactory(){ LoadData(); }

    private void LoadData(){
        list.add(new HT("001","心臟內科","12","2019/01/12"));
        list.add(new HT("002","肝膽腸胃科","20","2019/01/17"));
        list.add(new HT("003","內科","16","2019/02/16"));
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
    public void MoveTo(int index){
        position=index;
    }

*/
    public HT GetCurrent(){
        return list.get(position);
    }


    public HT[] GetAll(){
        return list.toArray(new HT[list.size()]);
    }




}
