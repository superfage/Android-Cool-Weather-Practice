package com.example.coolweather.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coolweather.R;
import com.example.coolweather.db.CoolWeatherOpenHelper;
import com.example.coolweather.model.CIty;
import com.example.coolweather.model.CoolWeatherDB;
import com.example.coolweather.model.County;
import com.example.coolweather.model.Province;
import com.example.coolweather.util.HttpCallbackListener;
import com.example.coolweather.util.HttpUtil;
import com.example.coolweather.util.Utility;

import java.util.ArrayList;
import java.util.List;

public class ChooseRreaActivity extends Activity {
    public static final int LEVEL_PROVINCE = 0;
    public static final int LEVEL_CITY=1;
    public static final int LEVEL_COUNTY=2;

    private ProgressBar progressBar;
    private TextView titleView;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private CoolWeatherDB coolWeatherDB;
    private List<String> dataList = new ArrayList<String>();
    private List<Province> provinceList;
    private List<CIty> cityList;
    private List<County> countyList;
    private Province selectedProvince;
    private CIty selectedCity;
    private County selectedCounty;
    private int currentLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.choose_area);
        listView = (ListView)findViewById(R.id.list_view);
        titleView = (TextView)findViewById(R.id.title_text);
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,dataList);
        listView.setAdapter(adapter);
        coolWeatherDB=CoolWeatherDB.getInstance(this);
        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(currentLevel == LEVEL_PROVINCE)
                {
                    selectedProvince = provinceList.get(position);
                    queryCities();
                }else if(currentLevel == LEVEL_CITY)
                {
                    selectedCity=cityList.get(position);
                    queryCounties();
                }
            }
        });
        queryProvinces();*/
    }

    //查询省份
    private void queryProvinces(){
        provinceList = coolWeatherDB.loadProvince();
        if(provinceList.size()>0){
            dataList.clear();
            for(Province p: provinceList){
                dataList.add(p.getProvincename());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            titleView.setText("china");
            currentLevel=LEVEL_PROVINCE;
        }
        else {
            queryFromSever(null,"province");
        }
    }
    //查询城市
    private void queryCities(){
        cityList = coolWeatherDB.loadCity(selectedProvince.getId());
        if(cityList.size()>0){
            dataList.clear();
            for(CIty c: cityList){
                dataList.add(c.getCityname());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            titleView.setText(selectedProvince.getProvincename());
            currentLevel=LEVEL_CITY;
        }
        else {
            queryFromSever(selectedProvince.getProvincecode(),"null");
        }
    }
    //查询县
    private void queryCounties(){
        countyList = coolWeatherDB.loadCounty(selectedCity.getId());
        if(countyList.size()>0){
            dataList.clear();
            for(County co:countyList)
            {
                dataList.add(co.getCountyname());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            titleView.setText(selectedCity.getCityname());
            currentLevel=LEVEL_COUNTY;
        }else {
            queryFromSever(selectedCity.getCitycode(),"country");
        }
    }
    //根据代号和类型从服务器下载数据
    private void queryFromSever(final String code,final String type){
        String address;
        if(!TextUtils.isEmpty(code)){
            address="http://www.weather.com.cn/data/list3/city"+code+".xml";
        }else {
            address = "http://www.weather.com.cn/data/list3/city.xml";
        }
        progressBar.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String respones) {
                boolean result = false;
                if("province".equals(type)){
                    result = Utility.handleProvinceResponse(coolWeatherDB,respones);
                }else if("city".equals(type)){
                    result = Utility.handleCityResponse(coolWeatherDB,respones,selectedProvince.getId());
                }else if("county".equals(type))
                {
                    result = Utility.handleCountyResponse(coolWeatherDB,respones,selectedCity.getId());
                }
                if(result){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.GONE);
                            if("province".equals(type))
                            {
                                queryProvinces();
                            }else if("city".equals(type)){
                                queryCities();
                            }else if("county".equals(type)){
                                queryCounties();
                            }
                        }
                    });
                }
            }


            @Override
            public void onError(Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(ChooseRreaActivity.this,"加载失败",Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }
    //捕获BACK确定返回之后的样式
    public void onBackPressed(){
        if(currentLevel == LEVEL_COUNTY){
            queryCities();
        }else if(currentLevel== LEVEL_CITY){
            queryCounties();
        }else {
            finish();
        }
    }

}
