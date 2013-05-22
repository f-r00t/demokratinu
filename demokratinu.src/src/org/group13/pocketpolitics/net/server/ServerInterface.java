package org.group13.pocketpolitics.net.server;

import org.group13.pocketpolitics.model.user.ArticleData;

public interface ServerInterface {

	public void authenticateReturned(boolean succeded, String username);
	
	public void registrationReturned(boolean succeded, boolean unameExists, boolean emailExists);
	
	public void postOpinionReturned(boolean succeded);
	public void postCommentReturned(boolean succeded);
	public void getArticleDataReturned(ArticleData data);
	
	public void operationFailed(ServerOperation oper);
}
