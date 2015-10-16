package com.zemanta.wat2pac.adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LocationsAdapter extends BaseAdapter
{
	private JSONArray locations;
	
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
		TextView textView = new TextView(parent.getContext());
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