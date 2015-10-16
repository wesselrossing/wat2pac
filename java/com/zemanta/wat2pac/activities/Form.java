package com.zemanta.wat2pac.activities;

import com.zemanta.wat2pac.airtable.Airtable;
import com.zemanta.wat2pac.airtable.OnAirtableResponseListener;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class Form extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		Airtable.getInstance().get("Items?limit=3&view=Main%20View", new OnAirtableResponseListener()
		{
			@Override
			public void onAirtableResponse(final String response)
			{
				TextView textView = new TextView(Form.this);
				textView.setText(response);
				setContentView(textView);
			}
			
			@Override
			public void onError(String error)
			{
				Toast.makeText(Form.this, error, Toast.LENGTH_LONG).show();
			}
		});
	}
}