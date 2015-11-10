/*
 * Copyright (C) 2010 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.administrator.clickproject.zxing.decoding;

import com.example.administrator.clickproject.zxing.activity.CaptureActivity;
import com.example.administrator.clickproject.zxing.camera.CameraManager;
import com.example.administrator.clickproject.zxing.camera.PlanarYUVLuminanceSource;
import com.example.administrator.zxingscan.R;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

import java.io.IOException;
import java.util.Hashtable;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;

public final class DecodeHandler extends Handler {

	private static final String TAG = DecodeHandler.class.getSimpleName();

	private final CaptureActivity activity;
	private final MultiFormatReader multiFormatReader;

	DecodeHandler(CaptureActivity activity, Hashtable<DecodeHintType, Object> hints) {
		multiFormatReader = new MultiFormatReader();
		multiFormatReader.setHints(hints);
		this.activity = activity;
	}


	@Override
	public void handleMessage(Message message) {
		if (message.what == R.id.decode) {
			//Log.d(TAG, "Got decode message");
			// 此处的添加 判断是直接扫描还是图库选择
			if (1 == message.arg1) {
				decodePic(message.obj.toString());
			}else {
				decode((byte[]) message.obj, message.arg1, message.arg2);
			}

		} else if (message.what == R.id.quit) {
			Looper.myLooper().quit();
		}
	}

	/**
	 * Decode the data within the viewfinder rectangle, and time how long it took. For efficiency,
	 * reuse the same reader objects from one decode to the next.
	 *
	 * @param data   The YUV preview frame.
	 * @param width  The width of the preview frame.
	 * @param height The height of the preview frame.
	 */
	private void decode(byte[] data, int width, int height) {
		long start = System.currentTimeMillis();
		Result rawResult = null;

		//modify here
		byte[] rotatedData = new byte[data.length];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++)
				rotatedData[x * height + height - y - 1] = data[x + y * width];
		}
		int tmp = width; // Here we are swapping, that's the difference to #11
		width = height;
		height = tmp;

		PlanarYUVLuminanceSource source = CameraManager.get().buildLuminanceSource(rotatedData, width, height);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
		try {
			rawResult = multiFormatReader.decodeWithState(bitmap);
		} catch (ReaderException re) {
			// continue
		} finally {
			multiFormatReader.reset();
		}

		if (rawResult != null) {
			long end = System.currentTimeMillis();
			Log.d(TAG, "Found barcode (" + (end - start) + " ms):\n" + rawResult.toString());
			Message message = Message.obtain(activity.getHandler(), R.id.decode_succeeded, rawResult);
			Bundle bundle = new Bundle();
			bundle.putParcelable(DecodeThread.BARCODE_BITMAP, source.renderCroppedGreyscaleBitmap());
			message.setData(bundle);
			//Log.d(TAG, "Sending decode succeeded message...");
			message.sendToTarget();
		} else {
			Message message = Message.obtain(activity.getHandler(), R.id.decode_failed);
			message.sendToTarget();
		}
	}


	/***
	 * 从图库中选取图片完成解码
	 * @param uri
	 */
	private void decodePic(String uri) {
		Bitmap bm = null;
		try {
			// 从返回的URI中获取bitmap
			bm = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), Uri.parse(uri));
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (bm == null) {
			Log.d("deCode", "-----------------------null");
			return;
		}
		Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();
		hints.put(DecodeHintType.CHARACTER_SET, "UTF8"); // 设置二维码内容的编码
		MultiFormatReader multiFormatReader = new MultiFormatReader();
		multiFormatReader.setHints(hints);
		LuminanceSource source = new BitmapLuance(bm);
		BinaryBitmap bit = new BinaryBitmap(new HybridBinarizer(source));
		Result rawResult = null;
		try {
			rawResult = multiFormatReader.decodeWithState(bit);

		} catch (NotFoundException e) {
			e.printStackTrace();
		} finally {
			multiFormatReader.reset();
		}
		Handler handler = activity.getHandler();
		if (null != rawResult) {
			if (handler != null) {
				// 此时是网CaptureActivityHandler发送信息 "1"的含义变化了，注意！！！
				Message message = Message.obtain(handler, R.id.decode_succeeded, 1, 3, rawResult);
				message.sendToTarget();
			} else if (handler == null) {
				// "1"表示成功 "3"补位子
				handler = new CaptureActivityHandler(activity); //  创建CaptureActivityHandler
				Message message = Message.obtain(handler, R.id.decode_succeeded, 1, 3, rawResult);
				message.sendToTarget();
			}
		} else {
			handler = new CaptureActivityHandler(activity); //  创建CaptureActivityHandler
			Message message = Message.obtain(handler, R.id.decode_failed, 3, 3);
			Bundle bundle = new Bundle();
			bundle.putInt("faile", 3);
			message.setData(bundle);
			message.sendToTarget();
		}
	}

}
