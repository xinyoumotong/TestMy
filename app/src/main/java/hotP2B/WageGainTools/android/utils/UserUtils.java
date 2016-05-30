package hotP2B.WageGainTools.android.utils;

import java.util.List;

import org.kymjs.kjframe.KJDB;
import org.kymjs.kjframe.database.annotate.Id;
import org.kymjs.kjframe.utils.KJLoger;

import android.content.Context;
import hotP2B.WageGainTools.android.AppContext;
import hotP2B.WageGainTools.android.NotProguard;
import hotP2B.WageGainTools.android.bean.UserAccount;
import hotP2B.WageGainTools.android.bean.response.LoginResponse;

public class UserUtils 
{
	public static final int TYPE_UPDATE_USER_REALNAME=1;
	public static final int TYPE_UPDATE_USER_REALAUTH=2; 
	public static final int TYPE_UPDATE_USER_IMAGENAME=3; 
	public static final int TYPE_UPDATE_USER_DELEGATEACCOUT_STATUS=4; 
	public static final int TYPE_UPDATE_USER_BALANCE=5; 

	
	@NotProguard
	public static class User
	{
		@Id
	    private int id; // 本地数据库id
		private String username;
		private String userpkid;
	    private String canautologin;
	    private String loginkeyid;
	    
	    public User()
	    {
	    	
	    }
	    public User(String username,String userpkid,String canautologin,String loginkeyid)
	    {
	    	this.id=0;
	    	this.username=username;
	    	this.userpkid=userpkid;
	    	this.canautologin=canautologin;
	    	this.loginkeyid=loginkeyid;
	    	
	    }
	    
	    public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
	    
	    public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}

		public String getUserpkid() {
			return userpkid;
		}
		public void setUserpkid(String userpkid) {
			this.userpkid = userpkid;
		}
		public String getCanautologin() {
			return canautologin;
		}
		public void setCanautologin(String canautologin) {
			this.canautologin = canautologin;
		}
		
		public String getLoginkeyid() {
			return loginkeyid;
		}
		public void setLoginkeyid(String loginkeyid) {
			this.loginkeyid = loginkeyid;
		}
	}
	
	
	public static UserAccount parse2UserAccount(LoginResponse loginResponse,String username)
	{
		UserAccount userAccount=null;
		try 
		{
			userAccount=new  UserAccount();
			userAccount.setUsername(username);
			userAccount.setUserpkid(loginResponse.getUserpkid());
			userAccount.setCanautologin(loginResponse.getCanautologin());
			userAccount.setReallyname(loginResponse.getReallyname());
			userAccount.setBizpkid(loginResponse.getBizpkid());
			userAccount.setBizname(loginResponse.getBizname());
			userAccount.setReallyidentity(loginResponse.getReallyidentity());
			userAccount.setLoginkeyid(loginResponse.getLoginkeyid());
			userAccount.setImagename(loginResponse.getImagename());
			userAccount.setAuthentcustid(loginResponse.getAuthentcustid());
	    } 
		catch (Exception e) 
		{
	       KJLoger.exception(e);
	       userAccount=null;
	    }
		return userAccount;
	}
	
	public static void updateUserAccount(int type,String value)
	{
		if(AppContext.m_CurrentAccount==null)return;
		switch(type)
		{
		case TYPE_UPDATE_USER_REALNAME://更新真实姓名
			AppContext.m_CurrentAccount.setReallyname(value);
			break;
		case TYPE_UPDATE_USER_REALAUTH://更新实名认证状态
			AppContext.m_CurrentAccount.setReallyidentity(value);
			break;
		case TYPE_UPDATE_USER_IMAGENAME://更新头像
			AppContext.m_CurrentAccount.setImagename(value);
			break;
		case TYPE_UPDATE_USER_DELEGATEACCOUT_STATUS://更改用户托管状态
			AppContext.m_CurrentAccount.setAuthentcustid(value);
			break;
		case TYPE_UPDATE_USER_BALANCE://更新账户余额
			AppContext.m_CurrentAccount.setBalance(value);
			break;
		default:
			break;
		}
	}
	public static void updateUserBankInfo(int bankCount,String balance)
	{
		if(AppContext.m_CurrentAccount==null)return;

	    AppContext.m_CurrentAccount.setBankCardCount(bankCount);
	    AppContext.m_CurrentAccount.setBalance(balance);
	
	}
		
	 public static void saveUser(Context context, User u) 
	 {
	     KJDB kjdb =AppContext.getKJDB(context);
	     kjdb.deleteByWhere(User.class, "");
	     kjdb.save(u);
	 }
	 public static void deleteUser(Context context) 
	 {
		 KJDB kjdb =AppContext.getKJDB(context);
	     kjdb.deleteByWhere(User.class, "");
	 }
	 
	  public static synchronized User getLocalUser(Context context) 
	  {
    	User result=null;
    	KJDB kjdb =AppContext.getKJDB(context);
        List<User> datas = kjdb.findAll(User.class);
        if (datas != null && datas.size() > 0) 
        {
        	result = datas.get(0);
        } 
        else 
        {
        	result=null;
        }
        return result;
	 }
	
}
