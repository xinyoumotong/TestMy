package hotP2B.WageGainTools.android.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import hotP2B.WageGainTools.android.R;

public class TemplateFragment extends TitleBarFragment {

	@Override
	protected View inflaterView(LayoutInflater arg0, ViewGroup arg1, Bundle arg2) {
		View view = View.inflate(outsideAty, R.layout.test, null);
		return view;
	}

	 @Override
	 protected void setActionBarRes(ActionBarRes actionBarRes) 
	 {
		 super.setActionBarRes(actionBarRes);
		
	 }
	 
	 @Override
	 protected void initData() 
	 {
	   super.initData();    
	 }

	 @Override
	 protected void initWidget(View parentView) 
	 {
	   super.initWidget(parentView);
	   
    
	 }


     @Override
	 public void onBackClick() 
	 {
		super.onBackClick();
		this.outsideAty.finish();	
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
