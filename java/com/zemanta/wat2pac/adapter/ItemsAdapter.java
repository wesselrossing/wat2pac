package com.zemanta.wat2pac.adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zemanta.wat2pac.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;

public class ItemsAdapter extends BaseAdapter
{
	private LayoutInflater inflater;
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
		TextView textView;
		
		if(convertView != null)
		{
			textView = (TextView) convertView;
		}
		else
		{
			if(inflater == null)
			{
				inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			}
			
			textView = (TextView) inflater.inflate(R.layout.spinner_item, null);
		}
		
		textView.setText(getItem(position));
		
		return textView;
	}
}