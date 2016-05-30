package hotP2B.WageGainTools.android.dialog;

import java.lang.reflect.Method;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import hotP2B.WageGainTools.android.R;
import hotP2B.WageGainTools.android.ui.widget.SafetyKeyBoard;
import hotP2B.WageGainTools.android.utils.ScreenUtils;

public class KeyboardDialog extends Dialog implements SafetyKeyBoard.FinishListener,View.OnFocusChangeListener,View.OnClickListener 
{
	private SafetyKeyBoard keyboard;
	private Context context;
	
	public KeyboardDialog(Context context) 
	{
		this(context,R.style.KeyBoardDialogStyle);
	}
	@SuppressLint("InflateParams")
	public KeyboardDialog(Context context, int theme) 
	{
		super(context, theme);
        this.context=context;
		
		View view = getLayoutInflater().inflate(R.layout.dialog_keyboard, null);
	    this.keyboard = (SafetyKeyBoard)view.findViewById(R.id.safetyKeyBoard);
	    this.keyboard.setFinishListener(this);
	    
	    super.setContentView(view);
	}

	@Override
	protected void onCreate(Bundle bundle) 
	{
	    super.onCreate(bundle);
	    this.setCancelable(true);
	    this.setCanceledOnTouchOutside(true);
	    getWindow().setGravity(Gravity.BOTTOM);


        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width =ScreenUtils.getScreenWidth(context);
        p.height=(int) (ScreenUtils.getScreenHeight(context)*0.4D);
        getWindow().setAttributes(p);
	}
	
	public void fillEdit(EditText e, int type)
    {
      if (this.keyboard != null)
      {
    	 e.setOnFocusChangeListener(this);
    	 e.setOnClickListener(this);
    	 setInput(e);
         this.keyboard.setEdit(e, type);
      }
    }

    
    public void setInput(EditText edit)
    {
      if (android.os.Build.VERSION.SDK_INT <= 10) 
      {  
    	  edit.setInputType(InputType.TYPE_NULL);  
      } 
      else 
      {  
           getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);  
           try 
           {  
               Class<EditText> cls = EditText.class;  
               Method setShowSoftInputOnFocus;  
               setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);  
               setShowSoftInputOnFocus.setAccessible(true);  
               setShowSoftInputOnFocus.invoke(edit, false);  
           } 
          catch(Exception e) 
          {  
               try 
               {
            	   Class<EditText> cls = EditText.class;  
                   Method setShowSoftInputOnFocus;  
                   setShowSoftInputOnFocus = cls.getMethod("setSoftInputShownOnFocus", boolean.class);  
                   setShowSoftInputOnFocus.setAccessible(true);
				   setShowSoftInputOnFocus.invoke(edit, false);
			   } 
               catch (Exception e1) 
               {
				  e1.printStackTrace();
			   } 
          }   
      }

    }

	@Override
	public void onFinish() 
	{
	   dismiss();
		
	}
	@Override
	public void onFocusChange(View v, boolean hasFocus) 
	{
		if(hasFocus)
		{
			if(!this.isShowing())
			{
			    this.show();
			}
		   return;
		}
		this.onFinish();
	}
	@Override
	public void onClick(View v) 
	{

		if(!this.isShowing())
		{
		  this.show();
		}

    }

    
	

}
