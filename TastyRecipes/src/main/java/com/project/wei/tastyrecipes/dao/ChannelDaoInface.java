package com.project.wei.tastyrecipes.dao;

import android.content.ContentValues;

import com.project.wei.tastyrecipes.bean.ChannelItem;

import java.util.List;
import java.util.Map;

public interface ChannelDaoInface {
	public boolean addCache(ChannelItem item);

	public boolean deleteCache(String whereClause, String[] whereArgs);

	public boolean updateCache(ContentValues values, String whereClause,
							   String[] whereArgs);

	public Map<String, String> viewCache(String selection,
										 String[] selectionArgs);

	public List<Map<String, String>> listCache(String selection,
											   String[] selectionArgs);

	public void clearFeedTable();
}
