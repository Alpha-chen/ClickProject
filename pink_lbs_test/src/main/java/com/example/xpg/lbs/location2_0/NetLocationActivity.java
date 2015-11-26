package com.example.xpg.lbs.location2_0;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.xpg.lbs.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

/**
 * 网络定位示例
 */
public class NetLocationActivity extends Activity implements
        AMapLocationListener, OnClickListener {
    private AMapLocationClient aMapLocationClient;
    private AMapLocationClientOption aMapLocationClientOption;

    private TextView mLocationLatlngTextView;// 坐标信息
    private TextView mLocationAccurancyTextView;// 定位精确信息
    private TextView mLocationMethodTextView;// 定位方法信息
    private TextView mLocationTimeTextView;// 定位时间信息
    private TextView mLocationDesTextView;// 定位描述信息

    private TextView mLocationCountryTextView;// 所在国家
    private TextView mLocationProvinceTextView;// 所在省
    private TextView mLocationCityTextView;// 所在市
    private TextView mLocationCountyTextView;// 所在区县
    private TextView mLocationRoadTextView;// 所在街道
    private TextView mLocationCityCodeTextView;// 城市编码
    private TextView mLocationAreaCodeTextView;// 区域编码

    private Button mLocationTimeButton;// 修改定位时间按钮

    private Random mRandom = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 不显示程序的标题栏
        setContentView(R.layout.activity_net_location2_0);
        init();
        initView();
    }

    /**
     * 初始化定位
     */
    private void init() {
        // 初始化定位，只采用网络定位
        aMapLocationClient = new AMapLocationClient(getApplicationContext());
        aMapLocationClientOption = new AMapLocationClientOption();
        aMapLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        aMapLocationClientOption.setOnceLocation(true);
        aMapLocationClient.setLocationListener(this);
        aMapLocationClient.setLocationOption(aMapLocationClientOption);

    }

    /**
     * 初始化View
     */
    private void initView() {
        mLocationLatlngTextView = (TextView) findViewById(R.id.location_latlng_text2_0);
        mLocationAccurancyTextView = (TextView) findViewById(R.id.location_accurancy_text2_0);
        mLocationMethodTextView = (TextView) findViewById(R.id.location_method_text2_0);
        mLocationTimeTextView = (TextView) findViewById(R.id.location_time_text2_0);
        mLocationDesTextView = (TextView) findViewById(R.id.location_description_text2_0);
        mLocationCountryTextView = (TextView) findViewById(R.id.location_country_text2_0);
        mLocationProvinceTextView = (TextView) findViewById(R.id.location_province_text2_0);
        mLocationCityTextView = (TextView) findViewById(R.id.location_city_text2_0);
        mLocationCountyTextView = (TextView) findViewById(R.id.location_county_text2_0);
        mLocationRoadTextView = (TextView) findViewById(R.id.location_road_text2_0);
        mLocationAreaCodeTextView = (TextView) findViewById(R.id.location_area_code_text2_0);
        mLocationCityCodeTextView = (TextView) findViewById(R.id.location_city_code_text2_0);
        mLocationTimeButton = (Button) findViewById(R.id.location_time_button2_0);
        mLocationTimeButton.setOnClickListener(this);
    }


    @Override
    public void onLocationChanged(AMapLocation amapLocation) {

        if (amapLocation != null
                && amapLocation.getErrorCode() == 0) {
            Log.d("Net", "net------aMapLocation.toString()==" + amapLocation.toStr());
            // 定位成功回调信息，设置相关消息
            mLocationLatlngTextView.setText(amapLocation.getLatitude() + "  "
                    + amapLocation.getLongitude());
            mLocationAccurancyTextView.setText(String.valueOf(amapLocation
                    .getAccuracy()));
            mLocationMethodTextView.setText(amapLocation.getProvider());

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(amapLocation.getTime());

            mLocationTimeTextView.setText(df.format(date));
            mLocationDesTextView.setText(amapLocation.getAddress());
            mLocationCountryTextView.setText(amapLocation.getCountry());
            if (amapLocation.getProvince() == null) {
                mLocationProvinceTextView.setText("null");
            } else {
                mLocationProvinceTextView.setText(amapLocation.getProvince());
            }
            mLocationCityTextView.setText(amapLocation.getCity());
            mLocationCountyTextView.setText(amapLocation.getDistrict());
            mLocationRoadTextView.setText(amapLocation.getRoad());
            mLocationCityCodeTextView.setText(amapLocation.getCityCode());
            mLocationAreaCodeTextView.setText(amapLocation.getAdCode());
        } else {
            Log.e("AmapErr", "Location ERR:" + amapLocation.getErrorCode() + "=====erroInfo==" + amapLocation.getErrorInfo());
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        // 移除定位请求
        // 销毁定位
        aMapLocationClient.stopLocation();

    }

    protected void onDestroy() {
        super.onDestroy();
        aMapLocationClient.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        aMapLocationClient.startLocation();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }
}
