package com.android.hcbd.socc.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Created by guocheng on 2017/4/19.
 */

public class BItmapUtils {
    /**
     * 图片转成string
     * @param bitmap
     * @return
     */
    public static String convertIconToString(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();// outputstream
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] appicon = baos.toByteArray();// 转为byte数组
        return Base64.encodeToString(appicon, Base64.DEFAULT);

    }

    /**
     * string转成bitmap
     * @param st
     */
    public static Bitmap convertStringToIcon(String st){
        Bitmap bitmap = null;
        try{
            byte[] bitmapArray;
            bitmapArray = Base64.decode(st, Base64.DEFAULT);
            bitmap =BitmapFactory.decodeByteArray(bitmapArray, 0,
                            bitmapArray.length);
            return bitmap;
        }catch (Exception e){
            return null;
        }
    }

}
