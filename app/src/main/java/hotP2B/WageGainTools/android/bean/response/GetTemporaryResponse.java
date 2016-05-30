package hotP2B.WageGainTools.android.bean.response;

import hotP2B.WageGainTools.android.bean.BaseResponse;

public class GetTemporaryResponse extends BaseResponse
{
	
	private static final long serialVersionUID = 1L;
	private String userpkid;
    private String usernoticeid;
    private String userpass;
    public String getUsernoticeid() {
		return usernoticeid;
	}
	public void setUsernoticeid(String usernoticeid) {
		this.usernoticeid = usernoticeid;
	}
	
	public String getUserpkid() {
		return userpkid;
	}
	public void setUserpkid(String userpkid) {
		this.userpkid = userpkid;
	}
	
	public String getUserpass() {
		return userpass;
	}
	public void setUserpass(String userpass) {
		this.userpkid = userpass;
	}


}
