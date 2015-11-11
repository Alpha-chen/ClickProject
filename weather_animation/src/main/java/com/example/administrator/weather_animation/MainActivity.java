package com.example.administrator.weather_animation;

import com.example.administrator.weather_animation.adapter.WeatherListAdapter;
import com.example.administrator.weather_animation.util.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

/***
 * 显示可显示的动画效果
 */
public class MainActivity extends BaseActivity {
    /**跳转到不同天气的listView**/
    private ListView weather_listView;
    private String TAG=this.getClass().getSimpleName();
    private List<Class<? extends BaseActivity>> activity=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        iniViewData();
    }

    @Override
    public void initView() {
        super.initView();
        weather_listView= (ListView) findViewById(R.id.weather_listView);
        activity= new ArrayList<>();
        weather_listView.setOnItemClickListener(this);
    }

    @Override
    public void iniViewData() {
        super.iniViewData();
        List data= new ArrayList(){};
        String []  dataArray=getResources().getStringArray(R.array.wether);
        int temp = dataArray.length;
        for (int n =0;n<temp;n++){
            data.add(n,dataArray[n]);
            Log.d(TAG,data.get(n).toString());
        }
        activity.add(QingWeatherAcitivty.class);
        activity.add(DuoYunAcitivity.class);
        WeatherListAdapter weatherListAdapter = new WeatherListAdapter(MainActivity.this,data);
        weather_listView.setAdapter(weatherListAdapter);
        weather_listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        super.onItemClick(parent, view, position, id);
        Log.d(TAG, "view=" + view + "\r" + "position=" + position + "\r" + "id=" + id);
        Intent intent = new Intent();
        if (position>activity.size()||null==activity.get(position)){
            Toast.makeText(MainActivity.this,"还没有加载。。。",Toast.LENGTH_LONG).show();
            return;
        }
        intent.setClass(MainActivity.this,activity.get(position));
        startActivity(intent);
    }
}
