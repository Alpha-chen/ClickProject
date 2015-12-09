package com.example.xpg.lbs.sms.utl;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SendEmailActivity  {
	private Button send; 
	private EditText userid; 
	private EditText password; 
	private EditText from; 
	private EditText to; 
	private EditText subject; 
	private EditText body;

    public SendEmailActivity(){

    }
    /** Called when the activity is first created. */
    public void onCreate()
    {
               	 MailSenderInfo mailInfo = new MailSenderInfo();
                 mailInfo.setMailServerHost("smtp.qq.com");    
                 mailInfo.setMailServerPort("25");    
                 mailInfo.setValidate(true);    
                 mailInfo.setUserName("1336746854@qq.com");  //��������ַ
                 mailInfo.setPassword("");//�����������
                 mailInfo.setFromAddress("841740273@qq.com");
                 mailInfo.setToAddress(to.getText().toString());    
                 mailInfo.setSubject(subject.getText().toString());    
                 mailInfo.setContent(body.getText().toString());    
                 
                    //�������Ҫ�������ʼ�   
                 SimpleMailSender sms = new SimpleMailSender();   
                     sms.sendTextMail(mailInfo);//���������ʽ    
                     //sms.sendHtmlMail(mailInfo);//����html��ʽ 

                } 
}