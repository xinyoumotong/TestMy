package hotP2B.WageGainTools.android.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import hotP2B.WageGainTools.android.AppConfig;
import hotP2B.WageGainTools.android.service.DaemonService;

public class AlarmReceiver extends BroadcastReceiver 
{  
    @Override  
    public void onReceive(Context context, Intent intent) 
    {  
        if (intent.getAction().equals(AppConfig.ALARM_BROADCAST_ACTION)) 
        {  
            Intent i = new Intent(context, DaemonService.class);  
            context.startService(i);  
        }  
    }  
}  
