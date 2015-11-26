package com.example.xpg.lbs.location2_0;

import com.example.xpg.lbs.R;
import com.example.xpg.lbs.util.BaseActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**点击定位，选择定位，完成定位
 * Created by xupangen
 * on 2015/11/26.
 * e-mail: xupangen@ffrj.net
 */
public class SearchPOIActivity extends BaseActivity {
    private String TAG =getClass().getSimpleName();

    private Button testPoi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_poi);
        testPoi= (Button) findViewById(R.id.search_poi);
        testPoi.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent intent=new Intent();
        switch (v.getId()){
            case  R.id.search_poi:
                intent.setClass(SearchPOIActivity.this,POIListActivity.class);
                startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


}
