package com.example.xpg.lbs;

import com.amap.api.location.AMapLocation;
import com.example.xpg.lbs.amapLocationUtil.AMapLocationCallBack;
import com.example.xpg.lbs.amapLocationUtil.AMapLocationManager;
import com.example.xpg.lbs.location2_0.Main2_0Activity;
import com.example.xpg.lbs.sms.utl.*;
import com.example.xpg.lbs.util.BaseActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by xupangen
 * on 2015/11/26.
 * e-mail: xupangen@ffrj.net
 */
public class MainActivity extends BaseActivity implements AMapLocationCallBack {
    private TextView mText;
    private AMapLocation aMapLocation;
//    private Handler mHanlder=new Handler();

    @Override
    public boolean handleMessage(Message msg) {
        if (msg.what == Constant.AMAPLOCATION_MESSAGE_SUCCESS) {
            aMapLocation = (AMapLocation) msg.obj;
            mText.setText(aMapLocation.getAddress());
        } else if (msg.what == Constant.AMAPLOCATION_MESSAGE_SUCCESS) {
            Toast.makeText(MainActivity.this, "errorCode=" + msg.getData().getInt("errorCode") + " " + "errorMessage=" + msg.getData().getString("errorMessage"), Toast.LENGTH_LONG).show();
        }
        return super.handleMessage(msg);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.email).setOnClickListener(this);

        mText = (TextView) findViewById(R.id.test1);
        setUpLocation();
    }

    private void setUpLocation() {
        AMapLocationManager aMapLocationManager = new AMapLocationManager(MainActivity.this);
        aMapLocationManager.setAMapLocationCallBack(this);
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
            case R.id.email:
                intent.setClass(MainActivity.this, SendEmailActivity.class);
                startActivity(intent);
            default:
                break;
        }
    }


    @Override
    public void onLocationSuccess(AMapLocation aMapLocation) {
        Message message = new Message();
        message.what = Constant.AMAPLOCATION_MESSAGE_SUCCESS;
        message.obj = aMapLocation;
        handler.sendMessage(message);
    }

    @Override
    public void onLocationFailed(int errorCode, String errorMessage) {
        Message message = new Message();
        message.what = Constant.AMAPLOCATION_MESSAGE_FAILURE;
        Bundle bundle = new Bundle();
        bundle.putString("errorMessage", errorMessage);
        bundle.putInt("errorCode", errorCode);
        message.setData(bundle);
        handler.sendMessage(message);
    }

}
