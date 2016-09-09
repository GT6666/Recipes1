package com.project.wei.tastyrecipes.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.project.wei.tastyrecipes.R;
import com.project.wei.tastyrecipes.fragment.SettingFragment;

public class SettingActivity extends AppCompatActivity {
    Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mToolbar =(Toolbar) findViewById(R.id.toolbar_preference);
        setSupportActionBar(mToolbar);
        initToolbar();
        getFragmentManager().beginTransaction().replace(R.id.content_frame, new SettingFragment()).commit();

    }
    private void initToolbar() {
        mToolbar.setTitle("设置");//这里改动
        mToolbar.setTitleTextColor(getResources().getColor(R.color.mypage_item_true));//文字颜色设置
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.icon_left_back);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * 选项菜单
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return false;
    }



    
}
