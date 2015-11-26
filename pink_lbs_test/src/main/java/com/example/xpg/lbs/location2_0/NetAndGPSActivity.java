package com.example.xpg.lbs.location2_0;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.example.xpg.lbs.R;
import com.example.xpg.lbs.util.BaseActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

/***
 * 混合定位模式
 */
public class NetAndGPSActivity extends BaseActivity implements LocationSource, AMapLocationListener, RadioGroup.OnCheckedChangeListener {
    private String TAG = getClass().getSimpleName();
    private AMap aMap;
    private MapView mapView;
    private OnLocationChangedListener mListener;
    private AMapLocationClient aMapLocationClient;
    private AMapLocationClientOption aMapLocationClientOption;
    private RadioGroup radioGroup;
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_and_gps2_0);
        mapView = (MapView) findViewById(R.id.map2_0);
        mapView.onCreate(savedInstanceState); // 这个必须要从写
        initView();

    }

    @Override
    public void initView() {
        super.initView();
        textView = (TextView) findViewById(R.id.location_errInfo_text2_0);
        if (null == aMap) {
            aMap = mapView.getMap();
            setUpMap();
        }
        radioGroup = (RadioGroup) findViewById(R.id.gps_radio_group2_0);
        radioGroup.setOnCheckedChangeListener(this);

    }

    private void setUpMap() {
        aMap.setLocationSource(this);
        aMap.setMyLocationEnabled(true);
        aMap.getUiSettings().setMyLocationButtonEnabled(true);
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
    }


    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (null == aMapLocationClient) {
            aMapLocationClient = new AMapLocationClient(getApplicationContext());
            aMapLocationClient.startLocation();
        } else {
            Log.d(TAG, "locationManagerProxy不为空,请检查locationManagerProxy是否remove");
        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (null != aMapLocationClient) {
            aMapLocationClient.onDestroy();
        }
        aMapLocationClient = null;

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.gps_rotate_button2_0:
                aMap.setMyLocationType(AMap.LOCATION_TYPE_MAP_ROTATE);
                break;
            case R.id.gps_follow_button2_0:
                aMap.setMyLocationType(AMap.LOCATION_TYPE_MAP_FOLLOW);
                break;
            case R.id.gps_locate_button2_0:
                aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
                break;
            default:
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        deactivate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener != null && null != aMapLocation) {
            textView.setVisibility(View.VISIBLE);
            if (aMapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(aMapLocation);
                textView.setText("是否有精度:" + aMapLocation.hasAccuracy() + "  " + "精度:" + aMapLocation.getAccuracy() + "\r\n" + "经度:" +
                        aMapLocation.getLongitude() + "\r\n" + "纬度:" + aMapLocation.getLatitude() + "\r\n" + "海拔高度:" + aMapLocation.hasAltitude() +
                        "  海拔高度:" + aMapLocation.getAltitude() + "\r\n" + "地址:" + aMapLocation.getAddress() + "\r\n" + "是否当前朝向:" + aMapLocation.hasBearing() +
                        "  当前朝向(相对于北方):" + aMapLocation.getBearing() + "\r\n" + "是否有速度:" + aMapLocation.hasSpeed() + "  速度:" + aMapLocation.getSpeed() +
                        "\r\n" + "位置提供者:" + aMapLocation.getProvider());
            } else {
                textView.setText("出错了:" + aMapLocation.getErrorInfo());
            }
        }

    }



}
