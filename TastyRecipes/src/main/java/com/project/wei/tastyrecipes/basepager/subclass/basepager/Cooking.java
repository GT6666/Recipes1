package com.project.wei.tastyrecipes.basepager.subclass.basepager;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.project.wei.tastyrecipes.basepager.BasePager;

/**
 * Created by Administrator on 2016/8/23 0023.
 */
public class Cooking extends BasePager{

    public Cooking(Activity activity) {
        super(activity);
    }
    @Override
    public void initData(){
        TextView textView = new TextView(mActivity);
        textView.setText("首页");
        textView.setTextSize(50);
        textView.setTextColor(Color.RED);
        textView.setGravity(Gravity.CENTER);
        fl_content.addView(textView);

        tv_title.setText("首页");//修改页面标题
        ibtn_menu.setVisibility(View.INVISIBLE);// 隐藏菜单按钮
    }
}
