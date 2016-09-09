package com.project.wei.tastyrecipes.receive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.util.Log;

import com.project.wei.tastyrecipes.service.MyMaintainService;

public class MyMaintainReceiver extends BroadcastReceiver {
    private static final String TAG = "MyMaintainReceiver";
    public static int i = 0;
    public MyMaintainReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        //throw new UnsupportedOperationException("Not yet implemented");
        if(intent!=null) {
            Log.i(TAG,"间隔服务广播已接受到！");
            String msg = intent.getStringExtra("msg");
            i++;
            //Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            Log.i(TAG,"onReceive="+msg+i);
            //以上为显示测试效果

            //从服务器端pull数据的操作,并像短信一样实现显示效果




        }

    }
}
