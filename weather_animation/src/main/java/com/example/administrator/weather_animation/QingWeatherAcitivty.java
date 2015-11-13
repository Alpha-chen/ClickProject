package com.example.administrator.weather_animation;

import com.example.administrator.weather_animation.util.BaseActivity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 晴天
 * Created by xupangen
 * on 2015/11/10.
 * e-mail: xupangen@ffrj.net
 */
public class QingWeatherAcitivty extends BaseActivity{
    private  String TAG=this.getClass().getSimpleName();
    /**第一片云朵**/
    private ImageView findCloud1;
    /**第二个云朵**/
    private ImageView findCloud2;

    private LinearLayout qing;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qing);
        initView();
        initEvent();

    }

    public void translateAnimRun(final View view)
    {
        DisplayMetrics outDisplayMetrice =new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outDisplayMetrice);
        float hight= outDisplayMetrice.heightPixels;
        float width= outDisplayMetrice.widthPixels;
//        ObjectAnimator animatorCloud1=ObjectAnimator.ofFloat(findCloud1,"translationX",-80.0F,width);
//        animatorCloud1.setInterpolator(new LinearInterpolator());
//        animatorCloud1.setRepeatMode(ObjectAnimator.RESTART);

        ObjectAnimator animatorLinear=ObjectAnimator.ofFloat(qing,"translationX",-80.0F,width);
        animatorLinear.setDuration(3000);
        animatorLinear.setInterpolator(new LinearInterpolator());
        animatorLinear.setRepeatCount(-1);
        animatorLinear.start();

    }
    @Override
    public void initView() {
        super.initView();
        findCloud1= (ImageView) findViewById(R.id.fine_day_cloud1);
        qing= (LinearLayout) findViewById(R.id.qing_ll);

    }

    @Override
    public void initData() {
        super.initData();

    }

    @Override
    public void initEvent() {
        super.initEvent();


    }


}
