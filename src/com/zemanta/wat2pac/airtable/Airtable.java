package com.zemanta.wat2pac.airtable;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class Airtable
{
	private static Airtable singleton;
	private static final String API_KEY = AirtableApiKey.API_KEY;
	
	public static synchronized Airtable getInstance()
	{
		if(singleton == null)
		{
			singleton = new Airtable();
		}
		
		return singleton;
	}
	
	private List<Communication> pending = new ArrayList<Airtable.Communication>(); // TODO replace with a more appropriate collection
	
	private Airtable()
	{
		new Communicator().start();
	}
	
	public JSONObject get(OnAirtableResponseListener listener)
	{
		synchronized(pending)
		{
			pending.add(new Communication("https://api.airtable.com/v0/appS5yu0UXCu8A0o6/Items?limit=3&view=Main%20View", listener));
			pending.notify();
		}
		
		return null;
	}
	
	private class Communicator extends Thread
	{
		@Override
		public void run()
		{
			while(true)
			{
				Communication communication;
				
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
					
					communication = pending.remove(0);
					
					try
					{
						URL url = new URL(communication.url + "&api_key=" + API_KEY);
						HttpURLConnection connection = (HttpURLConnection) url.openConnection();
						InputStream inputStream = new BufferedInputStream(connection.getInputStream());
						
						BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
						StringBuilder response = new StringBuilder();
						String line;
						while ((line = reader.readLine()) != null)
						{
							response.append(line);
						}
						
						communication.listener.onAirtableResponse(response.toString());
					}
					catch(Exception e) // TODO improve exception handling
					{
						throw new RuntimeException(e);
					}
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