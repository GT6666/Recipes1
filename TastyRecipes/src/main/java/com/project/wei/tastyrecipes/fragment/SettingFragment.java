package com.project.wei.tastyrecipes.fragment;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.util.Log;

import com.project.wei.tastyrecipes.R;


/**
 * Created by tomchen on 2/27/16.
 */
public class SettingFragment extends PreferenceFragment {

    private static final String TAG = "SettingFragment";
    private CheckBoxPreference cbp_save_net;
    private CheckBoxPreference cbp_push_news;
    private CheckBoxPreference cbp_day_nghit_;
    private CheckBoxPreference cbp_control_music;

    private  Preference pf_clear_cache;
    private  Preference pf_check_update;
    private  Preference pf_about_us;
    private  Preference pf_current_version;


    private ListPreference list_choice;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        cbp_save_net = (CheckBoxPreference) findPreference("cbp_save_net");
        cbp_push_news = (CheckBoxPreference) findPreference("cbp_push_news");
        cbp_day_nghit_ = (CheckBoxPreference) findPreference("cbp_day_nghit_");
        cbp_control_music = (CheckBoxPreference) findPreference("cbp_control_music");

        pf_clear_cache = (Preference) findPreference("pf_clear_cache");
        pf_check_update = (Preference) findPreference("pf_check_update");
        pf_about_us = (Preference) findPreference("pf_about_us");
        pf_current_version=(Preference) findPreference("pf_current_version");

        list_choice = (ListPreference) findPreference("list_choice");


        MyPreferenceClickListener myPreferenceClickListener = new MyPreferenceClickListener();
        // 设置监听器
        cbp_save_net.setOnPreferenceClickListener(myPreferenceClickListener);
        cbp_push_news.setOnPreferenceClickListener(myPreferenceClickListener);
        cbp_day_nghit_.setOnPreferenceClickListener(myPreferenceClickListener);
        cbp_control_music.setOnPreferenceClickListener(myPreferenceClickListener);



        // 得到我们的存储Preferences值的对象，然后对其进行相应操作
        /*SharedPreferences shp = PreferenceManager.getDefaultSharedPreferences(this);
        boolean apply_wifiChecked = shp.getBoolean("apply_wifi", false);*/


    }

    //对于CheckBoxPreference点击事件  onPreferenceClick onPreferenceTreeClick都会触发
    //对于Preference点击事件  只有onPreferenceTreeClick会触发  所以Preference跳转操作在这里执行
    class MyPreferenceClickListener extends PreferenceActivity implements Preference.OnPreferenceClickListener,  Preference.OnPreferenceChangeListener{
        //点击事件触发
        @Override
        public boolean onPreferenceClick(Preference preference) {
            Log.i(TAG, "onPreferenceClick----->"+String.valueOf(preference.getKey()));
            // 对控件进行操作
            operatePreference(preference);
            return false;
        }

        //当Preference的值发生改变时触发该事件，true则以新值更新控件的状态，false则do noting
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            Log.i(TAG, "onPreferenceChange----->"+String.valueOf(preference.getKey()));
            if (preference.getKey().equals("cbp_save_net")){                  //省流量模式
                Log.i(TAG, " cbp_save_net ="+ cbp_save_net.isChecked());
                return true;
            }else if(preference.getKey().equals("cbp_push_news")){              //新闻是否推送
                Log.i(TAG, " cbp_push_news ="+ cbp_push_news.isChecked());
                return true;
            }else if(preference.getKey().equals("cbp_day_nghit_")){              //夜间模式
                Log.i(TAG, " cbp_day_nghit_ isCheckd ="+ cbp_day_nghit_.isChecked());
                return true;
            }else if(preference.getKey().equals("cbp_control_music")){              //推送音乐关闭
                Log.i(TAG, " cbp_control_music isCheckd ="+ cbp_control_music.isChecked());
                return true;
            }
            return true;  //保存更新后的值
        }
    }

    // 对控件进行的一些操作
    private void operatePreference(Preference preference) {
        if (preference.getKey().equals("cbp_save_net")){                  //省流量模式路口
            Log.i(TAG, " Wifi CB, and isCheckd ="+ cbp_save_net.isChecked());
        }else if(preference.getKey().equals("cbp_push_news")){              //新闻是否推送路口
            Log.i(TAG, " Wifi CB, and isCheckd ="+ cbp_push_news.isChecked());
        }else if(preference.getKey().equals("cbp_day_nghit_")){              //夜间模式路口
            Log.i(TAG, " Wifi CB, and isCheckd ="+ cbp_day_nghit_.isChecked());
        }else if(preference.getKey().equals("cbp_control_music")){              //推送音乐关闭路口
            Log.i(TAG, " Wifi CB, and isCheckd ="+ cbp_control_music.isChecked());
        }
    }

    //这里进行Intent跳转操作  这个方法中可以启动Activity
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
                                         Preference preference) {
        Log.i(TAG, "onPreferenceTreeClick----->"+preference.getKey());
        // 对控件进行操作
        operatePreference(preference);
        //跳转操作示例如下
        /*if (preference.getKey().equals("cbp_save_net")) {
            // 创建一个新的Intent，
            // 函数如果返回true， 则跳转至该自定义的新的Intent ;
            // 函数如果返回false，则跳转至xml文件中配置的Intent ;
            *//*Intent i = new Intent(HelloPreference.this, OtherActivity.class);  //OtherActivity只是一个简单的Activity
            i.putExtra("type", "wifi");
            startActivity(i);*//*
            return true;
        }*/
        return false;
    }

}
