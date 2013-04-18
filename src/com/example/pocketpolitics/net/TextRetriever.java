package com.example.pocketpolitics.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

class TextRetriever {
	
	private static final String QUERY_START = "http://data.riksdagen.se/dokumentlista/?rm=2013&d=&ts=&sn=&parti=&iid=&bet=Sku21&org=&kat=&sz=10&sort=c&utformat=xml&termlista=";
	private static final String QUERY_END = "";

	protected TextRetriever(){
		
	}
	
	
}
