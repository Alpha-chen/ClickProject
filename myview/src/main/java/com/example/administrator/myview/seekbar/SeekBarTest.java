package com.example.administrator.myview.seekbar;

import com.example.administrator.myview.R;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by xupangen
 * on 2015/12/22.
 * e-mail: xupangen@ffrj.net
 */
public class SeekBarTest extends Activity {
    private SeekBar seekbar = null;

    private String startTimeStr = "19:30:33";

    private String endTimeStr = "21:23:21";

    private TextView text, startTime, endTime;

    /**
     * 视频组中第一个和最后一个视频之间的总时长
     */
    private int totalSeconds = 0;

    /**
     * 屏幕宽度
     */
    private int screenWidth;

    /**
     * 自定义随着拖动条一起移动的空间
     */
    private MoveLayout textMoveLayout;

    private ViewGroup.LayoutParams layoutParams;
    /**
     * 托动条的移动步调
     */
    private float moveStep = 0;
    private View view;
    private ImageView moods;
    RelativeLayout relativeLayout;
    ImageView imageView;
    ImageView imageView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b);
        screenWidth = getWindowManager().getDefaultDisplay().getWidth();
        LayoutInflater inflater = LayoutInflater.from(SeekBarTest.this);
        view = inflater.inflate(R.layout.mood, null);
        moods = (ImageView) view.findViewById(R.id.moods);
        layoutParams = new ViewGroup.LayoutParams(50, 50);
        textMoveLayout = (MoveLayout) findViewById(R.id.textLayout);
//        textMoveLayout.addView(view, layoutParams);
//        textMoveLayout.removeView(view);
        textMoveLayout.addView(view, layoutParams);
//        view.layout(0, 20, screenWidth, 80);
        view.layout(0, 0, screenWidth, 80);
        /**
         * findView
         */
        seekbar = (SeekBar) findViewById(R.id.seekbar);
        startTime = (TextView) findViewById(R.id.start_time);
        endTime = (TextView) findViewById(R.id.end_time);
        /**
         * setListener
         */
        seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListenerImp());

        searchVideos();

    }

    public void searchVideos() {
        startTime.setText("0");
        endTime.setText("11");
        view.setBackgroundColor(Color.argb(0, 255, 255, 255));
        totalSeconds = totalSeconds(startTimeStr, endTimeStr);
        seekbar.setEnabled(true);
        seekbar.setMax(10);
        seekbar.setProgress(1);
        moveStep = (float) (((float) screenWidth / (float) 110) * 10);

    }

    private class OnSeekBarChangeListenerImp implements
            SeekBar.OnSeekBarChangeListener {

        // 触发操作，拖动
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            Log.d("seekBar运行时候progress=", progress + "");
//            view.setBackgroundColor(Color.argb(progress * 10, 255, 255, 255));
            view.layout((int) (progress * moveStep), 20, screenWidth - (int) (progress * moveStep), 80);
//            text.layout((int) (progress * moveStep), 20, screenWidth, 80);

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

    /**
     * 计算连个时间之间的秒数
     */

    private static int totalSeconds(String startTime, String endTime) {

        String[] st = startTime.split(":");
        String[] et = endTime.split(":");

        int st_h = Integer.valueOf(st[0]);
        int st_m = Integer.valueOf(st[1]);
        int st_s = Integer.valueOf(st[2]);

        int et_h = Integer.valueOf(et[0]);
        int et_m = Integer.valueOf(et[1]);
        int et_s = Integer.valueOf(et[2]);

        int totalSeconds = (et_h - st_h) * 3600 + (et_m - st_m) * 60
                + (et_s - st_s);

        return totalSeconds;

    }

    /**
     * 根据当前选择的秒数还原时间点
     */

    private static String getCheckTimeBySeconds(int progress, String startTime) {

        String return_h = "", return_m = "", return_s = "";

        String[] st = startTime.split(":");

        int st_h = Integer.valueOf(st[0]);
        int st_m = Integer.valueOf(st[1]);
        int st_s = Integer.valueOf(st[2]);

        int h = progress / 3600;

        int m = (progress % 3600) / 60;

        int s = progress % 60;

        if ((s + st_s) >= 60) {

            int tmpSecond = (s + st_s) % 60;

            m = m + 1;

            if (tmpSecond >= 10) {
                return_s = tmpSecond + "";
            } else {
                return_s = "0" + (tmpSecond);
            }

        } else {
            if ((s + st_s) >= 10) {
                return_s = s + st_s + "";
            } else {
                return_s = "0" + (s + st_s);
            }

        }

        if ((m + st_m) >= 60) {

            int tmpMin = (m + st_m) % 60;

            h = h + 1;

            if (tmpMin >= 10) {
                return_m = tmpMin + "";
            } else {
                return_m = "0" + (tmpMin);
            }

        } else {
            if ((m + st_m) >= 10) {
                return_m = (m + st_m) + "";
            } else {
                return_m = "0" + (m + st_m);
            }

        }

        if ((st_h + h) < 10) {
            return_h = "0" + (st_h + h);
        } else {
            return_h = st_h + h + "";
        }

        return return_h + ":" + return_m + ":" + return_s;
    }
}
