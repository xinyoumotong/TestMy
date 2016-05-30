package hotP2B.WageGainTools.android.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import hotP2B.WageGainTools.android.R;
import android.view.View;

public class CustomSelectDialog extends Dialog implements View.OnClickListener
{

	private TextView m_select_tv_1;
	private TextView m_select_tv_2;
	private TextView m_select_tv_title;
	
	private String mTitle;
	private String[] mSelectArray;
	
	private IDialogSelectCallBack callback;

	
	private static final int STYLE_DEFAULT=R.style.CustomDialogStyle;

	public CustomSelectDialog(Context context,String title,int selectOptionId,IDialogSelectCallBack callback) 
	{
		this(context,STYLE_DEFAULT,title,selectOptionId,callback);

	}
	public CustomSelectDialog(Context context,int titleId,int selectOptionId,IDialogSelectCallBack callback) 
	{
		this(context,STYLE_DEFAULT,context.getResources().getString(titleId),selectOptionId,callback);

	}
	public CustomSelectDialog(Context context, int theme,String title,int selectOptionId,IDialogSelectCallBack callback) 
	{
		super(context, theme);
		
		this.mTitle=title;
		this.callback=callback;
		
		mSelectArray=context.getResources().getStringArray(selectOptionId);
	}
    
    @Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.dialog_select);
		
		this.m_select_tv_title=(TextView)findViewById(R.id.select_tv_title);
		this.m_select_tv_1=(TextView)findViewById(R.id.select_tv_1);
		this.m_select_tv_2=(TextView)findViewById(R.id.select_tv_2);

		this.m_select_tv_1.setOnClickListener(this);
		this.m_select_tv_2.setOnClickListener(this);
		
		this.m_select_tv_title.setText(this.mTitle);
		this.m_select_tv_1.setText(mSelectArray[0]);
		this.m_select_tv_2.setText(mSelectArray[1]);

	}
	@Override
	public void onClick(View v)
	{
		switch(v.getId())
		{
		case R.id.select_tv_1:
			callback.onSelect(this, 0);
			break;
		case R.id.select_tv_2:
			callback.onSelect(this, 1);
			break;
		}
	}
	
	public static abstract interface IDialogSelectCallBack
	{
		public abstract void onSelect(Dialog dialog, int which);
	}
}
