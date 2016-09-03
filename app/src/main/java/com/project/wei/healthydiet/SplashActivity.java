package com.project.wei.healthydiet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Thread(){
            @Override
            public void run() {
                getDataFromServer();
            }
        }.start();

    }

    private void getDataFromServer() {
     /*   HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, GlobalConstants.HealthyDietURL,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String result = responseInfo.result;
                        Log.i("tag",result+"111111111111111111111111111111");
                        //把获取出来的json数据存放起来
                        //解析json数据
                        processData(result);
                    }

                    @Override
                    public void onFailure(com.lidroid.xutils.exception.HttpException e, String s) {

                    }

                });*/
        try {
            URL url = new URL(GlobalConstants.HealthyDietURL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(5000);
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                InputStream inputStream = urlConnection.getInputStream();
                String stringFromInputstream = MyHttpUtils.getStringFromInputstream(inputStream);
                try {
                    JSONObject jsonObject = new JSONObject(stringFromInputstream);
                    String result = jsonObject.getString("result");
                    processData(stringFromInputstream);
                    Log.i("TAG",result);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void processData(String result) {
        //利用Gson框架来解析json数据，一定搞懂原理
        Gson gson = new Gson();
        //把解析出来的数据存放到了NewsMenu类中
        MillionMenus millionMenus = gson.fromJson(result, MillionMenus.class);
        String s = millionMenus.toString();
        Log.i("tag",s);

    }

}
