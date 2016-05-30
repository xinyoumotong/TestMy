package hotP2B.WageGainTools.android.inter;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.webkit.JavascriptInterface;
import hotP2B.WageGainTools.android.AppConfig;
import hotP2B.WageGainTools.android.AppImage;
import hotP2B.WageGainTools.android.utils.UserUtils;

public class HOTAPI 
{

    public static final int ACTION_OPEN_ACCOUNT=1;//开通托管账户
    public static final int ACTION_WITHDRAW=2;    //提现
    public static final int ACTION_INVEST=3;      //投标
    public static final int ACTION_FLOW=4;        //流标
    public static final int ACTION_RECHARGE=5;    //充值
    
    private Activity activity;

    public HOTAPI(Activity activity)
    {
       this.activity = activity;
    }

    
    @JavascriptInterface
    public void onSuccess(int type,String message)
    {
 	   //CustomToastDialog.makeSuccess(activity, message, Toast.LENGTH_LONG).show();
 	   switch(type)
 	   {
    	   case ACTION_OPEN_ACCOUNT:
    	   {
    		   UserUtils.updateUserAccount(UserUtils.TYPE_UPDATE_USER_DELEGATEACCOUT_STATUS,"1");
    	   }
    	   break;
    	   
    	   case ACTION_INVEST:
    	   {
    		   Intent intent=new Intent(AppConfig.LICAI_BROADCAST_ACTION);
    		   activity.sendBroadcast(intent);
    	   }
    	   break;
    	   case ACTION_FLOW:
    	   {
    		   Intent intent=new Intent(AppConfig.FLOW_BROADCAST_ACTION);
    		   activity.sendBroadcast(intent);
    	   }
    	   break;
    	  default:
    	   break;
 	   }
 	  
 	   closeBrowser();
    }
    @JavascriptInterface
    public void onFailure(int type,int errorNo, String strMsg)
    {
 	   //CustomToastDialog.makeError(activity, "错误代码:" + errorNo + ",错误信息:" + strMsg, Toast.LENGTH_LONG).show();
 	   closeBrowser();
    }
    
    @JavascriptInterface
    public void showImage(String url)
    {
    	Intent intent = new Intent();
        intent.putExtra(AppImage.URL_KEY, url);
        intent.setClass(activity, AppImage.class);
        activity.startActivity(intent);
    }
    
    private void closeBrowser()
    {
    	new Handler().postDelayed(new Runnable()
    	{
			@Override
			public void run() 
			{
				activity.finish();
			}
    		
    	}, 1500);
    }
}