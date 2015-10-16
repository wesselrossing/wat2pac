package com.zemanta.wat2pac.airtable;

public interface OnAirtableResponseListener
{
	public void onAirtableResponse(String response);
	
	public void onError(String error);
}
