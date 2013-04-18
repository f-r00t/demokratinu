package com.test.pocketpolitics.net;

import com.example.pocketpolitics.net.Retriever;

import android.test.AndroidTestCase;
import android.util.Log;



public class RetrieverTester extends AndroidTestCase {
	
	public void testTx(){
		Log.i( "TextRetriever"  ,Retriever.getInstance().getText("2013", "Sku21") );
	}
}
