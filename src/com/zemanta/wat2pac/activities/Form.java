package com.zemanta.wat2pac.activities;

import com.zemanta.wat2pac.airtable.Airtable;
import com.zemanta.wat2pac.airtable.OnAirtableResponseListener;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Form extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		Airtable.getInstance().get(new OnAirtableResponseListener()
		{
			@Override
			public void onAirtableResponse(final String response)
			{
				Form.this.runOnUiThread(new Runnable()
				{
					@Override
					public void run()
					{
						TextView textView = new TextView(Form.this);
						textView.setText(response);
						setContentView(textView);
					}
				});
			}
		});
	}
}