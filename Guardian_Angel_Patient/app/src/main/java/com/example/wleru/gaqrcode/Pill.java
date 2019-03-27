package com.example.wleru.gaqrcode;

public class Pill {
    private int pphoto;
    private String pname;
    private int pnumber;


    public Pill() {
        super();
    }

    public Pill( int pphoto, String pname, int pnumber) {
        this.pphoto = pphoto;
        this.pname = pname;
        this.pnumber = pnumber;
    }

    public  int getPphoto() {
        return pphoto;
    }

    public void setPphoto( int pphoto) {
        this.pphoto = pphoto;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public int getPnumber() {
        return pnumber;
    }

    public void setPnumber(int pnumber) {
        this.pnumber = pnumber;
    }

}
