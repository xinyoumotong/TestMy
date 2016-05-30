package hotP2B.WageGainTools.android.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import hotP2B.WageGainTools.android.R;

public class LockPointAdapter extends BaseAdapter 
{

	private Context context;
	private char[] chArray;
	
	public LockPointAdapter(Context context)
	{
	    this.context = context;
	}
	public void setData(String str)
	{
		if(str!=null)
		{
			this.chArray=str.toCharArray();
			this.notifyDataSetChanged();
		}
	}
	@Override
	public int getCount() {
		return 9;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		ImageView image=new ImageView(this.context);
		image.setImageResource(R.mipmap.icon_point_n);
		if(this.chArray!=null)
		{
		   for(int i=0;i<this.chArray.length;i++)
		   {
			 if (this.chArray[i]-'1'== position)
			 {
				 image.setImageResource(R.mipmap.icon_point_s);
				 break;
			 }
		   }
		}
	
		return image;
	}

}
