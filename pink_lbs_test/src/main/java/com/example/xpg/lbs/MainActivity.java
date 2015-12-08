package com.example.xpg.lbs;

import com.amap.api.location.AMapLocation;
import com.example.xpg.lbs.amapLocationUtil.AMapLocationCallBack;
import com.example.xpg.lbs.amapLocationUtil.AMapLocationManager;
import com.example.xpg.lbs.location2_0.Main2_0Activity;
import com.example.xpg.lbs.util.BaseActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by xupangen
 * on 2015/11/26.
 * e-mail: xupangen@ffrj.net
 */
public class MainActivity extends BaseActivity implements AMapLocationCallBack{
    private TextView mText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);

        mText= (TextView) findViewById(R.id.test1);
        setUpLocation();
    }

    private void setUpLocation() {
        AMapLocationManager aMapLocationManager= new AMapLocationManager(MainActivity.this);
        aMapLocationManager.startOnceLocation();
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent intent = new Intent();
        switch (v.getId()) {

            case R.id.button:
                break;
            case R.id.button2:
                intent.setClass(MainActivity.this, Main2_0Activity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }


    @Override
    public void onLocationSuccess(AMapLocation aMapLocation) {
        mText.setText(aMapLocation.getAddress());
    }

    @Override
    public void onLocationFailed(int errorCode, String errorMessage) {

    }

}
