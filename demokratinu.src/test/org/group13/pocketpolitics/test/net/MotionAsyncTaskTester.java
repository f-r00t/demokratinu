package org.group13.pocketpolitics.test.net;

import org.group13.pocketpolitics.net.Retriever;

import android.test.AndroidTestCase;

public class MotionAsyncTaskTester extends AndroidTestCase {

	public void testStrings(){
		Retriever.translate("2011/12", "54");
		Retriever.translate("2012/13", "u73");
	}
}
