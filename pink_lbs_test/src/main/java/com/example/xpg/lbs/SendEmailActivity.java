package com.example.xpg.lbs;

import com.example.xpg.lbs.util.BaseActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ProgressBar;

/**
 * Created by xupangen
 * on 2015/12/9.
 * e-mail: xupangen@ffrj.net
 */
public class SendEmailActivity extends BaseActivity {
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sendEmail();
    }

    private void sendEmail() {
        Intent  intent  = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:841740273@qq.com"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "这是一个android客户端发送的测试信息标题");
        intent.putExtra(Intent.EXTRA_TEXT, "这是一个android客户端发送的测试信息");
        startActivity(intent);
    }
}
