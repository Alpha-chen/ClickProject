package com.example.xpg.lbs;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.example.xpg.lbs.util.BaseActivity;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends BaseActivity {
    private String TAG = getClass().getSimpleName();
    private TextView mLocation;
    private LocationManagerProxy mLocationManagerProxy = null;
    /**
     * 广播出定位成功的数据
     */
    public static final String GPSLOCATION_BROADCAST_ACTION = "com.location.apis.gpslocationdemo.broadcast";
    /**
     * 创建一个延迟执行的PendingIntent
     */
    private PendingIntent mPendingIntent = null;
    private Handler mHadler = new Handler() {

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    @Override
    public void initView() {
        super.initView();
        mLocation = (TextView) findViewById(R.id.test_location);
        findViewById(R.id.step).setOnClickListener(this);
        findViewById(R.id.step1).setOnClickListener(this);
        findViewById(R.id.step2).setOnClickListener(this);
        findViewById(R.id.step3).setOnClickListener(this);
    }

    private BroadcastReceiver mGPSBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() != null && intent.getAction().equals(GPSLOCATION_BROADCAST_ACTION)) {
                mLocationManagerProxy.removeUpdates(mPendingIntent);
                Bundle bundle = intent.getExtras();
                Parcelable parcelable = bundle.getParcelable(LocationManagerProxy.KEY_LOCATION_CHANGED);
                Location location = (Location) parcelable;
                mLocation.setText(location.toString());
                Log.d(TAG, "通过广播返回的数据===" + location.getProvider());
            } else {
                mLocationManagerProxy.removeUpdates(mPendingIntent);
                mLocation.setText("未接受到返回的数据");

            }
        }
    };

    private void initGPS() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(GPSLOCATION_BROADCAST_ACTION);
        registerReceiver(mGPSBroadcastReceiver, filter);
        Intent intent = new Intent(GPSLOCATION_BROADCAST_ACTION);
        mPendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
        mLocationManagerProxy = LocationManagerProxy.getInstance(this);
        mLocationManagerProxy.requestLocationUpdates(LocationManagerProxy.GPS_PROVIDER, 2 * 6000, 5, mPendingIntent);
        mHadler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mLocationManagerProxy.removeUpdates(mPendingIntent);
//                Log.d(TAG, "出现改文字表示没用数据到广播中");
//                mLocationManagerProxy.setGpsEnable(false);
                mLocationManagerProxy.requestLocationData(LocationManagerProxy.GPS_PROVIDER, 2 * 6000, 5, new AMapLocationListener() {
                    @Override
                    public void onLocationChanged(AMapLocation aMapLocation) {
                        if (null == aMapLocation || aMapLocation.getAMapException().getErrorCode() != 0) {
                            Log.d(TAG, "出错了===" + "errorCode==" + aMapLocation.getAMapException().getErrorCode() + "\r\n" + "errorMessage=" + aMapLocation.getAMapException().getErrorMessage());
                        } else {
                            mLocation.setText(aMapLocation.toString());
                            Log.d(TAG, "aMapLocation.toString()==" + aMapLocation.toString());
                        }
                    }

                    @Override
                    public void onLocationChanged(Location location) {
                        Log.d(TAG, "location.toString()==" + location.toString());
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                });
            }

        }, 3 * 4000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initGPS();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLocationManagerProxy.removeUpdates(mPendingIntent);
        unregisterReceiver(mGPSBroadcastReceiver);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.step:
                intent.setClass(MainActivity.this, NetLocationActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
