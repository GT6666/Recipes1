package com.project.wei.tastyrecipes.basepager.subclass.basemenudetailpager;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.project.wei.tastyrecipes.R;
import com.project.wei.tastyrecipes.activity.CookingDetailsActivity;
import com.project.wei.tastyrecipes.basepager.BaseMenuDetailPager;
import com.project.wei.tastyrecipes.domain.ClassifyDetail;
import com.project.wei.tastyrecipes.domain.MillionMenus;
import com.project.wei.tastyrecipes.global.GlobalConstants;
import com.project.wei.tastyrecipes.utils.CacheUtil;
import com.project.wei.tastyrecipes.utils.FlymeUtils;
import com.project.wei.tastyrecipes.utils.NetworkUtil;

import java.util.List;

/**
 * Created by Administrator on 2016/8/24 0024.
 */
//  继承BaseMenuDetailPager，并没有什么关联，只是刚好有它需要的方法
public class TabDetailPager extends BaseMenuDetailPager {

    private MillionMenus.ResultBean.ListBean mTabData;//单个页签的网络数据
//    private TextView view;

    @ViewInject(R.id.lv_news)
    private ListView lv_news;

    public  String mUrl;
    private ListNewsAdapter listNewsAdapter;
    private ClassifyDetail classifyDetail;
    private List<ClassifyDetail.DataBean> data;

    public TabDetailPager(Activity activity, MillionMenus.ResultBean.ListBean newsTabData) {
        super(activity);
        mTabData = newsTabData;
        mUrl = GlobalConstants.CLASSIFYDETAIL+ mTabData.id;//每个页面具体内容的url
    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.pager_tab_detail, null);
        ViewUtils.inject(this,view);

        // 设置listview的点击事件
        lv_news.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ClassifyDetail.DataBean dataBean = data.get(position);
                //                Log.i("qqqqq",data.get(position).steps.toString()+"11111111111111111111111");
                dataBean.setSteps(data.get(position).steps);
                Intent intent1 = new Intent(mActivity,CookingDetailsActivity.class);
                intent1.putExtra("step", dataBean);
                mActivity.startActivity(intent1);

            }
        });

        return view;
    }

    @Override
    public void initData() {
  /*      view.setText(mTabData.title);
        Log.i("tag",mTabData.title);*/
        //先去查看缓存，有的话先加载缓存，然后再去服务器请求数据
    /*    String cache = CacheUtil.getCache(mUrl, mActivity);
        if (!TextUtils.isEmpty(cache)) {
            processData(cache);
        }*/
        getDataFromServer();
    }

    private void getDataFromServer() {
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET,mUrl , new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                //把下载的json数据存放到缓存
                // 这里的缓存很重要，因为你只有把把json存到本地，
                // 才能根据路径找到BitmapUtils缓存的图片，否则，即使缓存了图片，也不会显示出来
                CacheUtil.setCache(mUrl,result,mActivity);
                processData(result);//解析数据
            }

            @Override
            public void onFailure(HttpException e, String s) {
                // 请求失败
                e.printStackTrace();
                Toast.makeText(mActivity, s, Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void processData(String result) {
        if (result == null) {
            Toast.makeText(mActivity,"Appkey 已用完！",Toast.LENGTH_LONG);
        }else {
            Gson gson = new Gson();
            classifyDetail = gson.fromJson(result, ClassifyDetail.class);

            // 列表新闻填充数据
            data = classifyDetail.result.data;
            Log.i("fffffffffff", data.toString());
            if (data != null) {
                listNewsAdapter = new ListNewsAdapter();
                lv_news.setAdapter(listNewsAdapter);
            }
        }
    }

    class ListNewsAdapter extends BaseAdapter {

        private  BitmapUtils bitmapUtils;

        public ListNewsAdapter() {
            bitmapUtils = new BitmapUtils(mActivity);
            bitmapUtils.configDefaultLoadingImage(R.drawable.pic_list_item_bg);
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public ClassifyDetail.DataBean getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(mActivity, R.layout.item_list_cookbook, null);
                holder = new ViewHolder();
                holder.imageViewPic = (ImageView) convertView.findViewById(R.id.iv_foodpic);
                holder.textViewName = (TextView) convertView.findViewById(R.id.tv_foodname);
                convertView.setTag(holder);
            } else {
                 holder = (ViewHolder) convertView.getTag();
            }
            ClassifyDetail.DataBean data = getItem(position);
            holder.textViewName.setText(data.title);
            bitmapUtils.display(holder.imageViewPic,data.albums.get(0));

            //省流量模式
            //wifi模式下正常显示
            if (NetworkUtil.isWifiAvailable(mActivity.getApplicationContext())) {
                holder.textViewName.setText(data.title);
                bitmapUtils.display(holder.imageViewPic,data.albums.get(0));
            }

            SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mActivity);
            //判断是否是小米和魅族手机，如果是那么就正常加载，因为系统判断是否是wifi状态失效。还没找到解决办法
            //小米手机未验证能否实现省流量，如果不行加上（| MIUIUtils.isMIUI()）这句
            if(FlymeUtils.isFlyme()){
                holder.textViewName.setText(data.title);
                bitmapUtils.display(holder.imageViewPic,data.albums.get(0));
            }else{
                //不是省流量模式，采用原来的方法
                if (!defaultSharedPreferences.getBoolean("cbp_save_net",false)) {
                    holder.textViewName.setText(data.title);
                    bitmapUtils.display(holder.imageViewPic,data.albums.get(0));
                } else {//是省流量模式，不加载数据
                    if (!NetworkUtil.isWifiAvailable(mActivity.getApplicationContext()) && NetworkUtil.isMobileNetAvailable(mActivity.getApplicationContext())) {
                        holder.textViewName.setText(data.title);
                        holder.imageViewPic.setImageResource(R.drawable.click_load_image);
                    }
                }
            }
            return convertView;
        }
    }
    static class ViewHolder {
        public ImageView imageViewPic;
        public TextView textViewName;
    }


}
