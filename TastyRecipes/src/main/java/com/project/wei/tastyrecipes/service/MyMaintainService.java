package com.project.wei.tastyrecipes.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.project.wei.tastyrecipes.R;
import com.project.wei.tastyrecipes.activity.MainActivity;

public class MyMaintainService extends Service {

    private static final String TAG = "MyMaintainService";

    public MyMaintainService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG,"onStartCommand");
        flags = START_STICKY;
        Notification notification = new Notification(R.mipmap.ic_launcher,
                getString(R.string.app_name), System.currentTimeMillis());

        PendingIntent pendingintent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);
        notification.setLatestEventInfo(this, "uploadservice", "请保持程序在后台运行",
                pendingintent);
        return super.onStartCommand(intent, flags, startId);
    }
    //创建服务，并开启AlarmManager，进行时间间隔重复操作
    @Override
    public void onCreate() {
        Log.i(TAG,"onCreate");
        startForeground(0, new Notification());//最大程度，使其service不被kill掉
        //创建对象，action指向广播接受类
        Intent intent = new Intent("MYALARMRECEIVER");
        intent.putExtra("msg","我将隔一段时间发送给广播接收者");
        //创建PendingIntent对象封装Intent,由于使用广播，注意使用getBroadcast方法
        PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);

        //获取AlarmManager对象
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        //API设置闹钟参数
        Log.i(TAG,"currentTimeMillis="+System.currentTimeMillis());
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),1000*3, pi);
        super.onCreate();
    }

    //当服务关闭的时候，也把AlarmManager闹钟管理也给关闭了
    @Override
    public void onDestroy() {
        Log.i(TAG,"onDestroy");
        Intent intent = new Intent("MYALARMRECEIVER");
        PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);

        //要取消闹钟提示器，首先构建一个同样的内容（intent，包括向如同的目标接收器，但不包括extras，因为extras不用于判断intent的唯一性）
        //的pending intent,然后通过AlarmManager的cancel()进行删除
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pi);
        stopForeground(true);
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }
}
