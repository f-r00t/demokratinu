package org.group13.pocketpolitics.net;

import org.group13.pocketpolitics.net.riksdag.Retriever;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class Connected {

	public static boolean isConnected(Context ctx){
		if(ctx == null){
			Log.e(Retriever.class.getSimpleName(),"PocketDebug: in Connected: Context null error");
			return false;
		}

		ConnectivityManager conMgr = (ConnectivityManager)
				ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netwInfo = conMgr.getActiveNetworkInfo();

		return netwInfo != null && netwInfo.isConnected();
	}
}
