package hotP2B.WageGainTools.android.utils;

import android.annotation.SuppressLint;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.kymjs.kjframe.utils.StringUtils;

@SuppressLint("SimpleDateFormat")
public class TimeUtils {

    private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() 
    {
      
		@Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    public static boolean dateIsTody(String sdate) {
        Date time = null;

        if (StringUtils.isInEasternEightZones()) {
            time = StringUtils.toDate(sdate);
        } else {
            time = StringUtils.transformTime(StringUtils.toDate(sdate),
                    TimeZone.getTimeZone("GMT+08"), TimeZone.getDefault());
        }
        if (time == null) {
            return false;
        }
    
        Calendar cal = Calendar.getInstance();

        // 判断是否是同一天
        String curDate = dateFormater.get().format(cal.getTime());
        String paramDate = dateFormater.get().format(time);
        return curDate.equals(paramDate);
    }
    
    //将日期转换为指定的格式
	public static String formatDateTime(Date date,String format)
    {
    	 SimpleDateFormat df = new SimpleDateFormat(format);
         return df.format(date);
    }
    
    
    //判断字符串是否是合法的日期
    public static boolean isValidDate(String str,String format) 
    {
        boolean b=false;
        SimpleDateFormat sf = new SimpleDateFormat(format);
        try 
        {
            sf.setLenient(false);
            sf.parse(str);
            b=true;
        } 
        catch (ParseException e) 
        {
        } 
        return b;
    }
    
    public static Date addDate(Date date,int field,int value)
    {
    	GregorianCalendar gc=new GregorianCalendar();
    	gc.setTime(date);
    	gc.add(field,value); 
    	return gc.getTime();
    	
    }
    
    
    
}
