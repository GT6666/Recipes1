package com.project.wei.tastyrecipes.basepager.subclass.basepager;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ToxicBakery.viewpager.transforms.ABaseTransformer;
import com.ToxicBakery.viewpager.transforms.CubeOutTransformer;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.project.wei.tastyrecipes.R;
import com.project.wei.tastyrecipes.activity.LocalImageHolderView;
import com.project.wei.tastyrecipes.activity.ShowNewsActivity;
import com.project.wei.tastyrecipes.basepager.BasePager;
import com.project.wei.tastyrecipes.utils.NetworkUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/23 0023.
 */
public class Cooking extends BasePager{

    private ConvenientBanner convenientBanner;//顶部广告栏控件
    private ArrayList<Integer> localImages = new ArrayList<Integer>();

    private Integer[] imageResId={R.drawable.gv1,R.drawable.gv2,R.drawable.gv3,
            R.drawable.gv4,R.drawable.gv5,R.drawable.gv6,
            R.drawable.gv7,R.drawable.gv8, R.drawable.gv9};
    private String[] titles={"永和豆浆", "俏江南", "金汉斯",
            "西贝莜面村", "外婆家", "港丽餐厅",
            "味千拉面", "海底捞火锅", "小肥羊火锅"};


    private View inflate;
    private GridView gv_main;


    public Cooking(Activity activity) {
        super(activity);
    }

    public View initView(){

        inflate = View.inflate(mActivity, R.layout.homepage_main, null);
        return inflate;
    }
    @Override
    public void initData(){

        gv_main = (GridView) inflate. findViewById(R.id.gv_main);

        gv_main.setAdapter(new MyGridViewAdapter());
        initViews();
        setImageURI(null);//init();
        //ibtn_menu.setVisibility(View.INVISIBLE);// 隐藏菜单按钮
    }

    private void initViews() {
        convenientBanner = (ConvenientBanner) inflate.findViewById(R.id.convenientBanner);
        convenientBanner.startTurning(3000);
       // onResume();
        //onPause();
    }


   /* protected void onResume() {
        onResume();
        //开始自动翻页
        convenientBanner.startTurning(3000);
    }

    // 停止自动翻页

    protected void onPause() {
          onPause();
        //停止翻页
        convenientBanner.stopTurning();
    }*/


    private void init(){
        initImageLoader();
        loadTestDatas();
        //本地图片
        convenientBanner.setPages(
                new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, localImages)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                //.setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
                .setOnItemClickListener(new OnItemClickListener() {
                    String url=null;
                    @Override
                    public void onItemClick(int position) {

                        switch (position){
                            case 0:
                                url="http://jingyan.baidu.com/article/636f38bb3747b1d6b84610f2.html";
                                break;
                            case 1:
                                url="http://jingyan.baidu.com/article/3f16e003ad262d2591c10380.html";
                                break;
                            case 2:
                                url="http://jingyan.baidu.com/article/f0062228311bc9fbd3f0c8dc.html";
                                break;
                            case 3:
                                url="http://jingyan.baidu.com/article/0a52e3f47afc28bf62ed721c.html";
                                break;
                            case 4:
                                url="http://jingyan.baidu.com/article/ea24bc399bc7bdda62b331b8.html";
                                break;
                            case 5:
                                url="http://jingyan.baidu.com/article/eae078278b3ed41fec5485bb.html";
                                break;
                            case 6:
                                url="http://jingyan.baidu.com/article/5d368d1e1ed9bb3f60c057f2.html";
                                break;
                        }

                        if(url!=null) {
                            Intent intent = new Intent(mActivity,ShowNewsActivity.class);
                            intent.putExtra("url", url);
                            mActivity.startActivity(intent);
                        }
                    }
                });

        gv_main.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            String url=null;
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position){
                    case 0:
                        url="http://www.yonho.net.cn";
                        break;
                    case 1:
                        url="http://www.southbeauty.com";
                        break;
                    case 2:
                        url="http://www.goldenhans.com";
                        break;
                    case 3:
                        url="http://www.xibei.com.cn";
                        break;
                    case 4:
                        url="http://www.waipojia.com";
                        break;
                    case 5:
                        url="http://www.charme-sh.com";
                        break;
                    case 6:
                        url="http://www.ajisen.com.cn";
                        break;
                    case 7:
                        url="http://www.haidilao.com";
                        break;
                    case 8:
                        url="http://www.littlesheep.com";
                        break;
                }

