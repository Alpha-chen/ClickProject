package com.example.xpg.lbs.location2_0;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.example.xpg.lbs.R;
import com.example.xpg.lbs.util.BaseActivity;
import com.example.xpg.lbs.util.POIListAdapter;

import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 显示附近的POI
 * Created by xupangen
 * on 2015/11/26.
 * e-mail: xupangen@ffrj.net
 */
public class POIListActivity extends BaseActivity implements AMapLocationListener, PoiSearch.OnPoiSearchListener {
    private String TAG = getClass().getSimpleName();
    private AMapLocationClient aMapLocationClient;
    private AMapLocationClientOption aMapLocationClientOption;

    private TextView titleView;
    private ProgressBar isSearchPoi;
    private EditText isSearchPoiET;
    private ListView resultPOI;
    private POIListAdapter adapter;
    private List<PoiItem> data = null;
    private double location_longtitude;
    private double location_latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selesct_poi);
        initView();
        initData();
    }

    @Override
    public void initView() {
        super.initView();
        aMapLocationClient = new AMapLocationClient(getApplicationContext());
        aMapLocationClientOption = new AMapLocationClientOption();
        isSearchPoiET = (EditText) findViewById(R.id.test_poi);
        titleView = (TextView) findViewById(R.id.test_location2_0);
//        isSearchPoi = new ProgressBar(POIListActivity.this);
        isSearchPoi = (ProgressBar) findViewById(R.id.progressBar);
        resultPOI = (ListView) findViewById(R.id.poi_list);

    }

    @Override
    public void initData() {
        super.initData();
        setUpAMapClient();
    }

    private void setUpAMapClient() {
        aMapLocationClientOption.setOnceLocation(true);
        aMapLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        aMapLocationClient.setLocationOption(aMapLocationClientOption);
        aMapLocationClient.setLocationListener(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        aMapLocationClient.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        aMapLocationClient.stopLocation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        aMapLocationClient.startLocation();
    }


    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (null != aMapLocation) {
            if (aMapLocation.getErrorCode() == 0) {
                location_latitude = aMapLocation.getLatitude();
                location_longtitude = aMapLocation.getLongitude();
                titleView.setText(aMapLocation.getLatitude() + "  " + aMapLocation.getLongitude());
                try {
                    getPOI();
                } catch (AMapException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(POIListActivity.this, aMapLocation.getErrorInfo(), Toast.LENGTH_LONG).show();
                Log.e(TAG, "errorCode=" + aMapLocation.getErrorCode() + "\r\n" + "错误信息=" + aMapLocation.getErrorInfo());
            }
        }
    }

    public void getPOI() throws AMapException {
        // 设置搜索的便捷，矩形，原型，半径，是否按远近
        PoiSearch.SearchBound bound = new PoiSearch.SearchBound(new LatLonPoint(location_latitude, location_longtitude), 1000, true);
        final PoiSearch p = new PoiSearch(POIListActivity.this, new PoiSearch.Query("餐饮", "shanghai"));
        p.setBound(bound);
//        p.setOnPoiSearchListener(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                PoiResult poiResult=null;
                try {
                     poiResult = p.searchPOI();
                } catch (AMapException e) {
                    e.printStackTrace();
                }
                final PoiResult finalPoiResult = poiResult;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        isSearchPoi.setVisibility(View.GONE);
                        resultPOI.setVisibility(View.VISIBLE);
                        data = finalPoiResult.getPois();
                        Log.d(TAG, "poiResult=" + finalPoiResult.getPois().size());
                        adapter = new POIListAdapter(POIListActivity.this, data);
                        resultPOI.setAdapter(adapter);
                    }
                });
            }
        }).start();
//        PoiResult poiResult = p.searchPOI();
//        isSearchPoi.setVisibility(View.GONE);
//        resultPOI.setVisibility(View.VISIBLE);
//        data = poiResult.getPois();
//        Log.d(TAG, "poiResult=" + poiResult.toString());
//        adapter = new POIListAdapter(POIListActivity.this, data);
//        resultPOI.setAdapter(adapter);

    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        if (i == 0) {

            isSearchPoi.setVisibility(View.GONE);
            resultPOI.setVisibility(View.VISIBLE);
            data = poiResult.getPois();
            adapter = new POIListAdapter(POIListActivity.this, data);
            resultPOI.setAdapter(adapter);
        }
    }
}
