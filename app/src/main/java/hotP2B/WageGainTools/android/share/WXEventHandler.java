package hotP2B.WageGainTools.android.share;

import java.util.Date;

import com.tencent.mm.sdk.openapi.BaseReq;
import com.tencent.mm.sdk.openapi.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXWebpageObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.widget.Toast;
import hotP2B.WageGainTools.android.AppConfig;
import hotP2B.WageGainTools.android.utils.ImageHelper;
import hotP2B.WageGainTools.android.utils.TimeUtils;

public class WXEventHandler implements  IWXAPIEventHandler 
{


	 private static WXEventHandler instance = null;
	 private IWXAPI api = null;

	 
	 private Context context;
	 
	 private String buildTransaction(final String type) 
     {
		return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
	 }
	 
	
	 
	 public static WXEventHandler getInstance()
	 {
	    if (instance == null)
	    {
	      instance = new WXEventHandler();
	    }
	    return instance;
	 }	
	 public boolean canShare()
	 {
	    return isWXInstalled();
	 }

	 public boolean canShareToWXSceneTimeline()
	 {
	    return (isWXInstalled()) && (this.api.getWXAppSupportAPI() >= 0x21020001);
	 }
	 public IWXAPI getWXapi()
	 {
	    return this.api;
	 }
	 
	 public boolean isWXInstalled()
	 {
	   return this.api.isWXAppInstalled();
	 }
	 
	 public void registerApp(Context context)
	 {
	    if (context == null) return;
	    this.context=context;
	    this.api = WXAPIFactory.createWXAPI(context, ShareConstants.WX_APP_ID, false);
	    this.api.registerApp(ShareConstants.WX_APP_ID);
	 }
	 

	 public boolean shareWXSceneSession(String url, String title, String decription, Bitmap bitmap)
	  {
	    if (url == null) return false;
	    return shareWebPageToWX(SendMessageToWX.Req.WXSceneSession, url, title, decription, bitmap);
	  }

	  public boolean shareWXSceneSession(String url, String title, String description, String imageurl)
	  {
	    if (url == null) return false;
	    return shareWebPageToWX(SendMessageToWX.Req.WXSceneSession, url, title, description, imageurl);
	  }

	  public boolean shareWXSceneTimeline(String url, String title, String description, Bitmap bitmap)
	  {
	    if ((!canShareToWXSceneTimeline()) || (url == null)) return false;
	    return shareWebPageToWX(SendMessageToWX.Req.WXSceneTimeline, url, title, description, bitmap);
	  }

	  public boolean shareWXSceneTimeline(String url, String title, String description, String imageurl)
	  {
	    if ((!canShareToWXSceneTimeline()) || (url == null)) return false;
	    return shareWebPageToWX(SendMessageToWX.Req.WXSceneTimeline, url, title, description, imageurl);
	  }

	  
	  public boolean shareWebPageToWX(final int type, final String url, final String title, final String description, final Bitmap bitmap)
	  {
	    new Handler().post(new Runnable()
	    {
	    	@Override
	        public void run()
	        {
	    		WXWebpageObject webpage = new WXWebpageObject();
	    		webpage.webpageUrl = url;
	    		
	    		WXMediaMessage msg = new WXMediaMessage(webpage);
	    		msg.title = title;
	    		msg.description =description;
	            msg.setThumbImage(bitmap);
	            SendMessageToWX.Req req = new SendMessageToWX.Req();
	            req.transaction = WXEventHandler.this.buildTransaction("webpage");
	            req.message =msg;
	            req.scene = type;
	            WXEventHandler.this.api.sendReq(req);
	         }
	    });
	    return true;
	  }

	  public boolean shareWebPageToWX(final int type, final String url, final String title, final String description, final String imageurl)
	  {
	    new Thread(new Runnable()
	    {
	    	@Override
	        public void run()
	        {
	    		WXWebpageObject webpage = new WXWebpageObject();
	    		webpage.webpageUrl = url;
	    		
	    		WXMediaMessage msg = new WXMediaMessage(webpage);
	    		msg.title = title;
	    		msg.description =description;
	    		String filename="";
		    	if(imageurl.lastIndexOf("/")>0)
		    	{
		    	    filename="share_"+TimeUtils.formatDateTime(new Date(),"yyyyMMddHHmm")+"_"+imageurl.substring(imageurl.lastIndexOf("/")+1);	
		        }
		    	else
		    	{
		    		filename="share_"+System.currentTimeMillis()+".png";	
		    	}
	        	ImageHelper.saveWebImage(imageurl, AppConfig.shareImagePath+filename);
	        	Bitmap bitmap=ImageHelper.extractThumbNail(AppConfig.shareImagePath+filename, 150, 150, true);
	    		msg.setThumbImage(bitmap);
	            SendMessageToWX.Req req = new SendMessageToWX.Req();
	            req.transaction = WXEventHandler.this.buildTransaction("webpage");
	            req.message =msg;
	            req.scene = type;
	            WXEventHandler.this.api.sendReq(req);
	         }
	    }).start();
	    return true;
	  }
	  

    public void unRegisterApp()
	{
	  this.api.unregisterApp();
	}
    
    
    @Override
	public void onReq(BaseReq req) {
		
	}

	@Override
	public void onResp(BaseResp resp) 
	{
		String str;
		if (resp instanceof SendMessageToWX.Resp)
		{
		   switch (resp.errCode)
		   {
		      case 0:
		    	  str = "微信分享成功";
		    	  break;
		      case -2:
		    	  str = "微信分享取消";
		    	  break;
		      case -4:
		    	  str = "微信分享被拒绝";
		    	  break;
		      default:
			      str = "微信分享发生错误:"+resp.errStr;
			      break;
		    }
		    Toast.makeText(this.context, str, Toast.LENGTH_SHORT).show();
		}


	}

	public void handleIntent(Intent intent) 
	{
		if(intent==null || api==null) return;
		api.handleIntent(intent, this);
		
	}
}
