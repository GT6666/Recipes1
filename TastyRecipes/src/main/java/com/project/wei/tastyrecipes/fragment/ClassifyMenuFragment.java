package com.project.wei.tastyrecipes.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.project.wei.tastyrecipes.R;
import com.project.wei.tastyrecipes.activity.ChannelActivity;
import com.project.wei.tastyrecipes.activity.MainActivity;
import com.project.wei.tastyrecipes.basepager.subclass.basepager.Classify;
import com.project.wei.tastyrecipes.bean.ChannelItem;
import com.project.wei.tastyrecipes.domain.MillionMenus;
import com.project.wei.tastyrecipes.utils.InputUtil;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/22 0022.
 */
public class ClassifyMenuFragment extends BaseFragment {
    private static final String TAG = "ClassifyMenuFragment";
    //通过XUtils框架中的ViewUtils模块，注解来获取xml文件中的对象
    //注解方式就可以进行UI，资源和事件绑定；无需findViewById和setClickListener等。
    @ViewInject(R.id.gv_list)
    private GridView gv_list;

    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.ibtn_menu)
    private ImageButton ibtn_menu;
    @ViewInject(R.id.ibtn_back)
    private ImageButton ibtn_back;
    @ViewInject(R.id.ibtn_change)
    private ImageButton ibtn_change;

    private ArrayList<MillionMenus.ResultBean> mMillionMenuData;// 侧边栏网络数据对象
    private int mCurrentPos;// 当前被选中的item的位置
    private LeftMenuAdapter adapter;
    private ArrayList<ChannelItem> channelItem;
    private ClassifyMenuAdapter classifyMenuAdapter;

    int[] pics = new int[]{R.mipmap.lv1, R.mipmap.lv2, R.mipmap.lv3, R.mipmap.lv4,
            R.mipmap.lv5, R.mipmap.lv6, R.mipmap.lv7, R.mipmap.lv8, R.mipmap.lv9, R.mipmap.lv10,
            R.mipmap.lv11, R.mipmap.lv12, R.mipmap.lv13, R.mipmap.lv14, R.mipmap.lv15, R.mipmap.lv16,
            R.mipmap.lv17, R.mipmap.lv18, R.mipmap.lv19, R.mipmap.lv20, R.mipmap.lv21, R.mipmap.lv22,
            R.mipmap.lv23, R.mipmap.lv24, R.mipmap.lv25, R.mipmap.lv26, R.mipmap.lv27, R.mipmap.lv28,};

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_leftmenu, null);
        ViewUtils.inject(this, view);//注入view，才算完成
        return view;
    }

    @Override
    public void initData() {
        tv_title.setText("分类");
        ibtn_back.setVisibility(View.VISIBLE);
        ibtn_menu.setVisibility(View.GONE);
        ibtn_change.setVisibility(View.VISIBLE);

        ibtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });

        ibtn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), ChannelActivity.class), 100);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //        ArrayList<ChannelItem> channelItem = data.getParcelableExtra("channelItem");
        //        channelItem = data.getExtras().getParcelableArrayList("channelItem");// 和上面对比，这个才可以
        // 上面是实现的Parcelable接口，这里是实现的Serializable接口，
        // 实现两种接口都可以实现对象集合的传递，但是，Serializable接口能实现对象集合的保存，
        // 而Parcelable接口，适合内存中传递数据，并不适合保存或者网络传输数据
        channelItem = (ArrayList<ChannelItem>) data.getSerializableExtra("channelItem");
    }

    //  每次启动APP时，都要先去文件里检查有没有存过数据，有的话就用已保存的数据
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i("onCreate", "onCreate执行");

        channelItem = InputUtil.readListFromSdCard(getActivity(), "channelitem");
        if (channelItem == null) {
            Log.i("dq", "读取数据失败");
        } else {
            Log.i("dq", "读取数据成功");
            for (int i = 0; i < channelItem.size(); i++) {
                String s = channelItem.get(i).getName();
                Log.i("dq", s);
            }

        }

        super.onCreate(savedInstanceState);
    }

    //  在ChannelActivity设置数据，点击确定后，将回传的数据马上显示出来
    @Override
    public void onResume() {
        super.onResume();
        Log.i("zzzzzzzzzz", "onResume执行");
        if (channelItem != null) {
            classifyMenuAdapter = new ClassifyMenuAdapter();
            if (classifyMenuAdapter != null) {
                gv_list.setAdapter(classifyMenuAdapter);
                classifyMenuAdapter.notifyDataSetChanged();

                // 必须在这里也加上点击事件，因为第一次进来时设置的点击事件不能在这里使用
                gv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        mCurrentPos = position;// 更新当前被选中的位置
                        toggle();// 收起侧边栏
                        setCurrentDetailPager(position); // 侧边栏点击之后, 要修改FrameLayout中的内容
                    }
                });
            }
        }

    }


    // 给gridview设置数据
    public void setMenuData(ArrayList<MillionMenus.ResultBean> data) {
        mCurrentPos = 0;//当前选中的位置归零，每次点击后，先解析数据，然后把数据通过setMenuData传送过来，
        // 在这里就可以把菜单栏menu设置为选中，即mCurrentPos=0
        // 更新页面
        mMillionMenuData = data;
        if (channelItem == null) {
            // 表示第一次进来时，就是还没有设置显示多少分类的时候，把所有的分类都显示出来，同时加上点击事件
            adapter = new LeftMenuAdapter();
            gv_list.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            gv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mCurrentPos = position;// 更新当前被选中的位置
                    toggle();// 收起侧边栏
                    setCurrentDetailPager(position); // 侧边栏点击之后, 要修改FrameLayout中的内容
                }
            });
        } else {
            gv_list.setAdapter(classifyMenuAdapter);
        }
    }

    private void toggle() {
        MainActivity mainActivity = (MainActivity) mActivity;
        SlidingMenu slidingMenu = mainActivity.getSlidingMenu();
        slidingMenu.toggle();// 如果当前状态是开, 调用后就关; 反之亦然
    }

    //  设置当前菜单详情页
    protected void setCurrentDetailPager(int position) {
        MainActivity mainActivity = (MainActivity) mActivity;

        ContentFragment fragment = mainActivity.getContentFragment();

        Classify Classify = fragment.getClassifyDetailPager();

            Classify.setCurrentDetailPager(position);


    }

    class LeftMenuAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mMillionMenuData.size();
        }

        @Override
        public MillionMenus.ResultBean getItem(int position) {
            return mMillionMenuData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(mActivity, R.layout.item_leftmenu, null);
            TextView tv_menu = (TextView) view.findViewById(R.id.tv_menu);
            LinearLayout ll_menu = (LinearLayout) view.findViewById(R.id.ll_menu);

            MillionMenus.ResultBean millionMenuData = getItem(position);
            String name = millionMenuData.name;
            tv_menu.setText(name);
            tv_menu.setBackgroundResource(pics[position]);
            return view;
        }
    }

    //   必须再写一个适配器，用于显示回传的数据，以消除数据减少时，能够重新排列
    class ClassifyMenuAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return channelItem.size();
        }

        @Override
        public ChannelItem getItem(int position) {
            return channelItem.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(mActivity, R.layout.item_classifymenu, null);
            TextView tv_menu = (TextView) view.findViewById(R.id.tv_classifymenu);

            ChannelItem millionMenuData = getItem(position);
            String name = millionMenuData.getName();

            tv_menu.setText(name);
            tv_menu.setBackgroundResource(pics[position]);
            return view;
        }
    }

}
