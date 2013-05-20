package org.group13.pocketpolitics.net.server;

import org.group13.pocketpolitics.model.user.Account;

public class RegisterAsyncTask extends PostAsyncTask {

	RegisterAsyncTask(Account user){
		super(user, ServerUrl.Register);
	}
}
