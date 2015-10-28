package com.example.administrator.clickproject.ericssonlabs;

import com.example.administrator.clickproject.R;
import com.example.administrator.clickproject.zxing.activity.CaptureActivity;
import com.example.administrator.clickproject.zxing.encoding.EncodingHandler;
import com.example.administrator.clickproject.zxing.encoding.QRCodeUtil;
import com.google.zxing.WriterException;

import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class BarCodeTestActivity extends Activity {
	private TextView resultTextView;
	private EditText qrStrEditText;
	private ImageView qrImgImageView;

	private Bitmap logoBM=null;
	private Uri path=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        resultTextView = (TextView) this.findViewById(R.id.tv_scan_result);
        qrStrEditText = (EditText) this.findViewById(R.id.et_qr_string);
        qrImgImageView = (ImageView) this.findViewById(R.id.iv_qr_image);
        
        Button scanBarCodeButton = (Button) this.findViewById(R.id.btn_scan_barcode);
        scanBarCodeButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//打开扫描界面扫描条形码或二维码
				Intent openCameraIntent = new Intent(BarCodeTestActivity.this,CaptureActivity.class);
				startActivityForResult(openCameraIntent, 33);
			}
		});

		Button btn_album= (Button) findViewById(R.id.btn_album);
		btn_album.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent= new Intent(Intent.ACTION_GET_CONTENT);
				intent.setType("image/*");
				Intent wrapIntent=Intent.createChooser(intent,null);
				startActivityForResult(wrapIntent,55);
			}
		});

        Button generateQRCodeButton = (Button) this.findViewById(R.id.btn_add_qrcode);
        generateQRCodeButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final String contentString = qrStrEditText.getText().toString();
				if (!contentString.equals("")) {

					QRcodeAsynTask qRcodeAsynTask = new QRcodeAsynTask(path,contentString,BarCodeTestActivity.this);
					qRcodeAsynTask.execute();
                    // 根据字符串生成二维码图片并显示在界面上，第二个参数为图片的大小（350*350）
//						Bitmap qrCodeBitmap = EncodingHandler.createQRCode(contentString, 350);
//						qrImgImageView.setImageBitmap(qrCodeBitmap);
                }else {
                    Toast.makeText(BarCodeTestActivity.this, "Text can not be empty", Toast.LENGTH_SHORT).show();
                }

			}
		});


		qrImgImageView.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {

				return false;
			}
		});
    }

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		//处理扫描结果（在界面上显示）
		if ( 33 ==requestCode) {
			Bundle bundle = data.getExtras();
			String scanResult = bundle.getString("result");
			resultTextView.setText(scanResult);
		}else if (55==requestCode){
			if (null==data){
				Toast.makeText(this,"你没有选择任何图片",Toast.LENGTH_LONG).show();
				return;
			}else {
				path= Uri.parse(data.getDataString());
			}
		}
	}

	class   QRcodeAsynTask extends  AsyncTask<Object,Integer,Object>{
		private  Uri path=null;
		private  String content="";
		Context context;
	   	public 	QRcodeAsynTask(Uri path,String content,Context context){
			this.path=path;
			this.content= content;
			this.context=context;
		}
			@Override
			protected Object doInBackground(Object... params) {
				Bitmap	logoBM=null;
				try {
					logoBM= MediaStore.Images.Media.getBitmap(context.getContentResolver(), path);
				} catch (IOException e) {
					e.printStackTrace();
				}
				Bitmap bitmap = QRCodeUtil.createQRImage(content, 450, 450, logoBM, Environment.getExternalStorageDirectory().getAbsolutePath());
				return bitmap;
			}

		@Override
		protected void onPostExecute(Object o) {
			qrImgImageView.setImageBitmap((Bitmap)o);
			super.onPostExecute(o);
		}
	}
}
