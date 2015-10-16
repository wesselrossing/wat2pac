package com.zemanta.wat2pac.adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zemanta.wat2pac.R;
import com.zemanta.wat2pac.activities.DownloadImageTask;
import com.zemanta.wat2pac.airtable.Airtable;
import com.zemanta.wat2pac.airtable.OnAirtableResponseListener;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
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
		ViewGroup viewGroup;
		
		if(convertView != null)
		{
			viewGroup = (ViewGroup) convertView;
		}
		else
		{
			if(inflater == null)
			{
				inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			}
			
			viewGroup = (ViewGroup) inflater.inflate(R.layout.suitcase_item, null);
		}
		
		final ViewGroup finalViewGroup = viewGroup;
		
		Airtable.getInstance().get("Pack%20list/" + getItem(position), new OnAirtableResponseListener()
		{
			@Override
			public void onError(String error) {

				Toast.makeText(finalViewGroup.getContext(), error, Toast.LENGTH_LONG).show();
			}
			
			@Override
			public void onAirtableResponse(String response)
			{
				try
				{
				new DownloadImageTask((ImageView) finalViewGroup.findViewById(R.id.image))
					.execute(new JSONObject(response).getJSONObject("fields").getJSONArray("Attachments").getJSONObject(0).getString("url"));
				}catch(Exception e){}
			}
		});
		
//		textView.setText(getItem(position));
		
		return viewGroup;
	}
}