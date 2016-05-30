
package hotP2B.WageGainTools.android.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import hotP2B.WageGainTools.android.R;


public abstract class CustomDialog extends Dialog implements View.OnClickListener
{
	protected TextView dialog_tv_msg;
	protected String msg="";
	protected Context context;


	public CustomDialog(Context context) 
	{
		this(context,R.style.CustomDialogStyle);
	}
	
	public CustomDialog(Context context,String msg) 
	{
		this(context,R.style.CustomDialogStyle);
		this.msg=msg;
	}

	public CustomDialog(Context context, int theme) 
	{
		super(context, theme);
		this.context=context;
	}
	
	public CustomDialog(Context context, int theme,String msg) 
	{
		super(context, theme);
		this.msg=msg;
		this.context=context;
	}
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);	
		setContentView(getLayoutId());
		init();
		initData();
		initView();
	
	}
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}


	private void init() 
	{
		try
		{
			dialog_tv_msg=(TextView)this.findViewById(R.id.dialog_tv_msg);
			if(!TextUtils.isEmpty(this.msg))
			{
				dialog_tv_msg.setText(this.msg);
			}
		}
		catch(Exception e)
		{
			
		}
		
	}


	public void initData() {

	}
	public void initView() {
		
	}

	public abstract int getLayoutId();

	@Override
	public void onClick(View v) 
	{
		this.dismiss();
	}

	


}
