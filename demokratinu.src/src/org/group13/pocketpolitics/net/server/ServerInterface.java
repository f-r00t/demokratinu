package org.group13.pocketpolitics.net.server;

import java.util.List;

import org.group13.pocketpolitics.model.user.ArticleData;

public interface ServerInterface {

	public void authenticateReturned(boolean succeded);
	
	public void registrationReturned(boolean succeded, boolean unameExists, boolean emailExists);
	
	public void postOpinionReturned(boolean succeded);
	public void postCommentReturned(boolean succeded);
	public void getArticleDataReturned(boolean succeded, ArticleData data);
	
	public void operationFailed(ServerOperation oper);
}
