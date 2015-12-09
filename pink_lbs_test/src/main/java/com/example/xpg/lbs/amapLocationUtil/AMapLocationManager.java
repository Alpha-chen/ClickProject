package com.example.xpg.lbs.amapLocationUtil;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import android.content.Context;
import android.widget.Toast;

/**
 * 对定位进行管理，
 * Created by xupangen
 * on 2015/12/8.
 * e-mail: xupangen@ffrj.net
 */
public class AMapLocationManager implements AMapLocationListener {
    private Context context;
    private AMapLocationClient aMapLocationClient;
    private AMapLocationClientOption aMapLocationClientOption;
    private AMapLocationCallBack callBack;

    private boolean isDoing = true; // 防止用户在进行一次定位未完成时候频繁的点击

    public AMapLocationManager(Context context) {
        this.context = context;
    }

    public AMapLocationManager(Context context, AMapLocationClientOption option) {
        this.context = context;
        aMapLocationClientOption=option;
    }


    public void setAMapLocationCallBack(AMapLocationCallBack callBackListener){
        this.callBack=callBackListener;

    }
    private AMapLocationClientOption getStandardOption() {
        AMapLocationClientOption aMapLocationClientOption1 = new AMapLocationClientOption();
        aMapLocationClientOption1.setOnceLocation(true);
        aMapLocationClientOption1.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        aMapLocationClientOption1.setInterval(2000); // 网络请求间隔
        aMapLocationClientOption1.setHttpTimeOut(2000 * 10); // 定位请求超时
        aMapLocationClientOption1.setMockEnable(false);  // 不允许模拟地理位置
        return aMapLocationClientOption1;
    }

    /**
     * 创建AMapLocationClient 并初始化
     * @param option 定位的一些选项
     * @return
     */
    private void getInstance(AMapLocationClientOption option) {
        if (null == aMapLocationClient) {
            aMapLocationClient = new AMapLocationClient(context);
        }
        if (null == option) {
            aMapLocationClientOption = getStandardOption();
        }
        aMapLocationClient.setLocationOption(aMapLocationClientOption);
        aMapLocationClient.setLocationListener(this);
    }

    /**
     * 定位开始
     */
    public void startOnceLocation() {
        if (isDoing) {
            if (null==callBack){
                throw new NullPointerException("AMapLocationManager中AMapLocationCallBack为空");
            }
            getInstance(aMapLocationClientOption);
            aMapLocationClient.startLocation();
            isDoing = false;
        } else {
            Toast.makeText(context, "别急啊，上一次还没有OK", Toast.LENGTH_LONG).show();
        }
    }

    private void stopLocation() {
        aMapLocationClient.stopLocation();
        aMapLocationClient.unRegisterLocationListener(this);
        aMapLocationClient.onDestroy();
        callBack = null;
        isDoing = true;
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (null != aMapLocation) {
            int tempCode = aMapLocation.getErrorCode();
            if (tempCode == 0) {
                callBack.onLocationSuccess(aMapLocation);
                stopLocation();
            } else {
                callBack.onLocationFailed(tempCode, aMapLocation.getErrorInfo());
                if (tempCode == 27) {
                    Toast.makeText(context, "搜索失败,请检查网络连接！", Toast.LENGTH_LONG).show();
                } else if (tempCode == 32) {
                    Toast.makeText(context, "key验证无效！", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context,
                            "未知错误，请稍后重试!错误码为" + tempCode, Toast.LENGTH_LONG).show();
                }
                stopLocation();
            }

        } else {
//            callBack.UnCaughtException(1);
            stopLocation();
        }

    }
}
