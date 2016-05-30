package hotP2B.WageGainTools.android.dialog;

import android.content.Context;
import hotP2B.WageGainTools.android.R;

public class RingProgressDialog extends CustomDialog {

	public RingProgressDialog(Context context) {
		super(context);
	}
	
	public RingProgressDialog(Context context,String msg) 
	{
		super(context,msg);
	}

	@Override
	public int getLayoutId() {
		return R.layout.dialog_ring_progress;
	}



}
