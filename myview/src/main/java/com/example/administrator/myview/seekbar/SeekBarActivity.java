package com.example.administrator.myview.seekbar;

import com.example.administrator.myview.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
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
    /**
     * 托动条的移动步调
     */
    private float moveStep = 0;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a);
        screenWidth = getWindowManager().getDefaultDisplay().getWidth();
        textMoveLayout = (RelativeLayout) findViewById(R.id.textLayout);
        hahha = (LinearLayout) findViewById(R.id.hahha);
        imageView = (ImageView) findViewById(R.id.testIV);
        /**
         * findView
         */
        seekbar = (SeekBar) findViewById(R.id.seekbar);
        /**
         * setListener
         */
        seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListenerImp());

        searchVideos();

    }

    @Override
    protected void onResume() {
        super.onResume();
        moveStep = (((float) (screenWidth - (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics())
        ) ));
        moveStep = (float)((screenWidth-textMoveLayout.getWidth())/8);
        Log.d(TAG,"screenWidth="+screenWidth);
//        moveStep = (((float) (screenWidth / 9)));
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
            imageView.setAlpha((float) (progress % 8 * 0.1));
            Log.d(TAG + "aa", progress % 8 * 0.1 + "");
            Log.d(TAG + "moveLayout-getWidth", textMoveLayout.getWidth() + "");
            Log.d(TAG + "moveLayout-PaddingWidth", textMoveLayout.getLeft() + "");
            Log.d(TAG + "seekBarParent", hahha.getWidth() + "");
            Log.d(TAG + "seekBarWidth", seekBar.getWidth() + "");
            Log.d(TAG + "moveLayout-width", textMoveLayout.getWidth() + "");
            Log.d(TAG + "moveLayout-height", textMoveLayout.getHeight() + "");
            Log.d(TAG + "tagProgress", progress + "");
//            text.setText(getCheckTimeBySeconds(progress, startTimeStr));
        }

        // 表示进度条刚开始拖动，开始拖动时候触发的操作
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        // 停止拖动时候
        public void onStopTrackingTouch(SeekBar seekBar) {
            // TODO Auto-generated method stub
        }
    }


}
