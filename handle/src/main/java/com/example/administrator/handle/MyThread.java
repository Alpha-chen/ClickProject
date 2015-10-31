package com.example.administrator.handle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.os.Message;

/**
 * Created by xupangen
 * on 2015/10/29.
 * e-mail: xupangen@ffrj.net
 */
public class MyThread extends Thread {

    private long currentTime;
    private MainActivity activity;

    public MyThread(MainActivity activity) {
        this.activity = activity;
        currentTime = System.currentTimeMillis();

    }
    @Override
    public void run() {
        Looper.prepare();
        URL url = null;
        HttpURLConnection urlConnection = null;
        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
        try {
            File file = new File(Environment.getExternalStorageDirectory() + "/my.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            fileOutputStream = new FileOutputStream(file);
            url = new URL("http://www.bubuko.com/infodetail-589156.html");
//          url= new URL("http://blog.csdn.net/aboutjunjun/article/details/9040827");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.getContentEncoding();
//          urlConnection.setRequestMethod("POST");
            urlConnection.setConnectTimeout(1000);
            inputStream = urlConnection.getInputStream();
            InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");


//          sb.append(inputStream.read());
           int  n=0;
            if (inputStream.read() != -1) {
                fileOutputStream.write(inputStream.read());
                n++;
            }
            char[] buffer = new char[n];
            reader.read(buffer);
            Message message = Message.obtain();
            Bundle bundle = new Bundle();
            message.arg1 = 1;
            bundle.putString("data", new String(buffer));
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
        Looper.loop();
    }

}