                if(url!=null) {
                    Intent intent = new Intent(mActivity, ShowNewsActivity.class);
                    intent.putExtra("url", url);
                    mActivity.startActivity(intent);
                }
            }
        });

    }

    //初始化网络图片缓存库
    private void initImageLoader(){
        //网络图片例子,结合常用的图片缓存库UIL,你可以根据自己需求自己换其他网络图片库
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                //showImageForEmptyUri(R.drawable.ic_default_adimage)
                .cacheInMemory(true).cacheOnDisk(true).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                mActivity).defaultDisplayImageOptions(defaultOptions)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);
    }
    /*
    加入测试Views
    * */
    private void loadTestDatas() {
        //本地图片集合
        for (int position = 0; position < 7; position++)
            localImages.add(getResId("ic_vp_" + position, R.drawable.class));


//        //翻页效果

        try {
            Class cls = Class.forName("com.ToxicBakery.viewpager.transforms." +CubeOutTransformer.class.getSimpleName());
            ABaseTransformer transforemer= (ABaseTransformer)cls.newInstance();
            convenientBanner.getViewPager().setPageTransformer(true,transforemer);

            //部分3D特效需要调整滑动速度
            convenientBanner.setScrollDuration(1200);


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    /**
     * 通过文件名获取资源id 例子：getResId("icon", R.drawable.class);
     *
     * @param variableName
     * @param c
     * @return
     */
    public static int getResId(String variableName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    // 开始自动翻页

/*    protected void onResume() {
        //开始自动翻页
        convenientBanner.startTurning(3000);
    }*/

    // 停止自动翻页

/*    protected void onPause() {
        //停止翻页
        convenientBanner.stopTurning();
    }*/

    class MyGridViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 9;
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

            View view = View.inflate(mActivity, R.layout.item_gridview, null);

            ImageView iv_item_grid = (ImageView) view.findViewById(R.id.iv_item_grid);
            TextView tv_item_grid = (TextView) view.findViewById(R.id.tv_item_grid);

            iv_item_grid.setImageResource(imageResId[position]);
            tv_item_grid.setText(titles[position]);
            return view;
        }
    }

    //省流量模式
    public void setImageURI(final Uri uri) {
       /* this.getHierarchy()
                .setActualImageScaleType(ScalingUtils.ScaleType.FOCUS_CROP);*/
        //wifi 情况下，都加载图片
        if (NetworkUtil.isWifiAvailable(mActivity.getApplicationContext())) {
            //super.setImageURI(uri);//facebook的库
            init();
            return;
        }
        //
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mActivity);//这里上下文写什么
        //不是省流量模式，采用原来的方法
        if (!defaultSharedPreferences.getBoolean("cbp_save_net",false)) {
            Log.i("zengjibin Test","不是省流量模式");
            init();
        } else {//是省流量模式，不加载数据
            if (!NetworkUtil.isWifiAvailable(mActivity.getApplicationContext()) && NetworkUtil.isMobileNetAvailable(mActivity.getApplicationContext())) {
                Log.i("zengjibin Test","是省流量模式");
                Drawable placeImage;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    placeImage = mActivity.getResources().getDrawable(R.drawable.click_load_image,mActivity.getApplicationContext().getTheme());
                } else {
                    placeImage = mActivity.getResources().getDrawable(R.drawable.click_load_image);
                }
            }

        }
    }


}
