package hotP2B.WageGainTools.android;

import android.view.View;
public class AppAssets extends AppTitleBar 
{
	@Override
	public void setRootView() 
	{
		this.setContentView(R.layout.aty_assets);
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
	  this.mTvTitle.setText("总资产");

	}
    
    @Override
    public void initWidget() 
    {
        super.initWidget();  
    }
    
    @Override
   	public void onBackClick() 
   	{
   	    super.onBackClick();
   		this.finish();	
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
	
}