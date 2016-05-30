package hotP2B.WageGainTools.android.bean.response;

import hotP2B.WageGainTools.android.bean.BaseResponse;

public class LoginResponse extends BaseResponse
{
	private static final long serialVersionUID = 1L;
	
	private String userpkid;
    private String canautologin;
	private String reallyname;
	private String bizpkid;
	private String bizname; 
	private String reallyidentity;
	private String loginkeyid;
	private String imagename;
	private String authentcustid;//是否已经开通汇付账户
	

	public String getImagename() {
		return imagename;
	}
	public void setImagename(String imagename) {
		this.imagename = imagename;
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
	public String getReallyname() {
		return reallyname;
	}
	public void setReallyname(String reallyname) {
		this.reallyname = reallyname;
	}
	public String getBizpkid() {
		return bizpkid;
	}
	public void setBizpkid(String bizpkid) {
		this.bizpkid = bizpkid;
	}
	public String getBizname() {
		return bizname;
	}
	public void setBizname(String bizname) {
		this.bizname = bizname;
	}
	public String getReallyidentity() {
		return reallyidentity;
	}
	public void setReallyidentity(String reallyidentity) {
		this.reallyidentity = reallyidentity;
	}
	public String getLoginkeyid() {
		return loginkeyid;
	}
	public void setLoginkeyid(String loginkeyid) {
		this.loginkeyid = loginkeyid;
	}
	
	public String getAuthentcustid() {
		return authentcustid;
	}
	public void setAuthentcustid(String authentcustid) {
		this.authentcustid = authentcustid;
	}

}
