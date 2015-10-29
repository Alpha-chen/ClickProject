package com.example.administrator.clickproject.zxing.decoding;

import com.google.zxing.LuminanceSource;

import android.graphics.Bitmap;

public class BitmapLuance extends LuminanceSource {

    private byte[] bitmapPixels;

    public BitmapLuance(Bitmap bitmap) {

        super(bitmap.getWidth(), bitmap.getHeight());

        //初始化位图存放数组
        int[] data = new int[bitmap.getWidth() * bitmap.getHeight()];
        this.bitmapPixels = new byte[bitmap.getWidth() * bitmap.getHeight()];
        bitmap.getPixels(data, 0, getWidth(), 0, 0, getWidth(), getHeight());

        for (int i = 0; i < data.length; i++) {
            this.bitmapPixels[i] = (byte) data[i];
        }
    }

    @Override
    public byte[] getMatrix() {
        return bitmapPixels;
    }

    @Override
    public byte[] getRow(int y, byte[] row) {
        System.arraycopy(bitmapPixels, y * getWidth(), row, 0, getWidth());
        return row;
    }

}
