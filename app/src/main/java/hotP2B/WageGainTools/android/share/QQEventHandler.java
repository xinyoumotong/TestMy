package hotP2B.WageGainTools.android.share;

import java.util.ArrayList;

import hotP2B.WageGainTools.android.R;
import hotP2B.WageGainTools.android.utils.ImageHelper;

import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.open.utils.ThreadManager;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

public class QQEventHandler 
{

	
	  private static QQEventHandler instance = null;
	  private Tencent mTencent = null;
	  private Context context=null;
	  
	  public static QQEventHandler getInstance()
	  {
		 if (instance == null)
		 {
		      instance = new QQEventHandler();
		 }
		 return instance;
	  }	
	  
	  public void init(Context context)
	  {
		  this.context=context;
		  mTencent = Tencent.createInstance(ShareConstants.QQ_APP_ID,context);
	  }
	  
	  private void doShareToQQ(final Bundle bundle, final Activity activity)
	  {
		ThreadManager.getMainHandler().post(new Runnable()
	    {
	      @Override
	      public void run()
	      {
	        QQEventHandler.this.mTencent.shareToQQ(activity, bundle, qqShareListener);
	      }
	    });
	  }
	  
	  private void doShareToQzone(final Bundle bundle, final Activity activity)
	  {
		  ThreadManager.getMainHandler().post(new Runnable()
	      {
	        public void run()
	        {
		        QQEventHandler.this.mTencent.shareToQzone(activity, bundle, qZoneShareListener);
	        }
	        });
	   }
	  private IUiListener qqShareListener=new  IUiListener()
      {

		@Override
		public void onCancel() 
		{
			Toast.makeText(context, "QQ分享取消", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onComplete(Object arg0) 
		{
			Toast.makeText(context, "QQ分享完成", Toast.LENGTH_SHORT).show();
			
		}

		@Override
		public void onError(UiError e) 
		{
			Toast.makeText(context, "QQ分享发生错误:"+e.errorMessage, Toast.LENGTH_SHORT).show();
		}
      
      };
	  private IUiListener qZoneShareListener=new  IUiListener()
      {

		@Override
		public void onCancel() 
		{
			Toast.makeText(context, "QQ空间分享取消", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onComplete(Object arg0) 
		{
			Toast.makeText(context, "QQ空间分享完成", Toast.LENGTH_SHORT).show();
			
		}

		@Override
		public void onError(UiError e) 
		{
			Toast.makeText(context, "QQ空间分享发生错误:"+e.errorMessage, Toast.LENGTH_SHORT).show();
		}
      
      };

      public void onActivityResult(int requestCode, int resultCode, Intent data) 
	  { 
    	 
          if (requestCode == Constants.REQUEST_QQ_SHARE) 
          {
          	if (resultCode == Constants.ACTIVITY_OK) 
          	{
          		Tencent.handleResultData(data, qqShareListener);
          	}
          } 
          if (requestCode == Constants.REQUEST_QZONE_SHARE) 
          {
          	if (resultCode == Constants.ACTIVITY_OK) 
          	{
          		Tencent.handleResultData(data, qZoneShareListener);
          	}
          }
	  }
      
      public void shareImageToQQ(String imageurl, Activity activity)
      {
        Bundle bundle = new Bundle();
        bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, imageurl);
        bundle.putString(QQShare.SHARE_TO_QQ_APP_NAME, activity.getResources().getString(R.string.app_name));
        bundle.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
        bundle.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
        doShareToQQ(bundle, activity);
      }
      public void shareImageToQZone(String imageurl, Activity activity)
      {
    	  Bundle bundle = new Bundle();
          bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, imageurl);
          bundle.putString(QQShare.SHARE_TO_QQ_APP_NAME, activity.getResources().getString(R.string.app_name));
          bundle.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
          bundle.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);
          doShareToQQ(bundle, activity);
      }
      

    

      public void shareWebPageToQQ(String targetUrl, String title, String summary, String imageUrl, Activity activity)
      {
         Bundle bundle = new Bundle();
         bundle.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
         bundle.putString(QQShare.SHARE_TO_QQ_TITLE, title);
         bundle.putString(QQShare.SHARE_TO_QQ_SUMMARY, summary);
         bundle.putString(QQShare.SHARE_TO_QQ_TARGET_URL, targetUrl);
         bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imageUrl);
         bundle.putString(QQShare.SHARE_TO_QQ_APP_NAME, activity.getResources().getString(R.string.app_name));
         bundle.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare.SHARE_TO_QQ_FLAG_QZONE_ITEM_HIDE);
         doShareToQQ(bundle, activity);
      }
      public void shareWebPageToQQ(String targetUrl, String title, String summary, Bitmap bitmap, Activity activity)
      {
    	  ImageHelper.saveImage(bitmap, ShareActivity.bitmap_logo_path);
    	  shareWebPageToQQ(targetUrl,title, summary,ShareActivity.bitmap_logo_path,activity);
      }

      public void shareWebPageToQZone(String targetUrl, String title, String summary, String imageUrl, Activity activity)
      {
    	Bundle bundle = new Bundle();
        bundle.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
        bundle.putString(QzoneShare.SHARE_TO_QQ_TITLE, title);
        bundle.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, summary);
        bundle.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, targetUrl);
        if(!TextUtils.isEmpty(imageUrl))
    	{
    	    ArrayList<String> imageUrls = new ArrayList<String>();
    	    imageUrls.add(imageUrl);
    	    bundle.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imageUrls);
    	}
   
        doShareToQzone(bundle, activity);
      }
      
      public void shareWebPageToQZone(String targetUrl, String title, String summary, Bitmap bitmap, Activity activity)
      {
    	  ImageHelper.saveImage(bitmap, ShareActivity.bitmap_logo_path);
    	  shareWebPageToQZone(targetUrl,title, summary,ShareActivity.bitmap_logo_path,activity);
      }
}
