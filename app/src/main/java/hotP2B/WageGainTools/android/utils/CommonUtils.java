package hotP2B.WageGainTools.android.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.kymjs.kjframe.utils.SystemTool;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import hotP2B.WageGainTools.android.AppContext;

public class CommonUtils 
{
    private final static Pattern casher = Pattern.compile("^\\d+(\\.\\d{1,2})?$");
    
    //判断密码是否合法
    public static boolean checkPassword(String password)
    {
  	   if(password==null || password.isEmpty())
  	   {
  		  return false;
  	   }
  	  
  	   Pattern pattern = Pattern.compile("[\\da-zA-Z]{6,16}"); 
       Pattern patternNumber = Pattern.compile(".*\\d{1,15}.*"); 
       Pattern patternLetter = Pattern.compile(".*[a-zA-Z]{1,15}.*"); 

       Matcher matcher = pattern.matcher(password); 
       Matcher matno = patternNumber.matcher(password); 
       Matcher maten = patternLetter.matcher(password); 

       if(matno.matches() && maten.matches() && matcher.matches())
       {
      	 return true;
       }
       else
       {
      	 return false;
       }
    }
    
    //判断金额是否合法
    public static boolean isCash(String cash) 
    {
    	if(cash==null || cash.length()==0)
    	{
    		return false;
    	}
        return casher.matcher(cash).matches();
    }
    

    
    //开通托管账户
    public static void openHuifuAccount(Context context,String userpkid)
    {
   	   HttpUtils.openAccount(context,userpkid);
    }
    
    //绑定银行卡
    public static void bindBankCard(Context context,String userpkid)
    {
       HttpUtils.bindCard(context,userpkid); 
    }
    
    //投资
    public static void invest(Context context,String userpkid,String bidproid,String transamt)
    {
    	HttpUtils.invest(context,userpkid,bidproid,transamt); 
    }
    //撤标
    public static void flowbid(Context context,String userpkid,String ordid,String transamt,String freezeid,String bidproid)
    {
    	HttpUtils.flowbid(context,userpkid,ordid,transamt,freezeid,bidproid);
    }
    
    //提现
    public static void withdraw(Context context,String userpkid,String money)
    {
    	HttpUtils.withdraw(context, userpkid, money);
    }
    
    //充值
    public static void recharge(Context context,String userpkid,String money)
    {
    	HttpUtils.recharge(context, userpkid, money);
    }
    
    public static void applyBankWater(Context context,String userpkid, String transamt, String bankid,String accountid)
    {
    	HttpUtils.applyBankWater(context, userpkid, transamt, bankid, accountid);
    }
    
    public static String convertLicaiTotal(double money)
    {
      return (money/10000)+"万";
    }
    
    public static long convertLicaiRemainTime(String Expiredate)
    {
    	long day=0; 
    	if(TimeUtils.isValidDate(Expiredate, "yyyy-MM-dd"))
    	{
    	   try 
    	   {
			  Date dt=new SimpleDateFormat("yyyy-MM-dd").parse(Expiredate);
			  Date now=new Date();
			  day=(dt.getTime()-now.getTime())/(24*60*60*1000);
			  if(day<0)day=0;
			  
	       } 
    	   catch (ParseException e) 
    	   {
			e.printStackTrace();
		   }
    	}
    	return day;
    }
    public static String convertLicaiChargingway(int Chargingway)
    {
    	String str="";
    	switch(Chargingway)
    	{
    	case 1:
    		str="等本等息";
    		break;
    	case 2:
    		str="一次性回款";
    		break;
    	case 3:
    		str="等额本息";
    		break;
    	default:
    		str="未知";
    		break;
    	}
    	return str;
    }
    public static double convertLicaiRemainMoney(double Project_Total,double PaidMoney)
    {
    	
    	BigDecimal b1 = new BigDecimal(String.valueOf(Project_Total));  
 	    BigDecimal b2 = new BigDecimal(String.valueOf(PaidMoney));  
 	    double d=b1.subtract(b2).doubleValue();  
    	if(d<0)
    	{
    	   d=0;
    	}
    	return d;
    }
    
    public static void goSystemSetting(Context context)
    {
	    Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
	    intent.setComponent(new ComponentName("com.android.settings", "com.android.settings.applications.InstalledAppDetails"));
	    intent.setData(Uri.parse("package:" + context.getPackageName()));
	    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    context.startActivity(intent);
    }
    
    public static String getIMEI(Context context)
    {
    	if(!org.kymjs.kjframe.utils.StringUtils.isEmpty(AppContext.IMEI))
    	{
    		return AppContext.IMEI;
    	}
    	try
    	{
    		return SystemTool.getPhoneIMEI(context);
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		return null;
    	}
    }

	//保留两位小数
	public static String keep2Decimal (String str) {
		try {
			DecimalFormat decimalFormat =new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
			double amount = Double.parseDouble(str);
			String string = decimalFormat.format(amount);//format 返回的是字符串
			return string;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

} 
