package hotP2B.WageGainTools.android.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import hotP2B.WageGainTools.android.ui.fragment.Welcome1Fragment;

public class AssetReceiver extends BroadcastReceiver {

	private Welcome1Fragment fragment;
	public AssetReceiver(Welcome1Fragment fragment)
	{
		this.fragment=fragment;
	}
	@Override
	public void onReceive(Context context, Intent intent) 
	{
		if(fragment!=null)
		{
		  fragment.refresh2();
		}
	}

}
