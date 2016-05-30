package hotP2B.WageGainTools.android;

import hotP2B.WageGainTools.android.R;
import hotP2B.WageGainTools.android.share.QQEventHandler;
import hotP2B.WageGainTools.android.ui.fragment.TitleBarFragment;
import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.utils.DensityUtils;

import com.tencent.connect.common.Constants;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;


/**
 * 应用主界面
 */

public class AppMain extends AppTitleBar implements OnTabChangeListener
{

    private DoubleClickExitHelper mDoubleClickExit;
    @BindView(id=android.R.id.tabhost)
    private FragmentTabHost mTabHost;
    
    public static AppMain appMain;

    @Override
    public void setRootView() 
    {
        setContentView(R.layout.aty_main);
    }
    
    @Override
    public void initData() 
    {
        super.initData();
    }
    

    @Override
    public void initWidget() 
    {
        super.initWidget();    
        
        mDoubleClickExit = new DoubleClickExitHelper(this);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.main_content);
        mTabHost.getTabWidget().setShowDividers(0);
        initTabs();
  
        mTabHost.setOnTabChangedListener(this);
        mTabHost.setCurrentTab(0);

        appMain=this;
	    AppContext.StartAlarmBroadcast(aty);
	    
	   
  
    }
    
    public void setTab(int index)
    {
    	if(mTabHost!=null)
    	{
    		mTabHost.setCurrentTab(index);
    	}
    }

   
	@SuppressLint("InflateParams")
	@SuppressWarnings("deprecation")
	private void initTabs() 
    {
        MainTab[] tabs = MainTab.values();
        final int size = tabs.length;
        for (int i = 0; i < size; i++) 
        {
            MainTab mainTab = tabs[i];
            TabSpec tab = mTabHost.newTabSpec(getString(mainTab.getResName()));
            
            View indicator = LayoutInflater.from(getApplicationContext()).inflate(R.layout.tab_indicator, null);
            TextView title = (TextView) indicator.findViewById(R.id.tab_title);
            ImageView tab_img = (ImageView) indicator.findViewById(R.id.tab_img);

//            tab_img.setLayoutParams(new LinearLayout.LayoutParams((1 * DensityUtils.getDialogW(this)) / 14,(1 * DensityUtils.getDialogW(this)) / 14));

			Drawable drawable = this.getResources().getDrawable(mainTab.getResIcon());
            tab_img.setImageDrawable(drawable);
//            title.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null,null);
            title.setText(getString(mainTab.getResName()));

            tab.setIndicator(indicator);
            tab.setContent(new TabContentFactory() 
            {
                @Override
                public View createTabContent(String tag) 
                {
                    return new View(AppMain.this);
                }
            });
            mTabHost.addTab(tab, mainTab.getClz(), null);
    
          
        }
    }
    
    @Override
    protected void onResume() 
    {
       super.onResume();
    }
    
    @Override
    public void widgetClick(View v) 
    {
        super.widgetClick(v);
        
        switch (v.getId()) 
        {
        default:
            break;
        }
    }

	@Override
    protected void onBackClick() 
	{
        super.onBackClick();
        currentFragment=(TitleBarFragment)getCurrentFragment();
        if(currentFragment!=null)
        {
          currentFragment.onBackClick();
        }
    }

    @Override
    protected void onMenuClick() 
    {
        super.onMenuClick();
        currentFragment=(TitleBarFragment)getCurrentFragment();
        if(currentFragment!=null)
        {
          currentFragment.onMenuClick();
        }
    }

    @Override
    public void onTabChanged(String tabId) 
    {
        final int size = mTabHost.getTabWidget().getTabCount();
        for (int i = 0; i < size; i++) 
        {
            View v = mTabHost.getTabWidget().getChildAt(i);
            if (i == mTabHost.getCurrentTab()) 
            {
                v.setSelected(true);
            } 
            else 
            {
               v.setSelected(false);
            }
        }
    }

    private Fragment getCurrentFragment() 
    {
        return getSupportFragmentManager().findFragmentByTag(mTabHost.getCurrentTabTag());
    }

    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{
	     if (keyCode == KeyEvent.KEYCODE_BACK) 
	     {
             return mDoubleClickExit.onKeyDown(keyCode, event);
	     }
	     return super.onKeyDown(keyCode, event);
	}
    
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) 
	{ 
		if (requestCode == Constants.REQUEST_QQ_SHARE || requestCode == Constants.REQUEST_QZONE_SHARE) 
		{
		   QQEventHandler.getInstance().onActivityResult(requestCode, resultCode, data);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

}
