package hotP2B.WageGainTools.android;

import java.util.ArrayList;
import java.util.List;

import org.kymjs.kjframe.ui.BindView;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import hotP2B.WageGainTools.android.ui.fragment.GlobalNoticeFragment;
import hotP2B.WageGainTools.android.ui.fragment.UserNoticeFragment;
import hotP2B.WageGainTools.android.adapter.TabStripAdapter;
import hotP2B.WageGainTools.android.ui.widget.PagerSlidingTabStrip;


public class AppNotice extends AppTitleBar {

	
	@BindView(id = R.id.notice_tabstrip)
	private PagerSlidingTabStrip m_notice_tabstrip;
	@BindView(id = R.id.notice_viewpager)
	private ViewPager m_notice_viewpager;
	
	private List<Fragment> mFragments;
	private Fragment userNoticeFragment;
	private Fragment globalNoticeFragment;
	

	@Override
	public void setRootView() 
	{
		this.setContentView(R.layout.aty_notice);
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
        
        this.mFragments = new ArrayList<Fragment>();
		this.userNoticeFragment = new UserNoticeFragment();
	    this.globalNoticeFragment = new GlobalNoticeFragment();
		this.mFragments.add(this.userNoticeFragment);
		this.mFragments.add(this.globalNoticeFragment);
		
       this.m_notice_viewpager.setAdapter(new TabStripAdapter(this.getSupportFragmentManager(),this.mFragments,this,R.array.notice_option));
	   this.m_notice_tabstrip.setViewPager(m_notice_viewpager);
        
    }
    
    @Override
	public void initTitleBar()
	{
		this.mImgBack.setVisibility(View.VISIBLE);
		this.mTvTitle.setText("消息通知");
	}
    
    @Override
    protected void onResume() 
    {
    	super.onResume();

    }
    
    @Override
    protected void onDestroy() 
    {
    	super.onDestroy();
    	Intent intent=new Intent(AppConfig.ASSET_BROADCAST_ACTION);
		this.sendBroadcast(intent);

    }
    
    @Override
	 public void onBackClick() 
	 {
		super.onBackClick();
		this.finish();	
	 }
    
}
