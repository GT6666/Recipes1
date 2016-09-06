package com.project.wei.tastyrecipes.utils;

import android.content.Context;

import com.project.wei.tastyrecipes.bean.ChannelItem;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 * Created by wei on 2016/9/5 0005.
 */
public class InputUtil {
    public static ArrayList<ChannelItem> readListFromSdCard(Context context, String fileName) {
        /*if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {  //检测sd卡是否存在
            ArrayList<ChannelItem> list;
            File sdCardDir = Environment.getExternalStorageDirectory();
            File sdFile = new File(sdCardDir, fileName);*/
        ArrayList<ChannelItem> list;
        File file = new File(context.getFilesDir(), fileName);
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            list = (ArrayList<ChannelItem>) ois.readObject();
            fis.close();
            ois.close();
            return list;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }
}
