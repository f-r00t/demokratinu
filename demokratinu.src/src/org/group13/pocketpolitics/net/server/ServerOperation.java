package org.group13.pocketpolitics.net.server;

public enum ServerOperation {
	Register("http://fruktnet.no-ip.org/pocketpolitics/register.php"),
	Authenticate("http://fruktnet.no-ip.org/pocketpolitics/authenticate.php"),
	PostOpinion("http://fruktnet.no-ip.org/pocketpolitics/post-opinion.php"),
	PostComment("http://fruktnet.no-ip.org/pocketpolitics/post-comment.php"),
	GetArticleData("http://fruktnet.no-ip.org/pocketpolitics/get-article-data.php");
	
	private String url;
	ServerOperation(String url){
		this.url=url;
	}
	
	protected String getUrl(){
		return url;
	}
}
