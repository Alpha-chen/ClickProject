package com.example.administrator.handle;

import android.os.Handler;
import android.os.Message;

/**
 * Created by xupangen
 * on 2015/10/29.
 * e-mail: xupangen@ffrj.net
 */
class MyHandler extends Handler {

    public MainActivity activity;

    public MyHandler(MainActivity activity) {
        this.activity = activity;
    }


    @Override
    public void handleMessage(Message msg) {
        if (1 == msg.arg1) {
            activity.updateUI(msg.getData());
        }
        super.handleMessage(msg);
    }
}
