package com.example.administrator.weather_animation;

import com.example.administrator.weather_animation.util.BaseActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

/**
 * Created by xupangen
 * on 2015/11/13.
 * e-mail: xupangen@ffrj.net
 */
public class RainActivity extends BaseActivity {
    private ImageView rainxl;
    private ImageView rainCloud;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rain);

        initView();
    }


    @Override
    public void initView() {
        super.initView();
        rainxl= (ImageView) findViewById(R.id.rain_day);
        rainCloud= (ImageView) findViewById(R.id.fine_day_cloud2);

    }

    public  void rain(View view){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float height= displayMetrics.heightPixels;
        float width=displayMetrics.widthPixels;
        ObjectAnimator objectAnimator =ObjectAnimator.ofFloat(rainxl,"translationY",-200.0f,height);
        objectAnimator.setDuration(1500);
        objectAnimator.setRepeatCount(-1);
        objectAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        objectAnimator.setInterpolator(new DecelerateInterpolator());
//        objectAnimator.start();

        ObjectAnimator rainCloudor=ObjectAnimator.ofFloat(rainCloud,"translationX",-20.0f,-width);
        rainCloudor.setDuration(20000);
        rainCloudor.setInterpolator(new LinearInterpolator());
        rainCloudor.setRepeatCount(-1);
        rainCloudor.setRepeatMode(ObjectAnimator.RESTART);
        AnimatorSet set = new AnimatorSet();
        set.play(objectAnimator).with(rainCloudor);
        set.setDuration(2500);
        set.start();
    }
}
