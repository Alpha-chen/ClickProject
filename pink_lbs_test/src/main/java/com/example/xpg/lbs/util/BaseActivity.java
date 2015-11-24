package com.example.xpg.lbs.util;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

/**
 * 所有acticity的基类
 * Created by xupangen
 * on 2015/11/10.
 * e-mail: xupangen@ffrj.net
 */
public class BaseActivity extends Activity implements View.OnClickListener,AdapterView.OnItemClickListener{
    private String TAG=this.getClass().getSimpleName();

    /**
     *初始化布局
     */
    public void initView() {
    }

    /**
     * 初始化传入的数据
     */
    public void initData(){
    }

    /***
     * 为布局加载数据
     */
    public void iniViewData(){
    }

    /***
     * 初始化监听事件
     */
    public void initEvent(){
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG,"onActivityResult");
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG,"onClick");

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG,"onItemClick");

    }


}
