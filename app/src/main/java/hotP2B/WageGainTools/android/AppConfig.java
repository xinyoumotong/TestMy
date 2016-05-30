package hotP2B.WageGainTools.android;

import java.io.File;

import org.kymjs.kjframe.http.HttpConfig;

import android.os.Environment;

public class AppConfig 
{
	public static final String saveFolder = "hotP2B.WageGainTools.android";
	public static final String httpCachePath = saveFolder + "/httpCache";
	public static final String imgCachePath = saveFolder + "/imageCache";
	public static final String webImagePath = saveFolder + "/webImage";
	public static final String shareImagePath =Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+ saveFolder + "/shareImage/";
	
	public static final String APPNAME="hotP2B";
	public static final String PACKAGENAME="hotP2B.WageGainTools.android";
	public static final String ALARM_BROADCAST_ACTION=PACKAGENAME+".pushmessage.alarm";//推送
	public static final String ASSET_BROADCAST_ACTION=PACKAGENAME+".asset.alarm";//资产
	public static final String BALANCE_BROADCAST_ACTION=PACKAGENAME+".balance.alarm";//余额
	public static final String LICAI_BROADCAST_ACTION=PACKAGENAME+".licai.alarm";//理财
	public static final String FLOW_BROADCAST_ACTION=PACKAGENAME+".flow.alarm";//流标
	public static final String HONGBAO_BROADCAST_ACTION=PACKAGENAME+".hongbao.alarm";//红包
	public static final int NEWBID_INVEST_MAX_TIME=10;
	
	
	//permission
	public static final int REQUEST_PERMISSION_READ_PHONE_STATE=0;
	public static final int REQUEST_PERMISSION_CAMERA=1;
	public static final int REQUEST_PERMISSION_STORAGE=2;
	
	//DB
	public static final String DBNAME="hotP2B.db";
	public static final int DBVERSION=1;
	
	public static final int PAGE_SIZE=10;
	public static final HttpConfig GlobalHttpConfig=new HttpConfig();//应用于全局的HttpConfig
	
    public static final int ERROR_NONETWORK = 1;  // 网络错误
    public static final int ERROR_NODATA= 2;      //没有数据
    public static final int ERROR_FORMAT = 3;     // 格式错误
    public static final int ERROR_TOKEN=4;        //Token错误
    public static final int ERROR_DECRYPT=5;      //解密错误
    
	//推送
	public static final int ALARM_INTERVAL=120000;//定时器的间隔时间，单位为毫秒
	public static final int ACTION_NOTICE_URL=1;
	public static final String LOGIN_MANUAL="1";
	public static final String LONGIN_AUTO="2";
	
	
	//理财
	public static final int BID_STATE_DISCARD=-1;
	public static final int BID_STATE_OK=1;
    public static final int BID_STATE_RETURN=2;
    public static final int BID_STATE_ONGOING=3;
    
	
	//URL地址,测试-联调-正式
	public static final String Server="http://192.168.6.20:807/";
//    public static final String Server="http://42.159.240.77:81/";
	//public static final String Server="http://wgtools.service.hotp2b.com/";
	//理财测试地址
	public static final String Server_Licai="http://192.168.6.20:806/";
//	public static final String Server_Licai="http://81wgtoolsop.hotp2b.com/";
	//public static final String Server_Licai="http://wgtoolsop.hotp2b.com/";
	
