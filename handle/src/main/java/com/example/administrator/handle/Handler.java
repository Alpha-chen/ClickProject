package com.example.administrator.handle;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

/**
 * Created by xupangen
 * on 2015/10/29.
 * e-mail: xupangen@ffrj.net
 */
class MyHandler extends Handler {

    public Activity activity;
    public MyHandler(Activity activity){
        this.activity=activity;
    }


    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
    }
}
