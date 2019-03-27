package com.example.wleru.gaqrcode;

public class Notice {
    private String nname;
    private String nthour;
    private String ntmin;

    public Notice() {
        super();
    }

    public Notice(String nname, String nthour, String ntmin) {
        this.nname = nname;
        this.nthour = nthour;
        this.ntmin = ntmin;
    }

    public String getNname() {
        return nname;
    }

    public void setNname(String nname) {
        this.nname = nname;
    }

    public String getNthour() {
        return nthour;
    }

    public void setNthour(String nthour) {
        this.nthour = nthour;
    }

    public String getNtmin() {
        return ntmin;
    }

    public void setNtmin(String ntmin) {
        this.ntmin = ntmin;
    }
}
