package com.example.xpg.lbs;

import com.example.xpg.lbs.location2_0.Main2_0Activity;
import com.example.xpg.lbs.util.BaseActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by xupangen
 * on 2015/11/26.
 * e-mail: xupangen@ffrj.net
 */
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent intent = new Intent();
        switch (v.getId()) {

            case R.id.button:
                break;
            case R.id.button2:
                intent.setClass(MainActivity.this, Main2_0Activity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
