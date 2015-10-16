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
import android.widget.TextView;

public class LocationsAdapter extends BaseAdapter
{
	private JSONArray locations;
	private LayoutInflater inflater;
	
	public LocationsAdapter(JSONArray locations)
	{
		this.locations = locations;
	}
	
	@Override
	public long getItemId(int position)
	{
		return 0;
	}
	
	@Override
	public int getCount()
	{
		return locations.length();
	}
	
	@Override
	public JSONObject getItem(int position)
	{
		try
		{
			return locations.getJSONObject(position);
		}
		catch(JSONException e)
		{
			throw new RuntimeException(e);
		}
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
		
		JSONObject location = getItem(position);
		textView.setTag(location);
		
		try
		{
			textView.setText(location.getJSONObject("fields").getString("Name"));
		}
		catch(JSONException e)
		{
			// TODO
		}
		
		return textView;
	}
}