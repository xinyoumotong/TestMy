package hotP2B.WageGainTools.android.ui.widget;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.kymjs.kjframe.utils.StringUtils;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;


public class SimpleDatePicker extends EditText implements DatePickerDialog.OnDateSetListener {
	public interface OnDateSetListener 
	{
		public void onDateSet(SimpleDatePicker view, int year, int month, int day);
	}
	
	protected int year;
	protected int month;
	protected int day;
	protected long maxDate = -1;
	protected long minDate = -1;
	protected OnDateSetListener onDateSetListener = null;
	@SuppressLint("SimpleDateFormat")
	protected SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
	
	public OnDateSetListener getOnDateSetListener() {
		return onDateSetListener;
	}

	public void setOnDateSetListener(OnDateSetListener onDateSetListener) {
		this.onDateSetListener = onDateSetListener;
	}
	
	public SimpleDatePicker(Context context, AttributeSet attrs) {
		super(context, attrs);

		setInputType(InputType.TYPE_DATETIME_VARIATION_DATE);
		setFocusable(false);
		setToday();
	}
	
	public int getYear() {
		return year;
		
	}

	public void setYear(int year) {
		this.year = year;
		updateText();
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
		updateText();
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
		updateText();
	}
	
	public void setDate(int year, int month, int day) {
		this.year = year;
		this.month = month;
		this.day = day;
		
		updateText();
		

	}
	
	
	@SuppressWarnings("deprecation")
	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN)
		{
			String strDate=this.getText().toString().trim();
			if(!StringUtils.isEmpty(strDate))
			{
				Date curDate=StringUtils.toDate(strDate,dateFormat);
				if(curDate!=null)
				{
					this.year=1900+curDate.getYear();
					this.month=curDate.getMonth();
					this.day=curDate.getDate();
				}
			}
			showDatePicker();
		}
		
		return super.onTouchEvent(event);
	}
	
	public SimpleDateFormat getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(SimpleDateFormat dateFormat) {
		this.dateFormat = dateFormat;
		updateText();
	}
	
	public void setMaxDate(int year, int month, int day) {
		Calendar c = Calendar.getInstance();
		c.set(year, month, day, 23, 59, 59);
		this.maxDate = c.getTimeInMillis();
	}

	public void setMinDate(int year, int month, int day) {
		Calendar c = Calendar.getInstance();
		c.set(year, month, day, 0, 0, 0);
		this.minDate = c.getTimeInMillis();
	}
	
	public void setToday() {
		Calendar c = Calendar.getInstance();
		setDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
	}

	@SuppressWarnings("deprecation")
	protected void showDatePicker() 
	{
		final DatePickerDialog datePickerDialog = new DatePickerDialog(
				getContext(),
				this,
				getYear(),
				getMonth(),
				getDay());
		
		if (this.maxDate != -1) {
			datePickerDialog.getDatePicker().setMaxDate(maxDate);
		}
		if (this.minDate != -1) {
			datePickerDialog.getDatePicker().setMinDate(minDate);
		}
	
		datePickerDialog.setButton("确定", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which) {

				
			}
			
		});
		datePickerDialog.show();
	}

	@Override
	public void onDateSet(android.widget.DatePicker view, int year,
			int month, int day) {
		setDate(year, month, day);
		clearFocus();
		
		if(onDateSetListener != null)
			onDateSetListener.onDateSet(this, year, month, day);
	}
	
	public void updateText() {
		Calendar cal = new GregorianCalendar(getYear(), getMonth(), getDay());
		setText(dateFormat.format(cal.getTime()));
	}
}
