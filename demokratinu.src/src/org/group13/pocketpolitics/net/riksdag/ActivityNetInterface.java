package org.group13.pocketpolitics.net.riksdag;

public interface ActivityNetInterface<Out> {
	public void onPreExecute();
	public void onProgressUpdate(Integer procent);
	public void onSuccess(Out result);
	public void onFailure(String message);
}
