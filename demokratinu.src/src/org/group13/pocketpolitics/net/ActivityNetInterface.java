package org.group13.pocketpolitics.net;

public interface ActivityNetInterface<Out> {
	public void onPreExecute();
	public void onProgressUpdate(Out halfFinished);
	public void onSuccess(Out result);
	public void onFailure(String message);
}
