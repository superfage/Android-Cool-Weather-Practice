package com.example.coolweather.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.coolweather.db.CoolWeatherOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class CoolWeatherDB {
    //数据库名
    public static final String DB_NAME="cool_weather";
    //数据库版本
    public static final int version = 1;
    private static CoolWeatherDB coolWeatherDB;
    private SQLiteDatabase db;

    //构造方法私有化
    private CoolWeatherDB(Context context){
        CoolWeatherOpenHelper dbHelper = new CoolWeatherOpenHelper(context,DB_NAME,null,version);
        db = dbHelper.getWritableDatabase();
    }
    //获取CoolWeatherDB的实例
    public synchronized static CoolWeatherDB getInstance(Context context){
        if(coolWeatherDB == null){
            coolWeatherDB = new CoolWeatherDB((context));
        }
        return coolWeatherDB;
    }
    //把Province实例存储到数据库
    public  void saveProvince(Province province){
        if(province != null) {
            ContentValues Values = new ContentValues();//存储数据用
            Values.put("province_name",province.getProvincename());
            Values.put("province_code",province.getProvincecode());
            db.insert("Province",null,Values);
        }
    }
    //从数据库读取全国所有的省份信息
    public List<Province> loadProvince(){
        List<Province> list = new ArrayList<Province>();
        Cursor cursor = db.
                query("Province",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do {
                Province province = new Province();
                province.setID(cursor.getInt(cursor.getColumnIndex("id")));
                province.setProvincename((cursor.getString((cursor.getColumnIndex("province_name")))));
                province.setProvincecode((cursor.getString(cursor.getColumnIndex("province_code"))));
                list.add(province);
            }while (cursor.moveToNext());
        }
        return list;
    }
    //把city存储到数据库
    public void saveCity(CIty city){
        if(city != null) {
            ContentValues Values = new ContentValues();//存储数据用
            Values.put("city_name",city.getCityname());
            Values.put("city_code",city.getCitycode());
            Values.put("province_id",city.getProvinceid());
            db.insert("City",null,Values);
        }
    }
    //从数据库读取省内所有城市的信息
    public List<CIty> loadCity(int provinceid){
        List<CIty> list = new ArrayList<CIty>();
        Cursor cursor = db.
                query("City",null,"province_id =?",new String[]{String.valueOf(provinceid)},null,null,null);
        if(cursor.moveToFirst()){
            do {
                CIty city = new CIty();
                city.setID(cursor.getInt(cursor.getColumnIndex("id")));
                city.setCityname((cursor.getString((cursor.getColumnIndex("city_name")))));
                city.setCitycode((cursor.getString(cursor.getColumnIndex("city_code"))));
                city.setProvinceid(provinceid);
                list.add(city);
            }while (cursor.moveToNext());
        }
        return list;
    }
    //把county存储到数据库
    public void saveCounty(County county){
        if(county != null) {
            ContentValues Values = new ContentValues();//存储数据用
            Values.put("county_name",county.getCountyname());
            Values.put("county_code",county.getCountycode());
            Values.put("city_id",county.getCityId());
            db.insert("County",null,Values);
        }
    }
    //从数据库读取城市内所有县城的信息
    public List<County> loadCounty(int cityid){
        List<County> list = new ArrayList<County>();
        Cursor cursor = db.
                query("County",null,"city_id =?",new String[]{String.valueOf(cityid)},null,null,null);
        if(cursor.moveToFirst()){
            do {
                County county=new County();
                county.setID(cursor.getInt(cursor.getColumnIndex("id")));
                county.setCountyname((cursor.getString((cursor.getColumnIndex("county_name")))));
                county.setCountycode((cursor.getString(cursor.getColumnIndex("county_code"))));
                county.setCityId(cityid);
                list.add(county);
            }while (cursor.moveToNext());
        }
        return list;
    }
}
