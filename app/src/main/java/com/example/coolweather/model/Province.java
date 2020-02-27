package com.example.coolweather.model;

public class Province {
    private int id;
    private String Provincename;
    private  String Provincecode;

    public int getId() {
        return id;
    }
    public void setID(int id)
    {
        this.id = id;
    }
    public String getProvincename(){
        return Provincename;
    }
    public void setProvincename(String name){
        this.Provincename= name;
    }
    public String getProvincecode(){
        return Provincecode;
    }
    public void setProvincecode(String code)
    {
        this.Provincecode = code;
    }
}
