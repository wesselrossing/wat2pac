package com.zemanta.wat2pac.activities;

import com.zemanta.wat2pac.R;
import com.zemanta.wat2pac.airtable.Airtable;
import com.zemanta.wat2pac.airtable.OnAirtableResponseListener;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Spinner;
import android.widget.Toast;

public class Form extends Activity
{
	private Spinner where;
	
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
				
			}
			
			@Override
			public void onError(String error)
			{
				Toast.makeText(Form.this, error, Toast.LENGTH_LONG).show();
			}
		});
	}
}