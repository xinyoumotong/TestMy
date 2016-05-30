package hotP2B.WageGainTools.android.plugin;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import hotP2B.WageGainTools.android.AppConfig;

public class AppThirdStart extends Activity 
{
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
	    super.onCreate(savedInstanceState);  
	    handleIntent(getIntent());
	}
	
	@Override
	protected void onNewIntent(Intent intent) 
	{
		super.onNewIntent(intent);
        handleIntent(intent);
	}
	
	private void handleIntent(Intent intent)
	{
		if(intent!=null)
		{
			Uri uri = getIntent().getData(); 
			if(uri!=null && uri.getScheme().equals("hotp2b") && uri.getHost().equals("mobileapp.wgtools"))
			{
				this.startActivity(this.getPackageManager().getLaunchIntentForPackage(AppConfig.PACKAGENAME));
			}
		}
	
		this.finish();
	}
}
