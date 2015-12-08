package com.example.xpg.lbs.amapLocationUtil;

import com.amap.api.location.AMapLocation;

/**
 * 定位请求 回调函数
 * Created by xupangen
 * on 2015/12/8.
 * e-mail: xupangen@ffrj.net
 */
public interface AMapLocationCallBack {
    public void onLocationSuccess(AMapLocation aMapLocation); // 定位成功
    public void onLocationFailed(int errorCode,String errorMessage); // 定位失败
//    public void UnCaughtException(int myErrorCode); // "1"表示返回的AMapLocation为空

}
