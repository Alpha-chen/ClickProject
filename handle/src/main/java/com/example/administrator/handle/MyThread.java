package com.example.administrator.handle;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.os.Environment;

/**
 * Created by xupangen
 * on 2015/10/29.
 * e-mail: xupangen@ffrj.net
 */
public class MyThread extends  Thread {

    private  long  currentTime;
    private Activity activity;

    public MyThread(Activity activity){
        this.activity=activity;
        currentTime= System.currentTimeMillis();
    }


    @Override
    public void run() {
        super.run();
        URL url=null;
        URLConnection urlConnection=null;
        FileOutputStream fileOutputStream=null;
        try {
            fileOutputStream  = new FileOutputStream(Environment.getDownloadCacheDirectory()+"/my.txt");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
