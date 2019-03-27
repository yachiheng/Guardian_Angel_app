package com.example.wleru.gaqrcode;

//用藥時間
public class HT {
    private String id;
    private String department;
    private String number;
    private String date;

    public HT(String id, String department, String number, String date) {
        this.id = id;
        this.department = department;
        this.number = number;
        this.date = date;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) { this.department = department; }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
