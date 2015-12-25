package com.example.administrator.myview.seekbar;

import com.example.administrator.myview.R;

/**
 * Created by xupangen
 * on 2015/12/25.
 * e-mail: xupangen@ffrj.net
 */
public class ImageResArray {

    /***
     * 小秘密身心情图标
     */
    public static int  getMoodInfoIcon(int position){
        int []  myIcon=new int[]{
                R.drawable.ic_launcher, R.drawable.ic_launcher,
                R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher,
                R.drawable.ic_launcher, R.drawable.ic_launcher,
                R.drawable.ic_launcher, R.drawable.ic_launcher,
        };
        if (position>myIcon.length&&position<0){
            return -1;
        }
        return myIcon[position];
    }
}
