package com.zemanta.wat2pac.airtable;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.os.Handler;
import android.os.Looper;

public class Airtable
{
	private static Airtable singleton;
	private static final String API_KEY = AirtableApiKey.API_KEY;
	private static final String API = "https://api.airtable.com/v0/appS5yu0UXCu8A0o6/";
	
	public static synchronized Airtable getInstance()
	{
		if(singleton == null)
		{
			singleton = new Airtable();
		}
		
		return singleton;
	}
	
	private Handler mainThreadHandler = new Handler(Looper.getMainLooper());
	private List<Communication> pending = new ArrayList<Airtable.Communication>(); // TODO replace with a more appropriate collection
	
	private Airtable()
	{
		new Communicator().start();
	}
	
	public void get(String target, OnAirtableResponseListener listener)
	{
		synchronized(pending)
		{
			pending.add(
				new Communication(
					API + target,
					listener
				)
			);
		}
	}
	
	public void create(String target, JSONArray fields, OnAirtableResponseListener listener)
	{
		
	}
	
	private class Communicator extends Thread
	{
		@Override
		public void run()
		{
			while(true)
			{
				Communication poll;
				
				synchronized(pending)
				{
					if(pending.size() == 0)
					{
						try
						{
							pending.wait();
						}
						catch(InterruptedException e)
						{
							
						}
					}
					
					poll = pending.remove(0);
				}
				
				final Communication communication = poll;
				
				try
				{
					URL url = new URL(communication.url);
					HttpURLConnection connection = (HttpURLConnection) url.openConnection();
					connection.addRequestProperty("Authorization", "Bearer " + API_KEY);
					connection.addRequestProperty("Content-Type", "application/json");
					InputStream inputStream = new BufferedInputStream(connection.getInputStream());
					
					BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
					final StringBuilder response = new StringBuilder();
					String line;
					while ((line = reader.readLine()) != null)
					{
						response.append(line);
					}
					
					mainThreadHandler.post(new Runnable()
					{
						@Override
						public void run()
						{
							communication.listener.onAirtableResponse(response.toString());
						}
					});
				}
				catch(final Exception e) // TODO improve exception handling
				{
					mainThreadHandler.post(new Runnable()
					{
						@Override
						public void run()
						{
							communication.listener.onError("Exception while communication with Airtable, " + e.getClass().getSimpleName());
						}
					});
				}
			}
		}
	}
	
	private class Communication
	{
		private String url;
		private OnAirtableResponseListener listener;
		
		public Communication(String url, OnAirtableResponseListener listener)
		{
			this.url = url;
			this.listener = listener;
		}
	}
}