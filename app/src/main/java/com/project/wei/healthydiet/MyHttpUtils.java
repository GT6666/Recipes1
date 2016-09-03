package com.project.wei.healthydiet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2016/8/4 0004.
 */
//把HttpURLConnection请求后返回的InputStream转换为String类型，还可以转换为字节数组，只要在最后不要toString就可以
public class MyHttpUtils {

    public static String getStringFromInputstream(InputStream inputStream){

        String s="";
        ByteArrayOutputStream byteArrayOutputStream =new ByteArrayOutputStream();
        byte[] bytes = new byte[1024];
        int len=-1;
        try {
            while ((len = inputStream.read(bytes,0,1024))!=-1){
                byteArrayOutputStream.write(bytes,0,len);
            }
            byteArrayOutputStream.close();
             s = byteArrayOutputStream.toString("GBK");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;

    }
}
