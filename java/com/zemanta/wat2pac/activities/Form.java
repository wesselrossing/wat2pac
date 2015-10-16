package com.zemanta.wat2pac.activities;

import org.json.JSONException;
import org.json.JSONObject;

import com.zemanta.wat2pac.R;
import com.zemanta.wat2pac.adapter.ListTypeAdapter;
import com.zemanta.wat2pac.adapter.LocationsAdapter;
import com.zemanta.wat2pac.airtable.Airtable;
import com.zemanta.wat2pac.airtable.OnAirtableResponseListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class Form extends Activity
{
	private Spinner where;
	private Spinner when;
	private Spinner list;
	private Button go;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.form_layout);
		
		where = (Spinner) findViewById(R.id.where);
		
		Airtable.getInstance().get("Locations?view=Main%20View", new OnAirtableResponseListener()
		{
			@Override
			public void onAirtableResponse(String response)
			{
				try
				{
					where.setAdapter(new LocationsAdapter(new JSONObject(response).getJSONArray("records")));
				}
				catch(JSONException e)
				{
					// TODO
				}
			}
			
			@Override
			public void onError(String error)
			{
				Toast.makeText(Form.this, error, Toast.LENGTH_LONG).show();
			}
		});
		
		when = (Spinner) findViewById(R.id.when);
		
		Airtable.getInstance().get("Seasons?view=Main%20View", new OnAirtableResponseListener()
		{
			@Override
			public void onAirtableResponse(String response)
			{
				try
				{
					when.setAdapter(new LocationsAdapter(new JSONObject(response).getJSONArray("records")));
				}
				catch(JSONException e)
				{
					// TODO
				}
			}
			
			@Override
			public void onError(String error)
			{
				Toast.makeText(Form.this, error, Toast.LENGTH_LONG).show();
			}
		});
		
		list = (Spinner) findViewById(R.id.list);
		list.setAdapter(new ListTypeAdapter());
		
		go = (Button) findViewById(R.id.go);
		go.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				// TODO save
				
				startActivity(new Intent(Form.this, Suitcase.class));
			}
		});
	}
}