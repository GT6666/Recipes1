package com.project.wei.tastyrecipes.utils;

import android.content.Context;

import com.project.wei.tastyrecipes.bean.ChannelItem;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by wei on 2016/9/5 0005.
 */
public class OutputUtil {
    public static boolean writeListIntoSDcard(Context context,String fileName, ArrayList<ChannelItem> list){
       /* if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            File sdCardDir = Environment.getExternalStorageDirectory();//获取sd卡目录
            File sdFile  = new File(sdCardDir, fileName);*/
            File file = new File(context.getFilesDir(),fileName);
            try {
                FileOutputStream fos = new FileOutputStream(file);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(list);//写入
                fos.close();
                oos.close();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        return false;
    }
}
