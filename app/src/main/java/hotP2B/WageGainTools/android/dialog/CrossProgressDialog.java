package hotP2B.WageGainTools.android.dialog;

import android.content.Context;
import hotP2B.WageGainTools.android.R;

public class CrossProgressDialog extends CustomDialog{

	public CrossProgressDialog(Context context) {
		super(context);
	}
	
	public CrossProgressDialog(Context context,String msg) 
	{
		super(context, msg);
	}

	@Override
	public int getLayoutId() 
	{
		return R.layout.dialog_cross_progress;
	}


}
