package com.project.wei.tastyrecipes.bean;

import android.database.SQLException;
import android.util.Log;

import com.project.wei.tastyrecipes.dao.ChannelDao;
import com.project.wei.tastyrecipes.db.SQLHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChannelManage {
	public static ChannelManage channelManage;
	/**
	 * 默认的用户选择频道列表
	 * */
	public static List<ChannelItem> defaultUserChannels;
	/**
	 * 默认的其他频道列表
	 * */
	public static List<ChannelItem> defaultOtherChannels;
	private ChannelDao channelDao;
	/** 判断数据库中是否存在用户数据 */
	private boolean userExist = false;
	static {
		defaultUserChannels = new ArrayList<ChannelItem>();
		defaultOtherChannels = new ArrayList<ChannelItem>();
		defaultUserChannels.add(new ChannelItem(1, "菜式菜品", 1, 1));
		defaultUserChannels.add(new ChannelItem(2, "菜系", 2, 1));
		defaultUserChannels.add(new ChannelItem(3, "时令食材", 3, 1));
		defaultUserChannels.add(new ChannelItem(4, "功效", 4, 1));
		defaultUserChannels.add(new ChannelItem(5, "场景", 5, 1));
		defaultUserChannels.add(new ChannelItem(6, "工艺口味", 6, 1));
		defaultUserChannels.add(new ChannelItem(7, "菜肴", 7, 1));
		defaultUserChannels.add(new ChannelItem(8, "主食", 8, 1));
		defaultUserChannels.add(new ChannelItem(9, "西点", 9, 1));
		defaultUserChannels.add(new ChannelItem(10, "汤羹饮品", 10, 1));
		defaultUserChannels.add(new ChannelItem(11, "其他菜品", 11, 1));
		defaultUserChannels.add(new ChannelItem(12, "人群", 12, 1));

		defaultOtherChannels.add(new ChannelItem(13, "疾病", 1, 0));
		defaultOtherChannels.add(new ChannelItem(14, "畜肉类", 2, 0));
		defaultOtherChannels.add(new ChannelItem(15, "禽蛋类", 3, 0));
		defaultOtherChannels.add(new ChannelItem(16, "水产类", 4, 0));
		defaultOtherChannels.add(new ChannelItem(17, "蔬菜类", 5, 0));
		defaultOtherChannels.add(new ChannelItem(18, "水果类", 6, 0));
		defaultOtherChannels.add(new ChannelItem(19, "米面豆乳类", 7, 0));
		defaultOtherChannels.add(new ChannelItem(20, "日常", 8, 0));
		defaultOtherChannels.add(new ChannelItem(21, "节日", 9, 0));
		defaultOtherChannels.add(new ChannelItem(22, "节气", 10, 0));
		defaultOtherChannels.add(new ChannelItem(23, "基本工艺", 11, 0));
		defaultOtherChannels.add(new ChannelItem(24, "其他工艺", 12, 0));
		defaultOtherChannels.add(new ChannelItem(25, "基本口味", 13, 0));
		defaultOtherChannels.add(new ChannelItem(26, "多元口味", 14, 0));
		defaultOtherChannels.add(new ChannelItem(27, "水果味", 15, 0));
		defaultOtherChannels.add(new ChannelItem(28, "调味料", 16, 0));
	}

	private ChannelManage(SQLHelper paramDBHelper) throws SQLException {
		if (channelDao == null)
			channelDao = new ChannelDao(paramDBHelper.getContext());
		// NavigateItemDao(paramDBHelper.getDao(NavigateItem.class));
		return;
	}

	/**
	 * 初始化频道管理类
	 * @throws SQLException
	 */
	public static ChannelManage getManage(SQLHelper dbHelper)throws SQLException {
		if (channelManage == null)
			channelManage = new ChannelManage(dbHelper);
		return channelManage;
	}

	/**
	 * 清除所有的频道
	 */
	public void deleteAllChannel() {
		channelDao.clearFeedTable();
	}
	/**
	 * 获取其他的频道
	 * @return 数据库存在用户配置 ? 数据库内的用户选择频道 : 默认用户选择频道 ;
	 */
	public List<ChannelItem> getUserChannel() {
		Object cacheList = channelDao.listCache(SQLHelper.SELECTED + "= ?",new String[] { "1" });
		if (cacheList != null && !((List) cacheList).isEmpty()) {
			userExist = true;
			List<Map<String, String>> maplist = (List) cacheList;
			int count = maplist.size();
			List<ChannelItem> list = new ArrayList<ChannelItem>();
			for (int i = 0; i < count; i++) {
				ChannelItem navigate = new ChannelItem();
				navigate.setId(Integer.valueOf(maplist.get(i).get(SQLHelper.ID)));
				navigate.setName(maplist.get(i).get(SQLHelper.NAME));
				navigate.setOrderId(Integer.valueOf(maplist.get(i).get(SQLHelper.ORDERID)));
				navigate.setSelected(Integer.valueOf(maplist.get(i).get(SQLHelper.SELECTED)));
				list.add(navigate);
			}
			return list;
		}
		initDefaultChannel();
		return defaultUserChannels;
	}
	
	/**
	 * 获取其他的频道
	 * @return 数据库存在用户配置 ? 数据库内的其它频道 : 默认其它频道 ;
	 */
	public List<ChannelItem> getOtherChannel() {
		Object cacheList = channelDao.listCache(SQLHelper.SELECTED + "= ?" ,new String[] { "0" });
		List<ChannelItem> list = new ArrayList<ChannelItem>();
		if (cacheList != null && !((List) cacheList).isEmpty()){
			List<Map<String, String>> maplist = (List) cacheList;
			int count = maplist.size();
			for (int i = 0; i < count; i++) {
				ChannelItem navigate= new ChannelItem();
				navigate.setId(Integer.valueOf(maplist.get(i).get(SQLHelper.ID)));
				navigate.setName(maplist.get(i).get(SQLHelper.NAME));
				navigate.setOrderId(Integer.valueOf(maplist.get(i).get(SQLHelper.ORDERID)));
				navigate.setSelected(Integer.valueOf(maplist.get(i).get(SQLHelper.SELECTED)));
				list.add(navigate);
			}
			return list;
		}
		if(userExist){
			return list;
		}
		cacheList = defaultOtherChannels;
		return (List<ChannelItem>) cacheList;
	}
	
	/**
	 * 保存用户频道到数据库
	 * @param userList
	 */
	public void saveUserChannel(List<ChannelItem> userList) {
		for (int i = 0; i < userList.size(); i++) {
			ChannelItem channelItem = (ChannelItem) userList.get(i);
			channelItem.setOrderId(i);
			channelItem.setSelected(Integer.valueOf(1));
			channelDao.addCache(channelItem);
		}
	}
	
	/**
	 * 保存其他频道到数据库
	 * @param otherList
	 */
	public void saveOtherChannel(List<ChannelItem> otherList) {
		for (int i = 0; i < otherList.size(); i++) {
			ChannelItem channelItem = (ChannelItem) otherList.get(i);
			channelItem.setOrderId(i);
			channelItem.setSelected(Integer.valueOf(0));
			channelDao.addCache(channelItem);
		}
	}
	
	/**
	 * 初始化数据库内的频道数据
	 */
	private void initDefaultChannel(){
		Log.d("deleteAll", "deleteAll");
		deleteAllChannel();
		saveUserChannel(defaultUserChannels);
		saveOtherChannel(defaultOtherChannels);
	}
}
