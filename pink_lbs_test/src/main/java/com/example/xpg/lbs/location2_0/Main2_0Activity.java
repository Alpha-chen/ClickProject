package com.example.xpg.lbs.location2_0;

import com.example.xpg.lbs.R;
import com.example.xpg.lbs.util.BaseActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Main2_0Activity extends BaseActivity {
    private String TAG = getClass().getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2_0);

        initView();
    }

    @Override
    public void initView() {
        super.initView();
        findViewById(R.id.step2_0).setOnClickListener(this);
        findViewById(R.id.step12_0).setOnClickListener(this);
        findViewById(R.id.step22_0).setOnClickListener(this);
        findViewById(R.id.step32_0).setOnClickListener(this);
    }



    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.step2_0:
                intent.setClass(Main2_0Activity.this, NetLocationActivity.class);
                startActivity(intent);
                break;
            case R.id.step12_0:
                intent.setClass(Main2_0Activity.this, NetAndGPSActivity.class);
                startActivity(intent);
                break;
            case R.id.step22_0:
                intent.setClass(Main2_0Activity.this, FenceActivity.class);
                startActivity(intent);
                break;
            case R.id.step32_0:
                intent.setClass(Main2_0Activity.this, SearchPOIActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }
}
