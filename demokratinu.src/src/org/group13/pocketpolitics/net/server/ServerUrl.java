package org.group13.pocketpolitics.net.server;

public enum ServerUrl {
	Register("http://fruktnet.no-ip.org/pocketpolitics/register.php"),
	Authenticate("http://fruktnet.no-ip.org/pocketpolitics/authenticate.php");
	
	private String url;
	ServerUrl(String url){
		this.url=url;
	}
	
	public String getUrl(){
		return url;
	}
}
