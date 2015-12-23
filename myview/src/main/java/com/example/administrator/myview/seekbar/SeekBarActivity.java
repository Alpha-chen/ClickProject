package com.example.administrator.myview.seekbar;

import com.example.administrator.myview.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

/**
 * Created by xupangen
 * on 2015/12/22.
 * e-mail: xupangen@ffrj.net
 */
public class SeekBarActivity extends Activity {
    private SeekBar seekbar = null;
    /**
     * 屏幕宽度
     */
    private int screenWidth;
    /**
     * 自定义随着拖动条一起移动的空间
     */
    private RelativeLayout textMoveLayout;

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

    public void searchVideos() {
        seekbar.setEnabled(true);
        seekbar.setMax(9);
        Log.d("tag1", screenWidth + "");
        moveStep = (float) (((screenWidth - 80 )/ 9));
        Log.d("tag", moveStep + "");

    }

    private class OnSeekBarChangeListenerImp implements
            SeekBar.OnSeekBarChangeListener {

        // 触发操作，拖动
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            Log.d("width", textMoveLayout.getWidth() + "");
            Log.d("height", textMoveLayout.getHeight() + "");
            Log.d("tagProgress",progress+"");
            textMoveLayout.layout((int) (progress * moveStep), textMoveLayout.getTop(), textMoveLayout.getWidth() + (int) (progress * moveStep) , textMoveLayout.getBottom());
            imageView.setAlpha((float) (progress % 9 * 0.1));
            Log.d("aa", progress % 9 * 0.1 + "");
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
