package hotP2B.WageGainTools.android.adapter;

import java.util.List;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabStripAdapter extends FragmentPagerAdapter 
{
	private List<Fragment> list;
    private final String[] titles;
    
	 public TabStripAdapter(FragmentManager fm,List<Fragment> list,Context context,int resId) 
	 {
		super(fm);
		this.list=list;
		this.titles=context.getResources().getStringArray(resId);
	 }
	
	 @Override
	 public Fragment getItem(int arg0) 
	 {
		return (Fragment)this.list.get(arg0);
	 }

	 @Override
	 public int getCount() 
	 {
		return this.list.size();
	 }
	 
    @Override
    public CharSequence getPageTitle(int position) 
    {
	   return titles[position];
    }
}
