package com.example.xpg.lbs.location2_0;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
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
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 显示附近的POI
 * Created by xupangen
 * on 2015/11/26.
 * e-mail: xupangen@ffrj.net
 */
public class POIListActivity extends BaseActivity implements AMapLocationListener, RadioGroup.OnCheckedChangeListener, PoiSearch.OnPoiSearchListener, GeocodeSearch.OnGeocodeSearchListener {
    private String TAG = getClass().getSimpleName();
    private AMapLocationClient aMapLocationClient;
    private AMapLocationClientOption aMapLocationClientOption;
    private GeocodeSearch geocodeSearch;

    private TextView titleView;
    private ProgressBar isSearchPoi;
    private EditText isSearchPoiET;
    private ListView resultPOI;
    private POIListAdapter adapter;
    private List<PoiItem> data = null;
    private double location_longtitude;
    private double location_latitude;
    private RadioGroup radioGroup;
    private String myCity;
    private String cityCode;

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
        radioGroup = (RadioGroup) findViewById(R.id.radio_adress);
        radioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void initData() {
        super.initData();
        // 进入界面的定位
        setUpAMapClient();

        //根据地理地址查找位置
        geocodeSearch = new GeocodeSearch(POIListActivity.this);
        geocodeSearch.setOnGeocodeSearchListener(this);
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
                searchAroundPOI(aMapLocation);
            } else {
                Toast.makeText(POIListActivity.this, aMapLocation.getErrorInfo(), Toast.LENGTH_LONG).show();
                Log.e(TAG, "errorCode=" + aMapLocation.getErrorCode() + "\r\n" + "错误信息=" + aMapLocation.getErrorInfo());
            }
        }
    }


    /***
     * 搜索周边
     */
    public void searchAroundPOI(AMapLocation amaplocation) {
        PoiSearch.Query query = new PoiSearch.Query(amaplocation.getRoad(),"", amaplocation.getCityCode());
        query.setLimitDiscount(false);
        query.setLimitGroupbuy(false);
        query.setPageSize(20);
        query.setPageNum(0);
        PoiSearch poiSearch = new PoiSearch(this, query);
//        PoiSearch.SearchBound bound = new PoiSearch.SearchBound(new LatLonPoint(39.908127, 116.375257), 2000 , true);
        PoiSearch.SearchBound bound = new PoiSearch.SearchBound(new LatLonPoint(location_latitude, location_longtitude), 1000, true);
        poiSearch.setBound(bound);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        if (i == 0) {
            if (null != poiResult && poiResult.getQuery() != null) {
                isSearchPoi.setVisibility(View.GONE);
//                resultPOI.setVisibility(View.VISIBLE);
                PoiResult mMoiResult = poiResult;
                int pages = mMoiResult.getPageCount();
                myCity = poiResult.getPois().get(0).getSnippet();
                cityCode = poiResult.getPois().get(0).getCityCode();
                Log.d(TAG, "返回周围POI的页数,每页10条共" + pages + "页");
                Log.e(TAG, "poiResult的值==" + poiResult.toString());
                data = mMoiResult.getPois();
                if (0 == data.size()) {
                    List<SuggestionCity> suggestionCity = poiResult.getSearchSuggestionCitys();
                    for (SuggestionCity s : suggestionCity) {
                        Log.d(TAG, "建议的城市:" + s.getCityName());
                    }
                }
                adapter = new POIListAdapter(POIListActivity.this, data);
                resultPOI.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

        }
    }

    /***
     * 逆地理编码 搜索回调
     *
     * @param regeocodeResult
     * @param i
     */
    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        if (i == 0) {
            if (null != regeocodeResult) {
                RegeocodeAddress regocodeAdress = regeocodeResult.getRegeocodeAddress();
                data = regocodeAdress.getPois();
                for (PoiItem poiItem : data) {
                    Log.d(TAG, "逆地理编码周边:" + poiItem.getSnippet());
                }
                Log.d(TAG, "逆地理编码请求返回数据集合大小:" + data.size());
                adapter = new POIListAdapter(POIListActivity.this, data);
                resultPOI.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(POIListActivity.this, "返回的数据是空", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(POIListActivity.this, "出错了:" + "错误码" + i, Toast.LENGTH_LONG).show();
        }
    }

    /****
     * 地理地址编码回调
     *
     * @param geocodeResult
     * @param i
     */
    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
        if (i == 0) {
            if (null != geocodeResult) {
                List<GeocodeAddress> geocodeAdress = geocodeResult.getGeocodeAddressList();
                for (GeocodeAddress ge : geocodeAdress) {
                    Log.d(TAG, "输出地理编码对应的坐标:" + "经度:" + ge.getLatLonPoint().getLatitude() + "   纬度:" + ge.getLatLonPoint().getLongitude());
                }
//                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(POIListActivity.this, "返回的数据是空", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(POIListActivity.this, "出错了:" + "错误码" + i, Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        switch (checkedId) {

            case R.id.regeocodeAdress: // 逆地理编码请求
                regecodeRequest(new LatLonPoint(location_latitude, location_longtitude));
                break;
            case R.id.gecoderAdress: // 地理编码请求
                gecoderRequest(myCity, cityCode);
                break;
            default:
                break;

        }

    }

    /****
     * 地理位置编码
     *
     * @param name     要请求的地理地理地址
     * @param cityCode
     */
    private void gecoderRequest(String name, String cityCode) {
        GeocodeQuery query = new GeocodeQuery(name, cityCode);
        geocodeSearch.getFromLocationNameAsyn(query);

    }

    /***
     * 逆地理编码
     */
    private void regecodeRequest(LatLonPoint latonPoint) {
        RegeocodeQuery regeoQuery = new RegeocodeQuery(latonPoint, 2000 * 2, GeocodeSearch.AMAP);
        geocodeSearch.getFromLocationAsyn(regeoQuery);

    }
}