	public static final String ServicePrivderBaseUrl=Server+"wgtservice/";//接口站点的基础地址
	public static final String URL_GET_TOKEN=ServicePrivderBaseUrl+"token/token.htm?grant_type=client_credential&client=wgtools_ios";
	public static final String URL_GET_VERIFYCODE =ServicePrivderBaseUrl+ "mobileverification/mobileverification.htm?access_token=[@ACCESS_TOKEN]&lang=zh_CN";
	public static final String URL_GET_VERIFYCODE_FORRESETPWD =ServicePrivderBaseUrl+ "forgetpass/forgetpass.htm?access_token=[@ACCESS_TOKEN]&lang=zh_CN";
	public static final String URL_REGISTER = AppConfig.ServicePrivderBaseUrl+ "mobileverification/verifycode.htm?access_token=[@ACCESS_TOKEN]&lang=zh_CN";      
	public static final String URL_GETTEMPOTATYPWD = AppConfig.ServicePrivderBaseUrl+ "forgetpass/verifypasscode.htm?access_token=[@ACCESS_TOKEN]&lang=zh_CN";
	public static final String URL_REALAUTH = AppConfig.ServicePrivderBaseUrl+ "mobileverification/realauthentication.htm?access_token=[@ACCESS_TOKEN]&lang=zh_CN";
	public static final String URL_LOGIN= AppConfig.ServicePrivderBaseUrl+ "userlogin/userlogin.htm?access_token=[@ACCESS_TOKEN]&lang=zh_CN";
	public static final String URL_GET_GLOBALNOTICE=AppConfig.ServicePrivderBaseUrl+"notice/getglobalnotification.htm?access_token=[@ACCESS_TOKEN]&lang=zh_CN";
	public static final String URL_GET_USERNOTICE=AppConfig.ServicePrivderBaseUrl+"notice/getusernotification.htm?access_token=[@ACCESS_TOKEN]&lang=zh_CN";
	public static final String URL_CHANGEPASSWORD= AppConfig.ServicePrivderBaseUrl+ "updatepassword/updatepassword.htm?access_token=[@ACCESS_TOKEN]&lang=zh_CN";
	public static final String URL_UPDATEPORTRAIT=AppConfig.ServicePrivderBaseUrl+"image/image.htm?access_token=[@ACCESS_TOKEN]&lang=zh_CN";
	public static final String URL_GET_PERSONAL=AppConfig.ServicePrivderBaseUrl+"userlogin/readuserinfo.htm?access_token=[@ACCESS_TOKEN]&lang=zh_CN";
	public static final String URL_UPDATE_PERSONAL=AppConfig.ServicePrivderBaseUrl+"userlogin/updateuserinfo.htm?access_token=[@ACCESS_TOKEN]&lang=zh_CN";
	public static final String URL_GET_BANKCARD=AppConfig.ServicePrivderBaseUrl+"image/getcardid.htm?access_token=[@ACCESS_TOKEN]&lang=zh_CN";
	public static final String URL_GET_USERNOTICE_ALL=AppConfig.ServicePrivderBaseUrl+"notice/getallusernotification.htm?access_token=[@ACCESS_TOKEN]&lang=zh_CN";
	public static final String URL_GET_GLOBALNOTICE_ALL=AppConfig.ServicePrivderBaseUrl+"notice/getallglobalnotification.htm?access_token=[@ACCESS_TOKEN]&lang=zh_CN";
	public static final String URL_GET_SMS=AppConfig.ServicePrivderBaseUrl+"mobileverification/sendmobilecode.htm?access_token=[@ACCESS_TOKEN]&lang=zh_CN";

	//解绑银行卡
		public static final String URL_UNBIND_BANKCARD=Server+"huifuapi/querrycardinfo/userdeletecard.htm?access_token=[@ACCESS_TOKEN]&lang=zh_CN";

	
	//资产相关接口
	public static final String URL_HUIFU_OPEN_ACCOUNT=Server+"huifuapi/userregister/userregister.htm?access_token=[@ACCESS_TOKEN]&client=android";
	public static final String URL_HUIFU_BIND_BANKCARD=Server+"huifuapi/userbindcard/userbindcard.htm?access_token=[@ACCESS_TOKEN]";
	public static final String URL_HUIFU_WITHDRAW=Server+"huifuapi/usercash/usercash.htm?access_token=[@ACCESS_TOKEN]&client=android";
	public static final String URL_HUIFU_RECHARGE=Server+"huifuapi/userrecharge/recharge.htm?access_token=[@ACCESS_TOKEN]&client=android";
	public static final String URL_HUIFU_INVEST=Server+"hotp2b/loan/activebid.htm?access_token=[@ACCESS_TOKEN]&client=android";
	public static final String URL_HUIFU_FLOWBID=Server+"hotp2b/loan/loanfail.htm?access_token=[@ACCESS_TOKEN]&client=android";
	public static final String URL_HUIFU_APPLY_BANKWATER=Server+"hotp2b/loan/userpayment.htm?access_token=[@ACCESS_TOKEN]&client=android";
	
