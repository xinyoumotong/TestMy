package hotP2B.WageGainTools.android.ui.widget;

import android.content.Context;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class EditTextWithDel extends EditText 
{
	private ImageView imgDel=null;


	public EditTextWithDel(Context context) 
	{
		super(context);
		init();
	}

	public EditTextWithDel(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public EditTextWithDel(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	public void setImageView(ImageView img)
	{
		this.imgDel=img;

		this.imgDel.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				setText("");
				
			}
		});
		setDrawable();
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
		if(imgDel==null)return;
		if (length() >= 1 && this.isFocused()) 
		{
			imgDel.setVisibility(View.VISIBLE);
		} 
		else
		{
			imgDel.setVisibility(View.GONE);
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
