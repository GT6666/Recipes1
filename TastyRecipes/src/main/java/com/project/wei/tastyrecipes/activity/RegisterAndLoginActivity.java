package com.project.wei.tastyrecipes.activity;
import android.support.v7.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.project.wei.tastyrecipes.R;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;

public class RegisterAndLoginActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "RegisterActivity";
    private TextView tv_RegisterAndLoginActivity_register;
    private TextView tv_RegisterAndLoginActivity_login;
    private TextView tv_RegisterAndLoginActivity_Weibo;
    private TextView tv_RegisterAndLoginActivity_Qq;
    private PlatformActionListener actionListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_and_login);
        getSupportActionBar().hide();
        //初始化ShareSDK
        ShareSDK.initSDK(this);
        tv_RegisterAndLoginActivity_register = (TextView) findViewById(R.id.tv_RegisterAndLoginActivity_register);
        tv_RegisterAndLoginActivity_login = (TextView) findViewById(R.id.tv_RegisterAndLoginActivity_login);
        tv_RegisterAndLoginActivity_Weibo = (TextView) findViewById(R.id.tv_RegisterAndLoginActivity_Weibo);
        tv_RegisterAndLoginActivity_Qq = (TextView) findViewById(R.id.tv_RegisterAndLoginActivity_Qq);
        //AlertDialog
        new AlertDialog.Builder(this)
                .setTitle(R.string.if_register_needed)
                .setMessage(R.string.after_auth)
                .setPositiveButton(R.string.tpl_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //确定可以进行操作

                        //finish();
                    }
                }).show();
        //设置一个监听事件
        tv_RegisterAndLoginActivity_register.setOnClickListener(this);
        tv_RegisterAndLoginActivity_login.setOnClickListener(this);
        tv_RegisterAndLoginActivity_Weibo.setOnClickListener(this);
        tv_RegisterAndLoginActivity_Qq.setOnClickListener(this);

        //第三方登录回调三个函数，可在onComplete里拿数据
        actionListener = new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                Log.d(TAG, "成功页面");
                Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
                Platform qq = ShareSDK.getPlatform(QQ.NAME);
                if(qq!=null) {
                    Log.d(TAG, qq.getDb().getToken());
                    Log.d(TAG, weibo.getDb().getToken());
                    Log.d(TAG, qq.getDb().exportData());
                    Log.d(TAG, qq.getDb().getPlatformNname());
                    Log.d(TAG, qq.getDb().getTokenSecret());
                    Log.d(TAG, qq.getDb().getUserGender());
                    Log.d(TAG, qq.getDb().getUserIcon());
                    Log.d(TAG, qq.getDb().getUserId());
                    Log.d(TAG, qq.getDb().getUserName());
                    Log.d(TAG, qq.getDb().getExpiresIn() + "");
                    Log.d(TAG, qq.getDb().getExpiresTime() + "");
                }
                if(weibo!=null) {
                    Log.e(TAG, weibo.getDb().getUserId()); //获取用户id
                    Log.e(TAG, weibo.getDb().getUserName());//获取用户名称
                    Log.e(TAG, weibo.getDb().getUserIcon());//获取用户头像
                }

            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {

                Log.d(TAG, "错误页面");

            }

            @Override
            public void onCancel(Platform platform, int i) {

                Log.d(TAG, "取消授权");

            }
        };
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.tv_RegisterAndLoginActivity_register:
                Log.i(TAG,"register");
                break;
            case R.id.tv_RegisterAndLoginActivity_login:
                Log.i(TAG,"login");
                break;
            case R.id.tv_RegisterAndLoginActivity_Weibo:
                Log.i(TAG,"Weibo");
                openWeiboAuthorize();
                break;
            case R.id.tv_RegisterAndLoginActivity_Qq:
                Log.i(TAG,"Qq");
                openQQAuthorize();
                break;
        }
    }

    //QQ登录
    private void openWeiboAuthorize() {
        Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
        weibo.setPlatformActionListener(actionListener);
        weibo.authorize();
        //weibo.SSOSetting(true);//not use SSO login
        //weibo.showUser(null);// 获取用户信息
    }
    //微博登录
    private void openQQAuthorize() {
        Platform qq = ShareSDK.getPlatform(QQ.NAME);
        qq.setPlatformActionListener(actionListener);
        qq.authorize();
        //qq.SSOSetting(true);//not use SSO login
        //qq.showUser(null);// 获取用户信息
    }
}
