package com.roundlers.mytemplate.helper;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class GenericHelper {

    public static String saveToInternalStorage(Context context, Bitmap bitmapImage, String file, float compressionRatio, Bitmap.CompressFormat compressFormat, String dirName) {
        ContextWrapper cw = new ContextWrapper(context.getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir

        File directory = cw.getDir(dirName, Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, file + ".jpg");


        if(mypath.exists())
        {
            return directory.getAbsolutePath();
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            if (compressionRatio > 0) {
                bitmapImage.compress(compressFormat, (int) compressionRatio, fos);
            } else {
                bitmapImage.compress(compressFormat, 50, fos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

    public static void saveQuestionImages(Context context, Bitmap bitmapImage, String file) {
        File directory = context.getFilesDir();
        // Create imageDir
        File mypath = new File(directory, file);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null)
                    fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String saveToExternalStorage(Bitmap bitmap, String fileName) {
        File directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyTemplate/");

        // Create imageDir
        if (!directory.exists()) {
            directory.mkdir();
        }
        File mypath = new File(directory, fileName);
        FileOutputStream fos = null;
        try {

            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return mypath.getAbsolutePath();
    }

    public static boolean ifFileExists(Context context, String localFileName) {
        return new File(context.getFilesDir(), localFileName).exists();
    }
}
