package com.example.coolweather.util;

import android.text.TextUtils;

import com.example.coolweather.model.CIty;
import com.example.coolweather.model.CoolWeatherDB;
import com.example.coolweather.model.County;
import com.example.coolweather.model.Province;

public class Utility {
    //处理服务器返回的省级数据
    public synchronized static boolean handleProvinceResponse(CoolWeatherDB coolWeatherDB,String response){
        if(!TextUtils.isEmpty(response)){
            String[] allProvince =response.split(",");
            if(allProvince != null && allProvince.length >0)
            {
                for(String p : allProvince)
                {
                    String[] array = p.split("\\|");
                    Province province = new Province();
                    province.setProvincecode(array[0]);
                    province.setProvincename(array[1]);
                    coolWeatherDB.saveProvince(province);
                }
                return true;
            }
        }
        return false;
    }
    //处理城市级数据
    public static boolean handleCityResponse(CoolWeatherDB coolWeatherDB,String response,int provinceid){
        if(!TextUtils.isEmpty(response))
        {
            String[] allCities = response.split(",");
            if(allCities != null&&allCities.length>0)
            {
                for(String c: allCities)
                {
                    String[] array = c.split("\\|");
                    CIty city = new CIty();
                    city.setCitycode(array[0]);
                    city.setCityname(array[1]);
                    city.setProvinceid(provinceid);
                    coolWeatherDB.saveCity(city);
                }
            }
            return true;
        }
        return false;
    }
    //处理县级数据
    public  static boolean handleCountyResponse(CoolWeatherDB coolWeatherDB,String response,int cityid){
        if(!TextUtils.isEmpty(response))
        {
            String[] allCountys = response.split(",");
            if(allCountys != null&&allCountys.length>0)
            {
                for(String c:allCountys)
                {
                    String[] array = c.split("\\|");
                    County county = new County();
                    county.setCountycode(array[0]);
                    county.setCountyname(array[1]);
                    county.setCityId(cityid);
                    coolWeatherDB.saveCounty(county);
                }
            }
            return true;
        }
        return false;
    }

}
