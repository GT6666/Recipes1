package com.project.wei.tastyrecipes.basepager.subclass.basemenudetailpager;

import android.app.Activity;
import android.content.Intent;
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
        //////////////////////////////////////////////////////////////////////////////////
        //  在这里，处理PullRefreshListView中的下拉刷新和上拉加载数据，它怎么才能知道要进行刷新和加载呢？
        //  通过设置回调！！！ 就是PullRefreshListView，发送消息来，然后在这里处理
        // 5. 前端界面设置回调
        //  因为是在PullRefreshListView中设置的方法，所以它的对象才能调用setOnRefreshListener方法
      /*  lv_news.setOnRefreshListener(new PullRefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 最终在这里 刷新数据
                getDataFromServer();
            }

        });*/
        /*setOnRefreshListener方法小括号里面就是 OnRefreshListener的一个对象listener，有了这个对象，
        才会在PullRefreshListView中去初始化 mListener ，然后判断mListener不为null，才会在PullRefreshListView中调用mListener.onRefresh()，
        这里的onRefresh方法才会执行！！！*/

        /*举个列子，你让你朋友给你去买手机，你要告诉他：setOnRefreshListener方法，必须new出里面的对象，才相当于告诉了他，否则他还是不知道，
        然后他给你看好一个手机，他要问一下你要不要买:onRefresh方法，这个方法里面可以带参数，就想象成手机的一些配置，
        你最后决定要不要买：getDataFromServer，这个方法就是做具体的处理*/

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
        Gson gson = new Gson();
        classifyDetail = gson.fromJson(result, ClassifyDetail.class);

        // 列表新闻填充数据
        data = classifyDetail.result.data;
        Log.i("fffffffffff",data.toString());
        if (data != null) {
                listNewsAdapter = new ListNewsAdapter();
                lv_news.setAdapter(listNewsAdapter);
            }
    }

    class ListNewsAdapter extends BaseAdapter {

        private  BitmapUtils bitmapUtils;

        public ListNewsAdapter() {
            bitmapUtils = new BitmapUtils(mActivity);
            //bitmapUtils.configDefaultLoadingImage(R.drawable.news_pic_default);
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

            /*// 被点击了的item，进行回显
            String readIds = SharedPrefUtil.getString(mActivity, "read_ids", "");
            if (readIds.contains(data.id + "")) {
                holder.textViewTitle.setTextColor(Color.GRAY);
            } else { //  这里一定要判断 else 的情况，因为这里有item的复用，不写else时，会被复用，导致显示错误
                holder.textViewTitle.setTextColor(Color.BLACK);
            }*/
            bitmapUtils.display(holder.imageViewPic,data.albums.get(0));
            return convertView;
        }
    }
    static class ViewHolder {
        public ImageView imageViewPic;
        public TextView textViewName;
    }


}
