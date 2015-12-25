package com.example.administrator.myview.seekbar;

import com.example.administrator.myview.R;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by xupangen
 * on 2015/12/22.
 * e-mail: xupangen@ffrj.net
 */
public class SeekBarTest extends Activity implements SeekBar.OnSeekBarChangeListener{
    private static String TAG = "TestMoodActivity----->";
    private SeekBar seekbar = null;
    /**
     * 屏幕宽度
     */
    private int screenWidth;
    /**
     * 自定义随着拖动条一起移动的空间
     */
    private RelativeLayout textMoveLayouts;
    private LinearLayout menses_mood_top;
    private LinearLayout aaaa;
    /**
     * 托动条的移动步调
     */
    private float moveStep = 0;
    private float moveSteps = 0;
    private ImageView imageView;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menses_mood_dialog1);
        screenWidth = getWindowManager().getDefaultDisplay().getWidth();
        textMoveLayouts = (RelativeLayout) findViewById(R.id.menses_mood_select_layout);
        imageView = (ImageView) findViewById(R.id.menses_mood_select);
        menses_mood_top= (LinearLayout) findViewById(R.id.menses_mood_top);
        text= new TextView(this);
        RelativeLayout.LayoutParams layoutParams= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        text.setTextColor(Color.argb(80,255,165,189));
        text.setLayoutParams(layoutParams);
        menses_mood_top.addView(text);
        text.layout(100, 0, 80, 200);
        /**
         * findView
         */
        seekbar = (SeekBar) findViewById(R.id.menses_mood_select_seekBar);
        /**
         * setListener
         */
        seekbar.setOnSeekBarChangeListener(this);
//        measure();
        searchVideos();

    }

    private void measure() {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        aaaa.measure(w, h);
        int height =aaaa.getMeasuredHeight();
        int width =aaaa.getMeasuredWidth();
        Log.d(TAG, "width=" + width + "\r\n" + "height=" + height);
    }

    @Override
    protected void onResume() {
        super.onResume();
        moveSteps = (((float) ( (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics())
        ) ));
        Log.d(TAG,"aaadafsadfa="+textMoveLayouts.getWidth());
        moveStep = (float)((screenWidth-textMoveLayouts.getWidth()-moveSteps)/8);

        Log.d(TAG, "screenWidth=" + screenWidth);
        moveStep=58; // 此处的60 是根据布局算出来的，涉及到分辨率多设备的问题，要使用单位转为进行设置
        Log.d(TAG + "moveStep", moveStep + "");
    }

    public void searchVideos() {
        seekbar.setEnabled(true);
        seekbar.setMax(8);
        Log.d("tag1", screenWidth + "");
    }
    private void setMyMoodIcon(int progress) {
        imageView.setImageResource(ImageResArray.getMoodInfoIcon(progress));
    }

    @Override
    // 触发操作，拖动
    public void onProgressChanged(SeekBar seekBar, int progress,
                                  boolean fromUser) {
        moveLayout(progress);
        Log.d(TAG + "moveLayout-getWidth", textMoveLayouts.getWidth() + "");
        Log.d(TAG + "seekBarWidth", seekBar.getWidth() + "");
        Log.d(TAG + "tagProgress*moveStep=", progress*moveStep + "");
        Log.d(TAG + "moveLayout.getLeft=", textMoveLayouts.getLeft() + "");
        setMyMoodIcon(progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    private void moveLayout(int progress) {
        text.layout((int) (progress * moveStep),text.getTop(),text.getWidth()+(int) (progress * moveStep),text.getBottom());
        text.setText(progress + "");
        textMoveLayouts.layout((int) (progress * moveStep), textMoveLayouts.getTop(), textMoveLayouts.getWidth() + (int) (progress * moveStep), textMoveLayouts.getBottom());
        text.invalidate();
        textMoveLayouts.invalidate();
    }
}
