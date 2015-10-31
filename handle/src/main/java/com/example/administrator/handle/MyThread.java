package com.example.administrator.handle;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

/**
 * Created by xupangen
 * on 2015/10/29.
 * e-mail: xupangen@ffrj.net
 */
public class MyThread extends  Thread {

    private  long  currentTime;
    private MainActivity activity;

    public MyThread(MainActivity activity){
        this.activity=activity;
        currentTime= System.currentTimeMillis();
        Looper.prepare();
    }

    @Override
    public void run() {
        URL url=null;
        HttpURLConnection urlConnection=null;
        FileOutputStream fileOutputStream=null;
        InputStream inputStream = null;
        try {
            File file  = new File(Environment.getExternalStorageDirectory()+"/my.txt");
            if(!file.exists()){
                file.createNewFile();
            }
            fileOutputStream  = new FileOutputStream(Environment.getExternalStorageDirectory()+"/my.txt");
            url= new URL("http://blog.sina.com.cn/s/blog_4b2e0e6101015j55.html");
            urlConnection= (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            inputStream= urlConnection.getInputStream();
            byte[] a ;
            StringBuilder sb= new StringBuilder();
            while (inputStream.read()!=-1){
                fileOutputStream.write(inputStream.read());
//                bufferedOutputStream.write(a,0,0);
                sb.append(inputStream.read());
            }
            Looper.loop();
            System.out.print(sb.toString());
            Message message = Message.obtain();
            Bundle bundle = new Bundle();
            message.arg1=1;
            bundle.putString("data", sb.toString());
            message.setData(bundle);
            activity.getHandler().sendMessage(message);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.run();
    }
}
