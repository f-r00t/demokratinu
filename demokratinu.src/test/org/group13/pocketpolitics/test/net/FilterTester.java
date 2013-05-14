package org.group13.pocketpolitics.test.net;

import org.group13.pocketpolitics.net.Filter;

import android.test.AndroidTestCase;
import android.util.Log;

public class FilterTester extends AndroidTestCase{

	public void testEquals(){
		if(!Filter.testEquals()){
			Log.e(this.getClass().getSimpleName(), "Leif: error! not equal!");
			fail();
		}
	}
}
