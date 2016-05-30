package hotP2B.WageGainTools.android;

import java.util.ArrayList;
import java.util.List;

import org.kymjs.kjframe.ui.BindView;

import android.annotation.SuppressLint;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import hotP2B.WageGainTools.android.adapter.GuideAdapter;

public class AppManual extends AppTitleBar implements OnPageChangeListener
{


	private List<View> views;
	
	@BindView(id=R.id.guide_viewpager)
	private ViewPager viewpager;
	
	
	private GuideAdapter adapter;

	
	@Override
	public void setRootView() 
	{
		this.setContentView(R.layout.aty_manual);
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
	  this.mTvTitle.setText(R.string.manual);

	}
    
    @Override
    public void initWidget() 
    {
        super.initWidget();   
	    initViews();
    }

	@SuppressLint("InflateParams")
	private void initViews() 
	{
		LayoutInflater inflater = LayoutInflater.from(this);
	
		views = new ArrayList<View>();
		
	
		// 初始化引导图片列表
		views.add(inflater.inflate(R.layout.aty_manual_one, null));
		views.add(inflater.inflate(R.layout.aty_manual_two, null));
		views.add(inflater.inflate(R.layout.aty_manual_three, null));
		views.add(inflater.inflate(R.layout.aty_manual_four, null));

		this.adapter = new GuideAdapter(views);
		viewpager.setAdapter(this.adapter);
		viewpager.setOnPageChangeListener(this);
	}
	
	
     @Override
  	 public void onBackClick() 
  	 {
  		super.onBackClick();
  		this.finish();	
  	 }

	@Override
	public void onPageScrollStateChanged(int arg0)
	{
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

		
	}

	@Override
	public void onPageSelected(int arg0) 
	{

	}
}
