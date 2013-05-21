package org.group13.pocketpolitics.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class Connected {

	public static boolean isConnected(Context ctx){
		if(ctx == null){
			Log.e(Connected.class.getSimpleName(),"PocketDebug: in Connected: Context null error");
			return false;
		}

		ConnectivityManager conMgr = (ConnectivityManager)
				ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netwInfo = conMgr.getActiveNetworkInfo();

		return netwInfo != null && netwInfo.isConnected();
	}
}
