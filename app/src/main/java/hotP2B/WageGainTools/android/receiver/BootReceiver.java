package hotP2B.WageGainTools.android.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import hotP2B.WageGainTools.android.service.DaemonService;

public class BootReceiver extends BroadcastReceiver {  
  
    @Override  
    public void onReceive(Context context, Intent mintent) 
    {  
    	String action=mintent.getAction();
        if (Intent.ACTION_BOOT_COMPLETED.equals(action)) 
        { 
        	Intent i = new Intent(context, DaemonService.class);  
            context.startService(i);  
        }  
    }  
}  