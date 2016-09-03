package com.project.wei.tastyrecipes.fragment;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.project.wei.tastyrecipes.R;
import com.project.wei.tastyrecipes.activity.ChannelActivity;
import com.project.wei.tastyrecipes.activity.MainActivity;
import com.project.wei.tastyrecipes.basepager.subclass.basepager.Classify;
import com.project.wei.tastyrecipes.domain.MillionMenus;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/22 0022.
 */
public class ClassifyMenuFragment extends BaseFragment {
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

    private ArrayList<MillionMenus.ResultBean> mNewsMenuData;// 侧边栏网络数据对象
    private int mCurrentPos;// 当前被选中的item的位置
    private LeftMenuAdapter adapter;


    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_leftmenu, null);
        ViewUtils.inject(this,view);//注入view，才算完成
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
                startActivity(new Intent(getActivity(), ChannelActivity.class));
            }
        });
    }

   /* @Override
    public void onResume() {
        super.onResume();
        if (mChannelItem != null) {
            for (int i = 0; i < mChannelItem.size(); i++) {
                Log.i("ccccccc", mChannelItem.get(i).toString());
            }
        } else {
            Log.i("ff", "11111111111111111111111111111111111");
        }
    }
*/
    // 给gridview设置数据
    public void setMenuData(ArrayList<MillionMenus.ResultBean> data){
        mCurrentPos = 0;//当前选中的位置归零，每次点击新闻中心后，先解析数据，然后把数据通过setMenuData传送过来，
                        // 在这里就可以把菜单栏menu设置为选中新闻，即mCurrentPos=0
        // 更新页面
        mNewsMenuData = data;
        adapter = new LeftMenuAdapter();
        gv_list.setAdapter(adapter);

        gv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCurrentPos = position;// 更新当前被选中的位置
                adapter.notifyDataSetChanged();//刷新listview,是为了每次点击时可以更新一下menu， 这样才能使点击的menu变为红色，
                                               //否则，即使你更新了mCurrentPos,也会一直保持第一个menu红色
                toggle();// 收起侧边栏
                setCurrentDetailPager(position); // 侧边栏点击之后, 要修改新闻中心的FrameLayout中的内容
            }
        });
    }
    private void toggle() {
        MainActivity mainActivity = (MainActivity) mActivity;
        SlidingMenu slidingMenu = mainActivity.getSlidingMenu();
        slidingMenu.toggle();// 如果当前状态是开, 调用后就关; 反之亦然
    }

//  设置当前菜单详情页
    protected void setCurrentDetailPager(int position) {
        // 获取新闻中心的对象
        MainActivity mainActivity = (MainActivity) mActivity;
        // 获取ContentFragment
        ContentFragment fragment = mainActivity.getContentFragment();
        // 获取NewsCenterPager
        Classify Classify = fragment.getClassifyDetailPager();
        // 修改新闻中心的FrameLayout的布局
        Classify.setCurrentDetailPager(position);
    }

    class LeftMenuAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mNewsMenuData.size();
        }

        @Override
        public MillionMenus.ResultBean getItem(int position) {
            return mNewsMenuData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate( mActivity, R.layout.item_leftmenu, null);
            TextView tv_menu = (TextView) view.findViewById(R.id.tv_menu);

            MillionMenus.ResultBean newsMenuData = getItem(position);
            //等同于下面这个
            //NewsMenu.NewsMenuData newsMenuData = mNewsMenuData.get(position);
            tv_menu.setText(newsMenuData.name);
            //////////////////////////////////////////////////////////////////////////////////////////
            if (mCurrentPos == position) {
                // <item android:state_enabled="true" android:color="#F00"/>
                //在文字的状态选择器中 enabled表示的是可用的时候，如果不作处理，每一个menu都是可用的，即都是红色
                tv_menu.setEnabled(true);// 被选中,文字变为红色

            } else {
                tv_menu.setEnabled(false);// 未选中,文字变为白色
            }

            return view;
        }
    }

}
