package com.example.coolweather.model;

public class CIty {
    private int id;
    private String Cityname;
    private  String Citycode;
    private int Provinceid;

    public int getId() {
        return id;
    }
    public void setID(int id)
    {
        this.id = id;
    }
    public String getCityname(){
        return Cityname;
    }
    public void setCityname(String name){
        this.Cityname= name;
    }
    public String getCitycode(){
        return Citycode;
    }
    public void setCitycode(String code)
    {
        this.Citycode = code;
    }

    public int getProvinceid() {
        return Provinceid;
    }

    public void setProvinceid(int provinceid) {
        this.Provinceid = provinceid;
    }
}
