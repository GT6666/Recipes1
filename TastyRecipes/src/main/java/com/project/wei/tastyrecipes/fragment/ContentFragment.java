package com.project.wei.tastyrecipes.fragment;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.project.wei.tastyrecipes.R;
import com.project.wei.tastyrecipes.activity.MainActivity;
import com.project.wei.tastyrecipes.basepager.BasePager;
import com.project.wei.tastyrecipes.basepager.subclass.basepager.Cooking;
import com.project.wei.tastyrecipes.basepager.subclass.basepager.Classify;
import com.project.wei.tastyrecipes.basepager.subclass.basepager.SettingPager;
import com.project.wei.tastyrecipes.view.NoScrollViewPager;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/22 0022.
 */
public class ContentFragment extends BaseFragment {

    private NoScrollViewPager vp_content;
    private RadioGroup rg_content;

    private ArrayList<BasePager> mPagers;

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_content, null);
        vp_content = (NoScrollViewPager) view.findViewById(R.id.vp_content);
        rg_content = (RadioGroup) view.findViewById(R.id.rg_content);
        return view;
    }

    @Override
    public void initData() {
        mPagers = new ArrayList<>();
        //添加三个标签页
        mPagers.add(new Classify(mActivity));
        mPagers.add(new Cooking(mActivity));
        mPagers.add(new SettingPager(mActivity));

        vp_content.setAdapter(new ViewPagerAdapter());
        //      设置每个RadioButton选择后的事件处理，跳转到相应的pager
        radioGruopListener();
        //      设置在某些页面侧边栏不能滑出来
        pagerChangeListener();
    }

    private void radioGruopListener() {
        rg_content.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_classify:
                        vp_content.setCurrentItem(0,false);//参数二：是否具有滑动动画
                        break;
                    case R.id.rb_cooking:
                        vp_content.setCurrentItem(1,false);
                        break;
                    case R.id.rb_setting:
                        vp_content.setCurrentItem(2,false);
                        break;
                }
            }
        });
    }
    private void pagerChangeListener() {
        vp_content.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                ///////////////////////////////////////////////////////////////////////////////////
                BasePager pager = mPagers.get(position);
                pager.initData();//在这里初始化数据

                if ( position == 0) {
                    setSlidingMenuEnable(true);
                } else {
                    setSlidingMenuEnable(false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        vp_content.setCurrentItem(1);
        // 手动加载第一页数据，因为第一个页面是默认选中的，所以第一个页面不会加载数据
        mPagers.get(0).initData();
        setSlidingMenuEnable(false);
    }

    //  设置侧边栏是否能打开
    private void setSlidingMenuEnable(boolean enable) {
        //获取侧边栏对象
        MainActivity mainActivity = (MainActivity) mActivity;
        SlidingMenu slidingMenu = mainActivity.getSlidingMenu();
        if (enable) {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        } else {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }
    }


    class ViewPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return mPagers.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view ==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BasePager basePager = mPagers.get(position);

            //在这里调用每个viewpager的这个方法，初始化数据
            //viewpager会默认加载下一个页面的数据,为了节省流量和性能,
            // 不要在此处调用初始化数据的方法,而是监听每个viewpager，当它被选择后再初始化数据
            //            basePager.initData();

            View view = basePager.mRootView;//获取当前页面对象的布局
            container.addView(view);
            return view;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    // 获取分类页面
    public Classify getClassifyDetailPager() {
        Classify pager = (Classify) mPagers.get(0);
        return pager;
    }

}
