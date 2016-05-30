package hotP2B.WageGainTools.android.utils;

import java.util.List;

import org.kymjs.kjframe.KJDB;
import org.kymjs.kjframe.database.annotate.Id;
import org.kymjs.kjframe.utils.KJLoger;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import android.content.Context;
import android.util.Log;
import hotP2B.WageGainTools.android.AppContext;
import hotP2B.WageGainTools.android.NotProguard;
import hotP2B.WageGainTools.android.bean.response.TokenResponse;


public class TokenUtils 
{
	@NotProguard
	public static class TokenInfo
	{
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getToken() {
			return token;
		}
		public void setToken(String token) {
			this.token = token;
		}
		public String getClientPriKey() {
			return clientPriKey;
		}
		public void setClientPriKey(String clientPriKey) {
			this.clientPriKey = clientPriKey;
		}
		public String getServerPubKey() {
			return serverPubKey;
		}
		public void setServerPubKey(String serverPubKey) {
			this.serverPubKey = serverPubKey;
		}
		public long getExpires_in() {
			return expires_in;
		}
		public void setExpires_in(long expires_in) {
			this.expires_in = expires_in;
		}
		public long getTokenTimeByLocal() {
			return tokenTimeByLocal;
		}
		public void setTokenTimeByLocal(long tokenTimeByLocal) {
			this.tokenTimeByLocal = tokenTimeByLocal;
		}
		public long getTokenInvalidTimeByLocal() {
			return tokenInvalidTimeByLocal;
		}
		public void setTokenInvalidTimeByLocal(long tokenInvalidTimeByLocal) {
			this.tokenInvalidTimeByLocal = tokenInvalidTimeByLocal;
		}
		public String getDevice_id() {
			return device_id;
		}
		public void setDevice_id(String device_id) {
			this.device_id = device_id;
		}
		public Boolean isInvalid()
		{
			return System.currentTimeMillis()>tokenInvalidTimeByLocal;
		}
		
		@Id
	    private int id; // 数据库id

		@XStreamAlias("token")
		private String token;
		@XStreamAlias("clientPriKey")
		private String clientPriKey;
		@XStreamAlias("serverPubKey")
		private String serverPubKey;
		@XStreamAlias("tokenTimeByLocal")
		private long tokenTimeByLocal;
		@XStreamAlias("tokenInvalidTimeByLocal")
		private long tokenInvalidTimeByLocal;
		
		@XStreamAlias("expires_in")
		private long expires_in;
		
		@XStreamAlias("device_id")
		private String device_id;
		
	}

	public static TokenInfo parse2TokenInfo(TokenResponse tokenResponse)
	{
		TokenInfo result=null;
		try 
		{
			result=new  TokenInfo();
			result.setId(0);
	        result.setToken(tokenResponse.getAccess_token());
	        result.setServerPubKey(tokenResponse.getRsa_client_publickey());
	        long curr=System.currentTimeMillis();
	        long expires_in=Integer.parseInt(tokenResponse.getExpires_in())*1000;//这里是毫秒
	        long invalidTime=curr+expires_in;
	        result.setTokenTimeByLocal(curr);
	        result.setExpires_in(expires_in);
	        result.setTokenInvalidTimeByLocal(invalidTime);
	            
	        if(result.isInvalid())
	        {
	           KJLoger.debug("获取到的Token已经失效!");
	           result=null;
	        }      
	    } 
		catch (Exception e) 
		{
	       Log.e("parse2TokenInfo 发生解析异常",e.getMessage());
	       KJLoger.exception(e);
	       result=null;
	    }
		return result;
	}
	
	public static void updateTokenInvalidTime(Context context)
	{
		TokenInfo token=getLocalToken(context);
		if(token!=null)
		{
			 long curr=System.currentTimeMillis();
			 token.setTokenTimeByLocal(curr);
			 token.setTokenInvalidTimeByLocal(curr+token.getExpires_in());
			 KJDB kjdb =AppContext.getKJDB(context);
			 kjdb.update(token, "");
		}
		
	}
	public static void saveLocalToken(Context context, TokenInfo token) 
	{
        KJDB kjdb =AppContext.getKJDB(context);
        kjdb.deleteByWhere(TokenInfo.class, "");
        kjdb.save(token);
    }

	public static void deleteLocalToken(Context context)
	{
		KJDB kjdb =AppContext.getKJDB(context);
		kjdb.deleteByWhere(TokenInfo.class, "");
	}
	
	public static TokenInfo getLocalToken(Context context) 
	{
		TokenInfo token=null;
        KJDB kjdb = AppContext.getKJDB(context);
        List<TokenInfo> datas = kjdb.findAll(TokenInfo.class);

        if (datas != null && datas.size() > 0) {
        	token = datas.get(0);
        }
        return token;
    }
}
