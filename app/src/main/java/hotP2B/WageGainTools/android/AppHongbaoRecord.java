package hotP2B.WageGainTools.android;

import java.util.ArrayList;
import java.util.List;

import org.kymjs.kjframe.ui.BindView;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import hotP2B.WageGainTools.android.adapter.TabStripAdapter;
import hotP2B.WageGainTools.android.ui.fragment.HongbaoBalanceFragment;
import hotP2B.WageGainTools.android.ui.fragment.HongbaoIncomeFragment;
import hotP2B.WageGainTools.android.ui.widget.PagerSlidingTabStrip;

public class AppHongbaoRecord extends AppTitleBar 
{
	@BindView(id = R.id.hongbao_record_tabstrip)
	private PagerSlidingTabStrip m_hongbao_record_tabstrip;
	@BindView(id = R.id.hongbao_record_viewpager)
	private ViewPager m_hongbao_record_viewpager;
	
	private List<Fragment> mFragments;
	private Fragment hongbaoBalanceFragment;
	private Fragment hongbaoIncomeFragment;
	

	@Override
	public void setRootView() 
	{
		this.setContentView(R.layout.aty_hongbao_record);
	}

	
	@Override
    public void initData() 
    {
        super.initData();
    }

    @Override
	public void initTitleBar()
	{
		this.mImgBack.setVisibility(View.VISIBLE);
		this.mTvTitle.setText("礼拜红包记录");
	}

    @Override
    public void initWidget() 
    {
        super.initWidget(); 
        
        this.mFragments = new ArrayList<Fragment>();
		this.hongbaoIncomeFragment = new HongbaoBalanceFragment();
	    this.hongbaoBalanceFragment = new HongbaoIncomeFragment();
		this.mFragments.add(this.hongbaoIncomeFragment);
		this.mFragments.add(this.hongbaoBalanceFragment);
		
        this.m_hongbao_record_viewpager.setAdapter(new TabStripAdapter(this.getSupportFragmentManager(),this.mFragments,this,R.array.hongbaoRecord_option));
	    this.m_hongbao_record_tabstrip.setViewPager(m_hongbao_record_viewpager);
        
    }

    @Override
	public void onBackClick() 
	{
		super.onBackClick();
		this.finish();	
	}
}