package com.example.administrator.weather_animation;

import com.example.administrator.weather_animation.util.BaseActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

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

    @Override
    public void initView() {
        super.initView();
        findCloud1= (ImageView) findViewById(R.id.fine_day_cloud1);
        findCloud2= (ImageView) findViewById(R.id.fine_day_cloud2);
    }

    @Override
    public void initEvent() {
        super.initEvent();
//        TranslateAnimation translateAnimation=new TranslateAnimation(Animation.RELATIVE_TO_SELF,
//                (float) -0.5, Animation.RELATIVE_TO_SELF,
//                (float) 1.7, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
        animation1= AnimationUtils.loadAnimation(QingWeatherAcitivty.this,R.anim.fine_day_cloud1_tranlate);
//        translateAnimation.setDuration(15000);
//        translateAnimation.setRepeatMode(TranslateAnimation.Rs);
        animation1.setAnimationListener(this);
        findCloud1.setAnimation(animation1);
        animation2=AnimationUtils.loadAnimation(QingWeatherAcitivty.this,R.anim.fine_day_cloud2_tranlate);

        animation2.setAnimationListener(this);
//      findCloud2.setAnimation(animation2);
        findCloud2.startAnimation(animation2);
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
