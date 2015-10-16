package com.zemanta.wat2pac.adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ItemsAdapter extends BaseAdapter
{
	private JSONArray items;
	
	public ItemsAdapter(JSONArray items)
	{
		this.items = items;
	}
	
	@Override
	public int getCount()
	{
		return items.length();
	}
	
	@Override
	public long getItemId(int position)
	{
		return 0;
	}
	
	@Override
	public String getItem(int position)
	{
		try
		{
			return items.getString(position);
		}
		catch(JSONException e)
		{
			// TODO
		}
		
		return null;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		TextView textView = new TextView(parent.getContext());
		textView.setText(getItem(position));
		return textView;
	}
}