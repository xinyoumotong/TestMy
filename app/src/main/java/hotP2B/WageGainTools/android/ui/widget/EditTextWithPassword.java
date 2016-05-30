package hotP2B.WageGainTools.android.ui.widget;

import android.content.Context;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class EditTextWithPassword extends EditText 
{
	private ImageView img=null;


	public EditTextWithPassword(Context context) 
	{
		super(context);
		init();
	}

	public EditTextWithPassword(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public EditTextWithPassword(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	public void setImageView(ImageView img)
	{
		this.img=img;

		this.img.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				showPassword();
				
			}

			
		});
		setDrawable();
	}
	
	private void showPassword() 
	{
		TransformationMethod method=this.getTransformationMethod();
		if(method instanceof HideReturnsTransformationMethod)
		{
			this.setTransformationMethod(PasswordTransformationMethod.getInstance());
			img.setSelected(false);
		}
		else
		{
			this.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
			img.setSelected(true);
		}
		this.setSelection(this.getText().length());	
	}
	
	private void init() 
	{
	
		addTextChangedListener(new TextWatcher() 
		{
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			@Override
			public void afterTextChanged(Editable s) 
			{
				setDrawable();
			}
			
		});

	}
	
	//设置删除图片
	private void setDrawable() 
	{
		if(img==null)return;
		if (length() >= 1 && this.isFocused()) 
		{
			img.setVisibility(View.VISIBLE);
		} 
		else
		{
			img.setVisibility(View.GONE);
		}
	}
	
	/**
	 * 焦点改变时的处理
	 */
	@Override
	protected void onFocusChanged(boolean focused, int direction,Rect previouslyFocusedRect) 
	{
		 setDrawable();
		super.onFocusChanged(focused, direction, previouslyFocusedRect);
	}
}