	public static final String URL_GET_ASSETS=Server+"finance/financeuserassets/getuserasset.htm?access_token=[@ACCESS_TOKEN]&lang=zh_CN";
	public static final String URL_GET_BALANCE=Server+"finance/balance/getbalance.htm?access_token=[@ACCESS_TOKEN]&lang=zh_CN";
	public static final String URL_GET_INCOME=Server+"finance/financeincomelist/userincomedetail.htm?access_token=[@ACCESS_TOKEN]&lang=zh_CN";
	public static final String URL_GET_SALARY=Server+"finance/payroll/getpayroll.htm?access_token=[@ACCESS_TOKEN]&lang=zh_CN";
	
	public static final String URL_GET_INVEST_HISTORY1=Server+"hotp2b/invest/loaning.htm?access_token=[@ACCESS_TOKEN]&lang=zh_CN";
	public static final String URL_GET_INVEST_HISTORY2=Server+"hotp2b/invest/loanpayment.htm?access_token=[@ACCESS_TOKEN]&lang=zh_CN";
	public static final String URL_GET_INVEST_HISTORY3=Server+"hotp2b/invest/loanfaillist.htm?access_token=[@ACCESS_TOKEN]&lang=zh_CN";
	public static final String URL_GET_INVEST_BIDCHECK=Server+"hotp2b/invest/checkbalance.htm?access_token=[@ACCESS_TOKEN]&lang=zh_CN";
	public static final String URL_GET_BANKWATER_CHECK=Server+"hotp2b/loan/userpayrequest.htm?access_token=[@ACCESS_TOKEN]&lang=zh_CN";
	
	
	
	public static final String URL_GET_HONGBAOINFO=Server+"redpackage/redpackage/getassets.htm?access_token=[@ACCESS_TOKEN]&lang=zh_CN";
	public static final String URL_SET_HONGBAO=Server+"redpackage/redpackage/redset.htm?access_token=[@ACCESS_TOKEN]&lang=zh_CN";
	public static final String URL_GET_HONGBAO_BALANCE=Server+"redpackage/reddetail/redreserve.htm?access_token=[@ACCESS_TOKEN]&lang=zh_CN";
	public static final String URL_GET_HONGBAO_INCOME=Server+"redpackage/reddetail/redincome.htm?access_token=[@ACCESS_TOKEN]&lang=zh_CN";
	public static final String URL_OPEN_HONGBAO=Server+"redpackage/redpackage/sendredpackage.htm?access_token=[@ACCESS_TOKEN]&lang=zh_CN";
	public static final String URL_ROLLOUT_HONGBAO=Server+"redpackage/reddetail/transfer.htm?access_token=[@ACCESS_TOKEN]&lang=zh_CN";
	
	//理财平台接口
	public static final String URL_GET_LICAIPRODUCT=Server_Licai+"outinterface/index/getfinancials.htm";
	public static final String URL_GET_UPDATE=Server_Licai+"outinterface/index/getversion-andorid.htm";
	public static final String URL_GET_FIND=Server_Licai+"outinterface/index/getnews.htm";
	public static final String URL_GET_BROADCASTNOTICE=Server_Licai+"outinterface/index/getbroad.htm";
	public static final String URL_APP_INTRODUCE=Server_Licai+"www/index/abouthotp2b.htm";
	public static final String URL_ACCOUNT_SAFE=Server_Licai+"www/index/accountsafety.htm";
	public static final String URL_HELP=Server_Licai+"www/index/helpercenter.htm";
    public static final String URL_GET_BIDINFO=Server_Licai+"outinterface/index/getfinancialcontents.htm";
    
   

}