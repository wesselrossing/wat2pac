package com.zemanta.wat2pac.adapter;

import com.zemanta.wat2pac.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListTypeAdapter extends BaseAdapter
{
	private String[] listTypes = new String[] { "Suggested pack list", "My pack list" };
	private LayoutInflater inflater;
	
	@Override
	public long getItemId(int position)
	{
		return 0;
	}
	
	@Override
	public int getCount()
	{
		return listTypes.length;
	}
	
	@Override
	public String getItem(int position)
	{
		return listTypes[position];
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