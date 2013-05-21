package org.group13.pocketpolitics.net.server;

enum ServerOperation {
	Register("http://fruktnet.no-ip.org/pocketpolitics/register.php"),
	Authenticate("http://fruktnet.no-ip.org/pocketpolitics/authenticate.php");
	
	private String url;
	ServerOperation(String url){
		this.url=url;
	}
	
	public String getUrl(){
		return url;
	}
}
