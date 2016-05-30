package hotP2B.WageGainTools.android.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import hotP2B.WageGainTools.android.share.WXEventHandler;

public class WXEntryActivity extends Activity 
{
	@Override
	public void onCreate(Bundle savedInstanceState) 
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
		WXEventHandler.getInstance().handleIntent(intent);
		this.finish();
	}

	

}
