package org.group13.pocketpolitics.test.net;

import org.group13.pocketpolitics.model.user.Account;
import org.group13.pocketpolitics.net.server.Syncer;

import android.test.AndroidTestCase;

public class PostAsyncTaskTester extends AndroidTestCase {

	public void testRegister(){
		Syncer.register(new Account("leif@testpolitics.se", "The HalfLeif untested", "12345"));
	}
}
