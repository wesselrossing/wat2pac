package com.zemanta.wat2pac.activities;

import com.zemanta.wat2pac.R;
import com.zemanta.wat2pac.airtable.Airtable;
import com.zemanta.wat2pac.airtable.OnAirtableResponseListener;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Suitcase extends Activity implements OnAirtableResponseListener
{
	private String id;
	private Button addItems;
	
	protected void onCreate(android.os.Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		id = getSharedPreferences("wat2pac", 0).getString("packListId", null);
		
		setContentView(R.layout.suitcase_layout);
		
		addItems = (Button) findViewById(R.id.addItems);
		addItems.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				startActivity(new Intent(Suitcase.this, Selector.class));
			}
		});
		
		Airtable.getInstance().get("Pack%20list", this);
	}
	
	@Override
	public void onAirtableResponse(String response)
	{
		
	}
	
	@Override
	public void onError(String error)
	{
		
	}
}