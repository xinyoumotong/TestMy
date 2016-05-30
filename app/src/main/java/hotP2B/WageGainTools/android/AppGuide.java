package hotP2B.WageGainTools.android;

import java.util.ArrayList;
import java.util.List;

import org.kymjs.kjframe.KJActivity;
import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.utils.PreferenceHelper;
import org.kymjs.kjframe.utils.SystemTool;

import android.annotation.SuppressLint;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import hotP2B.WageGainTools.android.adapter.GuideAdapter;

public class AppGuide extends KJActivity implements OnPageChangeListener
{
	@BindView(id=R.id.guide_viewpager)
	private ViewPager viewpager;
	@BindView(id=R.id.guide_ll_indicator)
	private LinearLayout ll_indicator;
	
	private GuideAdapter adapter;
	private ImageView[] dots;
	private List<View> views;
	private int currentIndex;

	@Override
    public void setRootView() 
	{
		setContentView(R.layout.aty_guide);
    }

    @Override
    public void initWidget() 
    {
        super.initWidget();
   
     	initViews();
     	initDots();
    }

	@SuppressLint("InflateParams")
	private void initViews() 
	{
		LayoutInflater inflater = LayoutInflater.from(this);
		views = new ArrayList<View>();
		views.add(inflater.inflate(R.layout.aty_what_new_one, null));
		views.add(inflater.inflate(R.layout.aty_what_new_two, null));
		views.add(inflater.inflate(R.layout.aty_what_new_three, null));
		views.add(inflater.inflate(R.layout.aty_what_new_four, null));
	
		this.adapter = new GuideAdapter(views);
		viewpager.setAdapter(this.adapter);
		viewpager.setOnPageChangeListener(this);
	}

	private void initDots() 
	{
		dots = new ImageView[views.size()];
		for (int i = 0; i < views.size(); i++) 
		{
		  dots[i] = (ImageView) ll_indicator.getChildAt(i);
		  dots[i].setSelected(false);
		}

		currentIndex = 0;
		dots[currentIndex].setSelected(true);
	}

	private void setCurrentDot(int position) 
	{
		if (position < 0 || position > (views.size() - 1) || currentIndex == position) 
		{
			return;
		}

		dots[currentIndex].setSelected(false);
		dots[position].setSelected(true);
		
		currentIndex = position;
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
		if(arg0==(views.size()-1))
		{
			ll_indicator.setVisibility(View.GONE);
		}
		else
		{
			ll_indicator.setVisibility(View.VISIBLE);
		}
		setCurrentDot(arg0);
	}
	

	
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
    	return true;
    }
	public void start(View v)
	{
	    String key="firstopen_"+SystemTool.getAppVersionName(this.aty);
	    PreferenceHelper.write(aty,AppConfig.APPNAME,key,false);
	    AppSimpleBack.postShowWith(this.aty, SimpleBackPage.LOGIN);
		this.finish();
	}

}
