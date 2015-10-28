package com.example.administrator.clickproject.ericssonlabs;

import com.example.administrator.clickproject.R;
import com.example.administrator.clickproject.zxing.activity.CaptureActivity;
import com.example.administrator.clickproject.zxing.decoding.BitmapLuance;
import com.example.administrator.clickproject.zxing.encoding.QRCodeUtil;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

import java.io.IOException;
import java.util.Hashtable;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/***
 * 主界面
 */
public class BarCodeTestActivity extends Activity {
	private TextView resultTextView;
	private EditText qrStrEditText;
	private ImageView qrImgImageView;

	private Bitmap logoBM=null;
	private Uri path=null;
	private View view=null;
	Dialog dialog=null;
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
				// 打开扫描界面扫描条形码或二维码
				Intent openCameraIntent = new Intent(BarCodeTestActivity.this,CaptureActivity.class);
				startActivity(openCameraIntent);
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
                }else {
                    Toast.makeText(BarCodeTestActivity.this, "Text can not be empty", Toast.LENGTH_SHORT).show();
                }
			}
		});


		qrImgImageView.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				dialog  = new Dialog(BarCodeTestActivity.this);
				view  = getLayoutInflater().inflate(R.layout.dialog, null);
				dialog.setContentView(view);
				dialog.setTitle("Tip");
				// 开发的过程中尽量不要使用final定义的变量 会造成额外的内存开销
				final Bitmap  bitmaps = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getAbsolutePath()+"/test.jpeg");

				// 1.使用asynTask 在多个异步操作的时候会让代码显得臃肿，可以看到下边使用两个asynTask，就已经很对了，较少的异步操作，比较适合
				// 2.而使用Thread的时候 单个异步操作的代码会相对冗余，使用runOnUiThread直接更新UI ，
				// 多个任务的时候，结合handler,message,looper，可对三者进行封装优化，代码整洁，清晰.
				// 3.二维码扫描解码，以及图库选择解码，代码整齐，结构清晰。

				// DecodePicAsyn decodePicAsyn = new DecodePicAsyn(bitmaps);
				// decodePicAsyn.execute();

				// 使用线程 完成解码，线程中使用runOnUiThread进行更新UI界面
				new Thread(new Runnable() {
					@Override
					public void run() {
						Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();
						hints.put(DecodeHintType.CHARACTER_SET, "UTF8"); // 设置二维码内容的编码
						MultiFormatReader multiFormatReader = new MultiFormatReader();
						multiFormatReader.setHints(hints);
						LuminanceSource source = new BitmapLuance(bitmaps);
						BinaryBitmap bit = new BinaryBitmap(new HybridBinarizer(source));
						Result rawResult = null;
						try {
							rawResult = multiFormatReader.decodeWithState(bit);

						} catch (NotFoundException e) {
							e.printStackTrace();
						} finally {
							multiFormatReader.reset();
						}

						final Result finalRawResult = rawResult;
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								dialog.show();
								if (null!=finalRawResult){
									view.findViewById(R.id.scan_tv).setVisibility(View.VISIBLE);
									view.setOnClickListener(new OnClickListener() {
										@Override
										public void onClick(View v) {
											dialog.dismiss();
											Toast.makeText(BarCodeTestActivity.this, finalRawResult.getText().toString(), Toast.LENGTH_LONG).show();
										}
									});
								}

							}
						});

					}
				}).start();
				return false;
			}
		});
    }

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// 处理扫描结果（在界面上显示）
		// 在进行判断的时候使用requestCode更加方便
		if (55==requestCode){
			if (null==data){
				Toast.makeText(this,"你没有选择任何图片",Toast.LENGTH_LONG).show();
				return;
			}else {
				path= Uri.parse(data.getDataString());
			}
		}
	}


	/***
	 *  长按 解码
	 */
	class DecodePicAsyn extends  AsyncTask<Object ,Integer ,Object>{
		private Bitmap bitmap=null;

		public DecodePicAsyn(Bitmap bitmap){
			this.bitmap= bitmap;
		}

		@Override
		protected Object doInBackground(Object... params) {
			Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();
			hints.put(DecodeHintType.CHARACTER_SET, "UTF8"); // 设置二维码内容的编码
			MultiFormatReader multiFormatReader = new MultiFormatReader();
			multiFormatReader.setHints(hints);
			LuminanceSource source = new BitmapLuance(bitmap);
			BinaryBitmap bit = new BinaryBitmap(new HybridBinarizer(source));
			Result rawResult = null;
			try {
				rawResult = multiFormatReader.decodeWithState(bit);

			} catch (NotFoundException e) {
				e.printStackTrace();
			} finally {
				multiFormatReader.reset();
			}
			return rawResult;
		}

		@Override
		protected void onPostExecute(Object o) {
			dialog.show();
			super.onPostExecute(o);
			if (null!=o){
				view.findViewById(R.id.scan_tv).setVisibility(View.VISIBLE);
				final Result result = (Result) o;
				view.findViewById(R.id.scan_tv).setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
						Toast.makeText(BarCodeTestActivity.this,result.getText().toString(),Toast.LENGTH_LONG).show();
					}
				});
			}
		}
	}

	/****
	 * 生成二维码中带图片
	 */
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
				//存储图片的名字
				String file="/"+"test.jpeg";
				Bitmap bitmap = QRCodeUtil.createQRImage(content, 450, 450, logoBM, Environment.getExternalStorageDirectory().getAbsolutePath(),file);
				return bitmap;
			}

		@Override
		protected void onPostExecute(Object o) {
			qrImgImageView.setImageBitmap((Bitmap)o);
			qrImgImageView.setFocusableInTouchMode(true);
			super.onPostExecute(o);
		}
	}
}
