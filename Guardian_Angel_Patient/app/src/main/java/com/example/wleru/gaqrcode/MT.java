package com.example.wleru.gaqrcode;

//用藥時間
public class MT {
    private String id;
    private String name;
    private String timeH;
    private String timeM;

    public MT(String id, String name, String timeH, String timeM) {
        this.id = id;
        this.name = name;
        this.timeH = timeH;
        this.timeM = timeM;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimeH() {
        return timeH;
    }

    public void setTimeH(String timeH) { this.timeH = timeH; }

    public String getTimeM() {
        return timeM;
    }

    public void setTimeM(String timeM) { this.timeM = timeM; }

}
