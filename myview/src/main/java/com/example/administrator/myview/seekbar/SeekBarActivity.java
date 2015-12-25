package com.example.administrator.myview.seekbar;

import com.example.administrator.myview.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

/**
 * Created by xupangen
 * on 2015/12/22.
 * e-mail: xupangen@ffrj.net
 */
public class SeekBarActivity extends Activity {
    private static String TAG = "SeekBarActivity----->";
    private SeekBar seekbar = null;
    /**
     * 屏幕宽度
     */
    private int screenWidth;
    /**
     * 自定义随着拖动条一起移动的空间
     */
    private RelativeLayout textMoveLayout;
    private LinearLayout hahha;
    private LinearLayout aaaa;
    /**
     * 托动条的移动步调
     */
    private float moveStep = 0;
    private float moveSteps = 0;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a);
        screenWidth = getWindowManager().getDefaultDisplay().getWidth();
        textMoveLayout = (RelativeLayout) findViewById(R.id.textLayout);
        imageView = (ImageView) findViewById(R.id.menses_mood_select);
        /**
         * findView
         */
        seekbar = (SeekBar) findViewById(R.id.seekbar);
        /**
         * setListener
         */
        seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListenerImp());


//        measure();
        searchVideos();
        Button button = (Button) findViewById(R.id.testMyButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(SeekBarActivity.this,FuckActivity.class);
                startActivity(intent);
            }
        });

    }

    private void measure() {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        aaaa.measure(w, h);
        int height =aaaa.getMeasuredHeight();
        int width =aaaa.getMeasuredWidth();
        Log.d(TAG,"width="+width+"\r\n"+"height="+height);
    }

    @Override
    protected void onResume() {
        super.onResume();
        moveSteps = (((float) ( (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics())
        ) ));
        Log.d(TAG,"aaadafsadfa="+textMoveLayout.getWidth());
        moveStep = (float)((screenWidth-textMoveLayout.getWidth()-moveSteps)/8);

        Log.d(TAG,"screenWidth="+screenWidth);
        moveStep=58; // 此处的60 是根据布局算出来的，涉及到分辨率多设备的问题，要使用单位转为进行设置
        Log.d(TAG + "moveStep", moveStep + "");
    }

    public void searchVideos() {
        seekbar.setEnabled(true);
        seekbar.setMax(8);
        Log.d("tag1", screenWidth + "");
    }

    private class OnSeekBarChangeListenerImp implements
            SeekBar.OnSeekBarChangeListener {

        // 触发操作，拖动
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {

            textMoveLayout.layout((int) (progress * moveStep), textMoveLayout.getTop(), textMoveLayout.getWidth() + (int) (progress * moveStep), textMoveLayout.getBottom());
            Log.d(TAG + "aa", progress % 8 * 0.1 + "");
            Log.d(TAG + "moveLayout-getWidth", textMoveLayout.getWidth() + "");
            Log.d(TAG + "moveLayout-PaddingWidth", textMoveLayout.getLeft() + "");
            Log.d(TAG + "seekBarWidth", seekBar.getWidth() + "");
            Log.d(TAG + "moveLayout-height", textMoveLayout.getHeight() + "");
            Log.d(TAG + "tagProgress", progress + "");
            setMyMood(progress);
        }

        // 表示进度条刚开始拖动，开始拖动时候触发的操作
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        // 停止拖动时候
        public void onStopTrackingTouch(SeekBar seekBar) {
            // TODO Auto-generated method stub
        }
    }
    private void setMyMood(int progress) {
        imageView.setImageResource(ImageResArray.getMoodInfoIcon(progress));
    }

}
