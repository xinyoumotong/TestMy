package hotP2B.WageGainTools.android.share;

import java.io.File;
import java.net.URI;
import java.net.URL;

import org.kymjs.kjframe.utils.DensityUtils;

import hotP2B.WageGainTools.android.AppConfig;
import hotP2B.WageGainTools.android.AppMain;
import hotP2B.WageGainTools.android.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Toast;

public class ShareActivity extends Activity 
{

    private View contentView = null;
    private View mshareView=null;
    
    private View wxCircle = null;
    private View wxfriend = null;
    private View qZone = null;
    private View qq = null;
    private View cancel = null;
 
    private TranslateAnimation animation;
    
    public static final String SHARE_TARGET = "share_target";
    public static final String SHARE_TITLE = "share_title";
    public static final String SHARE_CONTENT = "share_content";
    public static final String SHARE_PREVIEW = "share_preview";
 
    private String share_content = "";
    private String share_preview = "";
    private String share_target = "";
    private String share_title = "";
    
    public static Bitmap bitmap_logo=null;
    public static String bitmap_logo_path;
    

    
    private View.OnClickListener shareClick = new View.OnClickListener()
    {
    	@Override
    	public void onClick(View v)
        {
		switch(v.getId())
		{
	      case R.id.share_wx_circle:
	        ShareActivity.this.handleShareToWXCircle();
	        ShareActivity.this.close();
	        break;
	      case R.id.share_wx_friend:
  	        ShareActivity.this.handleShareToWXFriend();
  	        ShareActivity.this.close();
  	        break;
	      case R.id.share_qq:
	        ShareActivity.this.handleShareToQQ();
	        ShareActivity.this.close();
	        break;
	      case R.id.share_qzone:
	    	ShareActivity.this.handleShareToQZone();
    	    ShareActivity.this.close();
    	    break;
	      case R.id.share_cancel:
	    	ShareActivity.this.close();
	    	break;  
		   default:
			   break;
		}
      }
    };
    
    private void handleShareToWXCircle() 
    {
    	if (!WXEventHandler.getInstance().canShare())
  	    {
    		Toast.makeText(this, "请先安装微信客户端", Toast.LENGTH_SHORT).show();
    		return;
  	    }
  	    if (!WXEventHandler.getInstance().canShareToWXSceneTimeline())
  	    {
  	    	Toast.makeText(this, "微信版本过低,不支持分享到朋友圈", Toast.LENGTH_SHORT).show();
    		return;
  	    }
    	boolean b =false;
        if(!TextUtils.isEmpty(this.share_preview))
        {
            b=WXEventHandler.getInstance().shareWXSceneTimeline(this.share_target, this.share_title, this.share_content, this.share_preview);
        }
        else
        {
      	  b=WXEventHandler.getInstance().shareWXSceneTimeline(this.share_target, this.share_title, this.share_content,ShareActivity.bitmap_logo); 
        }
       if (!b)
       {
    	   Toast.makeText(this, "分享错误,请重试", Toast.LENGTH_SHORT).show();
       }
	}

   
	private void handleShareToWXFriend()
    {
	  if (!WXEventHandler.getInstance().canShare())
      {
		Toast.makeText(this, "请先安装微信客户端", Toast.LENGTH_SHORT).show();
		return;
      }
	  
      boolean b =false;
      if(!TextUtils.isEmpty(this.share_preview))
      {
          b=WXEventHandler.getInstance().shareWXSceneSession(this.share_target, this.share_title, this.share_content, this.share_preview);
      }
      else
      {
    	  b=WXEventHandler.getInstance().shareWXSceneSession(this.share_target, this.share_title, this.share_content,ShareActivity.bitmap_logo); 
      }
      
      if (!b)
     {
    	 Toast.makeText(this, "分享错误,请重试", Toast.LENGTH_SHORT).show();
      }
    }
    
    private void handleShareToQQ()
    {
      if(!TextUtils.isEmpty(this.share_preview))
   	  { 
         QQEventHandler.getInstance().shareWebPageToQQ(this.share_target, this.share_title, this.share_content, this.share_preview,AppMain.appMain);
   	  }
      else
      {
    	  QQEventHandler.getInstance().shareWebPageToQQ(this.share_target, this.share_title, this.share_content, ShareActivity.bitmap_logo, AppMain.appMain);
      }
    }

