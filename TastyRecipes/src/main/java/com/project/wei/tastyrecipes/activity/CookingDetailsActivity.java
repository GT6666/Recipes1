package com.project.wei.tastyrecipes.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.project.wei.tastyrecipes.R;
import com.project.wei.tastyrecipes.domain.ClassifyDetail;

import java.util.List;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class CookingDetailsActivity extends Activity {



    @ViewInject(R.id.lv_cooking)
    private ListView lv_cooking;

    private List<ClassifyDetail.StepsBean> steps;
    private BitmapUtils bitmapUtils;
    @ViewInject(R.id.iv_show)
    private ImageView iv_show;
    @ViewInject(R.id.tv_show)
    private TextView tv_show;
    @ViewInject(R.id.tv_imtro)
    private TextView tv_imtro;
    @ViewInject(R.id.tv_burden)
    private TextView tv_burden;
    @ViewInject(R.id.tv_ingerdients)
    private TextView tv_ingerdients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooking_details);
        ViewUtils.inject(this);

        ClassifyDetail.DataBean dataBean = getIntent().getParcelableExtra("step");//获取数据
        steps = dataBean.getSteps();

        View headView = View.inflate(getApplicationContext(), R.layout.item_header, null);
        ViewUtils.inject(this,headView);
       /* iv_show = (ImageView) headView.findViewById(R.id.iv_show);
        tv_show = (TextView) headView.findViewById(R.id.tv_show);*/

        lv_cooking.addHeaderView(headView);// 添加头布局

        bitmapUtils = new BitmapUtils(getApplicationContext());

        bitmapUtils.display(iv_show,dataBean.albums.get(0));
        tv_show.setText(dataBean.title);
        tv_imtro.setText(dataBean.imtro);
        tv_burden.setText(dataBean.burden);
        tv_ingerdients.setText(dataBean.ingredients);

        lv_cooking.setAdapter(new CookingDetailAdapter());// 设置数据
    }

    class CookingDetailAdapter extends BaseAdapter {
        BitmapUtils bitmapUtils;
        public CookingDetailAdapter() {
             bitmapUtils = new BitmapUtils(getApplicationContext());
        }

        @Override
        public int getCount() {
            return steps.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(getApplicationContext(), R.layout.item_cookingdetail, null);
            ImageView iv_steps = (ImageView) view.findViewById(R.id.iv_steps);
            TextView tv_step = (TextView) view.findViewById(R.id.tv_step);
            bitmapUtils.display(iv_steps,steps.get(position).img);
            tv_step.setText(steps.get(position).step);
            return view;
        }
    }

    public void back(View v){
        finish();
    }


    public void share(View v){

        showShare();
    }

    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("one key share");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this);
    }
}
