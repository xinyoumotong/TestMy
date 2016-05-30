package hotP2B.WageGainTools.android;

import java.io.UnsupportedEncodingException;
import java.security.PublicKey;
import java.util.List;
import java.util.Random;

import hotP2B.WageGainTools.android.bean.UserAccount;
import hotP2B.WageGainTools.android.bean.response.VerifyCodeResponse;
import hotP2B.WageGainTools.android.receiver.AlarmReceiver;
import hotP2B.WageGainTools.android.utils.AESCrypt;
import hotP2B.WageGainTools.android.utils.RsaHelper;
import hotP2B.WageGainTools.android.utils.TokenUtils;
import hotP2B.WageGainTools.android.utils.UserUtils;
import hotP2B.WageGainTools.android.utils.TokenUtils.TokenInfo;

import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;
import org.kymjs.kjframe.KJDB;
import org.kymjs.kjframe.bitmap.BitmapConfig;
import org.kymjs.kjframe.database.DaoConfig;
import org.kymjs.kjframe.http.HttpConfig;
import org.kymjs.kjframe.ui.KJActivityStack;
import org.kymjs.kjframe.utils.DensityUtils;
import org.kymjs.kjframe.utils.KJLoger;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Base64;

@ReportsCrashes
(
   //formUri = "http://192.168.8.127:8080/DemoTwo/Demo", //192.168.8.127
   mailTo = "pengfei736@163.com;18237152851@163.com",
   mode = ReportingInteractionMode.DIALOG,
   resDialogIcon = R.mipmap.ic_launcher,
   resDialogText = R.string.crash_dialog_text,
   resDialogTitle = R.string.crash_dialog_title,
   resDialogNegativeButtonText = R.string.crash_dialog_negativebutton_text,
   resDialogPositiveButtonText = R.string.crash_dialog_positivebutton_text
)
public class AppContext extends Application
{
    public static int screenW;
    public static int screenH;
    public static UserAccount m_CurrentAccount;
    public static boolean m_CurrentAccountIsLogin;

    public static KJDB kjdb=null;
    public static VerifyCodeResponse verifyCodeResponse=null;
    public static VerifyCodeResponse verifyCodeResponse2=null;
    public static boolean bForceUpdate=false;
    public static boolean bHasNewVersion=false;
    
    public static AppContext application;
    public static int IsNewInvestors=1;
    public static String IMEI="";
   
    @SuppressWarnings("deprecation")
	@Override
    public void onCreate() 
    {
        super.onCreate();
        ACRA.init(this);

		HttpConfig.TIMEOUT = 30000;
      
        BitmapConfig.CACHEPATH = AppConfig.imgCachePath;
        HttpConfig.CACHEPATH = AppConfig.httpCachePath;

        KJLoger.openDebutLog(false);
        KJLoger.openActivityState(false);
        m_CurrentAccount=null;
        m_CurrentAccountIsLogin=false;
        verifyCodeResponse=null;
        verifyCodeResponse2=null;
        
		AppContext.screenH = DensityUtils.getScreenH(this.getApplicationContext());
		AppContext.screenW = DensityUtils.getScreenW(this.getApplicationContext());
        
        AppConfig.GlobalHttpConfig.useDelayCache=false;
        AppConfig.GlobalHttpConfig.cacheTime = 0;
        
        application=this;
    }

	@Override
	public void onTerminate() {

    	//退出时发送一次定时器广播
    	Intent intent = new Intent(AppConfig.ALARM_BROADCAST_ACTION);  
    	sendBroadcast(intent);  
		super.onTerminate();
	}
	
    public static void exitUI()
    {
    	KJActivityStack.create().finishAllActivity();
    	System.exit(0);
    }
    //启动全局定时器，调动Alarmreceiver开始工作
    @SuppressLint("InlinedApi")
	public static void StartAlarmBroadcast(Context context)
    {
        Intent intent = new Intent(context, AlarmReceiver.class);  
        intent.setAction(AppConfig.ALARM_BROADCAST_ACTION);  
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);  
        long firstime = SystemClock.elapsedRealtime();  
        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);  
        am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, firstime,AppConfig.ALARM_INTERVAL, sender);  
    	
    }
    public static boolean isProessRunning(Context context, String proessName) 
    {
		boolean isRunning = false;
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

		List<RunningAppProcessInfo> lists = am.getRunningAppProcesses();
		for (RunningAppProcessInfo info : lists) 
		{
			if (info.processName.equals(proessName)) 
			{
				isRunning = true;
			}
		}
		return isRunning;
	}
    public static String CryptData(String text,String key)
	{
    	String result=null;
		try 
		{
			AESCrypt aes=new AESCrypt(key);
			String tstr=aes.encrypt(text);
			result=tstr;
		} catch (Exception e) 
		{
			e.printStackTrace();
			KJLoger.debug("CryptData()异常，信息为"+e.getMessage());
			result=null;
		}
		return result;
	}
    public static String DecryptData(String text,String key)
	{
    	String result=null;
		try 
		{
			AESCrypt aes=new AESCrypt(key);
			String tstr=aes.decrypt(text);
			result=tstr;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			KJLoger.debug("DecryptData()异常，信息为"+e.getMessage());
			result=null;
		}
		return result;
	}
    
    public static String getGolden() { 
    	int length=96;//length表示生成字符串的长度  
        String base = "0123456789abcdefghijklmnopqrstuvwxyz";     
        Random random = new Random();     
        StringBuffer sb = new StringBuffer();     
        for (int i = 0; i < length; i++) 
        {     
            int number = random.nextInt(base.length());     
            sb.append(base.charAt(number));     
        }     
        return sb.toString();     
    }     
    public static String getGoldenKeyByToken(String golden,TokenInfo token)
    {
    	String result=null;
		PublicKey pubKey=RsaHelper.decodePublicKeyFromXml(token.getServerPubKey());
		try 
		{
			byte[] requestArray= golden.getBytes("utf-8");
			byte[] requestSourceArray=RsaHelper.encryptData(requestArray, pubKey);
		
			result=new String(Base64.encode(requestSourceArray,Base64.URL_SAFE|Base64.NO_PADDING),"utf-8");
			
		} catch (UnsupportedEncodingException e) 
		{
			e.printStackTrace();
			result= null;
		}
		return result;
    }

	
	 public static KJDB getKJDB(Context context)
	 {
		if(kjdb==null)
		{
			DaoConfig config=new DaoConfig();
			config.setContext(context);
			config.setDbName(AppConfig.DBNAME);
			config.setDbVersion(AppConfig.DBVERSION);
			
			kjdb=KJDB.create(config);
		}
		return kjdb;
	 }
	 
	//退出当前账号
 	public static void logout(Activity aty) 
 	{
 		UserUtils.deleteUser(aty);
 	    AppContext.m_CurrentAccount=null;
 	    AppContext.m_CurrentAccountIsLogin=false;
 	    AppContext.verifyCodeResponse=null;
 	    AppContext.verifyCodeResponse2=null;
 	    AppContext.bForceUpdate=false;
 	    AppContext.bHasNewVersion=false;
		TokenUtils.deleteLocalToken(AppContext.application);
		KJActivityStack.create().finishAllActivity();
 	    AppSimpleBack.postShowWith(aty,SimpleBackPage.LOGIN);
 	}
	
}