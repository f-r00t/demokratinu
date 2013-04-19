package com.test.pocketpolitics.net;

import com.example.pocketpolitics.net.Retriever;

import android.test.AndroidTestCase;
import android.util.Log;



public class RetrieverTester extends AndroidTestCase {
	
	public void testTx(){
		
		String result = Retriever.getInstance().getText("2013", "Sku21");
		if(result!=null){
			Log.i( "TextRetriever"  , "Result: "+result);
		}
		else{
			Log.e(this.getClass().getSimpleName(), "Leif: Error: null result");
			fail();
		}
	}
}
