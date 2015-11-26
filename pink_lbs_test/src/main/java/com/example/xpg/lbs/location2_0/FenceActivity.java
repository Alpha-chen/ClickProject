package com.example.xpg.lbs.location2_0;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.Circle;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.example.xpg.lbs.R;
import com.example.xpg.lbs.util.BaseActivity;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 地图围栏
 * Created by xuPanGen
 * on 2015/11/25.
 * e-mail: xupangen@ffrj.net
 */
public class FenceActivity extends BaseActivity implements AMapLocationListener, AMap.OnMapClickListener, LocationSource, RadioGroup.OnCheckedChangeListener {
    private static final String GENFENCE_BROADCAST_ACTION = "com.location.apis.geofencedemo.broadcast";
    private String TAG = getClass().getSimpleName();
    private MapView mapView;
    private AMapLocationClient aMapLocationClient;
    private AMapLocationClientOption aMapLocationClientOption;
    private AMap aMap;
    private OnLocationChangedListener mListener;
    private Circle circle;
    private Marker mGpsMarker;
    private PendingIntent pendingIntent;

    private RadioGroup radioGroup;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_fence2_0);
        mapView = (MapView) findViewById(R.id.map_fence2_0);
        mapView.onCreate(savedInstanceState);
        initView();
        iniViewData();
    }

    @Override
    public void initView() {
        super.initView();
        if (null == aMap) {
            aMap = mapView.getMap();
            setUpMap();
        }
        radioGroup = (RadioGroup) findViewById(R.id.gps_radio_group_fence2_0);
        radioGroup.setOnCheckedChangeListener(this);
        textView = (TextView) findViewById(R.id.location_errInfo_text_fence2_0);

        MarkerOptions markerOptions = new MarkerOptions();

        Bitmap bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        canvas.drawCircle(0.0f, 0.0f, 1.0f, paint);
        textView.draw(canvas);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.location_marker))).anchor(0.5f, 0.5f);
        mGpsMarker = aMap.addMarker(markerOptions);
        aMap.setOnMapClickListener(this);
    }

    private void setUpMap() {
        aMap.setMyLocationEnabled(true);
        aMap.setLocationSource(this);
        aMap.getUiSettings().setMyLocationButtonEnabled(true);
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
        aMap.setOnMapClickListener(this);
    }

    private BroadcastReceiver myFenceBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(GENFENCE_BROADCAST_ACTION)) {
            }
            int status = intent.getExtras().getInt("event");
            if (1 == status) {
                Toast.makeText(FenceActivity.this, "进入区域内", Toast.LENGTH_LONG).show();
            } else if (2 == status) {
                Toast.makeText(FenceActivity.this, "离开区域内", Toast.LENGTH_LONG).show();
            }else if (8 == status) {
                Toast.makeText(FenceActivity.this, "驻留在区域内", Toast.LENGTH_LONG).show();
            }

        }
    };

    @Override
    public void iniViewData() {
        super.iniViewData();
        // 为广播创建一个filter
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(GENFENCE_BROADCAST_ACTION);
        // 注册广播
        registerReceiver(myFenceBroadcastReceiver, filter);

    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (null != mListener && null != aMapLocation) {
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


    @Override
    public void onMapClick(LatLng latLng) {
        aMapLocationClient.removeGeoFenceAlert(pendingIntent);
        if (null != circle) {
            circle.remove();
        }
        mGpsMarker.setPosition(latLng);
        aMapLocationClient.addGeoFenceAlert("1", latLng.latitude, latLng.longitude, 10, -1, pendingIntent);
        CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(latLng).radius(10).fillColor(Color.argb(150, 224, 171, 50)).strokeColor(Color.BLUE).strokeWidth(0.4f);
        circle = aMap.addCircle(circleOptions);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(myFenceBroadcastReceiver);
        aMapLocationClient.removeGeoFenceAlert(pendingIntent);
        aMapLocationClient.stopLocation();

        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        deactivate();
    }

    // 开始定位
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (null == aMapLocationClient && !aMapLocationClient.isStarted()) {
            Intent intent = new Intent(GENFENCE_BROADCAST_ACTION);
            pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
            aMapLocationClient = new AMapLocationClient(getApplicationContext());
            aMapLocationClientOption = new AMapLocationClientOption();
            aMapLocationClientOption.setWifiActiveScan(true);
            aMapLocationClientOption.setNeedAddress(true);
            aMapLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            aMapLocationClientOption.setInterval(1000 * 60);
            aMapLocationClientOption.setHttpTimeOut(1000 * 60 * 5);
            aMapLocationClientOption.isNeedAddress();
            aMapLocationClientOption.isGpsFirst();
            aMapLocationClient.setLocationOption(aMapLocationClientOption);
            aMapLocationClient.setLocationListener(this);
            aMapLocationClient.startLocation();
        } else {

        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (null != aMapLocationClient) {
            aMapLocationClient.removeGeoFenceAlert(pendingIntent);
            aMapLocationClient.stopLocation();
            aMapLocationClient.onDestroy();
        }
        aMapLocationClient = null;

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.gps_rotate_button_fence2_0:
                aMap.setMyLocationType(AMap.LOCATION_TYPE_MAP_ROTATE);
                break;
            case R.id.gps_follow_button_fence2_0:
                aMap.setMyLocationType(AMap.LOCATION_TYPE_MAP_FOLLOW);
                break;
            case R.id.gps_locate_button_fence2_0:
                aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
                break;
            default:
                break;
        }
    }
}
