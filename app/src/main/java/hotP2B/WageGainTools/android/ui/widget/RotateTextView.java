package hotP2B.WageGainTools.android.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;
import hotP2B.WageGainTools.android.R;

public class RotateTextView extends TextView 
{
	private int degree;
	
	public RotateTextView(Context context) 
	{
		super(context,null);
	}
	public RotateTextView(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		this.setGravity(Gravity.CENTER);
		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RotateTextView);
		this.degree = typedArray.getInt(0, 0);
		typedArray.recycle();

	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		canvas.save();
		canvas.translate(getCompoundPaddingLeft(), getExtendedPaddingTop());
		canvas.rotate(this.degree, getWidth() / 2.0F, getHeight() / 2.0F);
	    super.onDraw(canvas);
	    canvas.restore();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	    setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
	}

	public void setDegrees(int degree)
	{
	    this.degree = degree;
	}

	
	

}
