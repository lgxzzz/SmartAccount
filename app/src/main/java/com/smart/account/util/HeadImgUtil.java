package com.smart.account.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class HeadImgUtil {
    public static String sdpath = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static String filename ="head.bmp";

    public static void saveBitmap(Bitmap bitmap){
        FileOutputStream fos = null;
        try {
            File f = new File(sdpath ,filename);
            if (f.exists()) {
                f.delete();
            }

            fos = new FileOutputStream(f);
            if (null != fos) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);
                fos.flush();
                fos.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Bitmap getHeadBitmap(Context context){
        Bitmap bitmap = null;
        try{
            BitmapFactory.Options options=new BitmapFactory.Options();
            options.inSampleSize=(int)7.5; /*图片长宽方向缩小倍数*/
            options.inJustDecodeBounds=false;

            File file = new File(sdpath,filename);
            Uri uri = Uri.fromFile(file);
            bitmap =BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri), null, options);

        }catch (Exception e){
            e.printStackTrace();
        }
        return bitmap;
    }

}
