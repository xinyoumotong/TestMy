package hotP2B.WageGainTools.android.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.TextView;
import hotP2B.WageGainTools.android.R;

public class CustomProgressDialog extends Dialog 
{

	private CircularProgressView mProgressView;
	private TextView mMessageView;
	
	private String mMessage;
	
	private static final String MESSSAGE_DEFAULT="加载中...";
	private static final int STYLE_DEFAULT=R.style.CustomDialogStyle;
	
	
	private final int []Colors={Color.GREEN,Color.BLUE,Color.RED};
	private int colorIndex=0;
	
	public CustomProgressDialog(Context context)
	{
		this(context,STYLE_DEFAULT,null,false);
	}
	public CustomProgressDialog(Context context,String message)
	{
		this(context,STYLE_DEFAULT,message,false);
	}
	
	public CustomProgressDialog(Context context,boolean cancelable)
	{
		this(context,STYLE_DEFAULT,null,cancelable);
	}
	
	public CustomProgressDialog(Context context,int theme)
	{
		this(context,theme,null,false);
	}
	public CustomProgressDialog(Context context,String message,boolean cancelable)
	{
		this(context,STYLE_DEFAULT,message,cancelable);
	}
	public CustomProgressDialog(Context context,int theme,String message)
	{
		this(context,theme,message,false);
	}

	public CustomProgressDialog(Context context,int theme,String message,boolean cancelable)
	{
		super(context,theme);
		this.mMessage=message;
		
		this.setCancelable(cancelable);
		this.setCanceledOnTouchOutside(cancelable);
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.dialog_loading);
		
		this.mProgressView=(CircularProgressView)findViewById(R.id.loading_progress);
		this.mMessageView=(TextView)findViewById(R.id.loading_message);
	
		if(TextUtils.isEmpty(this.mMessage))
		{
			this.mMessageView.setText(MESSSAGE_DEFAULT);
		}
		else
		{
    	   this.mMessageView.setText(this.mMessage);
		}
    	
    	mProgressView.addListener(new CircularProgressViewAdapter() 
    	{
            @Override
            public void onAnimationReset() 
            {
                mProgressView.setColor(Colors[colorIndex++%Colors.length]);
            }

        });
    	startAnimationThreadStuff(100);

      	
	}
	 private void startAnimationThreadStuff(long delay) 
	 {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() 
        {
            @Override
            public void run() 
            {
            	mProgressView.startAnimation();
            }
        }, delay);
	}
	 
	 public String getmMessage() 
	 {
		return mMessage;
	 }

	 public void setmMessage(String mMessage) 
	 {
		this.mMessage = mMessage;
	 }
	
}
