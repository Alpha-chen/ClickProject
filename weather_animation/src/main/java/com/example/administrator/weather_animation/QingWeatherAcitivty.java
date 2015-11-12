package com.example.administrator.weather_animation;

import com.example.administrator.weather_animation.util.BaseActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 晴天
 * Created by xupangen
 * on 2015/11/10.
 * e-mail: xupangen@ffrj.net
 */
public class QingWeatherAcitivty extends BaseActivity implements Animation.AnimationListener{
    private  String TAG=this.getClass().getSimpleName();
    /**第一片云朵**/
    private ImageView findCloud1;
    /**第二个云朵**/
    private ImageView findCloud2;
    private Animation animation1;
    private Animation animation2;
    private Animation animation3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qing);
        initView();
        initEvent();

    }

    public void rotateyAnimRun(final View view)
    {
        ObjectAnimator//
                .ofFloat(findCloud1, "translationX", 0.0F, 1000.0F)//
                .setDuration(500)//
                .start();
//        ObjectAnimator anim = ObjectAnimator//
//                .ofFloat(view, "zhy", 1.0F,  0.2F)//
//                .setDuration(500);//
//        anim.start();
//
//        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                float cVal = (Float) animation.getAnimatedValue();
//                view.setAlpha(cVal);
//                view.setScaleX(cVal);
//                view.setScaleY(cVal);
//            }
//        });
    }
    @Override
    public void initView() {
        super.initView();
        findCloud1= (ImageView) findViewById(R.id.fine_day_cloud1);
        findCloud2= (ImageView) findViewById(R.id.fine_day_cloud2);
    }

    @Override
    public void initData() {
        super.initData();

    }

    @Override
    public void initEvent() {
        super.initEvent();

    }


    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (null!=animation&&animation==animation1){
                Log.d(TAG,"animation1");
        }else if(null!=animation&&animation==animation2){
                Log.d(TAG,"animatino2");
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
