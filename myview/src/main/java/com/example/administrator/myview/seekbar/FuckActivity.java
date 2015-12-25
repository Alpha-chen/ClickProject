package com.example.administrator.myview.seekbar;

import com.example.administrator.myview.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

/**
 * Created by xupangen
 * on 2015/12/25.
 * e-mail: xupangen@ffrj.net
 */
public class FuckActivity  extends Activity implements  SeekBar.OnSeekBarChangeListener{
    private ImageView selectView;
    private RelativeLayout move;
    private SeekBar mySeekBar;
    private static  String TAG="FuckActivity";
    private  int moveStep;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.b);

        selectView= (ImageView) findViewById(R.id.select);
        move= (RelativeLayout) findViewById(R.id.move);
        mySeekBar= (SeekBar) findViewById(R.id.mySeekBar);
        mySeekBar.setOnSeekBarChangeListener(this);

        moveStep=58;
        mySeekBar.setMax(8);
        mySeekBar.setProgress(5);
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        selectView.setImageResource(ImageResArray.getMoodInfoIcon(progress));
        move.layout(moveStep*progress,move.getTop(),moveStep*progress+move.getWidth(),move.getBottom());
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
