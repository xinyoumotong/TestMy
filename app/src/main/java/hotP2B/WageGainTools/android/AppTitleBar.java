package hotP2B.WageGainTools.android;

import org.kymjs.kjframe.KJActivity;

import android.annotation.TargetApi;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import hotP2B.WageGainTools.android.ui.fragment.TitleBarFragment;
import hotP2B.WageGainTools.android.ui.widget.SystemBarTintManager;

/**
 * 应用Activity基类
 * 
 */
public abstract class AppTitleBar extends KJActivity {


    public RelativeLayout mRlTitleBar;
    public ImageView mImgBack;
    public ImageView mImgMenu;
    public TextView mTvTitle;
    
    protected TitleBarFragment currentFragment;

    protected final Handler mMainLoopHandler = new Handler(Looper.getMainLooper());


    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

	    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
	    {
		  setTranslucentStatus(true);
		}
		SystemBarTintManager tintManager = new SystemBarTintManager(this);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintResource(R.color.titlebar);
		
        super.onCreate(savedInstanceState);

    }
    
    @TargetApi(19) 
	private void setTranslucentStatus(boolean on) 
    {
		Window win = getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}

  
    @Override
    public void initData()
    {
		 try 
		 {
	         mRlTitleBar = (RelativeLayout) findViewById(R.id.titlebar);
	         mImgBack = (ImageView) findViewById(R.id.titlebar_img_back);
	         mImgMenu=(ImageView)findViewById(R.id.titlebar_img_menu);
	         mTvTitle = (TextView) findViewById(R.id.titlebar_text_title);
	         mImgBack.setOnClickListener(this);
	         mImgMenu.setOnClickListener(this);
	         
	     }
		 catch (NullPointerException e) 
		 {
	         throw new NullPointerException("TitleBar Notfound from Activity layout");
	     }
		 
		 initTitleBar();
    }
    
    public void initTitleBar()
    {
    	
    }

    @Override
    public void widgetClick(View v) 
    {
        super.widgetClick(v);
        
        switch (v.getId()) 
        {
        case R.id.titlebar_img_back:
            onBackClick();
            break;
        case R.id.titlebar_img_menu:
        	onMenuClick();
            break;
        default:
            break;
        }
        
    }
    
    public void changeFragment(int resView, TitleBarFragment targetFragment) 
    {
        if (targetFragment.equals(currentFragment)) 
        {
            return;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!targetFragment.isAdded()) 
        {
            transaction.add(resView, targetFragment, targetFragment.getClass().getName());
        }
        if (targetFragment.isHidden()) 
        {
            transaction.show(targetFragment);
            targetFragment.onChange();
        }
        if (currentFragment != null && currentFragment.isVisible()) 
        {
            transaction.hide(currentFragment);
        }
        currentFragment = targetFragment;
        transaction.commit();
    }

    protected void onBackClick() {}

    protected void onMenuClick() {}
    



}
