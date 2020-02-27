package com.example.coolweather.model;

public class County {
    private int id;
    private String Countyname;
    private  String Countycode;
    private int CityId;

    public int getId() {
        return id;
    }
    public void setID(int id)
    {
        this.id = id;
    }
    public String getCountyname(){
        return Countyname;
    }
    public void setCountyname(String name){
        this.Countyname= name;
    }
    public String getCountycode(){
        return Countycode;
    }
    public void setCountycode(String code)
    {
        this.Countycode = code;
    }

    public int getCityId() {
        return CityId;
    }

    public void setCityId(int cityId) {
        CityId = cityId;
    }
}