    private void handleShareToQZone()
    {
	  if(!TextUtils.isEmpty(this.share_preview))
	  { 
		  QQEventHandler.getInstance().shareWebPageToQZone(this.share_target, this.share_title, this.share_content, this.share_preview, AppMain.appMain);

	  }
	  else
	  {
		  QQEventHandler.getInstance().shareWebPageToQZone(this.share_target, this.share_title, this.share_content,ShareActivity.bitmap_logo,AppMain.appMain);
	  }
    }
    
   
	@SuppressWarnings("deprecation")
	@SuppressLint("InflateParams")
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		this.contentView = getLayoutInflater().inflate(R.layout.aty_share, null);
		setContentView(this.contentView);
		this.mshareView = findViewById(R.id.share_layout);
		this.animation = new TranslateAnimation(0.0F, 0.0F, DensityUtils.dip2px(this,125.0F),0);
		this.animation.setDuration(300L);
		
		ShareActivity.bitmap_logo=((BitmapDrawable)getResources().getDrawable(R.mipmap.ic_launcher)).getBitmap();
		ShareActivity.bitmap_logo_path=AppConfig.shareImagePath+"hotp2b_logo.png";
		File file=new File(AppConfig.shareImagePath);
		if(!file.exists())
		{
			file.mkdirs();
		}


		getWindow().getDecorView().setOnTouchListener(new View.OnTouchListener()
		{
			@SuppressLint("ClickableViewAccessibility")
			@Override
			public boolean onTouch(View v, MotionEvent event) 
			{
				if (event.getAction() == MotionEvent.ACTION_UP)
		        {
		          Rect rect = new Rect();
		          ShareActivity.this.mshareView.getHitRect(rect);
		          if (!rect.contains((int)event.getX(), (int)event.getY()))
		          {
		        	  ShareActivity.this.close();
				      return true;
		          }
		        }
		       
		        return false;  
			}
		    
		});
		
		initView();
		
		handleIntent(savedInstanceState);
		
	}
	
	
    private void initView()
    {
   
	    this.wxCircle = findViewById(R.id.share_wx_circle);
	    this.wxfriend = findViewById(R.id.share_wx_friend);
	    this.qq = findViewById(R.id.share_qq);
	    this.qZone = findViewById(R.id.share_qzone);
	    this.cancel= findViewById(R.id.share_cancel);

	   
	    
	    this.wxfriend.setOnClickListener(this.shareClick);
	    this.wxCircle.setOnClickListener(this.shareClick);
	    this.qq.setOnClickListener(this.shareClick);
	    this.qZone.setOnClickListener(this.shareClick);
	    this.cancel.setOnClickListener(this.shareClick);

	   
	    WXEventHandler.getInstance().registerApp(getApplicationContext());
	    QQEventHandler.getInstance().init(getApplicationContext());


     }
	
    private void handleIntent(Bundle bundle)
    {
	    if ((bundle == null) && (getIntent() != null))
	    {
	    	bundle = getIntent().getExtras();
	    }
	    if(bundle != null)
	    {
	      this.share_title = bundle.getString(SHARE_TITLE);
	      this.share_content = bundle.getString(SHARE_CONTENT);
	      this.share_preview = bundle.getString(SHARE_PREVIEW);
	      this.share_target = bundle.getString(SHARE_TARGET);
	      if (this.share_target != null)
	      {
	        this.share_target = toURLEncoded(this.share_target);
	      }
	     
	    }
	    this.mshareView.setVisibility(View.VISIBLE);
	    this.mshareView.startAnimation(this.animation);
    }
    
    public String toURLEncoded(String share_target)
    {
      if ((share_target == null) || (share_target.equals(""))) return "";
      try
      {
         URL url = new URL(share_target);
         String str = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef()).toURL().toString();
         return str;
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
      return "";
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
       if (keyCode == KeyEvent.KEYCODE_BACK)
       {
          close();
          return true;
       }
      return super.onKeyDown(keyCode,  event);
    }
    
    private void close()
    {
      TranslateAnimation animation = new TranslateAnimation(0.0F, 0.0F, 0.0F, DensityUtils.dip2px(this,125.0F));
      animation.setDuration(300L);
      animation.setAnimationListener(new Animation.AnimationListener()
      {
		@Override
		public void onAnimationStart(Animation animation) {
			
		}

		@Override
		public void onAnimationEnd(Animation animation) 
		{
			ShareActivity.this.mshareView.setVisibility(View.GONE);
	        ShareActivity.this.contentView.setVisibility(View.INVISIBLE);
	        ShareActivity.this.finish();
			
		}

		@Override
		public void onAnimationRepeat(Animation animation) {

			
		}
        
      });
      this.mshareView.startAnimation(animation);
    }

	 @Override
	 protected void onDestroy() 
	 {
	    try
	    {
	       WXEventHandler.getInstance().unRegisterApp();
	       super.onDestroy();
	    }
	    catch (Exception e)
	    {
	        e.printStackTrace();
	    }
	}
	 

}
