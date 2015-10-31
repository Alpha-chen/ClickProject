package com.example.administrator.handle;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity {

    private static MyHandler  myHandler;
    Dialog dialog=null;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        myHandler= new MyHandler(MainActivity.this);
//        looper= new Looper();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myHandler=null;
    }

    /***
     * t如果调用该方法的线程的生命周期与该acitivity的生命周期 不一致 就可能导致内存泄漏
     * @return
     */
    public static Handler getHandler(){
      return myHandler;
    }


    private void initView() {
        tv= (TextView) findViewById(R.id.my_test);
        findViewById(R.id.download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyThread  myThread=new MyThread(MainActivity.this);
                myThread.start();
                dialog = new Dialog(MainActivity.this);
                dialog.setTitle("正在下载....");
                dialog.setContentView(new ProgressBar(MainActivity.this));
                dialog.setCancelable(false);
                dialog.show();
            }
        });

    }

    public void updateUI(Bundle bundle){
        dialog.dismiss();
        tv.setText(bundle.getString("data"));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
